package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.uums.inteface.domain.usermanagement.SendDegreeResponse;

/**
 * SendDegreeResponseTrans转换测试.
 * 
 * @author HuangHua
 */
public class SendDegreeResponseTransTest {

	// 转换类
	/** The trans. */
	private static SendDegreeResponseTrans trans = new SendDegreeResponseTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		SendDegreeResponse sendDegreeResponse = new SendDegreeResponse();
		sendDegreeResponse.setSuccessCount(1);
		sendDegreeResponse.setFailedCount(1);

		try {
			// Object2String
			String json = trans.fromMessage(sendDegreeResponse);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.uums.inteface.domain.usermanagement.SendDegreeResponse result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
