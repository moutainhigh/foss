package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.util.List;

public class CenterAndDeliveryBigRegionAreaDto {
	//大区名称		
	private String bigRegionName;
	//大区编码
	private String bigRegionCode;
	//车牌号
	private String bigVehicleNo;
	//车队
	private String bigVehicleDept;
	//大区的虚拟编码
	private String virtualCode;
    //小区
	private List<CenterAndDeliverySamlleRegionAreaDto> andDeliverySmalleRegionAreaDtos; 
    //车牌号
	private List<String>  bigVehicleNos;
	
    public String getVirtualCode() {
		return virtualCode;
	}

	public void setVirtualCode(String virtualCode) {
		this.virtualCode = virtualCode;
	}

	public String getBigVehicleDept() {
		return bigVehicleDept;
	}

	public void setBigVehicleDept(String bigVehicleDept) {
		this.bigVehicleDept = bigVehicleDept;
	}

	public String getBigRegionName() {
		return bigRegionName;
	}

	public void setBigRegionName(String bigRegionName) {
		this.bigRegionName = bigRegionName;
	}

	 

	public String getBigVehicleNo() {
		return bigVehicleNo;
	}

	public void setBigVehicleNo(String bigVehicleNo) {
		this.bigVehicleNo = bigVehicleNo;
	}

	public String getBigRegionCode() {
		return bigRegionCode;
	}

	public void setBigRegionCode(String bigRegionCode) {
		this.bigRegionCode = bigRegionCode;
	}

	public List<CenterAndDeliverySamlleRegionAreaDto> getAndDeliverySmalleRegionAreaDtos() {
		return andDeliverySmalleRegionAreaDtos;
	}

	public void setAndDeliverySmalleRegionAreaDtos(
			List<CenterAndDeliverySamlleRegionAreaDto> andDeliverySmalleRegionAreaDtos) {
		this.andDeliverySmalleRegionAreaDtos = andDeliverySmalleRegionAreaDtos;
	}

	public List<String> getBigVehicleNos() {
		return bigVehicleNos;
	}

	public void setBigVehicleNos(List<String> bigVehicleNos) {
		this.bigVehicleNos = bigVehicleNos;
	}
		
}
