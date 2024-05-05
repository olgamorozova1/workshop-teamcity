package com.example.teamcity.api.requests;

import com.example.teamcity.api.requests.checked.CheckedAuthSettings;
import com.example.teamcity.api.requests.checked.CheckedBuildConfig;
import com.example.teamcity.api.requests.checked.CheckedProject;
import com.example.teamcity.api.requests.checked.CheckedUser;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class CheckedRequests {
    private CheckedUser userRequest;
    private CheckedProject projectRequest;
    private CheckedBuildConfig buildConfigRequest;
    private CheckedAuthSettings authSettingsRequest;

    public CheckedRequests(RequestSpecification spec) {
        this.userRequest = new CheckedUser(spec);
        this.buildConfigRequest = new CheckedBuildConfig(spec);
        this.projectRequest = new CheckedProject(spec);
        this.authSettingsRequest = new CheckedAuthSettings(spec);
    }
}
