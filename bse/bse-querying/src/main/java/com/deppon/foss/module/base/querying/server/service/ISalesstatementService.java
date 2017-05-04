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
 * PROJECT NAME	: bse-querying
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/querying/server/service/ISalesstatementService.java
 * 
 * FILE NAME        	: ISalesstatementService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * Service营业报表清单
 * 
 * @author 073586-FOSS-LiXuexing
 * @date 2012-12-25
 */
package com.deppon.foss.module.base.querying.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillListByComplexCondationDto;

public interface ISalesstatementService extends IService {

    /**
     * 分页 查询 营业报表清单 list
     * 
     * @author 073586-FOSS-LiXuexing
     * @date 2012-12-25
     */
    Map<String, Object> querySalesStatementByComplexCondation(
	    WaybillListByComplexCondationDto condition, int start, int limit);

    /**
     * 导出 营业报表清单list
     * 
     * @author 073586-FOSS-LiXuexing
     * @date 2012-12-25
     */
    ExportResource exportStatementByComplexCondation(
	    WaybillListByComplexCondationDto condition,List<String> codeArr);

    /**
     * 打印营业报表清单list
     * 
     * @author 073586-FOSS-LiXuexing
     * @date 2012-12-25
     */
    Map<String, Object> printSalesStatementByComplexCondation(
	    WaybillListByComplexCondationDto condition);
    /**
     * 获得当前登录用户 所在营业部
     * 
     * @author 073586-FOSS-LiXuexing
     * @date 2012-12-25
     */
    SaleDepartmentEntity querySaleDepartmentByCode(String codeStr);
}
