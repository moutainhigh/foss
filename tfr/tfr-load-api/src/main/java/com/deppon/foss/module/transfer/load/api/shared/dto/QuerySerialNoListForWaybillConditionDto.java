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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/QuerySerialNoListForWaybillConditionDto.java
 *  
 *  FILE NAME          :QuerySerialNoListForWaybillConditionDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.util.List;

/** 
 * @className: QuerySerialNoListForWaybillConditionDto
 * @author: ShiWei shiwei@outlook.com
 * @description: 查询交接运单界面，打开运单显示流水号时，传入的查询条件
 * @date: 2012-11-27 下午7:30:18
 * 
 */
public class QuerySerialNoListForWaybillConditionDto implements Serializable {

	private static final long serialVersionUID = -4304046199714754867L;
	//运单号
	private String waybillNo;
	//库存部门
	private String currentDeptCode;
	//下一部门list
	private List<String> nextDeptCodeList;
	//下一部门
	private String nextDeptCode;
	//交接单号
	private String handOverBillNo;
	//库区code
	private String goodsAreaCode;
	//非正常库区类型list
	private List<String> abnormalGoodsAreaTypeList;
	//是否合车
	private String isJoinCar;
	//是否快速添加功能
	private String isQuickAdd;
	
	public String getNextDeptCode() {
		return nextDeptCode;
	}
	public void setNextDeptCode(String nextDeptCode) {
		this.nextDeptCode = nextDeptCode;
	}
	public String getIsQuickAdd() {
		return isQuickAdd;
	}
	public void setIsQuickAdd(String isQuickAdd) {
		this.isQuickAdd = isQuickAdd;
	}
	public String getIsJoinCar() {
		return isJoinCar;
	}
	public void setIsJoinCar(String isJoinCar) {
		this.isJoinCar = isJoinCar;
	}
	public List<String> getAbnormalGoodsAreaTypeList() {
		return abnormalGoodsAreaTypeList;
	}
	public void setAbnormalGoodsAreaTypeList(List<String> abnormalGoodsAreaTypeList) {
		this.abnormalGoodsAreaTypeList = abnormalGoodsAreaTypeList;
	}
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}
	public String getHandOverBillNo() {
		return handOverBillNo;
	}
	public void setHandOverBillNo(String handOverBillNo) {
		this.handOverBillNo = handOverBillNo;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getCurrentDeptCode() {
		return currentDeptCode;
	}
	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}
	public List<String> getNextDeptCodeList() {
		return nextDeptCodeList;
	}
	public void setNextDeptCodeList(List<String> nextDeptCodeList) {
		this.nextDeptCodeList = nextDeptCodeList;
	}

}