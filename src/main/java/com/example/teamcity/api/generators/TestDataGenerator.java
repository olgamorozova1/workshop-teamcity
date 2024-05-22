package com.example.teamcity.api.generators;

import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.NewProjectDescription;
import com.example.teamcity.api.models.Role;
import com.example.teamcity.api.models.Roles;
import com.example.teamcity.api.models.User;

import java.util.List;

import static com.example.teamcity.api.generators.RandomDataGenerator.generateRandomClassData;

public class TestDataGenerator {

    public static TestData generate() {
        var user = generateRandomClassData(User.class);
        var project = generateRandomClassData(NewProjectDescription.class);
        var buildType = generateBuildType(project);
        return TestData.builder()
                .user(user)
                .project(project)
                .buildType(buildType)
                .build();
    }

    public static Roles setRoles(com.example.teamcity.api.enums.Role role, String scope) {
        return Roles.builder().role(List.of(Role.builder().roleId(role.getText()).scope(scope).build())).build();
    }

    public static BuildType generateBuildType(NewProjectDescription project) {
        return generateRandomClassData(BuildType.class).withProject(project);
    }
}
