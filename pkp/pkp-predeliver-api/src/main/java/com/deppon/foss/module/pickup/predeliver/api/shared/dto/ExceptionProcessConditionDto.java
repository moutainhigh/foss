/**
 *  initial comments.
 */
/*
 * PROJECT NAME: pkp-predeliver-api
 * PACKAGE NAME: com.deppon.foss.module.pickup.predeliver.api.shared.dto
 * FILE    NAME: ExceptionProcessConditionDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * 处理异常DTO.
 *
 * @author 043258-foss-zhaobin
 * @date 2012-10-30 上午9:05:52
 */
public class ExceptionProcessConditionDto implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** 库存天数(起). */
	private String storageDayBegin;
	
	/** 库存天数(止). */
	private String storageDayEnd;
	
	/** 登记时间(起). */
	private Date exceptionTimeBegin;
	
	/** 登记时间(止). */
	private Date exceptionTimeEnd;
	
	/** 异常类型. */
	private String exceptionType;
	
	/** 异常环节. */
	private String exceptionLink;
	
	/** 状态. */
	private String status;
	
	/** 运单号. */
	private String waybillNo;
	
	/** 异常_ID. */
	private String tSrvExceptionId;
	
	/** 异常处理结果. */
	private String notes;
	
	/** 流水号. */
	private String serialNo;
	
	/** 当前登录部门. */
	private String departmentCode;
	
	/** 运单状态. */
	private String active;
	
	private String arrivesheetId;
	/** 区县编码. */
	private String countyCode;
	/** 区县名称. */
	private String countyName;
	
	
	/** 异常操作. */
	private String exceptionOperate;
	/** 通知内容. */
	private String noticeContext;
	
	/** 预计送货日期. */
	private Date deliverDate;
	
	/**
	 * 选择的运单号列表
	 */
	private String[] arrayWaybillNos;
	/**
	 * 更新时间(起)
	 */
	private Date modifyTimeBegin;
	
	/**
	 * 更新时间(止)
	 */
	private Date modifyTimeEnd;
	
	/**
	 * 异常原因
	 */
	private String exceptionReason;
	
	/**
	 * 签收记录状态(自定义)
	 */
	private String signRecord;
	

	public String getExceptionOperate() {
		return exceptionOperate;
	}

	public void setExceptionOperate(String exceptionOperate) {
		this.exceptionOperate = exceptionOperate;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	/**
	 * Gets the t srv exception id.
	 *
	 * @return the tSrvExceptionId
	 */
	public String getTSrvExceptionId() {
		return tSrvExceptionId;
	}

	/**
	 * Sets the t srv exception id.
	 *
	 * @param tSrvExceptionId the tSrvExceptionId to see
	 */
	public void setTSrvExceptionId(String tSrvExceptionId) {
		this.tSrvExceptionId = tSrvExceptionId;
	}

	/**
	 * Gets the storage day begin.
	 *
	 * @return the storageDayBegin
	 */
	public String getStorageDayBegin() {
		return storageDayBegin;
	}

	/**
	 * Sets the storage day begin.
	 *
	 * @param storageDayBegin the storageDayBegin to see
	 */
	public void setStorageDayBegin(String storageDayBegin) {
		this.storageDayBegin = storageDayBegin;
	}

	/**
	 * Gets the storage day end.
	 *
	 * @return the storageDayEnd
	 */
	public String getStorageDayEnd() {
		return storageDayEnd;
	}

	/**
	 * Sets the storage day end.
	 *
	 * @param storageDayEnd the storageDayEnd to see
	 */
	public void setStorageDayEnd(String storageDayEnd) {
		this.storageDayEnd = storageDayEnd;
	}

	/**
	 * Gets the exception time begin.
	 *
	 * @return the exceptionTimeBegin
	 */
	public Date getExceptionTimeBegin() {
		return exceptionTimeBegin;
	}

	/**
	 * Sets the exception time begin.
	 *
	 * @param exceptionTimeBegin the exceptionTimeBegin to see
	 */
	@DateFormat
	public void setExceptionTimeBegin(Date exceptionTimeBegin) {
		this.exceptionTimeBegin = exceptionTimeBegin;
	}

	/**
	 * Gets the exception time end.
	 *
	 * @return the exceptionTimeEnd
	 */
	public Date getExceptionTimeEnd() {
		return exceptionTimeEnd;
	}

	/**
	 * Sets the exception time end.
	 *
	 * @param exceptionTimeEnd the exceptionTimeEnd to see
	 */
	@DateFormat
	public void setExceptionTimeEnd(Date exceptionTimeEnd) {
		this.exceptionTimeEnd = exceptionTimeEnd;
	}

	/**
	 * Gets the exception type.
	 *
	 * @return the exceptionType
	 */
	public String getExceptionType() {
		return exceptionType;
	}

	/**
	 * Sets the exception type.
	 *
	 * @param exceptionType the exceptionType to see
	 */
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	/**
	 * Gets the exception link.
	 *
	 * @return the exceptionLink
	 */
	public String getExceptionLink() {
		return exceptionLink;
	}

	/**
	 * Sets the exception link.
	 *
	 * @param exceptionLink the exceptionLink to see
	 */
	public void setExceptionLink(String exceptionLink) {
		this.exceptionLink = exceptionLink;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the status to see
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the waybill no.
	 *
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * Sets the waybill no.
	 *
	 * @param waybillNo the waybillNo to see
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * Gets the notes.
	 *
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Sets the notes.
	 *
	 * @param notes the notes to see
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * Gets the serial no.
	 *
	 * @return the serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * Sets the serial no.
	 *
	 * @param serialNo the serialNo to see
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * Gets the department code.
	 *
	 * @return the departmentCode
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * Sets the department code.
	 *
	 * @param departmentCode the departmentCode to see
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**
	 * Gets the active.
	 *
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * Sets the active.
	 *
	 * @param active the active to see
	 */
	public void setActive(String active) {
		this.active = active;
	}

	public String gettSrvExceptionId() {
		return tSrvExceptionId;
	}

	public void settSrvExceptionId(String tSrvExceptionId) {
		this.tSrvExceptionId = tSrvExceptionId;
	}

	public String getArrivesheetId() {
		return arrivesheetId;
	}

	public void setArrivesheetId(String arrivesheetId) {
		this.arrivesheetId = arrivesheetId;
	}

	public Date getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	public String getNoticeContext() {
		return noticeContext;
	}

	public void setNoticeContext(String noticeContext) {
		this.noticeContext = noticeContext;
	}

	public String[] getArrayWaybillNos() {
		return arrayWaybillNos;
	}

	public void setArrayWaybillNos(String[] arrayWaybillNos) {
		this.arrayWaybillNos = arrayWaybillNos;
	}

	public Date getModifyTimeBegin() {
		return modifyTimeBegin;
	}

	public void setModifyTimeBegin(Date modifyTimeBegin) {
		this.modifyTimeBegin = modifyTimeBegin;
	}

	public Date getModifyTimeEnd() {
		return modifyTimeEnd;
	}

	public void setModifyTimeEnd(Date modifyTimeEnd) {
		this.modifyTimeEnd = modifyTimeEnd;
	}

	public String getExceptionReason() {
		return exceptionReason;
	}

	public void setExceptionReason(String exceptionReason) {
		this.exceptionReason = exceptionReason;
	}

	public String getSignRecord() {
		return signRecord;
	}

	public void setSignRecord(String signRecord) {
		this.signRecord = signRecord;
	}

}