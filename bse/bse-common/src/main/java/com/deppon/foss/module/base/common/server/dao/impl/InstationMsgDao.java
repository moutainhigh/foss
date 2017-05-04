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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/dao/impl/InstationMsgDao.java
 * 
 * FILE NAME        	: InstationMsgDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.common.api.server.dao.IInstationMsgDao;
import com.deppon.foss.module.base.common.api.shared.domain.InstationMsgEntity;
import com.deppon.foss.module.base.common.api.shared.dto.MessagesDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.define.FossConstants;

/**
 * 站内消息明细Dao
 * @author 101911-foss-zhouChunlai
 * @date 2012-12-10 上午9:05:20
 */
public class InstationMsgDao extends iBatis3DaoImpl implements IInstationMsgDao {
	
	private static final String NAMESPACE = "foss.bse.bse-common.instationMsgDao.";
	
	/**
	 * 新增人员对人员发的站内消息
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-13 上午9:11:04
	 */
	@Override
	public int insertInstationMsg(InstationMsgEntity entity) { 
		return getSqlSession().insert(NAMESPACE + "insertInstationMsg", entity);
	}
	/**
	 * 批量新增站内消息
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-13 下午7:06:14
	 */
	public int batchSaveMsg(List<InstationMsgEntity> instationMsgList){
	    //	 该批量执行有性能问题，现在已弃用，mapper文件中该方法以及注释掉   
		return getSqlSession().insert(NAMESPACE + "batchSaveInstationMsg", instationMsgList);
	}
	
	/**
	 * 根据条件查询出所有站内消息数据
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-13 上午11:16:51
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MessagesDto> queryInstationMsgByEntity(MessagesDto dto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE + "queryInstationMsgByEntity", dto, rowBounds);
	}
	
	/**
	 * 根据条件查询出所有站内消息数据总条数
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午11:05:43
	 */
	@Override
	public long countQueryInstationMsgByEntity(MessagesDto dto) {
		return (Long) getSqlSession().selectOne(NAMESPACE+"countQueryInstationMsgByEntity", dto);
	}
	
	/**
	 * 根据员工查询出所有站内消息数据
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-13 上午11:16:51
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MessagesDto> queryInstationMsgByReceiveUserInfo(String receiveUserCode,String receiveOrgCode,Set<String> receiveRoleCodes, int start, int limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		//接收方编码为当前登录人
		map.put("receiveSubOrgCode", receiveOrgCode);
		map.put("receiveRoleCodes", receiveRoleCodes);
		map.put("systemConstant", FossConstants.ROOT_ORG_CODE);
		//消息读取状态为未读
		map.put("isReaded",DictionaryValueConstants.MSG__READ_STATUS__UNREAD);
		//消息类型为全网消息和站内消息
		map.put("msgType_allNet",DictionaryValueConstants.MSG_TYPE__ALLNET);
		map.put("msgType_normal",DictionaryValueConstants.MSG_TYPE__NORMAL);
		//消息状态为有效
		map.put("active",FossConstants.ACTIVE);
		//分页条件
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE + "queryInstationMsgByReceiveUserCode", map, rowBounds);
	}
	
	/**
	 * 根据条件查询出所有站内消息数据总条数
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午11:05:43
	 */
	@Override
	public long countQueryInstationMsgByReceiveUserInfo(String receiveUserCode,String receiveOrgCode,Set<String> receiveRoleCodes) {
		Map<String, Object> map = new HashMap<String, Object>();
		//接收方编码为当前登录人
		map.put("receiveSubOrgCode", receiveOrgCode);
		map.put("receiveRoleCodes", receiveRoleCodes);
		map.put("systemConstant",FossConstants.ROOT_ORG_CODE);
		//消息读取状态为未读
		map.put("isReaded",DictionaryValueConstants.MSG__READ_STATUS__UNREAD);
		//消息类型为全网消息和站内消息
		map.put("msgType_allNet",DictionaryValueConstants.MSG_TYPE__ALLNET);
		map.put("msgType_normal",DictionaryValueConstants.MSG_TYPE__NORMAL);
		//消息状态为有效
		map.put("active",FossConstants.ACTIVE);
		return (Long) getSqlSession().selectOne(NAMESPACE+"countQueryInstationMsgByReceiveUserCode", map);
	}
	
	/**
	 * 统计当前用户未处理站内消息数据总条数
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午10:56:22
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MessagesDto> queryInstationMsgTotal(String receiveUserCode,String receiveOrgCode,Set<String> receiveRoleCodes,String msgType) { 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("receiveSubOrgCode", receiveOrgCode);
		map.put("receiveRoleCodes", receiveRoleCodes);
		map.put("systemConstant", FossConstants.ROOT_ORG_CODE);
		map.put("msgType",msgType);
		map.put("msgType_allNet", DictionaryValueConstants.MSG_TYPE__ALLNET);
		map.put("isReaded",DictionaryValueConstants.MSG__READ_STATUS__UNREAD);
		map.put("active",FossConstants.ACTIVE);
		map.put("rowNum",NumberConstants.NUMERAL_SIX);
		
		return getSqlSession().selectList(NAMESPACE+"queryInstationMsgTotal", map);
	}
	
	/** 
	 * 查询当前用户未处理站内消息数据
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-29 上午8:32:45
	 * @return 
	 * @see com.deppon.foss.module.base.common.api.server.dao.IInstationMsgDao#queryInstationMsgTotal(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MessagesDto> queryMsgByMsgType(String receiveUserCode,String receiveOrgCode,Set<String> receiveRoleCodes,String msgType, int start, int limit) { 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("receiveSubOrgCode", receiveOrgCode);
		map.put("receiveRoleCodes", receiveRoleCodes);
		map.put("systemConstant", FossConstants.ROOT_ORG_CODE);
		map.put("msgType",msgType);
		map.put("msgType_allNet", DictionaryValueConstants.MSG_TYPE__ALLNET);
		map.put("isReaded",DictionaryValueConstants.MSG__READ_STATUS__UNREAD);
		map.put("active",FossConstants.ACTIVE); 
		//分页条件
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE+"queryMsgByMsgType", map ,rowBounds);
	}
	/**
	 *	查询当前用户未处理站内消息数据总条数
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午11:05:43
	 */
	@Override
	public long countQueryMsgByMsgType(String receiveUserCode,String receiveOrgCode,Set<String> receiveRoleCodes,String msgType ) { 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("receiveSubOrgCode", receiveOrgCode);
		map.put("receiveRoleCodes", receiveRoleCodes);
		map.put("systemConstant", FossConstants.ROOT_ORG_CODE);
		map.put("msgType",msgType);
		map.put("msgType_allNet", DictionaryValueConstants.MSG_TYPE__ALLNET);
		map.put("isReaded",DictionaryValueConstants.MSG__READ_STATUS__UNREAD);
		map.put("active",FossConstants.ACTIVE); 
		
		return (Long) getSqlSession().selectOne(NAMESPACE+"countQueryMsgByMsgType", map);
	}
	/**
	 * 根据Id更新站内消息的读取状态
	 * @author 101911-foss-zhouChunlai
	 * @param ids Id列表
	 * @date 2012-12-25 下午3:19:12
	 * @return 
	 */
	@Override
	public int  updateInstationMsgByIds(List<String> ids,String modifyUserCode,String modifyUserName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids",ids); 
		map.put("isReaded_N",DictionaryValueConstants.MSG__READ_STATUS__UNREAD);
		map.put("isReaded_R",DictionaryValueConstants.MSG__READ_STATUS__READ);
		map.put("modifyUserCode",modifyUserCode);
		map.put("modifyUserName",modifyUserName);
		map.put("modifyTime",new Date());
		map.put("active",FossConstants.ACTIVE); 
		return getSqlSession().update(NAMESPACE+"updateInstationMsg", map);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<MessagesDto> queryFailingInvoiceMsgTotal(String currentDeptCode, String msgType) { 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentDeptCode", currentDeptCode);
		map.put("msgType",msgType);
		map.put("active",FossConstants.ACTIVE);
		map.put("isReaded",DictionaryValueConstants.MSG__READ_STATUS__UNREAD);
		return getSqlSession().selectList(NAMESPACE+"queryFailingInvoiceMsgTotal", map);
	}
	/** 
	 * <p>TODO(查询未对账月结客户消息)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-2 上午9:50:35
	 * @param currentDeptCode
	 * @param msgTypeUnreconciliation
	 * @return 
	 * @see com.deppon.foss.module.base.common.api.server.dao.IInstationMsgDao#queryUnReconciliationMsgTotal(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MessagesDto> queryUnReconciliationMsgTotal(
			String currentDeptCode, String msgType) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentDeptCode", currentDeptCode);
		map.put("msgType",msgType);
		map.put("active",FossConstants.ACTIVE);
		return getSqlSession().selectList(NAMESPACE+"queryUnReconciliationMsgTotal", map);
	}
	/** 
	 * <p>TODO(查询距结款时间不足5日还未还款月结客户消息)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-2 上午9:50:35
	 * @param currentDeptCode
	 * @param msgTypeNorepayment
	 * @return 
	 * @see com.deppon.foss.module.base.common.api.server.dao.IInstationMsgDao#queryNoRepaymentMsgTotal(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MessagesDto> queryNoRepaymentMsgTotal(String currentDeptCode,
			String msgType) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentDeptCode", currentDeptCode);
		map.put("msgType",msgType);
		map.put("active",FossConstants.ACTIVE);
		return getSqlSession().selectList(NAMESPACE+"queryNoRepaymentMsgTotal", map);
	}
}
