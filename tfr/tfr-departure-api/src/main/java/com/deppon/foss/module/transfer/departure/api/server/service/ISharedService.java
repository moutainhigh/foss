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
 *  PROJECT NAME  : tfr-departure-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/server/service/ISharedService.java
 *  
 *  FILE NAME          :ISharedService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.departure.api.shared.dto.AutoTaskDTO;
/**
 * 
 * 制定一些共用的处理业务的方法，包括车辆任务绑定表的操作，定时任务
 * @author foss-liubinbin(for IBM)
 * @date 2012-11-2 上午10:23:10
 */
public interface ISharedService extends IService{
	/**
	 * 
	 * 更新运单、交接单状态
	 * @param taskFlag 用来指示执行哪一条任务
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-2 上午10:45:22
	 */
	void refreshDataForActionTask(String taskFlag);
	
	/**
	 * 
	 * 定时任务：“待放行”状态的车辆n分钟（默认30分钟可配置）内放行有效，超过时间值则变为已失效状态
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-24 上午11:04:20
	 */
	void autoCancle();

	/**
	 * 
	 * 单件出入库插入pkp临时表记录
	 * 
	 * @param dto
	 * @author ibm-wangfei
	 * @date Feb 25, 2013 1:41:54 PM
	 */
	void insertIOTempForPKP(AutoTaskDTO dto);
	
	/**
	 * 整车入库Job
	 * @author 316759-wangruipeng
	 * @date 2016-07-19 下午3:33:20
	 */
	void pushForWholeVehicle();
	
}