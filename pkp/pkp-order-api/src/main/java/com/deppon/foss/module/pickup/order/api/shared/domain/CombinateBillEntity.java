package com.deppon.foss.module.pickup.order.api.shared.domain;

import java.util.Date;

public class CombinateBillEntity {
	 private String id;
	 //发货客户名称
	 private String deliveryCustomerName;
	 //客户标签编号
	 private String customerLableNums;
	 //运单号
	 private String waybillNo;
	 //ERP单号
	 private String erpOrderNo;
	 //流水号
	 private String serialNo;
	 //订单状态
	 private String active;
	 //创建时间
	 private Date createTime;
	 //开单时间
	 private Date billTime;
	 //开单人
	 private String createUserCode;
	 //开单组织
	 private String createOrgCode;
	 //始发城市名称
	 private String departmentCityName;
	 //收货地址
	 private String receiveCustomerAddress;
	 /**
	  * 到达门店编码
	 */
	 private String arriveStoreNUM;
	 //德邦收货门部门编码
	 private String customerPickupOrgCode;
	 //收货人
	 private String receiveCustomerContact;
	 //提货方式
	 private String receiveMethod;
	 //运输性质
	 private String productCode;
	 //运输性质名称
	 private String productName;
	 //包装类型
	 private String packageService;
	 //货物类型
	 private String goodsType;
	 //付款方式
	 private String paidMethod;
	 //总件数
	 private Integer pieces;
	 //重量
	 private double goodsWeightTotal;
	 //体积
	 private double goodsVolumeTotal;
	 /**
	  * FOSS获得数据时系统时间
	 */
	private Date foss_systime;

	/**
	 * 条码
	 */
	private String barcode;
	
	/**
	 * 送标记
	 */
	private String send;
	
	/**
	 * 目的地名称
	 */
	private String destinationName;

	/**
	 * 到达外场名称
	 */
	private String destTransCenterName;
	
	/**
	 * 目的站提货网点编码
	 */
	private String destStationNumber;
	
	/**
	 * 路由库位信息
	 */
	private String  goodsAreas;
	
	/**
	 * 是否发货大客户
	 */
	private String deliveryBigCustomer;
	
	/**
	 * 是否收货大客户
	 */
	private String receiveBigCustomer;
	
	/**
	 * 修改时间
	 */
	private Date  modifyTime;

	/**
	 * 是否展会货
	 */
	private String isExhibitCargo ;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}
	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}
	public String getCustomerLableNums() {
		return customerLableNums;
	}
	public void setCustomerLableNums(String customerLableNums) {
		this.customerLableNums = customerLableNums;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getErpOrderNo() {
		return erpOrderNo;
	}
	public void setErpOrderNo(String erpOrderNo) {
		this.erpOrderNo = erpOrderNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getBillTime() {
		return billTime;
	}
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	public String getDepartmentCityName() {
		return departmentCityName;
	}
	public void setDepartmentCityName(String departmentCityName) {
		this.departmentCityName = departmentCityName;
	}
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}
	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}
	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}
	public String getReceiveMethod() {
		return receiveMethod;
	}
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getPackageService() {
		return packageService;
	}
	public void setPackageService(String packageService) {
		this.packageService = packageService;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public String getPaidMethod() {
		return paidMethod;
	}
	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}
	public Integer getPieces() {
		return pieces;
	}
	public void setPieces(Integer pieces) {
		this.pieces = pieces;
	}
	public double getGoodsWeightTotal() {
		return goodsWeightTotal;
	}
	public void setGoodsWeightTotal(double goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}
	public double getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}
	public void setGoodsVolumeTotal(double goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}
//////////////////////////////////////////////////////////////////////
	public String getArriveStoreNUM() {
		return arriveStoreNUM;
	}
	public void setArriveStoreNUM(String arriveStoreNUM) {
		this.arriveStoreNUM = arriveStoreNUM;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Date getFoss_systime() {
		return foss_systime;
	}
	public void setFoss_systime(Date foss_systime) {
		this.foss_systime = foss_systime;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getSend() {
		return send;
	}
	public void setSend(String send) {
		this.send = send;
	}
	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	public String getDestTransCenterName() {
		return destTransCenterName;
	}
	public void setDestTransCenterName(String destTransCenterName) {
		this.destTransCenterName = destTransCenterName;
	}
	public String getDestStationNumber() {
		return destStationNumber;
	}
	public void setDestStationNumber(String destStationNumber) {
		this.destStationNumber = destStationNumber;
	}
	public String getGoodsAreas() {
		return goodsAreas;
	}
	public void setGoodsAreas(String goodsAreas) {
		this.goodsAreas = goodsAreas;
	}
	public String getDeliveryBigCustomer() {
		return deliveryBigCustomer;
	}
	public void setDeliveryBigCustomer(String deliveryBigCustomer) {
		this.deliveryBigCustomer = deliveryBigCustomer;
	}
	public String getReceiveBigCustomer() {
		return receiveBigCustomer;
	}
	public void setReceiveBigCustomer(String receiveBigCustomer) {
		this.receiveBigCustomer = receiveBigCustomer;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getIsExhibitCargo() {
		return isExhibitCargo;
	}
	public void setIsExhibitCargo(String isExhibitCargo) {
		this.isExhibitCargo = isExhibitCargo;
	}
	
	 
}
