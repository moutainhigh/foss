package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.crm.CustBaseInfo;
import com.deppon.esb.inteface.domain.crm.CustInfoSyncRequest;
import com.deppon.esb.pojo.util.TestUtils;

/**
 * CustInfoSyncRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class CustInfoSyncRequestTransTest {

	// 转换类
	/** The trans. */
	private static CustInfoSyncRequestTrans trans = new CustInfoSyncRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		CustInfoSyncRequest custInfoSyncRequest = new CustInfoSyncRequest();
		custInfoSyncRequest.setOperateType("string");
		CustBaseInfo custBaseInfo = new CustBaseInfo();
		custBaseInfo.setFcustnumber("string");
		custBaseInfo.setFregistaddress("string");
		custBaseInfo.setFcustnature("string");
		custBaseInfo.setFcusttype("string");
		custBaseInfo.setCredit(java.math.BigDecimal.ZERO);
		custBaseInfo.setFcustname("string");
		custBaseInfo.setFdegree("string");
		custBaseInfo.setFtaxregnumber("string");
		custBaseInfo.setFdeptid("string");
		custBaseInfo.setFcrmcancel(true);
		custBaseInfo.setFid("string");
		custBaseInfo.setMonthSettlementCustOwe(java.math.BigDecimal.ZERO);
		custBaseInfo.setFcreatetime(new java.util.Date());
		custBaseInfo.setFlastUpdateTime(new java.util.Date());
		custInfoSyncRequest.setCustBaseInfo(custBaseInfo);
		TestUtils.generateSetMethod(CustBaseInfo.class);
		try {
			// Object2String
			String json = trans.fromMessage(custInfoSyncRequest);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.crm.CustInfoSyncRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
