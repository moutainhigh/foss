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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/commonselector/ICommonPayeeInfoDao.java
 * 
 * FILE NAME        	: ICommonPayeeInfoDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PayeeInfoEntity;

/**
 * 公共查询组件--付款方信息DAO接口
 * @author 078823-foss-panGuangjun
 * @date 2012-12-14 下午2:44:17
 */
public interface ICommonPayeeInfoDao {

	/**
	 * 查询付款方信息
	 * 
	 * @author 078823-foss-panGuangjun
	 * @date 2012-12-15 下午2:44:17
	 * @param entity
	 *            付款方信息实体
	 * @return List<PayeeInfoEntity>:付款方集合
	 */
	 List<PayeeInfoEntity> searchPayeeInfoByCondition(
			PayeeInfoEntity payeeInfoEntity, int start, int limit);

	/**
	 * 查询总条数
	 * 
	 * @author 078823-foss-panGuangjun
	 * @date 2012-12-15 下午2:44:17
	 * @return long
	 */
	 long countPayeeInfoByCondition(PayeeInfoEntity payeeInfoEntity);

}
