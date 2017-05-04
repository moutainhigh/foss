package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 重大货物异常自动上报的dto
 * @author 306548-foss-honglujun
 *
 */
public class RecordErrorImportantWaybillDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private String id;
	/**
	 *  运单号
	 */
	private String waybillNo;
	
	/**
	 * 保价声明价值
	 */
	private BigDecimal insuranceAmount;
	
	/**
	 * 开单时间
	 */
	private Date billTime;
	
	/**
	 * 货物名称
	 */
	private String goodsName;
	
	/** 
	 * 流水号
	 */ 
	private String serialNo;
	
	/**
	 * 外包装是否完好
	 */
	private String packingResult;
	
	/**
	 * 货物总体积
	 */
	private BigDecimal goodsVolumeTotal;
	
	/**
	 * 货物总件数
	 */
	private Integer goodsQtyTotal;
	
	/**
	 *  上报时间
	 */
	private Date createTime;
	
	/**
	 * 是否已经上报
	 */
	private String active;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	/***
	 * 签收人名称
	 */
	private String operateName;

	/***
	 * 签收人code
	 */
	private String operateCode;
	
	/***
	 * 签收部门
	 */
	private String operateOrgCode;
	
	/**
	 * 签收部门名称
	 */
	private String operateOrgName;
	
	/**
	 * 签收时间
	 */
	private Date signTime;
	
	/**
	 * 派送车牌号（派送时）
	 */
	private String vehicleNo;
	
	/**
	 * 派送司机工号
	 */
	private String driverCode;
	
	/**
	 * 派送司机姓名
	 */
	private String driverName;
	
	/**
	 * 派送司机所在部门编码
	 */
	private String driverOrgCode;

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

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getPackingResult() {
		return packingResult;
	}

	public void setPackingResult(String packingResult) {
		this.packingResult = packingResult;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public String getOperateCode() {
		return operateCode;
	}

	public void setOperateCode(String operateCode) {
		this.operateCode = operateCode;
	}

	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	public String getOperateOrgName() {
		return operateOrgName;
	}

	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverOrgCode() {
		return driverOrgCode;
	}

	public void setDriverOrgCode(String driverOrgCode) {
		this.driverOrgCode = driverOrgCode;
	}

}
