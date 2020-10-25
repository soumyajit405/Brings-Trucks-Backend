package com.gvn.brings.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;





public class GoogleGeoHelper {
	public HashMap<String,String> getLatLng(String address) throws JSONException
	{
		HashMap<String,String> response=new HashMap<>();
		try
		{
			String addressToBeSent=encode(address);
			String url1="https://maps.googleapis.com/maps/api/geocode/json?address="+addressToBeSent+"&key=AIzaSyCRKo8UF_UeA1HWg_xR4gSCjexMpPZCLhQ";
			
			
			URL url = new URL(url1.toString());
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
						OutputStream os = conn.getOutputStream();
		
			os.flush();

			if(conn.getResponseCode()==200)
			{
				System.out.println("success");
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			
		String jsonText = readAll(br);
	      JSONObject json = new JSONObject(jsonText);
			String output;
			Double dist1;
			
			JSONArray al= (JSONArray) json.get("results");
			json=(JSONObject) al.get(0);
			JSONObject geometry=(JSONObject)json.get("geometry");
			
			JSONObject location=(JSONObject)geometry.get("location");
			
			response.put("lat", location.get("lat").toString());
			response.put("lng", location.get("lng").toString());
			//al=(JSONArray)json.getJSONArray("steps");
			conn.disconnect();
			return response;
			
						
						
		

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		 }
		  return response;

	}
	
	
	public String getAddress(String lat,String lng) throws JSONException
	{
		HashMap<String,String> response=new HashMap<>();
		try
		{
			//String addressToBeSent=encode(address);
			String url1="https://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+lng+"&key=AIzaSyCK9fuG_pwM2oswtu6vDVZH5CqjleY-Zvw";
			
			
			URL url = new URL(url1.toString());
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
						OutputStream os = conn.getOutputStream();
		
			os.flush();

			if(conn.getResponseCode()==200)
			{
				System.out.println("success");
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			
		String jsonText = readAll(br);
	      JSONObject json = new JSONObject(jsonText);
			String output;
			Double dist1;
			
			JSONArray al= (JSONArray) json.get("results");
			json=(JSONObject) al.get(0);
			String formattedAddress=(String)json.get("formatted_address");
			
			System.out.println(formattedAddress.replace("'",""));
			
			
			//al=(JSONArray)json.getJSONArray("steps");
			conn.disconnect();
			return formattedAddress;
			
						
						
		

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		 }
		  return "NA";

	}
	public  String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }
 public static void main(String args[])
 {
	 GoogleGeoHelper gh=new GoogleGeoHelper();
	 gh.getAddress("17.4400802","78.34891679999998");
 }
	
 public  String encode(String url)  
 {  
           try {  
                String encodeURL=URLEncoder.encode( url, "UTF-8" );  
                return encodeURL;  
           } catch (UnsupportedEncodingException e) {  
                return "Issue while encoding" +e.getMessage();  
           }  
 }
	

}
