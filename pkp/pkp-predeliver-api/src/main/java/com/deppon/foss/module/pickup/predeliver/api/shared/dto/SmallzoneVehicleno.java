package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

/**
 *  GIS接口省市区-> 小区图层list
 * @author 239284
 *
 */
public class SmallzoneVehicleno {

	//小区id
	private String smallzoneId;
	//小区对应的车牌号
	private String vehicleNo;
	
	public String getSmallzoneId() {
		return smallzoneId;
	}
	public void setSmallzoneId(String smallzoneId) {
		this.smallzoneId = smallzoneId;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
}
