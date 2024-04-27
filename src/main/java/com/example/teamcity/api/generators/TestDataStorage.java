package com.example.teamcity.api.generators;

import java.util.ArrayList;
import java.util.List;

public class TestDataStorage {
    private static TestDataStorage testDataStorage;
    private List<TestData> testDataList;

    private TestDataStorage() {
        this.testDataList = new ArrayList<>();
    }

    public static TestDataStorage getStorage() {
        if (testDataStorage == null) {
            testDataStorage = new TestDataStorage();
        }
        return testDataStorage;
    }

    public TestData addTestData() {
        var testData = TestDataGenerator.generate();
        getStorage().testDataList.add(testData);
        return testData;
    }

    public TestData addTestData(TestData testData) {
        getStorage().testDataList.add(testData);
        return testData;
    }

    public void delete() {
        testDataList.forEach(TestData::delete);
    }
}
