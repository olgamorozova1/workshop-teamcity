package com.example.teamcity.api.constants;

public class Endpoints {
    public final static String USER_ENDPOINT = "/app/rest/users";
    public final static String USER_BY_USERNAME_ENDPOINT = USER_ENDPOINT + "/username:";
    public static final String PROJECT_ENDPOINT = "/app/rest/projects";
    public static final String PROJECT_ENDPOINT_BY_ID = PROJECT_ENDPOINT + "/id:";
    public static final String BUILD_CONFIG_ENDPOINT = "/app/rest/buildTypes";
    public static final String BUILD_CONFIG_ENDPOINT_BY_ID = BUILD_CONFIG_ENDPOINT + "/id:";
    public final static String AUTH_SETTINGS_ENDPOINT = "/app/rest/server/authSettings";
    public final static String AGENTS_ENDPOINT = "/app/rest/agents";
    public final static String AGENTS_ENDPOINT_BY_ID = AGENTS_ENDPOINT + "/id:%s";
    public final static String AUTHORIZE_INFO_AGENTS_ENDPOINT = AGENTS_ENDPOINT_BY_ID + "/authorizedInfo";
    public final static String AGENTS_TYPES_ENDPOINT = "/app/rest/agentTypes";
}
