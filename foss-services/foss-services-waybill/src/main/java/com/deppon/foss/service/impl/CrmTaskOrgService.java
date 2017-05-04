package com.deppon.foss.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.common.api.shared.dto.OrgDto;
import com.deppon.foss.module.transfer.stock.api.server.service.IMatchTaskOrgService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.TaskDeptRequest;
import com.deppon.foss.service.ICrmTaskOrgService;
import com.deppon.foss.util.ESBHeaderConstant;



/**
 * foss给CRM提供接口，根据运单号 、来电人、来电人类型，查询任务部门
 * @author 200978  xiaobingcheng
 * @update 200968 zwd 20150420
 * 2014-11-7
 */

public class CrmTaskOrgService implements ICrmTaskOrgService{
	
	private static final Logger LOG = LoggerFactory.getLogger(CrmTaskOrgService.class);
	
	@Context
	HttpServletRequest request;
	@Context
	HttpServletResponse response;
	
	
	/**匹配任务部门service*/
	private IMatchTaskOrgService matchTaskOrgService;
	public void setMatchTaskOrgService(IMatchTaskOrgService matchTaskOrgService) {
		this.matchTaskOrgService = matchTaskOrgService;
	}
    
	/*public List<OrgDto> matchTaskOrg(String waybillNo){
		
		LOG.info("匹配任务部门开始。。。。");
		
		// 设置相应header
		response.setHeader(ESBHeaderConstant.VERSION, request.getHeader(ESBHeaderConstant.VERSION));
		response.setHeader(ESBHeaderConstant.ESBSERVICECODE, "FOSS_ESB2FOSS_MATCH_TASKORG");
		response.setHeader(ESBHeaderConstant.REQUESTID, request.getHeader(ESBHeaderConstant.REQUESTID));
		response.setHeader(ESBHeaderConstant.BUSINESSID, request.getHeader(ESBHeaderConstant.BUSINESSID));
		response.setHeader(ESBHeaderConstant.MESSAGEFORMAT, request.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
		response.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, request.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
		response.setHeader(ESBHeaderConstant.BACKSERVICECODE, request.getHeader(ESBHeaderConstant.BACKSERVICECODE));
		response.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
		response.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
		
		
		if(StringUtils.isEmpty(waybillNo)){
			LOG.error("CRM传进来的运单号为空！");
		}
		try {
			
			List<OrgDto> orgDtoList = new ArrayList<OrgDto>();
			orgDtoList = matchTaskOrgService.matchTaskOrg(waybillNo);
			response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
			LOG.info("匹配任务部门结束。。。。");
			return orgDtoList;
		} catch (Exception e) {
			response.setHeader(ESBHeaderConstant.RESULTCODE, "0");
			LOG.error("匹配任务部门异常！");
		}
		return null;
	}*/
  

	@Override
	public List<OrgDto> matchTaskOrg(String taskDeptRequest) {
		
		TaskDeptRequest taskDeptRequestBean = null;
		if(!StringUtils.isEmpty(taskDeptRequest)){
			 taskDeptRequestBean  = (TaskDeptRequest) JSONObject.toBean(
					JSONObject.fromObject(taskDeptRequest.toString()), TaskDeptRequest.class);
		}else{
			LOG.info("CRM传输的数据为空!");
			return null;
		}
		String waybillNo = taskDeptRequestBean.getWaybillNumber();
		String callMan   = taskDeptRequestBean.getCallMan();
		String callType  = taskDeptRequestBean.getCallType();
		//调用类型
		String methodType = taskDeptRequestBean.getMethodType();
		
		/**
		 * 调用类型
		 * 1、methodType == 2   匹配工单短信部门
		 * 2、methodType  默认  匹配任务部门
		 */
		if("2".equals(methodType)){
			LOG.info("匹配工单短信开始。。。。");
	    }else {
			LOG.info("匹配任务部门开始。。。。");
	    }
		
		// 设置相应header
		response.setHeader(ESBHeaderConstant.VERSION, request.getHeader(ESBHeaderConstant.VERSION));
		response.setHeader(ESBHeaderConstant.ESBSERVICECODE, "FOSS_ESB2FOSS_MATCH_TASKORG");
		response.setHeader(ESBHeaderConstant.REQUESTID, request.getHeader(ESBHeaderConstant.REQUESTID));
		response.setHeader(ESBHeaderConstant.BUSINESSID, request.getHeader(ESBHeaderConstant.BUSINESSID));
		response.setHeader(ESBHeaderConstant.MESSAGEFORMAT, request.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
		response.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, request.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
		response.setHeader(ESBHeaderConstant.BACKSERVICECODE, request.getHeader(ESBHeaderConstant.BACKSERVICECODE));
		response.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
		response.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
	//	response.setContentType("text/xml;charset=UTF-8"); 

		
		if(StringUtils.isEmpty(waybillNo)){
			LOG.error("CRM传进来的运单号为空！");
		}
		
		if(StringUtils.isEmpty(callMan)){
			LOG.error("CRM传进来的来电人为空！");
		}
		
		if(StringUtils.isEmpty(callType)){
			LOG.error("CRM传进来的来电人类型为空！");
		}
		
		if(StringUtils.isEmpty(methodType)){
			LOG.error("CRM传进来的调用类型为空！");
		}
		
		/**
		 * 调用类型
		 * 1、methodType == 2   匹配工单短信部门
		 * 2、methodType  默认  匹配任务部门
		 */
		
		if("2".equals(methodType)){
			try {
				List<OrgDto> orgDtoList = new ArrayList<OrgDto>();
				
				orgDtoList = matchTaskOrgService.matchTaskOrg(waybillNo , callType);
				
				response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
				LOG.info("匹配工单短信部门结束。。。。");
				return orgDtoList;
			} catch (Exception e) {
				response.setHeader(ESBHeaderConstant.RESULTCODE, "0");
				LOG.error("匹配工单短信部门异常！");
			}
			
		}else{
			try {
				List<OrgDto> orgDtoList = new ArrayList<OrgDto>();
				
				orgDtoList = matchTaskOrgService.matchTaskOrg(waybillNo , callMan , callType);
				
				response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
				LOG.info("匹配任务部门结束。。。。");
				return orgDtoList;
			} catch (Exception e) {
				response.setHeader(ESBHeaderConstant.RESULTCODE, "0");
				LOG.error("匹配任务部门异常！");
			}
		}
		
		return null;
	}
  
}
