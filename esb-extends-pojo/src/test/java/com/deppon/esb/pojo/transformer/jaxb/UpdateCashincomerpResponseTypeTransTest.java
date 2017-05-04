package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.finance.UpdateCashincomerpResponseType;

/**
 * UpdateCashincomerpResponseTypeTrans转换测试.
 * 
 * @author HuangHua
 */
public class UpdateCashincomerpResponseTypeTransTest {

	// 转换类
	/** The trans. */
	private static UpdateCashincomerpResponseTypeTrans trans = new UpdateCashincomerpResponseTypeTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		UpdateCashincomerpResponseType updateCashincomerpResponseType = new UpdateCashincomerpResponseType();
		updateCashincomerpResponseType.setSuccessCount(1);
		updateCashincomerpResponseType.setFailedCount(1);
		updateCashincomerpResponseType.setSerialNO("string");

		try {
			// Object2String
			String json = trans.fromMessage(updateCashincomerpResponseType);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.finance.UpdateCashincomerpResponseType result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
