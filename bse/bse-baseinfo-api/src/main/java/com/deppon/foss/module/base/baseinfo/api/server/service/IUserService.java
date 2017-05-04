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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IUserService.java
 * 
 * FILE NAME        	: IUserService.java
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
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.UserException;
/**
 * 用来操作交互"用户信息"的数据库对应数据访问Service接口：SUC-226
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-7 下午6:47:17</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-7 下午6:47:17
 * @since
 * @version
 */
public interface IUserService extends IService {

    /**
     * <p>新增一个“用户信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:43
     * @param user “用户信息”实体
     * @param createUser 创建人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；0：失败
     * @see
     */
     int addUser(UserEntity user, String createUser, boolean ignoreNull) throws UserException;
    
    /**
     * <p>根据“用户信息”记录标识集合删除（物理删除）多条“用户信息”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:42
     * @param ids 记录唯一标识集合
     * @param modifyUser 修改人
     * @return 1：成功；0：失败
     * @see
     */
     int deleteUser(List<String> ids, String modifyUser) throws UserException;

    /**
     * <p>修改一个“用户信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:48
     * @param user “用户信息”实体
     * @param modifyUser 修改人
     * @return 1：成功；0：失败
     * @see
     */
     int updateUser(UserEntity user, String modifyUser) throws UserException;
    
    /**
     * <p>根据“用户信息”记录唯一标识查询出一条“用户信息”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-7 下午5:31:47
     * @param id 记录唯一标识
     * @return “用户信息”实体
     * @see
     */
     UserEntity queryUserListBySelectiveCondition(String id) throws UserException;
    
    /**
     * <p>根据条件有选择的检索出符合条件的“用户信息”唯一实体（条件做自动判断，只选择实体中非空值）</p>
     * @author 100847-foss-GaoPeng
     * @date 2012-11-7 下午5:31:47
     * @param user 以“用户信息”实体承载的条件参数实体
     * @return “用户信息”实体
     * @throws UserException
     * @see
     */
     UserEntity queryUserListBySelectiveCondition(UserEntity user) throws UserException;
    
    /**
     * <p>根据条件有选择的检索出符合条件的“用户信息”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-7 下午5:28:21
     * @param leasedDriver 以“用户信息”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“用户信息”实体列表
     * @see
     */
     List<UserEntity> queryUserListBySelectiveCondition(UserEntity user, int offset, int limit) throws UserException;
    
    /**
     * <p>提供给"结算"来验证用户编号和密码的正确性</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-7 下午8:40:42
     * @param empCode 用户编号
     * @param password 密码
     * @return
     * @throws UserException
     * @see
     */
     boolean queryUserForValidationCorrectness(String empCode,String password) throws UserException;
    
    /**
     * 通过用户名查询用户
     * @param loginName
     * @return
     */
     UserEntity findByLoginName(String loginName) throws UserException;

    /**
     * 更改用户最后登录时间 updateLastLoginDate
     * @param user
     * @return int
     * @since:
     */
     int updateLastLoginDate(String userId) throws UserException;

    /**
     * 通过用户ID从缓存中得到用户信息
     * findByUserId
     * @param id
     * @return
     * @return UserEntity
     * @since:
     */
	 UserEntity findByUserId(String id);

    /**
     * 以下全为查询
     */

    /**
     * 精确查询 通过 EMP_CODE 查询 用户
     * 
     * @author 087584-foss-lijun
     * @date 2013-1-8 下午10:5:55
     */
    UserEntity queryUserByEmpCode(String code);

    /**
     * 
     * <p>通过资源code，查询分配了该资源的所有用户</p> 
     * @author ztjie
     * @date 2013-2-2 下午3:51:02
     * @param resourceCode
     * @return
     * @see
     */
	 List<String> queryUserIdsByResourceCode(String resourceCode);

	/**
     * 
     * <p>通过角色code，查询分配了该角色的所有用户</p> 
     * @author ztjie
     * @date 2013-2-2 下午3:51:02
     * @param resourceCode
     * @return
     * @see
     */
	 List<String> queryUserIdsByRoleCode(String roleCode);
	 /**
	  * 
	  * @Description: 修改密码
	  * @author FOSSDP-sz
	  * @date 2013-3-29 上午11:27:47
	  * @param oldPwd
	  * @param newPwd
	  * @return 加密后的密码字符串
	  * @version V1.0
	  */
	 String modifyUserPwd(String userName, String newPwd);
	 /**
	  * 
	  *<p> 根据查询的条件，获取符合条件的记录数</P>
	  * @author 130566-foss-ZengJunfan
	  * @date 2013-4-25上午10:34:15
	  *@param userEntity
	  *@return
	  */
	 Long queryUserCountBySelectiveCondition(UserEntity userEntity);

	 /**
	  * 
	  * <p>通过角色code，查询分配了该角色的所有用户及组织，并把用户与组织按"用户编码#组织编码"的方式进行返回</p> 
	  * @author ztjie
	  * @date 2013-8-19 上午8:42:11
	  * @param roleCode
	  * @return
	  * @see
	  */
	List<String> queryUserAndOrgCodesByRoleCodeForCache(String roleCode);

	UserEntity queryEmpByUserCode(String id);
	
	/**
	 * 判断当前用户密码使用期限是否到三个月
	 * @author 187862-dujunhui
	 * @date 2015-10-27 下午2:49:24
	 */
	int queryLeftDaysOfPsw(String userName,String psw);
	
}
