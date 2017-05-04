package com.deppon.foss.module.transfer.lms;

import com.deppon.foss.util.test.AbstractValidationWebTest;


public class ValidationWebTest extends AbstractValidationWebTest {
	@Override
	protected String getValidationUrl() {
		return "http://localhost:8280/tfr-lms-itf";
	}
}
