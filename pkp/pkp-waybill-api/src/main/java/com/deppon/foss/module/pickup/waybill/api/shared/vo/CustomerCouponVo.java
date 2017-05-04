package com.deppon.foss.module.pickup.waybill.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @Description: (客户与优惠券)
 * @author hbhk
 * @date 2015-6-9 下午1:39:43
 */
public class CustomerCouponVo implements Serializable {

	/**
	 *  
	 */
	private static final long serialVersionUID = 7976278670143033420L;

	// 客户编码
	private String customerCode;
	// 优惠券编码
	private String couponCode;
	private String phone;
	private BigDecimal amount;

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
	

}
