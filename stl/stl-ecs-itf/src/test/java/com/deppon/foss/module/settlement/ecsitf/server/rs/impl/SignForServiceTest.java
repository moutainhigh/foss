package com.deppon.foss.module.settlement.ecsitf.server.rs.impl;

import java.io.IOException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.Test;

public class SignForServiceTest {
	/**
	 * ECS-327090-2016-05-09
	 * 判断签收数据是新增还是更改
	 */
	@Test
	public void updateTest() throws HttpException, IOException{
		//WaybillSignResultEntity waybill=new WaybillSignResultEntity();
		/*waybill.setWaybillNo("280561b0-9b56-4479-832a-a0e79a3f4d16");
		waybill.setSignSituation("asd");
		waybill.setSignGoodsQty(1);
		waybill.setSignTime(new Date());
		waybill.setCreateTime(new Date());
		waybill.setModifyTime(new Date());
		waybill.setActive("Y");*/
		String requestJson = "{\"waybillNo\":\"280561b0-9b56-4479-832a-a0e79a3f4d16\",\"signSituation\":\"asd\",\"signGoodsQty\":1,\"signTime\":\"2016-5-12\",\"createTime\":\"2016-5-12\",\"modifyTime\":\"2016-5-12\",\"active\":\"Y\"}";
		String url = "http://10.224.229.122:8080/stl-ecs-itf/rest/v1/foss/stl/signForService/updateSignFor";
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
