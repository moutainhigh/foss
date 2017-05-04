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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/server/dao/ISerialNumberRuleDao.java
 *  
 *  FILE NAME          :ISerialNumberRuleDao.java
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
package com.deppon.foss.module.transfer.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.common.api.shared.domain.CreateErrorLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.SerialNumberRuleEntity;

/**
 * 处理与中转编号相关的数据操作
 * @author foss-wuyingjie
 * @date 2012-11-17 下午4:52:22
 * 
 * 
 */
public interface ISerialNumberRuleDao {

	/**
	 * 由编码规则编号获取编码规则记录
	 * @author foss-wuyingjie
	 * @date 2012-11-5 上午11:16:21
	 * 
	 * 
	 */
	SerialNumberRuleEntity selectSnRule(String ruleCode);
	
	/**
	 * 由编码规则编号获取编码规则记录并锁定
	 * @author foss-wuyingjie
	 * @date 2012-11-5 上午11:16:21
	 * 
	 * 
	 */
	SerialNumberRuleEntity selectSnRuleForUpdate(String ruleCode);

	/**
	 * 新增一条编码规则记录
	 * 
	 * @author foss-wuyingjie
	 * @date 2012-11-5 上午11:17:42
	 * 
	 * 
	 */
	void addSnRule(SerialNumberRuleEntity snEntity);

	/**
	 * 重置此序列
	 * @author foss-wuyingjie
	 * @date 2012-11-5 上午11:18:55
	 */
	void resetSequence(String sequenceName);

	/**
	 * 获取此序列的最新值
	 * @author foss-wuyingjie
	 * @date 2012-11-5 上午11:19:46
	 * 
	 * 
	 */
	long getNextSequenceValue(String sequenceName);

	/**
	 * 按业务编码编号更新此规则记录的时间及计数
	 * @author foss-wuyingjie
	 * @date 2012-11-5 上午11:20:35
	 * 
	 * 
	 */
	void updateSnRule(SerialNumberRuleEntity snEntity);

	/**
	 * 通过始发地、目的地、出发日期
	 * 
	 * @param oriOrgCode 始发地编号
	 * 
	 * @param destOrgCode 目的地编号
	 * 
	 * @param leaveTime 出发日期(yyMMdd)
	 * 
	 * @author foss-wuyingjie
	 * 
	 * @date 2012-11-17 下午4:53:46
	 */
	String queryVehicleAssembleBillNo(String oriOrgCode,	List<String> destOrgCodeList, String leaveTime);
	
	/**
	 * 插入错误记录
	 * @author alfred
	 * @date 2016-10-19 16:26:34
	 * @param entity
	 * @return
	 * @see
	 */
	public void createErrorLog(CreateErrorLogEntity entity);
	
}