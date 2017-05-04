//package com.deppon.esb.pojo.transformer.jaxb;
//
//import org.junit.Assert;
//import org.junit.Test;
//
//import com.deppon.esb.inteface.domain.crm.BankPayInfo;
//import com.deppon.esb.inteface.domain.crm.ClaimsPayBillGenerateRequest;
//
///**
// * ClaimsPayBillGenerateRequestTrans转换测试.
// * 
// * @author HuangHua
// */
//public class ClaimsPayBillGenerateRequestTransTest {
//
//	// 转换类
//	/** The trans. */
//	private static ClaimsPayBillGenerateRequestTrans trans = new ClaimsPayBillGenerateRequestTrans();
//
//	/**
//	 * 先Object2String,然后String2Object.
//	 * 
//	 * @author HuangHua
//	 * @date 2012-12-20 下午7:05:09
//	 */
//	@Test
//	public void test() {
//		ClaimsPayBillGenerateRequest claimsPayBillGenerateRequest = new ClaimsPayBillGenerateRequest();
//		claimsPayBillGenerateRequest.setPayBillLastTime(new java.util.Date());
//		claimsPayBillGenerateRequest.setClaimType("string");
//		claimsPayBillGenerateRequest.setClaimWay("string");
//		claimsPayBillGenerateRequest.setBusinessType("string");
//		claimsPayBillGenerateRequest.setDeptNo("string");
//		claimsPayBillGenerateRequest.setCustNo("string");
//		claimsPayBillGenerateRequest.setClaimMoney(java.math.BigDecimal.ZERO);
//		claimsPayBillGenerateRequest.setBillNo("string");
//		claimsPayBillGenerateRequest.setCreatorNo("string");
//		BankPayInfo info = new BankPayInfo();
//		info.setAccountName("S");
//		info.setAccountNumber("S");
//		info.setBankCityCode("S");
//		info.setBankCityName("S");
//		info.setBankCode("S");
//		info.setBankName("S");
//		info.setBankProvinceCode("S");
//		info.setPayWay("S");
//		info.setSubbranchCode("S");
//		info.setSubbranchName("S");
//		claimsPayBillGenerateRequest.setBankPayInfo(info);
//
//		try {
//			// Object2String
//			String json = trans.fromMessage(claimsPayBillGenerateRequest);
//			Assert.assertNotNull(json);
//			// String2Object
//			com.deppon.esb.inteface.domain.crm.ClaimsPayBillGenerateRequest result = trans.toMessage(json);
//			Assert.assertNotNull(result);
//		} catch (Exception e) {
//			Assert.fail("有异常，测试不通过！");
//		}
//	}
//
//}
