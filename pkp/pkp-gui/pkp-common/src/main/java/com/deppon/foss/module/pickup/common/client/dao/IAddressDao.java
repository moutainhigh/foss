/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/IAddressDao.java
 * 
 * FILE NAME        	: IAddressDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
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
package com.deppon.foss.module.pickup.common.client.dao;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;


/**
 * 
 * 行政区域数据提供接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:102246-foss-shaohongliang,date:2012-10-24 下午6:44:35, </p>
 * @author 102246-foss-shaohongliang
 * @date 2012-10-24 下午6:44:35
 * @since
 * @version
 */
public interface IAddressDao {
	
	/**
	 * 
	 * <p>获取所有国家</p> 
	 * @author 354805-taodongguo
	 * @date 2016-10-11 14:57:58
	 * @return
	 * @see
	 */
	List<AddressFieldDto> queryAllNations();

	/**
	 * 
	 * <p>获取所有省份</p> 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-24 下午4:34:39
	 * @return
	 * @see
	 */
	List<AddressFieldDto> queryAllProvinces();
	
	/**
	 * 
	 * <p>获取所有国内省份</p> 
	 * @author 354805 - taodongguo
	 * @date 2016-10-11 14:58:28
	 * @return
	 * @see
	 */
	List<AddressFieldDto> queryAllChinaProvinces();
	
	/**
	 * 
	 * <p>获取所有城市</p> 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-24 下午4:34:39
	 * @return
	 * @see
	 */
	List<AddressFieldDto> queryAllCitys();
	
	/**
	 * 
	 * <p>获取所有区域</p> 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-24 下午4:34:39
	 * @return
	 * @see
	 */
	List<AddressFieldDto> queryAllCountys();
	
	/**
	 * 
	 * <p>获取热门城市</p> 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-24 下午4:34:21
	 * @return
	 * @see
	 */
	List<AddressFieldDto> queryHotCitys();
	
	/**
	 * 
	 * <p>根据城市查询区域</p> 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-24 下午6:41:41
	 * @param cityId
	 * @return
	 * @see
	 */
	List<AddressFieldDto> queryCountysByCityId(String cityId);

	/**
	 * 
	 * <p>根据省份查询城市</p> 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-24 下午6:41:45
	 * @param provinceId
	 * @return
	 * @see
	 */
	List<AddressFieldDto> queryCitysByProvinceId(String provinceId);
	
	/**
	 * 
	 * <p>根据国家查询省份</p> 
	 * @author 354805-taodongguo
	 * @date 2016-10-11 14:59:01
	 * @param nationId
	 * @return
	 * @see
	 */
	List<AddressFieldDto> queryProvincesByNationId(String nationId);
	
	/**
	 * 
	 * 根据国家Code查询名称
	 * @author 354805-foss-taodongguo
	 * @date 2016-10-15 17:26:14
	 */
	String queryNationByCode(String nationCode);
	
	/**
	 * 
	 * 根据省份Code查询名称
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午7:58:00
	 */
	String queryProviceByCode(String provCode);

	/**
	 * 
	 * 根据城市Code查询名称
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午7:58:00
	 */
	String queryCityByCode(String cityCode);

	/**
	 * 
	 * 根据省份区域查询名称
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午7:58:00
	 */
	String queryCountyByCode(String distCode);

	/**
	 * 根据名字来查询城市名称
	 * @author 026123-foss-lifengteng
	 * @date 2013-7-29 上午11:00:56
	 */
	AddressFieldDto queryCityByName(String name);
	
	/**
	 * 根据省份code查询国家信息
	 * @author 354805-foss-taodongguo
	 * @date 2016-10-25 09:56:01
	 */
	AddressFieldDto queryNationByProvinceCode(String provCode);

	/**
	 * 根据区域ID 查询区下面所有的乡镇(街道)
	 * @param countyId
	 * @return
	 */
	List<AddressFieldDto> queryCountyListByCountyId(String countyId);

}