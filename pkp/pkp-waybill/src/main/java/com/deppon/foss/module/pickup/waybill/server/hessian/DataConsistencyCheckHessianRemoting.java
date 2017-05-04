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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/hessian/FossSSOLoginRemoting.java
 * 
 * FILE NAME        	: FossSSOLoginRemoting.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.hessian;

import java.util.List;

import javax.annotation.Resource;

import com.deppon.foss.framework.server.deploy.hessian.annotation.Remote;
import com.deppon.foss.module.pickup.waybill.api.server.service.IDataConsistencyService;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IDataConsistencyCheckHessianRemoting;
import com.deppon.foss.module.pickup.waybill.shared.vo.DataConsistencyVo;

/**
 * 
 * 单点登录生成Token Remoting
 * @author 102246-foss-shaohongliang
 * @date 2012-12-1 上午10:54:09
 */

@Remote
public class DataConsistencyCheckHessianRemoting implements IDataConsistencyCheckHessianRemoting {

	@Resource
	IDataConsistencyService dataConsistencyService;
	
	//@Resource
	//public void setDateConsistencyService(IDateConsistencyService dateConsistencyService){
	//	this.dateConsistencyService=dateConsistencyService;
	//}
	
	/**
	 * 通过传入的表名返回各个表的记录数
	 * getUrl
	 * @return 各个表的记录数
	 * @return List<Integer>
	 */
	@Override
	public List<DataConsistencyVo> countQueryTableDate(List<DataConsistencyVo> tableName,String userCode) {
		return dataConsistencyService.countQueryTableDate(tableName,userCode);
	}
}