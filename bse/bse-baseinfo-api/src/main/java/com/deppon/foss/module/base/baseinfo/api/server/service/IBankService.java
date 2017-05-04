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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IBankService.java
 * 
 * FILE NAME        	: IBankService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;

/**
 * 银行Service接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-30 下午4:07:16
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-30 下午4:07:16
 * @since
 * @version
 */
public interface IBankService extends IService {

	/**
	 * 新增银行
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-10-30 下午4:07:16
	 * @param entity
	 *            银行实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int addBank(BankEntity entity);

	/**
	 * 根据code作废银行
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-30 下午4:07:16
	 * @param codeStr
	 *            code字符串
	 * @param modifyUser
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int deleteBank(String codeStr, String modifyUser);

	/**
	 * 修改银行
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-30 下午4:07:16
	 * @param entity
	 *            银行实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int updateBank(BankEntity entity);

	/**
	 * 根据传入对象查询符合条件所有银行信息
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-30 下午4:07:16
	 * @param limit
	 *            每页最大显示记录数
	 * @param start
	 *            开始记录数
	 * @return 符合条件的实体列表
	 * @see
	 */
	List<BankEntity> queryBanks(BankEntity entity, int limit, int start);

	/**
	 * 统计总记录数
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-30 下午4:07:16
	 * @param entity
	 *            银行实体
	 * @return
	 * @see
	 */
	Long queryRecordCount(BankEntity entity);

	/**
	 * <p>
	 * 根据银行编码查询银行信息
	 * </p>
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2013-2-2 上午10:34:13
	 * @param bankCode
	 *            银行编码
	 * @return
	 * @see
	 */
	BankEntity queryBankInfoByCode(String bankCode);

	/**
	 * 获取最后更新时间
	 * 
	 * @author dp-yangtong
	 * @date 2012-10-30 下午4:07:16
	 * @param entity
	 *            银行实体
	 * @return
	 * @see
	 */
	Date getLastUpdateTime();

	/**
	 * @获得银行信息，用于银行信息同步
	 * @param fromDate
	 * @param toDate
	 * @param pageIndex
	 * @param PageSize
	 * @return Customer
	 */
	List<BankEntity> getBanks(Date fromDate, String userID);
  /**
   * 根据银行名称精确查询银行信息
   * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
   * @author 268984 
   * @date 2016-4-14 下午3:52:01
   * @param bankName
   * @return
   * @see
   */
	BankEntity queryBankInfoByName(String bankName);

}
