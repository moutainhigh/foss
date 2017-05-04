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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/util/TemplateUtil.java
 * 
 * FILE NAME        	: TemplateUtil.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.util;

import java.util.Map;
/**
 * 
 * 在线通知模板工具类
 * @author foss-meiying
 * @date 2012-12-22 下午6:15:53
 * @since
 * @version
 */
public class TemplateUtil {
	private static final String PREFIX = "$";
	/**
	 * 替换内容，返回在线通知内容
	 * @author foss-meiying
	 * @date 2012-12-26 下午8:56:58
	 * @param map
	 * @return
	 * @see
	 */
	public static String getSignNotice(Map<String, String> map) {
		String signNoticeTemp = "货物单号 :$waybillNo$的货物于$signTime$已签收，签收件数 :$signGoodsQty$件，签收人:$deliverymanName$， 该货物$isException$异常。";
		String content = "";
		for (Map.Entry<String, String> m : map.entrySet()) {
			String code = PREFIX + m.getKey() + PREFIX;
			content = signNoticeTemp.replace(code, m.getValue() == null ? "" : m.getValue());
			signNoticeTemp = content;
		}
		return content;
	}
}