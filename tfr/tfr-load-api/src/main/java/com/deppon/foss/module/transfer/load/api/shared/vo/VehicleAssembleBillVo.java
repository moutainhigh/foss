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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/vo/VehicleAssembleBillVo.java
 *  
 *  FILE NAME          :VehicleAssembleBillVo.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillOptLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryVehicleAssembleBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryVehicleAssembleBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.RewardOrPunishAgreementDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateVehicleAssembleBillDto;

/** 
 * @className: VehicleAssembleBillVo
 * @author: ShiWei shiwei@outlook.com
 * @description: 配载单模块vo类
 * @date: 2012-11-8 下午3:03:16
 * 
 */
public class VehicleAssembleBillVo {

	//返回到前台的交接单list
	private List<QueryHandOverBillDto> handOverBillList;
	//配载单新增界面，查询待配载交接单时传入的查询条件
	private QueryHandOverBillConditionDto queryHandOverBillConditionDto;
	//配载单基本信息实体
	private VehicleAssembleBillEntity baseEntity;
	//配载单中交接单list
	private List<VehicleAssembleBillDetailEntity> billDetailEntityList;
	
	//配载单奖罚协议
	private RewardOrPunishAgreementDto rewardOrPunishDto ;
	
	//接收查询配载单界面传入的查询条件
	private QueryVehicleAssembleBillConditionDto queryConditionDto;
	
	//返回查询配载单结果列表
	private List<QueryVehicleAssembleBillDto> queriedBillList;
	//配载车次号
	private String vehicleAssembleNo;
	//配载单日志列表
	private List<VehicleAssembleBillOptLogEntity> optLogList;
	//配置单下所有的交接单号
	private List<String> handOverBillNos;
	//配载单修改时提交的dto
	private UpdateVehicleAssembleBillDto updateVehicleAssembleBillDto;
	//根据车牌号获取的车辆信息
	private VehicleAssociationDto vehicleInfo;
	//前台传入的车牌号
	private String vehicleNo;
	//前台传入的货柜编号
	private String containerNo;
	//挂牌号
	private String trailerVehicleNo;
	//根据货柜编号获取的货柜信息
	private OwnTruckEntity containerInfo;
	//到达部门(外场编码)
	private String outfieldCode;
	//到达部门是否支持自动分拣
	private OutfieldEntity outfield;
	//配载单发车日期
	private Date leaveTime;
	//配载单到达部门code
	private String destDeptCode;
	//调用外请车约车接口获取的外请车总费用
	private BigDecimal vehicleFee;
	//是否为整车
	private String isCarLoad;
	//是否已做出发付款确认
	private String isDepartPaymentConfirm;
	//司机code
	private String driverCode;
	//司机姓名
	private String driverName;
	//班次
	private String frequencyNo;
	//司机电话
	private String driverTel;
	//修改配载单时，校验是否可以修改司机、车牌号、结算信息等
	private String isStlInfoAndDriverVehicleCanBeModified;
	//查询配载单基本信息时，是来源于修改界面还是详情界面
	private String isModify;
	//修改配载单时，获取到达部门是否支持自动分拣
	private String isSupportAutoSorting;
	//打印配载单时,验证配载单
	private List<String> vehicleAssembleNos;
	//打印配载单时,验证配载单结果, 大于0则提示还有checkPrintVehicleAssembleBillRestlt个配置单没选择
	private Long checkPrintVehicleAssembleBillRestlt;
	/**判断运输合同是否重复打印大于0则是重复打印 **/
	private int checkCarriageContractPrinted;
	/**
	 * 修改界面，获取当前外请车牌号是否为合同车
	 */
	private String isBarginCar;
	/**
	 * 配载类型
	 */
	private String assembleType;
	/**
	 * 外请车约车编号
	 */
	private String inviteNo;
	/**
	 * 运输性质
	 */
	private String transProperty;
	/**
	 * 运行时数
	 */
	private BigDecimal runHours;
	
	/**
	 * 自有车类型
	 */
	private String ownerVehicleType;
	
	/**
	 * 大部门code
	 */
	private String superOrgCode;
	
	/**
	 * 错误信息
	 */
	private String errorMessage;
	
	//在途装车类型
	private String midWayLoadType;
	
	//租车标记状态
	private String isHoding;
	//外请车车价
	private BigDecimal inviteFeetotal;
	
	
	public BigDecimal getInviteFeetotal() {
		return inviteFeetotal;
	}
	public void setInviteFeetotal(BigDecimal inviteFeetotal) {
		this.inviteFeetotal = inviteFeetotal;
	}
	public String getIsHoding() {
		return isHoding;
	}
	public void setIsHoding(String isHoding) {
		this.isHoding = isHoding;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getSuperOrgCode() {
		return superOrgCode;
	}
	public void setSuperOrgCode(String superOrgCode) {
		this.superOrgCode = superOrgCode;
	}
	public String getOwnerVehicleType() {
		return ownerVehicleType;
	}
	public void setOwnerVehicleType(String ownerVehicleType) {
		this.ownerVehicleType = ownerVehicleType;
	}
	public BigDecimal getRunHours() {
		return runHours;
	}
	public void setRunHours(BigDecimal runHours) {
		this.runHours = runHours;
	}
	public String getTransProperty() {
		return transProperty;
	}
	public void setTransProperty(String transProperty) {
		this.transProperty = transProperty;
	}
	public String getInviteNo() {
		return inviteNo;
	}
	public void setInviteNo(String inviteNo) {
		this.inviteNo = inviteNo;
	}
	public String getAssembleType() {
		return assembleType;
	}
	public void setAssembleType(String assembleType) {
		this.assembleType = assembleType;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getIsBarginCar() {
		return isBarginCar;
	}
	public void setIsBarginCar(String isBarginCar) {
		this.isBarginCar = isBarginCar;
	}
	public String getIsSupportAutoSorting() {
		return isSupportAutoSorting;
	}
	public void setIsSupportAutoSorting(String isSupportAutoSorting) {
		this.isSupportAutoSorting = isSupportAutoSorting;
	}
	public String getIsModify() {
		return isModify;
	}
	public void setIsModify(String isModify) {
		this.isModify = isModify;
	}
	public String getIsStlInfoAndDriverVehicleCanBeModified() {
		return isStlInfoAndDriverVehicleCanBeModified;
	}
	public void setIsStlInfoAndDriverVehicleCanBeModified(
			String isStlInfoAndDriverVehicleCanBeModified) {
		this.isStlInfoAndDriverVehicleCanBeModified = isStlInfoAndDriverVehicleCanBeModified;
	}
	public String getDriverTel() {
		return driverTel;
	}
	public void setDriverTel(String driverTel) {
		this.driverTel = driverTel;
	}
	public String getDriverCode() {
		return driverCode;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	public String getFrequencyNo() {
		return frequencyNo;
	}
	public void setFrequencyNo(String frequencyNo) {
		this.frequencyNo = frequencyNo;
	}
	public String getIsDepartPaymentConfirm() {
		return isDepartPaymentConfirm;
	}
	public void setIsDepartPaymentConfirm(String isDepartPaymentConfirm) {
		this.isDepartPaymentConfirm = isDepartPaymentConfirm;
	}
	public String getIsCarLoad() {
		return isCarLoad;
	}
	public void setIsCarLoad(String isCarLoad) {
		this.isCarLoad = isCarLoad;
	}
	public BigDecimal getVehicleFee() {
		return vehicleFee;
	}
	public void setVehicleFee(BigDecimal vehicleFee) {
		this.vehicleFee = vehicleFee;
	}
	@DateFormat
	public Date getLeaveTime() {
		return leaveTime;
	}
	@DateFormat
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}
	public String getDestDeptCode() {
		return destDeptCode;
	}
	public void setDestDeptCode(String destDeptCode) {
		this.destDeptCode = destDeptCode;
	}
	public OutfieldEntity getOutfield() {
		return outfield;
	}
	public void setOutfield(OutfieldEntity outfield) {
		this.outfield = outfield;
	}
	public String getOutfieldCode() {
		return outfieldCode;
	}
	public void setOutfieldCode(String outfieldCode) {
		this.outfieldCode = outfieldCode;
	}
	public OwnTruckEntity getContainerInfo() {
		return containerInfo;
	}
	public void setContainerInfo(OwnTruckEntity containerInfo) {
		this.containerInfo = containerInfo;
	}
	public String getContainerNo() {
		return containerNo;
	}
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public VehicleAssociationDto getVehicleInfo() {
		return vehicleInfo;
	}
	public void setVehicleInfo(VehicleAssociationDto vehicleInfo) {
		this.vehicleInfo = vehicleInfo;
	}
	public UpdateVehicleAssembleBillDto getUpdateVehicleAssembleBillDto() {
		return updateVehicleAssembleBillDto;
	}
	public void setUpdateVehicleAssembleBillDto(
			UpdateVehicleAssembleBillDto updateVehicleAssembleBillDto) {
		this.updateVehicleAssembleBillDto = updateVehicleAssembleBillDto;
	}
	public List<VehicleAssembleBillOptLogEntity> getOptLogList() {
		return optLogList;
	}
	public void setOptLogList(List<VehicleAssembleBillOptLogEntity> optLogList) {
		this.optLogList = optLogList;
	}
	public String getVehicleAssembleNo() {
		return vehicleAssembleNo;
	}
	public void setVehicleAssembleNo(String vehicleAssembleNo) {
		this.vehicleAssembleNo = vehicleAssembleNo;
	}
	public List<QueryVehicleAssembleBillDto> getQueriedBillList() {
		return queriedBillList;
	}
	public void setQueriedBillList(
			List<QueryVehicleAssembleBillDto> queriedBillList) {
		this.queriedBillList = queriedBillList;
	}
	public QueryVehicleAssembleBillConditionDto getQueryConditionDto() {
		return queryConditionDto;
	}
	public void setQueryConditionDto(
			QueryVehicleAssembleBillConditionDto queryConditionDto) {
		this.queryConditionDto = queryConditionDto;
	}
	public QueryHandOverBillConditionDto getQueryHandOverBillConditionDto() {
		return queryHandOverBillConditionDto;
	}
	public void setQueryHandOverBillConditionDto(
			QueryHandOverBillConditionDto queryHandOverBillConditionDto) {
		this.queryHandOverBillConditionDto = queryHandOverBillConditionDto;
	}
	public List<QueryHandOverBillDto> getHandOverBillList() {
		return handOverBillList;
	}
	public void setHandOverBillList(List<QueryHandOverBillDto> handOverBillList) {
		this.handOverBillList = handOverBillList;
	}
	public VehicleAssembleBillEntity getBaseEntity() {
		return baseEntity;
	}
	public void setBaseEntity(VehicleAssembleBillEntity baseEntity) {
		this.baseEntity = baseEntity;
	}
	public List<VehicleAssembleBillDetailEntity> getBillDetailEntityList() {
		return billDetailEntityList;
	}
	public void setBillDetailEntityList(
			List<VehicleAssembleBillDetailEntity> billDetailEntityList) {
		this.billDetailEntityList = billDetailEntityList;
	}
	public List<String> getHandOverBillNos() {
		return handOverBillNos;
	}
	public void setHandOverBillNos(List<String> handOverBillNos) {
		this.handOverBillNos = handOverBillNos;
	}
	public List<String> getVehicleAssembleNos() {
		return vehicleAssembleNos;
	}
	public void setVehicleAssembleNos(List<String> vehicleAssembleNos) {
		this.vehicleAssembleNos = vehicleAssembleNos;
	}
	public Long getCheckPrintVehicleAssembleBillRestlt() {
		return checkPrintVehicleAssembleBillRestlt;
	}
	public void setCheckPrintVehicleAssembleBillRestlt(
			Long checkPrintVehicleAssembleBillRestlt) {
		this.checkPrintVehicleAssembleBillRestlt = checkPrintVehicleAssembleBillRestlt;
	}
	/**   
	 * checkCarriageContractPrinted   
	 *   
	 * @return  the checkCarriageContractPrinted   
	 */
	
	public int getCheckCarriageContractPrinted() {
		return checkCarriageContractPrinted;
	}
	/**   
	 * @param checkCarriageContractPrinted the checkCarriageContractPrinted to set
	 * Date:2013-4-19下午3:26:04
	 */
	public void setCheckCarriageContractPrinted(int checkCarriageContractPrinted) {
		this.checkCarriageContractPrinted = checkCarriageContractPrinted;
	}
	public RewardOrPunishAgreementDto getRewardOrPunishDto() {
		return rewardOrPunishDto;
	}
	public void setRewardOrPunishDto(RewardOrPunishAgreementDto rewardOrPunishDto) {
		this.rewardOrPunishDto = rewardOrPunishDto;
	}
	public String getMidWayLoadType() {
		return midWayLoadType;
	}
	public void setMidWayLoadType(String midWayLoadType) {
		this.midWayLoadType = midWayLoadType;
	}
	
	public String getTrailerVehicleNo() {
		return trailerVehicleNo;
	}
	public void setTrailerVehicleNo(String trailerVehicleNo) {
		this.trailerVehicleNo = trailerVehicleNo;
	}
	
	
}