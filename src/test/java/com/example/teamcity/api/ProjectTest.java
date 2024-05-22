package com.example.teamcity.api;

import com.example.teamcity.api.models.NewProjectDescription;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.UncheckedBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.example.teamcity.api.constants.Endpoints.PROJECT_ENDPOINT;
import static com.example.teamcity.api.generators.RandomData.getInvalidCharacter;
import static com.example.teamcity.api.generators.RandomData.getNumber;
import static com.example.teamcity.api.generators.RandomData.getString;
import static com.example.teamcity.api.generators.RandomDataGenerator.generateRandomClassData;
import static com.example.teamcity.api.spec.Specifications.getSpec;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;

public class ProjectTest extends BaseApiTest {
    @DataProvider
    public Object[][] testDataRootProject() {
        return new Object[][]{
                {"id min value", generateRandomClassData(NewProjectDescription.class).withId(getString(1))},
                {"id max value", generateRandomClassData(NewProjectDescription.class).withId(getString(225))},
                {"name min value", generateRandomClassData(NewProjectDescription.class).withName(getString(1))},
                {"name max value", generateRandomClassData(NewProjectDescription.class).withName(getString(255))},
                {"copyAllAssociatedSettings false", generateRandomClassData(NewProjectDescription.class).withCopyAllAssociatedSettings(false)},
                {"copyAllAssociatedSettings null", generateRandomClassData(NewProjectDescription.class).withCopyAllAssociatedSettings(null)},
        };
    }

    @Test(dataProvider = "testDataRootProject")
    public void createProject(String description, NewProjectDescription project) {
        var testData = testDataStorage.addTestData().withProject(project);
        projectRequestBySuperUser.create(testData.getProject());
        var actualProject = projectIdRequestBySuperUser.get(testData.getProject().getId());
        softAssert.assertThat(actualProject.getId()).as("Project id is not correct").isEqualTo(testData.getProject().getId());
        softAssert.assertThat(actualProject.getName()).as("Project name is not correct").isEqualTo(testData.getProject().getName());
        softAssert.assertThat(actualProject.getParentProjectId()).as("Parent project locator is not correct").isEqualTo(testData.getProject().getParentProject().getLocator());
        softAssert.assertThat(actualProject.getCopyAllAssociatedSettings()).as("CopyAllAssociatedSettings is not correct").isEqualTo(testData.getProject().getCopyAllAssociatedSettings());
        softAssert.assertAll();
    }

    @DataProvider
    public Object[][] invalidTestData() {
        return new Object[][]{
                {"id < min value", generateRandomClassData(NewProjectDescription.class).withId(EMPTY), SC_INTERNAL_SERVER_ERROR, "Project ID must not be empty"},
                {"id > max value", generateRandomClassData(NewProjectDescription.class).withId(getString(226)), SC_INTERNAL_SERVER_ERROR, "invalid: it is 226 characters long while the maximum length is 225. ID should start with a latin letter and contain only latin letters, digits and underscores (at most 225 characters)"},
                {"id starts with digit", generateRandomClassData(NewProjectDescription.class).withId(getNumber(1)), SC_INTERNAL_SERVER_ERROR, "ID should start with a latin letter and contain only latin letters, digits and underscores (at most 225 characters)"},
                {"id starts with underscore", generateRandomClassData(NewProjectDescription.class).withId("_" + getString(10)), SC_INTERNAL_SERVER_ERROR, "invalid: starts with non-letter character '_'. ID should start with a latin letter and contain only latin letters, digits and underscores (at most 225 characters)"},
                {"id contains invalid symbol", generateRandomClassData(NewProjectDescription.class).withId(getString(10) + getInvalidCharacter()), SC_INTERNAL_SERVER_ERROR, "ID should start with a latin letter and contain only latin letters, digits and underscores (at most 225 characters)"},
                {"name < min value", generateRandomClassData(NewProjectDescription.class).withName(EMPTY), SC_BAD_REQUEST, "Project name cannot be empty"},
                {"name > max value", generateRandomClassData(NewProjectDescription.class).withName(getString(256)), SC_BAD_REQUEST, ""},
                {"name = null", generateRandomClassData(NewProjectDescription.class).withName(null), SC_BAD_REQUEST, "Project name cannot be empty"},
                {"parentProject locator null", generateRandomClassData(NewProjectDescription.class).withParentProject(generateRandomClassData(Project.class).withLocator(null)), SC_BAD_REQUEST, "No project specified. Either 'id', 'internalId' or 'locator' attribute should be present."},
        };
    }

    @Test(dataProvider = "invalidTestData")
    public void createProjectInvalidData(String description, NewProjectDescription project, int httpStatusCode, String errorMessage) {
        var testData = testDataStorage.addTestData().withProject(project);
        var response = new UncheckedBase(getSpec().superUserSpec(), PROJECT_ENDPOINT).create(testData.getProject());
        softAssert.assertThat(response.getStatusCode()).as("Status code is incorrect").isEqualTo(httpStatusCode);
        softAssert.assertThat(response.getBody().asString()).as("Response error message is incorrect").contains(errorMessage);
        softAssert.assertAll();
    }

    @Test
    public void createProjectWithSameId() {
        var firstProjectTestData = testDataStorage.addTestData();
        var firstProjectId = firstProjectTestData.getProject().getId();
        var secondProjectTestData = testDataStorage.addTestData().withProject(generateRandomClassData(NewProjectDescription.class).withId(firstProjectId));
        projectRequestBySuperUser.create(firstProjectTestData.getProject());
        var response = new UncheckedBase(getSpec().superUserSpec(), PROJECT_ENDPOINT).create(secondProjectTestData.getProject());
        softAssert.assertThat(response.getStatusCode()).as("Status code is incorrect").isEqualTo(SC_BAD_REQUEST);
        softAssert.assertThat(response.getBody().asString()).as("Response error message is incorrect").contains(format("Project ID \"%s\" is already used by another project", firstProjectId));
        softAssert.assertAll();
    }

    @Test
    public void createProjectWithSameName() {
        var firstProjectTestData = testDataStorage.addTestData();
        var firstProjectName = firstProjectTestData.getProject().getName();
        var secondProjectTestData = testDataStorage.addTestData().withProject(generateRandomClassData(NewProjectDescription.class).withName(firstProjectName));
        projectRequestBySuperUser.create(firstProjectTestData.getProject());
        var response = new UncheckedBase(getSpec().superUserSpec(), PROJECT_ENDPOINT).create(secondProjectTestData.getProject());
        softAssert.assertThat(response.getStatusCode()).as("Status code is incorrect").isEqualTo(SC_BAD_REQUEST);
        softAssert.assertThat(response.getBody().asString()).as("Response error message is incorrect").contains(format("Project with this name already exists: %s", firstProjectName));
        softAssert.assertAll();
    }
}
