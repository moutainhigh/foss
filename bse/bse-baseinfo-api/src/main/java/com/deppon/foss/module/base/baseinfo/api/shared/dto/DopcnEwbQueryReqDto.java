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
public class DopcnEwbQueryReqDto implements Serializable {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 6786255921261822912L;

	// 物流公司名	@XmlElement(name = "companyCode")
	private String companyCode;
	
	// 运单号	@XmlElement(name = "waybillNo")
	private String waybillNo;
	
	public DopcnEwbQueryReqDto() {
		super();
		this.companyCode = "FOSS";
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

	@Override
	public String toString() {
		return "DopcnEwbQueryReq [companyCode=" + companyCode + ", waybillNo="
				+ waybillNo + "]";
	}

}
