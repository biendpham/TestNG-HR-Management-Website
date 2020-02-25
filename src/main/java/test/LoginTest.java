package test;

import java.awt.Desktop;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import entities.User;
import utils.ExcelUtil;
import utils.TimeUtil;

public class LoginTest extends TestConfig{
	
	Map<Integer, Object[]> testNGResult;
	List<User> list;

	String admin_home_url = "http://localhost:8080/SOF302_Assignment_PS08445-1.0/admin/home";

	@Test
	public void loginTest() {
		for (int i = 0; i < list.size(); i++) {
			String testDescript = "Login with " + list.get(i).toString();
			System.out.println(testDescript);
			try {
				fillForm(list.get(i));
				if (doLogin()) {
					testNGResult.put(i + 2, new Object[] { i + 1, testDescript, "pass", "pass", TimeUtil.getCurrentTime()});
					logOut();
				} else {
					testNGResult.put(i + 2, new Object[] { i + 1, testDescript, "pass", "fail", TimeUtil.getCurrentTime()});
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				testNGResult.put(i + 2, new Object[] { i + 1, testDescript, "pass", "fail", TimeUtil.getCurrentTime()});
			}
		}

	}

	public void fillForm(User user) throws Exception {
		// send data to username field
		WebElement username = driver.findElement(uimap.getLocator("login_username_field"));
		username.sendKeys(user.getUsername());

		// send data to password field
		WebElement password = driver.findElement(uimap.getLocator("login_password_field"));
		password.sendKeys(user.getPassword());
	}

	public boolean doLogin() throws Exception {
		driver.findElement(uimap.getLocator("login_login_button")).click();
		WebElement ok_button = waitForElement(5, uimap.getLocator("swal_ok_button"));
		ok_button.click();
		if (driver.getCurrentUrl().equals(admin_home_url)) {
			return true;
		} else {
			return false;
		}
	}

	public void logOut() throws Exception {

		WebElement user_link = waitForElement(5, uimap.getLocator("login_user_link"));
		user_link.click();

		WebElement logout_link = waitForElement(5, uimap.getLocator("layout_logout_link"));
		logout_link.click();
	}
	
	@BeforeClass
	public void setUp() {
		testNGResult = new LinkedHashMap<>();

		testNGResult.put(1, new Object[] { "STT", "Mô tả", "Kết quả mong đợi", "Trạng thái", "Thời gian test" });

		try {
			// read data from excel
			list = Arrays
					.asList(mapper.readValue(ExcelUtil.readXLSX(workingDir + "\\TestData.xlsx", 0), User[].class));
			System.out.println("list.size: " + list.size());
		} catch (Exception e) {
			throw new IllegalStateException("Can't start the Chrome web driver", e);
		}

	}

	@AfterClass
	public void tearDown() throws InterruptedException {
		try {
			ExcelUtil.writeXLSX(workingDir + "//TestResult.xlsx", testNGResult, 0);
			Desktop.getDesktop().open(new File(workingDir + "//TestResult.xlsx"));
			System.out.println("Ghi excel thanh cong");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Ghi excel that bai");
		}
		driver.close();
	}
	
	public WebElement waitForElement(int seconds, By by) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		return wait.until(ExpectedConditions.elementToBeClickable(by));
	}
}
