package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 * 可视化自动排序节点Dto
 */
@SuppressWarnings("serial")
public class VisibleAutoSortNodeDto implements Serializable{

	private String calculateType;	//计算方式
	private VisibleAutoSortNodeInfoDto vehicleRealityOut;	//实际出车地
	private VisibleAutoSortNodeInfoDto startDeptEntity;	//排序开始地
	private List<VisibleAutoSortNodeInfoDto> waybillList = new ArrayList<VisibleAutoSortNodeInfoDto>();	//正常运单
	private List<VisibleAutoSortNodeInfoDto> waybillSpeciallList = new ArrayList<VisibleAutoSortNodeInfoDto>();	//空车出特殊运单
	private VisibleAutoSortNodeInfoDto endDeptEntity;	//排序结束地
	private VisibleAutoSortNodeInfoDto vehicleRealityOver;	//实际停车地
	
	public String getCalculateType() {
		return calculateType;
	}
	public void setCalculateType(String calculateType) {
		this.calculateType = calculateType;
	}
	public VisibleAutoSortNodeInfoDto getVehicleRealityOut() {
		return vehicleRealityOut;
	}
	public void setVehicleRealityOut(VisibleAutoSortNodeInfoDto vehicleRealityOut) {
		this.vehicleRealityOut = vehicleRealityOut;
	}
	public VisibleAutoSortNodeInfoDto getStartDeptEntity() {
		return startDeptEntity;
	}
	public void setStartDeptEntity(VisibleAutoSortNodeInfoDto startDeptEntity) {
		this.startDeptEntity = startDeptEntity;
	}
	public List<VisibleAutoSortNodeInfoDto> getWaybillList() {
		return waybillList;
	}
	public void setWaybillList(List<VisibleAutoSortNodeInfoDto> waybillList) {
		this.waybillList = waybillList;
	}
	public List<VisibleAutoSortNodeInfoDto> getWaybillSpeciallList() {
		return waybillSpeciallList;
	}
	public void setWaybillSpeciallList(
			List<VisibleAutoSortNodeInfoDto> waybillSpeciallList) {
		this.waybillSpeciallList = waybillSpeciallList;
	}
	public VisibleAutoSortNodeInfoDto getEndDeptEntity() {
		return endDeptEntity;
	}
	public void setEndDeptEntity(VisibleAutoSortNodeInfoDto endDeptEntity) {
		this.endDeptEntity = endDeptEntity;
	}
	public VisibleAutoSortNodeInfoDto getVehicleRealityOver() {
		return vehicleRealityOver;
	}
	public void setVehicleRealityOver(VisibleAutoSortNodeInfoDto vehicleRealityOver) {
		this.vehicleRealityOver = vehicleRealityOver;
	}
	
	
}
