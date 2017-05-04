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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/dao/IMarketingEventChannelDao.java
 * 
 * FILE NAME        	: IMarketingEventChannelDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.MarketingEventChannelEntity;

public interface IMarketingEventChannelDao {
	/**
	 * 
	 * @Description: 根据主键删除记录
	 * @author FOSSDP-sz
	 * @date 2013-3-8 上午10:28:35
	 * @param id
	 * @return
	 * @version V1.0
	 */
    int deleteByPrimaryKey(String id);

    /**
     * 
     * @Description: 新增记录，值不为空的字段不插入
     * @author FOSSDP-sz
     * @date 2013-3-8 上午10:28:47
     * @param record
     * @return
     * @version V1.0
     */
    int insertSelective(MarketingEventChannelEntity record);
    
    /**
     * 
     * @Description: 根据主键查询记录
     * @author FOSSDP-sz
     * @date 2013-3-8 上午10:28:58
     * @param id
     * @return
     * @version V1.0
     */
    MarketingEventChannelEntity selectByPrimaryKey(String id);
    
    /**
     * 
     * @Description: 根据条件查询记录
     * @author FOSSDP-sz
     * @date 2013-3-8 上午10:29:07
     * @param record
     * @return
     * @version V1.0
     */
    List<MarketingEventChannelEntity> selectByCondition(MarketingEventChannelEntity record);

    /**
     * 
     * @Description: 根据主键更新记录，值为空的字段不更新
     * @author FOSSDP-sz
     * @date 2013-3-8 上午10:29:15
     * @param record
     * @return
     * @version V1.0
     */
    int updateByPrimaryKeySelective(MarketingEventChannelEntity record);
    
    /**
     * 
     * @Description: 根据折扣方案查询该方案所有渠道信息
     * @author FOSSDP-sz
     * @date 2013-3-8 上午10:29:24
     * @param eventId
     * @return
     * @version V1.0
     */
    List<MarketingEventChannelEntity> queryMarketingEventChannelListByEventId(String eventId);
    
    /**
     * 
     * @Description: 根据市场活动ID删除记录
     * @author FOSSDP-sz
     * @date 2013-3-8 上午10:29:35
     * @param marketEventId
     * @return
     * @version V1.0
     */
    int deleteByMarketEventId(String marketEventId);
    /**
     * 
     * @Description: 根据市场活动ID修改记录
     * @author FOSSDP-sz
     * @date 2013-3-8 上午10:29:44
     * @param record
     * @return
     * @version V1.0
     */
    int updateByMarketEventIdSelective(MarketingEventChannelEntity record);
    
    /**
     * 
     *
     * @Function: com.deppon.foss.module.pickup.pricing.api.server.dao.IMarketingEventChannelDao.deleteByMarketEventIdAndChannelCode
     * @Description:根据折扣方案的ID和渠道code删除渠道信息
     *
     * @param channelEntity
     * @param channelCodes
     * @return
     *
     * @version:v1.0
     * @author:DP-FOSS-YANGKANG
     * @date:2015-1-12 下午3:14:00
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2015-1-12    DP-FOSS-YANGKANG      v1.0.0         create
     */
    int deleteByMarketEventIdAndChannelCode(MarketingEventChannelEntity channelEntity,List<String> channelCodes);
}