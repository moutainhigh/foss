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
 * PROJECT NAME	: bse-uums-itf
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/uumsitf/esb/service/IUserInformationService.java
 * 
 * FILE NAME        	: IUserInformationService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.uumsitf.esb.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.UserException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.UserOrgRoleException;
/**
 * 同步"用户信息"和"用户组织角色信息"的复杂服务
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2013-1-8 下午7:20:15</p>
 * @author 100847-foss-GaoPeng
 * @date 2013-1-8 下午7:20:15
 * @since
 * @version
 */
public interface IUserInformationService extends IService {

    /**
     * <p>新增一个“用户信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:43
     * @param user “用户信息”实体
     * @param userOrgRoles 用户组织角色集合
     * @param createUser 创建人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；0：失败
     * @see
     */
    int addUser(UserEntity user, List<UserOrgRoleEntity> userOrgRoles, String createUser, boolean ignoreNull) throws UserException;
    
    /**
     * <p>根据“用户信息”记录标识集合删除（物理删除）多条“用户信息”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:42
     * @param user “用户信息”实体
     * @param userOrgRoles 用户组织角色集合
     * @param modifyUser 修改人
     * @return 1：成功；0：失败
     * @see
     */
    int deleteUser(UserEntity user, List<UserOrgRoleEntity> userOrgRoles, String modifyUser) throws UserException;

    
    /**
     * <p>自离的用户，限制用户登录，但不删除用户权限</p> 
     * @author 313353-foss-QiuPeng
     * @date 2016-02-26 下午15:38:42
     * @param user “用户信息”实体
     * @param modifyUser 修改人
     * @return 1：成功；0：失败
     * @see
     */
    int leaveUser(UserEntity user, String modifyUser) throws UserException;

    /**
     * <p>修改一个“用户信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:48
     * @param user “用户信息”实体
     * @param userOrgRoles 用户组织角色集合
     * @param modifyUser 修改人
     * @return 1：成功；0：失败
     * @see
     */
    int updateUser(UserEntity user, List<UserOrgRoleEntity> userOrgRoles, String modifyUser) throws UserException;
    
    /**
     * <p>根据条件有选择的检索出符合条件的“用户信息”唯一实体（条件做自动判断，只选择实体中非空值）</p>
     * @author 100847-foss-GaoPeng
     * @date 2012-11-7 下午5:31:47
     * @param user 以“用户信息”实体承载的条件参数实体
     * @return “用户信息”实体
     * @throws UserException
     * @see
     */
    UserEntity queryUserListBySelective(UserEntity user) throws UserException;
    
    /**
     * <p>根据条件有选择的检索出符合条件的“用户组织角色信息”唯一实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:49
     * @param userOrgRole 以“用户组织角色信息”实体承载的条件参数实体
     * @return “用户组织角色信息”实体
     * @see
     */
    UserOrgRoleEntity queryUserOrgRoleBySelective(UserOrgRoleEntity userOrgRole) throws UserOrgRoleException;
}
