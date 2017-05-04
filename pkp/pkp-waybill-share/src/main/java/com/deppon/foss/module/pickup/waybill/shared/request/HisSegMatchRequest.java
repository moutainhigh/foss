package com.deppon.foss.module.pickup.waybill.shared.request;

import java.io.Serializable;
/**
 * gis查询目的站信息服务接口的请求实体参数
 * @author 321993 zhangdianhao
 * @date 2017-03-16 下午13:53:50
 * @version 1.0
 */
public class HisSegMatchRequest implements Serializable{
	
	private static final long serialVersionUID = -84182425582847995L;
	/**
	 * 运单号
	 */
	private String waybillNum;
	/**
	 * 省份名称
	 */
	private String province;
	/**
	 * 省份编码
	 */
	private String provinceCode;
	/**
	 * 城市名称
	 */
	private String city;
	/**
	 * 城市编码
	 */
	private String cityCode;
	/**
	 * 区/县名称
	 */
	private String county;
	/**
	 * 区/县编码
	 */
	private String countyCode;
	/**
	 * 乡镇名称
	 */
	private String town;
	/**
	 * 乡镇编码
	 */
	private String townCode;
	/**
	 * 详细地址
	 */
	private String detailAddress;
	/**
	 * 运输性质
	 */
	private String transType;
	/**
	 * 提货方式
	 */
	private String deliveryType;
	public String getWaybillNum() {
		return waybillNum;
	}
	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getCountyCode() {
		return countyCode;
	}
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getTownCode() {
		return townCode;
	}
	public void setTownCode(String townCode) {
		this.townCode = townCode;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

}
