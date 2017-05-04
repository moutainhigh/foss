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
 * PROJECT NAME	: pkp-pda-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pda/api/server/service/IPdaSignService.java
 * 
 * FILE NAME        	: IPdaSignService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pda.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ElectronicTicketEntity;

/**
 * 刷卡电子小票图片地址
 * @author 
 * @date
 * @since
 * @version
 */
public interface IPdaImageService extends IService {
	
	/**
	 * 新增刷卡电子小票图片地址
	 * 
	 * 
	 * @author
	 * @date
	 */
	int pdaAddImage(List<ElectronicTicketEntity> entitys);
	List<ElectronicTicketEntity> queryElectronicTicketListBySerialNumber(
			List<String> serialNos, int start, int limit);
	List<ElectronicTicketEntity> queryElectronicTicketListByWaybillNumber(
			List<String> wayBillNos, int start, int limit);
	long queryCountByWaybn(List<String> wayBillNos);
	long queryCountBySerialN(List<String> serialNos);
	int deleteElectronicTicket(ElectronicTicketEntity entity);
	
	public List<ElectronicTicketEntity> queryElectronicTicketBySerialNo(ElectronicTicketEntity entity);
	
			

}