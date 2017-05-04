package com.deppon.esb.pojo.transformer.jaxb;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.vehicle.CarStateInfo;
import com.deppon.esb.inteface.domain.vehicle.CarStateInfocationRequest;

/**
 * CarStateInfocationRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class CarStateInfocationRequestTransTest {

	// 转换类
	/** The trans. */
	private static CarStateInfocationRequestTrans trans = new CarStateInfocationRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		CarStateInfocationRequest carStateInfocationRequest = new CarStateInfocationRequest();
		CarStateInfo info = new CarStateInfo();
		info.setArriveDateTime(new Date());
		info.setCarNumber("S");
		info.setCarState("S");
		info.setCity("S");
		info.setDepartureTime(new Date());
		carStateInfocationRequest.setCarStateInfo(info);

		try {
			// Object2String
			String json = trans.fromMessage(carStateInfocationRequest);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.vehicle.CarStateInfocationRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
