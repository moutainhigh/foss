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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/PAndDeliveryZoneRegionVo.java
 * 
 * FILE NAME        	: PAndDeliveryZoneRegionVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.RegionVehicleInfoDto;

/**
 * (接送货VO).
 *
 * @author 073586-FOSS-LIXUEXING
 * @date 2012-11-27 19:40:42
 * @since
 * @version
 */
@SuppressWarnings("rawtypes")
public class PAndDeliveryZoneRegionVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6709471332955961785L;
	
	/**
	 * 接送货小区实体.
	 */
	private SmallZoneEntity smallZoneEntity;
	
	/**
	 * 接送货小区实体LIST.
	 */
	private List<SmallZoneEntity> smallZoneEntityList;
	
	/**
	 * 接送货大区实体.
	 */
	private BigZoneEntity bigZoneEntity;
	
	/**
	 * 接送货大区实体LIST.
	 */
	private List<BigZoneEntity> bigZoneEntityList;
	
	/**
	 * 定人定区实体.
	 */
	private RegionVehicleEntity regionVehicleEntity;
	
	/**
	 * 定人定区实体LIST.
	 */
	private List<RegionVehicleEntity> regionVehicleEntityList;
	
	/**
	 * 定人定区实体.
	 */
	private RegionVehicleInfoDto regionVehicleDto;
	
	/**
	 * 定人定区实体LIST.
	 */
	private List<RegionVehicleInfoDto> regionVehicleDtoList;
	
	/**
	 * 封装树节点，由于大小区不是同一个类 所以没有泛型.
	 */
	private List treeList;
	
	/**
	 * [查询大小区树时的节点id]实体中对应virtualCode字段，字段间以逗号分隔[支持批量删除].
	 */
	private String codeStr;
	/**
	 * 用户选择车队所属部门
	 */
	private String motorOrg;
	
	/**
	 * 区域类型
	 */
	private String regionType;
	/**
	 * 管理部门对应派送部坐标
	 */
	private String deliverySaleCoordinate;
	
	public String getRegionType() {
		return regionType;
	}

	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}

	/**
	 * 返回前台的INT类型属性.
	 */
	private int returnInt;

	/**
	 * 接送货小区实体LIST.
	 */
	private List<String> addSmallZoneList = new ArrayList<String>();
	
	/**
	 * 接送货小区实体LIST.
	 */
	private List<String> delSmallZoneList = new ArrayList<String>();
	
	/**
	 * 部门编码集合.
	 */
	private List<String> deptCodeList;
	
	
	private String queryParam;

	public String getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}	
	public String getMotorOrg() {
		return motorOrg;
	}

	public void setMotorOrg(String motorOrg) {
		this.motorOrg = motorOrg;
	}

	/**
	 * 下面是get,set方法.
	 * 
	 * 
	 *
	 * @return the 接送货小区实体
	 * @author 073586-FOSS-LIXUEXING
	 * @date 2012-11-27  19:40:42
	 */
	public SmallZoneEntity getSmallZoneEntity() {
		return smallZoneEntity;
	}
	
	/**
	 * 设置 接送货小区实体.
	 *
	 * @param smallZoneEntity the new 接送货小区实体
	 */
	public void setSmallZoneEntity(SmallZoneEntity smallZoneEntity) {
		this.smallZoneEntity = smallZoneEntity;
	}
	
	/**
	 * 获取 接送货小区实体LIST.
	 *
	 * @return the 接送货小区实体LIST
	 */
	public List<SmallZoneEntity> getSmallZoneEntityList() {
		return smallZoneEntityList;
	}
	
	/**
	 * 设置 接送货小区实体LIST.
	 *
	 * @param smallZoneEntityList the new 接送货小区实体LIST
	 */
	public void setSmallZoneEntityList(List<SmallZoneEntity> smallZoneEntityList) {
		this.smallZoneEntityList = smallZoneEntityList;
	}
	
	/**
	 * 获取 接送货大区实体.
	 *
	 * @return the 接送货大区实体
	 */
	public BigZoneEntity getBigZoneEntity() {
		return bigZoneEntity;
	}
	
	/**
	 * 设置 接送货大区实体.
	 *
	 * @param bigZoneEntity the new 接送货大区实体
	 */
	public void setBigZoneEntity(BigZoneEntity bigZoneEntity) {
		this.bigZoneEntity = bigZoneEntity;
	}
	
	/**
	 * 获取 [查询大小区树时的节点id]实体中对应virtualCode字段，字段间以逗号分隔[支持批量删除].
	 *
	 * @return the [查询大小区树时的节点id]实体中对应virtualCode字段，字段间以逗号分隔[支持批量删除]
	 */
	public String getCodeStr() {
		return codeStr;
	}
	
	/**
	 * 设置 [查询大小区树时的节点id]实体中对应virtualCode字段，字段间以逗号分隔[支持批量删除].
	 *
	 * @param codeStr the new [查询大小区树时的节点id]实体中对应virtualCode字段，字段间以逗号分隔[支持批量删除]
	 */
	public void setCodeStr(String codeStr) {
		this.codeStr = codeStr;
	}
	
	/**
	 * 获取 返回前台的INT类型属性.
	 *
	 * @return the 返回前台的INT类型属性
	 */
	public int getReturnInt() {
		return returnInt;
	}
	
	/**
	 * 设置 返回前台的INT类型属性.
	 *
	 * @param returnInt the new 返回前台的INT类型属性
	 */
	public void setReturnInt(int returnInt) {
		this.returnInt = returnInt;
	}
	
	/**
	 * 获取 接送货大区实体LIST.
	 *
	 * @return the 接送货大区实体LIST
	 */
	public List<BigZoneEntity> getBigZoneEntityList() {
	    return bigZoneEntityList;
	}
	
	/**
	 * 设置 接送货大区实体LIST.
	 *
	 * @param bigZoneEntityList the new 接送货大区实体LIST
	 */
	public void setBigZoneEntityList(List<BigZoneEntity> bigZoneEntityList) {
	    this.bigZoneEntityList = bigZoneEntityList;
	}
	
	/**
	 * 获取 定人定区实体.
	 *
	 * @return the 定人定区实体
	 */
	public RegionVehicleEntity getRegionVehicleEntity() {
		return regionVehicleEntity;
	}
	
	/**
	 * 设置 定人定区实体.
	 *
	 * @param regionVehicleEntity the new 定人定区实体
	 */
	public void setRegionVehicleEntity(RegionVehicleEntity regionVehicleEntity) {
		this.regionVehicleEntity = regionVehicleEntity;
	}
	
	/**
	 * 获取 定人定区实体LIST.
	 *
	 * @return the 定人定区实体LIST
	 */
	public List<RegionVehicleEntity> getRegionVehicleEntityList() {
		return regionVehicleEntityList;
	}
	
	/**
	 * 设置 定人定区实体LIST.
	 *
	 * @param regionVehicleEntityList the new 定人定区实体LIST
	 */
	public void setRegionVehicleEntityList(
			List<RegionVehicleEntity> regionVehicleEntityList) {
		this.regionVehicleEntityList = regionVehicleEntityList;
	}
	
	/**
	 * 获取 封装树节点，由于大小区不是同一个类 所以没有泛型.
	 *
	 * @return the 封装树节点，由于大小区不是同一个类 所以没有泛型
	 */
	public List getTreeList() {
		return treeList;
	}
	
	/**
	 * 设置 封装树节点，由于大小区不是同一个类 所以没有泛型.
	 *
	 * @param treeList the new 封装树节点，由于大小区不是同一个类 所以没有泛型
	 */
	public void setTreeList(List treeList) {
		this.treeList = treeList;
	}
	
	/**
	 * 获取 接送货小区实体LIST.
	 *
	 * @return the addSmallZoneList
	 */
	public List<String> getAddSmallZoneList() {
		return addSmallZoneList;
	}
	
	/**
	 * 设置 接送货小区实体LIST.
	 *
	 * @param addSmallZoneList the addSmallZoneList to set
	 */
	public void setAddSmallZoneList(List<String> addSmallZoneList) {
		this.addSmallZoneList = addSmallZoneList;
	}
	
	/**
	 * 获取 接送货小区实体LIST.
	 *
	 * @return the delSmallZoneList
	 */
	public List<String> getDelSmallZoneList() {
		return delSmallZoneList;
	}
	
	/**
	 * 设置 接送货小区实体LIST.
	 *
	 * @param delSmallZoneList the delSmallZoneList to set
	 */
	public void setDelSmallZoneList(List<String> delSmallZoneList) {
		this.delSmallZoneList = delSmallZoneList;
	}
	
	/**
	 * 获取 定人定区实体.
	 *
	 * @return  the regionVehicleDto
	 */
	public RegionVehicleInfoDto getRegionVehicleDto() {
	    return regionVehicleDto;
	}
	
	/**
	 * 设置 定人定区实体.
	 *
	 * @param regionVehicleDto the regionVehicleDto to set
	 */
	public void setRegionVehicleDto(RegionVehicleInfoDto regionVehicleDto) {
	    this.regionVehicleDto = regionVehicleDto;
	}
	
	/**
	 * 获取 定人定区实体LIST.
	 *
	 * @return  the regionVehicleDtoList
	 */
	public List<RegionVehicleInfoDto> getRegionVehicleDtoList() {
	    return regionVehicleDtoList;
	}
	
	/**
	 * 设置 定人定区实体LIST.
	 *
	 * @param regionVehicleDtoList the regionVehicleDtoList to set
	 */
	public void setRegionVehicleDtoList(
		List<RegionVehicleInfoDto> regionVehicleDtoList) {
	    this.regionVehicleDtoList = regionVehicleDtoList;
	}
	
	/**
	 * 获取 部门编码集合.
	 *
	 * @return  the deptCodeList
	 */
	public List<String> getDeptCodeList() {
	    return deptCodeList;
	}
	
	/**
	 * 设置 部门编码集合.
	 *
	 * @param deptCodeList the deptCodeList to set
	 */
	public void setDeptCodeList(List<String> deptCodeList) {
	    this.deptCodeList = deptCodeList;
	}

	public String getDeliverySaleCoordinate() {
		return deliverySaleCoordinate;
	}

	public void setDeliverySaleCoordinate(String deliverySaleCoordinate) {
		this.deliverySaleCoordinate = deliverySaleCoordinate;
	}
	
}
