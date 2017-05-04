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
 *  DESCRIPTION   : 偏线外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/api/server/service/IUninputPartiallineService.java
 * 
 *  FILE NAME     :IUninputPartiallineService.java
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
import com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.HandoverBillDetailDto;

public interface IUninputPartiallineService {

	/**
	 * 查询未录入偏线外发单列表（查询交接单明细）
	 * 
	 * @author dp-zhongyubing
	 * @date 2012-10-22 上午10:57:57
	 */
	List<HandoverBillDetailDto> queryUninputedpartial(HandoverBillDetailDto detailDto, int limit, int start,
			CurrentInfo user);

	/**
	 * 查询未录入偏线外发单列表（查询交接单明细）总条数
	 * 
	 * @author dp-zhongyubing
	 * @date 2012-10-22 上午11:00:11
	 */
	Long queryUninputedpartialCount(HandoverBillDetailDto detailDto,CurrentInfo currentInfo);

	/**
	 * 查询运单号是否存在未录入的交接单
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-16 上午11:29:29
	 */
	void queryHandedUninputWaybill(ExternalBillDto dto);

}