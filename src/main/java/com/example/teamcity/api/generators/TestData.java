package com.example.teamcity.api.generators;

import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Deletable;
import com.example.teamcity.api.models.NewProjectDescription;
import com.example.teamcity.api.models.User;
import com.example.teamcity.api.requests.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder
@With
public class TestData {
    private User user;
    private NewProjectDescription project;
    private BuildType buildType;

    public static void delete(Deletable entity) {
        new UncheckedBase(Specifications.getSpec().superUserSpec(), entity.getEndpointToDelete())
                .delete(entity.getIdToDelete());
    }
}
