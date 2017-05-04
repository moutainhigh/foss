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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/common/api/server/dao/IBillReceivableEntityDao.java
 * 
 * FILE NAME        	: IBillReceivableEntityDao.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.List;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableDEntity;

/**
 * 应收单明细Service 接口
 * @author 黄乐为
 * @date 2016-1-9 上午10:15:32
 */
public interface IBillReceivableDService {

    /**
     * 新增一条应收单明细记录
     * @author 黄乐为
     * @date 2016-1-9 上午10:27:26
     * @param entity
     * @return
     */
    void add(BillReceivableDEntity entity); 
    
    /**
     * 新增多条应收单明细记录
     * @author 黄乐为
     * @date 2016-1-12 上午8:20:08
     * @param list
     * @return
     */
    void addList(List<BillReceivableDEntity> list);

    /**
     * 根据传入的一到多个应收单号,查询一到多条应收单明细信息
     * @author 黄乐为
     * @date 2016-1-9 上午10:30:49
     * @param receivableNos 应收单号集合
     * @param active 		是否有效
     * @return
     */
    List<BillReceivableDEntity> queryByReceivableNOs(List<String> receivableNos, String active);

    /**
     * 根据传入的一到多个来源单号，获取一到多条应收单明细信息
     * @author 黄乐为
     * @date 2016-1-9 上午10:32:37
     * @param sourceBillNos 来源单号集合
     * @param active		是否有效
     * @return
     */
    List<BillReceivableDEntity> queryBySourceBillNOs(List<String> sourceBillNos, String active);

    /**
     * 根据运单号查询应收单明细
     * @author 尤坤
     * @param receivableNo
     * @return
     */
    List<BillReceivableDEntity> queryByReceivableNo(String receivableNo);
	
}
