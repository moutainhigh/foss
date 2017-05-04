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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/server/service/ISignInAndLogOutService.java
 * 
 * FILE NAME        	: ISignInAndLogOutService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.order.api.shared.domain.PdaSignEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.PdaSignDto;

/**
 * 调度解除POS签到,签到注销服务
 * @author 273279-foss-liding
 * 
 */
public interface ISignInAndLogOutSalesPartmentService extends IService {
	
	
	
	/**
	 * 解除司机签到
	 * @param record
	 * 			deviceNo
	 * 				设备号
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			createTime
	 * 				创建时间
	 * 			unbundler
	 * 				解绑人
	 * 			unbundlerCode
	 * 				解绑人编码
	 * 			unbundleReason
	 * 				解绑原因
	 * 			unbundleTime
	 * 				解绑时间
	 * 			status
	 * 				状态
	 * 			userType
	 * 				用户类型
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-19 下午4:38:38
	 */
	int handResolveBind(PdaSignEntity entity);
	
	/**
	 * 根据条件查询签到信息.
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 273279-foss-liding
	 * @date 2012-11-19 下午4:37:34
	 */
	List<PdaSignDto> querySalesPartmentSignedInfoByPage(PdaSignDto dto, int start, int limit);
	/**
	 * 
	 * 查询签到信息的条数
	 * 
	 * @author 273279-foss-liding
	 * @date 2013-4-2 下午3:19:45
	 */
	Long querySalesPartmentSignedInfoCount(PdaSignDto dto);

	/**
	 * 根据条件查询签到信息
	 * 
	 * @author 273279-foss-liding
	 */
	List<PdaSignDto> querySalesPartmentInfoByPage(PdaSignDto dto, int start,
			int limit);
	/**
	 * 
	 * 自动解除司机签到
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-3-15 上午10:15:51
	 */
	void autoUnbundle();
}