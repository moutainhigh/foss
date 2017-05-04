/*******************************************************************************
 * Copyright 2014 BSE TEAM
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/AdministrativeRegionsDto.java
 * 
 * FILE NAME        	: AdministrativeRegionsDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
/**
 * 用来扩展“行政区域信息”的数据库对应实体的DTO 
 * 用于查询行政区域中的县区所对应的服务网点的点坐标和范围坐标
 * @author foss-WeiXing
 * @date 2014-08-27 上午10:57:53
 * @since
 * @version
 */
public class AdministrativeRegionsDto extends AdministrativeRegionsEntity implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 3360717992164742772L;
    
    private String expressDeliveryCoordinate;  //服务范围坐标
    private String depCoordinate; //部门服务区坐标编号
    private String countyCode;   //区县行政代码
    private String mapType;  //地图类型
    
    
	public String getExpressDeliveryCoordinate() {
		return expressDeliveryCoordinate;
	}
	public void setExpressDeliveryCoordinate(String expressDeliveryCoordinate) {
		this.expressDeliveryCoordinate = expressDeliveryCoordinate;
	}
	public String getDepCoordinate() {
		return depCoordinate;
	}
	public void setDepCoordinate(String depCoordinate) {
		this.depCoordinate = depCoordinate;
	}
	public String getCountyCode() {
		return countyCode;
	}
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	public String getMapType() {
		return mapType;
	}
	public void setMapType(String mapType) {
		this.mapType = mapType;
	}

}
