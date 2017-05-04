package com.deppon.foss.module.transfer.scheduling.server.service.impl;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.ConnectTimeoutException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.tool.domain.AccessHeader;
import com.deppon.dpap.esb.mqc.tool.util.ESBJMSAccessor;
import com.deppon.foss.module.pickup.waybill.shared.reponse.DeliverGoodsListReponse;
import com.deppon.foss.module.pickup.waybill.shared.request.DeliverGoodsListQueryRequest;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcExternalBillResponse;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.utils.PropertiesUtil;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IASYRentCarCubcService;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.RentCarCubcRequest;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.RentCarCubcResponse;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.CrmBudgetControlRequestEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.CrmBudgetControlResponseEntity;
import com.deppon.foss.util.UUIDUtils;

public class ASYRentCarCubcService implements IASYRentCarCubcService {
	private static Log logger = LogFactory.getLog(ASYRentCarCubcService.class);
	@Override
	public RentCarCubcResponse pushaddRentCar(RentCarCubcRequest rentCarCubcList) {
		String l = UUIDUtils.getUUID();
		logger.info("start of pushaddRentCar:" + l);
		
		AccessHeader accessHeader = new AccessHeader();
		accessHeader.setEsbServiceCode("ESB_FOSS2ESB_FOSS_CUBC_ADDCARRENT");
		accessHeader.setBusinessId("S" + l);
		accessHeader.setBusinessDesc1("同步临时租车单至CUBC");
		accessHeader.setBusinessDesc2(l);
		accessHeader.setVersion("0.1");
		try {
			com.deppon.cubc.module.foss.shared.domain.RentCarCubcRequest rentCarRequest =new com.deppon.cubc.module.foss.shared.domain.RentCarCubcRequest ();
			String request = JSONObject.toJSONString(rentCarCubcList);
			logger.info("pushaddRentCar request = " + request);
			rentCarRequest =JSONObject.parseObject(request,com.deppon.cubc.module.foss.shared.domain.RentCarCubcRequest.class);  
				
			ESBJMSAccessor.asynReqeust(accessHeader, rentCarRequest);
		} catch (ESBException e) {
			logger.error("cubc - ASYRentCarCubcService异常", e);
			throw new TfrBusinessException("cubc - 接口异常", e);
		}
		logger.info("end of pushaddRentCar:" + rentCarCubcList);
		return null;
	}
	
	/**
	 * author:lurker-lee
	 * date  :2017-04-18
	 * description: 临时租车标记同步给FSSC系统，走ESB接口
	 */
	@Override
	public CrmBudgetControlResponseEntity pushTemptalMarkFeeInfoToFSSC(
			CrmBudgetControlRequestEntity crmRequestEnt) {
		
		String flag =crmRequestEnt.getFlag(),message=null;//0：占用预算占用	1：释放预算
		if("1".equals(flag)){
			message="开始,报账平台取消租车标记并释放租车金额同步给FSSC,租车编码："+crmRequestEnt.getClaimID();
		}else if("0".equals(flag)){
			message="开始,申请租车标记并占用预算同步给FSSC,租车编码："+crmRequestEnt.getClaimID();
		}
		logger.info(message);
		//String code = "ESB_FOSS2ESB_TEMPRENTALMARK_FEE_FOSSTOFSSC";  ESB现在不给webservice走ESB，现改为直连
		//String url = PropertiesUtil.getKeyValue("esb.rs")+"/"+code;
		//String url="http://192.168.20.148:8080/fssc/webservice/budgetForFoss/process";
		//String url="http://10.224.64.32:8881/claim/webservice/budgetForFoss/process";
	    //对接生产报账平台系统
		String url="http://fssc.deppon.com/fssc/webservice/budgetForFoss/process";
		logger.info("传入FSCC报账系统参数：" + JSONObject.toJSONString(crmRequestEnt));
		HttpClient httpClient = new HttpClient();
		PostMethod  postMethod=new PostMethod(url);
		CrmBudgetControlResponseEntity responseDto = new CrmBudgetControlResponseEntity();
		postMethod.addRequestHeader("Content-type","application/json; charset=utf-8");
		String requestStr = JSONObject.toJSONString(crmRequestEnt);
		RequestEntity requestEntity = new StringRequestEntity(requestStr);
		postMethod.setRequestEntity(requestEntity);
		try {
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(5999);
			httpClient.executeMethod(postMethod);
			BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));  
			StringBuffer stringBuffer = new StringBuffer();  
			String str = "";  
			while((str = reader.readLine())!=null){  
				stringBuffer.append(str);  
			} 
			String responseStr = stringBuffer.toString();
			logger.error("FOSS推送租车标记调用FSSC返回："+responseStr);
		    if(null == responseStr||"".equals(responseStr)){ 
		    	return null;	
		    }
		    responseDto = JSONObject.parseObject(responseStr, CrmBudgetControlResponseEntity.class);
		    logger.error("推送给FSSC报账系统接口调用结束...");
		}catch(ConnectTimeoutException e){
			logger.error("租车标记调用FSSC接口网络请求超时"+ e.getMessage());
			throw new TfrBusinessException("租车标记调用FSSC接口网络请求超时");
		}catch(SocketTimeoutException e){
			logger.error("租车标记调用FSSC接口网络响应超时"+ e.getMessage());
			throw new TfrBusinessException("租车标记调用FSSC接口网络响应超时");
		}catch (Exception e) {
			logger.error("租车标记调用FSSC接口异常" + e.getMessage());
			throw new TfrBusinessException("租车标记调用FSSC接口异常"+ e.getMessage());
		} 
		return responseDto;
	}
	
}
