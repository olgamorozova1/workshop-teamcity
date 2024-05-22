package com.example.teamcity.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;

import static com.example.teamcity.api.constants.Endpoints.BUILD_CONFIG_ENDPOINT_BY_ID;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class BuildType implements Deletable {
    private String id;
    private String name;
    private NewProjectDescription project;
    private Steps steps;

    @Override
    public String getEndpointToDelete() {
        return BUILD_CONFIG_ENDPOINT_BY_ID;
    }

    @Override
    public String getIdToDelete() {
        return id;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class Steps {
        private List<Step> step;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class Step {
        private String name;
        private String type;
        private Properties properties;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class Properties {
        private List<Property> property;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class Property {
        private String name;
        private String value;
    }
}
