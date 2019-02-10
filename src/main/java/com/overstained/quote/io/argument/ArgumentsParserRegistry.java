package com.overstained.quote.io.argument;

import java.util.Set;

import com.overstained.quote.io.QuoteArgument;

public interface ArgumentsParserRegistry {
	void registerArguments(ArgumentsResolver configurationAdjuster, QuoteArgument ...arguments);
	ArgumentsResolver retrieveConfigurationModifier(QuoteArgument argument);
	Set<QuoteArgument> retrieveRegisteredArguments();
}
