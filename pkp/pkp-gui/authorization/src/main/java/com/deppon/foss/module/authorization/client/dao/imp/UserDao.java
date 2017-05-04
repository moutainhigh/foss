/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-gui
 * 
 * FILE PATH        	: authorization/src/main/java/com/deppon/foss/module/authorization/client/dao/imp/UserDao.java
 * 
 * FILE NAME        	: UserDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.authorization.client.dao.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;


import com.deppon.foss.module.authorization.client.dao.IUserDao;
import com.deppon.foss.module.authorization.client.dto.PkpUserEntityDto;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.util.define.FossConstants;


/**
 * 用户DAO层访问实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:32:55,content:TODO </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:32:55
 * @since
 * @version
 */
public class UserDao implements IUserDao {
	
	private static final String NAMESPACE = "foss.pkp.UserEntityMapper.";
	
	private SqlSession sqlSession;
	
	private PkpUserEntityDto dto;

	@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public UserEntity getById(String id) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + "getById", id);
	}

	@Override
	public List<UserEntity> getAll(UserEntity user) {
		
		return sqlSession.selectList(NAMESPACE + "getAll", user);
	}

	@Override
	public void update(UserEntity user) {
		dto = new PkpUserEntityDto();
		//BeanUtils.copyProperties(user, dto);
		if(user.getEmployee()!=null)
			dto.setEmpCode(user.getEmployee().getEmpCode());
		dto.setValidDate(user.getBeginDate());
		dto.setInvalidDate(user.getEndDate());
		sqlSession.update(NAMESPACE + "update", dto);
	}

	@Override
	public void insert(UserEntity user) {
		dto = new PkpUserEntityDto();
		//BeanUtils.copyProperties(user, dto); 
		if(user.getEmployee()!=null)
			dto.setEmpCode(user.getEmployee().getEmpCode());
		dto.setValidDate(user.getBeginDate());
		dto.setInvalidDate(user.getEndDate());
		sqlSession.insert(NAMESPACE + "insert", dto);
	}

	@Override
	public void deleteById(String userId) {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + "deleteById", userId);
	}

	@Override
	public UserEntity getUserWithRoleIdAndFunctionCodeById(String userId) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + 
				"getUserWithRoleIdAndFunctionCodeById", userId);
	}

	@Override
	public UserEntity getByLoginName(String userName) {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String,String>();
		map.put("userCode", userName);
		map.put("active", FossConstants.ACTIVE);
		return sqlSession.selectOne(NAMESPACE +"selectUserByCode", map);
	}

}