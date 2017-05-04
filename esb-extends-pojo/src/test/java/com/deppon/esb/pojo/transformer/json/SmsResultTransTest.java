package com.deppon.esb.pojo.transformer.json;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.pojo.domain.foss2sms.SmsResult;

/**
 * SmsResultTrans转换测试.
 * 
 * @author HuangHua
 */
public class SmsResultTransTest {

	// 转换类
	/** The trans. */
	private static SmsResultTrans trans = new SmsResultTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		SmsResult smsResult = new SmsResult();
		smsResult.setReason("string");
		smsResult.setResultCode("string");
		List<String> failList = new ArrayList<String>();
		failList.add("ddddd");
		smsResult.setFailList(failList);

		try {
			// Object2String
			String json = trans.fromMessage(smsResult);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.pojo.domain.foss2sms.SmsResult result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
