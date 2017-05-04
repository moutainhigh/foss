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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/uumsitf/esb/service/impl/UserInformationService.java
 * 
 * FILE NAME        	: UserInformationService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.uumsitf.esb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.ICache;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserOrgRoleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.UserException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.UserOrgRoleException;
import com.deppon.foss.module.base.uumsitf.esb.service.IUserInformationService;
import com.deppon.foss.util.common.FossTTLCache;
import com.deppon.foss.util.define.FossConstants;

/**
 * 同步"用户信息"和"用户组织角色信息"的复杂服务实现
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:100847-foss-GaoPeng,date:2013-1-10 上午8:16:45
 * </p>
 * 
 * @author 100847-foss-GaoPeng
 * @date 2013-1-10 上午8:16:45
 * @since
 * @version
 */
public class UserInformationService implements IUserInformationService {

	private IUserService userService;

	private IUserOrgRoleService userOrgRoleService;

	// private IUserDeptDataService
	// userDeptDataService;//优化：提至UUMS接口中处理用户部门数据权限-dujunhui

	/**
	 * @set
	 * @param userDeptDataService
	 */
	// public void setUserDeptDataService(IUserDeptDataService
	// userDeptDataService) {
	// /*
	// *@set
	// *@this.userDeptDataService = userDeptDataService
	// */
	// this.userDeptDataService = userDeptDataService;
	// }

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserInformationService.class);

	/**
	 * <p>
	 * 新增一个“用户信息”实体入库（忽略实体中是否存在空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-20 上午11:02:43
	 * @param user
	 *            “用户信息”实体
	 * @param userOrgRoles
	 *            用户组织角色集合
	 * @param createUser
	 *            创建人
	 * @param ignoreNull
	 *            true：忽略空值，false：包含空值
	 * @return 1：成功；0：失败
	 * @throws UserException
	 * @see com.deppon.foss.module.base.uumsitf.esb.service.IUserInformationService#addUser(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity,
	 *      java.util.List, java.lang.String, boolean)
	 */
	@Override
	@Transactional
	public int addUser(UserEntity user, List<UserOrgRoleEntity> userOrgRoles,
			String createUser, boolean ignoreNull) throws UserException {
		// 用户信息
		userService.addUser(user, createUser, ignoreNull);
		// 用户组织权限信息
		for (UserOrgRoleEntity userOrgRole : userOrgRoles) {
			userOrgRoleService.addUserOrgRole(userOrgRole, createUser,
					ignoreNull);
		}
		// userDeptDataService.refreshUserDeptData(user.getEmpCode());
		return FossConstants.SUCCESS;
	}

	/**
	 * <p>
	 * 根据“用户信息”记录标识集合删除（物理删除）多条“用户信息”记录
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-20 上午11:02:42
	 * @param user
	 *            “用户信息”实体
	 * @param userOrgRoles
	 *            用户组织角色集合
	 * @param modifyUser
	 *            修改人
	 * @return 1：成功；0：失败
	 * @throws UserException
	 * @see com.deppon.foss.module.base.uumsitf.esb.service.IUserInformationService#deleteUser(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity,
	 *      java.util.List, java.lang.String)
	 */
	@Override
	@Transactional
	public int deleteUser(UserEntity user,
			List<UserOrgRoleEntity> userOrgRoles, String modifyUser)
			throws UserException {
		// 用户信息
		List<String> userIds = new ArrayList<String>();
		userIds.add(user.getId());
		LOGGER.info("开始删除用户，用户编码为" + user.getEmpCode());
		userService.deleteUser(userIds, modifyUser);
		LOGGER.info("结束删除用户，用户编码为" + user.getEmpCode());
		// UUMS所传递的用户部门角色信息永远为该用户的最新角色信息
		// 根据员工编码删除用户部门角色信息
		UserOrgRoleEntity userOrgRoleEntity = new UserOrgRoleEntity();
		// 创建日期
		Date date = new Date();
		userOrgRoleEntity.setEmpCode(user.getEmpCode());
		userOrgRoleEntity.setVersion(date.getTime());
		userOrgRoleEntity.setModifyDate(date);
		userOrgRoleEntity
				.setModifyUser(ComnConst.EXTERNAL_SYSTEM_UUMS_USER_ACCOUNT);
		LOGGER.info("开始删除用户部门角色信息，用户编码为" + user.getEmpCode());
		userOrgRoleService.deleteUserOrgRole(userOrgRoleEntity);
		LOGGER.info("结束删除用户部门角色信息，用户编码为" + user.getEmpCode());
		// 强制刷新用户缓存
		this.refreshUserCache(user.getId());
		// userDeptDataService.refreshUserDeptData(user.getEmpCode());
		return FossConstants.SUCCESS;
	}

	/**
	 * <p>
	 * 自离的用户，限制用户登录，但不删除用户权限
	 * </p>
	 * 
	 * @author 313353-foss-QiuPeng
	 * @date 2016-02-26 下午15:38:42
	 * @param user
	 *            “用户信息”实体
	 * @param modifyUser
	 *            修改人
	 * @return 1：成功；0：失败
	 * @throws UserException
	 * @see com.deppon.foss.module.base.uumsitf.esb.service.IUserInformationService#leaveUser(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity,
	 *      java.lang.String)
	 */
	@Override
	@Transactional
	public int leaveUser(UserEntity user, String modifyUser)
			throws UserException {
		// 用户信息
		List<String> userIds = new ArrayList<String>();
		userIds.add(user.getId());
		LOGGER.info("开始删除用户，用户编码为" + user.getEmpCode());
		userService.deleteUser(userIds, modifyUser);
		LOGGER.info("结束删除用户，用户编码为" + user.getEmpCode());
		return FossConstants.SUCCESS;
	}

	/**
	 * <p>
	 * 修改一个“用户信息”实体入库（忽略实体中是否存在空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-20 上午11:02:48
	 * @param user
	 *            “用户信息”实体
	 * @param userOrgRoles
	 *            用户组织角色集合
	 * @param modifyUser
	 *            修改人
	 * @return 1：成功；0：失败
	 * @throws UserException
	 * @see com.deppon.foss.module.base.uumsitf.esb.service.IUserInformationService#updateUser(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity,
	 *      java.util.List, java.lang.String)
	 */
	@Override
	@Transactional
	public int updateUser(UserEntity user,
			List<UserOrgRoleEntity> userOrgRoles, String modifyUser)
			throws UserException {
		// 用户信息
		LOGGER.info("开始修改用户，用户编码为" + user.getEmpCode());
		userService.updateUser(user, modifyUser);
		LOGGER.info("结束修改用户，用户编码为" + user.getEmpCode());
		// UUMS所传递的用户部门角色信息永远为该用户的最新角色信息
		// 根据员工编码删除用户部门角色信息
		UserOrgRoleEntity userOrgRoleEntity = new UserOrgRoleEntity();
		// 创建日期
		Date date = new Date();
		userOrgRoleEntity.setEmpCode(user.getEmpCode());
		userOrgRoleEntity.setVersion(date.getTime());
		userOrgRoleEntity.setModifyDate(date);
		userOrgRoleEntity
				.setModifyUser(ComnConst.EXTERNAL_SYSTEM_UUMS_USER_ACCOUNT);
		LOGGER.info("修改用户时，开始先删除用户部门角色信息，用户编码为" + user.getEmpCode());
		userOrgRoleService.deleteUserOrgRole(userOrgRoleEntity);
		LOGGER.info("修改用户时，结束删除用户部门角色信息，用户编码为" + user.getEmpCode());

		// 如果角色信息不为空，则将传入的角色信息全部新增进来
		// 用户组织权限信息
		if (CollectionUtils.isNotEmpty(userOrgRoles)) {
			LOGGER.info("修改用户时，开始新增用户部门角色信息，用户编码为" + user.getEmpCode());
			for (UserOrgRoleEntity userOrgRole : userOrgRoles) {
				userOrgRoleService.addUserOrgRoleForSycUumsUpdate(userOrgRole,
						modifyUser, false);
			}
			LOGGER.info("修改用户时，结束新增用户部门角色信息，用户编码为" + user.getEmpCode());

		}
		// 强制刷新用户缓存
		this.refreshUserCache(user.getId());
		// userDeptDataService.refreshUserDeptData(user.getEmpCode());
		return FossConstants.SUCCESS;
	}

	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“用户信息”唯一实体（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-7 下午5:31:47
	 * @param user
	 *            以“用户信息”实体承载的条件参数实体
	 * @return “用户信息”实体
	 * @throws UserException
	 * @see com.deppon.foss.module.base.uumsitf.esb.service.IUserInformationService#queryUserListBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity)
	 */
	@Override
	@Transactional(readOnly = true)
	public UserEntity queryUserListBySelective(UserEntity user)
			throws UserException {
		return userService.queryUserListBySelectiveCondition(user);
	}

	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“用户组织角色信息”唯一实体（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-12 下午2:30:49
	 * @param userOrgRole
	 *            以“用户组织角色信息”实体承载的条件参数实体
	 * @return “用户组织角色信息”实体
	 * @throws UserOrgRoleException
	 * @see com.deppon.foss.module.base.uumsitf.esb.service.IUserInformationService#queryUserOrgRoleBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity)
	 */
	@Override
	@Transactional(readOnly = true)
	public UserOrgRoleEntity queryUserOrgRoleBySelective(
			UserOrgRoleEntity userOrgRole) throws UserOrgRoleException {
		return userOrgRoleService.queryUserOrgRoleBySelective(userOrgRole);
	}

	/**
	 * <p>
	 * 比较两个"用户组织权限"的不同集合
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2013-1-11 下午3:24:48
	 * @param oldUserOrgRoles
	 *            老的"用户组织权限"集合
	 * @param newUserOrgRoles
	 *            新的"用户组织权限"集合
	 * @return "用户组织权限"集合
	 * @throws UserException
	 * @see
	 */
	@SuppressWarnings("unused")
	private List<UserOrgRoleEntity> compareUserOrgRole(
			List<UserOrgRoleEntity> oldUserOrgRoles,
			List<UserOrgRoleEntity> newUserOrgRoles) throws UserException {
		List<UserOrgRoleEntity> result = new ArrayList<UserOrgRoleEntity>();
		for (UserOrgRoleEntity newUserOrgRole : newUserOrgRoles) {
			boolean intoResult = true;
			for (UserOrgRoleEntity oldUserOrgRole : oldUserOrgRoles) {
				if (StringUtils.equals(newUserOrgRole.getEmpCode(),
						oldUserOrgRole.getEmpCode())) {
					if (StringUtils.equals(newUserOrgRole.getOrgCode(),
							oldUserOrgRole.getOrgCode())
							&& StringUtils.equals(newUserOrgRole.getRoleCode(),
									oldUserOrgRole.getRoleCode())) {
						intoResult = false;
						break;
					}
				}
			}
			if (intoResult) {
				result.add(newUserOrgRole);
			}
		}
		return result;
	}

	/**
	 * 强制更新用户，刷新用户的“用户部门角色”缓存
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-24 下午6:08:33
	 * @param code
	 *            人员工号
	 */
	public void refreshUserCache(String id) {
		if (StringUtils.isNotBlank(id)) {
			@SuppressWarnings("unchecked")
			ICache<String, Object> refreshableCache = CacheManager
					.getInstance().getCache(FossTTLCache.USER_CACHE_UUID);
			if (refreshableCache != null) {
				// 作废此记录
				refreshableCache.invalid(id);
			}
		}
	}

	/**
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	/**
	 * @param userOrgRoleService
	 *            the userOrgRoleService to set
	 */
	public void setUserOrgRoleService(IUserOrgRoleService userOrgRoleService) {
		this.userOrgRoleService = userOrgRoleService;
	}
}
