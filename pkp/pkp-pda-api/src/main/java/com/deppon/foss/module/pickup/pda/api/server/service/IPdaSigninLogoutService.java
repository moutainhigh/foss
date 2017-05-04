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
 * PROJECT NAME	: pkp-pda-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pda/api/server/service/IPdaSigninLogoutService.java
 * 
 * FILE NAME        	: IPdaSigninLogoutService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pda.api.server.service;

import java.util.Date;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaSigninDto;

/**
 * 提供PDA签到(长短途）与注销的功能。
 * @author 097972-foss-dengtingting
 * 
 */
public interface IPdaSigninLogoutService extends IService {
	
	/**
	 * PDA签到接口
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-27 上午9:11:56
	 * @param deviceNo 设备号
	 * @param driverCode 司机工号
	 * @param vehicleNo 车牌号
	 * @param signTime 签到时间
	 * @param userType 用户类型
	 */
	void signIn(PdaSigninDto pdaSigninDto);
	
	/**
	 * PDA注销接口
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-27 下午2:56:10
	 * @param deviceNo 设备号
	 * @param driverCode 司机工号
	 * @param vehicleNo 车牌号
	 * @param unbundleTime 注销时间
	 * mod by liding for NCI
	 * 增加用户类型参数
	 */
	void loginOut(String deviceNo,String driverCode,String vehicleNo,String userType,Date unbundleTime);
}