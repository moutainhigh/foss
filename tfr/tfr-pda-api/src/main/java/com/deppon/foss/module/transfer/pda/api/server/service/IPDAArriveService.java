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
 *  PROJECT NAME  : tfr-pda-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/server/service/IPDAArriveService.java
 *  
 *  FILE NAME          :IPDAArriveService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.pda.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDADepartDto;
/**
 * 
 * 对外的PDA接口
 * @author foss-liubinbin(for IBM)
 * @date 2012-11-2 上午10:25:00
 */
public interface IPDAArriveService extends IService
{
	/**
	 * 
	 * 记录PDA发车时间
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-31 下午6:27:07
	 */
	public String writeArriveData(PDADepartDto pdaDto);
	

}