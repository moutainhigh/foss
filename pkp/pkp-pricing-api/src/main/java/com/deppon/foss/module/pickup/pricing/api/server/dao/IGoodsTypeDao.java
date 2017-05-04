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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/dao/IGoodsTypeDao.java
 * 
 * FILE NAME        	: IGoodsTypeDao.java
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

import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GoodsTypeDto;
/**
 * 
 * (货物类型定义)
 * @author 岳洪杰 
 * @mail   dpyhj@deppon.com
 * @date 2012-10-15 下午6:11:24
 * @since
 * @version
 */
public interface IGoodsTypeDao {

    /**
     * 
     * <p>(新增货物定义)</p> 
     * @author 岳洪杰
     * @date 2012-10-15 下午6:12:25
     * @param record
     * @return
     * @see
     */
    int insert(GoodsTypeEntity record);

    
    /**
     * 
     * <p>(查询货物定义信息)</p> 
     * @author 岳洪杰
     * @date 2012-10-15 下午6:13:00
     * @param id
     * @return
     * @see
     */
    GoodsTypeEntity selectByPrimaryKey(String id);

    /**
     * 
     * <p>(修改)</p> 
     * @author 岳洪杰
     * @date 2012-10-15 下午6:13:33
     * @param record
     * @return
     * @see
     */
    int updateByPrimaryKey(GoodsTypeEntity record);
    
    /**
     * 激活货物类型
     * <p>(激活货物类型，衍生出条目信息)</p> 
     * @author 岳洪杰
     * @date 2012-10-16 下午1:51:26
     * @param goodsTypeEntity
     * @return
     * @see
     */
    int activationGoodsType(List<String> goodsTypeIds);
    
    /**
     * 
     * 查询货物类型集合-根据不同条件
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午5:25:11
     */
    List<GoodsTypeEntity> findGoodsTypeByCondiction(GoodsTypeEntity entity);
    
    
    /**
     * 
     * 查询货物类型-分页
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午5:26:50
     */
    List<GoodsTypeEntity> findGoodsTypePagingByCondiction(GoodsTypeEntity entity,int start,int limit);
    
    /**
     * 
     * 查询货物类型分页总记录
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午5:27:27
     */
    Long countGoodsTypePagingByCondiction(GoodsTypeEntity entity);
    
    
    /**
     * 
     * 根据货物编号返回货物数据集
     * @author DP-Foss-YueHongJie
     * @date 2012-10-29 上午10:38:03
     */
    GoodsTypeEntity queryGoodsTypeByGoodTypeCode(String goodsCode,Date billDate);
    
    /**
     * 
     * 查询缓存中货物集合信息
     * @author DP-Foss-YueHongJie
     * @date 2012-10-30 下午2:40:19
     */
    List<GoodsTypeEntity> findGoodsTypeByCache(GoodsTypeDto entity,Date billDate);
    
    /**
     * 
     * @Description: 查询缓存中货物集合信息
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-28 上午11:36:07
     * @param goodsCode
     * @param billDate
     * @return
     * @version V1.0
     */
    GoodsTypeEntity queryGoodsTypeByCache(String goodsCode,Date billDate);
    /**
     * 
     * @Description: 查询货物类型编号最大值
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2013-1-5 下午2:51:01
     * @return
     * @version V1.0
     */
    String getMaxGoodsTypeCode();
    /**
     * 
     * @Description: 根据货物类型编号和适用时间查询货物类型集合
     * @author FOSSDP-sz
     * @date 2013-1-31 上午11:07:58
     * @param entity
     * @param billDate
     * @return
     * @version V1.0
     */
    List<GoodsTypeEntity> queryGoodsTypeByCode(String goodsCode);
    
    /**
     * 查询与当前name相同的记录
     * @param name
     * @return
     */
    List<GoodsTypeEntity> isTheSameGoodsTypeName(String name);
}