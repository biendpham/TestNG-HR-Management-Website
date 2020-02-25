package test;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import entities.Staff;
import utils.ExcelUtil;
import utils.TimeUtil;

public class StaffTest extends TestConfig{
	Map<Integer, Object[]> testNGResult;
	List<Staff> list;

	String staff_url = "http://localhost:8080/SOF302_Assignment_PS08445-1.0/admin/staff";
	int stt=1;
	
	@Test(priority = 1)
	public void insertStaffTest() {
		readDataFromExcel(4);
		for (int i = 0; i < list.size(); i++) {
			Staff staff = list.get(i);
			String testData = staff.toString();
			System.out.println(testData);
			try {
				fillForm(staff);
				System.out.println("after full");
				if (doInsert()) {
					testNGResult.put(stt+1, new Object[] { stt, testData, staff.getExpectOutput(), "Thêm thành công", TimeUtil.getCurrentTime()});
				} else {
					throw new Exception();
				}				
			} catch (Exception e) {
				e.printStackTrace();
				testNGResult.put(stt+1, new Object[] { stt, testData, staff.getExpectOutput(), "Thêm thất bại", TimeUtil.getCurrentTime()});
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

	public void readDataFromExcel(int sheetNum) {
		try {
			list = Arrays.asList(
					mapper.readValue(ExcelUtil.readXLSX(workingDir + "\\TestData.xlsx", sheetNum), Staff[].class));
			System.out.println("list.size: " + list.size());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("get data from excel fail");
		}
		
	}
	
	public void fillForm(Staff staff) throws Exception {
		WebElement fullname = driver.findElement(uimap.getLocator("staff_fullname_field"));
		fullname.clear();
		fullname.sendKeys(staff.getFullName());
		
		List<WebElement> genders = driver.findElements(uimap.getLocator("staff_gender_checkbox"));
		if (staff.getGender()) {
			genders.get(0).click();
		} else {
			genders.get(1).click();
		}

		WebElement birthday = driver.findElement(uimap.getLocator("staff_birthday_field"));
		birthday.clear();
		birthday.sendKeys(staff.getBirthday());

		WebElement phone = driver.findElement(uimap.getLocator("staff_phone_field"));
		phone.clear();
		phone.sendKeys(staff.getPhone());

		WebElement email = driver.findElement(uimap.getLocator("staff_email_field"));
		email.clear();
		email.sendKeys(staff.getEmail());

		WebElement salary = driver.findElement(uimap.getLocator("staff_salary_field"));
		salary.clear();
		salary.sendKeys(staff.getSalary());
	
		List<WebElement> departs = driver.findElements(uimap.getLocator("staff_depart_options"));
		for (WebElement element : departs) {
			if (element.getAttribute("value").equals(staff.getDepartId())) {
				element.click();
			}
			
		}

		WebElement image = driver.findElement(uimap.getLocator("staff_imageUpload"));
		image.sendKeys(workingDir + "\\src\\main\\resources\\image\\" + staff.getImage());
		Thread.sleep(2000);
		WebElement note = driver.findElement(uimap.getLocator("staff_note_field"));
		note.clear();
		note.sendKeys(staff.getNotes());
	}
	
	@BeforeClass
	public void setUp() {
		try {
			login();
			driver.get(staff_url);
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
			ExcelUtil.writeXLSX(workingDir + "//TestResult.xlsx", testNGResult, 2);
			System.out.println("Ghi excel thanh cong");
			Desktop.getDesktop().open(new File(workingDir + "//TestResult.xlsx"));
			driver.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Ghi excel that bai");
		}
	}

}
