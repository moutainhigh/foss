package com.deppon.esb.pojo.transformer.json;

import java.sql.Timestamp;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.pojo.domain.foss2sms.SmsInfo;

/**
 * SmsInfoTrans转换测试.
 * 
 * @author HuangHua
 */
public class SmsInfoTransTest {

	// 转换类
	/** The trans. */
	private static SmsInfoTrans trans = new SmsInfoTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		SmsInfo smsInfo = new SmsInfo();
		smsInfo.setMobile("string");
		smsInfo.setMsgContent("string");
		smsInfo.setSendDept("string");
		smsInfo.setSender("string");
		smsInfo.setMsgType("string");
		smsInfo.setMsgSource("string");
		smsInfo.setUnionId("string");
		smsInfo.setWaybillNo("string");
		smsInfo.setServiceType("string");
		smsInfo.setLatestSendTime(new Timestamp(0));
		smsInfo.setSendTime(new Timestamp(0));

		try {
			// Object2String
			String json = trans.fromMessage(smsInfo);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.pojo.domain.foss2sms.SmsInfo result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
