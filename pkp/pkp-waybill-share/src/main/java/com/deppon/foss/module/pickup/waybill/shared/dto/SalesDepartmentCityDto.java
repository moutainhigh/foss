/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

/**
 * 营业部城市dto
 * @author 026123-foss-lifengteng
 *
 */
public class SalesDepartmentCityDto implements Serializable {

	/**
     * 序列化对象ID
     */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 城市编码
	 */
	private String cityCode;
	
	/**
	 * 城市名称
	 */
	private String cityName;
	
	/**
	 * 营业部编码
	 */
	private String salesDepartmentCode;
	
	/**
	 * 省份编码
	 */
	private String provCode;

	/**
	 * 省份名称
	 */
	private String provName;
	
	/**
	 * 城市类型：试点城市、非试点城市（即为空）、快递代理理城市
	 */
	private String cityType;
	
	/**
	 * 是否是试点营业部
	 */
	private String testSalesDepartment;
	
	public String getTestSalesDepartment() {
		return testSalesDepartment;
	}

	public void setTestSalesDepartment(String testSalesDepartment) {
		this.testSalesDepartment = testSalesDepartment;
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

	public String getSalesDepartmentCode() {
		return salesDepartmentCode;
	}

	public void setSalesDepartmentCode(String salesDepartmentCode) {
		this.salesDepartmentCode = salesDepartmentCode;
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

	public String getCityType() {
		return cityType;
	}

	public void setCityType(String cityType) {
		this.cityType = cityType;
	}
	
	
}
