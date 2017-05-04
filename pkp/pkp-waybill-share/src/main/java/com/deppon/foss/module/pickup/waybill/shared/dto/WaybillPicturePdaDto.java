package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

public class WaybillPicturePdaDto {
	private static final long serialVersionUID = -8812980922263778489L;
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 运单标识码
	 */
	private String waybillUuid;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 司机code
	 */
	private String driverCode;
	/**
	 * 是否大票货
	 */
	private String bigGoodsFlag;
	/**
	 * 是否现金
	 */
	private String cashPayFlag;
	/**
	 * 图片url
	 */
	private String filePath;
	/**
	 * 运单类型
	 */
	private String pendgingType;
	/**
	 * 司机所在车队部门
	 */
	private String billOrgCode;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 设备号
	 */
	private String equipmentNo;
	/**
	 * 操作人
	 */
	private String operator;
	/**
	 * 百度ID
	 */
    private String baiDuId ;
    /**
	 * 收货部门(出发部门)
	 */
	private String receiveOrgCode;
    /**
	 * 是否大件上楼加收
	 */
	private String isBigUp;
	/**
	 * 500KG到1000KG超重件数
	 */
	private Integer fhToOtOverQty;
	/**
	 * 1000KG到2000KG超重件数
	 */
	private Integer otToTtOverQty;
	/**
	 * 劳务费费率
	 */
	private BigDecimal serviceRate;
	/**
	 * 劳务费
	 */
	private BigDecimal serviceFee;
	/**
	 * 车牌号
	 */
	private String truckCode;
	
	/**
	 * 司机手机
	 * @return
	 */
	private String mobilephone;
	
	/**
	 * 是否特殊客户 Y 特殊  N 非特殊
	 */
	private String  specialCustomer;
	
	/**
	 * 是否展会货 Y 是  N 否
	 */
	private String isExhibitCargo;
	public String getIsExhibitCargo() {
		return isExhibitCargo;
	}
	public void setIsExhibitCargo(String isExhibitCargo) {
		this.isExhibitCargo = isExhibitCargo;
	}
	
	public String getBaiDuId() {
		return baiDuId;
	}
	public String getBigGoodsFlag() {
		return bigGoodsFlag;
	}
	public String getBillOrgCode() {
		return billOrgCode;
	}
	public String getCashPayFlag() {
		return cashPayFlag;
	}
	public String getDriverCode() {
		return driverCode;
	}
	public String getEquipmentNo() {
		return equipmentNo;
	}
	public Integer getFhToOtOverQty() {
		return fhToOtOverQty;
	}
	public String getFilePath() {
		return filePath;
	}
	public String getId() {
		return id;
	}
	public String getIsBigUp() {
		return isBigUp;
	}
	public String getOperator() {
		return operator;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public Integer getOtToTtOverQty() {
		return otToTtOverQty;
	}
	public String getPendgingType() {
		return pendgingType;
	}
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}
	public String getRemark() {
		return remark;
	}
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public String getWaybillUuid() {
		return waybillUuid;
	}
	public void setBaiDuId(String baiDuId) {
		this.baiDuId = baiDuId;
	}
	public void setBigGoodsFlag(String bigGoodsFlag) {
		this.bigGoodsFlag = bigGoodsFlag;
	}
	public void setBillOrgCode(String billOrgCode) {
		this.billOrgCode = billOrgCode;
	}
	public void setCashPayFlag(String cashPayFlag) {
		this.cashPayFlag = cashPayFlag;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	public void setEquipmentNo(String equipmentNo) {
		this.equipmentNo = equipmentNo;
	}
	public void setFhToOtOverQty(Integer fhToOtOverQty) {
		this.fhToOtOverQty = fhToOtOverQty;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setIsBigUp(String isBigUp) {
		this.isBigUp = isBigUp;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setOtToTtOverQty(Integer otToTtOverQty) {
		this.otToTtOverQty = otToTtOverQty;
	}
	public void setPendgingType(String pendgingType) {
		this.pendgingType = pendgingType;
	}
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public void setWaybillUuid(String waybillUuid) {
		this.waybillUuid = waybillUuid;
	}
	public BigDecimal getServiceRate() {
		return serviceRate;
	}
	public void setServiceRate(BigDecimal serviceRate) {
		this.serviceRate = serviceRate;
	}
	public BigDecimal getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getSpecialCustomer() {
		return specialCustomer;
	}
	public void setSpecialCustomer(String specialCustomer) {
		this.specialCustomer = specialCustomer;
	}
	
	private Date creatTime;
	
	private String newFilePath;
	
	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public String getNewFilePath() {
		return newFilePath;
	}

	public void setNewFilePath(String newFilePath) {
		this.newFilePath = newFilePath;
	}
}
