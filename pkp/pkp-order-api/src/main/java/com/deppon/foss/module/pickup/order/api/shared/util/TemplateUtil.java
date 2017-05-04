/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 038590-foss-wanghui
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
 * PROJECT NAME	: pkp-order-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/util/TemplateUtil.java
 * 
 * FILE NAME        	: TemplateUtil.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.util;

import java.util.Map;

/**
 * 在线通知模板工具类
 * 
 * @author 038590-foss-wanghui
 * @date 2012-12-21 下午6:08:11
 */
public class TemplateUtil {

	public static final String ORDER_NOTICE_TEMP = "$salesDepartment$:订单编号为$orderNo$的订单由司机$driverName$(电话)车牌号$vehicleNo$承接受理时间$operateTime$受理人：$operator$受理部门$dept$";

	private static final String PREFIX = "$";

	/**
	 * 
	 * 替换内容，返回在线通知内容
	 * 
	 * @param
	 * @author 038590-foss-wanghui
	 * @date 2012-12-21 下午6:10:05
	 */
	public static String getOrderNotice(Map<String, String> map) {
		String content = "";
		String template = ORDER_NOTICE_TEMP;
		for (Map.Entry<String, String> m : map.entrySet()) {
			String code = PREFIX + m.getKey() + PREFIX;
			content = template.replace(code, m.getValue() == null ? "" : m.getValue());
			template = content;
		}
		return content;
	}
}