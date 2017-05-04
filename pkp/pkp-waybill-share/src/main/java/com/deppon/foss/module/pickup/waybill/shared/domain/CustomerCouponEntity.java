package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
* @Description:  (客户与优惠券)
* @author hbhk 
* @date 2015-6-9 下午1:40:19
 */
public class CustomerCouponEntity extends BaseEntity{

	/**
	 *  
	 */
	private static final long serialVersionUID = 7976278670143033420L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	//客户编码
	private String customerCode;
	//优惠券编码
	private String couponCode;
	//是否是否用 Y 未使用 N 已使用
	private String used;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 面额
	 */
	private BigDecimal  amount;
	
	/**
	 * 有效时间
	 */
	private Date  activeDate;
	
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Date getActiveDate() {
		return activeDate;
	}
	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
}
