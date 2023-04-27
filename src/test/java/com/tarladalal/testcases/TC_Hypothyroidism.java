package com.tarladalal.testcases;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.tarladala.utilities.ExcelReader;
import com.tarladala.utilities.Loggerload;

public class TC_Hypothyroidism extends BaseClass {
	ExcelReader read;

	// static WebDriver driver;
	public static ArrayList<String> recipeNameList = new ArrayList<String>();
	public static ArrayList<String> recipeIdList = new ArrayList<String>();
	public static ArrayList<String> ingredientList = new ArrayList<String>();
	public static ArrayList<String> prepTimeList = new ArrayList<String>();
	public static ArrayList<String> cookTimeList = new ArrayList<String>();
	public static ArrayList<String> prepMethodList = new ArrayList<String>();
	public static ArrayList<String> NutrientList = new ArrayList<String>();
	public static ArrayList<String> recUrlList = new ArrayList<String>();
	public static ArrayList<String> eliminationList = new ArrayList<String>();
	HashMap<String, String> NutrientMap = new HashMap<String, String>();

	@Test(groups = "Hypothyroid", priority = 1)
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

	@Test(groups = "Hypothyroid", priority = 2)
	public void getRecipeDetails() throws InterruptedException {
		WebElement searchtxt;
		searchtxt = driver.findElement(By.xpath("//div[@id='search']/input[@id='ctl00_txtsearch']"));
		searchtxt.click();
		// Thread.sleep(1000);
		searchtxt.sendKeys("hypothyroid breakfast");
		WebElement submitbtn = driver.findElement(By.name("ctl00$imgsearch"));
		submitbtn.click();
	}

	@Test(groups = "Hypothyroid", priority = 3)
	public void captureDatainsheet() throws IOException, InterruptedException {
		try {
			String excelpath = System.getProperty("user.dir")
					+ "\\src\\test\\resources\\TestData\\8WebScrappershypothyroiddata.xlsx";
			read = new ExcelReader(excelpath);
			Loggerload.info("Read Excel file");
			/************** CREATE EXCEL SHEET WITH COLUMN HEADING **************/
			System.out.println("Setting excel sheet to read");
			read.setcelldata("Hyporthyroidism", 0, 0, "RecipeId");
			read.setcelldata("Hyporthyroidism", 0, 1, "Recipename");
			// read.setcelldata("Hyporthyroidism", 0, 2, "Recipe Category");
			// read.setcelldata("Hyporthyroidism", 0, 3, "Food Category");
			read.setcelldata("Hyporthyroidism", 0, 4, "Ingredients");
			read.setcelldata("Hyporthyroidism", 0, 5, "Preparation Time");
			read.setcelldata("Hyporthyroidism", 0, 6, "Cooking Time");
			read.setcelldata("Hyporthyroidism", 0, 7, "Preparation method");
			// read.setcelldata("Hyporthyroidism", 0, 8, "Nutrient values");
			read.setcelldata("Hyporthyroidism", 0, 9, "Recipe URL");

			/************** Page Count *****************/
//		int pagescount = driver.findElements(By.xpath("//a[@class='rescurrpg']")).size();
//		System.out.println("Number of Pages:" + pagescount);
			int pagescount = driver.findElements(By.xpath("//div[@id='maincontent']/div/div/div[3]/a")).size();
			System.out.println("pagecount" + pagescount);

			/***************** Row count(Recipe Card Count) *************/
			int cardcount = driver.findElements(By.xpath("//div[@class='rcc_recipecard']")).size();
			System.out.println("Number of Rows in a page:" + cardcount);

			for (int j = 1; j <= pagescount; j++) {
				try {
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
							WebElement recpUrl = driver.findElement(By.xpath(
									"//div[@class='rcc_recipecard'][" + k + "]//span[@class='rcc_recipename']/a"));
							recUrlList.add(recpUrl.getAttribute("href"));
							recpUrl.click();
							Thread.sleep(3000);
							WebElement Ingrediants = driver.findElement(By.xpath("//div[@id='rcpinglist']"));
							ingredientList.add(Ingrediants.getText());
							System.out.println("Ingrediants are : " + Ingrediants.getText());
							WebElement PrepTime = driver.findElement(By.xpath("//p[2]//time[@itemprop='prepTime']"));
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
								} catch (NoSuchElementException e) {
									e.printStackTrace();
								} catch (Exception e) {
									e.printStackTrace();
								}

							}
							System.out.println("Preperation Time is : " + PrepTime.getText());
							WebElement CookTime = driver.findElement(By.xpath("//p[2]//time[2][@itemprop='cookTime']"));
							cookTimeList.add(CookTime.getText());
							System.out.println("Cooking Time is : " + CookTime.getText());
							WebElement PrepMethod = driver.findElement(By.xpath("//div[@id='recipe_small_steps']"));
							prepMethodList.add(PrepMethod.getText());
							int trsize = driver.findElements(By.xpath("//table[@id='rcpnutrients']//tr")).size();
							System.out.println("Nutrient table size" + trsize);
							for (int tr = 1; tr < trsize; tr++) {
								String r = driver
										.findElement(By.xpath("//table[@id='rcpnutrients']//tr[" + tr + "]/td[1]"))
										.getText();
								String v = driver
										.findElement(By.xpath("//table[@id='rcpnutrients']//tr[" + tr + "]/td[2]/span"))
										.getText();
								System.out.println("Nutrient Value:" + r + " " + v);
								NutrientList.add(r + " " + v);
							}
							Thread.sleep(2000);
							driver.navigate().back();
							// driver.navigate().refresh();
							Thread.sleep(2000);
						} catch (StaleElementReferenceException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					String pageNo = Integer.toString(j + 1);
					driver.findElement(By.xpath("//div[@id='pagination']/a[text()='" + pageNo + "']")).click();
					System.out.println("clicking pagae number:" + pageNo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println("Writing " + recipeNameList.size() + " recipes into Excel sheet");

			String[] eliminationList1 = new String[] { "tofu", "edamame", "tempeh", "cauliflower", "peaches", "cabbage",
					"broccoli", "kale", "spinach", "sweet potatoes", "strawberries", "pine nuts", "peanuts",
					"green tea", "coffee", "alcohol", "soy milk", "white bread", "cakes", "pastries", "fried food",
					"sugar", "ham", "bacon", "salami", "sausages", "frozen food", "gluten", "sodas", "caffeine",
					"noodles", "soups", "salad dressings", "sauces", "candies", "milk", "soy", "egg", "sesame",
					"peanuts", "walnut", "almond", "hazelnut", "pecan", "cashew", "pistachio", "shell fish",
					"seafood" };
			String ingList;
			for (int k = 0; k < recipeNameList.size(); k++) {
				try {
					ingList = ingredientList.get(k).toLowerCase();
					if (ingList != null && !stringContainsItemFromList(ingList, eliminationList1)) {
						read.setcelldata("Hyporthyroidism", k + 1, 0, recipeIdList.get(k));
						read.setcelldata("Hyporthyroidism", k + 1, 1, recipeNameList.get(k));
						// read.setcelldata("Hyporthyroidism", k + 1, 2, recipeid.get(k));
						// read.setcelldata("Hyporthyroidism", k + 1, 3, recipeid.get(k));
						read.setcelldata("Hyporthyroidism", k + 1, 4, ingredientList.get(k));
						read.setcelldata("Hyporthyroidism", k + 1, 5, prepTimeList.get(k));
						read.setcelldata("Hyporthyroidism", k + 1, 6, cookTimeList.get(k));
						read.setcelldata("Hyporthyroidism", k + 1, 7, prepMethodList.get(k));
						read.setcelldata("Hyporthyroidism", k + 1, 8, NutrientList.get(k));
						read.setcelldata("Hyporthyroidism", k + 1, 9, recUrlList.get(k));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Loggerload.info("Succfully read all data");
		} catch (Exception e) {
			System.out.println("Unexpected error occured" + e);

		}
	}
}
