package executionEngine;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import org.apache.log4j.xml.DOMConfigurator;

import utility.*;
import config.*;

public class DriverScript {
	public static Properties OR;
	public static Object actionKeywords;
	public static String sActionKeyword;
	public static String sPageObject;
	public static String sData;
	public static Method[] methods;
	
	public static int indexFirstStep;
	public static int indexLastSteps;
	public static int cursorTestCase;
	public static String sTestCaseID;
	public static String sRunMode;
	
	public static Boolean bResult = true;
	
	public DriverScript() throws NoSuchMethodException, SecurityException {
		actionKeywords = new ActionKeywords();
		methods = actionKeywords.getClass().getDeclaredMethods();
	}
	
	public static void main(String[] args) throws Exception {		
		DOMConfigurator.configure("log4j.xml");
		ExcelUtils.setExcelFile(Constants.PATH_DATAFILE + "\\" + Constants.NAME_DATAFILE);
		String path_OR = Constants.PATH_OR;
		FileInputStream inputStream = new FileInputStream(path_OR);
		OR = new Properties(System.getProperties());
		OR.load(inputStream);
		
		DriverScript startEngine = new DriverScript();
		startEngine.execute_TestCase();
	}
	
	private static void execute_Actions() throws Exception {
		for (Method method : methods) {
			if (method.getName().equals(sActionKeyword)) {
				method.invoke(actionKeywords, sPageObject, sData);
				if (bResult == true) {
					ExcelUtils.setCellData(Constants.KEYWORD_PASS, cursorTestCase, Constants.INDEXCOL_TESTSTEPS_RESULT, Constants.SHEET_TESTSTEPS);
					break;
				} else {
					ExcelUtils.setCellData(Constants.KEYWORD_FAIL, cursorTestCase, Constants.INDEXCOL_TESTSTEPS_RESULT, Constants.SHEET_TESTSTEPS);
					ActionKeywords.closeBrowser(sPageObject, sData);
					break;
				}
			}
		}
	}
	
	private void execute_TestCase() throws Exception {
		int totalTestCases = ExcelUtils.getRowCount(Constants.SHEET_TESTCASES) - 1;
		String sTestCaseID = null;
		String sRunMode = null;
		int indexTestStepsTemp = 1;
		for (int indexTestCase = 1; indexTestCase <= totalTestCases; indexTestCase++) {
			cursorTestCase = 0;
			bResult = true;
			sTestCaseID = ExcelUtils.getCellData(indexTestCase, Constants.INDEXCOL_TESTCASE_ID, Constants.SHEET_TESTCASES);
			sRunMode = ExcelUtils.getCellData(indexTestCase, Constants.INDEXCOL_RUNMODE, Constants.SHEET_TESTCASES);
			
			if (sRunMode.equalsIgnoreCase("Yes")) {
				Log.startTestCase(sTestCaseID);
				bResult = true;
				while (indexTestStepsTemp < ExcelUtils.getRowCount(Constants.SHEET_TESTSTEPS)) {
					if (ExcelUtils.getCellData(indexTestStepsTemp, Constants.INDEXCOL_TESTCASE_ID, Constants.SHEET_TESTSTEPS).equalsIgnoreCase(sTestCaseID)) {
						indexFirstStep = indexTestStepsTemp;
						break;
					} 
					indexTestStepsTemp++;
				}
				indexLastSteps = ExcelUtils.getTestStepsCount(Constants.SHEET_TESTSTEPS, sTestCaseID, indexFirstStep) + indexFirstStep - 1;
				for (cursorTestCase = indexFirstStep; cursorTestCase <= indexLastSteps; cursorTestCase++) {
					sActionKeyword = ExcelUtils.getCellData(cursorTestCase, Constants.INDEXCOL_ACTIONKEYWORD, Constants.SHEET_TESTSTEPS);
					sPageObject = ExcelUtils.getCellData(cursorTestCase, Constants.INDEXCOL_PAGEOBJECTS, Constants.SHEET_TESTSTEPS);
					sData = ExcelUtils.getCellData(cursorTestCase, Constants.INDEXCOL_DATASET, Constants.SHEET_TESTSTEPS);
					execute_Actions();
					if (bResult == false) {
						ExcelUtils.setCellData(Constants.KEYWORD_FAIL, indexTestCase, Constants.INDEXCOL_TESTCASE_RESULT, Constants.SHEET_TESTCASES);
						Log.endTestCase(sTestCaseID);
						break;
					}
				}
				if (bResult == true) {
					ExcelUtils.setCellData(Constants.KEYWORD_PASS, indexTestCase, Constants.INDEXCOL_TESTCASE_RESULT, Constants.SHEET_TESTCASES);
					Log.endTestCase(sTestCaseID);
				}
			} 
		}
	}
}
