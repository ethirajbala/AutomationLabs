package walmart.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import walmart.labs.util.Report;


public class ProductAddToCart {
	
	// Click quantity dropdwon
	@FindBy(xpath = "//div[@data-name='product-quantity-dropdown']")
	WebElement selectQuantity;
	
	//AddToCartButton
	@FindBy(id="WMItemAddToCartBtn")
	WebElement AddToCartButton;
	
	//CheckoutButton
	@FindBy(id="PACCheckoutBtn")
	WebElement checkOutButton;
	
	
	// Click quantity dropdown
	public void clickQuantity(){
		
		try{
		selectQuantity.click();
		}
		
		catch (Exception e)
		{
		  Report.log("Unable to click Quantity drop down");
		}
	}

	
	// Choose quantity
	public boolean selectQuantity(WebDriver driver, int qty){
		
		try {
		
		String quantity = "//button[@type='button' and contains(text(),'"+qty+"')]";
		WebElement productQuantity = driver.findElement(By.xpath(quantity));
		productQuantity.click();
		return true;
		}
		
		catch(Exception e){
			Report.log("Unable to select quantity");
			return false;
		}
	}
	
	//Click AddtoCartButton
	public void AddToCartButton(){
		
		try{
			AddToCartButton.click();
		}
		
		catch(Exception e){
			Report.log("Unable to click Add To Cart Button");
		}
	}
	
	//Click CheckOutButton
	public void CheckOutButton(){
		
		try{
			checkOutButton.click();
		}
		
		catch(Exception e){
			Report.log("Unable to click checkout Button");
		}
	}	
	
}
