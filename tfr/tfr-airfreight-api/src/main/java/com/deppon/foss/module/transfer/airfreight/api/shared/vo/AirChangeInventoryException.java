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
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/api/shared/vo/AirChangeInventoryException.java
 *  
 *  FILE NAME          :AirChangeInventoryException.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.vo;

import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;

/**
 * 变更合大票异常类
 * @author 099197-foss-zhoudejun
 * @date 2012-12-15 下午2:34:18
 */
public class AirChangeInventoryException extends TfrBusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8579117843531032697L;

	/**
	 * 调用结算接口失败
	 */
	public static final String AIRFREIGHT_STL_ERROR = "foss.airfreight.exception.sltAirChangeInventoryException";
	
	/**
	 * 
	 */
	public AirChangeInventoryException (){
		super();
	}
	
	/**
	 * 
	 *
	 * @param errorCode 
	 */
	public AirChangeInventoryException(String errorCode) {
		this.errCode = errorCode;
	}
	
}