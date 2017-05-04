package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

public class TaobaoDepponDistrictMapEntity extends BaseEntity{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 61632942L; 
	/**
	 * 淘宝省
	 */
	private String taobaoProvince;
	/**
	 * 淘宝市
	 */
	private String taobaoCity;
	/**
	 * 淘宝区
	 */
	private String taobaoCounty;
	/**
	 * 德邦省
	 */
	private String depponProvince;
	/**
	 * 德邦市
	 */
	private String depponCity;
	/**
	 * 德邦区
	 */
	private String depponCounty;
	/**
	 * 淘宝地址
	 */
	private String taobaoAddress;
	/**
	 * 德邦地址
	 */
	private String depponAddress;
	/**
	 * 是否有效
	 */
	private String active;
	/**
	 * get set 方法
	 */
	public String getTaobaoProvince() {
		return taobaoProvince;
	}
	public void setTaobaoProvince(String taobaoProvince) {
		this.taobaoProvince = taobaoProvince;
	}
	public String getTaobaoCity() {
		return taobaoCity;
	}
	public void setTaobaoCity(String taobaoCity) {
		this.taobaoCity = taobaoCity;
	}
	public String getTaobaoCounty() {
		return taobaoCounty;
	}
	public void setTaobaoCounty(String taobaoCounty) {
		this.taobaoCounty = taobaoCounty;
	}
	public String getDepponProvince() {
		return depponProvince;
	}
	public void setDepponProvince(String depponProvince) {
		this.depponProvince = depponProvince;
	}
	public String getDepponCity() {
		return depponCity;
	}
	public void setDepponCity(String depponCity) {
		this.depponCity = depponCity;
	}
	public String getDepponCounty() {
		return depponCounty;
	}
	public void setDepponCounty(String depponCounty) {
		this.depponCounty = depponCounty;
	}
	public String getTaobaoAddress() {
		return taobaoAddress;
	}
	public void setTaobaoAddress(String taobaoAddress) {
		this.taobaoAddress = taobaoAddress;
	}
	public String getDepponAddress() {
		return depponAddress;
	}
	public void setDepponAddress(String depponAddress) {
		this.depponAddress = depponAddress;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
}
