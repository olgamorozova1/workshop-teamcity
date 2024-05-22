package com.example.teamcity.api;

import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.CheckedBase;
import com.example.teamcity.api.requests.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import org.hamcrest.Matchers;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static com.example.teamcity.api.constants.Endpoints.BUILD_CONFIG_ENDPOINT;
import static com.example.teamcity.api.constants.Endpoints.PROJECT_ENDPOINT;
import static com.example.teamcity.api.constants.Endpoints.PROJECT_ENDPOINT_BY_ID;
import static com.example.teamcity.api.enums.Role.PROJECT_ADMIN;
import static com.example.teamcity.api.enums.Role.SYSTEM_ADMIN;
import static com.example.teamcity.api.enums.Scope.G;
import static com.example.teamcity.api.enums.Scope.P;
import static com.example.teamcity.api.generators.TestDataGenerator.setRoles;
import static com.example.teamcity.api.spec.Specifications.getSpec;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

public class RolesTest extends BaseApiTest {


    @Test
    public void unauthorizedUserShouldNotHaveRightsToCreateProject() {
        var testData = testDataStorage.addTestData();

        new UncheckedBase(Specifications.getSpec().unauthSpec(), PROJECT_ENDPOINT)
                .create(testData.getProject())
                .then().assertThat().statusCode(SC_UNAUTHORIZED);
        new UncheckedBase(getSpec().superUserSpec(), PROJECT_ENDPOINT_BY_ID)
                .get(testData.getProject().getId())
                .then().assertThat().statusCode(SC_NOT_FOUND)
                .body(Matchers.containsString(format("No project found by locator 'count:1,id:%s'", testData.getProject().getId())));
    }


    @Test
    public void systemAdminShouldHaveRightsToCreateProject() {
        var testData = testDataStorage.addTestData();

        testData.getUser().setRoles(setRoles(SYSTEM_ADMIN, G.getText()));

        userRequestBySuperUser.create(testData.getUser());

        var project = new CheckedBase<>(Specifications.getSpec().authSpec(testData.getUser()), PROJECT_ENDPOINT, Project.class)
                .create(testData.getProject());

        softAssert.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }

    @Test
    public void projectAdminShouldHaveRightsToCreateBuildConfigToHisProject() {
        var testData = testDataStorage.addTestData();

        projectRequestBySuperUser.create(testData.getProject());

        testData.getUser().setRoles(setRoles(PROJECT_ADMIN, P.getText() + testData.getProject().getId()));

        userRequestBySuperUser.create(testData.getUser());

        var buildConfig = new CheckedBase<>(Specifications.getSpec().authSpec(testData.getUser()), BUILD_CONFIG_ENDPOINT, BuildType.class)
                .create(testData.getBuildType());

        softAssert.assertThat(buildConfig.getId()).isEqualTo(testData.getBuildType().getId());
    }

    @Test
    public void projectAdminShouldNotHaveRightsToCreateBuildConfigToAnotherProject() {
        var firstTestData = testDataStorage.addTestData();
        var secondTestData = testDataStorage.addTestData();

        projectRequestBySuperUser.create(firstTestData.getProject());
        projectRequestBySuperUser.create(secondTestData.getProject());

        firstTestData.getUser().setRoles(setRoles(PROJECT_ADMIN, P.getText() + firstTestData.getProject().getId()));

        userRequestBySuperUser.create(firstTestData.getUser());

        secondTestData.getUser().setRoles(setRoles(PROJECT_ADMIN, P.getText() + secondTestData.getProject().getId()));

        userRequestBySuperUser.create(secondTestData.getUser());

        new UncheckedBase(Specifications.getSpec().authSpec(secondTestData.getUser()), BUILD_CONFIG_ENDPOINT)
                .create(firstTestData.getBuildType())
                .then().assertThat().statusCode(SC_FORBIDDEN);
    }

    @AfterMethod
    public void afterTest() {
        softAssert.assertAll();
    }
}
