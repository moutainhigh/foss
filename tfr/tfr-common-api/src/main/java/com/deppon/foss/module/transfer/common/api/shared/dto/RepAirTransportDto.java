package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 *@fields serialVersionUID
 *@author 268220-chenmin
 *@date  2016-5-6 上午8:48:34
 *@version V1.0
 */
public class RepAirTransportDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 运单号/包号
	 */
	private String billNoPackNo;
	/**
	 * 预配班次
	 */
	private String  prMatchflight;
	/**
	 * 开单方式
	 */
	private String billWay;
	/**
	 * 目的站
	 */
	private String destinationStation;
	/**
	 *毛重（公斤） 
	 */
	private BigDecimal  grossWeight;
	/**
	 * 计费重量（公斤）
	 */
	private BigDecimal countWeight;
	/**
	 * 体积(立方米)
	 */
	private BigDecimal volume;
	/**
	 * 件数
	 */
	private int Number;
	/**
	 * 品名
	 */
	private String GoodsName;
	/**
	 * 库存状态
	 */
	private String stockState;
	/**
	 * 出发时间
	 */
	private Date startTime;
	/**
	 * 费率
	 */
	private double rate;
	/**
	 * 收货部门
	 */
	private String receiveDept;
	/**
	 * 索票备注
	 */
	private String theTicketRemarks;
	/**
	 * 运输性质
	 */
	private String transportProperties;
	/**
	 * 提货方式
	 */
	private String deliveryMode;
	/**
	 * 开单时间
	 */
	private Date billTime;
	/**
	 * 收货人电话
	 */
	private String receiverContactPhone;
	
	/**
	 * 收货人姓名
	 */
	private String receiverName;
	
	/**
	 * 收货人地址
	 */
	private String receiverAddress;
	
	/**
	 * 送货费
	 */
	private BigDecimal deliverFee;
	
	/**
	 * 到付费
	 */
	private BigDecimal arrivalFee;
	
	/**
	 * 代收款
	 */
	private BigDecimal collectionFee;
	
	/**
	 * 产品类型
	 */
	private String productCode;
	
	
	
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public BigDecimal getDeliverFee() {
		return deliverFee;
	}
	public void setDeliverFee(BigDecimal deliverFee) {
		this.deliverFee = deliverFee;
	}
	public BigDecimal getArrivalFee() {
		return arrivalFee;
	}
	public void setArrivalFee(BigDecimal arrivalFee) {
		this.arrivalFee = arrivalFee;
	}
	public BigDecimal getCollectionFee() {
		return collectionFee;
	}
	public void setCollectionFee(BigDecimal collectionFee) {
		this.collectionFee = collectionFee;
	}
	public String getBillNoPackNo() {
		return billNoPackNo;
	}
	public void setBillNoPackNo(String billNoPackNo) {
		this.billNoPackNo = billNoPackNo;
	}
	public String getPrMatchflight() {
		return prMatchflight;
	}
	public void setPrMatchflight(String prMatchflight) {
		this.prMatchflight = prMatchflight;
	}
	public String getBillWay() {
		return billWay;
	}
	public void setBillWay(String billWay) {
		this.billWay = billWay;
	}
	public String getGoodsName() {
		return GoodsName;
	}
	public void setGoodsName(String goodsName) {
		GoodsName = goodsName;
	}
	public String getStockState() {
		return stockState;
	}
	public void setStockState(String stockState) {
		this.stockState = stockState;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getReceiveDept() {
		return receiveDept;
	}
	public void setReceiveDept(String receiveDept) {
		this.receiveDept = receiveDept;
	}
	public String getTheTicketRemarks() {
		return theTicketRemarks;
	}
	public void setTheTicketRemarks(String theTicketRemarks) {
		this.theTicketRemarks = theTicketRemarks;
	}
	public String getTransportProperties() {
		return transportProperties;
	}
	public void setTransportProperties(String transportProperties) {
		this.transportProperties = transportProperties;
	}
	public String getDeliveryMode() {
		return deliveryMode;
	}
	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	public BigDecimal getGrossWeight() {
		return grossWeight;
	}
	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}
	public BigDecimal getCountWeight() {
		return countWeight;
	}
	public void setCountWeight(BigDecimal countWeight) {
		this.countWeight = countWeight;
	}
	public BigDecimal getVolume() {
		return volume;
	}
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	public int getNumber() {
		return Number;
	}
	public void setNumber(int number) {
		Number = number;
	}
	public String getDestinationStation() {
		return destinationStation;
	}
	public void setDestinationStation(String destinationStation) {
		this.destinationStation = destinationStation;
	}
	public Date getBillTime() {
		return billTime;
	}
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}
	public String getReceiverContactPhone() {
		return receiverContactPhone;
	}
	public void setReceiverContactPhone(String receiverContactPhone) {
		this.receiverContactPhone = receiverContactPhone;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	} 
	
}
