package com.tarladalal.testcases;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.tarladala.utilities.ConfigReader;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
 ConfigReader configReader = new ConfigReader();

	public String baseURL = configReader.getApplicationURl();

	public static WebDriver driver;

	@Parameters("browser")
	@BeforeClass
	public void setup(String br) {

		if (br.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			disableChromeImages(options);
			driver = WebDriverManager.chromedriver().capabilities(options).create();
		} else if (br.contains("firefox")) {
			FirefoxOptions options=new FirefoxOptions();
			disableImagesFirefox(options);
			driver = WebDriverManager.firefoxdriver().capabilities(options).create();
		}
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
	
	public void captureScreeshot(WebDriver driver,String tname) throws IOException {
		TakesScreenshot ts=(TakesScreenshot) driver;
		File source=ts.getScreenshotAs(OutputType.FILE);
		File target=new File(System.getProperty("user.dir")+"/Screenshots"+tname+".png");
		FileUtils.copyFile(source, target);
		System.out.println("Screenshot taken");
	}
	public static ChromeOptions disableChromeImages(ChromeOptions options) {
		HashMap<String,Object> imagesMap=new HashMap<String,Object>();
		imagesMap.put("images", 2);
		HashMap<String,Object> prefsMap=new HashMap<String,Object>();
		prefsMap.put("profile.default_content_setting_values", imagesMap);
		options.setExperimentalOption("prefs", prefsMap);
		return options;
	}
	public static FirefoxOptions disableImagesFirefox(FirefoxOptions options) {
		FirefoxProfile profile=new FirefoxProfile();
		profile.setPreference("permission.default.image",2);
		options.setProfile(profile);
		return options;
	}
	public static boolean stringContainsItemFromList(String inputStr, String[] items) {
		return Arrays.stream(items).anyMatch(inputStr::contains);
	}
}
