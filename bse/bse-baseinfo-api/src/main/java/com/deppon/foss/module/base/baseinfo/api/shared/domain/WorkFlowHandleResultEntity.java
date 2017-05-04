package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * @author 078816-WangPeng
 * @date   2014-01-13
 * @description 工作流审批记录
 *
 */
public class WorkFlowHandleResultEntity extends BaseEntity {

	/**
	 * VersionUID
	 */
	private static final long serialVersionUID = -7005676956862962633L;
  
	// 处理时间
	private Date handleTime;
	
	// 处理人
	private String handler;
	
	// 处理结果
	private String handlerResult;
	
	// 处理意见
	private String handleAdvise;
	
	//权责
	private String responsibilities;
	
	//工作流号
	private String workFlowNo;

	public Date getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getHandlerResult() {
		return handlerResult;
	}

	public void setHandlerResult(String handlerResult) {
		this.handlerResult = handlerResult;
	}

	public String getHandleAdvise() {
		return handleAdvise;
	}

	public void setHandleAdvise(String handleAdvise) {
		this.handleAdvise = handleAdvise;
	}

	public String getResponsibilities() {
		return responsibilities;
	}

	public void setResponsibilities(String responsibilities) {
		this.responsibilities = responsibilities;
	}

	public String getWorkFlowNo() {
		return workFlowNo;
	}

	public void setWorkFlowNo(String workFlowNo) {
		this.workFlowNo = workFlowNo;
	}
	
	
}
