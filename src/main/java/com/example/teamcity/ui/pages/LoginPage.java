package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.api.models.User;
import com.example.teamcity.ui.Selectors;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.element;

@Getter
public class LoginPage extends Page {
    private static final String LOGIN_PAGE_URL = "/login.html";
    private SelenideElement userNameInput = element(Selectors.byId("username"));
    private SelenideElement passwordInput = element(Selectors.byId("password"));

    public LoginPage open() {
        Selenide.open(LOGIN_PAGE_URL);
        return this;
    }

    public void login(User user) {
        userNameInput.sendKeys(user.getUsername());
        passwordInput.sendKeys(user.getPassword());
        submit();
    }
}
