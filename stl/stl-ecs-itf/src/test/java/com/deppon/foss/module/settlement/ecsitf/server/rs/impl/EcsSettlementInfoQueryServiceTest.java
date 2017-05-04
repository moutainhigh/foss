package com.deppon.foss.module.settlement.ecsitf.server.rs.impl;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.Test;

/**
 * 快递综合查询财务信息接口
 * @author foss-231434-bieyexiong
 * @date 2016-4-22 16:07
 */
public class EcsSettlementInfoQueryServiceTest {
	
	@Test
	public void testQueryWaybillFinanceInfo() throws HttpException, IOException{
//		String requestJson = "{\"waybillNo\":\"5000000568\"}";
//		EcsComplementRequest req = new EcsComplementRequest();
//		req.setEmpCode("10086");
//		req.setEmpName("萨菲罗斯");
//		String requestJson = JSONObject.toJSONString(req);
		String requestJson = "{\"waybillNo\":\"5000000568\"}";
		String url = "http://10.224.229.116:8080/stl-ecs-itf/v1/foss/stl/ecsQueryFinanceInfo/queryFinanceInfo";
		
		//创建请求实体
		RequestEntity reqEntity = new StringRequestEntity(requestJson,"application/json","UTF-8");
		//post请求
		PostMethod post = new PostMethod(url);
		post.setRequestEntity(reqEntity);
		post.addRequestHeader("Content-Type","application/json;charset=UTF-8");
		
		
		//发送请求
		new HttpClient().executeMethod(post);
		
		String resJson = post.getResponseBodyAsString();
		System.out.println(resJson);
	}
}
