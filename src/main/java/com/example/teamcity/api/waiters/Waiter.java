package com.example.teamcity.api.waiters;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Waiter {

    @SneakyThrows
    public static <T> boolean waitForCondition(Supplier<T> request, Predicate<T> condition) {
        final long startTime = System.currentTimeMillis();
        final long endTime = startTime + TimeUnit.SECONDS.toMillis(90);
        var result = false;
        do {
            var response = request.get();
            result = condition.test(response);
            Thread.sleep(5000);
        }
        while (!result && System.currentTimeMillis() < endTime);
        return result;
    }
}
