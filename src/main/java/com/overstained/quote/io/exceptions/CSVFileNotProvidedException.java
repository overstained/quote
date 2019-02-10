package com.overstained.quote.io.exceptions;

import com.overstained.quote.business.logic.exceptions.QuoteException;

public class CSVFileNotProvidedException extends QuoteException {
	private static final long serialVersionUID = 1L;
	
	private static final String FILE_NOT_PROVIDED = "quote.file.not.provided";

	public CSVFileNotProvidedException() {
		super(FILE_NOT_PROVIDED);
	}

}
