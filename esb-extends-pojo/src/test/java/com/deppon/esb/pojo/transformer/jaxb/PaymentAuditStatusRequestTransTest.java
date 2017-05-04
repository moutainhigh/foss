package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.payment.PaymentAuditStatusRequest;

/**
 * PaymentAuditStatusRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class PaymentAuditStatusRequestTransTest {

	// 转换类
	/** The trans. */
	private static PaymentAuditStatusRequestTrans trans = new PaymentAuditStatusRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		PaymentAuditStatusRequest paymentAuditStatusRequest = new PaymentAuditStatusRequest();
		paymentAuditStatusRequest.setResult("string");
		paymentAuditStatusRequest.setBatchNo("string");
		paymentAuditStatusRequest.setRemark("string");

		try {
			// Object2String
			String json = trans.fromMessage(paymentAuditStatusRequest);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.payment.PaymentAuditStatusRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
