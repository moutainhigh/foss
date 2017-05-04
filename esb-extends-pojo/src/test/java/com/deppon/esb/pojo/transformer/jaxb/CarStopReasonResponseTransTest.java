package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.vehicle.CarStopReasonResponse;

/**
 * CarStopReasonResponseTrans转换测试.
 * 
 * @author HuangHua
 */
public class CarStopReasonResponseTransTest {

	// 转换类
	/** The trans. */
	private static CarStopReasonResponseTrans trans = new CarStopReasonResponseTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		CarStopReasonResponse carStopReasonResponse = new CarStopReasonResponse();
		carStopReasonResponse.setSuccessCount(1);
		carStopReasonResponse.setFailCount(1);

		try {
			// Object2String
			String json = trans.fromMessage(carStopReasonResponse);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.vehicle.CarStopReasonResponse result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
