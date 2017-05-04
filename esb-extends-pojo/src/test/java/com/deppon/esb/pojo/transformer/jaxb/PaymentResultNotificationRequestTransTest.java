package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.payment.PaymentResultNotificationRequest;

/**
 * PaymentResultNotificationRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class PaymentResultNotificationRequestTransTest {

	// 转换类
	/** The trans. */
	private static PaymentResultNotificationRequestTrans trans = new PaymentResultNotificationRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		PaymentResultNotificationRequest paymentResultNotificationRequest = new PaymentResultNotificationRequest();
		paymentResultNotificationRequest.setPayBillDeptNo("string");
		paymentResultNotificationRequest.setPayBillAmount(java.math.BigDecimal.ZERO);
		paymentResultNotificationRequest.setWorkflowNo("string");
		paymentResultNotificationRequest.setWorkflowType("string");
		paymentResultNotificationRequest.setExamineResult("string");
		paymentResultNotificationRequest.setExamineComment("string");

		try {
			// Object2String
			String json = trans.fromMessage(paymentResultNotificationRequest);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.payment.PaymentResultNotificationRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
