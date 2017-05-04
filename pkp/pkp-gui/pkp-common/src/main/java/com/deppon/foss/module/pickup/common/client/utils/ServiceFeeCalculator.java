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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/utils/ServiceFeeCalculator.java
 * 
 * FILE NAME        	: ServiceFeeCalculator.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.utils;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.TargetError;

/**
 * 价格计算服务类
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:zhangdongping,date:2012-11-9 上午11:50:35,
 * </p>
 * 
 * @author zhangdongping
 * @date 2012-11-9 上午11:50:35
 * @since
 * @version
 */
public class ServiceFeeCalculator {

	//100
	private static final int HUNDRED = 100;
	
	//2e-15
	private static final double ROUNDNUMBER = 2e-15;
	public static final String PRICE_RULE_RESULT_STRING = "res";

	/**
	 * 根据传入的重量(或体积)和价格明细，计算出来总价
	 * 
	 * @param parm
	 * @param freight
	 * @return
	 */
	public static Double calculate(String expression,
			Map<String, Object> placeholder) {
		// 返回之前不用的本地变量
		return execute(expression, placeholder);
	}

	/**
	 * 将价格公式解释的模板转化为实际情形下的解释
	 * 
	 * @param template
	 * @param placeholder
	 * @return
	 */
	public static String parseDetail(String template,
			Map<String, Object> placeholder) {

		if (null == template || "".equals(template.trim())) {
			return "";
		}
		String detail = template;
		if (null != placeholder && placeholder.size() > 0) {
			Set<Entry<String, Object>> entrySet = placeholder.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String value = (null == placeholder.get(entry.getKey())) ? entry
						.getKey() : placeholder.get(entry.getKey()).toString();
				detail = detail.replaceAll(entry.getKey(), value);
			}
		}
		return detail;
	}

	/**
	 * 价格计算
	 * 
	 * @param script
	 * @param placeholder
	 * @return
	 */
	public static Double execute(String script, Map<String, Object> placeholder) {
		Interpreter i = BeanShellUtil.getInterpreter();
		try {

			if (null != placeholder && !placeholder.isEmpty()) {
				Set<Entry<String, Object>> es = placeholder.entrySet();
				for (Entry<String, Object> e : es) {
					i.set(e.getKey(), e.getValue());
				}
			}

			i.eval(script);
			Double result = (Double) i.get(PRICE_RULE_RESULT_STRING);
			if (null == result) {
				throw new BeanShellException("Return result is null");
			} else {
				/**
				 * 由于beanshell中，两个double相乘，会引起精度的缺失，即使使用BigDecimal也不能解决
				 * 比如：9.7*0.95 = 9.214999999999998 所以四舍五入时变成9.21而不是9.22
				 * 一个简单解决方法就是round时，加上2e-15作为精度缺失的补充
				 */
				return Double.valueOf(Math.round((result + ROUNDNUMBER) * HUNDRED) / HUNDRED);
			}
		} catch (TargetError targetError) {
			throw new BeanShellException("Could not execute BeanShell script",
					targetError);
		} catch (EvalError evalError) {
			throw new BeanShellException("Could not execute BeanShell script",
					evalError);
		} finally {
			BeanShellUtil.freeInterpreter(i);
		}
	}

}