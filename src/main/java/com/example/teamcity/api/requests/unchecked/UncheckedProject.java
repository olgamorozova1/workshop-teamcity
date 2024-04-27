package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.requests.Crud;
import com.example.teamcity.api.requests.Request;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;


public class UncheckedProject extends Request implements Crud {
    private static final String PROJECT_ENDPOINT = "/app/rest/projects";
    private static final String PROJECT_ENDPOINT_BY_ID = PROJECT_ENDPOINT + "/id:";

    public UncheckedProject(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Response create(Object obj) {
        return given()
                .spec(spec)
                .body(obj)
                .post(PROJECT_ENDPOINT);
    }

    @Override
    public Response get(String id) {
        return given().spec(spec).get(PROJECT_ENDPOINT_BY_ID + id);
    }

    @Override
    public Object update(Object value, Object obj) {
        return null;
    }

    @Override
    public Response delete(String id) {
        return given()
                .spec(spec)
                .delete(PROJECT_ENDPOINT_BY_ID + id);
    }
}
