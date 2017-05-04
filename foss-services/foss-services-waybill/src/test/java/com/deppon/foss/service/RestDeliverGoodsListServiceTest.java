package com.deppon.foss.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.pickup.waybill.shared.reponse.DeliverGoodsListReponse;
import com.deppon.foss.module.pickup.waybill.shared.request.DeliverGoodsListQueryRequest;
import com.deppon.foss.module.pickup.waybill.shared.vo.DeliverGoodsListVo;
import com.deppon.foss.util.DateUtils;

public class RestDeliverGoodsListServiceTest {

	@Test 
	public void testGetDeliverGoodsList() {          
                 
		RestTemplate restTemplate = new RestTemplate();
		DeliverGoodsListQueryRequest deliverGoodsListQueryVoRequest = new DeliverGoodsListQueryRequest();
		Date startTime = DateUtils.convert("2015-08-07");
		Date endTime = DateUtils.convert("2015-09-07");
		  
		List<String> waybillNoList = new ArrayList<String>() ;
		String[] str = {"5755555555","5655555555","5599999999","5522999999","5522999998",
		"5522999988","5089789682","235556428","235556422","15081001" } ;
		for(String res : str){
			waybillNoList.add(res) ;   
		}   
		 
		deliverGoodsListQueryVoRequest.setDeliveryCustomerCode("401221122");
		deliverGoodsListQueryVoRequest.setStartTime(startTime);
		deliverGoodsListQueryVoRequest.setEndTime(endTime);
		deliverGoodsListQueryVoRequest.setBeginMonthTime(DateUtils.convert("2015-08"));
		deliverGoodsListQueryVoRequest.setEndMonthTime(DateUtils.convert("2015-08"));
//		deliverGoodsListQueryVoRequest.setSignStatus("SIGN_STATUS_ALL");
		deliverGoodsListQueryVoRequest.setWaybillNoList(waybillNoList);
		deliverGoodsListQueryVoRequest.setStart(0);
		deliverGoodsListQueryVoRequest.setLimit(20);
		
		System.out.println(deliverGoodsListQueryVoRequest.toString());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		 
	    HttpEntity<DeliverGoodsListQueryRequest> request = new HttpEntity<DeliverGoodsListQueryRequest>(deliverGoodsListQueryVoRequest, headers);
//	    ResponseEntity<DeliverGoodsListReponse> res = restTemplate.postForEntity("http://10.224.68.116:8080/foss-services-waybill/restful/waybill/deliverGoodsList/getDeliverGoodsList", request, DeliverGoodsListReponse.class);
	    ResponseEntity<DeliverGoodsListReponse> res = restTemplate.postForEntity("http://10.224.68.116:8080/foss/restful/waybill/deliverGoodsList/getDeliverGoodsList", request, DeliverGoodsListReponse.class);
			 
			 
		// 测试地址
//		ResponseEntity<deliverGoodsListQueryVoRequest> res = restTemplate
//				.postForEntity(
//						"http://192.168.20.21/foss-services-waybill/restful/waybill/deliverGoodsList/getDeliverGoodsList",
//						request, deliverGoodsListQueryVoRequest.class);
		// 正式地址
		// ResponseEntity<deliverGoodsListQueryVoRequest> res = restTemplate
		// .postForEntity("http://192.168.4.203/foss-services-waybill/restful/waybill/deliverGoodsList/getDeliverGoodsList"
		// , request, deliverGoodsListQueryVoRequest.class);

		System.out.println("返回的总记录数："+res.getBody().getTotalCount());

		System.out.println("测试结果：" + res.getBody().toString());
		
		List<DeliverGoodsListVo> deliverGoodsListVoList = res.getBody().getDeliverGoodsListVoList();
		for(DeliverGoodsListVo deliverGoodsListVo : deliverGoodsListVoList){
			String deliverGoodsListVoJson = JSONObject.toJSONString(deliverGoodsListVo);
			System.out.println(deliverGoodsListVo.toString());
			System.out.println("deliverGoodsListVoJson="+deliverGoodsListVoJson);
		}
	}

}
