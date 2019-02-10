package com.overstained.quote.io.exceptions;

import com.overstained.quote.business.logic.exceptions.QuoteException;

public class CSVInvalidFileException extends QuoteException {
	private static final long serialVersionUID = 1L;
	
	private static final String INVALID_FILE = "quote.file.invalid";

	public CSVInvalidFileException(String path) {
		super(INVALID_FILE, path);
	}

}
