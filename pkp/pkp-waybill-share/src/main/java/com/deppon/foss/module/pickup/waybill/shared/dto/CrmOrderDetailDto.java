/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/CrmOrderDetailDto.java
 * 
 * FILE NAME        	: CrmOrderDetailDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * CRM订单明细DTO
 * @author foss-sunrui
 * @date 2012-12-25 上午10:16:19
 */
public class CrmOrderDetailDto implements Serializable {

    /**
	 * The Constant serialVersionUID.
	 */
    private static final long serialVersionUID = -464488269765300385L;

    /**
	 * The hasten count.
	 */
    private int hastenCount;

    /**
	 * The last hasten time.
	 */
    private Date lastHastenTime;

    /**
	 * The contact man id.
	 */
    private String contactManId;

    /**
	 * The order number.
	 */
    private String orderNumber;

    /**
	 * The channel number.
	 */
    private String channelNumber;

    /**
	 * The shipper id.
	 */
    private String shipperId;

    /**
	 * The shipper number.
	 */
    private String shipperNumber;

    /**
	 * The shipper name.
	 */
    private String shipperName;

    /**
	 * The contact name.
	 */
    private String contactName;

    /**
	 * The contact mobile.
	 */
    private String contactMobile;

    /**
	 * The contact phone.
	 */
    private String contactPhone;

    /**
	 * The contact province.
	 */
    private String contactProvince;

    /**
	 * The contact city.
	 */
    private String contactCity;

    /**
	 * The contact area.
	 */
    private String contactArea;

    /**
	 * 发货人省编码.
	 */
    private String contactProvinceCode;

    /**
	 * 发货人市编码.
	 */
    private String contactCityCode;

    /**
	 * 发货人区县编码.
	 */
    private String contactAreaCode;

    /**
	 * The contact address.
	 */
    private String contactAddress;

    /**
	 * The is receive goods.
	 */
    private boolean isReceiveGoods;

    /**
	 * The begin accept time.
	 */
    private Date beginAcceptTime;

    /**
	 * The end accept time.
	 */
    private Date endAcceptTime;

    /**
	 * The receiver cust id.
	 */
    private String receiverCustId;

    /**
	 * The receiver cust number.
	 */
    private String receiverCustNumber;

    /**
	 * The receiver custcompany.
	 */
    private String receiverCustcompany;

    /**
	 * The receiver cust name.
	 */
    private String receiverCustName;

    /**
	 * The receiver cust mobile.
	 */
    private String receiverCustMobile;

    /**
	 * The receiver cust phone.
	 */
    private String receiverCustPhone;

    /**
	 * The receiver cust province.
	 */
    private String receiverCustProvince;

    /**
	 * The receiver cust city.
	 */
    private String receiverCustCity;

    /**
	 * The receiver cust area.
	 */
   private String receiverCustArea;
    
    /**
	 * 接货人省编码.
	 */
    private String receiverCustProvinceCode;

    /**
	 * 接货人市编码.
	 */
    private String receiverCustCityCode;

    /**
	 * 接货人区县编码.
	 */
    private String receiverCustAreaCode; 
	//收货人地址备注
    protected String receiverCustAddrRemark;
    //发货人地址备注
    protected String contactAddrRemark;
    
    /**
	 * The receiver cust address.
	 */
    private String receiverCustAddress;

    /**
	 * The is sendmessage.
	 */
    private boolean isSendmessage;

    /**
	 * The receiving to point.
	 */
    private String receivingToPoint;

    /**
	 * The receiving to point name.
	 */
    private String receivingToPointName;

    /**
	 * The transport mode.
	 */
    private String transportMode;

    /**
	 * The goods name.
	 */
    private String goodsName;

    /**
	 * The packing.
	 */
    private String packing;

    /**
	 * The goods type.
	 */
    private String goodsType;

    /**
	 * The goods number.
	 */
    private int goodsNumber;

    /**
	 * The total weight.
	 */
    private double totalWeight;

    /**
	 * The total volume.
	 */
    private double totalVolume;

    /**
	 * The payment type.
	 */
    private String paymentType;

    /**
	 * The channel type.
	 */
    private String channelType;

    /**
	 * The channel cust id.
	 */
    private String channelCustId;

    /**
	 * The delivery mode.
	 */
    private String deliveryMode;

    /**
	 * The recive loan type.
	 */
    private String reciveLoanType;

    /**
	 * The revice money amount.
	 */
    private BigDecimal reviceMoneyAmount;

    /**
	 * The insured amount.
	 */
    private BigDecimal insuredAmount;

    /**
	 * The order time.
	 */
    private Date orderTime;

    /**
	 * The start station.
	 */
    private String startStation;

    /**
	 * The start station name.
	 */
    private String startStationName;

    /**
	 * The accept dept.
	 */
    private String acceptDept;

    /**
	 * The accept dept name.
	 */
    private String acceptDeptName;

    /**
	 * The order status.
	 */
    private String orderStatus;

    /**
	 * The deal person.
	 */
    private String dealPerson;

    /**
	 * The order accept time.
	 */
    private Date orderAcceptTime;

    /**
	 * The receiver.
	 */
    private String receiver;

    /**
	 * The accepter person mobile.
	 */
    private String accepterPersonMobile;

    /**
	 * The waybill number.
	 */
    private String waybillNumber;

    /**
	 * The resource.
	 */
    private String resource;

    /**
	 * The trans note.
	 */
    private String transNote;

    /**
	 * The return bill type.
	 */
    private String returnBillType;

    /**
	 * The order person.
	 */
    private String orderPerson;

    /**
	 * The feedback info.
	 */
    private String feedbackInfo;

    /**
	 * The online name.
	 */
    private String onlineName;

    /**
	 * The member type.
	 */
    private String memberType;

    /**
     * 运单状态  （有效 终止 作废）
     */
	private String actualFreightStatus;
	
	/**
	 * 优惠券编码
	 */
	private String couponNumber;
	
	
	
	
	
	
	public String getCouponNumber() {
		return couponNumber;
	}


	public void setCouponNumber(String couponNumber) {
		this.couponNumber = couponNumber;
	}


	/**
	 * @return the actualFreightStatus
	 */
	public String getActualFreightStatus() {
		return actualFreightStatus;
	}


	/**
	 * @param actualFreightStatus the actualFreightStatus to set
	 */
	public void setActualFreightStatus(String actualFreightStatus) {
		this.actualFreightStatus = actualFreightStatus;
	}


	/**
	 * @return the hastenCount .
	 */
	public int getHastenCount() {
		return hastenCount;
	}

	
	/**
	 *@param hastenCount the hastenCount to set.
	 */
	public void setHastenCount(int hastenCount) {
		this.hastenCount = hastenCount;
	}

	
	/**
	 * @return the lastHastenTime .
	 */
	public Date getLastHastenTime() {
		return lastHastenTime;
	}

	
	/**
	 *@param lastHastenTime the lastHastenTime to set.
	 */
	public void setLastHastenTime(Date lastHastenTime) {
		this.lastHastenTime = lastHastenTime;
	}

	
	/**
	 * @return the contactManId .
	 */
	public String getContactManId() {
		return contactManId;
	}

	
	/**
	 *@param contactManId the contactManId to set.
	 */
	public void setContactManId(String contactManId) {
		this.contactManId = contactManId;
	}

	
	/**
	 * @return the orderNumber .
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	
	/**
	 *@param orderNumber the orderNumber to set.
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	
	/**
	 * @return the channelNumber .
	 */
	public String getChannelNumber() {
		return channelNumber;
	}

	
	/**
	 *@param channelNumber the channelNumber to set.
	 */
	public void setChannelNumber(String channelNumber) {
		this.channelNumber = channelNumber;
	}

	
	/**
	 * @return the shipperId .
	 */
	public String getShipperId() {
		return shipperId;
	}

	
	/**
	 *@param shipperId the shipperId to set.
	 */
	public void setShipperId(String shipperId) {
		this.shipperId = shipperId;
	}

	
	/**
	 * @return the shipperNumber .
	 */
	public String getShipperNumber() {
		return shipperNumber;
	}

	
	/**
	 *@param shipperNumber the shipperNumber to set.
	 */
	public void setShipperNumber(String shipperNumber) {
		this.shipperNumber = shipperNumber;
	}

	
	/**
	 * @return the shipperName .
	 */
	public String getShipperName() {
		return shipperName;
	}

	
	/**
	 *@param shipperName the shipperName to set.
	 */
	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}

	
	/**
	 * @return the contactName .
	 */
	public String getContactName() {
		return contactName;
	}

	
	/**
	 *@param contactName the contactName to set.
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	
	/**
	 * @return the contactMobile .
	 */
	public String getContactMobile() {
		return contactMobile;
	}

	
	/**
	 *@param contactMobile the contactMobile to set.
	 */
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	
	/**
	 * @return the contactPhone .
	 */
	public String getContactPhone() {
		return contactPhone;
	}

	
	/**
	 *@param contactPhone the contactPhone to set.
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	
	/**
	 * @return the contactProvince .
	 */
	public String getContactProvince() {
		return contactProvince;
	}

	
	/**
	 *@param contactProvince the contactProvince to set.
	 */
	public void setContactProvince(String contactProvince) {
		this.contactProvince = contactProvince;
	}

	
	/**
	 * @return the contactCity .
	 */
	public String getContactCity() {
		return contactCity;
	}

	
	/**
	 *@param contactCity the contactCity to set.
	 */
	public void setContactCity(String contactCity) {
		this.contactCity = contactCity;
	}

	
	/**
	 * @return the contactArea .
	 */
	public String getContactArea() {
		return contactArea;
	}

	
	/**
	 *@param contactArea the contactArea to set.
	 */
	public void setContactArea(String contactArea) {
		this.contactArea = contactArea;
	}

	
	/**
	 * @return the contactAddress .
	 */
	public String getContactAddress() {
		return contactAddress;
	}

	
	/**
	 *@param contactAddress the contactAddress to set.
	 */
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	
	/**
	 * @return the isReceiveGoods .
	 */
	public boolean isReceiveGoods() {
		return isReceiveGoods;
	}

	
	/**
	 *@param isReceiveGoods the isReceiveGoods to set.
	 */
	public void setReceiveGoods(boolean isReceiveGoods) {
		this.isReceiveGoods = isReceiveGoods;
	}

	
	/**
	 * @return the beginAcceptTime .
	 */
	public Date getBeginAcceptTime() {
		return beginAcceptTime;
	}

	
	/**
	 *@param beginAcceptTime the beginAcceptTime to set.
	 */
	public void setBeginAcceptTime(Date beginAcceptTime) {
		this.beginAcceptTime = beginAcceptTime;
	}

	
	/**
	 * @return the endAcceptTime .
	 */
	public Date getEndAcceptTime() {
		return endAcceptTime;
	}

	
	/**
	 *@param endAcceptTime the endAcceptTime to set.
	 */
	public void setEndAcceptTime(Date endAcceptTime) {
		this.endAcceptTime = endAcceptTime;
	}

	
	/**
	 * @return the receiverCustId .
	 */
	public String getReceiverCustId() {
		return receiverCustId;
	}

	
	/**
	 *@param receiverCustId the receiverCustId to set.
	 */
	public void setReceiverCustId(String receiverCustId) {
		this.receiverCustId = receiverCustId;
	}

	
	/**
	 * @return the receiverCustNumber .
	 */
	public String getReceiverCustNumber() {
		return receiverCustNumber;
	}

	
	/**
	 *@param receiverCustNumber the receiverCustNumber to set.
	 */
	public void setReceiverCustNumber(String receiverCustNumber) {
		this.receiverCustNumber = receiverCustNumber;
	}

	
	/**
	 * @return the receiverCustcompany .
	 */
	public String getReceiverCustcompany() {
		return receiverCustcompany;
	}

	
	/**
	 *@param receiverCustcompany the receiverCustcompany to set.
	 */
	public void setReceiverCustcompany(String receiverCustcompany) {
		this.receiverCustcompany = receiverCustcompany;
	}

	
	/**
	 * @return the receiverCustName .
	 */
	public String getReceiverCustName() {
		return receiverCustName;
	}

	
	/**
	 *@param receiverCustName the receiverCustName to set.
	 */
	public void setReceiverCustName(String receiverCustName) {
		this.receiverCustName = receiverCustName;
	}

	
	/**
	 * @return the receiverCustMobile .
	 */
	public String getReceiverCustMobile() {
		return receiverCustMobile;
	}

	
	/**
	 *@param receiverCustMobile the receiverCustMobile to set.
	 */
	public void setReceiverCustMobile(String receiverCustMobile) {
		this.receiverCustMobile = receiverCustMobile;
	}

	
	/**
	 * @return the receiverCustPhone .
	 */
	public String getReceiverCustPhone() {
		return receiverCustPhone;
	}

	
	/**
	 *@param receiverCustPhone the receiverCustPhone to set.
	 */
	public void setReceiverCustPhone(String receiverCustPhone) {
		this.receiverCustPhone = receiverCustPhone;
	}

	
	/**
	 * @return the receiverCustProvince .
	 */
	public String getReceiverCustProvince() {
		return receiverCustProvince;
	}

	
	/**
	 *@param receiverCustProvince the receiverCustProvince to set.
	 */
	public void setReceiverCustProvince(String receiverCustProvince) {
		this.receiverCustProvince = receiverCustProvince;
	}

	
	/**
	 * @return the receiverCustCity .
	 */
	public String getReceiverCustCity() {
		return receiverCustCity;
	}

	
	/**
	 *@param receiverCustCity the receiverCustCity to set.
	 */
	public void setReceiverCustCity(String receiverCustCity) {
		this.receiverCustCity = receiverCustCity;
	}

	
	/**
	 * @return the receiverCustArea .
	 */
	public String getReceiverCustArea() {
		return receiverCustArea;
	}

	
	/**
	 *@param receiverCustArea the receiverCustArea to set.
	 */
	public void setReceiverCustArea(String receiverCustArea) {
		this.receiverCustArea = receiverCustArea;
	}

	
	/**
	 * @return the receiverCustAddress .
	 */
	public String getReceiverCustAddress() {
		return receiverCustAddress;
	}

	
	/**
	 *@param receiverCustAddress the receiverCustAddress to set.
	 */
	public void setReceiverCustAddress(String receiverCustAddress) {
		this.receiverCustAddress = receiverCustAddress;
	}

	
	/**
	 * @return the isSendmessage .
	 */
	public boolean isSendmessage() {
		return isSendmessage;
	}

	
	/**
	 *@param isSendmessage the isSendmessage to set.
	 */
	public void setSendmessage(boolean isSendmessage) {
		this.isSendmessage = isSendmessage;
	}

	
	/**
	 * @return the receivingToPoint .
	 */
	public String getReceivingToPoint() {
		return receivingToPoint;
	}

	
	/**
	 *@param receivingToPoint the receivingToPoint to set.
	 */
	public void setReceivingToPoint(String receivingToPoint) {
		this.receivingToPoint = receivingToPoint;
	}

	
	/**
	 * @return the receivingToPointName .
	 */
	public String getReceivingToPointName() {
		return receivingToPointName;
	}

	
	/**
	 *@param receivingToPointName the receivingToPointName to set.
	 */
	public void setReceivingToPointName(String receivingToPointName) {
		this.receivingToPointName = receivingToPointName;
	}

	
	/**
	 * @return the transportMode .
	 */
	public String getTransportMode() {
		return transportMode;
	}

	
	/**
	 *@param transportMode the transportMode to set.
	 */
	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}

	
	/**
	 * @return the goodsName .
	 */
	public String getGoodsName() {
		return goodsName;
	}

	
	/**
	 *@param goodsName the goodsName to set.
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	
	/**
	 * @return the packing .
	 */
	public String getPacking() {
		return packing;
	}

	
	/**
	 *@param packing the packing to set.
	 */
	public void setPacking(String packing) {
		this.packing = packing;
	}

	
	/**
	 * @return the goodsType .
	 */
	public String getGoodsType() {
		return goodsType;
	}

	
	/**
	 *@param goodsType the goodsType to set.
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	
	/**
	 * @return the goodsNumber .
	 */
	public int getGoodsNumber() {
		return goodsNumber;
	}

	
	/**
	 *@param goodsNumber the goodsNumber to set.
	 */
	public void setGoodsNumber(int goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	
	/**
	 * @return the totalWeight .
	 */
	public double getTotalWeight() {
		return totalWeight;
	}

	
	/**
	 *@param totalWeight the totalWeight to set.
	 */
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}

	
	/**
	 * @return the totalVolume .
	 */
	public double getTotalVolume() {
		return totalVolume;
	}

	
	/**
	 *@param totalVolume the totalVolume to set.
	 */
	public void setTotalVolume(double totalVolume) {
		this.totalVolume = totalVolume;
	}

	
	/**
	 * @return the paymentType .
	 */
	public String getPaymentType() {
		return paymentType;
	}

	
	/**
	 *@param paymentType the paymentType to set.
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	
	/**
	 * @return the channelType .
	 */
	public String getChannelType() {
		return channelType;
	}

	
	/**
	 *@param channelType the channelType to set.
	 */
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	
	/**
	 * @return the channelCustId .
	 */
	public String getChannelCustId() {
		return channelCustId;
	}

	
	/**
	 *@param channelCustId the channelCustId to set.
	 */
	public void setChannelCustId(String channelCustId) {
		this.channelCustId = channelCustId;
	}

	
	/**
	 * @return the deliveryMode .
	 */
	public String getDeliveryMode() {
		return deliveryMode;
	}

	
	/**
	 *@param deliveryMode the deliveryMode to set.
	 */
	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	
	/**
	 * @return the reciveLoanType .
	 */
	public String getReciveLoanType() {
		return reciveLoanType;
	}

	
	/**
	 *@param reciveLoanType the reciveLoanType to set.
	 */
	public void setReciveLoanType(String reciveLoanType) {
		this.reciveLoanType = reciveLoanType;
	}

	
	/**
	 * @return the reviceMoneyAmount .
	 */
	public BigDecimal getReviceMoneyAmount() {
		return reviceMoneyAmount;
	}

	
	/**
	 *@param reviceMoneyAmount the reviceMoneyAmount to set.
	 */
	public void setReviceMoneyAmount(BigDecimal reviceMoneyAmount) {
		this.reviceMoneyAmount = reviceMoneyAmount;
	}

	
	/**
	 * @return the insuredAmount .
	 */
	public BigDecimal getInsuredAmount() {
		return insuredAmount;
	}

	
	/**
	 *@param insuredAmount the insuredAmount to set.
	 */
	public void setInsuredAmount(BigDecimal insuredAmount) {
		this.insuredAmount = insuredAmount;
	}

	
	/**
	 * @return the orderTime .
	 */
	public Date getOrderTime() {
		return orderTime;
	}

	
	/**
	 *@param orderTime the orderTime to set.
	 */
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	
	/**
	 * @return the startStation .
	 */
	public String getStartStation() {
		return startStation;
	}

	
	/**
	 *@param startStation the startStation to set.
	 */
	public void setStartStation(String startStation) {
		this.startStation = startStation;
	}

	
	/**
	 * @return the startStationName .
	 */
	public String getStartStationName() {
		return startStationName;
	}

	
	/**
	 *@param startStationName the startStationName to set.
	 */
	public void setStartStationName(String startStationName) {
		this.startStationName = startStationName;
	}

	
	/**
	 * @return the acceptDept .
	 */
	public String getAcceptDept() {
		return acceptDept;
	}

	
	/**
	 *@param acceptDept the acceptDept to set.
	 */
	public void setAcceptDept(String acceptDept) {
		this.acceptDept = acceptDept;
	}

	
	/**
	 * @return the acceptDeptName .
	 */
	public String getAcceptDeptName() {
		return acceptDeptName;
	}

	
	/**
	 *@param acceptDeptName the acceptDeptName to set.
	 */
	public void setAcceptDeptName(String acceptDeptName) {
		this.acceptDeptName = acceptDeptName;
	}

	
	/**
	 * @return the orderStatus .
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	
	/**
	 *@param orderStatus the orderStatus to set.
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	
	/**
	 * @return the dealPerson .
	 */
	public String getDealPerson() {
		return dealPerson;
	}

	
	/**
	 *@param dealPerson the dealPerson to set.
	 */
	public void setDealPerson(String dealPerson) {
		this.dealPerson = dealPerson;
	}

	
	/**
	 * @return the orderAcceptTime .
	 */
	public Date getOrderAcceptTime() {
		return orderAcceptTime;
	}

	
	/**
	 *@param orderAcceptTime the orderAcceptTime to set.
	 */
	public void setOrderAcceptTime(Date orderAcceptTime) {
		this.orderAcceptTime = orderAcceptTime;
	}

	
	/**
	 * @return the receiver .
	 */
	public String getReceiver() {
		return receiver;
	}

	
	/**
	 *@param receiver the receiver to set.
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	
	/**
	 * @return the accepterPersonMobile .
	 */
	public String getAccepterPersonMobile() {
		return accepterPersonMobile;
	}

	
	/**
	 *@param accepterPersonMobile the accepterPersonMobile to set.
	 */
	public void setAccepterPersonMobile(String accepterPersonMobile) {
		this.accepterPersonMobile = accepterPersonMobile;
	}

	
	/**
	 * @return the waybillNumber .
	 */
	public String getWaybillNumber() {
		return waybillNumber;
	}

	
	/**
	 *@param waybillNumber the waybillNumber to set.
	 */
	public void setWaybillNumber(String waybillNumber) {
		this.waybillNumber = waybillNumber;
	}

	
	/**
	 * @return the resource .
	 */
	public String getResource() {
		return resource;
	}

	
	/**
	 *@param resource the resource to set.
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}

	
	/**
	 * @return the transNote .
	 */
	public String getTransNote() {
		return transNote;
	}

	
	/**
	 *@param transNote the transNote to set.
	 */
	public void setTransNote(String transNote) {
		this.transNote = transNote;
	}

	
	/**
	 * @return the returnBillType .
	 */
	public String getReturnBillType() {
		return returnBillType;
	}

	
	/**
	 *@param returnBillType the returnBillType to set.
	 */
	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}

	
	/**
	 * @return the orderPerson .
	 */
	public String getOrderPerson() {
		return orderPerson;
	}

	
	/**
	 *@param orderPerson the orderPerson to set.
	 */
	public void setOrderPerson(String orderPerson) {
		this.orderPerson = orderPerson;
	}

	
	/**
	 * @return the feedbackInfo .
	 */
	public String getFeedbackInfo() {
		return feedbackInfo;
	}

	
	/**
	 *@param feedbackInfo the feedbackInfo to set.
	 */
	public void setFeedbackInfo(String feedbackInfo) {
		this.feedbackInfo = feedbackInfo;
	}

	
	/**
	 * @return the onlineName .
	 */
	public String getOnlineName() {
		return onlineName;
	}

	
	/**
	 *@param onlineName the onlineName to set.
	 */
	public void setOnlineName(String onlineName) {
		this.onlineName = onlineName;
	}

	
	/**
	 * @return the memberType .
	 */
	public String getMemberType() {
		return memberType;
	}

	
	/**
	 *@param memberType the memberType to set.
	 */
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}


	public String getContactProvinceCode() {
		return contactProvinceCode;
	}


	public void setContactProvinceCode(String contactProvinceCode) {
		this.contactProvinceCode = contactProvinceCode;
	}


	public String getContactCityCode() {
		return contactCityCode;
	}


	public void setContactCityCode(String contactCityCode) {
		this.contactCityCode = contactCityCode;
	}


	public String getContactAreaCode() {
		return contactAreaCode;
	}


	public void setContactAreaCode(String contactAreaCode) {
		this.contactAreaCode = contactAreaCode;
	}


	public String getReceiverCustProvinceCode() {
		return receiverCustProvinceCode;
	}


	public void setReceiverCustProvinceCode(String receiverCustProvinceCode) {
		this.receiverCustProvinceCode = receiverCustProvinceCode;
	}


	public String getReceiverCustCityCode() {
		return receiverCustCityCode;
	}


	public void setReceiverCustCityCode(String receiverCustCityCode) {
		this.receiverCustCityCode = receiverCustCityCode;
	}


	public String getReceiverCustAreaCode() {
		return receiverCustAreaCode;
	}


	public void setReceiverCustAreaCode(String receiverCustAreaCode) {
		this.receiverCustAreaCode = receiverCustAreaCode;
	}

	/**
	  * 是否电子发票
	  * @author:218371-foss-zhaoyanjun
	  * @date:2014-10-21下午14:12
	  */
	private String isElectronicInvoice;
	
	/**
	 * 发票手机号
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-10-21下午14:12
	 */
	private String invoiceMobilePhone;
	
	/**
	 * 发票发送邮箱
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-10-21下午14:23
	 */
	private String email;

	public String getIsElectronicInvoice() {
		return isElectronicInvoice;
	}

	public void setIsElectronicInvoice(String isElectronicInvoice) {
		this.isElectronicInvoice = isElectronicInvoice;
	}

	public String getInvoiceMobilePhone() {
		return invoiceMobilePhone;
	}

	public void setInvoiceMobilePhone(String invoiceMobilePhone) {
		this.invoiceMobilePhone = invoiceMobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getReceiverCustAddrRemark() {
		return receiverCustAddrRemark;
	}


	public void setReceiverCustAddrRemark(String receiverCustAddrRemark) {
		this.receiverCustAddrRemark = receiverCustAddrRemark;
	}


	public String getContactAddrRemark() {
		return contactAddrRemark;
	}


	public void setContactAddrRemark(String contactAddrRemark) {
		this.contactAddrRemark = contactAddrRemark;
	}
    
	/**
	 * Dmana-9885运费
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-02-02 下午16:49
	 */
	private BigDecimal crmTransportFee;
	public BigDecimal getCrmTransportFee() {
		return crmTransportFee;
	}


	public void setCrmTransportFee(BigDecimal crmTransportFee) {
		this.crmTransportFee = crmTransportFee;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

//请整合时保留此段代码
//	/**
//	 * Dmana-9885重量
//	 * @author:218371-foss-zhaoyanjun
//	 * @date:2015-02-02 下午16:49
//	 */
//	private BigDecimal CRMWeight;
//	
//	/**
//	 * Dmana-9885体积
//	 * @author:218371-foss-zhaoyanjun
//	 * @date:2015-02-02 下午16:49
//	 */
//	private BigDecimal CRMVolume;
//
//	public BigDecimal getCRMWeight() {
//		return CRMWeight;
//	}
//
//
//	public void setCRMWeight(BigDecimal cRMWeight) {
//		CRMWeight = CRMWeight;
//	}
//
//
//	public BigDecimal getCRMVolume() {
//		return CRMVolume;
//	}
//
//
//	public void setCRMVolume(BigDecimal cRMVolume) {
//		CRMVolume = CRMVolume;
//	}
}