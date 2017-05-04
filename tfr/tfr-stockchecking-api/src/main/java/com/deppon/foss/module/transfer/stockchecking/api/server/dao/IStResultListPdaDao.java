/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-stockchecking-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/server/dao/IStResultListPdaDao.java
 *  
 *  FILE NAME          :IStResultListPdaDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StResultListPdaEntity;

/**
 * 处理pda清仓结果相关的crud操作
 * @author foss-wuyingjie
 * @date 2012-11-13 下午5:18:44
 */
public interface IStResultListPdaDao {

	/**
	 * 新增一条清仓任务扫描记录
	 *
	 * @param stResultListPdaEntity
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-18 上午10:50:49
	 */
	void addStResultListPdaEntity(StResultListPdaEntity stResultListPdaEntity);

	/**
	 * 按扫描时间倒序排序取第一个数据，此数据为最后一次的数据，不能以当前扫描状态为准，PDA上传为异步上传
	 *
	 * @param stResultListPdaEntity.stTaskNo
	 * @param stResultListPdaEntity.waybillNo
	 * @param stResultListPdaEntity.serialNo
	 * @return
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-18 下午2:04:38
	 */
	StResultListPdaEntity queryLastScanEntity(StResultListPdaEntity stResultListPdaEntity);

	/**
	 * 通过清仓编号获取其下对应的扫描明细信息，按运单号、序列号正序排列，按扫描时间倒序排列
	 *
	 * @param stTaskNo
	 * @return
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-18 下午4:39:36
	 */
	List<StResultListPdaEntity> queryStResultListPda(String stTaskNo);

	/**
	 * @author niuly
	 * @date 2014-3-17下午5:00:18
	 * @function 根据任务号信息查询pda扫描记录
	 * @param stResultListPdaEntity
	 * @return
	 */
	List<StResultListPdaEntity> queryStPdaResultList(String taskNo);
}