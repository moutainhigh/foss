package com.deppon.foss.module.base.crm.itf.server.service.impl;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISpecialdiscountService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SpecialdiscountDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SpecialdiscountResponseDto;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.crm.itf.server.service.ISyncSpecialdiscountService;
import com.deppon.foss.util.define.ESBHeaderConstant;

public class SyncSpecialdiscountService implements ISyncSpecialdiscountService {
	
	/**
	 * 日志
	 */
    private static final Logger LOGGER = LoggerFactory.getLogger(SyncSpecialdiscountService.class);
	
	private ISpecialdiscountService  specialdiscountService;
	
	/**
	 * 业务互斥锁服务.
	 */
	private IBusinessLockService businessLockService;
	
	/**
	 * 设置 业务互斥锁服务.
	 * 
	 * @param businessLockService
	 *            the businessLockService to set
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public void setSpecialdiscountService(
			ISpecialdiscountService specialdiscountService) {
		this.specialdiscountService = specialdiscountService;
	}
    
    @Context
    protected HttpServletRequest req;
	@Context
	protected HttpServletResponse resp;
	

	@Override
	public String addSpecialdiscount(
			String specialdiscount) {
		LOGGER.info("[进入特惠活动分组借口]:"+System.currentTimeMillis()+"");
		resp.setHeader(ESBHeaderConstant.VERSION, req.getHeader(ESBHeaderConstant.VERSION));
		resp.setHeader(ESBHeaderConstant.ESBSERVICECODE, "ESB_CRM2ESB_CUSTOMER_PREFERENTIAL_INFORMATION");
		resp.setHeader(ESBHeaderConstant.REQUESTID, req.getHeader(ESBHeaderConstant.REQUESTID));
		resp.setHeader(ESBHeaderConstant.BUSINESSID, req.getHeader(ESBHeaderConstant.BUSINESSID));
		resp.setHeader(ESBHeaderConstant.MESSAGEFORMAT, req.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
		resp.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, req.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
		resp.setHeader(ESBHeaderConstant.BACKSERVICECODE, req.getHeader(ESBHeaderConstant.BACKSERVICECODE));
		resp.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
		resp.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
		SpecialdiscountResponseDto response = new SpecialdiscountResponseDto();
		
		JSONObject obj = JSONObject.fromObject(specialdiscount).getJSONObject("specialDiscountGroup");
		
		SpecialdiscountDto request = (SpecialdiscountDto) JSONObject.toBean(obj,SpecialdiscountDto.class);
		try{
			if(request!=null){
				
				MutexElement mutex = new MutexElement(String.valueOf(request.getCrmId()
				    ), "CRM_SPECIALDISCOUNT_CRMID",
				    MutexElementType.CRM_SPECIALDISCOUNT_CRMID);
			    LOGGER.info("开始加锁：" + mutex.getBusinessNo());
			    boolean result = businessLockService.lock(mutex,
				    NumberConstants.ZERO);
			    if (result) {
			    	LOGGER.info("成功加锁：" + mutex.getBusinessNo());
			    } else {
					LOGGER.info("失败加锁：" + mutex.getBusinessNo());
					response.setIfSuccess(false);
					resp.setHeader("ESB-ResultCode", "0");
					response.setErrorMsg("加锁失败");
					return com.alibaba.fastjson.JSONObject.toJSONString(response);
			    }
				
				
				resp.setHeader("ESB-ResultCode", "1");
				response.setIfSuccess(true);
				try {
					specialdiscountService.syncInfo(request); 
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				 
				 
				LOGGER.info("开始解锁：" + mutex.getBusinessNo());
				// 解业务锁
				businessLockService.unlock(mutex);
				LOGGER.info("完成解锁：" + mutex.getBusinessNo());
				
			}else{
				response.setIfSuccess(false);
				resp.setHeader("ESB-ResultCode", "0");
				response.setErrorMsg("request实体类不为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			response.setIfSuccess(false);
			resp.setHeader("ESB-ResultCode", "0");
			response.setErrorMsg("数据异常....");
		}
		LOGGER.info("[离开特惠活动分组借口]:"+System.currentTimeMillis()+"");
		return com.alibaba.fastjson.JSONObject.toJSONString(response);
	}

}
