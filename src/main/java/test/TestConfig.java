package test;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.databind.ObjectMapper;

import entities.User;
import utils.ExcelUtil;
import utils.UIMap;

public class TestConfig {
	public WebDriver driver;
	public UIMap uimap;
	public String workingDir;
	String login_url = "http://localhost:8080/SOF302_Assignment_PS08445-1.0/authentication/login";
	ObjectMapper mapper;
	Map<Integer, Object[]> testNGResult;
	
	public TestConfig() {
		System.out.println("start test config");
		workingDir = System.getProperty("user.dir");
		uimap = new UIMap(workingDir + "\\src\\main\\resources\\locator.properties");
		mapper = new ObjectMapper();
		
		String chromeDriverName = "webdriver.chrome.driver";
		String chromeDriverPath = workingDir + "\\chromedriver.exe";
		System.setProperty(chromeDriverName, chromeDriverPath);

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.get(login_url);
	}

	public void login() {
		try {
			User user = Arrays
					.asList(mapper.readValue(ExcelUtil.readXLSX(workingDir + "\\TestData.xlsx", 0), User[].class))
					.get(0);

			WebElement username = driver.findElement(uimap.getLocator("login_username_field"));
			username.sendKeys(user.getUsername());

			WebElement password = driver.findElement(uimap.getLocator("login_password_field"));
			password.sendKeys(user.getPassword());

			driver.findElement(uimap.getLocator("login_login_button")).click();
			WebElement ok_button = waitForElement(1, uimap.getLocator("swal_ok_button"));
			ok_button.click();
			Thread.sleep(1000);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("login fail");
		}
	}

	public boolean doInsert() {
		try {
			driver.findElement(uimap.getLocator("insert_button")).click();
			WebElement swal_title = waitForElement(1, uimap.getLocator("swal_title"));

			if (swal_title.getText().equals("Thành công")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean doUpdate() {
		try {
			driver.findElement(uimap.getLocator("update_button")).click();
			WebElement swal_title = waitForElement(1, uimap.getLocator("swal_title"));

			if (swal_title.getText().equals("Thành công")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean doDelete() {
		try {
			driver.findElement(uimap.getLocator("delete_button")).click();
			WebElement confirm_button = waitForElement(1, uimap.getLocator("swal_confirm_button"));
			confirm_button.click();
			WebElement swal_title = waitForElement(1, uimap.getLocator("swal_title"));
			
			if (swal_title.getText().equals("Thành công")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public void clickOK(){
		try {
			WebElement ok_button = waitForElement(1, uimap.getLocator("swal_ok_button"));
			ok_button.click();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void doReset(){
		try {
			driver.findElement(uimap.getLocator("reset_button")).click();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public WebElement waitForElement(int seconds, By by) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		return wait.until(ExpectedConditions.elementToBeClickable(by));
	}
}
