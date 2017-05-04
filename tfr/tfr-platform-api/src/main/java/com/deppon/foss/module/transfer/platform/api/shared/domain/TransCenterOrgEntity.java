package com.deppon.foss.module.transfer.platform.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;


/**
* @description 查询所有外场对应的事业部 以及 本部 实体类封装
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年3月13日 上午11:35:35
*/
public class TransCenterOrgEntity extends BaseEntity {

	
	/**
	* @fields serialVersionUID
	* @author 14022-foss-songjie
	* @update 2014年3月13日 上午11:33:14
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	* @fields 部门code,name,本部 ,事业部,大区
	* @author 14022-foss-songjie
	* @update 2014年3月13日 上午11:35:12
	* @version V1.0
	*/
	private String orgCode, name,bigdept,division,bigArea;

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBigdept() {
		return bigdept;
	}

	public void setBigdept(String bigdept) {
		this.bigdept = bigdept;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getBigArea() {
		return bigArea;
	}

	public void setBigArea(String bigArea) {
		this.bigArea = bigArea;
	}
	
	

}
