package com.mobiquityinc.models;

/**
 * Holds an item
 *
 * @author ychlih
 */
public class Item {


    private Integer index;
    private Double weight;
    private Double cost;
    // (cost/weight)
    private Double costWeightRatio;

    public Item(Integer index, Double weight, Double cost) {
        this.index = index;
        this.weight = weight;
        this.cost = cost;

    }

    public Item() {

    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }


    public Double getCostWeightRatio() {
        if (weight != 0) {
            this.costWeightRatio = cost / weight;
        }
        return this.costWeightRatio;
    }


}
