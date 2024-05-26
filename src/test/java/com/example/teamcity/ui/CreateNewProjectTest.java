package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.ui.pages.admin.CreteNewProjectPage;
import com.example.teamcity.ui.pages.admin.ProjectPage;
import com.example.teamcity.ui.pages.favorites.ProjectsPage;
import org.testng.annotations.Test;

import static com.example.teamcity.ui.pages.admin.CreateBuildConfigurationPage.ERROR_MESSAGE_ID_IS_EMPTY;
import static com.example.teamcity.ui.pages.admin.CreateBuildConfigurationPage.ERROR_MESSAGE_NAME_IS_EMPTY;
import static com.example.teamcity.ui.pages.admin.CreteNewProjectPage.ERROR_MESSAGE_INVALID_BUILD_NAME;
import static com.example.teamcity.ui.pages.admin.CreteNewProjectPage.ERROR_MESSAGE_INVALID_PROJECT_ID;
import static com.example.teamcity.ui.pages.admin.CreteNewProjectPage.ERROR_MESSAGE_INVALID_PROJECT_NAME;
import static com.example.teamcity.ui.pages.admin.CreteNewProjectPage.ERROR_MESSAGE_INVALID_URL;
import static com.example.teamcity.ui.pages.admin.CreteNewProjectPage.ERROR_MESSAGE_PROJECT_NAME_IS_EMPTY;
import static com.example.teamcity.ui.pages.admin.VcsRootPage.ERROR_MESSAGE_URL_IS_EMPTY;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class CreateNewProjectTest extends BaseUiTest {
    public static final String VALID_REPOSITORY_URL = "https://github.com/AlexPshe/spring-core-for-qa";

    //------------------ Positive tests --------------------//

    @Test(description = "Authorized user should be able create new project")
    public void authorizedUserShouldBeAbleCreateNewProject() {
        var testData = testDataStorage.addTestData();

        loginAsUser(testData.getUser());

        new CreteNewProjectPage()
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectByUrl(VALID_REPOSITORY_URL)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());

        new ProjectsPage().open()
                .getSubProjects()
                .stream().reduce((first, second) -> second).get()
                .getHeader().shouldHave(Condition.text(testData.getProject().getName()));
    }

    @Test(description = "Create new project with build configuration manually")
    public void createNewProjectWithBuildConfigurationManually() {
        var testData = testDataStorage.addTestData();

        loginAsUser(testData.getUser());

        ProjectPage projectPage = new CreteNewProjectPage()
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectManually(testData.getProject().getName(), testData.getProject().getId());

        softAssert.assertThat(projectPage.getProjectNameText()).isEqualTo(testData.getProject().getName());
        softAssert.assertThat(projectPage.getProjectIdText()).isEqualTo(testData.getProject().getId());

        projectPage.createBuildConfiguration()
                .createBuildConfiguration(testData.getBuildType().getName(), testData.getBuildType().getId())
                .addRepositoryUrl(VALID_REPOSITORY_URL);

        var project = new ProjectsPage().open()
                .getSubProjects()
                .stream().reduce((first, second) -> second).get();
        project.expand();
        var build = project.getBuilds().stream().reduce((first, second) -> second).get();

        softAssert.assertThat(project.getHeader().getText()).contains(testData.getProject().getName());
        softAssert.assertThat(build.getNameText().getText()).contains(testData.getBuildType().getName());
        softAssert.assertAll();
    }

    //------------------ Negative tests --------------------//

    @Test(description = "Check create new project from VCS invalid data")
    public void checkCreateNewProjectFromVCSInvalidData() {
        var testData = testDataStorage.addTestData();

        loginAsUser(testData.getUser());

        var newProjectPage = new CreteNewProjectPage()
                .open(testData.getProject().getParentProject().getLocator());

        String errorMessage = newProjectPage
                .createProjectByUrl(randomAlphabetic(20))
                .getErrorMessageUrl();

        softAssert.assertThat(errorMessage).as("No message invalid url").isEqualTo(ERROR_MESSAGE_INVALID_URL);

        newProjectPage.createProjectByUrl(VALID_REPOSITORY_URL)
                .setupProject("", "");

        String errorInvaliProjectName = newProjectPage.getErrorMessageProjectName();
        String errorInvaliBuildName = newProjectPage.getErrorMessageBuildName();

        softAssert.assertThat(errorInvaliProjectName).as("No message invalid project name").isEqualTo(ERROR_MESSAGE_INVALID_PROJECT_NAME);
        softAssert.assertThat(errorInvaliBuildName).as("No message invalid build name").isEqualTo(ERROR_MESSAGE_INVALID_BUILD_NAME);
        softAssert.assertAll();
    }

    @Test(description = "Create new project with build configuration manually invalid data")
    public void createNewProjectWithBuildConfigurationManuallyInvalidData() {
        var testData = testDataStorage.addTestData();
        var createNewProjectPage = new CreteNewProjectPage();

        loginAsUser(testData.getUser());

        createNewProjectPage
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectManually("", testData.getProject().getId());

        String errorInvaliProjectName = createNewProjectPage.getErrorMessageCreateProjectName();
        softAssert.assertThat(errorInvaliProjectName).as("No message invalid project name").isEqualTo(ERROR_MESSAGE_PROJECT_NAME_IS_EMPTY);

        createNewProjectPage
                .createProjectManually(testData.getProject().getName(), "");

        String errorInvaliProjectId = createNewProjectPage.getErrorMessageInvalidProjectId();
        softAssert.assertThat(errorInvaliProjectId).as("No message invalid project name").isEqualTo(ERROR_MESSAGE_INVALID_PROJECT_ID);

        ProjectPage projectPage = createNewProjectPage
                .createProjectManually(testData.getProject().getName(), testData.getProject().getId());

        var buildConfigPage = projectPage.createBuildConfiguration();

        buildConfigPage.createBuildConfiguration("", "");
        var invalidNameMessage = buildConfigPage.getErrorMessageInvalidBuildName();
        var invalidIdMessage = buildConfigPage.getErrorMessageInvalidBuildId();

        softAssert.assertThat(invalidNameMessage).as("No message invalid project name").isEqualTo(ERROR_MESSAGE_NAME_IS_EMPTY);
        softAssert.assertThat(invalidIdMessage).as("No message invalid project name").isEqualTo(ERROR_MESSAGE_ID_IS_EMPTY);

        var vcsRootPage = buildConfigPage.createBuildConfiguration(testData.getBuildType().getName(), testData.getBuildType().getId());

        vcsRootPage.addRepositoryUrl("");

        var invalidUrlMessage = vcsRootPage.getErrorMessageInvalidUrl();
        softAssert.assertThat(invalidUrlMessage).as("No message invalid url").isEqualTo(ERROR_MESSAGE_URL_IS_EMPTY);
        softAssert.assertAll();
    }
}
