package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.NoteApplicationEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.NoteStockInEntity;

/**
 * 小票单据申请DTO
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-10-23 上午8:13:41
 */
public class NoteApplyDto extends NoteApplicationEntity {

	private static final long serialVersionUID = 8811703788888408081L;

	/**
	 * 申请开始时间
	 */
	private Date applyStartTime;

	/**
	 * 申请结束时间
	 */
	private Date applyEndTime;

	/**
	 * 入库开始时间
	 */
	private Date storageStartTime;

	/**
	 * 入库结束时间
	 */
	private Date storageEndTime;

	/**
	 * 下发开始时间
	 */
	private Date issuedStartTime;

	/**
	 * 下发结束时间
	 */
	private Date issuedEndTime;

	/**
	 * 核销开始时间
	 */
	private Date writeoffStartTime;

	/**
	 * 核销结束时间
	 */
	private Date writeoffEndTime;

	/**
	 * 状态类型
	 */
	private String statusType;

	/**
	 * 修改数据类别
	 */
	private String modifyDataType;

	/**
	 * 下发实体列表
	 */
	private List<NoteStockInEntity> stockInList;

	/**
	 * 要更新的App_ID号
	 */
	private List<String> noteAppIds;

	/**
	 * 下发开始编号
	 */
	private String beginNo;

	/**
	 * 下发结束编号
	 */
	private String endNo;

	/**
	 * 用户编码（权限）
	 */
	private String currentUserCode;

	/**
	 * @return applyStartTime
	 */
	public Date getApplyStartTime() {
		return applyStartTime;
	}

	/**
	 * @param applyStartTime
	 */
	public void setApplyStartTime(Date applyStartTime) {
		this.applyStartTime = applyStartTime;
	}

	/**
	 * @return applyEndTime
	 */
	public Date getApplyEndTime() {
		return applyEndTime;
	}

	/**
	 * @param applyEndTime
	 */
	public void setApplyEndTime(Date applyEndTime) {
		this.applyEndTime = applyEndTime;
	}

	/**
	 * @return storageStartTime
	 */
	public Date getStorageStartTime() {
		return storageStartTime;
	}

	/**
	 * @param storageStartTime
	 */
	public void setStorageStartTime(Date storageStartTime) {
		this.storageStartTime = storageStartTime;
	}

	/**
	 * @return storageEndTime
	 */
	public Date getStorageEndTime() {
		return storageEndTime;
	}

	/**
	 * @param storageEndTime
	 */
	public void setStorageEndTime(Date storageEndTime) {
		this.storageEndTime = storageEndTime;
	}

	/**
	 * @return issuedStartTime
	 */
	public Date getIssuedStartTime() {
		return issuedStartTime;
	}

	/**
	 * @param issuedStartTime
	 */
	public void setIssuedStartTime(Date issuedStartTime) {
		this.issuedStartTime = issuedStartTime;
	}

	/**
	 * @return issuedEndTime
	 */
	public Date getIssuedEndTime() {
		return issuedEndTime;
	}

	/**
	 * @param issuedEndTime
	 */
	public void setIssuedEndTime(Date issuedEndTime) {
		this.issuedEndTime = issuedEndTime;
	}

	/**
	 * @return writeoffStartTime
	 */
	public Date getWriteoffStartTime() {
		return writeoffStartTime;
	}

	/**
	 * @param writeoffStartTime
	 */
	public void setWriteoffStartTime(Date writeoffStartTime) {
		this.writeoffStartTime = writeoffStartTime;
	}

	/**
	 * @return writeoffEndTime
	 */
	public Date getWriteoffEndTime() {
		return writeoffEndTime;
	}

	/**
	 * @param writeoffEndTime
	 */
	public void setWriteoffEndTime(Date writeoffEndTime) {
		this.writeoffEndTime = writeoffEndTime;
	}

	/**
	 * @return statusType
	 */
	public String getStatusType() {
		return statusType;
	}

	/**
	 * @param statusType
	 */
	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}

	/**
	 * @return modifyDataType
	 */
	public String getModifyDataType() {
		return modifyDataType;
	}

	/**
	 * @param modifyDataType
	 */
	public void setModifyDataType(String modifyDataType) {
		this.modifyDataType = modifyDataType;
	}

	/**
	 * @return stockInList
	 */
	public List<NoteStockInEntity> getStockInList() {
		return stockInList;
	}

	/**
	 * @param stockInList
	 */
	public void setStockInList(List<NoteStockInEntity> stockInList) {
		this.stockInList = stockInList;
	}

	/**
	 * @return noteAppIds
	 */
	public List<String> getNoteAppIds() {
		return noteAppIds;
	}

	/**
	 * @param noteAppIds
	 */
	public void setNoteAppIds(List<String> noteAppIds) {
		this.noteAppIds = noteAppIds;
	}

	/**
	 * @return beginNo
	 */
	public String getBeginNo() {
		return beginNo;
	}

	/**
	 * @param beginNo
	 */
	public void setBeginNo(String beginNo) {
		this.beginNo = beginNo;
	}

	/**
	 * @return endNo
	 */
	public String getEndNo() {
		return endNo;
	}

	/**
	 * @param endNo
	 */
	public void setEndNo(String endNo) {
		this.endNo = endNo;
	}

	/**
	 * @return currentUserCode
	 */
	public String getCurrentUserCode() {
		return currentUserCode;
	}

	/**
	 * @param currentUserCode
	 */
	public void setCurrentUserCode(String currentUserCode) {
		this.currentUserCode = currentUserCode;
	}

}
