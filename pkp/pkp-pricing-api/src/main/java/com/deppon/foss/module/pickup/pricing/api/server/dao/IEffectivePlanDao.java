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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/dao/IEffectivePlanDao.java
 * 
 * FILE NAME        	: IEffectivePlanDao.java
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


import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanEntity;
/**
 * 
 * (时效方案主信息DAO)
 * @author 岳洪杰
 * @date 2012-10-12 上午9:07:02
 * @since
 * @version v1.0
 */
public interface IEffectivePlanDao {
    
    /**
     * 
     * <p>删除单个时效方案信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午3:24:25
     * @param id
     * @return
     * @see
     */
    int deleteByPrimaryKey(String id);

    /**
     * 
     * <p>添加时效方案信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午3:24:45
     * @param record
     * @return
     * @see
     */
    int insertSelective(EffectivePlanEntity record);

    /**
     * 
     * <p>查询单个时效方案</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午3:24:58
     * @param id
     * @return
     * @see
     */
    EffectivePlanEntity selectByPrimaryKey(String id);

    /**
     * 
     * <p>修改时效方案信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午3:25:11
     * @param record
     * @return
     * @see
     */
    int updateByPrimaryKeySelective(EffectivePlanEntity record);

    /**
     * 
     * <p>(根据当前传入时间与始发区域查询所符合的最新唯一时效方案</p> 
     * @author 岳洪杰
     * @date 2012-10-13 上午8:49:20
     * @param deptRegionId 始发区域ID
     * @param cuurentTime  当前业务日期
     * @param active   有效标志
     * @return 
     * @see
     */
    EffectivePlanEntity findEffectivePlanByRegionId(Date cuurentTime,String active,String deptRegionId);
    
    /**
     * 
     * 查询分页时效主信息
     * @author DP-Foss-YueHongJie
     * @date 2012-11-10 下午7:53:37
     */
    List<EffectivePlanEntity> searchEffectivePlanByCondition(EffectivePlanEntity effectivePlanEntity, int start, int limit);
    /**
     * 
     * <p>(根据传入时间，始发区域ID返回最新时效版本信息)</p> 
     * @author 岳洪杰
     * @date 2012-10-13 上午8:59:17
     * @param effectivePlanEntity  默认当前日期
     * @param start 开始坐标
     * @param limit 结束坐标
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDao#findEffectivePlanByRegionId(java.util.Date, java.lang.String)
     */
     List<EffectivePlanEntity> searchEffectivePlanByCondition(EffectivePlanEntity effectivePlanEntity);
    
    /**
     * 
     * searchEffectivePlanByConditionCount(时效分页查询总记录数)
     * @param effectivePlanEntity
     * @return Long
     * @author DP-Foss-YueHongJie
     * @exception 
     * @since  1.0.0
     */
     Long searchEffectivePlanByConditionCount(EffectivePlanEntity effectivePlanEntity);
    
    /**
     * 
     * <p>批量删除时效</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午3:26:18
     * @param ids
     * @see
     */
     void deleteEffectivePlanByIds(List<String> ids);
    
    /**
     * 
     * <p>批量激活时效</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午3:26:42
     * @param ids
     * @see
     */
     void activeEffectivePlanByIds(List<String> ids);
    
    /**
     * 
     * <p>根据名称查询时效方案信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-3-7 上午11:10:26
     * @param effectivePlanEntity
     * @return
     * @see
     */
     List<EffectivePlanEntity> searchEffectivePlanByName(EffectivePlanEntity effectivePlanEntity);
    
}