package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.models.Agent;
import com.example.teamcity.api.models.AgentTypes;
import com.example.teamcity.api.models.Agents;
import com.example.teamcity.api.requests.CheckedBase;
import com.example.teamcity.ui.pages.StartUpPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.example.teamcity.api.constants.Endpoints.AGENTS_ENDPOINT;
import static com.example.teamcity.api.constants.Endpoints.AGENTS_TYPES_ENDPOINT;
import static com.example.teamcity.api.constants.Endpoints.AUTHORIZE_INFO_AGENTS_ENDPOINT;
import static com.example.teamcity.api.spec.Specifications.getSpec;
import static com.example.teamcity.api.waiters.Waiter.waitForCondition;
import static java.lang.String.format;

public class SetupTest extends BaseUiTest {
    @Test
    public void startUpTest() {
        new StartUpPage()
                .open()
                .setupTeamCityServer()
                .getCreateAccountHeader()
                .shouldHave(Condition.text("Create Administrator Account"));
    }

    @Test
    public void setUpAgentTest() {
        var isAgentExist = waitForCondition(() -> new CheckedBase<>(getSpec().superUserSpec(), AGENTS_TYPES_ENDPOINT, AgentTypes.class).get(), agentTypes -> agentTypes.getCount() >= 1);
        var agentTypes = isAgentExist ? new CheckedBase<>(getSpec().superUserSpec(), AGENTS_TYPES_ENDPOINT, AgentTypes.class).get() : null;
        Assert.assertNotNull(agentTypes);
        var agentIdToAuthorize = agentTypes.getAgentType().get(0).getId();
        new CheckedBase<>(getSpec().superUserSpec(), format(AUTHORIZE_INFO_AGENTS_ENDPOINT, agentIdToAuthorize), Agent.class)
                .put(new Agent().withStatus(true));
        var agents = new CheckedBase<>(getSpec().superUserSpec(), AGENTS_ENDPOINT, Agents.class)
                .get();
        softAssert.assertThat(agents.getAgent().get(0).getId()).isEqualTo(agentIdToAuthorize);
        softAssert.assertAll();
    }
}
