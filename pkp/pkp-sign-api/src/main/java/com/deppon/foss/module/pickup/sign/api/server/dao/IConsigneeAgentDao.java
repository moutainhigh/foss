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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/dao/IConsigneeAgentDao.java
 * 
 * FILE NAME        	: IConsigneeAgentDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.sign.api.shared.domain.ConsigneeAgentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.SearchWaybillSignByOtherDto;
/**
 * 修改他人签收凭证dao
 */
public interface IConsigneeAgentDao {
	
	/**
	 * 根据id删除凭证
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * 插入凭证
	 * @param record
	 * @return
	 */
    int insert(ConsigneeAgentEntity record);
    
    /**
     * 只把不是null的字段插入凭证
     * @param record
     * @return
     */
    int insertSelective(ConsigneeAgentEntity record);

    /**
	 * 根据id查询凭证
	 * @param id
	 * @return
	 */
    ConsigneeAgentEntity selectByPrimaryKey(String id);

    /**
     * 根据id有选择性的更新凭证
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(ConsigneeAgentEntity record);

    /**
     * 根据id更新凭证
     * @param record
     * @return
     */
    int updateByPrimaryKey(ConsigneeAgentEntity record);
    

    /**
     * 查询是否有库存
     * @param waybillNo
     * @return
     */
    long selectExistInStock(String waybillNo, String endStockOrgCode, String goodsAreaCode,String goodsAreaCodeExpress);

    /**
     * 查询是否运单存在 并且到达 并且入库
     * @param waybillNo
     * @return
     */
	long selectWaybillExistInStock(SearchWaybillSignByOtherDto dto);

	/**
	 * 根据运单号查询凭证
	 * @param wayblillNo
	 * @return
	 * @author ibm-wangfei
	 * @date Apr 16, 2013 1:44:06 PM
	 */
	List<ConsigneeAgentEntity> selectByWayblillNo(String waybillNo);
}