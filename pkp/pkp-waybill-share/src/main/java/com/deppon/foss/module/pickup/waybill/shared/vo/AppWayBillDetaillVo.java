package com.deppon.foss.module.pickup.waybill.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * APP查询Foss的发货单/收货单信息
 * @author 272311
 * 2015.09.06
 */
public class AppWayBillDetaillVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1977069536387690256L;
	
	private String waybillNum          ;
    private String orderNum            ;
    private Date sendDate            ;
    private String departureName       ;
    private String departureAddress    ;
    private String departuretel        ;
    private String departure           ;
    private String shipperName         ;
    private String shippertel          ;
    private String shipperMobile       ;
    private String shipperAddress      ;
    private String consigneeName       ;
    private String consigneetel        ;
    private String consigneeMobile     ;
    private String consigneeAddress    ;
    private String goodsName           ;
    private float weight = 0.0f         ;
    private float cubage =0.0f             ;
    private int pieces = 0            ;
    private String packing             ;
    private String transProperties     ;
    private String deliveryMode        ;
    private String payWay              ;
    private BigDecimal insurance           ;
    private BigDecimal insuranceFee        ;
    private String refundType          ;
    private BigDecimal refund              ;
    private BigDecimal refundFee           ;
    private BigDecimal tranCharge          ;
    private BigDecimal deliveryCharge      ;
    private BigDecimal receiveCharge       ;
    private BigDecimal packingCharge       ;
    private BigDecimal totalCharge         ;
    private BigDecimal otherCharge         ;
    private String returnBillType      ;
    private String transDesc           ;
    private String wayBillState        ;
    private Date signedDate          ;
    private String destination         ;
    private String stationName         ;
    private String stationaddress      ;
    private String stationtel          ;
    private String departureFax        ;
    private String destinationFax      ;
    private String departureCityName   ;
    private String destinationCityName ;
    private Date predictArriveTime   ;
    
    /**
     * 子单/母单/正常件
     */
    private String waybillType ;
    
	public String getWaybillNum() {
		return waybillNum;
	}
	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getDepartureName() {
		return departureName;
	}
	public void setDepartureName(String departureName) {
		this.departureName = departureName;
	}
	public String getDepartureAddress() {
		return departureAddress;
	}
	public void setDepartureAddress(String departureAddress) {
		this.departureAddress = departureAddress;
	}
	public String getDeparturetel() {
		return departuretel;
	}
	public void setDeparturetel(String departuretel) {
		this.departuretel = departuretel;
	}
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	public String getShipperName() {
		return shipperName;
	}
	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}
	public String getShippertel() {
		return shippertel;
	}
	public void setShippertel(String shippertel) {
		this.shippertel = shippertel;
	}
	public String getShipperMobile() {
		return shipperMobile;
	}
	public void setShipperMobile(String shipperMobile) {
		this.shipperMobile = shipperMobile;
	}
	public String getShipperAddress() {
		return shipperAddress;
	}
	public void setShipperAddress(String shipperAddress) {
		this.shipperAddress = shipperAddress;
	}
	public String getConsigneeName() {
		return consigneeName;
	}
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	public String getConsigneetel() {
		return consigneetel;
	}
	public void setConsigneetel(String consigneetel) {
		this.consigneetel = consigneetel;
	}
	public String getConsigneeMobile() {
		return consigneeMobile;
	}
	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}
	public String getConsigneeAddress() {
		return consigneeAddress;
	}
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public float getCubage() {
		return cubage;
	}
	public void setCubage(float cubage) {
		this.cubage = cubage;
	}
	public int getPieces() {
		return pieces;
	}
	public void setPieces(int pieces) {
		this.pieces = pieces;
	}
	public String getPacking() {
		return packing;
	}
	public void setPacking(String packing) {
		this.packing = packing;
	}
	public String getTransProperties() {
		return transProperties;
	}
	public void setTransProperties(String transProperties) {
		this.transProperties = transProperties;
	}
	public String getDeliveryMode() {
		return deliveryMode;
	}
	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public BigDecimal getInsurance() {
		return insurance;
	}
	public void setInsurance(BigDecimal insurance) {
		this.insurance = insurance;
	}
	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}
	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}
	public String getRefundType() {
		return refundType;
	}
	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}
	public BigDecimal getRefund() {
		return refund;
	}
	public void setRefund(BigDecimal refund) {
		this.refund = refund;
	}
	public BigDecimal getRefundFee() {
		return refundFee;
	}
	public void setRefundFee(BigDecimal refundFee) {
		this.refundFee = refundFee;
	}
	public BigDecimal getTranCharge() {
		return tranCharge;
	}
	public void setTranCharge(BigDecimal tranCharge) {
		this.tranCharge = tranCharge;
	}
	public BigDecimal getDeliveryCharge() {
		return deliveryCharge;
	}
	public void setDeliveryCharge(BigDecimal deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}
	public BigDecimal getReceiveCharge() {
		return receiveCharge;
	}
	public void setReceiveCharge(BigDecimal receiveCharge) {
		this.receiveCharge = receiveCharge;
	}
	public BigDecimal getPackingCharge() {
		return packingCharge;
	}
	public void setPackingCharge(BigDecimal packingCharge) {
		this.packingCharge = packingCharge;
	}
	public BigDecimal getTotalCharge() {
		return totalCharge;
	}
	public void setTotalCharge(BigDecimal totalCharge) {
		this.totalCharge = totalCharge;
	}
	public BigDecimal getOtherCharge() {
		return otherCharge;
	}
	public void setOtherCharge(BigDecimal otherCharge) {
		this.otherCharge = otherCharge;
	}
	public String getReturnBillType() {
		return returnBillType;
	}
	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}
	public String getTransDesc() {
		return transDesc;
	}
	public void setTransDesc(String transDesc) {
		this.transDesc = transDesc;
	}
	public String getWayBillState() {
		return wayBillState;
	}
	public void setWayBillState(String wayBillState) {
		this.wayBillState = wayBillState;
	}
	public Date getSignedDate() {
		return signedDate;
	}
	public void setSignedDate(Date signedDate) {
		this.signedDate = signedDate;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getStationaddress() {
		return stationaddress;
	}
	public void setStationaddress(String stationaddress) {
		this.stationaddress = stationaddress;
	}
	public String getStationtel() {
		return stationtel;
	}
	public void setStationtel(String stationtel) {
		this.stationtel = stationtel;
	}
	public String getDepartureFax() {
		return departureFax;
	}
	public void setDepartureFax(String departureFax) {
		this.departureFax = departureFax;
	}
	public String getDestinationFax() {
		return destinationFax;
	}
	public void setDestinationFax(String destinationFax) {
		this.destinationFax = destinationFax;
	}
	public String getDepartureCityName() {
		return departureCityName;
	}
	public void setDepartureCityName(String departureCityName) {
		this.departureCityName = departureCityName;
	}
	public String getDestinationCityName() {
		return destinationCityName;
	}
	public void setDestinationCityName(String destinationCityName) {
		this.destinationCityName = destinationCityName;
	}
	public Date getPredictArriveTime() {
		return predictArriveTime;
	}
	public void setPredictArriveTime(Date predictArriveTime) {
		this.predictArriveTime = predictArriveTime;
	}
	public String getWaybillType() {
		return waybillType;
	}
	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
	}

}                                        
