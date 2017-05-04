package com.deppon.foss.module.settlement.consumer.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 超时装卸费申请单实体
 * <p style="display:none">
 * modifyRecord</p>
 * <p style="display:none">
 * version:V1.0,author:105762,date:2014-5-6 下午3:16:20,content:TODO</p>
 * @author 105762
 * @date 2014-5-6 下午3:16:20
 * @since
 * @version
 */
public class OverdueSFPaymentApplyEntity extends BaseEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5651309303914070136L;

	/**
	 * 申请部门编码
	 */
	private String applyOrgCode;
	/**
	 * 申请部门名称
	 */
	private String applyOrgName;
	/**
	 * 申请人
	 */
	private String applyUserName;
	/**
	 * 审批部门编码
	 */
	private String auditOrgCode;
	/**
	 * 审批部门名称
	 */
	private String auditOrgName;
	/**
	 * 审批状态
	 */
	private String auditStatus;
	/**
	 * 审批人
	 */
	private String auditUserName;
	
	/**
	 * 审批人编码
	 */
	private String auditUserCode;
	
	/**
	 * 创建日期
	 */
	private Date createTime;

	/**
	 * id
	 */
	private String id;

	/**
	 * 修改时间
	 */
	private Date modifyTime;
	/**
	 * 备注
	 */
	private String notes;

	/**
	 * 申请原因
	 */
	private String reason;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 应付单号
	 */
	private String payableNo;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 应付单ID
	 */
	private String payableId;

	/**
	 * 申请人工号
	 */
	private String applyUserCode;


	/**
	 * @return the applyUserCode
	 */
	public String getApplyUserCode() {
		return applyUserCode;
	}

	/**
	 * @param applyUserCode
	 *            the applyUserCode to set
	 */
	public void setApplyUserCode(String applyUserCode) {
		this.applyUserCode = applyUserCode;
	}

	/**
	 * @return the auditUserCode
	 */
	public String getAuditUserCode() {
		return auditUserCode;
	}

	/**
	 * @param auditUserCode
	 *            the auditUserCode to set
	 */
	public void setAuditUserCode(String auditUserCode) {
		this.auditUserCode = auditUserCode;
	}

	/**
	 * @return the applyOrgCode
	 */
	public String getApplyOrgCode() {
		return applyOrgCode;
	}

	/**
	 * @param applyOrgCode
	 *            the applyOrgCode to set
	 */
	public void setApplyOrgCode(String applyOrgCode) {
		this.applyOrgCode = applyOrgCode;
	}

	/**
	 * @return the applyOrgName
	 */
	public String getApplyOrgName() {
		return applyOrgName;
	}

	/**
	 * @param applyOrgName
	 *            the applyOrgName to set
	 */
	public void setApplyOrgName(String applyOrgName) {
		this.applyOrgName = applyOrgName;
	}

	/**
	 * @return the applyUserName
	 */
	public String getApplyUserName() {
		return applyUserName;
	}

	/**
	 * @param applyUserName
	 *            the applyUserName to set
	 */
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	/**
	 * @return the auditOrgCode
	 */
	public String getAuditOrgCode() {
		return auditOrgCode;
	}

	/**
	 * @param auditOrgCode
	 *            the auditOrgCode to set
	 */
	public void setAuditOrgCode(String auditOrgCode) {
		this.auditOrgCode = auditOrgCode;
	}

	/**
	 * @return the auditOrgName
	 */
	public String getAuditOrgName() {
		return auditOrgName;
	}

	/**
	 * @param auditOrgName
	 *            the auditOrgName to set
	 */
	public void setAuditOrgName(String auditOrgName) {
		this.auditOrgName = auditOrgName;
	}

	/**
	 * @return the auditStatus
	 */
	public String getAuditStatus() {
		return auditStatus;
	}

	/**
	 * @param auditStatus
	 *            the auditStatus to set
	 */
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	/**
	 * @return the auditUserName
	 */
	public String getAuditUserName() {
		return auditUserName;
	}

	/**
	 * @param auditUserName
	 *            the auditUserName to set
	 */
	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 *            the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 *            the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason
	 *            the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return the payableNo
	 */
	public String getPayableNo() {
		return payableNo;
	}

	/**
	 * @param payableNo
	 *            the payableNo to set
	 */
	public void setPayableNo(String payableNo) {
		this.payableNo = payableNo;
	}

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 *            the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the payableId
	 */
	public String getPayableId() {
		return payableId;
	}

	/**
	 * @param payableId
	 *            the payableId to set
	 */
	public void setPayableId(String payableId) {
		this.payableId = payableId;
	}

}
