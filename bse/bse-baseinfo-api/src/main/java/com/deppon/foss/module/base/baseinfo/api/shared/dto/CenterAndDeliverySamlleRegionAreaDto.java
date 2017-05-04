package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.util.List;

public class CenterAndDeliverySamlleRegionAreaDto {
//	小区编码
	private String smallRegionCode;
//	小区名称	   
	private String smallRegionName;
//	GIS关联ID
	private String gisId;
	//大区虚拟编码
    private String bigZoneCode;
    //车牌号
	private List<String>  smallVehicleNos;

	public String getSmallRegionCode() {
		return smallRegionCode;
	}

	public void setSmallRegionCode(String smallRegionCode) {
		this.smallRegionCode = smallRegionCode;
	}

	public String getSmallRegionName() {
		return smallRegionName;
	}

	public void setSmallRegionName(String smallRegionName) {
		this.smallRegionName = smallRegionName;
	}


	public List<String> getSmallVehicleNos() {
		return smallVehicleNos;
	}

	public void setSmallVehicleNos(List<String> smallVehicleNos) {
		this.smallVehicleNos = smallVehicleNos;
	}

	public String getGisId() {
		return gisId;
	}

	public void setGisId(String gisId) {
		this.gisId = gisId;
	}

	public String getBigZoneCode() {
		return bigZoneCode;
	}

	public void setBigZoneCode(String bigZoneCode) {
		this.bigZoneCode = bigZoneCode;
	}

	 


}
