package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.crm.inteface.foss.domain.ReturnVoucherPaymentResultRequest;

/**
 * ReturnVoucherPaymentResultRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class ReturnVoucherPaymentResultRequestTransTest {

	// 转换类
	/** The trans. */
	private static ReturnVoucherPaymentResultRequestTrans trans = new ReturnVoucherPaymentResultRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		ReturnVoucherPaymentResultRequest returnVoucherPaymentResultRequest = new ReturnVoucherPaymentResultRequest();
		returnVoucherPaymentResultRequest.setReason("string");
		returnVoucherPaymentResultRequest.setDeptCode("string");
		returnVoucherPaymentResultRequest.setCustCode("string");
		returnVoucherPaymentResultRequest.setAmount(java.math.BigDecimal.ZERO);
		returnVoucherPaymentResultRequest.setPaymentType("string");
		returnVoucherPaymentResultRequest.setWaybillNum("string");
		returnVoucherPaymentResultRequest.setPayResult(true);

		try {
			// Object2String
			String json = trans.fromMessage(returnVoucherPaymentResultRequest);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.crm.inteface.foss.domain.ReturnVoucherPaymentResultRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
