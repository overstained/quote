package com.overstained.quote.io.exceptions;

import com.overstained.quote.business.logic.exceptions.QuoteException;

public class InvalidAmountException extends QuoteException {
	private static final long serialVersionUID = 1L;
	
	private static final String INVALID_AMOUNT = "quote.invalid.amount";

	public InvalidAmountException(int amount) {
		super(INVALID_AMOUNT, amount);
	}
	
	public InvalidAmountException(String amount) {
		super(INVALID_AMOUNT, amount);
	}
}
