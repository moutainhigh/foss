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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/dao/IWaybillDisDtlDao.java
 * 
 * FILE NAME        	: IWaybillDisDtlDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.dao;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;

/**
 * 
 * 运单折扣明细数据持久层
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Bobby,date:2012-10-17 下午6:01:50</p>
 * @author Bobby
 * @date 2012-10-17 下午6:01:50
 * @since
 * @version
 */
public interface IWaybillDisDtlDao {

    int insert(WaybillDisDtlEntity record);
    
    /**
     * 批量插入对象数据
     * @author 026123-foss-lifengteng
     * @date 2012-11-5 上午11:02:09
     */
    void addBatch(List<WaybillDisDtlEntity> waybillDisDtl);

    WaybillDisDtlEntity queryByPrimaryKey(String id);
    
    /**
     * 根据运单号查询折扣明细列表
     * @author 026123-foss-lifengteng
     * @date 2012-11-5 下午4:21:43
     */
    List<WaybillDisDtlEntity> queryDisDtlEntityByNo(String waybillNo);

    /**
     * 更新折扣明细实体
     * @author 043260-foss-suyujun
     * @date 2012-12-18
     * @param record
     * @return int
     */
    int updateByPrimaryKeySelective(WaybillDisDtlEntity record);

    /**
     * 
     * <p>
     * 运单折扣明细<br />
     * </p>
     * @author suyujun
     * @version 0.1 2012-12-1
     * @param queryDto
     * @return
     * List<WaybillDisDtlEntity>
     */
	List<WaybillDisDtlEntity> queryNewDisDtlEntityByNo(LastWaybillRfcQueryDto queryDto);

	/**
	 * 
	 * <p>
	 * 删除折扣明细<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-12-3
	 * @param id
	 * @return
	 * Object
	 */
	int deleteWaybillDisDtlEntityById(String id);
    
}