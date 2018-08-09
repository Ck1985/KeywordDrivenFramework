package com.freelance.automationTestFramework.projectKeywordDrivenFramework;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import com.freelance.automationTestFramework.projectKeywordDrivenFramework.config.Action_Keywords;
import com.freelance.automationTestFramework.projectKeywordDrivenFramework.utility.Utils;

public class VerifyConfirmationPage {
	@BeforeClass
	public void beforeClass() throws Exception {
		Action_Keywords.setUpBeforeTest();
	}
	@Test
	public void run_VerifyConfirmationPage_Testcase() throws Exception {
		Utils.run_Each_TestCase("VerifyConfirmationPage_01");
	}
	@AfterClass
	public void afterClass() throws Exception {
		Action_Keywords.closeBrowser("", "", "");
	}
}
