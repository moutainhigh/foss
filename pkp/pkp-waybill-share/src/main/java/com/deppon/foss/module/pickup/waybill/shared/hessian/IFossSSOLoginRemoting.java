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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/hessian/IFossSSOLoginRemoting.java
 * 
 * FILE NAME        	: IFossSSOLoginRemoting.java
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
package com.deppon.foss.module.pickup.waybill.shared.hessian;

import com.deppon.foss.framework.service.IHessianService;

/**
 * 
 * 单点登录Remoting
 * @author 102246-foss-shaohongliang
 * @date 2012-12-1 上午10:54:09
 */
public interface IFossSSOLoginRemoting extends IHessianService {
	
	/**
	 * 通过目标应用服务的ID与待访问的页面信息，得到一个单点登录的URL，
	 * getUrl
	 * @param destAppID 目标应用服务的ID
	 * @param homePage  待访问的页面信息
	 * @return 一个单点登录的URL
	 * @return String
	 * @since: 0.6
	 */
	String getUrl(String destAppID, String homePage);
}