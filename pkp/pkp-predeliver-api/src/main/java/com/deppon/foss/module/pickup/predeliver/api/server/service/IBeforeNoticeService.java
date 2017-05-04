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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/service/INotifyCustomerService.java
 * 
 * FILE NAME        	: INotifyCustomerService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.InvoiceInfomationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyMultibillDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;

/**
 * 
 * 派送提前通知接口
 * @author 159231-foss-meiying
 * @date 2014-1-1 上午11:00:38
 */
public interface IBeforeNoticeService extends IService{
	
	/**
	 * 新增通知相关信息
	 *  @author 159231-foss-meiying
	 * @date 2014-3-5 上午10:56:29
	 */
	 void addNotificationInfo(NotifyCustomerConditionDto conditionDto);
	 /**
	  * 客户通知运单查询
	  *  @author 159231-foss-meiying
	  * @date 2014-3-5 上午10:56:40
	  */
	List<NotifyCustomerDto> queryWaybillInfoList(NotifyCustomerConditionDto conditionDto, int start, int limit);
	/**
	 * 查询客户通知记录总数
	 *  @author 159231-foss-meiying
	 * @date 2014-3-5 上午10:56:52
	 */
	Long queryWaybillInfoCount(NotifyCustomerConditionDto conditionDto);
	/**
	 * 根据运单编号，更新运单附属表的通知信息
	 *  @author 159231-foss-meiying
	 * @date 2014-3-5 上午10:57:05
	 */
	int updateActualFreightEntityByWaybillNo(ActualFreightEntity actualFreightEntity);
	/**
	 *  查询客户通知运单明细信息
	 *  @author 159231-foss-meiying
	 * @date 2014-3-5 上午10:57:17
	 */
	NotifyCustomerDto queryBeforeNoticeDetailByWaybillNo(NotifyCustomerConditionDto conditionDto);
	/**
	 * 添加运单通知信息
	 *  @author 159231-foss-meiying
	 * @date 2014-3-5 上午10:57:29
	 */
	void addNotifyCustomer(NotificationEntity notificationEntity, InvoiceInfomationEntity invoiceInfomationEntity);
	/**
	 * 通过运单编号查询运单通知记录.
	 *  @author 159231-foss-meiying
	 * @date 2014-2-18 上午11:18:22
	 */
	NotifyCustomerConditionDto queryWaybillInfo(NotifyCustomerConditionDto conditionDto);
	/**
	 * 查询不在库存的运单号
	 *  @author 159231-foss-meiying
	 * @date 2014-2-27 上午9:16:23
	 */
	 NotifyCustomerConditionDto queryNotInStockWaybillNos(NotifyCustomerConditionDto dto);
	 
	 /**
	  * 
	  * @Description			合送 
	  * @param bills			多个运单号码用","连接起来
	  * @return  		
	  * @author				mujun
	  * @date 				2014-4-10 上午9:42:23 
	  * @version				1.0
	  */
	 int mergeNoticeWaybill(String[] bills);
	 
	 /**
	  * 
	  * @Description			解除合并 
	  * @param code				需要解除的合并编码
	  * @return  		
	  * @author				mujun
	  * @date 				2014-4-10 上午9:44:27 
	  * @version				1.0
	  */
	 int relieveNoticeWaybill(String[] bills);
	 
	 /**
	  * 
	  * @Description			通过客户编码查询多票信息列表 
	  * @param CustomerCode
	  * @return  		
	  * @author				mujun
	  * @date 				2014-4-10 下午3:22:47 
	  * @version				1.0
	  */
	 List<NotifyMultibillDto> getMultibillListByCustomer(NotifyCustomerConditionDto notifyCustomerConditionDto);
	/**
	 * 批量通知
	 * @param conditionDto
	 */
	 void batchNotify(NotifyCustomerConditionDto conditionDto) ;
}