package com.deppon.foss.util.test;

import static org.junit.Assert.assertEquals;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;

public abstract class AbstractValidationWebTest {
	/**
	 * 验证URL
	 * @return
	 */
	protected abstract String getValidationUrl();

	protected final Log log = LogFactory.getLog(getClass());
	
	@Test
	public void validate() throws Exception {
		WebClient client = new WebClient();
		client.setJavaScriptEnabled(false);
		Page page = client.getPage(getValidationUrl());
		log.info(page.getWebResponse().getContentAsString());
		assertEquals(200, page.getWebResponse().getStatusCode());
		assertEquals("OK", page.getWebResponse().getStatusMessage());
	}
}
