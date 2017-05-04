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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/complex/IDriverService.java
 * 
 * FILE NAME        	: IDriverService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service.complex;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverBaseDto;
/**
 * 用来提供交互所有关于“司机（公司、外请）”的数据库对应数据访问复杂的Service接口：无SUC
 * <p>注意：由于数据建模设计中，公司车不直接关系到司机，外请车关系到司机，公司司机直接关系的是工号</p>
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-30 下午4:46:29</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-30 下午4:46:29
 * @since
 * @version
 */
public interface IDriverService extends IService {
    
    /**
     * <p>提供给"中转"模块使用，根据"司机姓名"模糊匹配获取"司机"相关的数据实体信息的DTO集合</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-5 下午6:46:51
     * @param driverName 司机姓名
     * @return DriverBaseDto封装的对象集合
     * @throws BusinessException
     * @see
     */
    public List<DriverBaseDto> queryAutoCompleteDriverBaseDtoByDriverName(String driverName) throws BusinessException;
}
