/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/vo/DeliverbillVo.java
 * 
 * FILE NAME        	: DeliverbillVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillNewDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DriverDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoadGaprepDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoadGaprepDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoadGaprepWaybillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoadTaskDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoadTaskDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoaderDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ScanDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillToArrangeDto;

/**
 * 派送单VO.
 *
 * @author ibm-
 * 		wangxiexu
 * @date 2012-10-18 
 * 		下午1:47:14
 * @since
 * @version
 */
public class DeliverbillVo implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7293716261043287112L;

	/** 派送单查询条件. */
	private DeliverbillDto deliverbillDto;

	/** 派送单查询结果列表. */
	private List<DeliverbillDto> deliverbillDtoList;

	/** 派送单明细列表. */
	private List<DeliverbillDetailEntity> deliverbillDetailList;

	/** 派送单. */
	private DeliverbillEntity deliverbill = new DeliverbillEntity();

	/** 派送单明细. */
	private DeliverbillDetailEntity deliverbillDetail;
	
	/** 派送单明细ID. */
	private String deliverbillDetailIds;

	/** 待排运单查询条件. */
	private WaybillToArrangeDto waybillToArrangeDto;

	/** 待排运单列表. */
	private List<WaybillToArrangeDto> waybillToArrangeDtoList;

	/** 车辆信息. */
	private VehicleAssociationDto vehicleAssociationDto;

	/** 派送单关联的装车任务信息(包括装车任务ID/编码，差错报告ID). */
	private LoadTaskDto loadTaskDto = new LoadTaskDto();

	/** 派送装车任务明细列表. */
	private List<LoadTaskDetailDto> loadTaskDetailList;

	/** 派送装车扫描明细. */
	private List<ScanDetailDto> scanDetailList;

	/** 派送装车差异报告明细. */
	private List<LoadGaprepWaybillDto> loadGaprepWaybillList;

	/** 派送装车任务明细ID. */
	private String loadTaskDetailId;

	/** 运单编号，少货明细查询条件. */
	private String waybillNo;

	/** 装车差异报告少货明细列表. */
	private List<LoadGaprepDetailDto> loadGaprepDetailList;

	/** 装车人列表. */
	private List<LoaderDto> loaderList;

	/** 历史差异报告列表. */
	private List<LoadGaprepDto> loadGaprepList;

	/** 查询公司司机条件Dto. */
	private DriverDto driverDto = new DriverDto();

	/** 公司司机列表. */
	private List<DriverDto> driverList;

	/** 派送单明细列表(用于查询未通知客户派送明细列表，到达联件数与排单件数不一致列表). */
	private List<DeliverbillDetailDto> deliverbillDetailDtoList;

	/** 库存件数与排单件数不一致列表. */
	private List<DeliverbillDetailDto> qtyInconsistentDeliverbillDetailList;

	/** 车辆体积装载率阈值(下限). */
	private double volumeLowerThreshold;

	/** 车辆重量装载率阈值(下限). */
	private double weightLowerThreshold;
	/**
	 * 运单号集合
	 */
	private List<String> waybillNos;
	/**
	 * 网上支付未支付完全运单号集合
	 */
	private List<String> notPayByOLWaybillNos;
	
	/**
	 * 新增更改单未处理集合
	 */
	private List<String> notWaybillrfcNos;
	public List<String> getNotWaybillrfcNos() {
		return notWaybillrfcNos;
	}

	public void setNotWaybillrfcNos(List<String> notWaybillrfcNos) {
		this.notWaybillrfcNos = notWaybillrfcNos;
	}

	/**
	 * 派送单编号集合
	 */
	private List<String> deliverbillNos;

	/**
	 * Gets the 派送单查询条件.
	 *
	 * @return the 派送单查询条件
	 */
	public DeliverbillDto getDeliverbillDto() {
		return deliverbillDto;
	}

	/**
	 * Sets the 派送单查询条件.
	 *
	 * @param deliverbillDto the new 派送单查询条件
	 */
	public void setDeliverbillDto(DeliverbillDto deliverbillDto) {
		this.deliverbillDto = deliverbillDto;
	}

	/**
	 * Gets the 派送单查询结果列表.
	 *
	 * @return the 派送单查询结果列表
	 */
	public List<DeliverbillDto> getDeliverbillDtoList() {
		return deliverbillDtoList;
	}

	/**
	 * Sets the 派送单查询结果列表.
	 *
	 * @param deliverbillDtoList the new 派送单查询结果列表
	 */
	public void setDeliverbillDtoList(List<DeliverbillDto> deliverbillDtoList) {
		this.deliverbillDtoList = deliverbillDtoList;
	}

	/**
	 * Gets the 派送单明细列表.
	 *
	 * @return the 派送单明细列表
	 */
	public List<DeliverbillDetailEntity> getDeliverbillDetailList() {
		return deliverbillDetailList;
	}

	/**
	 * Sets the 派送单明细列表.
	 *
	 * @param deliverbillDetailList the new 派送单明细列表
	 */
	public void setDeliverbillDetailList(
			List<DeliverbillDetailEntity> deliverbillDetailList) {
		this.deliverbillDetailList = deliverbillDetailList;
	}

	/**
	 * Gets the 派送单.
	 *
	 * @return the 派送单
	 */
	public DeliverbillEntity getDeliverbill() {
		return deliverbill;
	}

	/**
	 * Sets the 派送单.
	 *
	 * @param deliverbill the new 派送单
	 */
	public void setDeliverbill(DeliverbillEntity deliverbill) {
		this.deliverbill = deliverbill;
	}

	/**
	 * Gets the 派送单明细ID.
	 *
	 * @return the 派送单明细ID
	 */
	public String getDeliverbillDetailIds() {
		return deliverbillDetailIds;
	}

	/**
	 * Sets the 派送单明细ID.
	 *
	 * @param deliverbillDetailIds the new 派送单明细ID
	 */
	public void setDeliverbillDetailIds(String deliverbillDetailIds) {
		this.deliverbillDetailIds = deliverbillDetailIds;
	}

	/**
	 * Gets the 派送单明细.
	 *
	 * @return the 派送单明细
	 */
	public DeliverbillDetailEntity getDeliverbillDetail() {
		return deliverbillDetail;
	}

	/**
	 * Sets the 派送单明细.
	 *
	 * @param deliverbillDetail the new 派送单明细
	 */
	public void setDeliverbillDetail(DeliverbillDetailEntity deliverbillDetail) {
		this.deliverbillDetail = deliverbillDetail;
	}

	/**
	 * Gets the 待排运单查询条件.
	 *
	 * @return the 待排运单查询条件
	 */
	public WaybillToArrangeDto getWaybillToArrangeDto() {
		return waybillToArrangeDto;
	}

	/**
	 * Sets the 待排运单查询条件.
	 *
	 * @param waybillToArrangeDto the new 待排运单查询条件
	 */
	public void setWaybillToArrangeDto(WaybillToArrangeDto waybillToArrangeDto) {
		this.waybillToArrangeDto = waybillToArrangeDto;
	}

	/**
	 * Gets the 待排运单列表.
	 *
	 * @return the 待排运单列表
	 */
	public List<WaybillToArrangeDto> getWaybillToArrangeDtoList() {
		return waybillToArrangeDtoList;
	}

	/**
	 * Sets the 待排运单列表.
	 *
	 * @param waybillToArrangeDtoList the new 待排运单列表
	 */
	public void setWaybillToArrangeDtoList(
			List<WaybillToArrangeDto> waybillToArrangeDtoList) {
		this.waybillToArrangeDtoList = waybillToArrangeDtoList;
	}

	/**
	 * Gets the 车辆信息.
	 *
	 * @return the 车辆信息
	 */
	public VehicleAssociationDto getVehicleAssociationDto() {
		return vehicleAssociationDto;
	}

	/**
	 * Sets the 车辆信息.
	 *
	 * @param vehicleAssociationDto the new 车辆信息
	 */
	public void setVehicleAssociationDto(
			VehicleAssociationDto vehicleAssociationDto) {
		this.vehicleAssociationDto = vehicleAssociationDto;
	}

	/**
	 * Gets the 派送装车任务明细列表.
	 *
	 * @return the 派送装车任务明细列表
	 */
	public List<LoadTaskDetailDto> getLoadTaskDetailList() {
		return loadTaskDetailList;
	}

	/**
	 * Sets the 派送装车任务明细列表.
	 *
	 * @param loadTaskDetailList the new 派送装车任务明细列表
	 */
	public void setLoadTaskDetailList(List<LoadTaskDetailDto> loadTaskDetailList) {
		this.loadTaskDetailList = loadTaskDetailList;
	}

	/**
	 * Gets the 派送装车差异报告明细.
	 *
	 * @return the 派送装车差异报告明细
	 */
	public List<LoadGaprepWaybillDto> getLoadGaprepWaybillList() {
		return loadGaprepWaybillList;
	}

	/**
	 * Sets the 派送装车差异报告明细.
	 *
	 * @param loadGaprepWaybillList the new 派送装车差异报告明细
	 */
	public void setLoadGaprepWaybillList(
			List<LoadGaprepWaybillDto> loadGaprepWaybillList) {
		this.loadGaprepWaybillList = loadGaprepWaybillList;
	}

	/**
	 * Gets the 派送装车扫描明细.
	 *
	 * @return the 派送装车扫描明细
	 */
	public List<ScanDetailDto> getScanDetailList() {
		return scanDetailList;
	}

	/**
	 * Sets the 派送装车扫描明细.
	 *
	 * @param scanDetailList the new 派送装车扫描明细
	 */
	public void setScanDetailList(List<ScanDetailDto> scanDetailList) {
		this.scanDetailList = scanDetailList;
	}

	/**
	 * Gets the 派送装车任务明细ID.
	 *
	 * @return the 派送装车任务明细ID
	 */
	public String getLoadTaskDetailId() {
		return loadTaskDetailId;
	}

	/**
	 * Sets the 派送装车任务明细ID.
	 *
	 * @param loadTaskDetailId the new 派送装车任务明细ID
	 */
	public void setLoadTaskDetailId(String loadTaskDetailId) {
		this.loadTaskDetailId = loadTaskDetailId;
	}

	/**
	 * Gets the 运单编号，少货明细查询条件.
	 *
	 * @return the 运单编号，少货明细查询条件
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * Sets the 运单编号，少货明细查询条件.
	 *
	 * @param waybillNo the new 运单编号，少货明细查询条件
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * Gets the 派送单关联的装车任务信息(包括装车任务ID/编码，差错报告ID).
	 *
	 * @return the 派送单关联的装车任务信息(包括装车任务ID/编码，差错报告ID)
	 */
	public LoadTaskDto getLoadTaskDto() {
		return loadTaskDto;
	}

	/**
	 * Sets the 派送单关联的装车任务信息(包括装车任务ID/编码，差错报告ID).
	 *
	 * @param loadTaskDto the new 派送单关联的装车任务信息(包括装车任务ID/编码，差错报告ID)
	 */
	public void setLoadTaskDto(LoadTaskDto loadTaskDto) {
		this.loadTaskDto = loadTaskDto;
	}

	/**
	 * Gets the 装车差异报告少货明细列表.
	 *
	 * @return the 装车差异报告少货明细列表
	 */
	public List<LoadGaprepDetailDto> getLoadGaprepDetailList() {
		return loadGaprepDetailList;
	}

	/**
	 * Sets the 装车差异报告少货明细列表.
	 *
	 * @param loadGaprepDetailList the new 装车差异报告少货明细列表
	 */
	public void setLoadGaprepDetailList(
			List<LoadGaprepDetailDto> loadGaprepDetailList) {
		this.loadGaprepDetailList = loadGaprepDetailList;
	}

	/**
	 * Gets the 装车人列表.
	 *
	 * @return the 装车人列表
	 */
	public List<LoaderDto> getLoaderList() {
		return loaderList;
	}

	/**
	 * Sets the 装车人列表.
	 *
	 * @param loaderList the new 装车人列表
	 */
	public void setLoaderList(List<LoaderDto> loaderList) {
		this.loaderList = loaderList;
	}

	/**
	 * Gets the 历史差异报告列表.
	 *
	 * @return the 历史差异报告列表
	 */
	public List<LoadGaprepDto> getLoadGaprepList() {
		return loadGaprepList;
	}

	/**
	 * Sets the 历史差异报告列表.
	 *
	 * @param loadGaprepList the new 历史差异报告列表
	 */
	public void setLoadGaprepList(List<LoadGaprepDto> loadGaprepList) {
		this.loadGaprepList = loadGaprepList;
	}

	/**
	 * Gets the 查询公司司机条件Dto.
	 *
	 * @return the 查询公司司机条件Dto
	 */
	public DriverDto getDriverDto() {
		return driverDto;
	}

	/**
	 * Sets the 查询公司司机条件Dto.
	 *
	 * @param driverDto the new 查询公司司机条件Dto
	 */
	public void setDriverDto(DriverDto driverDto) {
		this.driverDto = driverDto;
	}

	/**
	 * Gets the 公司司机列表.
	 *
	 * @return the 公司司机列表
	 */
	public List<DriverDto> getDriverList() {
		return driverList;
	}

	/**
	 * Sets the 公司司机列表.
	 *
	 * @param driverList the new 公司司机列表
	 */
	public void setDriverList(List<DriverDto> driverList) {
		this.driverList = driverList;
	}

	/**
	 * Gets the 派送单明细列表(用于查询未通知客户派送明细列表，到达联件数与排单件数不一致列表).
	 *
	 * @return the 派送单明细列表(用于查询未通知客户派送明细列表，到达联件数与排单件数不一致列表)
	 */
	public List<DeliverbillDetailDto> getDeliverbillDetailDtoList() {
		return deliverbillDetailDtoList;
	}

	/**
	 * Sets the 派送单明细列表(用于查询未通知客户派送明细列表，到达联件数与排单件数不一致列表).
	 *
	 * @param deliverbillDetailDtoList the new 派送单明细列表(用于查询未通知客户派送明细列表，到达联件数与排单件数不一致列表)
	 */
	public void setDeliverbillDetailDtoList(
			List<DeliverbillDetailDto> deliverbillDetailDtoList) {
		this.deliverbillDetailDtoList = deliverbillDetailDtoList;
	}

	/**
	 * Gets the 车辆体积装载率阈值(下限).
	 *
	 * @return the 车辆体积装载率阈值(下限)
	 */
	public double getVolumeLowerThreshold() {
		return volumeLowerThreshold;
	}

	/**
	 * Sets the 车辆体积装载率阈值(下限).
	 *
	 * @param volumeLowerThreshold the new 车辆体积装载率阈值(下限)
	 */
	public void setVolumeLowerThreshold(double volumeLowerThreshold) {
		this.volumeLowerThreshold = volumeLowerThreshold;
	}

	/**
	 * Gets the 车辆重量装载率阈值(下限).
	 *
	 * @return the 车辆重量装载率阈值(下限)
	 */
	public double getWeightLowerThreshold() {
		return weightLowerThreshold;
	}

	/**
	 * Sets the 车辆重量装载率阈值(下限).
	 *
	 * @param weightLowerThreshold the new 车辆重量装载率阈值(下限)
	 */
	public void setWeightLowerThreshold(double weightLowerThreshold) {
		this.weightLowerThreshold = weightLowerThreshold;
	}

	/**
	 * Gets the 库存件数与排单件数不一致列表.
	 *
	 * @return the 库存件数与排单件数不一致列表
	 */
	public List<DeliverbillDetailDto> getQtyInconsistentDeliverbillDetailList() {
		return qtyInconsistentDeliverbillDetailList;
	}

	/**
	 * Sets the 库存件数与排单件数不一致列表.
	 *
	 * @param qtyInconsistentDeliverbillDetailList the new 库存件数与排单件数不一致列表
	 */
	public void setQtyInconsistentDeliverbillDetailList(
			List<DeliverbillDetailDto> qtyInconsistentDeliverbillDetailList) {
		this.qtyInconsistentDeliverbillDetailList = qtyInconsistentDeliverbillDetailList;
	}

	/**
	 * Gets the 运单号集合.
	 *
	 * @return the 运单号集合
	 */
	public List<String> getWaybillNos() {
		return waybillNos;
	}

	/**
	 * Sets the 运单号集合.
	 *
	 * @param waybillNos the new 运单号集合
	 */
	public void setWaybillNos(List<String> waybillNos) {
		this.waybillNos = waybillNos;
	}

	public List<String> getDeliverbillNos() {
		return deliverbillNos;
	}

	public void setDeliverbillNos(List<String> deliverbillNos) {
		this.deliverbillNos = deliverbillNos;
	}

	public List<String> getNotPayByOLWaybillNos() {
		return notPayByOLWaybillNos;
	}

	public void setNotPayByOLWaybillNos(List<String> notPayByOLWaybillNos) {
		this.notPayByOLWaybillNos = notPayByOLWaybillNos;
	}

}