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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/BindingUIInit.java
 * 
 * FILE NAME        	: BindingUIInit.java
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
package com.deppon.foss.module.pickup.common.client.ui;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;

import com.deppon.foss.framework.client.commons.binding.validation.adapters.IntAdapter;
import com.deppon.foss.framework.client.commons.binding.validation.adapters.LengthAdapter;
import com.deppon.foss.framework.client.commons.binding.validation.adapters.MobileAdapter;
import com.deppon.foss.framework.client.commons.binding.validation.adapters.NotNullAdapter;
import com.deppon.foss.framework.client.commons.binding.validation.annotations.Int;
import com.deppon.foss.framework.client.commons.binding.validation.annotations.Length;
import com.deppon.foss.framework.client.commons.binding.validation.annotations.Mobile;
import com.deppon.foss.framework.client.commons.binding.validation.annotations.NotNull;
import com.deppon.foss.framework.client.commons.task.CancelledException;
import com.deppon.foss.framework.client.commons.task.ITaskContext;
import com.deppon.foss.framework.client.commons.task.TaskSupport;
import com.deppon.foss.framework.client.core.binding.BindingFactory;
import com.deppon.foss.framework.client.core.binding.TypePair;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.boot.client.autorun.IAutoRunner;
import com.deppon.foss.module.pickup.common.client.utils.StringToBigDecimalConvertorValidator;
import com.deppon.foss.module.pickup.common.client.utils.StringToDateConvertorValidator;
import com.deppon.foss.module.pickup.common.client.utils.StringToIntegerConvertorValidator;

 

/**
 * UI绑定注册类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午10:11:13, </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午10:11:13
 * @since
 * @version
 */
public class BindingUIInit extends TaskSupport implements IAutoRunner {

	
	/**
	 *绑定ui启动  后台执行任务.
	 * 非UI的工作，全部放在该方法内做，包括远程调用，数据转换，数据等，
	 * 其它，UI相关的工作，放在TaskListner中处理
	 * 
	 * @param context 任务上下文
	 * @throws CancelledException 任务被取消时抛出
	 * @throws Exception 任务出错时抛出异常
	 */
	public void execute(ITaskContext context) throws Exception {
		//非空校验
		BindingFactory.getIntsance().registerValidatorAnnotation(NotNull.class, new NotNullAdapter());
		//整型校验
		BindingFactory.getIntsance().registerValidatorAnnotation(Int.class, new IntAdapter());
		//长度校验
		BindingFactory.getIntsance().registerValidatorAnnotation(Length.class, new LengthAdapter());
		//手机号码校验
		BindingFactory.getIntsance().registerValidatorAnnotation(Mobile.class, new MobileAdapter());
		//整型转换
		BindingFactory.getIntsance().registerConverter(new TypePair(JTextFieldValidate.class, Integer.class), StringToIntegerConvertorValidator.class);
		//金额转换
		BindingFactory.getIntsance().registerConverter(new TypePair(JTextFieldValidate.class, BigDecimal.class), StringToBigDecimalConvertorValidator.class);
		//日期装换
		BindingFactory.getIntsance().registerConverter(new TypePair(JTextFieldValidate.class, Date.class), StringToDateConvertorValidator.class);
		
		registerToBeanCovert();
	}
	
	/**
	 * BeanUtils 转换器注入
	 * 20140423 MANA-2018 DEFECT-2663 
	 * @author 157229-zxy
	 * 
	 */
	public void registerToBeanCovert(){
		BigDecimalConverter bigDecimalConverter = new BigDecimalConverter(null);
		ConvertUtils.register(bigDecimalConverter, BigDecimal.class);
	}
	
}