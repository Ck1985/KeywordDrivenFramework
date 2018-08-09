package com.freelance.automationTestFramework.projectKeywordDrivenFramework.config;

import java.util.concurrent.TimeUnit;
import java.lang.reflect.Method;
import java.util.function.Function;
import org.openqa.selenium.JavascriptExecutor;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.freelance.automationTestFramework.projectKeywordDrivenFramework.CheckOut;
import com.freelance.automationTestFramework.projectKeywordDrivenFramework.utility.*;

import com.freelance.automationTestFramework.projectKeywordDrivenFramework.utility.Log;

public class Action_Keywords {
	private static WebDriver driver;
	private static WebDriverWait wait;
	private static WebElement element;
	private static Method[] methods;
	private static long timeoutInSeconds = 10;
	
	public Action_Keywords() {
		methods = this.getClass().getDeclaredMethods();
	}
	
	public static Method[] getMethodArray() {
		return methods;
	}
	
	public static void setUpBeforeTest() throws Exception {
		DOMConfigurator.configure("log4j.xml");
		ExcelUtils.setExcelFile(Constants.PATH_EXCELFILE + "\\" + Constants.NAME_EXCELFILE);
		Utils.createOR();
		System.setProperty(Constants.CONFIG_CHROMEDRIVER, Constants.PATH_CHROMEDRIVER);
	}
	
	public static void openBrowser(String object, String data, String testCaseId) throws Exception {
		Log.info("Opening Browser");
		try {
			if (data.equalsIgnoreCase("Chrome")) {
				System.setProperty(Constants.CONFIG_CHROMEDRIVER, Constants.PATH_CHROMEDRIVER);
				driver = new ChromeDriver();
			} else if(data.equalsIgnoreCase("Firefox")) {
				System.setProperty(Constants.CONFIG_FIREFOXDRIVER, Constants.PATH_FIREFOXDRIVER);
				FirefoxOptions option = new FirefoxOptions();
				option.setBinary("C:\\Program Files\\Firefox Nightly\\firefox.exe");
				driver = new FirefoxDriver(option);
			} else if (data.equalsIgnoreCase("IE")) {
				System.setProperty(Constants.CONFIG_DRIVERIE, Constants.PATH_DRIVERIE);
				driver = new InternetExplorerDriver();
			}
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} catch (Exception ex) {
			Log.error("Not able to open Browser due Exception: " + ex.getMessage());
			throw (ex);
		}
	}
	
	public static void navigate(String object, String data, String testCaseId) throws Exception {
		try {
			driver.get(Constants.URL_WEBSITE);
			Log.info("Navigating to URL");
		} catch (Exception ex) {
			Log.error("Not able to navigate due Exception: " + ex.getMessage());
			throw (ex);
		}
	}
	
	public static void click(String object, String data, String testCaseId) throws Exception {
		try {
			element = driver.findElement(By.xpath(Utils.getRepository(object)));
			element.click();
			Log.info("Click on WebElement: " + object);
		} catch (Exception ex) {
			Log.error("Not able to click: " + object + " due Exception: " + ex.getMessage());
			throw (ex);
		}
	}
	
	public static void selectDropdown(String object, String data, String testCaseId) throws Exception {
		try {
			element = driver.findElement(By.xpath(Utils.getRepository(object)));
			Select select = new Select(element);
			select.selectByVisibleText(data);
		} catch (Exception ex) {
			throw (ex);
		}
	}
	
	public static void clear(String object, String data, String testCaseId) throws Exception {
		try {
			element = driver.findElement(By.xpath(Utils.getRepository(object)));
			element.clear();
		} catch (Exception ex) {
			throw (ex);
		}
	}
	
	public static void input(String object, String data, String testCaseId) throws Exception {
		try {
			element = driver.findElement(By.xpath(Utils.getRepository(object)));
			element.sendKeys(data);
			Log.info("Entering text into " + object);
		} catch (Exception ex) {
			Log.error("Not able to input " + object + " due Exception: " + ex.getMessage());
			throw (ex);
		}
	}
	
	public static void hover(String object, String data, String testCaseId) throws Exception {
		try {
			element = driver.findElement(By.xpath(Utils.getRepository(object)));
			Actions action = new Actions(driver);
			action.moveToElement(element).build().perform();
		} catch (Exception ex) {
			throw (ex);
		}
	}
	
	public static void scrollPageToElement(String object, String data, String testCaseId) throws Exception {
		try {			
			JavascriptExecutor jvs = (JavascriptExecutor)driver;
			jvs.executeScript("scroll(255,0)");
		} catch (Exception ex) {
			throw (ex);
		}
	}
	
	public static void waitFor(String object, String data, String testCaseId) throws Exception {
		try {
			wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Utils.getRepository(object))));
		} catch (Exception ex) {
			throw (ex);
		}
	}
	
	public static void waitForTextbox(String object, String data, String testCaseId) throws Exception {
		try {
			element = driver.findElement(By.xpath(Utils.getRepository(object)));
			wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception ex) {
			throw (ex);
		}
	}
	
	public static void untilJQueryIsDone(String object, String data, String testCaseId) throws Exception {
		try {
			until(driver, (d) -> 
			                     {
			                    	 Boolean isJQueryCallDone = (Boolean)((JavascriptExecutor)driver).executeScript("return jQuery.active==0");
			                    	 if (!isJQueryCallDone) {
			                    		 System.out.println("JQuery is processing ...");
			                    		 return isJQueryCallDone;
			                    	 } else {
			                    		 System.out.println("JQuery has just done !");
			                    		 return isJQueryCallDone;
			                    	 }
			                     }, timeoutInSeconds);
		} catch (Exception ex) {
			throw (ex);
		}
	}
	
	public static void closeBrowser(String object, String data, String testCaseId) throws Exception {
		try {
			Log.info("Closing Browser");
			driver.quit();
		} catch (Exception ex) {
			Log.error("Not able to Close Browser due Exception: " + ex.getMessage());
			throw (ex);
		}
	}
	
	@SuppressWarnings("deprecation")
	private static void until(WebDriver driver, Function<WebDriver, Boolean> waitCondition, long timeoutInSeconds) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, timeoutInSeconds);
		webDriverWait.withTimeout(timeoutInSeconds, TimeUnit.SECONDS);
		try {
			webDriverWait.until(waitCondition);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public static Boolean confirmProduct(String object, String data, String testCaseId) throws Exception {
		Boolean confirmResult = false;
		String expectedName = null,
			   productPrice = null,
			   actuallyName = null;
		try {
			expectedName = ExcelUtils.getExcelData(Constants.SHEET_TESTSTEPS, getRowProductNameExcel(testCaseId), Constants.INDEXCOL_DATASET_TESTSTEPS);
			actuallyName = driver.findElement(By.xpath(Utils.getRepository(object))).getAttribute("innerText");
			productPrice = driver.findElement(By.xpath("//*[@id='checkout_page_container']/div/table/tbody/tr[2]/td[4]/span")).getText();
			
			System.out.println(getRowProductNameExcel(testCaseId));
			System.out.println(expectedName);
			System.out.println(actuallyName);
			System.out.println(productPrice);
			
			if (expectedName.equals(actuallyName) && !(productPrice.equals(""))) {
				System.out.println("True");
				CheckOut.bResult = true;
				confirmResult = true;
			} else {
				System.out.println("False");
				CheckOut.bResult = false;
				confirmResult = false;
			}
			driver.findElement(By.xpath("//*[@id='account_logout']/a"));
			return confirmResult;
		} catch (Exception ex) {
			throw (ex);		
		}
	}
	
	public static Boolean verifyConfirmationPage(String object, String data, String testCaseId) throws Exception {
		Boolean verifyResult = false;
		String expectedName = null,
			   actuallyName = null,
			   productPrice = null;
		
		try {
			expectedName = ExcelUtils.getExcelData(Constants.SHEET_TESTSTEPS, getRowProductNameExcel(testCaseId), Constants.INDEXCOL_DATASET_TESTSTEPS);
			actuallyName = driver.findElement(By.xpath(Utils.getRepository(object))).getAttribute("innerText");
			productPrice = driver.findElement(By.xpath("//*[@class='wpsc-purchase-log-transaction-results']/tbody/tr[last()]/td[2]")).getText();
			
			System.out.println(getRowProductNameExcel(testCaseId));
			System.out.println(expectedName);
			System.out.println(actuallyName);
			System.out.println(productPrice);
			
			if (expectedName.equals(actuallyName) && !(productPrice.equals(""))) {
				System.out.println("True");
				CheckOut.bResult = true;
				verifyResult = true;
			} else {
				System.out.println("False");
				CheckOut.bResult = false;
				verifyResult = false;
			}
			driver.findElement(By.xpath("//*[@id='account_logout']/a"));
			return verifyResult;
		} catch (Exception ex) {
			throw (ex);
		}
	}
	
	private static int getRowProductNameExcel(String testCaseId) throws Exception {
		int indexStartTC = 0,
			cursorTC = 1,
			indexRowProductName = 0;
		try {
			while (!ExcelUtils.getExcelData(Constants.SHEET_TESTSTEPS, cursorTC, Constants.INDEXCOL_TESTCASEID_TESTSTEPS).equals(testCaseId)) {
				cursorTC++;
			}
			indexStartTC = cursorTC;
			indexRowProductName = indexStartTC + 9;
			
			return indexRowProductName;
		} catch (Exception ex) {
			throw (ex);
		}
	}
}
