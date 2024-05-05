package com.example.teamcity.api;

import com.example.teamcity.api.generators.TestData;
import com.example.teamcity.api.models.NewProjectDescription;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.example.teamcity.api.generators.RandomData.getInvalidCharacter;
import static com.example.teamcity.api.generators.RandomData.getNumber;
import static com.example.teamcity.api.generators.RandomData.getString;
import static com.example.teamcity.api.generators.TestDataGenerator.generateProject;
import static com.example.teamcity.api.generators.TestDataGenerator.generateProjectDescription;
import static com.example.teamcity.api.generators.TestDataGenerator.generateUser;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;

public class ProjectTest extends BaseApiTest {

    @DataProvider
    public Object[][] testDataRootProject() {
        return new Object[][]{
                {"id min value", generateProjectDescription().withId(getString(1))},
                {"id max value", generateProjectDescription().withId(getString(225))},
                {"name min value", generateProjectDescription().withName(getString(1))},
                {"name max value", generateProjectDescription().withName(getString(255))},
                {"copyAllAssociatedSettings false", generateProjectDescription().withCopyAllAssociatedSettings(false)},
                {"copyAllAssociatedSettings null", generateProjectDescription().withCopyAllAssociatedSettings(null)},
        };
    }

    @Test(dataProvider = "testDataRootProject")
    public void createProject(String description, NewProjectDescription project) {
        var testData = testDataStorage.addTestData(TestData.builder().user(generateUser()).project(project).build());
        checkedWithSuperUser.getProjectRequest().create(testData.getProject());
        var actualProject = checkedWithSuperUser.getProjectRequest().get(testData.getProject().getId());
        softAssert.assertThat(actualProject.getId()).as("Project id is not correct").isEqualTo(testData.getProject().getId());
        softAssert.assertThat(actualProject.getName()).as("Project name is not correct").isEqualTo(testData.getProject().getName());
        softAssert.assertThat(actualProject.getParentProjectId()).as("Parent project locator is not correct").isEqualTo(testData.getProject().getParentProject().getLocator());
        softAssert.assertThat(actualProject.getCopyAllAssociatedSettings()).as("CopyAllAssociatedSettings is not correct").isEqualTo(testData.getProject().getCopyAllAssociatedSettings());
        softAssert.assertAll();
    }

    @DataProvider
    public Object[][] invalidTestData() {
        return new Object[][]{
                {"id < min value", generateProjectDescription().withId(EMPTY), SC_INTERNAL_SERVER_ERROR, "Project ID must not be empty"},
                {"id > max value", generateProjectDescription().withId(getString(226)), SC_INTERNAL_SERVER_ERROR, "invalid: it is 226 characters long while the maximum length is 225. ID should start with a latin letter and contain only latin letters, digits and underscores (at most 225 characters)"},
                {"id starts with digit", generateProjectDescription().withId(getNumber(1)), SC_INTERNAL_SERVER_ERROR, "ID should start with a latin letter and contain only latin letters, digits and underscores (at most 225 characters)"},
                {"id starts with underscore", generateProjectDescription().withId("_" + getString(10)), SC_INTERNAL_SERVER_ERROR, "invalid: starts with non-letter character '_'. ID should start with a latin letter and contain only latin letters, digits and underscores (at most 225 characters)"},
                {"id contains invalid symbol", generateProjectDescription().withId(getString(10) + getInvalidCharacter()), SC_INTERNAL_SERVER_ERROR, "ID should start with a latin letter and contain only latin letters, digits and underscores (at most 225 characters)"},
                {"name < min value", generateProjectDescription().withName(EMPTY), SC_BAD_REQUEST, "Project name cannot be empty"},
                {"name > max value", generateProjectDescription().withName(getString(256)), SC_BAD_REQUEST, ""},
                {"name = null", generateProjectDescription().withName(null), SC_BAD_REQUEST, "Project name cannot be empty"},
                {"parentProject locator null", generateProjectDescription().withParentProject(generateProject().withLocator(null)), SC_BAD_REQUEST, "No project specified. Either 'id', 'internalId' or 'locator' attribute should be present."},
        };
    }

    @Test(dataProvider = "invalidTestData")
    public void createProjectInvalidData(String description, NewProjectDescription project, int httpStatusCode, String errorMessage) {
        var testData = testDataStorage.addTestData(TestData.builder().user(generateUser()).project(project).build());
        var response = uncheckedWithSuperUser.getProjectRequest().create(testData.getProject());
        softAssert.assertThat(response.getStatusCode()).as("Status code is incorrect").isEqualTo(httpStatusCode);
        softAssert.assertThat(response.getBody().asString()).as("Response error message is incorrect").contains(errorMessage);
        softAssert.assertAll();
    }

    @Test
    public void createProjectWithSameId() {
        var firstProjectTestData = testDataStorage.addTestData(TestData.builder().user(generateUser()).project(generateProjectDescription()).build());
        var firstProjectId = firstProjectTestData.getProject().getId();
        var secondProjectTestData = testDataStorage.addTestData(TestData.builder().user(generateUser()).project(generateProjectDescription().withId(firstProjectId)).build());
        checkedWithSuperUser.getProjectRequest().create(firstProjectTestData.getProject());
        var response = uncheckedWithSuperUser.getProjectRequest().create(secondProjectTestData.getProject());
        softAssert.assertThat(response.getStatusCode()).as("Status code is incorrect").isEqualTo(SC_BAD_REQUEST);
        softAssert.assertThat(response.getBody().asString()).as("Response error message is incorrect").contains(format("Project ID \"%s\" is already used by another project", firstProjectId));
        softAssert.assertAll();
    }

    @Test
    public void createProjectWithSameName() {
        var firstProjectTestData = testDataStorage.addTestData(TestData.builder().user(generateUser()).project(generateProjectDescription()).build());
        var firstProjectName = firstProjectTestData.getProject().getName();
        var secondProjectTestData = testDataStorage.addTestData(TestData.builder().user(generateUser()).project(generateProjectDescription().withName(firstProjectName)).build());
        checkedWithSuperUser.getProjectRequest().create(firstProjectTestData.getProject());
        var response = uncheckedWithSuperUser.getProjectRequest().create(secondProjectTestData.getProject());
        softAssert.assertThat(response.getStatusCode()).as("Status code is incorrect").isEqualTo(SC_BAD_REQUEST);
        softAssert.assertThat(response.getBody().asString()).as("Response error message is incorrect").contains(format("Project with this name already exists: %s", firstProjectName));
        softAssert.assertAll();
    }
}
