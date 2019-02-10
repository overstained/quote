package com.overstained.quote.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
	private static PropertiesLoader instance;
	
	private PropertiesLoader() {}
	
	public static PropertiesLoader getInstance() {
		if(instance == null) {
			instance = new PropertiesLoader();
		}
		return instance;
	}
	
	public Properties load(String fileName) {
		Properties properties = new Properties();
		try {
		    properties.load(ClassLoader.getSystemResourceAsStream(fileName));
		    return properties;
		} 
		catch (IOException ex) {
		    return null;
		}
	}
}
