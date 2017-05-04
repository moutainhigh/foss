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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/vo/BarcodePrintLabelVo.java
 * 
 * FILE NAME        	: BarcodePrintLabelVo.java
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
package com.deppon.foss.module.pickup.creating.client.vo;

/**
 * (标签打印用到的VO)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-jiangfei,date:2012-11-5 下午12:29:11,content:
 * </p>
 * 
 * @author foss-jiangfei
 * @date 2012-11-5 下午12:29:11
 * @since
 * @version
 */
public class BarcodePrintLabelVo {

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

	// 当前用户 开单人
	private String optuserNum;

	// 运单号
	private String waybillNumber;

	// 打印流水号
	private String printSerialnos;

	// 始发站 出发城市
	private String leavecity;

	// 目的站
	private String destination;

	// 运输类型 是否偏线
	private String isAgent;

	// 目的站编码
	private String destinationCode;

	// 最终外场编码 deptno
	private String lastTransCenterNo;

	// 最终外场ID
	private String finaloutfieldid;

	// 最终外场城市名称 finaloutname
	private String lastTransCenterCity;

	// 重量
	private String weight;

	// 总数量 件数
	private String totalPieces;

	// 包装
	private String packing;

	// 是否异常货物 异常货 unusual[异]
	private String unusual;

	// 运输类型 transtype[中文]
	private String transtype;

	// 打印日期 printdate
	private String printDate;

	// 是否送货上门 是否送货 deliver[送]
	private String deliverToDoor;

	// 货物类型 goodstype[A/B/ ]
	private String goodstype;

	// 航班早班 preassembly[早班]
	private String preassembly;

	/**
	 * getFinaloutfieldid
	 * @return String
	 */
	public String getFinaloutfieldid() {
		return finaloutfieldid;
	}

	/**
	 * setFinaloutfieldid
	 * @param finaloutfieldid String
	 */
	public void setFinaloutfieldid(String finaloutfieldid) {
		this.finaloutfieldid = finaloutfieldid;
	}

	/**
	 * getPreassembly
	 * @return String
	 */
	public String getPreassembly() {
		return preassembly;
	}

	/**
	 * setPreassembly
	 * @param preassembly String
	 */
	public void setPreassembly(String preassembly) {
		this.preassembly = preassembly;
	}

	/**
	 * getDeliverTo Door
	 * @return String
	 */
	public String getDeliverToDoor() {
		return deliverToDoor;
	}

	/**
	 * getAddr1
	 * @return String
	 */
	public String getAddr1() {
		return addr1;
	}

	/**
	 * setAddr1
	 * @param addr1 String
	 */
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	/**
	 * getAddr2
	 * @return String
	 */ 
	public String getAddr2() {
		return addr2;
	}

	/**
	 * setAddr2
	 * @param addr2 String
	 */
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	/**
	 * getAddr3
	 * @return String
	 */
	public String getAddr3() {
		return addr3;
	}
	/**
	 * setAddr3 
	 * @param addr3 String
	 */
	public void setAddr3(String addr3) {
		this.addr3 = addr3;
	}
	/**
	 * getAddr4
	 * @return String
	 */
	public String getAddr4() {
		return addr4;
	}

	/**
	 * setAddr4
	 * @param addr4 String
	 */
	public void setAddr4(String addr4) {
		this.addr4 = addr4;
	}

	/**
	 * getLocation1
	 * @return String
	 */
	public String getLocation1() {
		return location1;
	}

	/**
	 * setLocation1
	 * @param location1 String
	 */
	public void setLocation1(String location1) {
		this.location1 = location1;
	}

	/**
	 * getLocation2 
	 * @return String
	 */
	public String getLocation2() {
		return location2;
	}

	/**
	 * setLocation2
	 * @param location2 String
	 */
	public void setLocation2(String location2) {
		this.location2 = location2;
	}

	/**
	 * getLocation3
	 * @return String
	 */
	public String getLocation3() {
		return location3;
	}

	/**
	 * setLocation3
	 * @param location3 String
	 */
	public void setLocation3(String location3) {
		this.location3 = location3;
	}

	/**
	 * getLocation4
	 * @return String
	 */
	public String getLocation4() {
		return location4;
	}

	/**
	 * setLocation4
	 * @param location4 String
	 */
	public void setLocation4(String location4) {
		this.location4 = location4;
	}

	/**
	 * getPrintSerialnos
	 * @return String
	 */
	public String getPrintSerialnos() {
		return printSerialnos;
	}

	/**
	 * setPrintSerialnos
	 * @param printSerialnos String
	 */ 
	public void setPrintSerialnos(String printSerialnos) {
		this.printSerialnos = printSerialnos;
	}

	/**
	 * getDestinationCode
	 * @return String
	 */
	public String getDestinationCode() {
		return destinationCode;
	}

	/**
	 * setDestinationCode
	 * @param destinationCode String
	 */
	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}

	/**
	 * getPrintDate
	 * @return String
	 */ 
	public String getPrintDate() {
		return printDate;
	}

	/**
	 * setPrintDate
	 * @param printDate String
	 */
	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}

	/**
	 * getLastTransCenterNo
	 * @return String
	 */
	public String getLastTransCenterNo() {
		return lastTransCenterNo;
	}

	/**
	 * setLastTransCenterNo
	 * @param lastTransCenterNo String
	 */
	public void setLastTransCenterNo(String lastTransCenterNo) {
		this.lastTransCenterNo = lastTransCenterNo;
	}

	/**
	 * getLastTransCenterCity
	 * @return String
	 */
	public String getLastTransCenterCity() {
		return lastTransCenterCity;
	}

	/**
	 * setLastTransCenterCity
	 * @param lastTransCenterCity String
	 */
	public void setLastTransCenterCity(String lastTransCenterCity) {
		this.lastTransCenterCity = lastTransCenterCity;
	}

	/**
	 * setDeliverTo Door
	 * @param deliverTo Door String
	 */
	public void setDeliverToDoor(String deliverToDoor) {
		this.deliverToDoor = deliverToDoor;
	}

	/**
	 * getWaybillNumber
	 * @return String
	 */
	public String getWaybillNumber() {
		return waybillNumber;
	}

	/**
	 * setWaybillNumber
	 * @param waybillNumber String
	 */
	public void setWaybillNumber(String waybillNumber) {
		this.waybillNumber = waybillNumber;
	}

	/**
	 * getDestination
	 * @return String
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * setDestination 
	 * @param destination String
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * getGoodstype
	 * @return String
	 */
	public String getGoodstype() {
		return goodstype;
	}

	/**
	 * setGoodstype 
	 * @param goodstype String
	 */
	public void setGoodstype(String goodstype) {
		this.goodstype = goodstype;
	}

	/**
	 * getIsAgent
	 * @return String
	 */
	public String getIsAgent() {
		return isAgent;
	}

	/**
	 * setIsAgent
	 * @param isAgent String
	 */
	public void setIsAgent(String isAgent) {
		this.isAgent = isAgent;
	}

	/**
	 * getLeavecity
	 * @return String
	 */
	public String getLeavecity() {
		return leavecity;
	}

	/**
	 * setLeavecity
	 * @param leavecity String
	 */ 
	public void setLeavecity(String leavecity) {
		this.leavecity = leavecity;
	}

	/**
	 * getOptuserNum
	 * @return String
	 */
	public String getOptuserNum() {
		return optuserNum;
	}

	/**
	 * setOptuserNum
	 * @param optuserNum String
	 */
	public void setOptuserNum(String optuserNum) {
		this.optuserNum = optuserNum;
	}

	/**
	 * getPacking
	 * @return String
	 */
	public String getPacking() {
		return packing;
	}

	/**
	 * setPacking
	 * @param packing String
	 */
	public void setPacking(String packing) {
		this.packing = packing;
	}

	/**
	 * getWeight
	 * @return String
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * setWeight
	 * @param weight String
	 */ 
	public void setWeight(String weight) {
		this.weight = weight;
	}

	/**
	 * getTotalPieces
	 * @return String
	 */
	public String getTotalPieces() {
		return totalPieces;
	}

	/**
	 * setTotalPieces
	 * @param totalPieces String
	 */
	public void setTotalPieces(String totalPieces) {
		this.totalPieces = totalPieces;
	}

	/**
	 * getTranstype
	 * @return String
	 */
	public String getTranstype() {
		return transtype;
	}

	/**
	 * setTranstype
	 * @param transtype String
	 */ 
	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}

	/**
	 * getUnusual
	 * @return String
	 */
	public String getUnusual() {
		return unusual;
	}

	/**
	 * setUnusual
	 * @param unusual String
	 */
	public void setUnusual(String unusual) {
		this.unusual = unusual;
	}
}