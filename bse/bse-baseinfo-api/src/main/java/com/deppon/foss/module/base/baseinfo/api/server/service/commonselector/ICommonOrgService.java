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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonOrgService.java
 * 
 * FILE NAME        	: ICommonOrgService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.server.service
 * FILE    NAME: ICommonOrgService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonOrgDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonOrgVo;

/**
 * 公共组件查询--组织查询
 * @author panGuangJun
 * @date 2012-11-28 上午10:18:37
 */
public interface ICommonOrgService {
	/**
	 * description 通过条件查询组织信息--返回组织的编码和名称
	 * @author panGuangJun
	 * @param org:查询的实体 -封装条件
	 * @param sql:传递的sql
	 * @return List<CommonOrgEntity>:符合条件的组织集合
	 * @date 2012-11-28 上午10:19:51
	 */
	List<CommonOrgEntity> searchOrgByCondition(CommonOrgVo orgSerchCondtion);
	/**
	 * description 通过名称/编码/拼音查询组织信息--返回组织的编码和名称
	 * @author panGuangJun
	 * @param org:查询的实体 -封装条件
	 * @param sql:传递的sql
	 * @return List<CommonOrgEntity>:符合条件的组织集合
	 * @date 2012-11-28 上午10:19:51
	 */
	List<CommonOrgEntity> searchOrgByParam(CommonOrgVo orgSerchCondtion);

	/**
	 *description 通过条件查询组织信息的总数--返回组织的编码和名称
	 * @author panGuangJun
	 * @param orgSearchCondtion:查询条件
	 * @return 总数条数
	 * @date 2012-11-28 上午11:18:49
	 */
	Long countOrgByCondition(CommonOrgVo orgSearchCondtion);
	/**
	 *通过名称/编码/拼音查询组织信息的总数
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-6 下午3:07:29
	* @return Long
	* @param 
	 */
	Long countOrgByParam(CommonOrgVo orgSerchCondtion);
	
	/**
	 * 根据网点编码查询网点信息
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-1-22 下午2:54:21
	 * @return 
	 */
	CommonOrgEntity searchOrgInfoByCode(String code);
	/**
	 * <p>org组织表选择器</p> 
	 * @author 232607 
	 * @date 2015-6-2 上午10:40:02
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	List<CommonOrgEntity> queryOrgByParam(CommonOrgDto dto, int start, int limit);
	/**
	 * <p>org组织表选择器</p> 
	 * @author 232607 
	 * @date 2015-6-2 上午10:40:39
	 * @param dto 
	 * @return
	 * @see
	 */
	Long queryOrgByParamCount(CommonOrgDto dto);
}

