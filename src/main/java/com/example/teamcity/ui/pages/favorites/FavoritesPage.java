package com.example.teamcity.ui.pages.favorites;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;

public class FavoritesPage extends Page {
    private SelenideElement header = element(Selectors.byDataTest("overview-header"));
    private SelenideElement projectsLoaderSpinner = element(Selectors.byDataTest("ring-loader-inline"));

    @Step("Wait for Favorites page loading")
    public void waitUntilFavoritesPageIsLoaded() {
        waitUntilPageIsLoaded();
        header.shouldBe(Condition.visible, Duration.ofSeconds(10));
        waitForProjectListLoad();
    }

    @Step("Wait for project list loading")
    private void waitForProjectListLoad() {
        projectsLoaderSpinner.shouldNotBe(Condition.visible, Duration.ofSeconds(30));
    }
}
