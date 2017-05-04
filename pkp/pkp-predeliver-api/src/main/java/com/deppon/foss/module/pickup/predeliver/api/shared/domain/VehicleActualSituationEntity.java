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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/domain/VehicleActualSituationEntity.java
 * 
 * FILE NAME        	: VehicleActualSituationEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 订单车载信息实体
 * 
 * @author 038590-foss-wanghui
 * @date 2012-11-1 下午12:07:01
 */
public class VehicleActualSituationEntity extends BaseEntity {

	private static final long serialVersionUID = -607726204333668242L;
	// id
	private String id;
	// 车牌号
	private String vehicleNo;
	// 剩余重量
	private BigDecimal remainingWeight;
	// 剩余体积
	private BigDecimal remainingVolume;
	// 已接票数
	private Integer alreadyPickupGoodsQty;
	// 未接票数
	private Integer nonePickupGoodsQty;
	// 已送票数
	private Integer alreadyDeliverGoodsQty;
	// 未送票数
	private Integer noneDeliverGoodsQty;
	// 创建时间
	private Date createTime;

	/**
	 * TODO（方法详细描述说明、方法参数的具体涵义）.
	 * 
	 * @return the id
	 * @author ibm-wangfei
	 * @date Dec 25, 2012 10:03:38 AM
	 * @see com.deppon.foss.framework.entity.BaseEntity#getId()
	 */
	public String getId() {
		return id;
	}

	/**
	 * TODO（方法详细描述说明、方法参数的具体涵义）.
	 * 
	 * @param id the id to set
	 * @author ibm-wangfei
	 * @date Dec 25, 2012 10:03:38 AM
	 * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * @param vehicleNo the vehicleNo to see
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * @return the remainingWeight
	 */
	public BigDecimal getRemainingWeight() {
		return remainingWeight;
	}

	/**
	 * @param remainingWeight the remainingWeight to see
	 */
	public void setRemainingWeight(BigDecimal remainingWeight) {
		this.remainingWeight = remainingWeight;
	}

	/**
	 * @return the remainingVolume
	 */
	public BigDecimal getRemainingVolume() {
		return remainingVolume;
	}

	/**
	 * @param remainingVolume the remainingVolume to see
	 */
	public void setRemainingVolume(BigDecimal remainingVolume) {
		this.remainingVolume = remainingVolume;
	}

	/**
	 * @return the alreadyPickupGoodsQty
	 */
	public Integer getAlreadyPickupGoodsQty() {
		return alreadyPickupGoodsQty;
	}

	/**
	 * @param alreadyPickupGoodsQty the alreadyPickupGoodsQty to see
	 */
	public void setAlreadyPickupGoodsQty(Integer alreadyPickupGoodsQty) {
		this.alreadyPickupGoodsQty = alreadyPickupGoodsQty;
	}

	/**
	 * @return the nonePickupGoodsQty
	 */
	public Integer getNonePickupGoodsQty() {
		return nonePickupGoodsQty;
	}

	/**
	 * @param nonePickupGoodsQty the nonePickupGoodsQty to see
	 */
	public void setNonePickupGoodsQty(Integer nonePickupGoodsQty) {
		this.nonePickupGoodsQty = nonePickupGoodsQty;
	}

	/**
	 * @return the alreadyDeliverGoodsQty
	 */
	public Integer getAlreadyDeliverGoodsQty() {
		return alreadyDeliverGoodsQty;
	}

	/**
	 * @param alreadyDeliverGoodsQty the alreadyDeliverGoodsQty to see
	 */
	public void setAlreadyDeliverGoodsQty(Integer alreadyDeliverGoodsQty) {
		this.alreadyDeliverGoodsQty = alreadyDeliverGoodsQty;
	}

	/**
	 * @return the noneDeliverGoodsQty
	 */
	public Integer getNoneDeliverGoodsQty() {
		return noneDeliverGoodsQty;
	}

	/**
	 * @param noneDeliverGoodsQty the noneDeliverGoodsQty to see
	 */
	public void setNoneDeliverGoodsQty(Integer noneDeliverGoodsQty) {
		this.noneDeliverGoodsQty = noneDeliverGoodsQty;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}