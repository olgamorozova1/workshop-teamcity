package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.models.User;
import com.example.teamcity.api.requests.Crud;
import com.example.teamcity.api.requests.Request;
import com.example.teamcity.api.requests.unchecked.UncheckedUser;
import io.restassured.specification.RequestSpecification;

import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_OK;


public class CheckedUser extends Request implements Crud {

    public CheckedUser(RequestSpecification spec) {
        super(spec);
    }


    @Override
    public User create(Object obj) {
        return new UncheckedUser(spec)
                .create(obj)
                .then().assertThat().statusCode(SC_OK)
                .extract().as(User.class);
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
    public String delete(String id) {
        return new UncheckedUser(spec)
                .delete(id)
                .then().assertThat().statusCode(SC_NO_CONTENT)
                .extract().asString();
    }
}
