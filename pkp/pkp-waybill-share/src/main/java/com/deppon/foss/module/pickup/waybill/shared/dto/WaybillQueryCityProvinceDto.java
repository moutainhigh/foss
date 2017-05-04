/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

/**
 * @author ibm-foss-sxw
 *
 */
public class WaybillQueryCityProvinceDto  implements Serializable{
	/**
	 * 序列化标识 
	 */
	private static final long serialVersionUID = 4415497196785932002L;
	private String id;
	private String orgCode;
	private String orgName;
	private String cityCode;
	private String cityName;
	private String provCode;
	private String provName;
	private String countyCode;
	private String countyName;
	
	
	public String getCountyCode() {
		return countyCode;
	}
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getProvCode() {
		return provCode;
	}
	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}
	public String getProvName() {
		return provName;
	}
	public void setProvName(String provName) {
		this.provName = provName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	
}
