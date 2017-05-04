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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/server/service/IUnloadDiffReportService.java
 *  
 *  FILE NAME          :IUnloadDiffReportService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.lostwarning.api.server.service;


import com.deppon.foss.framework.service.IService;

/** 
 * @className: IUnloadLackDiffReportService
 * @author: WangWenbo 
 * @description: 卸车少货差异报告service接口
 * @date: 2016-06-26 上午10:06:17
 * 
 */
public interface IUnloadLackDiffReportService extends IService {
	
	/**
	 * 处理卸车少货差异报告
	 * @author 311396-foss-WangWenbo
	 * @date 2016-06-26 上午10:06:17
	 */
	void executeUnloadLackDiffReportTask(int threadNo, int threadCount);
}