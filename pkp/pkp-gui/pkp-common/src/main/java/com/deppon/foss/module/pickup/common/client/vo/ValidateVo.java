package com.deppon.foss.module.pickup.common.client.vo;

/**
 * 验证VO
 * @author WangQianJin
 * @date 2013-7-10 下午6:23:43
 */
public class ValidateVo {

	//提货网点
	private String customerPickupOrgCode;
	//产品性质
	private String productCode;
	//提货方式
	private String receiveMethod;
	//合票类型
	private String freightMethod;
	
	//是否需要校验提货网点
	private String isNeedValCus;

	public String getIsNeedValCus() {
		return isNeedValCus;
	}

	
	public void setIsNeedValCus(String isNeedValCus) {
		this.isNeedValCus = isNeedValCus;
	}

	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}
	
	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}
	
	public String getProductCode() {
		return productCode;
	}
	
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	public String getReceiveMethod() {
		return receiveMethod;
	}
	
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}
	
	public String getFreightMethod() {
		return freightMethod;
	}
	
	public void setFreightMethod(String freightMethod) {
		this.freightMethod = freightMethod;
	}
	
	
}
