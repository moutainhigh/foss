package com.deppon.esb.pojo.transformer.jaxb;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.vehicle.OilCostDetail;
import com.deppon.esb.inteface.domain.vehicle.OilCostSyncResponse;

/**
 * OilCostSyncResponseTrans转换测试.
 * 
 * @author HuangHua
 */
public class OilCostSyncResponseTransTest {

	// 转换类
	/** The trans. */
	private static OilCostSyncResponseTrans trans = new OilCostSyncResponseTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		OilCostSyncResponse oilCostSyncResponse = new OilCostSyncResponse();
		OilCostDetail costDetail = new OilCostDetail();
		costDetail.setCarNo("string");
		costDetail.setRefuelAmount(2f);
		costDetail.setCurentMile_0020(1);
		costDetail.setRefuelTime(new Date());
		oilCostSyncResponse.getOilCostDetails().add(costDetail);

		try {
			// Object2String
			String json = trans.fromMessage(oilCostSyncResponse);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.vehicle.OilCostSyncResponse result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
