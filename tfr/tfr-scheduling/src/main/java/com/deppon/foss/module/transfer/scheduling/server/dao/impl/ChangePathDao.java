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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/dao/impl/ChangePathDao.java
 * 
 *  FILE NAME     :ChangePathDao.java
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IChangePathDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ChangePathEntity;


/**
 * 路径改变dao实现
 * @author huyue
 * @date 2012-10-11 下午9:13:22
 */
public class ChangePathDao extends iBatis3DaoImpl implements IChangePathDao{

	private static final String NAMESPACE = "Foss.changePath.";
	
	/**
	 * 查询路径改变LIST
	 * @author huyue
	 * @date 2012-10-18 下午6:41:24
	 */
	public List<ChangePathEntity> queryChangePath(ChangePathEntity changePathEntity) {
		
		@SuppressWarnings("unchecked")
		List<ChangePathEntity> changePathList = this.getSqlSession().selectList(NAMESPACE + "changePathQuery", changePathEntity);
		
		return changePathList;
	}
	
	/**
	 * 根据部门,原货区编号,时间查询相应调整策略 order by 生效时间倒序 routeNo 正序  如果有两次修改,则取最近一次修改
	 * @author huyue
	 * @date 2012-11-7 下午2:31:09
	 */
	public List<ChangePathEntity> queryByTime(Map<String, Object> map){
		@SuppressWarnings("unchecked")
		List<ChangePathEntity> changePathList = this.getSqlSession().selectList(NAMESPACE + "queryByTime", map);
		
		return changePathList;
	}

	/**
	 * 新增路径改变信息 自动筛选有值字段
	 * @author huyue
	 * @date 2012-10-18 下午6:41:43
	 */
	public int addChangePathSelect(ChangePathEntity changePathEntity) {
		String statement = NAMESPACE + "insertSelective";
		return getSqlSession().insert(statement, changePathEntity);
	}
	
	/**
	 * 新增路径改变信息
	 * @author huyue
	 * @date 2012-10-18 下午6:42:00
	 */
	public int addChangePath(ChangePathEntity changePathEntity) {
		String statement = NAMESPACE + "insert";
		return getSqlSession().insert(statement, changePathEntity);
	}
	
	/**
	 * 批量新增路径改变信息
	 * @author huyue
	 * @date 2012-10-18 下午6:42:09
	 */
	public int addListChangePath(List<ChangePathEntity> changePathList) {
		return getSqlSession().insert(NAMESPACE + "insertList", changePathList);
	}
	
	/**
	 * 根据PKID更新路径改变信息 自动筛选有值字段
	 * @author huyue
	 * @date 2012-10-18 下午6:42:17
	 */
	public int updateChangePathSelect(ChangePathEntity changePathEntity) {
		String statement = NAMESPACE + "updateByPrimaryKeySelective";
		return getSqlSession().insert(statement, changePathEntity);
	}
	
	/**
	 * 根据PKID更新路径改变信息
	 * @author huyue
	 * @date 2012-10-18 下午6:42:26
	 */
	public int updateChangePath(ChangePathEntity changePathEntity) {
		String statement = NAMESPACE + "updateByPrimaryKey";
		return getSqlSession().insert(statement, changePathEntity);
	}
	
	/**
	 * 批量更新路径改变信息
	 * @author huyue
	 * @date 2012-10-18 下午6:42:46
	 */
	public int updateListChangePath(List<ChangePathEntity> changePathList) {
		return getSqlSession().insert(NAMESPACE + "updateList", changePathList);
	}
	
	/**
	 * 根据部门和原货区查询非合车路径改变信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-9 下午3:12:35
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.IChangePathDao#queryModifyChangePath(com.deppon.foss.module.transfer.scheduling.api.shared.domain.ChangePathEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ChangePathEntity queryModifyChangePath(String origOrgCode, String origGoodsAreaCode) {
		@SuppressWarnings("rawtypes")
		Map paramsMap = new HashMap();
		paramsMap.put("origOrgCode", origOrgCode);
		paramsMap.put("origGoodsAreaCode", origGoodsAreaCode);
		paramsMap.put("changeType", TransportPathConstants.CHANGEPATH_STATUS_MODIFY);
		paramsMap.put("routeNo", TransportPathConstants.CHANGEPATH_ROUTE_NO);
		paramsMap.put("executeTime", new Date());
		
		ChangePathEntity changePath = null;
		List<ChangePathEntity> changePathList = this.getSqlSession().selectList(NAMESPACE + "queryModifyChangePath", paramsMap);
		if(CollectionUtils.isNotEmpty(changePathList)){
			changePath = changePathList.get(0);
		}
		return changePath;
	}
	/**
	 * 根据部门和原货区查询合车路径改变信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-9 下午3:12:41
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.IChangePathDao#queryTogetherTruckChangePath(com.deppon.foss.module.transfer.scheduling.api.shared.domain.ChangePathEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ChangePathEntity> queryTogetherTruckChangePath(String origOrgCode, String origGoodsAreaCode) {
		
		@SuppressWarnings("rawtypes")
		Map paramsMap = new HashMap();
		paramsMap.put("origOrgCode", origOrgCode);
		paramsMap.put("origGoodsAreaCode", origGoodsAreaCode);
		paramsMap.put("changeType", TransportPathConstants.CHANGEPATH_STATUS_JOINCAR);
		paramsMap.put("executeTime", new Date());
		
		return this.getSqlSession().selectList(NAMESPACE + "queryTogetherTruckChangePath", paramsMap);
	}
	
	
}