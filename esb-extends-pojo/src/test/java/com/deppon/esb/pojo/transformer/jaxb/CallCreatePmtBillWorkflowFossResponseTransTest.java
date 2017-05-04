/*
 * PROJECT NAME: esb-extends-pojo
 * PACKAGE NAME: com.deppon.esb.pojo.transformer.jaxb
 * FILE    NAME: CallCreatePmtBillWorkflowFossResponseTransTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.fssc.inteface.domain.payment.CallCreatePmtBillWorkflowFossResponse;

/**
 *
 * @author HuangHua
 * @date 2013-4-13 上午10:47:18
 */
public class CallCreatePmtBillWorkflowFossResponseTransTest {

	// 转换类
	/** The trans. */
	private static CallCreatePmtBillWorkflowFossResponseTrans trans = new CallCreatePmtBillWorkflowFossResponseTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * 
	 */
	@Test
	public void test() {

		CallCreatePmtBillWorkflowFossResponse response   = new CallCreatePmtBillWorkflowFossResponse();

		try {
			// Object2String
			String json = trans.fromMessage(response);
			Assert.assertNotNull(json);
			// String2Object
			CallCreatePmtBillWorkflowFossResponse result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	
	}

}
