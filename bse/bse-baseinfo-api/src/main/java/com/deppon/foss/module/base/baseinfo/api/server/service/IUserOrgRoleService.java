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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IUserOrgRoleService.java
 * 
 * FILE NAME        	: IUserOrgRoleService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.UserOrgRoleDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.UserOrgRoleException;
/**
 * 用来操作交互“用户组织角色信息”的数据库对应数据访问Service接口：SUC-41 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-7 下午7:25:40</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-7 下午7:25:40
 * @since
 * @version
 */
public interface IUserOrgRoleService extends IService {

    /**
     * <p>新增一个“用户组织角色信息”实体入库</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 上午10:18:55
     * @param userOrgRole “用户组织角色信息”实体
     * @param createUser 创建人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；-1：失败
     * @throws UserOrgRoleException
     * @see
     */
     int addUserOrgRole(UserOrgRoleEntity userOrgRole, String createUser,
	    boolean ignoreNull) throws UserOrgRoleException;

     /**
     * <p>根据“用户组织角色信息”记录唯一标识作废（逻辑删除）一条“用户组织角色信息”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 上午10:18:54
     * @param modifyUser 修改人
     * @param id 记录唯一标识
     * @return 1：成功；-1：失败
     * @throws UserOrgRoleException
     * @see
     */
     int deleteUserOrgRole(String id, String modifyUser) throws UserOrgRoleException;
    
    /**
     * <p>修改一个“用户组织角色信息”实体入库</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 上午10:19:00
     * @param userOrgRole “用户组织角色信息”实体
     * @param modifyUser 修改人
     * @return 1：成功；-1：失败
     * @throws UserOrgRoleException
     * @see
     */
     int updateUserOrgRole(UserOrgRoleEntity userOrgRole, String modifyUser) throws UserOrgRoleException;
    
    /**
     * <p>根据“用户组织角色信息”记录唯一标识查询出一条“用户组织角色信息”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 上午10:18:57
     * @param id 记录唯一标识
     * @return “用户组织角色信息”实体
     * @throws UserOrgRoleException
     * @see
     */
     UserOrgRoleEntity queryUserOrgRole(String id) throws UserOrgRoleException;
    
    /**
     * <p>根据条件有选择的检索出符合条件的“用户组织角色信息”唯一实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:49
     * @param userOrgRole 以“用户组织角色信息”实体承载的条件参数实体
     * @return “用户组织角色信息”实体
     * @see
     */
     UserOrgRoleEntity queryUserOrgRoleBySelective(UserOrgRoleEntity userOrgRole) throws UserOrgRoleException;
    
    /**
     * <p>根据条件有选择的检索出符合条件的“用户组织角色信息”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午10:51:05
     * @param userOrgRole 以“用户组织角色信息”实体承载的条件参数实体
     * @return 符合条件的“用户组织角色信息”实体列表
     * @see
     */
     List<UserOrgRoleEntity> queryUserOrgRoleListBySelective(UserOrgRoleEntity userOrgRole) throws UserOrgRoleException;
    
    /**
     * <p>根据条件有选择的检索出符合条件的“用户组织角色信息”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 上午10:18:58
     * @param airlinesAgent 以“用户组织角色信息”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“用户组织角色信息”实体列表
     * @throws UserOrgRoleException
     * @see
     */
     List<UserOrgRoleEntity> queryUserOrgRoleListBySelectiveCondition(UserOrgRoleEntity userOrgRole, int offset, int limit) throws UserOrgRoleException;
    
    
    
    /**
     * 下面是 087584-foss-lijun 添加
     */
    
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-22 下午10:54:42
     */
    UserOrgRoleEntity addUserOrgRoleByEntity(UserOrgRoleEntity entity, List<UserOrgRoleEntity> entitys)throws UserOrgRoleException;

    /**
     * 根据用户编码USER_CODE，部门编码ORG_CODE 作废数据
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-21 下午9:8:10
     */
    UserOrgRoleEntity deleteUserOrgRole(UserOrgRoleEntity entity)
	    throws UserOrgRoleException;

    /**
     * 根据用户编码USER_CODE，部门编码ORG_CODE，角色编码ORG_CODE数组  批量作废数据
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-21 下午9:8:10
     */
    UserOrgRoleEntity deleteUserOrgRoleMore(UserOrgRoleEntity entity,
	    String[] roleCodes) throws UserOrgRoleException;

    /** 
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     */
    List<UserOrgRoleEntity> queryUserOrgRoleExactByEntity(
	    UserOrgRoleEntity entity, int start, int limit);

    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
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
     * 根据employee工号，employee人员姓名 模糊 查询,
     * 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     */
    List<UserOrgRoleDto> queryUserOrgRoleDtoByEmployee(
	    EmployeeEntity employee, int start, int limit);
	    
    /**
     * 模糊查询-查询总条数，用于分页
     * 查询用户部门角色的列表信息
     * 
     * 根据employee工号，employee人员姓名 模糊 查询,
     * 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     */
    long queryUserOrgRoleDtoByEmployeeCount(EmployeeEntity employee) ;	    

    
	
	
    /**
     * 下面是一些工具方法
     */
    
    
    /**
     * 给用户部门角色加上“角色列表”
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-26 下午4:35:13
     */
    List<UserOrgRoleDto> attachRole(List<UserOrgRoleDto> dtos);
    
        
    /**
     * 将角色信息添加到“用户部门角色”的角色列表中
     * @author 087584-foss-lijun
     * @date 2012-11-22 下午7:43:53
     */
    UserOrgRoleDto attachRole(UserOrgRoleDto dto);
    
    /**
     * 添加“用户部门角色”时，根据部门标杆编码查出部门，将部门编码添加进去，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-22 下午7:43:53
     */
    UserOrgRoleEntity attachOrgWhenUpdate(UserOrgRoleEntity entity);
    
    
    /**
     * 添加“用户部门角色”时，根据部门标杆编码查出部门，将部门编码添加进去，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-22 下午7:43:53
     */
    List<UserOrgRoleEntity> attachOrgWhenUpdate(List<UserOrgRoleEntity> entitys);
    
    /**
	 * 根据部门编码获取该部门下所有员工编码
	 * @author 101911-foss-zhouChunlai
	 * @date 2013-3-15 下午5:38:56
	 */
	List<UserOrgRoleEntity> queryUserOrgRoleListByUnifieldCode(String unifieldCode);
	
	/**
	 * 
	 * <p>判断一个用户是否有超级管理员权限</p> 
	 * @author foss-zhujunyong
	 * @date Apr 18, 2013 5:43:34 PM
	 * @param employeeCode
	 * @return
	 * @see
	 */
	boolean checkSystemAdmin(String employeeCode);
	
	/**
	 * 更新 用户部门角色
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-7-12 下午2:02:57
	 * @param
	 * @return 成功失败标记
	 * @exception 
	 * @see
	 */
	void updateUserOrgRole(UserOrgRoleEntity entity); 
	
	
	 /**
	 * UUMS同步用户时，采用修改方式，新增用户部门角色信息
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-7-19 下午5:03:45
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	int addUserOrgRoleForSycUumsUpdate(UserOrgRoleEntity userOrgRole, String createUser,
			    boolean ignoreNull) throws UserOrgRoleException;
	
}
