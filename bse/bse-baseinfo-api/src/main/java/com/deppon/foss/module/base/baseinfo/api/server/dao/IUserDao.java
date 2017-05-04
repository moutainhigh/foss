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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IUserDao.java
 * 
 * FILE NAME        	: IUserDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;

/**
 * 用来操作交互"用户信息"的数据库对应数据访问DAO接口：SUC-226
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-7 下午6:45:48</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-7 下午6:45:48
 * @since
 * @version
 */
public interface IUserDao {
    
    /**
     * <p>新增一个“用户信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:53
     * @param user “用户信息”实体
     * @return 影响记录数
     * @see
     */
     int addUser(UserEntity user);

    /**
     * <p>新增一个“用户信息”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:51
     * @param user “用户信息”实体
     * @return 影响记录数
     * @see
     */
     int addUserBySelective(UserEntity user);
    
    /**
     * <p>根据“用户信息”记录唯一标识删除（物理删除）一条“用户信息”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:42
     * @param id 记录唯一标识
     * @return 影响记录数
     * @see
     */
     int deleteUser(String id);

    /**
     * <p>修改一个“用户信息”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:47
     * @param user “用户信息”实体
     * @return 影响记录数
     * @see
     */
     int updateUserBySelective(UserEntity user);

    /**
     * <p>修改一个“用户信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:45
     * @param user “用户信息”实体
     * @return 影响记录数
     * @see
     */
     int updateUser(UserEntity user);
    
    /**
     * <p>根据条件有选择的检索出符合条件的“用户信息”唯一实体（条件做自动判断，只选择实体中非空值）</p>
     * @author 100847-foss-GaoPeng
     * @date 2012-11-7 下午5:31:47
     * @param user 以“用户信息”实体承载的条件参数实体
     * @return “用户信息”实体
     * @see
     */
     UserEntity queryUserBySelective(UserEntity user);
    
    /**
     * <p>根据条件有选择的检索出符合条件的“用户信息”实体列表（条件做自动判断，只选择实体中非空值） </p>
     * @author 100847-foss-GaoPeng
     * @date 2012-11-7 下午5:28:21
     * @param user 以“用户信息”实体承载的条件参数实体
     * @return 符合条件的“用户信息”实体列表
     * @see
     */
     List<UserEntity> queryUserListBySelective(UserEntity user);

    /**
     * <p>根据条件（分页模糊）有选择的统计出符合条件的“用户信息”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午10:15:40
     * @param user 以“用户信息”实体承载的条件参数实体
     * @return 符合条件的“用户信息”实体记录条数
     * @see
     */
     Long queryUserRecordCountBySelectiveCondition(UserEntity user);
    
    /**
     * <p>根据条件（分页模糊）有选择的检索出符合条件的“用户信息”实体列表（条件做自动判断，只选择实体中非空值） </p>
     * @author 100847-foss-GaoPeng
     * @date 2012-11-7 下午5:28:21
     * @param user 以“用户信息”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“用户信息”实体列表
     * @see
     */
     List<UserEntity> queryUserListBySelectiveCondition(UserEntity user, int offset, int limit);

    /**
     * <p>
     * 提供给"GUI"下载"用户信息"的数据
     * </p>
     * 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-7 下午8:06:28
     * @param user “用户信息”实体参数
     * @return
     * @see
     */
     List<UserEntity> queryUserListForDownload(UserEntity user);

    /**
     * 最近更新时间
     * 
     * @return 最近更新时间
     */
     Date getLastModifyTime();

    /**
     * 通过UserId得到用户所拥有的角色ID与功能编码
     * 
     * @param userId
     * @return
     */
     UserEntity getUserWithRoleIdAndOrgIdById(String userId);

    /**
     * 通过empCode得到用户所属部门code与权限code与uri集合
     * 
     * @param empCode
     * @return
     */
     List<Set<String>> getOrgResCodeUrisByCode(
			String userCode,String deptCode);

    /**
     * 通过用户的登录名，得到用户对象
     * 
     * @param loginName
     * @return
     */
     UserEntity getByUserName(String userName);

    /**
     * 登录成功后更新最后登录时间 updateLastLoginDate
     * 
     * @param user
     * @return
     * @return int
     * @since:
     */
     int updateLastLoginDate(String userId);

    /**
     * 以下全为查询：
     */
    
    /**
     * 通过 标识编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2013-1-8 下午10:3:57
     */
    UserEntity queryUserByEmpCode(String code);

    /**
     * 
     * <p>通过资源code，查询分配了该资源的所有用户</p> 
     * @author ztjie
     * @date 2013-2-2 下午3:53:16
     * @param resourceCode
     * @return
     * @see
     */
	 List<String> getUserIdsByResourceCode(String resourceCode);

	/**
	 * 
	 * <p>通过角色code，查询分配了该角色的所有用户</p> 
	 * @author ztjie
	 * @date 2013-2-2 下午4:33:08
	 * @param roleCode
	 * @return
	 * @see
	 */
	 List<String> getUserIdsByRoleCode(String roleCode);

	/**
	 * 
	 * <p>通过用户code，查询分配了该用户对应的职员及部门信息</p> 
	 * @author ztjie
	 * @date 2013-2-28 下午4:10:59
	 * @param userCode
	 * @return
	 * @see
	 */
	 EmployeeEntity getUserEmployeeInfoByCode(String userCode);

	/**
	 * 
	 * <p>通过角色code，查询分配了该角色的所有用户及组织，并把用户与组织按"用户编码#组织编码"的方式进行返回</p> 
	 * @author ztjie
	 * @date 2013-8-19 上午8:50:16
	 * @param roleCode
	 * @return
	 * @see
	 */
	 List<String> getUserAndOrgCodesByRoleCodeForCache(String roleCode);
	 
	 /**
	  * 查询当前用户密码使用剩余天数(90天为限)
	  * @author 187862-dujunhui
	  * @date 2015-10-27 下午 2:53:36
	  */
	 int queryLeftDaysOfPsw(String userName,String psw);

	 /**
	  * 新增一个“用户信息”实体入库（返回新增实体，且ID字段在上层设置）
	  * @author 187862-dujunhui
	  * @date 2015-10-31 上午10:04:44
	  * @param user
	  * @return 
	  */
	 UserEntity addUserWithoutID(UserEntity user);
}
