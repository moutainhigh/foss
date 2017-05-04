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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IEffectivePlanService.java
 * 
 * FILE NAME        	: IEffectivePlanService.java
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

import org.apache.poi.ss.usermodel.Workbook;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectiveExpressPlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectiveExpressPlanEntity;


/**
 * 
 * (时效方案主信息,对时效主方案的管理定义，包括方案新增、变更、激活操作)
 * @author 岳洪杰
 * @date 2012-10-12 上午10:47:06
 * @since
 * @version
 */
public interface IEffectiveExpressPlanService extends IService {
    
    /**
     * 
     * <p>(根据传入时间与始发区域ID查询所符合的最新时效方案版本)</p> 
     * @author 岳洪杰
     * @date 2012-10-13 上午8:50:58
     * @param cuurentTime
     * @param regionId
     * @return
     * @see
     */
     EffectiveExpressPlanEntity findEffectiveExpressPlanByRegionId(Date cuurentTime,String active,String regionId);
    
    /**
     * 
     * 新增-时效主方案批次信息
     * @author DP-Foss-YueHongJie
     * @date 2012-10-25 下午6:39:14
     */
     int insertEffectiveExpressPlanEntity(EffectiveExpressPlanEntity effectivePlanEntity);
    
    
    /**
     * 
     * 激活时效方案信息
     * @author DP-Foss-YueHongJie
     * @date 2012-11-10 下午2:24:05
     */
     int activeEffectiveExpressPlan(List<String> effectivePlans);
    
    /**
     * 
     * 查询方案主信息分页
     * @author DP-Foss-YueHongJie
     * @date 2012-11-10 下午7:50:06
     */
     List<EffectiveExpressPlanEntity> searchEffectiveExpressByCondition(
	    EffectiveExpressPlanEntity effectivePlanEntity, int start, int limit);
    
    /**
     * 
     * 查询方案主信息分页总记录数
     * @author DP-Foss-YueHongJie
     * @date 2012-11-10 下午7:50:06
     */
     Long searchEffectiveExpressByConditionCount(
	    EffectiveExpressPlanEntity effectivePlanEntity);
    
    /**
     * 
     * queryEffectivePlanDetailInfo(查询时效明细信息)
     * @param effectivePlanDetailEntity
     * @return 
     * List<EffectiveExpressPlanDetailEntity>
     * @exception 
     * @since  1.0.0
     */
     List<EffectiveExpressPlanDetailEntity> queryEffectiveExpressPlanDetailInfo(EffectiveExpressPlanDetailEntity effectivePlanDetailEntity);
    
    /**
     * 
     * queryEffectivePlanDetailInfo(查询时效明细信息分页)
     * @param effectivePlanDetailEntity
     * @param start
     * @param limit
     * @return 
     * List<EffectiveExpressPlanDetailEntity>
     * @exception 
     * @since  1.0.0
     */
     List<EffectiveExpressPlanDetailEntity> queryEffectiveExpressPlanDetailInfoPagging(EffectiveExpressPlanDetailEntity effectivePlanDetailEntity,int start,int limit);
    
    /**
     * 
     * queryEffectivePlanDetailInfo(查询时效明细信息分页总数)
     * @param effectivePlanDetailEntity
     * @return 
     * Long 
     * @exception 
     * @since  1.0.0
     */
     Long queryEffectiveExpressPlanDetailInfoPaggingCount(EffectiveExpressPlanDetailEntity effectivePlanDetailEntity);
    
    /**
     * @Description: 复制时效主方案
     * Company:IBM
     * @author IBMDP-sz
     * @date 2012-12-22 下午8:36:39
     * @param effectivePlanId
     * @return EffectiveExpressPlanEntity
     * @version V1.0
     */
     EffectiveExpressPlanEntity copyEffectiveExpressPlan(String effectivePlanId);
    
    /**
     * @Description: 修改时效方案
     * Company:IBM
     * @author IBMDP-Administrator
     * @date 2012-12-22 下午10:01:09
     * @param effectivePlanEntity
     * @return
     * @version V1.0
     */
     void modifyEffectiveExpressPlan(EffectiveExpressPlanEntity effectivePlanEntity);
    
    /**
     * @Description: 导入时效数据
     * Company:IBM
     * @author IBMDP-sz
     * @date 2012-12-22 下午10:45:25
     * @version V1.0
     */
     void importEffectiveExpressPlanDetail(Workbook book);
    
    /**
     * @Description: 导出时效数据
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 上午10:44:31
     * @param effectivePlanDetailEntity
     * @return
     * @version V1.0
     */
     ExportResource  exportEffectiveExpressPlanDetailList(EffectiveExpressPlanDetailEntity effectivePlanDetailEntity);
    
    /**
     * 
     * <p>查询单个时效方案对象</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-26 上午9:39:38
     * @param id
     * @return
     * @see
     */
     EffectiveExpressPlanEntity getEffectiveExpressEntityById(String id);
    /**
     * 
     * <p>批量删除</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-1-5 下午8:04:55
     * @param ids
     * @see
     */
     void deleteEffectiveExpressPlanBatch(List<String> ids);
    /**
     * @Description: 中止时效方案
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2013-1-5 下午8:20:58
     * @param id
     * @version V1.0
     */
     void terminateEffectiveExpressPlan(EffectiveExpressPlanEntity effectivePlanEntity);
    
    /**
     * 
     * 批量立即激活时效方案信息
     * @author DP-Foss-yangkang
     * @date 2014-07-21 下午2:24:05
     */
     void immediatelyActiveEffectiveExpressPlan(List<String> activeEffectiveExpressPlanIds,Date beginTime);
    
}