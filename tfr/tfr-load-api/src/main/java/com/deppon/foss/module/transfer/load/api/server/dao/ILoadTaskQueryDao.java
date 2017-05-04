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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/dao/ILoadTaskQueryDao.java
 *  
 *  FILE NAME          :ILoadTaskQueryDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportWayBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadTaskDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadWayBillDetailDto;

/**
 * 查询装车任务Dao
 * @author ibm-zhangyixin
 * @date 2012-11-29 上午10:28:08
 */
public interface ILoadTaskQueryDao {

	/**
	 * 根据输入的参数查询出装车任务(分页)
	 * @author ibm-zhangyixin
	 * @date 2012-11-29 上午10:28:24
	 */
	List<LoadTaskDto> queryLoadTask(LoadTaskDto loadTaskDto, int limit,
			int start);

	/**
	 * 查询出总记录数
	 * @author ibm-zhangyixin
	 * @date 2012-11-29 上午10:28:43
	 */
	Long getTotCount(LoadTaskDto loadTaskDto);

	/**
	 * 根据装车任务ID 查询出所有理货员
	 * @author ibm-zhangyixin
	 * @date 2012-11-29 上午10:28:54
	 */
	List<LoaderParticipationEntity> queryLoaderByTaskId(String taskId);

	/**
	 * 根据装车任务编号查询出所有的装车运单明细
	 * @author ibm-zhangyixin
	 * @date 2012-11-29 上午10:29:14
	 */
	List<LoadWayBillDetailDto> queryLoadWayBillByTaskNo(String taskNo);

	/**
	 * 根据任务No得到装车任务
	 * @author ibm-zhangyixin
	 * @date 2012-11-29 上午10:29:35
	 */
	LoadTaskDto getLoadTaskByTaskNo(String taskNo);

	/**
	 * 根据loadWaybillDetailId得到所有流水号
	 * @author ibm-zhangyixin
	 * @date 2012-11-29 上午10:29:57
	 */
	List<LoadSerialNoEntity> queryloadSerialNoByLoadWaybillDetailId(
			String loadWaybillDetailId);

	/**
	 * 根据配送装车No获取配送装车Id
	 * @author ibm-zhangyixin
	 * @date 2012-12-3 下午5:18:11
	 */
	String queryLoadGaprepIdByGaprepNo(String gaprepNo);

	/**
	 * 根据派送装车差异报告ID查询出派送装车数据
	 * @author ibm-zhangyixin
	 * @date 2012-12-4 上午11:11:06
	 */
	DeliverLoadGapReportEntity queryLoadGaprepReport(String loadGaprepId);

	/**
	 * 根据派送装车差异报告ID查询出运单数据
	 * @author ibm-zhangyixin
	 * @date 2012-12-4 上午11:11:32
	 */
	List<DeliverLoadGapReportWayBillEntity> queryDeliverLoadGapReportWayBills(
			String loadGaprepId);

	/** 
	 * @Title: queryLoadTask 
	 * @Description: 根据输入的参数查询出装车任务(不分页)
	 * @param loadTaskDto
	 * @return    
	 * @return List<LoadTaskDto>    返回类型 
	 * queryLoadTask
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-29上午10:01:33
	 */ 
	List<LoadTaskDto> queryLoadTask(LoadTaskDto loadTaskDto);

	/** 
	 * @Title: queryExportLoadWayBillByTaskNo 
	 * @Description: 根据装车任务编号查询出所有的装车运单明细
	 * @param taskNo
	 * @return    
	 * @return List<LoadWayBillDetailDto>    返回类型 
	 * queryExportLoadWayBillByTaskNo
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-6-24下午7:52:00
	 */ 
	List<LoadWayBillDetailDto> queryExportLoadWayBillByTaskNo(String taskNo);
	
	/**
	 * 
	 * 提供方法给清仓，查询给定时间前的装车扫描状态
	 * @author alfred
	 * @date 2013-9-6 上午9:32:13
	 * @param loadSerialNo
	 * @return
	 * @see
	 */
	public List<LoadSerialNoEntity> queryLoadScanState(Map<String,Object> loadSerialNo);
	
	List<LoadSerialNoEntity> queryLdpLoadScanInfo(Map<String, String> condition);
}