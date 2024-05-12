package com.example.teamcity.api.generators;

import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.BuildType.Step;
import com.example.teamcity.api.models.BuildType.Steps;
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
import static com.example.teamcity.api.generators.RandomData.getValidCharactersString;
import static org.apache.commons.lang3.RandomUtils.nextBoolean;

public class TestDataGenerator {

    public static TestData generate() {
        var user = generateUser();
        var project = generateProjectDescription();
        var buildType = generateBuildType(project);
        return TestData.builder()
                .user(user)
                .project(project)
                .buildType(buildType)
                .build();
    }

    public static Roles generateRoles(com.example.teamcity.api.enums.Role role, String scope) {
        return Roles.builder().role(List.of(Role.builder().roleId(role.getText()).scope(scope).build())).build();
    }

    public static User generateUser() {
        return User.builder()
                .username(getString(10))
                .password(getString(10))
                .email(getEmail())
                .roles(generateRoles(SYSTEM_ADMIN, G.getText()))
                .build();
    }

    public static NewProjectDescription generateProjectDescription() {
        return NewProjectDescription.builder()
                .parentProject(generateProject())
                .name(getValidCharactersString())
                .id(getValidCharactersString())
                .copyAllAssociatedSettings(nextBoolean())
                .build();
    }

    public static Project generateProject() {
        return Project.builder().locator("_Root").build();
    }

    public static BuildType generateBuildType(NewProjectDescription project) {
        return BuildType.builder()
                .id(getValidCharactersString())
                .name(getValidCharactersString())
                .project(project)
                .steps(Steps.builder()
                        .step(List.of(Step
                                .builder()
                                .name(getString(10))
                                .type(getString(10))
                                .build()))
                        .build())
                .build();
    }
}
