package com.overstained.quote.business.logic;

import java.nio.file.Path;
import java.util.Properties;

import com.overstained.quote.utils.PropertiesLoader;

import lombok.Data;

@Data
public class QuoteConfiguration {
	
	private static final String QUOTE_BORROW_TERM_MONTHS = "quote.borrow.term.months";
	private static final String QUOTE_INCREMENT = "quote.increment";
	private static final String QUOTE_MIN_BORROW = "quote.min.borrow";
	private static final String QUOTE_MAX_BORROW = "quote.max.borrow";
	private static final String QUOTE_CHARSET = "quote.charset";
	private static final String QUOTE_AVAILABLE_HEADER = "quote.available.header";
	private static final String QUOTE_RATE_HEADER = "quote.rate.header";
	private static final String QUOTE_LENDER_HEADER = "quote.lender.header";
	private static final String QUOTE_DELIMITER = "quote.delimiter";
	private static final String CONFIGURATION_PROPERTIES = "configuration.properties";
	
	private Path csvFilePath;
	private int desiredAmount;
	private String delimiter;
	private String rateHeader;
	private String availableHeader;
	private String lenderHeader;
	private String charset;
	private int maxBorrowingAmount;
	private int minBorrowingAmount;
	private int amountIncrement;
	private int borrowingTermInMonths;

	public QuoteConfiguration() {
		Properties properties = PropertiesLoader.getInstance().load(CONFIGURATION_PROPERTIES);
		if (properties != null) {
			this.delimiter = properties.getProperty(QUOTE_DELIMITER);
			this.lenderHeader = properties.getProperty(QUOTE_LENDER_HEADER);
			this.rateHeader = properties.getProperty(QUOTE_RATE_HEADER);
			this.availableHeader = properties.getProperty(QUOTE_AVAILABLE_HEADER);
			this.charset = properties.getProperty(QUOTE_CHARSET);
			this.maxBorrowingAmount = Integer.parseInt(properties.getProperty(QUOTE_MAX_BORROW));
			this.minBorrowingAmount = Integer.parseInt(properties.getProperty(QUOTE_MIN_BORROW));
			this.amountIncrement = Integer.parseInt(properties.getProperty(QUOTE_INCREMENT));
			this.borrowingTermInMonths = Integer.parseInt(properties.getProperty(QUOTE_BORROW_TERM_MONTHS));
		}
	}
}
