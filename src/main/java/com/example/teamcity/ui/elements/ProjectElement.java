package com.example.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import lombok.Getter;

@Getter
public class ProjectElement extends PageElement {
    private final SelenideElement header;
    private final SelenideElement icon;

    public ProjectElement(SelenideElement element) {
        super(element);
        this.header = findElement(Selectors.byDataTest("subproject"));
        this.icon = findElement("svg");
    }
}
