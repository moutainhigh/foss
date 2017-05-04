package com.deppon.foss.module.pickup.waybill.shared.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.WsGuiQueryBillCalculateSubDubboDto;

/**
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:316759,date:2017-2-28 下午4:01:38,content: </p>
 * @author 316759 
 * @date 2017-2-28 下午4:01:38
 * @since
 * @version
 */
public class WsGuiQueryBillCalculateDubboRequest implements Serializable {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String allNet;
    
    private String arrvRegionId;
    
    private String users;
    
    private String startCountyCode;
    
    private String startCityCode;
    
    private String startProvCode;
    
    private String arriveCountyCode;
    
    private String arriveCityCode;
    
    private String arriveProvCode;
    
    private String channelCode;
    
    private String cityMarketCode;
    
    private String combBillTypeCode;
    
    private String currencyCdoe;
    
    private String customerCode;
    
    private String deptRegionId;
    
    private String destinationOrgCode;
    
    private String economySince;
    
    private BigDecimal feeRate = BigDecimal.ZERO;
    
    private String flightShift;
    
    private String goodsCode;
    
    private String industrulCode;
    
    private String isReceiveGoods;
    
    private String isSelfPickUp;
    
    private BigDecimal kilom = BigDecimal.ZERO;
    
    private String lastOrgCode;
    
    private String longOrShort;
    
    private String originalOrgCode;
    
    private BigDecimal originnalCost = BigDecimal.ZERO;
    
    private List<WsGuiQueryBillCalculateSubDubboDto> priceEntities;
    
    private String pricingEntryCode;
    
    private String productCode;
    
    private XMLGregorianCalendar receiveDate;
    
    private String subType;
    
    private BigDecimal volume = BigDecimal.ZERO;
    
    private BigDecimal weight = BigDecimal.ZERO;
    
    private BigDecimal woodenVolume = BigDecimal.ZERO;

	public String getAllNet() {
		return allNet;
	}

	public void setAllNet(String allNet) {
		this.allNet = allNet;
	}

	public String getArrvRegionId() {
		return arrvRegionId;
	}

	public void setArrvRegionId(String arrvRegionId) {
		this.arrvRegionId = arrvRegionId;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public String getStartCountyCode() {
		return startCountyCode;
	}

	public void setStartCountyCode(String startCountyCode) {
		this.startCountyCode = startCountyCode;
	}

	public String getStartCityCode() {
		return startCityCode;
	}

	public void setStartCityCode(String startCityCode) {
		this.startCityCode = startCityCode;
	}

	public String getStartProvCode() {
		return startProvCode;
	}

	public void setStartProvCode(String startProvCode) {
		this.startProvCode = startProvCode;
	}

	public String getArriveCountyCode() {
		return arriveCountyCode;
	}

	public void setArriveCountyCode(String arriveCountyCode) {
		this.arriveCountyCode = arriveCountyCode;
	}

	public String getArriveCityCode() {
		return arriveCityCode;
	}

	public void setArriveCityCode(String arriveCityCode) {
		this.arriveCityCode = arriveCityCode;
	}

	public String getArriveProvCode() {
		return arriveProvCode;
	}

	public void setArriveProvCode(String arriveProvCode) {
		this.arriveProvCode = arriveProvCode;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getCityMarketCode() {
		return cityMarketCode;
	}

	public void setCityMarketCode(String cityMarketCode) {
		this.cityMarketCode = cityMarketCode;
	}

	public String getCombBillTypeCode() {
		return combBillTypeCode;
	}

	public void setCombBillTypeCode(String combBillTypeCode) {
		this.combBillTypeCode = combBillTypeCode;
	}

	public String getCurrencyCdoe() {
		return currencyCdoe;
	}

	public void setCurrencyCdoe(String currencyCdoe) {
		this.currencyCdoe = currencyCdoe;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getDeptRegionId() {
		return deptRegionId;
	}

	public void setDeptRegionId(String deptRegionId) {
		this.deptRegionId = deptRegionId;
	}

	public String getDestinationOrgCode() {
		return destinationOrgCode;
	}

	public void setDestinationOrgCode(String destinationOrgCode) {
		this.destinationOrgCode = destinationOrgCode;
	}

	public String getEconomySince() {
		return economySince;
	}

	public void setEconomySince(String economySince) {
		this.economySince = economySince;
	}

	public BigDecimal getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
	}

	public String getFlightShift() {
		return flightShift;
	}

	public void setFlightShift(String flightShift) {
		this.flightShift = flightShift;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getIndustrulCode() {
		return industrulCode;
	}

	public void setIndustrulCode(String industrulCode) {
		this.industrulCode = industrulCode;
	}

	public String getIsReceiveGoods() {
		return isReceiveGoods;
	}

	public void setIsReceiveGoods(String isReceiveGoods) {
		this.isReceiveGoods = isReceiveGoods;
	}

	public String getIsSelfPickUp() {
		return isSelfPickUp;
	}

	public void setIsSelfPickUp(String isSelfPickUp) {
		this.isSelfPickUp = isSelfPickUp;
	}

	public BigDecimal getKilom() {
		return kilom;
	}

	public void setKilom(BigDecimal kilom) {
		this.kilom = kilom;
	}

	public String getLastOrgCode() {
		return lastOrgCode;
	}

	public void setLastOrgCode(String lastOrgCode) {
		this.lastOrgCode = lastOrgCode;
	}

	public String getLongOrShort() {
		return longOrShort;
	}

	public void setLongOrShort(String longOrShort) {
		this.longOrShort = longOrShort;
	}

	public String getOriginalOrgCode() {
		return originalOrgCode;
	}

	public void setOriginalOrgCode(String originalOrgCode) {
		this.originalOrgCode = originalOrgCode;
	}

	public BigDecimal getOriginnalCost() {
		return originnalCost;
	}

	public void setOriginnalCost(BigDecimal originnalCost) {
		this.originnalCost = originnalCost;
	}

	public List<WsGuiQueryBillCalculateSubDubboDto> getPriceEntities() {
		if(priceEntities == null){
			priceEntities = new ArrayList<WsGuiQueryBillCalculateSubDubboDto>();
		}
		return priceEntities;
	}

	public void setPriceEntities(
			List<WsGuiQueryBillCalculateSubDubboDto> priceEntities) {
		this.priceEntities = priceEntities;
	}

	public String getPricingEntryCode() {
		return pricingEntryCode;
	}

	public void setPricingEntryCode(String pricingEntryCode) {
		this.pricingEntryCode = pricingEntryCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public XMLGregorianCalendar getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(XMLGregorianCalendar receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
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

	public BigDecimal getWoodenVolume() {
		return woodenVolume;
	}

	public void setWoodenVolume(BigDecimal woodenVolume) {
		this.woodenVolume = woodenVolume;
	}

}
