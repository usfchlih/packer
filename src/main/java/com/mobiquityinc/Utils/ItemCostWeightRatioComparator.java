package com.mobiquityinc.Utils;

import com.mobiquityinc.models.Item;

import java.util.Comparator;

/**
 * @author ychlih
 */
public class ItemCostWeightRatioComparator implements Comparator<Item> {
    @Override
    public int compare(Item item1, Item item2) {
        int compare;

        if (item1.getCostWeightRatio() > item2.getCostWeightRatio()) {
            compare = -1;
        } else if (item1.getCostWeightRatio() < item2.getCostWeightRatio()) {
            compare = 1;
        } else {
            compare = -1;
        }

        return compare;
    }
}
