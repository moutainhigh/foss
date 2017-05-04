package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.payment.PaymentResultNotificationResponse;

/**
 * PaymentResultNotificationResponseTrans转换测试.
 * 
 * @author HuangHua
 */
public class PaymentResultNotificationResponseTransTest {

	// 转换类
	/** The trans. */
	private static PaymentResultNotificationResponseTrans trans = new PaymentResultNotificationResponseTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		PaymentResultNotificationResponse paymentResultNotificationResponse = new PaymentResultNotificationResponse();
		paymentResultNotificationResponse.setResult(true);
		paymentResultNotificationResponse.setReason("string");
		paymentResultNotificationResponse.setWorkflowNo("string");

		try {
			// Object2String
			String json = trans.fromMessage(paymentResultNotificationResponse);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.payment.PaymentResultNotificationResponse result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
