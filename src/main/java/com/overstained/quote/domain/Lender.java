package com.overstained.quote.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Lender {
	private String name;
	private BigDecimal rate;
	private int availableSum;
}
