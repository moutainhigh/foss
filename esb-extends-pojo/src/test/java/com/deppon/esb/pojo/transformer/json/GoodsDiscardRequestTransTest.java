package com.deppon.esb.pojo.transformer.json;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.esb.inteface.domain.workflow.GoodsDiscardRequest;

/**
 * GoodsDiscardRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class GoodsDiscardRequestTransTest {

	// 转换类
	/** The trans. */
	private static GoodsDiscardRequestTrans trans = new GoodsDiscardRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		GoodsDiscardRequest goodsDiscardRequest = new GoodsDiscardRequest();
		goodsDiscardRequest.setWorkFlowNo("string");
		goodsDiscardRequest.setWaybillNumber("string");
		goodsDiscardRequest.setWorkFlowName("string");
		goodsDiscardRequest.setLastExaminerNumber("string");
		goodsDiscardRequest.setLastExaminerName("string");
		goodsDiscardRequest.setDeptCode("string");
		goodsDiscardRequest.setDeptName("string");
		goodsDiscardRequest.setResult(true);
		goodsDiscardRequest.setReason("string");

		try {
			// Object2String
			String json = trans.fromMessage(goodsDiscardRequest);
			Assert.assertNotNull(json);
			// String2Object
			com.deppon.esb.inteface.domain.workflow.GoodsDiscardRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail("有异常，测试不通过！");
		}
	}

}
