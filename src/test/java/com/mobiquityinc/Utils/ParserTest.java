package com.mobiquityinc.Utils;

import com.mobiquityinc.models.Item;
import com.mobiquityinc.models.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.mobiquityinc.Utils.Parser.parseItem;
import static com.mobiquityinc.Utils.Parser.parseLine;

public class ParserTest {


    /**
     * Test Parsing a String test Case  into TestCase Object
     */
    @Test
    public void parseLineTest() {
        String testCaseStr = "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
        TestCase testCase = parseLine(testCaseStr);
        Assertions.assertEquals(81, testCase.getWeightLimit());
        Assertions.assertNotNull(testCase.getItems().get(0));
        Assertions.assertEquals(6, testCase.getItems().size());
    }


    /**
     * Test Parsing a string item, into Object of type Item.
     */
    @Test
    public void parseItemTest() {
        String itemStr = "(1,53.38,€45)";
        Item item = parseItem(itemStr);
        Assertions.assertNotNull(item);
        Assertions.assertEquals(1, item.getIndex());
        Assertions.assertEquals(53.38, item.getWeight());
        Assertions.assertEquals(45, item.getCost());
    }
}
