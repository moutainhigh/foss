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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pda/api/server/service/IPdaDeliverTaskService.java
 * 
 * FILE NAME        	: IPdaDeliverTaskService.java
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
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverHandTaskDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverTaskDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaFinancialDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaFinancialParamDto;

/**
 * 查询送货任务接口
 * @author 097972-foss-dengtingting
 *
 */
public interface IPdaDeliverTaskService extends IService {
	
	/**
	 * 根据司机工号、车牌号查询送货任务接口
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-29 上午9:12:32
	 * @param driverCode 司机工号
	 * @param vehicleNo 车牌号
	 * @return
	 */
	List<PdaDeliverTaskDto> getDeliverTaskList(String driverCode,String vehicleNo);
	
	/**
	 * 根据司机工号、车牌号查询快递送货任务接口
	 * @author 243921-foss-zhangtingting
	 * @date 2015-04-14 上午11:01:34
	 * @param driverCode 司机工号
	 * @param vehichleNo 车牌号
	 * @return
	 */
	List<PdaDeliverTaskDto> getExpressDeliverTaskList(String driverCode,String vehicleNo);
	
	/**
	 * 根据业务号、司机工号、车牌号查询快递送货任务接口
	 * @author 243921-foss-zhangtingting
	 * @date 2015-04-15 下午4:21:10
	 * @param taskNo 业务号
	 * @param driverCode 司机工号
	 * @param vehichleNo 车牌号
	 * @return
	 */
	List<PdaDeliverHandTaskDto> getDeliverHandTaskList(String taskNo,String driverCode,String vehicleNo);
	
	/**
	 * 完成下拉派送单接口 更新状态为 “已下拉”
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-22 下午8:35:59
	 */
	int updateDeliverbillStatus(String deliverbillNo);
	
	/**
	 * @Title: selectFinancial
	 * @Description: 根据运单号查询代收款和到付款
	 * @param pdaFinancialParamDto
	 * @return
	 */
	PdaFinancialDto selectFinancial(PdaFinancialParamDto pdaFinancialParamDto);

}