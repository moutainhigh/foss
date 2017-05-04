package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;

/**
 *
 *<p>Title: SysToGisOrgDto</p>
 * <p>Description:同步给GIS的组织dto实体 </p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-5-14
 */
public class SysToGisOrgDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3549852459199635642L;
	/**
	 * 部门编码
	 */
	private String orgCode;
	/**
	 * 部门名称
	 */
	private String orgName;
	/**
	 * 部门服务坐标编码
	 */
	private String orgCoordinate;
	
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
	public String getOrgCoordinate() {
		return orgCoordinate;
	}
	public void setOrgCoordinate(String orgCoordinate) {
		this.orgCoordinate = orgCoordinate;
	}
	
	

}
