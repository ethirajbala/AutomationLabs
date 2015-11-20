package walmart.testsuite;


import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import org.junit.Assert;

import walmart.labs.pageobject.DepartmentNavigation;
import walmart.labs.pageobject.ProductAddToCart;
import walmart.labs.pageobject.SigninPage;
import walmart.labs.testdata.TestDataAccessor;
import walmart.labs.testdata.factory.TestDataReaderFactory;
import walmart.labs.util.ConfigurationProperties;
import walmart.labs.util.annotation.TestData;
import walmart.labs.util.annotation.TestName;



 // Class to validate Add and Remove item

public class AddAndRemoveCartValidation extends TestBase {

	
	 // @param aTestCaseData the a test case data
	
	@TestName(testCaseName = "AddAndRemoveCartValidation", testCaseIds={"01"}, testCaseDescription="")
	@TestData(type = TestDataReaderFactory.JSON, source = "AddAndRemoveCartValidationTestData", dataBean = "walmart.labs.testdata.TestDataAccessor")
	@Test(groups = {"AddandRemoveCart"})

	public void addAndRemoveCartValidation(TestDataAccessor aTestCaseData){

		 // object for TestDataAccessor 
		TestDataAccessor testCaseData = aTestCaseData;

		// instantiated PageObjects for Page class
		DepartmentNavigation DepartmentNavigation = PageFactory.initElements(getDriver(), DepartmentNavigation.class);
		ProductAddToCart ProductAddToCart = PageFactory.initElements(getDriver(), ProductAddToCart.class);
		SigninPage SigninPage = PageFactory.initElements(getDriver(), SigninPage.class);
		
		// MasterData setup can defined at test case level MasterData.method(testCaseData);

		getDriver().get(ConfigurationProperties.get("customerFlow.url")); 
		Assert.assertTrue(DepartmentNavigation.searchProduct(testCaseData.getProductName()));
		DepartmentNavigation.clickSearchSubmit();
		Assert.assertTrue(DepartmentNavigation.productSelection(getDriver(),testCaseData.getProductName()));
		DepartmentNavigation.memorySelection();
		ProductAddToCart.clickQuantity();
		Assert.assertTrue(ProductAddToCart.selectQuantity(getDriver(), testCaseData.getquantity()));
		ProductAddToCart.AddToCartButton();
		ProductAddToCart.CheckOutButton();
		SigninPage.enterEmail(getDriver(), testCaseData.getenterEmail());
		SigninPage.enterPassword(getDriver(), testCaseData.getenterPassword());
	}

}
