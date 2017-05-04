/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-scheduling
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/dao/impl/ChangeQuantityDao.java
 * 
 *  FILE NAME     :ChangeQuantityDao.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IChangeQuantityDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ChangeQuantityEntity;


/**
 * 调整货量表dao实现
 * @author huyue
 * @date 2012-10-11 下午9:13:22
 */
public class ChangeQuantityDao extends iBatis3DaoImpl implements IChangeQuantityDao{

	private static final String NAMESPACE = "Foss.changeQuantity.";
	
	/**
	 * 新增调整货量表信息
	 * @author huyue
	 * @date 2012-11-14 下午8:31:57
	 */
	public int addchangeQuantity(ChangeQuantityEntity changeQuantityEntity) {
		String statement = NAMESPACE + "insert";
		return getSqlSession().insert(statement, changeQuantityEntity);
	}
	
	/**
	 * 新增调整货量表信息 选择插入
	 * @author huyue
	 * @date 2012-11-14 下午8:33:09
	 */
	public int addchangeQuantitySelective(ChangeQuantityEntity changeQuantityEntity) {
		String statement = NAMESPACE + "insertSelective";
		return getSqlSession().insert(statement, changeQuantityEntity);
	}
	
	/**
	 * 更新调整货量表信息
	 * @author huyue
	 * @date 2012-11-14 下午8:34:46
	 */
	public int updatechangeQuantity(ChangeQuantityEntity changeQuantityEntity) {
		String statement = NAMESPACE + "updateByPrimaryKey";
		return getSqlSession().insert(statement, changeQuantityEntity);
	}
	
	/**
	 * 更新调整货量表信息 选择插入
	 * @author huyue
	 * @date 2012-11-14 下午8:34:09
	 */
	public int updatechangeQuantitySelective(ChangeQuantityEntity changeQuantityEntity) {
		String statement = NAMESPACE + "updateByPrimaryKeySelective";
		return getSqlSession().insert(statement, changeQuantityEntity);
	}
	
	/**
	 * 查询调整货量表信息
	 * @author huyue
	 * @date 2012-11-14 下午8:44:32
	 */
	public ChangeQuantityEntity querychangeQuantity(ChangeQuantityEntity changeQuantityEntity) {
		String statement = NAMESPACE + "select";
		return (ChangeQuantityEntity) getSqlSession().selectOne(statement, changeQuantityEntity);
	}
	
	/**
	 * 查询调整货量表信息 批量
	 * @author huyue
	 * @date 2012-11-14 下午8:50:30
	 */
	@SuppressWarnings("unchecked")
	public List<ChangeQuantityEntity> querychangeQuantityList(ChangeQuantityEntity changeQuantityEntity) {
		String statement = NAMESPACE + "select";
		return getSqlSession().selectList(statement, changeQuantityEntity);
	}
}