package com.example.teamcity.api.requests;

import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Builder;


@Builder
@AllArgsConstructor
public class Request {
    protected final RequestSpecification spec;
    protected final String endpoint;
}
