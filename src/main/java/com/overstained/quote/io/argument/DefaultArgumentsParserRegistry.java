package com.overstained.quote.io.argument;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import com.overstained.quote.io.QuoteArgument;
import com.overstained.quote.io.exceptions.ArgumentNotRegisteredException;

public class DefaultArgumentsParserRegistry implements ArgumentsParserRegistry {
	public final Map<QuoteArgument, ArgumentsResolver> configurationMap = new EnumMap<>(QuoteArgument.class);

	@Override
	public void registerArguments(ArgumentsResolver configurationAdjuster, QuoteArgument ...arguments) {
		if(arguments != null) {
			for(QuoteArgument argument: arguments) {
				configurationMap.put(argument, configurationAdjuster);
			}
		}
	}
	
	@Override
	public ArgumentsResolver retrieveConfigurationModifier(QuoteArgument argument) {
		if(configurationMap.containsKey(argument)) {
			return configurationMap.get(argument);
		}
		throw new ArgumentNotRegisteredException();
	}
	
	@Override
	public Set<QuoteArgument> retrieveRegisteredArguments() {
		return configurationMap.keySet();
	}
	
}
