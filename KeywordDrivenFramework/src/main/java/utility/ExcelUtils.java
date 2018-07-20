package utility;

import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class ExcelUtils {
	private static XSSFWorkbook workbook;
	private static XSSFSheet sheet;
	private static XSSFRow row;
	private static XSSFCell cell;
	
	public static void setExcelFile(String pathExcelFile, String sheetName) throws Exception {
		FileInputStream inputStream = new FileInputStream(pathExcelFile);
		workbook = new XSSFWorkbook(inputStream);
		sheet = workbook.getSheet(sheetName);
	}
	
	public static String getCellData(int rowNum, int colNum) throws Exception {
		String cellData = null;
		row = sheet.getRow(rowNum);
		cellData = row.getCell(colNum).getStringCellValue();
		
		return cellData;
	}
}
