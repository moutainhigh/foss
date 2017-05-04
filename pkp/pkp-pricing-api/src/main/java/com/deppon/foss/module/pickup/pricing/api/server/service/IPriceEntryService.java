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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IPriceEntryService.java
 * 
 * FILE NAME        	: IPriceEntryService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
/**
 * 
 * (计价条目服务)
 * @author DP-Foss-YueHongJie
 * @date 2012-12-7 下午2:31:27
 * @version 1.0
 */
public interface IPriceEntryService extends IService{
    
    /**
     * 
     * <p>根据条目entryCode返回计价条目名称,entryCode设置请参照常量类PricingConstants</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-7 下午2:32:26
     * @param entryCode
     * @return
     * @see
     */
     String queryPriceEntryNameByCode(String entryCode);
    
    /**
     * 
     * <p>(根据条目entryCodes批量查询条目信息,entryCode设置请参照常量类PricingConstants)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-7 下午6:10:06
     * @param entryCodes
     * @return
     * @see
     */
     Map<String, String> queryPriceEntryNameByEntryCodes(List<String> entryCodes);
    
    /**
     * 
     * @Description: 从缓存中获取计价条目实体
     * @author FOSSDP-sz
     * @date 2013-2-19 下午3:49:52
     * @param entryCode
     * @param billDate
     * @return
     * @version V1.0
     */
    PriceEntity getPriceEntryByCache(String entryCode, Date billDate);
    
    /**
     * @Description: 刷新计价条目缓存
     * @author FOSSDP-sz
     * @date 2013-2-22 上午10:18:46
     * @param entryCode
     * @version V1.0
     */
    void refreshPriceEntryCache(String entryCode);
    
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
     * 根据条件查询费用信息
     * @author 025000-FOSS-helong
     * @date 2013-7-2
     */
    List<PriceEntity> findParentPriceEntitys();
}