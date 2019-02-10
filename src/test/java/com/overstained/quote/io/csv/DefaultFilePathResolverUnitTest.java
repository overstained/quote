package com.overstained.quote.io.csv;

import org.junit.Before;
import org.junit.Test;

import com.overstained.quote.io.csv.DefaultFilePathResolver;
import com.overstained.quote.io.csv.FilePathResolver;
import com.overstained.quote.io.exceptions.CSVFileNotFoundException;
import com.overstained.quote.io.exceptions.CSVFileNotProvidedException;

import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.spy;

public class DefaultFilePathResolverUnitTest {
	
	private FilePathResolver pathResolver;
	
	@Before
	public void setUp() {
		pathResolver = spy(DefaultFilePathResolver.class);
	}
	
	@Test
	public void itShouldThrowWhenProvidedFilePathIsNull() {
		willThrow(CSVFileNotProvidedException.class).given(pathResolver).resolve(null);
	}
	
	@Test
	public void itShouldThrowWhenProvidedFilePathIsEmpty() {
		willThrow(CSVFileNotProvidedException.class).given(pathResolver).resolve(null);
		
	}
	
	@Test
	public void itShouldThrowWhenFileAtPathIsNotFound() {
		willThrow(CSVFileNotFoundException.class).given(pathResolver).resolve("test.csv");
	}
}
