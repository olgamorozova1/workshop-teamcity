package com.example.teamcity.api;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.AuthSettings;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.UncheckedRequests;
import lombok.Getter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.util.List;

import static com.example.teamcity.api.spec.Specifications.getSpec;

@Getter
public class BaseApiTest extends BaseTest {
    public TestDataStorage testDataStorage;
    public CheckedRequests checkedWithSuperUser = new CheckedRequests(getSpec().superUserSpec());
    public UncheckedRequests uncheckedWithSuperUser = new UncheckedRequests(getSpec().superUserSpec());

    @BeforeSuite
    public void setUpSuite() {
        checkedWithSuperUser.getAuthSettingsRequest().create(AuthSettings
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
        testDataStorage.delete();
    }
}
