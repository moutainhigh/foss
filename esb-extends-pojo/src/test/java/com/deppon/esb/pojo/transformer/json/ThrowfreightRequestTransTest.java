package com.deppon.esb.pojo.transformer.json;

import java.math.BigDecimal;

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
		throwfreightRequest.setWaybillNumber("string");
		throwfreightRequest.setReason("string");
		throwfreightRequest.setApplicantName("string");
		throwfreightRequest.setApplypersoncode("string");
		throwfreightRequest.setAreaCode("string");
		throwfreightRequest.setReceivedDate(new java.util.Date());
		throwfreightRequest.setGoodsName("string");
		throwfreightRequest.setReceiveDept("string");
		throwfreightRequest.setFetchDept("string");
		throwfreightRequest.setStorageDept("string");
		throwfreightRequest.setFetchAmt(BigDecimal.ZERO);
		throwfreightRequest.setPrepayAmt(BigDecimal.ZERO);
		throwfreightRequest.setAmount(1);
		throwfreightRequest.setBless(BigDecimal.ZERO);
		throwfreightRequest.setSupplyGoodAmt(BigDecimal.ZERO);
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
