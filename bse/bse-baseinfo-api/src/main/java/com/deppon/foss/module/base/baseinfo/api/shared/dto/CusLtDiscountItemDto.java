package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class CusLtDiscountItemDto extends BaseEntity{
	 
	/**
	 * 此处应该序列化实体
	 */
	private static final long serialVersionUID = -2437469556018609496L;


	/**
	 * 梯度折扣基础资料ID
	 */
	private String id;
	
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
	
	//合同里面的
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
    
    /**
     * 合同表里的CRM_ID
     */
    private BigDecimal programCrmId;
    

	//详情里面
    /**
     * 详情主键itemId
     */
    private String itemId;
    
    /**
	 *  等级(CRM)
	 */
	private String degree;
	
	/**
	 * 最小金额(CRM)
	 */
	private double minAmount;
	
	/**
	 *  最大金额(CRM)
	 */
	private double maxAmount;
	
	/**
	 *  折扣(CRM)
	 */
	private double rate;
	
	/**
	 *  描述(CRM)
	 */
	private String itemDesc;
	
	/**
	 * 优惠方案id(CRM),关联基础资料里的fid
	 */
	private String dealId;
	
	/**
	 * 修改时间modifyDate(CRM)
	 * @return
	 */
	private Date modifyDate;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
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

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public double getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(double minAmount) {
		this.minAmount = minAmount;
	}

	public double getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(double maxAmount) {
		this.maxAmount = maxAmount;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	public BigDecimal getProgramCrmId() {
		return programCrmId;
	}

	public void setProgramCrmId(BigDecimal programCrmId) {
		this.programCrmId = programCrmId;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
}
