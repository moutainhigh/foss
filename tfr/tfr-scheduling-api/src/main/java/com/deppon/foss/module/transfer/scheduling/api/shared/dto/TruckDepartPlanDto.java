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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/TruckDepartPlanDto.java
 * 
 *  FILE NAME     :TruckDepartPlanDto.java
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
/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.shared.dto
 * FILE    NAME: TruckDepartPlanDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
import com.deppon.foss.module.transfer.scheduling.api.define.TruckDepartPlanConstants;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckDepartPlanEntity;

/**
 * 计划Dto
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-11-21 下午5:24:41
 */
public class TruckDepartPlanDto extends TruckDepartPlanEntity {

	private static final long serialVersionUID = 8544259262353567375L;
	/**
	 * 出发部门
	 */
	private String origOrgName;
	/**
	 * 到达部门
	 */
	private String destOrgName;
	/**
	 * 车辆数
	 */
	private BigDecimal carTotal;
	/**
	 * 班次数
	 */
	private BigDecimal frequencyTotal;
	/**
	 * 预测载重合计
	 */
	private BigDecimal weightTotal;
	/**
	 * 计划预计载重
	 */
	private BigDecimal preWeightTotal;
	/**
	 * 载重缺口
	 */
	private BigDecimal weightGapTotal;
	/**
	 * 预测体积合计
	 */
	private BigDecimal volumeTotal;
	/**
	 * 计划预计体积
	 */
	private BigDecimal preVolumeTotal;
	/**
	 * 体积缺口
	 */
	private BigDecimal volumeGapTotal;
	/**
	 * 创建时间从
	 */
	private Date createTimeFrom;
	/**
	 * 创建时间到
	 */
	private Date createTimeTo;
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 自有车数
	 */
	private BigDecimal ownCarTotal;
	/**
	 * 外请车数
	 */
	private BigDecimal outerCarTotal;
	/**
	 * 计划明细列表
	 */
	private List<TruckDepartPlanDetailDto> list;
	/**
	 * 班次状态
	 */
	private List<String> frequencyTypeList;

	/**
	 * (汇总)重量/体积
	 */
	private String weightVolTotal;
	/**
	 * 已计划重量/体积
	 */
	private String preWeightVolTotal;
	/**
	 * 缺口合计
	 */
	private String gapTotal;

	/**
	 * 获取 出发部门.
	 * 
	 * @return the 出发部门
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}

	/**
	 * 设置 出发部门.
	 * 
	 * @param origOrgName
	 *            the new 出发部门
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	/**
	 * 获取 到达部门.
	 * 
	 * @return the 到达部门
	 */
	public String getDestOrgName() {
		return destOrgName;
	}

	/**
	 * 设置 到达部门.
	 * 
	 * @param destOrgName
	 *            the new 到达部门
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	/**
	 * 获取 车辆数.
	 * 
	 * @return the 车辆数
	 */
	public BigDecimal getCarTotal() {
		return carTotal;
	}

	/**
	 * 设置 车辆数.
	 * 
	 * @param carTotal
	 *            the new 车辆数
	 */
	public void setCarTotal(BigDecimal carTotal) {
		this.carTotal = carTotal;
	}

	/**
	 * 获取 班次数.
	 * 
	 * @return the 班次数
	 */
	public BigDecimal getFrequencyTotal() {
		return frequencyTotal;
	}

	/**
	 * 设置 班次数.
	 * 
	 * @param frequencyTotal
	 *            the new 班次数
	 */
	public void setFrequencyTotal(BigDecimal frequencyTotal) {
		this.frequencyTotal = frequencyTotal;
	}

	/**
	 * 获取 预测载重合计.
	 * 
	 * @return the 预测载重合计
	 */
	public BigDecimal getWeightTotal() {
		return weightTotal;
	}

	/**
	 * 设置 预测载重合计.
	 * 
	 * @param weightTotal
	 *            the new 预测载重合计
	 */
	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
	}

	/**
	 * 获取 计划预计载重.
	 * 
	 * @return the 计划预计载重
	 */
	public BigDecimal getPreWeightTotal() {
		return preWeightTotal;
	}

	/**
	 * 设置 计划预计载重.
	 * 
	 * @param preWeightTotal
	 *            the new 计划预计载重
	 */
	public void setPreWeightTotal(BigDecimal preWeightTotal) {
		this.preWeightTotal = preWeightTotal;
	}

	/**
	 * 获取 载重缺口.
	 * 
	 * @return the 载重缺口
	 */
	public BigDecimal getWeightGapTotal() {
		// 重量
		if (this.getWeightTotal() != null) {
			if (this.getPreWeightTotal() != null) {
				weightGapTotal = this.getWeightTotal().subtract(this.getPreWeightTotal());
			} else {
				weightGapTotal = this.getWeightTotal();
			}
		} else {
			if (this.getPreWeightTotal() != null) {
				weightGapTotal = BigDecimal.ZERO.subtract(this.getPreWeightTotal());
			} else {
				weightGapTotal = BigDecimal.ZERO;
			}
		}
		return weightGapTotal.setScale(TruckDepartPlanConstants.SCALE_NUM, RoundingMode.HALF_UP);
	}

	/**
	 * 设置 载重缺口.
	 * 
	 * @param weightGapTotal
	 *            the new 载重缺口
	 */
	public void setWeightGapTotal(BigDecimal weightGapTotal) {
		this.weightGapTotal = weightGapTotal;
	}

	/**
	 * 获取 预测体积合计.
	 * 
	 * @return the 预测体积合计
	 */
	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}

	/**
	 * 设置 预测体积合计.
	 * 
	 * @param volumeTotal
	 *            the new 预测体积合计
	 */
	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}

	/**
	 * 获取 计划预计体积.
	 * 
	 * @return the 计划预计体积
	 */
	public BigDecimal getPreVolumeTotal() {
		return preVolumeTotal;
	}

	/**
	 * 设置 计划预计体积.
	 * 
	 * @param preVolumeTotal
	 *            the new 计划预计体积
	 */
	public void setPreVolumeTotal(BigDecimal preVolumeTotal) {
		this.preVolumeTotal = preVolumeTotal;
	}

	/**
	 * 获取 体积缺口.
	 * 
	 * @return the 体积缺口
	 */
	public BigDecimal getVolumeGapTotal() {
		if (this.getVolumeTotal() != null) {
			if (this.getPreVolumeTotal() != null) {
				volumeGapTotal = this.getVolumeTotal().subtract(this.getPreVolumeTotal());
			} else {
				volumeGapTotal = this.getVolumeTotal();
			}
		} else {
			if (this.getPreVolumeTotal() != null) {
				volumeGapTotal = BigDecimal.ZERO.subtract(this.getPreVolumeTotal());
			} else {
				volumeGapTotal = BigDecimal.ZERO;
			}
		}
		return volumeGapTotal.setScale(TruckDepartPlanConstants.SCALE_NUM, RoundingMode.HALF_UP);
	}

	/**
	 * 设置 体积缺口.
	 * 
	 * @param volumeGapTotal
	 *            the new 体积缺口
	 */
	public void setVolumeGapTotal(BigDecimal volumeGapTotal) {
		this.volumeGapTotal = volumeGapTotal;
	}

	/**
	 * 获取 创建时间从.
	 * 
	 * @return the 创建时间从
	 */
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTimeFrom() {
		return createTimeFrom;
	}

	/**
	 * 设置 创建时间从.
	 * 
	 * @param createTimeFrom
	 *            the new 创建时间从
	 */
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss")
	public void setCreateTimeFrom(Date createTimeFrom) {
		this.createTimeFrom = createTimeFrom;
	}

	/**
	 * 获取 创建时间到.
	 * 
	 * @return the 创建时间到
	 */
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTimeTo() {
		return createTimeTo;
	}

	/**
	 * 设置 创建时间到.
	 * 
	 * @param createTimeTo
	 *            the new 创建时间到
	 */
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss")
	public void setCreateTimeTo(Date createTimeTo) {
		this.createTimeTo = createTimeTo;
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
	 * 获取 自有车数.
	 * 
	 * @return the 自有车数
	 */
	public BigDecimal getOwnCarTotal() {
		return ownCarTotal;
	}

	/**
	 * 设置 自有车数.
	 * 
	 * @param ownCarTotal
	 *            the new 自有车数
	 */
	public void setOwnCarTotal(BigDecimal ownCarTotal) {
		this.ownCarTotal = ownCarTotal;
	}

	/**
	 * 获取 外请车数.
	 * 
	 * @return the 外请车数
	 */
	public BigDecimal getOuterCarTotal() {
		return outerCarTotal;
	}

	/**
	 * 设置 外请车数.
	 * 
	 * @param outerCarTotal
	 *            the new 外请车数
	 */
	public void setOuterCarTotal(BigDecimal outerCarTotal) {
		this.outerCarTotal = outerCarTotal;
	}

	/**
	 * 获取 计划明细列表.
	 * 
	 * @return the 计划明细列表
	 */
	public List<TruckDepartPlanDetailDto> getList() {
		return list;
	}

	/**
	 * 设置 计划明细列表.
	 * 
	 * @param list
	 *            the new 计划明细列表
	 */
	public void setList(List<TruckDepartPlanDetailDto> list) {
		this.list = list;
	}

	/**
	 * 获取 (汇总)重量/体积.
	 * 
	 * @return the (汇总)重量/体积
	 */
	public String getWeightVolTotal() {
		StringBuffer weightVolTotalBuffer = new StringBuffer();
		// 重量
		if (this.getWeightTotal() != null) {
			weightVolTotalBuffer.append(this.getWeightTotal().setScale(TruckDepartPlanConstants.SCALE_NUM,
					RoundingMode.HALF_UP));
		} else {
			weightVolTotalBuffer.append(BigDecimal.ZERO);
		}
		// 加入单位及分隔符
		weightVolTotalBuffer.append(TruckDepartPlanConstants.WEIGHT_UNIT).append(
				TruckDepartPlanConstants.QUANTITY_DESC_SEPERATOR);
		// 体积
		if (this.getVolumeTotal() != null) {
			weightVolTotalBuffer.append(this.getVolumeTotal().setScale(TruckDepartPlanConstants.SCALE_NUM,
					RoundingMode.HALF_UP));
		} else {
			weightVolTotalBuffer.append(BigDecimal.ZERO);
		}
		// 体积单位
		weightVolTotalBuffer.append(TruckDepartPlanConstants.VOLUMN_UNIT);
		weightVolTotal = weightVolTotalBuffer.toString();
		return weightVolTotal;
	}

	/**
	 * 获取 已计划重量/体积.
	 * 
	 * @return the 已计划重量/体积
	 */
	public String getPreWeightVolTotal() {
		StringBuffer preWeightVolTotalBuffer = new StringBuffer();
		// 重量
		if (this.getPreWeightTotal() != null) {
			preWeightVolTotalBuffer.append(this.getPreWeightTotal().setScale(TruckDepartPlanConstants.SCALE_NUM,
					RoundingMode.HALF_UP));
		} else {
			preWeightVolTotalBuffer.append(BigDecimal.ZERO);
		}
		// 加入单位及分隔符
		preWeightVolTotalBuffer.append(TruckDepartPlanConstants.WEIGHT_UNIT).append(
				TruckDepartPlanConstants.QUANTITY_DESC_SEPERATOR);
		// 体积
		if (this.getPreVolumeTotal() != null) {
			preWeightVolTotalBuffer.append(this.getPreVolumeTotal().setScale(TruckDepartPlanConstants.SCALE_NUM,
					RoundingMode.HALF_UP));
		} else {
			preWeightVolTotalBuffer.append(BigDecimal.ZERO);
		}
		// 体积单位
		preWeightVolTotalBuffer.append(TruckDepartPlanConstants.VOLUMN_UNIT);
		preWeightVolTotal = preWeightVolTotalBuffer.toString();
		return preWeightVolTotal;
	}

	/**
	 * 获取 缺口合计.
	 * 
	 * @return the 缺口合计
	 */
	public String getGapTotal() {
		StringBuffer gapTotalBuffer = new StringBuffer();
		gapTotalBuffer.append(this.getWeightGapTotal());
		// 加入单位及分隔符
		gapTotalBuffer.append(TruckDepartPlanConstants.WEIGHT_UNIT).append(
				TruckDepartPlanConstants.QUANTITY_DESC_SEPERATOR);

		gapTotalBuffer.append(this.getVolumeGapTotal());
		// 体积单位
		gapTotalBuffer.append(TruckDepartPlanConstants.VOLUMN_UNIT);
		gapTotal = gapTotalBuffer.toString();
		return gapTotal;
	}

	/**
	 * 设置 (汇总)重量/体积.
	 * 
	 * @param weightVolTotal
	 *            the new (汇总)重量/体积
	 */
	public void setWeightVolTotal(String weightVolTotal) {
		this.weightVolTotal = weightVolTotal;
	}

	/**
	 * 设置 已计划重量/体积.
	 * 
	 * @param preWeightVolTotal
	 *            the new 已计划重量/体积
	 */
	public void setPreWeightVolTotal(String preWeightVolTotal) {
		this.preWeightVolTotal = preWeightVolTotal;
	}

	/**
	 * 设置 缺口合计.
	 * 
	 * @param gapTotal
	 *            the new 缺口合计
	 */
	public void setGapTotal(String gapTotal) {
		this.gapTotal = gapTotal;
	}

	/**
	 * 获取 班次状态.
	 * 
	 * @return the 班次状态
	 */
	public List<String> getFrequencyTypeList() {
		return frequencyTypeList;
	}

	/**
	 * 设置 班次状态.
	 * 
	 * @param frequencyTypeList
	 *            the new 班次状态
	 */
	public void setFrequencyTypeList(List<String> frequencyTypeList) {
		this.frequencyTypeList = frequencyTypeList;
	}

}