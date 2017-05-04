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
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/service/IReCreateTransportationPathService.java
 * 
 *  FILE NAME     :IReCreateTransportationPathService.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TransportPathEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;

/**
 * 重新计算走货路径service
 * @author huyue
 * @date 2012-10-11 下午9:17:58
 */
public interface IReCreateTransportationPathService extends IService{

	/**
	 * 查询走货路径LIST的行数
	 * @author huyue
	 * @date 2012-10-11 下午9:18:17
	 */
	Long getCount(TransportPathEntity transportPathEntity)throws TfrBusinessException;

	/**
	 * 查询走货路径LIST分页
	 * @author huyue
	 * @date 2012-10-11 下午9:18:19
	 */
	List<TransportPathEntity> queryTransportPathforPage(
			TransportPathEntity transportPathEntity, int limit, int start)throws TfrBusinessException;
	
	/**
	 * 重新计算走货路径
	 * @author huyue
	 * @date 2012-11-5 上午10:26:32
	 */
	int reCreateTransportPath(List<TransportPathEntity> transportPathList)throws TfrBusinessException;
	
	/**
	 * 根据运单号、流水呈查询走货路径明细
	 * @author 038300-pengzhen
	 * @date 2013-05-29 上午10:26:32
	 */
	List<PathDetailEntity> queryPathDetail(String waybillNO,String serialNO) throws TfrBusinessException;
	
	/**
	 * 根据运单号查询当前部门库存信息
	 * @author 038300-pengzhen
	 * @date 2013-05-29 上午10:26:32
	 */
	List<StockEntity> queryStock(String waybillNO, String orgCode) throws TfrBusinessException;
}