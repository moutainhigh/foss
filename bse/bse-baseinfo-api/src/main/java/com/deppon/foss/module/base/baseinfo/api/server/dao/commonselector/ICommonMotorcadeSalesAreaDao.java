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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/commonselector/ICommonMotorcadeSalesAreaDao.java
 * 
 * FILE NAME        	: ICommonMotorcadeSalesAreaDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeSalesAreaEntity;
/**
 *  DAO接口
 * @author 078823-foss-panGuangjun
 * @date 2012-11-17 下午8:56:38
 */
public interface ICommonMotorcadeSalesAreaDao {

    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 078823-foss-panGuangjun
     * @date 2012-12-5 下午8:56:38
     */
    List<MotorcadeServeSalesAreaEntity> queryMotorcadeServeSalesAreaByCondtion(
	    MotorcadeServeSalesAreaEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 078823-foss-panGuangjun
     * @date 2012-12-5 下午8:56:38
     */
    long queryMotorcadeServeSalesAreaByCondtionCount(MotorcadeServeSalesAreaEntity entity);
	
}
