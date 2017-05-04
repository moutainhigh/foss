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
 *  PROJECT NAME  : tfr-partialline-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 落地配外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/api/server/service/ILdpAgencySystemReportQueryService.java
 * 
 *  FILE NAME     :ILdpAgencySystemReportQueryService.java
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

package com.deppon.foss.module.transfer.partialline.api.server.service;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpAgencySystemReportQueryDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpAgencySystemReportResultDto;

/**
 * 落地配全盘报表Service接口
 * 
 * @author ibm-liuzhaowei
 * @date 2013-07-30 下午2:30:16
 */
public interface ILdpAgencySystemReportQueryService {

	/**
	 * 根据Dto查询落地配全盘报表
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-30 下午2:32:16
	 * @return 
	 */
	List<LdpAgencySystemReportResultDto> queryLdpAgencySystemReportByDto(int offset,int start,LdpAgencySystemReportQueryDto dto,CurrentInfo cInfo);
	
	/**
	 * 根据Dto查询总记录条数
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-30 下午2:32:16
	 * @return 
	 */
	long queryTotalRecordsInDBByDto(LdpAgencySystemReportQueryDto dto,CurrentInfo cInfo);
}
