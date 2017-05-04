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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IEmployeeService.java
 * 
 * FILE NAME        	: IEmployeeService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ClassifiedIncomeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ClassifiedIncomeQueryDto;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
/**
 * 重分类收入基础信息service接口
 * @author 307196
 *
 */
public interface IClassifiedIncomeService extends IService {
	/**
	 * 新增重分类收入基础信息service接口
	 * @author 307196
	 *
	 */
	ClassifiedIncomeEntity addClassifiedIncome(ClassifiedIncomeEntity entity,CurrentInfo currentInfo);
	
    /**
     * 修改重分类收入基础信息service接口
     * @author 307196
     *
     */
	void updateClassifiedIncome(ClassifiedIncomeQueryDto entity,CurrentInfo currentInfo);
	
    /**
     * 作废重分类收入基础信息service接口
     * @author 307196
     *
     */
	void deleteClassifiedIncome(ClassifiedIncomeQueryDto entity,CurrentInfo currentInfo);
	
    
    /**
     * 总条数重分类收入基础信息service接口
     * @author 307196
     *
     */
    long queryTotalByCondition(ClassifiedIncomeQueryDto entity);
	
    /**
     * 分页查询重分类收入基础信息service接口
     * @author 307196
     *
     */
    List<ClassifiedIncomeEntity> queryClassifiedIncomeByCondition(ClassifiedIncomeQueryDto entity,int start, int limit);
   /**
    * 根据条件查询导出信息
    * @param entity
    * @return
    */
    ExportResource exportClassifiedIncomeList(ClassifiedIncomeQueryDto entity);
    /**
     * 导入信息
     * @param entitys
     */
    void importClassifiedIncomeList(List<ClassifiedIncomeEntity> entitys);
}
