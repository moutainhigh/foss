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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/CommonOrgDto.java
 * 
 * FILE NAME        	: CommonOrgDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
 
 
/**
 * 获取快递大区所映射行政区域LIST 查询结果dto
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:zhangdongping,date:Jul 31, 2013 2:07:43 PM,content:TODO </p>
 * @author zhangdongping
 * @date Jul 31, 2013 2:07:43 PM
 * @since
 * @version
 */
public class DistrictDto implements Serializable{
  
	 
    /**
     *  
     */
    private static final long serialVersionUID = 8495803941603946791L;
	/**
	 * （省CODE）
	 */
	private String proCode;
	/**
	 * （市CODE） 
	 */
	private String cityCode;
	/**
	 * （区CODE）
	 */
	private String  countyCode;
	
	/**
	 * （省名称）、
	 */
	private String proName;
	/**
	 * （市名称）
	 */
	private String cityName ;
	/**
	 * 区名称
	 */
	private String countyName;
	
	public String getProCode() {
	    return proCode;
	}
	
	public void setProCode(String proCode) {
	    this.proCode = proCode;
	}
	
	public String getCityCode() {
	    return cityCode;
	}
	
	public void setCityCode(String cityCode) {
	    this.cityCode = cityCode;
	}
	
	public String getCountyCode() {
	    return countyCode;
	}
	
	public void setCountyCode(String countyCode) {
	    this.countyCode = countyCode;
	}
	
	public String getProName() {
	    return proName;
	}
	
	public void setProName(String proName) {
	    this.proName = proName;
	}
	
	public String getCityName() {
	    return cityName;
	}
	
	public void setCityName(String cityName) {
	    this.cityName = cityName;
	}
	
	public String getCountyName() {
	    return countyName;
	}
	
	public void setCountyName(String countyName) {
	    this.countyName = countyName;
	}
	
	
}
