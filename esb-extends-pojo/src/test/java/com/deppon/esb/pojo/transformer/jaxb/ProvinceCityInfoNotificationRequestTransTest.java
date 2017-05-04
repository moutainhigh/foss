package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.payment.ProvinceCityInfoNotificationRequest;

/**
 * ProvinceCityInfoNotificationRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class ProvinceCityInfoNotificationRequestTransTest {

	// 转换类
	/** The trans. */
	private static ProvinceCityInfoNotificationRequestTrans trans = new ProvinceCityInfoNotificationRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		ProvinceCityInfoNotificationRequest provinceCityInfoNotificationRequest = new ProvinceCityInfoNotificationRequest();

		try {
			// Object2String
			String json = trans.fromMessage(provinceCityInfoNotificationRequest);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.payment.ProvinceCityInfoNotificationRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
