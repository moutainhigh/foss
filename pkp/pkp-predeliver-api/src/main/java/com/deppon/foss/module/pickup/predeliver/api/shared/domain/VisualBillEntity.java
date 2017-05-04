package com.deppon.foss.module.pickup.predeliver.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 可视化排单结果DTO
 * 
 * @author 239284
 * 
 */
public class VisualBillEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	// 1-运单号
	private String waybillNo;
	// 货物总件数
	private Integer goodsQtyTotal;
	// 货物总重量
	private BigDecimal goodsWeight;
	// 货物总体积
	private BigDecimal goodsVolume;
	// 货物尺寸
	private String goodsSize;
	// 货物包装
	private String goodsPackage;

	// 2-运单坐标
	private String longitude; // 经度
	private String latitude; // 纬度

	// 3-坐标展示信息-送货地址
	private String receiveCustomerProvCode; // 送货地址-省代码
	private String receiveCustomerCityCode; // 送货地址-市代码
	private String receiveCustomerDistCode; // 送货地址-区域代码
	private String receiveCustomerAddress; // 送货地址-街道
	private String receiveCustomerAddressNote; // 送货地址-详细地址
	// 3-坐标展示信息-预计送货日期/时间
	private String preDeliverDate; // 预计送货日期
	private String deliveryTimeInterval;// 送货时间(上、下、全天)
	private String deliveryTimeStart; // 时间开始
	private String deliveryTimeOver; // 时间结束
	// 3-坐标展示信息-特殊运单类型
//	private String specialNoType; // 特殊货物的运单 --根据特定情况判断
	private String specialAddressType; // 特殊地址类型
	// 3-坐标展示信息-理货员退出原因
	private String tallymanReturnReason; // 理货员退回原因
	// 3-坐标展示信息-是否晚交单
	private String lateNo; // 晚交运单
	private String isExhibition; //是否会展货
	private String uitraLongDelivery; //是否超远派送
	private String receiveMethod; //提货方式
	
	private String actualSmallzoneCode; //实际送货小区
	private String productCode; //运输性质
	private BigDecimal toPayAmount; //到付金额
	/**
	 * 空车出
	 */
	private String isEmptyCar;
	
	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
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

	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
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

	public String getGoodsSize() {
		return goodsSize;
	}

	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}

	public String getGoodsPackage() {
		return goodsPackage;
	}

	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	public String getSpecialAddressType() {
		return specialAddressType;
	}

	public void setSpecialAddressType(String specialAddressType) {
		this.specialAddressType = specialAddressType;
	}

	public String getTallymanReturnReason() {
		return tallymanReturnReason;
	}

	public void setTallymanReturnReason(String tallymanReturnReason) {
		this.tallymanReturnReason = tallymanReturnReason;
	}

	public String getLateNo() {
		return lateNo;
	}

	public void setLateNo(String lateNo) {
		this.lateNo = lateNo;
	}

	public String getReceiveCustomerProvCode() {
		return receiveCustomerProvCode;
	}

	public void setReceiveCustomerProvCode(String receiveCustomerProvCode) {
		this.receiveCustomerProvCode = receiveCustomerProvCode;
	}

	public String getReceiveCustomerCityCode() {
		return receiveCustomerCityCode;
	}

	public void setReceiveCustomerCityCode(String receiveCustomerCityCode) {
		this.receiveCustomerCityCode = receiveCustomerCityCode;
	}

	public String getReceiveCustomerDistCode() {
		return receiveCustomerDistCode;
	}

	public void setReceiveCustomerDistCode(String receiveCustomerDistCode) {
		this.receiveCustomerDistCode = receiveCustomerDistCode;
	}

	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	public String getReceiveCustomerAddressNote() {
		return receiveCustomerAddressNote;
	}

	public void setReceiveCustomerAddressNote(String receiveCustomerAddressNote) {
		this.receiveCustomerAddressNote = receiveCustomerAddressNote;
	}

	public String getPreDeliverDate() {
		return preDeliverDate;
	}

	public void setPreDeliverDate(String preDeliverDate) {
		this.preDeliverDate = preDeliverDate;
	}

	public String getActualSmallzoneCode() {
		return actualSmallzoneCode;
	}

	public void setActualSmallzoneCode(String actualSmallzoneCode) {
		this.actualSmallzoneCode = actualSmallzoneCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	public String getDeliveryTimeInterval() {
		return deliveryTimeInterval;
	}

	public void setDeliveryTimeInterval(String deliveryTimeInterval) {
		this.deliveryTimeInterval = deliveryTimeInterval;
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

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public String getIsEmptyCar() {
		return isEmptyCar;
	}

	public void setIsEmptyCar(String isEmptyCar) {
		this.isEmptyCar = isEmptyCar;
	}
	
}
