package com.example.teamcity.api.spec;

import com.example.teamcity.api.models.User;
import com.github.viclovsky.swagger.coverage.SwaggerCoverageRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static com.example.teamcity.api.config.Config.getProperty;
import static java.lang.String.format;


public class Specifications {
    private static Specifications spec;

    private Specifications() {
    }

    public static Specifications getSpec() {
        if (spec == null) {
            spec = new Specifications();
        }
        return spec;
    }

    private RequestSpecBuilder reqBuilder() {
        return new RequestSpecBuilder()
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new SwaggerCoverageRestAssured())
                .setBaseUri("http://" + getProperty("host"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON);
    }

    public RequestSpecification unauthSpec() {
        return reqBuilder().build();
    }

    public RequestSpecification authSpec(User user) {
        return reqBuilder()
                .setBaseUri(format("http://%s:%s@%s", user.getUsername(), user.getPassword(), getProperty("host")))
                .build();
    }

    public RequestSpecification superUserSpec() {
        return reqBuilder()
                .setBaseUri(format("http://:%s@%s", getProperty("superUserToken"), getProperty("host")))
                .build();
    }
}
