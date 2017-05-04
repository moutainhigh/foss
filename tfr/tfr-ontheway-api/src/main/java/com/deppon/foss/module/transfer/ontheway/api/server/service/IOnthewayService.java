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
 *  PROJECT NAME  : tfr-ontheway-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/ontheway/api/server/service/IOnthewayService.java
 *  
 *  FILE NAME          :IOnthewayService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.ontheway.api.server.service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.ontheway.api.shared.domain.ManualEntity;
import com.deppon.foss.module.transfer.ontheway.api.shared.domain.OnthewayEntity;
import com.deppon.foss.module.transfer.ontheway.api.shared.domain.QueryOnthewayEntity;

public interface IOnthewayService extends IService
{

	/**
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-24 上午11:04:20
	 */
	List<OnthewayEntity> queryOnthewayGrid(
			QueryOnthewayEntity queryEntity, int limit, int start);

	/**
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-24 上午11:04:20
	 */
	List<OnthewayEntity> queryOnthewayGridById(String truckTaskDetailId);

	/**
	 * 
	 * 增加一条在途信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-26 上午9:43:00
	 */
	void addManual(ManualEntity manualEntity);

	/**
	 * 
	 * 取得数量
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-26 上午9:43:28
	 */
	Long getCount(QueryOnthewayEntity queryEntity);

	/**
	 * 
	 * 待跟踪长途车辆
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-26 上午9:43:28
	 */
	Long getLongCount(QueryOnthewayEntity queryEntity);
	/**
	 * 
	 * 待跟踪长途车辆
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-26 上午9:43:28
	 */
	List<OnthewayEntity> getLongGrid(QueryOnthewayEntity queryEntity, int limit, int start);

	/**
	 * 
	 * 短途GPS离线车辆
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-26 上午9:43:28
	 */
	Long getShortGPSCount(QueryOnthewayEntity queryEntity);

	/**
	 * 
	 * 短途GPS离线车辆
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-26 上午9:43:28
	 */
	List<OnthewayEntity> getShortGPSGrid(QueryOnthewayEntity queryEntity, int limit, int start);
	/**
	 * 
	 * 接送获GPS离线车辆
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-26 上午9:43:28
	 */
	Long getPkpGPSCount(QueryOnthewayEntity queryEntity);
	
	/**
	 * 
	 * 接送获GPS离线车辆
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-26 上午9:43:28
	 */
	List<OnthewayEntity> getPkpGPSGrid(QueryOnthewayEntity queryEntity, int limit, int start);
	/** 
	 * @Title: encodeFileName 
	 * @Description: 将文件名转成UTF-8编码以防止乱码
	 * @param string
	 * @return    
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskQueryService#encodeFileName(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-24下午4:14:37
	 */ 
	String encodeFileName(String fileName);
	
	/** 
	 * @Title: exportLoadWayBillByTaskNo 
	 * @Description: 根据任务号导出卸车明细
	 * @param taskNo
	 * @return    
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskQueryService#exportLoadWayBillByTaskNo(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-24下午4:14:37
	 */ 
	public InputStream exportTruckDepartOrArriveByTaskNo(String taskNo);
}