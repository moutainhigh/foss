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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/commonselector/ICommonProductItemDao.java
 * 
 * FILE NAME        	: ICommonProductItemDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.server.dao
 * FILE    NAME: ICommonProductItemDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProductItemEntity;

/**
 *公共选择器--产品条目查询service接口定义
 * @author 078823-foss-panGuangJun
 * @date 2012-12-4 下午2:14:22
 */
public interface ICommonProductItemDao {
    /**
     * 
     * 查询条目-分页
     * @author DP-Foss-panGuangjun
     * @date 2012-12-4 下午5:56:33
     * @param productEntity实体条目
     * @param start 分页起始页 
     * @param limit 每页显示条目
     */
    List<ProductItemEntity> findPagingByCondition(ProductItemEntity productEntity,int start, int limit);
    
    /**
     * 
     * 查询总数
     * @author DP-Foss-panGuangjun
     * @date 2012-12-4 下午5:56:52
     * @param entity 条目实体 
     * @return long：总条数
     */
   long countPagingByCondition(ProductItemEntity entity);
}
