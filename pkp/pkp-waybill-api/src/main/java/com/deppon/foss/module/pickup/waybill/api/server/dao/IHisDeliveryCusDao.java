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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/dao/IHisDeliveryCusDao.java
 * 
 * FILE NAME        	: IHisDeliveryCusDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
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
package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.HisDeliveryCusEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.HisCustomerDto;

/**
 * 
 * 历史发货客户数据持久层接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-31 上午11:10:00, </p>
 * @author foss-sunrui
 * @date 2012-10-31 上午11:10:00
 * @since
 * @version
 */
public interface IHisDeliveryCusDao {
    
    /**
     * 
     * <p>插入一条记录</p> 
     * @author foss-sunrui
     * @date 2012-10-31 上午11:12:19
     * @param record
     * @return
     * @see
     */
    int insertSelective(HisDeliveryCusEntity record);

    /**
     * 
     * <p>通过主键查询</p> 
     * @author foss-sunrui
     * @date 2012-10-31 上午11:12:30
     * @param id
     * @return
     * @see
     */
    HisDeliveryCusEntity selectByPrimaryKey(String id);
    
    /**
     * 
     * <p>通过条件查询</p> 
     * @author foss-sunrui
     * @date 2012-10-31 上午11:13:18
     * @param hisCustomer
     * @return
     * @see
     */
    List<HisDeliveryCusEntity> queryByCondition(HisCustomerDto hisCustomer);   

    /**
     * 
     * <p>通过主键更新</p> 
     * @author foss-sunrui
     * @date 2012-10-31 上午11:31:17
     * @param record
     * @return
     * @see
     */
    int updateByPrimaryKeySelective(HisDeliveryCusEntity record);

    /**
     * 
     * <p>根据电话找历史接货客户</p> 
     * @author foss-gengzhe
     * @date 2012-12-13 上午10:13:01
     * @param phone
     * @return
     * @see
     */
	List<HisDeliveryCusEntity> selectByPhone(String phone);
	
    /**
     * 
     * <p>根据手机找历史接货客户</p> 
     * @author foss-gengzhe
     * @date 2012-12-13 上午10:13:01
     * @param mobilephone
     * @return
     * @see
     */
	List<HisDeliveryCusEntity> selectByMobilephone(String mobilephone);
	
	/**
	 * 
	 * <p>T新增收货客户</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-13 上午10:33:29
	 * @param record
	 * @return
	 * @see
	 */
	int insert(HisDeliveryCusEntity record);

	/**
	 * 根据多个手机号码查询历史发货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	List<HisDeliveryCusEntity> queryDeliveryCustByMobileList(List<String> mobileList,Date time);
	
	/**
	 * 根据多个电话号码查询历史发货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	List<HisDeliveryCusEntity> queryDeliveryCustByPhoneList(List<String> phoneList, Date time);
}