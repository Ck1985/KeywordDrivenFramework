package com.freelance.automationTestFramework.projectKeywordDrivenFramework;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.freelance.automationTestFramework.projectKeywordDrivenFramework.config.Action_Keywords;
import com.freelance.automationTestFramework.projectKeywordDrivenFramework.utility.Utils;

public class All_TestCase {
	@BeforeClass
	public void beforeClass() throws Exception {
		Action_Keywords.setUpBeforeTest();
	}
	@Test
	public void run_All_Testcase() throws Exception {
		Utils.run_AllTestCase();
	}
	@AfterClass
	public void afterClass() throws Exception {
		Action_Keywords.closeBrowser("", "", "");
	}
}
