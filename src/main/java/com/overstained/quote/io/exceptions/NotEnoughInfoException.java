package com.overstained.quote.io.exceptions;

import com.overstained.quote.business.logic.exceptions.QuoteException;

public class NotEnoughInfoException extends QuoteException {
	private static final long serialVersionUID = 1L;
	
	private static final String HELP_REQUESTED = "quote.help";

	public NotEnoughInfoException() {
		super(HELP_REQUESTED);
	}
}
