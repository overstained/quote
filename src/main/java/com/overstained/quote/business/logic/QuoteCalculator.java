package com.overstained.quote.business.logic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.overstained.quote.business.logic.exceptions.AmountCannotBeCoveredException;
import com.overstained.quote.domain.Lender;
import com.overstained.quote.domain.Quote;
import com.overstained.quote.io.exceptions.InvalidAmountException;
import com.overstained.quote.utils.BigDecimalMath;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QuoteCalculator {
	
	private QuoteConfiguration configuration;
	
	public Quote compute(List<Lender> lenders) {
		validateAmount(lenders);
		List<Lender> optimalLenders = findOptimalLenders(lenders);
		BigDecimal rate = computeRate(optimalLenders);
		return computeQuoteGivenRate(rate);
	}
	
	BigDecimal computeRate(List<Lender> lenders) {
		BigDecimal desiredAmount = BigDecimal.valueOf(configuration.getDesiredAmount());
		BigDecimal runningTotalAmount = BigDecimal.ZERO;
        BigDecimal weight = BigDecimal.ZERO;
        for (int i = 0; i < lenders.size(); i++)
        {
            Lender lender = lenders.get(i);

            BigDecimal remainingAmount = desiredAmount.subtract(runningTotalAmount);
            BigDecimal avialableLenderSum = BigDecimal.valueOf(lender.getAvailableSum());
            runningTotalAmount= runningTotalAmount.add(avialableLenderSum);

            weight = weight.add(i == lenders.size()-1 ? 
                lender.getRate().multiply(remainingAmount) :
                	lender.getRate().multiply(avialableLenderSum));
        }
        return weight.divide(desiredAmount, 12, RoundingMode.HALF_UP);
	}
	
	Quote computeQuoteGivenRate(BigDecimal rate)
    {
		BigDecimal desiredAmount = BigDecimal.valueOf(configuration.getDesiredAmount());
		BigDecimal borrowingTerm  =BigDecimal.valueOf(configuration.getBorrowingTermInMonths());
		BigDecimal monthFactor = BigDecimal.ONE.divide(BigDecimal.valueOf(12), 12, RoundingMode.HALF_UP);
		BigDecimal monthlyRate = BigDecimalMath.pow(BigDecimal.ONE.add(rate), monthFactor).subtract(BigDecimal.ONE);
        BigDecimal annualFactor = BigDecimal.ONE
        		.subtract(
        				BigDecimalMath.pow(BigDecimal.ONE.add(monthlyRate), borrowingTerm.negate())
        		).divide(monthlyRate, 12, RoundingMode.HALF_UP);
        BigDecimal monthlyPayment = desiredAmount.divide(annualFactor, 12, RoundingMode.HALF_UP);
        BigDecimal totalPayment = monthlyPayment.multiply(borrowingTerm);
        return new Quote(desiredAmount.intValue(), rate,  monthlyPayment, totalPayment);
    }

	List<Lender> findOptimalLenders(List<Lender> lenders) {
		return lenders.stream().sorted(Comparator.comparing(Lender::getRate)).collect(ArrayList::new,
				(list, lender) -> {
					int currentAmount = list.stream().mapToInt(Lender::getAvailableSum).sum();
					if (currentAmount < configuration.getDesiredAmount()) {
						list.add(lender);
					}
				}, (list1, list2) -> list1.addAll(list2));
	}

	void validateAmount(List<Lender> lenders) {
		int desiredAmount = configuration.getDesiredAmount();
		if (desiredAmount > configuration.getMaxBorrowingAmount()
				|| desiredAmount < configuration.getMinBorrowingAmount()
				|| desiredAmount % configuration.getAmountIncrement() != 0) {
			throw new InvalidAmountException(desiredAmount);
		}
		if (desiredAmount > lenders.stream().mapToInt(Lender::getAvailableSum).sum()) {
			throw new AmountCannotBeCoveredException(desiredAmount);
		}
	}
}
