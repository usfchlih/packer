package com.mobiquityinc.packer;

import com.mobiquityinc.Utils.ItemComparator;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.models.TestCase;
import com.mobiquityinc.models.Item;
import com.mobiquityinc.Utils.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;


/**
 * @author ychlih
 */
public class Packer {

    private static final Logger logger = LoggerFactory.getLogger(Packer.class);

    //Max weight that a	package	can	take is	≤	100.
    private static final int PACKAGE_MAX_WEIGHT = 100;

    /**
     * Main method,
     * @param filePath
     * @return
     * @throws APIException
     */

    public static String pack(String filePath) throws APIException {

        if (filePath.contains("..")) {
            throw new APIException("Filename contains invalid path sequence " + filePath);
        }

        FileReader fileReader;
        BufferedReader bufferedReader;
        String testCaseStringLine;
        TestCase parsedTestCase;
        StringBuilder outputResult = new StringBuilder();
        try {
            fileReader = new FileReader(filePath);
            bufferedReader = new BufferedReader(fileReader);

            while ((testCaseStringLine = bufferedReader.readLine()) != null) {
                logger.debug("Test Case to Parse : {}", testCaseStringLine);
                parsedTestCase = Parser.parseLine(testCaseStringLine);
                logger.debug("Parsed TestCase : " +
                        "weight limit : {}, " +
                        "number of elt: {}", parsedTestCase.getWeightLimit(), parsedTestCase.getItems().size());
                outputResult.append(chooseItemsToPack(parsedTestCase) + System.lineSeparator());
            }

        } catch (IOException e) {
            throw new APIException(filePath + " Not Found");
        }

        logger.debug(" output result:{}{}", System.lineSeparator(), outputResult.toString());
        return outputResult.toString();
    }


    /**
     * Choose things to pack from the TestCase
     *
     * @param testCase
     * @return indexes of chosen things separated by comma
     */
    public static String chooseItemsToPack(final TestCase testCase) {

        int currentPackageWeight = 0;
        Set<Item> itemsSortedByCost = new TreeSet<>(new ItemComparator());
        itemsSortedByCost.addAll(testCase.getItems());
        StringBuilder indexesOfChosenItems = new StringBuilder();


        for (Item item : itemsSortedByCost) {
            logger.debug("Item index : {} - cost {} - weight : {}", item.getIndex(), item.getCost(), item.getWeight());

            if ((currentPackageWeight + item.getWeight()) <= testCase.getWeightLimit()) {

                currentPackageWeight += item.getWeight();
                if (indexesOfChosenItems.length() != 0) {
                    indexesOfChosenItems.append(",");
                }
                indexesOfChosenItems.append(item.getIndex());
            }

            //Max weight that a	package	can	take is	≤	100.
            if ((currentPackageWeight + item.getWeight() >= PACKAGE_MAX_WEIGHT)) {
                break;
            }

        }

        if (indexesOfChosenItems.length() == 0) {
            indexesOfChosenItems.append("–");
        }

        return indexesOfChosenItems.toString();
    }

}
