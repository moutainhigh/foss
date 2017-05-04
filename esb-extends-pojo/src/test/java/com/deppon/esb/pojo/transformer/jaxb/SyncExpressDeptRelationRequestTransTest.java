package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.ows.inteface.domain.SyncExpressDeptRelationRequest;

public class SyncExpressDeptRelationRequestTransTest {
	// 转换类
	/** The trans. */
	private static SyncExpressDeptRelationRequestTrans trans = new SyncExpressDeptRelationRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		SyncExpressDeptRelationRequest syncExpressDeptRelationRequest = new SyncExpressDeptRelationRequest();
		try {
			// Object2String
			String json = trans.fromMessage(syncExpressDeptRelationRequest);
			Assert.assertNotNull(json);
			// String2Object
			SyncExpressDeptRelationRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}
}
