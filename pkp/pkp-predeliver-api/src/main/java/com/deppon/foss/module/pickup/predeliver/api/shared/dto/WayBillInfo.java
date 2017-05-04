package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

/**
 * GIS接口交互的运单信息
 * @author 239284
 *
 */
public class WayBillInfo {

	//参数1-运单号
	private String waybillNo;
	
	 //参数2-运单坐标
	private String positionLng;//经度
	private String positionLat;  //纬度
	
	private String address; //送货地址
	private String qtyWeightVolum;//交单件数/重量/体积:
	private String goodsSize;//尺寸: 
	private String goodPackage; //, "包装: " 
	private String deliveryTime ;//预计送货日期/时间: 
	private String goodStatusLate; //货物状态/是否晚交单: "
	private String specialBillType ;//特殊运单类型: " 
	private String returnReason ;//理货员退回原因: 
	
	//参数4-运单状态
	private String wayBillStatus ; //特殊运单标识1， 其他标识0
	//重量-地图求和
	private String goodsWeight; 
	//体积-地图求和
	private String goodsVolume;
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getPositionLng() {
		return positionLng;
	}
	public void setPositionLng(String positionLng) {
		this.positionLng = positionLng;
	}
	public String getPositionLat() {
		return positionLat;
	}
	public void setPositionLat(String positionLat) {
		this.positionLat = positionLat;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getQtyWeightVolum() {
		return qtyWeightVolum;
	}
	public void setQtyWeightVolum(String qtyWeightVolum) {
		this.qtyWeightVolum = qtyWeightVolum;
	}
	public String getGoodsSize() {
		return goodsSize;
	}
	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}
	public String getGoodPackage() {
		return goodPackage;
	}
	public void setGoodPackage(String goodPackage) {
		this.goodPackage = goodPackage;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getGoodStatusLate() {
		return goodStatusLate;
	}
	public void setGoodStatusLate(String goodStatusLate) {
		this.goodStatusLate = goodStatusLate;
	}
	public String getSpecialBillType() {
		return specialBillType;
	}
	public void setSpecialBillType(String specialBillType) {
		this.specialBillType = specialBillType;
	}
	public String getReturnReason() {
		return returnReason;
	}
	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}
	public String getWayBillStatus() {
		return wayBillStatus;
	}
	public void setWayBillStatus(String wayBillStatus) {
		this.wayBillStatus = wayBillStatus;
	}
	public String getGoodsWeight() {
		return goodsWeight;
	}
	public void setGoodsWeight(String goodsWeight) {
		this.goodsWeight = goodsWeight;
	}
	public String getGoodsVolume() {
		return goodsVolume;
	}
	public void setGoodsVolume(String goodsVolume) {
		this.goodsVolume = goodsVolume;
	}
	
}
