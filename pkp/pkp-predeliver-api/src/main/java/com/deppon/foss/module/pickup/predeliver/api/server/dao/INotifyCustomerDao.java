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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/dao/INotifyCustomerDao.java
 * 
 * FILE NAME        	: INotifyCustomerDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.InvoiceInfomationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyMultibillDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;

/**
 * 
 * 客户通知综合类
 * 
 * @author ibm-wangfei
 * @date Oct 19, 2012 3:21:18 PM
 */
public interface INotifyCustomerDao {

	/**
	 * 新增运单通知信息
	 * 
	 * @author ibm-wangfei
	 * @date Oct 19, 2012 3:28:46 PM
	 */
	int addNotificationEntity(NotificationEntity notificationEntity);

	/**
	 * 更新运单通知信息
	 * 
	 * @author ibm-wangfei
	 * @date Oct 19, 2012 3:28:46 PM
	 */
	int updateNotificationEntity(NotificationEntity notificationEntity);

	/**
	 * 
	 * 新增运单通知发票信息
	 * 
	 * @author ibm-wangfei
	 * @date Oct 19, 2012 3:29:04 PM
	 */
	int addInvoiceInfomationEntity(InvoiceInfomationEntity invoiceInfomationEntity);

	/**
	 * 
	 * 通过运单通知查询运单通知记录
	 * 
	 * @author ibm-wangfei
	 * @date Oct 19, 2012 3:29:26 PM
	 */
	List<NotificationEntity> queryNotificationsByParam(NotificationEntity notifycation);
	/**
	 * 
	 * 通过运单通知查询运单通知记录
	 * 
	 * @author ibm-wangfei
	 * @date Oct 19, 2012 3:29:26 PM
	 */
	List<NotificationEntity> queryNotificationsByParam1(NotificationEntity notifycation);

	/**
	 * 
	 * 根据运单号查询运单通知列表
	 * 
	 * @author ibm-wangfei
	 * @date Oct 24, 2012 4:40:05 PM
	 */
	List<NotifyCustomerDto> queryNotifyCustomerListForWaybillNo(NotifyCustomerConditionDto conditionDto);

	/**
	 * 
	 * 根据交接单号查询运单通知列表
	 * 
	 * @author ibm-wangfei
	 * @date Oct 24, 2012 4:40:05 PM
	 */
	List<NotifyCustomerDto> queryNotifyCustomerListForHandoverNo(NotifyCustomerConditionDto conditionDto, int start, int limit);

	/**
	 * 
	 * 根据交接单号查询运单通知数量
	 * 
	 * @author ibm-wangfei
	 * @date Nov 7, 2012 4:32:13 PM
	 */
	NotifyCustomerConditionDto queryNotifyCustomerCountForHandoverNo(NotifyCustomerConditionDto conditionDto);

	/**
	 * 
	 * 根据车次号、预计到达时间查询运单通知列表
	 * 
	 * @author ibm-wangfei
	 * @date Oct 24, 2012 4:40:05 PM
	 */
	List<NotifyCustomerDto> queryNotifyCustomerListForVehicleAssembleNo(NotifyCustomerConditionDto conditionDto, int start, int limit);

	/**
	 * 
	 * 根据车次号查询运单通知数量
	 * 
	 * @author ibm-wangfei
	 * @date Nov 7, 2012 4:32:13 PM
	 */
	NotifyCustomerConditionDto queryNotifyCustomerCountForVehicleAssembleNo(NotifyCustomerConditionDto conditionDto);

	/**
	 * 
	 * 根据库存查询运单通知列表
	 * 
	 * @author ibm-wangfei
	 * @date Oct 24, 2012 4:40:05 PM
	 */
	List<NotifyCustomerDto> queryNotifyCustomerListForStock(NotifyCustomerConditionDto conditionDto, int start, int limit);

	/**
	 * 
	 * 根据库存查询运单通知数量
	 * 
	 * @author ibm-wangfei
	 * @date Nov 7, 2012 4:32:13 PM
	 */
	NotifyCustomerConditionDto queryNotifyCustomerCountForStock(NotifyCustomerConditionDto conditionDto);

	/**
	 * 
	 * 查询运单通知明细
	 * 
	 * @author ibm-wangfei
	 * @date Oct 24, 2012 4:40:05 PM
	 */
	NotifyCustomerDto queryNotifyCustomer(NotifyCustomerConditionDto conditionDto);

	/**
	 * 
	 * 根据运单编号，更新运单附属表的通知信息
	 * 
	 * @author ibm-wangfei
	 * @date Oct 31, 2012 2:21:46 PM
	 */
	int updateActualFreightEntityByWaybillNo(ActualFreightEntity actualFreightEntity);

	/**
	 * 
	 * 根据运单号列表，查询运单相关信息
	 * 
	 * @author ibm-wangfei
	 * @date Nov 2, 2012 10:07:40 AM
	 */
	List<NotifyCustomerDto> queryWaybillsByNos(String[] waybillNos);

	/**
	 * 
	 * 初步查询出需要统计仓储费的运单信息
	 * 
	 * @author ibm-wangfei
	 * @date Nov 12, 2012 2:55:16 PM
	 */
	List<NotifyCustomerDto> queryWaybillsByCFF(String[] productCodes);

	/**
	 * 
	 * 批量更新短信状态
	 * 
	 * @author ibm-wangfei
	 * @date Nov 28, 2012 4:50:46 PM
	 */
	int batchUpdateNotificationEntity(NotificationEntity notificationEntity);

	/**
	 * 
	 * 查询符合生成仓储异常的运单
	 * 
	 * @author ibm-wangfei
	 * @date Dec 6, 2012 7:24:22 PM
	 */
	List<NotifyCustomerDto> queryWaybillsWithWarehousingTimeout(String[] productCodes, int warehouseTimeoutDate);
	
	/**
	 * 批量更新运单附加表通知状态
	 * @param notificationEntity
	 * @return 更新数量
	 * @author ibm-wangfei
	 * @date Jan 6, 2013 9:25:22 AM
	 */
	int batchUpdateActualFreightEntity(NotificationEntity notificationEntity);	
	
	/**
	 *
	 * 根据运单号、通知结果、时间查询
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-6-12 下午5:32:48
	 */
	ActualFreightEntity queryActualFreightByWaybillNo(ActualFreightEntity actualFreightEntity);
	
	/**
	 * 
	 * 查询当天语音通知成功的记录
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-6-19 上午11:12:23
	 */
	int queryVoiceNoticeSuccessByNo(NotificationEntity notificationEntity);
	/**
	 * 
	 * 查询当天短信通知成功的记录
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-6-19 上午11:12:23
	 */
	int querySmsNoticeSuccessByNo(NotificationEntity notificationEntity);
	/**
	 * 
	 * 根据车次号查询运单通知数量.
	 *  @author 159231-foss-meiying
	 * @date 2014-1-1 下午3:00:39
	 */
	Long queryBeforeNoticeCountForVehicleAssembleNo(NotifyCustomerConditionDto conditionDto);
	 /**
	  * 
	  * 根据车次号、预计到达时间查询运单通知列表
	  *  @author 159231-foss-meiying
	  * @date 2014-1-1 下午3:57:09
	  */
	List<NotifyCustomerDto> queryBeforeNoticeListForVehicleAssembleNo(NotifyCustomerConditionDto conditionDto, int start, int limit);
	/**
	 * 根据运单号查询运单通知数量.
	 *  @author 159231-foss-meiying
	 * @date 2014-1-22 下午3:45:39
	 */
	 Long queryBeforeNoticeCountForWaybillNos(NotifyCustomerConditionDto conditionDto) ;
	 /**
	  * 
	  * 根据交接单号查询运单通知数量.
	  *  @author 159231-foss-meiying
	  * @date 2014-1-22 下午3:49:01
	  */
	 Long queryBeforeNoticeCountForHandoverNo(NotifyCustomerConditionDto conditionDto);
	 /**
	  * 根据运单号查询运单通知数量.
	  *  @author 159231-foss-meiying
	  * @date 2014-1-22 下午3:53:44
	  */
	 List<NotifyCustomerDto> queryBeforeNoticeListForWaybillNos(NotifyCustomerConditionDto conditionDto, int start, int limit);
	 /**
	  * 根据交接单号查询运单通知列表.
	  *  @author 159231-foss-meiying
	  * @date 2014-1-22 下午3:55:54
	  */
	 List<NotifyCustomerDto> queryBeforeNoticeListForHandoverNo(NotifyCustomerConditionDto conditionDto, int start, int limit) ;
	 /**
	  * 查询派送提前通知-运单明细信息
	  *  @author 159231-foss-meiying
	  * @date 2014-2-20 下午2:17:12
	  */
	 NotifyCustomerDto queryBeforeNoticeDetailByWaybillNo(NotifyCustomerConditionDto conditionDto) ;
	 /**
	  * 根据传入的批量运单号查询不在到达部门库存的运单
	  *  @author 159231-foss-meiying
	  * @date 2014-2-27 上午9:11:12
	  */
	 List<String> queryNoWaybillStockByNos(NotifyCustomerConditionDto conditionDto);
	 /**
	  * 我只想把所有添加、更新操作写完
	  * @author Foss-105888-Zhangxingwang
	  * @date 2014-3-31 11:20:14
	  */
	 int updateByPrimaryKey(NotificationEntity notificationEntity);
	 
	 /**
	  * 
	  * @Description			合送 
	  * @param bills			多个运单号码用","连接起来
	  * @return  		
	  * @author				mujun
	  * @date 				2014-4-10 上午9:42:23 
	  * @version				1.0
	  */
	 int mergeNoticeWaybill(Map<String,String> mp);
	 
	 /**
	  * 
	  * @Description			解除合并 
	  * @param code				需要解除的合并编码
	  * @return  		
	  * @author				mujun
	  * @date 				2014-4-10 上午9:44:27 
	  * @version				1.0
	  */
	/**
	 * 解除合并 
	 * @param bills 需求解除的合送运单
	 * @return
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
	  * 根据运单号取通知记录表的送货要求(最新的一条)
	  *  @author 239284
	  * @param waybillNo
	  * @return 送货要求
	  */
	 String queryDeliverRequire(String waybillNo); 
	 
	 /**
		 * 根据运单号查询计划提前通知信息
		 * @author 329757-foss-liuxiangcheng 
		 * @date 2016-6-23 下午3:24:59
		 * @param @param conditionDto
		 * @param @param start
		 * @param @param limit
		  */
		 List<NotifyCustomerDto> queryArriveNoticeListForWaybillNos(
					NotifyCustomerConditionDto conditionDto, int start, int limit);
		
		/**
		 * 根据运单号查询计划提前通知信息总条数
		* @author 329757-foss-liuxiangcheng 
		* @date 2016-6-24 上午8:52:06
		* @param @param conditionDto
		* @param @return    设定文件 
		* @return Long    返回类型 
		* @throws
		 */
		 Long queryArriveNoticeCountForWaybillNos(
					NotifyCustomerConditionDto conditionDto);

		/**
		 * 根据计划到达时间组合查询总条数
		* @author 329757-foss-liuxiangcheng 
		* @date 2016-6-25 上午11:00:21
		* @param @param conditionDto
		* @param @return    设定文件 
		* @return Long    返回类型 
		* @throws
		 */
		 Long queryArriveNoticeCountForPlanArriveTime(
					NotifyCustomerConditionDto conditionDto);
		
		/**
		 * 根据实际到达时间查询总条数
		* @author 329757-foss-liuxiangcheng 
		* @date 2016-6-25 下午12:47:23
		* @param @param conditionDto
		* @param @return    设定文件 
		* @return Long    返回类型 
		* @throws
		 */
		 Long queryArriveNoticeCountForArriveTime(
					NotifyCustomerConditionDto conditionDto);
		
		/**
		 * 根据通知时间查询总条数
		* @author 329757-foss-liuxiangcheng 
		* @date 2016-6-25 下午12:53:11
		* @param @param conditionDto
		* @param @return    设定文件 
		* @return Long    返回类型 
		* @throws
		 */
		 Long queryArriveNoticeCountForNoticeTime(
					NotifyCustomerConditionDto conditionDto);
		
		/**
		 * 根据计划到达时间查询计划提前通知信息
		* @author 329757-foss-liuxiangcheng 
		* @date 2016-6-25 下午2:26:50
		* @param @param conditionDto
		* @param @param start
		* @param @param limit
		* @param @return    设定文件 
		* @return List<NotifyCustomerDto>    返回类型 
		* @throws
		 */
		 List<NotifyCustomerDto> queryArriveNoticeListForPlanArriveTime(
					NotifyCustomerConditionDto conditionDto, int start, int limit);
		
		/**
		 * 根据实际到达时间查询计划提前通知信息
		* @author 329757-foss-liuxiangcheng 
		* @date 2016-6-25 下午2:27:37
		* @param @param conditionDto
		* @param @param start
		* @param @param limit
		* @param @return    设定文件 
		* @return List<NotifyCustomerDto>    返回类型 
		* @throws
		 */
		 List<NotifyCustomerDto> queryArriveNoticeListForArriveTime(
					NotifyCustomerConditionDto conditionDto, int start, int limit);
		
		/**
		 * 根据通知时间查询计划提前通知信息
		* @author 329757-foss-liuxiangcheng 
		* @date 2016-6-25 下午2:27:59
		* @param @param conditionDto
		* @param @param start
		* @param @param limit
		* @param @return    设定文件 
		* @return List<NotifyCustomerDto>    返回类型 
		* @throws
		 */
		 List<NotifyCustomerDto> queryArriveNoticeListForNoticeTime(
					NotifyCustomerConditionDto conditionDto, int start, int limit);

		/**
		 * add by 329757 
		 * 根据运单号查询是否在库存表中
		 * @param waybillNo
		 * @return
		 */
		 String queryOrgNameByNo(String waybillNo);

		 /**
		  * 查询最新一条交接单记录
		 * @author 329757-liuxiangcheng   
		 * @date 2016-8-30 下午4:55:06
		  */
		Integer selectOneHandoverBillState(String waybillNo);
		
		/**
		 *根据运单号查询交接单中的始发部门 
		* @author 329757-liuxiangcheng   
		* @date 2016-8-30 下午5:29:17
		 */
		String selectOneOrigOrgName(String waybillNo);

		/**
		 * 根据运单号查询交接单中的到达部门
		* @author 329757-liuxiangcheng   
		* @date 2016-8-30 下午5:37:11
		 */
		String selectOneDestOrgName(String waybillNo);

		/**
		 * 查询到达时间
		 * @param waybillNo
		 * @return
		 */
		Date selectOneActualArriveTime(String waybillNo);

		/**
		 * 查询到达联状态
		 * @param waybillNo
		 * @return
		 */
		String selectArriveSheetStatus(String waybillNo);

		/**
		 * 查询到达联部门
		 * @param waybillNo
		 * @return
		 */
		String selectArriveSheetAddress(String waybillNo);

		/**
		 * 查询到达联时间
		 * @param waybillNo
		 * @return
		 */
		Date selectArriveSheetTime(String waybillNo);

		/**
		 * 查询所属部门的外场
		 * @param orgName
		 * @return
		 */
		String  selectOutNameByOrgName(String orgCode);
}