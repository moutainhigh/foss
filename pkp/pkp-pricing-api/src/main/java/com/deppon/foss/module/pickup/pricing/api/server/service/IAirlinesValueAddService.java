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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IAirlinesValueAddService.java
 * 
 * FILE NAME        	: IAirlinesValueAddService.java
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
import com.deppon.foss.module.pickup.pricing.api.shared.domain.AirlinesValueAddEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.AirlinesValueAddDto;

/**
 * 
 * 航空代理增值服务费用
 * @author DP-Foss-YueHongJie
 * @date 2012-10-24 下午3:35:45
 */
public interface IAirlinesValueAddService extends IService {
    
    /**
     * 
     * 删除航空代理增值服务方案
     * @author DP-Foss-YueHongJie
     * @date 2012-11-2 下午5:13:11
     */
     void deleteAirlinesValueAdd(String id);
    
    /**
     * 
     * 添加航空代理增值服务方案
     * @author DP-Foss-YueHongJie
     * @date 2012-11-2 下午5:13:29
     */
     void addAirlinesValueAdd(AirlinesValueAddEntity entity);
    
    /**
     * 
     * 修改航空代理增值服务方案
     * @author DP-Foss-YueHongJie
     * @date 2012-11-2 下午5:13:41
     */
     void updateAirlinesValueAdd(AirlinesValueAddEntity entity);
    
    /**
     * 
     * <p>修改方案信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-21 下午1:17:05
     * @param entity
     * @see
     */
    void updateByPrimaryKeySelective(AirlinesValueAddEntity entity);
    
    /**
     * 
     * 批量激活航空代理增值服务方案
     * @author DP-Foss-YueHongJie
     * @date 2012-11-2 下午5:13:54
     */
     void activeAirlinesValueAdd(List<String> productIds);
    
    /**
     * 
     * <p>单笔立即激活空运增值方案</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-3-1 上午10:19:20
     * @see
     */
     void immediatelyActiveAirlinesValueAdd(AirlinesValueAddEntity entity);
    
    
    /**
     * 
     * <p>单笔中止方案</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-3-1 上午10:06:28
     * @param entity
     * @see
     */
     void immediatelyStopAirlinesValueAdd(AirlinesValueAddEntity entity);
    
    
    /**
     * 
     * <p>批量删除方案-草稿状态</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-21 下午12:56:43
     * @param productIds
     * @return
     * @see
     */
     void deleteAirlinesValueAdd(List<String> productIds);
    
    
    /**
     * 
     * 升级航空代理增值服务方案
     * @author DP-Foss-YueHongJie
     * @date 2012-11-2 下午5:14:07
     */
     AirlinesValueAddEntity upgradeAirlinesValueAdd(AirlinesValueAddEntity entity);
    
    /**
     * 
     * 查询航空代理增值服务方案
     * @author DP-Foss-YueHongJie
     * @date 2012-11-2 下午5:14:21
     */
     List<AirlinesValueAddEntity> findAirlinesValueAdd(AirlinesValueAddDto entity);
    
    /**
     * 
     * 查询航空代理增值服务方案-分页查询
     * @author DP-Foss-YueHongJie
     * @date 2012-11-2 下午5:14:21
     */
     List<AirlinesValueAddEntity> findAirlinesValueAdd(AirlinesValueAddDto entity,int start ,int limit);
    
    /**
     * 
     * 查询航空代理增值服务方案-分页查询总数
     * @author DP-Foss-YueHongJie
     * @date 2012-11-2 下午5:14:21
     */
     Long findAirlinesValueAddCount(AirlinesValueAddDto entity);
    
    
    /**
     * 
     * <p>查询单个航空代理增值服务</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-21 上午11:20:51
     * @param id
     * @return
     * @see
     */
     AirlinesValueAddEntity findAirlinesValueAddById(String id);
    
    /**
     * 
     * 根据配载部门航空公司出发机场获取唯一航空代理增值服务相关费用
     * @author DP-Foss-YueHongJie
     * @param flightCode 航空公司编号
     * @param loadOrgCode 配载部门编号
     * @param deptAirfieldCode 出发机场编码
     * @param billDate 录入运单时间
     * @date 2012-11-2 上午11:12:49
     * @return AirlinesValueAddEntity 其中包括保费、燃油附加费、地面运输费、最低总金额
     */
     AirlinesValueAddEntity findAirlinesValueAddBycondition(String flightCode,
	    String loadOrgCode,String deptAirfieldCode,Date billDate);
    
    
}