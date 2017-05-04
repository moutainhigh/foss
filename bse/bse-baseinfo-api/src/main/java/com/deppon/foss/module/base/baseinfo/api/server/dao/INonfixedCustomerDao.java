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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/INonfixedCustomerDao.java
 * 
 * FILE NAME        	: INonfixedCustomerDao.java
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
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.math.BigInteger;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerPaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;


/**
 * 散客信息DAO接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-21 上午8:59:59 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-21 上午8:59:59
 * @since
 * @version
 */
public interface INonfixedCustomerDao {
    
    /**
     * 新增散客信息
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-11-21 上午8:59:59
     * @param entity
     *            散客信息实体
     * @return 1：成功；-1：失败
     * @see
     */
    int addNonfixedCustomer(NonfixedCustomerEntity entity);

    /**
     * 根据code作废散客信息
     * @author dp-xieyantao
     * @date 2012-11-20 下午6:20:51
     * @param code
     * @param modifyUser
     * @return 1：成功；-1：失败
     * @see
     */
    int deleteNonfixedCustomerByCode(String code, String modifyUser);

    /**
     * 修改散客信息
     * 
     * @author dp-xieyantao
     * @date 2012-11-21 上午8:59:59
     * @param entity
     *            散客信息实体
     * @return 1：成功；-1：失败
     * @see
     */
    int updateNonfixedCustomer(NonfixedCustomerEntity entity);
    
    /**
     * <p>根据临欠散客crmId查询散客信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-3-8 下午2:31:32
     * @param crmId ID 
     * @return
     * @see
     */
    NonfixedCustomerEntity queryEntityByCrmId(BigInteger crmId);
    
    /**
     * <p>通过传入一个客户编码查询出财务未作废散客信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-9 上午11:51:03
     * @param custCode 散客编码
     * @return
     * @see
     */
    List<NonfixedCustomerEntity> queryNoDeletedCustInfoByCode(String custCode);
    
    /**
     * <p>根据传入查询条件对象dto，查询符合条件的临欠散客信息集合list</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-31 上午10:28:20
     * @param condition
     * @return
     * @see
     */
    List<CustomerQueryConditionDto> queryCustomerByCondition(CustomerQueryConditionDto condition);
    /**
     * <p>根据传入查询条件对象dto，查询符合条件的临欠散客信息集合list(可以查询无效信息)</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-31 上午10:28:20
     * @param condition
     * @return
     * @see
     */
    List<NonfixedCustomerEntity> queryCustomerByConditionAll(CustomerQueryConditionDto condition);

	/**
	 * @param condition
	 * @return
	 */
	int countCustomerByCondition(CustomerPaginationDto condition);

	/**
	 * @param condition
	 * @return
	 */
	List<CustomerQueryConditionDto> queryCustomerByConditionByPage(
			CustomerPaginationDto condition);
	
	/**
	 * 根据查询条件获取客户信息是否存在
	 * @创建时间 2014-5-27 下午6:57:11   
	 * @创建人： WangQianJin
	 */
	int queryCustomerExistByCondition(CustomerQueryConditionDto condition);

}
