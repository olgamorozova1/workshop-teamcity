package com.example.teamcity.api;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.AuthSettings;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.User;
import com.example.teamcity.api.requests.CheckedBase;
import lombok.Getter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.util.List;

import static com.example.teamcity.api.constants.Endpoints.AUTH_SETTINGS_ENDPOINT;
import static com.example.teamcity.api.constants.Endpoints.PROJECT_ENDPOINT;
import static com.example.teamcity.api.constants.Endpoints.PROJECT_ENDPOINT_BY_ID;
import static com.example.teamcity.api.constants.Endpoints.USER_ENDPOINT;
import static com.example.teamcity.api.spec.Specifications.getSpec;


@Getter
public class BaseApiTest extends BaseTest {
    public TestDataStorage testDataStorage;
    protected final CheckedBase<User> userRequestBySuperUser = new CheckedBase<>(getSpec().superUserSpec(), USER_ENDPOINT, User.class);
    protected final CheckedBase<Project> projectRequestBySuperUser = new CheckedBase<>(getSpec().superUserSpec(), PROJECT_ENDPOINT, Project.class);
    protected final CheckedBase<Project> projectIdRequestBySuperUser = new CheckedBase<>(getSpec().superUserSpec(), PROJECT_ENDPOINT_BY_ID, Project.class);

    @BeforeSuite
    public void setUpSuite() {
        new CheckedBase<>(getSpec().superUserSpec(), AUTH_SETTINGS_ENDPOINT, AuthSettings.class).put(AuthSettings
                .builder()
                .perProjectPermissions(true)
                .modules(AuthSettings.Modules.builder().module(List.of(AuthSettings.Module
                        .builder()
                        .name("HTTP-Basic")
                        .build())).build())
                .build());
    }

    @BeforeMethod
    public void setUpTest() {
        testDataStorage = TestDataStorage.getStorage();
    }

    @AfterMethod
    public void cleanTest() {
        testDataStorage.deleteCreatedEntity();
    }
}
