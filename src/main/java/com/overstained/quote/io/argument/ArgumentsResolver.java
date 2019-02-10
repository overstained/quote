package com.overstained.quote.io.argument;

import com.overstained.quote.business.logic.QuoteConfiguration;

@FunctionalInterface
public interface ArgumentsResolver {
	void resolve(String value, QuoteConfiguration quoteConfiguration);
}
