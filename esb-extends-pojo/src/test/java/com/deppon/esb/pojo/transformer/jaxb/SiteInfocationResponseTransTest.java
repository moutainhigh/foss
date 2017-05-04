package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.vehicle.SiteInfocationResponse;

/**
 * SiteInfocationResponseTrans转换测试.
 * 
 * @author HuangHua
 */
public class SiteInfocationResponseTransTest {

	// 转换类
	/** The trans. */
	private static SiteInfocationResponseTrans trans = new SiteInfocationResponseTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		SiteInfocationResponse siteInfocationResponse = new SiteInfocationResponse();
		siteInfocationResponse.setSuccessCount(1);
		siteInfocationResponse.setFailedCount(1);

		try {
			// Object2String
			String json = trans.fromMessage(siteInfocationResponse);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.vehicle.SiteInfocationResponse result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
