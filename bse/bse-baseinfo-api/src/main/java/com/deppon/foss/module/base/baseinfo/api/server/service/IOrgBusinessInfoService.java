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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IOrgBusinessInfoService.java
 * 
 * FILE NAME        	: IOrgBusinessInfoService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.OrgBusinessInfoVo;

public interface IOrgBusinessInfoService extends IService {
    /**
     * 通过CODE标识数据来更新 行政组织业务属性
     * @author 087584-lijun
     * @date 2012-10-11 下午5:32:22
     */
    int updateOrgBusinessInfo(OrgBusinessInfoVo queryOrgBizVo);

    /**
     * ==========================================================
     * 以下全为查询方法
     */
    
    /**
     * 根据CODE查询部门的所有信息（包括部门基本信息，营业部信息，外场，车队&车队组&车队调度）
     * @author 087584-lijun
     * @date 2012-10-11 下午7:56:55
     */
    OrgBusinessInfoVo queryOrgBusinessInfoByCode(String code);
    
    
   
    

}
