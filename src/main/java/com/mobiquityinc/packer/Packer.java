package com.mobiquityinc.packer;

import com.mobiquityinc.Utils.ItemComparator;
import com.mobiquityinc.Utils.ItemCostWeightRatioComparator;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.models.Package;
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
     * The main method, Pack items form test cases to package
     *
     * @param filePath
     * @return
     * @throws APIException
     */

    public static String pack(String filePath) throws APIException {

        if (filePath.contains("..")) {
            throw new APIException("Filename contains invalid path sequence " + filePath);
        }
        // Read file
        StringBuilder outputResult = new StringBuilder();
        try {
            String testCaseStringLine;
            TestCase parsedTestCase;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            //Read each line as a test case.
            while ((testCaseStringLine = bufferedReader.readLine()) != null) {
                logger.debug("Test Case to Parse : {}", testCaseStringLine);
                // parse string line into TestCase
                parsedTestCase = Parser.parseLine(testCaseStringLine);
                logger.debug("Parsed TestCase : weight limit : {}, number of elt: {}",
                        parsedTestCase.getWeightLimit(), parsedTestCase.getItems().size());
                //Choose items from the test case to pack
                outputResult.append(chooseItemsToPack(parsedTestCase) + System.lineSeparator());
            }

        } catch (IOException e) {
            throw new APIException(filePath + " Not Found");
        }

        logger.info(" output result:{}{}", System.lineSeparator(), outputResult.toString());
        return outputResult.toString();
    }


    /**
     * Choose things to pack from the TestCase
     *
     * @param testCase
     * @return indexes of chosen things separated by comma
     */
    protected static String chooseItemsToPack(final TestCase testCase) {

        // Sort by cost and start picking items with the highest cost - return first package candidate
        Package costBasedPackage = chooseItemsToPackByCost(testCase);
        //Sort by cost/weight ratio, start picking items with the highest ratio - return second package candidate
        Package costWeightRatioBasedPackage = chooseItemsToPackByCostWeightRatio(testCase);
        // Choose the package with the highest cost or lowest weight
        Package chosenPackage = choosePackage(costBasedPackage, costWeightRatioBasedPackage);

        // format result , separate values by comma, put "–" if no value
        StringBuilder indexesOfChosenItems = new StringBuilder();
        for (Item item : chosenPackage.getItems()) {
            if (indexesOfChosenItems.length() != 0) {
                indexesOfChosenItems.append(",").append(item.getIndex());
            } else {
                indexesOfChosenItems.append(item.getIndex());
            }
        }

        if (indexesOfChosenItems.length() == 0) {
            indexesOfChosenItems.append("–");
        }

        return indexesOfChosenItems.toString();
    }

    /**
     * @param testCase Object of type TestCase
     * @return Object of type Package
     */
    private static Package chooseItemsToPackByCost(final TestCase testCase) {

        Set<Item> itemsSortedByCost = new TreeSet<>(new ItemComparator());
        itemsSortedByCost.addAll(testCase.getItems());
        Package costBasedPackage = new Package();


        for (Item item : itemsSortedByCost) {
            logger.debug("Item index : {} - Cost {} - Weight : {}", item.getIndex(), item.getCost(), item.getWeight());

            //Max weight that a	package	can	take is	≤	100.
            if ((costBasedPackage.getWeight() + item.getWeight() >= PACKAGE_MAX_WEIGHT)) {
                break;
            }

            if ((costBasedPackage.getWeight() + item.getWeight()) <= testCase.getWeightLimit()) {
                costBasedPackage.addItem(item);
            }

        }

        return costBasedPackage;

    }

    /**
     * Pack items by sorting them by cost/weight ratio
     *
     * @param testCase
     * @return
     */
    private static Package chooseItemsToPackByCostWeightRatio(final TestCase testCase) {

        Set<Item> itemsSortedByCostWeightRatio = new TreeSet<>(new ItemCostWeightRatioComparator());
        itemsSortedByCostWeightRatio.addAll(testCase.getItems());
        Package costWeightRatioBasedPackage = new Package();

        for (Item item : itemsSortedByCostWeightRatio) {
            logger.debug("Item index : {} - Cost {} - Weight : {}", item.getIndex(), item.getCost(), item.getWeight());

            //Max weight that a	package	can	take is	≤	100.
            if ((costWeightRatioBasedPackage.getWeight() + item.getWeight() >= PACKAGE_MAX_WEIGHT)) {
                break;
            }

            if ((costWeightRatioBasedPackage.getWeight() + item.getWeight()) <= testCase.getWeightLimit()) {

                costWeightRatioBasedPackage.addItem(item);
            }
        }
        return costWeightRatioBasedPackage;
    }


    /**
     * Choose Package between packages based on cost and weight
     *
     * @param costBasedPackage            Package containing items obtained by sorting items by cost
     * @param costWeightRatioBasedPackage Package containing items obtained by sorting items by cost/weight ratio
     * @return Object of type Package
     */
    private static Package choosePackage(Package costBasedPackage, Package costWeightRatioBasedPackage) {

        Package chosenPackage;
        if (costBasedPackage.getCost() > costWeightRatioBasedPackage.getCost()) {
            //choose costBasedPackage
            chosenPackage = costBasedPackage;
        } else if (costBasedPackage.getCost() < costWeightRatioBasedPackage.getCost()) {
            //choose costWeightRatioBasedPackage
            chosenPackage = costWeightRatioBasedPackage;
            // package with the same price , send the package with the lowest weight
        } else {
            if (costBasedPackage.getWeight() < costWeightRatioBasedPackage.getWeight()) {
                //choose costBasedPackage
                chosenPackage = costBasedPackage;
            } else {
                //choose costWeightRatioBasedPackage
                chosenPackage = costWeightRatioBasedPackage;
            }
        }
        return chosenPackage;
    }

}
