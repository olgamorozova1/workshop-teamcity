package com.example.teamcity.ui.elements;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import io.qameta.allure.Step;
import lombok.Getter;

import java.util.List;

import static com.codeborne.selenide.Selenide.elements;

@Getter
public class ProjectElement extends PageElement {
    private final SelenideElement header;
    private final SelenideElement icon;
    private final SelenideElement expandIcon;
    private final ElementsCollection builds;

    public ProjectElement(SelenideElement element) {
        super(element);
        this.header = findElement(Selectors.byDataTest("subproject"));
        this.icon = findElement("svg");
        this.expandIcon = findElement(Selectors.byClass("ring-icon-icon SvgIcon__icon--wZ Subproject__arrow--NT CollapsibleLine__arrow--so"));
        builds = elements(Selectors.byClass("BuildTypes__item--UX"));
    }

    @Step("Click expand icon")
    public void expand() {
        expandIcon.click();
    }

    @Step("Get builds from page")
    public List<SubprojectElement> getBuilds() {
        return generatePageElements(builds, SubprojectElement::new);
    }
}
