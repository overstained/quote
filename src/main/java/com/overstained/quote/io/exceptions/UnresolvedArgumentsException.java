package com.overstained.quote.io.exceptions;

import com.overstained.quote.business.logic.exceptions.QuoteException;

public class UnresolvedArgumentsException extends QuoteException {
	private static final long serialVersionUID = 1L;
	
	private static final String UNRESOLVED_ARGUMENTS = "quote.arguments.unresolved";

	public UnresolvedArgumentsException(Object...arguments) {
		super(UNRESOLVED_ARGUMENTS);
	}

}
