package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.VisualBillEntity;

/**
 * 可视化排单结果DTO
 * 
 * @author 239284
 * 
 */
public class VisualBillDto extends VisualBillEntity {

	private static final long serialVersionUID = 3080363125691115580L;
	
	private String actualProvN; // 送货地址-省
	private String actualCityN; // 送货地址-市
	private String actualDistrictN; // 送货地址-区域
	
	//给GIS展示-图层小区
	private String actualSmallzoneCode;
	//给GIS展示-图层车辆
	private String actualVehicleNo;

	// 其他聚合信息
	private String provCityN; // 聚合后-省市名称
	private Integer totalCount; // 聚合后-运单总条数

	// 3-坐标展示信息-货物状态
	private String goodStatus; // 货物状态
	
	//可排单件数
	private String arrangeToQty;
	
	public String getActualProvN() {
		return actualProvN;
	}

	public void setActualProvN(String actualProvN) {
		this.actualProvN = actualProvN;
	}

	public String getActualCityN() {
		return actualCityN;
	}

	public void setActualCityN(String actualCityN) {
		this.actualCityN = actualCityN;
	}

	public String getActualDistrictN() {
		return actualDistrictN;
	}

	public void setActualDistrictN(String actualDistrictN) {
		this.actualDistrictN = actualDistrictN;
	}

	public String getProvCityN() {
		return provCityN;
	}

	public void setProvCityN(String provCityN) {
		this.provCityN = provCityN;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public String getGoodStatus() {
		return goodStatus;
	}

	public void setGoodStatus(String goodStatus) {
		this.goodStatus = goodStatus;
	}

	public String getArrangeToQty() {
		return arrangeToQty;
	}

	public void setArrangeToQty(String arrangeToQty) {
		this.arrangeToQty = arrangeToQty;
	}

	public String getActualSmallzoneCode() {
		return actualSmallzoneCode;
	}

	public void setActualSmallzoneCode(String actualSmallzoneCode) {
		this.actualSmallzoneCode = actualSmallzoneCode;
	}

	public String getActualVehicleNo() {
		return actualVehicleNo;
	}

	public void setActualVehicleNo(String actualVehicleNo) {
		this.actualVehicleNo = actualVehicleNo;
	}
	
}
