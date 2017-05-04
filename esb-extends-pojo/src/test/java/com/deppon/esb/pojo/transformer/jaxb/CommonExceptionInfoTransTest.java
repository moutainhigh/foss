package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.exception.CommonExceptionInfo;

/**
 * CommonExceptionInfoTrans转换测试.
 * 
 * @author HuangHua
 */
public class CommonExceptionInfoTransTest {

	// 转换类
	/** The trans. */
	private static CommonExceptionInfoTrans trans = new CommonExceptionInfoTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		CommonExceptionInfo commonExceptionInfo = new CommonExceptionInfo();
		commonExceptionInfo.setExceptioncode("string");
		commonExceptionInfo.setExceptiontype("string");
		commonExceptionInfo.setMessage("string");
		commonExceptionInfo.setCreatedTime(new java.util.Date());
		commonExceptionInfo.setDetailedInfo("string");

		try {
			// Object2String
			String json = trans.fromMessage(commonExceptionInfo);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.exception.CommonExceptionInfo result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
