package com.basics;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.http.annotation.ThreadingBehavior;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



public class GooglePlaceAPI 
{
	

	public static void main(String args[]) throws IOException
	{
		
		RestAssured.baseURI=CommonMethods.readProperty("hostname");
		
		//Adding place to google maps and here log().all() gives all the responses.
	 Response res=given().log().all().
	                queryParam("key", CommonMethods.readProperty("API_key")).
	                body("{"+
   " \"location\":{"+
       " \"lat\" : -38.383494,"+
       " \"lng\" : 33.427362 "+
    "},"+
   " \"accuracy\":50,"+
   " \"name\":\"Frontline house\","+
   " \"phone_number\":\"(+91) 983 893 3937\","+
   " \"address\" : \"29, side layout, cohen 09\","+
   " \"types\": [\"shoe park\",\"shop\"],"+
    "\"website\" : \"http://google.com\","+
    "\"language\" : \"French-IN\""+
"}").
	       when().
	           post(CommonMethods.readProperty("add_api_resource")).
	       then().assertThat().statusCode(200).and().
	           contentType(ContentType.JSON).and().
	           body("status", equalTo("OK")).
	       extract().response();
	 
	 //Storing response in string and printing -- Task 1 grab the repsonse
	 String responseString= res.asString();
	 System.out.println(responseString);
	 
	 //Converting string to json -- Jsonpath is a class which is used to convert string to json
	 JsonPath js= new JsonPath(responseString);
	 
	 //prettify() is used to convert String json into correct json
	 System.out.println(js.prettify());
	
	 //grab the placeID.
	 
	 String place_id=js.get("place_id");
	 System.out.println(place_id);
	 
	 //Delete the place id.
	/* System.out.println("=======================Running Delete API=============================");
	 Response resp=
	 given().
	      queryParam("key", readProperty("API_key")).
	      body("{"+
    "\"place_id\":\""+place_id+"\""+       //Sending place id from above.
	    		  "}").
	 when().
	      delete("delete_api_resource").
	  then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().body("status", equalTo("OK")).
	  extract().response();
	 
	 String responsetoString=resp.asString();
	 System.out.println(responsetoString);*/
	 
	 
	      
		

	}

	
}
