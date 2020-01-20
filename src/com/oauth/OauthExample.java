<<<<<<< HEAD
package com.oauth;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import com.oauth.pojo.API;
import com.oauth.pojo.GetCourses;
import com.oauth.pojo.WebAutomation;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class OauthExample {
	public static void main(String[] args) throws InterruptedException 
	{
	DesiredCapabilities cap= DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		//User Signed into Google by hitting google authentication and get the code(access_code)
        File f = new File("chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", f.getAbsolutePath());
		System.out.println(f.getAbsolutePath());
		WebDriver driver= new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope="
				+ "https://www.googleapis.com/auth/userinfo.email&auth_url="
				+ "https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri="
				+ "https://rahulshettyacademy.com/getCourse.php&state=verifyfjdss]");
		
		Thread.sleep(4000);
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("testmfino005@gmail.com");
		driver.findElement(By.xpath("//*[text()='Next']")).click();
		
		Thread.sleep(4000);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("mFino@3600");
		driver.findElement(By.xpath("//*[text()='Next']")).click();
		Thread.sleep(4000);
		System.out.println("*********************************");
		String completeURL=driver.getCurrentUrl();
		System.out.println("Complete URL is "+completeURL);
	//String completeURL="https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2FvQHSBt_-V0tLy4PbWbpD0RZRRSeOYxOObQtYk3PetGGVOHMipws30lxNUTtzqzBoK-1H7i79EOoh7jfQDgUAfsI&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none&session_state=2394b0a1b2f2bad3e9ed97848635ebfabfea5293..5ede#";
	//String comurl="https://accounts.google.com/signin/v2/challenge/ipp?client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&as=vSAhNxHWlBBkASOWmp2I1g&destination=https%3A%2F%2Frahulshettyacademy.com&approval_state=!ChRNTV9PMDFUeWVJOTJYMllrZmgychIfNHl1N25QN0l0QTRkOEhuU1JuY2dubW9lTndEVC14WQ%E2%88%99AJDr988AAAAAXiV2Xm23nzy1KW5ZqtXGhPy4KYpyj_SI&oauthgdpr=1&xsrfsig=ChkAeAh8T0LdgOibSWkd5t_TMUo22Mw-qea_Eg5hcHByb3ZhbF9zdGF0ZRILZGVzdGluYXRpb24SBXNvYWN1Eg9vYXV0aHJpc2t5c2NvcGU&flowName=GeneralOAuthFlow&TL=APDPHBAxiumf8f6YZB3BZFAwjETeqlen0a6mvlWn9CFe_U1Nz9sXbieNXeYH5GbI&cid=5&navigationDirection=forward";
		
		String partialURL=completeURL.split("code=")[1];
		System.out.println(partialURL);
		String access_code=partialURL.split("&scope")[0];
		System.out.println(access_code);
		
		//System.out.println(access_code);

		
		//Here Generating access_token code using google oauth code.
	/*
		 * client id and client secret code will provide by resource owner.
		 * Here Resource Owner is Google. Means Rahul Shetty academy api internally calling google users to login.
		 * Grant type used here authorization code. Grant type basically is to interact two api's.
		 * state and session are required parameters in this case.
		 * redirect url is nothing but an end point
		 */
		String accessTokenResponse=	given().urlEncodingEnabled(false)
				.queryParams("code",access_code)
				.queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("grant_type","authorization_code")
				.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
				.when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		System.out.println("Response is "+accessTokenResponse);
			JsonPath js=new JsonPath(accessTokenResponse);
			String accessToken=js.getString("access_token");
	
			if(accessToken==null)
			{
				System.out.println("Access token is null");
			}
			else
			{
				System.out.println("************************************");
				System.out.println("Access Token or code generated using google oauth is "+accessToken);

				
				//Login into rahul sheey academy using gmail account instead of creating new account.
				//Here Used access token which is generated using oauth of google.
				//converting response into GetCourcse Java class. So that this class will take care of validating response object.
				//Here expect()..defaultParser(Parser.JSON) will return json if response header is application/json we can avoid default header. If it is application/text then we need to write defaultParser(Parser.json). In our example it is text. so we written this method.
               //Response will store in gc object of GetCourse class. So that we can required object from response.
				   GetCourses gc=
						   given().
				              queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON).
				            when().
				               get("https://rahulshettyacademy.com/getCourse.php").as(GetCourses.class);
				   //Here we are deserialized means getting data from response and storing in JAVA Object. Whereas Serialization is used to set JAVA object to api as a body.

		         System.out.println(gc.getLinkedIn());
		         System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());

		         //above we used get(index). But in future if any object get inserted then index value might get to avoid this please find the below code.
				   List<API> apicourses= gc.getCourses().getApi();
				   for(int i=0;i<=apicourses.size();i++)
				   {
					   System.out.println(apicourses.get(i).getCourseTitle().equals("SoapUI webservices testing"));
				   }
				   
				   
				   Thread.sleep(5000);
		
				   String ExpectedCourses[]={"Selenium", "Protractor", "Cypress"};
				   ArrayList<String> a= new ArrayList<>();
				 List<WebAutomation> coursetitles= gc.getCourses().getWebAutomation();
				 for(int i=0;i<=coursetitles.size();i++)
				 {
					 //here we are storing list of courses(actual courses) in array a.
					 a.add(coursetitles.get(i).getCourseTitle());
				 }
				 //Here Storing ExpectedList in other list using Arrays()
				 List<String> expectedlist=Arrays.asList(ExpectedCourses);
				 Assert.assertTrue(a.equals(expectedlist));
			}
		


	}

}
=======
package com.oauth;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import com.pojo.API;
import com.pojo.GetCourses;
import com.pojo.WebAutomation;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class OauthExample {
	public static void main(String[] args) throws InterruptedException 
	{
	DesiredCapabilities cap= DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		//User Signed into Google by hitting google authentication and get the code(access_code)
        File f = new File("chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", f.getAbsolutePath());
		System.out.println(f.getAbsolutePath());
		WebDriver driver= new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope="
				+ "https://www.googleapis.com/auth/userinfo.email&auth_url="
				+ "https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri="
				+ "https://rahulshettyacademy.com/getCourse.php&state=verifyfjdss]");
		
		Thread.sleep(4000);
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("testmfino005@gmail.com");
		driver.findElement(By.xpath("//*[text()='Next']")).click();
		
		Thread.sleep(4000);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("mFino@3600");
		driver.findElement(By.xpath("//*[text()='Next']")).click();
		Thread.sleep(4000);
		System.out.println("*********************************");
		String completeURL=driver.getCurrentUrl();
		System.out.println("Complete URL is "+completeURL);
	//String completeURL="https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2FvQHSBt_-V0tLy4PbWbpD0RZRRSeOYxOObQtYk3PetGGVOHMipws30lxNUTtzqzBoK-1H7i79EOoh7jfQDgUAfsI&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none&session_state=2394b0a1b2f2bad3e9ed97848635ebfabfea5293..5ede#";
	//String comurl="https://accounts.google.com/signin/v2/challenge/ipp?client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&as=vSAhNxHWlBBkASOWmp2I1g&destination=https%3A%2F%2Frahulshettyacademy.com&approval_state=!ChRNTV9PMDFUeWVJOTJYMllrZmgychIfNHl1N25QN0l0QTRkOEhuU1JuY2dubW9lTndEVC14WQ%E2%88%99AJDr988AAAAAXiV2Xm23nzy1KW5ZqtXGhPy4KYpyj_SI&oauthgdpr=1&xsrfsig=ChkAeAh8T0LdgOibSWkd5t_TMUo22Mw-qea_Eg5hcHByb3ZhbF9zdGF0ZRILZGVzdGluYXRpb24SBXNvYWN1Eg9vYXV0aHJpc2t5c2NvcGU&flowName=GeneralOAuthFlow&TL=APDPHBAxiumf8f6YZB3BZFAwjETeqlen0a6mvlWn9CFe_U1Nz9sXbieNXeYH5GbI&cid=5&navigationDirection=forward";
		
		String partialURL=completeURL.split("code=")[1];
		System.out.println(partialURL);
		String access_code=partialURL.split("&scope")[0];
		System.out.println(access_code);
		
		//System.out.println(access_code);

		
		//Here Generating access_token code using google oauth code.
	/*
		 * client id and client secret code will provide by resource owner.
		 * Here Resource Owner is Google. Means Rahul Shetty academy api internally calling google users to login.
		 * Grant type used here authorization code. Grant type basically is to interact two api's.
		 * state and session are required parameters in this case.
		 * redirect url is nothing but an end point
		 */
		String accessTokenResponse=	given().urlEncodingEnabled(false)
				.queryParams("code",access_code)
				.queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("grant_type","authorization_code")
				.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
				.when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		System.out.println("Response is "+accessTokenResponse);
			JsonPath js=new JsonPath(accessTokenResponse);
			String accessToken=js.getString("access_token");
	
			if(accessToken==null)
			{
				System.out.println("Access token is null");
			}
			else
			{
				System.out.println("************************************");
				System.out.println("Access Token or code generated using google oauth is "+accessToken);

				
				//Login into rahul sheey academy using gmail account instead of creating new account.
				//Here Used access token which is generated using oauth of google.
				//converting response into GetCourcse Java class. So that this class will take care of validating response object.
				//Here expect()..defaultParser(Parser.JSON) will return json if response header is application/json we can avoid default header. If it is application/text then we need to write defaultParser(Parser.json). In our example it is text. so we written this method.
               //Response will store in gc object of GetCourse class. So that we can required object from response.
				   GetCourses gc=
						   given().
				              queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON).
				            when().
				               get("https://rahulshettyacademy.com/getCourse.php").as(GetCourses.class);

				   //Here we are deserialized means getting data from response and storing in JAVA Object. Whereas Serialization is used to set JAVA object to api as a body.
		         System.out.println(gc.getLinkedIn());
		         System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());

		         //above we used get(index). But in future if any object get inserted then index value might get to avoid this please find the below code.
				   List<API> apicourses= gc.getCourses().getApi();
				   for(int i=0;i<=apicourses.size();i++)
				   {
					   System.out.println(apicourses.get(i).getCourseTitle().equals("SoapUI webservices testing"));
				   }
				   
				   
				   Thread.sleep(5000);
		
				   String ExpectedCourses[]={"Selenium", "Protractor", "Cypress"};
				   ArrayList<String> a= new ArrayList<>();
				 List<WebAutomation> coursetitles= gc.getCourses().getWebAutomation();
				 for(int i=0;i<=coursetitles.size();i++)
				 {
					 //here we are storing list of courses(actual courses) in array a.
					 a.add(coursetitles.get(i).getCourseTitle());
				 }
				 //Here Storing ExpectedList in other list using Arrays()
				 List<String> expectedlist=Arrays.asList(ExpectedCourses);
				 Assert.assertTrue(a.equals(expectedlist));
			}
		


	}

}
>>>>>>> a04d6831065b592c50dd19284908c267b0afb04e
