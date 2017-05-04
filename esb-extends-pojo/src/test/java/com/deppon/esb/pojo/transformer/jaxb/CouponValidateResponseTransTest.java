package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.crm.CouponValidateResponse;

/**
 * CouponValidateResponseTrans转换测试.
 * 
 * @author HuangHua
 */
public class CouponValidateResponseTransTest {

	// 转换类
	/** The trans. */
	private static CouponValidateResponseTrans trans = new CouponValidateResponseTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		CouponValidateResponse couponValidateResponse = new CouponValidateResponse();
		couponValidateResponse.setIsAvailable(true);
		couponValidateResponse.setCheapMoney(java.math.BigDecimal.ZERO);

		try {
			// Object2String
			String json = trans.fromMessage(couponValidateResponse);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.crm.CouponValidateResponse result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
