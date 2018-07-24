package utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;

import config.*;
import executionEngine.DriverScript;

public class ExcelUtils {
	private static XSSFWorkbook workbook;
	private static XSSFSheet sheet;
	private static XSSFRow row;
	private static XSSFCell cell;
	private static int totalSteps;
	
	public static void setExcelFile(String pathExcelFile) throws Exception {
		try {
			FileInputStream inputStream = new FileInputStream(pathExcelFile);
			workbook = new XSSFWorkbook(inputStream);
		} catch (Exception ex) {
			Log.error("Class ExcelUtils || Method setExcelFile || Excception des: " + ex.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static String getCellData(int rowNum, int colNum, String sheetName) throws Exception {
		try {
			sheet = workbook.getSheet(sheetName);
			cell = sheet.getRow(rowNum).getCell(colNum);
			String cellData = cell.getStringCellValue();			
			return cellData;
		} catch (Exception ex) {
			Log.error("Class ExcelUtils || Method getCellData || Exception des: " + ex.getMessage());
			DriverScript.bResult = false;
			return "";
		}
	}
	
	@SuppressWarnings("static-access")
	public static void setCellData(String result, int rowNum, int colNum, String sheetName) throws Exception {
		try {
			FileOutputStream outputStream = new FileOutputStream(new File(Constants.PATH_DATAFILE + "\\" + Constants.NAME_DATAFILE));
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(rowNum);
			cell = row.getCell(colNum, row.RETURN_BLANK_AS_NULL);
			if (cell == null) {
				cell = row.createCell(colNum);
				cell.setCellValue(result);
			} else {
				cell.setCellValue(result);
			}
			workbook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (Exception ex) {
			Log.error("Class ExcelUtils || Method setCellData || Exception des: " + ex.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static int getRowCount(String sheetName) throws Exception {
		int rowCount = 0;
		try {
			rowCount = workbook.getSheet(sheetName).getLastRowNum() + 1;
		} catch (Exception ex) {
			Log.error("Class ExcelUtils || Method getRowCount || Exception des: " + ex.getMessage());
			DriverScript.bResult = false;
		}
		return rowCount;
	}
	
	public static int getRowTestCase(String sTestCaseName, int colNum, String sheetName) throws Exception {
		int iRow = 0;
		try {
			sheet = workbook.getSheet(sheetName);
			int rowCount = getRowCount(sheetName);
			String cellData = null;
			for (iRow = 1; iRow < rowCount; iRow++) {
				cellData = getCellData(iRow, colNum, sheetName);
				if (cellData.equalsIgnoreCase(sTestCaseName)) {
					break;
				}
			}
		} catch (Exception ex) {
			Log.error("Class ExcelUtils || Method getRowTestCase || Exception des: " + ex.getMessage());
			DriverScript.bResult = false;
		}
		
		return iRow;
	}
	
	public static int getTestStepsCount(String sheetName, String sTestCaseID, int iTestCaseStart) throws Exception {
		totalSteps = 0;
		try {
			for (int i = iTestCaseStart; i < getRowCount(sheetName); i++) {
				if (!getCellData(i, Constants.INDEXCOL_TESTCASE_ID, sheetName).equalsIgnoreCase(sTestCaseID)) {
					return totalSteps;
				} else {
					totalSteps++;
				}
			}
		} catch (Exception ex) {
			Log.error("Class ExcelUtils || Method getTestStepsCount || Exception des: " + ex.getMessage());
			DriverScript.bResult = false;
		}
		return totalSteps;
	}
}
