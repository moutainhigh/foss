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

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.InvoiceInfomationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArrivalGoodsWaybillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.StorageJobDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;

/**
 * 
 * 通知客户Service
 * @author ibm-wangfei
 * @date Oct 19, 2012 10:10:25 AM
 */
public interface INotifyCustomerService extends IService{
	
	/**
	 * 
	 * 新增通知相关信息
	 * @author ibm-wangfei
	 * @date Oct 22, 2012 11:40:11 AM
	 */
	 void addNotificationInfo(NotifyCustomerConditionDto conditionDto);
	
	/**
	 * 
	 * 通过运单编号查询运单通知记录
	 * @author ibm-wangfei
	 * @date Oct 24, 2012 4:34:29 PM
	 */
	NotifyCustomerConditionDto queryWaybillInfo(NotifyCustomerConditionDto conditionDto);
	
	/**
	 * 
	 * 客户通知运单查询
	 * @author ibm-wangfei
	 * @date Oct 24, 2012 4:34:07 PM
	 */
	List<NotifyCustomerDto> queryWaybillInfoList(NotifyCustomerConditionDto conditionDto, int start, int limit);
	/**
	 * 
	 * 导出通知客户
	 * 
	 * @param conditionDto
	 * @return
	 * @author ibm-wangfei
	 * @date Jul 2, 2013 11:45:19 AM
	 */
	InputStream exportNotifyCustomerInfo(NotifyCustomerConditionDto conditionDto);
	
	/**
	 * queryWaybillsByNos
	 * 根据运单号列表，查询运单相关信息
	 * @author ibm-wangfei
	 * @date Nov 2, 2012 10:54:17 AM
	 */
	List<NotifyCustomerDto> queryWaybillsByNos(String waybillNos, NotificationEntity notificationEntity, List<NotificationEntity> notificationEntitys);
	
	/**
	 * 
	 * 批量通知
	 * @author ibm-wangfei
	 * @date Nov 2, 2012 4:53:07 PM
	 */
	void batchNotify(NotifyCustomerConditionDto conditionDto);
	
	/**
	 * 
	 * 查询仓储费
	 * @author ibm-wangfei
	 * @date Nov 5, 2012 11:35:59 AM
	 */
	List<NotifyCustomerDto> queryStorageList(String waybillNos);

	/**
	 * 
	 * 查询客户通知记录总数
	 * @author ibm-wangfei
	 * @date Nov 7, 2012 4:17:37 PM
	 */
	NotifyCustomerConditionDto queryWaybillInfoCount(NotifyCustomerConditionDto conditionDto);

	/**
	 * 
	 * 获取仓库免费保管天数
	 * @author ibm-wangfei
	 * @date Nov 21, 2012 3:50:19 PM
	 */
	int getWarehouseFreeSafeDataNum();
	
	/**
	 * 
	 * 获取短信、语言可发送语言区间
	 * @author ibm-wangfei
	 * @date Nov 21, 2012 3:50:19 PM
	 */
	String getInformationReceiveTimeRange();
	
	/**
	 * 
	 * 同步更新短信、语音发送状态
	 * 本JOB每三分钟执行一次
	 * @author ibm-wangfei
	 * @date Nov 27, 2012 6:14:48 PM
	 */
	void syncSmsStatus();

	/**
	 * 
	 * 发送短信、语音
	 * @param notificationEntity 消息实体
	 * 必输字段见NotificationEntity，ID不需要赋值
	 * 需要添加事务处理
	 * 
	 * @author ibm-wangfei
	 * @date Nov 28, 2012 5:33:36 PM
	 */
	void sendMessage(NotificationEntity notificationEntity);

	/**
	 * 
	 * <p>运单仓储异常JOB</p>
	 * 无论通知是否成功，入库时间达到一星期客户还未到营业部提货，则此票货物标记为“仓储超时异常”，进入“异常货物处理流程”。<br>
	 * 本JOB每日执行一次<br>
	 * 因本JOB需要用到在库天数（非工作日），所以必须在计算完仓储费后执行
	 * @author ibm-wangfei
	 * @date Nov 29, 2012 7:51:09 PM
	 */
	void processWarehousingTimeout();

	/**
	 * 
	 * 根据运单编号，更新运单附属表的通知信息
	 * 
	 * @author ibm-wangfei
	 * @date Oct 31, 2012 2:25:58 PM
	 */
	int updateActualFreightEntityByWaybillNo(ActualFreightEntity actualFreightEntity);
	
	/**
	 * 
	 * 获取客户通知短信、语音内容
	 * @author ibm-wangfei
	 * @date Dec 5, 2012 2:18:14 PM
	 */
	String[] queryNoticeContent(NotificationEntity entity);
	
	/**
	 * 获取短信信息.
	 * 
	 * @param entity the entity
	 * @param dto the dto
	 * @return the string
	 * @author ibm-wangfei
	 * @date Nov 21, 2012 4:02:40 PM
	 */
	String[] queryNoticeContent(NotificationEntity entity, NotifyCustomerDto dto);

	/**
	 * 
	 * 查询客户通知运单明细信息
	 * @param conditionDto
	 * @return NotifyCustomerDto
	 * @author ibm-wangfei
	 * @date Dec 24, 2012 6:02:30 PM
	 */
	NotifyCustomerDto queryNotificationByWaybillNo(NotifyCustomerConditionDto conditionDto);

	/**
	 * 
	 * 查询客户通知历史记录列表.
	 * 
	 * @param waybillNo
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 8, 2013 2:09:21 PM
	 */
	List<NotificationEntity> queryNotificationByWaybillNo(String waybillNo);

	/**
	 * 
	 * 查询运单通知记录-运单轨迹用
	 * 
	 * @param waybillNo
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 14, 2013 10:23:00 AM
	 */
	List<NotificationEntity> queryNotificationByWaybillNoAsc(String waybillNo);

	/**
	 * 
	 * 系统配置参数获取
	 * 
	 * @param storageJobDto
	 * @author ibm-wangfei
	 * @date Mar 4, 2013 7:45:03 PM
	 */
	StorageJobDto getConfigurationParams(String [] code);

	/**
	 * 
	 * 执行具体操作
	 * @param entity
	 * @param currDate
	 * @param smsSendLog
	 * @author ibm-wangfei
	 * @date Apr 23, 2013 1:41:50 PM
	 */
	void excuteInfo(NotificationEntity entity, Date currDate);

	/**
	 * 
	 * 更新之前的短信、运单的通知状态为失败
	 * 
	 * @param beforeDate
	 * @param notifyResults
	 * @author ibmbatchUpdateInfo-wangfei
	 * @date Apr 19, 2013 10:25:14 AM
	 */
	void batchUpdateInfo(Date beforeDate, String[] notifyResults);

	/**
	 * 
	 * 添加运单通知信息. 
	 * 
	 * @param notificationEntity
	 * @param invoiceInfomationEntity
	 * @author ibm-wangfei
	 * @date Apr 27, 2013 10:31:55 AM
	 */
	void addNotifyCustomer(NotificationEntity notificationEntity, InvoiceInfomationEntity invoiceInfomationEntity);

	/**
	 * 
	 * 设置到达联通知信息
	 * 
	 * @return
	 * @author ibm-wangfei
	 * @date Jun 19, 2013 10:37:45 AM
	 */
	void setArriveSheetNotifyInfo(ArriveSheetEntity arriveSheetEntity);

	/**
	 * 
	 * 自动通知
	 * 
	 * @param estimatedPickupTime
	 * @param dto
	 * @author ibm-wangfei
	 * @date Jun 24, 2013 5:19:55 PM
	 */
	void autoSendMessageDetail(String estimatedPickupTime, NotifyCustomerDto dto,boolean isPilotOrgCode);
	/**
	 * 
	 * 自动推送到货信息
	 * @author 243921 zhangtingting
	 * @date 2015-12-25 下午5:02:43
	 */
	void autoSendArrivalGoods(ArrivalGoodsWaybillDto dto);
	/**
	 * 检验是否试点部门 
	 *  @author 159231-foss-meiying
	 * @date 2014-4-4 上午9:42:14
	 */
	 boolean checkIsPilotOrgCode(String orgCode);

	/**
	 * 
	 * 根据运单号，判断运单有没有通知成功过。
	 * @param waybillNo
	 * @return
	 * @author ibm-wangfei
	 * @date Jul 4, 2013 10:09:37 AM
	 */
	boolean isNotificationSuccessByWaybillNo(String waybillNo);
	/**
	 * 根据运单号查询通知明细信息
	 *  @author 159231-foss-meiying
	 * @date 2014-4-24 下午4:41:57
	 */
	 NotifyCustomerConditionDto queryNoticeList(String waybillNo);
	 /**
	  * 根据运单号查询最后一次通知的信息
	  * @author 159231 meiying
	  * 2015-4-29  下午8:05:08
	  * @param notify
	  * @return
	  */
	 NotificationEntity queryLastNotifyByWaybillNo(NotificationEntity notify);
	 /**
	  * 根据ID修改通知的部分信息
	  * @author 159231 meiying
	  * 2015-5-4  下午2:26:04
	  * @param notify
	  * @return
	  */
	 int updatePartByPrimaryKeySelective(NotificationEntity notify);
	 /**
	  * 根据运单号，判断运单是否通知成功过   提供给中转的接口
	  * @author 243921 - foss - zhangtingting
	  * @date 2015-08-18 上午9:08:12
	  * @param waybillNo
	  * @return
	  */
	 boolean isNotificationByWaybillNoForTfr(String waybillNo);
	 
	/**
	 * @title executeCashTime
	 * @description 根据运单号算出规定兑现时间
	 * @author houlv
	 * @create_date 2016年9月20日
	 * @param waybillNo
	 * @return
	 */
	Date executeCashTime(String waybillNo);
	 
}