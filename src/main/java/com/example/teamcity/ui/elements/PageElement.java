package com.example.teamcity.ui.elements;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.Page;
import org.openqa.selenium.By;

public abstract class PageElement extends Page {
    private final SelenideElement element;

    public PageElement(SelenideElement element) {
        this.element = element;
    }

    public SelenideElement findElement(By by) {
        return element.find(by);
    }

    public SelenideElement findElement(String value) {
        return element.find(value);
    }

    public ElementsCollection findElements(By by) {
        return element.findAll(by);
    }
}
