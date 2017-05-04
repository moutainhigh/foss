package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.finance.VerifyBadBebtsRequest;

/**
 * VerifyBadBebtsRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class VerifyBadBebtsRequestTransTest {

	// 转换类
	/** The trans. */
	private static VerifyBadBebtsRequestTrans trans = new VerifyBadBebtsRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		VerifyBadBebtsRequest verifyBadBebtsRequest = new VerifyBadBebtsRequest();
		verifyBadBebtsRequest.setResult(true);
		verifyBadBebtsRequest.setSerialNO("string");
		verifyBadBebtsRequest.setApplyMoney(java.math.BigDecimal.ZERO);
		verifyBadBebtsRequest.setWorkflowName("string");

		try {
			// Object2String
			String json = trans.fromMessage(verifyBadBebtsRequest);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.finance.VerifyBadBebtsRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
