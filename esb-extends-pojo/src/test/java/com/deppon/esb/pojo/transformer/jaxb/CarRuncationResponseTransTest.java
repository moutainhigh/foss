package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.vehicle.CarRunDisposeResult;
import com.deppon.esb.inteface.domain.vehicle.CarRuncationResponse;

/**
 * CarRuncationResponseTrans转换测试.
 * 
 * @author HuangHua
 */
public class CarRuncationResponseTransTest {

	// 转换类
	/** The trans. */
	private static CarRuncationResponseTrans trans = new CarRuncationResponseTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		CarRuncationResponse carRuncationResponse = new CarRuncationResponse();
		CarRunDisposeResult cresult = new CarRunDisposeResult();
		cresult.setReason("S");
		cresult.setResult(true);
		cresult.setSoleNumber("S");
		carRuncationResponse.setCarRunResult(cresult);

		try {
			// Object2String
			String json = trans.fromMessage(carRuncationResponse);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.vehicle.CarRuncationResponse result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
