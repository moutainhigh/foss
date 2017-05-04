/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/dto/ChangeLabelGoodsPrintDto.java
 *  
 *  FILE NAME          :ChangeLabelGoodsPrintDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-package-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.packaging.api.shared.dto
 * FILE    NAME: PackagingLabelPrintDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.shared.dto;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 打印标签数据
 * @author 046130-foss-xuduowei
 * @date 2012-11-15 下午4:20:18
 */
public class ChangeLabelGoodsPrintDto extends BaseEntity{
	private static final long serialVersionUID = -6278501010365277997L;

	/** 地区1 **/
	private String addr1;

    /** 地区2 **/
    private String addr2;

    /** 地区3 **/
    private String addr3;

    /** 地区4 **/
    private String addr4;

    /** 编号1 **/
    private String location1;

    /** 编号2 **/
    private String location2;

    /** 编号3 **/
    private String location3;

    /** 编号4 **/
    private String location4;

    /** 当前用户 开单人 **/
    private String optuserNum;

    /** 运单号**/
    private String waybillNumber;

    /** 打印流水号 **/
    private String printSerialnos;

    /** 始发站 出发城市 **/
    private String leavecity;

    /** 目的站 **/
    private String destination;

    /** 运输类型 是否偏线 **/
    private String isAgent;

    /** 目的站编码 **/
    private String destinationCode;

    /** 最终外场编码 deptno **/
    private String lastTransCenterNo;
    
    /** 最终外场ID **/ 
    private String  finaloutfieldid;

    /** 最终外场城市名称 finaloutname **/
    private String lastTransCenterCity;

    /** 重量 **/
    private String weight;

    /** 总数量 件数 **/
    private String totalPieces;

    /** 包装 **/
    private String packing;

    /** 是否异常货物  异常货 unusual[异] **/
    private String unusual;

    /** 运输类型 transtype[中文] **/
    private String transtype;
    
    /** 打印日期 printdate **/
    private String printDate;
    
    /** 是否送货上门   是否送货 deliver[送] **/
    private String deliverToDoor;

    /** 货物类型 goodstype[A/B/ ]**/
    private String goodstype; 

    /** 航班早班 preassembly[早班] **/ 
    private String  preassembly;

	/**
	 * 获取 地区1 *.
	 *
	 * @return the 地区1 *
	 */
	public String getAddr1() {
		return addr1;
	}

	/**
	 * 设置 地区1 *.
	 *
	 * @param addr1 the new 地区1 *
	 */
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	/**
	 * 获取 地区2 *.
	 *
	 * @return the 地区2 *
	 */
	public String getAddr2() {
		return addr2;
	}

	/**
	 * 设置 地区2 *.
	 *
	 * @param addr2 the new 地区2 *
	 */
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	/**
	 * 获取 地区3 *.
	 *
	 * @return the 地区3 *
	 */
	public String getAddr3() {
		return addr3;
	}

	/**
	 * 设置 地区3 *.
	 *
	 * @param addr3 the new 地区3 *
	 */
	public void setAddr3(String addr3) {
		this.addr3 = addr3;
	}

	/**
	 * 获取 地区4 *.
	 *
	 * @return the 地区4 *
	 */
	public String getAddr4() {
		return addr4;
	}

	/**
	 * 设置 地区4 *.
	 *
	 * @param addr4 the new 地区4 *
	 */
	public void setAddr4(String addr4) {
		this.addr4 = addr4;
	}

	/**
	 * 获取 编号1 *.
	 *
	 * @return the 编号1 *
	 */
	public String getLocation1() {
		return location1;
	}

	/**
	 * 设置 编号1 *.
	 *
	 * @param location1 the new 编号1 *
	 */
	public void setLocation1(String location1) {
		this.location1 = location1;
	}

	/**
	 * 获取 编号2 *.
	 *
	 * @return the 编号2 *
	 */
	public String getLocation2() {
		return location2;
	}

	/**
	 * 设置 编号2 *.
	 *
	 * @param location2 the new 编号2 *
	 */
	public void setLocation2(String location2) {
		this.location2 = location2;
	}

	/**
	 * 获取 编号3 *.
	 *
	 * @return the 编号3 *
	 */
	public String getLocation3() {
		return location3;
	}

	/**
	 * 设置 编号3 *.
	 *
	 * @param location3 the new 编号3 *
	 */
	public void setLocation3(String location3) {
		this.location3 = location3;
	}

	/**
	 * 获取 编号4 *.
	 *
	 * @return the 编号4 *
	 */
	public String getLocation4() {
		return location4;
	}

	/**
	 * 设置 编号4 *.
	 *
	 * @param location4 the new 编号4 *
	 */
	public void setLocation4(String location4) {
		this.location4 = location4;
	}

	/**
	 * 获取 当前用户 开单人 *.
	 *
	 * @return the 当前用户 开单人 *
	 */
	public String getOptuserNum() {
		return optuserNum;
	}

	/**
	 * 设置 当前用户 开单人 *.
	 *
	 * @param optuserNum the new 当前用户 开单人 *
	 */
	public void setOptuserNum(String optuserNum) {
		this.optuserNum = optuserNum;
	}

	/**
	 * 获取 运单号*.
	 *
	 * @return the 运单号*
	 */
	public String getWaybillNumber() {
		return waybillNumber;
	}

	/**
	 * 设置 运单号*.
	 *
	 * @param waybillNumber the new 运单号*
	 */
	public void setWaybillNumber(String waybillNumber) {
		this.waybillNumber = waybillNumber;
	}

	/**
	 * 获取 打印流水号 *.
	 *
	 * @return the 打印流水号 *
	 */
	public String getPrintSerialnos() {
		return printSerialnos;
	}

	/**
	 * 设置 打印流水号 *.
	 *
	 * @param printSerialnos the new 打印流水号 *
	 */
	public void setPrintSerialnos(String printSerialnos) {
		this.printSerialnos = printSerialnos;
	}

	/**
	 * 获取 始发站 出发城市 *.
	 *
	 * @return the 始发站 出发城市 *
	 */
	public String getLeavecity() {
		return leavecity;
	}

	/**
	 * 设置 始发站 出发城市 *.
	 *
	 * @param leavecity the new 始发站 出发城市 *
	 */
	public void setLeavecity(String leavecity) {
		this.leavecity = leavecity;
	}

	/**
	 * 获取 目的站 *.
	 *
	 * @return the 目的站 *
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * 设置 目的站 *.
	 *
	 * @param destination the new 目的站 *
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * 获取 运输类型 是否偏线 *.
	 *
	 * @return the 运输类型 是否偏线 *
	 */
	public String getIsAgent() {
		return isAgent;
	}

	/**
	 * 设置 运输类型 是否偏线 *.
	 *
	 * @param isAgent the new 运输类型 是否偏线 *
	 */
	public void setIsAgent(String isAgent) {
		this.isAgent = isAgent;
	}

	/**
	 * 获取 目的站编码 *.
	 *
	 * @return the 目的站编码 *
	 */
	public String getDestinationCode() {
		return destinationCode;
	}

	/**
	 * 设置 目的站编码 *.
	 *
	 * @param destinationCode the new 目的站编码 *
	 */
	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}

	/**
	 * 获取 最终外场编码 deptno *.
	 *
	 * @return the 最终外场编码 deptno *
	 */
	public String getLastTransCenterNo() {
		return lastTransCenterNo;
	}

	/**
	 * 设置 最终外场编码 deptno *.
	 *
	 * @param lastTransCenterNo the new 最终外场编码 deptno *
	 */
	public void setLastTransCenterNo(String lastTransCenterNo) {
		this.lastTransCenterNo = lastTransCenterNo;
	}

	/**
	 * 获取 最终外场ID *.
	 *
	 * @return the 最终外场ID *
	 */
	public String getFinaloutfieldid() {
		return finaloutfieldid;
	}

	/**
	 * 设置 最终外场ID *.
	 *
	 * @param finaloutfieldid the new 最终外场ID *
	 */
	public void setFinaloutfieldid(String finaloutfieldid) {
		this.finaloutfieldid = finaloutfieldid;
	}

	/**
	 * 获取 最终外场城市名称 finaloutname *.
	 *
	 * @return the 最终外场城市名称 finaloutname *
	 */
	public String getLastTransCenterCity() {
		return lastTransCenterCity;
	}

	/**
	 * 设置 最终外场城市名称 finaloutname *.
	 *
	 * @param lastTransCenterCity the new 最终外场城市名称 finaloutname *
	 */
	public void setLastTransCenterCity(String lastTransCenterCity) {
		this.lastTransCenterCity = lastTransCenterCity;
	}

	/**
	 * 获取 重量 *.
	 *
	 * @return the 重量 *
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * 设置 重量 *.
	 *
	 * @param weight the new 重量 *
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}

	/**
	 * 获取 总数量 件数 *.
	 *
	 * @return the 总数量 件数 *
	 */
	public String getTotalPieces() {
		return totalPieces;
	}

	/**
	 * 设置 总数量 件数 *.
	 *
	 * @param totalPieces the new 总数量 件数 *
	 */
	public void setTotalPieces(String totalPieces) {
		this.totalPieces = totalPieces;
	}

	/**
	 * 获取 包装 *.
	 *
	 * @return the 包装 *
	 */
	public String getPacking() {
		return packing;
	}

	/**
	 * 设置 包装 *.
	 *
	 * @param packing the new 包装 *
	 */
	public void setPacking(String packing) {
		this.packing = packing;
	}

	/**
	 * 获取 是否异常货物  异常货 unusual[异] *.
	 *
	 * @return the 是否异常货物  异常货 unusual[异] *
	 */
	public String getUnusual() {
		return unusual;
	}

	/**
	 * 设置 是否异常货物  异常货 unusual[异] *.
	 *
	 * @param unusual the new 是否异常货物  异常货 unusual[异] *
	 */
	public void setUnusual(String unusual) {
		this.unusual = unusual;
	}

	/**
	 * 获取 运输类型 transtype[中文] *.
	 *
	 * @return the 运输类型 transtype[中文] *
	 */
	public String getTranstype() {
		return transtype;
	}

	/**
	 * 设置 运输类型 transtype[中文] *.
	 *
	 * @param transtype the new 运输类型 transtype[中文] *
	 */
	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}

	/**
	 * 获取 打印日期 printdate *.
	 *
	 * @return the 打印日期 printdate *
	 */
	public String getPrintDate() {
		return printDate;
	}

	/**
	 * 设置 打印日期 printdate *.
	 *
	 * @param printDate the new 打印日期 printdate *
	 */
	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}

	/**
	 * 获取 是否送货上门   是否送货 deliver[送] *.
	 *
	 * @return the 是否送货上门   是否送货 deliver[送] *
	 */
	public String getDeliverToDoor() {
		return deliverToDoor;
	}

	/**
	 * 设置 是否送货上门   是否送货 deliver[送] *.
	 *
	 * @param deliverToDoor the new 是否送货上门   是否送货 deliver[送] *
	 */
	public void setDeliverToDoor(String deliverToDoor) {
		this.deliverToDoor = deliverToDoor;
	}

	/**
	 * 获取 货物类型 goodstype[A/B/ ]*.
	 *
	 * @return the 货物类型 goodstype[A/B/ ]*
	 */
	public String getGoodstype() {
		return goodstype;
	}

	/**
	 * 设置 货物类型 goodstype[A/B/ ]*.
	 *
	 * @param goodstype the new 货物类型 goodstype[A/B/ ]*
	 */
	public void setGoodstype(String goodstype) {
		this.goodstype = goodstype;
	}

	/**
	 * 获取 航班早班 preassembly[早班] *.
	 *
	 * @return the 航班早班 preassembly[早班] *
	 */
	public String getPreassembly() {
		return preassembly;
	}

	/**
	 * 设置 航班早班 preassembly[早班] *.
	 *
	 * @param preassembly the new 航班早班 preassembly[早班] *
	 */
	public void setPreassembly(String preassembly) {
		this.preassembly = preassembly;
	}
    
    
}