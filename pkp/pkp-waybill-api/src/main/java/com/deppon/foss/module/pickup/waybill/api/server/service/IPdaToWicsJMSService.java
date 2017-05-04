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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/service/ICrmOrderJMSService.java
 * 
 * FILE NAME        	: ICrmOrderJMSService.java
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
package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.TimeLookDto;

/**
 * PDA开单后给WICS系统的关于到达外场的时效
 * @date 2015-08-12 下午5:23:50
 * @since
 * @version
 */
public interface IPdaToWicsJMSService extends IService {

    /**
     * @author 220125
     * 传值至ESB的执行方法
     * @date 下午5:24:31
     * @param request
     * @return
     * @see
     */
	public  void sendInFomationToWics(TimeLookDto timeLookDto);
	/**
	 * 补录生成运单JOB
	 * @author:220125 Yangxiaolong
	 * @date 2015-9-8 16:47:58
	 * @throws Exception
	 */
	public void batchTimeSetExpressJobs();
	
	
	
   public void sendInFomationToWics(List<TimeLookDto> list);
	
	
	
    
}