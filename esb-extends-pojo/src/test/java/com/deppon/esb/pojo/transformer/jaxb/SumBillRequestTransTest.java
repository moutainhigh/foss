package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.air.SumBillRequest;

/**
 * SumBillRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class SumBillRequestTransTest {

	// 转换类
	/** The trans. */
	private static SumBillRequestTrans trans = new SumBillRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		SumBillRequest sumBillRequest = new SumBillRequest();
		sumBillRequest.setCreatorNumber("string");
		sumBillRequest.setSenderName("string");
		sumBillRequest.setSendDate(new java.util.Date());
		sumBillRequest.setSubject("string");
		sumBillRequest.setMailFolderName("string");
		sumBillRequest.setNoticeFlag("string");
		sumBillRequest.setReadFlag("string");
		sumBillRequest.setMailFlag("string");
		sumBillRequest.setPriorityLevel("string");
		sumBillRequest.setMailSize(1);
		sumBillRequest.setAttachmentName("string");
		sumBillRequest.setAttachmentLink("string");

		try {
			// Object2String
			String json = trans.fromMessage(sumBillRequest);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.air.SumBillRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
