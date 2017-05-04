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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/PlatformEntity.java
 * 
 * FILE NAME        	: PlatformEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 */
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 月台
 * 
 * @author zhujunyong
 * Create on Oct 10, 2012
 * 
 */
public class PlatformEntity extends BaseEntity{

	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = 744637969033719059L;

	/**
	 * 部门编码
	 */
	private String organizationCode;
	
	/**
	 * 外场编码（扩展）
	 */
	private String transferCode;
	
	/**
	 * 部门名称(扩展)
	 */
	private String organizationName;
	
	/**
	 * 月台编码
	 */
	private String platformCode;
	
	/**
	 * 虚拟编码
	 */
	private String virtualCode;

	/**
	 * 是否有升降台
	 */
	private String hasLift;
	
	/**
	 * 高度  毫米
	 */
	private Integer height;
	
	/**
	 * 宽度  毫米
	 */
	private Integer width;
	
	/**
	 * 位置
	 */
	private String position;
	
	/**
	 * 是否启用
	 */
	private String active;
	
	/**
	 * 备注
	 */
	private String notes;
	
	/**
	 * 部门列表（数据权限用）
	 */
	private List<String> orgCodeList;
	
	/**
	 * 最大可匹配车型编码
	 */
	private String vehicleCode;
	
	/**
	 * 最大可匹配车型名称(冗余)
	 */
	private String vehicleName;
	/**
	 * 是否有升降台（中文）
	 */
	private String chasLift;
	/**
	 * 车型名称
	 */
	private String vehicleNameInfo;

	/**
	 * 横坐标
	 */
	private String abscissa;
	
	/**
	 * 纵坐标
	 */
	private String ordinate;
	
	/**
	 * 月台类型:长途
	 */
	private String longDistance;
	/**
	 * 月台类型:长途
	 */
	private String shortDistance;
	/**
	 * 月台类型:长途
	 */
	private String pkp;
	/**
	 * 
	 * <p>检查是否有升降台</p> 
	 * @author foss-zhujunyong
	 * @date Mar 7, 2013 2:07:47 PM
	 * @return
	 * @see
	 */
	
	public boolean checkHasLift() {
	    return StringUtils.equals(hasLift, FossConstants.YES);
	}
	

	public String getChasLift() {
		if(StringUtils.equals(FossConstants.YES, hasLift)){
			chasLift = "有";
		}else{
			chasLift = "无";
		}
		return chasLift;
	}

	public void setChasLift(String chasLift) {
		this.chasLift = chasLift;
	}

	public String getVehicleNameInfo() {
		return vehicleNameInfo;
	}


	public void setVehicleNameInfo(String vehicleNameInfo) {
		this.vehicleNameInfo = vehicleNameInfo;
	}


	/**
	 * @return  the vehicleCode
	 */
	public String getVehicleCode() {
	    return vehicleCode;
	}
	
	/**
	 * @param vehicleCode the vehicleCode to set
	 */
	public void setVehicleCode(String vehicleCode) {
	    this.vehicleCode = vehicleCode;
	}

	/**
	 * @return  the organizationCode
	 */
	public String getOrganizationCode() {
	    return organizationCode;
	}
	
	/**
	 * @param organizationCode the organizationCode to set
	 */
	public void setOrganizationCode(String organizationCode) {
	    this.organizationCode = organizationCode;
	}
	
	/**
	 * @return  the platformCode
	 */
	public String getPlatformCode() {
	    return platformCode;
	}
	
	/**
	 * @param platformCode the platformCode to set
	 */
	public void setPlatformCode(String platformCode) {
	    this.platformCode = platformCode;
	}
	
	/**
	 * @return  the virtualCode
	 */
	public String getVirtualCode() {
	    return virtualCode;
	}
	
	/**
	 * @param virtualCode the virtualCode to set
	 */
	public void setVirtualCode(String virtualCode) {
	    this.virtualCode = virtualCode;
	}
	
	/**
	 * @return  the hasLift
	 */
	public String getHasLift() {
	    return hasLift;
	}
	
	/**
	 * @param hasLift the hasLift to set
	 */
	public void setHasLift(String hasLift) {
	    this.hasLift = hasLift;
	}
	
	/**
	 * @return  the height
	 */
	public Integer getHeight() {
	    return height;
	}
	
	/**
	 * @param height the height to set
	 */
	public void setHeight(Integer height) {
	    this.height = height;
	}
	
	/**
	 * @return  the width
	 */
	public Integer getWidth() {
	    return width;
	}
	
	/**
	 * @param width the width to set
	 */
	public void setWidth(Integer width) {
	    this.width = width;
	}
	
	/**
	 * @return  the position
	 */
	public String getPosition() {
	    return position;
	}
	
	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
	    this.position = position;
	}
	
	/**
	 * @return  the active
	 */
	public String getActive() {
	    return active;
	}
	
	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
	    this.active = active;
	}
	
	/**
	 * @return  the notes
	 */
	public String getNotes() {
	    return notes;
	}
	
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
	    this.notes = notes;
	}

	/**
	 * @return  the orgCodeList
	 */
	public List<String> getOrgCodeList() {
	    return orgCodeList;
	}

	/**
	 * @param orgCodeList the orgCodeList to set
	 */
	public void setOrgCodeList(List<String> orgCodeList) {
	    this.orgCodeList = orgCodeList;
	}
	
	/**
	 * @return  the vehicleName
	 */
	public String getVehicleName() {
	    return vehicleName;
	}

	/**
	 * @param vehicleName the vehicleName to set
	 */
	public void setVehicleName(String vehicleName) {
	    this.vehicleName = vehicleName;
	}

	/**
	 * @return transferCode
	 */
	public String getTransferCode() {
		return transferCode;
	}

	/**
	 * @param  transferCode  
	 */
	public void setTransferCode(String transferCode) {
		this.transferCode = transferCode;
	}

	/**
	 * @return organizationName
	 */
	public String getOrganizationName() {
		return organizationName;
	}

	/**
	 * @param  organizationName  
	 */
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}





	/**
	 * @return  the abscissa
	 */
	public String getAbscissa() {
		return abscissa;
	}


	/**
	 * @param abscissa the abscissa to set
	 */
	public void setAbscissa(String abscissa) {
		this.abscissa = abscissa;
	}


	/**
	 * @return  the ordinate
	 */
	public String getOrdinate() {
		return ordinate;
	}


	/**
	 * @param ordinate the ordinate to set
	 */
	public void setOrdinate(String ordinate) {
		this.ordinate = ordinate;
	}


	/**
	 * @return  the longDistance
	 */
	public String getLongDistance() {
		return longDistance;
	}


	/**
	 * @param longDistance the longDistance to set
	 */
	public void setLongDistance(String longDistance) {
		this.longDistance = longDistance;
	}


	/**
	 * @return  the shortDistance
	 */
	public String getShortDistance() {
		return shortDistance;
	}


	/**
	 * @param shortDistance the shortDistance to set
	 */
	public void setShortDistance(String shortDistance) {
		this.shortDistance = shortDistance;
	}


	/**
	 * @return  the pkp
	 */
	public String getPkp() {
		return pkp;
	}


	/**
	 * @param pkp the pkp to set
	 */
	public void setPkp(String pkp) {
		this.pkp = pkp;
	}


	
	
	
}