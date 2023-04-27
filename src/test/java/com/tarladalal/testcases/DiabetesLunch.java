package com.tarladalal.testcases;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.tarladala.utilities.ConfigReader;
import com.tarladala.utilities.ExcelReader;
import com.tarladala.utilities.Loggerload;

public class DiabetesLunch extends BaseClass {
	
	ExcelReader read;
	ConfigReader con;
	WebDriver driver;
	
	public void clicksearch(){
	
		driver.findElement(By.id("ctl00_imgsearch")).click();
	}
	
	public void diabeticlunchveg() throws IOException {
		con=new ConfigReader();
		  System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/test/resources/WebDriver/geckodriver.exe");
			driver = new FirefoxDriver();
			driver.get(con.url());
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		Loggerload.info("The diabetic breakfast vegetarian is searched");
		WebElement search2=driver.findElement(By.id("ctl00_txtsearch"));
		search2.clear();
		search2.sendKeys("diabetic vegetarian lunch"); //153
		clicksearch();
	}
	public void diabeticlunchvegan() throws IOException {
		con=new ConfigReader();
		  System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/test/resources/WebDriver/geckodriver.exe");
			driver = new FirefoxDriver();
			driver.get(con.url());
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		Loggerload.info("The diabetic breakfast vegan is searched");
		WebElement search2=driver.findElement(By.id("ctl00_txtsearch"));
		search2.clear();
		search2.sendKeys("diabetic vegan lunch"); //149
		clicksearch();
	}
	public void diabeticlunchjain() throws IOException {
		con=new ConfigReader();
		  System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/test/resources/WebDriver/geckodriver.exe");
			driver = new FirefoxDriver();
			driver.get(con.url());
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		Loggerload.info("The diabetic breakfast jain is searched");
		WebElement search2=driver.findElement(By.id("ctl00_txtsearch"));
		search2.clear();
		search2.sendKeys("diabetic jain lunch"); //202
		clicksearch();
	}
	
	@Test(priority=1) 
	public void dbtclunchveg() throws IOException {
		diabeticlunchveg();
		con=new ConfigReader();
		   String excelpath=System.getProperty("user.dir") + "/src/test/resources/TestData/8WebScrappersdiabeticdata.xlsx";
			read=new ExcelReader(excelpath);
			read.setcelldata("Sheet1", 0, 0, "RecipeId");
			read.setcelldata("Sheet1", 0, 1, "Recipename");
			read.setcelldata("Sheet1", 0, 2, "Ingredients");
			read.setcelldata("Sheet1", 0, 3, "PreparationTime");
			read.setcelldata("Sheet1", 0, 4, "CookingTime");
			read.setcelldata("Sheet1", 0, 5, "PreparationMethod");
			read.setcelldata("Sheet1", 0, 6, "Nutrient");
			read.setcelldata("Sheet1", 0, 7, "Value");
			read.setcelldata("Sheet1", 0, 8, "Recipeurl");
		int pagescount=driver.findElements(By.xpath("//div[@id='maincontent']/div/div/div[3]/a")).size();
		System.out.println("pagecount" +pagescount);
		int cardcount=driver.findElements(By.xpath("//div[@class='rcc_recipecard']")).size();
		System.out.println("cardcount" +cardcount);
		List<String> recipename = new ArrayList<String>();
		List<String> recipeid = new ArrayList<String>();
		List<String> ingrelist= new ArrayList<String>();
		List<Object> prptime= new ArrayList<Object>();
		List<Object> cooktime= new ArrayList<Object>();
		List<Object> prepmethod= new ArrayList<Object>();
		List<Object>  recipeurl= new ArrayList<Object>();
		 Map<String, Object> map = new HashMap<String, Object>();

		  ArrayList<String> key=null;
		  ArrayList<Object> valueList=null;
		 List<ArrayList<String>> finalistforkey = new ArrayList<ArrayList<String>>();
			List<ArrayList<Object>> finalistforvalue = new ArrayList<ArrayList<Object>>();
			for(int m=1; m<=5; m++) {
				driver.findElement(By.xpath("//div[@id='maincontent']/div/div/div[3]/a["+m+"]")).click();
			}
		for(int j=5; j<=7; j++) {
			for(int i=1; i<=cardcount; i++) {
				System.out.println("in page"+j+ "in card"+i);
				try {
				 String recipeidwithdate=driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//div[2]/span")).getText();
				  String formattedrecipeid = recipeidwithdate.substring(8, recipeidwithdate.length() - 9);
				  recipeid.add(formattedrecipeid);
					System.out.println("in page"+j+ "in card"+i);
				  recipename.add(driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")).getText());
					System.out.println("in page"+j+ "in card"+i);
				} catch(NoAlertPresentException e) {
					System.out.println(e +e.getMessage());
				}
				  driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")).click();
				  WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(30));
				  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='rcpinglist']")));
				  ingrelist.add( driver.findElement(By.xpath("//div[@id='rcpinglist']")).getText()); 
				  prepmethod.add( driver.findElement(By.xpath("//div[@id='recipe_small_steps']//ol")).getText()); 
				  recipeurl.add(driver.getCurrentUrl());
				  try {
					  int trsize= driver.findElements(By.xpath("//table[@id='rcpnutrients']//tr")).size();
					  for(int tr=1; tr<=trsize; tr++) {
						String  k=driver.findElement(By.xpath("//table[@id='rcpnutrients']//tr["+tr+"]/td[1]")).getText();
						String  v=driver.findElement(By.xpath("//table[@id='rcpnutrients']//tr["+tr+"]/td[2]/span")).getText();					  
						  map.put(k, v);
					  }
						  key = new ArrayList<String>(map.keySet());
						  valueList = new ArrayList<Object>(map.values());
						  finalistforkey.add(key);
						finalistforvalue.add(valueList);
						
					  }  catch(Exception e) {
						  System.out.println("Table not found");
					  }
				  try {
					   Object prep= driver.findElement(By.xpath("//p[2]//time[@itemprop='prepTime']")).getText();
					   prptime.add(driver.findElement(By.xpath("//p[2]//time[@itemprop='prepTime']")).getText());
				  }catch(Exception e) {
						   System.out.println("Preoaration time element not present");
					   } 
				   
				  try {

				 Object cook= driver.findElement(By.xpath("//p[2]//time[2][@itemprop='cookTime']")).getText();
				 cooktime.add(driver.findElement(By.xpath("//p[2]//time[2][@itemprop='cookTime']")).getText());	
				  } catch (Exception e) {
					   System.out.println("Cook time element not present");
				  }
				   
					System.out.println("in page"+j+ "in card"+i);
				driver.navigate().back();	
		}
			System.out.println("in page last"+j);
			driver.findElement(By.xpath("//div[@id='maincontent']/div/div/div[3]/a["+j+"]")).click();
			
		}
		System.out.println(recipename);
		System.out.println("prep" +prptime.size());
	      	String[] eliminationList1 = new String[] { "sugar", "white rice", "white bread", "soda", "flavoured water",
					"Gatorade", "honey", "maple syrup", "Palmolein oil", "banana", "melon", "dairy milk", "butter",
					"cheese", "cream of rice", "rice flour", "rice", "rava", "corn", "pasta", "apple juice", "orange juice", "pomegranate juice", "margarines", "peanut butter", 
					"spreads", "frozen foods", "flavoured curd", "flavoured yogurt", "corn flakes", "puffed rice", "bran flakes", "instant oatmeal", "jaggery", "sweet", "candies",
					"Chocolate", "refined flour", "all purpose flour", "alcoholic beverage", "wine", "beer", "whisky", "brandy", "bacon", "sausages", "hot dogs", "deli meats", 
					"chicken nuggets", "chicken patties", "jam", "jelly", "mango pickle", "pickled cucumber", "tomato pickle", "pickle", "canned pineapple", "canned peaches",
					"canned mangoes", "canned pears", "canned fruits", "canned mandarines", "canned oranges", "canned cherries", "chips", "mayonnaise", "powdered milk", "dried beans",
					"dried peas", "dried corn", "doughnut", "cake", "pastry", "cookies", "croissants", "sweetened tea", "sweetened coffee", "artificial sweetener", "packaged snack", "coca-cola", 
					"fanta", "diet coke", "sprite", "mountain dew", "pepsi", "Dr pepper", "7up", "banana", "watermelon", "cantaloupe", "honeydew", "winter melon", "milk", "butter", "cheese"};
	      	
	      	String[] addList = new String[] { "broccoli", "pumpkin", "pumpkin seeds", "chia seeds", "flax seeds",
					"apples", "almonds", "walnuts", "peanuts", "cashews", "pecans", "hazelnuts", "macadamia nuts",
					"pine nuts", "brazil nuts", "pistachios", "butternuts", "ladys finger", "okra", "beans", "raspberries", "strawberries", "blueberries", "blackberries", "eggs", 
					"yogurt", "curd", "bitter gourd", "rolled oats", "steel cut oats", "chicken", "fish", "quinoa", "mushroom"};
			Object ingList;
			for(int l=0; l<recipename.size(); l++) {
				ingList = ingrelist.get(l);
				if (ingList != null && !stringContainsItemFromList(ingList.toString(), eliminationList1) && stringContainsItemFromList(ingList.toString(), addList)) {
			 read.setcelldata("Sheet1", l+1, 0, recipeid.get(l).toString());
			 read.setcelldata("Sheet1", l+1, 1, recipename.get(l).toString());
			 read.setcelldata("Sheet1", l+1, 2, ingrelist.get(l).toString());
			 read.setcelldata("Sheet1", l+1, 3, prptime.get(l).toString());
			 read.setcelldata("Sheet1", l+1, 4, cooktime.get(l).toString());
			 read.setcelldata("Sheet1", l+1, 5, prepmethod.get(l).toString());
			 read.setcelldata("Sheet1", l+1, 8, recipeurl.get(l).toString());
	    	 read.setcelldata("Sheet1", l+1, 6, finalistforkey.get(l).toString());
	    	 read.setcelldata("Sheet1", l+1, 7, finalistforvalue.get(l).toString());
				}
			}
	    
				
	}
	
	@Test(priority=1) 
	public void dbtclunchvegan() throws IOException {
		diabeticlunchvegan();
		con=new ConfigReader();
		   String excelpath=System.getProperty("user.dir") + "/src/test/resources/TestData/8WebScrappersdiabeticdata.xlsx";
			read=new ExcelReader(excelpath);
			read.setcelldata("Sheet5", 0, 0, "RecipeId");
			read.setcelldata("Sheet5", 0, 1, "Recipename");
			read.setcelldata("Sheet5", 0, 2, "Ingredients");
			read.setcelldata("Sheet5", 0, 3, "PreparationTime");
			read.setcelldata("Sheet5", 0, 4, "CookingTime");
			read.setcelldata("Sheet5", 0, 5, "PreparationMethod");
			read.setcelldata("Sheet5", 0, 6, "Nutrient");
			read.setcelldata("Sheet5", 0, 7, "Value");
			read.setcelldata("Sheet5", 0, 8, "Recipeurl");
		int pagescount=driver.findElements(By.xpath("//div[@id='maincontent']/div/div/div[2]/a")).size();
		System.out.println("pagecount" +pagescount);
		int cardcount=driver.findElements(By.xpath("//div[@class='rcc_recipecard']")).size();
		System.out.println("cardcount" +cardcount);
		List<String> recipename = new ArrayList<String>();
		List<String> recipeid = new ArrayList<String>();
		List<String> ingrelist= new ArrayList<String>();
		List<Object> prptime= new ArrayList<Object>();
		List<Object> cooktime= new ArrayList<Object>();
		List<Object> prepmethod= new ArrayList<Object>();
		List<Object>  recipeurl= new ArrayList<Object>();
		 Map<String, Object> map = new HashMap<String, Object>();

		  ArrayList<String> key=null;
		  ArrayList<Object> valueList=null;
		 List<ArrayList<String>> finalistforkey = new ArrayList<ArrayList<String>>();
			List<ArrayList<Object>> finalistforvalue = new ArrayList<ArrayList<Object>>();
			for(int m=1; m<=5; m++) {
				driver.findElement(By.xpath("//div[@id='maincontent']/div/div/div[2]/a["+m+"]")).click();
			}
		for(int j=5; j<=pagescount; j++) {
			for(int i=1; i<=cardcount; i++) {
				System.out.println("in page"+j+ "in card"+i);
				try {
				 String recipeidwithdate=driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//div[2]/span")).getText();
				  String formattedrecipeid = recipeidwithdate.substring(8, recipeidwithdate.length() - 9);
				  recipeid.add(formattedrecipeid);
					System.out.println("in page"+j+ "in card"+i);
				  recipename.add(driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")).getText());
					System.out.println("in page"+j+ "in card"+i);
				} catch(UnhandledAlertException e) {
					System.out.println(e +e.getMessage());
				}
				  driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")).click();
				  WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(30));
				  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='rcpinglist']")));
				  ingrelist.add( driver.findElement(By.xpath("//div[@id='rcpinglist']")).getText()); 
				  prepmethod.add( driver.findElement(By.xpath("//div[@id='recipe_small_steps']//ol")).getText()); 
				  recipeurl.add(driver.getCurrentUrl());
				  try {
					  int trsize= driver.findElements(By.xpath("//table[@id='rcpnutrients']//tr")).size();
					  for(int tr=1; tr<=trsize; tr++) {
						String  k=driver.findElement(By.xpath("//table[@id='rcpnutrients']//tr["+tr+"]/td[1]")).getText();
						String  v=driver.findElement(By.xpath("//table[@id='rcpnutrients']//tr["+tr+"]/td[2]/span")).getText();					  
						  map.put(k, v);
					  }
						  key = new ArrayList<String>(map.keySet());
						  valueList = new ArrayList<Object>(map.values());
						  finalistforkey.add(key);
						finalistforvalue.add(valueList);
						
					  }  catch(Exception e) {
						  System.out.println("Table not found");
					  }
				  try {
				   if(!(driver.findElement(By.xpath("//p[2]//time[@itemprop='prepTime']")).isDisplayed())) {
					   System.out.println("Prepration time is not present");
				   }  else {
					   Object prep= driver.findElement(By.xpath("//p[2]//time[@itemprop='prepTime']")).getText();
					   try {
					   if(prep.equals(null)) {
						   System.out.println("Prep time is null");
					   } else 
						   prptime.add(driver.findElement(By.xpath("//p[2]//time[@itemprop='prepTime']")).getText());
					   } catch (NullPointerException e) {
						   System.out.println("Null");
					   }
				   }
				  }
				  catch (ElementNotInteractableException e) {
					   System.out.println(e +e.getMessage());
				   }
				   
				  try {
				  if(!driver.findElement(By.xpath("//p[2]//time[2][@itemprop='cookTime']")).isDisplayed()) {
					   System.out.println("Cook time is not present");
					     } else {
				 Object cook= driver.findElement(By.xpath("//p[2]//time[2][@itemprop='cookTime']")).getText();
				 try {
					   if(cook.equals(null)) {
						   System.out.println("Cook time is null");
					   } else 
						   cooktime.add(driver.findElement(By.xpath("//p[2]//time[2][@itemprop='cookTime']")).getText());	
					   } catch (NullPointerException e) {
						   System.out.println("Null");
					   }
					     }
				  } catch (ElementNotInteractableException e) {
					   System.out.println(e +e.getMessage());
				   }
					System.out.println("in page"+j+ "in card"+i);
				driver.navigate().back();	
		}
			System.out.println("in page last"+j);
			driver.findElement(By.xpath("//div[@id='maincontent']/div/div/div[2]/a["+j+"]")).click();
			
		}
		System.out.println(recipename);
		System.out.println("prep" +prptime.size());
	      	String[] eliminationList1 = new String[] { "sugar", "white rice", "white bread", "soda", "flavoured water",
					"Gatorade", "honey", "maple syrup", "Palmolein oil", "banana", "melon", "dairy milk", "butter",
					"cheese", "cream of rice", "rice flour", "rice", "rava", "corn", "pasta", "apple juice", "orange juice", "pomegranate juice", "margarines", "peanut butter", 
					"spreads", "frozen foods", "flavoured curd", "flavoured yogurt", "corn flakes", "puffed rice", "bran flakes", "instant oatmeal", "jaggery", "sweet", "candies",
					"Chocolate", "refined flour", "all purpose flour", "alcoholic beverage", "wine", "beer", "whisky", "brandy", "bacon", "sausages", "hot dogs", "deli meats", 
					"chicken nuggets", "chicken patties", "jam", "jelly", "mango pickle", "pickled cucumber", "tomato pickle", "pickle", "canned pineapple", "canned peaches",
					"canned mangoes", "canned pears", "canned fruits", "canned mandarines", "canned oranges", "canned cherries", "chips", "mayonnaise", "powdered milk", "dried beans",
					"dried peas", "dried corn", "doughnut", "cake", "pastry", "cookies", "croissants", "sweetened tea", "sweetened coffee", "artificial sweetener", "packaged snack", "coca-cola", 
					"fanta", "diet coke", "sprite", "mountain dew", "pepsi", "Dr pepper", "7up", "banana", "watermelon", "cantaloupe", "honeydew", "winter melon", "milk", "eggs", "butter", "cheese"};
	      	
	      	String[] addList = new String[] { "broccoli", "pumpkin", "pumpkin seeds", "chia seeds", "flax seeds",
					"apples", "almonds", "walnuts", "peanuts", "cashews", "pecans", "hazelnuts", "macadamia nuts",
					"pine nuts", "brazil nuts", "pistachios", "butternuts", "ladys finger", "okra", "beans", "raspberries", "strawberries", "blueberries", "blackberries",  
					"yogurt", "curd", "bitter gourd", "rolled oats", "steel cut oats", "chicken", "fish", "quinoa", "mushroom"};
			Object ingList;
			for(int l=0; l<recipename.size(); l++) {
				ingList = ingrelist.get(l);
				if (ingList != null && !stringContainsItemFromList(ingList.toString(), eliminationList1) && stringContainsItemFromList(ingList.toString(), addList)) {
			 read.setcelldata("Sheet5", l+1, 0, recipeid.get(l).toString());
			 read.setcelldata("Sheet5", l+1, 1, recipename.get(l).toString());
			 read.setcelldata("Sheet5", l+1, 2, ingrelist.get(l).toString());
			 read.setcelldata("Sheet5", l+1, 3, prptime.get(l).toString());
			 read.setcelldata("Sheet5", l+1, 4, cooktime.get(l).toString());
			 read.setcelldata("Sheet5", l+1, 5, prepmethod.get(l).toString());
			 read.setcelldata("Sheet5", l+1, 8, recipeurl.get(l).toString());
	    	 read.setcelldata("Sheet5", l+1, 6, finalistforkey.get(l).toString());
	    	 read.setcelldata("Sheet5", l+1, 7, finalistforvalue.get(l).toString());
				}
			}
	    
				
	}
	//here
	@Test(priority=1) 
	public void dbtclunchjain() throws IOException {
		
		diabeticlunchjain();
		con=new ConfigReader();
			
		   String excelpath=System.getProperty("user.dir") + "/src/test/resources/TestData/8WebScrappersdiabeticdata.xlsx";
			read=new ExcelReader(excelpath);
			read.setcelldata("Sheet6", 0, 0, "RecipeId");
			read.setcelldata("Sheet6", 0, 1, "Recipename");
			read.setcelldata("Sheet6", 0, 2, "Ingredients");
			read.setcelldata("Sheet6", 0, 3, "PreparationTime");
			read.setcelldata("Sheet6", 0, 4, "CookingTime");
			read.setcelldata("Sheet6", 0, 5, "PreparationMethod");
			read.setcelldata("Sheet6", 0, 6, "Nutrient");
			read.setcelldata("Sheet6", 0, 7, "Value");
			read.setcelldata("Sheet6", 0, 8, "Recipeurl");
		int pagescount=driver.findElements(By.xpath("//div[@id='maincontent']/div/div/div[3]/a")).size();
		System.out.println("pagecount" +pagescount);
		int cardcount=driver.findElements(By.xpath("//div[@class='rcc_recipecard']")).size();
		System.out.println("cardcount" +cardcount);
		List<String> recipename = new ArrayList<String>();
		List<String> recipeid = new ArrayList<String>();
		List<String> ingrelist= new ArrayList<String>();
		List<Object> prptime= new ArrayList<Object>();
		List<Object> cooktime= new ArrayList<Object>();
		List<Object> prepmethod= new ArrayList<Object>();
		List<Object>  recipeurl= new ArrayList<Object>();
		 Map<String, Object> map = new HashMap<String, Object>();
		 String  k;
		 String v;
		  int trsize = 0;
		  ArrayList<String> key=null;
		  ArrayList<Object> valueList=null;
		 List<ArrayList<String>> finalistforkey = new ArrayList<ArrayList<String>>();
			List<ArrayList<Object>> finalistforvalue = new ArrayList<ArrayList<Object>>();
			for(int m=1; m<=7; m++) {
				driver.findElement(By.xpath("//div[@id='maincontent']/div/div/div[3]/a["+m+"]")).click();
			}
		for(int j=8; j<=pagescount; j++) {
			for(int i=1; i<=cardcount; i++) {
				System.out.println("in page"+j+ "in card"+i);
				try {
				 String recipeidwithdate=driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//div[2]/span")).getText();
				  String formattedrecipeid = recipeidwithdate.substring(8, recipeidwithdate.length() - 9);
				  recipeid.add(formattedrecipeid);
					System.out.println("in page"+j+ "in card"+i);
				  recipename.add(driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")).getText());
					System.out.println("in page"+j+ "in card"+i);
				} catch(UnhandledAlertException e) {
					System.out.println(e +e.getMessage());
				}
				  driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")).click();
				  WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(30));
				  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='rcpinglist']")));
				  ingrelist.add( driver.findElement(By.xpath("//div[@id='rcpinglist']")).getText()); 
				  prepmethod.add( driver.findElement(By.xpath("//div[@id='recipe_small_steps']//ol")).getText()); 
				  recipeurl.add(driver.getCurrentUrl());
				  try {
					  WebDriverWait waitee = new WebDriverWait(driver, Duration.ofSeconds(30));
					  waitee.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='rcpnutrients']//tr")));
					   trsize= driver.findElements(By.xpath("//table[@id='rcpnutrients']//tr")).size();
					  for(int tr=1; tr<=trsize; tr++) {
					  k=driver.findElement(By.xpath("//table[@id='rcpnutrients']//tr["+tr+"]/td[1]")).getText();
					v=driver.findElement(By.xpath("//table[@id='rcpnutrients']//tr["+tr+"]/td[2]/span")).getText();					  
						  map.put(k, v);
					  }
						  key = new ArrayList<String>(map.keySet());
						  valueList = new ArrayList<Object>(map.values());
						  finalistforkey.add(key);
						finalistforvalue.add(valueList);
						
					  }  catch(Exception e) {
						  System.out.println("Table not found");
					  }
				  try {
					   Object prep= driver.findElement(By.xpath("//p[2]//time[@itemprop='prepTime']")).getText();
					   prptime.add(driver.findElement(By.xpath("//p[2]//time[@itemprop='prepTime']")).getText());
				  }catch(Exception e) {
						   System.out.println("Preoaration time element not present");
					   } 
				   
				  try {

				 Object cook= driver.findElement(By.xpath("//p[2]//time[2][@itemprop='cookTime']")).getText();
				 cooktime.add(driver.findElement(By.xpath("//p[2]//time[2][@itemprop='cookTime']")).getText());	
				  } catch (Exception e) {
					   System.out.println("Cook time element not present");
				  }
					System.out.println("in page"+j+ "in card"+i);
				driver.navigate().back();	
		}
			System.out.println("in page last"+j);
			driver.findElement(By.xpath("//div[@id='maincontent']/div/div/div[3]/a["+j+"]")).click();
			
		}
		System.out.println(recipename);
		System.out.println("prep" +prptime.size());
	      	String[] eliminationList1 = new String[] { "sugar", "white rice", "white bread", "soda", "flavoured water",
					"Gatorade", "honey", "maple syrup", "Palmolein oil", "banana", "melon", "dairy milk", "butter",
					"cheese", "cream of rice", "rice flour", "rice", "rava", "corn", "pasta", "apple juice", "orange juice", "pomegranate juice", "margarines", "peanut butter", 
					"spreads", "frozen foods", "flavoured curd", "flavoured yogurt", "corn flakes", "puffed rice", "bran flakes", "instant oatmeal", "jaggery", "sweet", "candies",
					"Chocolate", "refined flour", "all purpose flour", "alcoholic beverage", "wine", "beer", "whisky", "brandy", "bacon", "sausages", "hot dogs", "deli meats", 
					"chicken nuggets", "chicken patties", "jam", "jelly", "mango pickle", "pickled cucumber", "tomato pickle", "pickle", "canned pineapple", "canned peaches",
					"canned mangoes", "canned pears", "canned fruits", "canned mandarines", "canned oranges", "canned cherries", "chips", "mayonnaise", "powdered milk", "dried beans",
					"dried peas", "dried corn", "doughnut", "cake", "pastry", "cookies", "croissants", "sweetened tea", "sweetened coffee", "artificial sweetener", "packaged snack", "coca-cola", 
					"fanta", "diet coke", "sprite", "mountain dew", "pepsi", "Dr pepper", "7up", "banana", "watermelon", "cantaloupe", "honeydew", "winter melon", "milk", "butter", "cheese"};
	      	
	      	String[] addList = new String[] { "broccoli", "pumpkin", "pumpkin seeds", "chia seeds", "flax seeds",
					"apples", "almonds", "walnuts", "peanuts", "cashews", "pecans", "hazelnuts", "macadamia nuts",
					"pine nuts", "brazil nuts", "pistachios", "butternuts", "ladys finger", "okra", "beans", "raspberries", "strawberries", "blueberries", "blackberries", "eggs", 
					"yogurt", "curd", "bitter gourd", "rolled oats", "steel cut oats", "chicken", "fish", "quinoa", "mushroom"};
			Object ingList;
			for(int l=0; l<recipename.size(); l++) {
				ingList = ingrelist.get(l);
				if (ingList != null && !stringContainsItemFromList(ingList.toString(), eliminationList1) && stringContainsItemFromList(ingList.toString(), addList)) {
			 read.setcelldata("Sheet6", l+1, 0, recipeid.get(l).toString());
			 read.setcelldata("Sheet6", l+1, 1, recipename.get(l).toString());
			 read.setcelldata("Sheet6", l+1, 2, ingrelist.get(l).toString());
			 read.setcelldata("Sheet6", l+1, 3, prptime.get(l).toString());
			 read.setcelldata("Sheet6", l+1, 4, cooktime.get(l).toString());
			 read.setcelldata("Sheet6", l+1, 5, prepmethod.get(l).toString());
			 read.setcelldata("Sheet6", l+1, 8, recipeurl.get(l).toString());
	    	 read.setcelldata("Sheet6", l+1, 6, finalistforkey.get(l).toString());
	    	 read.setcelldata("Sheet6", l+1, 7, finalistforvalue.get(l).toString());
				}
			}
	    
				
	}
	
	

}
