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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/validation/descriptor/PrinSignDescriptor.java
 * 
 * FILE NAME        	: PrinSignDescriptor.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.validation.descriptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.module.pickup.common.client.utils.NumberValidate;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;

/**
 * @author 105089-foss-yangtong
 *
 */
/**
 * 
 * 打印运单号检验
 * <p style="display:none">
 * modifyRecord
 * </p>
 * version:V1.0,author:Bobby,date:2012-10-11 下午1:13:22,
 * @date 2012-10-11 下午1:13:22
 * @since
 * @version
 */
public class ExpPrinSignDescriptor {
	
	/**
	 * 日志
	 */
	private static final Log LOG = LogFactory.getLog(ExpPrinSignDescriptor.class);

	/**
	 * servivce
	 */
	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager
			.getI18n(ExpPrinSignDescriptor.class);

	private static final int TEN = 10;
	/**
	 * 
	 * <p>
	 * (运单号校验)
	 * </p>
	 * 
	 * @author 105089-FOSS-yangtong
	 * @date 2012-10-17 下午03:12:22
	 * @param number
	 * @return
	 * @see
	 */
	public String validateWaybillNo(String waybillNo) {
		if (NumberValidate.checkBetweenLength(waybillNo, TEN, TEN)) {
			Boolean bool = waybillService.checkWaybillNo(waybillNo);
			if (!bool) {
				LOG.info("运单号不存在");
				return i18n.get("foss.gui.creating.waybillDescriptor.waybillNo.noExist");
			} else {
				return WaybillConstants.SUCCESS;
			}
		} else {
			return i18n.get("foss.gui.creatingexp.waybillDescriptor.waybillNo.length");
		}
	}
}