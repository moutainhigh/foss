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
 * PROJECT NAME	: bse-querying
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/querying/server/dao/IUserDefinedQueryDao.java
 * 
 * FILE NAME        	: IUserDefinedQueryDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserDefinedQueryConditionEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserDefinedQuerySchemeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.UserDefinedQueryDto;

/**
 * 自定义查询DAO接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:073586-FOSS-LIXUEXING,date:2013-01-21 20:33
 * </p>
 * 
 * @author 073586-FOSS-LIXUEXING
 * @date 2013-01-21 20:33
 * @since
 * @version
 */
public interface IUserDefinedQueryDao {
	/**
	 * 新增自定义查询方案
	 * 
	 * @author 073586-FOSS-LIXUEXING
	 * @date 2013-01-21 20:33
	 * @param entity
	 * @return
	 * @see
	 */
	int addUserDefinedQueryScheme(UserDefinedQuerySchemeEntity entity);

	/**
	 * 根据code作废自定义查询方案
	 * 
	 * @author 073586-FOSS-LIXUEXING
	 * @date 2013-01-21 20:33
	 * @param codes【SCHEME_CODE】
	 * @return
	 * @see
	 */
	int deleteUserDefinedQuerySchemeByCode(String[] codes, String modifyUser);

	/**
	 * 修改自定义查询方案
	 * 
	 * @author 073586-FOSS-LIXUEXING
	 * @date 2013-01-21 20:33
	 * @param entity
	 * @return
	 * @see
	 */
	int updateUserDefinedQueryScheme(UserDefinedQuerySchemeEntity entity);

	/**
	 * 根据传入对象查询符合条件所有自定义查询方案
	 * 
	 * @author 073586-FOSS-LIXUEXING
	 * @date 2013-01-21 20:33
	 * @param entity
	 * @param limit
	 * @param start
	 * @return
	 * @see
	 */
	List<UserDefinedQuerySchemeEntity> queryUserDefinedQuerySchemes(
			UserDefinedQuerySchemeEntity entity);

	/**
	 * 新增自定义查询条件
	 * 
	 * @author 073586-FOSS-LIXUEXING
	 * @date 2013-01-21 20:33
	 * @param entity
	 * @return
	 * @see
	 */
	int addUserDefinedQueryCondition(UserDefinedQueryConditionEntity entity);

	/**
	 * 根据code作废自定义查询条件
	 * 
	 * @author 073586-FOSS-LIXUEXING
	 * @date 2013-01-21 20:33
	 * @param codes【ID】
	 * @return
	 * @see
	 */
	int deleteUserDefinedQueryConditionByCode(String[] codes, String modifyUser);
	
	/**
	 * 根据code作废自定义查询条件
	 * 
	 * @author 073586-FOSS-LIXUEXING
	 * @date 2013-01-21 20:33
	 * @param codes【PARENT_SCHEME_CODE】
	 * @return
	 * @see
	 */
	int deleteUserDefinedQueryConditionBySchemeCode(String[] codes,
		String modifyUser);
	/**
	 * 修改自定义查询条件
	 * 
	 * @author 073586-FOSS-LIXUEXING
	 * @date 2013-01-21 20:33
	 * @param entity
	 * @return
	 * @see
	 */
	int updateUserDefinedQueryCondition(UserDefinedQueryConditionEntity entity);

	/**
	 * 根据传入对象查询符合条件所有自定义查询条件
	 * 
	 * @author 073586-FOSS-LIXUEXING
	 * @date 2013-01-21 20:33
	 * @param entity
	 * @param limit
	 * @param start
	 * @return
	 * @see
	 */
	List<UserDefinedQueryConditionEntity> queryUserDefinedQueryConditions(
			UserDefinedQueryConditionEntity entity);

	/**
	 * 根据传入对象查询符合条件所有自定义查询方案和条件
	 * 
	 * @author 073586-FOSS-LIXUEXING
	 * @date 2013-01-21 20:33
	 * @param entity
	 * @param limit
	 * @param start
	 * @return List<UserDefinedQueryDto>
	 * @see
	 */
	List<UserDefinedQueryDto> queryUserDefinedQueryDtos(
			UserDefinedQueryDto entity);

}
