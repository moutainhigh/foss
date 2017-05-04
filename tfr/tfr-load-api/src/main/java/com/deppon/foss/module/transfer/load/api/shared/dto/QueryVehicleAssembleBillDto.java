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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/QueryVehicleAssembleBillDto.java
 *  
 *  FILE NAME          :QueryVehicleAssembleBillDto.java
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

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
import com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillEntity;

/** 
 * @className: QueryVehicleAssembleBillDto
 * @author: ShiWei shiwei@outlook.com
 * @description: 查询配载单列表实体，由于需要关联任务车辆，配载单实体属性不够用，故有此类
 * @date: 2012-11-13 下午5:02:31
 * 
 */
public class QueryVehicleAssembleBillDto extends VehicleAssembleBillEntity {

	private static final long serialVersionUID = -9136428606699400601L;
	//出发时间
	private Date departTime;
	//预计到达时间
	private Date  planArriveTime;
	//到达时间
	private Date arriveTime;
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
     * 配载单总件数
     * */
    private Integer goodsQtyTotal;
    
   
	/**
     * 配载单总票数
     * */
    private Integer waybillQtyTotal;
    
    /**
     * 配载单总体积
     * */
    private BigDecimal volumeTotal;
    
    /**
     * 配载单总总量
     * */
    private BigDecimal weightTotal;
    
    /**
     * 外请车车主姓名
     * */
    private String truckOwnerName;
    
    /**
     * 外请车约车信息部
     * */
    private String applyPath;
    
    /**
     * 信息部编码(tps系统编码)310248
     * */
    private String ministryinformationCode;
    
    
	public String getMinistryinformationCode() {
		return ministryinformationCode;
	}
	public void setMinistryinformationCode(String ministryinformationCode) {
		this.ministryinformationCode = ministryinformationCode;
	}
    
    
    @DateFormat
	public Date getArriveTime() {
		return arriveTime;
	}
    @DateFormat
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getTruckOwnerName() {
		return truckOwnerName;
	}
	public void setTruckOwnerName(String truckOwnerName) {
		this.truckOwnerName = truckOwnerName;
	}
	//310248
	public String getApplyPath() {
		return applyPath;
	}
	public void setApplyPath(String applyPath) {
		this.applyPath = applyPath;
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
	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}
	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}
	public BigDecimal getWeightTotal() {
		return weightTotal;
	}
	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
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
	@DateFormat
	public Date getDepartTime() {
		return departTime;
	}
	@DateFormat
	public void setDepartTime(Date departTime) {
		this.departTime = departTime;
	}
	@DateFormat
	public Date getPlanArriveTime() {
		return planArriveTime;
	}
	@DateFormat
	public void setPlanArriveTime(Date planArriveTime) {
		this.planArriveTime = planArriveTime;
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