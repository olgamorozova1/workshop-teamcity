package com.example.teamcity.api;

import com.example.teamcity.api.requests.checked.CheckedBuildConfig;
import com.example.teamcity.api.requests.checked.CheckedProject;
import com.example.teamcity.api.requests.unchecked.UncheckedBuildConfig;
import com.example.teamcity.api.requests.unchecked.UncheckedProject;
import com.example.teamcity.api.spec.Specifications;
import org.hamcrest.Matchers;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Role.PROJECT_ADMIN;
import static com.example.teamcity.api.enums.Role.SYSTEM_ADMIN;
import static com.example.teamcity.api.enums.Scope.G;
import static com.example.teamcity.api.enums.Scope.P;
import static com.example.teamcity.api.generators.TestDataGenerator.generateRoles;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

public class RolesTest extends BaseApiTest {

    @Test
    public void unauthorizedUserShouldNotHaveRightsToCreateProject() {
        var testData = testDataStorage.addTestData();

        new UncheckedProject(Specifications.getSpec().unauthSpec())
                .create(testData.getProject())
                .then().assertThat().statusCode(SC_UNAUTHORIZED);
        uncheckedWithSuperUser.getProjectRequest()
                .get(testData.getProject().getId())
                .then().assertThat().statusCode(SC_NOT_FOUND)
                .body(Matchers.containsString(format("No project found by locator 'count:1,id:%s'", testData.getProject().getId())));
    }


    @Test
    public void systemAdminShouldHaveRightsToCreateProject() {
        var testData = testDataStorage.addTestData();

        testData.getUser().setRoles(generateRoles(SYSTEM_ADMIN, G.getText()));

        checkedWithSuperUser.getUserRequest().create(testData.getUser());

        var project = new CheckedProject(Specifications.getSpec().authSpec(testData.getUser()))
                .create(testData.getProject());

        softAssert.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }

    @Test
    public void projectAdminShouldHaveRightsToCreateBuildConfigToHisProject() {
        var testData = testDataStorage.addTestData();

        checkedWithSuperUser.getProjectRequest().create(testData.getProject());

        testData.getUser().setRoles(generateRoles(PROJECT_ADMIN, P.getText() + testData.getProject().getId()));

        checkedWithSuperUser.getUserRequest()
                .create(testData.getUser());

        var buildConfig = new CheckedBuildConfig(Specifications.getSpec().authSpec(testData.getUser()))
                .create(testData.getBuildType());

        softAssert.assertThat(buildConfig.getId()).isEqualTo(testData.getBuildType().getId());
    }

    @Test
    public void projectAdminShouldNotHaveRightsToCreateBuildConfigToAnotherProject() {
        var firstTestData = testDataStorage.addTestData();
        var secondTestData = testDataStorage.addTestData();

        checkedWithSuperUser.getProjectRequest().create(firstTestData.getProject());
        checkedWithSuperUser.getProjectRequest().create(secondTestData.getProject());

        firstTestData.getUser().setRoles(generateRoles(PROJECT_ADMIN, P.getText() + firstTestData.getProject().getId()));

        checkedWithSuperUser.getUserRequest().create(firstTestData.getUser());

        secondTestData.getUser().setRoles(generateRoles(PROJECT_ADMIN, P.getText() + secondTestData.getProject().getId()));

        checkedWithSuperUser.getUserRequest().create(secondTestData.getUser());

        new UncheckedBuildConfig(Specifications.getSpec().authSpec(secondTestData.getUser()))
                .create(firstTestData.getBuildType())
                .then().assertThat().statusCode(SC_FORBIDDEN);
    }

    @AfterMethod
    public void afterTest() {
        softAssert.assertAll();
    }
}
