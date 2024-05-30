package com.example.teamcity.ui.pages.admin;


import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.element;

public class CreteNewProjectPage extends Page {
    public static final String ERROR_MESSAGE_INVALID_URL = "Cannot create a project using the specified URL. The URL is not recognized.";
    public static final String ERROR_MESSAGE_INVALID_PROJECT_NAME = "Project name must not be empty";
    public static final String ERROR_MESSAGE_INVALID_PROJECT_ID = "Project ID must not be empty.";
    public static final String ERROR_MESSAGE_INVALID_BUILD_NAME = "Build configuration name must not be empty";
    public static final String ERROR_MESSAGE_PROJECT_NAME_IS_EMPTY = "Project name is empty";

    private SelenideElement urlInput = element(Selectors.byId("url"));
    private SelenideElement projectNameInput = element(Selectors.byId("projectName"));
    private SelenideElement buildTypeNameInput = element(Selectors.byId("buildTypeName"));
    private SelenideElement createManuallyLink = element(Selectors.byDataHintContainer("create-project"));
    private SelenideElement createProjectNameInput = element(Selectors.byId("name"));
    private SelenideElement createProjectIdInput = element(Selectors.byId("externalId"));
    private SelenideElement errorMessageUrlSpan = element(Selectors.byId("error_url"));
    private SelenideElement errorMessageProjectNameSpan = element(Selectors.byId("error_projectName"));
    private SelenideElement errorMessageBuildNameSpan = element(Selectors.byId("error_buildTypeName"));
    private SelenideElement errorMessageCreateProjectNameSpan = element(Selectors.byId("errorName"));
    private SelenideElement errorMessageCreateProjectIdSpan = element(Selectors.byId("errorExternalId"));

    @Step("Open 'Create new project' page")
    public CreteNewProjectPage open(String parentProjectId) {
        Selenide.open("/admin/createObjectMenu.html?projectId=" + parentProjectId + "&showMode=createProjectMenu");
        waitUntilPageIsLoaded();
        return this;
    }

    @Step("Select create project by url")
    public CreteNewProjectPage createProjectByUrl(String url) {
        urlInput.clear();
        urlInput.sendKeys(url);
        submit();
        return this;
    }

    @Step("Setup project")
    public void setupProject(String projectName, String buildTypeName) {
        projectNameInput.clear();
        projectNameInput.sendKeys(projectName);
        buildTypeNameInput.clear();
        buildTypeNameInput.sendKeys(buildTypeName);
        submit();
    }

    @Step("Select create project manually")
    public ProjectPage createProjectManually(String name, String id) {
        createManuallyLink.click();
        createProjectNameInput.clear();
        createProjectNameInput.sendKeys(name);
        createProjectIdInput.clear();
        createProjectIdInput.sendKeys(id);
        submit();
        return new ProjectPage();
    }

    @Step("Get error message url")
    public String getErrorMessageUrl() {
        return errorMessageUrlSpan.getText();
    }

    @Step("Get error project name")
    public String getErrorMessageProjectName() {
        return errorMessageProjectNameSpan.getText();
    }

    @Step("Get error build name")
    public String getErrorMessageBuildName() {
        return errorMessageBuildNameSpan.getText();
    }

    @Step("Get error create project name")
    public String getErrorMessageCreateProjectName() {
        return errorMessageCreateProjectNameSpan.getText();
    }

    @Step("Get error invalid project id")
    public String getErrorMessageInvalidProjectId() {
        return errorMessageCreateProjectIdSpan.getText();
    }
}
