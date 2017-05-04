package com.deppon.foss.module.settlement.ecsitf.server.rs.impl;

import net.sf.json.JSONArray;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 快递派送业务调用相关结算接口  单元测试
 * @author 243921-zhangtingting
 * @date 2016-06-07 上午08:18:24
 */
public class EcsDeliverForSettlementTest {

	/**
	 * 获取网上支付未完成的运单
	 */
	@Test
	public void testQueryReceivableAmount() throws HttpException, IOException {

		List<String> list = new ArrayList<String>();
		list.add("566666223");
		list.add("566666224");
		
		String requestJson = JSONArray.fromObject(list).toString();
		String url = "http://10.224.229.136:8080/stl-ecs-itf/v1/foss/stl/ecsDeliverForSettlement/queryUnpaidOnlinePay";

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
