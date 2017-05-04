/**
 *  initial comments.
 */
package com.deppon.foss.module.pickup.pricing.server.service;

import com.deppon.foss.util.test.AbstractValidationWebTest;


public class ValidationWebTest extends AbstractValidationWebTest {
	@Override
	protected String getValidationUrl() {
		return "http://localhost:8180/pkp-pricing-web";
	}
}