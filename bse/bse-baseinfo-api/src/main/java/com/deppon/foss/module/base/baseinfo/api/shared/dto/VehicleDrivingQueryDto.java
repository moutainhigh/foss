package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import java.util.List;

public class VehicleDrivingQueryDto implements Serializable{

	private static final long serialVersionUID = 3300713068429543858L;

	/**
	 * 长途车队名称
	 */
	private String longHaulFleetName; 
	
	/**
	 * 长途车队编码
	 */
	private String longHaulFleetCode;
	
	/**
	 * 已选车队ID集合
	 */
	private List<String> selectedIdList;
	
	private String selectedIds;
	private String active;
	public String getLongHaulFleetName() {
		return longHaulFleetName;
	}
	public void setLongHaulFleetName(String longHaulFleetName) {
		this.longHaulFleetName = longHaulFleetName;
	}
	public String getLongHaulFleetCode() {
		return longHaulFleetCode;
	}
	public void setLongHaulFleetCode(String longHaulFleetCode) {
		this.longHaulFleetCode = longHaulFleetCode;
	}
	public List<String> getSelectedIdList() {
		return selectedIdList;
	}
	public void setSelectedIdList(List<String> selectedIdList) {
		this.selectedIdList = selectedIdList;
	}
	public String getSelectedIds() {
		return selectedIds;
	}
	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
	

	
}
