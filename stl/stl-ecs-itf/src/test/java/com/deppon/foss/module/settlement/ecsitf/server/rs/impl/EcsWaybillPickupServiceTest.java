package com.deppon.foss.module.settlement.ecsitf.server.rs.impl;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.Test;

/**
 * 快递新增、更改、作废、校验运单，财务相关接口
 * @author foss-231434-bieyexiong
 * @date 2016-4-19 11:51
 */
public class EcsWaybillPickupServiceTest {

//	@Test
	public void testAddWaybill() throws HttpException, IOException{
//		String requestJson = "{\"waybillNo\":\"5000000568\"}";
//		EcsComplementRequest req = new EcsComplementRequest();
//		req.setEmpCode("10086");
//		req.setEmpName("萨菲罗斯");
//		String requestJson = JSONObject.toJSONString(req);
		String requestJson = "{\"waybillNo\":\"5000000568\",\"empCode\":\"10086\",\"empName\":\"萨菲罗斯\"}";
		String url = "http://10.224.229.116:8080/stl-ecs-itf/v1/foss/stl/ecsWaybillPickup/addWaybill";
		
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
	
//	@Test
	public void testCanChange() throws HttpException, IOException{
//		String requestJson = "{\"waybillNo\":\"5000000568\"}";
//		EcsComplementRequest req = new EcsComplementRequest();
//		req.setEmpCode("10086");
//		req.setEmpName("萨菲罗斯");
//		String requestJson = JSONObject.toJSONString(req);
		String requestJson = "{\"waybillNo\":\"5000000568\"}";
		String url = "http://10.224.229.116:8080/stl-ecs-itf/v1/foss/stl/ecsWaybillPickup/canChange";
		
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
	
	@Test
	public void testModifiAndCancle() throws HttpException, IOException{
//		String requestJson = "{\"waybillNo\":\"5000000568\"}";
//		EcsComplementRequest req = new EcsComplementRequest();
//		req.setEmpCode("10086");
//		req.setEmpName("萨菲罗斯");
//		String requestJson = JSONObject.toJSONString(req);
		String requestJson = "{\"waybillNo\":\"5000000568\",\"empCode\":\"10086\",\"empName\":\"萨菲罗斯\"}";
		String url = "http://10.224.229.116:8080/stl-ecs-itf/v1/foss/stl/ecsWaybillPickup/modifiAndCancle";
		
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
