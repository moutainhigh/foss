package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.crm.inteface.foss.domain.SiteReceiveRequest;

/**
 * SiteReceiveRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class SiteReceiveRequestTransTest {

	// 转换类
	/** The trans. */
	private static SiteReceiveRequestTrans trans = new SiteReceiveRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		SiteReceiveRequest siteReceiveRequest = new SiteReceiveRequest();
		siteReceiveRequest.setDeptCode("string");
		siteReceiveRequest.setDeptName("string");
		siteReceiveRequest.setDeptProvince("string");
		siteReceiveRequest.setDeptCity("string");
		siteReceiveRequest.setIsAB(true);
		siteReceiveRequest.setDeptArea("string");
		siteReceiveRequest.setDeptAddress("string");
		siteReceiveRequest.setDescript("string");
		siteReceiveRequest.setContactWay("string");
		siteReceiveRequest.setLeaveOutDept("string");
		siteReceiveRequest.setLeaveMiddleChange("string");
		siteReceiveRequest.setIsUsed(true);
		siteReceiveRequest.setSuperiorArea("string");
		siteReceiveRequest.setIsOpen(true);
		siteReceiveRequest.setOrganisationId("string");
		siteReceiveRequest.setIsArrived(true);
		siteReceiveRequest.setIsLeave(true);
		siteReceiveRequest.setIsSendToHome(true);
		siteReceiveRequest.setIsGetBySelf(true);
		siteReceiveRequest.setIsOutSend(true);
		siteReceiveRequest.setIsCarTeam(true);
		siteReceiveRequest.setIsHasPDA(true);
		siteReceiveRequest.setStandardCode("string");
		siteReceiveRequest.setHandType("string");

		try {
			// Object2String
			String json = trans.fromMessage(siteReceiveRequest);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.crm.inteface.foss.domain.SiteReceiveRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
