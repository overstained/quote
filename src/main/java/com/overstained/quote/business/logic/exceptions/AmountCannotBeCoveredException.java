package com.overstained.quote.business.logic.exceptions;

public class AmountCannotBeCoveredException extends QuoteException {
	private static final long serialVersionUID = 1L;
	
	private static final String EXCEEDING_AMOUNT = "quote.exceeding.amount";

	public AmountCannotBeCoveredException(int amount) {
		super(EXCEEDING_AMOUNT, amount);
	}
}
