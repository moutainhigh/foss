package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.uums.inteface.domain.usermanagement.SendAdminOrgRequest;

/**
 * SendAdminOrgRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class SendAdminOrgRequestTransTest {

	// 转换类
	/** The trans. */
	private static SendAdminOrgRequestTrans trans = new SendAdminOrgRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		SendAdminOrgRequest sendAdminOrgRequest = new SendAdminOrgRequest();

		try {
			// Object2String
			String json = trans.fromMessage(sendAdminOrgRequest);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.uums.inteface.domain.usermanagement.SendAdminOrgRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
