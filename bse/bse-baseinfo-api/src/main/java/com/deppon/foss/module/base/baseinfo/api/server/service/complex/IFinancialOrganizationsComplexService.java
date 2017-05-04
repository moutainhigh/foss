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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/complex/IFinancialOrganizationsComplexService.java
 * 
 * FILE NAME        	: IFinancialOrganizationsComplexService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service.complex;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FinancialOrganizationsEntity;


/**
 * 财务组织复杂查询 service接口
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-18 上午11:04:19
 */
public interface IFinancialOrganizationsComplexService {
    
    /**
     * 根据人员编码，获得组织信息，再通过组织信息中的子公司编码，获得子公司对象
     * 
     * 提供给 中转
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-18 下午5:08:41
     */
    FinancialOrganizationsEntity queryFinancialOrganizationsByEmpCode(String empCode);

}
