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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/MarketingService.java
 * 
 * FILE NAME        	: MarketingService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.util.List;

import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryService;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IMarketingEventChannelDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IMarketingEventDAO;
import com.deppon.foss.module.pickup.pricing.api.server.service.IMaketingEventService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MarketingEventChannelEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MarketingEventEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.MarketingEventDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * @Description: 
 * MarketingService.java Create on 2013-3-17 下午4:41:57
 * Company:IBM
 * @author FOSSDP-Administrator
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class MarketingService implements IMaketingEventService {
	/**
	 * 数据字典SERVICE
	 */
	@Inject
	private IDataDictionaryService dataDictionaryService;
	/**
	 * 市场活动DAO
	 */
	@Inject
	private IMarketingEventDAO marketingEventDao;
	/**
	 * 渠道DAO
	 */
	@Inject
	private IMarketingEventChannelDao marketingEventChannelDao;

	/**
	 * 设置 数据字典SERVICE.
	 * 
	 * @param dataDictionaryService
	 *            the new 数据字典SERVICE
	 */
	public void setDataDictionaryService(IDataDictionaryService dataDictionaryService) {
		this.dataDictionaryService = dataDictionaryService;
	}

	/**
	 * 设置 渠道DAO.
	 * 
	 * @param marketingEventChannelDao
	 *            the new 渠道DAO
	 */
	public void setMarketingEventChannelDao(IMarketingEventChannelDao marketingEventChannelDao) {
		this.marketingEventChannelDao = marketingEventChannelDao;
	}

	/**
	 * 设置 市场活动DAO.
	 * 
	 * @param marketingEventDao
	 *            the new 市场活动DAO
	 */
	public void setMarketingEventDao(IMarketingEventDAO marketingEventDao) {
		this.marketingEventDao = marketingEventDao;
	}

	/**
	 * 
	 * <p>
	 * Description:根据条件查询折扣方案列表（目前条件为：方案名称、方案状态、起始结束时间）<br />
	 * </p>
	 * 
	 * @author admin
	 * 
	 * @version 0.1 2012-10-23
	 * 
	 * @param marketintEvent
	 * 
	 * @param start
	 * 
	 * @param limit
	 * 
	 * @return
	 * 
	 *         List<MarketingEventDto>
	 */
	public List<MarketingEventDto> selectMarketingEventList(MarketingEventEntity marketintEvent, int start, int limit) {
		List<MarketingEventDto> list = marketingEventDao.selectMarketingEventList(marketintEvent, start, limit);
		// 获取折扣方案对应的渠道code和渠道name
		MarketingEventDto eventDto;
		String eventId;
		List<MarketingEventChannelEntity> channelList;
		for (int i = 0; i < list.size(); i++) {
			eventDto = (MarketingEventDto) list.get(i);
			eventId = eventDto.getId();
			channelList = marketingEventChannelDao.queryMarketingEventChannelListByEventId(eventId);
			MarketingEventChannelEntity channel;
			String channelCode;
			String channelName = "";
			for (int j = 0; j < channelList.size(); j++) {
				channel = (MarketingEventChannelEntity) channelList.get(j);
				channelCode = channel.getSalesChannelCode();
				// 根据渠道Code从数据字典获取渠道名称
				DataDictionaryEntity dict = dataDictionaryService.queryDataDictionaryByTermsCode(channelCode);
				if (null != dict) {
					channelName = dict.getTermsName();
					eventDto.addOrderChannel(channelCode, channelName);
				}
			}
		}
		return list;
	}

	/**
	 * 
	 * <p>
	 * Description: 根据主键查找价格折扣方案<br />
	 * </p>
	 * 
	 * @author admin
	 * 
	 * @version 0.1 2012-10-18
	 * 
	 * @param id
	 * 
	 * @return
	 * 
	 *         MarketingEventEntity
	 */
	@Override
	public MarketingEventEntity selectByPrimaryKey(String id) {
		return marketingEventDao.selectByPrimaryKey(id);
	}

	/**
	 * 
	 * <p>
	 * Description:新增价格折扣方案（只有字段值不为空的才被存入）<br />
	 * </p>
	 * 
	 * @author admin
	 * 
	 * @version 0.1 2012-10-18
	 * 
	 * @param marketingEventEntity
	 * 
	 * @return
	 * 
	 *         int
	 */
	@Override
	public int addMaketingEventSelective(MarketingEventEntity marketingEventEntity) {
		return marketingEventDao.insertSelective(marketingEventEntity);
	}

	/**
	 * 
	 * <p>
	 * Description:根据主键更新价格折扣方案（只有字段值不为空的才会被更新）<br />
	 * </p>
	 * 
	 * @author admin
	 * 
	 * @version 0.1 2012-10-18
	 * 
	 * @param marketingEventEntity
	 * 
	 * @return
	 * 
	 *         int
	 */
	@Override
	public int updateByPrimaryKeySelective(MarketingEventEntity marketingEventEntity) {
		return marketingEventDao.updateByPrimaryKeySelective(marketingEventEntity);
	}

	/**
	 * 
	 * <p>
	 * Description:根据主键ID删除状态为“新建”的价格折扣方案<br />
	 * </p>
	 * 
	 * @author admin
	 * 
	 * @version 0.1 2012-10-18
	 * 
	 * @param id
	 * 
	 * @return
	 * 
	 *         int
	 */
	@Override
	public int deleteByPrimaryKey(MarketingEventEntity marketingEventEntity) {
		int rt = -1;
		if (FossConstants.ACTIVE.equals(marketingEventEntity.getActive())) {
			rt = marketingEventDao.deleteByPrimaryKey(marketingEventEntity.getId());
		}
		return rt;
	}

	/**
	 * 启用折扣方案（只能启用状态为“新建的”记录）
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author admin
	 * 
	 * @version 0.1 2012-10-23
	 * 
	 * @param marketingEventEntity
	 * 
	 * @return
	 * 
	 *         int
	 */
	public int activeMarketEvents(MarketingEventEntity marketingEventEntity) {
		int rt = -1;
		if (FossConstants.INACTIVE.equals(marketingEventEntity.getActive())) {
			rt = marketingEventDao.updateByPrimaryKeySelective(marketingEventEntity);
		}
		return rt;
	}

	/**
	 * 
	 * <p>
	 * Description:结束折扣方案（只能结束状态为“启用”的记录）<br />
	 * </p>
	 * 
	 * @author admin
	 * 
	 * @version 0.1 2012-10-23
	 * 
	 * @param marketingEventEntity
	 * 
	 * @return
	 * 
	 *         int
	 */
	public int disableMarketEvents(MarketingEventEntity marketingEventEntity) {
		int rt = -1;
		if (FossConstants.ACTIVE.equals(marketingEventEntity.getActive())) {
			rt = marketingEventDao.updateByPrimaryKeySelective(marketingEventEntity);
		}
		return rt;
	}

	/**
	 * 
	 * <p>
	 * Description:复制一个旧的方案，并且版本号在原方案基础上加1，原有方案不变<br />
	 * </p>
	 * 
	 * @author admin
	 * 
	 * @version 0.1 2012-10-23
	 * 
	 * @param id
	 * 
	 * @return
	 * 
	 *         int
	 */
	public int copyOldtoNewVersion(String id) {
		int rt = -1;
		MarketingEventEntity marketingEventEntity = marketingEventDao.selectByPrimaryKey(id);
		marketingEventEntity.setId(UUIDUtils.getUUID());
		Long oldVersion = marketingEventEntity.getVersionNo();
		marketingEventEntity.setVersionNo(oldVersion + 1);
		rt = marketingEventDao.insertSelective(marketingEventEntity);
		//增加新方案的明细数据
		return rt;
	}

	/**
	 * 
	 * @Description: 删除明细
	 * 
	 * @author FOSSDP-Administrator
	 * 
	 * @date 2013-3-17 下午4:41:15
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	public int saveMarketingEventDetail() {
		return -1;
	}
}