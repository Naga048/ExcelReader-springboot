package com.poc.h2.demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExUtil {
	
	public static boolean isString(String value)
	{
		Pattern p = Pattern.compile("^[a-zA-Z\\s+]*$");//. represents single character  
		if(value.contains("\""))
			value = value.replaceAll("^\"|\"$", "");
		Matcher m = p.matcher(value);  
		boolean macher = m.matches();  
		
		
		return macher;
		
	}
	
	public static boolean isDouble(String value)
	{
		Pattern p = Pattern.compile("[0-9]+(\\.){0,1}[0-9]*");//. represents single character  
		if(value.contains("\""))
			value = value.replaceAll("^\"|\"$", "");
		Matcher m = p.matcher(value);  
		boolean macher = m.matches();  
		
		
		return macher;
		
	}
	
	public static void main(String[] args) {
		System.out.println(isString("Internet Banking"));
	}

}
