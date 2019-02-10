package com.overstained.quote.io.argument;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.overstained.quote.business.logic.QuoteConfiguration;
import com.overstained.quote.io.QuoteArgument;
import com.overstained.quote.io.csv.FilePathResolver;
import com.overstained.quote.io.exceptions.InvalidAmountException;
import com.overstained.quote.io.exceptions.NotEnoughArgumentsException;
import com.overstained.quote.io.exceptions.NotEnoughInfoException;
import com.overstained.quote.io.exceptions.UnresolvedArgumentsException;

public class ArgumentParserUnitTest {

	private ArgumentsParser argumentsParser;
	private Path pathMock;

	@Before
	public void setUp() {
		argumentsParser = spy(ArgumentsParser.class);
		FilePathResolver pathResolverMock = mock(FilePathResolver.class);
		argumentsParser.setFilePathResolver(pathResolverMock);
		pathMock = mock(Path.class);
		given(pathResolverMock.resolve(any(String.class))).willReturn(pathMock);
	}

	@Test
	public void itShouldThrowWhenNoArgumentsAreProvided() {
		willThrow(NotEnoughArgumentsException.class).given(argumentsParser).resolve(Collections.emptyList());
	}

	@Test
	public void itShouldThrowWhenSingleHelpArgumentIsProvided() {
		List<String> arguments = new LinkedList<>();
		arguments.add(QuoteArgument.HELP.getArgName());

		willThrow(NotEnoughInfoException.class).given(argumentsParser).resolve(arguments);
	}
	
	@Test
	public void itShouldThrowWhenFilenameAndAmountNotProvided() {
		List<String> arguments = new LinkedList<>();
		arguments.add("fsdfa");

		willThrow(NotEnoughArgumentsException.class).given(argumentsParser).resolve(arguments);
	}
	
	@Test
	public void itShouldThrowWhenProvidedWithInvalidAmount() {
		List<String> arguments = Arrays.asList("test.csv", "12s");

		willThrow(InvalidAmountException.class).given(argumentsParser).resolve(arguments);
	}

	@Test
	public void itShouldUpdateCSVFileNameAndAmountWhenProvidedWithFileAndValidAmount() {
		List<String> arguments = Arrays.asList("test.csv", "123");

		QuoteConfiguration configuration = argumentsParser.resolve(arguments);

		assertThat(configuration.getCsvFilePath(), equalTo(pathMock));
		assertThat(configuration.getDesiredAmount(), equalTo(123));
	}

	@Test
	public void itShouldThrowWhenInvalidArgumentsArePassed() {
		List<String> arguments = Arrays.asList("test.csv", "123", "-p123", "--depth124");
		mockAndSetArgumentsRegistry(QuoteArgument.DELIMITER_LONG);

		willThrow(UnresolvedArgumentsException.class).given(argumentsParser).resolve(arguments);
	}

	@Test
	public void itShouldSuccessfullyReturnConfigurationWhenValidArgument() {
		List<String> arguments = Arrays.asList("test.csv", "123", "-d;");
		mockAndSetArgumentsRegistry(QuoteArgument.DELIMITER);

		argumentsParser.resolve(arguments);
	}

	private void mockAndSetArgumentsRegistry(QuoteArgument... arguments) {
		ArgumentsParserRegistry argumentsRegistryMock = mock(ArgumentsParserRegistry.class);
		Set<QuoteArgument> registeredArguments = new HashSet<>();
		registeredArguments.addAll(Arrays.asList(arguments));
		given(argumentsRegistryMock.retrieveRegisteredArguments()).willReturn(registeredArguments);
		given(argumentsRegistryMock.retrieveConfigurationModifier(any(QuoteArgument.class))).willReturn((m, n) -> {
		});
		argumentsParser.setArgumentParserRegistry(argumentsRegistryMock);
	}

}
