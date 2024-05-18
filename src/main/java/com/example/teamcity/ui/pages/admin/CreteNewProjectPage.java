package com.example.teamcity.ui.pages.admin;


import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;

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

    public CreteNewProjectPage open(String parentProjectId) {
        Selenide.open("/admin/createObjectMenu.html?projectId=" + parentProjectId + "&showMode=createProjectMenu");
        waitUntilPageIsLoaded();
        return this;
    }

    public CreteNewProjectPage createProjectByUrl(String url) {
        urlInput.clear();
        urlInput.sendKeys(url);
        submit();
        return this;
    }

    public void setupProject(String projectName, String buildTypeName) {
        projectNameInput.clear();
        projectNameInput.sendKeys(projectName);
        buildTypeNameInput.clear();
        buildTypeNameInput.sendKeys(buildTypeName);
        submit();
    }

    public ProjectPage createProjectManually(String name, String id) {
        createManuallyLink.click();
        createProjectNameInput.clear();
        createProjectNameInput.sendKeys(name);
        createProjectIdInput.clear();
        createProjectIdInput.sendKeys(id);
        submit();
        return new ProjectPage();
    }

    public String getErrorMessageUrl() {
        return errorMessageUrlSpan.getText();
    }

    public String getErrorMessageProjectName() {
        return errorMessageProjectNameSpan.getText();
    }

    public String getErrorMessageBuildName() {
        return errorMessageBuildNameSpan.getText();
    }

    public String getErrorMessageCreateProjectName() {
        return errorMessageCreateProjectNameSpan.getText();
    }

    public String getErrorMessageInvalidProjectId() {
        return errorMessageCreateProjectIdSpan.getText();
    }
}
