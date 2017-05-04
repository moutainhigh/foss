package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.crm.QueryUnSignedResponse;

/**
 * QueryUnSignedResponseTrans转换测试.
 * 
 * @author HuangHua
 */
public class QueryUnSignedResponseTransTest {

	// 转换类
	/** The trans. */
	private static QueryUnSignedResponseTrans trans = new QueryUnSignedResponseTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		QueryUnSignedResponse queryUnSignedResponse = new QueryUnSignedResponse();
		queryUnSignedResponse.setWaybillNo("string");
		queryUnSignedResponse.setSender("string");
		queryUnSignedResponse.setSenderMobile("string");
		queryUnSignedResponse.setSenderTelephone("string");
		queryUnSignedResponse.setSenderAddress("string");
		queryUnSignedResponse.setWayBillState("string");

		try {
			// Object2String
			String json = trans.fromMessage(queryUnSignedResponse);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.crm.QueryUnSignedResponse result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
