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
 * FILE PATH        	: authorization/src/main/java/com/deppon/foss/module/authorization/client/dao/imp/UserFunctionDao.java
 * 
 * FILE NAME        	: UserFunctionDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.authorization.client.dao.imp;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.authorization.client.dao.IUserFunctionDao;
import com.deppon.foss.module.authorization.client.domain.UserFunction;

/**
 * 用户功能DAO层实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:33:05,content:TODO </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:33:05
 * @since
 * @version
 */
public class UserFunctionDao implements IUserFunctionDao {

	private SqlSession sqlSession;

	@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public void insertUserFunction(UserFunction userFunction) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public List<UserFunction> queryUserfunctionByUserID(String userId) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(
				"foss.userfunction.queryUserfunctionByUserID", userId);
	}
	/**
	 * 
	 * 删除用户方法
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	@Override
	public void deleteUserfunctionByUserID(String userId) {
		// TODO Auto-generated method stub
		/*sqlSession.delete("foss.userfunction.deleteUserfunctionByUserID",
				userId);*/
	}

}