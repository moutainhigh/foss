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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IPublishPriceService.java
 * 
 * FILE NAME        	: IPublishPriceService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SaleDepartmentInfoDto;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PublicPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultPriceDto;


/**
 * 
 * (公布价接口服务信息)
 * @author 岳洪杰
 * @date 2012-10-15 下午2:58:58
 * @since
 * @version
 */
public interface IPublishPriceService extends IService{
    /**
     * 
     * <p>(返回公布价信息,根据deptNo与arrive,deptNo默认为当前营业部网点，
     * arrive为可选项查询最新公布价信息其中包含承诺时效、取货时间、
     * 重货、轻货费率、最低一票相关信息在此组装时效最新List与价格最新List)</p> 
     * @author 岳洪杰
     * @date 2012-10-12 上午9:03:18
     * @param deptNo-营业部编号
     * @param arrive-目的站
     * @param billDate-开单日期
     * @return List<PublishPriceVo> - 公布价集合信息
     * @see
     */
    List<PublishPriceEntity> queryPublishPriceDetail(String startDeptNo ,String destinationCode, Date billDate);
    
    /**
	 * 查询偏线公布价信息
	 * @author WangQianJin
	 * @param queryBillCacilateDto
	 * @return
	 */
    List<PublishPriceEntity> queryPublishPriceDetailForPX(QueryBillCacilateDto queryBillCacilateDto);
    
    /**
     * 
     * @Description: 根据出发、到达城市Code查询公布价,PDA专用
     * @author FOSSDP-sz
     * @date 2013-2-25 下午2:06:45
     * @param startCityCode
     * @param destinationCityCode
     * @param billDate
     * @return
     * @version V1.0
     */
    List<PublicPriceDto> queryPublishPriceDetailByCity(String startCityCode ,String destinationCityCode, Date billDate);
    
    /**
     * @Description: 根据出发城市、目的地条件查询公布价
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2013-1-7 下午4:57:02
     * @param startCityCode
     * @param destinationCountryCode
     * @param destinationProvinceCode
     * @param destinationCityCode
     * @param destinationCountyCode
     * @param billDate
     * @return
     * @version V1.0
     */
	List<PublicPriceDto> queryPublishPriceDetailByConditionWithFlight(
			String startCityCode, String destinationCountryCode,
			String destinationProvinceCode, String destinationCityCode,
			String destinationCountyCode, String destinationAddress, String flightSort, Date billDate);
	
	/**
	 * 
	 * @Description: 从GIS获取网点Code
	 * Company:IBM
	 * @author FOSSDP-sz
	 * @date 2013-1-9 下午3:24:33
	 * @param destinationProvinceCode
	 * @param destinationCityCode
	 * @param destinationCountyCode
	 * @param destinationAddress
	 * @param interfaceType
	 * @return
	 * @version V1.0
	 */
	List<String> getCompositeStationCodes(String destinationProvinceCode, String destinationCityCode,
			String destinationCountyCode, String destinationAddress, String interfaceType);
	/**
	 * 
	 * @Description: 获取网点信息
	 * Company:IBM
	 * @author FOSSDP-sz
	 * @date 2013-1-23 上午10:25:21
	 * @param codes
	 * @return
	 * @version V1.0
	 */
	List<SaleDepartmentInfoDto> getOuterAndDepartment(List<String> codes);
	/**
	 * 
	 * @Description: 获取网点信息
	 * @author FOSSDP-sz
	 * @date 2013-4-22 上午11:45:02
	 * @param codes
	 * @param regionId
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	List<SaleDepartmentInfoDto> getOuterAndDepartment(List<String> codes, String regionId, String regionNature,String productCode,String priceRegionIdClass);

	List<PublicPriceDto> queryPublishPriceDetailByCondition(
			String startCityCode, String destinationCountryCode,
			String destinationProvinceCode, String destinationCityCode,
			String destinationCountyCode, String destinationAddress,
			Date billDate);

	List<PublishPriceEntity> queryPublishPriceDetailForSalesAndPx(
			String startDeptCode, String arrvRegionCode);
	
	/**
	 * 查询公布价分段信息
	 * @param queryProductPriceDto
	 * @return
	 */
	List<ResultPriceDto> queryResultPrice(QueryProductPriceDto queryProductPriceDto);
	
	/**
	 * 判断出发城市和到达城市是否是省编码，如果是省编码就返回true
	 * @param startCityCode
	 * @param destinationCityCode
	 * @return
	 */
    boolean queryPublishPriceDetailisProvince(String startCityCode ,String destinationCityCode);
}