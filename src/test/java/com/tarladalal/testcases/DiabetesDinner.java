package com.tarladalal.testcases;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
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

public class DiabetesDinner extends BaseClass{
	ExcelReader read;
	ConfigReader con;
	WebDriver driver;
	
	public void clicksearch(){
	
		driver.findElement(By.id("ctl00_imgsearch")).click();
	}
	
	public void diabeticdinnerveg() throws IOException {
		con=new ConfigReader();
		  System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/test/resources/WebDriver/geckodriver.exe");
			driver = new FirefoxDriver();
			driver.get(con.url());
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		Loggerload.info("The diabetic vegetarian dinner is searched");
		WebElement search2=driver.findElement(By.id("ctl00_txtsearch"));
		search2.clear();
		search2.sendKeys("diabetic dinner vegetarian"); //15
		clicksearch();
	}
	public void diabeticdinnervegan() throws IOException {
		con=new ConfigReader();
		  System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/test/resources/WebDriver/geckodriver.exe");
			driver = new FirefoxDriver();
			driver.get(con.url());
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		Loggerload.info("The diabetic vegan dinner is searched");
		WebElement search2=driver.findElement(By.id("ctl00_txtsearch"));
		search2.clear();
		search2.sendKeys("diabetic dinner vegan"); //149
		clicksearch();
	}
	public void diabeticdinnerjain() throws IOException {
		con=new ConfigReader();
		  System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/test/resources/WebDriver/geckodriver.exe");
			driver = new FirefoxDriver();
			driver.get(con.url());
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		Loggerload.info("The diabetic jain dinner is searched");
		WebElement search2=driver.findElement(By.id("ctl00_txtsearch"));
		search2.clear();
		search2.sendKeys("diabetic dinner jain"); //38
		clicksearch();
	}
	
	
	@Test(priority=2) 
	public void dbtdinnerveg() throws IOException {
		diabeticdinnerveg();
		con=new ConfigReader();
	
		   String excelpath=System.getProperty("user.dir") + "/src/test/resources/TestData/8WebScrappersdiabeticdata.xlsx";
			read=new ExcelReader(excelpath);
			read.setcelldata("vegdinner", 0, 0, "RecipeId");
			read.setcelldata("vegdinner", 0, 1, "Recipename");
			read.setcelldata("vegdinner", 0, 2, "Ingredients");
			read.setcelldata("vegdinner", 0, 3, "PreparationTime");
			read.setcelldata("vegdinner", 0, 4, "CookingTime");
			read.setcelldata("vegdinner", 0, 5, "PreparationMethod");
			read.setcelldata("vegdinner", 0, 6, "Nutrient");
			read.setcelldata("vegdinner", 0, 7, "Value");
			read.setcelldata("vegdinner", 0, 8, "Recipeurl");
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
			for(int i=1; i<=cardcount; i++) {
				System.out.println("in card"+i);
				try {
				 String recipeidwithdate=driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//div[2]/span")).getText();
				  String formattedrecipeid = recipeidwithdate.substring(8, recipeidwithdate.length() - 9);
				  recipeid.add(formattedrecipeid);
					System.out.println("in card"+i);
				  recipename.add(driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")).getText());
					System.out.println("in card"+i);
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

	  
	System.out.println("in card"+i);
			
			driver.navigate().back();				
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
					"fanta", "diet coke", "sprite", "mountain dew", "pepsi", "Dr pepper", "7up", "banana", "watermelon", "cantaloupe", "honeydew", "winter melon", "milk", "butter", "eggs", "cheese"};
	    	String[] addList = new String[] { "broccoli", "pumpkin", "pumpkin seeds", "chia seeds", "flax seeds",
						"apples", "almonds", "walnuts", "peanuts", "cashews", "pecans", "hazelnuts", "macadamia nuts",
						"pine nuts", "brazil nuts", "pistachios", "butternuts", "ladys finger", "okra", "beans", "raspberries", "strawberries", "blueberries", "blackberries", "eggs", 
						"yogurt", "curd", "bitter gourd", "rolled oats", "steel cut oats", "chicken", "fish", "quinoa", "mushroom"};
			Object ingList;
			for(int l=0; l<recipename.size(); l++) {
				ingList = ingrelist.get(l);
				if (ingList != null && !stringContainsItemFromList(ingList.toString(), eliminationList1) && stringContainsItemFromList(ingList.toString(), addList))  {
			 read.setcelldata("vegdinner", l+1, 0, recipeid.get(l).toString());
			 read.setcelldata("vegdinner", l+1, 1, recipename.get(l).toString());
			 read.setcelldata("vegdinner", l+1, 2, ingrelist.get(l).toString());
			 read.setcelldata("vegdinner", l+1, 3, prptime.get(l).toString());
			 read.setcelldata("vegdinner", l+1, 4, cooktime.get(l).toString());
			 read.setcelldata("vegdinner", l+1, 5, prepmethod.get(l).toString());
			 read.setcelldata("vegdinner", l+1, 8, recipeurl.get(l).toString());
	    	 read.setcelldata("vegdinner", l+1, 6, finalistforkey.get(l).toString());
	    	 read.setcelldata("vegdinner", l+1, 7, finalistforvalue.get(l).toString());
				}
			}
		
}
	
	@Test(priority=2) 
	public void dbtdinnervegan() throws IOException {
		diabeticdinnervegan();
		con=new ConfigReader();
	
		   String excelpath=System.getProperty("user.dir") + "/src/test/resources/TestData/8WebScrappersdiabeticdata.xlsx";
			read=new ExcelReader(excelpath);
			read.setcelldata("vegandinner", 0, 0, "RecipeId");
			read.setcelldata("vegandinner", 0, 1, "Recipename");
			read.setcelldata("vegandinner", 0, 2, "Ingredients");
			read.setcelldata("vegandinner", 0, 3, "PreparationTime");
			read.setcelldata("vegandinner", 0, 4, "CookingTime");
			read.setcelldata("vegandinner", 0, 5, "PreparationMethod");
			read.setcelldata("vegandinner", 0, 6, "Nutrient");
			read.setcelldata("vegandinner", 0, 7, "Value");
			read.setcelldata("vegandinner", 0, 8, "Recipeurl");
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
//			for(int m=1; m<=8; m++) {
//				driver.findElement(By.xpath("//div[@id='maincontent']/div/div/div[3]/a["+m+"]")).click();
//			}
		for(int j=1; j<=pagescount; j++) {
			for(int i=1; i<=cardcount; i++) {
				System.out.println("in page"+j+ "in card"+i);
				try {
				 String recipeidwithdate=driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//div[2]/span")).getText();
				  String formattedrecipeid = recipeidwithdate.substring(8, recipeidwithdate.length() - 9);
				  recipeid.add(formattedrecipeid);
					System.out.println("in card"+i);
				  recipename.add(driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")).getText());
					System.out.println("in card"+i);
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
					"fanta", "diet coke", "sprite", "mountain dew", "pepsi", "Dr pepper", "7up", "banana", "watermelon", "cantaloupe", "honeydew", "winter melon", "milk", "butter", "eggs", "cheese"};
	    	String[] addList = new String[] { "broccoli", "pumpkin", "pumpkin seeds", "chia seeds", "flax seeds",
						"apples", "almonds", "walnuts", "peanuts", "cashews", "pecans", "hazelnuts", "macadamia nuts",
						"pine nuts", "brazil nuts", "pistachios", "butternuts", "ladys finger", "okra", "beans", "raspberries", "strawberries", "blueberries", "blackberries", 
						"yogurt", "curd", "bitter gourd", "rolled oats", "steel cut oats", "chicken", "fish", "quinoa", "mushroom"};
			Object ingList;
			for(int l=0; l<recipename.size(); l++) {
				ingList = ingrelist.get(l);
				if (ingList != null && !stringContainsItemFromList(ingList.toString(), eliminationList1) && stringContainsItemFromList(ingList.toString(), addList))  {
			 read.setcelldata("vegandinner", l+1, 0, recipeid.get(l).toString());
			 read.setcelldata("vegandinner", l+1, 1, recipename.get(l).toString());
			 read.setcelldata("vegandinner", l+1, 2, ingrelist.get(l).toString());
			 read.setcelldata("vegandinner", l+1, 3, prptime.get(l).toString());
			 read.setcelldata("vegandinner", l+1, 4, cooktime.get(l).toString());
			 read.setcelldata("vegandinner", l+1, 5, prepmethod.get(l).toString());
			 read.setcelldata("vegandinner", l+1, 8, recipeurl.get(l).toString());
	    	 read.setcelldata("vegandinner", l+1, 6, finalistforkey.get(l).toString());
	    	 read.setcelldata("vegandinner", l+1, 7, finalistforvalue.get(l).toString());
				}
			}
		
}

	

	@Test(priority=2) 
	public void dbtdinnerjain() throws IOException {
		diabeticdinnerjain();
		con=new ConfigReader();
	
		   String excelpath=System.getProperty("user.dir") + "/src/test/resources/TestData/8WebScrappersdiabeticdata.xlsx";
			read=new ExcelReader(excelpath);
			read.setcelldata("jaindinner", 0, 0, "RecipeId");
			read.setcelldata("jaindinner", 0, 1, "Recipename");
			read.setcelldata("jaindinner", 0, 2, "Ingredients");
			read.setcelldata("jaindinner", 0, 3, "PreparationTime");
			read.setcelldata("jaindinner", 0, 4, "CookingTime");
			read.setcelldata("jaindinner", 0, 5, "PreparationMethod");
			read.setcelldata("jaindinner", 0, 6, "Nutrient");
			read.setcelldata("jaindinner", 0, 7, "Value");
			read.setcelldata("jaindinner", 0, 8, "Recipeurl");
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
			for(int j=1; j<=pagescount; j++) {
			for(int i=1; i<=cardcount; i++) {
				System.out.println("in card"+i);
				try {
				 String recipeidwithdate=driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//div[2]/span")).getText();
				  String formattedrecipeid = recipeidwithdate.substring(8, recipeidwithdate.length() - 9);
				  recipeid.add(formattedrecipeid);
					System.out.println("in card"+i);
				  recipename.add(driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span/a")).getText());
					System.out.println("in card"+i);
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
					"fanta", "diet coke", "sprite", "mountain dew", "pepsi", "Dr pepper", "7up", "banana", "watermelon", "cantaloupe", "honeydew", "carrot", "potato", "beetroot", "onion", "sweet potato", "garlic", "yuca", "turmeric", "winter melon", "milk", "butter", "eggs", "cheese"};
	    	String[] addList = new String[] { "broccoli", "pumpkin", "pumpkin seeds", "chia seeds", "flax seeds", 
						"apples", "almonds", "walnuts", "peanuts", "cashews", "pecans", "hazelnuts", "macadamia nuts",
						"pine nuts", "brazil nuts", "pistachios", "butternuts", "ladys finger", "okra", "beans", "raspberries", "strawberries", "blueberries", "blackberries", "eggs", 
						"yogurt", "curd", "bitter gourd", "rolled oats", "steel cut oats", "chicken", "fish", "quinoa", "mushroom"};
			Object ingList;
			for(int l=0; l<recipename.size(); l++) {
				ingList = ingrelist.get(l);
				if (ingList != null && !stringContainsItemFromList(ingList.toString(), eliminationList1) && stringContainsItemFromList(ingList.toString(), addList))  {
			 read.setcelldata("jaindinner", l+1, 0, recipeid.get(l).toString());
			 System.out.println(recipeid.get(l).toString());
			 read.setcelldata("jaindinner", l+1, 1, recipename.get(l).toString());
			 read.setcelldata("jaindinner", l+1, 2, ingrelist.get(l).toString());
			 read.setcelldata("jaindinner", l+1, 3, prptime.get(l).toString());
			 read.setcelldata("jaindinner", l+1, 4, cooktime.get(l).toString());
			 read.setcelldata("jaindinner", l+1, 5, prepmethod.get(l).toString());
			 read.setcelldata("jaindinner", l+1, 8, recipeurl.get(l).toString());
	    	 read.setcelldata("jaindinner", l+1, 6, finalistforkey.get(l).toString());
	    	 read.setcelldata("jaindinner", l+1, 7, finalistforvalue.get(l).toString());
				}
			}
		
}
}
