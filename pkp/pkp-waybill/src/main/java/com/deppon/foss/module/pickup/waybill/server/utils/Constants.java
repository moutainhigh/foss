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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/utils/Constants.java
 * 
 * FILE NAME        	: Constants.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.utils;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;

/**
 * 
 * 读取properties文件定义常量
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-22 上午11:23:31</p>
 * @author foss-sunrui
 * @date 2012-10-22 上午11:23:31
 * @since
 * @version
 */
public class Constants {
	

	/**
	 * 定义常量值：4 
	 * 1、不可继承 
	 * 2、避免魔法数字
	 */
	private static final int FOUR = 4;
	private static final int NUMBER_90 = 90;
	
	//丢货的时候ischanged的标识传D
	public static final String MISS_GOODS="D";
	//弃货
	public static final String DERELICT_GOODS="Q";
	//异常货
	public static final String ABNORMAL_GOODS="Y";
    
	/**
	 * 把三级产品类型转换成中转到中转线路的运输类型(专线，偏线，空运) 精准卡航 精准城运 精准汽运（长途） 精准汽运（短途） 转成 专线
	 * 汽运偏线 转成偏线
	 * 精准空运 转成空运
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-13 上午11:48:25
	 */
	public static String convertProductCodeToLineType(String productCode) {
		if (StringUtils.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT, productCode)
				|| StringUtils.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT, productCode)
				|| StringUtils.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT, productCode)
				|| StringUtils.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT, productCode)
				|| StringUtils.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PCP, productCode)) {
			return DictionaryValueConstants.BSE_LINE_TYPE_ZHUANXIAN;
		}
		if (StringUtils.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE, productCode)) {
			return DictionaryValueConstants.BSE_LINE_TYPE_PIANXIAN;
		}
		if (StringUtils.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT, productCode)) {
			return DictionaryValueConstants.BSE_LINE_TYPE_KONGYUN;
		}
		return null;
	}
	
	/**
	 * 三个月历史客户
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-31 下午3:26:34
	 */
	public static Date genLastThreeMonthDate() {
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		now.add(Calendar.DAY_OF_MONTH, - NUMBER_90);
		return now.getTime();
	}
	
	/**
	 * 将字符串的“时分”拼接到时间的“时分”上
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-19 下午7:43:44
	 */
	public static Date mergeTime(Date date, String time) {
		
		if (date == null || time == null) {
			return null;
		}
		if (time.length() != FOUR) {
			throw new IllegalArgumentException("非法的时间格式");
		}
		if (!StringUtil.isNumeric(time)) {
			throw new IllegalArgumentException("非法的时间字符");
		}

		Calendar oldCal = Calendar.getInstance();
		oldCal.setTime(date);
		oldCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(time.substring(0, 2)));
		oldCal.set(Calendar.MINUTE, Integer.valueOf(time.substring(2, FOUR)));
		// 设置秒为0
		oldCal.set(Calendar.SECOND, 0);
		// 设置毫秒为0
		oldCal.set(Calendar.MILLISECOND, 0);

		return oldCal.getTime();
	}
	
	/**
	 * 是否专线 : 1、精准卡航、 精准城运 精准汽运（长途）、 精准汽运（短途） =>
	 * 专线 
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-27 下午7:23:24
	 */
	public static boolean isSpecialLineProduct(String productCode) {
		// 根据三级产品判断是否是汽运
		if (StringUtils.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT, productCode)
				|| StringUtils.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT, productCode)
				|| StringUtils.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT, productCode)
				|| StringUtils.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT, productCode)
				|| StringUtils.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PCP, productCode)) {
			return true;
		}else{
			return false;
		}
	}
	public static String GRAY_VESTSYSTEM_CODE_FOSS="FOSS";
	public static String GRAY_VESTSYSTEM_CODE_FOSS_JSH="FOSS-JSH";
	public static String GRAY_VESTSYSTEM_CODE_CUBC="CUBC";
	public static String GRAY_SOURCE_BILLTYPE_W = "W";
	public static String GRAY_SOURCE_BILLTYPE_CU = "CU";
	public static String GRAY_SOURCE_BILLTYPE_DP = "DP";
	public static String GRAY_SOURCE_BILLTYPE_PTP = "PTP";
	public static String GRAY_SERVICE_EXCEPTION = "调用灰度接口出现异常，请稍后重试";

}