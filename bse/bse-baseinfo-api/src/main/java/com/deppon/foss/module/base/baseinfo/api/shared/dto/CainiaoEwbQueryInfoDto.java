package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;

/**
 * 菜鸟二期 - 电子面单DOP与内部系统交互信息实体
 * @description DOP与FOSS等公司内部系统交互使用
 * @version V1.0
 * @author css
 * @date 2015-6-13
 */

public class CainiaoEwbQueryInfoDto implements Serializable {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 6786255921261822912L;

	// 物流公司名
	private String companyCode;
	
	// 运单号
	private String waybillNo;
	
	// 收货人姓名
	private String consigneeName;
	
	// 收货人电话
	private String consigneePhone;
	
	// 收货地址
	private WaybillAddressDto consigneeAddress;

	public CainiaoEwbQueryInfoDto() {
		super();
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneePhone() {
		return consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}

	public WaybillAddressDto getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(WaybillAddressDto consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	@Override
	public String toString() {
		return "CainiaoEwbQueryInfo [companyCode=" + companyCode
				+ ", waybillNo=" + waybillNo + ", consigneeName="
				+ consigneeName + ", consigneePhone=" + consigneePhone
				+ ", consigneeAddress=" + consigneeAddress + "]";
	}
	
}
