package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;

/**
 * 菜鸟二期 - 电子面单地址信息实体
 * @description DOP 与 菜鸟平台交互使用 电子面单查询信息中属性
 * @version V1.0
 * @author css
 * @date 2015-6-13
 */
//@XmlAccessorType(XmlAccessType.FIELD)
public class WaybillAddressDto implements Serializable {
	
	/**
	 * serialUID
	 */
	private static final long serialVersionUID = -8763297562708959302L;

	// 省名称	@XmlElement(name = "provinceName")
	private String provinceName;
	
	// 省地址编码（菜鸟地址库编码）	@XmlElement(name = "provinceCode")
	private Long provinceCode;
	
	// 市名称(如果是省管区县，则为空)	@XmlElement(name = "cityName")
	private String cityName;
	
	// 市地址编码（菜鸟地址库编码）	@XmlElement(name = "cityCode")
	private Long cityCode;
	
	// 区名称	@XmlElement(name = "areaName")
	private String areaName;
	
	// 区编码（菜鸟地址库编码）	@XmlElement(name = "areacode")
	private Long areacode;
	
	// 街道&镇名称	@XmlElement(name = "townName")
	private String townName;
	
	// 街道&镇编码（菜鸟地址库编码）	@XmlElement(name = "townCode")
	private Long townCode;
	
	// 剩余详细地址信息	@XmlElement(name = "detailAddress")
	private String detailAddress;

	public WaybillAddressDto() {
		super();
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Long getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(Long provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Long getCityCode() {
		return cityCode;
	}

	public void setCityCode(Long cityCode) {
		this.cityCode = cityCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Long getAreacode() {
		return areacode;
	}

	public void setAreacode(Long areacode) {
		this.areacode = areacode;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public Long getTownCode() {
		return townCode;
	}

	public void setTownCode(Long townCode) {
		this.townCode = townCode;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	@Override
	public String toString() {
		return "WaybillAddress [provinceName=" + provinceName
				+ ", provinceCode=" + provinceCode + ", cityName=" + cityName
				+ ", cityCode=" + cityCode + ", areaName=" + areaName
				+ ", areacode=" + areacode + ", townName=" + townName
				+ ", townCode=" + townCode + ", detailAddress=" + detailAddress
				+ "]";
	}
	
}
