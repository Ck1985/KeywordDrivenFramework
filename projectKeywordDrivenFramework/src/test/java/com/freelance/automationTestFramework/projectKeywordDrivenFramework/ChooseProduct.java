package com.freelance.automationTestFramework.projectKeywordDrivenFramework;

import com.freelance.automationTestFramework.projectKeywordDrivenFramework.config.*;
import com.freelance.automationTestFramework.projectKeywordDrivenFramework.utility.*;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

public class ChooseProduct {
	@BeforeClass
	public void beforeClass() throws Exception {
		Action_Keywords.setUpBeforeTest();
	}
	@Test
	public void run_ChooseProduct_TestCase() throws Exception {				
		Utils.run_Each_TestCase("ChooseProduct_01");
		//Utils.run_AllTestCase();
	}
	@AfterClass
	public void afterClass() throws Exception {
		Action_Keywords.closeBrowser("","","");
	}
}
