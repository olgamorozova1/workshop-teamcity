package com.example.teamcity.api.generators;

import com.github.javafaker.Faker;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

public class RandomData {
    private static final Faker faker = new Faker();

    public static String getString(int length) {
        return randomAlphabetic(length);
    }

    public static String getNumber(int length) {
        return randomNumeric(length);
    }

    public static String getValidCharactersString() {
        return randomAlphabetic(1) + "_" + randomAlphanumeric(10);
    }

    public static char getInvalidCharacter() {
        var invalidChars = "!\"#$%&'()*+,-./:;<=>?@[\\]^`{|}~";
        return invalidChars.charAt(faker.random().nextInt(invalidChars.length()));
    }

    public static String getEmail() {
        return faker.internet().emailAddress();
    }
}
