package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.header.AuthInfo;
import com.deppon.esb.header.ESBHeader;
import com.deppon.esb.header.StatusInfo;
import com.deppon.esb.header.StatusList;

/**
 * ESBHeaderTrans转换测试.
 * 
 * @author HuangHua
 */
public class ESBHeaderTransTest {

	// 转换类
	/** The trans. */
	private static ESBHeaderTrans trans = new ESBHeaderTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		ESBHeader eSBHeader = new ESBHeader();
		eSBHeader.setVersion("string");
		eSBHeader.setBusinessId("string");
		eSBHeader.setBusinessDesc1("string");
		eSBHeader.setBusinessDesc2("string");
		eSBHeader.setBusinessDesc3("string");
		eSBHeader.setRequestId("string");
		eSBHeader.setResponseId("string");
		eSBHeader.setSourceSystem("string");
		eSBHeader.setTargetSystem("string");
		eSBHeader.setEsbServiceCode("string");
		eSBHeader.setBackServiceCode("string");
		eSBHeader.setMessageFormat("string");
		eSBHeader.setExchangePattern(new java.lang.Integer(1));
		eSBHeader.setSentSequence(new java.lang.Integer(1));
		eSBHeader.setResultCode(new java.lang.Integer(1));
		AuthInfo authInfo = new AuthInfo();
		authInfo.setPassword("sss");
		authInfo.setUsername("sss");
		eSBHeader.setAuthentication(authInfo);
		com.deppon.esb.header.StatusList list= new StatusList();
		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setStatusId("111111");
		statusInfo.setTimeStamp(2222222222l);
		list.getStatusInfo().add(statusInfo);
		eSBHeader.setStatusList(list);

		try {
			// Object2String
			String json = trans.fromMessage(eSBHeader);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.header.ESBHeader result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
