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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IContactWithAirlinesDao.java
 * 
 * FILE NAME        	: IContactWithAirlinesDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAirlinesEntity;
/**
 * 用来操作交互“正单交货人”的数据库对应数据访问DAO接口：SUC-37
 * <p style="display:none">modifycontactAirlines</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-12 上午11:52:59</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-12 上午11:52:59
 * @since
 * @version
 */
public interface IContactWithAirlinesDao {

    /**
     * <p>新增一个“正单交货人”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:53:16
     * @param contactAirlines “正单交货人”实体
     * @return 影响记录数
     * @see
     */
     int addContactWithAirlines(ContactAirlinesEntity contactAirlines);

    /**
     * <p>新增一个“正单交货人”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:53:13
     * @param contactAirlines “正单交货人”实体
     * @return 影响记录数
     * @see
     */
     int addContactWithAirlinesBySelective(ContactAirlinesEntity contactAirlines);

    /**
     * <p>根据“正单交货人”记录唯一标识作废（物理删除）一条“正单交货人”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:53:02
     * @param id 记录唯一标识
     * @return 影响记录数
     * @see
     */
     int deleteContactWithAirlines(String id);

    /**
     * <p>修改一个“正单交货人”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:53:09
     * @param contactAirlines “正单交货人”实体
     * @return 影响记录数
     * @see
     */
     int updateContactWithAirlinesBySelective(ContactAirlinesEntity contactAirlines);

    /**
     * <p>修改一个“正单交货人”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:53:07
     * @param contactAirlines “正单交货人”实体
     * @return 影响记录数
     * @see
     */
     int updateContactWithAirlines(ContactAirlinesEntity contactAirlines);
    

    /**
     * <p>根据条件有选择的检索出符合条件的“正单交货人”唯一实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:53:11
     * @param contactAirlines 以“正单交货人”实体承载的条件参数实体
     * @return “正单交货人”实体
     * @see
     */
     ContactAirlinesEntity queryContactWithAirlinesBySelective(ContactAirlinesEntity contactAirlines);
    
    /**
     * <p>根据条件有选择的检索出符合条件的“正单交货人”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-2 下午5:36:39
     * @param contactAirlines 以“正单交货人”实体承载的条件参数实体
     * @return 符合条件的“正单交货人”实体列表
     * @see
     */
     List<ContactAirlinesEntity> queryContactWithAirlinesListBySelective(ContactAirlinesEntity contactAirlines);
    
    /**
     * <p>根据条件（分页模糊）有选择的统计出符合条件的“正单交货人”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午10:15:40
     * @param flight 以“正单交货人”实体承载的条件参数实体
     * @return 符合条件的“正单交货人”实体记录条数
     * @see
     */
     long queryContactAirlinesCountBySelectiveCondition(ContactAirlinesEntity contactAirlines);
    
    /**
     * <p>根据条件（分页模糊）有选择的检索出符合条件的“正单交货人”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-2 下午5:36:39
     * @param contactAirlines 以“正单交货人”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“正单交货人”实体列表
     * @see
     */
     List<ContactAirlinesEntity> queryContactAirlinesListBySelectiveCondition(ContactAirlinesEntity contactAirlines, int offset, int limit);
}
