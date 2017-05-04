package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.vehicle.CarStopReasonRequest;

/**
 * CarStopReasonRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class CarStopReasonRequestTransTest {

	// 转换类
	/** The trans. */
	private static CarStopReasonRequestTrans trans = new CarStopReasonRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		CarStopReasonRequest carStopReasonRequest = new CarStopReasonRequest();

		try {
			// Object2String
			String json = trans.fromMessage(carStopReasonRequest);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.vehicle.CarStopReasonRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
