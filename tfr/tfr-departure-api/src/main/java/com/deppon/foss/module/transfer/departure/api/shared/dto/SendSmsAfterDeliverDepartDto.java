/**  
 * Project Name:tfr-departure-api  
 * File Name:SendSmsAfterDeliverDepartDto.java  
 * Package Name:com.deppon.foss.module.transfer.departure.api.shared.dto  
 * Date:2015年5月24日下午2:47:29  
 * Copyright (c) 2015, shiwei@outlook.com All Rights Reserved.  
 *  
*/  
  
package com.deppon.foss.module.transfer.departure.api.shared.dto;  

import java.math.BigDecimal;

/**  
 * ClassName:SendSmsAfterDeliverDepartDto <br/>  
 * Reason:   派送放行时，给收货客户发短信，需要获取的运单明细字段. <br/>  
 * Date:     2015年5月24日 下午2:47:29 <br/>  
 * @author   shiwei-045923 shiwei@outlook.com  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */
public class SendSmsAfterDeliverDepartDto {

	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 发货客户名称
	 */
	private String deliveryCustomerName;
	
	/**
	 * 收货客户名称
	 */
	private String receiveCustomerName;
	
	/**
	 * 收货客户手机
	 */
	private String receiveCustomerMobilephone;
	
	/**
	 * 货物总件数
	 */
	private Integer goodsQtyTotal;
	
	/**
	 * 派送件数
	 */
	private Integer deliverQtyTotal;
	
	/**
	 * 派送重量
	 */
	private BigDecimal deliverWeight;
	
	/**
	 * 派送体积
	 */
	private BigDecimal deliverVolume;
	
	/**
	 * 司机姓名
	 */
	private String driverName;
	
	/**
	 * 司机工号
	 */
	private String driverCode;
	
	/**
	 * 司机手机
	 */
	private String driverMobilephone;
	
	/**
	 * 派送部门code
	 */
	private String orgCode;
	
	/**
	 * 到付金额
	 */
	private BigDecimal toPayAmount;

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}

	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public Integer getDeliverQtyTotal() {
		return deliverQtyTotal;
	}

	public void setDeliverQtyTotal(Integer deliverQtyTotal) {
		this.deliverQtyTotal = deliverQtyTotal;
	}

	public BigDecimal getDeliverWeight() {
		return deliverWeight;
	}

	public void setDeliverWeight(BigDecimal deliverWeight) {
		this.deliverWeight = deliverWeight;
	}

	public BigDecimal getDeliverVolume() {
		return deliverVolume;
	}

	public void setDeliverVolume(BigDecimal deliverVolume) {
		this.deliverVolume = deliverVolume;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverMobilephone() {
		return driverMobilephone;
	}

	public void setDriverMobilephone(String driverMobilephone) {
		this.driverMobilephone = driverMobilephone;
	}

	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}
	
	
}
  
