package com.deppon.foss.services.tracking;

import com.deppon.foss.util.test.AbstractValidationWebTest;

public class ValidationWebTest extends AbstractValidationWebTest {
	@Override
	protected String getValidationUrl() {
		return "http://localhost:8280/foss-services-tracking/ws/trackingws?wsdl";
	}
}
