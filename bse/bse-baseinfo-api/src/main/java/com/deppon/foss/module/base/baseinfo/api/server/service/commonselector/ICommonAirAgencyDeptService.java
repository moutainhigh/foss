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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonAirAgencyDeptService.java
 * 
 * FILE NAME        	: ICommonAirAgencyDeptService.java
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
 * FILE    NAME: ICommonAirAgencyDeptService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;

/**
 * 公共查询组件--空运/偏线代理网点查询service接口
 * @author 078823-foss-panGuangJun
 * @date 2012-12-6 下午1:43:44
 */
public interface ICommonAirAgencyDeptService {
	/**
     * 根据传入对象查询符合条件所有空运代理网点信息 
     * @author 078823-foss-panGuangJun
     * @date 2012-12-6 上午11:23:05
     * @param entity 空运/偏线代理网点实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     */
    List<OuterBranchEntity> queryAgencyDeptsByCondition(OuterBranchEntity entity,int limit,int start);
    
    /**
     * 统计总记录数 
     * @author 078823-foss-panGuangJun
     * @date 2012-12-6 上午11:23:05
     * @param entity 空运/偏线代理网点实体
     * @return
     */
    Long countRecordByCondition(OuterBranchEntity entity);
}
