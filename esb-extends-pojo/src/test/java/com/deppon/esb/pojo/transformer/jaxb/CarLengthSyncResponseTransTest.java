package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.vehicle.CarLengthSyncResponse;

/**
 * CarLengthSyncResponseTrans转换测试.
 * 
 * @author HuangHua
 */
public class CarLengthSyncResponseTransTest {

	// 转换类
	/** The trans. */
	private static CarLengthSyncResponseTrans trans = new CarLengthSyncResponseTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		CarLengthSyncResponse carLengthSyncResponse = new CarLengthSyncResponse();
		carLengthSyncResponse.setSuccessCount(1);
		carLengthSyncResponse.setFailCount(1);

		try {
			// Object2String
			String json = trans.fromMessage(carLengthSyncResponse);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.vehicle.CarLengthSyncResponse result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
