package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;

/**
 * 快递点部营业部配置时，所需营业部信息扩展Dto
 * @author foss-qiaolifeng
 * @date 2013-7-25 下午4:51:50
 */
public class ExpressSaleDepartmentResultDto extends SaleDepartmentEntity {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2608743167816845283L;

	/**
	 * 省份编码
	 */
	private String provCode;
	
	/**
	 * 省份名称
	 */
	private String provName;
	
	/**
	 * 城市编码
	 */
	private String cityCode;
	
	/**
	 * 城市名称
	 */
	private String cityName;
	
	/**
	 * 部门简称
	 */
	private String orgSimpleName;

	/**
	 * 部门英文简称
	 */
	private String orgEnSimple;

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

	public String getOrgSimpleName() {
		return orgSimpleName;
	}

	public void setOrgSimpleName(String orgSimpleName) {
		this.orgSimpleName = orgSimpleName;
	}

	public String getOrgEnSimple() {
		return orgEnSimple;
	}

	public void setOrgEnSimple(String orgEnSimple) {
		this.orgEnSimple = orgEnSimple;
	}
	
	
}
