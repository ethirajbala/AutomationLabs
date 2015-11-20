# AutomationLabs

Framework details:

Automation Framework build on page class model. This approach reduces maintenance effort during change in requirements / element id.
Code reusability, easy scripting by invoking page method and its very well support Agile project automation as well.

Proper validation at script level, detail test run log, screen capture at failure, Parallel execution, Integrate with Maven and CI, test report can be push to test management tools.

Usage:

Framework has following packages,

../pageobject - single class to cover all objects in a page
../test data - getter and setter method to maintain data
../test data/factory - JSON and XML parser (Not in GIT)
../testsuite - automation scripts with test case name and TestBase class to handle before and after test method
../util - contains common method, driver factory (Not in GIT)
../dataprovider - data provider to extract data from test data file (Not in GIT)
../listener - define test groups
resources/test data - test data for test case and to keep master test data at application level
config.properties - 

pageObject Class:

DepartmentNavigation.java -
Search home page to search and select the product, related methods are available for access.
dynamic xpath’s are build where ever its require


ProductAddtoCart.java -

quantity selection and Add to cart method are available.
dynamic Xpath’s are build where ever its require.

SigninPage.java -

login methods are available in this class.

testng.xml -

Defined test class at group level 
Defined parallel test execution count 


POM.XML-

All dependencies are defined
Path for test report generation
