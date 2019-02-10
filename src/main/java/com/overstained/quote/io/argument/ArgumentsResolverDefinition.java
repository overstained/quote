package com.overstained.quote.io.argument;

import com.overstained.quote.business.logic.QuoteConfiguration;
import com.overstained.quote.io.QuoteArgument;
import com.overstained.quote.io.exceptions.UnresolvedArgumentsException;

public class ArgumentsResolverDefinition {
	
	public static ArgumentsParserRegistry registerResolvers() {
		ArgumentsParserRegistry argumentsRegistry =  new DefaultArgumentsParserRegistry();
		argumentsRegistry.registerArguments(ArgumentsResolverDefinition::resolveCharset, QuoteArgument.CHARSET_LONG);
		argumentsRegistry.registerArguments(ArgumentsResolverDefinition::resolveDelimiter, QuoteArgument.DELIMITER, QuoteArgument.DELIMITER_LONG);
		argumentsRegistry.registerArguments(ArgumentsResolverDefinition::resolveMapping, QuoteArgument.MAP, QuoteArgument.MAP_LONG);
		return argumentsRegistry;
	}
	
	private static void resolveCharset(String charset, QuoteConfiguration quoteConfiguration) {
		quoteConfiguration.setCharset(charset);
	}
	
	private static void resolveDelimiter(String delimiter, QuoteConfiguration quoteConfiguration) {
		quoteConfiguration.setDelimiter(delimiter);
	}
	
	private static void resolveMapping(String mapping, QuoteConfiguration quoteConfiguration) {
		if(!mapping.contains(":")) {
			throw new UnresolvedArgumentsException(mapping);
		}
		String[] mappingHeaders = mapping.split(":");
		switch(mappingHeaders[0]) {
			case "RATE":
				quoteConfiguration.setRateHeader(mappingHeaders[1]);
			break;
			case "LENDER":
				quoteConfiguration.setLenderHeader(mappingHeaders[1]);
			case "AVAILABLE":
				quoteConfiguration.setAvailableHeader(mappingHeaders[2]);
			default:
				throw new UnresolvedArgumentsException(mapping);
		}
	}
}
