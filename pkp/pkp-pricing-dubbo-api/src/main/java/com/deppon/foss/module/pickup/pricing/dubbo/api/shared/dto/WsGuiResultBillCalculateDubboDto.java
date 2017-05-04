package com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:316759,date:2017-2-28 下午4:15:03,content: </p>
 * @author 316759 
 * @date 2017-2-28 下午4:15:03
 * @since
 * @version
 */
public class WsGuiResultBillCalculateDubboDto implements Serializable {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal actualFeeRate;
    
    private String arrvRegionId;
    
    private String belongToPriceEntityCode;
    
    private String belongToPriceEntityName;
    
    private String caculateExpression;
    
    private BigDecimal caculateFee;
    
    private String caculateType;
    
    private String candelete;
    
    private String canmodify;
    
    private String centralizePickup;
    
    private BigDecimal defaultBF;
    
    private BigDecimal discountFee;
    
    private List<WsGuiResultDiscountDubboDto> discountPrograms;
    
    private BigDecimal fee;
    
    private BigDecimal feeRate;
    
    private String flightShiftNo;
    
    private String goodsTypeCode;
    
    private String goodsTypeName;
    
    private BigDecimal heavyFeeRate;
    
    private BigDecimal heavyFeeRatePickUpNo;
    
    private BigDecimal heavyFeeRatePickUpYes;
    
    private String id;
    
    private BigDecimal lightFeeRate;
    
    private BigDecimal lightFeeRatePickUpNo;
    
    private BigDecimal lightFeeRatePickUpYes;
    
    private String longOrShort;
    
    private BigDecimal maxFee;
    
    private BigDecimal maxFeeRate;
    
    private BigDecimal minFee;
    
    private BigDecimal minFeePickUpNo;
    
    private BigDecimal minFeePickUpYes;
    
    private BigDecimal minFeeRate;
    
    private String priceEntryCode;
    
    private String priceEntryName;
    
    private String productCode;
    
    private String productName;
    
    private WsResultOuterPriceCaccilateDubboDto resultOuterPriceCaccilateDto;
    
    private String startRegionId;
    
    private String subType;
    
    private String subTypeName;
    
    private BigDecimal volumeWeight;

	public BigDecimal getActualFeeRate() {
		return actualFeeRate;
	}

	public void setActualFeeRate(BigDecimal actualFeeRate) {
		this.actualFeeRate = actualFeeRate;
	}

	public String getArrvRegionId() {
		return arrvRegionId;
	}

	public void setArrvRegionId(String arrvRegionId) {
		this.arrvRegionId = arrvRegionId;
	}

	public String getBelongToPriceEntityCode() {
		return belongToPriceEntityCode;
	}

	public void setBelongToPriceEntityCode(String belongToPriceEntityCode) {
		this.belongToPriceEntityCode = belongToPriceEntityCode;
	}

	public String getBelongToPriceEntityName() {
		return belongToPriceEntityName;
	}

	public void setBelongToPriceEntityName(String belongToPriceEntityName) {
		this.belongToPriceEntityName = belongToPriceEntityName;
	}

	public String getCaculateExpression() {
		return caculateExpression;
	}

	public void setCaculateExpression(String caculateExpression) {
		this.caculateExpression = caculateExpression;
	}

	public BigDecimal getCaculateFee() {
		return caculateFee;
	}

	public void setCaculateFee(BigDecimal caculateFee) {
		this.caculateFee = caculateFee;
	}

	public String getCaculateType() {
		return caculateType;
	}

	public void setCaculateType(String caculateType) {
		this.caculateType = caculateType;
	}

	public String getCandelete() {
		return candelete;
	}

	public void setCandelete(String candelete) {
		this.candelete = candelete;
	}

	public String getCanmodify() {
		return canmodify;
	}

	public void setCanmodify(String canmodify) {
		this.canmodify = canmodify;
	}

	public String getCentralizePickup() {
		return centralizePickup;
	}

	public void setCentralizePickup(String centralizePickup) {
		this.centralizePickup = centralizePickup;
	}

	public BigDecimal getDefaultBF() {
		return defaultBF;
	}

	public void setDefaultBF(BigDecimal defaultBF) {
		this.defaultBF = defaultBF;
	}

	public BigDecimal getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(BigDecimal discountFee) {
		this.discountFee = discountFee;
	}

	public List<WsGuiResultDiscountDubboDto> getDiscountPrograms() {
		if (discountPrograms == null) {
            discountPrograms = new ArrayList<WsGuiResultDiscountDubboDto>();
        }
		return discountPrograms;
	}

	public void setDiscountPrograms(List<WsGuiResultDiscountDubboDto> discountPrograms) {
		this.discountPrograms = discountPrograms;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
	}

	public String getFlightShiftNo() {
		return flightShiftNo;
	}

	public void setFlightShiftNo(String flightShiftNo) {
		this.flightShiftNo = flightShiftNo;
	}

	public String getGoodsTypeCode() {
		return goodsTypeCode;
	}

	public void setGoodsTypeCode(String goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
	}

	public String getGoodsTypeName() {
		return goodsTypeName;
	}

	public void setGoodsTypeName(String goodsTypeName) {
		this.goodsTypeName = goodsTypeName;
	}

	public BigDecimal getHeavyFeeRate() {
		return heavyFeeRate;
	}

	public void setHeavyFeeRate(BigDecimal heavyFeeRate) {
		this.heavyFeeRate = heavyFeeRate;
	}

	public BigDecimal getHeavyFeeRatePickUpNo() {
		return heavyFeeRatePickUpNo;
	}

	public void setHeavyFeeRatePickUpNo(BigDecimal heavyFeeRatePickUpNo) {
		this.heavyFeeRatePickUpNo = heavyFeeRatePickUpNo;
	}

	public BigDecimal getHeavyFeeRatePickUpYes() {
		return heavyFeeRatePickUpYes;
	}

	public void setHeavyFeeRatePickUpYes(BigDecimal heavyFeeRatePickUpYes) {
		this.heavyFeeRatePickUpYes = heavyFeeRatePickUpYes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getLightFeeRate() {
		return lightFeeRate;
	}

	public void setLightFeeRate(BigDecimal lightFeeRate) {
		this.lightFeeRate = lightFeeRate;
	}

	public BigDecimal getLightFeeRatePickUpNo() {
		return lightFeeRatePickUpNo;
	}

	public void setLightFeeRatePickUpNo(BigDecimal lightFeeRatePickUpNo) {
		this.lightFeeRatePickUpNo = lightFeeRatePickUpNo;
	}

	public BigDecimal getLightFeeRatePickUpYes() {
		return lightFeeRatePickUpYes;
	}

	public void setLightFeeRatePickUpYes(BigDecimal lightFeeRatePickUpYes) {
		this.lightFeeRatePickUpYes = lightFeeRatePickUpYes;
	}

	public String getLongOrShort() {
		return longOrShort;
	}

	public void setLongOrShort(String longOrShort) {
		this.longOrShort = longOrShort;
	}

	public BigDecimal getMaxFee() {
		return maxFee;
	}

	public void setMaxFee(BigDecimal maxFee) {
		this.maxFee = maxFee;
	}

	public BigDecimal getMaxFeeRate() {
		return maxFeeRate;
	}

	public void setMaxFeeRate(BigDecimal maxFeeRate) {
		this.maxFeeRate = maxFeeRate;
	}

	public BigDecimal getMinFee() {
		return minFee;
	}

	public void setMinFee(BigDecimal minFee) {
		this.minFee = minFee;
	}

	public BigDecimal getMinFeePickUpNo() {
		return minFeePickUpNo;
	}

	public void setMinFeePickUpNo(BigDecimal minFeePickUpNo) {
		this.minFeePickUpNo = minFeePickUpNo;
	}

	public BigDecimal getMinFeePickUpYes() {
		return minFeePickUpYes;
	}

	public void setMinFeePickUpYes(BigDecimal minFeePickUpYes) {
		this.minFeePickUpYes = minFeePickUpYes;
	}

	public BigDecimal getMinFeeRate() {
		return minFeeRate;
	}

	public void setMinFeeRate(BigDecimal minFeeRate) {
		this.minFeeRate = minFeeRate;
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

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public WsResultOuterPriceCaccilateDubboDto getResultOuterPriceCaccilateDto() {
		return resultOuterPriceCaccilateDto;
	}

	public void setResultOuterPriceCaccilateDto(
			WsResultOuterPriceCaccilateDubboDto resultOuterPriceCaccilateDto) {
		this.resultOuterPriceCaccilateDto = resultOuterPriceCaccilateDto;
	}

	public String getStartRegionId() {
		return startRegionId;
	}

	public void setStartRegionId(String startRegionId) {
		this.startRegionId = startRegionId;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getSubTypeName() {
		return subTypeName;
	}

	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}

	public BigDecimal getVolumeWeight() {
		return volumeWeight;
	}

	public void setVolumeWeight(BigDecimal volumeWeight) {
		this.volumeWeight = volumeWeight;
	}

}
