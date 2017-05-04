package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.air.SumBillResponse;

/**
 * SumBillResponseTrans转换测试.
 * 
 * @author HuangHua
 */
public class SumBillResponseTransTest {

	// 转换类
	/** The trans. */
	private static SumBillResponseTrans trans = new SumBillResponseTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		SumBillResponse sumBillResponse = new SumBillResponse();
		sumBillResponse.setMailId("string");
		sumBillResponse.setAttachmentId("string");

		try {
			// Object2String
			String json = trans.fromMessage(sumBillResponse);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.air.SumBillResponse result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
