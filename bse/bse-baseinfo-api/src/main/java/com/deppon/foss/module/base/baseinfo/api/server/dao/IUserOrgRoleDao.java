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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IUserOrgRoleDao.java
 * 
 * FILE NAME        	: IUserOrgRoleDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.UserOrgRoleDto;
/**
 * 用来操作交互“用户组织角色信息”的数据库对应数据访问DAO接口：SUC-41 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-7 下午7:08:06</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-7 下午7:08:06
 * @since
 * @version
 */
public interface IUserOrgRoleDao {

    /**
     * <p>新增一个“用户组织角色信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:53
     * @param userOrgRole “用户组织角色信息”实体
     * @return 影响记录数
     * @see
     */
     int addUserOrgRole(UserOrgRoleEntity userOrgRole);

    /**
     * <p>新增一个“用户组织角色信息”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:51
     * @param userOrgRole “用户组织角色信息”实体
     * @return 影响记录数
     * @see
     */
     int addUserOrgRoleBySelective(UserOrgRoleEntity userOrgRole);
    
    /**
     * <p>根据“用户组织角色信息”记录唯一标识删除（物理删除）一条“用户组织角色信息”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:42
     * @param id 记录唯一标识
     * @return 影响记录数
     * @see
     */
     int deleteUserOrgRole(String id);

    /**
     * <p>修改一个“用户组织角色信息”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:47
     * @param userOrgRole “用户组织角色信息”实体
     * @return 影响记录数
     * @see
     */
     int updateUserOrgRoleBySelective(UserOrgRoleEntity userOrgRole);

    /**
     * <p>修改一个“用户组织角色信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:45
     * @param userOrgRole “用户组织角色信息”实体
     * @return 影响记录数
     * @see
     */
     int updateUserOrgRole(UserOrgRoleEntity userOrgRole);
    
    /**
     * <p>根据条件有选择的检索出符合条件的“用户组织角色信息”唯一实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:49
     * @param userOrgRole 以“用户组织角色信息”实体承载的条件参数实体
     * @return “用户组织角色信息”实体
     * @see
     */
     UserOrgRoleEntity queryUserOrgRoleBySelective(UserOrgRoleEntity userOrgRole);
    
    /**
     * <p>根据条件有选择的检索出符合条件的“用户组织角色信息”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午10:51:05
     * @param userOrgRole 以“用户组织角色信息”实体承载的条件参数实体
     * @return 符合条件的“用户组织角色信息”实体列表
     * @see
     */
     List<UserOrgRoleEntity> queryUserOrgRoleListBySelective(UserOrgRoleEntity userOrgRole);
    
    /**
     * <p>根据条件（分页模糊）有选择的检索出符合条件的“用户组织角色信息”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午10:51:05
     * @param userOrgRole 以“用户组织角色信息”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“用户组织角色信息”实体列表
     * @see
     */
     List<UserOrgRoleEntity> queryUserOrgRoleListBySelectiveCondition(UserOrgRoleEntity userOrgRole, int offset, int limit);
    
    /**
     * <p>提供给"GUI"下载"用户组织角色信息"的数据</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-7 下午8:06:28
     * @param userOrgRole “用户组织角色信息”实体参数
     * @return
     * @see
     */
     List<UserOrgRoleEntity> queryUserOrgRoleListForDownload(UserOrgRoleEntity userOrgRole);
    
    
    
    /**
     * 下面是 087584-foss-lijun 添加
     */
    
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-22 下午10:54:42
     */
    UserOrgRoleEntity addUserOrgRoleByEntity(UserOrgRoleEntity entity);
    
    /**
     * 根据用户编码USER_CODE，部门编码ORG_CODE 作废数据
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-21 下午9:12:17
     */
    UserOrgRoleEntity deleteUserOrgRole(UserOrgRoleEntity entity);
    
    

    /**
     * 根据用户编码USER_CODE，部门编码ORG_CODE，角色编码ORG_CODE数组  批量作废数据
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-21 下午9:8:10
     */
    UserOrgRoleEntity deleteUserOrgRoleMore(UserOrgRoleEntity entity, String[] roleCodes);

    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-21 下午10:07:13
     */
    List<UserOrgRoleEntity> queryUserOrgRoleExactByEntity(UserOrgRoleEntity entity, int start, int limit);
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-21 下午10:07:13
     */
    long queryUserOrgRoleExactByEntityCount(UserOrgRoleEntity entity);
    
    


    /**
     * 需求更改后使用的查询方式
     */
    

    
    /**
     * 模糊查询
     * 查询用户部门角色的列表信息
     * 
     * 根据用户部门角色的userOrgRole用户工号，employee人员姓名，userOrgRole部门标杆编码（精确），role角色名称 查询
     * 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IValuablesDao#queryValuablesExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.ValuablesEntity)
     */
    List<UserOrgRoleDto> queryUserOrgRoleDto(UserOrgRoleEntity userOrgRole,
	    EmployeeEntity employee, RoleEntity role, int start, int limit);
    
    
    /**
     * 模糊查询-查询总条数，用于分页
     * 查询用户部门角色的列表信息
     * 
     * 根据用户部门角色的userOrgRole用户工号，employee人员姓名，userOrgRole部门标杆编码（精确），role角色名称 查询
     * 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IValuablesDao#queryValuablesExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.ValuablesEntity)
     */
    long queryUserOrgRoleDtoCount(UserOrgRoleEntity userOrgRole, EmployeeEntity employee, RoleEntity role);
    
    /**
     * 当操作部门，角色名称为空时的查询方式
     */
    
    /**
     * 模糊查询
     * 查询用户部门角色的列表信息
     * 
     * 根据用户部门角色的userOrgRole用户工号，employee人员姓名，userOrgRole部门标杆编码（精确），role角色名称 查询
     * 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IValuablesDao#queryValuablesExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.ValuablesEntity)
     */
    List<UserOrgRoleDto> queryUserOrgRoleDtoByEmployee(EmployeeEntity employee, int start, int limit);
    
    
    /**
     * 模糊查询-查询总条数，用于分页
     * 查询用户部门角色的列表信息
     * 
     * 根据用户部门角色的userOrgRole用户工号，employee人员姓名，userOrgRole部门标杆编码（精确），role角色名称 查询
     * 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IValuablesDao#queryValuablesExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.ValuablesEntity)
     */
    long queryUserOrgRoleDtoByEmployeeCount(EmployeeEntity employee);
    
    /** 
     * 根据部门编码获取该部门下所有员工编码
     * @author 101911-foss-zhouChunlai
     * @date 2013-3-15 下午4:55:49
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserOrgRoleDao#queryUserOrgRoleListByUnifieldCode(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity)
     */
   List<UserOrgRoleEntity> queryUserOrgRoleListByUnifieldCode(String unifieldCode);
   /**
    * <p>新增一个“用户组织角色信息”实体入无效库 （只选择实体中非空值）</p> 
    * 
    * @author 130346-foss-lifanghong
    * 
    * @date 2014-3-27 下午2:30:47
    * 
    * @param userOrgRole “用户组织角色信息”实体
    * 
    * @return 影响记录数
    * 
    * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserOrgRoleDao#updateUserOrgRoleBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity)
    */
	int addUnactiveUserOrgRoleBySelective(UserOrgRoleEntity userOrgRole);
}
