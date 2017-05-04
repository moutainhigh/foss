package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.ows.inteface.domain.CityInfo;
import com.deppon.ows.inteface.domain.SyncExpressCityRequest;

/**
 * SumBillResponseTrans转换测试.
 * 
 * @author HuangHua
 */
public class SyncExpressCityRequestTransTest {

	// 转换类
	/** The trans. */
	private static SyncExpressCityRequestTrans trans = new SyncExpressCityRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		SyncExpressCityRequest syncExpressCityRequest = new SyncExpressCityRequest();
		CityInfo info = new CityInfo();
		info.setActive("active");
		info.setId("id");
		syncExpressCityRequest.getCitys().add(info);

		try {
			// Object2String
			String json = trans.fromMessage(syncExpressCityRequest);
			Assert.assertNotNull(json);
			// String2Object
			SyncExpressCityRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
