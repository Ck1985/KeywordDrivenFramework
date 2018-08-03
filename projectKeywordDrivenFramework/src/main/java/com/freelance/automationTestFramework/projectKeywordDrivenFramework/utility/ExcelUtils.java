package com.freelance.automationTestFramework.projectKeywordDrivenFramework.utility;

import java.io.FileInputStream;
import java.io.IOException;

import com.freelance.automationTestFramework.projectKeywordDrivenFramework.config.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class ExcelUtils {
	private static XSSFWorkbook workbook;
	private static XSSFSheet sheet;
	private static XSSFRow row;
	private static XSSFCell cell;
	
	public static void setExcelFile(String pathExcelFile) throws Exception, IOException {
		try {
			FileInputStream inputStream = new FileInputStream(Constants.PATH_EXCELFILE + "\\" + Constants.NAME_EXCELFILE);
			workbook = new XSSFWorkbook(inputStream);
		} catch (Exception ex) {
			throw (ex);
		}
	}
	
	public static String getExcelData(String sheetName, int rowIndex, int colIndex) throws Exception, IOException {
		String sData = null;
		try {
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(rowIndex);
			cell = row.getCell(colIndex);
			sData = cell.getStringCellValue();
			
			return sData;
		} catch (Exception ex) {
			return "";
		}
	}
	
	public static int getRowNumSheet(String sheetName) throws Exception {
		int rowNum = 0;
		try {
			sheet = workbook.getSheet(sheetName);
			rowNum = sheet.getLastRowNum();
			return rowNum;
		} catch (Exception ex) {
			throw (ex);
		}
	}
	
	public static void setExcelData() throws Exception {
		
	}
}
