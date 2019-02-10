package com.overstained.quote.io.exceptions;

import com.overstained.quote.business.logic.exceptions.QuoteException;

public class CSVFileNotFoundException extends QuoteException {
	private static final long serialVersionUID = 1L;
	
	private static final String FILE_NOT_FOUND = "quote.file.not.found";

	public CSVFileNotFoundException(String path) {
		super(FILE_NOT_FOUND, path);
	}

}
