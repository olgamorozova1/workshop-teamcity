package com.example.teamcity.ui.elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import lombok.Getter;

import java.time.Duration;

@Getter
public class SubprojectElement extends PageElement {
    private final SelenideElement nameText;

    public SubprojectElement(SelenideElement element) {
        super(element);
        this.nameText = findElement(Selectors.byClass("MiddleEllipsis__middleEllipsis--Ei BuildTypeLine__caption--uj"));
        waitForBuildNameVisibility();
    }

    private void waitForBuildNameVisibility() {
        nameText.shouldBe(Condition.interactable, Duration.ofSeconds(10));
    }
}
