package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 运单子母件信息
 * @author 272311
 * 2015.09.06
 */
public class WaybillCZMEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 202110892246630308L;
	/**
	 * 运单号
	 */
	private String waybillNum ;
	/**
	 * 母单号
	 */
	private String parentWaybillNo         ;
	/**
	 * 子单号
	 */
	private String waybillNo               ;
	/**
	 * 是否有效,Y有效,N为无效
	 */
	private String active                  ;
	/**
	 * 创建时间
	 */
	private Date createTime                ;
	/**
	 * 修改时间
	 */
	private Date modifyTime                ;
	/**
	 * 业务时间
	 */
	private Date billTime                  ;
	/**
	 * 创建人编码
	 */
	private String createUserCode          ;
	/**
	 * 修改人编码
	 */
	private String modifyUserCode          ;
	/**
	 * 修改组织编码
	 */
	private String modifyOrgCode           ;
	/**
	 * NORMAL为默认普通运单,EWAYBILL为电子运单
	 */
	private String waybillType             ;
	/**
	 * 开单组织编码
	 */
	private String createOrgCode           ;
	/**
	 * 修改组织编码
	 */
	private String receiveOrgCode          ;
	/**
	 * 母定单号
	 */
	private String orderNo                 ;
	/**
	 * 补录类型
	 */
	private String pendingType             ;
	/**
	 * 原定单号
	 */
	private String parentOrderNo           ;
	/**
	 * 总重量
	 */
	private BigDecimal goodsWeightTotal    ;
	/**
	 * 总体积
	 */
	private BigDecimal goodsVolumeTotal    ;
	/**
	 * 子件母件判定:Y母件;N:子件
	 */
	private String isPicPackage            ;
	/**
	 * 单件总量
	 */
	private BigDecimal weight              ;
	/**
	 * 单件体积
	 */
	private BigDecimal volume              ;
	/**
	 * 任务编码ID
	 */
	private String taskId                  ;
	public String getWaybillNum() {
		return waybillNum;
	}
	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}
	public String getParentWaybillNo() {
		return parentWaybillNo;
	}
	public void setParentWaybillNo(String parentWaybillNo) {
		this.parentWaybillNo = parentWaybillNo;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
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
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
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
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	public String getModifyOrgCode() {
		return modifyOrgCode;
	}
	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}
	public String getWaybillType() {
		return waybillType;
	}
	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
	}
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getPendingType() {
		return pendingType;
	}
	public void setPendingType(String pendingType) {
		this.pendingType = pendingType;
	}
	public String getParentOrderNo() {
		return parentOrderNo;
	}
	public void setParentOrderNo(String parentOrderNo) {
		this.parentOrderNo = parentOrderNo;
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
	public String getIsPicPackage() {
		return isPicPackage;
	}
	public void setIsPicPackage(String isPicPackage) {
		this.isPicPackage = isPicPackage;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public BigDecimal getVolume() {
		return volume;
	}
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
    

}
