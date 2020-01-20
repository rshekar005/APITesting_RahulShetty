package com.basics;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class TestSelenium {

	@Test
	public void launch() {
        WebDriver driver;
		System.setProperty("webdriver.chrome.driver", "chromedriver_78.exe");
		
		driver= new ChromeDriver();
		System.out.println("Driver initialized");
		driver.get("https://www.google.com");
		
		
		
	}

}
