package com.deppon.esb.pojo.transformer.jaxb;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.crm.inteface.domain.ResultInfo;
import com.deppon.crm.inteface.domain.SyncMotorcadeResponse;

/**
 * SyncPubBankAccResponseTypeTrans转换测试.
 * 
 * @author HuangHua
 */
public class SyncMotorcadeResponseTransTest {

	// 转换类
	/** The trans. */
	private static SyncMotorcadeResponseTrans trans = new SyncMotorcadeResponseTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		SyncMotorcadeResponse response = new SyncMotorcadeResponse();
		List<ResultInfo> resultInfos = new ArrayList<ResultInfo>();
		ResultInfo resultInfo = new ResultInfo();
		resultInfo.setResultCode("1");
		resultInfos.add(resultInfo);
		response.getResultInfos().add(resultInfo);
		try {
			// Object2String
			String json = trans.fromMessage(response);
			Assert.assertNotNull(json);
			// String2Object
			SyncMotorcadeResponse result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
