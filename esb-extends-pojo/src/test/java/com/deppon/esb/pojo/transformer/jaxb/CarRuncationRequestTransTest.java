package com.deppon.esb.pojo.transformer.jaxb;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.vehicle.CarRunInfo;
import com.deppon.esb.inteface.domain.vehicle.CarRuncationRequest;

/**
 * CarRuncationRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class CarRuncationRequestTransTest {

	// 转换类
	/** The trans. */
	private static CarRuncationRequestTrans trans = new CarRuncationRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		CarRuncationRequest carRuncationRequest = new CarRuncationRequest();
		CarRunInfo carRunInfo = new  CarRunInfo();
		carRunInfo.setActionType(2);
		carRunInfo.setCarNumber("S");
		carRunInfo.setCarUsage("S");
		carRunInfo.setContainerNo("S");
		carRunInfo.setOriginalKilometer(BigDecimal.ONE);
		carRunInfo.setPurposeKilometer(BigDecimal.ZERO);
		carRunInfo.setReachDateTime(new Date());
		carRunInfo.setSoleNumber("S");
		carRuncationRequest.setCarRunInfo(carRunInfo);

		try {
			// Object2String
			String json = trans.fromMessage(carRuncationRequest);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.vehicle.CarRuncationRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
