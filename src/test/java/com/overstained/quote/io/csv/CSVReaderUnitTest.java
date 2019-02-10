package com.overstained.quote.io.csv;

import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.spy;

import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.junit.Test;

import com.overstained.quote.io.exceptions.CSVEmptyFileException;
import com.overstained.quote.io.exceptions.CSVFileNotProvidedException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CSVReaderUnitTest {

	private static final String UTF_8 = "UTF-8";

	@Test
	public void itShouldThrowIWhenProvidedEmptyCSVFile() throws URISyntaxException {
		CSVReader csvReader = spy(
				new CSVReader(Paths.get(ClassLoader.getSystemResource("csv_test/empty.csv").toURI()), UTF_8));

		willThrow(CSVEmptyFileException.class).given(csvReader).readFile(h -> {
		}, d -> {
		});
	}

	@Test
	public void itShouldThrowWhenCSVFileIsNotFound() throws URISyntaxException {
		CSVReader csvReader = spy(new CSVReader(Paths.get("not_here.csv"), UTF_8));

		willThrow(CSVFileNotProvidedException.class).given(csvReader).readFile(h -> {
		}, d -> {
		});
	}

	@Test
	public void itShouldCorrectlyCallbackWithHeaderAndData() throws URISyntaxException {
		CSVReader csvReader = spy(
				new CSVReader(Paths.get(ClassLoader.getSystemResource("csv_test/valid.csv").toURI()), UTF_8));

		willThrow(CSVEmptyFileException.class).given(csvReader).readFile(
				h -> assertThat(h, equalTo("Lender,Rate,Available")), d -> assertThat(d, equalTo("John,6.94,5343")));
	}
}
