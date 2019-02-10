package com.overstained.quote.business.logic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.BigDecimalCloseTo.closeTo;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.spy;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.overstained.quote.business.logic.exceptions.AmountCannotBeCoveredException;
import com.overstained.quote.domain.Lender;
import com.overstained.quote.domain.Quote;
import com.overstained.quote.io.exceptions.InvalidAmountException;

public class QuoteCalculatorUnitTest {

	@Test
	public void itShouldThrowWhenDesiredAmountIsLowerThanMinAmount() {
		QuoteConfiguration configuration = new QuoteConfiguration();
		configuration.setDesiredAmount(100);
		QuoteCalculator quoteCalculator = spy(new QuoteCalculator(configuration));

		willThrow(InvalidAmountException.class).given(quoteCalculator).compute(Collections.emptyList());
	}

	@Test
	public void itShouldThrowWhenDesiredAmountIsLargerThanMaxAmount() {
		QuoteConfiguration configuration = new QuoteConfiguration();
		configuration.setDesiredAmount(18000);
		QuoteCalculator quoteCalculator = spy(new QuoteCalculator(configuration));

		willThrow(InvalidAmountException.class).given(quoteCalculator).compute(Collections.emptyList());
	}

	@Test
	public void itShouldThrowWhenDesiredAmountIsNotMultipleOfIncrement() {
		QuoteConfiguration configuration = new QuoteConfiguration();
		configuration.setDesiredAmount(9150);
		QuoteCalculator quoteCalculator = spy(new QuoteCalculator(configuration));

		willThrow(InvalidAmountException.class).given(quoteCalculator).compute(Collections.emptyList());
	}

	@Test
	public void itShouldThrowWhenDesiredAmountExceedsLendersCumulativeCapacity() {
		QuoteConfiguration configuration = new QuoteConfiguration();
		configuration.setDesiredAmount(9000);
		QuoteCalculator quoteCalculator = spy(new QuoteCalculator(configuration));

		willThrow(AmountCannotBeCoveredException.class).given(quoteCalculator)
				.compute(Collections.singletonList(new Lender("l1", BigDecimal.valueOf(7), 8999)));
	}
	
	@Test
	public void itShouldCorrectlyComputeQuoteWhenGivenAListOfLenders() {
		QuoteConfiguration configuration = new QuoteConfiguration();
		configuration.setDesiredAmount(1000);
		QuoteCalculator quoteCalculator = spy(new QuoteCalculator(configuration));

		Quote quote = quoteCalculator.compute(createLendersList());
		
		assertThat(quote.getRate(), closeTo(BigDecimal.valueOf(0.07), BigDecimal.valueOf(1e-2)));
		assertThat(quote.getTotalPayment(), closeTo(BigDecimal.valueOf(1106.86), BigDecimal.valueOf(1e-2)));
		assertThat(quote.getMonthlyPayment(), closeTo(BigDecimal.valueOf(30.75), BigDecimal.valueOf(1e-2)));
	}
	
	private List<Lender> createLendersList() {
		return Arrays.asList(
				new Lender("Bob", BigDecimal.valueOf(0.75), 640),
				new Lender("Jane",BigDecimal.valueOf(0.069),480),
				new Lender("Fred",BigDecimal.valueOf(0.071),520),
				new Lender("Mary",BigDecimal.valueOf(0.104),170),
				new Lender("John",BigDecimal.valueOf(0.081),320),
				new Lender("Date",BigDecimal.valueOf(0.074),140),
				new Lender("Angela",BigDecimal.valueOf(0.071),60)
		);
	}
}
