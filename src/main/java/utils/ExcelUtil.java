package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ExcelUtil {

	public static boolean writeXLSX(String filePath, Map<Integer, Object[]> data, int sheetNum) {
		try {
			File myFile = new File(filePath);
			FileInputStream fis = new FileInputStream(myFile);

			XSSFWorkbook workBook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workBook.getSheetAt(sheetNum);

			Set<Integer> keySet = data.keySet();
			int rownum = 0;
			for (Integer key : keySet) {
				Row row = sheet.createRow(rownum++);
				Object[] objArr = data.get(key);
				int cellnum = 0;
				for (Object obj : objArr) {
					Cell cell = row.createCell(cellnum++);
					if (obj instanceof Integer) {
						cell.setCellValue((Integer) obj);
					} else if (obj instanceof String) {
						cell.setCellValue((String) obj);
					} else if (obj instanceof Boolean) {
						cell.setCellValue((Boolean) obj);
					} else if (obj instanceof Date) {
						cell.setCellValue((Date) obj);
					} else if (obj instanceof Double) {
						cell.setCellValue((Double) obj);
					}
				}
			}
			fis.close();

			FileOutputStream out = new FileOutputStream(new File(filePath));
			workBook.write(out);
			workBook.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String readXLSX(String filePath, int sheetNum) {
		XSSFWorkbook workBook = null;
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode arrayNode = mapper.createArrayNode();

		try {
			File myFile = new File(filePath);
			FileInputStream fis = new FileInputStream(myFile);

			workBook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workBook.getSheetAt(sheetNum);

			Row firstRow = sheet.getRow(0);

			for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
				ObjectNode node = mapper.createObjectNode();
				Row row = sheet.getRow(i);

				for (int j = 1; j < firstRow.getLastCellNum(); j++) {
					Cell cellFirstRow = firstRow.getCell(j);
					Cell cell = row.getCell(j);

					if (cell != null) {
						cell.setCellType(CellType.STRING);
						node.put(cellFirstRow.getStringCellValue(), cell.getStringCellValue());
					} else {
						node.put(cellFirstRow.getStringCellValue(), "");
					}
				}
				arrayNode.add(node);
			}

			return arrayNode.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			try {
				workBook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
