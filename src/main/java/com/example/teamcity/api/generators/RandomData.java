package com.example.teamcity.api.generators;

import com.github.javafaker.Faker;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class RandomData {
    private static final int LENGTH = 10;
    private static final String TEST_DATA_PREFIX = "test_";
    private static final Faker faker = new Faker();

    public static String getString() {
        return TEST_DATA_PREFIX + randomAlphabetic(LENGTH);
    }

    public static String getEmail() {
        return faker.internet().emailAddress();
    }
}
