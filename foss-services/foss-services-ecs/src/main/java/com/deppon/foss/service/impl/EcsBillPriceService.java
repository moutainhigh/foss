package com.deppon.foss.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.pickup.pricing.api.server.service.IWaybillExpressPriceChangeServiceForECS;
import com.deppon.foss.module.pickup.pricing.api.server.service.IWaybillExpressPriceServiceForECS;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPriceChangeServiceForECS;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPriceServiceForECS;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.EscWayBillRequestEntity;
import com.deppon.foss.module.pickup.waybill.shared.response.EscWayBillResponseEntity;
import com.deppon.foss.service.IEcsBillPriceService;

/**
 * FOSS 提供给悟空查询快递总运费
 * @author Foss-308595-GELL
 *
 */
@Service
public class EcsBillPriceService implements IEcsBillPriceService {
	private static final Logger LOG = LoggerFactory.getLogger(EcsBillPriceService.class);
	
	//开单计费
	@Resource
	private IWaybillExpressPriceServiceForECS waybillExpressPriceServiceForECS;
	//改单计费
	@Resource
	private IWaybillExpressPriceChangeServiceForECS waybillExpressPriceChangeServiceForECS;
	
	@Context
    protected HttpServletRequest req;
    @Context
    protected HttpServletResponse resp;
    
	@Override
	public EscWayBillResponseEntity queryBillPrice(String requestJson) {
		LOG.info("悟空快递计费接口--开始");
		//经过ESB需要设置的头部
////        resp.setHeader(ESBHeaderConstant.VERSION, req.getHeader(ESBHeaderConstant.VERSION));
////        resp.setHeader(ESBHeaderConstant.ESBSERVICECODE, "ECS_FOSS_PRICING");
////        resp.setHeader(ESBHeaderConstant.REQUESTID, req.getHeader(ESBHeaderConstant.REQUESTID));
////        resp.setHeader(ESBHeaderConstant.BUSINESSID, req.getHeader(ESBHeaderConstant.BUSINESSID));
////        resp.setHeader(ESBHeaderConstant.MESSAGEFORMAT, req.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
////        resp.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, req.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
////        resp.setHeader(ESBHeaderConstant.BACKSERVICECODE, req.getHeader(ESBHeaderConstant.BACKSERVICECODE));
////        resp.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
////        resp.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
//        
		//转成请求实体
    	EscWayBillRequestEntity requestEntity = new EscWayBillRequestEntity();
    	//初始化返回实体
    	EscWayBillResponseEntity responseEntity = new EscWayBillResponseEntity();
    	
        if(null == requestJson){
        	LOG.info("悟空快递计费接口--请求实体为空");
        	responseEntity.setSuccess("0");
        	responseEntity.setMessage("请求实体为空");
    		return responseEntity;
        }else{
			try {
				requestEntity = JSONObject.parseObject(requestJson, EscWayBillRequestEntity.class);
			} catch (Exception e) {
				LOG.info("悟空快递计费接口--请求类转换异常");
				responseEntity.setSuccess("0");
				responseEntity.setMessage("请求类转换异常");
				return responseEntity;
			}
        	
        	/**
        	 * 根据是开单请求还是改单请求走不同的访问方法
        	 */
        	//开单
        	if(null == requestEntity.getIsChange() || WaybillConstants.NO.equals(requestEntity.getIsChange())){
        		LOG.info("悟空快递计费接口--开单计费BEGIN ");
        		saveEcsBillLog(requestEntity.getWaybillNo(),"delivery_create", "begin", requestJson);
        		responseEntity = queryBillingPrice(requestEntity);        		
    			String responseJson = JSONObject.toJSONString(responseEntity);
        		saveEcsBillLog(requestEntity.getWaybillNo(),"delivery_create", "end", responseJson);
        		LOG.info("悟空快递计费接口--开单计费END ");
        	}else{//改单
        		LOG.info("悟空快递计费接口--改单计费BEGIN ");
        		saveEcsBillLog(requestEntity.getWaybillNo(),"delivery_change", "begin", requestJson);
        		responseEntity = queryChangePrice(requestEntity);
        		String responseJson = JSONObject.toJSONString(responseEntity);
        		saveEcsBillLog(requestEntity.getWaybillNo(),"delivery_change", "end", responseJson);
        		LOG.info("悟空快递计费接口--改单计费END ");
        	}
        	//返回处理好的返回实体
//        	resp.setHeader("ESB-ResultCode", "1");
        	return responseEntity;
        }
	}
	
	//开单总运费
	public EscWayBillResponseEntity queryBillingPrice(EscWayBillRequestEntity requestEntity){
		EscWayBillResponseEntity responseEntity = waybillExpressPriceServiceForECS.queryBillingPrice(requestEntity);
		return responseEntity;
	}
	
	//改单总运费
	public EscWayBillResponseEntity queryChangePrice(EscWayBillRequestEntity requestEntity){
		EscWayBillResponseEntity responseEntity = waybillExpressPriceChangeServiceForECS.queryChangePrice(requestEntity);
		return responseEntity;
	}
	
	//日志保存
	public void saveEcsBillLog(String wayBillNo, String billType, String type, String json){
		// TODO Auto-generated method stub
		final Map<String, Object> ecsBillLog = new HashMap<String, Object>();		
		ecsBillLog.put("wayBillNo", wayBillNo);
		ecsBillLog.put("billType", billType);
		ecsBillLog.put("type", type);
		ecsBillLog.put("json", json); 
		ecsBillLog.put("createTime", new Date()); 
		new Thread(new Runnable(){ 
			@Override
			public void run() {
				//新增和更改日志都走同一个方法，都插入一张表
				waybillExpressPriceServiceForECS.saveEcsBillLog(ecsBillLog);
			}
			
		}).start();

		
	}
}
