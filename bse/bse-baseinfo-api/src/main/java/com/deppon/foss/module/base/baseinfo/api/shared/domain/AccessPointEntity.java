package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 接驳点实体
 * @author 198771
 *
 */
public class AccessPointEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7323635827977028083L;
	
	/**
	 * 接驳点名称
	 */
	private String name;
	/**
	 * 接驳点编码
	 */
	private String code;
	/**
	 * 经营大区编码
	 */
	private String bigRegionCode;
	/**
	 * 中转场编码
	 */
	private String transferCode;
	/**
	 * 状态
	 */
	private String statu;
	
	/**
	 * 启用
	 */
	private String active;
	/**
	 * 省份
	 */
	private String province;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 区县
	 */
	private String county;
	/**
	 * 街道
	 */
	private String street;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getBigRegionCode() {
		return bigRegionCode;
	}
	public void setBigRegionCode(String bigRegionCode) {
		this.bigRegionCode = bigRegionCode;
	}
	public String getTransferCode() {
		return transferCode;
	}
	public void setTransferCode(String transferCode) {
		this.transferCode = transferCode;
	}
	public String getStatu() {
		return statu;
	}
	public void setStatu(String statu) {
		this.statu = statu;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
}
