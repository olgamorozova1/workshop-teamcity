package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;

import static com.codeborne.selenide.Selenide.element;

public class VcsRootPage extends Page {
    public static final String ERROR_MESSAGE_URL_IS_EMPTY = "Repository URL must not be empty";
    private SelenideElement repositoryUrl = element(Selectors.byId("repositoryUrl"));
    private SelenideElement repositoryUrlErrorSpan = element(Selectors.byId("error_repositoryUrl"));

    public void addRepositoryUrl(String url) {
        repositoryUrl.sendKeys(url);
        submit();
    }

    public String getErrorMessageInvalidUrl() {
        return repositoryUrlErrorSpan.getText();
    }
}
