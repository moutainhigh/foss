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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/util/IntegerUtils.java
 * 
 * FILE NAME        	: IntegerUtils.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.util;

import java.math.BigDecimal;

/**
 * 
 * 整形数据处理工具类
 * 
 * @author ibm-wangxiexu
 * @date 2012-12-25 下午1:02:28
 */
public class IntegerUtils {
	/**
	 * 
	 * 判断两个整形数据是否相等且均非空
	 * @author ibm-wangxiexu
	 * @date 2012-12-25 下午1:05:30
	 */
	public static boolean equals(Integer x, Integer y) {
		if (x == null || y == null) {
			return false;
		}

		return x.equals(y);
	}
	
	/**
	 * 
	 * 返回Integer
	 * 
	 * @param s
	 * @return
	 * @author ibm-wangfei
	 * @date Mar 5, 2013 11:06:06 AM
	 */
	public static Integer toInteger(String s) {
		try {
			return Integer.valueOf(s);
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * 
	 * 返回BigDecimal
	 * 
	 * @param s
	 * @return
	 * @author ibm-wangfei
	 * @date Mar 5, 2013 11:06:06 AM
	 */
	public static BigDecimal toBigDecimal(String s) {
		try {
			return BigDecimal.valueOf(toInteger(s));
		} catch (Exception e) {
			return BigDecimal.valueOf(0);
		}
	}
}