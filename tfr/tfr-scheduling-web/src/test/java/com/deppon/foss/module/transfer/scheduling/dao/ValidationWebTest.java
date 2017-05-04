package com.deppon.foss.module.transfer.scheduling.dao;

import com.deppon.foss.util.test.AbstractValidationWebTest;


public class ValidationWebTest extends AbstractValidationWebTest {
	@Override
	protected String getValidationUrl() {
		return "http://localhost:8280/tfr-scheduling-web";
	}
}
