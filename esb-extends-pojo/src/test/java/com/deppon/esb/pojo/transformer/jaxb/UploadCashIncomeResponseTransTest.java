package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.finincomecash.UploadCashIncomeResponse;

/**
 * UploadCashIncomeResponseTrans转换测试.
 * 
 * @author HuangHua
 */
public class UploadCashIncomeResponseTransTest {

	// 转换类
	/** The trans. */
	private static UploadCashIncomeResponseTrans trans = new UploadCashIncomeResponseTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		UploadCashIncomeResponse uploadCashIncomeResponse = new UploadCashIncomeResponse();
		uploadCashIncomeResponse.setPaymentDate(new java.util.Date());
		//uploadCashIncomeResponse.setSuccessNum(1);
		//uploadCashIncomeResponse.setFailureNum(1);

		try {
			// Object2String
			String json = trans.fromMessage(uploadCashIncomeResponse);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.finincomecash.UploadCashIncomeResponse result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
