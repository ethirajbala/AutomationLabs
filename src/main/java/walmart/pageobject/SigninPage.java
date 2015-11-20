package walmart.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import walmart.labs.util.Report;

public class SigninPage {
	
	
	//Signin email
	@FindBy(id="COAC0WelAccntEmail")
	WebElement enterEmail;
	
	//Password field
	@FindBy(id="COAC0WelAccntPswd")
	WebElement enterPassword;
	
	//Account Signin button
	@FindBy(id="COAC0WelAccntSignInBtn")
	WebElement accountSignin;
	

	
	
	public boolean enterEmail(WebDriver driver, String email){
		
		try{
			enterEmail.sendKeys(email);	
			return true;
		}
		
		catch(Exception e){
			Report.log("Unable to enter email address");
			return false;
		}	
	}
	
  public boolean enterPassword(WebDriver driver, String password){
		
		try{
			enterPassword.sendKeys(password);	
			return true;
		}
		
		catch(Exception e){
			Report.log("Unable to enter password");
			return false;
		}	
	}
  
  public void accountSignin(){
		
		try{
			accountSignin.click();
		}
		
		catch(Exception e){
			Report.log("Unable to click Signin Button");
		}	
	}
	
	
}
