/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/IVehicleAgencyDeptDao.java
 * 
 * FILE NAME        	: IVehicleAgencyDeptDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.common.client.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.pickup.common.client.dto.QueryPickupPointDto;
/**
 * 用来操作交互“偏线代理网点”的数据库对应数据访问DAO接口：SUC-649
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-12 下午2:34:0 </p>
 * @author foss-jiangfei
 * @date 2012-10-12 下午2:34:02
 * @since
 * @version
 */
public interface IVehicleAgencyDeptDao {
   
    /**
     * 根据传入参数查询代理网点（空运代理网点和偏线代理网点） 
     * @author foss-jiangfei
     * @date 2012-11-2 上午9:00:56
     * @param dto 参数封装DTO（包括：目的站、代理网点名称、代理网点类型、用于版本控制时间）
     * @return
     * @see
     */
    List<OuterBranchEntity> queryOuterBranchs(QueryPickupPointDto dto);
    
    /**
     * 根据条件查询偏线信息
     * @author WangQianJin
     * @date 2013-7-19 下午1:52:21
     */
    OuterBranchEntity queryOuterBranchByDto(QueryPickupPointDto dto2);
    
   
}