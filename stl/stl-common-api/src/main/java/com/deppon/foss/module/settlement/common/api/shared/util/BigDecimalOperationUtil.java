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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/util/BigDecimalOperationUtil.java
 * 
 * FILE NAME        	: BigDecimalOperationUtil.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.settlement.common.api.shared.util;

import java.math.BigDecimal;

/**
 * BigDecimal 操作类.
 * @218392 zhangyongxue
 * @date 2016-06-17 01:09:15 AM
 */
public class BigDecimalOperationUtil {
	// 默认除法运算精度
	/** The Constant DEF_DIV_SCALE. */
	private static final int DEF_DIV_SCALE = 10;

	// 这个类不能实例化
	/**
	 * Instantiates a new big decimal operation util.
	 */
	private BigDecimalOperationUtil() {
	}

	/**
	 * 提供精确的乘法运算。.
	 *
	 * @param b1 the b1
	 * @param b2 the b2
	 * @param scale the scale
	 * @return 两个参数的积
	 */
	public static BigDecimal mul(BigDecimal b1, BigDecimal b2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The   scale   must   be   a   positive   integer   or   zero");
		}
		if (b1 ==  null || b2 == null) {
			return BigDecimal.valueOf(0);
		}
		return round(b1.multiply(b2), scale);
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。.
	 *
	 * @param b1 the b1
	 * @param b2 the b2
	 * @return 两个参数的商
	 * @since
	 * @version
	 */

	public static BigDecimal div(BigDecimal b1, BigDecimal b2) {
		return div(b1, b2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。.
	 *
	 * @param b1 the b1
	 * @param b2 the b2
	 * @param scale 表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 * @since
	 * @version
	 */

	public static BigDecimal div(BigDecimal b1, BigDecimal b2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The   scale   must   be   a   positive   integer   or   zero");
		}
		if (b1 ==  null || b2 == null) {
			return BigDecimal.valueOf(0);
		}
		if (b2.intValue() == 0) {
			return BigDecimal.valueOf(0);
		}
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
	}

	
	
	/**
	 * 提供精确的小数位四舍五入处理。.
	 *
	 * @param b the b
	 * @param scale 小数点后保留几位
	 * @return 四舍五入后的结果
	 * @since
	 * @version
	 */

	public static BigDecimal round(BigDecimal b, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal one = BigDecimal.valueOf(1);
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 两个BigDecimal类型进行比较.
	 *
	 * @param b1 the b1
	 * @param b2 the b2
	 * @return 相等true  有差异 false
	 * @since
	 * @version
	 */
	public static boolean compare(BigDecimal b1, BigDecimal b2) {
		boolean b = true;
		if (b1.compareTo(b2) != 0) {
			b = false;
		}
		return b;
	}
	
	/**
	 * 
	 * 两个BigDecimal类型进行减法处理,如果b1或b2为空，则返回0
	 * 
	 * @author 218392 张永雪
	 * @date 2016-06-17 01:10:20
	 */
	public static BigDecimal subtract(BigDecimal b1, BigDecimal b2){
		if(b1 == null || b2 == null){
			return BigDecimal.ZERO;
		} else {
			return b1.subtract(b2);
		}
	}
}