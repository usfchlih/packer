The packer job is to fill packages in a way to maximize the cost within a weight limit.

## Getting Started

This project is meant to be used as a library (maven dependency).


### Prerequisites

Software that need to be installed to use this library :

```
JDK8, Maven
```

### Installing

Clone the repository :

```
git clone https://github.com/usfchlih/packer.git
```
Install in your local maven repository:

```
mvn clean install
```

Add these dependency to you pom.xml

```
  <dependency>
    <groupId>com.mobiquityinc</groupId>
    <artifactId>packer</artifactId>
    <version>1.0-SNAPSHOT</version>
  <dependency>
```

In you class, import the following class :

```
import com.mobiquityinc.packer.Packer;

```

Use the method public static String pack to pack items in packages from the file :
```
Packer.pack("/absolutePath/file.txt")
```
The method takes the absolute path.

## Running the tests

The project includes a file with test cases in : src/test/resources/testCases.txt

To run tests use :
```
mvn test
```
Or directly from the IDE.

## Solution :

The application has two main jobs, parse the file that contains the test cases into into POJOs, and choose from each test case the items to pack.

The solution of packing items combines the following two methods to obtain a combination of Items with Highest price, and eventually lowest weight if price is the same:

##### Method1 : Sort by Costs :
To choose Items to pack, the items get sorted based on cost in descending order, for elements with same costs, they get sorted based on weight in ascending order.
The method will start packing items with highest cost until the weight limit is reached : **first candidate package**.

##### Method 2 : Sort by Cost/weight Ratio:
To choose Items to pack, Calculate Cost/weight ratio of each item, sort items in descending order based on this ratio. Items with highest ratio have high cost and low weight, they are good candidates to fill the package with.
The method will start packing items with highest cost/weight ratio until the weight limit is reached : **second candidate package**.

##### Choose between the obtained packages :
**Compare the two packages obtained by method1 and method2, pick the one with highest cost, if costs are the same, pick the one with the lowest weight.**

