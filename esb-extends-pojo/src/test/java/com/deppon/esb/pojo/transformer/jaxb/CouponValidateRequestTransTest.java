package com.deppon.esb.pojo.transformer.jaxb;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.crm.CouponValidateRequest;
import com.deppon.esb.inteface.domain.crm.WayBillDetail;
import com.deppon.esb.inteface.domain.crm.WaybillInfo;

/**
 * CouponValidateRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class CouponValidateRequestTransTest {

	// 转换类
	/** The trans. */
	private static CouponValidateRequestTrans trans = new CouponValidateRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		CouponValidateRequest couponValidateRequest = new CouponValidateRequest();
		couponValidateRequest.setCouponNumber("string");
		WaybillInfo info = new WaybillInfo();
		info.setArrArea("S");
		info.setCustNum("S");
		info.setLeaveArea("S");
		info.setOrderNum("S");
		info.setProductNum("S");
		info.setTotalPrice(BigDecimal.ZERO);
		info.setWayBillNum("S");
		WayBillDetail billDetail =  new WayBillDetail();
		billDetail.setPrice(BigDecimal.ONE);
		billDetail.setPriceEntryCode(BigDecimal.TEN);
		info.setWayBillDatail(billDetail);
		couponValidateRequest.setWaybillInfo(info);
		couponValidateRequest.setFlag("string");

		try {
			// Object2String
			String json = trans.fromMessage(couponValidateRequest);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.crm.CouponValidateRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
