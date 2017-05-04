package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.util.Date;
import java.util.List;

/**
 * 汇款确认DTO
 * 
 * @author zbw
 * @date 2012-12-24 下午8:28:31
 */
public class BillCODPayConfirmDto {

	/**
	 * 批次号
	 */
	private String batchNumber;

	/**
	 * 代收货款状态
	 */
	private List<String> statuses;

	/**
	 * 付款人编码
	 */
	private String exportUserCode;

	/**
	 * 导出开始时间
	 */
	private Date exportStartTime;

	/**
	 * 导出结束时间
	 */
	private Date exportEndTime;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 应付单是否有效
	 */
	private String payableActive;

	/**
	 * 应付单单据子类型
	 */
	private String payableBillType;

	/**
	 * 退款路径
	 */
	private String refundPath;
	
	/** 核销类型.  */
	private String writeoffType;
	
	/**
	 * @return batchNumber
	 */
	public String getBatchNumber() {
		return batchNumber;
	}

	/**
	 * @param batchNumber
	 */
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	/**
	 * @return statuses
	 */
	public List<String> getStatuses() {
		return statuses;
	}

	/**
	 * @param statuses
	 */
	public void setStatuses(List<String> statuses) {
		this.statuses = statuses;
	}

	/**
	 * @return exportUserCode
	 */
	public String getExportUserCode() {
		return exportUserCode;
	}

	/**
	 * @param exportUserCode
	 */
	public void setExportUserCode(String exportUserCode) {
		this.exportUserCode = exportUserCode;
	}

	/**
	 * @return exportStartTime
	 */
	public Date getExportStartTime() {
		return exportStartTime;
	}

	/**
	 * @param exportStartTime
	 */
	public void setExportStartTime(Date exportStartTime) {
		this.exportStartTime = exportStartTime;
	}

	/**
	 * @return exportEndTime
	 */
	public Date getExportEndTime() {
		return exportEndTime;
	}

	/**
	 * @param exportEndTime
	 */
	public void setExportEndTime(Date exportEndTime) {
		this.exportEndTime = exportEndTime;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return payableActive
	 */
	public String getPayableActive() {
		return payableActive;
	}

	/**
	 * @param payableActive
	 */
	public void setPayableActive(String payableActive) {
		this.payableActive = payableActive;
	}

	/**
	 * @return payableBillType
	 */
	public String getPayableBillType() {
		return payableBillType;
	}

	/**
	 * @param payableBillType
	 */
	public void setPayableBillType(String payableBillType) {
		this.payableBillType = payableBillType;
	}

	/**
	 * @return refundPath
	 */
	public String getRefundPath() {
		return refundPath;
	}

	/**
	 * @param refundPath
	 */
	public void setRefundPath(String refundPath) {
		this.refundPath = refundPath;
	}

	/**
	 * @return writeoffType
	 */
	public String getWriteoffType() {
		return writeoffType;
	}

	/**
	 * @param writeoffType
	 */
	public void setWriteoffType(String writeoffType) {
		this.writeoffType = writeoffType;
	}

}
