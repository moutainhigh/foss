package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/***
 * 可视化自动排序Dto(页面地图)
 */
@SuppressWarnings("serial")
public class VisiblePageMapAutoSortDto implements Serializable{
	
	private String id;	//id
	
	/**
	 * 部门详情
	 */
	private String deptCode;	//部门code
	private String deptName;	//部门name
	private String deptLat;	//纬度
	private String deptLng;//经度
	
	/**
	 * 运单详情
	 */
	private String waybillNo;	//运单号
	private String deliveryAddress;	//送货地址
	private String canData;		//排单信息（件数/重量/体积）
	private String waybillPacking;	//包装
	private String deliveryData;	//送货时间（送货时间段/送货时间点）
	private String waybillTagType;	//是否特殊地址
	private String goodStatus;	//特殊状态
	private Integer serialNo;	//序号
	private String waybillLat;	//纬度
	private String waybillLng;	//经度
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public String getCanData() {
		return canData;
	}
	public void setCanData(String canData) {
		this.canData = canData;
	}
	public String getWaybillPacking() {
		return waybillPacking;
	}
	public void setWaybillPacking(String waybillPacking) {
		this.waybillPacking = waybillPacking;
	}
	public String getDeliveryData() {
		return deliveryData;
	}
	public void setDeliveryData(String deliveryData) {
		this.deliveryData = deliveryData;
	}
	public String getWaybillTagType() {
		return waybillTagType;
	}
	public void setWaybillTagType(String waybillTagType) {
		this.waybillTagType = waybillTagType;
	}
	public String getGoodStatus() {
		return goodStatus;
	}
	public void setGoodStatus(String goodStatus) {
		this.goodStatus = goodStatus;
	}
	public Integer getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}
	public String getWaybillLat() {
		return waybillLat;
	}
	public void setWaybillLat(String waybillLat) {
		this.waybillLat = waybillLat;
	}
	public String getWaybillLng() {
		return waybillLng;
	}
	public void setWaybillLng(String waybillLng) {
		this.waybillLng = waybillLng;
	}
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
	public String getDeptLat() {
		return deptLat;
	}
	public void setDeptLat(String deptLat) {
		this.deptLat = deptLat;
	}
	public String getDeptLng() {
		return deptLng;
	}
	public void setDeptLng(String deptLng) {
		this.deptLng = deptLng;
	}
	
	
}
