package com.overstained.quote.io.exceptions;

import com.overstained.quote.business.logic.exceptions.QuoteException;

public class CSVFileNotAccessibleException extends QuoteException {
	private static final long serialVersionUID = 1L;
	
	private static final String FILE_NOT_ACCESSIBLE = "quote.file.not.accessible";

	public CSVFileNotAccessibleException(String path) {
		super(FILE_NOT_ACCESSIBLE, path);
	}

}
