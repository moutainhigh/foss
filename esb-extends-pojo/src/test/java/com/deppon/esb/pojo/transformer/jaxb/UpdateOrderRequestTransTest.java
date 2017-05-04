package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.crm.inteface.foss.domain.UpdateOrderRequest;

/**
 * UpdateOrderRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class UpdateOrderRequestTransTest {

	// 转换类
	/** The trans. */
	private static UpdateOrderRequestTrans trans = new UpdateOrderRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		UpdateOrderRequest updateOrderRequest = new UpdateOrderRequest();
		updateOrderRequest.setDriverName("string");
		updateOrderRequest.setWaybillNumber("string");
		updateOrderRequest.setOrderNumber("string");
		updateOrderRequest.setOprateUserNum("string");
		updateOrderRequest.setOprateDeptCode("string");
		updateOrderRequest.setDriverMobile("string");
		updateOrderRequest.setGoodsStatus("string");
		updateOrderRequest.setBackInfo("string");

		try {
			// Object2String
			String json = trans.fromMessage(updateOrderRequest);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.crm.inteface.foss.domain.UpdateOrderRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
