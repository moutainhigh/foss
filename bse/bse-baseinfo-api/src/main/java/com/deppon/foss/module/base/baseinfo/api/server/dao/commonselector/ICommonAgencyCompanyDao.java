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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/commonselector/ICommonAgencyCompanyDao.java
 * 
 * FILE NAME        	: ICommonAgencyCompanyDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
/**
 * 公共查询组件--“空运代理公司”的数据库对应数据访问DAO接口
 * @author 078823-foss-panGuangJun
 * @date 2012-12-6 上午11:57:39
 * @since
 * @version
 */
public interface ICommonAgencyCompanyDao {
    /**
     * 根据传入对象查询符合条件所有空运代理公司信息 
     * @author 078823-foss-panGuangJun
     * @date 2012-12-6 上午11:03:18
     * @param entity 偏线/空运代理公司实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     */
    List<BusinessPartnerEntity> queryAgencyCompanysByCondtion(BusinessPartnerEntity entity,int limit,int start);
    
    /**
     * 统计总记录数 
     * @author 078823-foss-panGuangJun
     * @date 2012-12-6 上午11:03:18
     * @param entity 偏线/空运代理公司实体
     * @return 符合条件的总记录数
     */
    Long countRecordByCondition(BusinessPartnerEntity entity);
    
}
