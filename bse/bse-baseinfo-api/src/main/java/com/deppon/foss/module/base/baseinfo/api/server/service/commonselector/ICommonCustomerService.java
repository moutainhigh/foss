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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonCustomerService.java
 * 
 * FILE NAME        	: ICommonCustomerService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.server.service
 * FILE    NAME: ICommonCustomerService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CusAccountDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;

/**
 * 公共组件--客户信息及客户账号信息查询service接口定义
 * @author 078823-foss-panGuangJun
 * @date 2012-12-4 下午3:33:04
 */
public interface ICommonCustomerService {
    /**
     * 根据传入对象查询符合条件所有客户信息
     * 
     * @author 078823-foss-panGuangjun
     * @date 2012-12-4 下午2:28:27
     * @param entity :查询条件
     * @param limit:页数大小
     * @param start:开始条数
     * @return
     */
     List<CustomerEntity> queryCustomers(CustomerEntity entity,  int limit, int start);
    /**
     * 统计总记录数
     * 
     * @author 078823-foss-panGuangjun
     * @date 2012-12-4 下午2:28:27
     * @param entity:查询条件
     * @return Long
     */
     Long queryRecordCount(CustomerEntity entity);
    /**
     * 通过传入查询条件对象dto，返回客户（会员）信息的对象集合list 
     * 
     * @author 078823-foss-panGuangjun
     * @date 2012-12-4 上午11:42:44
     * @param condition:查询条件
     * @return  List<CustomerContactDto>:包含客户账号信息的集合
     */
     List<CusAccountDto> queryCustomerAndAccountsInfo(CustomerQueryConditionDto condition);
     
     /**
 	 *  查询包含客户或散户的账号信息集合.
 	 * @author 101911-foss-zhouChunlai
 	 * @date 2013-3-13 上午9:52:12
 	 */
     List<CusAccountDto> queryCustAccountsInfo(CustomerQueryConditionDto condition);
     
	 /**
	 * <p>客户表与客户账户表关联后的信息查询，用于开户人姓名选择器</p> 
	 * @author 232607 
	 * @date 2015-11-4 上午10:59:54
	 * @param cusAccountDto
	 * @param start 
	 * @param limit 
	 * @return
	 * @see
	 */
	 List<CusAccountDto> queryCusAccountJoinCus(
			CusAccountDto cusAccountDto, int limit, int start);
	 
	 /**
	 * <p>客户表与客户账户表关联后的信息查询，用于开户人姓名选择器</p> 
	 * @author 232607 
	 * @date 2015-11-4 上午11:00:45
	 * @param cusAccountDto
	 * @return
	 * @see
	 */
	 long queryCusAccountJoinCusCount(CusAccountDto cusAccountDto);
    
    
}
