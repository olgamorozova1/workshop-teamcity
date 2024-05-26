package com.example.teamcity.ui.pages.favorites;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.elements.ProjectElement;
import io.qameta.allure.Step;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selenide.element;
import static com.codeborne.selenide.Selenide.elements;

public class ProjectsPage extends FavoritesPage {
    public static final String FAVORITE_PROJECTS_URL = "/favorite/projects";
    private static final String SUBPROJECT_CLASS_NAME = "Subproject__container--Px";
    private ElementsCollection subprojects = elements(Selectors.byClass(SUBPROJECT_CLASS_NAME));
    private SelenideElement subproject = element(Selectors.byClass(SUBPROJECT_CLASS_NAME));

    @Step("Open 'Project' page")
    public ProjectsPage open() {
        Selenide.open(FAVORITE_PROJECTS_URL);
        waitUntilFavoritesPageIsLoaded();
        return this;
    }

    @Step("Get sub projects")
    public List<ProjectElement> getSubProjects() {
        waitForSubprojectElement();
        return generatePageElements(subprojects, ProjectElement::new);
    }

    @Step("Wait for elements to be shown")
    private void waitForSubprojectElement() {
        subproject.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }
}
