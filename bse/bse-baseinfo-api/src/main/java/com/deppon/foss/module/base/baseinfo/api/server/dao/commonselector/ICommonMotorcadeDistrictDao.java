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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/commonselector/ICommonMotorcadeDistrictDao.java
 * 
 * FILE NAME        	: ICommonMotorcadeDistrictDao.java
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
 * FILE    NAME: ICommonMotorcadeServeDistrictDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeDistrictEntity;

/**
 * 公共查询组件--车队行政区域查询Dao接口定义
 * @author 078823-foss-panGuangJun
 * @date 2012-12-5 上午11:54:20
 */
public interface ICommonMotorcadeDistrictDao {
	/** 
     * 根据条件查询车队对应行政区域 
     * @author 078823-foss-panGuangjun
     * @date 2012-10-19 上午11:11:15
     * @param entity:查询条件,一般传递的条件包含 车队编码，行政区域名称
     * @param start:开始条数
     * @param limit：页数大小
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeServeDistrictDao#queryMotorcadeServeDistrictExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeDistrictEntity, int, int)
     */
     List<MotorcadeServeDistrictEntity> queryMotorcadeServeDistrictByCondition(
	    MotorcadeServeDistrictEntity entity, int start, int limit);
    /**
      * 根据条件查询车队对应行政区域 总数
     * @author 078823-foss-panGuangjun
     * @date 2012-10-19 上午11:11:15
     * @param entity:查询条件,一般传递的条件包含 车队编码，行政区域名称     * @author 078823-foss-panGuangjun
     * @date 2012-12-5 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeServeDistrictDao#queryMotorcadeServeDistrictExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeDistrictEntity)
     */
     long queryMotorcadeServeDistricByCondition(MotorcadeServeDistrictEntity entity);
}
