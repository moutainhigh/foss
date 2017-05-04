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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/AdjustOutVehicleFeeDto.java
 *  
 *  FILE NAME          :AdjustOutVehicleFeeDto.java
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
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import com.deppon.foss.util.DateUtils;

/**
 * 外请车费用调整 Dto
 * 
 * @author dp-liming
 * @date 2012-11-19 上午11:30:52
 */
public class AdjustOutVehicleFeeDto implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8705585426443444528L;
	


	/**
	 * 交接日期
	 */
	private Date handoverTime;
	
	/**
	 * 开始日期
	 */
	private Date beginTime;
	
	/**
	 * 结束日期
	 */
	private Date endTime;
	
	/**
	 * 开始日期
	 */
	private String beginTimeStr;

	/**
	 * 结束日期
	 */
	private String endTimeStr;

	/**
	 * 司机电话
	 */
	private String driverMobilePhone;

	/**
	 * 装车总金额
	 */
	private BigDecimal loadFeeTotal;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 货物类型
	 */
	private String goodsType;

	/**
	 * 配载类型(区分整车/非整车的外请车)
	 */
	private String assembleType;

	/**
	 * 车辆所有权类别
	 */
	private String vehicleOwnerShip;

	/**
	 * 车辆出发日期
	 */
	private Date leaveTime;

	/**
	 * 付款方式
	 */
	private String paymentType;

	/**
	 * 是否押回单
	 */
	private String beReturnReceipt;

	/**
	 * 币种
	 */
	private String currencyCode;

	/**
	 * Id
	 */
	private String id;

	/**
	 * 配载车次号
	 */
	private String vehicleassembleNo;

	/**
	 * 车牌号
	 */
	private String vehicleNo;

	/**
	 * 主驾驶编号
	 */
	private String driverCode;

	/**
	 * 主驾驶姓名
	 */
	private String driverName;

	/**
	 * 出发部门编号
	 */
	private String origOrgCode;

	/**
	 * 出发部门名称
	 */
	private String origOrgName;

	/**
	 * 到达部门编号
	 */
	private String destOrgCode;

	/**
	 * 到达部门名称
	 */
	private String destOrgName;

	/**
	 * 总运费
	 */
	private BigDecimal feeTotal;

	/**
	 * 预付运费总额
	 */
	private BigDecimal prepaidFeeTotal;

	/**
	 * 到付运费总额
	 */
	private BigDecimal arriveFeeTotal;

	/**
	 * 奖罚类型
	 */
	private String awardType;

	/**
	 * 调整费用
	 */
	private BigDecimal adjustFee;

	/**
	 * 调整原因
	 */
	private String adjustReason;

	/**
	 * 其他原因
	 */
	private String otherReason;

	/**
	 * 审核人编号
	 */
	private String auditorCode;

	/**
	 * 审核人名称
	 */
	private String auditorName;

	/**
	 * 部门经理审核状态
	 */
	private String auditState;

	/**
	 * 审核意见
	 */
	private String auditOpinion;
	
	/**
	 * 是否时效条款
	 */
	private String timelinessClause;
	
	/**
	 * 产生原因
	 */
	private String cause;
	
	/**
	 * 变化时长
	 * 小于0为提前, 大于0为延后
	 * 单位为小时, 保留两位小数
	 */
	private BigDecimal timelinessDuration;

	/**
	 * 变化时长
	 * 小于0为提前, 大于0为延后
	 * 单位为小时, 保留两位小数
	 */
	private String timelinessDurationStr;
	
	/**
	 * 是否在途装车
	 */
	private String  beMidwayload;
	
	/**
	 * 是否最终到达
	 */
	private String befinallyarrive;
	
	/**
	 * 配载单属性, 是否时效条款
	 */
	private String beTimeLiness;
	
	/**
	 * 配载单属性, 运行时长(从线路信息中获取的)
	 */
	private String runHours;
	
	/**
	 * 审批意见
	 * @author 269701--lln--2015-07-12
	 */
	private String approvalOpinion;
	
	/**
	 * 获取 交接日期.
	 *
	 * @return the 交接日期
	 */
	public Date getHandoverTime() {
		return handoverTime;
	}

	/**
	 * 设置 交接日期.
	 *
	 * @param handoverTime the new 交接日期
	 */
	public void setHandoverTime(Date handoverTime) {
		this.handoverTime = handoverTime;
	}

	/**
	 * 获取 司机电话.
	 *
	 * @return the 司机电话
	 */
	public String getDriverMobilePhone() {
		return driverMobilePhone;
	}

	/**
	 * 设置 司机电话.
	 *
	 * @param driverMobilePhone the new 司机电话
	 */
	public void setDriverMobilePhone(String driverMobilePhone) {
		this.driverMobilePhone = driverMobilePhone;
	}

	/**
	 * 获取 装车总金额.
	 *
	 * @return the 装车总金额
	 */
	public BigDecimal getLoadFeeTotal() {
		return loadFeeTotal;
	}

	/**
	 * 设置 装车总金额.
	 *
	 * @param loadFeeTotal the new 装车总金额
	 */
	public void setLoadFeeTotal(BigDecimal loadFeeTotal) {
		this.loadFeeTotal = loadFeeTotal;
	}

	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 设置 运单号.
	 *
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 获取 货物类型.
	 *
	 * @return the 货物类型
	 */
	public String getGoodsType() {
		return goodsType;
	}

	/**
	 * 设置 货物类型.
	 *
	 * @param goodsType the new 货物类型
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	/**
	 * 获取 配载类型(区分整车/非整车的外请车).
	 *
	 * @return the 配载类型(区分整车/非整车的外请车)
	 */
	public String getAssembleType() {
		return assembleType;
	}

	/**
	 * 设置 配载类型(区分整车/非整车的外请车).
	 *
	 * @param assembleType the new 配载类型(区分整车/非整车的外请车)
	 */
	public void setAssembleType(String assembleType) {
		this.assembleType = assembleType;
	}

	/**
	 * 获取 车辆所有权类别.
	 *
	 * @return the 车辆所有权类别
	 */
	public String getVehicleOwnerShip() {
		return vehicleOwnerShip;
	}

	/**
	 * 设置 车辆所有权类别.
	 *
	 * @param vehicleOwnerShip the new 车辆所有权类别
	 */
	public void setVehicleOwnerShip(String vehicleOwnerShip) {
		this.vehicleOwnerShip = vehicleOwnerShip;
	}

	/**
	 * 获取 车辆出发日期.
	 *
	 * @return the 车辆出发日期
	 */
	public Date getLeaveTime() {
		return leaveTime;
	}

	/**
	 * 设置 车辆出发日期.
	 *
	 * @param leaveTime the new 车辆出发日期
	 */
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

	/**
	 * 获取 付款方式.
	 *
	 * @return the 付款方式
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * 设置 付款方式.
	 *
	 * @param paymentType the new 付款方式
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * 获取 是否押回单.
	 *
	 * @return the 是否押回单
	 */
	public String getBeReturnReceipt() {
		return beReturnReceipt;
	}

	/**
	 * 设置 是否押回单.
	 *
	 * @param beReturnReceipt the new 是否押回单
	 */
	public void setBeReturnReceipt(String beReturnReceipt) {
		this.beReturnReceipt = beReturnReceipt;
	}

	/**
	 * 获取 币种.
	 *
	 * @return the 币种
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * 设置 币种.
	 *
	 * @param currencyCode the new 币种
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * 获取 id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置 id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取 配载车次号.
	 *
	 * @return the 配载车次号
	 */
	public String getVehicleassembleNo() {
		return vehicleassembleNo;
	}

	/**
	 * 设置 配载车次号.
	 *
	 * @param vehicleassembleNo the new 配载车次号
	 */
	public void setVehicleassembleNo(String vehicleassembleNo) {
		this.vehicleassembleNo = vehicleassembleNo;
	}

	/**
	 * 获取 车牌号.
	 *
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * 设置 车牌号.
	 *
	 * @param vehicleNo the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 主驾驶编号.
	 *
	 * @return the 主驾驶编号
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * 设置 主驾驶编号.
	 *
	 * @param driverCode the new 主驾驶编号
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * 获取 主驾驶姓名.
	 *
	 * @return the 主驾驶姓名
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * 设置 主驾驶姓名.
	 *
	 * @param driverName the new 主驾驶姓名
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * 获取 出发部门编号.
	 *
	 * @return the 出发部门编号
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	/**
	 * 设置 出发部门编号.
	 *
	 * @param origOrgCode the new 出发部门编号
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	/**
	 * 获取 出发部门名称.
	 *
	 * @return the 出发部门名称
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}

	/**
	 * 设置 出发部门名称.
	 *
	 * @param origOrgName the new 出发部门名称
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	/**
	 * 获取 到达部门编号.
	 *
	 * @return the 到达部门编号
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * 设置 到达部门编号.
	 *
	 * @param destOrgCode the new 到达部门编号
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * 获取 到达部门名称.
	 *
	 * @return the 到达部门名称
	 */
	public String getDestOrgName() {
		return destOrgName;
	}

	/**
	 * 设置 到达部门名称.
	 *
	 * @param destOrgName the new 到达部门名称
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	/**
	 * 获取 总运费.
	 *
	 * @return the 总运费
	 */
	public BigDecimal getFeeTotal() {
		return feeTotal;
	}

	/**
	 * 设置 总运费.
	 *
	 * @param feeTotal the new 总运费
	 */
	public void setFeeTotal(BigDecimal feeTotal) {
		this.feeTotal = feeTotal;
	}

	/**
	 * 获取 预付运费总额.
	 *
	 * @return the 预付运费总额
	 */
	public BigDecimal getPrepaidFeeTotal() {
		return prepaidFeeTotal;
	}

	/**
	 * 设置 预付运费总额.
	 *
	 * @param prepaidFeeTotal the new 预付运费总额
	 */
	public void setPrepaidFeeTotal(BigDecimal prepaidFeeTotal) {
		this.prepaidFeeTotal = prepaidFeeTotal;
	}

	/**
	 * 获取 到付运费总额.
	 *
	 * @return the 到付运费总额
	 */
	public BigDecimal getArriveFeeTotal() {
		return arriveFeeTotal;
	}

	/**
	 * 设置 到付运费总额.
	 *
	 * @param arriveFeeTotal the new 到付运费总额
	 */
	public void setArriveFeeTotal(BigDecimal arriveFeeTotal) {
		this.arriveFeeTotal = arriveFeeTotal;
	}

	/**
	 * 获取 奖罚类型.
	 *
	 * @return the 奖罚类型
	 */
	public String getAwardType() {
		return awardType;
	}

	/**
	 * 设置 奖罚类型.
	 *
	 * @param awardType the new 奖罚类型
	 */
	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}

	/**
	 * 获取 调整费用.
	 *
	 * @return the 调整费用
	 */
	public BigDecimal getAdjustFee() {
		return adjustFee;
	}

	/**
	 * 设置 调整费用.
	 *
	 * @param adjustFee the new 调整费用
	 */
	public void setAdjustFee(BigDecimal adjustFee) {
		this.adjustFee = adjustFee;
	}

	/**
	 * 获取 调整原因.
	 *
	 * @return the 调整原因
	 */
	public String getAdjustReason() {
		return adjustReason;
	}

	/**
	 * 设置 调整原因.
	 *
	 * @param adjustReason the new 调整原因
	 */
	public void setAdjustReason(String adjustReason) {
		this.adjustReason = adjustReason;
	}

	/**
	 * 获取 其他原因.
	 *
	 * @return the 其他原因
	 */
	public String getOtherReason() {
		return otherReason;
	}

	/**
	 * 设置 其他原因.
	 *
	 * @param otherReason the new 其他原因
	 */
	public void setOtherReason(String otherReason) {
		this.otherReason = otherReason;
	}

	/**
	 * 获取 审核人编号.
	 *
	 * @return the 审核人编号
	 */
	public String getAuditorCode() {
		return auditorCode;
	}

	/**
	 * 设置 审核人编号.
	 *
	 * @param auditorCode the new 审核人编号
	 */
	public void setAuditorCode(String auditorCode) {
		this.auditorCode = auditorCode;
	}

	/**
	 * 获取 审核人名称.
	 *
	 * @return the 审核人名称
	 */
	public String getAuditorName() {
		return auditorName;
	}

	/**
	 * 设置 审核人名称.
	 *
	 * @param auditorName the new 审核人名称
	 */
	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	/**
	 * 获取 部门经理审核状态.
	 *
	 * @return the 部门经理审核状态
	 */
	public String getAuditState() {
		return auditState;
	}

	/**
	 * 设置 部门经理审核状态.
	 *
	 * @param auditState the new 部门经理审核状态
	 */
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	/**
	 * 获取 审核意见.
	 *
	 * @return the 审核意见
	 */
	public String getAuditOpinion() {
		return auditOpinion;
	}

	/**
	 * 设置 审核意见.
	 *
	 * @param auditOpinion the new 审核意见
	 */
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	
	/**
	 * 设置 审批意见.
	 * @author 269701
	 * @param approvalOpinion the new 审批意见
	 */
	public void setApprovalOpinion(String approvalOpinion) {
		this.approvalOpinion = approvalOpinion;
	}

	/**
	 * 获取 审批意见.
	 * @author 269701
	 * @return the 审批意见
	 */
	public String getApprovalOpinion() {
		return approvalOpinion;
	}
	
	/**
	 * 获取 是否在途装车.
	 *
	 * @return the 是否在途装车
	 */
	public String getBeMidwayload() {
		return beMidwayload;
	}

	/**
	 * 设置 是否在途装车.
	 *
	 * @param beMidwayload the new 是否在途装车
	 */
	public void setBeMidwayload(String beMidwayload) {
		this.beMidwayload = beMidwayload;
	}

	/**
	 * 获取 是否最终到达.
	 *
	 * @return the 是否最终到达
	 */
	public String getBefinallyarrive() {
		return befinallyarrive;
	}

	/**
	 * 设置 是否最终到达.
	 *
	 * @param befinallyarrive the new 是否最终到达
	 */
	public void setBefinallyarrive(String befinallyarrive) {
		this.befinallyarrive = befinallyarrive;
	}

	/**
	 * @return set the beginTime
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime the beginTime to set
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @return set the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return set the beTimeLiness
	 */
	public String getBeTimeLiness() {
		return beTimeLiness;
	}

	/**
	 * @param beTimeLiness the beTimeLiness to set
	 */
	public void setBeTimeLiness(String beTimeLiness) {
		this.beTimeLiness = beTimeLiness;
	}

	/**
	 * @return set the runHours
	 */
	public String getRunHours() {
		return runHours;
	}

	/**
	 * @param runHours the runHours to set
	 */
	public void setRunHours(String runHours) {
		this.runHours = runHours;
	}

	/**
	 * @return set the timelinessClause
	 */
	public String getTimelinessClause() {
		return timelinessClause;
	}

	/**
	 * @param timelinessClause the timelinessClause to set
	 */
	public void setTimelinessClause(String timelinessClause) {
		this.timelinessClause = timelinessClause;
	}

	/**
	 * @return set the timelinessDuration
	 */
	public BigDecimal getTimelinessDuration() {
		return timelinessDuration;
	}

	/**
	 * @param timelinessDuration the timelinessDuration to set
	 */
	public void setTimelinessDuration(BigDecimal timelinessDuration) {
		this.timelinessDuration = timelinessDuration;
	}

	/**
	 * @return set the timelinessDurationStr
	 */
	public String getTimelinessDurationStr() {
		if(timelinessDuration == null) {
			timelinessDuration = BigDecimal.ZERO;
		}
		DecimalFormat df = new DecimalFormat("0.00");
		if(timelinessDuration.doubleValue() < 0) {
			//提前
			timelinessDurationStr = "提前" + df.format(timelinessDuration.abs()) + "小时";
		} else {
			//延后
			timelinessDurationStr = "延后" + df.format(timelinessDuration) + "小时";
		}
		return timelinessDurationStr;
	}

	/**
	 * @param timelinessDurationStr the timelinessDurationStr to set
	 */
	public void setTimelinessDurationStr(String timelinessDurationStr) {
		this.timelinessDurationStr = timelinessDurationStr;
	}

	/**
	 * @return set the cause
	 */
	public String getCause() {
		return cause;
	}

	/**
	 * @param cause the cause to set
	 */
	public void setCause(String cause) {
		this.cause = cause;
	}

	/**
	 * @return set the beginTimeStr
	 */
	public String getBeginTimeStr() {
		if(beginTime == null) {
			return null;
		}
		return DateUtils.convert(beginTime);
	}

	/**
	 * @param beginTimeStr the beginTimeStr to set
	 */
	public void setBeginTimeStr(String beginTimeStr) {
		this.beginTimeStr = beginTimeStr;
	}

	/**
	 * @return set the endTimeStr
	 */
	public String getEndTimeStr() {
		if(endTime == null) {
			return null;
		}
		return DateUtils.convert(endTime);
	}

	/**
	 * @param endTimeStr the endTimeStr to set
	 */
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
}