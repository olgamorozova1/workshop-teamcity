package com.example.teamcity.api;

import com.example.teamcity.api.generators.TestData;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.BuildType.Step;
import com.example.teamcity.api.models.NewProjectDescription;
import com.example.teamcity.api.requests.checked.CheckedBuildConfig;
import com.example.teamcity.api.requests.checked.CheckedProject;
import com.example.teamcity.api.requests.unchecked.UncheckedBuildConfig;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static com.example.teamcity.api.generators.RandomData.getInvalidCharacter;
import static com.example.teamcity.api.generators.RandomData.getNumber;
import static com.example.teamcity.api.generators.RandomData.getString;
import static com.example.teamcity.api.generators.TestDataGenerator.generateBuildType;
import static com.example.teamcity.api.generators.TestDataGenerator.generateProjectDescription;
import static com.example.teamcity.api.generators.TestDataGenerator.generateUser;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;

public class BuildConfigurationTest extends BaseApiTest {

    @Test
    public void buildConfigurationTest() {
        var testData = testDataStorage.addTestData();

        checkedWithSuperUser.getUserRequest().create(testData.getUser());

        var project = new CheckedProject(Specifications.getSpec().authSpec(testData.getUser()))
                .create(testData.getProject());

        softAssert.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
        softAssert.assertAll();
    }


    @DataProvider
    public Object[][] testDataBuildConfig() {
        NewProjectDescription project;
        return new Object[][]{
                {"id min value", project = generateProjectDescription(), generateBuildType(project).withId(getString(1))},
                {"id max value", project = generateProjectDescription(), generateBuildType(project).withId(getString(225))},
                {"name min value", project = generateProjectDescription(), generateBuildType(project).withName(getString(1))},
                {"name max value", project = generateProjectDescription(), generateBuildType(project).withName(getString(255))},
                {"several steps", project = generateProjectDescription(), generateBuildType(project).withSteps(BuildType.Steps.builder().step(List.of(Step.builder().name(getString(10)).type(getString(10)).build(), Step.builder().name(getString(10)).type(getString(10)).build())).build())},
                {"empty steps", project = generateProjectDescription(), generateBuildType(project).withSteps(BuildType.Steps.builder().build())},
        };
    }

    @Test(dataProvider = "testDataBuildConfig")
    public void createBuildConfiguration(String description, NewProjectDescription project, BuildType buildType) {
        var testData = testDataStorage.addTestData(TestData.builder().user(generateUser()).project(project).buildType(buildType).build());
        checkedWithSuperUser.getProjectRequest().create(testData.getProject());
        checkedWithSuperUser.getUserRequest().create(testData.getUser());

        var actualBuildConfig = new CheckedBuildConfig(Specifications.getSpec().authSpec(testData.getUser()))
                .create(testData.getBuildType());

        softAssert.assertThat(actualBuildConfig.getId()).as("Invalid id").isEqualTo(testData.getBuildType().getId());
        softAssert.assertThat(actualBuildConfig.getName()).as("Invalid name").isEqualTo(testData.getBuildType().getName());
        softAssert.assertThat(actualBuildConfig.getProject().getId()).as("Invalid project").isEqualTo(testData.getBuildType().getProject().getId());
        softAssert.assertThat(actualBuildConfig.getSteps()).as("Invalid steps").isEqualTo(testData.getBuildType().getSteps());
        softAssert.assertAll();
    }

    @DataProvider
    public Object[][] testDataInvalidBuildConfig() {
        NewProjectDescription project;
        return new Object[][]{
                {"id < min value", project = generateProjectDescription(), generateBuildType(project).withId(EMPTY), SC_INTERNAL_SERVER_ERROR, "Build configuration or template ID must not be empty"},
                {"id > max value", project = generateProjectDescription(), generateBuildType(project).withId(getString(226)), SC_INTERNAL_SERVER_ERROR, "is invalid: it is 226 characters long while the maximum length is 225. ID should start with a latin letter and contain only latin letters, digits and underscores (at most 225 characters)"},
                {"id starts with digit", project = generateProjectDescription(), generateBuildType(project).withId(getNumber(1)), SC_INTERNAL_SERVER_ERROR, "is invalid"},
                {"id starts with underscore", project = generateProjectDescription(), generateBuildType(project).withId("_" + getString(10)), SC_INTERNAL_SERVER_ERROR, "is invalid: starts with non-letter character '_'. ID should start with a latin letter and contain only latin letters, digits and underscores (at most 225 characters)"},
                {"id contains invalid symbol", project = generateProjectDescription(), generateBuildType(project).withId(getString(10) + getInvalidCharacter()), SC_INTERNAL_SERVER_ERROR, "ID should start with a latin letter and contain only latin letters, digits and underscores (at most 225 characters)"},
                {"name < min value", project = generateProjectDescription(), generateBuildType(project).withName(EMPTY), SC_BAD_REQUEST, "When creating a build type, non empty name should be provided."},
                {"name > max value", project = generateProjectDescription(), generateBuildType(project).withName(getString(256)), SC_BAD_REQUEST, ""},
                {"invalid project id", project = generateProjectDescription(), generateBuildType(generateProjectDescription()), SC_NOT_FOUND, "No project found by locator"},
        };
    }

    @Test(dataProvider = "testDataInvalidBuildConfig")
    public void createBuildConfigurationInvalidData(String description, NewProjectDescription project, BuildType buildType, int httpStatusCode, String errorMessage) {
        var testData = testDataStorage.addTestData(TestData.builder().user(generateUser()).project(project).buildType(buildType).build());
        checkedWithSuperUser.getProjectRequest().create(testData.getProject());
        checkedWithSuperUser.getUserRequest().create(testData.getUser());

        var response = new UncheckedBuildConfig(Specifications.getSpec().authSpec(testData.getUser()))
                .create(testData.getBuildType());

        softAssert.assertThat(response.getStatusCode()).as("Status code is incorrect").isEqualTo(httpStatusCode);
        softAssert.assertThat(response.getBody().asString()).as("Response error message is incorrect").contains(errorMessage);
        softAssert.assertAll();
    }

    @Test
    public void createBuildConfigWithSameId() {
        var project = generateProjectDescription();
        var user = generateUser();
        var testData = testDataStorage.addTestData(TestData.builder().user(user).project(project).buildType(generateBuildType(project)).build());
        var buildTypeWithDuplicatedId = generateBuildType(project).withId(testData.getBuildType().getId());

        checkedWithSuperUser.getProjectRequest().create(project);
        checkedWithSuperUser.getUserRequest().create(user);

        new CheckedBuildConfig(Specifications.getSpec().authSpec(user))
                .create(testData.getBuildType());

        var response = new UncheckedBuildConfig(Specifications.getSpec().authSpec(user))
                .create(buildTypeWithDuplicatedId);

        softAssert.assertThat(response.getStatusCode()).as("Status code is incorrect").isEqualTo(SC_BAD_REQUEST);
        softAssert.assertThat(response.getBody().asString()).as("Response error message is incorrect").contains(format("The build configuration / template ID \"%s\" is already used by another configuration or template", testData.getBuildType().getId()));
        softAssert.assertAll();
    }

    @Test
    public void createBuildConfigWithSameName() {
        var project = generateProjectDescription();
        var user = generateUser();
        var testData = testDataStorage.addTestData(TestData.builder().user(user).project(project).buildType(generateBuildType(project)).build());
        var buildTypeWithDuplicatedName = generateBuildType(project).withName(testData.getBuildType().getName());

        checkedWithSuperUser.getProjectRequest().create(project);
        checkedWithSuperUser.getUserRequest().create(user);

        new CheckedBuildConfig(Specifications.getSpec().authSpec(user))
                .create(testData.getBuildType());

        var response = new UncheckedBuildConfig(Specifications.getSpec().authSpec(user))
                .create(buildTypeWithDuplicatedName);

        softAssert.assertThat(response.getStatusCode()).as("Status code is incorrect").isEqualTo(SC_BAD_REQUEST);
        softAssert.assertThat(response.getBody().asString()).as("Response error message is incorrect").contains(format("Build configuration with name \"%s\" already exists in project: \"%s\"", testData.getBuildType().getName(), project.getName()));
        softAssert.assertAll();
    }
}
