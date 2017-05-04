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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/VehicleAssembleBillEntity.java
 *  
 *  FILE NAME          :VehicleAssembleBillEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * 配载单实体类
 * @author 045923-foss-shiwei
 * @date 2012-11-8 上午9:45:48
 */
public class VehicleAssembleBillEntity extends BaseEntity{
   
	private static final long serialVersionUID = -5429255536858620047L;

	//发车计划明细ID
    private String truckDepartPlanDetailId;
    //配载车次号
    private String vehicleAssembleNo;
    //货物类型
    private String goodsType;
    //配载类型
    private String assembleType;
    //出发部门编码
    private String origOrgCode;
    //出发部门名称
    private String origOrgName;
    //到达部门名称
    private String destOrgName;
    //到达部门编码
    private String destOrgCode;
    //车牌号
    private String vehicleNo;
    //车辆所有权类别
    private String vehicleOwnerShip;
    //司机姓名
    private String driverName;
    //司机编码
    private String driverCode;
    //司机电话
    private String driverMobilePhone;
    //出发日期
    private Date leaveTime;
    //运输性质
    private String transProperty;
    //运行时数
    private String runHours;
    //装载率
    private String loadingRate;
    //货柜号
    private String containerNo;
    //考核体积
    private BigDecimal examineVolume;
    //额定净空
    private BigDecimal ratedClearance;
    //额定载重
    private BigDecimal ratedLoad;
    //制单人姓名 
    private String createUserName;
    //制单人编号
    private String createUserCode;
    //修改人姓名
    private String modifyUserName;
    //修改人编号
    private String modifyUserCode;
    //备注
    private String note;
    //车辆信息
    private String vehicleMessage;
    //卡车班次
    private String frequencyNo;
    //是否在途装卸
    private String beMidWayLoad;
    //是否最终到达
    private String beFinallyArrive;
    //付款方式
    private String paymentType;
    //总运费
    private BigDecimal feeTotal;
    //预付运费总额
    private BigDecimal prePaidFeeTotal;
    //到付运费总额
    private BigDecimal arriveFeeTotal;
    //装车总金额
    private BigDecimal loadFeeTotal;
    //代收货款
    private BigDecimal collectionFeeTotal;
    //是否押回单
    private String beReturnReceipt;
    //是否时效条款
    private String beTimeLiness;
    //线路 
    private String line;
    //币种
    private String currencyCode;
    //是否生成任务车辆
    private String beCreateTaskTruck;
    //状态
    private int state;
    //车型
    private String vehicleModel;
    /**
     * 外请车约车编号
     */
    private String inviteNo;
    
    // 在途装载类型
    private String midWayLoadType;
    
    /**
     * 外请车总运费修改原因
     */
    private String feeTotalChangeNotes;
    
    //是否与零担合车
    private String beInLTLCar;
    //在途只裝不卸
    private String beMidWayOnlyLoad;
    //中途到达部门
    private String onTheWayDestOrgCode;
    //中途到达部门名称
    private String onTheWayDestOrgName;
    //司机自带货量--重量
    private BigDecimal  driverOfWeight;
    //司机自带货量--体积
    private BigDecimal  driverOfVolumn;
    //适用载重
    private BigDecimal  applicationRatedLoad;
    //适用净空
    private BigDecimal  applicationRatedClearance;
  
	private String beCarSmallUsed;
	
	public String getBeInLTLCar() {
		return beInLTLCar;
	}
	public void setBeInLTLCar(String beInLTLCar) {
		this.beInLTLCar = beInLTLCar;
	}
	public String getOnTheWayDestOrgName() {
		return onTheWayDestOrgName;
	}
	public void setOnTheWayDestOrgName(String onTheWayDestOrgName) {
		this.onTheWayDestOrgName = onTheWayDestOrgName;
	}
	public String getBeMidWayOnlyLoad() {
		return beMidWayOnlyLoad;
	}
	public void setBeMidWayOnlyLoad(String beMidWayOnlyLoad) {
		this.beMidWayOnlyLoad = beMidWayOnlyLoad;
	}
	public String getOnTheWayDestOrgCode() {
		return onTheWayDestOrgCode;
	}
	public void setOnTheWayDestOrgCode(String onTheWayDestOrgCode) {
		this.onTheWayDestOrgCode = onTheWayDestOrgCode;
	}
	public String getBeTimeLiness() {
		return beTimeLiness;
	}
	public void setBeTimeLiness(String beTimeLiness) {
		this.beTimeLiness = beTimeLiness;
	}
	public String getFeeTotalChangeNotes() {
		return feeTotalChangeNotes;
	}
	public void setFeeTotalChangeNotes(String feeTotalChangeNotes) {
		this.feeTotalChangeNotes = feeTotalChangeNotes;
	}
	public String getInviteNo() {
		return inviteNo;
	}
	public void setInviteNo(String inviteNo) {
		this.inviteNo = inviteNo;
	}
	public String getVehicleModel() {
		return vehicleModel;
	}
	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getModifyUserName() {
		return modifyUserName;
	}
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	public String getBeCreateTaskTruck() {
		return beCreateTaskTruck;
	}
	public void setBeCreateTaskTruck(String beCreateTaskTruck) {
		this.beCreateTaskTruck = beCreateTaskTruck;
	}
	public String getVehicleAssembleNo() {
		return vehicleAssembleNo;
	}
	public void setVehicleAssembleNo(String vehicleAssembleNo) {
		this.vehicleAssembleNo = vehicleAssembleNo;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public String getAssembleType() {
		return assembleType;
	}
	public void setAssembleType(String assembleType) {
		this.assembleType = assembleType;
	}
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	public String getOrigOrgName() {
		return origOrgName;
	}
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}
	public String getDestOrgName() {
		return destOrgName;
	}
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}
	public String getDestOrgCode() {
		return destOrgCode;
	}
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getVehicleOwnerShip() {
		return vehicleOwnerShip;
	}
	public void setVehicleOwnerShip(String vehicleOwnerShip) {
		this.vehicleOwnerShip = vehicleOwnerShip;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverCode() {
		return driverCode;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	public String getDriverMobilePhone() {
		return driverMobilePhone;
	}
	public void setDriverMobilePhone(String driverMobilePhone) {
		this.driverMobilePhone = driverMobilePhone;
	}
	@DateFormat
	public Date getLeaveTime() {
		return leaveTime;
	}
	@DateFormat
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}
	public String getTransProperty() {
		return transProperty;
	}
	public void setTransProperty(String transProperty) {
		this.transProperty = transProperty;
	}
	public String getRunHours() {
		return runHours;
	}
	public void setRunHours(String runHours) {
		this.runHours = runHours;
	}
	public String getLoadingRate() {
		return loadingRate;
	}
	public void setLoadingRate(String loadingRate) {
		this.loadingRate = loadingRate;
	}
	public String getContainerNo() {
		return containerNo;
	}
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}
	public BigDecimal getExamineVolume() {
		return examineVolume;
	}
	public void setExamineVolume(BigDecimal examineVolume) {
		this.examineVolume = examineVolume;
	}
	public BigDecimal getRatedClearance() {
		return ratedClearance;
	}
	public void setRatedClearance(BigDecimal ratedClearance) {
		this.ratedClearance = ratedClearance;
	}
	public BigDecimal getRatedLoad() {
		return ratedLoad;
	}
	public void setRatedLoad(BigDecimal ratedLoad) {
		this.ratedLoad = ratedLoad;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getVehicleMessage() {
		return vehicleMessage;
	}
	public void setVehicleMessage(String vehicleMessage) {
		this.vehicleMessage = vehicleMessage;
	}
	public String getFrequencyNo() {
		return frequencyNo;
	}
	public void setFrequencyNo(String frequencyNo) {
		this.frequencyNo = frequencyNo;
	}
	public String getBeMidWayLoad() {
		return beMidWayLoad;
	}
	public void setBeMidWayLoad(String beMidWayLoad) {
		this.beMidWayLoad = beMidWayLoad;
	}
	public String getBeFinallyArrive() {
		return beFinallyArrive;
	}
	public void setBeFinallyArrive(String beFinallyArrive) {
		this.beFinallyArrive = beFinallyArrive;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public BigDecimal getFeeTotal() {
		return feeTotal;
	}
	public void setFeeTotal(BigDecimal feeTotal) {
		this.feeTotal = feeTotal;
	}
	public BigDecimal getPrePaidFeeTotal() {
		return prePaidFeeTotal;
	}
	public void setPrePaidFeeTotal(BigDecimal prePaidFeeTotal) {
		this.prePaidFeeTotal = prePaidFeeTotal;
	}
	public BigDecimal getArriveFeeTotal() {
		return arriveFeeTotal;
	}
	public void setArriveFeeTotal(BigDecimal arriveFeeTotal) {
		this.arriveFeeTotal = arriveFeeTotal;
	}
	public BigDecimal getLoadFeeTotal() {
		return loadFeeTotal;
	}
	public void setLoadFeeTotal(BigDecimal loadFeeTotal) {
		this.loadFeeTotal = loadFeeTotal;
	}
	public BigDecimal getCollectionFeeTotal() {
		return collectionFeeTotal;
	}
	public void setCollectionFeeTotal(BigDecimal collectionFeeTotal) {
		this.collectionFeeTotal = collectionFeeTotal;
	}
	public String getBeReturnReceipt() {
		return beReturnReceipt;
	}
	public void setBeReturnReceipt(String beReturnReceipt) {
		this.beReturnReceipt = beReturnReceipt;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	/**
	 * 获取车辆明细id
	 * 
	 * author：105869-foss-heyongdong
	 * 
	 * date:2013-07-30 
	 */
	public String getTruckDepartPlanDetailId() {
		return truckDepartPlanDetailId;
	}

	/**
	 * 设置车辆明细id
	 * 
	 * author：105869-foss-heyongdong
	 * 
	 * date:2013-07-30 
	 */
	public void setTruckDepartPlanDetailId(String truckDepartPlanDetailId) {
		this.truckDepartPlanDetailId = truckDepartPlanDetailId;
	}
	public String getMidWayLoadType() {
		return midWayLoadType;
	}
	public void setMidWayLoadType(String midWayLoadType) {
		this.midWayLoadType = midWayLoadType;
	}
	public BigDecimal getDriverOfWeight() {
		return driverOfWeight;
	}
	public void setDriverOfWeight(BigDecimal driverOfWeight) {
		this.driverOfWeight = driverOfWeight;
	}
	public BigDecimal getDriverOfVolumn() {
		return driverOfVolumn;
	}
	public void setDriverOfVolumn(BigDecimal driverOfVolumn) {
		this.driverOfVolumn = driverOfVolumn;
	}
	public BigDecimal getApplicationRatedLoad() {
		return applicationRatedLoad;
	}
	public void setApplicationRatedLoad(BigDecimal applicationRatedLoad) {
		this.applicationRatedLoad = applicationRatedLoad;
	}
	public BigDecimal getApplicationRatedClearance() {
		return applicationRatedClearance;
	}
	public void setApplicationRatedClearance(BigDecimal applicationRatedClearance) {
		this.applicationRatedClearance = applicationRatedClearance;
	}
	public String getBeCarSmallUsed() {
		return beCarSmallUsed;
	}
	public void setBeCarSmallUsed(String beCarSmallUsed) {
		this.beCarSmallUsed = beCarSmallUsed;
	}
	
	
	
	
}