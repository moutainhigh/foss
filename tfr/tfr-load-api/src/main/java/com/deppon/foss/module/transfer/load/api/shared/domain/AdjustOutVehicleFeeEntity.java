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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/AdjustOutVehicleFeeEntity.java
 *  
 *  FILE NAME          :AdjustOutVehicleFeeEntity.java
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
import java.text.DecimalFormat;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 外请车费用调整 Entity
 * 
 * @author dp-liming
 * @date 2012-11-19 上午11:30:52
 */
public class AdjustOutVehicleFeeEntity extends BaseEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6456293762992191971L;

	/**
	 * Id
	 */
	private String id;

	/**
	 * 配载车次号
	 */
	private String vehicleassembleNo;

	/**
	 * 配载类型
	 */
	private String assembleType;

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
	 * 司机电话
	 * 
	 * @author 269701
	 */
	private String driverMobilePhone;

	/**
	 * 交接日期
	 */
	private Date handoverTime;

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
	 * 初始金额
	 */
	private BigDecimal initAmt;

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
	 * 审核状态
	 * 
	 * @author 269701 部门经理审核状态改为 审核状态
	 */
	private String auditState;

	/**
	 * 审核意见
	 * 
	 * @author 269701 原来的审批意见改为 审核意见
	 */
	private String auditOpinion;
	/**
	 * 审批意见
	 * 
	 * @author 269701 追加审批状态
	 */
	private String approvalOpinion;

	/**
	 * 产生原因
	 */
	private String cause;

	/**
	 * 是否时效条款
	 */
	private String timelinessClause;

	/**
	 * 变化时长 小于0为提前, 大于0为延后 单位为小时, 保留两位小数
	 */
	private BigDecimal timelinessDuration;

	/**
	 * 变化时长 小于0为提前, 大于0为延后 单位为小时, 保留两位小数
	 */
	private String timelinessDurationStr;

	/**
	 * 未审核记录条数
	 * 
	 * @author 269701--foss--lln
	 * @date 2015--07--16
	 */
	private Integer noAuditCount;
	/**
	 * 审批中记录条数
	 * 
	 * @author 269701--foss--lln
	 * @date 2015--07--16
	 */
	private Integer auditInCount;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.framework.entity.BaseEntity#getId()
	 */
	public String getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
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
	 * @param vehicleassembleNo
	 *            the new 配载车次号
	 */
	public void setVehicleassembleNo(String vehicleassembleNo) {
		this.vehicleassembleNo = vehicleassembleNo;
	}

	/**
	 * 获取 配载类型.
	 * 
	 * @return the 配载类型
	 */
	public String getAssembleType() {
		return assembleType;
	}

	/**
	 * 设置 配载类型.
	 * 
	 * @param assembleType
	 *            the new 配载类型
	 */
	public void setAssembleType(String assembleType) {
		this.assembleType = assembleType;
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
	 * @param vehicleNo
	 *            the new 车牌号
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
	 * @param driverCode
	 *            the new 主驾驶编号
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
	 * @param driverName
	 *            the new 主驾驶姓名
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
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
	 * @param driverMobilePhone
	 *            the new 司机电话
	 */
	public void setDriverMobilePhone(String driverMobilePhone) {
		this.driverMobilePhone = driverMobilePhone;
	}

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
	 * @param handoverTime
	 *            the new 交接日期
	 */
	public void setHandoverTime(Date handoverTime) {
		this.handoverTime = handoverTime;
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
	 * @param origOrgCode
	 *            the new 出发部门编号
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
	 * @param origOrgName
	 *            the new 出发部门名称
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
	 * @param destOrgCode
	 *            the new 到达部门编号
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
	 * @param destOrgName
	 *            the new 到达部门名称
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
	 * @param feeTotal
	 *            the new 总运费
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
	 * @param prepaidFeeTotal
	 *            the new 预付运费总额
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
	 * @param arriveFeeTotal
	 *            the new 到付运费总额
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
	 * @param awardType
	 *            the new 奖罚类型
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
	 * @param adjustFee
	 *            the new 调整费用
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
	 * @param adjustReason
	 *            the new 调整原因
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
	 * @param otherReason
	 *            the new 其他原因
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
	 * @param auditorCode
	 *            the new 审核人编号
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
	 * @param auditorName
	 *            the new 审核人名称
	 */
	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	/**
	 * 获取 审核状态.
	 * 
	 * @return the 审核状态
	 */
	public String getAuditState() {
		return auditState;
	}

	/**
	 * 设置 审核状态.
	 * 
	 * @param auditState
	 *            the new 审核状态
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
	 * @param auditOpinion
	 *            the new 审核意见
	 */
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	/**
	 * 获取 审批意见.
	 * 
	 * @return the 审批意见
	 */
	public String getApprovalOpinion() {
		return approvalOpinion;
	}

	/**
	 * 设置 审批意见.
	 * 
	 * @param approvalOpinion
	 *            the new 审批意见
	 */
	public void setApprovalOpinion(String approvalOpinion) {
		this.approvalOpinion = approvalOpinion;
	}

	/**
	 * @return set the timelinessClause
	 */
	public String getTimelinessClause() {
		return timelinessClause;
	}

	/**
	 * @param timelinessClause
	 *            the timelinessClause to set
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
	 * @param timelinessDuration
	 *            the timelinessDuration to set
	 */
	public void setTimelinessDuration(BigDecimal timelinessDuration) {
		this.timelinessDuration = timelinessDuration;
	}

	/**
	 * 变化时长转换为中文
	 * 
	 * @return set the timelinessDurationStr
	 */
	public String getTimelinessDurationStr() {
		if (timelinessDuration == null) {
			timelinessDuration = BigDecimal.ZERO;
		}
		DecimalFormat df = new DecimalFormat("0.00");
		if (timelinessDuration.doubleValue() < 0) {
			// 提前
			timelinessDurationStr = "提前" + df.format(timelinessDuration.abs())
					+ "小时";
		} else {
			// 延后
			timelinessDurationStr = "延后" + df.format(timelinessDuration) + "小时";
		}
		return timelinessDurationStr;
	}

	/**
	 * @param timelinessDurationStr
	 *            the timelinessDurationStr to set
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
	 * @param cause
	 *            the cause to set
	 */
	public void setCause(String cause) {
		this.cause = cause;
	}

	/**
	 * @return set the initAmt
	 */
	public BigDecimal getInitAmt() {
		return initAmt;
	}

	/**
	 * @param initAmt
	 *            the initAmt to set
	 */
	public void setInitAmt(BigDecimal initAmt) {
		this.initAmt = initAmt;
	}

	/**
	 * 获取 未审核记录条数
	 * 
	 * @return the noAuditCount
	 */
	public Integer getNoAuditCount() {
		return noAuditCount;
	}

	/**
	 * 设置 未审核记录条数
	 * 
	 * @param noAuditCount
	 *            the noAuditCount to set
	 */
	public void setNoAuditCount(Integer noAuditCount) {
		this.noAuditCount = noAuditCount;
	}

	/**
	 * 获取 审批中记录条数
	 * 
	 * @return the auditInCount
	 */
	public Integer getAuditInCount() {
		return auditInCount;
	}

	/**
	 * 设置 审批中记录条数
	 * 
	 * @param auditInCount
	 *            the auditInCount to set
	 */
	public void setAuditInCount(Integer auditInCount) {
		this.auditInCount = auditInCount;
	}

}