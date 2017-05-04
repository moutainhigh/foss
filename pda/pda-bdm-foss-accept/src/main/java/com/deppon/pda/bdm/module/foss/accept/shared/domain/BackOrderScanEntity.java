package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.util.Date;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.shared.domain 
 * @file BackOrderScanEntity.java 
 * @description 退回订单
 * @author ChenLiang
 * @created 2012-12-31 下午2:19:49    
 * @version 1.0
 */
public class BackOrderScanEntity extends ScanMsgEntity {
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 车牌号
	 */
	private String truckCode;

	/**
	 * 订单编号/约车编号
	 */
	private String orderCode;

	/**
	 * 订单状态
	 */
	private String orderStatus;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 订单类型
	 */
	private String orderType;

	/**
	 * 退回原因
	 */
	private String backReason;
	/**
	 * 转发订单到司机工号
	 */
	private String forwardDriverCode;
	/**
	 * 转发订单到司机姓名
	 */
	private String forwardDriverName;
	/**
	 * 转发/退回ORDER_RETURN 退回      ORDER_FORWARD 转发
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

	public String getBackReason() {
		return backReason;
	}

	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getTruckCode() {
		return truckCode;
	}

	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

}