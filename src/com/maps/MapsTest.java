package com.maps;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;

import groovyjarjarantlr.collections.List;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class MapsTest 
{
	public static void main(String args[])
	{
		MapPojo m= new MapPojo();
		m.setAccuracy(50);
		m.setAddress("29, side layout, cohen 09");
		m.setName("Rajashekar");
		m.setPhone_number("871220333");
		m.setWebsite("https://Rajashekar");
		m.setLanguage("English");
		
		//Adding list of objects
		ArrayList<String> mylist= new ArrayList<>();
		mylist.add("Kirana Shop");
		mylist.add("Mutton Shop");
		m.setTypes(mylist);
		
		//It is a different pojo class so that we need to create a new method for this class and assign values to that objects and map location object to map object
		
		LocationPojo location= new LocationPojo();
		location.setLat(-38.383494);
		location.setLng(33.427362);
		
		m.setLocation(location);
		
		
		RestAssured.baseURI="http://216.10.245.166";
		Response response=
				given().
				   queryParam("key", "qaclick123").
				   body(m).
                when().
                   post("/maps/api/place/add/json"). 
                then(). 
                    assertThat().statusCode(200). 
                extract().response();
		
		String responseasString=response.asString();
		
		JsonPath json = new JsonPath(responseasString);
		System.out.println(json.prettify());
	
	}

}
