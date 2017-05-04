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
 *  PROJECT NAME  : tfr-common-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/server/service/IFOSSToOAService.java
 *  
 *  FILE NAME          :IFOSSToOAService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-oa-itf
 * PACKAGE NAME: com.deppon.foss.module.transfer.oa.server.service
 * FILE    NAME: FOSSToOAService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.common.api.server.service;

import com.deppon.foss.module.transfer.common.api.shared.dto.OaReportClearMore;
import com.deppon.foss.module.transfer.common.api.shared.dto.OaReportClearless;
import com.deppon.foss.module.transfer.common.api.shared.dto.OaReportNolabel;
import com.deppon.foss.module.transfer.common.api.shared.dto.OaReportSlipError;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseDto;

/**
 * foss上报差错信息给oa
 * 
 * @author 046130-foss-xuduowei
 * @date 2012-11-16 下午6:15:01
 */
public interface IFOSSToOAService {
	/**
	 * 
	 * 上报无标签多货差错信息
	 * 
	 * @param oaReportNolabel
	 *            无标签多货数据
	 * @return NoLabelResponseDto(failureReason 错误原因，errorID 差错编号)
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-16 下午6:28:53
	 */
	ResponseDto reportNolabel(OaReportNolabel oaReportNolabel);
	
	
	/**
	* @description 根据OA的差错编号 更新
	* @param oaErrorID
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月22日 下午3:08:43
	*/
	String updateGoodsArriverelay(String oaErrorID);

	/**
	 * 
	 * 上报少货差错（清仓少货，卸车少货）
	 * 
	 * @param oaReportClearless
	 *            差错信息
	 * @return NoLabelResponseDto(failureReason 错误原型，errorID 差错编号)
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-28 上午9:17:50
	 */
	ResponseDto reportLessGoods(OaReportClearless oaReportClearless);

	/**
	 * 
	 * 上报多货差错（清仓多货，装车多货，卸车多货）
	 * 
	 * @param oaReportClearMore
	 *            差错信息
	 * @return NoLabelResponseDto(failureReason 错误原型，errorID 差错编号)
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-28 上午9:19:40
	 */
	ResponseDto reportMoreGoods(OaReportClearMore oaReportClearMore);

	/**
	 * 
	 * 上报封签差错
	 * 
	 * @param oaReportSlipError
	 *            封签信息
	 * @return NoLabelResponseDto(failureReason 错误原型，errorID 差错编号)
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-28 上午9:21:53
	 */
	ResponseDto reportSlipError(OaReportSlipError oaReportSlipError);

	/**
	 * 
	 * 上报少货找到
	 * 
	 * @param oaErrorNo
	 *            差错编号
	 * @param userId
	 *            发现人工号
	 * @preOrg 上一部门
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-28 上午9:39:44
	 */
	ResponseDto reportLessGoodsFound(String oaErrorNo, String userCode, String userName, String preOrg);

	/**
	 * 
	 * 查询卸车差异报告处理状态
	 * 
	 * @param errorID
	 *            差异报告编号
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-1 下午5:13:35
	 */
	String queryUnloadErrorStatus(String errorID);

	/**
	 * 
	 * 查询违禁品信息
	 * 
	 * @param waybillNo
	 *            运单号
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-6 上午8:24:50
	 */
	String queryContrabandGoods(String waybillNo);
}