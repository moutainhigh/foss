package com.deppon.foss.service.price;

import com.deppon.foss.util.test.AbstractValidationWebTest;

public class ValidationWebTest extends AbstractValidationWebTest {
	@Override
	protected String getValidationUrl() {
		return "http://localhost:8280/foss-services-price//publishPriceInfoService?wsdl";
	}
}
