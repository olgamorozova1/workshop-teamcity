package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.requests.Crud;
import com.example.teamcity.api.requests.Request;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UncheckedAuthSettings extends Request implements Crud {
    private final static String AUTH_SETTINGS_ENDPOINT = "/app/rest/server/authSettings";

    public UncheckedAuthSettings(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Response create(Object obj) {
        return given()
                .spec(spec)
                .body(obj)
                .put(AUTH_SETTINGS_ENDPOINT);
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
    public Object delete(String id) {
        return null;
    }
}
