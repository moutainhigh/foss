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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/dao/IHisReceiveCusDao.java
 * 
 * FILE NAME        	: IHisReceiveCusDao.java
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

import com.deppon.foss.module.pickup.waybill.shared.domain.HisReceiveCusEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.HisCustomerDto;

/**
 * 
 * 历史收货客户数据持久层接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-31 上午11:10:22, </p>
 * @author foss-sunrui
 * @date 2012-10-31 上午11:10:22
 * @since
 * @version
 */
public interface IHisReceiveCusDao {
    
    /**
     * 
     * <p>插入一条记录</p> 
     * @author foss-sunrui
     * @date 2012-10-31 上午11:31:36
     * @param record
     * @return
     * @see
     */
    int insertSelective(HisReceiveCusEntity record);

    /**
     * 
     * <p>通过主键查询</p> 
     * @author foss-sunrui
     * @date 2012-10-31 上午11:31:48
     * @param id
     * @return
     * @see
     */
    HisReceiveCusEntity queryByPrimaryKey(String id);
    
    /**
     * 
     * <p>通过条件查询</p> 
     * @author foss-sunrui
     * @date 2012-10-31 上午11:32:01
     * @param hisCustomer
     * @return
     * @see
     */
    List<HisReceiveCusEntity> queryByCondition(HisCustomerDto hisCustomer);
    
    /**
     * 
     * <p>根据主键更新记录</p> 
     * @author foss-sunrui
     * @date 2012-10-31 上午11:34:48
     * @param record
     * @return
     * @see
     */
    int updateByPrimaryKeySelective(HisReceiveCusEntity record);
    

    /**
     * 
     * <p>根据电话找历史收货客户</p> 
     * @author foss-gengzhe
     * @date 2012-12-13 上午10:37:19
     * @param phone
     * @return
     * @see
     */
	List<HisReceiveCusEntity> selectByPhone(String phone);
	
	/**
	 * 
	 * <p>根据手机找历史收货客户</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-13 上午10:37:38
	 * @param mobilephone
	 * @return
	 * @see
	 */
	List<HisReceiveCusEntity> selectByMobilephone(String mobilephone);
	
	/**
	 * 
	 * <p>新增收货客户</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-13 上午10:38:02
	 * @param record
	 * @return
	 * @see
	 */
	int insert(HisReceiveCusEntity record);

	/**
	 * 根据多个手机号码查询最近三个月的历史收货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	List<HisReceiveCusEntity> queryReceiveCustByMobileList(List<String> mobileList, Date time);

	/**
	 * 根据多个电话号码查询最近三个月的历史收货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	List<HisReceiveCusEntity> queryReceiveCustByPhoneList(List<String> phoneList, Date time);

}