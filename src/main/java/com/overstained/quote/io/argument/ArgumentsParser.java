package com.overstained.quote.io.argument;

import java.util.List;
import java.util.Set;

import com.overstained.quote.business.logic.QuoteConfiguration;
import com.overstained.quote.io.QuoteArgument;
import com.overstained.quote.io.csv.DefaultFilePathResolver;
import com.overstained.quote.io.csv.FilePathResolver;
import com.overstained.quote.io.exceptions.CSVFileNotProvidedException;
import com.overstained.quote.io.exceptions.InvalidAmountException;
import com.overstained.quote.io.exceptions.NotEnoughArgumentsException;
import com.overstained.quote.io.exceptions.NotEnoughInfoException;
import com.overstained.quote.io.exceptions.UnresolvedArgumentsException;

public class ArgumentsParser {
	
	private ArgumentsParserRegistry argumentParserRegistry;
	private FilePathResolver filePathResolver;
	
	public ArgumentsParser() {
		this.filePathResolver = new DefaultFilePathResolver();
	}
	
	public QuoteConfiguration resolve(List<String> arguments) {
		validateArguments(arguments);
		QuoteConfiguration quoteConfiguration = new QuoteConfiguration();
		quoteConfiguration.setCsvFilePath(filePathResolver.resolve(arguments.get(0)));
		quoteConfiguration.setDesiredAmount(Integer.parseInt(arguments.get(1)));
		if(argumentParserRegistry != null) {
			Set<QuoteArgument> registeredArguments = argumentParserRegistry.retrieveRegisteredArguments();
			arguments.stream().skip(2).forEach(arg -> {
				QuoteArgument matchingArgument = registeredArguments
				.stream()
				.filter(regArg -> arg.startsWith(regArg.getArgName()))
				.findFirst()
				.orElseThrow(() -> new UnresolvedArgumentsException(arg));
				argumentParserRegistry
				.retrieveConfigurationModifier(matchingArgument)
				.resolve(arg.replaceFirst("^" + matchingArgument.getArgName(), ""), quoteConfiguration);
			});
		}
		return quoteConfiguration;
	}
	
	void validateArguments(List<String> arguments) {
		if(arguments == null || arguments.isEmpty()) {
			throw new CSVFileNotProvidedException();
		}
		if(arguments.get(0).equals(QuoteArgument.HELP.getArgName())) {
			throw new NotEnoughInfoException();
		}
		if(arguments.size() < 2) {
			throw new NotEnoughArgumentsException();
		}
		try {
			Integer.parseInt(arguments.get(1));
		} catch (NumberFormatException nfe) {
			throw new InvalidAmountException(arguments.get(1));
		}
	}

	public void setArgumentParserRegistry(ArgumentsParserRegistry argumentParserRegistry) {
		this.argumentParserRegistry = argumentParserRegistry;
	}

	public void setFilePathResolver(FilePathResolver filePathResolver) {
		this.filePathResolver = filePathResolver;
	}
}
