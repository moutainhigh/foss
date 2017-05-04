package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;
import java.util.Date;

public class PdaOrderOmsDto  implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 订单id
	 */
	private String id;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 订单类型 -- 接货or转货
	 */
	private String orderType;
	/**
	 * 司机编码
	 */
	private String driverCode;
	/**
	 * 更新时间
	 */
	private Date updateDateTime;
	/**
	 * 订单状态（已读、接收、退回）
	 */
	private String orderStatus;
	/**
	 * 退回原因
	 */
	private String returnReason;
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 转发人工号
	 */
	private String forwardDriverCode;
	
	/**
	 * 转发人姓名
	 */
	private String forwardDriverName;
	
	/**
	 * 操作状态(1、退回：ORDER_RETURN  2、转发：ORDER_FORWARD)
	 */
	private String optState;

	/**
	 * 接货最早时间
	 */
	private Date earliestPickupTime;
	/**
	 * 接货最晚时间
	 */
	private Date latestPickupTime;
	/**
	 * 快递员电话
	 */
	private String expressEmpTel;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getDriverCode() {
		return driverCode;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	public Date getUpdateDateTime() {
		return updateDateTime;
	}
	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getReturnReason() {
		return returnReason;
	}
	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getForwardDriverCode() {
		return forwardDriverCode;
	}
	public void setForwardDriverCode(String forwardDriverCode) {
		this.forwardDriverCode = forwardDriverCode;
	}
	public String getForwardDriverName() {
		return forwardDriverName;
	}
	public void setForwardDriverName(String forwardDriverName) {
		this.forwardDriverName = forwardDriverName;
	}
	public String getOptState() {
		return optState;
	}
	public void setOptState(String optState) {
		this.optState = optState;
	}
	public Date getEarliestPickupTime() {
		return earliestPickupTime;
	}
	public void setEarliestPickupTime(Date earliestPickupTime) {
		this.earliestPickupTime = earliestPickupTime;
	}
	public Date getLatestPickupTime() {
		return latestPickupTime;
	}
	public void setLatestPickupTime(Date latestPickupTime) {
		this.latestPickupTime = latestPickupTime;
	}
	public String getExpressEmpTel() {
		return expressEmpTel;
	}
	public void setExpressEmpTel(String expressEmpTel) {
		this.expressEmpTel = expressEmpTel;
	}
	
}
