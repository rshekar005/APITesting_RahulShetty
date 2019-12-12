package com.basics;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
/**
 * 
 * @author Shekhar
 * In this class passed json file directly without delimiters.
 * If there is static json then we can send directly.
 * 	//generateStringFromResponse()--This method is used to read json data or excel and will convert that data to a byte and then to string. returns a string and will pass as a payload to request.
 */
public class StaticJson 
{
	@Test
    public void addBook() throws IOException 
	{
		RestAssured.baseURI=CommonMethods.readProperty("Library_hostname");
		Response resp= given().
				          header("Content-Type", "application/json").
				          body(CommonMethods.generateStringFromResponse(System.getProperty("user.dir")+"/addbook.json")).
				          
				       when().                   
				           post(CommonMethods.readProperty("Add_book_resource")).
				       then(). 
				        extract().response();
		//System.out.println(resp.asString());
		CommonMethods.jsonData(resp);
		System.out.println("******************************");
		
	}

}
