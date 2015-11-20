package walmart.testsuite;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import walmart.labs.testdata.TestDataAccessor;
import walmart.labs.util.ConfigurationProperties;
import walmart.labs.util.Report;
import walmart.labs.util.WebDriverFactory;
import walmart.labs.util.annotation.TestName;
import walmart.testsuite.TestBase;;

public class TestBase implements ITest {
	

	/** The logger. */
	static Logger logger = LogManager.getLogger(TestBase.class.getName());

	/** The driver. */
	private WebDriver driver = null;
	
	/** The custom test case name. */
	private String customTestCaseName = null;
	
	/** The test case format. */
	private final String testCaseFormat = "&lt;/%s&gt;";
	
	/** The test method. */
	private Method testMethod = null;
	
	/** Target folder for screenshot capture. */
	private static final String SCREENSHOT_FOLDER = "/target/screenshots/";
	
	/** This command collect browser process Id's and close their session.To make browser name as parameter is pending */
	private static final String[] CLOSE_BROWSER = {"/bin/sh", "-c","kill -9 `ps -ef | grep Safari | grep -v grep | awk '{print $2}'`"};

	/** Process Id's. */ 
	private Process p = null;
	
	/** The mapper. */
	private static ObjectMapper mapper = new ObjectMapper();
	
	

	/**
	 * Gets the driver.
	 *
	 * @return the driver
	 */
	public WebDriver getDriver() {
		if(driver == null){
			String browserInt = ConfigurationProperties.get("browser");
			if(browserInt == null){
				logger.error("Incorrect Web Driver Configuration. Web driver config not found.");
			}else{
				int browser = Integer.parseInt(browserInt.trim());
				driver = WebDriverFactory.getWebDriver(browser);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			}
			
		}
		return driver;
	}
		
	
	/**
	 * Setup test suite.
	 *
	 * @throws Exception the exception
	 */
	@BeforeSuite
	public void setupTestSuite() throws Exception {
		
		/**
		 * Master data can be defined here
		 */
	}
	
	
	/**
	 * Setup.
	 *
	 * @param aMethod the new up
	 */
	@BeforeMethod
	public void setup(Method aMethod){
		this.testMethod = aMethod;
		if(aMethod.getAnnotation(TestName.class) == null || aMethod.getAnnotation(Test.class) == null){
			System.out.println("==========================================================================================================");
			System.out.println("The test method not correctly annotated - templates @TestName / @Test annotations.");
			System.out.println("==========================================================================================================");
			System.exit(1);
		}
		getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}

	/**
	 * TearDown.
	 */
	
	@AfterMethod(alwaysRun = true)
	public void takeScreenshot(ITestContext context, ITestResult result) {

		// Getting TestCasename using Annotation
		Annotation annotation = testMethod.getAnnotation(TestName.class);
		TestName ts = (TestName) annotation;
		
		//Remove spaces from TestCasename
		this.customTestCaseName = ts.testCaseName().replace(" ", "");
		
		// Checking result status @ method level
		if (!result.isSuccess()) {
			
			try {
				//Take screenshot type as file
				File f = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);
				String user_dir = System.getProperty("user.dir");
				String path = SCREENSHOT_FOLDER + this.customTestCaseName;

				File saved = new File(user_dir +"/"+ path, this.customTestCaseName
						+ getCurrentDataTime() + ".png");

				// Copy the screenshot file to target folder
				FileUtils.copyFile(f, saved);
				
				//screenshot link to appear on Reporter log
				Reporter.log("=========================================================================================================="
						+ "<br>");
				Reporter.log(" Screenshot for Testcase: " + this.customTestCaseName
						+ "<br>");
				Reporter.log("Failure URL: " + driver.getCurrentUrl()
						,true);
				Reporter.log("<a href= " +saved.getPath()
						+ ">Screenshots<br></a>" + "<br>",true);
				Reporter.log("=========================================================================================================="
						+ "<br>");
				
				// Below commented code will display the screenshot in ReportNG output log, Not as a hyperlink
				
				//Reporter.log("screenshot for "+this.customTestCaseName+" url="+driver.getCurrentUrl()+
						//"<div style=\"height:400px; width: 750px; overflow:scroll\"><img src=\""+saved.getPath()+"\"></div>", 
						//true);

			} catch (IOException e) {
				Reporter.log("Error generating screenshot for "
						+ this.customTestCaseName + ": " +e, true);
			}
		}	
		driver.close();
		driver.quit();
		driver = null;
	}

	/**
	 * Quits Browser.
	 */
	@AfterSuite
	public void quitBrowser() {
		if (getDriver() != null) {
			getDriver().quit();
			driver = null;
		}
		String browserCloseAll = ConfigurationProperties
				.get("browser.closeall");
		if (browserCloseAll.equalsIgnoreCase("true")) {
			Report.log("Test Suite execution completed and Closing all the open browser sessions");
			try {
                // To close all open browsers
				p = Runtime.getRuntime().exec(CLOSE_BROWSER);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Report.log("Exception while closing all the opened browser sessions :"
						+ e);
			}
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Report.log("Exception at close browser wait process:" + e);
			}
		}
	}

	/**
	 * This method gives the current date and time as string.
	 *
	 * @return String
	 */
	private String getCurrentDataTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		// get current date time with Date()
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public String getTestName() {
		String aReturnValue = null;
		if(testMethod !=null){
			if(this.customTestCaseName == null){
		        String[] testCaseIds = {};
		        Annotation annotation = testMethod.getAnnotation(TestName.class);
		        TestName ts = (TestName)annotation;
            	this.customTestCaseName = ts.testCaseName();
            	testCaseIds = ts.testCaseIds();
            	if(testCaseIds != null){
            		this.customTestCaseName += "[";
	            	for (String testCaseId : testCaseIds) {
	            		this.customTestCaseName += String.format(testCaseFormat, testCaseId);
	            	}
	            	this.customTestCaseName += "]";
            	}
		        aReturnValue = this.customTestCaseName;
			}
		}
		if(this.customTestCaseName == null){
			aReturnValue = "No method Name.";
		}else{
			aReturnValue = this.customTestCaseName;
		}
		return aReturnValue;		
	}
	
	/**
	 * Wait for load.
	 *
	 * @param driver the driver
	 */
	protected void waitForLoad(WebDriver driver) {
	    ExpectedCondition<Boolean> pageLoadCondition = new
	        ExpectedCondition<Boolean>() {
	            public Boolean apply(WebDriver driver) {
	                return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
	            }
	        };
	    WebDriverWait wait = new WebDriverWait(driver, 60);
	    wait.until(pageLoadCondition);
	}

}
