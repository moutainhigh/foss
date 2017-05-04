/*
 * PROJECT NAME: esb-extends-pojo
 * PACKAGE NAME: com.deppon.esb.pojo.transformer.jaxb
 * FILE    NAME: CallCreatePmtBillWorkflowFossRequestTransTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.fssc.inteface.domain.payment.CallCreatePmtBillWorkflowFossRequest;

/**
 * BankInfoNotificationRequestTrans转换测试.
 * 
 * @author HuangHua
 * @date 2013-4-13 上午10:46:57
 */
public class CallCreatePmtBillWorkflowFossRequestTransTest {

	// 转换类
	/** The trans. */
	private static CallCreatePmtBillWorkflowFossRequestTrans trans = new CallCreatePmtBillWorkflowFossRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * 
	 */
	@Test
	public void test() {

		CallCreatePmtBillWorkflowFossRequest request  = new CallCreatePmtBillWorkflowFossRequest();

		try {
			// Object2String
			String json = trans.fromMessage(request);
			Assert.assertNotNull(json);
			// String2Object
			CallCreatePmtBillWorkflowFossRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	
	}

}
