package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.payment.BankInfoNotificationRequest;

/**
 * BankInfoNotificationRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class BankInfoNotificationRequestTransTest {

	// 转换类
	/** The trans. */
	private static BankInfoNotificationRequestTrans trans = new BankInfoNotificationRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		BankInfoNotificationRequest bankInfoNotificationRequest = new BankInfoNotificationRequest();

		try {
			// Object2String
			String json = trans.fromMessage(bankInfoNotificationRequest);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.payment.BankInfoNotificationRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
