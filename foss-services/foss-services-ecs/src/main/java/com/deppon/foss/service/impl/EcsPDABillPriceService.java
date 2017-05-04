package com.deppon.foss.service.impl;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MarkActivitiesException;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaResultBillCalculateDto;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPDAPriceServiceForECS;
import com.deppon.foss.module.pickup.waybill.shared.response.EscWayPDABillResponseEntity;
import com.deppon.foss.service.IEcsPDABillPriceService;

/**
 * 悟空PDA计价
 */
@Service 
public class EcsPDABillPriceService implements IEcsPDABillPriceService {
	private static final Logger LOG = LoggerFactory.getLogger(EcsPDABillPriceService.class);

	@Resource
	IWaybillPDAPriceServiceForECS waybillPDAPriceServiceForECS;
					 
	@Override
	public String queryPDABillPrice(String requestJson){
		//计算后的实体类
		EscWayPDABillResponseEntity escWayPDABillResponseEntity = queryEcsPDABillPrice(requestJson);
		//将实体类转成JSON字符串
		String pdaPriceString = JSON.toJSONString(escWayPDABillResponseEntity);
		return pdaPriceString;
	}
	
	public EscWayPDABillResponseEntity queryEcsPDABillPrice(String requestJson) {
		LOG.info("悟空快递PDA计费接口--开始");
		//转成请求实体
    	PdaQueryBillCalculateDto billCalculateDto = new PdaQueryBillCalculateDto();
    	//返回实体
    	EscWayPDABillResponseEntity escWayPDABillResponseEntity = new EscWayPDABillResponseEntity();
    	
		if(null == requestJson){
			LOG.info("悟空快递PDA计费接口--请求实体为空");
			escWayPDABillResponseEntity.setSuccess("0");
    		escWayPDABillResponseEntity.setMessage("请求实体为空");
    		escWayPDABillResponseEntity.setPdaResultBillCalculateDto(new ArrayList<PdaResultBillCalculateDto>());
    		return escWayPDABillResponseEntity;
        }else{
        	try {
    			billCalculateDto = JSONObject.parseObject(requestJson, PdaQueryBillCalculateDto.class);
    		} catch (Exception e1) {
    			LOG.info("悟空快递PDA计费接口--请求类转换异常");
    			escWayPDABillResponseEntity.setSuccess("0");
    			escWayPDABillResponseEntity.setMessage("请求类转换异常");
    			escWayPDABillResponseEntity.setPdaResultBillCalculateDto(new ArrayList<PdaResultBillCalculateDto>());
				return escWayPDABillResponseEntity;
    		}
        	
        	try {
        		LOG.info("悟空PDA计费接口--开单计费BEGIN "+new Date());
        		escWayPDABillResponseEntity = waybillPDAPriceServiceForECS.queryPDABillPrice(billCalculateDto);
        		LOG.info("悟空PDA计费接口--开单计费BEGIN "+new Date()); 
        		return escWayPDABillResponseEntity;
			} catch(MarkActivitiesException markExcep){
				LOG.info("悟空快递PDA计费接口--计算运费异常");
				escWayPDABillResponseEntity.setSuccess("0");
	    		escWayPDABillResponseEntity.setMessage(markExcep.getErrorCode().toString());
	    		escWayPDABillResponseEntity.setPdaResultBillCalculateDto(new ArrayList<PdaResultBillCalculateDto>());
				return escWayPDABillResponseEntity;
			} catch (Exception e) {
				LOG.info("悟空快递PDA计费接口--计算运费异常");
				escWayPDABillResponseEntity.setSuccess("0");
	    		escWayPDABillResponseEntity.setMessage(e.toString());
	    		escWayPDABillResponseEntity.setPdaResultBillCalculateDto(new ArrayList<PdaResultBillCalculateDto>());
				return escWayPDABillResponseEntity;
			}
        }
	}
}
