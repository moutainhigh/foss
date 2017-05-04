package com.deppon.esb.pojo.transformer.jaxb;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.crm.inteface.foss.domain.OrderRightInfo;
import com.deppon.crm.inteface.foss.domain.OrderRightRequest;

/**
 * OrderRightRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class OrderRightRequestTransTest {

	// 转换类
	/** The trans. */
	private static OrderRightRequestTrans trans = new OrderRightRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		OrderRightRequest orderRightRequest = new OrderRightRequest();
		OrderRightInfo orderRightInfo =  new OrderRightInfo();
		orderRightInfo.setDepartment("S");
		orderRightInfo.setOperateTime(new Date());
		orderRightInfo.setOperateType("SS");
		orderRightInfo.setOrderTeam("SSS");
		orderRightRequest.setOrderRightInfo(orderRightInfo);

		try {
			// Object2String
			String json = trans.fromMessage(orderRightRequest);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.crm.inteface.foss.domain.OrderRightRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
