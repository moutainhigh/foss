package com.deppon.foss.module.settlement.ecsitf.server.rs.impl;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.Test;

/**
 * 快递系统新增、修改、作废长途交接单时，相关财务单据处理接口
 * @author foss-231434-bieyexiong
 * @date 2016-4-14 20:08
 */
public class EcsTruckStowageServiceTest {
	
//	@Test
	public void testAddTruckStowage() throws HttpException, IOException{
//		String requestJson = "{\"waybillNo\":\"5000000568\"}";
//		EcsComplementRequest req = new EcsComplementRequest();
//		req.setEmpCode("10086");
//		req.setEmpName("萨菲罗斯");
//		String requestJson = JSONObject.toJSONString(req);
		String requestJson = "{\"waybillNo\":\"5000000568\",\"empCode\":\"10086\",\"empName\":\"萨菲罗斯\"}";
		String url = "http://10.224.229.116:8080/stl-ecs-itf/v1/foss/stl/ecsTruckStowage/addTruckStowage";
		
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
	public void testCheckPayableIsWriteOff() throws HttpException, IOException{
//		String requestJson = "{\"waybillNo\":\"5000000568\"}";
//		EcsComplementRequest req = new EcsComplementRequest();
//		req.setEmpCode("10086");
//		req.setEmpName("萨菲罗斯");
//		String requestJson = JSONObject.toJSONString(req);
		String requestJson = "{\"vehicleAssembleNo\":\"5000000568\"}";
		String url = "http://10.224.229.116:8080/stl-ecs-itf/v1/foss/stl/ecsTruckStowage/checkPayable";
		
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
	public void testModifyTruckStowage() throws HttpException, IOException{
//		String requestJson = "{\"waybillNo\":\"5000000568\"}";
//		EcsComplementRequest req = new EcsComplementRequest();
//		req.setEmpCode("10086");
//		req.setEmpName("萨菲罗斯");
//		String requestJson = JSONObject.toJSONString(req);
		String requestJson = "{\"waybillNo\":\"5000000568\",\"empCode\":\"10086\",\"empName\":\"萨菲罗斯\"}";
		String url = "http://10.224.229.116:8080/stl-ecs-itf/v1/foss/stl/ecsTruckStowage/modifyTruckStowage";
		
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
	public void testDisableTruckStowage() throws HttpException, IOException{
//		String requestJson = "{\"waybillNo\":\"5000000568\"}";
//		EcsComplementRequest req = new EcsComplementRequest();
//		req.setEmpCode("10086");
//		req.setEmpName("萨菲罗斯");
//		String requestJson = JSONObject.toJSONString(req);
		String requestJson = "{\"waybillNo\":\"5000000568\",\"empCode\":\"10086\",\"empName\":\"萨菲罗斯\"}";
		String url = "http://10.224.229.116:8080/stl-ecs-itf/v1/foss/stl/ecsTruckStowage/disableTruckStowage";
		
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
