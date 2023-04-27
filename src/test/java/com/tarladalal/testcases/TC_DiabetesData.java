package com.tarladalal.testcases;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.tarladala.utilities.ExcelReader;
import com.tarladala.utilities.Loggerload;

public class TC_DiabetesData extends BaseClass {
	ExcelReader read;

	// static WebDriver driver;
	public static ArrayList<String> recipeNameList = new ArrayList<String>();
	public static ArrayList<String> recipeIdList = new ArrayList<String>();
	public static ArrayList<String> ingredientList = new ArrayList<String>();
	public static ArrayList<String> prepTimeList = new ArrayList<String>();
	public static ArrayList<String> cookTimeList = new ArrayList<String>();
	public static ArrayList<String> prepMethodList = new ArrayList<String>();
	public static ArrayList<String> nutrientList = new ArrayList<String>();
	public static ArrayList<String> recUrlList = new ArrayList<String>();

	@Test(groups = "Diabetes", priority = 1)
	public void browserlaunch() throws InterruptedException, IOException {
		driver.get(baseURL);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		// Thread.sleep(1000);
		if (driver.getTitle().equals("Indian Recipes | Indian Vegetarian Recipes | Top Indian Veg Dishes")) {
			Assert.assertTrue(true);
			Loggerload.info("Successfully Launched the website");
		} else {
			captureScreeshot(driver, "browserlaunch");
			Assert.assertTrue(false);
			Loggerload.info("Failed to Launch the website");
		}
	}

	@Test(groups = "Diabetes", priority = 2)
	public void getRecipeDetails() throws InterruptedException {
		WebElement searchtxt;
		searchtxt = driver.findElement(By.xpath("//div[@id='search']/input[@id='ctl00_txtsearch']"));
		searchtxt.click();
		// Thread.sleep(1000);
		searchtxt.sendKeys("diabetic vegetarian breakfast");
		WebElement submitbtn = driver.findElement(By.name("ctl00$imgsearch"));
		submitbtn.click();
	}

	@Test(groups = "Diabetes", priority = 3)
	public void captureDatainsheet() throws IOException, InterruptedException {
		try {
			String excelpath = System.getProperty("user.dir")
					+ "\\src\\test\\resources\\TestData\\8WebScrappersdiabeticdata.xlsx";
			read = new ExcelReader(excelpath);
			Loggerload.info("Read Excel file");

			/************** CREATE EXCEL SHEET WITH COLUMN HEADING **************/
			read.setcelldata("Diabetic", 0, 0, "RecipeId");
			read.setcelldata("Diabetic", 0, 1, "Recipename");
			read.setcelldata("Diabetic", 0, 2, "Recipe Category");
			read.setcelldata("Diabetic", 0, 3, "Food Category");
			read.setcelldata("Diabetic", 0, 4, "Ingredients");
			read.setcelldata("Diabetic", 0, 5, "Preparation Time");
			read.setcelldata("Diabetic", 0, 6, "Cooking Time");
			read.setcelldata("Diabetic", 0, 7, "Preparation method");
			read.setcelldata("Diabetic", 0, 8, "Nutrient values");
			read.setcelldata("Diabetic", 0, 9, "Recipe URL");

			/************** Page Count *****************/
//		int pagescount = driver.findElements(By.xpath("//a[@class='rescurrpg']")).size();
//		System.out.println("Number of Pages:" + pagescount);
			int pagescount = driver.findElements(By.xpath("//div[@id='maincontent']/div/div/div[3]/a")).size();
			System.out.println("pagecount" + pagescount);

			/***************** Row count(Recipe Card Count) *************/
			int cardcount = driver.findElements(By.xpath("//div[@class='rcc_recipecard']")).size();
			System.out.println("Number of Rows in a page:" + cardcount);

			for (int j = 1; j <= 2; j++) {
				// List of recipename
				List<WebElement> ReceipeList = driver.findElements(By.xpath("//div[@class='rcc_recipecard']"));
				for (int i = 1; i <= ReceipeList.size(); i++) {
					WebElement recipeName = driver.findElement(
							By.xpath("//div[@class='rcc_recipecard'][" + i + "]//span[@class='rcc_recipename']/a"));
					System.out.println("Name of Recipe : " + recipeName.getText());
					recipeNameList.add(recipeName.getText());
				}
				// List of RecipeId
				for (int l = 1; l <= ReceipeList.size(); l++) {
					String recId = driver.findElement(By.xpath("(//div[@class='rcc_recipecard'])[" + l + "]//span"))
							.getText();
					recId = recId.split("\n")[0];
					recipeIdList.add(recId.replaceAll("[^0-9]", ""));
				}
				// ReceipeList.size()
				for (int k = 1; k <= ReceipeList.size(); k++) {
					try {
						WebElement recpUrl = driver.findElement(
								By.xpath("//div[@class='rcc_recipecard'][" + k + "]//span[@class='rcc_recipename']/a"));
						recUrlList.add(recpUrl.getAttribute("href"));
						recpUrl.click();
						Thread.sleep(3000);
						WebElement Ingrediants = driver.findElement(By.xpath("//div[@id='rcpinglist']"));
						ingredientList.add(Ingrediants.getText());
						System.out.println("Ingrediants are : " + Ingrediants.getText());
						WebElement PrepTime = driver.findElement(By.xpath("//p//time[1]"));
						if (!PrepTime.isDisplayed()) {
							System.out.println("Prepration time is not present");
						} else {
							Object prep = PrepTime.getText();
							try {
								if (prep.equals(null)) {
									System.out.println("Prep time is null");
									prepTimeList.add("null");
								} else
									prepTimeList.add(PrepTime.getText());
							} catch (NullPointerException e) {
								System.out.println("Null");
							}
						}
						System.out.println("Preperation Time is : " + PrepTime.getText());
						WebElement CookTime = driver.findElement(By.xpath("//p//time[2]"));
						cookTimeList.add(CookTime.getText());
						System.out.println("Cooking Time is : " + CookTime.getText());
						WebElement PrepMethod = driver.findElement(By.xpath("//div[@id='recipe_small_steps']"));
						prepMethodList.add(PrepMethod.getText());
						int trsize = driver.findElements(By.xpath("//table[@id='rcpnutrients']//tr")).size();
						System.out.println("Nutrient table size" + trsize);
						for (int tr = 1; tr < trsize; tr++) {
							try {
								String r = driver
										.findElement(By.xpath("//table[@id='rcpnutrients']//tr[" + tr + "]/td[1]"))
										.getText();
								String v = driver
										.findElement(By.xpath("//table[@id='rcpnutrients']//tr[" + tr + "]/td[2]/span"))
										.getText();
								nutrientList.add(r + " " + v);
							} catch (NoSuchElementException e) {
								System.out.println("Handled NoSuchElementException");
							}
						}
						System.out.println("Array Nutrient" + nutrientList.toString());

						Thread.sleep(2000);
						driver.navigate().back();
						Thread.sleep(2000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// driver.findElement(By.xpath("//div[@id='maincontent']/div/div/div[3]/a[" + j
				// + "]")).click();
				String pageNo = Integer.toString(j + 1);
				driver.findElement(By.xpath("//div[@id='maincontent']/div/div/div[3]/a[text()='" + pageNo + "']"))
						.click();
			}
			System.out.println("Writing " + recipeNameList.size() + " recipes into Excel sheet");

			String[] eliminationList1 = new String[] { "sugar", "shite rice", "white bread", "soda", "flavoured water",
					"Gatorade", "honey", "maple syrup", "Palmolein oil", "banana", "melon", "dairy milk", "butter",
					"cheese", "cream of rice", "rice flour", "rice", "rava", "corn" };
			String ingList;
			for (int k = 0; k < recipeNameList.size(); k++) {
				ingList = ingredientList.get(k).toLowerCase();
				if (ingList != null && !stringContainsItemFromList(ingList, eliminationList1)) {
					read.setcelldata("Diabetic", k + 1, 0, recipeIdList.get(k));
					read.setcelldata("Diabetic", k + 1, 1, recipeNameList.get(k));
					read.setcelldata("Diabetic", k + 1, 2, "Breakfast");
					read.setcelldata("Diabetic", k + 1, 3, "Vegetarian");
					read.setcelldata("Diabetic", k + 1, 4, ingredientList.get(k));
					read.setcelldata("Diabetic", k + 1, 5, prepTimeList.get(k));
					read.setcelldata("Diabetic", k + 1, 6, cookTimeList.get(k));
					read.setcelldata("Diabetic", k + 1, 7, prepMethodList.get(k));
					read.setcelldata("Diabetic", k + 1, 8, nutrientList.get(k));
					read.setcelldata("Diabetic", k + 1, 9, recUrlList.get(k));
				} else if (k != 0) {
					k--;
				}

			}
			Loggerload.info("Succfully Wrote all data");
			System.out.println("Succfully wrote all data into excel");
		} catch (Exception e) {
			System.out.println("Unexpected error occured" + e);
		}

	}

}
