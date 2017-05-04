package com.deppon.foss.module.pickup.predeliver.api.shared.vo;

import java.io.Serializable;

/**
 * 工作流查询DTO
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2013-7-24 下午12:41:24,content:TODO </p>
 * @author 136892
 * @date 2013-7-24 下午12:41:24
 * @since
 * @version
 */
public class WorkFlowExamineVo implements Serializable {

	/**
	 * 描述 
	 */
	private static final long serialVersionUID = 32285973440318897L;
	/**
	 * 业务单据ID (与流程号对应的业务单据的唯一标示)
	 */
	private String claimNo;
	/**
	 * 工作项ID
	 */
	private String workItemId;
	/**
	 * 流程实例ID
	 */
	private String procInstId;
	/**
	 * 环节定义ID
	 */
	private String activityDefId;
	/**
	 * 用户工号
	 */
	private String userId;
	/**
	 * 是否代理审批，代理审批为1，普通审批为0
	 */
	private String isAgent;
	/**
	 * 流程定义名称
	 */
	private String processDefName;
	/**
	 * 工作流类型
	 */
	private String flowType;
	/**
	 * flowType set/get方法
	 * @return
	 */
	public String getFlowType() {
		return flowType;
	}
	/**
	 * flowType set/get方法
	 * @return
	 */
	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}
	/**
	 * claimNo set/get方法
	 * @return
	 */
	public String getClaimNo() {
		return claimNo;
	}
	/**
	 * claimNo set/get方法
	 * @return
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	/**
	 * workItemId set/get方法
	 * @return
	 */
	public String getWorkItemId() {
		return workItemId;
	}
	/**
	 * workItemId set/get方法
	 * @return
	 */
	public void setWorkItemId(String workItemId) {
		this.workItemId = workItemId;
	}
	/**
	 * procInstId set/get方法
	 * @return
	 */
	public String getProcInstId() {
		return procInstId;
	}
	/**
	 * procInstId set/get方法
	 * @return
	 */
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	/**
	 * procInstId set/get方法
	 * @return
	 */
	public String getActivityDefId() {
		return activityDefId;
	}
	/**
	 * activityDefId set/get方法
	 * @return
	 */
	public void setActivityDefId(String activityDefId) {
		this.activityDefId = activityDefId;
	}
	/**
	 * activityDefId set/get方法
	 * @return
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * userId set/get方法
	 * @return
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * isAgent set/get方法
	 * @return
	 */
	public String getIsAgent() {
		return isAgent;
	}
	/**
	 * isAgent set/get方法
	 * @return
	 */
	public void setIsAgent(String isAgent) {
		this.isAgent = isAgent;
	}
	/**
	 * processDefName set/get方法
	 * @return
	 */
	public String getProcessDefName() {
		return processDefName;
	}
	/**
	 * processDefName set/get方法
	 * @return
	 */
	public void setProcessDefName(String processDefName) {
		this.processDefName = processDefName;
	}
}