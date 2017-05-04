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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/ISyncSalesDepartmentService.java
 * 
 * FILE NAME        	: ISyncSalesDepartmentService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;



/**
 * 同步FOSS的营业部信息给官网系统
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-15 上午11:50:25
 */
public interface ISyncSalesDepartmentService extends IService {
   
    /**
     * 向官网同步营业部信息
     * @author 101911-foss-zhouChunlai
     * @param 
     * @date 2013-1-15 上午11:54:19
     * @return 
     */
	void syncSalesDepartmentDataToOws(List<SaleDepartmentEntity> saleDepartmentList);

}
