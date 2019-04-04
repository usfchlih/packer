package com.mobiquityinc.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Holds Package Candidate
 * @author ychlih
 */
public class Package {

    private Double weight = 0.0;
    private Double cost = 0.0;
    private List<Item> items = new ArrayList<>();


    public Double getCost() {
        return cost;
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public Double getWeight() {
        return weight;
    }

    public void addItem(Item item) {
        this.items.add(item);
        this.weight += item.getWeight();
        this.cost += item.getCost();
    }
}
