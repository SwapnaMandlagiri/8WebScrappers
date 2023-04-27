package com.tarladala.utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
	Properties pro;
	public ConfigReader() {
		try {
			FileInputStream fis = new FileInputStream("src/test/resources/properties/config.properties");
			pro = new Properties();
			pro.load(fis);
			//fis.close();

		} catch (Exception e) {
			System.out.println("Exception is"+e.getMessage());	
	}

	}
	public String getApplicationURl() {
		String url=pro.getProperty("baseURL");
		return url;
	}
	public String url() {
		String url=pro.getProperty("baseURL");
		return url;
		
	}

}
