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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IAirlinesAccountDao.java
 * 
 * FILE NAME        	: IAirlinesAccountDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAccountEntity;
/**
 * 用来操作交互“航空公司账户”的数据库对应数据访问DAO接口：SUC-43 
 * <p style="display:none">modifyairlinesAccount</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-12 上午8:49:03</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-12 上午8:49:03
 * @since
 * @version
 */
public interface IAirlinesAccountDao {

    /**
     * <p>新增一个“航空公司账户”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:49:16
     * @param airlinesAccount “航空公司账户”实体
     * @return 影响记录数
     * @see
     */
     int addAirlinesAccount(AirlinesAccountEntity airlinesAccount);

    /**
     * <p>新增一个“航空公司账户”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:49:18
     * @param airlinesAccount “航空公司账户”实体
     * @return 影响记录数
     * @see
     */
     int addAirlinesAccountBySelective(AirlinesAccountEntity airlinesAccount);

    /**
     * <p>根据“航空公司账户”记录唯一标识作废（物理删除）一条“航空公司账户”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:49:10
     * @param id 记录唯一标识
     * @return 影响记录数
     * @see
     */
     int deleteAirlinesAccount(String id);
    
    /**
     * <p>修改一个“航空公司账户”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:49:26
     * @param airlinesAccount “航空公司账户”实体
     * @return 影响记录数
     * @see
     */
     int updateAirlinesAccountBySelective(AirlinesAccountEntity airlinesAccount);

    /**
     * <p>修改一个“航空公司账户”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:49:29
     * @param airlinesAccount “航空公司账户”实体
     * @return 影响记录数
     * @see
     */
     int updateAirlinesAccount(AirlinesAccountEntity airlinesAccount);
    
    /**
     * <p>根据条件有选择的检索出符合条件的“航空公司账户”唯一的一个实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-3 上午10:09:01
     * @param airlinesAccount 以“航空公司账户”实体承载的条件参数实体
     * @return “航空公司账户”实体
     * @see
     */
     AirlinesAccountEntity queryAirlinesAccountBySelective(AirlinesAccountEntity airlinesAccount);
    
    /**
     * <p>根据条件有选择的检索出符合条件的“航空公司账户”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-2 下午5:36:39
     * @param leasedDriver 以“航空公司账户”实体承载的条件参数实体
     * @return 符合条件的“航空公司账户”实体列表
     * @see
     */
     List<AirlinesAccountEntity> queryAirlinesAccountListBySelective(AirlinesAccountEntity airlinesAccount);
    
    /**
     * <p>根据条件（分页模糊）统计处出符合条件的“航空公司账户”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-3 上午10:10:07
     * @param airlinesAccount 以“航空公司账户”实体承载的条件参数实体
     * @return 符合条件的“航空公司账户”记录数
     * @see
     */
     Long queryAirlinesAccountRecordCountBySelectiveCondition(AirlinesAccountEntity airlinesAccount);
    
    /**
     * <p>根据条件（分页模糊）有选择的检索出符合条件的“航空公司账户”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-2 下午5:36:39
     * @param leasedDriver 以“航空公司账户”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“航空公司账户”实体列表
     * @see
     */
     List<AirlinesAccountEntity> queryAirlinesAccountListBySelectiveCondition(AirlinesAccountEntity airlinesAccount, int offset, int limit);
}
