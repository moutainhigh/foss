/*
 * PROJECT NAME: esb-extends-pojo
 * PACKAGE NAME: com.deppon.esb.pojo.transformer.jaxb
 * FILE    NAME: PmtResultNotificationResponseTransTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.foss.esb.inteface.domain.payment.PmtResultNotificationResponse;

/**
 * 
 * @author HuangHua
 * @date 2013-4-13 上午10:49:12
 */
public class PmtResultNotificationResponseTransTest {

	// 转换类
	/** The trans. */
	private static PmtResultNotificationResponseTrans trans = new PmtResultNotificationResponseTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * 
	 */
	@Test
	public void test() {

		PmtResultNotificationResponse response = new PmtResultNotificationResponse();

		try {
			// Object2String
			String json = trans.fromMessage(response);
			Assert.assertNotNull(json);
			// String2Object
			PmtResultNotificationResponse result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}

	}

}
