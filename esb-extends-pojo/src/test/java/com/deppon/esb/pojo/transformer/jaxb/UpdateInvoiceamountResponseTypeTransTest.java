package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.finance.UpdateInvoiceamountResponseType;

/**
 * UpdateInvoiceamountResponseTypeTrans转换测试.
 * 
 * @author HuangHua
 */
public class UpdateInvoiceamountResponseTypeTransTest {

	// 转换类
	/** The trans. */
	private static UpdateInvoiceamountResponseTypeTrans trans = new UpdateInvoiceamountResponseTypeTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		UpdateInvoiceamountResponseType updateInvoiceamountResponseType = new UpdateInvoiceamountResponseType();
		updateInvoiceamountResponseType.setSuccessCount(1);
		updateInvoiceamountResponseType.setFailedCount(1);
		updateInvoiceamountResponseType.setSerialNO("string");

		try {
			// Object2String
			String json = trans.fromMessage(updateInvoiceamountResponseType);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.finance.UpdateInvoiceamountResponseType result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
