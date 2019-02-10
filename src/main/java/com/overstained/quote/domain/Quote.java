package com.overstained.quote.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Quote {
	private int desiredAmount;
	private BigDecimal rate;
	private BigDecimal monthlyPayment;
	private BigDecimal totalPayment;

}
