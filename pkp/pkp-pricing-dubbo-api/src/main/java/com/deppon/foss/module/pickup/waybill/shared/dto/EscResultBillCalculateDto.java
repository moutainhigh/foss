package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 费用信息
 * @author Foss-308595-GELL
 * 2016年5月6日 17:48:56
 */
public class EscResultBillCalculateDto implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 106598521464192254L;
	

	 /**
    * 费用类型代码
    */
   private String  priceEntryCode;
   
   /**
    *  费用类型名称
    */
   private String priceEntryName;
   
   /**
    * 子类型
    */
   private String subType;
	
	/**
    * 最终实际计算的费率
    */
   private BigDecimal actualFeeRate;
   
   /**
    * 经过计算后的费用，例如使用传入的 重量* feeRate 得出后的计算值
    */
   private BigDecimal caculateFee;
	
   /**
    * 打折后的费用
    */
   private BigDecimal discountFee;
   
   /**
    * 单价/费率
    */
   private BigDecimal feeRate;
   
   /**
    * 计费类别:WEIGHT，按重量计费，单位为公斤，VOLUME 按体积计费，单位为立方；
    */
   private String caculateType;
   
   /**
    * 折扣信息
    */
   private List<EscResultDiscountDto> resultDiscountDtos;

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
	
	public BigDecimal getActualFeeRate() {
		return actualFeeRate;
	}
	
	public void setActualFeeRate(BigDecimal actualFeeRate) {
		this.actualFeeRate = actualFeeRate;
	}
	
	public BigDecimal getCaculateFee() {
		return caculateFee;
	}
	
	public void setCaculateFee(BigDecimal caculateFee) {
		this.caculateFee = caculateFee;
	}
	
	public BigDecimal getDiscountFee() {
		return discountFee;
	}
	
	public void setDiscountFee(BigDecimal discountFee) {
		this.discountFee = discountFee;
	}
	
	public BigDecimal getFeeRate() {
		return feeRate;
	}
	
	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
	}
	
	public String getCaculateType() {
		return caculateType;
	}
	
	public void setCaculateType(String caculateType) {
		this.caculateType = caculateType;
	}
	
	public List<EscResultDiscountDto> getResultDiscountDtos() {
		return resultDiscountDtos;
	}
	
	public void setResultDiscountDtos(List<EscResultDiscountDto> resultDiscountDtos) {
		this.resultDiscountDtos = resultDiscountDtos;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

   
}
