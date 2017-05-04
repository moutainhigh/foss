/** 
 * @author  FOSS综合-261997-css 
 * @date 创建时间：2015-6-30 上午8:54:59 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
package com.deppon.foss.module.base.crm.itf.server.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.crm.module.client.sync.domain.dto.CrmSyncRequest;
import com.deppon.crm.module.client.sync.domain.dto.CustCustbasedata;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICrmpushfossLogService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISyncInfosFromCrmService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CrmpushfossLogEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.crm.itf.server.service.ISynCustomerInfoRestfulService;
import com.deppon.foss.util.ESBHeaderConstant;
import com.deppon.foss.ws.syncdata.domain.SyncResponse;

/**
 * @author Foss综合-261997-css
 * 
 */
public class SynCustomerInfoRestfulService implements
		ISynCustomerInfoRestfulService {

	/**
	 * 日志.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SynCustomerInfoService.class);

	@Context
	protected HttpServletRequest req;
	@Context
	protected HttpServletResponse resp;
	/**
	 * 从CRM系统同步客户主数据业务处理接口
	 */
	private ISyncInfosFromCrmService syncInfosFromCrmService;
	
	/**
	 * 从CRM系统同步客户主数据日志表记录接口
	 */
	private ICrmpushfossLogService crmpushfossLogService;

	/**
	 * 业务互斥锁服务.
	 */
	private IBusinessLockService businessLockService;

	/**
	 * @param syncInfosFromCrmService
	 *            the syncInfosFromCrmService to set
	 */
	public void setSyncInfosFromCrmService(
			ISyncInfosFromCrmService syncInfosFromCrmService) {
		this.syncInfosFromCrmService = syncInfosFromCrmService;
	}

	public void setCrmpushfossLogService(
			ICrmpushfossLogService crmpushfossLogService) {
		this.crmpushfossLogService = crmpushfossLogService;
	}

	/**
	 * 设置 业务互斥锁服务.
	 * 
	 * @param businessLockService
	 *            the businessLockService to set
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.base.crm.itf.server.service.
	 * ISynCustomerInfoRestfulService#queryCrmInfo(java.lang.String)
	 */
	@Override
	public String queryCrmInfo(String queryCrmInfo) {
		resp.setHeader(ESBHeaderConstant.VERSION,
				req.getHeader(ESBHeaderConstant.VERSION));
		resp.setHeader(ESBHeaderConstant.ESBSERVICECODE,"FOSS_ESB2FOSS_CUSTOME_MASTERRDATA_SYNC_FOSS");
		resp.setHeader(ESBHeaderConstant.REQUESTID,
				req.getHeader(ESBHeaderConstant.REQUESTID));
		resp.setHeader(ESBHeaderConstant.BUSINESSID,
				req.getHeader(ESBHeaderConstant.BUSINESSID));
		resp.setHeader(ESBHeaderConstant.MESSAGEFORMAT,
				req.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
		resp.setHeader(ESBHeaderConstant.EXCHANGEPATTERN,
				req.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
		resp.setHeader(ESBHeaderConstant.BACKSERVICECODE,
				req.getHeader(ESBHeaderConstant.BACKSERVICECODE));
		resp.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID()
				.toString());
		resp.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
		ObjectMapper mapper = new ObjectMapper();
		SyncResponse response = new SyncResponse();
		CustCustbasedata custbasedata = null;
		CrmSyncRequest crmSyncRequest = null;
		CrmpushfossLogEntity crmpushfossLog = new CrmpushfossLogEntity();
		try {
			crmSyncRequest = mapper.readValue(queryCrmInfo,CrmSyncRequest.class);
			LOGGER.info("请求参数:"
					+ com.alibaba.fastjson.JSONObject
							.toJSONString(crmSyncRequest));
			//把CRM传递过来的信息传入日志记录表 2015-07-13 css 261997
			crmpushfossLog.setContent(queryCrmInfo);
			crmpushfossLog.setCreateTime(new Date());
			
			if (null == crmSyncRequest) {
				response.setReturn(false);
				resp.setHeader("ESB-ResultCode", "1");
				LOGGER.info("返回数据:"
						+ com.alibaba.fastjson.JSONObject
								.toJSONString(response));
				crmpushfossLog.setFalsereason("返回数据有问题！");
				crmpushfossLog.setResult(com.alibaba.fastjson.JSONObject.toJSONString(response));
				crmpushfossLog.setTrueorfalse("0");
				crmpushfossLogService.insert(crmpushfossLog);
				return com.alibaba.fastjson.JSONObject.toJSONString(response);
			} else {
				custbasedata = crmSyncRequest.gettCustCustbasedata();
			}

		} catch (Exception e) {
			e.printStackTrace();
			response = new SyncResponse();
			response.setReturn(false);
			resp.setHeader("ESB-ResultCode", "0");
		}

		if (null != custbasedata) {
			// 业务锁
			MutexElement mutex = new MutexElement(String.valueOf(custbasedata
					.getFid()), "CUSTOMER_CRMID",
					MutexElementType.CUSTOMER_CRMID);
			LOGGER.info("开始加锁：" + mutex.getBusinessNo());			
			crmpushfossLog.setCrmCusId(BigDecimal.valueOf(Long.valueOf(mutex.getBusinessNo())));
			boolean result = businessLockService.lock(mutex,
					NumberConstants.ZERO);
			if (result) {
				LOGGER.info("成功加锁：" + mutex.getBusinessNo());
			} else {
				LOGGER.info("失败加锁：" + mutex.getBusinessNo());
				response.setReturn(false);
				crmpushfossLog.setFalsereason("失败加锁：" + mutex.getBusinessNo());
				crmpushfossLog.setResult(com.alibaba.fastjson.JSONObject.toJSONString(response));
				crmpushfossLog.setTrueorfalse("0");
				crmpushfossLogService.insert(crmpushfossLog);
				return com.alibaba.fastjson.JSONObject.toJSONString(response);
			}

			// 客户同步主数据处理

			try {
				response = syncInfosFromCrmService.syncInfo(crmSyncRequest);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				crmpushfossLog.setFalsereason("客户主数据为空！.......");
				crmpushfossLog.setResult(com.alibaba.fastjson.JSONObject.toJSONString(response));
				crmpushfossLog.setTrueorfalse("0");
				crmpushfossLog.setCode(custbasedata.getFcustnumber());
				crmpushfossLog.setFalsereason("ORA-00001: 违反唯一约束条件 (BSE.UQ_CRMID)!");
				crmpushfossLogService.insert(crmpushfossLog);
				e.printStackTrace();
			}

			LOGGER.info("开始解锁：" + mutex.getBusinessNo());
			// 解业务锁
			businessLockService.unlock(mutex);
			LOGGER.info("完成解锁：" + mutex.getBusinessNo());
			
			if (response.isReturn()) {
				resp.setHeader("ESB-ResultCode", "1");
			}
			
			crmpushfossLog.setResult(com.alibaba.fastjson.JSONObject.toJSONString(response));
			crmpushfossLog.setTrueorfalse("1");
			crmpushfossLog.setCode(custbasedata.getFcustnumber());
		
			crmpushfossLogService.insert(crmpushfossLog);
			
			return com.alibaba.fastjson.JSONObject.toJSONString(response);
		} else {
			LOGGER.info("客户主数据为空！.......");
			response.setReturn(false);
			resp.setHeader("ESB-ResultCode", "0");
			crmpushfossLog.setFalsereason("客户主数据为空！.......");
			crmpushfossLog.setResult(com.alibaba.fastjson.JSONObject.toJSONString(response));
			crmpushfossLog.setTrueorfalse("0");
			crmpushfossLogService.insert(crmpushfossLog);
			return com.alibaba.fastjson.JSONObject.toJSONString(response);
		}

	}

}
