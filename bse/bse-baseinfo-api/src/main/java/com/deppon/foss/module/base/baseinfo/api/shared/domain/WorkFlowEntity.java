package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * @author 078816-WangPeng
 * @date   2014-01-07
 * @description 工作流基类
 *
 */
public class WorkFlowEntity extends BaseEntity {

	/**
	 * VersionUID
	 */
	private static final long serialVersionUID = 6583499854743910147L;
	
	//工作流号
    private String procInstId;
    
    //工作流名称
    private String workFlowName;

    //申请人
    private String applyMan;
    
    //申请人名称
    private String applyManName;
    
    //申请日期
    private Date applyTime;
    
    //申请人职位
    private String title;

    //是否有效
    private String active;
    
    //所属部门
    private String applyManDept;
    
    //所属部门名称
    private String applyManDeptName;
   
    //工作流状态
    private String workFlowStatus;
    
    //当前审批人
    private String currentApprover;
    
    //当前审批人名称
    private String currentApproverName;
    
    //当前审批人所属部门
    private String currentApproverDept;
    
   //当前审批人所属部门名称
    private String currentApproverDeptName;
    
    //审批时间
    private Date approvalTime;
    
    //申请原因备注
    private String note;
    
    //审批意见
    private String approvalViews;
    
    //存放上传文件的服务器url
    private String url;
    
    //审批结果
    private String approvalResult;
    
	//流程定义名称(每个系统都唯一)
	private String processDefName;
	
	//工作流类别
	private String workFlowType;

	//业务编码
	private String bizCode;
	
	public String getWorkFlowName() {
		return workFlowName;
	}

	public void setWorkFlowName(String workFlowName) {
		this.workFlowName = workFlowName;
	}

	public String getApplyMan() {
		return applyMan;
	}

	public void setApplyMan(String applyMan) {
		this.applyMan = applyMan;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getApplyManDept() {
		return applyManDept;
	}

	public void setApplyManDept(String applyManDept) {
		this.applyManDept = applyManDept;
	}

	public String getWorkFlowStatus() {
		return workFlowStatus;
	}

	public void setWorkFlowStatus(String workFlowStatus) {
		this.workFlowStatus = workFlowStatus;
	}

	public String getCurrentApprover() {
		return currentApprover;
	}

	public void setCurrentApprover(String currentApprover) {
		this.currentApprover = currentApprover;
	}

	public String getApplyManDeptName() {
		return applyManDeptName;
	}

	public void setApplyManDeptName(String applyManDeptName) {
		this.applyManDeptName = applyManDeptName;
	}

	public String getApplyManName() {
		return applyManName;
	}

	public void setApplyManName(String applyManName) {
		this.applyManName = applyManName;
	}

	public String getCurrentApproverName() {
		return currentApproverName;
	}

	public void setCurrentApproverName(String currentApproverName) {
		this.currentApproverName = currentApproverName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCurrentApproverDept() {
		return currentApproverDept;
	}

	public void setCurrentApproverDept(String currentApproverDept) {
		this.currentApproverDept = currentApproverDept;
	}

	public String getCurrentApproverDeptName() {
		return currentApproverDeptName;
	}

	public void setCurrentApproverDeptName(String currentApproverDeptName) {
		this.currentApproverDeptName = currentApproverDeptName;
	}

	public Date getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}

	public String getApprovalViews() {
		return approvalViews;
	}

	public void setApprovalViews(String approvalViews) {
		this.approvalViews = approvalViews;
	}

	public String getApprovalResult() {
		return approvalResult;
	}

	public void setApprovalResult(String approvalResult) {
		this.approvalResult = approvalResult;
	}

	public String getProcessDefName() {
		return processDefName;
	}

	public void setProcessDefName(String processDefName) {
		this.processDefName = processDefName;
	}

	public String getWorkFlowType() {
		return workFlowType;
	}

	public void setWorkFlowType(String workFlowType) {
		this.workFlowType = workFlowType;
	}
	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
    
}
