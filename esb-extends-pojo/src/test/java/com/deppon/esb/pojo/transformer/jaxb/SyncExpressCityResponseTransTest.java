package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.ows.inteface.domain.SyncExpressCityResponse;

/**
 * SyncExpressCityResponseTrans转换测试.
 * 
 * @author HuangHua
 */
public class SyncExpressCityResponseTransTest {

	// 转换类
	/** The trans. */
	private static SyncExpressCityResponseTrans trans = new SyncExpressCityResponseTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		SyncExpressCityResponse syncExpressCityResponse = new SyncExpressCityResponse();
		try {
			// Object2String
			String json = trans.fromMessage(syncExpressCityResponse);
			Assert.assertNotNull(json);
			// String2Object
			SyncExpressCityResponse result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
