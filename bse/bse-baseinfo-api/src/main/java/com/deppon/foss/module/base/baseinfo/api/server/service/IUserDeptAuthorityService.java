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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IUserDeptAuthorityService.java
 * 
 * FILE NAME        	: IUserDeptAuthorityService.java
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
 * FILE    NAME: IUserDeptAuthrorityService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserDeptDataEntity;

/**
 * 用户数据权限service接口
 * 
 * @author 078823-foss-panGuangJun
 * @date 2012-12-17 下午8:40:11
 */
public interface IUserDeptAuthorityService {

    /**
     * 
     * 增加用户数据权限
     * 
     * @author 078823-foss-panGuangJun
     * @date 2012-12-17 下午8:42:29
     * @return int 1 成功 ，-1 失败
     * @param deptDatas
     *            :增加的用户数据权限集合
     */
    int saveUserDepts(List<UserDeptDataEntity> deptDatas, String empCode);

    /**
     * 
     * 根据用户编码查询用户所拥有的数据权限
     * 
     * @author 078823-foss-panGuangJun
     * @date 2012-12-17 下午8:46:23
     * @return List<OrgAdministrativeInfoEntity>:用户拥有的数据权限
     * @param userCode
     *            :用户编码
     */
    List<OrgAdministrativeInfoEntity> searchUserDeptsByUserCode(String userCode);

    /**
     * 
     * 根据id批量删除用户拥有的数据权限
     * 
     * @author 078823-foss-panGuangJun
     * @date 2012-12-17 下午8:51:58
     * @return int 1 成功 ，-1 失败
     * @param userDeptIds
     *            :用户权限实体的id集合
     */
    int deleteUserDepts(List<UserDeptDataEntity> userDepts);

    /**
     * 
     * <p>
     * 根据用户编码查询用户所拥有的数据权限,包含当前部门
     * </p>
     * 
     * @author foss-zhujunyong
     * @date Jan 22, 2013 4:51:37 PM
     * @param userCode
     * @param currentDeptCode
     * @return
     * @see
     */
    List<String> searchUserDeptsByUserCode(String userCode,
	    String currentDeptCode);

    /**
     * 
     * <p>
     * 根据用户编码查询用户所拥有的数据权限表信息
     * </p>
     * 
     * @author 何波
     * @date 2013-2-22 上午11:27:26
     * @return
     * @see
     */
    List<UserDeptDataEntity> queryUserDeptsByUserCode(String userCode);
}
