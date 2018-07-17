package executionEngine;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverScript {
	private static WebDriver driver = null;
	
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\anony\\git\\KeywordDrivenFramework\\src\\main\\java\\Configurator\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://www.store.demoqa.com");
		
		driver.findElement(By.xpath("//*[@id='account']/a")).click();
		driver.findElement(By.id("log")).sendKeys("anonymous.vn1985@gmail.com");
		driver.findElement(By.id("pwd")).sendKeys("11100001");
		driver.findElement(By.id("login")).click();
		driver.findElement(By.xpath("//*[@id='account_logout']/a")).click();
		driver.quit();
	}
}
