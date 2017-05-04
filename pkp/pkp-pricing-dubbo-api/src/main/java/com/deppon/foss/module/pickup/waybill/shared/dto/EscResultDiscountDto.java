package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 折扣信息
 * @author Foss-308595-GELL
 * 2016年5月6日 17:48:41
 */
public class EscResultDiscountDto implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 106598521464125654L;

	/**
	 * 折扣率
	 */
	private BigDecimal discountRate;
	/**
	 * 市场CODE
	 */
	private String marketCode;
	/**
	 * 市场NAME
	 */
	private String marketName;
	/**
	 * 市场活动类型
	 */
	private String marketType;
	/**
	 * 计价条目CODE
	 */
	private String priceEntryCode;
	/**
	 * 计价条目Name
	 */
	private String priceEntryName;
	/**
	 * 子类型
	 */
	private String subType;
	/**
	 * 方案渠道CODE
	 */
	private String saleChannelCode;
	/**
	 * 方案渠道NAME
	 */
	private String saleChannelName;
	/**
	 * 优先级类型、产品、渠道、客户
	 */
	private String discountType;
	/**
	 * 优先级类型名称、产品、渠道、客户
	 */
	private String discountTypeName;
	
	/**
	 * 减免的费用
	 */
	private BigDecimal reduceFee; 
	
	/**
	 * 快递续重折扣率
	 */
	private BigDecimal renewalDiscountRate;
	
	public BigDecimal getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}
	public String getMarketCode() {
		return marketCode;
	}
	public void setMarketCode(String marketCode) {
		this.marketCode = marketCode;
	}
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public String getMarketType() {
		return marketType;
	}
	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}
	public String getPriceEntryCode() {
		return priceEntryCode;
	}
	public void setPriceEntryCode(String priceEntryCode) {
		this.priceEntryCode = priceEntryCode;
	}
	public String getPriceEntryName() {
		return priceEntryName;
	}
	public void setPriceEntryName(String priceEntryName) {
		this.priceEntryName = priceEntryName;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getSaleChannelCode() {
		return saleChannelCode;
	}
	public void setSaleChannelCode(String saleChannelCode) {
		this.saleChannelCode = saleChannelCode;
	}
	public String getSaleChannelName() {
		return saleChannelName;
	}
	public void setSaleChannelName(String saleChannelName) {
		this.saleChannelName = saleChannelName;
	}
	public String getDiscountType() {
		return discountType;
	}
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	public String getDiscountTypeName() {
		return discountTypeName;
	}
	public void setDiscountTypeName(String discountTypeName) {
		this.discountTypeName = discountTypeName;
	}
	public BigDecimal getReduceFee() {
		return reduceFee;
	}
	public void setReduceFee(BigDecimal reduceFee) {
		this.reduceFee = reduceFee;
	}
	public BigDecimal getRenewalDiscountRate() {
		return renewalDiscountRate;
	}
	public void setRenewalDiscountRate(BigDecimal renewalDiscountRate) {
		this.renewalDiscountRate = renewalDiscountRate;
	}
	
	
}
