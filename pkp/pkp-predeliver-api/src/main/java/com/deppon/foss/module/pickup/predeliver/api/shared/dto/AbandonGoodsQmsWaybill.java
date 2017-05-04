package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AbandonGoodsQmsWaybill implements Serializable{
	
	/**
	 * 类的序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 开单日期
	 */
	private Date billTime;
	
	/**
	 * 开单金额
	 */
	private BigDecimal totalFee;
	
	/**
	 * 代收货款金额
	 */
	private BigDecimal codAmount;

	/**
	 * 预付金额
	 */
	private BigDecimal prePayAmount;

	/**
	 * 到付金额
	 */
	private BigDecimal toPayAmount;

	/**
	 * 开单品名(货物名称)
	 */
	private String goodsName;

	/**
	 * 收货部门
	 */
	private String receiveOrgCode;

	/**
	 * 收货部门名称
	 */
	private String receiveOrgName;

	/**
	 * 到达部门(提货网点)
	 */
	private String customerPickupOrgCode;

	/**
	 * 到达部门名称
	 */
	private String customerPickupOrgName;

	/**
	 * 库存部门
	 */
	private String stockOrgCode;

	/**
	 * 库存部门名称
	 */
	private String stockOrgName;

	/**
	 * 到达大区
	 */
	private String bigRegionOrgCode;

	/**
	 * 到达大区名称
	 */
	private String bigRegionOrgName;

	/**
	 * 到达事业部
	 */
	private String divisionOrgCode;

	/**
	 * 到达事业部名称
	 */
	private String divisionOrgName;

	/**
	 * 开单件数
	 */
	private Integer goodsQtyTotal;

	/**
	 * 开单重量
	 */
	private BigDecimal goodsWeightTotal;

	/**
	 * 开单体积
	 */
	private BigDecimal goodsVolumeTotal;

	/**
	 * 保险价值
	 */
	private BigDecimal insuranceAmount;

	/**
	 * 运输性质
	 */
	private String productCode;

	/**
	 * 签收结果(签收情况)
	 */
	private String signSituation;

	/**
	 * 成功或失败的标识(0、失败；1、成功)
	 */
	private String result;

	/**
	 * 失败原因
	 */
	private String message;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public BigDecimal getCodAmount() {
		return codAmount;
	}

	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	public BigDecimal getPrePayAmount() {
		return prePayAmount;
	}

	public void setPrePayAmount(BigDecimal prePayAmount) {
		this.prePayAmount = prePayAmount;
	}

	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
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

	public String getStockOrgCode() {
		return stockOrgCode;
	}

	public void setStockOrgCode(String stockOrgCode) {
		this.stockOrgCode = stockOrgCode;
	}

	public String getStockOrgName() {
		return stockOrgName;
	}

	public void setStockOrgName(String stockOrgName) {
		this.stockOrgName = stockOrgName;
	}

	public String getBigRegionOrgCode() {
		return bigRegionOrgCode;
	}

	public void setBigRegionOrgCode(String bigRegionOrgCode) {
		this.bigRegionOrgCode = bigRegionOrgCode;
	}

	public String getBigRegionOrgName() {
		return bigRegionOrgName;
	}

	public void setBigRegionOrgName(String bigRegionOrgName) {
		this.bigRegionOrgName = bigRegionOrgName;
	}

	public String getDivisionOrgCode() {
		return divisionOrgCode;
	}

	public void setDivisionOrgCode(String divisionOrgCode) {
		this.divisionOrgCode = divisionOrgCode;
	}

	public String getDivisionOrgName() {
		return divisionOrgName;
	}

	public void setDivisionOrgName(String divisionOrgName) {
		this.divisionOrgName = divisionOrgName;
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

	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getSignSituation() {
		return signSituation;
	}

	public void setSignSituation(String signSituation) {
		this.signSituation = signSituation;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
