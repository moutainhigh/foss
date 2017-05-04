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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/dao/IPriceEntryDao.java
 * 
 * FILE NAME        	: IPriceEntryDao.java
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

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;

/**
 * 
 * 计价条目dao
 * @author DP-Foss-YueHongJie
 * @date 2012-11-12 下午3:25:15
 */
public interface IPriceEntryDao {
    	   	  
    /**
	 * 
	 *  根据不同条件查询计价条目
	 * @author DP-Foss-YueHongJie
	 * @param entity 实体条件
	 * @date 2012-11-12 下午3:23:08
	 */
	 List<PriceEntity> searchPriceEntryByConditions(PriceEntity entity);
	
	/**
	 * 
	 * <p>(根据计价条目CODE,查询计价条目名称)</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2012-12-7 下午2:29:13
	 * @param entryCode
	 * @return
	 * @see
	 */
	 String queryPriceEntryNameByCode(String entryCode);
	
	/**
	 * 
	 * @Description: 根据计价条目CODE,查询计价条目
	 * @author FOSSDP-sz
	 * @date 2013-1-21 下午4:53:01
	 * @param entryCode
	 * @return
	 * @version V1.0
	 */
	 PriceEntity queryPriceEntryByCode(String entryCode);
	
	/**
	 * @Description: 根据计价条目CODE,查询计价条目集合
	 * @author FOSSDP-sz
	 * @date 2013-2-1 下午5:05:41
	 * @param entryCode
	 * @return
	 * @version V1.0
	 */
	 List<PriceEntity> getPriceEntryByCode(String entryCode, Date billDate);
	
	/**
	 * 
	 * <p>(批量查询计价条目名称)</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2012-12-7 下午5:44:29
	 * @param entryCodes
	 * @return List<PriceEntity>
	 * @see
	 */
	 List<PriceEntity> queryPriceEntryNameByEntryCodes(List<String> entryCodes);
	 
	 
	  /**
	     * 
	     * <p>计价条目分页查询</p> 
	     * @author DP-Foss-YueHongJie
	     * @date 2013-4-1 上午8:56:10
	     * @param entity
	     * @param start
	     * @param limit
	     * @return
	     * @see
	     */
	    List<PriceEntity> findPriceEntityPagingByCondition(PriceEntity entity,int start,int limit);
	    
	    /**
	     * 
	     * <p>计价条目分页查询</p> 
	     * @author DP-Foss-YueHongJie
	     * @date 2013-4-1 上午9:19:16
	     * @param entity
	     * @return
	     * @see
	     */
	    Long countPriceEntityPagingByCondition(PriceEntity entity);
	    
	    /**
	     * 
	     * <p>主键Key查询</p> 
	     * @author DP-Foss-YueHongJie
	     * @date 2013-4-1 上午9:20:06
	     * @param id
	     * @return
	     * @see
	     */
	    PriceEntity findPriceEntityById(String id);
	    
	    /**
	     * 
	     * <p>修改计价条目</p> 
	     * @author DP-Foss-YueHongJie
	     * @date 2013-4-1 上午9:26:30
	     * @param priceEntity
	     * @see
	     */
	    void modifyPriceEntity(PriceEntity priceEntity);
	    
	    /**
	     * 
	     * <p>新增计价条目</p> 
	     * @author DP-Foss-YueHongJie
	     * @date 2013-4-1 上午9:28:24
	     * @param priceEntity
	     * @see
	     */
	    void addPriceEntry(PriceEntity priceEntity);
		
	    /**
	     * 
	     * <p>修改之前检查code是否已经存在</p> 
	     * @author DP-Foss-YueHongJie
	     * @date 2013-4-1 下午3:04:24
	     * @param priceEntity
	     * @return
	     * @see
	     */
	    PriceEntity modifyBeforeCheckPriceEntryCode(PriceEntity priceEntity);
	    
	    /**
	     * 
	     * 根据条件查询费用信息
	     * @author 025000-FOSS-helong
	     * @date 2013-7-2
	     */
	    List<PriceEntity> findParentPriceEntitys();
	    
}