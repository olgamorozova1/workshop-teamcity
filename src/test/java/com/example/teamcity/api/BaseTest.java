package com.example.teamcity.api;

import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected SoftAssertions softAssert;

    @BeforeMethod
    public void beforeTest() {
        softAssert = new SoftAssertions();
    }
}
