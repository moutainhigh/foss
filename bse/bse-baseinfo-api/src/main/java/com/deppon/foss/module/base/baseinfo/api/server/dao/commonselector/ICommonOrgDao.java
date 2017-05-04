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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/commonselector/ICommonOrgDao.java
 * 
 * FILE NAME        	: ICommonOrgDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.server.dao
 * FILE    NAME: ICommonOrgDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgSearchCondition;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonOrgDto;

/**
 * 组织公共组件查询dao
 * @author panGuangJun
 * @date 2012-11-28 上午10:22:33
 */
public interface ICommonOrgDao {
	/**
	 * 通过条件查询组织信息--返回组织的编码和名称
	 * @author panGuangJun
	 *  @param condtion:查询的实体 -封装条件
	 * @return List<CommonOrgEntity>:符合条件的组织集合
	 * @date 2012-11-28 上午10:22:58
	 */
	 List<CommonOrgEntity> queryOrgByCondition(OrgSearchCondition condtion);

	/**
	 * description 通过条件查询组织信息--返回组织的编码和名称
	 * @author panGuangJun
	 * @param orgSearchCondtion:查询条件
	 * @return 总数条数
	 * @date 2012-11-28 上午11:20:29
	 */
	long countOrgByCondition(OrgSearchCondition orgSearchCondtion);

	/**
	 * 根据前端输入框的值查询组织(值可能传入的是名称，也可能是拼音，也可能是编码)
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-6 下午3:10:26
	* @return List<CommonOrgEntity>
	* @param 
	 */
	List<CommonOrgEntity> queryOrgByParam(OrgSearchCondition orgSearchCondtion,int start,int limit);

	/**
	 * 返回查询的总条数
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-6 下午3:10:34
	* @return Long
	* @param 
	 */
	Long countOrgByParam(OrgSearchCondition orgSearchCondtion);

	/**
	 * <p>org组织表选择器</p> 
	 * @author 232607 
	 * @date 2015-6-2 上午10:49:34
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	List<CommonOrgEntity> queryOrgByParamNew(CommonOrgDto dto, int start,
			int limit);
	/**
	 * <p>org组织表选择器</p> 
	 * @author 232607 
	 * @date 2015-6-2 上午10:50:18
	 * @param dto
	 * @return
	 * @see
	 */
	Long queryOrgByParamNewCount(CommonOrgDto dto);
	
}
