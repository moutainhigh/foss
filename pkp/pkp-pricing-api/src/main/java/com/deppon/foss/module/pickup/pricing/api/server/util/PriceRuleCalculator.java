/**
 *  initial comments.
 */
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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/util/PriceRuleCalculator.java
 * 
 * FILE NAME        	: PriceRuleCalculator.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.TargetError;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.CaculateFeeConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.CaculateFeeDto;

/**
 * 
 * 价格计算服务类
 * 
 * <p style="display:none">modifyRecord</p>
 * 
 * <p style="display:none">version:V1.0,author:zhangdongping,date:2012-11-9 上午11:50:35,content:  </p>
 * 
 * @author zhangdongping
 * 
 * @date 2012-11-9 上午11:50:35
 * 
 * @since
 * 
 * @version
 * 
 */
public class PriceRuleCalculator {
	/**
	 * 常量设置
	 */
    public static final  String PRICE_RULE_RESULT_STRING = "res";
    /**
     * 常量设置
     */
    public static final  String CARRY_TYPE_ROUND = "1"; // 四舍五入
    public static final  String CARRY_TYPE_REDUCE = "2"; // 去位取整
    public static final int ONE_HUNDRED = 100;//整数常量-100
    /**
     * 日志
     */
	protected final static Logger logger = LoggerFactory.getLogger(PriceRuleCalculator.class);
    /**
     * 
     * @Description: 根据传入的重量(或体积)和价格明细，计算出来总价
     * 
     * @author FOSSDP-Administrator
     * 
     * @date 2013-3-19 上午10:45:49
     * 
     * @param parm
     * 
     * @param freight
     * 
     * @return
     * 
     * @version V1.0
     * 
     */
	public static Long calcFreightDetail(Double parm, CaculateFeeDto freight) {
		if (null == freight || null == parm) {
			return 0l;
		}
		Long result = null;
		if (freight.getMinFee() == null) {
			freight.setMinFee(0l);
		}
		if (freight.getMaxFee() == null) {
			freight.setMaxFee(PricingConstants.CRITERIA_DETAIL_ORIGINALCOST_MAX);
		}
		// 根据条件判断如何调用execute方法
		// 计算重量，根据进位类型判断
		double calWeight = parm.doubleValue(); 
		String script = ""; // 价格公式
		// 参数表
		Map<String, Object> placeholder = new HashMap<String, Object>(); 
		String expression = freight.getExpression();
		if (StringUtil.isNotBlank(expression)) {
			script = expression;
			setPlaceHolder(freight, calWeight, placeholder); 
			Double retResult = execute(script, placeholder);
			logger.trace("calcFreightDetail retResult: " + retResult);
			// 费用进位方式
			if (StringUtil.isEmpty(freight.getCarryType()) || StringUtil.equals(CARRY_TYPE_ROUND, freight.getCarryType().trim())) {
				// 四舍五入
				result = (long) Math.round(retResult * ONE_HUNDRED) / ONE_HUNDRED;
				// 去位取整
			} else if (StringUtil.equals(CARRY_TYPE_REDUCE, freight.getCarryType().trim())) { 
				result = (long) Math.floor(retResult * ONE_HUNDRED) / ONE_HUNDRED;
			}
			if (result != null && result > freight.getMaxFee()) {
				return freight.getMaxFee();
			}
			if (result != null && result < freight.getMinFee()) {
				return freight.getMinFee();
			}
			return result;
			 // 特殊情况
		} else {
			return freight.getFixPrice();
		}
	}
	
	 /**
     * 
     * @Description: 根据传入的重量(或体积)和价格明细，计算出来总价--专属按个计算
     * 
     * @param parm
     * 
     * @param freight
     * 
     * @return
     * 
     * @version V1.0
     * 
     */
	public static Long calcFreightDetailForSingleton(Double parm, CaculateFeeDto freight) {
		if (null == freight || null == parm) {
			return 0l;
		}
		Long result = null;
		if (freight.getMinFee() == null) {
			freight.setMinFee(0l);
		}
		if (freight.getMaxFee() == null) {
			freight.setMaxFee(PricingConstants.CRITERIA_DETAIL_ORIGINALCOST_MAX);
		}
		// 根据条件判断如何调用execute方法
		// 计算重量，根据进位类型判断
		double calWeight = parm.doubleValue(); 
		String script = ""; // 价格公式
		// 参数表
		Map<String, Object> placeholder = new HashMap<String, Object>(); 
		String expression = freight.getExpression();
		if (StringUtil.isNotBlank(expression)) {
			script = expression;
			setPlaceHolder(freight, calWeight, placeholder); 
			Double retResult = execute(script, placeholder);
			logger.trace("calcFreightDetail retResult: " + retResult);
			// 费用进位方式
			if (StringUtil.isEmpty(freight.getCarryType()) || StringUtil.equals(CARRY_TYPE_ROUND, freight.getCarryType().trim())) {
				// 四舍五入
				result = (long) Math.round(retResult * ONE_HUNDRED) / ONE_HUNDRED;
				// 去位取整
			} else if (StringUtil.equals(CARRY_TYPE_REDUCE, freight.getCarryType().trim())) { 
				result = (long) Math.floor(retResult * ONE_HUNDRED) / ONE_HUNDRED;
			}
			if (result != null && result > freight.getMaxFee() * parm) {
				return freight.getMaxFee() * Math.round(parm);
			}
			if (result != null && result < freight.getMinFee() * parm) {
				return freight.getMinFee()  * Math.round(parm);
			}
			return result;
			 // 特殊情况
		} else {
			return freight.getFixPrice();
		}
	}

	//设置参数列表数据值
	private static void setPlaceHolder(CaculateFeeDto freight,
			double calWeight, Map<String, Object> placeholder) {
		// 总重,总体积，总费用
		placeholder.put(CaculateFeeConstants.PRICERULE_EXPRESSION_WEIGHT, calWeight); 
		// 固定价
		placeholder.put(CaculateFeeConstants.PRICERULE_EXPRESSION_FIXED, (null == freight.getFixPrice()) ? 0.0 : freight.getFixPrice().longValue()); 
		// 单价or费率
		placeholder.put(CaculateFeeConstants.PRICERULE_EXPRESSION_WEIGHT_PRICE, (null == freight.getFeeRate()) ? 0.0 : freight.getFeeRate()); 
		// 首重（体积）																																		 
		placeholder.put(CaculateFeeConstants.PRICERULE_EXPRESSION_FIRST_WEIGHT, (null == freight.getFirstWeight()) ? 0.0 : freight.getFirstWeight()
				.doubleValue()); 
		//首重（体积）价格or 首重（体积）单价
		placeholder.put(CaculateFeeConstants.PRICERULE_EXPRESSION_FIRST_WEIGHT_PRICE, (null == freight.getFirstWeightPrice()) ? 0.0 : freight
				.getFirstWeightPrice().doubleValue());
		// 续重（体积）步进单位
		placeholder.put(CaculateFeeConstants.PRICERULE_EXPRESSION_ADD_WEIGHT, (null == freight.getAddWeightStep()) ? 1.0 : freight.getAddWeightStep()
				.doubleValue()); 
		//续重（体积）单价
		placeholder.put(CaculateFeeConstants.PRICERULE_EXPRESSION_ADD_WEIGHT_PRICE, (null == freight.getAddWeightPrice()) ? 0.0 : freight
				.getAddWeightPrice().doubleValue()); 
		// 续重（体积）步进单价
		// 折扣率
		placeholder.put(CaculateFeeConstants.PRICERULE_EXPRESSION_DISCOUNT, (null == freight.getDiscountRate()) ? 1 : freight.getDiscountRate()
				.doubleValue());
	}
	
    /**
     * 
     * @Description: 将价格公式解释的模板转化为实际情形下的解释
     * 
     * @author FOSSDP-Administrator
     * 
     * @date 2013-3-19 上午10:42:08
     * 
     * @param template
     * 
     * @param placeholder
     * 
     * @return
     * 
     * @version V1.0
     */
	public static String parseDetail(String template, Map<String, Object> placeholder) {
		if (StringUtil.isEmpty(template)) {
			return "";
		}
		String detail = template;
		if (null != placeholder && placeholder.size() > 0) {
			Set<Entry<String, Object>> keyset = placeholder.entrySet();
			for (Entry<String, Object> key : keyset) {
				String value = (null == key.getValue()) ? key.getKey() : key.getValue().toString();
				detail = detail.replaceAll(key.getKey(), value);
			}
		}
		return detail;
	}
    /**
     * 
     * @Description: 价格计算
     * 
     * @author FOSSDP-Administrator
     * 
     * @date 2013-3-19 上午10:42:26
     * 
     * @param script
     * 
     * @param placeholder
     * 
     * @return
     * 
     * @version V1.0
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
				return Double.valueOf((double) Math.round((result + 2e-15) * ONE_HUNDRED) / ONE_HUNDRED);
			}
		} catch (TargetError targetError) {
			throw new BeanShellException("Could not execute BeanShell script", targetError);
		} catch (EvalError evalError) {
			throw new BeanShellException("Could not execute BeanShell script", evalError);
		} finally {
			BeanShellUtil.freeInterpreter(i);
		}
	}
}

