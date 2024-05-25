package com.example.teamcity.api.waiters;

import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Waiter {

    public static <T> boolean waitForCondition(Supplier<T> request, Predicate<T> condition) {
        final long startTime = System.currentTimeMillis();
        final long endTime = startTime + TimeUnit.MINUTES.toMillis(1);
        var result = false;
        do {
            var response = request.get();
            result = condition.test(response);
        }
        while (!result && System.currentTimeMillis() < endTime);
        return result;
    }
}
