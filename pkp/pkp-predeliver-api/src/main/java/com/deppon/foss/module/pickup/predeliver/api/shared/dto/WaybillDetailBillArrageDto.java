package com.deppon.foss.module.pickup.predeliver.api.shared.dto;


import java.util.Date;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillDetailEntity;

/**
 * 已排单DTO
 * 
 * @author 239284
 * 
 */
@SuppressWarnings("serial")
public class WaybillDetailBillArrageDto extends DeliverbillDetailEntity {

	private String deliveryTimeInterval; //送货时间段
	private String deliveryTimeStart; // 送货时间范围-开始
	private String deliveryTimeOver;// 送货时间范围-结束
	private String togetherSendCode; // 合送编码
	
	private String regionName; //送货小区
	private String vehicleNo; //车牌号
	private String isVehicleScheduling; //车辆是否排班
	
	private String lastLoadOrgCode;//自动排序-最终配载部门
	private String longitude; //自动排序-经度
	private String latitude; //自动排序-纬度
	private Date cashTime;//规定兑现时间
	
	
	 /**
     * 实际送货小区CODE(人工在创建派送单(新)预处理建议里修改)
     */
    private String actualSmallzoneCode;
    /**
     * 实际送货小区名称(人工在创建派送单(新)预处理建议里修改)
     */
    private String actualSmallzoneName;
	
	public String getDeliveryTimeInterval() {
		return deliveryTimeInterval;
	}

	public void setDeliveryTimeInterval(String deliveryTimeInterval) {
		this.deliveryTimeInterval = deliveryTimeInterval;
	}

	public String getDeliveryTimeStart() {
		return deliveryTimeStart;
	}

	public void setDeliveryTimeStart(String deliveryTimeStart) {
		this.deliveryTimeStart = deliveryTimeStart;
	}

	public String getDeliveryTimeOver() {
		return deliveryTimeOver;
	}

	public void setDeliveryTimeOver(String deliveryTimeOver) {
		this.deliveryTimeOver = deliveryTimeOver;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	public String getIsVehicleScheduling() {
		return isVehicleScheduling;
	}

	public void setIsVehicleScheduling(String isVehicleScheduling) {
		this.isVehicleScheduling = isVehicleScheduling;
	}
	
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getTogetherSendCode() {
		return togetherSendCode;
	}

	public void setTogetherSendCode(String togetherSendCode) {
		this.togetherSendCode = togetherSendCode;
	}

	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	
	/**
	 * 获取actualSmallzoneCode
	 * @return the actualSmallzoneCode
	 */
	public String getActualSmallzoneCode() {
		return actualSmallzoneCode;
	}

	/**
	 * 设置actualSmallzoneCode
	 * @param actualSmallzoneCode 要设置的actualSmallzoneCode
	 */
	public void setActualSmallzoneCode(String actualSmallzoneCode) {
		this.actualSmallzoneCode = actualSmallzoneCode;
	}

	/**
	 * 获取actualSmallzoneName
	 * @return the actualSmallzoneName
	 */
	public String getActualSmallzoneName() {
		return actualSmallzoneName;
	}

	/**
	 * 设置actualSmallzoneName
	 * @param actualSmallzoneName 要设置的actualSmallzoneName
	 */
	public void setActualSmallzoneName(String actualSmallzoneName) {
		this.actualSmallzoneName = actualSmallzoneName;
	}

	public Date getCashTime() {
		return cashTime;
	}

	public void setCashTime(Date cashTime) {
		this.cashTime = cashTime;
	}

}
