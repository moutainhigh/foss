package com.deppon.esb.pojo.transformer.jaxb;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.crm.OrigCustSyncRequest;
import com.deppon.esb.inteface.domain.crm.ScatterCustInfo;

/**
 * OrigCustSyncRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class OrigCustSyncRequestTransTest {

	// 转换类
	/** The trans. */
	private static OrigCustSyncRequestTrans trans = new OrigCustSyncRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		OrigCustSyncRequest origCustSyncRequest = new OrigCustSyncRequest();
		ScatterCustInfo scatterCustInfo = new ScatterCustInfo();
		scatterCustInfo.setFscatterid(new BigInteger("2222"));
		scatterCustInfo.setFscattertype("string");
		scatterCustInfo.setFprocredit(java.math.BigDecimal.ZERO);
		scatterCustInfo.setOperateType("string");
		scatterCustInfo.setCustName("string");
		scatterCustInfo.setCustCode("string");
		scatterCustInfo.setCustNumber("string");
		scatterCustInfo.setContactName("string");
		scatterCustInfo.setMobilePhone("string");
		scatterCustInfo.setTelephone("string");
		scatterCustInfo.setContactAddress("string");
		scatterCustInfo.setCustStatus("string");
		scatterCustInfo.setLastModifyTime(new java.util.Date());
		origCustSyncRequest.setScatterCustInfo(scatterCustInfo);

		try {
			// Object2String
			String json = trans.fromMessage(origCustSyncRequest);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.crm.OrigCustSyncRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
