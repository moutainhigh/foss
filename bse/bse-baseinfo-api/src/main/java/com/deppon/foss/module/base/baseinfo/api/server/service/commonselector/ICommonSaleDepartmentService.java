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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonSaleDepartmentService.java
 * 
 * FILE NAME        	: ICommonSaleDepartmentService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.server.service
 * FILE    NAME: ICommonSaleDepartmentService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;

/**
 *公共查询组件--营业部查询service接口定义
 * @author 078823-foss-panGuangJun
 * @date 2012-12-5 下午2:39:28
 */
public interface ICommonSaleDepartmentService {
	 /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 078823-foss-panGuangJun
     * @date 2012-15-5 下午5:20:31
     */
    List<SaleDepartmentEntity> querySaleDepartmentExactByEntity(
	    SaleDepartmentEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 078823-foss-panGuangJun
     * @date 2012-12-5 下午5:20:31
     */
    long querySaleDepartmentExactByEntityCount(SaleDepartmentEntity entity);
    
    /**
     * 精确查询
     * 查询不包括虚拟营业部和快递点部的营业部
     *
     * @author 313353-foss-qiupeng
     * @date 2016-10-9 上午9:20:31
     */
    List<SaleDepartmentEntity> querySaleDeptFilterExactByEntity(
	    SaleDepartmentEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 查询不包括虚拟营业部和快递点部的营业部总条数
     * 
     * @author 313353-foss-qiupeng
     * @date 2016-10-9 上午9:20:31
     */
    long querySaleDeptFilterExactByEntityCount(SaleDepartmentEntity entity);
    
}
