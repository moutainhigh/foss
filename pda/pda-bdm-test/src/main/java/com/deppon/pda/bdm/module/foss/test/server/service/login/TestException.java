package com.deppon.pda.bdm.module.foss.test.server.service.login;

import com.deppon.foss.framework.exception.BusinessException;

public class TestException extends BusinessException{
	public TestException(String msg) {
		super(msg);
	}
	public TestException() {
	}
}
