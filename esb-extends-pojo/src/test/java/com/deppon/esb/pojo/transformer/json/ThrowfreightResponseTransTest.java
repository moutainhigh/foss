package com.deppon.esb.pojo.transformer.json;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.wfs.ThrowfreightResponse;

/**
 * ThrowfreightResponseTrans转换测试.
 * 
 * @author HuangHua
 */
public class ThrowfreightResponseTransTest {

	// 转换类
	/** The trans. */
	private static ThrowfreightResponseTrans trans = new ThrowfreightResponseTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		ThrowfreightResponse throwfreightResponse = new ThrowfreightResponse();
		throwfreightResponse.setWaybillNumber("string");
		throwfreightResponse.setResult(true);
		throwfreightResponse.setReason("string");
		throwfreightResponse.setProcessinstid(1l);

		try {
			// Object2String
			String json = trans.fromMessage(throwfreightResponse);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.wfs.ThrowfreightResponse result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
