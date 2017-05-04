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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IOrgIntricateQueryService.java
 * 
 * FILE NAME        	: IOrgIntricateQueryService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;


public interface IOrgIntricateQueryService {
    /**
     * 通过传入一个车队或车队下调度组的code，查询出车队下的所有接送货组
     * 经过跟王辉沟通，实际返回为车队组，如果车队下面没有车队组，则返回为空
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-26 上午10:14:19
     */
    List<OrgAdministrativeInfoEntity> querySubOrgByCode(String code);
	
}
