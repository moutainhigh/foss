package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CountFreightEntity implements Serializable {
	
	private static final long serialVersionUID = -8195783099956236954L;
	private String originalOrgCode; // 出发部门编码
	private String destinationOrgCode; // 到达部门编码
	private String productCode; // 产品编码
	private Date receiveDate;// 开单日期
	private double weight;// 重量
	private double volume;// 体积
	private String currencyCode;// 币种
	private String customerCode;// 客户编码
	private String goodsCode;// 货物类型
	private String isReceiveGoods;// 是否接货
	private String flightShift;// 航班班次
	private String industrulCode;// 行业
	private String channelCode;// 订单渠道
	private List<PricingEntry> priciings; // 计价条目列表
    private String isSelfPickUp;    // 是否自提
    private String marketingCode;  //营销活动编码
    private String startOutFieldCode;  //出发外场
    private String arriveOutFieldCode;  //到达外场
	public String getOriginalOrgCode() {
		return originalOrgCode;
	}

	public void setOriginalOrgCode(String originalOrgCode) {
		this.originalOrgCode = originalOrgCode;
	}

	public String getDestinationOrgCode() {
		return destinationOrgCode;
	}

	public void setDestinationOrgCode(String destinationOrgCode) {
		this.destinationOrgCode = destinationOrgCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCdoe) {
		this.currencyCode = currencyCdoe;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getIsReceiveGoods() {
		return isReceiveGoods;
	}

	public void setIsReceiveGoods(String isReceiveGoods) {
		this.isReceiveGoods = isReceiveGoods;
	}

	public String getFlightShift() {
		return flightShift;
	}

	public void setFlightShift(String flightShift) {
		this.flightShift = flightShift;
	}

	public String getIndustrulCode() {
		return industrulCode;
	}

	public void setIndustrulCode(String industrulCode) {
		this.industrulCode = industrulCode;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public List<PricingEntry> getPriciings() {
		return priciings;
	}

	public void setPriciings(List<PricingEntry> priciings) {
		this.priciings = priciings;
	}

    public String getIsSelfPickUp() {
        return isSelfPickUp;
    }

    public void setIsSelfPickUp(String isSelfPickUp) {
        this.isSelfPickUp = isSelfPickUp;
    }

    public String getMarketingCode() {
        return marketingCode;
    }

    public void setMarketingCode(String marketingCode) {
        this.marketingCode = marketingCode;
    }

	public String getStartOutFieldCode() {
		return startOutFieldCode;
	}

	public void setStartOutFieldCode(String startOutFieldCode) {
		this.startOutFieldCode = startOutFieldCode;
	}

	public String getArriveOutFieldCode() {
		return arriveOutFieldCode;
	}

	public void setArriveOutFieldCode(String arriveOutFieldCode) {
		this.arriveOutFieldCode = arriveOutFieldCode;
	}
	

}
