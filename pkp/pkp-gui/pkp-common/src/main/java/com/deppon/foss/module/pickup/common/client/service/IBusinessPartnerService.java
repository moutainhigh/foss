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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/IBusinessPartnerService.java
 * 
 * FILE NAME        	: IBusinessPartnerService.java
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
package com.deppon.foss.module.pickup.common.client.service;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;


/**
 * 合作伙伴服务
 * @author 105089-foss-yangtong
 * @date 2012-11-6 下午3:11:54
 */
public interface IBusinessPartnerService {
	
	
	/**
	 * 插条记录
	 */
	void addBusinessPartner(BusinessPartnerEntity businessPartner);
	
	/**
	 * 更新条记录
	 */
	void updateBusinessPartner(BusinessPartnerEntity businessPartner);
	
	/**
	 * 新增或更新记录
	 */
	void saveOrUpdate(BusinessPartnerEntity businessPartner);
	/**
	 * 删除
	 * @param businessPartner
	 */
	void delete(BusinessPartnerEntity businessPartner);

}