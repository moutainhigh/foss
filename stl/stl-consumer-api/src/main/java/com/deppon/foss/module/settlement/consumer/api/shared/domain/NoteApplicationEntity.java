package com.deppon.foss.module.settlement.consumer.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 小票单据申请审批
 * @author 101911-foss-zhouChunlai
 * @date 2012-10-10 下午4:37:54
 */
public class NoteApplicationEntity extends BaseEntity {

	private static final long serialVersionUID = -2552541394593632684L;

	
	/**
	 * 申请部门编码
	 */
	private String applyOrgCode;

	/**
	 * 申请部门名称
	 */
	private String applyOrgName;

	/**
	 * 申请数量
	 */
	private Integer applyAmount;

	/**
	 * 申请时间
	 */
	private Date applyTime;

	/**
	 * 申请人名称
	 */
	private String applyUserName;

	/**
	 * 申请人编码
	 */
	private String applyUserCode;

	/**
	 * 业务日期
	 */
	private Date businessDate;

	/**
	 * 单据状态
	 */
	private String status;

	/**
	 * 审批状态
	 */
	private String approveStatus;

	/**
	 * 审批人编码
	 */
	private String approveUserCode;

	/**
	 * 审批人名称
	 */
	private String approveUserName;

	/**
	 * 审批时间
	 */
	private Date approveTime;

	/**
	 * 审批人部门编码
	 */
	private String approveOrgCode;

	/**
	 * 审批部门名称
	 */
	private String approveOrgName;

	/**
	 * 审批未同意原因
	 */
	private String approveNotes;

	/**
	 * 下发人编码
	 */
	private String issuedUserCode;

	/**
	 * 下发时间
	 */
	private Date issuedTime;

	/**
	 * 下发人名称
	 */
	private String issuedUserName;

	/**
	 * 下发部门编码
	 */
	private String issuedOrgCode;

	/**
	 * 下发部门名称
	 */
	private String issuedOrgName;

	/**
	 * 入库时间
	 */
	private Date storageTime;

	/**
	 * 入库人编码
	 */
	private String storageUserCode;

	/**
	 * 入库人名称
	 */
	private String storageUserName;

	/**
	 * 核销时间
	 */
	private Date writeoffTime;

	/**
	 * 核销人编码
	 */
	private String writeoffUserCode;

	/**
	 * 核销人名称
	 */
	private String writeoffUserName;

	/**
	 * 核销部门编码
	 */
	private String writeoffOrgCode;

	/**
	 * 核销部门名称
	 */
	private String writeoffOrgName;

	/**
	 * 核销状态
	 */
	private String writeoffStatus;

	/**
	 * 核销单据备注信息
	 */
	private String writeoffNotes;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 修改人名称
	 */
	private String modifyUserName;

	/**
	 * 修改人编码
	 */
	private String modifyUserCode;

	/**
	 * 是否有效
	 */
	private String active;



	/**
	 * @return applyOrgCode
	 */
	public String getApplyOrgCode() {
		return applyOrgCode;
	}

	/**
	 * @param  applyOrgCode  
	 */
	public void setApplyOrgCode(String applyOrgCode) {
		this.applyOrgCode = applyOrgCode;
	}

	/**
	 * @return applyOrgName
	 */
	public String getApplyOrgName() {
		return applyOrgName;
	}

	/**
	 * @param  applyOrgName  
	 */
	public void setApplyOrgName(String applyOrgName) {
		this.applyOrgName = applyOrgName;
	}

	/**
	 * @return applyAmount
	 */
	public Integer getApplyAmount() {
		return applyAmount;
	}

	/**
	 * @param  applyAmount  
	 */
	public void setApplyAmount(Integer applyAmount) {
		this.applyAmount = applyAmount;
	}

	/**
	 * @return applyTime
	 */
	public Date getApplyTime() {
		return applyTime;
	}

	/**
	 * @param  applyTime  
	 */
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	/**
	 * @return applyUserName
	 */
	public String getApplyUserName() {
		return applyUserName;
	}

	/**
	 * @param  applyUserName  
	 */
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	/**
	 * @return applyUserCode
	 */
	public String getApplyUserCode() {
		return applyUserCode;
	}

	/**
	 * @param  applyUserCode  
	 */
	public void setApplyUserCode(String applyUserCode) {
		this.applyUserCode = applyUserCode;
	}

	/**
	 * @return businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param  businessDate  
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param  status  
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return approveStatus
	 */
	public String getApproveStatus() {
		return approveStatus;
	}

	/**
	 * @param  approveStatus  
	 */
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	/**
	 * @return approveUserCode
	 */
	public String getApproveUserCode() {
		return approveUserCode;
	}

	/**
	 * @param  approveUserCode  
	 */
	public void setApproveUserCode(String approveUserCode) {
		this.approveUserCode = approveUserCode;
	}

	/**
	 * @return approveUserName
	 */
	public String getApproveUserName() {
		return approveUserName;
	}

	/**
	 * @param  approveUserName  
	 */
	public void setApproveUserName(String approveUserName) {
		this.approveUserName = approveUserName;
	}

	/**
	 * @return approveTime
	 */
	public Date getApproveTime() {
		return approveTime;
	}

	/**
	 * @param  approveTime  
	 */
	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	/**
	 * @return approveOrgCode
	 */
	public String getApproveOrgCode() {
		return approveOrgCode;
	}

	/**
	 * @param  approveOrgCode  
	 */
	public void setApproveOrgCode(String approveOrgCode) {
		this.approveOrgCode = approveOrgCode;
	}

	/**
	 * @return approveOrgName
	 */
	public String getApproveOrgName() {
		return approveOrgName;
	}

	/**
	 * @param  approveOrgName  
	 */
	public void setApproveOrgName(String approveOrgName) {
		this.approveOrgName = approveOrgName;
	}

	/**
	 * @return approveNotes
	 */
	public String getApproveNotes() {
		return approveNotes;
	}

	/**
	 * @param  approveNotes  
	 */
	public void setApproveNotes(String approveNotes) {
		this.approveNotes = approveNotes;
	}

	/**
	 * @return issuedUserCode
	 */
	public String getIssuedUserCode() {
		return issuedUserCode;
	}

	/**
	 * @param  issuedUserCode  
	 */
	public void setIssuedUserCode(String issuedUserCode) {
		this.issuedUserCode = issuedUserCode;
	}

	/**
	 * @return issuedTime
	 */
	public Date getIssuedTime() {
		return issuedTime;
	}

	/**
	 * @param  issuedTime  
	 */
	public void setIssuedTime(Date issuedTime) {
		this.issuedTime = issuedTime;
	}

	/**
	 * @return issuedUserName
	 */
	public String getIssuedUserName() {
		return issuedUserName;
	}

	/**
	 * @param  issuedUserName  
	 */
	public void setIssuedUserName(String issuedUserName) {
		this.issuedUserName = issuedUserName;
	}

	/**
	 * @return issuedOrgCode
	 */
	public String getIssuedOrgCode() {
		return issuedOrgCode;
	}

	/**
	 * @param  issuedOrgCode  
	 */
	public void setIssuedOrgCode(String issuedOrgCode) {
		this.issuedOrgCode = issuedOrgCode;
	}

	/**
	 * @return issuedOrgName
	 */
	public String getIssuedOrgName() {
		return issuedOrgName;
	}

	/**
	 * @param  issuedOrgName  
	 */
	public void setIssuedOrgName(String issuedOrgName) {
		this.issuedOrgName = issuedOrgName;
	}

	/**
	 * @return storageTime
	 */
	public Date getStorageTime() {
		return storageTime;
	}

	/**
	 * @param  storageTime  
	 */
	public void setStorageTime(Date storageTime) {
		this.storageTime = storageTime;
	}

	/**
	 * @return storageUserCode
	 */
	public String getStorageUserCode() {
		return storageUserCode;
	}

	/**
	 * @param  storageUserCode  
	 */
	public void setStorageUserCode(String storageUserCode) {
		this.storageUserCode = storageUserCode;
	}

	/**
	 * @return storageUserName
	 */
	public String getStorageUserName() {
		return storageUserName;
	}

	/**
	 * @param  storageUserName  
	 */
	public void setStorageUserName(String storageUserName) {
		this.storageUserName = storageUserName;
	}

	/**
	 * @return writeoffTime
	 */
	public Date getWriteoffTime() {
		return writeoffTime;
	}

	/**
	 * @param  writeoffTime  
	 */
	public void setWriteoffTime(Date writeoffTime) {
		this.writeoffTime = writeoffTime;
	}

	/**
	 * @return writeoffUserCode
	 */
	public String getWriteoffUserCode() {
		return writeoffUserCode;
	}

	/**
	 * @param  writeoffUserCode  
	 */
	public void setWriteoffUserCode(String writeoffUserCode) {
		this.writeoffUserCode = writeoffUserCode;
	}

	/**
	 * @return writeoffUserName
	 */
	public String getWriteoffUserName() {
		return writeoffUserName;
	}

	/**
	 * @param  writeoffUserName  
	 */
	public void setWriteoffUserName(String writeoffUserName) {
		this.writeoffUserName = writeoffUserName;
	}

	/**
	 * @return writeoffOrgCode
	 */
	public String getWriteoffOrgCode() {
		return writeoffOrgCode;
	}

	/**
	 * @param  writeoffOrgCode  
	 */
	public void setWriteoffOrgCode(String writeoffOrgCode) {
		this.writeoffOrgCode = writeoffOrgCode;
	}

	/**
	 * @return writeoffOrgName
	 */
	public String getWriteoffOrgName() {
		return writeoffOrgName;
	}

	/**
	 * @param  writeoffOrgName  
	 */
	public void setWriteoffOrgName(String writeoffOrgName) {
		this.writeoffOrgName = writeoffOrgName;
	}

	/**
	 * @return writeoffStatus
	 */
	public String getWriteoffStatus() {
		return writeoffStatus;
	}

	/**
	 * @param  writeoffStatus  
	 */
	public void setWriteoffStatus(String writeoffStatus) {
		this.writeoffStatus = writeoffStatus;
	}

	/**
	 * @return writeoffNotes
	 */
	public String getWriteoffNotes() {
		return writeoffNotes;
	}

	/**
	 * @param  writeoffNotes  
	 */
	public void setWriteoffNotes(String writeoffNotes) {
		this.writeoffNotes = writeoffNotes;
	}

	/**
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param  createTime  
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param  modifyTime  
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return modifyUserName
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}

	/**
	 * @param  modifyUserName  
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	/**
	 * @return modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * @param  modifyUserCode  
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param  active  
	 */
	public void setActive(String active) {
		this.active = active;
	}

}
