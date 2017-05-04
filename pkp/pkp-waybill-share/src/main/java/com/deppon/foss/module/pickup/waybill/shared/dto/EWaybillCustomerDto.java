package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
/**
 * 查询客户信息的Dto
 * @author Foss-105888-Zhangxingwang
 * @date 2014-7-16 16:54:42
 */
public class EWaybillCustomerDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
     * 联系人名称.
     */
    private String contactName;
    
    /**
     * 联系人ID.
     */
    private String contactId;
    
    /**
     * 联系人编码.
     */
    private String contactCode;
    
    /**
     * 联系人手机.
     */
    private String mobilePhone;
    
    /**
     * 办公电话.
     */
    private String officePhone;
    
    /**
     * 联系人地址.
     */
    private String address;
	
	/**
	 * 下单量
	 */
	private Integer eWaybillTotal;
	
	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * 是否经过地址采集
	 */
	private String isCollectGps;
	
	private String customerName;
	
	
	private String customerCode;

	
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getContactCode() {
		return contactCode;
	}

	public void setContactCode(String contactCode) {
		this.contactCode = contactCode;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer geteWaybillTotal() {
		return eWaybillTotal;
	}

	public void seteWaybillTotal(Integer eWaybillTotal) {
		this.eWaybillTotal = eWaybillTotal;
	}
	
	public String getIsCollectGps() {
		return isCollectGps;
	}
	
	public void setIsCollectGps(String isCollectGps) {
		this.isCollectGps = isCollectGps;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
}
