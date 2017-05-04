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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IProvinceCityInfoService.java
 * 
 * FILE NAME        	: IProvinceCityInfoService.java
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
package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProvinceCityEntity;

/**
 * 银行省市信息Service接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-11-19 下午5:31:00
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-11-19 下午5:31:00
 * @since
 * @version
 */
public interface IProvinceCityInfoService extends IService {

	/**
	 * 新增银行省市信息
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-11-19 下午5:51:00
	 * @param entity
	 *            银行省市信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int addProvinceCity(ProvinceCityEntity entity);

	/**
	 * 根据code作废银行省市信息
	 * 
	 * @author dp-xieyantao
	 * @date 2012-11-19 下午5:51:00
	 * @param code
	 *            code字符串
	 * @param modifyUser
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int deleteProvinceCity(String code, String modifyUser);

	/**
	 * 修改银行省市信息
	 * 
	 * @author dp-xieyantao
	 * @date 2012-11-19 下午5:51:00
	 * @param entity
	 *            银行省市信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int updateProvinceCity(ProvinceCityEntity entity);

	/**
	 * <p>
	 * 根据省市编码查询省市信息
	 * </p>
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-12-1 下午5:05:46
	 * @param code
	 * @return
	 * @see
	 */
	ProvinceCityEntity queryCityEntityByCode(String code);

}
