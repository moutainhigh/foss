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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/INonfixedCustomerService.java
 * 
 * FILE NAME        	: INonfixedCustomerService.java
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

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerPaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;

/**
 * 散客信息Service接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-20 下午6:20:51 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-20 下午6:20:51
 * @since
 * @version
 */
public interface INonfixedCustomerService extends IService {
    
    /**
     * 新增散客信息
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-11-20 下午6:20:51
     * @param entity
     *            散客信息实体
     * @return 1：成功；-1：失败
     * @see
     */
     int addNonfixedCustomer(NonfixedCustomerEntity entity);

    /**
     * 根据开户账号作废散客信息
     * 
     * @author dp-xieyantao
     * @date 2012-11-20 下午6:20:51
     * @param custId 散客ID
     * @param modifyUser
     * @return 1：成功；-1：失败
     * @see
     */
     int deleteNonfixedCustomerByCode(String custId, String modifyUser);

    /**
     * 修改散客信息
     * 
     * @author dp-xieyantao
     * @date 2012-11-20 下午6:20:51
     * @param entity
     *            散客信息实体
     * @return 1：成功；-1：失败
     * @see
     */
     int updateNonfixedCustomer(NonfixedCustomerEntity entity);
     
     /**
      * <p>通过传入一个客户编码查询出财务未作废散客信息</p> 
      * @author 094463-foss-xieyantao
      * @date 2013-7-9 上午11:51:03
      * @param custCode 散客编码
      * @return
      * @see
      */
     NonfixedCustomerEntity queryNoDeletedCustInfoByCode(String custCode);
    
    /**
     * <p>根据传入查询条件对象dto，查询符合条件的临欠散客信息集合list（只能查询有效的）</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-31 上午10:28:20
     * @param condition
     * @return
     * @see
     */
     List<CustomerQueryConditionDto> queryCustomerByCondition(CustomerQueryConditionDto condition);
     
     /**
      * <p>根据传入查询条件对象dto，查询符合条件的临欠散客信息集合list(可以查询有效无效的)</p> 
      * @author 094463-foss-xieyantao
      * @date 2013-1-31 上午10:28:20
      * @param condition
      * @return
      * @see
      */
      List<NonfixedCustomerEntity> queryCustomerByConditionAll(CustomerQueryConditionDto condition);

	/**
	 * 查询散客记录总数
	 * @param tempConditionDto
	 */
	int countCustomerByCondition(CustomerPaginationDto tempConditionDto);

	/**
	 * @param tempConditionDto
	 * @return
	 */
	List<CustomerQueryConditionDto> queryCustomerByConditionByPage(
			CustomerPaginationDto tempConditionDto);
	
	/**
	 * 根据查询条件获取客户信息是否存在
	 * @创建时间 2014-5-27 下午6:57:11   
	 * @创建人： WangQianJin
	 */
	int queryCustomerExistByCondition(CustomerQueryConditionDto condition);

}
