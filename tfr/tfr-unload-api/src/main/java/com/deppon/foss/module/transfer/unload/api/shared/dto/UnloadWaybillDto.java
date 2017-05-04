package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.List;

public class UnloadWaybillDto implements Serializable {

	private static final long serialVersionUID = -1943290505355797915L;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 是否贵重物品
	 */
	private String isValue;

	/**
	 * 收货部门编码
	 */
	private String receiveOrgCode;

	/**
	 * 收货部门名称
	 */
	private String receiveOrgName;

	/**
	 * 提货网点编码
	 */
	private String customerPickupOrgCode;

	/**
	 * 提货网点名称
	 */
	private String customerPickupOrgName;

	/**
	 * 重量
	 */
	private double weight;

	/**
	 * 体积
	 */
	private double volume;

	/**
	 * 件数
	 */
	private int waybillQty;

	/**
	 * 品名
	 */
	private String goodsName;

	/**
	 * 产品名称
	 */
	private String productName;

	/**
	 * 包装
	 */
	private String packing;

	/**
	 * 提货网点对应的接货网店编码
	 */
	private String stationNumber;

	/**
	 * 是否违禁品
	 */
	private String beContraband;

	/**
	 * pda上显示的一个行政区域
	 */
	private String adminiRegion;

	/**
	 * 是否转寄退回
	 */
	private String isHandle;

	/**
	 * 是否电子面单
	 */
	private String beEWaybill;

	/**
	 * 是否7天返货
	 */
	private String isSevenDaysReturn;

	/**
	 * 流水号集合
	 */
	private List<UnloadSerialDto> serialList;

	/**
	 * 交接单号(冗余)
	 */
	private String handoverBillNo;

	/**
	 * 卸车运单明细id(冗余)
	 */
	private String unloadWaybillDetailId;

	/**
	 * 收货客户地址-区编码(冗余)
	 */
	private String receiveCustomerDistCode;

	/**
	 * 是否快递(冗余)
	 */
	private String isExpress;

	/**
	 * 提货方式(冗余)
	 */
	private String receiveMethod;

	/**
	 * 产品编码(冗余)
	 */
	private String productCode;

	public String getHandoverBillNo() {
		return handoverBillNo;
	}

	public void setHandoverBillNo(String handoverBillNo) {
		this.handoverBillNo = handoverBillNo;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getIsValue() {
		return isValue;
	}

	public void setIsValue(String isValue) {
		this.isValue = isValue;
	}

	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	public String getCustomerPickupOrgName() {
		return customerPickupOrgName;
	}

	public void setCustomerPickupOrgName(String customerPickupOrgName) {
		this.customerPickupOrgName = customerPickupOrgName;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public int getWaybillQty() {
		return waybillQty;
	}

	public void setWaybillQty(int waybillQty) {
		this.waybillQty = waybillQty;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPacking() {
		return packing;
	}

	public void setPacking(String packing) {
		this.packing = packing;
	}

	public String getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(String stationNumber) {
		this.stationNumber = stationNumber;
	}

	public String getBeContraband() {
		return beContraband;
	}

	public void setBeContraband(String beContraband) {
		this.beContraband = beContraband;
	}

	public List<UnloadSerialDto> getSerialList() {
		return serialList;
	}

	public void setSerialList(List<UnloadSerialDto> serialList) {
		this.serialList = serialList;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public String getAdminiRegion() {
		return adminiRegion;
	}

	public void setAdminiRegion(String adminiRegion) {
		this.adminiRegion = adminiRegion;
	}

	public String getReceiveCustomerDistCode() {
		return receiveCustomerDistCode;
	}

	public void setReceiveCustomerDistCode(String receiveCustomerDistCode) {
		this.receiveCustomerDistCode = receiveCustomerDistCode;
	}

	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getIsHandle() {
		return isHandle;
	}

	public void setIsHandle(String isHandle) {
		this.isHandle = isHandle;
	}

	public String getBeEWaybill() {
		return beEWaybill;
	}

	public void setBeEWaybill(String beEWaybill) {
		this.beEWaybill = beEWaybill;
	}

	public String getIsSevenDaysReturn() {
		return isSevenDaysReturn;
	}

	public void setIsSevenDaysReturn(String isSevenDaysReturn) {
		this.isSevenDaysReturn = isSevenDaysReturn;
	}

	public String getUnloadWaybillDetailId() {
		return unloadWaybillDetailId;
	}

	public void setUnloadWaybillDetailId(String unloadWaybillDetailId) {
		this.unloadWaybillDetailId = unloadWaybillDetailId;
	}

	@Override
	public String toString() {
		return "UnloadWaybillDto [waybillNo=" + waybillNo + ", isValue=" + isValue + ", receiveOrgCode="
				+ receiveOrgCode + ", receiveOrgName=" + receiveOrgName + ", customerPickupOrgCode="
				+ customerPickupOrgCode + ", customerPickupOrgName=" + customerPickupOrgName + ", weight=" + weight
				+ ", volume=" + volume + ", waybillQty=" + waybillQty + ", goodsName=" + goodsName + ", productName="
				+ productName + ", packing=" + packing + ", stationNumber=" + stationNumber + ", beContraband="
				+ beContraband + ", adminiRegion=" + adminiRegion + ", isHandle=" + isHandle + ", beEWaybill="
				+ beEWaybill + ", isSevenDaysReturn=" + isSevenDaysReturn + ", serialList=" + serialList
				+ ", handoverBillNo=" + handoverBillNo + ", unloadWaybillDetailId=" + unloadWaybillDetailId
				+ ", receiveCustomerDistCode=" + receiveCustomerDistCode + ", isExpress=" + isExpress
				+ ", receiveMethod=" + receiveMethod + ", productCode=" + productCode + "]";
	}

}
