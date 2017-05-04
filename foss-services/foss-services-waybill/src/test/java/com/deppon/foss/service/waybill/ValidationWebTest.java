package com.deppon.foss.service.waybill;

import com.deppon.foss.util.test.AbstractValidationWebTest;


public class ValidationWebTest extends AbstractValidationWebTest {
	@Override
	protected String getValidationUrl() {
		return "http://localhost:8280/foss-services-waybill/services/accountService?wsdl";
	}
}
