package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.payment.CallCreatePaymentBillWorkflowFossRequest;

/**
 * CallCreatePaymentBillWorkflowFossRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class CallCreatePaymentBillWorkflowFossRequestTransTest {

	// 转换类
	/** The trans. */
	private static CallCreatePaymentBillWorkflowFossRequestTrans trans = new CallCreatePaymentBillWorkflowFossRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		CallCreatePaymentBillWorkflowFossRequest callCreatePaymentBillWorkflowFossRequest = new CallCreatePaymentBillWorkflowFossRequest();
		callCreatePaymentBillWorkflowFossRequest.setPayBillDeptNo("string");
		callCreatePaymentBillWorkflowFossRequest.setPayBillDeptName("string");
		callCreatePaymentBillWorkflowFossRequest.setPayBillAmount(java.math.BigDecimal.ZERO);
		callCreatePaymentBillWorkflowFossRequest.setPayBillComNo("string");
		callCreatePaymentBillWorkflowFossRequest.setPayBillBankNo("string");
		callCreatePaymentBillWorkflowFossRequest.setPayBillPayeeName("string");
		callCreatePaymentBillWorkflowFossRequest.setPayBillCelephone("string");
		callCreatePaymentBillWorkflowFossRequest.setPayBillAppNo("string");
		callCreatePaymentBillWorkflowFossRequest.setPayBillLastTime(new java.util.Date());
		//callCreatePaymentBillWorkflowFossRequest.setPayBillExpDesc("string");
		callCreatePaymentBillWorkflowFossRequest.setPayBillAppType("1");
//		callCreatePaymentBillWorkflowFossRequest.setPayBillExpDate(new java.util.Date());
//		callCreatePaymentBillWorkflowFossRequest.setExpensesType("string");

		try {
			// Object2String
			String json = trans.fromMessage(callCreatePaymentBillWorkflowFossRequest);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.payment.CallCreatePaymentBillWorkflowFossRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
