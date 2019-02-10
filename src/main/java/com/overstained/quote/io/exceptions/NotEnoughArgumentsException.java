package com.overstained.quote.io.exceptions;

import com.overstained.quote.business.logic.exceptions.QuoteException;

public class NotEnoughArgumentsException  extends QuoteException {
	private static final long serialVersionUID = 1L;
	
	private static final String MISSING_ARGUMENTS = "quote.arguments.missing";

	public NotEnoughArgumentsException() {
		super(MISSING_ARGUMENTS);
	}
}
