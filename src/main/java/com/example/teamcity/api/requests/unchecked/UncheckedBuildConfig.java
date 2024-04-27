package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.requests.Crud;
import com.example.teamcity.api.requests.Request;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UncheckedBuildConfig extends Request implements Crud {
    private static final String BUILD_CONFIG_ENDPOINT = "/app/rest/buildTypes";
    private static final String BUILD_CONFIG_ENDPOINT_BY_ID = BUILD_CONFIG_ENDPOINT + "/id:";

    public UncheckedBuildConfig(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Response create(Object obj) {
        return given().spec(spec).body(obj)
                .post(BUILD_CONFIG_ENDPOINT);
    }

    @Override
    public Object get(String id) {
        return null;
    }

    @Override
    public Object update(Object value, Object obj) {
        return null;
    }

    @Override
    public Response delete(String id) {
        return given().spec(spec)
                .delete(BUILD_CONFIG_ENDPOINT_BY_ID + id);
    }
}
