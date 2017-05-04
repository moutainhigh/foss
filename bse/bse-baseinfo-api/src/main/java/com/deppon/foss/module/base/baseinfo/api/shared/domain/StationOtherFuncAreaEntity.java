package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

public class StationOtherFuncAreaEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String deptCode;

	private String deptName;

	private String deptNo;

	private String funcAreaName;

	private String funcAreaNo;

	private Double lat;

	private Double lng;

	private Double funcAreaWidth;

	private Double funcAreaHeight;

	private String remark;
	
	private String active;
	
	private String empNo;

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getFuncAreaName() {
		return funcAreaName;
	}

	public void setFuncAreaName(String funcAreaName) {
		this.funcAreaName = funcAreaName;
	}

	public String getFuncAreaNo() {
		return funcAreaNo;
	}

	public void setFuncAreaNo(String funcAreaNo) {
		this.funcAreaNo = funcAreaNo;
	}
 
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getFuncAreaWidth() {
		return funcAreaWidth;
	}

	public void setFuncAreaWidth(Double funcAreaWidth) {
		this.funcAreaWidth = funcAreaWidth;
	}

	public Double getFuncAreaHeight() {
		return funcAreaHeight;
	}

	public void setFuncAreaHeight(Double funcAreaHeight) {
		this.funcAreaHeight = funcAreaHeight;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	
	
}
