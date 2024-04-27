package com.example.teamcity.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuildType {
    private String id;
    private String name;
    private NewProjectDescription project;
    private Steps steps;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Steps {
        private List<Step> step;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Step {
        private String name;
        private String type;
        private Properties properties;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Properties {
        private List<Property> property;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Property {
        private String name;
        private String value;
    }
}
