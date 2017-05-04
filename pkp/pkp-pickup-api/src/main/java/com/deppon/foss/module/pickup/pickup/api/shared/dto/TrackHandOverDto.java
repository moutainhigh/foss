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
 * PROJECT NAME	: pkp-pickup-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/api/shared/dto/TrackHandOverDto.java
 * 
 * FILE NAME        	: TrackHandOverDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 配置单信息
 * 
 * @author ibm-wangfei
 * @date Dec 29, 2012 5:05:03 PM
 */
public class TrackHandOverDto implements Serializable {
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 2070680702574650230L;
	/** 保存时间 */
	private Date createTime;
	/** 出发时间 */
	private Date leaveTime;
	/** 到达时间 */
	private Date arriveTime;
	/** 交接编号 */
	private String handOverNo;
	/** 出发部门编码 */
	private String origOrgCode;
	/** 出发部门名称 */
	private String origOrgName;
	/** 到达部门编码 */
	private String destOrgCode;
	/** 到达部门名称 */
	private String destOrgName;
	/** 是否预配 */
	private String isPreHandOverBill;
	/** 交接类型 */
	private String handOverType;
	/** 已配件数 */
	private String goodsQty;

	/**
	 * 获取 保存时间.
	 * 
	 * @return the 保存时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置 保存时间.
	 * 
	 * @param createTime the new 保存时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取 出发时间.
	 * 
	 * @return the 出发时间
	 */
	public Date getLeaveTime() {
		return leaveTime;
	}

	/**
	 * 设置 出发时间.
	 * 
	 * @param leaveTime the new 出发时间
	 */
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

	/**
	 * 获取 到达时间.
	 * 
	 * @return the 到达时间
	 */
	public Date getArriveTime() {
		return arriveTime;
	}

	/**
	 * 设置 到达时间.
	 * 
	 * @param arriveTime the new 到达时间
	 */
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	/**
	 * 获取 交接编号.
	 * 
	 * @return the 交接编号
	 */
	public String getHandOverNo() {
		return handOverNo;
	}

	/**
	 * 设置 交接编号.
	 * 
	 * @param handOverNo the new 交接编号
	 */
	public void setHandOverNo(String handOverNo) {
		this.handOverNo = handOverNo;
	}

	/**
	 * 获取 出发部门编码.
	 * 
	 * @return the 出发部门编码
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	/**
	 * 设置 出发部门编码.
	 * 
	 * @param origOrgCode the new 出发部门编码
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
	 * 获取 到达部门编码.
	 * 
	 * @return the 到达部门编码
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * 设置 到达部门编码.
	 * 
	 * @param destOrgCode the new 到达部门编码
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
	 * 获取 是否预配.
	 * 
	 * @return the 是否预配
	 */
	public String getIsPreHandOverBill() {
		return isPreHandOverBill;
	}

	/**
	 * 设置 是否预配.
	 * 
	 * @param isPreHandOverBill the new 是否预配
	 */
	public void setIsPreHandOverBill(String isPreHandOverBill) {
		this.isPreHandOverBill = isPreHandOverBill;
	}

	/**
	 * 获取 交接类型.
	 * 
	 * @return the 交接类型
	 */
	public String getHandOverType() {
		return handOverType;
	}

	/**
	 * 设置 交接类型.
	 * 
	 * @param handOverType the new 交接类型
	 */
	public void setHandOverType(String handOverType) {
		this.handOverType = handOverType;
	}

	/**
	 * 获取 已配件数.
	 * 
	 * @return the 已配件数
	 */
	public String getGoodsQty() {
		return goodsQty;
	}

	/**
	 * 设置 已配件数.
	 * 
	 * @param goodsQty the new 已配件数
	 */
	public void setGoodsQty(String goodsQty) {
		this.goodsQty = goodsQty;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}