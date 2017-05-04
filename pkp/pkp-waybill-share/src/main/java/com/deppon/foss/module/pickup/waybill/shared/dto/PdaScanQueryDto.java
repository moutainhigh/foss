/*
 * 
 */
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * PDA扫描查询参数DTO
 * @author Bailei - 136334
 * @date 2015-1-4 下午9:01:04
 */
public class PdaScanQueryDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1617125812706533160L;

	/**
	 * 运单id
	 */
	private String id;
	
	/**
	 * 运单号
	 */
	private String waybillNo;

	//母单号
    private String parentWaybillNo;
	
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
	 * 是否完成状态
	 */
	private String whetherComplete;
	
	/**
	 * 有效状态，Y为有效，N为无效
	 */
	private String active;
	/**
	 * 货物重量
	 */
	private BigDecimal goodsWeight;
	/**
	 * 货物体积
	 */
	private BigDecimal goodsVolume;
	
	//200968 zwd 2015.3.26 卸车人编码
	private String clerkCode;
	//200968 zwd 2015.3.26  卸车部门
	private String operateOrgCode;
	
	//开单部门
	private String createOrgCode;

	//收货部门
	private String receiveOrgCode;
    
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	/**
     * 卸车部门编码
     * */
	public String getOperateOrgCode() {
	return operateOrgCode;
	}

	public void setOperateOrgCode(String operateOrgCode) {
	this.operateOrgCode = operateOrgCode;
	}
    /**
     * 卸车人编码
     * */
	public String getClerkCode() {
	return clerkCode;
	}
	 /**
	     * 卸车人编码
	     * */
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

	public String getParentWaybillNo() {
		return parentWaybillNo;
	}

	public void setParentWaybillNo(String parentWaybillNo) {
		this.parentWaybillNo = parentWaybillNo;
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getWhetherComplete() {
		return whetherComplete;
	}

	public void setWhetherComplete(String whetherComplete) {
		this.whetherComplete = whetherComplete;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public BigDecimal getGoodsWeight() {
		return goodsWeight;
	}

	public void setGoodsWeight(BigDecimal goodsWeight) {
		this.goodsWeight = goodsWeight;
	}

	public BigDecimal getGoodsVolume() {
		return goodsVolume;
	}

	public void setGoodsVolume(BigDecimal goodsVolume) {
		this.goodsVolume = goodsVolume;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
}