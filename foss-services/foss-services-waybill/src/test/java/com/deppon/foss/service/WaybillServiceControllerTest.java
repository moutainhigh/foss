package com.deppon.foss.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.deppon.foss.shared.request.WaybillDetailForOfficialRequest;
import com.deppon.foss.shared.request.WaybillDetailForSOCRequest;
import com.deppon.foss.shared.request.WaybillSignDetailQueryRequest;
import com.deppon.foss.shared.response.WaybillDetailForSOCResponse;
import com.deppon.foss.shared.response.WaybillSignDetailResponse;
import com.deppon.foss.shared.vo.WaybillDetailForOfficialVo;
import com.deppon.foss.util.DateUtils;

public class WaybillServiceControllerTest {

	private Logger logger = LoggerFactory.getLogger(WaybillServiceControllerTest.class);
	
	@Test
	public void testGetWaybillSign() {
     
			RestTemplate restTemplate = new RestTemplate();
			WaybillSignDetailQueryRequest entity = new WaybillSignDetailQueryRequest();
      
			 
			entity.setDeliveryCustomerCode("401221122"); //649750 401221122

			Date startTime = DateUtils.convert("2015-05-20 10:10:00");
			Date endTime = new Date(System.currentTimeMillis());
			  
			entity.setStartTime(startTime); 
			entity.setEndTime(endTime); 
			  
			entity.setStart(1);  
			entity.setLimit(10);    

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<WaybillSignDetailQueryRequest> request = new HttpEntity<WaybillSignDetailQueryRequest>(entity, headers);
			ResponseEntity<WaybillSignDetailResponse> res = restTemplate.postForEntity("http://127.0.0.1:8080/foss/restful/waybill/sign/getWaybillSign/", request, WaybillSignDetailResponse.class);

			System.out.println(res.getBody().getTotalCount());
			System.out.println(res.getBody().getSignWaybill());
			
			System.out.println("测试结果："+res.getBody().toString());

	}
	
	
	@Test
	public void testQueryWaybillDetailForOfficial() {
     
			RestTemplate restTemplate = new RestTemplate();
			WaybillDetailForOfficialRequest entity = new WaybillDetailForOfficialRequest();
      
			List<String> waybillNoList = new ArrayList<String>();
			waybillNoList.add("5689505050");
			waybillNoList.add("5986569869");
			entity.setWaybillNoList(waybillNoList); 
			System.out.println(JSONObject.fromObject(entity).toString());

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<WaybillDetailForOfficialRequest> request = new HttpEntity<WaybillDetailForOfficialRequest>(entity, headers);
			ResponseEntity<WaybillDetailForOfficialVo> res = restTemplate.postForEntity("http://127.0.0.1:8080/foss/restful/waybill/official/queryWaybillDetailForOfficial/", request, WaybillDetailForOfficialVo.class);

			
			System.out.println("测试结果："+JSONObject.fromObject(res.getBody()).toString());

	}
	
	@Test
	public void testQueryWaybillDetailForSOC() {
     
			try {
				RestTemplate restTemplate = new RestTemplate();
				WaybillDetailForSOCRequest entity = new WaybillDetailForSOCRequest();
     
				List<String> waybillNoList = new ArrayList<String>();
				waybillNoList.add("5689505050");
				waybillNoList.add("5986569869");
				entity.setWaybillNoList(waybillNoList); 


				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<WaybillDetailForSOCRequest> request = new HttpEntity<WaybillDetailForSOCRequest>(entity, headers);
				ResponseEntity<WaybillDetailForSOCResponse> res = restTemplate.postForEntity("http://192.168.67.12:8580/esb2/rs/ESB_CRM2ESB_CLAIM_APPLICATION", request, WaybillDetailForSOCResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
}
