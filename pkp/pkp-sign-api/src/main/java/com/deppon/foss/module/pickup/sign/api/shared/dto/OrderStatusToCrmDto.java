package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;

/**
 * 订单签收状态更新dto
 * @author 243921
 * @date 2016-03-03 上午8:42:28
 *
 */
public class OrderStatusToCrmDto  implements Serializable{
	/**
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 发货客户编码
	 */
	private String customerCode;
	/**
	 * 操作备注
	 */
	private String backInfo;
	/**
	 * 操作人编码
	 */
	private String oprateUserCode;
	/**
	 * 操作人组织编码
	 */
	private String oprateDeptCode;
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
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getBackInfo() {
		return backInfo;
	}
	public void setBackInfo(String backInfo) {
		this.backInfo = backInfo;
	}
	public String getOprateUserCode() {
		return oprateUserCode;
	}
	public void setOprateUserCode(String oprateUserCode) {
		this.oprateUserCode = oprateUserCode;
	}
	public String getOprateDeptCode() {
		return oprateDeptCode;
	}
	public void setOprateDeptCode(String oprateDeptCode) {
		this.oprateDeptCode = oprateDeptCode;
	}

}
