package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILtDiscountBackInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncCusLtdiscountafterService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusLtdiscountafterEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusLtdiscountafterItemEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnLtTdDiscountInfoEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.util.define.ESBHeaderConstant;

public class SyncCusLtdiscountafterService implements
		ISyncCusLtdiscountafterService {
	/**
     * 声明logger日志对象.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SyncCusLtdiscountafterService.class);
    
    /**
     * 业务互斥锁服务.
     */
    private IBusinessLockService businessLockService;
    
    /**
     * 事后折概要信息service
     */
    private ILtDiscountBackInfoService ltDiscountBackInfoService;   
    
    public IBusinessLockService getBusinessLockService() {
		return businessLockService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public ILtDiscountBackInfoService getLtDiscountBackInfoService() {
		return ltDiscountBackInfoService;
	}

	public void setLtDiscountBackInfoService(
			ILtDiscountBackInfoService ltDiscountBackInfoService) {
		this.ltDiscountBackInfoService = ltDiscountBackInfoService;
	}

    @Context
    protected HttpServletRequest req;
	@Context
	protected HttpServletResponse resp;
	
	@Override
	public ReturnLtTdDiscountInfoEntity synCusLtdiscountafter(String cusLtdiscountafter) {
		LOGGER.info("零担梯度折接口开始"+System.currentTimeMillis()+"");
		resp.setHeader(ESBHeaderConstant.VERSION, req.getHeader(ESBHeaderConstant.VERSION));
		resp.setHeader(ESBHeaderConstant.ESBSERVICECODE, "ESB_CRM2ESB_ZERO_BURDEN_AFTERWARD_DISCOUNT");
		resp.setHeader(ESBHeaderConstant.REQUESTID, req.getHeader(ESBHeaderConstant.REQUESTID));
		resp.setHeader(ESBHeaderConstant.BUSINESSID, req.getHeader(ESBHeaderConstant.BUSINESSID));
		resp.setHeader(ESBHeaderConstant.MESSAGEFORMAT, req.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
		resp.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, req.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
		resp.setHeader(ESBHeaderConstant.BACKSERVICECODE, req.getHeader(ESBHeaderConstant.BACKSERVICECODE));
		resp.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
		resp.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
	
		ReturnLtTdDiscountInfoEntity response = new ReturnLtTdDiscountInfoEntity();
		//将CRM请求过来的String cusLtdiscountafter 转换成JSONObject对象,后面括号里cusLtdiscountafterEntity是CRM传过来的外面实体的key
		JSONObject object = JSONObject.fromObject(cusLtdiscountafter).getJSONObject("cusLtdiscountafterEntity");
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("dealItems", CusLtdiscountafterItemEntity.class);
		//将JSONObject转换成对应的实体类--梯度折基础资料实体cusLtdiscountafterEntity
		CusLtdiscountafterEntity cusLtdiscountafterEntity = (CusLtdiscountafterEntity) JSONObject.toBean(object,CusLtdiscountafterEntity.class,classMap);
		try {
			if(cusLtdiscountafterEntity != null&&cusLtdiscountafterEntity.getCustNum()!=null){
				
				MutexElement mutex = new MutexElement(cusLtdiscountafterEntity.getFid(), "CRM_GRADIENT_DISCOUNT_ID", MutexElementType.CRM_GRADIENT_DISCOUNT_ID);
				LOGGER.info("开始加锁：" + mutex.getBusinessNo());
				boolean result = businessLockService.lock(mutex, NumberConstants.ZERO);
				if(result){
					LOGGER.info("成功加锁：" + mutex.getBusinessNo());
					resp.setHeader("ESB-ResultCode", "1");
					response.setIfSuccess(true);
					
					//开始往FOSS表中同步数据
					ltDiscountBackInfoService.updateCusLtdiscountafterInfo(cusLtdiscountafterEntity);
					
					LOGGER.info("开始解锁：" + mutex.getBusinessNo());
					// 解业务锁
					businessLockService.unlock(mutex);
					LOGGER.info("完成解锁：" + mutex.getBusinessNo());
				}else{
					LOGGER.info("失败加锁：" + mutex.getBusinessNo());
					 response.setIfSuccess(false);
					 resp.setHeader("ESB-ResultCode", "1");
					 response.setErrorMsg("加锁失败");
				}
			}else{
				response.setIfSuccess(false);
				resp.setHeader("ESB-ResultCode", "1");
				response.setErrorMsg("实体类不能为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setIfSuccess(false);
			resp.setHeader("ESB-ResultCode", "1");
			response.setErrorMsg("数据异常...");
		}
		
		
		LOGGER.info("[零担梯度折接口结束]:"+System.currentTimeMillis()+"");
		return response;
	}

}
