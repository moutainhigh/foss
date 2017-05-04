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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/dao/IAirlinesValueAddDao.java
 * 
 * FILE NAME        	: IAirlinesValueAddDao.java
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

import com.deppon.foss.module.pickup.pricing.api.shared.domain.AirlinesValueAddEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.AirlinesValueAddDto;

/**
 * 
 * 航空公司代理增值服务费用DAO
 * @author DP-Foss-YueHongJie
 * @date 2012-11-2 下午4:44:37
 */
public interface IAirlinesValueAddDao {
    
    /**
     * 
     * 新增方案信息
     * @author DP-Foss-YueHongJie
     * @date 2012-11-2 下午4:49:05
     */
    void insert(AirlinesValueAddEntity record);
    
    /**
     * 
     * <p>新增方案信息,xml中只对非null字段</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-21 上午11:09:30
     * @param record
     * @return
     * @see
     */
    void insertSelective(AirlinesValueAddEntity record);
    /**
     * 
     * 删除方案信息 
     * @author DP-Foss-YueHongJie
     * @date 2012-11-2 下午4:49:05
     */
    void deleteByPrimaryKey(String id);
    
    /**
     * 
     * 修改方案信息
     * @author DP-Foss-YueHongJie
     * @date 2012-11-2 下午4:49:05
     */
    void updateByPrimaryKey(AirlinesValueAddEntity entity);
    
    /**
     * 
     * <p>修改方案信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-21 下午1:15:52
     * @param entity
     * @see
     */
    void updateByPrimaryKeySelective(AirlinesValueAddEntity entity);
    
    
    /**
     * 
     * 批量激活方案信息
     * @author DP-Foss-YueHongJie
     * @date 2012-11-2 下午4:50:08
     */
    void activeAirlinesValueAdd(List<String> ids);
    
    /**
     * 
     * <p>批量删除方案信息-只适用删除草稿状态</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-21 下午12:58:06
     * @param ids
     * @return
     * @see
     */
    void deleteAirlinesValueAdd(List<String> ids);
    
    
    /**
     * 
     * 升级方案
     * @author DP-Foss-YueHongJie
     * @date 2012-11-2 下午4:50:28
     */
    void upgradeAirlinesValueAdd(AirlinesValueAddEntity entity);
    
    
    /**
     * 
     * 根据条件查询所有航空增值服务信息
     * @author DP-Foss-YueHongJie
     * @date 2012-11-2 下午4:50:43
     */
    List<AirlinesValueAddEntity> findAirlinesValueAdd( AirlinesValueAddDto dto);
    
    /**
     * 
     * 根据方案ID查询所有信息
     * @author DP-Foss-YueHongJie
     * @date 2012-11-2 下午4:50:58
     */
    AirlinesValueAddEntity selectByPrimaryKey(String primaryKey);
    
    
    /**
     * 
     * 根据条件查询所有航空增值服务信息
     * @author DP-Foss-YueHongJie
     * @date 2012-11-2 下午4:50:43
     */
    List<AirlinesValueAddEntity> findAirlinesValueAdd(AirlinesValueAddDto dto,int start,int limit);
    
    /**
     * <p>(查询分页总数)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-20 下午5:00:00
     * @param dto
     * @return
     * @see
     */
    Long findAirlinesValueAddCount(AirlinesValueAddDto dto);
    
    
    /**
     * 
     * <p>根据航空公司代理Code,机场Code,配载部门Code查询唯一航空代理公司增值服务费用</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-1-6 下午5:06:49
     * @param airlinesCode 航空公司
     * @param airPortCode 出发机场
     * @param loadOrgCode 配载部门
     * @param businessDate 业务日期
     * @return
     * @see
     */
    AirlinesValueAddEntity queryAirLinesValueAddByCodes(String airlinesCode ,String airPortCode,String loadOrgCode,Date businessDate);
    
    
}