package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * GIS接口省市区->运单List
 */
public class TogetherInfo {

	//聚合-省市
	public String togetherProvCity;
	//聚合-省市下的总票数
	public String togetherCount;
	//区下图层
	public List<SmallzoneVehicleno> smallVehicleNoList = new ArrayList<SmallzoneVehicleno>();
	//运单信息明细
	public List<WayBillInfo> waybillInfoList = new ArrayList<WayBillInfo>();
	
	public String getTogetherProvCity() {
		return togetherProvCity;
	}
	public void setTogetherProvCity(String togetherProvCity) {
		this.togetherProvCity = togetherProvCity;
	}
	public String getTogetherCount() {
		return togetherCount;
	}
	public void setTogetherCount(String togetherCount) {
		this.togetherCount = togetherCount;
	}
	public List<WayBillInfo> getWaybillInfoList() {
		return waybillInfoList;
	}
	public void setWaybillInfoList(List<WayBillInfo> waybillInfoList) {
		this.waybillInfoList = waybillInfoList;
	}
	public List<SmallzoneVehicleno> getSmallVehicleNoList() {
		return smallVehicleNoList;
	}
	public void setSmallVehicleNoList(List<SmallzoneVehicleno> smallVehicleNoList) {
		this.smallVehicleNoList = smallVehicleNoList;
	}
	
}
