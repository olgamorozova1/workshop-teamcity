package com.example.teamcity.api.generators;

import com.example.teamcity.api.models.Deletable;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;

import static com.example.teamcity.api.generators.TestData.delete;

public class TestDataStorage {
    private static TestDataStorage testDataStorage;
    private List<TestData> testDataList;
    public List<Object> createdEntities;

    private TestDataStorage() {
        this.testDataList = new ArrayList<>();
        createdEntities = new ArrayList<>();
    }

    public static TestDataStorage getStorage() {
        if (testDataStorage == null) {
            testDataStorage = new TestDataStorage();
        }
        return testDataStorage;
    }

    @Step("Add test data")
    public TestData addTestData() {
        var testData = TestDataGenerator.generate();
        getStorage().testDataList.add(testData);
        return testData;
    }

    @Step("Add test data")
    public TestData addTestData(TestData testData) {
        getStorage().testDataList.add(testData);
        return testData;
    }

    public void addCreatedEntity(Object createdEntity) {
        createdEntities.add(createdEntity);
    }

    public void deleteCreatedEntity() {
        createdEntities.forEach(x -> {
            if (x instanceof Deletable) {
                delete((Deletable) x);
            }
        });
    }
}
