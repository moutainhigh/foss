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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/ICusContactDao.java
 * 
 * FILE NAME        	: ICusContactDao.java
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

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;


/**
 * 客户联系人信息DAO接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-21 上午9:02:53, </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-21 上午9:02:53
 * @since
 * @version
 */
public interface ICusContactDao {
    
    /**
     * 新增客户联系人信息
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-11-21 上午9:02:53
     * @param entity
     *            客户联系人信息实体
     * @return 1：成功；-1：失败
     * @see
     */
    int addCusContact(ContactEntity entity);

    /**
     * 根据code作废客户联系人信息
     * @author dp-xieyantao
     * @date 2012-11-20 下午1:49:51
     * @param crmId
     * @param modifyUser
     * @return 1：成功；-1：失败
     * @see
     */
    int deleteCusContactByCode(BigDecimal crmId, String modifyUser);

    /**
     * 修改客户联系人信息
     * @author dp-xieyantao
     * @date 2012-11-21 上午9:02:53
     * @param entity
     *            客户联系人信息实体
     * @return 1：成功；-1：失败
     * @see
     */
    int updateCusContact(ContactEntity entity);
    
    /**
     * <p>根据crmId,最后一次修改时间查询客户联系人是否存在</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 上午10:15:29
     * @param crmId
     * @param lastupdatetime
     * @return
     * @see
     */
    boolean queryCusContactByCrmId(BigDecimal crmId,Date lastupdatetime);

    /**
     * 根据手机号码查询联系人信息
     *
     * auther:wangpeng_078816
     * date:2014-4-23
     *
     */
    ContactEntity queryCusContactByMobile(String mobile);
    
    /**
     * 根据手机号码或电话号码与名称查询联系人信息
     *
     * auther:WangQianJin
     * date:2014-08-10
     *
     */
    ContactEntity queryCusContactByMobileOrPhoneAndName(CustomerQueryConditionDto queryParam);
    
    /**
     * 根据CUSTFOSSID查询散客的联系人信息
     *
     * auther:css
     * date:2015-07-15 20:15
     *
     */
    ContactEntity queryCusContactByOwnCustId(String ownCustId);
    
    /**
     * 修改客户联系人信息
     * @author css
     * @date 2015-07-16 下午4:02:53
     * @param entity
     *            客户联系人信息实体
     * @return 1：成功；-1：失败
     * @see
     */
    int updateCusContactByCusfossid(ContactEntity entity);


    /**
     * 根据手机号码,固话验证联系人是否存在
     * auther:273296
     * date:2016-4-14
     *
     */
	boolean queryExistsCusContactByMobileOrTelphone(String mobilePhone,String telphone);

	/**
     * 根据crmId验证联系人是否存在
     * auther:273296
     * date:2016-4-14
     *
     */
	boolean queryExistsCusContactByCrmId(BigDecimal crmId);


}
