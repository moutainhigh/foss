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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/dao/impl/MarketingEventDAO.java
 * 
 * FILE NAME        	: MarketingEventDAO.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IMarketingEventDAO;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MarketingEventEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.MarketingEventDto;

public class MarketingEventDAO extends SqlSessionDaoSupport implements IMarketingEventDAO {
	/**
	 * ibatis Mapper
	 */
	private static final String MAKETING_EVENT_NAME_SPASE="com.deppon.foss.module.pickup.pricing.api.server.dao.MarketingEventMapper.";
	
	/**
	 * <p>
	 * Description:查询价格折扣方案列表<br />
	 * </p>
	 * @author admin
	 * @version 0.1 2012-10-18
	 * @return
	 * List<MarketingEventEntity>
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<MarketingEventDto> selectMarketingEventList(MarketingEventEntity marketintEvent, int start, int limit) {
		//设置分页条件
		RowBounds rowBounds = new RowBounds(start, limit);
		String sql = MAKETING_EVENT_NAME_SPASE + "searchMarketingEventByCondition";	
		return getSqlSession().selectList(sql, marketintEvent, rowBounds);
	}
	
	/**
	 * <p>
	 * Description:根据主键Id删除记录<br />
	 * </p>
	 * @author admin
	 * @version 0.1 2012-10-18
	 * @param id
	 * @return
	 * int
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		return getSqlSession().delete(MAKETING_EVENT_NAME_SPASE + "deleteByPrimaryKey",id);
	}

	 /**
     * <p>
     * Description:新增记录（内容不为空的字段）<br />
     * </p>
     * @author admin
     * @version 0.1 2012-10-18
     * @param record
     * @return
     * int
     */
	@Override
	public int insertSelective(MarketingEventEntity record) {
		String sql = MAKETING_EVENT_NAME_SPASE + "insertSelective";		
		return getSqlSession().insert(sql, record);				
	}

	 /**
     * <p>
     * Description:根据主键查询记录<br />
     * </p>
     * @author admin
     * @version 0.1 2012-10-18
     * @param id
     * @return
     * MarketingEventEntity
     */
	@Override
	public MarketingEventEntity selectByPrimaryKey(String id) {
		String sql = MAKETING_EVENT_NAME_SPASE + "selectByPrimaryKey";		
		return (MarketingEventEntity)getSqlSession().selectOne(sql, id);				
	}

	/**
     * <p>
     * Description:根据code查询记录<br />
     * </p>
     * @author admin
     * @version 0.1 2012-10-18
     * @param id
     * @return
     * MarketingEventEntity
     */
	@Override
	@SuppressWarnings("unchecked")
    public List<MarketingEventEntity> selectByMarketCode(String code, Date billDate) {
		Map map = new HashMap();
		map.put("code", code);
		map.put("endTime", billDate);
		String sql = MAKETING_EVENT_NAME_SPASE + "selectByMarketCode";		
		return getSqlSession().selectList(sql, map);	
    }
	/**
     * <p>
     * Description:根据主键更新内容不为空的字段<br />
     * </p>
     * @author admin
     * @version 0.1 2012-10-18
     * @param record
     * @return
     * int
     */
	@Override
	public int updateByPrimaryKeySelective(MarketingEventEntity record) {
		String sqlAddress = MAKETING_EVENT_NAME_SPASE + "updateByPrimaryKeySelective";		
		return getSqlSession().update(sqlAddress, record);				
	}

	 /**
     * 
     * <p>
     * Description:根据条件bean查询获取符合条件的记录列表，目前支持参数：tSrvPriceRegionId，modifyTime<br />
     * </p>
     * @author admin
     * @version 0.1 2012-11-12
     * @param marketintEvent
     * @return
     * List<MarketingEventEntity>
     */
	@Override
	@SuppressWarnings("unchecked")
	public List<MarketingEventEntity> selectMarketingEventList(MarketingEventEntity marketintEvent) {
		String sql = MAKETING_EVENT_NAME_SPASE + "searchMarketingEvent";	
		return getSqlSession().selectList(sql, marketintEvent);
	}
	
	 /**
   	 * <p>
   	 * Description:查询价格折扣方案列表<br />
   	 * </p>
   	 * @author sz
   	 * @version 0.1 2012-12-06
   	 * @param marketintEvent
   	 * @return
   	 * List<MarketingEventDto>
   	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MarketingEventEntity> queryMarketingEventList(MarketingEventEntity marketintEvent) {
		String sql = MAKETING_EVENT_NAME_SPASE + "queryMarketingEventByCondition";	
		return getSqlSession().selectList(sql, marketintEvent);
	}
	
	/**
   	 * <p>
   	 * Description:查询价格折扣方案总数<br />
   	 * </p>
   	 * @author sz
   	 * @version 0.1 2012-12-06
   	 * @param marketintEvent
   	 * @return
   	 * List<MarketingEventDto>
   	 */
	@Override
	public Long countMarketingEvent(MarketingEventEntity marketintEvent) {
		String sql = MAKETING_EVENT_NAME_SPASE + "countMarketingEventByCondition";	
		return (Long)getSqlSession().selectOne(sql, marketintEvent);
	}
	
	/**
   	 * 
   	 * <p>查询价格折扣方案列表（分页）</p> 
   	 * @author Administrator
   	 * @date 2012-12-14 上午9:34:40
   	 * @param marketintEvent
   	 * @param start
   	 * @param limit
   	 * @return
   	 * @see
   	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MarketingEventEntity> queryMarketingEventList(MarketingEventEntity marketingEvent, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		String sql = MAKETING_EVENT_NAME_SPASE + "queryMarketingEventByCondition";	
		return getSqlSession().selectList(sql, marketingEvent, rowBounds);
	}
	
	/**
	 * <p>
	 * Description:根据NAME查询折扣方案<br />
	 * </p>
	 * @author sz
	 * @version 0.1 2012-12-06
	 * @param marketintEventName
	 * @return
	 * List<MarketingEventDto>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MarketingEventEntity> queryMarketingEventByName(String marketintEventName) {
		String sql = MAKETING_EVENT_NAME_SPASE + "queryMarketingEventByName";	
		return getSqlSession().selectList(sql, marketintEventName);
	}

	/**
	 * @Description: 获取折扣方案最大的编码
	 * Company:IBM
	 * @author FOSSDP-sz
	 * @date 2013-1-5 上午11:35:38
	 * @return
	 * @version V1.0
	 */
	@Override
	public String getMarketingEventMaxCode(String type) {
		String sql = MAKETING_EVENT_NAME_SPASE + "getMarketEventMaxCode";	
		return (String)getSqlSession().selectOne(sql, type);
	}

}