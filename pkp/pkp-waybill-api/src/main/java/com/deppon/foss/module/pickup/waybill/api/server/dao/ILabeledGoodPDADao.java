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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/dao/ILabeledGoodPDADao.java
 * 
 * FILE NAME        	: ILabeledGoodPDADao.java
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

import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodPDAEntity;

/**
 * 
 * PDA货签信息数据持久层接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-12-11 下午2:37:42, </p>
 * @author foss-sunrui
 * @date 2012-12-11 下午2:37:42
 * @since
 * @version
 */
public interface ILabeledGoodPDADao {

	/**
	 * 
	 * <p>插入一条记录</p> 
	 * @author foss-sunrui
	 * @date 2012-12-11 下午2:38:50
	 * @param record
	 * @return
	 * @see
	 */
	int insertSelective(LabeledGoodPDAEntity record);

	/**
	 * 
	 * <p>按主键查询</p> 
	 * @author foss-sunrui
	 * @date 2012-12-11 下午2:39:01
	 * @param id
	 * @return
	 * @see
	 */
	LabeledGoodPDAEntity queryByPrimaryKey(String id);
	
	/**
	 * 
	 * <p>按运单编号查询</p> 
	 * @author foss-sunrui
	 * @date 2012-12-11 下午2:39:01
	 * @param id
	 * @return
	 * @see
	 */
	List<LabeledGoodPDAEntity> queryByWaybillNo(String waybillNo);

	/**
	 * 
	 * <p>按主键更新</p> 
	 * @author foss-sunrui
	 * @date 2012-12-11 下午2:39:08
	 * @param record
	 * @return
	 * @see
	 */
	int updateByPrimaryKeySelective(LabeledGoodPDAEntity record);
	
	/**
	 * 通过运单号更新记录状态
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-18 下午4:20:44
	 */
	int updateActiveByWaybillNo(String waybillNo);

	/**
	 * 批量插入PDA货签信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-17 下午4:30:50
	 */
	int insertBatch(List<LabeledGoodPDAEntity> list);
	
	/**
	 * 批量更新PDA货签失效状态
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-17 下午4:30:50
	 */
	int updateActiveBatch(List<LabeledGoodPDAEntity> list);
		

}