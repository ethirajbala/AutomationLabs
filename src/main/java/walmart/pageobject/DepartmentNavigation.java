package walmart.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import walmart.labs.util.Report;

public class DepartmentNavigation {

		// Search field on home page
		@FindBy(xpath = "//input[@placeholder='Search']")
		WebElement productSearch;

		// The search submit button on home page.
		@FindBy(xpath = "//button[contains(@class, 'searchbar-submit')]")
		WebElement searchSubmitButton;	
		
	    // Memory size
		@FindBy(xpath = "//input[@id = 'generic-facet-16GB to 32GB-1']")
		WebElement memorySize;
			
		
        // Product Search on home page
		public boolean searchProduct(String productName) {
			try {
				productSearch.sendKeys(productName);
				return true;
			
			    }catch (Exception e) {
				Report.log("Unable to search the product" + productName);
				return false;
			}
			
		}

		
		// Search submit button on home page
		public void clickSearchSubmit() {
			try {
				searchSubmitButton.click();
				
			    }catch (Exception e) {
				Report.log("Unable to click the search submit button on home page");
			}
		}

			// Product Selection
			public boolean productSelection(WebDriver driver, String productName) {	
				try {
					// get product name and build dynamic xPath
						
					String productXpath = "//a[contains(@href, '"+productName+"-')]";
					
					System.out.print("ProductSelection Xpath:" + productXpath);
					
					WebElement product = driver.findElement(By.xpath(productXpath));
					product.click();
					
					return true;
					
				 }catch (Exception e) {
					Report.log("Unable to select the product");
					return false;
					
				}	
		}
		
			//MemorySize selection
			public void memorySelection(){
				try{
					
				}catch(Exception e){
					Report.log("Unable to select the memory size");
				}
			}			
			
	}
	
