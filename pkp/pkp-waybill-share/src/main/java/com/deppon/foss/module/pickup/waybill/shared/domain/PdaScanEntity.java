/**
 * PDA盲扫信息
 */
package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * PDA盲扫信息
 * @author FOSS-136334-BaiLei
 * @date 2014-12-30 14:43:42
 * 
 */
public class PdaScanEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7964574540007997495L;

	/**
	 * 运单id
	 */
	private String id;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 流水号
	 */
	private String serialNo;

	/**
	 * 司机/快递员工号
	 */
	private String driverCode;
	
	/**
	 * 运单类型，EWAYBILL为电子运单
	 */
	private String waybillType;

	/**
	 * 单个流水号重量
	 */
	private BigDecimal weight;

	/**
	 * 单个流水号体积
	 */
	private BigDecimal volume;

	/**
	 * 任务类型，PICKUP为收货任务
	 */
	private String taskType;
	
	/**
	 * 任务ID
	 */
	private String taskId;
	
	/**
	 * 扫描类型，SCAN为正扫，CANCEL为反扫
	 */
	private String scanType;

	/**
	 * 扫描时间
	 */
	private Date scanTime;
	
	/**
	 * 更新时间
	 */
	private Date modifyTime;
	
	/**
	 * 是否完成状态
	 */
	private String whetherComplete;

	/**
	 * 有效状态，Y为有效，N为无效
	 */
	private String active;
	/*
	 * 库存部门编码
	 * */
	private String operateOrgCode;

	/**
	 * 开单组织
	 */
	private String createOrgCode;
	/*
	 * 
	 * 理货员工号
	 * */
	private String clerkCode;
	
	/**
	 * 收货部门
	 * */
	private String receiveOrgCode;
	
	
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	public String getClerkCode() {
		return clerkCode;
	}

	public void setClerkCode(String clerkCode) {
		this.clerkCode = clerkCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getWaybillType() {
		return waybillType;
	}

	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	
	public String getScanType() {
		return scanType;
	}

	public void setScanType(String scanType) {
		this.scanType = scanType;
	}

	public Date getScanTime() {
		return scanTime;
	}

	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getWhetherComplete() {
		return whetherComplete;
	}

	public void setWhetherComplete(String whetherComplete) {
		this.whetherComplete = whetherComplete;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
}
