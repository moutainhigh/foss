package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.crm.SyncAreaPriceRequest;

/**
 * SyncAreaPriceRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class SyncAreaPriceRequestTransTest {

	// 转换类
	/** The trans. */
	private static SyncAreaPriceRequestTrans trans = new SyncAreaPriceRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		SyncAreaPriceRequest syncAreaPriceRequest = new SyncAreaPriceRequest();
		syncAreaPriceRequest.setCity("string");
		syncAreaPriceRequest.setSerialNumber("string");
		syncAreaPriceRequest.setCountry("string");
		syncAreaPriceRequest.setProvince("string");
		syncAreaPriceRequest.setCounty("string");
		syncAreaPriceRequest.setAreaType("string");
		syncAreaPriceRequest.setEffectiveDate("string");
		syncAreaPriceRequest.setExpirationDate("string");
		syncAreaPriceRequest.setName("string");
		syncAreaPriceRequest.setDescription("string");

		try {
			// Object2String
			String json = trans.fromMessage(syncAreaPriceRequest);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.crm.SyncAreaPriceRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
