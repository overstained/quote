package com.overstained.quote;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.overstained.quote.business.logic.QuoteCalculator;
import com.overstained.quote.business.logic.QuoteConfiguration;
import com.overstained.quote.business.logic.exceptions.QuoteException;
import com.overstained.quote.domain.Lender;
import com.overstained.quote.domain.Quote;
import com.overstained.quote.io.QuoteReader;
import com.overstained.quote.io.TemplateResolver;
import com.overstained.quote.io.argument.ArgumentsParser;
import com.overstained.quote.io.argument.ArgumentsResolverDefinition;

public class App {
	private static final String QUOTE = "quote";

	public static void main(String[] args) {
		try {
			ArgumentsParser argumentsParser = new ArgumentsParser();
			argumentsParser.setArgumentParserRegistry(ArgumentsResolverDefinition.registerResolvers());
			QuoteConfiguration configuration = argumentsParser.resolve(Arrays.asList(args));
			QuoteReader quoteReader = new QuoteReader(configuration);
			List<Lender> lenders = quoteReader.extractLenders();
			QuoteCalculator calculator = new QuoteCalculator(configuration);
			Quote quote = calculator.compute(lenders);
			System.out.println(TemplateResolver.getInstance().resolve(QUOTE, quote.getDesiredAmount(),
					quote.getRate().multiply(BigDecimal.valueOf(100)).setScale(1, BigDecimal.ROUND_HALF_UP),
					quote.getMonthlyPayment().setScale(2, BigDecimal.ROUND_HALF_UP),
					quote.getTotalPayment().setScale(2, BigDecimal.ROUND_HALF_UP)));
		} catch (QuoteException qe) {
			System.out.println(qe);
		}
	}
}
