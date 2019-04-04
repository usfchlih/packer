package com.mobiquityinc.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds a testCase
 * @author ychlih
 */
public class TestCase {

    // Weight limit in the test case.
    private Double weightLimit;
    // List of Items in the test case.
    private List<Item> items = new ArrayList<>();

    public Double getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(Double weightLimit) {
        this.weightLimit = weightLimit;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }


}
