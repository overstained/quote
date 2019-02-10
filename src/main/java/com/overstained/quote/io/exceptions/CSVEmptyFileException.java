package com.overstained.quote.io.exceptions;

import com.overstained.quote.business.logic.exceptions.QuoteException;

public class CSVEmptyFileException extends QuoteException {
	private static final long serialVersionUID = 1L;
	
	private static final String EMPTY_FILE = "quote.file.empty";

	public CSVEmptyFileException(String path) {
		super(EMPTY_FILE, path);
	}

}
