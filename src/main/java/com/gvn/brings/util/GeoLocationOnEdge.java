package com.gvn.brings.util;

import java.util.ArrayList;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class GeoLocationOnEdge {

	
	  public String checkLocationOnEdge() throws Exception {
		    ScriptEngineManager manager = new ScriptEngineManager();
		    ScriptEngine engine = manager.getEngineByName("JavaScript");
		    if (!(engine instanceof Invocable)) {
		      System.out.println("Invoking methods is not supported.");
		      //return;
		    }
		    Invocable inv = (Invocable) engine;
		    String scriptPath = "E:/Soumiyajit/Soumiyajit Project/GeoHelper.js";

		    engine.eval("load('" + scriptPath + "')");

		    Object geoFunction = engine.get("geoFunction");

		    ArrayList<String> listOfCoordinates= new ArrayList<>();
		    Object addResult = inv.invokeMethod(geoFunction, "checkPosition", "46.0","-125.9");
		    
		    System.out.println(addResult);
		    return addResult.toString();
		    
		  }
	  
	  public static void main(String args[])
	  {
		  GeoLocationOnEdge gd=new GeoLocationOnEdge();
		  try {
			gd.checkLocationOnEdge();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }

}
