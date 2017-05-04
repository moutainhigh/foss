package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
* @description 	OPP轨迹实体
* @version 1.0
* @author 269701-foss-lln
* @update 2016年5月16日 下午7:11:43
 */
public class OppLocusEntity implements Serializable{

	
	/**
	* @fields serialVersionUID
	* @author 269701-foss-lln
	* @update 2016年5月16日 下午7:14:40
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;
	/**id***/
	private String id;
	/**货物状态*/
	private String currentStatus;
	/**操作状态*/
	private String operStatus;
	/**操作件数 */
	private Integer  operNum;
	/**操作部门名称 */
	private String  operationOrgName;
	/**操作部门编码 */
	private String operationOrgCode;
	/** 操作人姓名*/
	private String operationName;
	/** 操作人编码 */
	private String operationCode;
	/** 操作时间*/
	private String operTime;
	/** 清单号*/
	private String airWaybillNo;
	/** 运单号*/
	private String waybillNo;
	/** 流水号*/
	private String serialNo;
	/** 返货备注*/
	private String notes;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getOperStatus() {
		return operStatus;
	}
	public void setOperStatus(String operStatus) {
		this.operStatus = operStatus;
	}
	public Integer getOperNum() {
		return operNum;
	}
	public void setOperNum(Integer operNum) {
		this.operNum = operNum;
	}
	public String getOperationOrgName() {
		return operationOrgName;
	}
	public void setOperationOrgName(String operationOrgName) {
		this.operationOrgName = operationOrgName;
	}
	public String getOperationOrgCode() {
		return operationOrgCode;
	}
	public void setOperationOrgCode(String operationOrgCode) {
		this.operationOrgCode = operationOrgCode;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getOperationCode() {
		return operationCode;
	}
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}
	public String getOperTime() {
		return operTime;
	}
	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}
	public String getAirWaybillNo() {
		return airWaybillNo;
	}
	public void setAirWaybillNo(String airWaybillNo) {
		this.airWaybillNo = airWaybillNo;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
}
