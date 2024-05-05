package com.example.teamcity.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class AuthSettings {
    private boolean allowGuest;
    private String guestUsername;
    private String welcomeText;
    private boolean collapseLoginForm;
    private boolean perProjectPermissions;
    private boolean emailVerification;
    private Modules modules;

    @Data
    @AllArgsConstructor
    @Builder
    public static class Modules {
        private List<Module> module;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Module {
        private String name;
        private Properties properties;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Properties {
        private List<Property> property;
        private int count;
        private String href;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Property {
        private String name;
        private String value;
        private boolean inherited;
        private Type type;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Type {
        private String rawValue;
    }
}
