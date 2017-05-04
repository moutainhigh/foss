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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/dao/IMarketingEventDAO.java
 * 
 * FILE NAME        	: IMarketingEventDAO.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.MarketingEventEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.MarketingEventDto;

public interface IMarketingEventDAO {
	
	/**
	 * <p>
	 * Description:查询价格折扣方案列表<br />
	 * </p>
	 * @author admin
	 * @version 0.1 2012-10-18
	 * @return
	 * List<MarketingEventEntity>
	 */
	List<MarketingEventDto> selectMarketingEventList(MarketingEventEntity marketintEvent, int start, int limit);
	
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
    int deleteByPrimaryKey(String id);

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
    int insertSelective(MarketingEventEntity record);

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
    MarketingEventEntity selectByPrimaryKey(String id);

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
    List<MarketingEventEntity> selectByMarketCode(String code, Date billDate);
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
    int updateByPrimaryKeySelective(MarketingEventEntity record);

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
    List<MarketingEventEntity> selectMarketingEventList(MarketingEventEntity marketintEvent);
    
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
   	List<MarketingEventEntity> queryMarketingEventList(MarketingEventEntity marketintEvent);
   	
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
   	Long countMarketingEvent(MarketingEventEntity marketintEvent);
   	
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
   	List<MarketingEventEntity> queryMarketingEventList(MarketingEventEntity marketintEvent, int start, int limit);
   	
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
	List<MarketingEventEntity> queryMarketingEventByName(String marketintEventName);
	
	/**
	 * @Description: 获取折扣方案最大的编码
	 * Company:IBM
	 * @author FOSSDP-sz
	 * @date 2013-1-5 上午11:35:38
	 * @return
	 * @version V1.0
	 */
	String getMarketingEventMaxCode(String type);
}