package com.deppon.esb.pojo.transformer.jaxb;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.finance.SyncPubBankAccResponseType;

/**
 * SyncPubBankAccResponseTypeTrans转换测试.
 * 
 * @author HuangHua
 */
public class SyncPubBankAccResponseTypeTransTest {

	// 转换类
	/** The trans. */
	private static SyncPubBankAccResponseTypeTrans trans = new SyncPubBankAccResponseTypeTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		SyncPubBankAccResponseType syncPubBankAccResponseType = new SyncPubBankAccResponseType();
		syncPubBankAccResponseType.setSuccessCount(1);
		syncPubBankAccResponseType.setFailedCount(1);

		try {
			// Object2String
			String json = trans.fromMessage(syncPubBankAccResponseType);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.finance.SyncPubBankAccResponseType result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
