package com.deppon.foss.module.settlement.dubbo.api.util;

import java.util.UUID;

/**
 * 
 * @author 郑文礼（zhengwl@cn.ibm.com）
 *
 */
public class UUIDUtils {

	/**
	 * 
	 * 通过jdk自带的uuid生成器生成36位的uuid；
	 * @author zhengwl
	 * @date 2012-10-17 上午11:43:55
	 */
	public static String getUUID() {
		// 使用JDK自带的UUID生成器
		return UUID.randomUUID().toString();
	}
}
