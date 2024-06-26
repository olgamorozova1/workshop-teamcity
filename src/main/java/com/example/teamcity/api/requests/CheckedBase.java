package com.example.teamcity.api.requests;

import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;

import static com.example.teamcity.api.generators.TestDataStorage.getStorage;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_OK;

public class CheckedBase<T> extends Request implements Crud {
    private final Class<T> returnType;

    public CheckedBase(RequestSpecification spec, String endpoint, Class<T> returnType) {
        super(spec, endpoint);
        this.returnType = returnType;
    }

    @Step("Send api request to create object")
    @Override
    public T create(Object obj) {
        var entity = new UncheckedBase(spec, endpoint).create(obj)
                .then().assertThat().statusCode(SC_OK)
                .extract().as(returnType);
        getStorage().addCreatedEntity(entity);
        return entity;
    }

    @Step("Send api request to update object")
    public T put(Object obj) {
        return new UncheckedBase(spec, endpoint).put(obj)
                .then().assertThat().statusCode(SC_OK)
                .extract().as(returnType);
    }

    @Step("Send api request to get data")
    @Override
    public T get(String id) {
        return new UncheckedBase(spec, endpoint)
                .get(id)
                .then().assertThat().statusCode(SC_OK)
                .extract().as(returnType);
    }

    @Step("Send api request to get data")
    public T get() {
        return new UncheckedBase(spec, endpoint)
                .get()
                .then().assertThat().statusCode(SC_OK)
                .extract().as(returnType);
    }

    @Step("Send api request to update data")
    @Override
    public T update(Object value, Object obj) {
        return null;
    }

    @Step("Send api request to delete data")
    @Override
    public String delete(String id) {
        return new UncheckedBase(spec, endpoint)
                .delete(id)
                .then().assertThat().statusCode(SC_NO_CONTENT)
                .extract().asString();
    }
}
