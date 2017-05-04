package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 空运代理网点和外场公共选择器
 * @author 130566
 *
 */
public class CommonAirPartAndDeptEntity  extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 部门编码
	 */
	private String agentDeptName;
	/**
	 * 部门名称
	 */
	private String agentDeptCode;
	/**
	 * 联系电话
	 */
	private String contactPhone;
	/**
	 * 城市编码
	 */
	private String cityCode;
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 是否启用
	 */
	private String active;
	
	
	
	public String getAgentDeptName() {
		return agentDeptName;
	}

	public void setAgentDeptName(String agentDeptName) {
		this.agentDeptName = agentDeptName;
	}

	public String getAgentDeptCode() {
		return agentDeptCode;
	}

	public void setAgentDeptCode(String agentDeptCode) {
		this.agentDeptCode = agentDeptCode;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	

}
