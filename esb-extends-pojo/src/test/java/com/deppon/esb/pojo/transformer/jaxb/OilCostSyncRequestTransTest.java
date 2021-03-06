package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.vehicle.OilCostSyncRequest;

/**
 * OilCostSyncRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class OilCostSyncRequestTransTest {

	// 转换类
	/** The trans. */
	private static OilCostSyncRequestTrans trans = new OilCostSyncRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		OilCostSyncRequest oilCostSyncRequest = new OilCostSyncRequest();
		oilCostSyncRequest.setCarNo("string");
		oilCostSyncRequest.setBeginTime(new java.util.Date());
		oilCostSyncRequest.setEndTime(new java.util.Date());

		try {
			// Object2String
			String json = trans.fromMessage(oilCostSyncRequest);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.vehicle.OilCostSyncRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
