/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-common-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/server/service/IMessageService.java
 * 
 * FILE NAME        	: IMessageService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.base.common.api.shared.domain.InstationMsgEntity;
import com.deppon.foss.module.base.common.api.shared.dto.MessagesDto;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

/**
 * 站内消息Service
 * 
 * 1. 全网消息
 *    调用接口createInstationMsg，并且指定接收者类型为全网；
 * 2. 发送给单个员工
 *    调用接口createInstationMsg，并且指定接收者员工号；
 * 3. 发送给部门或者部门+角色
 *    调用接口createBatchInstationMsg，指定组织编码，如果：
 *    3.1 不指定角色，则对此部门下的所有员工进行发送
 *    3.2 指定角色，则对此部门下的特定角色的员工发送消息
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-12-10 上午9:08:12
 */
public interface IMessageService extends IService {

	/**
	 * 人员对人员/全网的站内消息发送
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-12 下午6:57:59
	 */
	void createInstationMsg(InstationMsgEntity entity);
	/**
	 * 手动发送全网消息
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-12 下午6:57:59
	 */
	void manualCreateInstationMsg(InstationJobMsgEntity entity,CurrentInfo currentInfo); 
	/**
	 * 人员对组织的站内消息发送
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-12 下午6:57:59
	 */
	void createBatchInstationMsg(InstationJobMsgEntity entity);
	
	/**
	 * 手动发送人员对组织站内消息
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-12 下午6:57:59
	 */
	void manualCreateBatchInstationMsg(InstationJobMsgEntity entity,CurrentInfo currentInfo);
	
	/**
	 * 根据条件查询站内消息
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-12 下午8:02:56
	 */
	List<MessagesDto> queryInstationMsgByEntity(MessagesDto entity,CurrentInfo currentInfo, int start, int limit);
	/**
	 * 根据条件查询站内消息总条数
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-18 上午10:03:24
	 * @return 
	 */
	Long countQueryInstationMsgByEntity(MessagesDto entity,CurrentInfo currentInfo);
	
	/**
	 * 根据当前用户编码查询站内消息
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-12 下午8:02:56
	 */
	List<MessagesDto> queryInstationMsgByEmpInfo(CurrentInfo currentInfo, int start, int limit);
	/**
	 * 根据当前用户编码查询站内消息
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-18 上午10:03:24
	 * @return 
	 */
	Long countQueryInstationMsgByEmpInfo(CurrentInfo currentInfo);
	/**
	 * 统计当前用户未处理站内消息数据总条数
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午10:56:22
	 */
	List<MessagesDto> queryInstationMsgTotal(CurrentInfo currentInfo,String msgType);
	
	/**
	 * 统计当前用户未处理站内消息数据总条数
	 * @author 132599-foss-shenweihua
	 * @date 2013-07-22  下午3:36:22
	 */
	List<MessagesDto> queryQWInstationMsgTotal(CurrentInfo currentInfo);
	
	/** 
	 * 查询当前用户未处理站内消息数据
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-29 上午8:32:45
	 * @return 
	 */
	List<MessagesDto> queryMsgByMsgType(String msgType,CurrentInfo currentInfo, int start, int limit);
	/**
	 *	查询当前用户未处理站内消息数据总条数
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午11:05:43
	 */
	long countQueryMsgByMsgType(String msgType,CurrentInfo currentInfo);
	/**
	 * 根据Id更新站内消息的读取状态
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-25 下午3:19:12
	 * @return 
	 */
	void  updateInstationMsgByIds(List<String> ids,CurrentInfo currentInfo);
	List<MessagesDto> queryFailingInvoiceMsgTotal(CurrentInfo currentInfo, String msgType); 

	/**
	 * <p>TODO(查询未对账月结客户消息)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-2 上午9:36:14
	 * @param currentInfo
	 * @param msgTypeUnreconciliation
	 * @return
	 * @see
	 */
	List<MessagesDto> queryUnReconciliationMsgTotal(CurrentInfo currentInfo,
			String msgType);
	/**
	 * <p>TODO(查询距结款时间不足5日还未还款月结客户消息)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-2 上午9:37:48
	 * @param currentInfo
	 * @param msgTypeNorepayment
	 * @return
	 * @see
	 */
	List<MessagesDto> queryNoRepaymentMsgTotal(CurrentInfo currentInfo,
			String msgType); 
	/**
	 * 派送退回提醒
	 * @author 307196-zhaoliujun
	 * @param exceptionOperate
	 * @param status
	 * @return
	 */
	List<MessagesDto> queryDeliveryReturnMsgTotal(String exceptionOperate, String status,CurrentInfo currentInfo);
	/**
	 * <p>ECS快递将站内消息推给FOSS，FOSS新增到数据库中</p> 
	 * @author 232607 
	 * @date 2016-4-28 下午5:01:50
	 * @param entity
	 * @see
	 */
	void saveInstationMsgByECS(InstationMsgEntity entity);
}
