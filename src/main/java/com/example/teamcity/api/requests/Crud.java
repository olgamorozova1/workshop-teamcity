package com.example.teamcity.api.requests;

public interface Crud {
    Object create(Object obj);

    Object get(String id);

    Object update(Object value, Object obj);

    Object delete(String id);
}
