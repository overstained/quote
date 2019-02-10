package com.overstained.quote.io;

import org.junit.Test;

import com.overstained.quote.io.TemplateResolver;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TemplateResolverUnitTest {
	
	
	@Test
	public void itShouldReturnEmptystringWhenGivenNonExistantKey() {
		assertThat(TemplateResolver.getInstance().resolve("test"), equalTo(""));
	}
	
	@Test
	public void itShouldReturnCorrectTemplateWhenGivenKey() {
		assertThat(TemplateResolver.getInstance().resolve("test.template"), equalTo("test_template"));
	}
	
	@Test
	public void itShouldCorrectlyFormatTemplateWhenGivenKeyAndArguments() {
		assertThat(TemplateResolver.getInstance().resolve("test.template.args", 1234), equalTo("test_template: 1234"));
	}
}
