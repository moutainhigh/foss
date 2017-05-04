package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * FOSS地址库实体
 * @author 198771
 *
 */
public class ExpressDeliveryAddressEntity extends BaseEntity{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 165247628792132574L;
	/**
	 * 营业部名称
	 */
	private String departmentName;
	/**
	 * 营业部编码
	 */
	private String departmentCode;
	/**
	 * 省
	 */
	private String provinceName;
	/**
	 * 省编码
	 */
	private String provinceCode;
	/**
	 * 市
	 */
	private String cityName;
	/**
	 * 市编码
	 */
	private String cityCode;
	/**
	 * 区县
	 */
	private String countyName;
	/**
	 * 区县编码
	 */
	private String countyCode;
	/**
	 * 乡镇
	 */
	private String townName;
	/**
	 * 乡镇编码
	 */
	private String townCode;
	/**
	 * 街/道
	 */
	private String street;
	/**
	 * 省市区地址
	 */
	private String pccAddress;
	/**
	 * 门牌号起
	 */
	private String startHouseNumber;
	/**
	 * 门牌号止
	 */
	private String endHouseNumber;
	
	/**
	 * 生效时间
	 */
	private Date startTime;
	/**
	 * 失效时间
	 */
	private Date endTime;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 地标
	 */
	private String sign;
	
	/**
	 * 是否启用
	 */
	private String active;
	
	/**
	 * 操作人姓名
	 */
	private String operatorName;
	
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getCountyCode() {
		return countyCode;
	}
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
	public String getTownCode() {
		return townCode;
	}
	public void setTownCode(String townCode) {
		this.townCode = townCode;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getPccAddress() {
		return pccAddress;
	}
	public void setPccAddress(String pccAddress) {
		this.pccAddress = pccAddress;
	}
	public String getStartHouseNumber() {
		return startHouseNumber;
	}
	public void setStartHouseNumber(String startHouseNumber) {
		this.startHouseNumber = startHouseNumber;
	}
	public String getEndHouseNumber() {
		return endHouseNumber;
	}
	public void setEndHouseNumber(String endHouseNumber) {
		this.endHouseNumber = endHouseNumber;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
}
