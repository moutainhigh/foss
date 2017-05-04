package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.vehicle.CarStateInfoDisposeReult;
import com.deppon.esb.inteface.domain.vehicle.CarStateInfocationResponse;

/**
 * CarStateInfocationResponseTrans转换测试.
 * 
 * @author HuangHua
 */
public class CarStateInfocationResponseTransTest {

	// 转换类
	/** The trans. */
	private static CarStateInfocationResponseTrans trans = new CarStateInfocationResponseTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		CarStateInfocationResponse carStateInfocationResponse = new CarStateInfocationResponse();
		CarStateInfoDisposeReult disposeReult = new CarStateInfoDisposeReult();
		disposeReult.setCarNumber("S");
		disposeReult.setReason("S");
		disposeReult.setResult(true);
		carStateInfocationResponse.setCarStateInfoDisposeReult(disposeReult);

		try {
			// Object2String
			String json = trans.fromMessage(carStateInfocationResponse);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.vehicle.CarStateInfocationResponse result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
