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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/dao/ILabeledGoodChangeDao.java
 * 
 * FILE NAME        	: ILabeledGoodChangeDao.java
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
package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodChangeEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodDto;

/**
 *  更改单修改件数和打木架数量修改详细信息冗余保存对象dao
 * @author 026123-foss-lifengteng
 *
 */
public interface ILabeledGoodChangeDao {

	/**
     * 
     * <p>按需插入一条数据</p> 
     * @author foss-sunrui
     * @date 2012-10-30 下午3:12:29
     * @param record
     * @return
     * @see
     */
    int insertSelective(LabeledGoodChangeEntity record);
    
    /**
     * 
     * <p>批量插入</p> 
     * @author foss-sunrui
     * @date 2012-11-6 下午4:53:14
     * @param record
     * @return
     * @see
     */
    int insertBatch(List<LabeledGoodChangeEntity> list);

	/**
	 * 根据运单号查询最近一次审批通过的 货签打木架和更改数量信息冗余信息表，如果该运单一次都没有更改过
	 * 责返回是一个size = 0的 List
	 * @param waybillNo
	 * @return
	 */
	List<LabeledGoodChangeEntity> queryLastApprovedLabelGoodChangeHistory(
			String waybillNo);
   
	/**
	 * 根据运单号查询最近一次审中的 货签打木架和更改数量信息冗余信息表，
	 * 如果该运单一次都没有更改过
	 * 责返回是一个size = 0的 List
	 * @param waybillNo
	 * @return
	 */
	List<LabeledGoodChangeEntity> queryInProcessLabelGoodChangeHistory(String waybillNo);

	/**
	 * 将运单更新为审批通过
	 * @param waybillNo
	 */
	int updateLabeledGoodChangeToApprove(String waybillNo);

	/**
	 * 删除历史冗余信息 审批不通过 
	 * 
	 * @param waybillNo
	 */
	int deleteLabeledGoodChangeInProcessByWaybillNo(String waybillNo);

	/**
	 * 调用中转增加 取消走货路径中其中一票的接口
	 * @param newLabelList
	 */
	void updateLabeledGoodChangeToNeedInvokeTfr(List<LabeledGoodDto> newLabelList);
	
	/**
	 * 查询最近一次审批通过的所有记录
	 * @param waybillNo
	 * @return
	 */
	List<LabeledGoodChangeEntity>  queryLastApprovedChange(String waybillNo);

	/**
	 * 将状态更新为不用更新中转接口了
	 * @param addList
	 */
	void updateLabeledGoodChangeToNoNeedInVokeInterface(List<LabeledGoodChangeEntity> addList);
	
	/**
	 * 根据id将状态更新为不用更新中转接口了 
	 * @param id
	 * @author 272311-sangwenhao
	 */
	void updateLabeledGoodChangeToNoNeedInVokeInterface(String id);

	/**
	 * 更改运单号
	 * @param waybillNo
	 * @param waybillNo2
	 */
	void modifyWaybillNo(String waybillNo, String waybillNo2);
}