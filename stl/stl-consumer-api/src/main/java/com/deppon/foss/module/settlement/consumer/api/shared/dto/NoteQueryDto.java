package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 小票单据综合查询DTO
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-10-23 上午8:13:41
 */
public class NoteQueryDto implements Serializable {

	private static final long serialVersionUID = 8366842884734558444L;

	/**
	 * 票据明细ID
	 */
	private String noteDetailsId;

	/**
	 * 票据申请ID
	 */
	private String noteAppId;

	/**
	 * 票据入库ID
	 */
	private String noteStockInId;

	/**
	 * 票据编码
	 */
	private String detailsNo;

	/**
	 * 申请部门编码
	 */
	private String applyOrgCode;

	/**
	 * 申请部门名称
	 */
	private String applyOrgName;

	/**
	 * 申请时间
	 */
	private Date applyTime;

	/**
	 * 下发起始编号
	 */
	private Integer beginNo;

	/**
	 * 下发终止编号
	 */
	private Integer endNo;

	/**
	 * 下发起始与终止组合 XX-XX
	 */
	private String beginWithEndNo;

	/**
	 * 下发人编码
	 */
	private String issuedUserCode;

	/**
	 * 下发人名称
	 */
	private String issuedUserName;

	/**
	 * 下发时间
	 */
	private Date issuedTime;

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
	 * 核销状态
	 */
	private String writeoffStatus;

	/**
	 * 核销人编码
	 */
	private String writeoffUserCode;

	/**
	 * 核销人名称
	 */
	private String writeoffUserName;

	/**
	 * 单据状态
	 */
	private String status;

	/**
	 * @return noteDetailsId
	 */
	public String getNoteDetailsId() {
		return noteDetailsId;
	}

	/**
	 * @param noteDetailsId
	 */
	public void setNoteDetailsId(String noteDetailsId) {
		this.noteDetailsId = noteDetailsId;
	}

	/**
	 * @return noteAppId
	 */
	public String getNoteAppId() {
		return noteAppId;
	}

	/**
	 * @param noteAppId
	 */
	public void setNoteAppId(String noteAppId) {
		this.noteAppId = noteAppId;
	}

	/**
	 * @return noteStockInId
	 */
	public String getNoteStockInId() {
		return noteStockInId;
	}

	/**
	 * @param noteStockInId
	 */
	public void setNoteStockInId(String noteStockInId) {
		this.noteStockInId = noteStockInId;
	}

	/**
	 * @return detailsNo
	 */
	public String getDetailsNo() {
		return detailsNo;
	}

	/**
	 * @param detailsNo
	 */
	public void setDetailsNo(String detailsNo) {
		this.detailsNo = detailsNo;
	}

	/**
	 * @return applyOrgCode
	 */
	public String getApplyOrgCode() {
		return applyOrgCode;
	}

	/**
	 * @param applyOrgCode
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
	 * @param applyOrgName
	 */
	public void setApplyOrgName(String applyOrgName) {
		this.applyOrgName = applyOrgName;
	}

	/**
	 * @return applyTime
	 */
	public Date getApplyTime() {
		return applyTime;
	}

	/**
	 * @param applyTime
	 */
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	/**
	 * @return beginNo
	 */
	public Integer getBeginNo() {
		return beginNo;
	}

	/**
	 * @param beginNo
	 */
	public void setBeginNo(Integer beginNo) {
		this.beginNo = beginNo;
	}

	/**
	 * @return endNo
	 */
	public Integer getEndNo() {
		return endNo;
	}

	/**
	 * @param endNo
	 */
	public void setEndNo(Integer endNo) {
		this.endNo = endNo;
	}

	/**
	 * @return beginWithEndNo
	 */
	public String getBeginWithEndNo() {
		return beginWithEndNo;
	}

	/**
	 * @param beginWithEndNo
	 */
	public void setBeginWithEndNo(String beginWithEndNo) {
		this.beginWithEndNo = beginWithEndNo;
	}

	/**
	 * @return issuedUserCode
	 */
	public String getIssuedUserCode() {
		return issuedUserCode;
	}

	/**
	 * @param issuedUserCode
	 */
	public void setIssuedUserCode(String issuedUserCode) {
		this.issuedUserCode = issuedUserCode;
	}

	/**
	 * @return issuedUserName
	 */
	public String getIssuedUserName() {
		return issuedUserName;
	}

	/**
	 * @param issuedUserName
	 */
	public void setIssuedUserName(String issuedUserName) {
		this.issuedUserName = issuedUserName;
	}

	/**
	 * @return issuedTime
	 */
	public Date getIssuedTime() {
		return issuedTime;
	}

	/**
	 * @param issuedTime
	 */
	public void setIssuedTime(Date issuedTime) {
		this.issuedTime = issuedTime;
	}

	/**
	 * @return storageTime
	 */
	public Date getStorageTime() {
		return storageTime;
	}

	/**
	 * @param storageTime
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
	 * @param storageUserCode
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
	 * @param storageUserName
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
	 * @param writeoffTime
	 */
	public void setWriteoffTime(Date writeoffTime) {
		this.writeoffTime = writeoffTime;
	}

	/**
	 * @return writeoffStatus
	 */
	public String getWriteoffStatus() {
		return writeoffStatus;
	}

	/**
	 * @param writeoffStatus
	 */
	public void setWriteoffStatus(String writeoffStatus) {
		this.writeoffStatus = writeoffStatus;
	}

	/**
	 * @return writeoffUserCode
	 */
	public String getWriteoffUserCode() {
		return writeoffUserCode;
	}

	/**
	 * @param writeoffUserCode
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
	 * @param writeoffUserName
	 */
	public void setWriteoffUserName(String writeoffUserName) {
		this.writeoffUserName = writeoffUserName;
	}

	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
