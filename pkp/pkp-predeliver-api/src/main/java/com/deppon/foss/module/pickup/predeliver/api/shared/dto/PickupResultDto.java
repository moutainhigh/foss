package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/***
 * 提货清单结果Vo
 * @author foss-yuting
 * @date 2014-11-12 上午14:17:49
 * @since
 * @version
 */
@SuppressWarnings("serial")
public class PickupResultDto implements Serializable{
	/** 
	 * id
	 */
	private String id;
	/**
	 * 序列号
	 */
	private Integer serialNumber;
	/** 
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 货物的包装
	 */
	private String goodPackage;
	
	/**
	 * 货物的开单件数
	 */
	private Integer  billingGoodsQty;
	
	/**
	 * 货物重量
	 */
	private BigDecimal goodWeight;
	
	/**
	 * 货物体积
	 */
	private BigDecimal goodVolume;
	
	/**
	 * 货物尺寸
	 */
	private String goodSize;
	
	/**
	 * 操作的状态，下拉框为数据字典配置，包含已告知、已部分签收、已全部签收、已反签收、已撤销、全部，默认显示“已告知”状态；
	 */
	private String state;
	
	/**
	 * 操作时间
	 */
	private Date operTime;
	
	/**
	 * 操作部门名称
	 */
	private String operateOrgName;
	
	/**
	 * 操作部门编码
	 */
	private String operateOrgCode;
	
	/**
	 * 操作人
	 */
	private String operateUserName;
	
	/**
	 * 操作人编码
	 */
	private String operateUserCode;
	
	/**
	 * 对应外场
	 */
	private String endStockOrgCode;
	
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
	public String getGoodPackage() {
		return goodPackage;
	}
	public void setGoodPackage(String goodPackage) {
		this.goodPackage = goodPackage;
	}
	public Integer getBillingGoodsQty() {
		return billingGoodsQty;
	}
	public void setBillingGoodsQty(Integer billingGoodsQty) {
		this.billingGoodsQty = billingGoodsQty;
	}
	public BigDecimal getGoodWeight() {
		return goodWeight;
	}
	public void setGoodWeight(BigDecimal goodWeight) {
		this.goodWeight = goodWeight;
	}
	public BigDecimal getGoodVolume() {
		return goodVolume;
	}
	public void setGoodVolume(BigDecimal goodVolume) {
		this.goodVolume = goodVolume;
	}
	public String getGoodSize() {
		return goodSize;
	}
	public void setGoodSize(String goodSize) {
		this.goodSize = goodSize;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	public Integer getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getOperateOrgName() {
		return operateOrgName;
	}
	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}
	public String getOperateOrgCode() {
		return operateOrgCode;
	}
	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}
	public String getOperateUserName() {
		return operateUserName;
	}
	public void setOperateUserName(String operateUserName) {
		this.operateUserName = operateUserName;
	}
	public String getOperateUserCode() {
		return operateUserCode;
	}
	public void setOperateUserCode(String operateUserCode) {
		this.operateUserCode = operateUserCode;
	}
	public String getEndStockOrgCode() {
		return endStockOrgCode;
	}
	public void setEndStockOrgCode(String endStockOrgCode) {
		this.endStockOrgCode = endStockOrgCode;
	}
	
}
