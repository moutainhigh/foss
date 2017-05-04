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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/dao/IReturnBillProcessDao.java
 * 
 * FILE NAME        	: IReturnBillProcessDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.sign.api.shared.domain.ReturnBillProcessEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RtSearchReturnBillProcessDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.SearchReturnBillProcessDto;


/***
 * 签收单返单dao
 * @date 2012-11-20 下午6:58:13
 * @since
 * @version
 */
public interface IReturnBillProcessDao {
	/**
	 * 
	 * 签收单返单结果
	 * @date 2012-11-20 下午6:58:13
	 */
	List<RtSearchReturnBillProcessDto> searchReturnBillProcessList(
			SearchReturnBillProcessDto dto, int start, int limit);
	
	
	/**
	 * 
	 * 查询签收单返单结果数据集大小
	 * @date 2012-11-20 下午6:58:13
	 */
	Long getReturnBillProcessCount(SearchReturnBillProcessDto dto);


	/**
	 * 根据id查询ReturnBillProcess
	 * @date 2012-11-22 下午1:58:45
	 */
	RtSearchReturnBillProcessDto searchReturnBillProcessById(
			SearchReturnBillProcessDto dto);

	/**
	 * 更新ReturnBillProcess
	 * @date 2012-11-22 下午1:58:45
	 */
	void updateReturnBillProcess(
			ReturnBillProcessEntity returnBillProcessEntity);
	
	/**
	 * 根据运单号查询签收单信息
	 * 运单号： 运单单号
	 * @date 2012-11-22 下午1:58:45
	 */
	List<ReturnBillProcessEntity> querySignedBillByWaybillNo(String waybillNo);
	
	/**
	 * 
	 * 导出excel表格
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-6-27 下午4:52:09
	 */
	List<RtSearchReturnBillProcessDto> searchReturnBillProcessList(
			SearchReturnBillProcessDto dto);

	/**
	 * 批量更新 ReturnBillProcess 
	 * @author 268377
	 * @param rtSearchReturnBillProcessDto
	 * @return
	 */
	int updateBatchRetrunBillProcess(RtSearchReturnBillProcessDto rtSearchReturnBillProcessDto);
}