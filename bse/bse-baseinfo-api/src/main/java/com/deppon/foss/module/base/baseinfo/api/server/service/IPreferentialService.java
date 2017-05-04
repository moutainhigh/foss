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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IPreferentialService.java
 * 
 * FILE NAME        	: IPreferentialService.java
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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PreferentialEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PreferentialInfoDto;

/**
 * 客户优惠信息Service接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-11-22 上午10:15:51
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-11-22 上午10:15:51
 * @since
 * @version
 */
public interface IPreferentialService extends IService {

	/**
	 * 新增客户优惠信息
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-11-22 上午10:15:51
	 * @param entity
	 *            客户优惠信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int addPreferential(PreferentialEntity entity);

	/**
	 * 根据code作废客户优惠信息
	 * 
	 * @author dp-xieyantao
	 * @date 2012-11-22 上午10:15:51
	 * @param crmId
	 * @param modifyUser
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int deletePreferentialByCode(BigDecimal crmId, String modifyUser);

	/**
	 * 修改客户优惠信息
	 * 
	 * @author dp-xieyantao
	 * @date 2012-11-22 上午10:15:51
	 * @param entity
	 *            客户优惠信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int updatePreferential(PreferentialEntity entity);

	/**
	 * <p>
	 * 根据crmId,最后一次修改时间查询客户优惠信息是否存在
	 * </p>
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-11-26 上午10:15:29
	 * @param crmId
	 * @param lastupdatetime
	 * @return
	 * @see
	 */
	boolean queryPreferentialByCrmId(BigDecimal crmId, Date lastupdatetime);

	/**
	 * <p>
	 * 根据客户编码、时间查询客户当前时间内的客户优惠信息
	 * </p>
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-12-20 上午10:20:09
	 * @param customerCode
	 *            客户编码
	 * @param date
	 *            查询时间
	 * @return
	 * @see
	 */
	PreferentialInfoDto queryPreferentialInfo(String customerCode, Date date,String productCode);
	
	/**
	 * <p>
	 * 根据客户编码、时间查询客户当前的价格版本时间信息
	 * </p>
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2013-8-29 上午10:20:09
	 * @param customerCode
	 *            客户编码
	 * @param date
	 *            查询时间
	 * @return
	 * @see
	 */
	PreferentialInfoDto queryPriceVersionInfo(String customerCode, Date date);

	/**
	 * 规定时间上是否有合同信息
	 */
	List<PreferentialInfoDto> queryCusBargainByCodeAndTime(String customerCode, Date date);
}
