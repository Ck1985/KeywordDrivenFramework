package com.freelance.automationTestFramework.projectKeywordDrivenFramework.utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
			Log.error("Class ExcelUtils || Method setExcelFile || Excception des: " + ex.getMessage());
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
			
			if (sData == null) {
				return "";
			} else {
				return sData;
			}
		} catch (Exception ex) {
			Log.error("Class ExcelUtils || Method getExcelData || Exception des: " + ex.getMessage());
			throw (ex);
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
	
	@SuppressWarnings("static-access")
	public static void setExcelData(String sheetName, int indexRow, int indexCol, String result) throws Exception {
		try {
			FileOutputStream outputStream = new FileOutputStream(Constants.PATH_EXCELFILE + "\\" + Constants.NAME_EXCELFILE);
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(indexRow);
			cell = row.getCell(indexCol, row.RETURN_BLANK_AS_NULL);
			if (cell == null) {
				cell = row.createCell(indexCol);
				cell.setCellValue(result);
			} else {
				cell.setCellValue(result);
			}
			workbook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (Exception ex) {
			Log.error("Class ExcelUtils || Method setExcelData || Exception des: " + ex.getMessage());
			throw (ex);
		}
	}
}
