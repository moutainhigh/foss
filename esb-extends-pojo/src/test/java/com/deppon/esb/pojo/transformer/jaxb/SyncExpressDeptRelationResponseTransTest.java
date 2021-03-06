package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.ows.inteface.domain.SyncExpressDeptRelationResponse;

public class SyncExpressDeptRelationResponseTransTest {
	// 转换类
	/** The trans. */
	private static SyncExpressDeptRelationResponseTrans trans = new SyncExpressDeptRelationResponseTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		SyncExpressDeptRelationResponse syncExpressDeptRelationResponse = new SyncExpressDeptRelationResponse();
		try {
			// Object2String
			String json = trans.fromMessage(syncExpressDeptRelationResponse);
			Assert.assertNotNull(json);
			// String2Object
			SyncExpressDeptRelationResponse result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}
}
