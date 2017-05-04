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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/exception/VehicleActualSituationException.java
 * 
 * FILE NAME        	: VehicleActualSituationException.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;


/**
 * 车辆实际情况异常
 * @author 038590-foss-wanghui
 * @date 2012-11-3 下午1:58:32
 */
public class VehicleActualSituationException extends BusinessException {

	private static final long serialVersionUID = 1213773709776707096L;

	public VehicleActualSituationException(String errCode) {
		super(errCode);
	}

	public VehicleActualSituationException(String errCode, String msg) {
		super(errCode, msg);
	}

}