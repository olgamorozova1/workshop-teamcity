package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByText;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.element;

public class ProjectPage extends Page {
    private SelenideElement projectNameInput = element(Selectors.byId("name"));
    private SelenideElement projectIdInput = element(Selectors.byId("externalId"));
    private SelenideElement createBuildConfigurationText = element(new ByText("Create build configuration"));


    public String getProjectNameText() {
        return projectNameInput.getValue();
    }

    public String getProjectIdText() {
        return projectIdInput.getValue();
    }

    @Step("Create build configuration on Project page")
    public CreateBuildConfigurationPage createBuildConfiguration() {
        createBuildConfigurationText.click();
        return new CreateBuildConfigurationPage();
    }
}


