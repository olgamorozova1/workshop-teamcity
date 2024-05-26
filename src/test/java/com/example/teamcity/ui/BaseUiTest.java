package com.example.teamcity.ui;

import com.codeborne.selenide.Configuration;
import com.example.teamcity.api.BaseTest;
import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.models.User;
import com.example.teamcity.ui.pages.LoginPage;
import io.qameta.allure.Step;
import org.testng.annotations.BeforeSuite;

public class BaseUiTest extends BaseTest {

    @BeforeSuite
    public void setUpUiTests() {
        Configuration.baseUrl = "http://" + Config.getProperty("host");
        Configuration.remote = "http://" + Config.getProperty("remote");
        Configuration.reportsFolder = "target/surefire-reports";
        Configuration.downloadsFolder = "target/downloads";

        BrowserSettings.setup(Config.getProperty("browser"));
    }

    @Step("Login as user")
    public void loginAsUser(User user) {
        userRequestBySuperUser.create(user);
        new LoginPage().open().login(user);
    }
}
