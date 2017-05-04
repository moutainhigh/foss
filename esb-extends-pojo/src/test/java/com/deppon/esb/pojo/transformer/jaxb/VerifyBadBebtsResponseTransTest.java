package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.finance.VerifyBadBebtsResponse;

/**
 * VerifyBadBebtsResponseTrans转换测试.
 * 
 * @author HuangHua
 */
public class VerifyBadBebtsResponseTransTest {

	// 转换类
	/** The trans. */
	private static VerifyBadBebtsResponseTrans trans = new VerifyBadBebtsResponseTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		VerifyBadBebtsResponse verifyBadBebtsResponse = new VerifyBadBebtsResponse();
		verifyBadBebtsResponse.setResult(true);
		verifyBadBebtsResponse.setReason("string");

		try {
			// Object2String
			String json = trans.fromMessage(verifyBadBebtsResponse);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.finance.VerifyBadBebtsResponse result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
