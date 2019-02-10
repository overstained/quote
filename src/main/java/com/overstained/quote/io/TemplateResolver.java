package com.overstained.quote.io;

import java.util.Properties;

import com.overstained.quote.utils.PropertiesLoader;

public class TemplateResolver {
	private static final String TEMPLATES_PROPERTIES = "templates.properties";
	private static TemplateResolver templateResolver;
	private Properties properties;
	
	private TemplateResolver() {
		this.properties = PropertiesLoader.getInstance().load(TEMPLATES_PROPERTIES);
	}
	
	public String resolve(String templateKey, Object... arguments) {
		String template = properties.getProperty(templateKey);
		if(template == null) {
			return "";
		}
		return String.format(template, arguments);
	}
	
	public static TemplateResolver getInstance() {
		if(templateResolver == null) {
			templateResolver = new TemplateResolver();
		}
		return templateResolver;
	}
}
