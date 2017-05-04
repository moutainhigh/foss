package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;

/**
 * 菜鸟二期 - 电子面单DOP与内部系统交互信息实体
 * @description DOP与FOSS等公司内部系统交互使用
 * @version V1.0
 * @author css
 * @date 2015-6-13
 */
//@XmlAccessorType(XmlAccessType.FIELD)
public class DopcnEwbQueryRespDto implements Serializable {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 6786255921261822912L;

	// 运单号	@XmlElement(name = "waybillNo")
	private String waybillNo;
	
	// 收货人姓名	@XmlElement(name = "consigneeName")
	private String consigneeName;
	
	// 收货人电话	@XmlElement(name = "consigneePhone")
	private String consigneePhone;
	
	// 收货地址	@XmlElement(name = "consigneeAddress")
	private WaybillAddressDto consigneeAddress;

	public DopcnEwbQueryRespDto() {
		super();
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
		return "CainiaoEwbQueryInfo [companyCode="
				+ ", waybillNo=" + waybillNo + ", consigneeName="
				+ consigneeName + ", consigneePhone=" + consigneePhone
				+ ", consigneeAddress=" + consigneeAddress + "]";
	}
	
}
