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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/dao/IEffectivePlanDetailDao.java
 * 
 * FILE NAME        	: IEffectivePlanDetailDao.java
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
import java.util.Set;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanDetailEntity;

/**
 * 
 * (时效方案详细信息)
 * @author 岳洪杰
 * @date 2012-10-12 上午10:40:09
 * @since
 * @version
 */
public interface IEffectivePlanDetailDao {
    
    /**
     * 
     * <p>删除单个明细实体</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:53:07
     * @param id
     * @return
     * @see
     */
    int deleteByPrimaryKey(String id);

    /**
     * 
     * <p>添加时效明细</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:53:21
     * @param record
     * @return
     * @see
     */
    int insertSelective(EffectivePlanDetailEntity record);

    /**
     * 
     * <p>查询单个时效明细</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:53:32
     * @param id
     * @return
     * @see
     */
    EffectivePlanDetailEntity selectByPrimaryKey(String id);

    /**
     * 
     * <p>修改时效明细</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:53:46
     * @param record
     * @return
     * @see
     */
    int updateByPrimaryKeySelective(EffectivePlanDetailEntity record);
    
    /**
     * 
     * <p>查询时效明细信息分页</p> 
     * @author 岳洪杰
     * @param effectivePlanDetailEntity  时效明细实体
     * @param start 分页开始业
     * @param limit 分页截止业
     * @date 2012-10-12 上午11:41:50
     * @return
     * @see
     */
     List<EffectivePlanDetailEntity> queryEffectivePlanDetailInfoPagging(EffectivePlanDetailEntity effectivePlanDetailEntity,int start ,int limit);
    
    
    /**
     * 
     * <p>查询时效明细信息总数</p> 
     * @author 岳洪杰
     * @param effectivePlanDetailEntity  时效明细实体
     * @date 2012-10-12 上午11:41:50
     * @return Long  总记录数
     * @see
     */
     Long queryEffectivePlanDetailInfoPaggingCount(EffectivePlanDetailEntity effectivePlanDetailEntity);
    
    
    
    /**
     * 
     * <p>查询时效明细信息</p> 
     * @author 岳洪杰
     * @param effectivePlanDetailEntity  时效明细实体
     * @date 2012-10-12 上午11:41:50
     * @return
     * @see
     */
     List<EffectivePlanDetailEntity> queryEffectivePlanDetailInfo(EffectivePlanDetailEntity effectivePlanDetailEntity);

    /**
     * <p>
     * Description:根据始发区域ID和最后更新日期查询时效方案明细列表（供离线开单下载数据）<br />
     * </p>
     * @author zhangwei
     * @version 0.1 2012-11-6
     * @param deptRegionId
     * @param lastModifyTime
     * @return
     * List<EffectivePlanDetailEntity>
     */
    List<EffectivePlanDetailEntity> queryEffectivePlanDetailInfoForDownload(String deptRegionId, Date lastModifyTime);
    
    /**
     * 
     * 按照始发区域ID,到达区域ID,Level3 产品-精准卡航...,营业日期 查询唯一时效明细
     * @author DP-Foss-YueHongJie
     * @date 2012-11-6 下午1:41:57
     * @param deptRegionId 始发区域ID
     * @param arrvieRegionId 到达区域ID
     * @param productCode 产品编码
     * @param businessDate 营业日期 
     * @return EffectivePlanDetailEntity  时效明细信息
     */
     List<EffectivePlanDetailEntity> queryEffectivePlanDetailListByCondition(
	    String deptRegionId, String arrvieRegionId, String productCode,Date businessDate);
    
    
    /**
     * 
     * <p>(方法详细描述说明、方法参数的具体涵义)</p> 
     * @author sz
     * @date 2012-12-11 下午3:31:26
     * @param effectivePlanDetailEntity
     * @return
     * @see
     */
    public List<EffectivePlanDetailEntity> queryEffectivePlanDetailByCondition(EffectivePlanDetailEntity effectivePlanDetailEntity);
    
    /**
     * 
     * <p>批量删除时效明细信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:50:50
     * @param ids
     * @see
     */
     void deleteEffectivePlanDetailByIds(List<String> ids);
    
    /**
     * 
     * <p>批量激活时效明细信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:51:28
     * @param ids
     * @see
     */
     void activeEffectivePlanDetailByIds(List<String> ids);
    
    /**
     * 
     * 查询分页时效明细信息
     * @author DP-Foss-YueHongJie
     * @date 2012-11-10 下午7:53:37
     */
    List<EffectivePlanDetailEntity> searchEffectivePlanDetailEntityByCondition(EffectivePlanDetailEntity effectivePlanEntity, int start, int limit);
   
    /**
     * 
     * <p>查询总数</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-26 上午10:16:13
     * @param effectivePlanEntity
     * @return
     * @see
     */
     Long searchEffectivePlanDetailEntityByConditionCount(EffectivePlanDetailEntity effectivePlanEntity);
    
    /**
     * 
     * @Description: 查询时效明细信息总数
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2013-1-9 下午4:52:52
     * @param deptRegionId
     * @param arrvieRegionIds
     * @param productCode
     * @param businessDate
     * @return
     * @version V1.0
     */
     List<EffectivePlanDetailEntity> queryEffectivePlanDetailListByArrvIds(
    	    String deptRegionId, List<String> arrvieRegionIds, String productCode,String active, Date billDate);
    /**
     * 
     * @Description: 根据出发和到达区域ID集合查询时效明细信息
     * @author FOSSDP-Administrator
     * @date 2013-1-25 下午4:08:33
     * @param deptRegionIds
     * @param arrvieRegionIds
     * @param productCode
     * @param active
     * @param businessDate
     * @return
     * @version V1.0
     */
     List<EffectivePlanDetailEntity> queryEffectivePlanDetailListByRegionIds (
    		Set<String> deptRegionIds, Set<String> arrvieRegionIds, String productCode,String active, Date billDate);
    
    /**
     * 
     * @Description: 根据出发和到达区域ID集合查询时效明细信息
     * @author FOSSDP-Administrator
     * @date 2013-1-25 下午4:08:33
     * @param deptRegionIds
     * @param arrvieRegionIds
     * @param productCode
     * @param active
     * @param businessDate
     * @return
     * @version V1.0
     */
     List<EffectivePlanDetailEntity> queryEffectivePlanDetailListByRegionIds (
    		List<String> deptRegionIds, List<String> arrvieRegionIds, String productCode,String active, Date billDate);
     
     /**
      * 
      * <p>批量新增时效明细信息</p> 
      * @author DP-Foss-YueHongJie
      * @date 2013-4-24 下午4:36:56
      * @param oldId 复制前的UUID
      * @param newId 复制后新的UUID
      * @see
      */
     int insertBatchEffectivePlanDetail(String oldId,String newId);

	/**
	 * 分页查询需要下载的时效明细数据
	 * @param entity
	 * @param i
	 * @param thousand
	 * @return
	 */
     List<EffectivePlanDetailEntity> queryEffectivePlanDetailByConditionByPage(
			EffectivePlanDetailEntity entity, int start, int limited);
     
}