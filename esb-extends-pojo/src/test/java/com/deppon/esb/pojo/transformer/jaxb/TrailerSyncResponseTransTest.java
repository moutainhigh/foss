package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.vehicle.TrailerSyncResponse;

/**
 * TrailerSyncResponseTrans转换测试.
 * 
 * @author HuangHua
 */
public class TrailerSyncResponseTransTest {

	// 转换类
	/** The trans. */
	private static TrailerSyncResponseTrans trans = new TrailerSyncResponseTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		TrailerSyncResponse trailerSyncResponse = new TrailerSyncResponse();
		trailerSyncResponse.setSuccessCount(1);
		trailerSyncResponse.setFailCount(1);

		try {
			// Object2String
			String json = trans.fromMessage(trailerSyncResponse);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.vehicle.TrailerSyncResponse result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
