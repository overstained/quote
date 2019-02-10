package com.overstained.quote.io;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.overstained.quote.business.logic.QuoteConfiguration;
import com.overstained.quote.domain.Lender;
import com.overstained.quote.io.csv.CSVReader;
import com.overstained.quote.io.exceptions.BadDelimiterException;
import com.overstained.quote.io.exceptions.CSVInvalidFileException;

public class QuoteReader {
	private static final int LENDER_INDEX = 0;
	private static final int RATE_INDEX = 1;
	private static final int AVAILABLE_INDEX = 2;
	
	private QuoteConfiguration quoteConfiguration;
	private CSVReader csvReader;
	private int maxHeaderIndex;
	private int[] headerPermutation;

	public QuoteReader(QuoteConfiguration quoteConfiguration) {
		this.headerPermutation = new int[3];
		this.quoteConfiguration = quoteConfiguration;
		this.csvReader = new CSVReader(quoteConfiguration.getCsvFilePath(), quoteConfiguration.getCharset());
	}

	public List<Lender> extractLenders() {
		List<Lender> lenders = new LinkedList<>();
		csvReader.readFile(header -> {
			List<String> headerColumns = validateAndSplitHeader(header);
			mapHeader(headerColumns);
		}, data -> lenders.add(validateAndMapToLender(data)));
		return lenders;
	}

	List<String> validateAndSplitHeader(String header) {
		if (!header.contains(quoteConfiguration.getDelimiter())) {
			throw new BadDelimiterException(quoteConfiguration.getDelimiter());
		}
		List<String> headerColumns = Arrays.asList(header.split(quoteConfiguration.getDelimiter()));
		if (headerColumns.size() < 3 ||
			!headerColumns.containsAll(Arrays.asList(quoteConfiguration.getLenderHeader(),
				quoteConfiguration.getRateHeader(), quoteConfiguration.getAvailableHeader()))) {
			throw new CSVInvalidFileException("");
		}
		return headerColumns;
	}
	
	Lender validateAndMapToLender(String dataRow) {
		String[] columnData = dataRow.split(quoteConfiguration.getDelimiter());
		if(columnData.length < maxHeaderIndex) {
			throw new CSVInvalidFileException("");
		}
		return new Lender(columnData[headerPermutation[LENDER_INDEX]], 
				new BigDecimal(columnData[headerPermutation[RATE_INDEX]]),
				Integer.parseInt(columnData[headerPermutation[AVAILABLE_INDEX]]));
	}

	void mapHeader(List<String> headerColumns) {
		headerPermutation[LENDER_INDEX] = headerColumns.indexOf(quoteConfiguration.getLenderHeader());
		headerPermutation[RATE_INDEX] = headerColumns.indexOf(quoteConfiguration.getRateHeader());
		headerPermutation[AVAILABLE_INDEX] = headerColumns.indexOf(quoteConfiguration.getAvailableHeader());
		maxHeaderIndex = Arrays.stream(headerPermutation).max().orElse(0);
	}

}
