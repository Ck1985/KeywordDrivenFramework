package executionEngine;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import utility.*;
import config.*;

public class DriverScript {
	//private static WebDriver driver = null;
	public static Properties OR;
	public static Object actionKeywords;
	public static String sActionKeyword;
	public static String sPageObject;
	public static Method[] methods;
	
	public DriverScript() throws NoSuchMethodException, SecurityException {
		actionKeywords = new ActionKeywords();
		methods = actionKeywords.getClass().getDeclaredMethods();
	}
	
	public static void main(String[] args) throws Exception {
		/*DriverScript ds = new DriverScript();
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
		
		String pathExcelFile = "C:\\Users\\anony\\git\\KeywordDrivenFramework\\src\\main\\java\\dataEngine\\DataEngine.xlsx";
		ExcelUtils.setExcelFile(pathExcelFile, "Test Steps");
		
		String actionKeyword = null;
		for (int iRow = 1; iRow <= 9; iRow++) {
			actionKeyword = ExcelUtils.getCellData(iRow, 3);
			if (actionKeyword.equals("openBrowser")) {
				ActionKeywords.openBrowser();
			} else if (actionKeyword.equals("navigate")) {
				ActionKeywords.navigate();
			} else if (actionKeyword.equals("click_MyAccount")) {
				ActionKeywords.click_MyAccount();
			} else if (actionKeyword.equals("input_Username")) {
				ActionKeywords.input_Username();
			} else if (actionKeyword.equals("input_Password")) {
				ActionKeywords.input_Password();
			} else if (actionKeyword.equals("click_Login")) {
				ActionKeywords.click_Login();
			} else if (actionKeyword.equals("waitFor")) {
				ActionKeywords.waitFor();
			} else if (actionKeyword.equals("click_Logout")) {
				ActionKeywords.click_Logout();
			} else {
				ActionKeywords.closeBrowser();
			}
		}*/
				
		DriverScript ds = new DriverScript();
		System.setProperty(Constants.CONFIGDRIVERCHROME, Constants.PATHDRIVERCHROME);
		ExcelUtils.setExcelFile(Constants.PATH_DATAFILE + "\\" + Constants.NAME_DATAFILE, Constants.SHEETNAME);
		String path_OR = Constants.PATH_OR;
		FileInputStream inputStream = new FileInputStream(path_OR);
		OR = new Properties(System.getProperties());
		OR.load(inputStream);
		
		for (int iRow = 1; iRow <= 9; iRow++) {
			sActionKeyword = ExcelUtils.getCellData(iRow, Constants.INDEXCOL_ACTIONKEYWORD);
			sPageObject = ExcelUtils.getCellData(iRow, Constants.INDEXCOL_PAGEOBJECTS);
			execute_Actions();
		}
	}
	
	private static void execute_Actions() throws Exception {
		for (Method method : methods) {
			if (method.getName().equals(sActionKeyword)) {
				method.invoke(actionKeywords, sPageObject);
				break;
			}
		}
	}
}
