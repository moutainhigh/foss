package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.payment.PayeeInfoNotificationResponse;

/**
 * PayeeInfoNotificationResponseTrans转换测试.
 * 
 * @author HuangHua
 */
public class PayeeInfoNotificationResponseTransTest {

	// 转换类
	/** The trans. */
	private static PayeeInfoNotificationResponseTrans trans = new PayeeInfoNotificationResponseTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		PayeeInfoNotificationResponse payeeInfoNotificationResponse = new PayeeInfoNotificationResponse();
		payeeInfoNotificationResponse.setSuccessCount(1);
		payeeInfoNotificationResponse.setFailedCount(1);

		try {
			// Object2String
			String json = trans.fromMessage(payeeInfoNotificationResponse);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.payment.PayeeInfoNotificationResponse result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
