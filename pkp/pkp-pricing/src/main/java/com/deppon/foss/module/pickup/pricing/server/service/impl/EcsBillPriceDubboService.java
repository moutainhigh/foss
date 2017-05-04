package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.pickup.pricing.api.server.service.IEcsBillPriceDubboService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IWaybillExpressPriceChangeServiceForECS;
import com.deppon.foss.module.pickup.pricing.api.server.service.IWaybillExpressPriceServiceForECS;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.EscWayBillRequestEntity;
import com.deppon.foss.module.pickup.waybill.shared.response.EscWayBillResponseEntity;

/**
 * FOSS 提供给悟空系统查询查询总运费
 * @author foss-265475-GELL
 *
 */
public class EcsBillPriceDubboService implements IEcsBillPriceDubboService{

	
	private static final Logger LOG = LoggerFactory.getLogger(EcsBillPriceDubboService.class);
	
	//开单计费
	@Resource
	private IWaybillExpressPriceServiceForECS waybillExpressPriceServiceForECS;
	//改单计费
	@Resource
	private IWaybillExpressPriceChangeServiceForECS waybillExpressPriceChangeServiceForECS;
	
	@Override
	public EscWayBillResponseEntity queryBillPrice(EscWayBillRequestEntity requestEntity) {
		/**
    	 * 根据是开单请求还是改单请求走不同的访问方法
    	 */
		EscWayBillResponseEntity responseEntity = new EscWayBillResponseEntity();
    	
		if(null == requestEntity){
        	LOG.info("悟空快递计费接口--请求实体为空");
        	responseEntity.setSuccess("0");
        	responseEntity.setMessage("请求实体为空");
    		return responseEntity;
        }
		
		//开单
    	if(null == requestEntity.getIsChange() || WaybillConstants.NO.equals(requestEntity.getIsChange())){
    		LOG.info("悟空快递计费接口--开单计费BEGIN ");
    		saveEcsBillLog(requestEntity.getWaybillNo(),"delivery_create", "begin", requestEntity);
    		responseEntity = waybillExpressPriceServiceForECS.queryBillingPrice(requestEntity);			
    		saveEcsBillLog(requestEntity.getWaybillNo(),"delivery_create", "end", responseEntity);
    		LOG.info("悟空快递计费接口--开单计费END ");
    	}else{//改单
    		LOG.info("悟空快递计费接口--改单计费BEGIN ");
    		saveEcsBillLog(requestEntity.getWaybillNo(),"delivery_change", "begin", requestEntity);
    		responseEntity = waybillExpressPriceChangeServiceForECS.queryChangePrice(requestEntity);
    		saveEcsBillLog(requestEntity.getWaybillNo(),"delivery_change", "end", responseEntity);
    		LOG.info("悟空快递计费接口--改单计费END ");
    	}
		return responseEntity;
	}
	
	
	
	/**
	 * 日志保存
	 * @param wayBillNo
	 * @param billType
	 * @param type
	 * @param object 保存信息对象
	 */
	public void saveEcsBillLog(final String wayBillNo, final String billType,
			final String type, final Object object){
		// TODO Auto-generated method stub
		new Thread(new Runnable(){ 
			@Override
			public void run() {
				Map<String, Object> ecsBillLog = new HashMap<String, Object>();		
				ecsBillLog.put("wayBillNo", wayBillNo);
				ecsBillLog.put("billType", billType);
				ecsBillLog.put("type", type);
				ecsBillLog.put("createTime", new Date());
				String json = JSONObject.toJSONString(object);
				LOG.info("悟空计价保存日志：wayBillNo="+wayBillNo+" type="+type+" json="+json);
				ecsBillLog.put("json", json);
				//新增和更改日志都走同一个方法，都插入一张表
				waybillExpressPriceServiceForECS.saveEcsBillLog(ecsBillLog);
			}
			
		}).start();

		
	}
}
