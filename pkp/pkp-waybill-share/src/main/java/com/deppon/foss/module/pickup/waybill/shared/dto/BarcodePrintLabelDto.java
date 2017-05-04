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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/BarcodePrintLabelDto.java
 * 
 * FILE NAME        	: BarcodePrintLabelDto.java
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

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * (标签打印用到的VO)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-jiangfei,date:2012-11-5 下午12:29:11,
 * </p>
 * 
 * @author foss-jiangfei
 * @date 2012-11-5 下午12:29:11
 * @since
 * @version
 */
public class BarcodePrintLabelDto extends BaseEntity {
	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = -1921551036308388478L;
	//350909     郭倩云       零担轻货上分拣取得是城市简称
	private String simpleLeaveCity;
	//350909     郭倩云       零担轻货上分拣结果
	private String sortingResult;
	// 350909      郭倩云        倒数第二外场编码 deptno
	private String lastSecondTransCenterNo;
	// 350909      郭倩云        倒数第二外场ID
    private String lastSecondFinaloutFieldId;
	// 350909      郭倩云        倒数第二外场简称
    private String lastSecondTransCenterCity;
    // 350909      郭倩云        倒数第三外场编码 deptno
 	private String lastThirdTransCenterNo;
 	// 350909      郭倩云        倒数第三外场ID
     private String lastThirdFinaloutFieldId;
 	// 350909      郭倩云        倒数第三外场简称
     private String lastThirdTransCenterCity;
    // 350909      郭倩云        最终外场简称(例如武汉转运场简称武汉)
     private String lastFirstTransCenterCity;
	/**
	 * 运单实体
	 */
	private WaybillEntity waybillBean;

	// * 地区1
	private String addr1;

	// * 地区2
	private String addr2;

	// * 地区3
	private String addr3;

	// * 地区4
	private String addr4;

	// * 编号1
	private String location1;

	// * 编号2
	private String location2;

	// * 编号3
	private String location3;

	// * 编号4

	private String location4;

	// 当前用户
	private String optuserNum;

	// 运单号
	private String waybillNumber;

	// 打印流水号
	private String printSerialnos;

	// 始发站 出发城市
	private String leavecity;
	
	//第二城市外场名称
	private String secLoadOrgName;

	// 目的站
	private String destination;

	// 运输类型 是否偏线
	private String isAgent;

	// 目的站编码
	private String destinationCode;
	
	//提货网点编码
	private String customerPickupOrgCode;

	// 最终外场编码 deptno
	private String lastTransCenterNo;

	// 最终外场ID
	private String finaloutfieldid;

	// 最终外场简称
	private String lastTransCenterCity;

	// 重量
	private String weight;

	// 总数量 件数
	private String totalPieces;

	// 包装
	private String packing;

	// 是否异常货物 异常货 unusual[异]
	private String unusual;

	// 运输性质名字 transtype[中文]
	private String transtype;

	// 打印日期 printdate
	private String printDate;

	// 是否送货上门 是否送货 deliver[送]
	private String deliverToDoor;

	// 货物类型 goodstype[A/B/ ]
	private String goodstype;

	// 航班早班 preassembly[早班]
	private String preassembly;
	
	//收货客户联系人
	private String receiveCustomerContact;
	
	//是否发货大客户
	private String deliveryBigCustomer;
	
	//是否收货大客户
	private String receiveBigCustomer;
	
	//渠道单号
	private String channelNumber;
	
	//对接外场
	private String partnerBillingLogo;
	
	
	public String getPartnerBillingLogo() {
		return partnerBillingLogo;
	}

	public void setPartnerBillingLogo(String partnerBillingLogo) {
		this.partnerBillingLogo = partnerBillingLogo;
	}

	public String getChannelNumber() {
		return channelNumber;
	}

	public void setChannelNumber(String channelNumber) {
		this.channelNumber = channelNumber;
	}
	//运单状态
	private String waybillStatus;
	//收货客户的省  市  区
	private String receiveCustomerProvName;
	private String  receiveCustomerCityName;
	private String receiveCustomerDistName;
	//到达市
	private String destionCity;
	//转寄退回件
	private String forwardReturn;
	
	/**
	 * 打木标签需求字段
	 * @return
	 */
	// 打木架货物尺寸
	private String standGoodsSize;
	// 打木架货物件数
	private Integer standGoodsNum;
	// 打木架货物体积
	private BigDecimal standGoodsVolume;
	//打木架要求
	private String standRequirement;
	//打木箱要求
	private String boxRequirement;
	public String getStandRequirement() {
		return standRequirement;
	}

	public void setStandRequirement(String standRequirement) {
		this.standRequirement = standRequirement;
	}

	public String getBoxRequirement() {
		return boxRequirement;
	}

	public void setBoxRequirement(String boxRequirement) {
		this.boxRequirement = boxRequirement;
	}

	public String getSalverRequirement() {
		return salverRequirement;
	}

	public void setSalverRequirement(String salverRequirement) {
		this.salverRequirement = salverRequirement;
	}
	//打木托要求
	private String salverRequirement;
	// 打木箱货物件数
	private Integer boxGoodsNum;
	public String getStandGoodsSize() {
		return standGoodsSize;
	}

	public void setStandGoodsSize(String standGoodsSize) {
		this.standGoodsSize = standGoodsSize;
	}

	public Integer getStandGoodsNum() {
		return standGoodsNum;
	}

	public void setStandGoodsNum(Integer standGoodsNum) {
		this.standGoodsNum = standGoodsNum;
	}

	public BigDecimal getStandGoodsVolume() {
		return standGoodsVolume;
	}

	public void setStandGoodsVolume(BigDecimal standGoodsVolume) {
		this.standGoodsVolume = standGoodsVolume;
	}

	public Integer getBoxGoodsNum() {
		return boxGoodsNum;
	}

	public void setBoxGoodsNum(Integer boxGoodsNum) {
		this.boxGoodsNum = boxGoodsNum;
	}

	public String getBoxGoodsSize() {
		return boxGoodsSize;
	}

	public void setBoxGoodsSize(String boxGoodsSize) {
		this.boxGoodsSize = boxGoodsSize;
	}

	public BigDecimal getBoxGoodsVolume() {
		return boxGoodsVolume;
	}

	public void setBoxGoodsVolume(BigDecimal boxGoodsVolume) {
		this.boxGoodsVolume = boxGoodsVolume;
	}

	public Integer getSalverGoodsNum() {
		return salverGoodsNum;
	}

	public void setSalverGoodsNum(Integer salverGoodsNum) {
		this.salverGoodsNum = salverGoodsNum;
	}
	// 打木箱货物尺寸
	private String boxGoodsSize;
	// 打木箱货物体积
	private BigDecimal boxGoodsVolume;
	// 打木托件数
	private Integer salverGoodsNum;
	
	/**是否零担电子运单*/
	private String isElecLtlWaybill = FossConstants.NO;
	/**零担电子运单的状态，该字段当且仅当BarcodePrintLabelDto.isElecLtlWaybill为真时才有效，否则请忽略该字段的值*/
	private String elecLtlWaybillStatus;
	/**其他包装（用于零担电子运单标签打印）*/
	private String otherPackage;
	
	public String getDestionCity() {
		return destionCity;
	}

	public void setDestionCity(String destionCity) {
		this.destionCity = destionCity;
	}

	public String getReceiveCustomerProvName() {
		return receiveCustomerProvName;
	}

	public void setReceiveCustomerProvName(String receiveCustomerProvName) {
		this.receiveCustomerProvName = receiveCustomerProvName;
	}

	public String getReceiveCustomerCityName() {
		return receiveCustomerCityName;
	}

	public void setReceiveCustomerCityName(String receiveCustomerCityName) {
		this.receiveCustomerCityName = receiveCustomerCityName;
	}

	public String getReceiveCustomerDistName() {
		return receiveCustomerDistName;
	}

	public void setReceiveCustomerDistName(String receiveCustomerDistName) {
		this.receiveCustomerDistName = receiveCustomerDistName;
	}

	public String getForwardReturn() {
		return forwardReturn;
	}

	public void setForwardReturn(String forwardReturn) {
		this.forwardReturn = forwardReturn;
	}
	/**是否有星标记,对外提供用于判断该标签是否支持自动分拣 **/
	private String isStarFlag;
	
	/**
	 * 运输性质code == 产品
	 */
	private String productCode;
	/**
	 * @author 200945-wutao
	 * wutao == start
	 * DMANA-3745:
	 * FOSS标签打印：货物标签左上角添加收货地址行政区域；
	 * 添加字段countyRegion 进行打印标签传递和接收参数
	 */
	private String countyRegion;
	
	public void setCountyRegion(String countyRegion) {
		this.countyRegion = countyRegion;
	}
	
	public String getCountyRegion() {
		return countyRegion;
	}
	//是否展会货
	private String isExhibitCargo ;
		
	public String getIsExhibitCargo() {
		return isExhibitCargo;
	}

	public void setIsExhibitCargo(String isExhibitCargo) {
		this.isExhibitCargo = isExhibitCargo;
	}
	
	//是否显示“德邦物流”
	private String isPrintLogo;

	public String getIsPrintLogo() {
		return isPrintLogo;
	}

	public void setIsPrintLogo(String isPrintLogo) {
		this.isPrintLogo = isPrintLogo;
	}
	/**
	 * 判定是否出发快递营业部，用该字段进行判断
	 * @author 200945-wutao
	 * @date 2014-08-26
	 */
	private String isNoStop;

	public String getIsNoStop() {
		return isNoStop;
	}
	
	public void setIsNoStop(String isNoStop) {
		this.isNoStop = isNoStop;
	}
	/**
	 * <p>获得星标记</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-5-10 上午10:50:38
	 * @return
	 * @see
	 */
	public String getIsStarFlag() {
	    return isStarFlag;
	}

	/**
	 * <p>设置星标记</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-5-10 上午10:50:38
	 * @return
	 * @see
	 */
	public void setIsStarFlag(String isStarFlag) {
	    this.isStarFlag = isStarFlag;
	}
	/**
	 * @return the addr1 .
	 */
	public String getAddr1() {
		return addr1;
	}

	/**
	 * @param addr1
	 *            the addr1 to set.
	 */
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	/**
	 * @return the addr2 .
	 */
	public String getAddr2() {
		return addr2;
	}

	/**
	 * @param addr2
	 *            the addr2 to set.
	 */
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	/**
	 * @return the addr3 .
	 */
	public String getAddr3() {
		return addr3;
	}

	/**
	 * @param addr3
	 *            the addr3 to set.
	 */
	public void setAddr3(String addr3) {
		this.addr3 = addr3;
	}

	/**
	 * @return the addr4 .
	 */
	public String getAddr4() {
		return addr4;
	}

	/**
	 * @param addr4
	 *            the addr4 to set.
	 */
	public void setAddr4(String addr4) {
		this.addr4 = addr4;
	}

	/**
	 * @return the location1 .
	 */
	public String getLocation1() {
		return location1;
	}

	/**
	 * @param location1
	 *            the location1 to set.
	 */
	public void setLocation1(String location1) {
		this.location1 = location1;
	}

	/**
	 * @return the location2 .
	 */
	public String getLocation2() {
		return location2;
	}

	/**
	 * @param location2
	 *            the location2 to set.
	 */
	public void setLocation2(String location2) {
		this.location2 = location2;
	}

	/**
	 * @return the location3 .
	 */
	public String getLocation3() {
		return location3;
	}

	/**
	 * @param location3
	 *            the location3 to set.
	 */
	public void setLocation3(String location3) {
		this.location3 = location3;
	}

	/**
	 * @return the location4 .
	 */
	public String getLocation4() {
		return location4;
	}

	/**
	 * @param location4
	 *            the location4 to set.
	 */
	public void setLocation4(String location4) {
		this.location4 = location4;
	}

	/**
	 * @return the optuserNum .
	 */
	public String getOptuserNum() {
		return optuserNum;
	}

	/**
	 * @param optuserNum
	 *            the optuserNum to set.
	 */
	public void setOptuserNum(String optuserNum) {
		this.optuserNum = optuserNum;
	}

	/**
	 * @return the waybillNumber .
	 */
	public String getWaybillNumber() {
		return waybillNumber;
	}

	/**
	 * @param waybillNumber
	 *            the waybillNumber to set.
	 */
	public void setWaybillNumber(String waybillNumber) {
		this.waybillNumber = waybillNumber;
	}

	/**
	 * @return the printSerialnos .
	 */
	public String getPrintSerialnos() {
		return printSerialnos;
	}

	/**
	 * @param printSerialnos
	 *            the printSerialnos to set.
	 */
	public void setPrintSerialnos(String printSerialnos) {
		this.printSerialnos = printSerialnos;
	}

	/**
	 * @return the leavecity .
	 */
	public String getLeavecity() {
		return leavecity;
	}

	/**
	 * @param leavecity
	 *            the leavecity to set.
	 */
	public void setLeavecity(String leavecity) {
		this.leavecity = leavecity;
	}

	/**
	 * @return the destination .
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination
	 *            the destination to set.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return the isAgent .
	 */
	public String getIsAgent() {
		return isAgent;
	}

	/**
	 * @param isAgent
	 *            the isAgent to set.
	 */
	public void setIsAgent(String isAgent) {
		this.isAgent = isAgent;
	}

	/**
	 * @return the destinationCode .
	 */
	public String getDestinationCode() {
		return destinationCode;
	}

	/**
	 * @param destinationCode
	 *            the destinationCode to set.
	 */
	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}

	/**
	 * @return the lastTransCenterNo .
	 */
	public String getLastTransCenterNo() {
		return lastTransCenterNo;
	}

	/**
	 * @param lastTransCenterNo
	 *            the lastTransCenterNo to set.
	 */
	public void setLastTransCenterNo(String lastTransCenterNo) {
		this.lastTransCenterNo = lastTransCenterNo;
	}

	/**
	 * @return the finaloutfieldid .
	 */
	public String getFinaloutfieldid() {
		return finaloutfieldid;
	}

	/**
	 * @param finaloutfieldid
	 *            the finaloutfieldid to set.
	 */
	public void setFinaloutfieldid(String finaloutfieldid) {
		this.finaloutfieldid = finaloutfieldid;
	}

	/**
	 * @return the lastTransCenterCity .
	 */
	public String getLastTransCenterCity() {
		return lastTransCenterCity;
	}

	/**
	 * @param lastTransCenterCity
	 *            the lastTransCenterCity to set.
	 */
	public void setLastTransCenterCity(String lastTransCenterCity) {
		this.lastTransCenterCity = lastTransCenterCity;
	}

	/**
	 * @return the weight .
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set.
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}

	/**
	 * @return the totalPieces .
	 */
	public String getTotalPieces() {
		return totalPieces;
	}

	/**
	 * @param totalPieces
	 *            the totalPieces to set.
	 */
	public void setTotalPieces(String totalPieces) {
		this.totalPieces = totalPieces;
	}

	/**
	 * @return the packing .
	 */
	public String getPacking() {
		return packing;
	}

	/**
	 * @param packing
	 *            the packing to set.
	 */
	public void setPacking(String packing) {
		this.packing = packing;
	}

	/**
	 * @return the unusual .
	 */
	public String getUnusual() {
		return unusual;
	}

	/**
	 * @param unusual
	 *            the unusual to set.
	 */
	public void setUnusual(String unusual) {
		this.unusual = unusual;
	}

	/**
	 * @return the transtype .
	 */
	public String getTranstype() {
		return transtype;
	}

	/**
	 * @param transtype
	 *            the transtype to set.
	 */
	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}

	/**
	 * @return the printDate .
	 */
	public String getPrintDate() {
		return printDate;
	}

	/**
	 * @param printDate
	 *            the printDate to set.
	 */
	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}

	/**
	 * @return the deliverToDoor .
	 */
	public String getDeliverToDoor() {
		return deliverToDoor;
	}

	/**
	 * @param deliverToDoor
	 *            the deliverToDoor to set.
	 */
	public void setDeliverToDoor(String deliverToDoor) {
		this.deliverToDoor = deliverToDoor;
	}

	/**
	 * @return the goodstype .
	 */
	public String getGoodstype() {
		return goodstype;
	}

	/**
	 * @param goodstype
	 *            the goodstype to set.
	 */
	public void setGoodstype(String goodstype) {
		this.goodstype = goodstype;
	}

	/**
	 * @return the preassembly .
	 */
	public String getPreassembly() {
		return preassembly;
	}

	/**
	 * @param preassembly
	 *            the preassembly to set.
	 */
	public void setPreassembly(String preassembly) {
		this.preassembly = preassembly;
	}

	/**
	 * @return the receiveCustomerContact
	 */
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	/**
	 * @param receiveCustomerContact the receiveCustomerContact to set
	 */
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}


	public String getDeliveryBigCustomer() {
		return deliveryBigCustomer;
	}

	public void setDeliveryBigCustomer(String deliveryBigCustomer) {
		this.deliveryBigCustomer = deliveryBigCustomer;
	}

	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	  * @return  the waybillBean
	 */
	public WaybillEntity getWaybillBean() {
		return waybillBean;
	}

	/**
	 * @param waybillBean the waybillBean to set
	 */
	public void setWaybillBean(WaybillEntity waybillBean) {
		this.waybillBean = waybillBean;
	}

	public String getWaybillStatus() {
		return waybillStatus;
	}

	public void setWaybillStatus(String waybillStatus) {
		this.waybillStatus = waybillStatus;
	}
	
	public String getSecLoadOrgName() {
		return secLoadOrgName;
	}

	public void setSecLoadOrgName(String secLoadOrgName) {
		this.secLoadOrgName = secLoadOrgName;
	}

	public String getReceiveBigCustomer() {
		return receiveBigCustomer;
	}

	public void setReceiveBigCustomer(String receiveBigCustomer) {
		this.receiveBigCustomer = receiveBigCustomer;
	}

	public String getIsElecLtlWaybill() {
		return isElecLtlWaybill;
	}

	public void setIsElecLtlWaybill(String isElecLtlWaybill) {
		this.isElecLtlWaybill = isElecLtlWaybill;
	}

	public String getElecLtlWaybillStatus() {
		return elecLtlWaybillStatus;
	}

	public void setElecLtlWaybillStatus(String elecLtlWaybillStatus) {
		this.elecLtlWaybillStatus = elecLtlWaybillStatus;
	}
	
	public String getOtherPackage() {
		return otherPackage;
	}
	
	public void setOtherPackage(String otherPackage) {
		this.otherPackage = otherPackage;
	}

	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	public String getSortingResult() {
		return sortingResult;
	}

	public void setSortingResult(String sortingResult) {
		this.sortingResult = sortingResult;
	}

	public String getLastSecondTransCenterNo() {
		return lastSecondTransCenterNo;
	}

	public void setLastSecondTransCenterNo(String lastSecondTransCenterNo) {
		this.lastSecondTransCenterNo = lastSecondTransCenterNo;
	}

	public String getLastSecondFinaloutFieldId() {
		return lastSecondFinaloutFieldId;
	}

	public void setLastSecondFinaloutFieldId(String lastSecondFinaloutFieldId) {
		this.lastSecondFinaloutFieldId = lastSecondFinaloutFieldId;
	}

	public String getLastSecondTransCenterCity() {
		return lastSecondTransCenterCity;
	}

	public void setLastSecondTransCenterCity(String lastSecondTransCenterCity) {
		this.lastSecondTransCenterCity = lastSecondTransCenterCity;
	}

	public String getLastThirdTransCenterNo() {
		return lastThirdTransCenterNo;
	}

	public void setLastThirdTransCenterNo(String lastThirdTransCenterNo) {
		this.lastThirdTransCenterNo = lastThirdTransCenterNo;
	}

	public String getLastThirdFinaloutFieldId() {
		return lastThirdFinaloutFieldId;
	}

	public void setLastThirdFinaloutFieldId(String lastThirdFinaloutFieldId) {
		this.lastThirdFinaloutFieldId = lastThirdFinaloutFieldId;
	}

	public String getLastThirdTransCenterCity() {
		return lastThirdTransCenterCity;
	}

	public void setLastThirdTransCenterCity(String lastThirdTransCenterCity) {
		this.lastThirdTransCenterCity = lastThirdTransCenterCity;
	}

	public String getLastFirstTransCenterCity() {
		return lastFirstTransCenterCity;
	}

	public void setLastFirstTransCenterCity(String lastFirstTransCenterCity) {
		this.lastFirstTransCenterCity = lastFirstTransCenterCity;
	}

	public String getSimpleLeaveCity() {
		return simpleLeaveCity;
	}

	public void setSimpleLeaveCity(String simpleLeaveCity) {
		this.simpleLeaveCity = simpleLeaveCity;
	}
}