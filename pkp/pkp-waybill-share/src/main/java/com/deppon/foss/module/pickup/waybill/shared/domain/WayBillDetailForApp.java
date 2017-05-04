
package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 运单信息
 * 
 */
public class WayBillDetailForApp {
    private String number;
    private String tranType;
    private String tranProperty;
    private String sender;
    private String senderPhone;
    private String senderMobile;
    private String departure;
    private String senderAddress;
    private String senderAddressNote;
    private String consignee;
    private String consigneePhone;
    private String consigneeMobile;
    private String destination;
    private String consigneeAddress;
    private String consigneeAddressNote;
    private String goodName;
    private int pieces;
    private Float weight;
    private Float cubage;
    private BigDecimal totalCharge;
    private String payment;
    private BigDecimal preCharge;
    private BigDecimal arriveCharge;
    private String refundType;
    private BigDecimal refund;
    private BigDecimal refundFee;
    private String deliveryType;
    private BigDecimal consignCharge;
    private BigDecimal deliveryCharge;
    private BigDecimal signBackCharge;
    private BigDecimal pickCharge;
    private BigDecimal laborRebate;
    private BigDecimal publishCharge;
    private String departureDeptName;
    private String departureDeptNumber;
    private String departureDeptAddr;
    private String departureDeptPhone;
    private String departureDeptFax;
    private String ladingStationName;
    private String ladingStationNumber;
    private String ladingStationAddr;
    private String ladingStationPhone;
    private String ladingStationFax;
    private Boolean isSigned;
    private Boolean isNormalSigned;
    private String signRecorderId;
    private Date signedDate;
    private Date preArriveTime;
    private Date firstSignedDate;
    private String signedDesc;
    private String orderNumber;
    private BigDecimal insuranceValue;
    private BigDecimal insurance;
    private String packing;
    private String orderState;
    private BigDecimal otherPayment;
    private String tranDesc;
    private String senderNumber;
    private String consigneeNumber;
    private String isClear;
    private String signBackType;
    private String transNotice;
    private Date sendTime;
    private String receiveDeptName;
    private String receiveDeptNumber;
    private String stowageDept;
    private String senderCityCode;
    private String senderCityName;
    private String senderProvinceCode;
    private String senderProvinceName;
    private String consigneeCityCode;
    private String consigneeCityName;
    private String consigneeProvinceCode;
    private String consigneeProvinceName;
    private Boolean isDoorToDoorPick;
    private String smsNoticeResult;
    private String signBillBackWay;
    private String exDepartureRegionNubmer;
    private String exDepartureRegionName;
    private String exDepartureRegionStandardNubmer;
    private String exDestinationRegionNubmer;
    private String exDestinationRegionName;
    private String exDestinationRegionStandardNubmer;
    private String exDepartureCourierNumber;
    private String exDepartureCourierName;
    private String exDepartureDeptNumber;
    private String exDepartureDeptStandardNumber;
    private String exDepartureDeptName;
    private String exDestinationCourierNumber;
    private String exDestinationCourierName;
    private String exDestinationDeptNumber;
    private String exDestinationDeptStandardNumber;
    private String exDestinationDeptName;
    private List<WaybillCostInfoForApp> waybillCostInfos;
    private String departureDistrictCode;
    private String departureDistrictName;
    private String consigneeDistrictCode;
    private String consigneeDistrictName;
    private String pickupCentralized;
    
    
    private String createUserCode;
    private String createUserName;
    private String createOrgCode;
    private String createOrgName;
    private String driverCode;
    private String driverName;
    private String driverOrgCode;
    private String driverOrgName;
    private String modifyTime;
    private String modifyUserCode;
    private String modifyUserName;
    private String modifyOrgCode;
    private String modifyOrgName;
    private String goodsSize;
    private String isExpress;
    private String licensePlateNum;
    private String  createOrgPrincipalCode;
    private String  createOrgPrincipalName;
    private String  principalOrgName;
    private String  principalOrgCode;
    private String  expCreateTime;
    private String  expModifyTime;
    private String  expBillTime;
    private String  expArriveOrgCode;
    private String  expArriveOrgName;
  	private String  pendingType;
  	//DN201512110012 QMS差错上报新增字段
	//是否快递集中补录
  	private String isExpressFocus;
	//开单快递员
  	private String billingCourier;
    
	public String getExpCreateTime() {
		return expCreateTime;
	}
	public void setExpCreateTime(String expCreateTime) {
		this.expCreateTime = expCreateTime;
	}
	public String getExpModifyTime() {
		return expModifyTime;
	}
	public void setExpModifyTime(String expModifyTime) {
		this.expModifyTime = expModifyTime;
	}
	public String getExpBillTime() {
		return expBillTime;
	}
	public void setExpBillTime(String expBillTime) {
		this.expBillTime = expBillTime;
	}
	public String getPrincipalOrgName() {
		return principalOrgName;
	}
	public void setPrincipalOrgName(String principalOrgName) {
		this.principalOrgName = principalOrgName;
	}
	public String getPrincipalOrgCode() {
		return principalOrgCode;
	}
	public void setPrincipalOrgCode(String principalOrgCode) {
		this.principalOrgCode = principalOrgCode;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getTranType() {
		return tranType;
	}
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
	public String getTranProperty() {
		return tranProperty;
	}
	public void setTranProperty(String tranProperty) {
		this.tranProperty = tranProperty;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSenderPhone() {
		return senderPhone;
	}
	public void setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}
	public String getSenderMobile() {
		return senderMobile;
	}
	public void setSenderMobile(String senderMobile) {
		this.senderMobile = senderMobile;
	}
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	public String getSenderAddress() {
		return senderAddress;
	}
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}
	public String getSenderAddressNote() {
		return senderAddressNote;
	}
	public void setSenderAddressNote(String senderAddressNote) {
		this.senderAddressNote = senderAddressNote;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getConsigneePhone() {
		return consigneePhone;
	}
	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}
	public String getConsigneeMobile() {
		return consigneeMobile;
	}
	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getConsigneeAddress() {
		return consigneeAddress;
	}
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	public String getConsigneeAddressNote() {
		return consigneeAddressNote;
	}
	public void setConsigneeAddressNote(String consigneeAddressNote) {
		this.consigneeAddressNote = consigneeAddressNote;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public int getPieces() {
		return pieces;
	}
	public void setPieces(int pieces) {
		this.pieces = pieces;
	}
	public Float getWeight() {
		return weight;
	}
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	public Float getCubage() {
		return cubage;
	}
	public void setCubage(Float cubage) {
		this.cubage = cubage;
	}
	public BigDecimal getTotalCharge() {
		return totalCharge;
	}
	public void setTotalCharge(BigDecimal totalCharge) {
		this.totalCharge = totalCharge;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public BigDecimal getPreCharge() {
		return preCharge;
	}
	public void setPreCharge(BigDecimal preCharge) {
		this.preCharge = preCharge;
	}
	public BigDecimal getArriveCharge() {
		return arriveCharge;
	}
	public void setArriveCharge(BigDecimal arriveCharge) {
		this.arriveCharge = arriveCharge;
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
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public BigDecimal getConsignCharge() {
		return consignCharge;
	}
	public void setConsignCharge(BigDecimal consignCharge) {
		this.consignCharge = consignCharge;
	}
	public BigDecimal getDeliveryCharge() {
		return deliveryCharge;
	}
	public void setDeliveryCharge(BigDecimal deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}
	public BigDecimal getSignBackCharge() {
		return signBackCharge;
	}
	public void setSignBackCharge(BigDecimal signBackCharge) {
		this.signBackCharge = signBackCharge;
	}
	public BigDecimal getPickCharge() {
		return pickCharge;
	}
	public void setPickCharge(BigDecimal pickCharge) {
		this.pickCharge = pickCharge;
	}
	public BigDecimal getLaborRebate() {
		return laborRebate;
	}
	public void setLaborRebate(BigDecimal laborRebate) {
		this.laborRebate = laborRebate;
	}
	public BigDecimal getPublishCharge() {
		return publishCharge;
	}
	public void setPublishCharge(BigDecimal publishCharge) {
		this.publishCharge = publishCharge;
	}
	public String getDepartureDeptName() {
		return departureDeptName;
	}
	public void setDepartureDeptName(String departureDeptName) {
		this.departureDeptName = departureDeptName;
	}
	public String getDepartureDeptNumber() {
		return departureDeptNumber;
	}
	public void setDepartureDeptNumber(String departureDeptNumber) {
		this.departureDeptNumber = departureDeptNumber;
	}
	public String getDepartureDeptAddr() {
		return departureDeptAddr;
	}
	public void setDepartureDeptAddr(String departureDeptAddr) {
		this.departureDeptAddr = departureDeptAddr;
	}
	public String getDepartureDeptPhone() {
		return departureDeptPhone;
	}
	public void setDepartureDeptPhone(String departureDeptPhone) {
		this.departureDeptPhone = departureDeptPhone;
	}
	public String getDepartureDeptFax() {
		return departureDeptFax;
	}
	public void setDepartureDeptFax(String departureDeptFax) {
		this.departureDeptFax = departureDeptFax;
	}
	public String getLadingStationName() {
		return ladingStationName;
	}
	public void setLadingStationName(String ladingStationName) {
		this.ladingStationName = ladingStationName;
	}
	public String getLadingStationNumber() {
		return ladingStationNumber;
	}
	public void setLadingStationNumber(String ladingStationNumber) {
		this.ladingStationNumber = ladingStationNumber;
	}
	public String getLadingStationAddr() {
		return ladingStationAddr;
	}
	public void setLadingStationAddr(String ladingStationAddr) {
		this.ladingStationAddr = ladingStationAddr;
	}
	public String getLadingStationPhone() {
		return ladingStationPhone;
	}
	public void setLadingStationPhone(String ladingStationPhone) {
		this.ladingStationPhone = ladingStationPhone;
	}
	public String getLadingStationFax() {
		return ladingStationFax;
	}
	public void setLadingStationFax(String ladingStationFax) {
		this.ladingStationFax = ladingStationFax;
	}
	public Boolean getIsSigned() {
		return isSigned;
	}
	public void setIsSigned(Boolean isSigned) {
		this.isSigned = isSigned;
	}
	public Boolean getIsNormalSigned() {
		return isNormalSigned;
	}
	public void setIsNormalSigned(Boolean isNormalSigned) {
		this.isNormalSigned = isNormalSigned;
	}
	public String getSignRecorderId() {
		return signRecorderId;
	}
	public void setSignRecorderId(String signRecorderId) {
		this.signRecorderId = signRecorderId;
	}
	public Date getSignedDate() {
		return signedDate;
	}
	public void setSignedDate(Date signedDate) {
		this.signedDate = signedDate;
	}
	public Date getFirstSignedDate() {
		return firstSignedDate;
	}
	public void setFirstSignedDate(Date firstSignedDate) {
		this.firstSignedDate = firstSignedDate;
	}
	public String getSignedDesc() {
		return signedDesc;
	}
	public void setSignedDesc(String signedDesc) {
		this.signedDesc = signedDesc;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public BigDecimal getInsuranceValue() {
		return insuranceValue;
	}
	public void setInsuranceValue(BigDecimal insuranceValue) {
		this.insuranceValue = insuranceValue;
	}
	public BigDecimal getInsurance() {
		return insurance;
	}
	public void setInsurance(BigDecimal insurance) {
		this.insurance = insurance;
	}
	public String getPacking() {
		return packing;
	}
	public void setPacking(String packing) {
		this.packing = packing;
	}
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	public BigDecimal getOtherPayment() {
		return otherPayment;
	}
	public void setOtherPayment(BigDecimal otherPayment) {
		this.otherPayment = otherPayment;
	}
	public String getTranDesc() {
		return tranDesc;
	}
	public void setTranDesc(String tranDesc) {
		this.tranDesc = tranDesc;
	}
	public String getSenderNumber() {
		return senderNumber;
	}
	public void setSenderNumber(String senderNumber) {
		this.senderNumber = senderNumber;
	}
	public String getConsigneeNumber() {
		return consigneeNumber;
	}
	public void setConsigneeNumber(String consigneeNumber) {
		this.consigneeNumber = consigneeNumber;
	}
	public String getIsClear() {
		return isClear;
	}
	public void setIsClear(String isClear) {
		this.isClear = isClear;
	}
	public String getSignBackType() {
		return signBackType;
	}
	public void setSignBackType(String signBackType) {
		this.signBackType = signBackType;
	}
	public String getTransNotice() {
		return transNotice;
	}
	public void setTransNotice(String transNotice) {
		this.transNotice = transNotice;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getReceiveDeptName() {
		return receiveDeptName;
	}
	public void setReceiveDeptName(String receiveDeptName) {
		this.receiveDeptName = receiveDeptName;
	}
	public String getReceiveDeptNumber() {
		return receiveDeptNumber;
	}
	public void setReceiveDeptNumber(String receiveDeptNumber) {
		this.receiveDeptNumber = receiveDeptNumber;
	}
	public String getStowageDept() {
		return stowageDept;
	}
	public void setStowageDept(String stowageDept) {
		this.stowageDept = stowageDept;
	}
	public String getSenderCityCode() {
		return senderCityCode;
	}
	public void setSenderCityCode(String senderCityCode) {
		this.senderCityCode = senderCityCode;
	}
	public String getSenderCityName() {
		return senderCityName;
	}
	public void setSenderCityName(String senderCityName) {
		this.senderCityName = senderCityName;
	}
	public String getSenderProvinceCode() {
		return senderProvinceCode;
	}
	public void setSenderProvinceCode(String senderProvinceCode) {
		this.senderProvinceCode = senderProvinceCode;
	}
	public String getSenderProvinceName() {
		return senderProvinceName;
	}
	public void setSenderProvinceName(String senderProvinceName) {
		this.senderProvinceName = senderProvinceName;
	}
	public String getConsigneeCityCode() {
		return consigneeCityCode;
	}
	public void setConsigneeCityCode(String consigneeCityCode) {
		this.consigneeCityCode = consigneeCityCode;
	}
	public String getConsigneeCityName() {
		return consigneeCityName;
	}
	public void setConsigneeCityName(String consigneeCityName) {
		this.consigneeCityName = consigneeCityName;
	}
	public String getConsigneeProvinceCode() {
		return consigneeProvinceCode;
	}
	public void setConsigneeProvinceCode(String consigneeProvinceCode) {
		this.consigneeProvinceCode = consigneeProvinceCode;
	}
	public String getConsigneeProvinceName() {
		return consigneeProvinceName;
	}
	public void setConsigneeProvinceName(String consigneeProvinceName) {
		this.consigneeProvinceName = consigneeProvinceName;
	}
	public Boolean getIsDoorToDoorPick() {
		return isDoorToDoorPick;
	}
	public void setIsDoorToDoorPick(Boolean isDoorToDoorPick) {
		this.isDoorToDoorPick = isDoorToDoorPick;
	}
	public String getSmsNoticeResult() {
		return smsNoticeResult;
	}
	public void setSmsNoticeResult(String smsNoticeResult) {
		this.smsNoticeResult = smsNoticeResult;
	}
	public String getSignBillBackWay() {
		return signBillBackWay;
	}
	public void setSignBillBackWay(String signBillBackWay) {
		this.signBillBackWay = signBillBackWay;
	}
	public String getExDepartureRegionNubmer() {
		return exDepartureRegionNubmer;
	}
	public void setExDepartureRegionNubmer(String exDepartureRegionNubmer) {
		this.exDepartureRegionNubmer = exDepartureRegionNubmer;
	}
	public String getExDepartureRegionName() {
		return exDepartureRegionName;
	}
	public void setExDepartureRegionName(String exDepartureRegionName) {
		this.exDepartureRegionName = exDepartureRegionName;
	}
	public String getExDepartureRegionStandardNubmer() {
		return exDepartureRegionStandardNubmer;
	}
	public void setExDepartureRegionStandardNubmer(
			String exDepartureRegionStandardNubmer) {
		this.exDepartureRegionStandardNubmer = exDepartureRegionStandardNubmer;
	}
	public String getExDestinationRegionNubmer() {
		return exDestinationRegionNubmer;
	}
	public void setExDestinationRegionNubmer(String exDestinationRegionNubmer) {
		this.exDestinationRegionNubmer = exDestinationRegionNubmer;
	}
	public String getExDestinationRegionName() {
		return exDestinationRegionName;
	}
	public void setExDestinationRegionName(String exDestinationRegionName) {
		this.exDestinationRegionName = exDestinationRegionName;
	}
	public String getExDestinationRegionStandardNubmer() {
		return exDestinationRegionStandardNubmer;
	}
	public void setExDestinationRegionStandardNubmer(
			String exDestinationRegionStandardNubmer) {
		this.exDestinationRegionStandardNubmer = exDestinationRegionStandardNubmer;
	}
	public String getExDepartureCourierNumber() {
		return exDepartureCourierNumber;
	}
	public void setExDepartureCourierNumber(String exDepartureCourierNumber) {
		this.exDepartureCourierNumber = exDepartureCourierNumber;
	}
	public String getExDepartureCourierName() {
		return exDepartureCourierName;
	}
	public void setExDepartureCourierName(String exDepartureCourierName) {
		this.exDepartureCourierName = exDepartureCourierName;
	}
	public String getExDepartureDeptNumber() {
		return exDepartureDeptNumber;
	}
	public void setExDepartureDeptNumber(String exDepartureDeptNumber) {
		this.exDepartureDeptNumber = exDepartureDeptNumber;
	}
	public String getExDepartureDeptStandardNumber() {
		return exDepartureDeptStandardNumber;
	}
	public void setExDepartureDeptStandardNumber(
			String exDepartureDeptStandardNumber) {
		this.exDepartureDeptStandardNumber = exDepartureDeptStandardNumber;
	}
	public String getExDepartureDeptName() {
		return exDepartureDeptName;
	}
	public void setExDepartureDeptName(String exDepartureDeptName) {
		this.exDepartureDeptName = exDepartureDeptName;
	}
	public String getExDestinationCourierNumber() {
		return exDestinationCourierNumber;
	}
	public void setExDestinationCourierNumber(String exDestinationCourierNumber) {
		this.exDestinationCourierNumber = exDestinationCourierNumber;
	}
	public String getExDestinationCourierName() {
		return exDestinationCourierName;
	}
	public void setExDestinationCourierName(String exDestinationCourierName) {
		this.exDestinationCourierName = exDestinationCourierName;
	}
	public String getExDestinationDeptNumber() {
		return exDestinationDeptNumber;
	}
	public void setExDestinationDeptNumber(String exDestinationDeptNumber) {
		this.exDestinationDeptNumber = exDestinationDeptNumber;
	}
	public String getExDestinationDeptStandardNumber() {
		return exDestinationDeptStandardNumber;
	}
	public void setExDestinationDeptStandardNumber(
			String exDestinationDeptStandardNumber) {
		this.exDestinationDeptStandardNumber = exDestinationDeptStandardNumber;
	}
	public String getExDestinationDeptName() {
		return exDestinationDeptName;
	}
	public void setExDestinationDeptName(String exDestinationDeptName) {
		this.exDestinationDeptName = exDestinationDeptName;
	}
	public List<WaybillCostInfoForApp> getWaybillCostInfos() {
		return waybillCostInfos;
	}
	public void setWaybillCostInfos(List<WaybillCostInfoForApp> waybillCostInfos) {
		this.waybillCostInfos = waybillCostInfos;
	}
	public String getDepartureDistrictCode() {
		return departureDistrictCode;
	}
	public void setDepartureDistrictCode(String departureDistrictCode) {
		this.departureDistrictCode = departureDistrictCode;
	}
	public String getDepartureDistrictName() {
		return departureDistrictName;
	}
	public void setDepartureDistrictName(String departureDistrictName) {
		this.departureDistrictName = departureDistrictName;
	}
	public String getConsigneeDistrictCode() {
		return consigneeDistrictCode;
	}
	public void setConsigneeDistrictCode(String consigneeDistrictCode) {
		this.consigneeDistrictCode = consigneeDistrictCode;
	}
	public String getConsigneeDistrictName() {
		return consigneeDistrictName;
	}
	public void setConsigneeDistrictName(String consigneeDistrictName) {
		this.consigneeDistrictName = consigneeDistrictName;
	}
	public String getPickupCentralized() {
		return pickupCentralized;
	}
	public void setPickupCentralized(String pickupCentralized) {
		this.pickupCentralized = pickupCentralized;
	}
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	public String getDriverCode() {
		return driverCode;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getIsExpress() {
		return isExpress;
	}
	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	public String getModifyUserName() {
		return modifyUserName;
	}
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	public String getModifyOrgCode() {
		return modifyOrgCode;
	}
	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}
	public String getGoodsSize() {
		return goodsSize;
	}
	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}
	public String getModifyOrgName() {
		return modifyOrgName;
	}
	public void setModifyOrgName(String modifyOrgName) {
		this.modifyOrgName = modifyOrgName;
	}
	public String getCreateOrgName() {
		return createOrgName;
	}
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}
	public String getDriverOrgCode() {
		return driverOrgCode;
	}
	public void setDriverOrgCode(String driverOrgCode) {
		this.driverOrgCode = driverOrgCode;
	}
	public String getDriverOrgName() {
		return driverOrgName;
	}
	public void setDriverOrgName(String driverOrgName) {
		this.driverOrgName = driverOrgName;
	}
	public String getLicensePlateNum() {
		return licensePlateNum;
	}
	public void setLicensePlateNum(String licensePlateNum) {
		this.licensePlateNum = licensePlateNum;
	}
	public String getCreateOrgPrincipalCode() {
		return createOrgPrincipalCode;
	}
	public void setCreateOrgPrincipalCode(String createOrgPrincipalCode) {
		this.createOrgPrincipalCode = createOrgPrincipalCode;
	}
	public String getCreateOrgPrincipalName() {
		return createOrgPrincipalName;
	}
	public void setCreateOrgPrincipalName(String createOrgPrincipalName) {
		this.createOrgPrincipalName = createOrgPrincipalName;
	}
	public String getExpArriveOrgCode() {
		return expArriveOrgCode;
	}
	public void setExpArriveOrgCode(String expArriveOrgCode) {
		this.expArriveOrgCode = expArriveOrgCode;
	}
	public String getExpArriveOrgName() {
		return expArriveOrgName;
	}
	public void setExpArriveOrgName(String expArriveOrgName) {
		this.expArriveOrgName = expArriveOrgName;
	}
	public Date getPreArriveTime() {
		return preArriveTime;
	}
	public void setPreArriveTime(Date preArriveTime) {
		this.preArriveTime = preArriveTime;
	}
	public String getPendingType() {
		return pendingType;
	}
	public void setPendingType(String pendingType) {
		this.pendingType = pendingType;
	}
	public String getIsExpressFocus() {
		return isExpressFocus;
	}

	public void setIsExpressFocus(String isExpressFocus) {
		this.isExpressFocus = isExpressFocus;
	}

	public String getBillingCourier() {
		return billingCourier;
	}

	public void setBillingCourier(String billingCourier) {
		this.billingCourier = billingCourier;
	}
}
