package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

public class BadWaybillDetailDto {
	
	//发货客户编码
	private String deliveryCustomerCode;
	
	//运单号
	private String waybillNo;
	
	//应收单号
	private String stlPayNo;
	
	//金额
	private BigDecimal charg;
	
	//坏账时间
	private Date createTime;
	

	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getStlPayNo() {
		return stlPayNo;
	}

	public void setStlPayNo(String stlPayNo) {
		this.stlPayNo = stlPayNo;
	}

	public BigDecimal getCharg() {
		return charg;
	}

	public void setCharg(BigDecimal charg) {
		this.charg = charg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	

}
