package config;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;

import static executionEngine.DriverScript.OR;

public class ActionKeywords {
	public static WebDriver driver;
	
	public static void openBrowser(String object) {
		driver = new ChromeDriver();
	}
	
	public static void navigate(String object) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(Constants.URL);
	}
	
	/*public static void click_MyAccount() {
		driver.findElement(By.xpath("//*[@id='account']/a")).click();
	}*/
	
	public static void click(String object) throws Exception {
		driver.findElement(By.xpath(OR.getProperty(object))).click();
	}
	
	public static void input_Username(String object) {
		driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Constants.USERNAME);
	}
	
	public static void input_Password(String object) {
		driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Constants.PASSWORD);
	}
	
	/*public static void click_Login() {
		driver.findElement(By.id("login")).click();
	}*/
	
	public static void waitFor(String object) throws Exception {
		Thread.sleep(3000);
	}
	
	/*public static void click_Logout() {
		driver.findElement(By.xpath("//*[@id='account_logout']/a")).click();
	}*/
	
	public static void closeBrowser(String object) {
		driver.quit();
	}
}
