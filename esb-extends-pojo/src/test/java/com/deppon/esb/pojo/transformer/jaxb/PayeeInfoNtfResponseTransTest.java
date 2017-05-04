/*
 * PROJECT NAME: esb-extends-pojo
 * PACKAGE NAME: com.deppon.esb.pojo.transformer.jaxb
 * FILE    NAME: PayeeInfoNtfResponseTransTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.foss.esb.inteface.domain.payment.PayeeInfoNtfResponse;

/**
 *
 * @author HuangHua
 * @date 2013-4-13 上午10:48:31
 */
public class PayeeInfoNtfResponseTransTest {

	// 转换类
	/** The trans. */
	private static  PayeeInfoNtfResponseTrans trans = new  PayeeInfoNtfResponseTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * 
	 */
	@Test
	public void test() {

		 PayeeInfoNtfResponse response = new  PayeeInfoNtfResponse();

		try {
			// Object2String
			String json = trans.fromMessage(response);
			Assert.assertNotNull(json);
			// String2Object
			 PayeeInfoNtfResponse result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}

	}

}
