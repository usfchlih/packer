package com.mobiquityinc.Utils;

import com.mobiquityinc.models.Item;

import java.util.Comparator;

/**
 * @author ychlih
 */
public class ItemComparator implements Comparator<Item> {

    @Override
    public int compare(Item item1, Item item2) {
        int compare;
        if (item1.getCost() > item2.getCost()) {
            compare = -1;
        } else if (item1.getCost() < item2.getCost()) {
            compare = 1;
        } else {
            //Compare weights (the lowest weight first)
            if (item1.getWeight() > item2.getWeight()) compare = 1;
            else compare = -1;
        }
        return compare;
    }

}
