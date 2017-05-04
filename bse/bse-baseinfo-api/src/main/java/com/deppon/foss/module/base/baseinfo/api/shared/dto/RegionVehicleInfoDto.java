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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/RegionVehicleInfoDto.java
 * 
 * FILE NAME        	: RegionVehicleInfoDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity;


/**
 * 定车定区信息封装类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-1-17 上午9:13:59 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-1-17 上午9:13:59
 * @since
 * @version
 */
public class RegionVehicleInfoDto extends RegionVehicleEntity{

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 8915282604543780649L;
    
    /**
     * 集中接送货小区编码.
     */
    private String smallZoneCode;
    
    /**
     * 集中接送货小区名称.
     */
    private String smallZoneName;
    /**
    * 				新增一、二级定区车、机动车车牌号、一、二级定区车对应VirtualCode
    * 				新增一、二级定区车后，每条记录里面有一个虚拟编码，为保证修改、删除时能找到对应的虚拟编码，
    * 				在将定区实体整合为一个实体时，将对应记录的虚拟编码同时保留
    * 				update by 092020-lipengfei
    */
    /**
     * 机动车车牌号
     */
    private String motorVehicleNo;
    /**
     * 一级定区车车牌号
     */
    private String fristRegionVehicleNo;
    /**
     * 二级定区车车牌号
     */
    private String secondRegionVehicleNo;
    /**
     * 机动车所在记录的虚拟编码
     */
    private String motorVirtualCode;
    /**
     * 一级定区车所在记录的虚拟编码
     */
    private String fristRegionVehicleCode;
    /**
     * 二级定区车所在记录的虚拟编码
     */
    private String secondRegionVehicleCode;
    /**
     * 获取一级定区车车牌号.
     *
     * @return  the fristRegionVehicleNo
     */
	public String getFristRegionVehicleNo() {
		return fristRegionVehicleNo;
	}

    /**
     * 设置 一级定区车车牌号.
     *
     * @param fristRegionVehicleNo the fristRegionVehicleNo to set
     */
	public void setFristRegionVehicleNo(String fristRegionVehicleNo) {
		this.fristRegionVehicleNo = fristRegionVehicleNo;
	}

    /**
     * 获取 二级定区车车牌号.
     *
     * @return  the secondRegionVehicleNo
     */
	public String getSecondRegionVehicleNo() {
		return secondRegionVehicleNo;
	}
	/**
     * 设置  二级定区车车牌号.
     *
     * @param secondRegionVehicleNo the secondRegionVehicleNo to set
     */
	public void setSecondRegionVehicleNo(String secondRegionVehicleNo) {
		this.secondRegionVehicleNo = secondRegionVehicleNo;
	}  
    /**
     * 获取 机动车车牌号.
     *
     * @return  the motorVehicleNo
     */
	public String getMotorVehicleNo() {
		return motorVehicleNo;
	}
	/**
     * 设置  机动车车牌号.
     *
     * @param motorVehicleNo the motorVehicleNo to set
     */
	public void setMotorVehicleNo(String motorVehicleNo) {
		this.motorVehicleNo = motorVehicleNo;
	}
    /**
     * 获取 机动车所在记录的虚拟编码.
     *
     * @return  the motorVehicleNo
     */
	public String getMotorVirtualCode() {
		return motorVirtualCode;
	}
	/**
     * 设置  机动车所在记录的虚拟编码.
     *
     * @param motorVirtualCode the motorVirtualCode to set
     */
	public void setMotorVirtualCode(String motorVirtualCode) {
		this.motorVirtualCode = motorVirtualCode;
	}
    /**
     * 获取 一级定区车所在记录的虚拟编码.
     *
     * @return  the fristRegionVehicleCode
     */
	public String getFristRegionVehicleCode() {
		return fristRegionVehicleCode;
	}
	/**
     * 设置 一级定区车所在记录的虚拟编码
     *
     * @param fristRegionVehicleCode the fristRegionVehicleCode to set
     */
	public void setFristRegionVehicleCode(String fristRegionVehicleCode) {
		this.fristRegionVehicleCode = fristRegionVehicleCode;
	}
    /**
     * 获取 二级定区车所在记录的虚拟编码.
     *
     * @return  the secondRegionVehicleCode
     */
	public String getSecondRegionVehicleCode() {
		return secondRegionVehicleCode;
	}
	/**
     * 设置  二级定区车所在记录的虚拟编码.
     *
     * @param secondRegionVehicleCode the secondRegionVehicleCode to set
     */
	public void setSecondRegionVehicleCode(String secondRegionVehicleCode) {
		this.secondRegionVehicleCode = secondRegionVehicleCode;
	}
    /**
     * 获取 集中接送货小区编码.
     *
     * @return  the smallZoneCode
     */
    public String getSmallZoneCode() {
        return smallZoneCode;
    }
    
    /**
     * 设置 集中接送货小区编码.
     *
     * @param smallZoneCode the smallZoneCode to set
     */
    public void setSmallZoneCode(String smallZoneCode) {
        this.smallZoneCode = smallZoneCode;
    }
    
    /**
     * 获取 集中接送货小区名称.
     *
     * @return  the smallZoneName
     */
    public String getSmallZoneName() {
        return smallZoneName;
    }
    
    /**
     * 设置 集中接送货小区名称.
     *
     * @param smallZoneName the smallZoneName to set
     */
    public void setSmallZoneName(String smallZoneName) {
        this.smallZoneName = smallZoneName;
    }
    
    

}
