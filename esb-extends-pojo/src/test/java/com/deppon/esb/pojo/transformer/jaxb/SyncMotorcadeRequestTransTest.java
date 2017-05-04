package com.deppon.esb.pojo.transformer.jaxb;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.crm.inteface.domain.MotorcadeInfo;
import com.deppon.crm.inteface.domain.SyncMotorcadeRequest;
import com.deppon.crm.inteface.domain.SyncMotorcadeResponse;

/**
 * SyncMotorcadeRequestTrans转换测试.
 * 
 * @author songshishuai
 */
public class SyncMotorcadeRequestTransTest {

	// 转换类
	/** The trans. */
	private static SyncMotorcadeRequestTrans trans = new SyncMotorcadeRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		SyncMotorcadeRequest request = new SyncMotorcadeRequest();
		List<MotorcadeInfo> motorcadeInfo = new  ArrayList<MotorcadeInfo>();
		MotorcadeInfo info = new MotorcadeInfo();
		info.setIsTopMotorcade("1");
		motorcadeInfo.add(info);
		request.getMotorcadeInfo().add(info);
		
		try {
			// Object2String
			String json = trans.fromMessage(request);
			Assert.assertNotNull(json);
			// String2Object
			SyncMotorcadeRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
