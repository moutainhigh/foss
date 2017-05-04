package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.uums.inteface.domain.usermanagement.SendUserInfoDeptAllResponse;

/**
 * SendUserInfoResponseTrans转换测试.
 * 
 * @author HuangHua
 */
public class SendUserInfoResponseTransTest {

	// 转换类
	/** The trans. */
	private static SendUserInfoResponseTrans trans = new SendUserInfoResponseTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		SendUserInfoDeptAllResponse sendUserInfoResponse = new SendUserInfoDeptAllResponse();
		sendUserInfoResponse.setSuccessCount(1);
		sendUserInfoResponse.setFailedCount(1);

		try {
			// Object2String
			String json = trans.fromMessage(sendUserInfoResponse);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.uums.inteface.domain.usermanagement.SendUserInfoDeptAllResponse result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
