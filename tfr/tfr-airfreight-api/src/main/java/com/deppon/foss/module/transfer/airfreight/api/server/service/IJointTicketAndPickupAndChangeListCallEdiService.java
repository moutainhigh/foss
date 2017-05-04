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
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/IJointTicketAndPickupAndChangeListCallEdiService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
/**
 * 合大票、中转提货清单、变更清单调用Edi接口 
 * @author 099197-foss-zhoudejun
 * @date 2012-12-27 上午10:31:56
 */
public interface IJointTicketAndPickupAndChangeListCallEdiService extends IService {

	/**
	 * 合大票Edi接口
	 * @param airWaybillNo 正单号
	 * @return InputStream 返回IO流
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-27 上午10:33:36
	 */
	InputStream uploadPickupCallEdi(List<String> idsList , String callIsNotEdiFlag ,String airWaybillNo);
	
	/**
	 * 中转提货Edi接口
	 * @param airWaybillNo 正单号
	 * @return InputStream 返回IO流
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-27 上午10:33:36
	 */
	InputStream uploadTranPickupCallEdi(List<String> idsList , String callIsNotEdiFlag ,String airWaybillNo);
	
	/**
	 * 变更Edi接口
	 * @param airWaybillNo 正单号
	 * @return InputStream 返回IO流
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-27 上午10:33:36
	 */
	InputStream uploadChangeListCallEdi(List<String> idsList ,String callIsNotEdiFlag,String airWaybillNo);
}