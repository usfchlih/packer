package com.mobiquityinc.Utils;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.models.Item;
import com.mobiquityinc.models.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Take the file, return Things of each test case with its package(weightList)
 *
 * @author ychlih
 */
public class Parser {


    private static final Logger logger = LoggerFactory.getLogger(Parser.class);

    //Max weight and cost of	an item	is ≤ 100
    private static final int ITEM_MAX_COST = 100;
    private static final int ITEM_MAX_WEIGHT = 100;

    /**
     * Parse the testCase String line into an Object of type TestCase.
     *
     * @param testCase String of format xx : (xx,xx,$x) (xx,xx,$x)`
     * @return testCase @TestCase
     */
    public static TestCase parseLine(String testCase) {

        TestCase parsedTestCase = new TestCase();
        try {

            List<Item> items = new ArrayList<>();
            // The weight limit and the items are separated by `:
            String[] testCaseArray = testCase.split(":");
            logger.debug("Weight limit : {}, Els of this testCase : {}", testCaseArray[0], testCaseArray[1]);
            parsedTestCase.setWeightLimit(Double.valueOf(testCaseArray[0].trim()));
            // Item are separated by spaces : (xx,xx,$x) (xx,xx,$x)
            String[] itemsArray = testCaseArray[1].trim().split(" ");

            Item item;
            for (int i = 0; i < itemsArray.length; i++) {
                // Parse String item into an object Item
                item = parseItem(itemsArray[i]);
                // Max weight and cost of an item is ≤ 100
                if ((item.getCost() <= ITEM_MAX_COST) && (item.getWeight() <= ITEM_MAX_WEIGHT)) {
                    items.add(item);
                }

            }
            // Put the list of parsed Items into the Parsed testCase Object
            parsedTestCase.setItems(items);
            logger.debug("Weight Limit : {}, number of els {}", parsedTestCase.getWeightLimit(), parsedTestCase.getItems().size());
        } catch (Exception e) {
            throw new APIException("Test Case in bad format");
        }
        return parsedTestCase;
    }


    /**
     * Parse String Item into Object Item.
     *
     * @param ItemStr String of format (xx,xx,$x)
     * @return Object of Type Item
     */
    public static Item parseItem(String ItemStr) {

        logger.debug(" Parsing Item : {}", ItemStr);
        // Remove the brackets in (xx,xx,$xx)
        String[] oneItemArray = ItemStr.substring(1, ItemStr.length() - 1).split(",");
        Item item = new Item();
        try {

            item.setIndex(Integer.valueOf(oneItemArray[0]));
            item.setWeight(Double.valueOf(oneItemArray[1]));
            // Remove the Currency sign in (xx,xx,$x)
            item.setCost(Double.valueOf(oneItemArray[2].replaceAll("[^0-9]", "")));
            logger.debug("Parsed Item => Index: {} , weight: {}, Cost: {}", item.getIndex(), item.getWeight(), item.getCost());
        } catch (Exception e) {
            throw new APIException("Item string in bad format");
        }
        return item;

    }

}
