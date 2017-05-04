package com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:316759,date:2017-2-28 下午4:13:51,content: </p>
 * @author 316759 
 * @date 2017-2-28 下午4:13:51
 * @since
 * @version
 */
public class WsResultOuterPriceCaccilateDubboDto implements Serializable {
	
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String caculateType;
    
    private BigDecimal calateFee;
    
    private String currencyCdoe;
    
    private String goodsCode;
    
    private String isMinFee;
    
    private BigDecimal minFee;
    
    private String outFieldCode;
    
    private String partialLineCode;
    
    private String productCode;
    
    private BigDecimal rateFee;
    
    private XMLGregorianCalendar receiveDate;
    
    private BigDecimal volume;
    
    private BigDecimal weight;

	public String getCaculateType() {
		return caculateType;
	}

	public void setCaculateType(String caculateType) {
		this.caculateType = caculateType;
	}

	public BigDecimal getCalateFee() {
		return calateFee;
	}

	public void setCalateFee(BigDecimal calateFee) {
		this.calateFee = calateFee;
	}

	public String getCurrencyCdoe() {
		return currencyCdoe;
	}

	public void setCurrencyCdoe(String currencyCdoe) {
		this.currencyCdoe = currencyCdoe;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getIsMinFee() {
		return isMinFee;
	}

	public void setIsMinFee(String isMinFee) {
		this.isMinFee = isMinFee;
	}

	public BigDecimal getMinFee() {
		return minFee;
	}

	public void setMinFee(BigDecimal minFee) {
		this.minFee = minFee;
	}

	public String getOutFieldCode() {
		return outFieldCode;
	}

	public void setOutFieldCode(String outFieldCode) {
		this.outFieldCode = outFieldCode;
	}

	public String getPartialLineCode() {
		return partialLineCode;
	}

	public void setPartialLineCode(String partialLineCode) {
		this.partialLineCode = partialLineCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public BigDecimal getRateFee() {
		return rateFee;
	}

	public void setRateFee(BigDecimal rateFee) {
		this.rateFee = rateFee;
	}

	public XMLGregorianCalendar getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(XMLGregorianCalendar receiveDate) {
		this.receiveDate = receiveDate;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
    
}
