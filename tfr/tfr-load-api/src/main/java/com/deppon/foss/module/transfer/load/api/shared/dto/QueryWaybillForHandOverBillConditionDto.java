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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/QueryWaybillForHandOverBillConditionDto.java
 *  
 *  FILE NAME          :QueryWaybillForHandOverBillConditionDto.java
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
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/** 
 * @className: QueryWaybillForHandOverBillDto
 * @author: ShiWei shiwei@outlook.com
 * @description: 查询交接运单之查询条件
 * @date: 2012-11-26 下午7:02:59
 * 
 */
public class QueryWaybillForHandOverBillConditionDto implements Serializable {

	private static final long serialVersionUID = -329815961022589292L;
	//起始入库时间
	private Date beginInStorageTime;
	//截止入库时间
	private Date endInStorageTime;
	//运输性质
	private List<String> transPropertyList;
	//交接单到达部门code(PDA建立装车任务拉取运单时，到达部门为list)
	private List<String> arriveDeptList;
	//运输类型
	private String transType;
	//货物类型(A,B货)
	private String goodsType;
	//运单号
	private String waybillNo;
	//当前部门code
	private String currentDeptCode;
	//必走货时间
	private Date priorityTime;
	//要排除的库区的种类list
	private List<String> abnormalGoodsAreaTypeList;
	/**
	 * 是否修改交接单界面中通过快速添加功能进行查询，如果是，则没有查询到库存后不抛异常，返回空，因为此时除了查询库存进行添加
	 * 还要对已经删除的流水号进行添加
	 */
	private String isModifyHandOverBill;
	
	/**
	 * 交接类型
	 */
	private String handOverType;
	
	public String getHandOverType() {
		return handOverType;
	}
	public void setHandOverType(String handOverType) {
		this.handOverType = handOverType;
	}
	public String getIsModifyHandOverBill() {
		return isModifyHandOverBill;
	}
	public void setIsModifyHandOverBill(String isModifyHandOverBill) {
		this.isModifyHandOverBill = isModifyHandOverBill;
	}
	public List<String> getAbnormalGoodsAreaTypeList() {
		return abnormalGoodsAreaTypeList;
	}
	public void setAbnormalGoodsAreaTypeList(List<String> abnormalGoodsAreaTypeList) {
		this.abnormalGoodsAreaTypeList = abnormalGoodsAreaTypeList;
	}
	
	public List<String> getTransPropertyList() {
		return transPropertyList;
	}
	public void setTransPropertyList(List<String> transPropertyList) {
		this.transPropertyList = transPropertyList;
	}
	public Date getPriorityTime() {
		return priorityTime;
	}
	public void setPriorityTime(Date priorityTime) {
		this.priorityTime = priorityTime;
	}
	public String getCurrentDeptCode() {
		return currentDeptCode;
	}
	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}
	@DateFormat
	public Date getBeginInStorageTime() {
		return beginInStorageTime;
	}
	@DateFormat
	public void setBeginInStorageTime(Date beginInStorageTime) {
		this.beginInStorageTime = beginInStorageTime;
	}
	@DateFormat
	public Date getEndInStorageTime() {
		return endInStorageTime;
	}
	@DateFormat
	public void setEndInStorageTime(Date endInStorageTime) {
		this.endInStorageTime = endInStorageTime;
	}
	public List<String> getArriveDeptList() {
		return arriveDeptList;
	}
	public void setArriveDeptList(List<String> arriveDeptList) {
		this.arriveDeptList = arriveDeptList;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**   
	 * goodsType   
	 *   
	 * @return  the goodsType   
	 */
	
	public String getGoodsType() {
		return goodsType;
	}
	/**   
	 * @param goodsType the goodsType to set
	 * Date:2013-8-2下午4:43:15
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
}