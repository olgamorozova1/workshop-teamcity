package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.element;

public class CreateBuildConfigurationPage extends Page {
    public static final String ERROR_MESSAGE_NAME_IS_EMPTY = "Name must not be empty";
    public static final String ERROR_MESSAGE_ID_IS_EMPTY = "The ID field must not be empty.";

    private SelenideElement nameInput = element(Selectors.byId("buildTypeName"));
    private SelenideElement buildConfigurationIdInput = element(Selectors.byId("buildTypeExternalId"));
    private SelenideElement errorMessageNameInvalidSpan = element(Selectors.byId("error_buildTypeName"));
    private SelenideElement errorMessageIdInvalidSpan = element(Selectors.byId("error_buildTypeExternalId"));

    @Step("Create build configuration on Build Configuration page")
    public VcsRootPage createBuildConfiguration(String name, String id) {
        nameInput.sendKeys(name);
        buildConfigurationIdInput.clear();
        buildConfigurationIdInput.sendKeys(id);
        submit();
        return new VcsRootPage();
    }

    @Step("Get error message invalid build name")
    public String getErrorMessageInvalidBuildName() {
        return errorMessageNameInvalidSpan.getText();
    }

    @Step("Get error message invalid build id")
    public String getErrorMessageInvalidBuildId() {
        return errorMessageIdInvalidSpan.getText();
    }
}
