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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/service/ILoadTaskQueryService.java
 *  
 *  FILE NAME          :ILoadTaskQueryService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportWayBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadTaskDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadWayBillDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.LoadTaskVo;

/**
 * 查询装车任务
 * @author ibm-zhangyixin
 * @date 2012-11-29 上午10:28:08
 */
public interface ILoadTaskQueryService extends IService {

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
	@Deprecated
	List<LoadWayBillDetailDto> queryLoadWayBillByTaskNo(String taskNo);
	
	/**
	 * 根据装车任务编号和装车来源查询出所有的装车运单明细
	 * @author ibm-zhangyixin
	 * @date 2012-11-29 上午10:29:14
	 */
	List<LoadWayBillDetailDto> queryLoadWayBillByTaskNo(String taskNo, String source);

	/**
	 * 根据任务No得到装车任务
	 * @author ibm-zhangyixin
	 * @date 2012-11-29 上午10:29:35
	 */
	LoadTaskDto getLoadTaskByTaskNo(String taskNo);
	
	/**
	 * 根据任务No和装车来源得到装车任务
	 * @author ibm-zhangyixin
	 * @date 2012-11-29 上午10:29:35
	 */
//	LoadTaskDto getLoadTaskByTaskNo(String taskNo, String source);

	/**
	 * 根据loadWaybillDetailId得到所有流水号
	 * @author ibm-zhangyixin
	 * @date 2012-11-29 上午10:29:57
	 */
	List<LoadSerialNoEntity> queryloadSerialNoByLoadWaybillDetailId(
			String loadWaybillDetailId);
	
	/**
	 * 根据loadWaybillDetailId得到所有流水号...
	 * @author ibm-zhangyixin
	 * @date 2012-11-29 上午10:29:57
	 */
	List<LoadSerialNoEntity> queryloadSerialNoByLoadWaybillDetailId(
			LoadTaskVo loadTaskVo);

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
	 * @Title: encodeFileName 
	 * @Description: 将文件名转成UTF-8编码以防止乱码
	 * @param string
	 * @return    
	 * @return String    返回类型 
	 * encodeFileName
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-24下午3:09:03
	 */ 
	String encodeFileName(String string);

	/** 
	 * @Title: exportLoadWayBillByTaskNo 
	 * @Description: 根据任务号导出装车明细数据 
	 * @param taskNo
	 * @return    
	 * @return InputStream    返回类型 
	 * exportLoadWayBillByTaskNo
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-24下午3:09:43
	 */ 
	@Deprecated
	InputStream exportLoadWayBillByTaskNo(String taskNo);
	
	/** 
	 * @Title: exportLoadWayBillByTaskNo 
	 * @Description: 根据任务号导出装车明细数据 
	 * @param taskNo
	 * @return    
	 * @return InputStream    返回类型 
	 * exportLoadWayBillByTaskNo
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-24下午3:09:43
	 */ 
	InputStream exportLoadWayBillByTaskNo(String taskNo,  String source);

	/** 
	 * @Title: exportLoadTask 
	 * @Description:根据查询参数导出装车任务
	 * @param loadTaskDto
	 * @return    
	 * @return InputStream    返回类型 
	 * exportLoadTask
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-29上午9:56:47
	 */ 
	InputStream exportLoadTask(LoadTaskDto loadTaskDto);
}