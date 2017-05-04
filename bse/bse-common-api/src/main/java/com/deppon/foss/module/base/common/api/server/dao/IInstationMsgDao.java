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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/server/dao/IInstationMsgDao.java
 * 
 * FILE NAME        	: IInstationMsgDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.server.dao;

import java.util.List;
import java.util.Set;

import com.deppon.foss.module.base.common.api.shared.domain.InstationMsgEntity;
import com.deppon.foss.module.base.common.api.shared.dto.MessagesDto;


/**
 * 站内消息明细Dao
 * @author 101911-foss-zhouChunlai
 * @date 2012-12-10 上午8:48:58
 */
public interface IInstationMsgDao {
	
	/**
	 * 新增人员对人员发的站内消息
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-13 上午9:11:04
	 */
	int insertInstationMsg(InstationMsgEntity entity);
	
	/**
	 * 批量新增站内消息
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-13 下午7:06:14
	 */
	int batchSaveMsg(List<InstationMsgEntity> instationMsgList);
	
	/**
	 * 根据条件查询出所有站内消息数据
	 * @author 101911-foss-zhouChunlai
	 * @param entity 查询条件实体
	 * @date 2012-12-17 下午8:44:49
	 * @return 
	 */
	List<MessagesDto> queryInstationMsgByEntity(MessagesDto entity, int start, int limit);
	
	/**
	 * 根据条件查询出所有站内消息数据总条数
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午11:05:43
	 */
	long countQueryInstationMsgByEntity(MessagesDto entity);
	
	/**
	 * 根据员工查询出所有站内消息数据
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-13 上午11:16:51
	 */
	List<MessagesDto> queryInstationMsgByReceiveUserInfo(String receiveUserCode,String receiveOrgCode,Set<String> receiveRoleCodes, int start, int limit);
	

	/**
	 * 根据条件查询出所有站内消息数据总条数
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午11:05:43
	 */
	long countQueryInstationMsgByReceiveUserInfo(String receiveUserCode,String receiveOrgCode,Set<String> receiveRoleCodes);
	
	/**
	 * 统计当前用户未处理站内消息数据总条数
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午10:56:22
	 */
	List<MessagesDto> queryInstationMsgTotal(String receiveUserCode,String receiveOrgCode,Set<String> receiveRoleCodes,String msgType);
	
	/**
	 * 根据Id更新站内消息的读取状态
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-25 下午3:19:12
	 * @return 
	 */ 
	int  updateInstationMsgByIds(List<String> ids,String modifyUserCode,String modifyUserName) ;
	
	/**
	 * 查询当前用户未读取的消息数据
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午10:56:22
	 */
	List<MessagesDto> queryMsgByMsgType(String receiveUserCode,String receiveOrgCode,Set<String> receiveRoleCodes,String msgType, int start, int limit);
	
	/**
	 *	查询当前用户未处理站内消息数据总条数
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午11:05:43
	 */
	long countQueryMsgByMsgType(String receiveUserCode,String receiveOrgCode,Set<String> receiveRoleCodes,String msgType);

	List<MessagesDto> queryFailingInvoiceMsgTotal(String currentDeptCode,String msgType);
	/**
	 * <p>TODO(查询未对账月结客户消息)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-2 上午9:49:04
	 * @param currentDeptCode
	 * @param msgTypeUnreconciliation
	 * @return
	 * @see
	 */
	List<MessagesDto> queryUnReconciliationMsgTotal(String currentDeptCode,
			String msgType);

	/**
	 * <p>TODO(查询距结款时间不足5日还未还款月结客户消息)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-2 上午9:49:10
	 * @param currentDeptCode
	 * @param msgTypeNorepayment
	 * @return
	 * @see
	 */
	List<MessagesDto> queryNoRepaymentMsgTotal(String currentDeptCode,
			String msgType);
}
