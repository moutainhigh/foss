package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.uums.inteface.domain.usermanagement.SendUserInfoDeptAllRequest;

/**
 * SendUserInfoRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class SendUserInfoRequestTransTest {

	// 转换类
	/** The trans. */
	private static SendUserInfoRequestTrans trans = new SendUserInfoRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		SendUserInfoDeptAllRequest sendUserInfoRequest = new SendUserInfoDeptAllRequest();

		try {
			// Object2String
			String json = trans.fromMessage(sendUserInfoRequest);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.uums.inteface.domain.usermanagement.SendUserInfoDeptAllRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
