package com.deppon.foss.service.impl;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.module.transfer.common.api.shared.domain.WKResponseParameterEntity;
import com.deppon.foss.module.transfer.load.api.server.service.IWKLoadTempService;
import com.deppon.foss.module.transfer.load.api.shared.domain.WKLoadEntity;
import com.deppon.foss.service.ITRFWKLoadService;
import com.deppon.foss.util.define.ESBHeaderConstant;

import net.sf.json.JSONObject;

/**
 * @description 同步悟空装车信息
 * @version 1.0
 * @author 328864-foss-xieyang
 * @update 2016年5月11日 上午10:05:55
 */
public class TRFWKLoadService implements ITRFWKLoadService {
	
	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;
	
	
	private static final Logger log = LoggerFactory.getLogger(TRFWKLoadService.class);
	/**
	 * @description 同步wk那边创建装车信息 (non-Javadoc)
	 * @see com.deppon.foss.service.ITRFWKLoadService#sysnLoad(java.lang.String)
	 * @author 328864-foss-xieyang
	 * @update 2016年5月11日 上午10:47:23
	 * @version V1.0
	 */
	@Override
	public Object sysnLoad(String reqMsg) {
		// esb通用设置相应header
		//ECS系统需求走ESB服务改成直连
//		response.setHeader(ESBHeaderConstant.VERSION, request.getHeader(ESBHeaderConstant.VERSION));
//		response.setHeader(ESBHeaderConstant.ESBSERVICECODE, "FOSS_ESB2FOSS_CREATE_LOADTASK_FROMWK");
//		response.setHeader(ESBHeaderConstant.REQUESTID, request.getHeader(ESBHeaderConstant.REQUESTID));
//		response.setHeader(ESBHeaderConstant.BUSINESSID, request.getHeader(ESBHeaderConstant.BUSINESSID));
//		response.setHeader(ESBHeaderConstant.MESSAGEFORMAT, request.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
//		response.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, request.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
//		response.setHeader(ESBHeaderConstant.BACKSERVICECODE, request.getHeader(ESBHeaderConstant.BACKSERVICECODE));
//		response.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
//		response.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
//		//统一让ESB响应快递信息
//		response.setHeader(ESBHeaderConstant.RESULTCODE, "1");

		WKResponseParameterEntity resp = new WKResponseParameterEntity();
		resp.setResultFlag(false);
		
		if(log.isInfoEnabled()) {
			log.info("接收到同步wk创建装车数据是:" + reqMsg);
		}

		WKLoadEntity wkLoad = null;

		try {
			// 把json字符串转为实体
			wkLoad = JSON.parseObject(reqMsg, WKLoadEntity.class);
		} catch (Exception e) {
			resp.setFailureReason("解析传来的json数据失败" + e.getMessage());
		}

		if (wkLoad != null) {
			
			Map<String, Object> map = wkLoadTempService.insertWKLoad(wkLoad);
			if ((Boolean) map.get("result")) {
				resp.setResultFlag(true);
			} else {
				resp.setFailureReason(map.get("errMsg").toString());
				log.error(map.get("errMsg").toString());
			}
				
		}

		return JSONObject.fromObject(resp).toString();
	}

	@Override
	public Object sysnUpdateLoad(String reqMsg) {
		// esb通用设置相应header
		//ECS系统需求走ESB服务改成直连
//		response.setHeader(ESBHeaderConstant.VERSION, request.getHeader(ESBHeaderConstant.VERSION));
//		response.setHeader(ESBHeaderConstant.ESBSERVICECODE, "FOSS_ESB2FOSS_SUBMIT_LOADTASK_FROMWK");
//		response.setHeader(ESBHeaderConstant.REQUESTID, request.getHeader(ESBHeaderConstant.REQUESTID));
//		response.setHeader(ESBHeaderConstant.BUSINESSID, request.getHeader(ESBHeaderConstant.BUSINESSID));
//		response.setHeader(ESBHeaderConstant.MESSAGEFORMAT, request.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
//		response.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, request.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
//		response.setHeader(ESBHeaderConstant.BACKSERVICECODE, request.getHeader(ESBHeaderConstant.BACKSERVICECODE));
//		response.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
//		response.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
//		//统一让ESB响应快递信息
//		response.setHeader(ESBHeaderConstant.RESULTCODE, "1");	
		WKResponseParameterEntity resp = new WKResponseParameterEntity();
		resp.setResultFlag(false);

		
		if(log.isInfoEnabled()) {
			log.info("接收到同步wk更新装车数据是:" + reqMsg);
		}

		WKLoadEntity wkLoad = null;
		try {
			// 把json字符串转为实体
			wkLoad = JSON.parseObject(reqMsg, WKLoadEntity.class);
		} catch (Exception e) {
			resp.setFailureReason("解析传来的json数据失败" + e.getMessage());
		}

		if (wkLoad != null) {
			
			Map<String, Object> map = wkLoadTempService.updateWKLoad(wkLoad);
			if ((Boolean) map.get("result")) {
				resp.setResultFlag(true);
			} else {
				resp.setFailureReason(map.get("errMsg").toString());
				log.error(map.get("errMsg").toString());
			}
				
		}

		return JSONObject.fromObject(resp).toString();
	}
	
	
	
	/**
	 * 依赖注入DAO
	 */
	private IWKLoadTempService wkLoadTempService;
	public void setWkLoadTempService(IWKLoadTempService wkLoadTempService) {
		this.wkLoadTempService = wkLoadTempService;
	}
	
	

}
