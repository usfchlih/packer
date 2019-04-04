package com.mobiquityinc.packer;


import com.mobiquityinc.exception.APIException;

import static com.mobiquityinc.packer.Packer.chooseItemsToPack;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.mobiquityinc.models.Item;
import com.mobiquityinc.models.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author ychlih
 */
public class PackerTest {
    String testCaseFilePath;

    @BeforeEach
    void init() {
        Path resourceDirectory = Paths.get("src", "test", "resources");
        testCaseFilePath = resourceDirectory.toAbsolutePath().toString() + File.separator + "testCases.txt";
    }


    /**
     * Test the main method
     */
    @Test
    public void packTest() {

        StringBuilder expectedResult = new StringBuilder();
        expectedResult
                .append('4')
                .append(System.lineSeparator())
                .append("–")
                .append(System.lineSeparator())
                .append("2,7")
                .append(System.lineSeparator())
                .append("9,8");

        String actualResult = Packer.pack(testCaseFilePath);
        assertNotNull(actualResult);
        assertEquals(expectedResult.toString().trim(), actualResult.trim());
    }


    /**
     * Test choosing items to pack in the package
     */
    @Test
    public void chooseItemsToPackTestCase1() {
        // test case ==> 34 : (1,15.3,€34) (2,12,€30) (1,66,€22) : expected result 2,1

        List<Item> items = new ArrayList<>();

        items.addAll(
                Arrays.asList(
                        new Item(1, 15.3, 29.00),
                        new Item(2, 12.00, 30.00),
                        new Item(3, 66.00, 22.00)
                )
        );

        TestCase testCase = new TestCase();
        testCase.setWeightLimit(34.00);
        testCase.setItems(items);
        String result = chooseItemsToPack(testCase);

        assertEquals("2,1", result);

    }

    @Test
    public void chooseItemsToPackTestCase2() {
        //75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74)
        // (8,93.18,€35) (9,89.95,€78)
        List<Item> items3 = new ArrayList<>();
        items3.addAll(
                Arrays.asList(
                        new Item(1, 85.31, 29.00),
                        new Item(2, 14.55, 74.00),
                        new Item(3, 3.98, 16.00),
                        new Item(4, 26.24, 55.00),
                        new Item(5, 63.69, 52.00),
                        new Item(6, 76.25, 75.00),
                        new Item(7, 60.02, 74.00),
                        new Item(8, 93.18, 35.00),
                        new Item(9, 89.95, 78.00)


                )
        );
        TestCase testCase3 = new TestCase();
        testCase3.setWeightLimit(75.00);
        testCase3.setItems(items3);
        String result3 = chooseItemsToPack(testCase3);
        assertEquals("2,7", result3);
    }


    @Test
    public void chooseItemsToPackTestCase3() {
        // test case ==> 12 : (1,15.3,€34) (2,12,€30) (1,66,€22) : expected result 1,3
        List<Item> items2 = new ArrayList<>();
        items2.addAll(
                Arrays.asList(
                        new Item(1, 4.00, 6.00),
                        new Item(2, 9.00, 9.00),
                        new Item(3, 4.00, 6.00)
                )
        );

        TestCase testCase2 = new TestCase();
        testCase2.setWeightLimit(12.00);
        testCase2.setItems(items2);
        String result2 = chooseItemsToPack(testCase2);

        assertEquals("3,1", result2);
    }

    @Test
    public void chooseItemsToPackTestCase4() {
        // test case ==> 12 : (1,15.3,€34) (2,12,€30) (1,66,€22) : expected result 1,3
        List<Item> items2 = new ArrayList<>();
        items2.addAll(
                Arrays.asList(
                        new Item(1, 5.00, 6.00),
                        new Item(2, 9.00, 9.00),
                        new Item(3, 4.50, 6.50)
                )
        );

        TestCase testCase2 = new TestCase();
        testCase2.setWeightLimit(12.00);
        testCase2.setItems(items2);
        String result2 = chooseItemsToPack(testCase2);

        assertEquals("3,1", result2);
    }

    /**
     * Test raising ApiException
     */
    @Test
    public void packTestException() {
        assertThrows(APIException.class, () -> Packer.pack("dump/..")
        );
    }
}
