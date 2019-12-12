package com.basics;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class CommonMethods 
{
	public static String readProperty(String key) throws IOException
	{
		String property=System.getProperty("user.dir")+"/src/com/basics/config.properties";
		FileInputStream fis= new FileInputStream(property);
		Properties prop = new Properties();
		prop.load(fis);
		String value= prop.getProperty(key);
		System.out.println("==============="+value+"===============");
		return value;
	}
	/*This method is used to convert rawdata to xml*/
	public static XmlPath xmlData(Response res)
	{
		String s=res.asString();// Response from server side will come as raw data need to change to string and then covert to required data.
		XmlPath xml = new XmlPath(s);
		System.out.println(xml.prettify());
		return xml;	
	}
	/*This method is used to convert rawdata to json*/
	public static JsonPath jsonData(Response res)
	{
		String s=res.asString();// Response from server side will come as raw data need to change to string and then covert to required data.
		JsonPath js= new JsonPath(s);
		System.out.println(js.prettify());
		return js;	
	}
	
	//This method is used to read json data or excel and will convert that data to a byte and then to string. returns a string and will pass as a payload to request.
   public static String generateStringFromResponse(String uri) throws IOException
   {
	   return new String(Files.readAllBytes(Paths.get(uri))) ;
   }

}
