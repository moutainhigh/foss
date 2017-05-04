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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/dao/IReverseSignDetailDao.java
 * 
 * FILE NAME        	: IReverseSignDetailDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.sign.api.shared.domain.ReverseSignDetailEntity;

/**
 * 反签收明细DAO
 * @author ibm-lizhiguo
 * @date 2012-11-20 上午9:40:34
 */
public interface IReverseSignDetailDao {

	/**
	 * 
	 * 获得反签收明细List
	 * @author ibm-lizhiguo
	 * @date 2012-11-20 上午9:41:37
	 */
	List<ReverseSignDetailEntity> searchReverseSignDetailList(String signRfcId);

    /**
     * 
     * 保存反签收明细
     * @author ibm-lizhiguo
     * @date 2012-11-20 上午9:42:17
     */
	int insertSelective(ReverseSignDetailEntity record);
	
	int updateByPrimaryKeySelective(ReverseSignDetailEntity record);
	
	int updateByPrimaryKey(ReverseSignDetailEntity record);

	List<ReverseSignDetailEntity> selectReverseSignDetailByIds(
			List<String> reverseIds);

}