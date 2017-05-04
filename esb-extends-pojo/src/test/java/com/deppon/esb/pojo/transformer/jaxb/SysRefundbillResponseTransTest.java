package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.custom.yq.refund.SysRefundbillResponse;

/**
 * SysRefundbillResponseTrans转换测试.
 * 
 * @author HuangHua
 */
public class SysRefundbillResponseTransTest {

	// 转换类
	/** The trans. */
	private static SysRefundbillResponseTrans trans = new SysRefundbillResponseTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		SysRefundbillResponse sysRefundbillResponse = new SysRefundbillResponse();
		sysRefundbillResponse.setReason("string");
		sysRefundbillResponse.setIsSuccess(true);
		sysRefundbillResponse.setBatchNum("string");

		try {
			// Object2String
			String json = trans.fromMessage(sysRefundbillResponse);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.custom.yq.refund.SysRefundbillResponse result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
