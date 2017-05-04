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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/HandOverBillEntity.java
 *  
 *  FILE NAME          :HandOverBillEntity.java
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
 *交接单基本信息实体
 * @author 045923-foss-shiwei
 * @date 2012-10-16 下午2:56:40
 */
public class HandOverBillEntity extends BaseEntity {

	private static final long serialVersionUID = -1528734821764517108L;

	/**
	 * 交接单编号
	 */
    private String handOverBillNo;
    /**
     * 出发部门编号
     */
    private String departDeptCode;
    /**
     * 出发部门名称
     */
    private String departDept;
    /**
     * 到达部门/外发代理编码
     */
    private String arriveDeptCode;
    /**
     * 到达部门/外发代理名称
     */
    private String arriveDept;
    /**
     * 司机电话
     */
    private String driverTel;
    /**
     * 司机姓名
     */
    private String driverName;
    /**
     * 司机编码
     */
    private String driver;
    /**
     * 车牌号
     */
    private String vehicleNo;
    /**
     * 装车完成时间
     */
    private Date loadEndTime;
    /**
     * 货物类型
     */
    private String goodsType;
    /**
     * 交接类型
     */
    private String handOverType;
    /**
     * 是否代理上门接货
     */
    private String isAgencyVisit;
    /**
     * 制单人编号
     */
    private String createUserCode;
    /**
     * 制单人名称
     */
    private String createUserName;
    /**
     * 备注
     */
    private String note;
    /**
     * 是否预配交接单
     */
    private String isPreHandOverBill;
    /**
     * 总件数
     */
    private Integer goodsQtyTotal;
    /**
     * 总票数
     */
    private Integer waybillQtyTotal;
    /**
     * 总重量
     */
    private BigDecimal weightTotal;
    /**
     * 总体积
     */
    private BigDecimal volumeTotal;
    /**
     * 总金额
     */
    private BigDecimal moneyTotal;
    /**
     * 卡货票数
     */
    private Integer fastWaybillQty;
    /**
     * 线路名称
     */
    private String lineName;
    /**
     * 线路虚拟编码
     */
    private String lineCode;
    /**
     * 班次
     */
    private String frequencyNo;
    /**
     * 装车任务编号
     */
    private String loadTaskNo;
    /**
     * 发车计划ID
     */
    private String truckDepartPlanDetailId;
    /**
     * 交接时间
     */
    private Date handOverTime;
    /**
     * 修改人
     */
    private String modifyUserCode;
    /**
     * 修改人编号
     */
    private String modifyUserName;
    /**
     * 是否PDA扫描生成
     */
    private String isCreatedByPDA;
    /**
     * 交接单状态
     */
    private int handOverBillState;
    /**
     * 代收货款总额
     */
    private BigDecimal codAmountTotal;
    /**
     * 是否整车
     */
    private String isCarLoad;
    /**
     * 是否生成任务车辆明细信息(N，未生成；Y，已生成)
     */
    private String isCreateTaskTruck;
    /**
     * 币种
     */
    private String currencyCode;
    
    /**
     * 待办事项是否已漂移
     */
    private String beToDoDrifted;
    
    /**
     * 精准空运票数
     */
    private Integer waybillQtyAF;
    
    /**
     * 精准空运重量
     */
    private BigDecimal goodsWeightAF;
    
    /**
     * 精准空运体积
     */
    private BigDecimal goodsVolumeAF;
    
    /**
     * 精准卡航票数
     */
    private Integer waybillQtyFLF;
    
    /**
     * 精准卡航重量
     */
    private BigDecimal goodsWeightFLF;
    
    /**
     * 精准卡航体积
     */
    private BigDecimal goodsVolumeFLF;
    
    /**
     * 精准城运票数
     */
    private Integer waybillQtyFSF;
    
    /**
     * 精准城运重量
     */
    private BigDecimal goodsWeightFSF;
    
    /**
     * 精准城运体积
     */
    private BigDecimal goodsVolumeFSF;
    
    /**
     * 大票卡航票数
     */
    private Integer waybillQtyBGFLF;
    
    /**
     * 大票卡航重量
     */
    private BigDecimal goodsWeightBGFLF;
    
    /**
     * 大票卡航体积
     */
    private BigDecimal goodsVolumeBGFLF;
    
    /**
     * 大票城运票数
     */
    private Integer waybillQtyBGFSF;
    
    /**
     * 大票城运重量
     */
    private BigDecimal goodsWeightBGFSF;
    
    /**
     * 大票城运体积
     */
    private BigDecimal goodsVolumeBGFSF;
    
    
    /**
     * 是否包交接单
     */
    private String bePackage;
    
    /**
     * 转货类型
     * */
    private String tranGoodsType;
    
    /**
     * 是否挂牌号
     * */
    private String beTrailerVehicleNo;
    
    
	public String getBeTrailerVehicleNo() {
		return beTrailerVehicleNo;
	}
	public void setBeTrailerVehicleNo(String beTrailerVehicleNo) {
		this.beTrailerVehicleNo = beTrailerVehicleNo;
	}
	public String getBePackage() {
		return bePackage;
	}
	public void setBePackage(String bePackage) {
		this.bePackage = bePackage;
	}
	public Integer getWaybillQtyAF() {
		return waybillQtyAF;
	}
	public void setWaybillQtyAF(Integer waybillQtyAF) {
		this.waybillQtyAF = waybillQtyAF;
	}
	public BigDecimal getGoodsWeightAF() {
		return goodsWeightAF;
	}
	public void setGoodsWeightAF(BigDecimal goodsWeightAF) {
		this.goodsWeightAF = goodsWeightAF;
	}
	public BigDecimal getGoodsVolumeAF() {
		return goodsVolumeAF;
	}
	public void setGoodsVolumeAF(BigDecimal goodsVolumeAF) {
		this.goodsVolumeAF = goodsVolumeAF;
	}
	public Integer getWaybillQtyFLF() {
		return waybillQtyFLF;
	}
	public void setWaybillQtyFLF(Integer waybillQtyFLF) {
		this.waybillQtyFLF = waybillQtyFLF;
	}
	public BigDecimal getGoodsWeightFLF() {
		return goodsWeightFLF;
	}
	public void setGoodsWeightFLF(BigDecimal goodsWeightFLF) {
		this.goodsWeightFLF = goodsWeightFLF;
	}
	public BigDecimal getGoodsVolumeFLF() {
		return goodsVolumeFLF;
	}
	public void setGoodsVolumeFLF(BigDecimal goodsVolumeFLF) {
		this.goodsVolumeFLF = goodsVolumeFLF;
	}
	public Integer getWaybillQtyFSF() {
		return waybillQtyFSF;
	}
	public void setWaybillQtyFSF(Integer waybillQtyFSF) {
		this.waybillQtyFSF = waybillQtyFSF;
	}
	public BigDecimal getGoodsWeightFSF() {
		return goodsWeightFSF;
	}
	public void setGoodsWeightFSF(BigDecimal goodsWeightFSF) {
		this.goodsWeightFSF = goodsWeightFSF;
	}
	public BigDecimal getGoodsVolumeFSF() {
		return goodsVolumeFSF;
	}
	public void setGoodsVolumeFSF(BigDecimal goodsVolumeFSF) {
		this.goodsVolumeFSF = goodsVolumeFSF;
	}
	public String getBeToDoDrifted() {
		return beToDoDrifted;
	}
	public void setBeToDoDrifted(String beToDoDrifted) {
		this.beToDoDrifted = beToDoDrifted;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getFrequencyNo() {
		return frequencyNo;
	}
	public void setFrequencyNo(String frequencyNo) {
		this.frequencyNo = frequencyNo;
	}
	public String getIsCreateTaskTruck() {
		return isCreateTaskTruck;
	}
	public void setIsCreateTaskTruck(String isCreateTaskTruck) {
		this.isCreateTaskTruck = isCreateTaskTruck;
	}
	public BigDecimal getCodAmountTotal() {
		return codAmountTotal;
	}
	public void setCodAmountTotal(BigDecimal codAmountTotal) {
		this.codAmountTotal = codAmountTotal;
	}
	public String getIsCarLoad() {
		return isCarLoad;
	}
	public void setIsCarLoad(String isCarLoad) {
		this.isCarLoad = isCarLoad;
	}
	public int getHandOverBillState() {
		return handOverBillState;
	}
	public void setHandOverBillState(int handOverBillState) {
		this.handOverBillState = handOverBillState;
	}
	public String getLineCode() {
		return lineCode;
	}
	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
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
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	@DateFormat
	public Date getHandOverTime() {
		return handOverTime;
	}
	@DateFormat
	public void setHandOverTime(Date handOverTime) {
		this.handOverTime = handOverTime;
	}
	public String getTruckDepartPlanDetailId() {
		return truckDepartPlanDetailId;
	}
	public void setTruckDepartPlanDetailId(String truckDepartPlanDetailId) {
		this.truckDepartPlanDetailId = truckDepartPlanDetailId;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	public Integer getWaybillQtyTotal() {
		return waybillQtyTotal;
	}
	public void setWaybillQtyTotal(Integer waybillQtyTotal) {
		this.waybillQtyTotal = waybillQtyTotal;
	}
	public BigDecimal getWeightTotal() {
		return weightTotal;
	}
	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
	}
	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}
	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}
	public Integer getFastWaybillQty() {
		return fastWaybillQty;
	}
	public void setFastWaybillQty(Integer fastWaybillQty) {
		this.fastWaybillQty = fastWaybillQty;
	}
	public String getLoadTaskNo() {
		return loadTaskNo;
	}
	public void setLoadTaskNo(String loadTaskNo) {
		this.loadTaskNo = loadTaskNo;
	}
	public String getDepartDeptCode() {
		return departDeptCode;
	}
	public void setDepartDeptCode(String departDeptCode) {
		this.departDeptCode = departDeptCode;
	}
	public String getDepartDept() {
		return departDept;
	}
	public void setDepartDept(String departDept) {
		this.departDept = departDept;
	}
	public String getArriveDeptCode() {
		return arriveDeptCode;
	}
	public void setArriveDeptCode(String arriveDeptCode) {
		this.arriveDeptCode = arriveDeptCode;
	}
	public String getArriveDept() {
		return arriveDept;
	}
	public void setArriveDept(String arriveDept) {
		this.arriveDept = arriveDept;
	}
	public String getDriverTel() {
		return driverTel;
	}
	public void setDriverTel(String driverTel) {
		this.driverTel = driverTel;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	@DateFormat
	public Date getLoadEndTime() {
		return loadEndTime;
	}
	@DateFormat
	public void setLoadEndTime(Date loadEndTime) {
		this.loadEndTime = loadEndTime;
	}
	public String getHandOverType() {
		return handOverType;
	}
	public void setHandOverType(String handOverType) {
		this.handOverType = handOverType;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getHandOverBillNo() {
		return handOverBillNo;
	}
	public void setHandOverBillNo(String handOverBillNo) {
		this.handOverBillNo = handOverBillNo;
	}
	public BigDecimal getMoneyTotal() {
		return moneyTotal;
	}
	public void setMoneyTotal(BigDecimal moneyTotal) {
		this.moneyTotal = moneyTotal;
	}
	public String getIsAgencyVisit() {
		return isAgencyVisit;
	}
	public void setIsAgencyVisit(String isAgencyVisit) {
		this.isAgencyVisit = isAgencyVisit;
	}
	public String getIsPreHandOverBill() {
		return isPreHandOverBill;
	}
	public void setIsPreHandOverBill(String isPreHandOverBill) {
		this.isPreHandOverBill = isPreHandOverBill;
	}
	public String getIsCreatedByPDA() {
		return isCreatedByPDA;
	}
	public void setIsCreatedByPDA(String isCreatedByPDA) {
		this.isCreatedByPDA = isCreatedByPDA;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getTranGoodsType() {
		return tranGoodsType;
	}
	public void setTranGoodsType(String tranGoodsType) {
		this.tranGoodsType = tranGoodsType;
	}
	public Integer getWaybillQtyBGFLF() {
		return waybillQtyBGFLF;
	}
	public void setWaybillQtyBGFLF(Integer waybillQtyBGFLF) {
		this.waybillQtyBGFLF = waybillQtyBGFLF;
	}
	public BigDecimal getGoodsWeightBGFLF() {
		return goodsWeightBGFLF;
	}
	public void setGoodsWeightBGFLF(BigDecimal goodsWeightBGFLF) {
		this.goodsWeightBGFLF = goodsWeightBGFLF;
	}
	public BigDecimal getGoodsVolumeBGFLF() {
		return goodsVolumeBGFLF;
	}
	public void setGoodsVolumeBGFLF(BigDecimal goodsVolumeBGFLF) {
		this.goodsVolumeBGFLF = goodsVolumeBGFLF;
	}
	public Integer getWaybillQtyBGFSF() {
		return waybillQtyBGFSF;
	}
	public void setWaybillQtyBGFSF(Integer waybillQtyBGFSF) {
		this.waybillQtyBGFSF = waybillQtyBGFSF;
	}
	public BigDecimal getGoodsWeightBGFSF() {
		return goodsWeightBGFSF;
	}
	public void setGoodsWeightBGFSF(BigDecimal goodsWeightBGFSF) {
		this.goodsWeightBGFSF = goodsWeightBGFSF;
	}
	public BigDecimal getGoodsVolumeBGFSF() {
		return goodsVolumeBGFSF;
	}
	public void setGoodsVolumeBGFSF(BigDecimal goodsVolumeBGFSF) {
		this.goodsVolumeBGFSF = goodsVolumeBGFSF;
	}

}