package com.overstained.quote.io;

public enum QuoteArgument {
	HELP("--help"),
	DELIMITER("-d"),
	DELIMITER_LONG("--delimiter"),
	MAP("-m"),
	MAP_LONG("--map"),
	CHARSET_LONG("--charset");
	
	private String argName;
	
	private QuoteArgument(String argName) {
		this.argName = argName;
	}
	
	public String getArgName() {
		return this.argName;
	}
}
