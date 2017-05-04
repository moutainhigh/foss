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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/vo/NewPublishPriceVo.java
 * 
 * FILE NAME        	: NewPublishPriceVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.SaleDepartmentInfoDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PublicPriceDto;

/**
 * (新公布价VO)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-张斌,date:2013-1-24 上午10:13:10
 * </p>
 * 
 * @author dp-张斌
 * @date 2013-1-24 上午10:13:10
 * @since
 * @version
 */
public class NewPublishPriceVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4480452754031715805L;
	
	/**
	 * 查询结果
	 */
	private List<PublicPriceDto> publicPriceDtoList;

	/**
	 * 获取 查询结果.
	 *
	 * @return the 查询结果
	 */
	public List<PublicPriceDto> getPublicPriceDtoList() {
		return publicPriceDtoList;
	}

	/**
	 * 设置 查询结果.
	 *
	 * @param publicPriceDtoList the new 查询结果
	 */
	public void setPublicPriceDtoList(List<PublicPriceDto> publicPriceDtoList) {
		this.publicPriceDtoList = publicPriceDtoList;
	}
	/**
	 * 出发城市
	 */
	private String startCityCode;
	
	/**
	 * 航班类型
	 */
	private String flightSort;
	/**
	 * 获取 出发城市.
	 *
	 * @return the 出发城市
	 */
	public String getStartCityCode() {
		return startCityCode;
	}

	/**
	 * 设置 出发城市.
	 *
	 * @param startCityCode the new 出发城市
	 */
	public void setStartCityCode(String startCityCode) {
		this.startCityCode = startCityCode;
	}
	/**
	 * 国家code
	 */
	private String destinationCountryCode;
	/**
	 * 省份code
	 */
	private String destinationProvinceCode;
    /**
     * 城市code
     */
    private String destinationCityCode;
    /**
     * 区县code
     */
    private String destinationCountyCode;
    /**
     */
    private String destinationAddress;
    /**
     * 区域ID
     */
    private String deptRegionId;
    /**
     * 区域类型
     */
    private String regionNature;
    /**
     * 网点信息
     */
    private List<SaleDepartmentInfoDto> saleDepartmentInfoDtoList;
    /**
     * 网点集合
     */
    private List<String> deptCodes;
    /**
     * 起始网点集合
     */
    private List<String> startDeptCode;
    /**
     * 产品类别
     */
    private String productCode;
    /**
     * 价格区域ID类别
     */
    private String priceRegionIdClass;
	/**
	 * 获取 起始网点集合.
	 *
	 * @return the 起始网点集合
	 */
	public List<String> getStartDeptCode() {
		return startDeptCode;
	}

	/**
	 * 设置 起始网点集合.
	 *
	 * @param startDeptCode the new 起始网点集合
	 */
	public void setStartDeptCode(List<String> startDeptCode) {
		this.startDeptCode = startDeptCode;
	}

	/**
	 * 获取 网点集合.
	 *
	 * @return the 网点集合
	 */
	public List<String> getDeptCodes() {
		return deptCodes;
	}

	/**
	 * 设置 网点集合.
	 *
	 * @param deptCodes the new 网点集合
	 */
	public void setDeptCodes(List<String> deptCodes) {
		this.deptCodes = deptCodes;
	}

	/**
	 * 获取 网点信息.
	 *
	 * @return the 网点信息
	 */
	public List<SaleDepartmentInfoDto> getSaleDepartmentInfoDtoList() {
		return saleDepartmentInfoDtoList;
	}

	/**
	 * 设置 网点信息.
	 *
	 * @param saleDepartmentInfoDtoList the new 网点信息
	 */
	public void setSaleDepartmentInfoDtoList(
			List<SaleDepartmentInfoDto> saleDepartmentInfoDtoList) {
		this.saleDepartmentInfoDtoList = saleDepartmentInfoDtoList;
	}

	/**
	 * 获取 国家code.
	 *
	 * @return the 国家code
	 */
	public String getDestinationCountryCode() {
		return destinationCountryCode;
	}

	/**
	 * 设置 国家code.
	 *
	 * @param destinationCountryCode the new 国家code
	 */
	public void setDestinationCountryCode(String destinationCountryCode) {
		this.destinationCountryCode = destinationCountryCode;
	}

	/**
	 * 获取 省份code.
	 *
	 * @return the 省份code
	 */
	public String getDestinationProvinceCode() {
		return destinationProvinceCode;
	}

	/**
	 * 设置 省份code.
	 *
	 * @param destinationProvinceCode the new 省份code
	 */
	public void setDestinationProvinceCode(String destinationProvinceCode) {
		this.destinationProvinceCode = destinationProvinceCode;
	}

	/**
	 * 获取 城市code.
	 *
	 * @return the 城市code
	 */
	public String getDestinationCityCode() {
		return destinationCityCode;
	}

	/**
	 * 设置 城市code.
	 *
	 * @param destinationCityCode the new 城市code
	 */
	public void setDestinationCityCode(String destinationCityCode) {
		this.destinationCityCode = destinationCityCode;
	}

	/**
	 * 获取 区县code.
	 *
	 * @return the 区县code
	 */
	public String getDestinationCountyCode() {
		return destinationCountyCode;
	}

	/**
	 * 设置 区县code.
	 *
	 * @param destinationCountyCode the new 区县code
	 */
	public void setDestinationCountyCode(String destinationCountyCode) {
		this.destinationCountyCode = destinationCountyCode;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getDestinationAddress() {
		return destinationAddress;
	}

	/**
	 * 
	 *
	 * @param destinationAddress 
	 */
	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getDeptRegionId() {
		return deptRegionId;
	}

	public void setDeptRegionId(String deptRegionId) {
		this.deptRegionId = deptRegionId;
	}

	public String getRegionNature() {
		return regionNature;
	}

	public void setRegionNature(String regionNature) {
		this.regionNature = regionNature;
	}

	public String getFlightSort() {
		return flightSort;
	}

	public void setFlightSort(String flightSort) {
		this.flightSort = flightSort;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getPriceRegionIdClass() {
		return priceRegionIdClass;
	}

	public void setPriceRegionIdClass(String priceRegionIdClass) {
		this.priceRegionIdClass = priceRegionIdClass;
	}
	
}