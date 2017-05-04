/**
 *  initial comments.
 */
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
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/dao/impl/MarketingEventChannelDao.java
 * 
 * FILE NAME        	: MarketingEventChannelDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IMarketingEventChannelDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MarketingEventChannelEntity;

public class MarketingEventChannelDao extends SqlSessionDaoSupport implements IMarketingEventChannelDao {
	
	private static final String MAKETING_EVENT_NAME_SPACE="com.deppon.foss.module.pickup.pricing.api.server.dao.MarketingEventChannelEntityMapper.";
	private static final String SEARCH_CHANNEL = "searchChannelListByEventId";
	private static final String SELECT_BY_PK = "selectByPrimaryKey";
	private static final String SELECT_BY_CONDITION = "selectByCondition";
	private static final String INSERT_SELECTIVE = "insertSelective";
	private static final String DELETE_BY_PK = "deleteByPrimaryKey";
	private static final String UPDATE_BY_PK_SELECTIVE = "updateByPrimaryKeySelective";
	private static final String DELETE_BY_MARKETEVENTID = "deleteByMarketEventId";
	private static final String UPDATE_BY_MARKETEVENTID = "updateByMarketEventIdSelective";
	private static final String DELETE_BY_MARKETEVENTID_CHANNELCODE = "deleteByMarketEventIdAndChannelCode";
	 /**
     * 
     * @Description: 根据折扣方案查询该方案所有渠道信息
     * @author FOSSDP-sz
     * @date 2013-3-8 上午10:29:24
     * @param eventId
     * @return
     * @version V1.0
     */
	@Override
	@SuppressWarnings("unchecked")
	public List<MarketingEventChannelEntity> queryMarketingEventChannelListByEventId(String eventId) {
		String sql = MAKETING_EVENT_NAME_SPACE + SEARCH_CHANNEL;	
		return getSqlSession().selectList(sql, eventId);
	}
	/**
	 * 
	 * @Description: 根据主键删除记录
	 * @author FOSSDP-sz
	 * @date 2013-3-8 上午10:28:35
	 * @param id
	 * @return
	 * @version V1.0
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		return getSqlSession().delete(MAKETING_EVENT_NAME_SPACE + DELETE_BY_PK, id);
	}

	/**
     * 
     * @Description: 新增记录，值不为空的字段不插入
     * @author FOSSDP-sz
     * @date 2013-3-8 上午10:28:47
     * @param record
     * @return
     * @version V1.0
     */
	@Override
	public int insertSelective(MarketingEventChannelEntity record) {
		String sql = MAKETING_EVENT_NAME_SPACE + INSERT_SELECTIVE;		
		return getSqlSession().insert(sql, record);				
	}
	/**
     * 
     * @Description: 根据主键查询记录
     * @author FOSSDP-sz
     * @date 2013-3-8 上午10:28:58
     * @param id
     * @return
     * @version V1.0
     */
	@Override
	public MarketingEventChannelEntity selectByPrimaryKey(String id) {
		String sql = MAKETING_EVENT_NAME_SPACE + SELECT_BY_PK;		
		return (MarketingEventChannelEntity)getSqlSession().selectOne(sql, id);				
	}
	 /**
     * 
     * @Description: 根据主键更新记录，值为空的字段不更新
     * @author FOSSDP-sz
     * @date 2013-3-8 上午10:29:15
     * @param record
     * @return
     * @version V1.0
     */
	@Override
	public int updateByPrimaryKeySelective(MarketingEventChannelEntity record) {
		String sqlAddress = MAKETING_EVENT_NAME_SPACE + UPDATE_BY_PK_SELECTIVE;		
		return getSqlSession().update(sqlAddress, record);				
	}

	 /**
     * 
     * @Description: 根据市场活动ID删除记录
     * @author FOSSDP-sz
     * @date 2013-3-8 上午10:29:35
     * @param marketEventId
     * @return
     * @version V1.0
     */
	@Override
	public int deleteByMarketEventId(String marketEventId) {
		return getSqlSession().delete(MAKETING_EVENT_NAME_SPACE + DELETE_BY_MARKETEVENTID, marketEventId);
	}
	/**
     * 
     * @Description: 根据市场活动ID修改记录
     * @author FOSSDP-sz
     * @date 2013-3-8 上午10:29:44
     * @param record
     * @return
     * @version V1.0
     */
	@Override
	public int updateByMarketEventIdSelective(MarketingEventChannelEntity record) {
		String sqlAddress = MAKETING_EVENT_NAME_SPACE + UPDATE_BY_MARKETEVENTID;		
		return getSqlSession().update(sqlAddress, record);				
	}
	/**
     * 
     * @Description: 根据条件查询记录
     * @author FOSSDP-sz
     * @date 2013-3-8 上午10:29:07
     * @param record
     * @return
     * @version V1.0
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<MarketingEventChannelEntity> selectByCondition(
			MarketingEventChannelEntity record) {
		String sql = MAKETING_EVENT_NAME_SPACE + SELECT_BY_CONDITION;		
		return getSqlSession().selectList(sql, record);	
	}
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.dao.impl.MarketingEventChannelDao.deleteByMarketEventIdAndChannelCode
	 * @Description:根据折扣方案ID和渠道code删除对应渠道信息
	 *
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-12 下午2:55:16
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-12    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	@Override
	public int deleteByMarketEventIdAndChannelCode(MarketingEventChannelEntity channelEntity,List<String> channelCodes){
		String sql = MAKETING_EVENT_NAME_SPACE+DELETE_BY_MARKETEVENTID_CHANNELCODE;
		Map channelMap = new HashMap();
		channelMap.put("channelCodes", channelCodes);
		channelMap.put("marketEventId", channelEntity.getMarketingEventId());
		return getSqlSession().delete(sql,channelMap);
	}
	
}