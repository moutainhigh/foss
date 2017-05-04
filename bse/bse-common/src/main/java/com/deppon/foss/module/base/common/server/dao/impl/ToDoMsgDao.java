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
 * PROJECT NAME	: bse-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/dao/impl/ToDoMsgDao.java
 * 
 * FILE NAME        	: ToDoMsgDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.common.api.server.dao.IToDoMsgDao;
import com.deppon.foss.module.base.common.api.shared.domain.ToDoMsgEntity;
import com.deppon.foss.module.base.common.api.shared.dto.ToDoMsgDto;
import com.deppon.foss.module.base.common.api.shared.dto.TodoDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;

/**
 * 待办事项Dao
 * @author 101911-foss-zhouChunlai
 * @date 2012-12-14 上午8:19:10
 */
public class ToDoMsgDao extends iBatis3DaoImpl implements IToDoMsgDao {
	
	private static final String NAMESPACE = "foss.bse.bse-common.toDoMsgDao.";
	
	/**
	 * 批量保存待办
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-15 下午1:33:03updateToDoMsgByCondition
	 */
	@Override
	public int batchSaveToDoMsg(List<ToDoMsgEntity> entityList) {
		return getSqlSession().insert(NAMESPACE + "batchSaveToDoMsg", entityList);
	}
	
	/**
	 * <p>保存代办事项</p> 
	 * @author 094463-foss-xieyantao
	 * @date 2013-4-7 上午11:28:42
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.common.api.server.dao.IToDoMsgDao#saveToDoMsg(com.deppon.foss.module.base.common.api.shared.domain.ToDoMsgEntity)
	 */
	@Override
	public int saveToDoMsg(ToDoMsgEntity entity) {
	    
	    return this.getSqlSession().insert(NAMESPACE + "saveToDoMsg", entity);
	}
	
	/**
	 * 批量删除待办事项
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-15 下午5:25:42
	 */
	@Override
	public int deleteToDoMsg(List<ToDoMsgEntity> entityList) {
		return getSqlSession().delete(NAMESPACE + "batchDeleteToDoMsg", entityList);
	}
	
	/**
	 * <p>删除待办事项</p> 
	 * @author 094463-foss-xieyantao
	 * @date 2013-4-7 下午6:12:24
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.common.api.server.dao.IToDoMsgDao#
	 */
	@Override
	public int deleteToDoMsgOne(ToDoMsgEntity entity) {
	    return this.getSqlSession().delete(NAMESPACE + "deleteToDoMsgOne",entity);
	}
	/**
	 * 根据单号、待补录类型 结束待办。
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 268984 
	 * @date 2016-11-16 上午10:57:53
	 * @param waybillNO
	 * @return
	 * @see
	 */
	@Override
	public int updateToDoMsgByCondition(String waybillNO,String businessType) {
		if(StringUtil.isEmpty(waybillNO)||StringUtil.isEmpty(businessType)){
			return 0;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("businessNo",waybillNO);
		map.put("businessType",businessType);
		map.put("status", DictionaryValueConstants.TODOMSG__STATUS_TYPE__PROCESSED);
	return getSqlSession().update(NAMESPACE + "updateToDoMsgByNoAndType", map);
	 
	}
	/**
	 * 根据接收方编码 ，待办业类型，待办流水号  结束待办
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 下午2:16:57
	 */
	@Override
	public int updateToDoMsgByCondition(String receiveOrgCode,String businessType, String serialNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serialNumber",serialNumber);
		map.put("receiveOrgCode",receiveOrgCode);
		map.put("businessType",businessType);
		map.put("status", DictionaryValueConstants.TODOMSG__STATUS_TYPE__PROCESSED);
		
	return getSqlSession().update(NAMESPACE + "updateToDoMsgByCondition", map);
	 
	}
	/**
	 * 根据接收方编码 ，待办业类型，待办流水号  结束待办(给接送货)
	 * @author 130346-foss-lifanghong
	 * @date 2013-07-16 下午5:42:55 
	 */
	@Override
	public int updateMsgByCondition(String receiveOrgCode,String businessType, String serialNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serialNumber",serialNumber);
		map.put("receiveOrgCode",receiveOrgCode);
		map.put("businessType",businessType);
		map.put("status", DictionaryValueConstants.TODOMSG__STATUS_TYPE__PROCESSED);
		
	return getSqlSession().update(NAMESPACE + "updateMsgByCondition", map);
	 
	}
	/**
	 * 根据待办接收组织编码，接收方角色，待办类型，状态查询待办信息
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午10:56:24
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ToDoMsgDto> selectToDoMsgDetailByCondition(String receiveOrgCode, Set<String> receiveRoleCodes, String businessType,String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("receiveOrgCode",receiveOrgCode);
		map.put("receiveSubOrgCode",receiveOrgCode);
		map.put("receiveRoleCodes",receiveRoleCodes);
		map.put("receiveType_O",MessageConstants.MSG__RECEIVE_TYPE__ORG);
		map.put("receiveType_OR",MessageConstants.MSG__RECEIVE_TYPE__ORG_ROLE);  
		map.put("businessType",businessType);
		map.put("status",status);
		return this.getSqlSession().selectList(NAMESPACE + "selectToDoMsgDetailByCondition", map);
	}
	
	
	/**
	 * 根据待办接收组织编码，接收方角色，待办类型，状态查询待办信息
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午10:56:24
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ToDoMsgDto> selectToDoMsgDetailByConditionOne(String receiveOrgCode, Set<String> receiveRoleCodes, String businessType,String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("receiveOrgCode",receiveOrgCode);
		map.put("receiveSubOrgCode",receiveOrgCode);
		map.put("receiveRoleCodes",receiveRoleCodes);
		map.put("receiveType_O",MessageConstants.MSG__RECEIVE_TYPE__ORG);
		map.put("receiveType_OR",MessageConstants.MSG__RECEIVE_TYPE__ORG_ROLE);  
		map.put("businessType",businessType);
		map.put("status",status);
		return this.getSqlSession().selectList(NAMESPACE + "selectToDoMsgDetailByConditionOne", map);
	}
	
	/**
	 * 根据businesstype分类统计代办个数
	 * @param receiveOrgCode
	 * @param status
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ToDoMsgDto> countToDoMsgDetailGroupByBusinessType(
			String receiveOrgCode, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("receiveOrgCode",receiveOrgCode);
		map.put("receiveSubOrgCode",receiveOrgCode);
		map.put("receiveType_O",MessageConstants.MSG__RECEIVE_TYPE__ORG);
		map.put("receiveType_OR",MessageConstants.MSG__RECEIVE_TYPE__ORG_ROLE);  
		map.put("status",status);
		return this.getSqlSession().selectList(NAMESPACE + "countToDoMsgDetailGroupByBusinessType", map);
	}
	
	/**
	 * 根据待办实体参数查询待办信息
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午10:56:22
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ToDoMsgDto> selectToDoMsgDetailByEntity(ToDoMsgDto dto) {
		return this.getSqlSession().selectList(NAMESPACE + "selectToDoMsgDetailByDto", dto);
	}
	/**
	 * 根据待办实体参数查询待办信息总条数
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午11:05:43
	 */
	@Override
	public long countSelectToDoMsgDetailByEntity(ToDoMsgDto dto) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dto",dto); 
		map.put("receiveType_O",MessageConstants.MSG__RECEIVE_TYPE__ORG);
		map.put("receiveType_OR",MessageConstants.MSG__RECEIVE_TYPE__ORG_ROLE);  
		return (Long) getSqlSession().selectOne(NAMESPACE+"countSelectToDoMsgDetailByEntity", map);
	}
	/**
	 * 根据待办实体参数查询待办信息 带分页
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午10:56:22
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ToDoMsgDto> selectToDoMsgDetailByEntity(ToDoMsgDto dto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dto",dto); 
		map.put("receiveType_O",MessageConstants.MSG__RECEIVE_TYPE__ORG);
		map.put("receiveType_OR",MessageConstants.MSG__RECEIVE_TYPE__ORG_ROLE);  
		return getSqlSession().selectList(NAMESPACE+"selectToDoMsgDetailByEntity", map, rowBounds);
	}

	/**
	 * 根据待办类型查询待办事项列表
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午10:56:22
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ToDoMsgDto> queryToDoMsgByBisType(String businessType,String receiveOrgCode,Set<String> receiveRoleCodes,String urlType, int start, int limit) { 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("businessType",businessType);
		map.put("receiveSubOrgCode",receiveOrgCode);
		map.put("receiveRoleCodes",receiveRoleCodes);
		map.put("urlType",urlType);
		map.put("receiveType_O",MessageConstants.MSG__RECEIVE_TYPE__ORG);
		map.put("receiveType_OR",MessageConstants.MSG__RECEIVE_TYPE__ORG_ROLE);
		map.put("status",DictionaryValueConstants.TODOMSG__STATUS_TYPE__PROCESSING);

		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE+"queryToDoMsgByBisType", map,rowBounds);
	}
	/**
	 *根据待办类型查询待办事项列表 总条数
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午11:05:43
	 */
	@Override
	public long countQueryToDoMsgByBisType(String businessType,String receiveOrgCode,Set<String> receiveRoleCodes,String urlType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("businessType",businessType);
		map.put("receiveSubOrgCode",receiveOrgCode);
		map.put("receiveRoleCodes",receiveRoleCodes);
		map.put("urlType",urlType);
		map.put("receiveType_O",MessageConstants.MSG__RECEIVE_TYPE__ORG);
		map.put("receiveType_OR",MessageConstants.MSG__RECEIVE_TYPE__ORG_ROLE);
		map.put("status",DictionaryValueConstants.TODOMSG__STATUS_TYPE__PROCESSING);
		return (Long) getSqlSession().selectOne(NAMESPACE+"countQueryToDoMsgByBisType", map);
	}
	/**
	 * 统计当前用户未处理的待办事项
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午10:56:22
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ToDoMsgDto> queryToDoMsgTotal(String receiveOrgCode,Set<String> receiveRoleCodes,String urlType) { 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("receiveSubOrgCode",receiveOrgCode);
		map.put("receiveRoleCodes",receiveRoleCodes);
		map.put("receiveType_O",MessageConstants.MSG__RECEIVE_TYPE__ORG);
		map.put("receiveType_OR",MessageConstants.MSG__RECEIVE_TYPE__ORG_ROLE);
		map.put("status",DictionaryValueConstants.TODOMSG__STATUS_TYPE__PROCESSING);
		map.put("urlType",urlType);
		map.put("rowNum",NumberConstants.NUMERAL_SIX);
		
		return getSqlSession().selectList(NAMESPACE+"queryToDoMsgTotal", map);
	}

	@Override
	public Integer queryTOdoMsgTotalFromWaybill(TodoDto dto) {
		return (Integer) getSqlSession().selectOne(NAMESPACE+"queryTOdoMsgTotalFromWaybill", dto);
	}
	/**
	 * 查询库存超过90天，自动弃货未处理总数（根据当前传入的部门）
	 */
	@Override
	public Integer queryAutoAbandGoodsMsgTotal(TodoDto dto) {
		return (Integer) getSqlSession().selectOne(NAMESPACE+"queryAutoAbandGoodsMsgTotal", dto);
	}

	/**
	 * 根据部门查询cc催运单未解决的数量
	 */
	@Override
	public Integer queryCallCenterWaybillMsgTotal(TodoDto dto) {
		return (Integer) getSqlSession().selectOne(NAMESPACE+"queryCallCenterWaybillMsgTotal", dto);
	}
	
	
}
