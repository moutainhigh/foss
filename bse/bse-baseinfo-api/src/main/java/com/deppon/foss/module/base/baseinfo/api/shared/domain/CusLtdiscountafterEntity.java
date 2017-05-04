package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

public class CusLtdiscountafterEntity  extends BaseEntity implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 以下都是CRM那边传过来的字段，字段名字未做改变BaseEntity中修改（创建）人
	 * 修改（创建）时间也是CRM的，CRM也传过来了
	 */
	
	
	/**
	 * fid 主键(CRM)
	 */
	private String fid;
	
	/**
	 * 方案名称(CRM)
	 */
	private String dealName;
	
	/**
	 * 客户名称(CRM)
	 */
	private String custName;
	
	/**
	 * 客户编码(CRM)
	 */
	private String custNum;
	
	/**
	 * 方案生效时间(CRM)
	 */
	private Date beginTime;
	
	/**
	 * 方案失效时间(CRM)
	 */
	private Date endTime;
	
	/**
	 * 方案编码(CRM)
	 */
	private String dealNumber;
	
	/**
	 * 方案状态 (0有效，1审批中，2无效,3删除)(CRM)
	 */
	private String status;
	
	/**
	 * 方案条目集合(CRM)
	 */
	private List<CusLtdiscountafterItemEntity> dealItems;
	
	/**
	 * 方案类型(CRM)
	 */
	private String preferType;
	
	/**
	 * 工作流号(CRM)
	 */
	private String workflowNum;
	
	/**
	 * 申请事由(CRM)
	 */
	private String applyContent;
	
	
	/**
	 * 是否有效(扩展)
	 */
	private String active;
	
	/**
	 * 合同起始日期(扩展)
	 * @return
	 */
	private Date programBeginTime;
	
	/**
	 * 合同到期日期(扩展)
	 */
	private Date programEndTime;
	
    /**
     * 所属部门标杆编码(扩展).
     */
    private String unifiedCode;
	
	
	public String getActive() {
		return active;
	}
	
	public void setActive(String active) {
		this.active = active;
	}
	
	public String getFid() {
		return fid;
	}
	
	public void setFid(String fid) {
		this.fid = fid;
	}
	
	public String getDealName() {
		return dealName;
	}
	
	public void setDealName(String dealName) {
		this.dealName = dealName;
	}
	
	public String getCustName() {
		return custName;
	}
	
	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	public String getCustNum() {
		return custNum;
	}
	
	public void setCustNum(String custNum) {
		this.custNum = custNum;
	}
	
	public Date getBeginTime() {
		return beginTime;
	}
	
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	public String getDealNumber() {
		return dealNumber;
	}
	
	public void setDealNumber(String dealNumber) {
		this.dealNumber = dealNumber;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<CusLtdiscountafterItemEntity> getDealItems() {
		return dealItems;
	}

	public void setDealItems(List<CusLtdiscountafterItemEntity> dealItems) {
		this.dealItems = dealItems;
	}

	public String getPreferType() {
		return preferType;
	}
	
	public void setPreferType(String preferType) {
		this.preferType = preferType;
	}
	
	public String getWorkflowNum() {
		return workflowNum;
	}
	
	public void setWorkflowNum(String workflowNum) {
		this.workflowNum = workflowNum;
	}
	
	public String getApplyContent() {
		return applyContent;
	}
	
	public void setApplyContent(String applyContent) {
		this.applyContent = applyContent;
	}

	public Date getProgramBeginTime() {
		return programBeginTime;
	}

	public void setProgramBeginTime(Date programBeginTime) {
		this.programBeginTime = programBeginTime;
	}

	public Date getProgramEndTime() {
		return programEndTime;
	}

	public void setProgramEndTime(Date programEndTime) {
		this.programEndTime = programEndTime;
	}

	public String getUnifiedCode() {
		return unifiedCode;
	}

	public void setUnifiedCode(String unifiedCode) {
		this.unifiedCode = unifiedCode;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}