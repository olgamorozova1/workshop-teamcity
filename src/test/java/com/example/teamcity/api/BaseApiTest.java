package com.example.teamcity.api;

import com.example.teamcity.BaseTest;
import com.example.teamcity.api.models.AuthSettings;
import com.example.teamcity.api.requests.CheckedBase;
import lombok.Getter;
import org.testng.annotations.BeforeSuite;

import java.util.List;

import static com.example.teamcity.api.constants.Endpoints.AUTH_SETTINGS_ENDPOINT;
import static com.example.teamcity.api.spec.Specifications.getSpec;


@Getter
public class BaseApiTest extends BaseTest {

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
}
