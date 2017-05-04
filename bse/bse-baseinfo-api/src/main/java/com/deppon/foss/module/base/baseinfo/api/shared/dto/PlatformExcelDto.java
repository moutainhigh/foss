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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/PlatformExcelDto.java
 * 
 * FILE NAME        	: PlatformExcelDto.java
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

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * 月台数据导入导出dto
 * 
 * @author foss-zhujunyong
 * @date Dec 6, 2012 2:07:59 PM
 * @version 1.0
 */
public class PlatformExcelDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7597286454065940679L;

    // 行号 (用来定位，比如用来标示哪一行的数据有问题不能导入)
    private Integer rowNo;
    
    // 外场编号（非部门编码）
    private String transferCode;
    
    // 月台编码
    private String platformCode;

    // 是否有升降台
    private String hasLift;

    // 高度 
    private String height;

    // 宽度 
    private String width;
    
    // 最大可匹配车型编码
    private String vehicleCode;
    
    // 位置
    private String position;

    //月台类型
    private String platformType;
    
    //横坐标
    private String abscissa;
    
    //纵坐标
    private String ordinate;
    
    // 备注
    private String notes;
    


    // 用户编辑的excel文件中可能前后会有多余空格
    public void trimAll() {
	transferCode = transferCode != null ? transferCode.trim() : null;
	platformCode = platformCode != null ? platformCode.trim() : null;
	hasLift = hasLift != null ? hasLift.trim() : null;
	height = height != null ? height.trim() : null;
	width = width != null ? width.trim() : null;
	position = position != null ? position.trim() : null;
	notes = notes != null ? notes.trim() : null;
	vehicleCode = vehicleCode != null ? vehicleCode.trim() : null;
	platformType = platformType != null ? platformType.trim() : null;
	abscissa = abscissa != null ? abscissa.trim() : null;
	ordinate = ordinate != null ? ordinate.trim() : null;
    }
    
    // 验证是否有必填栏位没填
    public boolean validate(){
    	System.out.println("StringUtils.isNotBlank(ordinate)"+StringUtils.isNotBlank(transferCode));
    	System.out.println("StringUtils.isNotBlank(ordinate)"+StringUtils.isNotBlank(platformCode));
    	System.out.println("StringUtils.isNotBlank(ordinate)"+StringUtils.isNotBlank(hasLift));
    	System.out.println("StringUtils.isNotBlank(ordinate)"+StringUtils.isNotBlank(height));
    	System.out.println("StringUtils.isNotBlank(ordinate)"+StringUtils.isNotBlank(width));
    	System.out.println("StringUtils.isNotBlank(ordinate)"+StringUtils.isNotBlank(position));
    	System.out.println("StringUtils.isNotBlank(ordinate)"+StringUtils.isNotBlank(vehicleCode));
    	System.out.println("StringUtils.isNotBlank(ordinate)"+StringUtils.isNotBlank(platformType));
    	System.out.println("StringUtils.isNotBlank(ordinate)"+StringUtils.isNotBlank(abscissa));
    	System.out.println("StringUtils.isNotBlank(ordinate)"+StringUtils.isNotBlank(ordinate));
    	return StringUtils.isNotBlank(transferCode) 
		&& StringUtils.isNotBlank(platformCode)
		&& StringUtils.isNotBlank(hasLift)
		&& StringUtils.isNotBlank(height)
		&& StringUtils.isNotBlank(width)
		&& StringUtils.isNotBlank(position)
		&& StringUtils.isNotBlank(vehicleCode)
		&& StringUtils.isNotBlank(platformType)
		&& StringUtils.isNotBlank(abscissa)
		&& StringUtils.isNotBlank(ordinate);
    }
    
    
    public String getTransferCode() {
        return transferCode;
    }

    
    public void setTransferCode(String transferCode) {
        this.transferCode = transferCode;
    }

    
    public String getPlatformCode() {
        return platformCode;
    }

    
    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    
    public String getHasLift() {
        return hasLift;
    }

    
    public void setHasLift(String hasLift) {
        this.hasLift = hasLift;
    }

    
    public String getPosition() {
        return position;
    }

    
    public void setPosition(String position) {
        this.position = position;
    }

    
    public String getNotes() {
        return notes;
    }

    
    public void setNotes(String notes) {
        this.notes = notes;
    }


    
    public String getHeight() {
        return height;
    }


    
    public void setHeight(String height) {
        this.height = height;
    }


    
    public String getWidth() {
        return width;
    }


    
    public void setWidth(String width) {
        this.width = width;
    }


    
    public Integer getRowNo() {
        return rowNo;
    }


    
    public void setRowNo(Integer rowNo) {
        this.rowNo = rowNo;
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
	 * @return  the platformType
	 */
	public String getPlatformType() {
		return platformType;
	}

	/**
	 * @param platformType the platformType to set
	 */
	public void setPlatformType(String platformType) {
		this.platformType = platformType;
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
    
    
}
