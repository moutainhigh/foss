package com.deppon.foss.module.transfer.common.server.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.transfer.common.api.server.service.IFossToCubc;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcBillPayableConditionRequest;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcBillPayableConditionResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcExternalBillRequest;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcExternalBillResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcVehicleAssembleBillRequest;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcVehicleAssembleBillResponse;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.utils.PropertiesUtil;

public class FossToCubc implements IFossToCubc {
	
	private static final Logger Log = LoggerFactory.getLogger(FossToCubc.class);

	@Override
	public CubcExternalBillResponse pushUpdataExternalBill(CubcExternalBillRequest cubcExternalBillRequest) {
		Log.error("更新偏线外发单推送CUBC数据 START");
		String code = "ESB_FOSS2ESB_FOSS_PARTIALLINE_UPDATE";
		Log.error("传入CUBC参数：" + JSONObject.toJSONString(cubcExternalBillRequest));
		HttpClient httpClient = new HttpClient();
		String url = PropertiesUtil.getKeyValue("esb.rs")+"/"+code;
		PostMethod  postMethod=new PostMethod(url);
		Log.error("pushUpdataExternalBill url = " + url);
		CubcExternalBillResponse responseDto = new CubcExternalBillResponse();
		postMethod.addRequestHeader("Content-type","application/json; charset=utf-8");
		String requestStr = JSONObject.toJSONString(cubcExternalBillRequest);
		
		RequestEntity requestEntity = new StringRequestEntity(requestStr);
		postMethod.setRequestEntity(requestEntity);
		postMethod.setRequestEntity(requestEntity);
		
		try {
			httpClient.executeMethod(postMethod);
			BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));  
			StringBuffer stringBuffer = new StringBuffer();  
			String str = "";  
			while((str = reader.readLine())!=null){  
				stringBuffer.append(str);  } 
			String responseStr = stringBuffer.toString();
			Log.info("pushUpdataExternalBill responseStr = " + responseStr);
			    if(null == responseStr||"".equals(responseStr)){ 
			    	return null;	
			    }
			    if(responseStr.contains("exceptionCode")&&
						responseStr.contains("S000099")){
						return null;
						}
		 responseDto = JSONObject.parseObject(responseStr, CubcExternalBillResponse.class);
		 Log.error("更新偏线外发单推送CUBC数据 END");
		} catch (Exception e) {
			Log.error("CUBC接口异常" + e);
			throw new TfrBusinessException("CUBC接口访问异常" , e);
			
		}
		return responseDto;
	}

	@Override
	public CubcExternalBillResponse pushExternalBillStatus(CubcExternalBillRequest cubcExternalBillRequest) {
		
		Log.error("推送给cubc订单状态， 审核、反审核，作废外发单 START...");
		String code = "ESB_FOSS2ESB_FOSS_PARTIALLINE_AUDIT";
		Log.error("传入CUBC参数：" + JSONObject.toJSONString(cubcExternalBillRequest));
		HttpClient httpClient = new HttpClient();
		String url = PropertiesUtil.getKeyValue("esb.rs")+"/"+code;
		Log.error("FOSS推送审核偏线外发单号url："+url);
		PostMethod  postMethod=new PostMethod(url);
		CubcExternalBillResponse responseDto = new CubcExternalBillResponse();
		postMethod.addRequestHeader("Content-type","application/json; charset=utf-8");
		String requestStr = JSONObject.toJSONString(cubcExternalBillRequest);
		RequestEntity requestEntity = new StringRequestEntity(requestStr);
		postMethod.setRequestEntity(requestEntity);
		try {
			httpClient.executeMethod(postMethod);

			BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));  
			StringBuffer stringBuffer = new StringBuffer();  
			String str = "";  
			while((str = reader.readLine())!=null){  
				stringBuffer.append(str);  } 
			String responseStr = stringBuffer.toString();
			Log.info("pushExternalBillStatus responseStr = " + responseStr);
			Log.error("FOSS推送审核偏线外发单号返回："+responseStr);
			    if(null == responseStr||"".equals(responseStr)){ 
			    	return null;	
			    }
			    if(responseStr.contains("exceptionCode")&&
						responseStr.contains("S000099")){
						return null;
						}
		 responseDto = JSONObject.parseObject(responseStr, CubcExternalBillResponse.class);
		 Log.error("推送给cubc订单状态， 审核、反审核，作废外发单 END...");
		} catch (Exception e) {
			Log.error("CUBC接口异常" + e);
			throw new TfrBusinessException("CUBC接口访问异常" , e);
			
		} 
		
		return responseDto;
	
	}

	@Override
	public CubcVehicleAssembleBillResponse pushmodifyTruckStowage(CubcVehicleAssembleBillRequest cubcVehicleAssembleBillRequest) {
		String code = "ESB_FOSS2ESB_FOSS_STLVEHICLE_UPDATE";
		HttpClient httpClient = new HttpClient();
		String url = PropertiesUtil.getKeyValue("esb.rs")+"/"+code;
		PostMethod  postMethod=new PostMethod(url);
		Log.info("pushmodifyTruckStowage url = " + url);
		CubcVehicleAssembleBillResponse responseDto = new CubcVehicleAssembleBillResponse();
		postMethod.addRequestHeader("Content-type","application/json; charset=utf-8");
		String requestStr = JSONObject.toJSONString(cubcVehicleAssembleBillRequest);
		Log.info("pushmodifyTruckStowage requestStr >> " + requestStr);
		RequestEntity requestEntity = new StringRequestEntity(requestStr);
		postMethod.setRequestEntity(requestEntity);
		
		try {
			httpClient.executeMethod(postMethod);

			BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));  
			StringBuffer stringBuffer = new StringBuffer();  
			String str = "";  
			while((str = reader.readLine())!=null){  
				stringBuffer.append(str);  } 
			String responseStr = stringBuffer.toString();
			Log.info("pushmodifyTruckStowage responseStr = " + responseStr);
			    if(null == responseStr||"".equals(responseStr)){ 
			    	return null;	
			    }
			    if(responseStr.contains("exceptionCode")&&
						responseStr.contains("S000099")){
						return null;
						}
			responseDto = JSONObject.parseObject(responseStr, CubcVehicleAssembleBillResponse.class);
		} catch (Exception e) {
			Log.error("CUBC接口异常" + e);
			throw new TfrBusinessException("CUBC接口访问异常" , e);
			
		} 
		
		return responseDto;
	
	}

	@Override
	public CubcVehicleAssembleBillResponse pushdisableTruckStowage(CubcVehicleAssembleBillRequest cubcVehicleAssembleBillRequest) {
		String code = "ESB_FOSS2ESB_FOSS_STLVEHICLE_DELETE";
		HttpClient httpClient = new HttpClient();
		String url = PropertiesUtil.getKeyValue("esb.rs")+"/"+code;
		PostMethod  postMethod=new PostMethod(url);
		Log.info("pushdisableTruckStowage url = " + url);
		CubcVehicleAssembleBillResponse responseDto = new CubcVehicleAssembleBillResponse();
		postMethod.addRequestHeader("Content-type","application/json; charset=utf-8");
		String requestStr = JSONObject.toJSONString(cubcVehicleAssembleBillRequest);
		RequestEntity requestEntity = new StringRequestEntity(requestStr);
		postMethod.setRequestEntity(requestEntity);
		
		try {
			httpClient.executeMethod(postMethod);

			BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
			StringBuffer stringBuffer = new StringBuffer();
			String str = "";
			while ((str = reader.readLine()) != null) {
				stringBuffer.append(str);
			}
			String responseStr = stringBuffer.toString();
			Log.info("pushdisableTruckStowage responseStr = " + responseStr);
			if (null == responseStr || "".equals(responseStr)) {
				return null;
			}
			if (responseStr.contains("exceptionCode") && responseStr.contains("S000099")) {
				return null;
			}
			responseDto = JSONObject.parseObject(responseStr, CubcVehicleAssembleBillResponse.class);
		} catch (Exception e) {
			Log.error("CUBC接口异常" + e);
			throw new TfrBusinessException("CUBC接口访问异常" , e);
			
		} 
		
		return responseDto;
	
	}

	@Override
	public CubcVehicleAssembleBillResponse pushadjustOutVehicleFee(CubcVehicleAssembleBillRequest cubcVehicleAssembleBillRequest) {
		String code = "ESB_FOSS2ESB_FOSS_STLVEHICLE_ADJUST";
		HttpClient httpClient = new HttpClient();
		String url = PropertiesUtil.getKeyValue("esb.rs")+"/"+code;
		PostMethod  postMethod=new PostMethod(url);
		Log.info("pushadjustOutVehicleFee url = " + url);
		CubcVehicleAssembleBillResponse responseDto = new CubcVehicleAssembleBillResponse();
		postMethod.addRequestHeader("Content-type","application/json; charset=utf-8");
		String requestStr = JSONObject.toJSONString(cubcVehicleAssembleBillRequest);
		RequestEntity requestEntity = new StringRequestEntity(requestStr);
		postMethod.setRequestEntity(requestEntity);
		
		try {
			httpClient.executeMethod(postMethod);

			BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));  
			StringBuffer stringBuffer = new StringBuffer();  
			String str = "";  
			while((str = reader.readLine())!=null){  
				stringBuffer.append(str);  } 
			String responseStr = stringBuffer.toString();
			Log.info("pushadjustOutVehicleFee responseStr = " + responseStr);
			    if(null == responseStr||"".equals(responseStr)){ 
			    	return null;	
			    }
			    if(responseStr.contains("exceptionCode")&&
						responseStr.contains("S000099")){
						return null;
						}
			responseDto = JSONObject.parseObject(responseStr, CubcVehicleAssembleBillResponse.class);
		} catch (Exception e) {
			Log.error("CUBC接口异常" + e);
			throw new TfrBusinessException("CUBC接口访问异常" , e);
			
		} 
		
		return responseDto;
	
	}

	@Override
	public CubcBillPayableConditionResponse queryBillPayableByCondition(CubcBillPayableConditionRequest cubcBillPayableConditionRequest) {
		Log.info("start of queryBillPayableByCondition");
		String code = "ESB_FOSS2ESB_CUBC_STLNO_CHECK";
		HttpClient httpClient = new HttpClient();
		String url = PropertiesUtil.getKeyValue("esb.rs")+"/"+code;
		//url = "http://10.224.161.26:8080/trade-web/webservice/fossTransferModelRequestServiceImpl/checkStlVehicleNo";
		PostMethod  postMethod=new PostMethod(url);
		Log.info("queryBillPayableByCondition url = " + url);
		CubcBillPayableConditionResponse responseDto =  new CubcBillPayableConditionResponse();
		postMethod.addRequestHeader("Content-type","application/json; charset=utf-8");
		String requestStr = JSONObject.toJSONString(cubcBillPayableConditionRequest);
		RequestEntity requestEntity = new StringRequestEntity(requestStr);
		postMethod.setRequestEntity(requestEntity);
		
		try {
			httpClient.executeMethod(postMethod);

			BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));  
			StringBuffer stringBuffer = new StringBuffer();  
			String str = "";  
			while((str = reader.readLine())!=null){  
				stringBuffer.append(str);  } 
			String responseStr = stringBuffer.toString();
			Log.info("queryBillPayableByCondition responseStr = " + responseStr);
			    if(null == responseStr||"".equals(responseStr)){ 
			    	return null;	
			    }
			    if(responseStr.contains("exceptionCode")&&
						responseStr.contains("S000099")){
						return null;
						}
			responseDto = JSONObject.parseObject(responseStr, CubcBillPayableConditionResponse.class);
		} catch (Exception e) {
			Log.error("CUBC接口异常" + e);
			throw new TfrBusinessException("CUBC接口访问异常" , e);
			
		} 
		
		return responseDto;
	}

}
