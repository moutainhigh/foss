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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/dao/IStayHandoverserialDao.java
 * 
 * FILE NAME        	: IStayHandoverserialDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverserialEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.PdaDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StayHandoverDetailDto;
/**
 * 
 * 交接流水号Dao
 * @author foss-meiying
 * @date 2013-1-18 下午4:29:16
 * @since
 * @version
 */
public interface IStayHandoverserialDao {
	/**
	 * 根据id删除交流水号信息
	 * @author foss-meiying
	 * @date 2013-1-18 下午4:24:16
	 * @param id
	 * @return
	 * @see
	 */
    int deleteByPrimaryKey(String id);
    /**
     * 添加交接流水号信息
     * @author foss-meiying
     * @date 2013-1-18 下午4:24:49
     * @param record
     * @return
     * @see
     */
    StayHandoverserialEntity add(StayHandoverserialEntity record);
    /**
     * 添加交接流水号信息
     * @author foss-meiying
     * @date 2013-1-18 下午4:25:13
     * @param record
     * @return
     * @see
     */
    int addSelective(StayHandoverserialEntity record);
    /**
     * 根据id查询交接流水号信息
     * @author foss-meiying
     * @date 2013-1-18 下午4:26:05
     * @param id
     * @return
     * @see
     */
    StayHandoverserialEntity selectByPrimaryKey(String id);
    /**
     * 修改交接流水号信息(有选择性的修改)
     * @author foss-meiying
     * @date 2013-1-18 下午4:26:28
     * @param record
     * @return
     * @see
     */
    int updateByPrimaryKeySelective(StayHandoverserialEntity record);
    /**
     * 修改交接流水号信息
     * @author foss-meiying
     * @date 2013-1-18 下午4:26:46
     * @param record
     * @return
     * @see
     */
    int updateByPrimaryKey(StayHandoverserialEntity record);
    /**
     * 根据运单号,active ='Y'查询labeled_good_pda里的流水号集合
     * @author foss-meiying
     * @date 2013-1-22 下午2:43:08
     * @param dto
     * @return
     * @see
     */
    List<StayHandoverserialEntity>  querySerialNosByWaybillNo(StayHandoverDetailDto dto );
    
    /**
     * 批量保存交接流水号集合
     * @author foss-meiying
     * @date 2013-3-9 下午5:30:12
     * @param list
     * @return
     * @see
     */
    int addStayHandoverserialList(List<StayHandoverserialEntity> list);
    /**
     * 根据交接明细id 流水号集合删除满足条件的信息
     * @author foss-meiying
     * @date 2013-3-17 下午5:16:59
     * @param dto
     * @return
     * @see
     */
    int deleteByStayHandoverIdAndSerialNos(PdaDto dto);
}