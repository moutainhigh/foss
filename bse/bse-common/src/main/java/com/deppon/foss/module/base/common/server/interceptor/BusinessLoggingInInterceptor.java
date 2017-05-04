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
 * PROJECT NAME	: bse-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/interceptor/BusinessLoggingInInterceptor.java
 * 
 * FILE NAME        	: BusinessLoggingInInterceptor.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-common
 * PACKAGE NAME: com.deppon.foss.module.base.common.server.interceptor
 * FILE    NAME: BusinessLoggingInterceptor.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.common.server.interceptor;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.frontend.MethodDispatcher;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.cache.CacheUtils;
import com.deppon.foss.framework.server.components.logger.LogBuffer;
import com.deppon.foss.framework.server.components.logger.entity.LogInfo;
import com.deppon.foss.framework.server.components.logger.entity.LogType;
import com.deppon.foss.framework.server.context.AppContext;
import com.deppon.foss.framework.server.context.RequestContext;

/**
 * CXF业务日志拦截器
 * 
 * @author ibm-zhuwei
 * @date 2013-3-7 下午3:44:54
 */
public class BusinessLoggingInInterceptor extends AbstractSoapInterceptor {

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BusinessLoggingInInterceptor.class);

	/**
	 * 默认构造函数
	 * @author ibm-zhuwei
	 * @date 2013-3-14 上午10:51:18
	 */
	public BusinessLoggingInInterceptor() {
		// 拦截阶段
		super(Phase.USER_LOGICAL);
	}

	/**
	 * 监控日志
	 */
	@Resource(name = "performanceLog")
	private LogBuffer performanceLog;

	/**
	 * 拦截方法
	 * 
	 * @author ibm-zhuwei
	 * @date 2013-3-7 下午3:54:37
	 * @param message
	 * @throws Fault
	 * @see org.apache.cxf.interceptor.Interceptor#handleMessage(org.apache.cxf.message.Message)
	 */
	@Override
	public void handleMessage(SoapMessage message) throws Fault {

		try {
			// 获取CXF请求信息
			URI uri = (URI) message
					.get("javax.xml.ws.wsdl.description");

			// 获取CXF头信息
			Exchange exchange = message.getExchange();
			BindingOperationInfo bop = exchange.get(BindingOperationInfo.class);
			MethodDispatcher md = (MethodDispatcher) exchange
					.get(Service.class).get(MethodDispatcher.class.getName());
			Method method = md.getMethod(bop);	// 获取执行方法

			// 获取输入参数
			MessageContentsList inObjects = MessageContentsList
					.getContentsList(message);

			// 设置当前线程CONTEXT
			RequestContext.setCurrentContext(method.getName(), uri.toString(), uri.getHost());

			// 获取当前线程CONTEXT
			RequestContext context = RequestContext.getCurrentContext();
			
			// 设置LogInfo
			LogInfo info = new LogInfo();

			info.setIp(context.getIp());
			info.setUserName(null);	// 接口无用户
			info.setDate(new Date());
			info.setUrl(context.getRemoteRequestURL());
			info.setAction(LogInfo.BEGIN_ACTION);	// 接口入口
			info.setMethod(method.getName());
			info.setClazz(method.getDeclaringClass().getName());
			info.setAppName(AppContext.getAppContext().getContextPath());
			info.setType(LogType.INTERFACE);
			info.setRequestId(context.getRequestId());
			// 输入参数
			info.setArgs(CacheUtils.toJsonString(inObjects, true));

			// 写入mongodb
			performanceLog.write(info);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

}
