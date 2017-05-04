/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/vo/RegionVo.java
 * 
 * FILE NAME        	: RegionVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ShowDeptEntity;

/**
 * (区域VO)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-张斌,date:2012-10-13 上午10:13:10
 * </p>
 * 
 * @author dp-张斌
 * @date 2012-10-13 上午10:13:10
 * @since
 * @version
 */
public class RegionVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4480452754031715805L;

	/**
	 *  返回区域信息结果集
	 */
	private List<PriceRegionEntity> regionEntityList;
	/**
	 *  部门信息
	 */
	private List<ShowDeptEntity> showDeptEntityList;
	/**
	 *  区域关联部门信息(查询)
	 */
	private List<PriceRegioOrgnEntity> priceRegioOrgnEntityList;
	/** 
	 * 返回的行政趋于结果集
	 */
	private List<AdministrativeRegionsEntity> administrativeRegionsEntityList;
	/** 
	 * 查询区域关联部门条件
	 */
	private PriceRegioOrgnEntity priceRegioOrgnEntity;
	/** 
	 * 激活区域
	 */
	private List<String> regionIds;
	/** 
	 * 查询部门条件(区县CODE)
	 */
	private String countyCode;
	/** 
	 * 排除的部门ID
	 */
	private List<String> deptIds;
	/** 
	 * 区域关联部门信息(新增)
	 */
	private List<PriceRegioOrgnEntity> addPriceRegioOrgnEntityList;
	/** 
	 * 区域关联部门信息(修改)
	 */
	private List<PriceRegioOrgnEntity> updatePriceRegioOrgnEntityList;
	/** 
	 * 查询行政区域的父节点
	 */
	private String tparentRegionCode;
	/** 
	 * 查询条件
	 */
	private PriceRegionEntity regionEntity;
	/** 
	 * 区域性质
	 */
	private String regionNature;
	/**
	 * 起始时间
	 */
	private Date beginTime;
	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 获取 起始时间.
	 *
	 * @return the 起始时间
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * 设置 起始时间.
	 *
	 * @param beginTime the new 起始时间
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * 获取 返回区域信息结果集.
	 *
	 * @return the 返回区域信息结果集
	 */
	public List<PriceRegionEntity> getRegionEntityList() {
		return regionEntityList;
	}

	/**
	 * 设置 返回区域信息结果集.
	 *
	 * @param regionEntityList the new 返回区域信息结果集
	 */
	public void setRegionEntityList(List<PriceRegionEntity> regionEntityList) {
		this.regionEntityList = regionEntityList;
	}

	/**
	 * 获取 部门信息.
	 *
	 * @return the 部门信息
	 */
	public List<ShowDeptEntity> getShowDeptEntityList() {
		return showDeptEntityList;
	}

	/**
	 * 设置 部门信息.
	 *
	 * @param showDeptEntityList the new 部门信息
	 */
	public void setShowDeptEntityList(List<ShowDeptEntity> showDeptEntityList) {
		this.showDeptEntityList = showDeptEntityList;
	}

	/**
	 * 获取 区域关联部门信息(查询).
	 *
	 * @return the 区域关联部门信息(查询)
	 */
	public List<PriceRegioOrgnEntity> getPriceRegioOrgnEntityList() {
		return priceRegioOrgnEntityList;
	}

	/**
	 * 设置 区域关联部门信息(查询).
	 *
	 * @param priceRegioOrgnEntityList the new 区域关联部门信息(查询)
	 */
	public void setPriceRegioOrgnEntityList(
			List<PriceRegioOrgnEntity> priceRegioOrgnEntityList) {
		this.priceRegioOrgnEntityList = priceRegioOrgnEntityList;
	}

	/**
	 * 获取 返回的行政趋于结果集.
	 *
	 * @return the 返回的行政趋于结果集
	 */
	public List<AdministrativeRegionsEntity> getAdministrativeRegionsEntityList() {
		return administrativeRegionsEntityList;
	}

	/**
	 * 设置 返回的行政趋于结果集.
	 *
	 * @param administrativeRegionsEntityList the new 返回的行政趋于结果集
	 */
	public void setAdministrativeRegionsEntityList(
			List<AdministrativeRegionsEntity> administrativeRegionsEntityList) {
		this.administrativeRegionsEntityList = administrativeRegionsEntityList;
	}

	/**
	 * 获取 激活区域.
	 *
	 * @return the 激活区域
	 */
	public List<String> getRegionIds() {
		return regionIds;
	}

	/**
	 * 设置 激活区域.
	 *
	 * @param regionIds the new 激活区域
	 */
	public void setRegionIds(List<String> regionIds) {
		this.regionIds = regionIds;
	}

	/**
	 * 获取 查询部门条件(区县CODE).
	 *
	 * @return the 查询部门条件(区县CODE)
	 */
	public String getCountyCode() {
		return countyCode;
	}

	/**
	 * 设置 查询部门条件(区县CODE).
	 *
	 * @param countyCode the new 查询部门条件(区县CODE)
	 */
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	/**
	 * 获取 排除的部门ID.
	 *
	 * @return the 排除的部门ID
	 */
	public List<String> getDeptIds() {
		return deptIds;
	}

	/**
	 * 设置 排除的部门ID.
	 *
	 * @param deptIds the new 排除的部门ID
	 */
	public void setDeptIds(List<String> deptIds) {
		this.deptIds = deptIds;
	}

	/**
	 * 获取 区域关联部门信息(新增).
	 *
	 * @return the 区域关联部门信息(新增)
	 */
	public List<PriceRegioOrgnEntity> getAddPriceRegioOrgnEntityList() {
		return addPriceRegioOrgnEntityList;
	}

	/**
	 * 设置 区域关联部门信息(新增).
	 *
	 * @param addPriceRegioOrgnEntityList the new 区域关联部门信息(新增)
	 */
	public void setAddPriceRegioOrgnEntityList(
			List<PriceRegioOrgnEntity> addPriceRegioOrgnEntityList) {
		this.addPriceRegioOrgnEntityList = addPriceRegioOrgnEntityList;
	}

	/**
	 * 获取 区域关联部门信息(修改).
	 *
	 * @return the 区域关联部门信息(修改)
	 */
	public List<PriceRegioOrgnEntity> getUpdatePriceRegioOrgnEntityList() {
		return updatePriceRegioOrgnEntityList;
	}

	/**
	 * 设置 区域关联部门信息(修改).
	 *
	 * @param updatePriceRegioOrgnEntityList the new 区域关联部门信息(修改)
	 */
	public void setUpdatePriceRegioOrgnEntityList(
			List<PriceRegioOrgnEntity> updatePriceRegioOrgnEntityList) {
		this.updatePriceRegioOrgnEntityList = updatePriceRegioOrgnEntityList;
	}

	/**
	 * 获取 查询行政区域的父节点.
	 *
	 * @return the 查询行政区域的父节点
	 */
	public String getTparentRegionCode() {
		return tparentRegionCode;
	}

	/**
	 * 设置 查询行政区域的父节点.
	 *
	 * @param tparentRegionCode the new 查询行政区域的父节点
	 */
	public void setTparentRegionCode(String tparentRegionCode) {
		this.tparentRegionCode = tparentRegionCode;
	}

	/**
	 * 获取 查询条件.
	 *
	 * @return the 查询条件
	 */
	public PriceRegionEntity getRegionEntity() {
		return regionEntity;
	}

	/**
	 * 设置 查询条件.
	 *
	 * @param regionEntity the new 查询条件
	 */
	public void setRegionEntity(PriceRegionEntity regionEntity) {
		this.regionEntity = regionEntity;
	}

	/**
	 * 获取 查询区域关联部门条件.
	 *
	 * @return the 查询区域关联部门条件
	 */
	public PriceRegioOrgnEntity getPriceRegioOrgnEntity() {
		return priceRegioOrgnEntity;
	}

	/**
	 * 设置 查询区域关联部门条件.
	 *
	 * @param priceRegioOrgnEntity the new 查询区域关联部门条件
	 */
	public void setPriceRegioOrgnEntity(
			PriceRegioOrgnEntity priceRegioOrgnEntity) {
		this.priceRegioOrgnEntity = priceRegioOrgnEntity;
	}

	/**
	 * 获取 区域性质.
	 *
	 * @return the 区域性质
	 */
	public String getRegionNature() {
		return regionNature;
	}
	/**
	 * 设置 区域性质.
	 *
	 * @param regionNature the new 区域性质
	 */
	public void setRegionNature(String regionNature) {
		this.regionNature = regionNature;
	}
	/**
	 * 获取 结束时间.
	 *
	 * @return the 结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置 结束时间.
	 *
	 * @param endTime the new 结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}