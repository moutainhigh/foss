package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ExhibitWaybillDto {

	/**
	 * id
	 */
	private String id;
	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 产品ID
	 */
	private String productId;

	/**
	 * 运输性质
	 */
	private String productCode;
	/**
	 * 运输性质名称
	 */
	private String productName;

	/**
	 * 提货方式
	 */
	private String receiveMethod;

	/**
	 * 提货网点
	 */
	private String customerPickupOrgCode;

	/**
	 * 货物名称
	 */
	private String goodsName;

	/**
	 * 货物总件数
	 */
	private Integer goodsQtyTotal;

	/**
	 * 货物总重量
	 */
	private BigDecimal goodsWeightTotal;

	/**
	 * 货物总体积
	 */
	private BigDecimal goodsVolumeTotal;

	/**
	 * 货物类型
	 */
	private String goodsTypeCode;

	/**
	 * 是否贵重物品
	 */
	private String preciousGoods;

	/**
	 * 是否异形物品
	 */
	private String specialShapedGoods;

	/**
	 * 货物包装
	 */
	private String goodsPackage;


	/**
	 * 到达类型
	 */
	private String arriveType;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 开单时间
	 */
	private Date billTime;

	/**
	 * 是否整车运单
	 */
	private String isWholeVehicle;

	/**
	 * 运输类型
	 */
	private String transportType;

	/**
	 * 运单状态
	 */
	private String active;

	/**
	 * 处理类型:"PDA_ACTIVE"--PDA已补录 ，"PC_ACTIVE"--暂存已开单
	 */
	private String pendingType;

	/**
	 * 提货网点名称
	 */
	private String customerPickupOrgName;
	
	/**
	 * 是否快递
	 * @return
	 */
	private String isExpress;

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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	public String getGoodsTypeCode() {
		return goodsTypeCode;
	}

	public void setGoodsTypeCode(String goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
	}

	public String getPreciousGoods() {
		return preciousGoods;
	}

	public void setPreciousGoods(String preciousGoods) {
		this.preciousGoods = preciousGoods;
	}

	public String getSpecialShapedGoods() {
		return specialShapedGoods;
	}

	public void setSpecialShapedGoods(String specialShapedGoods) {
		this.specialShapedGoods = specialShapedGoods;
	}

	public String getGoodsPackage() {
		return goodsPackage;
	}

	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	public String getArriveType() {
		return arriveType;
	}

	public void setArriveType(String arriveType) {
		this.arriveType = arriveType;
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

	public String getIsWholeVehicle() {
		return isWholeVehicle;
	}

	public void setIsWholeVehicle(String isWholeVehicle) {
		this.isWholeVehicle = isWholeVehicle;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getPendingType() {
		return pendingType;
	}

	public void setPendingType(String pendingType) {
		this.pendingType = pendingType;
	}

	public String getCustomerPickupOrgName() {
		return customerPickupOrgName;
	}

	public void setCustomerPickupOrgName(String customerPickupOrgName) {
		this.customerPickupOrgName = customerPickupOrgName;
	}

	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	
	
	
	
}
