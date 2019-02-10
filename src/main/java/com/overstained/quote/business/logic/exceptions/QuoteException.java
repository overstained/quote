package com.overstained.quote.business.logic.exceptions;

import com.overstained.quote.io.TemplateResolver;

public class QuoteException extends RuntimeException {
	private static final String HELP_REDIRECT = "$help_redirect";
	private static final String QUOTE_HELP_REDIRECT = "quote.help.redirect";

	private static final long serialVersionUID = 1L;
	
	private String templateKey;
	private Object[] arguments;
	
	public QuoteException(String templateKey) {
		this.templateKey = templateKey;
	}
	
	public QuoteException(String templateKey, Object... arguments) {
		this(templateKey);
		this.arguments = arguments;
	}
	
	@Override
	public String toString() {
		return TemplateResolver.getInstance().resolve(templateKey, arguments).replace(HELP_REDIRECT, System.lineSeparator() +
			TemplateResolver.getInstance().resolve(QUOTE_HELP_REDIRECT));
	}
}
