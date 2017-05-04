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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/AbandonedGoodsDetailDto.java
 * 
 * FILE NAME        	: AbandonedGoodsDetailDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 综合派送查询条件DTO
 * 
 * @author ibm-meiying
 * @date 2013-06-24 下午3:41:41
 */
/**
 * @author 230532
 *
 */
public class ArriveDeliverQueryDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date billTimeStart;/*开单时间起*/
	private Date billTimeEnd;/*开单时间止*/
	private Date arriveTimeStart;/*到达时间起*/
	private Date arriveTimeEnd;/*到达时间止*/
	private Date inStockTimeStart;/*入库时间起*/
	private Date inStockTimeEnd;/*入库时间止*/
	private Date signTimeStart;/*签收时间起*/
	private Date signTimeEnd;/*签收时间止*/
	private Date operateTimeStart;/*送货时间起*/
	private Date operateTimeEnd;/*送货时间止*/
	private String deliverStatus;/*派送情况*/
	private String receiveCustomerContact;/*收货人姓名*/
	private String receiveCustomerAddress;/*地址*/
	private String receiveCustomerPhone;/*电话*/
	private String receiveCustomerMobilephone;/*手机*/
	private String waybillNo;/*运单号*/
	private String targetOrgCode;/*目的站*/
	private String returnbillStatus;/*返单状态*/
	private String returnbillType;/*返单类型*/
	private String inStockSituation;//入库情况
	private String productCode;/*运输性质*/
	private String transportType;/*运输类型*/
	private String receiveMethod;/*提货方式（全部、派送和自提）*/
	private String isSign;/*是否签收（已签收、未签收）*/
	private String endStockOrgName;
	/**
	 *  最终配载部门（判断是否为本部门）
	 */
	private String lastLoadOrgCode; 
	/**
	 * 最后库存code
	 */
	private String endStockOrgCode;
	
	/**
	 * 库区
	 */
	private String goodsAreaCode;
   /**
    * 零担、快递库区
    */
	private List<String> goodsAreaCodes;

	private String active;//是否有效
	private String situation;//签收情况（全部签收，正常签收，部分签收，异常签收）
	/**
	 * 派送单状态集合
	 */
	private List<String> deliverStatusList;
	private String settleStatus;//结清状态
	
	private String cargoName;//货物品名
	private String insuranceValue;//保价声明价值
 	private String insuranceFee;//保价费
	/**
	 * 到达联签收状态 -全部签收、部分签收、未签收
	 */
	private String signStatus;
 	/**
 	 * 签收操作问题
 	 */
 	private List<String> signOperateOrgCodes;
 	
 	/**
	 * 是否经过营业部(整车的等同于是否经过到达部门)
	 */
	private String isPassOwnDepartment;
	
	public Date getBillTimeStart() {
		return billTimeStart;
	}
	public void setBillTimeStart(Date billTimeStart) {
		this.billTimeStart = billTimeStart;
	}
	public Date getBillTimeEnd() {
		return billTimeEnd;
	}
	public void setBillTimeEnd(Date billTimeEnd) {
		this.billTimeEnd = billTimeEnd;
	}
	public Date getArriveTimeStart() {
		return arriveTimeStart;
	}
	public void setArriveTimeStart(Date arriveTimeStart) {
		this.arriveTimeStart = arriveTimeStart;
	}
	public Date getArriveTimeEnd() {
		return arriveTimeEnd;
	}
	public void setArriveTimeEnd(Date arriveTimeEnd) {
		this.arriveTimeEnd = arriveTimeEnd;
	}
	public Date getInStockTimeStart() {
		return inStockTimeStart;
	}
	public void setInStockTimeStart(Date inStockTimeStart) {
		this.inStockTimeStart = inStockTimeStart;
	}
	public Date getInStockTimeEnd() {
		return inStockTimeEnd;
	}
	public void setInStockTimeEnd(Date inStockTimeEnd) {
		this.inStockTimeEnd = inStockTimeEnd;
	}
	public Date getSignTimeStart() {
		return signTimeStart;
	}
	public void setSignTimeStart(Date signTimeStart) {
		this.signTimeStart = signTimeStart;
	}
	public Date getSignTimeEnd() {
		return signTimeEnd;
	}
	public void setSignTimeEnd(Date signTimeEnd) {
		this.signTimeEnd = signTimeEnd;
	}
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}
	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getTargetOrgCode() {
		return targetOrgCode;
	}
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}
	public String getReturnbillStatus() {
		return returnbillStatus;
	}
	public void setReturnbillStatus(String returnbillStatus) {
		this.returnbillStatus = returnbillStatus;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getTransportType() {
		return transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	public String getReceiveMethod() {
		return receiveMethod;
	}
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}
	public String getIsSign() {
		return isSign;
	}
	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}
	public Date getOperateTimeStart() {
		return operateTimeStart;
	}
	public void setOperateTimeStart(Date operateTimeStart) {
		this.operateTimeStart = operateTimeStart;
	}
	public Date getOperateTimeEnd() {
		return operateTimeEnd;
	}
	public void setOperateTimeEnd(Date operateTimeEnd) {
		this.operateTimeEnd = operateTimeEnd;
	}
	public String getDeliverStatus() {
		return deliverStatus;
	}
	public void setDeliverStatus(String deliverStatus) {
		this.deliverStatus = deliverStatus;
	}
	public String getInStockSituation() {
		return inStockSituation;
	}
	public void setInStockSituation(String inStockSituation) {
		this.inStockSituation = inStockSituation;
	}
	public String getReturnbillType() {
		return returnbillType;
	}
	public void setReturnbillType(String returnbillType) {
		this.returnbillType = returnbillType;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}
	public String getEndStockOrgCode() {
		return endStockOrgCode;
	}
	public void setEndStockOrgCode(String endStockOrgCode) {
		this.endStockOrgCode = endStockOrgCode;
	}
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}
	public List<String> getDeliverStatusList() {
		return deliverStatusList;
	}
	public void setDeliverStatusList(List<String> deliverStatusList) {
		this.deliverStatusList = deliverStatusList;
	}
	public String getEndStockOrgName() {
		return endStockOrgName;
	}
	public void setEndStockOrgName(String endStockOrgName) {
		this.endStockOrgName = endStockOrgName;
	}
	public String getSituation() {
		return situation;
	}
	public void setSituation(String situation) {
		this.situation = situation;
	}
	public String getCargoName() {
		return cargoName;
	}
	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}
	public String getInsuranceValue() {
		return insuranceValue;
	}
	public void setInsuranceValue(String insuranceValue) {
		this.insuranceValue = insuranceValue;
	}
	public String getInsuranceFee() {
		return insuranceFee;
	}
	public void setInsuranceFee(String insuranceFee) {
		this.insuranceFee = insuranceFee;
	}
	public List<String> getSignOperateOrgCodes() {
		return signOperateOrgCodes;
	}
	public void setSignOperateOrgCodes(List<String> signOperateOrgCodes) {
		this.signOperateOrgCodes = signOperateOrgCodes;
	}
	public String getIsPassOwnDepartment() {
		return isPassOwnDepartment;
	}
	public void setIsPassOwnDepartment(String isPassOwnDepartment) {
		this.isPassOwnDepartment = isPassOwnDepartment;
	}
	public String getSettleStatus() {
		return settleStatus;
	}
	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}
	public String getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}
	public List<String> getGoodsAreaCodes() {
	return goodsAreaCodes;
}
	public void setGoodsAreaCodes(List<String> goodsAreaCodes) {
	this.goodsAreaCodes = goodsAreaCodes;
}
}