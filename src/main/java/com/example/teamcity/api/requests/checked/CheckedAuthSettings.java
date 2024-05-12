package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.models.AuthSettings;
import com.example.teamcity.api.requests.Crud;
import com.example.teamcity.api.requests.Request;
import com.example.teamcity.api.requests.unchecked.UncheckedAuthSettings;
import io.restassured.specification.RequestSpecification;

import static org.apache.http.HttpStatus.SC_OK;

public class CheckedAuthSettings extends Request implements Crud {

    public CheckedAuthSettings(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public AuthSettings create(Object obj) {
        return new UncheckedAuthSettings(spec).create(obj)
                .then().assertThat().statusCode(SC_OK)
                .extract().as(AuthSettings.class);
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
