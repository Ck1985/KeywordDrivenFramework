package com.freelance.automationTestFramework.projectKeywordDrivenFramework.utility;

import  com.freelance.automationTestFramework.projectKeywordDrivenFramework.config.*;

import java.io.FileInputStream;
import java.util.Properties;
import java.lang.reflect.Method;

public class Utils {
	private static Properties OR;
	private static Method[] methods;
	private static String sPageObject;
	private static String sData;
	private static String sActionKeyword;
	private static int cursorSheet = 1;
	
	public static Properties createOR() throws Exception {
		try {
			FileInputStream inputStream = new FileInputStream(Constants.PATH_REPOSITORY);
			OR = new Properties();
			OR.load(inputStream);
			
			return OR;
		} catch (Exception ex) {
			throw (ex);
		}
	}
	
	public static String getRepository(String keyProperty) throws Exception {
		String or = null;
		try {
			or = OR.getProperty(keyProperty);
		} catch (Exception ex) {
			throw (ex);
		}
		
		return or;
	}
	
	public static String getRepositoryMyAccount() throws Exception {
		String OR_MyAccount = null;
		try {
			OR_MyAccount = OR.getProperty("btn_MyAccount");
		} catch (Exception ex) {
			throw (ex);
		}
		
		return OR_MyAccount;
	}
	
	public static String getRepositoryUsername() throws Exception {
		String OR_Username = null;
		try {
			OR_Username = OR.getProperty("txtbx_Username");
		} catch (Exception ex) {
			throw (ex);
		}
		
		return OR_Username;
	}
	
	public static String getRepositoryPassword() throws Exception {
		String OR_Password = null;
		try {
			OR_Password = OR.getProperty("txtbx_Password");
		} catch (Exception ex) {
			throw (ex);
		}
		
		return OR_Password;
	}
	
	public static String getRepositoryLoginBtn() throws Exception {
		String OR_LoginBtn = null;
		try {
			OR_LoginBtn = OR.getProperty("btn_Login");
		} catch (Exception ex) {
			throw (ex);
		}
		
		return OR_LoginBtn;
	}

	public static String getRepositoryLogoutBtn() throws Exception {
		String OR_LogoutBtn = null;
		try {
			OR_LogoutBtn = OR.getProperty("btn_Logout");
		} catch (Exception ex) {
			throw (ex);
		}
		
		return OR_LogoutBtn;
	}

	private static void execute_Actions(String sheetName, int rowIndex) throws Exception {
		String methodName = null;
		String testCaseId = null;
		Action_Keywords actionKeyword = new Action_Keywords();
		methods = Action_Keywords.getMethodArray();
		try {
			for (Method method : methods) {
				methodName = method.getName();
				sActionKeyword = ExcelUtils.getExcelData(sheetName, rowIndex, Constants.INDEXCOL_ACTIONKEYWORDS_TESTSTEPS);
				sPageObject = ExcelUtils.getExcelData(sheetName, rowIndex, Constants.INDEXCOL_PAGEOBJECTS_TESTSTEPS);
				sData = ExcelUtils.getExcelData(sheetName, rowIndex, Constants.INDEXCOL_DATASET_TESTSTEPS);
				testCaseId = ExcelUtils.getExcelData(sheetName, rowIndex, Constants.INDEXCOL_TESTCASEID_TESTSTEPS);
				if (methodName.equalsIgnoreCase(sActionKeyword)) {
					method.invoke(actionKeyword,sPageObject, sData, testCaseId);
					break;
				}
			}
		} catch (Exception ex) {
			throw (ex);
		}
	}
	
	public static void execute_TestCase(String sheetName, int rowIndex) throws Exception {
		String nameTCSheet_2 = null,
			   nameTCSheet_1 = null;
		int indexStartTC = cursorSheet,
			cursorTC = cursorSheet;
		try {
			nameTCSheet_2 = ExcelUtils.getExcelData(Constants.SHEET_TESTCASES, rowIndex, Constants.INDEXCOL_TESTCASEID_TESTCASES);
			nameTCSheet_1 = ExcelUtils.getExcelData(Constants.SHEET_TESTSTEPS, cursorSheet, Constants.INDEXCOL_TESTCASEID_TESTSTEPS);
			while (!nameTCSheet_1.equalsIgnoreCase(nameTCSheet_2)) {
				cursorSheet++;
				nameTCSheet_1 = ExcelUtils.getExcelData(Constants.SHEET_TESTSTEPS, cursorSheet, Constants.INDEXCOL_TESTCASEID_TESTSTEPS);
			}
			while (nameTCSheet_1.equalsIgnoreCase(nameTCSheet_2)) {
				execute_Actions(Constants.SHEET_TESTSTEPS, cursorSheet);
				cursorSheet++;
				nameTCSheet_1 = ExcelUtils.getExcelData(Constants.SHEET_TESTSTEPS, cursorSheet, Constants.INDEXCOL_TESTCASEID_TESTSTEPS);				
			}
		} catch (Exception ex) {
			throw (ex);
		}
	}
	
	public static void run_Each_TestCase(String testCaseName) throws Exception {
		try {
			int numRowTestCase = 0;
			String nameTC = "";
			String runModeTC = null;
			
			numRowTestCase = ExcelUtils.getRowNumSheet(Constants.SHEET_TESTCASES);
			for (int iRowSheet_2 = 1; iRowSheet_2 <= numRowTestCase; iRowSheet_2++) {
				runModeTC = ExcelUtils.getExcelData(Constants.SHEET_TESTCASES, iRowSheet_2, Constants.INDEXCOL_RUNMODE_TESTCASES);
				nameTC = ExcelUtils.getExcelData(Constants.SHEET_TESTCASES, iRowSheet_2, Constants.INDEXCOL_TESTCASEID_TESTCASES);
				if (runModeTC.equals("Yes") && nameTC.equals(testCaseName)) {
					Utils.execute_TestCase(Constants.SHEET_TESTSTEPS, iRowSheet_2);
					break;
				}
			}
		} catch (Exception ex) {
			throw (ex);
		}
	}
	
	public static void run_AllTestCase() throws Exception {
		try {
			int numRowTestCase = 0;
			String nameTC = "";
			String runModeTC = null;
			
			numRowTestCase = ExcelUtils.getRowNumSheet(Constants.SHEET_TESTCASES);
			for (int iRowSheet_2 = 1; iRowSheet_2 <= numRowTestCase; iRowSheet_2++) {
				runModeTC = ExcelUtils.getExcelData(Constants.SHEET_TESTCASES, iRowSheet_2, Constants.INDEXCOL_RUNMODE_TESTCASES);
				nameTC = ExcelUtils.getExcelData(Constants.SHEET_TESTCASES, iRowSheet_2, Constants.INDEXCOL_TESTCASEID_TESTCASES);
				if (runModeTC.equals("Yes")) {
					Utils.execute_TestCase(Constants.SHEET_TESTSTEPS, iRowSheet_2);
				}
			}
		} catch (Exception ex) {
			throw (ex);
		}
	}
}
