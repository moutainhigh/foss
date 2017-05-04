package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 点单差异报告流水
 * @author DPAP-CodeGenerator
 * @Date 2015-12-28 19:30:00
 */
public class OrderReportSerialNoDto implements Serializable {
	
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;

	/** 
	* id 
	*/
	private String id;
	
	/** 
	* 流水号 
	*/
	private String serialNo;
	
	/** 
	* 交接单号 
	*/
	private String handoverNo;
	
	/** 
	* 运单号 
	*/
	private String waybillNo;
	
	/** 
	* 点单差异类型（NORMAL正常,LOSE少货,MORE多货） 
	*/
	private String orderReportType;
	
	/** 
	* 备注 
	*/
	private String note;
	
	/** 
	* 差异原因 
	*/
	private String reason;
	
	/** 
	* 差异处理时间 
	*/
	private Date handleTime;
	/** 
	* 创建时间
	*/
	private Date createTime;
	
	/** 
	* 创建人
	*/
	private String createUserCode;	
	/** 
	* 修改时间
	*/
	private Date modifyTime;
	
	/** 
	* 修改人
	*/
	private String modifyUserCode;
	/**
	 * 是否处理过Y是N否
	 */
	private String isHandle;
	/**
	 * 点单任务差异报告明细Id
	 */
	private String detailId;

	/**
	 * @return  the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**get 流水号
	 * @return  the serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * set 流水号
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * get  交接单号
	 * @return  the handoverNo
	 */
	public String getHandoverNo() {
		return handoverNo;
	}

	/**
	 * set 交接单号
	 * @param handoverNo the handoverNo to set
	 */
	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}

	/**
	 * get运单号
	 * @return  the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * set 运单号
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * get点单差异类型（NORMAL正常,LOSE少货,MORE多货） 
	 * @return  the orderReportType
	 */
	public String getOrderReportType() {
		return orderReportType;
	}

	/**
	 * set 点单差异类型（NORMAL正常,LOSE少货,MORE多货） 
	 * @param orderReportType the orderReportType to set
	 */
	public void setOrderReportType(String orderReportType) {
		this.orderReportType = orderReportType;
	}

	/**备注
	 * @return  the note
	 */
	public String getNote() {
		return note;
	}

	/**备注
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 差异原因
	 * @return  the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * 差异原因
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * 差异处理时间
	 * @return  the handleTime
	 */
	public Date getHandleTime() {
		return handleTime;
	}

	/**
	 * 差异处理时间
	 * @param handleTime the handleTime to set
	 */
	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	/**
	 * @return  the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return  the createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * @param createUserCode the createUserCode to set
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * @return  the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return  the modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * @param modifyUserCode the modifyUserCode to set
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**
	 * 是否处理过Y是N否
	 * @return  the isHandle
	 */
	public String getIsHandle() {
		return isHandle;
	}

	/**
	 * 是否处理过Y是N否
	 * @param isHandle the isHandle to set
	 */
	public void setIsHandle(String isHandle) {
		this.isHandle = isHandle;
	}

	/**
	 * 点单任务差异报告明细Id
	 * @return  the detailId
	 */
	public String getDetailId() {
		return detailId;
	}
	/**
	 * 点单任务差异报告明细Id
	 * @param detailId the detailId to set
	 */
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	
}
