
package com.deppon.foss.shared.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * 运单信息
 * 
 */
public class WayBillDetail {

    protected String number;
    protected String tranType;
    protected String tranProperty;
    protected String sender;
    protected String senderPhone;
    protected String senderMobile;
    protected String departure;
    protected String senderAddress;
    protected String senderAddressNote;
    protected String consignee;
    protected String consigneePhone;
    protected String consigneeMobile;
    protected String destination;
    protected String consigneeAddress;
    protected String consigneeAddressNote;
    protected String goodName;
    protected int pieces;
    protected Float weight;
    protected Float cubage;
    protected BigDecimal totalCharge;
    protected String payment;
    protected BigDecimal preCharge;
    protected BigDecimal arriveCharge;
    protected String refundType;
    protected BigDecimal refund;
    protected BigDecimal refundFee;
    protected String deliveryType;
    protected BigDecimal consignCharge;
    protected BigDecimal deliveryCharge;
    protected BigDecimal signBackCharge;
    protected BigDecimal pickCharge;
    protected BigDecimal laborRebate;
    protected BigDecimal publishCharge;
    protected String departureDeptName;
    protected String departureDeptNumber;
    protected String departureDeptAddr;
    protected String departureDeptPhone;
    protected String departureDeptFax;
    protected String ladingStationName;
    protected String ladingStationNumber;
    protected String ladingStationAddr;
    protected String ladingStationPhone;
    protected String ladingStationFax;
    protected Boolean isSigned;
    protected Boolean isNormalSigned;
    protected String signRecorderId;
    protected Date signedDate;
    protected Date firstSignedDate;
    protected String signedDesc;
    protected String orderNumber;
    protected BigDecimal insuranceValue;
    protected BigDecimal insurance;
    protected String packing;
    protected String orderState;
    protected BigDecimal otherPayment;
    protected String tranDesc;
    protected String senderNumber;
    protected String consigneeNumber;
    protected String isClear;
    protected String signBackType;
    protected String transNotice;
    protected Date sendTime;
    protected String receiveDeptName;
    protected String receiveDeptNumber;
    protected String stowageDept;
    protected String senderCityCode;
    protected String senderCityName;
    protected String senderProvinceCode;
    protected String senderProvinceName;
    protected String consigneeCityCode;
    protected String consigneeCityName;
    protected String consigneeProvinceCode;
    protected String consigneeProvinceName;
    protected Boolean isDoorToDoorPick;
    protected String smsNoticeResult;
    protected String signBillBackWay;
    protected String exDepartureRegionNubmer;
    protected String exDepartureRegionName;
    protected String exDepartureRegionStandardNubmer;
    protected String exDestinationRegionNubmer;
    protected String exDestinationRegionName;
    protected String exDestinationRegionStandardNubmer;
    protected String exDepartureCourierNumber;
    protected String exDepartureCourierName;
    protected String exDepartureDeptNumber;
    protected String exDepartureDeptStandardNumber;
    protected String exDepartureDeptName;
    protected String exDestinationCourierNumber;
    protected String exDestinationCourierName;
    protected String exDestinationDeptNumber;
    protected String exDestinationDeptStandardNumber;
    protected String exDestinationDeptName;
    protected List<WaybillCostInfo> waybillCostInfos;
    //添加运单状态字段
    protected String waybillStatus;
    
    

    public String getWaybillStatus() {
		return waybillStatus;
	}

	public void setWaybillStatus(String waybillStatus) {
		this.waybillStatus = waybillStatus;
	}

	/**
     * Gets the value of the number property.
     * 
     *     
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     *     
     */
    public void setNumber(String value) {
        this.number = value;
    }

    /**
     * Gets the value of the tranType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTranType() {
        return tranType;
    }

    /**
     * Sets the value of the tranType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTranType(String value) {
        this.tranType = value;
    }

    /**
     * Gets the value of the tranProperty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTranProperty() {
        return tranProperty;
    }

    /**
     * Sets the value of the tranProperty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTranProperty(String value) {
        this.tranProperty = value;
    }

    /**
     * Gets the value of the sender property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSender() {
        return sender;
    }

    /**
     * Sets the value of the sender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSender(String value) {
        this.sender = value;
    }

    /**
     * Gets the value of the senderPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderPhone() {
        return senderPhone;
    }

    /**
     * Sets the value of the senderPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderPhone(String value) {
        this.senderPhone = value;
    }

    /**
     * Gets the value of the senderMobile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderMobile() {
        return senderMobile;
    }

    /**
     * Sets the value of the senderMobile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderMobile(String value) {
        this.senderMobile = value;
    }

    /**
     * Gets the value of the departure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeparture() {
        return departure;
    }

    /**
     * Sets the value of the departure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeparture(String value) {
        this.departure = value;
    }

    /**
     * Gets the value of the senderAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderAddress() {
        return senderAddress;
    }

    /**
     * Sets the value of the senderAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderAddress(String value) {
        this.senderAddress = value;
    }

    /**
     * Gets the value of the senderAddressNote property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderAddressNote() {
        return senderAddressNote;
    }

    /**
     * Sets the value of the senderAddressNote property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderAddressNote(String value) {
        this.senderAddressNote = value;
    }

    /**
     * Gets the value of the consignee property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsignee() {
        return consignee;
    }

    /**
     * Sets the value of the consignee property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsignee(String value) {
        this.consignee = value;
    }

    /**
     * Gets the value of the consigneePhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneePhone() {
        return consigneePhone;
    }

    /**
     * Sets the value of the consigneePhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneePhone(String value) {
        this.consigneePhone = value;
    }

    /**
     * Gets the value of the consigneeMobile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeMobile() {
        return consigneeMobile;
    }

    /**
     * Sets the value of the consigneeMobile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeMobile(String value) {
        this.consigneeMobile = value;
    }

    /**
     * Gets the value of the destination property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the value of the destination property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestination(String value) {
        this.destination = value;
    }

    /**
     * Gets the value of the consigneeAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    /**
     * Sets the value of the consigneeAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeAddress(String value) {
        this.consigneeAddress = value;
    }

    /**
     * Gets the value of the consigneeAddressNote property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeAddressNote() {
        return consigneeAddressNote;
    }

    /**
     * Sets the value of the consigneeAddressNote property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeAddressNote(String value) {
        this.consigneeAddressNote = value;
    }

    /**
     * Gets the value of the goodName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoodName() {
        return goodName;
    }

    /**
     * Sets the value of the goodName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoodName(String value) {
        this.goodName = value;
    }

    /**
     * Gets the value of the pieces property.
     * 
     */
    public int getPieces() {
        return pieces;
    }

    /**
     * Sets the value of the pieces property.
     * 
     */
    public void setPieces(int value) {
        this.pieces = value;
    }

    /**
     * Gets the value of the weight property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getWeight() {
        return weight;
    }

    /**
     * Sets the value of the weight property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setWeight(Float value) {
        this.weight = value;
    }

    /**
     * Gets the value of the cubage property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getCubage() {
        return cubage;
    }

    /**
     * Sets the value of the cubage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setCubage(Float value) {
        this.cubage = value;
    }

    /**
     * Gets the value of the totalCharge property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    /**
     * Sets the value of the totalCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalCharge(BigDecimal value) {
        this.totalCharge = value;
    }

    /**
     * Gets the value of the payment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayment() {
        return payment;
    }

    /**
     * Sets the value of the payment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayment(String value) {
        this.payment = value;
    }

    /**
     * Gets the value of the preCharge property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPreCharge() {
        return preCharge;
    }

    /**
     * Sets the value of the preCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPreCharge(BigDecimal value) {
        this.preCharge = value;
    }

    /**
     * Gets the value of the arriveCharge property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getArriveCharge() {
        return arriveCharge;
    }

    /**
     * Sets the value of the arriveCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setArriveCharge(BigDecimal value) {
        this.arriveCharge = value;
    }

    /**
     * Gets the value of the refundType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefundType() {
        return refundType;
    }

    /**
     * Sets the value of the refundType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefundType(String value) {
        this.refundType = value;
    }

    /**
     * Gets the value of the refund property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRefund() {
        return refund;
    }

    /**
     * Sets the value of the refund property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRefund(BigDecimal value) {
        this.refund = value;
    }

    /**
     * Gets the value of the refundFee property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRefundFee() {
        return refundFee;
    }

    /**
     * Sets the value of the refundFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRefundFee(BigDecimal value) {
        this.refundFee = value;
    }

    /**
     * Gets the value of the deliveryType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryType() {
        return deliveryType;
    }

    /**
     * Sets the value of the deliveryType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryType(String value) {
        this.deliveryType = value;
    }

    /**
     * Gets the value of the consignCharge property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getConsignCharge() {
        return consignCharge;
    }

    /**
     * Sets the value of the consignCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setConsignCharge(BigDecimal value) {
        this.consignCharge = value;
    }

    /**
     * Gets the value of the deliveryCharge property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDeliveryCharge() {
        return deliveryCharge;
    }

    /**
     * Sets the value of the deliveryCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDeliveryCharge(BigDecimal value) {
        this.deliveryCharge = value;
    }

    /**
     * Gets the value of the signBackCharge property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSignBackCharge() {
        return signBackCharge;
    }

    /**
     * Sets the value of the signBackCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSignBackCharge(BigDecimal value) {
        this.signBackCharge = value;
    }

    /**
     * Gets the value of the pickCharge property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPickCharge() {
        return pickCharge;
    }

    /**
     * Sets the value of the pickCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPickCharge(BigDecimal value) {
        this.pickCharge = value;
    }

    /**
     * Gets the value of the laborRebate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLaborRebate() {
        return laborRebate;
    }

    /**
     * Sets the value of the laborRebate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLaborRebate(BigDecimal value) {
        this.laborRebate = value;
    }

    /**
     * Gets the value of the publishCharge property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPublishCharge() {
        return publishCharge;
    }

    /**
     * Sets the value of the publishCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPublishCharge(BigDecimal value) {
        this.publishCharge = value;
    }

    /**
     * Gets the value of the departureDeptName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartureDeptName() {
        return departureDeptName;
    }

    /**
     * Sets the value of the departureDeptName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartureDeptName(String value) {
        this.departureDeptName = value;
    }

    /**
     * Gets the value of the departureDeptNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartureDeptNumber() {
        return departureDeptNumber;
    }

    /**
     * Sets the value of the departureDeptNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartureDeptNumber(String value) {
        this.departureDeptNumber = value;
    }

    /**
     * Gets the value of the departureDeptAddr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartureDeptAddr() {
        return departureDeptAddr;
    }

    /**
     * Sets the value of the departureDeptAddr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartureDeptAddr(String value) {
        this.departureDeptAddr = value;
    }

    /**
     * Gets the value of the departureDeptPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartureDeptPhone() {
        return departureDeptPhone;
    }

    /**
     * Sets the value of the departureDeptPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartureDeptPhone(String value) {
        this.departureDeptPhone = value;
    }

    /**
     * Gets the value of the departureDeptFax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartureDeptFax() {
        return departureDeptFax;
    }

    /**
     * Sets the value of the departureDeptFax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartureDeptFax(String value) {
        this.departureDeptFax = value;
    }

    /**
     * Gets the value of the ladingStationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLadingStationName() {
        return ladingStationName;
    }

    /**
     * Sets the value of the ladingStationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLadingStationName(String value) {
        this.ladingStationName = value;
    }

    /**
     * Gets the value of the ladingStationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLadingStationNumber() {
        return ladingStationNumber;
    }

    /**
     * Sets the value of the ladingStationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLadingStationNumber(String value) {
        this.ladingStationNumber = value;
    }

    /**
     * Gets the value of the ladingStationAddr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLadingStationAddr() {
        return ladingStationAddr;
    }

    /**
     * Sets the value of the ladingStationAddr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLadingStationAddr(String value) {
        this.ladingStationAddr = value;
    }

    /**
     * Gets the value of the ladingStationPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLadingStationPhone() {
        return ladingStationPhone;
    }

    /**
     * Sets the value of the ladingStationPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLadingStationPhone(String value) {
        this.ladingStationPhone = value;
    }

    /**
     * Gets the value of the ladingStationFax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLadingStationFax() {
        return ladingStationFax;
    }

    /**
     * Sets the value of the ladingStationFax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLadingStationFax(String value) {
        this.ladingStationFax = value;
    }

    /**
     * Gets the value of the isSigned property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsSigned() {
        return isSigned;
    }

    /**
     * Sets the value of the isSigned property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsSigned(Boolean value) {
        this.isSigned = value;
    }

    /**
     * Gets the value of the isNormalSigned property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsNormalSigned() {
        return isNormalSigned;
    }

    /**
     * Sets the value of the isNormalSigned property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsNormalSigned(Boolean value) {
        this.isNormalSigned = value;
    }

    /**
     * Gets the value of the signRecorderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignRecorderId() {
        return signRecorderId;
    }

    /**
     * Sets the value of the signRecorderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignRecorderId(String value) {
        this.signRecorderId = value;
    }

    /**
     * Gets the value of the signedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Date getSignedDate() {
        return signedDate;
    }

    /**
     * Sets the value of the signedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSignedDate(Date value) {
        this.signedDate = value;
    }

    /**
     * Gets the value of the firstSignedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Date getFirstSignedDate() {
        return firstSignedDate;
    }

    /**
     * Sets the value of the firstSignedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFirstSignedDate(Date value) {
        this.firstSignedDate = value;
    }

    /**
     * Gets the value of the signedDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignedDesc() {
        return signedDesc;
    }

    /**
     * Sets the value of the signedDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignedDesc(String value) {
        this.signedDesc = value;
    }

    /**
     * Gets the value of the orderNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * Sets the value of the orderNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderNumber(String value) {
        this.orderNumber = value;
    }

    /**
     * Gets the value of the insuranceValue property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getInsuranceValue() {
        return insuranceValue;
    }

    /**
     * Sets the value of the insuranceValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setInsuranceValue(BigDecimal value) {
        this.insuranceValue = value;
    }

    /**
     * Gets the value of the insurance property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getInsurance() {
        return insurance;
    }

    /**
     * Sets the value of the insurance property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setInsurance(BigDecimal value) {
        this.insurance = value;
    }

    /**
     * Gets the value of the packing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPacking() {
        return packing;
    }

    /**
     * Sets the value of the packing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPacking(String value) {
        this.packing = value;
    }

    /**
     * Gets the value of the orderState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderState() {
        return orderState;
    }

    /**
     * Sets the value of the orderState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderState(String value) {
        this.orderState = value;
    }

    /**
     * Gets the value of the otherPayment property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOtherPayment() {
        return otherPayment;
    }

    /**
     * Sets the value of the otherPayment property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOtherPayment(BigDecimal value) {
        this.otherPayment = value;
    }

    /**
     * Gets the value of the tranDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTranDesc() {
        return tranDesc;
    }

    /**
     * Sets the value of the tranDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTranDesc(String value) {
        this.tranDesc = value;
    }

    /**
     * Gets the value of the senderNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderNumber() {
        return senderNumber;
    }

    /**
     * Sets the value of the senderNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderNumber(String value) {
        this.senderNumber = value;
    }

    /**
     * Gets the value of the consigneeNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeNumber() {
        return consigneeNumber;
    }

    /**
     * Sets the value of the consigneeNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeNumber(String value) {
        this.consigneeNumber = value;
    }

    /**
     * Gets the value of the isClear property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsClear() {
        return isClear;
    }

    /**
     * Sets the value of the isClear property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsClear(String value) {
        this.isClear = value;
    }

    /**
     * Gets the value of the signBackType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignBackType() {
        return signBackType;
    }

    /**
     * Sets the value of the signBackType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignBackType(String value) {
        this.signBackType = value;
    }

    /**
     * Gets the value of the transNotice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransNotice() {
        return transNotice;
    }

    /**
     * Sets the value of the transNotice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransNotice(String value) {
        this.transNotice = value;
    }

    /**
     * Gets the value of the sendTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * Sets the value of the sendTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSendTime(Date value) {
        this.sendTime = value;
    }

    /**
     * Gets the value of the receiveDeptName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiveDeptName() {
        return receiveDeptName;
    }

    /**
     * Sets the value of the receiveDeptName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiveDeptName(String value) {
        this.receiveDeptName = value;
    }

    /**
     * Gets the value of the receiveDeptNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiveDeptNumber() {
        return receiveDeptNumber;
    }

    /**
     * Sets the value of the receiveDeptNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiveDeptNumber(String value) {
        this.receiveDeptNumber = value;
    }

    /**
     * Gets the value of the stowageDept property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStowageDept() {
        return stowageDept;
    }

    /**
     * Sets the value of the stowageDept property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStowageDept(String value) {
        this.stowageDept = value;
    }

    /**
     * Gets the value of the senderCityCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderCityCode() {
        return senderCityCode;
    }

    /**
     * Sets the value of the senderCityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderCityCode(String value) {
        this.senderCityCode = value;
    }

    /**
     * Gets the value of the senderCityName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderCityName() {
        return senderCityName;
    }

    /**
     * Sets the value of the senderCityName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderCityName(String value) {
        this.senderCityName = value;
    }

    /**
     * Gets the value of the senderProvinceCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderProvinceCode() {
        return senderProvinceCode;
    }

    /**
     * Sets the value of the senderProvinceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderProvinceCode(String value) {
        this.senderProvinceCode = value;
    }

    /**
     * Gets the value of the senderProvinceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderProvinceName() {
        return senderProvinceName;
    }

    /**
     * Sets the value of the senderProvinceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderProvinceName(String value) {
        this.senderProvinceName = value;
    }

    /**
     * Gets the value of the consigneeCityCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeCityCode() {
        return consigneeCityCode;
    }

    /**
     * Sets the value of the consigneeCityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeCityCode(String value) {
        this.consigneeCityCode = value;
    }

    /**
     * Gets the value of the consigneeCityName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeCityName() {
        return consigneeCityName;
    }

    /**
     * Sets the value of the consigneeCityName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeCityName(String value) {
        this.consigneeCityName = value;
    }

    /**
     * Gets the value of the consigneeProvinceCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeProvinceCode() {
        return consigneeProvinceCode;
    }

    /**
     * Sets the value of the consigneeProvinceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeProvinceCode(String value) {
        this.consigneeProvinceCode = value;
    }

    /**
     * Gets the value of the consigneeProvinceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeProvinceName() {
        return consigneeProvinceName;
    }

    /**
     * Sets the value of the consigneeProvinceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeProvinceName(String value) {
        this.consigneeProvinceName = value;
    }

    /**
     * Gets the value of the isDoorToDoorPick property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDoorToDoorPick() {
        return isDoorToDoorPick;
    }

    /**
     * Sets the value of the isDoorToDoorPick property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDoorToDoorPick(Boolean value) {
        this.isDoorToDoorPick = value;
    }

    /**
     * Gets the value of the smsNoticeResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmsNoticeResult() {
        return smsNoticeResult;
    }

    /**
     * Sets the value of the smsNoticeResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmsNoticeResult(String value) {
        this.smsNoticeResult = value;
    }

    /**
     * Gets the value of the signBillBackWay property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignBillBackWay() {
        return signBillBackWay;
    }

    /**
     * Sets the value of the signBillBackWay property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignBillBackWay(String value) {
        this.signBillBackWay = value;
    }

    /**
     * Gets the value of the exDepartureRegionNubmer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExDepartureRegionNubmer() {
        return exDepartureRegionNubmer;
    }

    /**
     * Sets the value of the exDepartureRegionNubmer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExDepartureRegionNubmer(String value) {
        this.exDepartureRegionNubmer = value;
    }

    /**
     * Gets the value of the exDepartureRegionName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExDepartureRegionName() {
        return exDepartureRegionName;
    }

    /**
     * Sets the value of the exDepartureRegionName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExDepartureRegionName(String value) {
        this.exDepartureRegionName = value;
    }

    /**
     * Gets the value of the exDepartureRegionStandardNubmer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExDepartureRegionStandardNubmer() {
        return exDepartureRegionStandardNubmer;
    }

    /**
     * Sets the value of the exDepartureRegionStandardNubmer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExDepartureRegionStandardNubmer(String value) {
        this.exDepartureRegionStandardNubmer = value;
    }

    /**
     * Gets the value of the exDestinationRegionNubmer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExDestinationRegionNubmer() {
        return exDestinationRegionNubmer;
    }

    /**
     * Sets the value of the exDestinationRegionNubmer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExDestinationRegionNubmer(String value) {
        this.exDestinationRegionNubmer = value;
    }

    /**
     * Gets the value of the exDestinationRegionName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExDestinationRegionName() {
        return exDestinationRegionName;
    }

    /**
     * Sets the value of the exDestinationRegionName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExDestinationRegionName(String value) {
        this.exDestinationRegionName = value;
    }

    /**
     * Gets the value of the exDestinationRegionStandardNubmer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExDestinationRegionStandardNubmer() {
        return exDestinationRegionStandardNubmer;
    }

    /**
     * Sets the value of the exDestinationRegionStandardNubmer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExDestinationRegionStandardNubmer(String value) {
        this.exDestinationRegionStandardNubmer = value;
    }

    /**
     * Gets the value of the exDepartureCourierNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExDepartureCourierNumber() {
        return exDepartureCourierNumber;
    }

    /**
     * Sets the value of the exDepartureCourierNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExDepartureCourierNumber(String value) {
        this.exDepartureCourierNumber = value;
    }

    /**
     * Gets the value of the exDepartureCourierName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExDepartureCourierName() {
        return exDepartureCourierName;
    }

    /**
     * Sets the value of the exDepartureCourierName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExDepartureCourierName(String value) {
        this.exDepartureCourierName = value;
    }

    /**
     * Gets the value of the exDepartureDeptNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExDepartureDeptNumber() {
        return exDepartureDeptNumber;
    }

    /**
     * Sets the value of the exDepartureDeptNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExDepartureDeptNumber(String value) {
        this.exDepartureDeptNumber = value;
    }

    /**
     * Gets the value of the exDepartureDeptStandardNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExDepartureDeptStandardNumber() {
        return exDepartureDeptStandardNumber;
    }

    /**
     * Sets the value of the exDepartureDeptStandardNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExDepartureDeptStandardNumber(String value) {
        this.exDepartureDeptStandardNumber = value;
    }

    /**
     * Gets the value of the exDepartureDeptName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExDepartureDeptName() {
        return exDepartureDeptName;
    }

    /**
     * Sets the value of the exDepartureDeptName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExDepartureDeptName(String value) {
        this.exDepartureDeptName = value;
    }

    /**
     * Gets the value of the exDestinationCourierNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExDestinationCourierNumber() {
        return exDestinationCourierNumber;
    }

    /**
     * Sets the value of the exDestinationCourierNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExDestinationCourierNumber(String value) {
        this.exDestinationCourierNumber = value;
    }

    /**
     * Gets the value of the exDestinationCourierName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExDestinationCourierName() {
        return exDestinationCourierName;
    }

    /**
     * Sets the value of the exDestinationCourierName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExDestinationCourierName(String value) {
        this.exDestinationCourierName = value;
    }

    /**
     * Gets the value of the exDestinationDeptNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExDestinationDeptNumber() {
        return exDestinationDeptNumber;
    }

    /**
     * Sets the value of the exDestinationDeptNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExDestinationDeptNumber(String value) {
        this.exDestinationDeptNumber = value;
    }

    /**
     * Gets the value of the exDestinationDeptStandardNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExDestinationDeptStandardNumber() {
        return exDestinationDeptStandardNumber;
    }

    /**
     * Sets the value of the exDestinationDeptStandardNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExDestinationDeptStandardNumber(String value) {
        this.exDestinationDeptStandardNumber = value;
    }

    /**
     * Gets the value of the exDestinationDeptName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExDestinationDeptName() {
        return exDestinationDeptName;
    }

    /**
     * Sets the value of the exDestinationDeptName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExDestinationDeptName(String value) {
        this.exDestinationDeptName = value;
    }

    /**
     * Gets the value of the waybillCostInfos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the waybillCostInfos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWaybillCostInfos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WaybillCostInfo }
     * 
     * 
     */
    public List<WaybillCostInfo> getWaybillCostInfos() {
        if (waybillCostInfos == null) {
            waybillCostInfos = new ArrayList<WaybillCostInfo>();
        }
        return this.waybillCostInfos;
    }

}
