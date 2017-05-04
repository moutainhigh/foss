/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/esb/ISendOrgAdministrativeInfoService.java
 * 
 * FILE NAME        	: ISendOrgAdministrativeInfoService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

/**
 * 配合主数据项目推送FOSS组织信息给UUMS系统
 * @author 187862-dujunhui
 * @date 2015-4-10 上午10:45:42
 * @version 1.0
 */
public interface ISendOrgAdministrativeInfoToUUService {
	/**
     * 同步“行政组织信息”到UUMS系统
     * @author 187862-dujunhui
     * @date 2015-4-10 上午10:46:33
     */
	void sendOrgAdministrativeInfoToUU();
}
