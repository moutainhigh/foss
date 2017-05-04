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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IGoodsTypeService.java
 * 
 * FILE NAME        	: IGoodsTypeService.java
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

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GoodsTypeDto;

/**
 * 货物类型定义
 * TODO(定义货物类型如：通用,海鲜,食品,杂物,A,B,C,D等等)
 * @author foss-yuehongjie 
 * @mail   dpyhj@deppon.com
 * @date 2012-10-16 上午11:30:53
 * @since
 * @version
 */
public interface IGoodsTypeService extends IService{
    
    
    /**
     * 添加货物定义
     * <p>(传入goodsTypeEntity添加货物定义信息)</p> 
     * @author 岳洪杰
     * @date 2012-10-16 上午11:32:12
     * @param goodsTypeEntity
     */
     int addGoodsType(GoodsTypeEntity goodsTypeEntity);
    
    /**
     * 修改货物定义
     * <p>(传入goodsTypeEntity修改货物定义信息)</p> 
     * @author 岳洪杰
     * @date 2012-10-16 上午11:32:46
     * @param goodsTypeEntity
     */
     int updateGoodsType(GoodsTypeEntity goodsTypeEntity);
    
    
    /**
     * 根据货物编号查询有效货物信息
     * @author 岳洪杰
     * @date 2012-10-16 上午11:33:47
     * @param goodsTypeCode
     * @return
     * @see
     */
     GoodsTypeEntity queryGoodsTypeByGoodTypeCode(String goodsTypeCode,Date billDate);
    
    /**
     * 激活货物定义
     * <p>(负责激活货物信息，自动衍生出条目信息。当前操作自动匹配产品类型(已激活状态)进行条目组装)</p>
     * @author 岳洪杰
     * @date 2012-10-16 上午11:35:31
     * @param goodsTypeEntity
     * @see
     */
     void activationGoodsType(List<String> goodIds); 
    
    /**
     * 
     * 分页查询货物信息（按照不同条件分页查询货物信息）
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午3:02:50
     */
     List<GoodsTypeEntity> findGoodsTypePagingByCondition(GoodsTypeEntity entity,int start,int limit);
    
    /**
     * 
     * 货物定义总数查询（按照不同条件查询货物信息总数）
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午3:02:50
     */
     Long countGoodsTypePagingByCondition(GoodsTypeEntity entity);
    
    
    /**
     * 
     * 查询货物定义信息
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午5:13:38
     */
     List<GoodsTypeEntity> findGoodsTypeByCondiction(GoodsTypeEntity entity);
    
    
    /**
     * 
     * 查询货物缓存数据
     * @author DP-Foss-YueHongJie
     * @date 2012-10-30 下午2:35:36
     */
     List<GoodsTypeEntity> findGoodsTypeByCache(GoodsTypeDto entity,Date billDate);
    
    /**
     * 
     * @Description: 查询货物缓存数据
     * @author FOSSDP-sz
     * @date 2013-2-19 下午3:31:07
     * @param goodsTypeCode
     * @param billDate
     * @return
     * @version V1.0
     */
     GoodsTypeEntity getGoodsTypeByCache(String goodsTypeCode, Date billDate);
    
    /**
     * 
     * @Description: 刷新货物缓存数据
     * @author FOSSDP-sz
     * @date 2013-2-22 上午10:09:46
     * @param goodsTypeCode
     * @version V1.0
     */
     void refreshGoodsTypeCache(String goodsTypeCode);
    
    /**
     * 
     * 查询唯一产品
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午5:15:25
     */
     GoodsTypeEntity findGoodsTypeById(String id);
    
    
}