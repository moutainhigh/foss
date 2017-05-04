package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.wfs.ThrowfreightRequest;

/**
 * ThrowfreightRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class ThrowfreightRequestTransTest {

	// 转换类
	/** The trans. */
	private static ThrowfreightRequestTrans trans = new ThrowfreightRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		ThrowfreightRequest throwfreightRequest = new ThrowfreightRequest();
		throwfreightRequest.setReason("string");
		throwfreightRequest.setAmount(1);
		throwfreightRequest.setAreaCode("string");
		throwfreightRequest.setApplicantName("string");
		throwfreightRequest.setApplypersoncode("string");
		throwfreightRequest.setWaybillNumber("string");
		throwfreightRequest.setReceivedDate(new java.util.Date());
		throwfreightRequest.setGoodsName("string");
		throwfreightRequest.setReceiveDept("string");
		throwfreightRequest.setFetchDept("string");
		throwfreightRequest.setStorageDept("string");
		throwfreightRequest.setFetchAmt(java.math.BigDecimal.ZERO);
		throwfreightRequest.setPrepayAmt(java.math.BigDecimal.ZERO);
		throwfreightRequest.setBless(java.math.BigDecimal.ZERO);
		throwfreightRequest.setSupplyGoodAmt(java.math.BigDecimal.ZERO);
		throwfreightRequest.setVolume(1d);
		throwfreightRequest.setDisposeType("string");
		//throwfreightRequest.setAccessories("string");
		throwfreightRequest.setWeight(1d);

		try {
			// Object2String
			String json = trans.fromMessage(throwfreightRequest);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.wfs.ThrowfreightRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
