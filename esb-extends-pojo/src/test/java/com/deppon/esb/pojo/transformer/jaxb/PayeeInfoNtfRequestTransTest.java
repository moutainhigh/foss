/*
 * PROJECT NAME: esb-extends-pojo
 * PACKAGE NAME: com.deppon.esb.pojo.transformer.jaxb
 * FILE    NAME: PayeeInfoNtfRequestTransTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.foss.esb.inteface.domain.payment.PayeeInfoNtfRequest;

/**
 * 
 * @author HuangHua
 * @date 2013-4-13 上午10:47:39
 */
public class PayeeInfoNtfRequestTransTest {

	// 转换类
	/** The trans. */
	private static PayeeInfoNtfRequestTrans trans = new PayeeInfoNtfRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * 
	 */
	@Test
	public void test() {

		PayeeInfoNtfRequest request = new PayeeInfoNtfRequest();

		try {
			// Object2String
			String json = trans.fromMessage(request);
			Assert.assertNotNull(json);
			// String2Object
			PayeeInfoNtfRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}

	}

}
