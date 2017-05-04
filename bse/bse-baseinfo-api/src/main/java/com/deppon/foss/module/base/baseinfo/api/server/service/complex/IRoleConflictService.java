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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/complex/IRoleConflictService.java
 * 
 * FILE NAME        	: IRoleConflictService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service.complex;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceConflictEntity;


/**
 * 角色互斥 DAO接口
 * @author 087584-foss-lijun
 * @date 2013-1-13 上午12:13:44
 */
public interface IRoleConflictService {
    
    /**
     * 根据2个角色编码查询这个两角色包含的权限有哪些是互斥的
     * 
     * 在给用户部门分配角色时判断角色互斥
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-26 下午4:36:7
     */
    List<ResourceConflictEntity> queryResourceConflictBy2Role(
	    String firstRoleCode, String secondRoleCode);
}
