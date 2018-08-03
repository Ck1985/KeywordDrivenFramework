package com.freelance.automationTestFramework.projectKeywordDrivenFramework.config;

import java.util.concurrent.TimeUnit;
import java.lang.reflect.Method;
import java.util.function.Function;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;

import com.freelance.automationTestFramework.projectKeywordDrivenFramework.CheckOut;
import com.freelance.automationTestFramework.projectKeywordDrivenFramework.utility.*;

public class Action_Keywords {
	private static WebDriver driver;
	private static Method[] methods;
	private static long timeoutInSeconds = 10;
	
	public Action_Keywords() throws Exception {
		methods = this.getClass().getDeclaredMethods();
	}
	
	public static Method[] getMethodArray() {
		return methods;
	}
	
	public static void setUpBeforeTest() throws Exception {
		ExcelUtils.setExcelFile(Constants.PATH_EXCELFILE + "\\" + Constants.NAME_EXCELFILE);
		Utils.createOR();
		System.setProperty(Constants.CONFIG_CHROMEDRIVER, Constants.PATH_CHROMEDRIVER);
	}
	
	public static void openBrowser(String testCaseId, String object, String data) throws Exception {
		try {
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} catch (Exception ex) {
			throw (ex);
		}
	}
	
	public static void navigate(String object, String data, String testCaseId) throws Exception {
		try {
			driver.get(Constants.URL_WEBSITE);
		} catch (Exception ex) {
			throw (ex);
		}
	}
	
	public static void click(String object, String data, String testCaseId) throws Exception {
		try {
			WebElement element = driver.findElement(By.xpath(Utils.getRepository(object)));
			element.click();
		} catch (Exception ex) {
			throw (ex);
		}
	}
	
	public static void input(String object, String data, String testCaseId) throws Exception {
		try {
			WebElement element = driver.findElement(By.xpath(Utils.getRepository(object)));
			element.sendKeys(data);
		} catch (Exception ex) {
			throw (ex);
		}
	}
	
	public static void hover(String object, String data, String testCaseId) throws Exception {
		try {
			WebElement element = driver.findElement(By.xpath(Utils.getRepository(object)));
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
		WebDriverWait wait = null;
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Utils.getRepositoryLogoutBtn())));
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
			driver.quit();
		} catch (Exception ex) {
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
		String expectedName = null,
			   productPrice = null,
			   actuallyName = null;
		try {
			expectedName = ExcelUtils.getExcelData(Constants.SHEET_TESTSTEPS, getRowProductNameExcel(testCaseId), 6);
			actuallyName = driver.findElement(By.xpath(Utils.getRepository(object))).getAttribute("innerText");
			productPrice = driver.findElement(By.xpath("//*[@id='checkout_page_container']/div/table/tbody/tr[2]/td[4]/span")).getAttribute("textContext");
			
			//System.out.println(getRowProductNameExcel(testCaseId));
			System.out.println(expectedName);
			//System.out.println(actuallyName);
			
			if (expectedName.equals(actuallyName) && !(productPrice.equals(""))) {
				System.out.println("True");
				CheckOut.bResult = true;
				return true;
			} else {
				System.out.println("False");
				CheckOut.bResult = false;
				return false;
			}
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
			indexRowProductName = indexStartTC + 10;
			
			return indexRowProductName;
		} catch (Exception ex) {
			throw (ex);
		}
	}
}
