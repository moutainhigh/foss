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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/CommonAdministrativeRegionsVo.java
 * 
 * FILE NAME        	: CommonAdministrativeRegionsVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;


public class CommonAdministrativeRegionsVo {

    // 行政区域详情
    private AdministrativeRegionsEntity administrativeRegionsDetail;
    
    private String parentId;
    private String name;
    private String active;
    private String degree;
    private String cityId;
    
    private String provinceId;
    private List<AdministrativeRegionsEntity> cityList;
    private List<AdministrativeRegionsEntity> areaList;
    private List<AdministrativeRegionsEntity> provinceList;
    private List<AdministrativeRegionsEntity> nationList;
    private List<AdministrativeRegionsEntity> reginList;
    /**
     * 下面是get,set方法
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-16 下午2:20:42
     */

    
    public AdministrativeRegionsEntity getAdministrativeRegionsDetail() {
        return administrativeRegionsDetail;
    }


    
    public List<AdministrativeRegionsEntity> getReginList() {
		return reginList;
	}



	public void setReginList(List<AdministrativeRegionsEntity> reginList) {
		this.reginList = reginList;
	}



	public void setAdministrativeRegionsDetail(
    	AdministrativeRegionsEntity administrativeRegionsDetail) {
        this.administrativeRegionsDetail = administrativeRegionsDetail;
    }


	public String getParentId() {
		return parentId;
	}


	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCityId() {
		return cityId;
	}


	public void setCityId(String cityId) {
		this.cityId = cityId;
	}


	
	
	public String getDegree() {
		return degree;
	}



	
	public void setDegree(String degree) {
		this.degree = degree;
	}



	public String getActive() {
		return active;
	}



	
	public void setActive(String active) {
		this.active = active;
	}



	public List<AdministrativeRegionsEntity> getCityList() {
		return cityList;
	}


	public void setCityList(List<AdministrativeRegionsEntity> cityList) {
		this.cityList = cityList;
	}


	public List<AdministrativeRegionsEntity> getAreaList() {
		return areaList;
	}


	public void setAreaList(List<AdministrativeRegionsEntity> areaList) {
		this.areaList = areaList;
	}



	public String getProvinceId() {
		return provinceId;
	}



	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}



	public List<AdministrativeRegionsEntity> getProvinceList() {
		return provinceList;
	}



	public void setProvinceList(List<AdministrativeRegionsEntity> provinceList) {
		this.provinceList = provinceList;
	}



	/**
	 *getter
	 */
	public List<AdministrativeRegionsEntity> getNationList() {
		return nationList;
	}



	/**
	 *setter
	 */
	public void setNationList(List<AdministrativeRegionsEntity> nationList) {
		this.nationList = nationList;
	}


    
}
