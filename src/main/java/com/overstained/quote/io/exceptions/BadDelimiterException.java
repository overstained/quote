package com.overstained.quote.io.exceptions;

import com.overstained.quote.business.logic.exceptions.QuoteException;

public class BadDelimiterException extends QuoteException {
	private static final long serialVersionUID = 1L;
	
	private static final String BAD_DELIMITER = "quote.file.bad.delimiter";

	public BadDelimiterException(String delimiter) {
		super(BAD_DELIMITER, delimiter);
	}

}
