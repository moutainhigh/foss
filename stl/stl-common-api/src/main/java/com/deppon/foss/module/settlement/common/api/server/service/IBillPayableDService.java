/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-common-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/common/api/server/dao/IBillPayableEntityDao.java
 * 
 * FILE NAME        	: IBillPayableEntityDao.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.common.api.server.service;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableDEntity;

import java.util.List;

/**
 * 应付单明细Service 接口
 * @author hemingyu
 * @date 2016-01-21 16:07:21
 */
public interface IBillPayableDService {

    /**
     * 新增一条应付单明细记录
     * @author hemingyu
     * @date 2016-01-21 16:07:21
     * @param entity
     * @return
     */
    void add(BillPayableDEntity entity);
    
    /**
     * 新增多条应付单明细记录
     * @author hemingyu
     * @date 2016-01-21 16:07:21
     * @param list
     * @return
     */
    void addList(List<BillPayableDEntity> list);

    /**
     * 根据传入的一到多个应付单号,查询一到多条应付单明细信息
     * @author hemingyu
     * @date 2016-01-21 16:07:21
     * @param payableNos 应收单号集合
     * @param active 		是否有效
     * @return
     */
    List<BillPayableDEntity> queryByPayableNOs(List<String> payableNos, String active);

    /**
     * 根据传入的一到多个来源单号，获取一到多条应付单明细信息
     * @author hemingyu
     * @date 2016-01-21 16:07:21
     * @param sourceBillNos 来源单号集合
     * @param active		是否有效
     * @return
     */
    List<BillPayableDEntity> queryBySourceBillNOs(List<String> sourceBillNos, String active);

}
