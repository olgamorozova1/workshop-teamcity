package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.requests.Crud;
import com.example.teamcity.api.requests.Request;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;


public class UncheckedUser extends Request implements Crud {
    private final static String USER_ENDPOINT = "/app/rest/users";
    private final static String USER_BY_USERNAME_ENDPOINT = USER_ENDPOINT + "/username:";

    public UncheckedUser(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Response create(Object obj) {
        return given()
                .spec(spec)
                .body(obj)
                .post(USER_ENDPOINT);
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
    public Response delete(String username) {
        return given()
                .spec(spec)
                .delete(USER_BY_USERNAME_ENDPOINT + username);
    }
}
