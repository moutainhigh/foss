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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/UserDao.java
 * 
 * FILE NAME        	: UserDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.UserOrgResCodeUrisDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 用来操作交互"用户信息"的数据库对应数据访问DAO接口的实现类：SUC-226
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:100847-foss-GaoPeng,date:2012-11-7 下午6:46:29
 * </p>
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-11-7 下午6:46:29
 * @since
 * @version
 */
public class UserDao extends SqlSessionDaoSupport implements IUserDao {

	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
			+ ".user";

	/**
	 * <p>
	 * 新增一个“用户信息”实体入库（忽略实体中是否存在空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-12 下午2:30:53
	 * @param user
	 *            “用户信息”实体
	 * @return 影响记录数
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDao#addUser(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity)
	 */
	@Override
	public int addUser(UserEntity user) {
		user.setId(UUIDUtils.getUUID());
		user.setCreateDate(new Date());
		user.setActive(FossConstants.ACTIVE);
		user.setModifyUser(user.getCreateUser());
		user.setModifyDate(new Date(NumberConstants.ENDTIME));
		user.setVersion(user.getCreateDate().getTime());
		return getSqlSession().insert(NAMESPACE + ".addUser", user);
	}

	/**
	 * <p>
	 * 新增一个“用户信息”实体入库 （只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-12 下午2:30:51
	 * @param user
	 *            “用户信息”实体
	 * @return 影响记录数
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDao#addUserBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity)
	 */
	@Override
	public int addUserBySelective(UserEntity user) {
		user.setId(UUIDUtils.getUUID());
		user.setCreateDate(new Date());
		user.setActive(FossConstants.ACTIVE);
		user.setModifyUser(user.getCreateUser());
		user.setModifyDate(new Date(NumberConstants.ENDTIME));
		user.setVersion(user.getCreateDate().getTime());
		return getSqlSession().insert(NAMESPACE + ".addUserBySelective", user);
	}

	/**
	 * <p>
	 * 根据“用户信息”记录唯一标识删除（物理删除）一条“用户信息”记录
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-12 下午2:30:42
	 * @param id
	 *            记录唯一标识
	 * @return 影响记录数
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDao#deleteUser(java.lang.String)
	 */
	@Override
	public int deleteUser(String id) {
		return getSqlSession().delete(NAMESPACE + ".deleteUser", id);
	}

	/**
	 * <p>
	 * 修改一个“用户信息”实体入库 （只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-12 下午2:30:47
	 * @param user
	 *            “用户信息”实体
	 * @return 影响记录数
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDao#updateUserBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity)
	 */
	@Override
	public int updateUserBySelective(UserEntity user) {
		user.setModifyDate(new Date());
		return getSqlSession().update(NAMESPACE + ".updateUserBySelective",
				user);
	}

	/**
	 * <p>
	 * 修改一个“用户信息”实体入库（忽略实体中是否存在空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-12 下午2:30:45
	 * @param user
	 *            “用户信息”实体
	 * @return 影响记录数
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDao#updateUser(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity)
	 */
	@Override
	public int updateUser(UserEntity user) {
		user.setModifyDate(new Date());
		return getSqlSession().update(NAMESPACE + ".updateUser", user);
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
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDao#queryUserBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public UserEntity queryUserBySelective(UserEntity user) {
		if (StringUtils.isBlank(user.getId())) {
			// 强制设置为只查询“启用”的记录
			user.setActive(FossConstants.ACTIVE);
		}

		RowBounds rowBounds = new RowBounds(NumberConstants.NUMERAL_ZORE,
				NumberConstants.NUMERAL_ONE);
		List<UserEntity> userList = getSqlSession().selectList(
				NAMESPACE + ".queryUserListBySelective", user, rowBounds);
		if (CollectionUtils.isEmpty(userList)) {
			return null;
		}
		return userList.get(NumberConstants.NUMERAL_ZORE);
	}

	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“用户信息”实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-7 下午5:28:21
	 * @param user
	 *            以“用户信息”实体承载的条件参数实体
	 * @return 符合条件的“用户信息”实体列表
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDao#queryUserListBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<UserEntity> queryUserListBySelective(UserEntity user) {
		// 强制设置为只查询“启用”的记录
		user.setActive(FossConstants.ACTIVE);
		return getSqlSession().selectList(
				NAMESPACE + ".queryUserListBySelective", user);
	}

	/**
	 * <p>
	 * 根据条件（分页模糊）有选择的统计出符合条件的“用户信息”实体记录数（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-25 上午10:15:40
	 * @param user
	 *            以“用户信息”实体承载的条件参数实体
	 * @return 符合条件的“用户信息”实体记录条数
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDao#queryUserRecordCountBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity)
	 */
	@Override
	public Long queryUserRecordCountBySelectiveCondition(UserEntity user) {
		long recordRount = 0;
		// 强制设置为只查询“启用”的记录
		user.setActive(FossConstants.ACTIVE);
		Object result = getSqlSession().selectOne(
				NAMESPACE + ".queryUserRecordCountBySelectiveCondition", user);
		if (null != result) {
			recordRount = Long.parseLong(result.toString());
		}
		return recordRount;
	}

	/**
	 * <p>
	 * 根据条件（分页模糊）有选择的检索出符合条件的“用户信息”实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-7 下午5:28:21
	 * @param user
	 *            以“用户信息”实体承载的条件参数实体
	 * @param offset
	 *            开始记录数
	 * @param limit
	 *            限制记录数
	 * @return 符合条件的“用户信息”实体列表
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDao#queryUserListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity,
	 *      int, int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<UserEntity> queryUserListBySelectiveCondition(UserEntity user,
			int offset, int limit) {
		// 强制设置为只查询“启用”的记录
		user.setActive(FossConstants.ACTIVE);
		return getSqlSession().selectList(
				NAMESPACE + ".queryUserListBySelectiveCondition", user,
				new RowBounds(offset, limit));
	}

	/**
	 * <p>
	 * 提供给"GUI"下载"用户信息"的数据
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-7 下午8:06:28
	 * @param userOrgRole
	 *            “用户信息”实体参数
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDao#queryUserListForDownload(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<UserEntity> queryUserListForDownload(UserEntity user) {
		return getSqlSession().selectList(
				NAMESPACE + ".queryUserListForDownload", user);
	}

	/**
	 * 最近更新时间
	 * 
	 * @return 最近更新时间
	 */
	@Override
	public Date getLastModifyTime() {
		long versionNo = (Long) getSqlSession().selectOne(
				NAMESPACE + ".getLastModifyTime");
		return new Date(versionNo);
	}

	/**
	 * 通过UserId得到用户所拥有的角色ID与所属部门ID
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public UserEntity getUserWithRoleIdAndOrgIdById(String userId) {
		// 构造查询条件：
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("userActive", FossConstants.ACTIVE);
		map.put("empActive", FossConstants.ACTIVE);
		map.put("orgActive", FossConstants.ACTIVE);
		map.put("userRoleOrgActive", FossConstants.ACTIVE);
		map.put("roleResActive", FossConstants.ACTIVE);
		UserEntity user = (UserEntity) getSqlSession().selectOne(
				NAMESPACE + ".getUserWithRoleIdAndOrgIdById", map);
		return user;
	}

	/**
	 * 通过empCode得到用户所属部门code与权限code及uri集合
	 * 
	 * @param empCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Set<String>> getOrgResCodeUrisByCode(String userCode,String deptCode) {
		// 构造查询条件：
		Map<String, Object> map = new HashMap<String, Object>();
		List<Set<String>> result = new ArrayList<Set<String>>();
		Set<String> uriSet = new HashSet<String>();
		Set<String> codeSet = new HashSet<String>();
		map.put("userCode", userCode);
		map.put("deptCode", deptCode);
		map.put("orgRoleActive", FossConstants.ACTIVE);
		map.put("roleResActive", FossConstants.ACTIVE);
		map.put("resActive", FossConstants.ACTIVE);
		List<UserOrgResCodeUrisDto> orgCodeAndRoleCodes = (List<UserOrgResCodeUrisDto>) getSqlSession()
				.selectList(NAMESPACE + ".getOrgResCodeUrisByEmpCode", map);
		for(UserOrgResCodeUrisDto dto : orgCodeAndRoleCodes){
			uriSet.add(dto.getResUri());
			codeSet.add(dto.getResCode());
		}
		result.add(codeSet);
		result.add(uriSet);
		return result;
	}

	/**
	 * 通过用户的登录名，得到用户对象
	 * 
	 * @param userName
	 * @return
	 */
	@Override
	public UserEntity getByUserName(String userName) {
		UserEntity entity = new UserEntity();
		entity.setUserName(userName);
		entity.setActive(FossConstants.ACTIVE);
		UserEntity user = (UserEntity) getSqlSession().selectOne(
				NAMESPACE + ".getByUserName", entity);
		return user;
	}

	/**
	 * 登录成功后更新最后登录时间
	 * 
	 * @param user
	 */
	@Override
	public int updateLastLoginDate(String userId) {
		return getSqlSession().update(NAMESPACE + ".updateLastLoginDate",
				userId);
	}

	/**
	 * 以下全为查询：
	 */

	/**
	 * 通过 标识编码查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2013-1-8 下午10:3:57
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UserEntity queryUserByEmpCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}

		// 构造查询条件：
		UserEntity entity = new UserEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setEmpCode(code);

		List<UserEntity> entitys = this.getSqlSession().selectList(
				NAMESPACE + ".queryUserByEmpCode", entity);
		if (CollectionUtils.isEmpty(entitys)) {
			return null;
		} else {
			return entitys.get(0);
		}
	}

	/**
	 * 
	 * <p>
	 * 通过资源code，查询分配了该资源的所有用户
	 * </p>
	 * 
	 * @author ztjie
	 * @date 2013-2-2 下午3:53:49
	 * @param resourceCode
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDao#getUserCodeByResourceCode(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getUserIdsByResourceCode(String resourceCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resourceCode", resourceCode);
		map.put("userActive", FossConstants.ACTIVE);
		map.put("userRoleOrgActive", FossConstants.ACTIVE);
		map.put("roleResActive", FossConstants.ACTIVE);
		map.put("resActive", FossConstants.ACTIVE);
		return getSqlSession().selectList(NAMESPACE + ".getUserIdsByResourceCode", map);
	}

	/**
	 * 
	 * <p>通过角色code，查询分配了该角色的所有用户</p> 
	 * @author ztjie
	 * @date 2013-2-2 下午4:33:37
	 * @param roleCode
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDao#getUserIdsByRoleCode(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getUserIdsByRoleCode(String roleCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleCode", roleCode);
		map.put("userActive", FossConstants.ACTIVE);
		map.put("userRoleOrgActive", FossConstants.ACTIVE);
		return getSqlSession().selectList(NAMESPACE + ".getUserIdsByRoleCode", map);
	}
	
	/**
	 * 
	 * <p>通过角色code，查询分配了该角色的所有用户及组织，并把用户与组织按"用户编码#组织编码"的方式进行返回</p> 
	 * @author ztjie
	 * @date 2013-8-19 上午8:51:29
	 * @param roleCode
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDao#getUserAndOrgCodesByRoleCodeForCache(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getUserAndOrgCodesByRoleCodeForCache(String roleCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleCode", roleCode);
		map.put("userRoleOrgActive", FossConstants.ACTIVE);
		return getSqlSession().selectList(NAMESPACE + ".getUserAndOrgCodesByRoleCodeForCache", map);
	}

	/**
	 * 
	 * <p>通过用户code，查询分配了该用户对应的职员及部门信息</p> 
	 * @author ztjie
	 * @date 2013-2-28 下午4:12:14
	 * @param userCode
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDao#getUserEmployeeInfoByCode(java.lang.String)
	 */
	@Override
	public EmployeeEntity getUserEmployeeInfoByCode(String userCode) {
		// 构造查询条件：
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userCode", userCode);
		map.put("empActive", FossConstants.ACTIVE);
		map.put("orgActive", FossConstants.ACTIVE);
		EmployeeEntity userEmp = (EmployeeEntity) getSqlSession().selectOne(
				NAMESPACE + ".getUserEmployeeInfoByCode", map);
		return userEmp;
	}

	/**
	  * 查询当前用户密码使用剩余天数(90天为限)
	  * @author 187862-dujunhui
	  * @date 2015-10-27 下午 2:55:11
	  */
	@Override
	public int queryLeftDaysOfPsw(String userName,String psw) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", userName);
		map.put("passWord", psw);
		Integer i= (Integer) getSqlSession().selectOne(NAMESPACE +".queryLeftDaysOfPsw",map);
		return i==null? NumberConstants.NUMBER_NEGTIVE_1:i;
	}
	
	/**
	 * 新增一个“用户信息”实体入库（返回新增实体，且ID字段在上层设置）</p>
	 * @author 187862-dujunhui
	 * @date 2015-10-31 上午10:05:24
	 * @param user
	 * @return 
	 */
	@Override
	public UserEntity addUserWithoutID(UserEntity user) {
		user.setCreateDate(new Date());
		user.setActive(FossConstants.ACTIVE);
		user.setModifyUser(user.getCreateUser());
		user.setModifyDate(new Date(NumberConstants.ENDTIME));
		user.setVersion(user.getCreateDate().getTime());
		int i=getSqlSession().insert(NAMESPACE + ".addUser", user);
		return i>0 ? user:null;
	}

}
