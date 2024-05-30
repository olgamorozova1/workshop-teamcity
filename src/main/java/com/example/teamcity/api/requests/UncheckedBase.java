package com.example.teamcity.api.requests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UncheckedBase extends Request implements Crud {

    public UncheckedBase(RequestSpecification spec, String endpoint) {
        super(spec, endpoint);
    }


    @Override
    public Response create(Object obj) {
        return given()
                .spec(spec)
                .body(obj)
                .post(endpoint);
    }

    public Response put(Object obj) {
        return given()
                .spec(spec)
                .body(obj)
                .put(endpoint);
    }

    @Override
    public Response get(String id) {
        return given().spec(spec).get(endpoint + id);
    }

    public Response get() {
        return given().spec(spec).get(endpoint);
    }

    @Override
    public Response update(Object value, Object obj) {
        return null;
    }

    @Override
    public Response delete(String id) {
        return given().spec(spec)
                .delete(endpoint + id);
    }
}
