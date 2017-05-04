package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/***
 * 可视化自动排序Dto(页面)
 */
@SuppressWarnings("serial")
public class VisibleAutoSortDto implements Serializable{
	
	private String id;	//id
	private String waybillNo;	//运单号
	private String receiveCustomerAddress;	//送货地址
	private String deliveryTimeInterval; //送货时间段
	private String recommendedDeliveryTime; //建议送货时间
	private String deliveryTimeStart;	//开始时间
	private String deliveryTimeOver;	//结束时间
	private String specialAddressType;	//特殊地址类型
	private String goodStatus;	//特殊货物类型
	private BigDecimal arrangeGoodsQty;	//件数
	private BigDecimal goodsWeight;	//重量
	private BigDecimal goodsVolume;	//体积
	private String isExhibition;	//是否会展货
	private String uitraLongDelivery;	//是否超远派送
	private BigDecimal toPayAmount; //到付金额
	private String receiveMethod; //提货方式
	private String goodsSize;	// 货物尺寸
	private String goodsPackage;	// 货物包装
	private Integer serialNo;	//序号
	private Date deliverDate; //预计送货时间
	private String longitude; //运单坐标经度
	private String latitude; //运单坐标纬度
	private String actualSmallzoneCode;	//实际送货小区code
	private String actualSmallzoneName;	//实际送货小区name
	private BigDecimal goodsQtyTotal;//开单件数
	private String productCode;	//运输性质
	private String isEmptyCar;	//是否空车出
	private Date cashTime ;//规定兑现时间
	
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
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}
	public String getDeliveryTimeInterval() {
		return deliveryTimeInterval;
	}
	public void setDeliveryTimeInterval(String deliveryTimeInterval) {
		this.deliveryTimeInterval = deliveryTimeInterval;
	}
	public String getSpecialAddressType() {
		return specialAddressType;
	}
	public void setSpecialAddressType(String specialAddressType) {
		this.specialAddressType = specialAddressType;
	}
	public String getGoodStatus() {
		return goodStatus;
	}
	public void setGoodStatus(String goodStatus) {
		this.goodStatus = goodStatus;
	}
	public BigDecimal getGoodsWeight() {
		return goodsWeight;
	}
	public void setGoodsWeight(BigDecimal goodsWeight) {
		this.goodsWeight = goodsWeight;
	}
	public BigDecimal getGoodsVolume() {
		return goodsVolume;
	}
	public void setGoodsVolume(BigDecimal goodsVolume) {
		this.goodsVolume = goodsVolume;
	}
	public String getIsExhibition() {
		return isExhibition;
	}
	public void setIsExhibition(String isExhibition) {
		this.isExhibition = isExhibition;
	}
	public String getUitraLongDelivery() {
		return uitraLongDelivery;
	}
	public void setUitraLongDelivery(String uitraLongDelivery) {
		this.uitraLongDelivery = uitraLongDelivery;
	}
	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}
	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}
	public String getReceiveMethod() {
		return receiveMethod;
	}
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}
	
	public String getGoodsPackage() {
		return goodsPackage;
	}
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
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
	/*public String getDeliverbillNo() {
		return deliverbillNo;
	}
	public void setDeliverbillNo(String deliverbillNo) {
		this.deliverbillNo = deliverbillNo;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getFrequencyNo() {
		return frequencyNo;
	}
	public void setFrequencyNo(String frequencyNo) {
		this.frequencyNo = frequencyNo;
	}
	public String getCarTaskInfo() {
		return carTaskInfo;
	}
	public void setCarTaskInfo(String carTaskInfo) {
		this.carTaskInfo = carTaskInfo;
	}
	public String getTakeGoodsDeptName() {
		return takeGoodsDeptName;
	}
	public void setTakeGoodsDeptName(String takeGoodsDeptName) {
		this.takeGoodsDeptName = takeGoodsDeptName;
	}
	public BigDecimal getExpectedbringVolume() {
		return expectedbringVolume;
	}
	public void setExpectedbringVolume(BigDecimal expectedbringVolume) {
		this.expectedbringVolume = expectedbringVolume;
	}
	public String getPreCarTaskTime() {
		return preCarTaskTime;
	}
	public void setPreCarTaskTime(String preCarTaskTime) {
		this.preCarTaskTime = preCarTaskTime;
	}
	public String getDeliverbillId() {
		return deliverbillId;
	}
	public void setDeliverbillId(String deliverbillId) {
		this.deliverbillId = deliverbillId;
	}
	public String getTakeGoodsDeptCode() {
		return takeGoodsDeptCode;
	}
	public void setTakeGoodsDeptCode(String takeGoodsDeptCode) {
		this.takeGoodsDeptCode = takeGoodsDeptCode;
	}
	public String getTransferDeptCode() {
		return transferDeptCode;
	}
	public void setTransferDeptCode(String transferDeptCode) {
		this.transferDeptCode = transferDeptCode;
	}
	public String getTransferDeptName() {
		return transferDeptName;
	}
	public void setTransferDeptName(String transferDeptName) {
		this.transferDeptName = transferDeptName;
	}*/
	public String getRecommendedDeliveryTime() {
		return recommendedDeliveryTime;
	}
	public void setRecommendedDeliveryTime(String recommendedDeliveryTime) {
		this.recommendedDeliveryTime = recommendedDeliveryTime;
	}
	public Integer getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}
	
	public BigDecimal getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	public void setGoodsQtyTotal(BigDecimal goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	public String getActualSmallzoneCode() {
		return actualSmallzoneCode;
	}
	public void setActualSmallzoneCode(String actualSmallzoneCode) {
		this.actualSmallzoneCode = actualSmallzoneCode;
	}
	public String getActualSmallzoneName() {
		return actualSmallzoneName;
	}
	public void setActualSmallzoneName(String actualSmallzoneName) {
		this.actualSmallzoneName = actualSmallzoneName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getIsEmptyCar() {
		return isEmptyCar;
	}
	public void setIsEmptyCar(String isEmptyCar) {
		this.isEmptyCar = isEmptyCar;
	}
	public BigDecimal getArrangeGoodsQty() {
		return arrangeGoodsQty;
	}
	public void setArrangeGoodsQty(BigDecimal arrangeGoodsQty) {
		this.arrangeGoodsQty = arrangeGoodsQty;
	}
	public Date getDeliverDate() {
		return deliverDate;
	}
	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}
/*	public double getNextNodeDistance() {
		return nextNodeDistance;
	}
	public void setNextNodeDistance(double nextNodeDistance) {
		this.nextNodeDistance = nextNodeDistance;
	}
*/
	public String getGoodsSize() {
		return goodsSize;
	}
	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}
	public Date getCashTime() {
		return cashTime;
	}
	public void setCashTime(Date cashTime) {
		this.cashTime = cashTime;
	}
	
}
