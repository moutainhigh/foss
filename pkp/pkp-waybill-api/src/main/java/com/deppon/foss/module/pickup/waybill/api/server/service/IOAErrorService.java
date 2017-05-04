/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/service/IOAErrorService.java
 * 
 * FILE NAME        	: IOAErrorService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.dto.LostRepDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.OaErrorReportDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.OaReportClearLose;
import com.deppon.foss.module.pickup.waybill.shared.dto.QueryVirtualResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResponseShortGoodsDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.OaReportClearless;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseDto;

/**
 * 
 * （查询OA差错信息）
 * 
 * @author 026113-foss-linwensheng
 * @date 2012-11-30 下午5:00:35
 */
public interface IOAErrorService extends IService {

	/**
	 * 
	 * 查询OA虚开单差错处理结果
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-1 下午2:30:01
	 */
	QueryVirtualResultDto queryVirtrualWaybillFromOA(String handingID);
	
	/**
	 * 上报OA差错
	  * Description: 
	  * @author deppon-157229-zxy
	  * @version 1.0 2014-1-13 下午5:04:16
	  * @param record
	  * @return
	 */
	Object reportOAError(OaErrorReportDto oaErrorDto);

	/**
	 * 反签收上报OA差错
	  * Description: 
	  * @author deppon-076234-pgy
	  * @version 1.0 2014-3-26 下午5:04:16
	  * @param record
	  * @return
	 */
	Object reverseSignOAError(OaErrorReportDto oaErrorDto);
	
	/**
	 * 上报快递代理理外发20天未签收丢货 DMANA-3046
	 * @Title: reportLessGoods 
	 * @author 200664-yangjinheng
	 * @date 2014年9月3日 上午9:49:56
	 * @throws
	 */
	ResponseDto reportLessGoods(OaReportClearless oaReportClearless);

	/**
	 * 丢货差错自动上报
	 * @Title: reportLostGoods 
	 * @author foss-yuting
	 * @date 2014年12月3日 下午9:49:56
	 * @throws
	 */
	ResponseDto reportLostGoods(OaReportClearLose oaReportClearLose);

	/**
	 * 内物短少差错自动上报
	 * @Title: reportExceptionGoods 
	 * @author foss-yuting
	 * @date 2014年12月3日 下午9:49:56
	 * @throws
	 */
	ResponseShortGoodsDto reportExceptionGoods(String json,String waybillNo);
	/**
	 * 
	 * 查询OA虚开单差错处理结果
	 * 
	 * @author huangwei
	 * @date 2015-11-14 下午2:30:01
	 */
	LostRepDto queryLostRepDto(String lostRepCode, String wayBillNo);
}