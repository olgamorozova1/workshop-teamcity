package com.example.teamcity.api;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.User;
import com.example.teamcity.api.requests.CheckedBase;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static com.example.teamcity.api.constants.Endpoints.PROJECT_ENDPOINT;
import static com.example.teamcity.api.constants.Endpoints.PROJECT_ENDPOINT_BY_ID;
import static com.example.teamcity.api.constants.Endpoints.USER_ENDPOINT;
import static com.example.teamcity.api.spec.Specifications.getSpec;

public class BaseTest {
    protected SoftAssertions softAssert;

    public TestDataStorage testDataStorage;
    protected final CheckedBase<User> userRequestBySuperUser = new CheckedBase<>(getSpec().superUserSpec(), USER_ENDPOINT, User.class);
    protected final CheckedBase<Project> projectRequestBySuperUser = new CheckedBase<>(getSpec().superUserSpec(), PROJECT_ENDPOINT, Project.class);
    protected final CheckedBase<Project> projectIdRequestBySuperUser = new CheckedBase<>(getSpec().superUserSpec(), PROJECT_ENDPOINT_BY_ID, Project.class);

    @BeforeMethod
    public void beforeTest() {
        softAssert = new SoftAssertions();
        testDataStorage = TestDataStorage.getStorage();
    }

    @AfterMethod
    public void cleanTest() {
        testDataStorage.deleteCreatedEntity();
    }
}
