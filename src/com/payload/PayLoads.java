package com.payload;

public class PayLoads 
{
	//Dynamic Payload
	public static String addBookPayload(String Nameofthebook,String isbn,String aisle,String author)
	{
		String addbook="{\r\n\"name\":\""+Nameofthebook+"\","
				+ "\r\n\"isbn\":\""+isbn+"\","
				+ "\r\n\"aisle\":\""+aisle+"\","
				+ "\r\n\"author\":\""+author+"\"\r\n}\r\n";
		return addbook;
	}
   
}
