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
public class VisibleAutoSortReturnDto implements Serializable{

	private String responseCode;	//请求是否成功
	private String exceptionMessage; //异常信息
	private double totalDistance;	//总距离
	private VisibleAutoSortNodeReturnInfoDto vehicleRealityOut;	//实际出车地
	private VisibleAutoSortNodeReturnInfoDto startDeptEntity;	//开始
	private VisibleAutoSortNodeReturnInfoDto endDeptEntity;	//结束
	private List<VisibleAutoSortNodeReturnInfoDto> waybillList = new ArrayList<VisibleAutoSortNodeReturnInfoDto>();	//运单
	
	public double getTotalDistance() {
		return totalDistance;
	}
	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}

	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	public List<VisibleAutoSortNodeReturnInfoDto> getWaybillList() {
		return waybillList;
	}
	public void setWaybillList(List<VisibleAutoSortNodeReturnInfoDto> waybillList) {
		this.waybillList = waybillList;
	}
	public VisibleAutoSortNodeReturnInfoDto getVehicleRealityOut() {
		return vehicleRealityOut;
	}
	public void setVehicleRealityOut(
			VisibleAutoSortNodeReturnInfoDto vehicleRealityOut) {
		this.vehicleRealityOut = vehicleRealityOut;
	}
	public VisibleAutoSortNodeReturnInfoDto getStartDeptEntity() {
		return startDeptEntity;
	}
	public void setStartDeptEntity(VisibleAutoSortNodeReturnInfoDto startDeptEntity) {
		this.startDeptEntity = startDeptEntity;
	}
	public VisibleAutoSortNodeReturnInfoDto getEndDeptEntity() {
		return endDeptEntity;
	}
	public void setEndDeptEntity(VisibleAutoSortNodeReturnInfoDto endDeptEntity) {
		this.endDeptEntity = endDeptEntity;
	}
	
	
}
