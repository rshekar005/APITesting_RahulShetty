package com.basics;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.payload.PayLoads;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AddBook {
	@Test(dataProvider="getData")
	public void addBook(String Nameofthebook,String isbn,String aisle,String authorname) throws IOException 
	{
		RestAssured.baseURI=CommonMethods.readProperty("Library_hostname");
		Response resp= given().
				          header("Content-Type", "application/json").
				          body(PayLoads.addBookPayload(Nameofthebook, isbn, aisle, authorname)).
				       when().
				           post(CommonMethods.readProperty("Add_book_resource")).
				       then(). 
				        extract().response();
		//System.out.println(resp.asString());
		CommonMethods.jsonData(resp);
		System.out.println("******************************");
		
	}
    @DataProvider
	public Object[][] getData()
	{
    	//Single dimension array -- collection of elements. Ex: {raja,shekar}
    	//Multiple dimension array -- collection of arrays. Ex: {{raja,shekar},{naga,raju},{}..}
    	//Sending 3 different set of arrays to object[][].
		return new Object[][]
				{
			      {"Bahubali","Raja","1234","Rajashekar"},
			      {"Saaho","Prabhas","12341","kolor"},
			      {"Kaabali","Rajnikanth","123341","Aishwarya"}
			    };
	}
	
	//Passing Static json(addbook.json) as a payload to library api
    
    
}
