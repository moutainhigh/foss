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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/IOutfieldDao.java
 * 
 * FILE NAME        	: IOutfieldDao.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;


/**
 * 外场数据访问接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:36:44, </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:36:44
 * @since
 * @version
 */
public interface IOutfieldDao {
	
	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	boolean addOutfield(OutfieldEntity outfield);

	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	void updateOutfield(OutfieldEntity outfield);
	
	  /**
     * 精确查询
     * 根据orgCode 查询
     * 
     * @author foss-jiangfei
     * @date 2012-11-27 下午2:56:36
     */
	
	OutfieldEntity queryOutfieldByOrgCode(String orgCode) ;
	
	/**
	 * 通过空运总调查外场
	 * 
	 * @author foss-zhangxiaohui
	 * @date Jan 31, 2013 10:48:29 AM
	 */
	String queryTransferCenterByAirDispatchCode(String airDispatchCode);

	/**
	 * @param outfield
	 */
	void delete(OutfieldEntity outfield);

}