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
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/domain/OrderVehicleEntity.java
 * 
 *  FILE NAME     :OrderVehicleEntity.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 约车申请实体
 * @author 104306-foss-wangLong
 * @date 2012-10-15 下午12:50:56
 */
public class OrderVehicleEntity extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	/** 约车编号 */
	private String orderNo;
	
	/** 申请时间 */
	private Date applyTime;
		
	/** 预约类型 */
	private String orderType;
		
	/** 车型  */
	private String orderVehicleModel;
		
	/** 派车车队 */
	private String dispatchTransDept;
		
	/** 用车地址*/
	private String useAddress;
		
	/** A货B货  可选*/
	private String goodsType;
		
	/** 是否尾板车 */
	private String isTailboard;
	
	/** 预计用车时间 */
	private Date predictUseTime;
		
	/** 货物名称  -可选 */
	private String goodsName;
		
	/** 货物重量 */
	private BigDecimal weight;
		
	/** 货物体积 */
	private BigDecimal volume;
		
	/** 货物件数 */
	private Integer goodsQty;
		
	/** 备注  -可选 */
	private String notes;
		
	/** 约车状态 */
	private String status;
		
	/** 申请网点名称*/
	private String applyOrgName;
		
	/** 申请网点编码 */
	private String applyOrgCode;
		
	/** 申请人员名称 */
	private String applyEmpName;
		
	/** 申请人员编码 */
	private String applyEmpCode;
	
	/** 固定电话  */
	private String telephoneNo;
	
	/** 手机号码 */
	private String mobilephoneNo;
	
	/** 订单号 */
	private String wayBillNo;
	
	/**  是否集中区域 转货订单 */
	private String isGroupZone;
	
	/**  用车部门名称  */
	private String useVehicleOrgName;
	
	/**  用车部门code */
	private String useVehicleOrgCode;
	
	/** 派车车队名称 */
	private String dispatchTransDeptName;
	
	/**顶级车队编号*/
    private String topFleetCode;
    /**当前登陆部门编号*/
    private String currentDeptCode;
    
    /**车辆到达确认时间*/
    private Date arrivalTime;
    /** 受理时间*/
    private Date passTime;
	/**
	 * 获得orderNo
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}
	
	/**
	 * 获得applyTime
	 * @return the applyTime
	 */
	public Date getApplyTime() {
		return applyTime;
	}
	
	/**
	 * 获得orderType
	 * @return the orderType
	 */
	public String getOrderType() {
		return orderType;
	}
	
	/**
	 * 获得orderVehicleModel
	 * @return the orderVehicleModel
	 */
	public String getOrderVehicleModel() {
		return orderVehicleModel;
	}
	
	/**
	 * 获得dispatchTransDept
	 * @return the dispatchTransDept
	 */
	public String getDispatchTransDept() {
		return dispatchTransDept;
	}
	
	/**
	 * 获得useAddress
	 * @return the useAddress
	 */
	public String getUseAddress() {
		return useAddress;
	}
	
	/**
	 * 获得goodsType
	 * @return the goodsType
	 */
	public String getGoodsType() {
		return goodsType;
	}
	
	/**
	 * 获得isTailboard
	 * @return the isTailboard
	 */
	public String getIsTailboard() {
		return isTailboard;
	}
	
	/**
	 * 获得predictUseTime
	 * @return the predictUseTime
	 */
	public Date getPredictUseTime() {
		return predictUseTime;
	}
	
	/**
	 * 获得goodsName
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}
	
	/**
	 * 获得weight
	 * @return the weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}
	
	/**
	 * 获得volume
	 * @return the volume
	 */
	public BigDecimal getVolume() {
		return volume;
	}
	
	/**
	 * 获得goodsQty
	 * @return the goodsQty
	 */
	public Integer getGoodsQty() {
		return goodsQty;
	}
	
	/**
	 * 获得notes
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	
	/**
	 * 获得status
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * 获得applyOrgName
	 * @return the applyOrgName
	 */
	public String getApplyOrgName() {
		return applyOrgName;
	}
	
	/**
	 * 获得applyOrgCode
	 * @return the applyOrgCode
	 */
	public String getApplyOrgCode() {
		return applyOrgCode;
	}
	
	/**
	 * 获得applyEmpName
	 * @return the applyEmpName
	 */
	public String getApplyEmpName() {
		return applyEmpName;
	}
	
	/**
	 * 获得applyEmpCode
	 * @return the applyEmpCode
	 */
	public String getApplyEmpCode() {
		return applyEmpCode;
	}
	
	/**
	 * 获得telephoneNo
	 * @return the telephoneNo
	 */
	public String getTelephoneNo() {
		return telephoneNo;
	}

	/**
	 * 获得mobilephoneNo
	 * @return the mobilephoneNo
	 */
	public String getMobilephoneNo() {
		return mobilephoneNo;
	}
	
	/**
	 * 设置wayBillNo
	 * @param wayBillNo the wayBillNo to set
	 */
	public String getWayBillNo() {
		return wayBillNo;
	}
	
	/**
	 * 设置orderNo
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	/**
	 * 设置applyTime
	 * @param applyTime the applyTime to set
	 */
	@DateFormat
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	/**
	 * 设置orderType
	 * @param orderType the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	/**
	 * 设置orderVehicleModel
	 * @param orderVehicleModel the orderVehicleModel to set
	 */
	public void setOrderVehicleModel(String orderVehicleModel) {
		this.orderVehicleModel = orderVehicleModel;
	}
	
	/**
	 * 设置dispatchTransDept
	 * @param dispatchTransDept the dispatchTransDept to set
	 */
	public void setDispatchTransDept(String dispatchTransDept) {
		this.dispatchTransDept = dispatchTransDept;
	}
	
	/**
	 * 设置useAddress
	 * @param useAddress the useAddress to set
	 */
	public void setUseAddress(String useAddress) {
		this.useAddress = useAddress;
	}
	
	/**
	 * 设置goodsType
	 * @param goodsType the goodsType to set
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	
	/**
	 * 设置isTailboard
	 * @param isTailboard the isTailboard to set
	 */
	public void setIsTailboard(String isTailboard) {
		this.isTailboard = isTailboard;
	}
	
	/**
	 * 设置predictUseTime
	 * @param predictUseTime the predictUseTime to set
	 */
	@DateFormat
	public void setPredictUseTime(Date predictUseTime) {
		this.predictUseTime = predictUseTime;
	}
	
	/**
	 * 设置goodsName
	 * @param goodsName the goodsName to set
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	/**
	 * 设置weight
	 * @param weight the weight to set
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	
	/**
	 * 设置volume
	 * @param volume the volume to set
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	
	/**
	 * 设置goodsQty
	 * @param goodsQty the goodsQty to set
	 */
	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}
	
	/**
	 * 设置notes
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	/**
	 * 设置status
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * 设置applyOrgName
	 * @param applyOrgName the applyOrgName to set
	 */
	public void setApplyOrgName(String applyOrgName) {
		this.applyOrgName = applyOrgName;
	}
	
	/**
	 * 设置applyOrgCode
	 * @param applyOrgCode the applyOrgCode to set
	 */
	public void setApplyOrgCode(String applyOrgCode) {
		this.applyOrgCode = applyOrgCode;
	}
	
	/**
	 * 设置applyEmpName
	 * @param applyEmpName the applyEmpName to set
	 */
	public void setApplyEmpName(String applyEmpName) {
		this.applyEmpName = applyEmpName;
	}
	
	/**
	 * 设置applyEmpCode
	 * @param applyEmpCode the applyEmpCode to set
	 */
	public void setApplyEmpCode(String applyEmpCode) {
		this.applyEmpCode = applyEmpCode;
	}
	
	/**
	 * 设置telephoneNo
	 * @param telephoneNo the telephoneNo to set
	 */
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}

	/**
	 * 设置mobilephoneNo
	 * @param mobilephoneNo the mobilephoneNo to set
	 */
	public void setMobilephoneNo(String mobilephoneNo) {
		this.mobilephoneNo = mobilephoneNo;
	}
	
	/**
	 * 设置wayBillNo
	 * @param wayBillNo the wayBillNo to set
	 */
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	
	/**
	 * 获得isGroupZone
	 * @return the isGroupZone
	 */	
	public String getIsGroupZone() {
		return isGroupZone;
	}

	/**
	 * 设置isGroupZone
	 * @param isGroupZone the isGroupZone to set
	 */
	public void setIsGroupZone(String isGroupZone) {
		this.isGroupZone = isGroupZone;
	}
	
	/**
	 * 获得useVehicleOrgName
	 * @return the useVehicleOrgName
	 */	
	public String getUseVehicleOrgName() {
		return useVehicleOrgName;
	}

	/**
	 * 设置useVehicleOrgName
	 * @param useVehicleOrgName the useVehicleOrgName to set
	 */
	public void setUseVehicleOrgName(String useVehicleOrgName) {
		this.useVehicleOrgName = useVehicleOrgName;
	}

	/**
	 * 获得useVehicleOrgCode
	 * @return the useVehicleOrgCode
	 */	
	public String getUseVehicleOrgCode() {
		return useVehicleOrgCode;
	}

	/**
	 * 设置useVehicleOrgCode
	 * @param useVehicleOrgCode the useVehicleOrgCode to set
	 */
	public void setUseVehicleOrgCode(String useVehicleOrgCode) {
		this.useVehicleOrgCode = useVehicleOrgCode;
	}
	
	/**
	 * 获得dispatchTransDeptName
	 * @return the dispatchTransDeptName
	 */
	public String getDispatchTransDeptName() {
		return dispatchTransDeptName;
	}

	/**
	 * 设置dispatchTransDeptName
	 * @param dispatchTransDeptName the dispatchTransDeptName to set
	 */
	public void setDispatchTransDeptName(String dispatchTransDeptName) {
		this.dispatchTransDeptName = dispatchTransDeptName;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[orderNo,").append(orderNo).append("]");
		stringBuilder.append("[applyTime,").append(applyTime).append("]");
		stringBuilder.append("[orderType,").append(orderType).append("]");
		stringBuilder.append("[orderVehicleModel,").append(orderVehicleModel).append("]");
		stringBuilder.append("[dispatchTransDept,").append(dispatchTransDept).append("]");
		stringBuilder.append("[useAddress,").append(useAddress).append("]");
		stringBuilder.append("[goodsType,").append(goodsType).append("]");
		stringBuilder.append("[isTailboard,").append(isTailboard).append("]");
		stringBuilder.append("[predictUseTime,").append(predictUseTime).append("]");
		stringBuilder.append("[goodsName,").append(goodsName).append("]");
		stringBuilder.append("[weight,").append(weight).append("]");
		stringBuilder.append("[volume,").append(volume).append("]");
		stringBuilder.append("[goodsQty,").append(goodsQty).append("]");
		stringBuilder.append("[notes,").append(notes).append("]");
		stringBuilder.append("[status,").append(status).append("]");
		stringBuilder.append("[applyOrgName,").append(applyOrgName).append("]");
		stringBuilder.append("[applyOrgCode,").append(applyOrgCode).append("]");
		stringBuilder.append("[applyEmpName,").append(applyEmpName).append("]");
		stringBuilder.append("[applyEmpCode,").append(applyEmpCode).append("]");
		stringBuilder.append("[telephoneNo,").append(telephoneNo).append("]");
		stringBuilder.append("[mobilephoneNo,").append(mobilephoneNo).append("]");
		stringBuilder.append("[wayBillNo,").append(wayBillNo).append("]");
		stringBuilder.append("[isGroupZone,").append(isGroupZone).append("]");
		stringBuilder.append("[useVehicleOrgCode,").append(useVehicleOrgCode).append("]");
		stringBuilder.append("[useVehicleOrgName,").append(useVehicleOrgName).append("]");
		return stringBuilder.toString();
	}

	public String getTopFleetCode() {
		return topFleetCode;
	}

	public void setTopFleetCode(String topFleetCode) {
		this.topFleetCode = topFleetCode;
	}

	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}

	/**
	 * @return the arrivalTime
	 */
	public Date getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * @param arrivalTime the arrivalTime to set
	 */
	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	/**
	 * @return the passTime
	 */
	public Date getPassTime() {
		return passTime;
	}

	/**
	 * @param passTime the passTime to set
	 */
	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}
}