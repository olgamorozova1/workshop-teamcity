package com.example.teamcity.api.generators;

import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.NewProjectDescription;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.Role;
import com.example.teamcity.api.models.Roles;
import com.example.teamcity.api.models.User;

import java.util.List;

import static com.example.teamcity.api.enums.Role.SYSTEM_ADMIN;
import static com.example.teamcity.api.enums.Scope.G;
import static com.example.teamcity.api.generators.RandomData.getEmail;
import static com.example.teamcity.api.generators.RandomData.getString;

public class TestDataGenerator {

    public static TestData generate() {
        var user = User.builder()
                .username(getString())
                .password(getString())
                .email(getEmail())
                .roles(generateRoles(SYSTEM_ADMIN, G.getText()))
                .build();
        var project = NewProjectDescription.builder()
                .parentProject(Project.builder().locator("_Root").build())
                .name(getString())
                .id(getString())
                .copyAllAssociatedSettings(true)
                .build();
        var buildType = BuildType.builder()
                .id(getString())
                .name(getString())
                .project(project)
                .build();
        return TestData.builder()
                .user(user)
                .project(project)
                .buildType(buildType)
                .build();
    }

    public static Roles generateRoles(com.example.teamcity.api.enums.Role role, String scope) {
        return Roles.builder().role(List.of(Role.builder().roleId(role.getText()).scope(scope).build())).build();
    }
}
