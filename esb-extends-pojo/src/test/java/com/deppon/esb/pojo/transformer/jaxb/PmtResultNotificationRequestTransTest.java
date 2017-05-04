/*
 * PROJECT NAME: esb-extends-pojo
 * PACKAGE NAME: com.deppon.esb.pojo.transformer.jaxb
 * FILE    NAME: PmtResultNotificationRequestTransTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.foss.esb.inteface.domain.payment.PmtResultNotificationRequest;

/**
 * 
 * @author HuangHua
 * @date 2013-4-13 上午10:48:53
 */
public class PmtResultNotificationRequestTransTest {

	// 转换类
	/** The trans. */
	private static PmtResultNotificationRequestTrans trans = new PmtResultNotificationRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * 
	 */
	@Test
	public void test() {

		PmtResultNotificationRequest request = new PmtResultNotificationRequest();

		try {
			// Object2String
			String json = trans.fromMessage(request);
			Assert.assertNotNull(json);
			// String2Object
			PmtResultNotificationRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}

	}

}
