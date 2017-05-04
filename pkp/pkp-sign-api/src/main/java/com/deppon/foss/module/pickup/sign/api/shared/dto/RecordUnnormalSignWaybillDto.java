package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/***
 * @author: foss-231434
 * @description: foss异常签收自动上报QMS   结果的DTO
 * @date:2016年6月22日 下午15:59:21
 */
public class RecordUnnormalSignWaybillDto implements Serializable{

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
	 * 是否已上报(“Y”有效即未上报，“N”无效即已上报)
	 */
	private String active;
	
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	/**
	 * 上报时间
	 */
	private Date reportTime;
	
	/**
	 * 备注
	 */
	private String note;
	
	/**
	 * 签收时间
	 */
	private Date signTime;
	
	/**
	 * 是否内物短少(Y:是，N:不是)
	 */
	private String isGoodsShort;
	
	/**
	 * 签收备注
	 */
	private String signNote;
	
	/**
	 * 货物名称
	 */
	private String goodsName;
	
	/**
	 * 零担差错-保险金额(保价声明价值)
	 */
	private BigDecimal safeMoney;

	/**
	 * 异常货物件数
	 */
	private Integer unnormalNumber;
	
	/**
	 * 异常签收时间(与上面签收时间本质上是一样，但qms需要字符串类型，所以避免重复新增字段)
	 */
	private String unnormalSignTime;
	
	/**
	 * 零担差错-经手部门(标杆编码)
	 */
	private String goodsHandleDeptCode;
	
	/**
	 * 零担差错-经手部门名称
	 */
	private String goodsHandleDeptName;
	
	/**
	 * 开单部门
	 */
	private String billingDeptCode;
	
	/**
	 * 开单部门名字
	 */
	private String billingDeptName;
	
	/**
	 * 零担差错-重量
	 */
	private BigDecimal sumWeight;
	
	/**
	 * 零担差错-体积
	 */
	private BigDecimal sumVolume;
	
	/**
	 * 零担差错-件数
	 */
	private Integer sumNumber;
	
	/**
	 * 零担差错-运费总额
	 */
	private BigDecimal freightSumFee;
	
	/**
	 * 货物包装
	 */
	private String goodsPackage;
	
	/**
	 * 零担差错-(开单)付款方式
	 */
	private String payType;
	
	/**
	 * 零担差错-运输性质
	 */
	private String transNature;
	
	/**
	 * 零担差错-派送方式（提货方式）
	 */
	private String pickUpType;
	
	/**
	 * 零担差错-发货时间(开单时间)
	 */
	private String sendGoodsTime;
	
	/**
	 * 签收类型(签收情况)
	 */
	private String signSituation;
	
	/**
	 * 货物类型
	 */
	private String goodsSourceType;
	
	/**
	 * 经手部门
	 */
	private List<Map<String,String>> goodsHandleDepts;
	
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

	public String getSignSituation() {
		return signSituation;
	}

	public void setSignSituation(String signSituation) {
		this.signSituation = signSituation;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public String getSignNote() {
		return signNote;
	}

	public void setSignNote(String signNote) {
		this.signNote = signNote;
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

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public BigDecimal getSafeMoney() {
		return safeMoney;
	}

	public void setSafeMoney(BigDecimal safeMoney) {
		this.safeMoney = safeMoney;
	}

	public Integer getUnnormalNumber() {
		return unnormalNumber;
	}

	public void setUnnormalNumber(Integer unnormalNumber) {
		this.unnormalNumber = unnormalNumber;
	}

	public String getGoodsHandleDeptCode() {
		return goodsHandleDeptCode;
	}

	public void setGoodsHandleDeptCode(String goodsHandleDeptCode) {
		this.goodsHandleDeptCode = goodsHandleDeptCode;
	}

	public String getGoodsHandleDeptName() {
		return goodsHandleDeptName;
	}

	public void setGoodsHandleDeptName(String goodsHandleDeptName) {
		this.goodsHandleDeptName = goodsHandleDeptName;
	}

	public String getBillingDeptCode() {
		return billingDeptCode;
	}

	public void setBillingDeptCode(String billingDeptCode) {
		this.billingDeptCode = billingDeptCode;
	}

	public String getBillingDeptName() {
		return billingDeptName;
	}

	public void setBillingDeptName(String billingDeptName) {
		this.billingDeptName = billingDeptName;
	}

	public BigDecimal getSumWeight() {
		return sumWeight;
	}

	public void setSumWeight(BigDecimal sumWeight) {
		this.sumWeight = sumWeight;
	}

	public BigDecimal getSumVolume() {
		return sumVolume;
	}

	public void setSumVolume(BigDecimal sumVolume) {
		this.sumVolume = sumVolume;
	}

	public Integer getSumNumber() {
		return sumNumber;
	}

	public void setSumNumber(Integer sumNumber) {
		this.sumNumber = sumNumber;
	}

	public BigDecimal getFreightSumFee() {
		return freightSumFee;
	}

	public void setFreightSumFee(BigDecimal freightSumFee) {
		this.freightSumFee = freightSumFee;
	}

	public String getGoodsPackage() {
		return goodsPackage;
	}

	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getTransNature() {
		return transNature;
	}

	public void setTransNature(String transNature) {
		this.transNature = transNature;
	}

	public String getPickUpType() {
		return pickUpType;
	}

	public void setPickUpType(String pickUpType) {
		this.pickUpType = pickUpType;
	}

	public String getSendGoodsTime() {
		return sendGoodsTime;
	}

	public void setSendGoodsTime(String sendGoodsTime) {
		this.sendGoodsTime = sendGoodsTime;
	}

	public String getUnnormalSignTime() {
		return unnormalSignTime;
	}

	public void setUnnormalSignTime(String unnormalSignTime) {
		this.unnormalSignTime = unnormalSignTime;
	}

	public String getIsGoodsShort() {
		return isGoodsShort;
	}

	public void setIsGoodsShort(String isGoodsShort) {
		this.isGoodsShort = isGoodsShort;
	}

	public String getGoodsSourceType() {
		return goodsSourceType;
	}

	public void setGoodsSourceType(String goodsSourceType) {
		this.goodsSourceType = goodsSourceType;
	}

	public List<Map<String, String>> getGoodsHandleDepts() {
		return goodsHandleDepts;
	}

	public void setGoodsHandleDepts(List<Map<String, String>> goodsHandleDepts) {
		this.goodsHandleDepts = goodsHandleDepts;
	}
	
}
