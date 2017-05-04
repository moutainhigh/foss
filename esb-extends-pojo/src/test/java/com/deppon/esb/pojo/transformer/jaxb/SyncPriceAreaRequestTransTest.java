package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.crm.inteface.foss.domain.SyncPriceAreaRequest;

/**
 * SyncPriceAreaRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class SyncPriceAreaRequestTransTest {

	// 转换类
	/** The trans. */
	private static SyncPriceAreaRequestTrans trans = new SyncPriceAreaRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		SyncPriceAreaRequest syncPriceAreaRequest = new SyncPriceAreaRequest();
		syncPriceAreaRequest.setCity("string");
		syncPriceAreaRequest.setDescript("string");
		syncPriceAreaRequest.setCountry("string");
		syncPriceAreaRequest.setProvince("string");
		syncPriceAreaRequest.setAreaType("string");
		syncPriceAreaRequest.setCode("string");
		syncPriceAreaRequest.setArea("string");
		syncPriceAreaRequest.setEffectTime(new java.util.Date());
		syncPriceAreaRequest.setInvalidTime(new java.util.Date());
		syncPriceAreaRequest.setName("string");

		try {
			// Object2String
			String json = trans.fromMessage(syncPriceAreaRequest);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.crm.inteface.foss.domain.SyncPriceAreaRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
