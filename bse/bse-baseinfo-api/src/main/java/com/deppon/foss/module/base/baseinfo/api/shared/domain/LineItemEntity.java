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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/LineItemEntity.java
 * 
 * FILE NAME        	: LineItemEntity.java
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
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 线段实体
 * 
 * @author foss-zhujunyong
 * @date Oct 24, 2012 1:57:03 PM
 * @version 1.0
 */
public class LineItemEntity extends BaseEntity {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 2030544080127571335L;

    /**
     * 顺序
     */
    private Long sequence;
    
    /**
     * 虚拟编码
     */
    private String virtualCode;
    
    /**
     * 线路虚拟编码
     */
    private String lineVirtualCode;

    /**
     * 出发部门编码
     */
    private String orginalOrganizationCode;

    /**
     * 到达部门编码
     */
    private String destinationOrganizationCode;

    /**
     * 出发部门名称
     */
    private String orginalOrganizationName;

    /**
     * 到达部门名称
     */
    private String destinationOrganizationName;
    
    /**
     * 出发城市编码(冗余)
     */
    private String orginalCityCode;
    
    /**
     * 到达城市编码(冗余)
     */
    private String destinationCityCode;

    /**
     * 出发城市名称(扩展)
     */
    private String orginalCityName;
    
    /**
     * 到达城市名称(扩展)
     */
    private String destinationCityName;
    
    /**
     * 普车时效(千分之小时)
     */
    private Long commonAging;
    
    /**
     * 卡车时效(千分之小时)
     */
    private Long fastAging;
    
    /**
     * 经停时间 (千分之小时)
     */
    private Long passbyAging;
    
    /**
     * 线段距离(公里)
     */
    private Long distance;
    
    /**
     * 是否有效
     */
    private String active;
    
    /**
     * 版本号
     */
    private Long version;

    /**
     * 备注
     */
    private String notes;
    
    /**
     * @return  the sequence
     */
    public Long getSequence() {
        return sequence;
    }
    
    /**
     * @param sequence the sequence to set
     */
    public void setSequence(Long sequence) {
        this.sequence = sequence;
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
     * @return  the lineVirtualCode
     */
    public String getLineVirtualCode() {
        return lineVirtualCode;
    }
    
    /**
     * @param lineVirtualCode the lineVirtualCode to set
     */
    public void setLineVirtualCode(String lineVirtualCode) {
        this.lineVirtualCode = lineVirtualCode;
    }
    
    /**
     * @return  the orginalOrganizationCode
     */
    public String getOrginalOrganizationCode() {
        return orginalOrganizationCode;
    }
    
    /**
     * @param orginalOrganizationCode the orginalOrganizationCode to set
     */
    public void setOrginalOrganizationCode(String orginalOrganizationCode) {
        this.orginalOrganizationCode = orginalOrganizationCode;
    }
    
    /**
     * @return  the destinationOrganizationCode
     */
    public String getDestinationOrganizationCode() {
        return destinationOrganizationCode;
    }
    
    /**
     * @param destinationOrganizationCode the destinationOrganizationCode to set
     */
    public void setDestinationOrganizationCode(String destinationOrganizationCode) {
        this.destinationOrganizationCode = destinationOrganizationCode;
    }
    
    /**
     * @return  the orginalCityCode
     */
    public String getOrginalCityCode() {
        return orginalCityCode;
    }
    
    /**
     * @param orginalCityCode the orginalCityCode to set
     */
    public void setOrginalCityCode(String orginalCityCode) {
        this.orginalCityCode = orginalCityCode;
    }
    
    /**
     * @return  the destinationCityCode
     */
    public String getDestinationCityCode() {
        return destinationCityCode;
    }

    /**
     * @param destinationCityCode the destinationCityCode to set
     */
    public void setDestinationCityCode(String destinationCityCode) {
        this.destinationCityCode = destinationCityCode;
    }
    
    /**
     * @return  the commonAging
     */
    public Long getCommonAging() {
        return commonAging;
    }
    
    /**
     * @param commonAging the commonAging to set
     */
    public void setCommonAging(Long commonAging) {
        this.commonAging = commonAging;
    }
    
    /**
     * @return  the fastAging
     */
    public Long getFastAging() {
        return fastAging;
    }
    
    /**
     * @param fastAging the fastAging to set
     */
    public void setFastAging(Long fastAging) {
        this.fastAging = fastAging;
    }
    
    /**
     * @return  the passbyAging
     */
    public Long getPassbyAging() {
        return passbyAging;
    }
    
    /**
     * @param passbyAging the passbyAging to set
     */
    public void setPassbyAging(Long passbyAging) {
        this.passbyAging = passbyAging;
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
     * @return  the distance
     */
    public Long getDistance() {
        return distance == null ? 0 : distance;
    }
    
    /**
     * @param distance the distance to set
     */
    public void setDistance(Long distance) {
        this.distance = distance;
    }

    /**
     * @return  the orginalCityName
     */
    public String getOrginalCityName() {
        return orginalCityName;
    }
 
    /**
     * @param orginalCityName the orginalCityName to set
     */
    public void setOrginalCityName(String orginalCityName) {
        this.orginalCityName = orginalCityName;
    }
    
    /**
     * @return  the destinationCityName
     */
    public String getDestinationCityName() {
        return destinationCityName;
    }

    /**
     * @param destinationCityName the destinationCityName to set
     */
    public void setDestinationCityName(String destinationCityName) {
        this.destinationCityName = destinationCityName;
    }
    
    /**
     * @return  the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Long version) {
        this.version = version;
    }

	/**
	 * @return orginalOrganizationName
	 */
	public String getOrginalOrganizationName() {
		return orginalOrganizationName;
	}

	/**
	 * @param  orginalOrganizationName  
	 */
	public void setOrginalOrganizationName(String orginalOrganizationName) {
		this.orginalOrganizationName = orginalOrganizationName;
	}

	/**
	 * @return destinationOrganizationName
	 */
	public String getDestinationOrganizationName() {
		return destinationOrganizationName;
	}

	/**
	 * @param  destinationOrganizationName  
	 */
	public void setDestinationOrganizationName(String destinationOrganizationName) {
		this.destinationOrganizationName = destinationOrganizationName;
	}
}