/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/exception/HandOverBillExceptionCode.java
 *  
 *  FILE NAME          :HandOverBillExceptionCode.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.exception;
/** 
 * @className: HandOverBillExceptionCode
 * @author: ShiWei shiwei@outlook.com
 * @description: 交接单异常code类
 * @date: 2012-10-26 下午3:50:24
 * 
 */
public class HandOverBillExceptionCode {
		//交接单已出发，无法作废
		public static final String HANDOVERBILL_ALREADY_LEAVE = "交接单已出发，无法作废";
		//交接单已被配载，无法作废
		public static final String HANDOVERBILL_ALREADY_ASSEMBLIED = "交接单已被配载，无法作废，配载单号：";
		
		private HandOverBillExceptionCode() {
			
		}
}