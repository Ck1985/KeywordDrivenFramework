package config;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;

import executionEngine.DriverScript;

import org.openqa.selenium.WebElement;

import static executionEngine.DriverScript.OR;

import utility.Log;

public class ActionKeywords {
	public static WebDriver driver;
	
	public static void openBrowser(String object, String data) throws Exception {
		Log.info("Opening Browser");
		try {
			if (data.equalsIgnoreCase("Chrome")) {
				System.setProperty(Constants.CONFIGDRIVERCHROME, Constants.PATHDRIVERCHROME);
				driver = new ChromeDriver();
			} else if(data.equalsIgnoreCase("Firefox")) {
				System.setProperty(Constants.CONFIGDRIVERGECKO, Constants.PATHDRIVERGECKO);
				FirefoxOptions option = new FirefoxOptions();
				option.setBinary("C:\\Program Files\\Firefox Nightly\\firefox.exe");
				driver = new FirefoxDriver(option);
			} else if (data.equalsIgnoreCase("IE")) {
				System.setProperty(Constants.CONFIGDRIVERIE, Constants.PATHDRIVERIE);
				driver = new InternetExplorerDriver();
			}
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} catch (Exception ex) {
			Log.error("Not able to open Browser due Exception: " + ex.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void navigate(String object, String data) throws Exception {
		try {
			Log.info("Navigating to URL");
			driver.get(Constants.URL);
		} catch (Exception ex) {
			Log.error("Not able to navigate due Exception: " + ex.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void click(String object, String data) throws Exception {
		try {
			Log.info("Click on WebElement: " + object);
			driver.findElement(By.xpath(OR.getProperty(object))).click();
		} catch (Exception ex) {
			Log.error("Not able to click: " + object + " due Exception: " + ex.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void input(String object, String data) throws Exception {
		try {
			Log.info("Entering text into " + object);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
		} catch (Exception ex) {
			Log.error("Not able to input " + object + " due Exception: " + ex.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void waitFor(String object, String data) throws Exception {
		try {
			Log.info("Waiting for 3 seconds");
			Thread.sleep(3000);
		} catch (Exception ex) {
			Log.error("Not able to wait for due Exception: " + ex.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void closeBrowser(String object, String data) throws Exception {
		try {
			Log.info("Closing Browser");
			driver.quit();
		} catch (Exception ex) {
			Log.error("Not able to Close Browser due Exception: " + ex.getMessage());
			DriverScript.bResult = false;
		}
	}
}
