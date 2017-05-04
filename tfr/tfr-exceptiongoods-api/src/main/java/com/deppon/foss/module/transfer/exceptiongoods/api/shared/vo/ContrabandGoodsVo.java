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
 *  PROJECT NAME  : tfr-exceptiongoods-api
 *  
 * package_name  : 
 *  
 *  FILE PATH          :/ContrabandGoodsVo.java
 * 
 *  FILE NAME          :ContrabandGoodsVo.java
 *  
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.exceptiongoods.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.ContrabandGoodsEntity;
/**
 * 封装了违禁品操作界面参数
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 下午12:09:43
 */
public class ContrabandGoodsVo implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8086476355302288080L;
	/** 违禁品*/
	private ContrabandGoodsEntity contrabandGoods;
	/** 违禁品List*/
	private List<ContrabandGoodsEntity> contrabandGoodsList;
	/** 部门类型*/
	private String orgType;
	/** 用户所属外场编号*/
	private String transferCenterCode;
	/** 用户所属外场名称*/
	private String transferCenterName;

	/**
	 * 获取 违禁品.
	 *
	 * @return the 违禁品
	 */
	public ContrabandGoodsEntity getContrabandGoods() {
		return contrabandGoods;
	}

	/**
	 * 设置 违禁品.
	 *
	 * @param contrabandGoods the new 违禁品
	 */
	public void setContrabandGoods(ContrabandGoodsEntity contrabandGoods) {
		this.contrabandGoods = contrabandGoods;
	}

	/**
	 * 获取 违禁品List.
	 *
	 * @return the 违禁品List
	 */
	public List<ContrabandGoodsEntity> getContrabandGoodsList() {
		return contrabandGoodsList;
	}

	/**
	 * 设置 违禁品List.
	 *
	 * @param contrabandGoodsList the new 违禁品List
	 */
	public void setContrabandGoodsList(
			List<ContrabandGoodsEntity> contrabandGoodsList) {
		this.contrabandGoodsList = contrabandGoodsList;
	}

	/**
	 * 获取 用户所属外场编号.
	 *
	 * @return the 用户所属外场编号
	 */
	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	/**
	 * 设置 用户所属外场编号.
	 *
	 * @param transferCenterCode the new 用户所属外场编号
	 */
	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	/**
	 * 获取 部门类型.
	 *
	 * @return the 部门类型
	 */
	public String getOrgType() {
		return orgType;
	}

	/**
	 * 设置 部门类型.
	 *
	 * @param orgType the new 部门类型
	 */
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	/**
	 * 获取 用户所属外场名称.
	 *
	 * @return the 用户所属外场名称
	 */
	public String getTransferCenterName() {
		return transferCenterName;
	}

	/**
	 * 设置 用户所属外场名称.
	 *
	 * @param transferCenterName the new 用户所属外场名称
	 */
	public void setTransferCenterName(String transferCenterName) {
		this.transferCenterName = transferCenterName;
	}
	
	
}