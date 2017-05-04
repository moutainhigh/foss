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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/IVehicleAgencyDeptService.java
 * 
 * FILE NAME        	: IVehicleAgencyDeptService.java
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
package com.deppon.foss.module.pickup.common.client.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.common.client.dto.QueryPickupPointDto;

/**
 * 偏线代理网点service 接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-xieyantao,date:2012-10-15 上午10:32:36, </p>
 * @author foss-jiangfei
 * @date 2012-10-15 上午10:32:36
 * @since
 * @version
 */
public interface IVehicleAgencyDeptService extends IService {
    
   /**
    * 家装网点
    * @param cuntyId
    * @return
    */
	List<SaleDepartmentEntity> getOrgAdminInfoEntityList(String countyId);
    /**
     * 
     * 根据传入参数查询代理网点（空运代理网点和偏线代理网点） 
     * @author foss-jiangfei
     * @date 2012-11-1 下午3:43:18
     * @param dto 参数封装DTO（包括：目的站、代理网点名称、代理网点类型、用于版本控制时间）
     * @return
     * @see
     */
    List<OuterBranchEntity> queryOuterBranchs(QueryPickupPointDto dto);
    
    /**
     * 
     * @author WangQianJin
     * @date 2013-7-19 下午2:14:33
     */
    String checkProductAndTarget(QueryPickupPointDto dto);

}