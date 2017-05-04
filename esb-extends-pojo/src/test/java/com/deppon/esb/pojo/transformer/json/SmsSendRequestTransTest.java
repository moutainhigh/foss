package com.deppon.esb.pojo.transformer.json;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.pojo.domain.foss2sms.SmsAuthorityInfo;
import com.deppon.esb.pojo.domain.foss2sms.SmsInfo;
import com.deppon.esb.pojo.domain.foss2sms.SmsSendRequest;

/**
 * SmsSendRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class SmsSendRequestTransTest {

	// 转换类
	/** The trans. */
	private static SmsSendRequestTrans trans = new SmsSendRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		SmsSendRequest smsSendRequest = new SmsSendRequest();
		SmsAuthorityInfo smsAuthority = new SmsAuthorityInfo();
		smsAuthority.setPassword("aaa");
		smsAuthority.setUsername("sss");
		smsSendRequest.setSmsAuthority(smsAuthority);
		List<SmsInfo> list =new ArrayList<SmsInfo>();
		SmsInfo smsInfo = new SmsInfo();
		smsInfo.setLatestSendTime(new Timestamp(222));
		smsSendRequest.setSmsInfos(list);
		try {
			// Object2String
			String json = trans.fromMessage(smsSendRequest);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.pojo.domain.foss2sms.SmsSendRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
