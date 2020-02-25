package test;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import entities.Depart;
import utils.ExcelUtil;
import utils.TimeUtil;

public class DepartTest extends TestConfig {

	List<Depart> list;

	String depart_url = "http://localhost:8080/SOF302_Assignment_PS08445-1.0/admin/depart";
	int stt=1;

	@Test(priority = 1)
	public void insertDepartTest() {
		readDataFromExcel(1);
		for (int i = 0; i < list.size(); i++) {
			String testData = list.get(i).toString();
			System.out.println(testData);
			Depart depart = list.get(i);
			try {
				fillForm(depart);
				if (doInsert()) {
					testNGResult.put(stt+1, new Object[] { stt, testData, depart.getExpectOutput(), "Thêm thành công", TimeUtil.getCurrentTime()});
				} else {
					throw new Exception();
				}				
			} catch (Exception e) {
				testNGResult.put(stt+1, new Object[] { stt, testData, depart.getExpectOutput(), "Thêm thất bại", TimeUtil.getCurrentTime()});
			} finally {
				try {
					clickOK();
					doReset();
					stt++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Test(priority = 2)
	public void updateDepartTest() {
		readDataFromExcel(2);
		for (int i = 0; i < list.size(); i++) {
			String testData = list.get(i).toString();
			System.out.println(testData);
			Depart depart = list.get(i);
			try {
				chooseDepart(1, depart.getId());
				fillForm(depart);
				if (doUpdate()) {
					testNGResult.put(stt+1, new Object[] { stt, testData, depart.getExpectOutput(),
							"Update thành công", TimeUtil.getCurrentTime() });
				} else {
					throw new Exception();
				}
			} catch (Exception e) {
				testNGResult.put(stt+1, new Object[] { stt, testData, depart.getExpectOutput(), "Update thất bại", TimeUtil.getCurrentTime() });
			} finally {
				try {
					clickOK();
					doReset();
					stt++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Test(priority = 3)
	public void deleteDepartTest() {
		readDataFromExcel(3);
		for (int i = 0; i < list.size(); i++) {
			String testData = list.get(i).toString();
			System.out.println(testData);
			Depart depart = list.get(i);
			try {
				chooseDepart(2, depart.getId());
				if (doDelete()) {
					testNGResult.put(stt+1, new Object[] { stt, testData, depart.getExpectOutput(),
							"Xóa thành công", TimeUtil.getCurrentTime() });
				} else {
					throw new Exception();
				}
			} catch (Exception e) {
				testNGResult.put(stt+1, new Object[] { stt, testData, depart.getExpectOutput(), "Xóa thất bại",
						TimeUtil.getCurrentTime() });
			} finally {
				try {
					clickOK();
					Thread.sleep(1000);
					stt++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void readDataFromExcel(int sheetNum) {
		try {
			list = Arrays.asList(
					mapper.readValue(ExcelUtil.readXLSX(workingDir + "\\TestData.xlsx", sheetNum), Depart[].class));
		} catch (IOException e) {
			System.out.println("get data from excel fail");
		}
		System.out.println("list.size: " + list.size());
	}

	public void fillForm(Depart depart) throws Exception {
		// send data to username field
		WebElement code = driver.findElement(uimap.getLocator("depart_code_field"));
		code.clear();
		code.sendKeys(depart.getCode());

		// send data to password field
		WebElement name = driver.findElement(uimap.getLocator("depart_name_field"));
		name.clear();
		name.sendKeys(depart.getName());
	}

	public void chooseDepart(int type, int departId) {
		switch (type) {
		case 1:
			// update
			try {
				List<WebElement> elements = driver.findElements(uimap.getLocator("table_tr"));
				for (WebElement element : elements) {
					if (Integer.parseInt(
							element.findElement(uimap.getLocator("tr_checkBox")).getAttribute("value")) == departId) {
						element.click();
					}
				}
			} catch (Exception e) {
				System.out.println("choose depart not work correctly");
			}
			break;
		case 2:
			// delete
			try {
				List<WebElement> elements = driver.findElements(uimap.getLocator("table_tr"));
				for (WebElement element : elements) {
					if (Integer.parseInt(
							element.findElement(uimap.getLocator("tr_checkBox")).getAttribute("value")) == departId) {
						element.findElement(uimap.getLocator("tr_checkBox")).click();
					}
				}
			} catch (Exception e) {
				System.out.println("choose depart not work correctly");
			}
			break;
		default:
			break;
		}

	}

	@BeforeClass
	public void setUp() {
		try {
			login();
			driver.get(depart_url);
		} catch (Exception e) {
			throw new IllegalStateException("Can't start the Chrome web driver", e);
		}
		testNGResult = new LinkedHashMap<>();
		testNGResult.put(1,
				new Object[] { "STT", "Dữ liệu test", "Kết quả mong đợi", "Kết quả thực tế", "Thời gian test" });
	}

	@AfterClass
	public void tearDown(){
		try {
			ExcelUtil.writeXLSX(workingDir + "//TestResult.xlsx", testNGResult, 1);
			System.out.println("Ghi excel thanh cong");
			Desktop.getDesktop().open(new File(workingDir + "//TestResult.xlsx"));
			driver.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Ghi excel that bai");
		}
	}

}
