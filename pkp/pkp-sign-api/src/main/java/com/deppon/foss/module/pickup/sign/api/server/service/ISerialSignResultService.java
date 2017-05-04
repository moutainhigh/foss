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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/service/ISignDetailService.java
 * 
 * FILE NAME        	: ISignDetailService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.service;

import java.util.Date;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SerialSignResultEntity;
/**
 * 外发流水签收service
 * @author foss-sunyanfei
 * @date 2015-10-24 上午11:47:54
 * @since
 * @version
 */
public interface ISerialSignResultService extends IService {
    
    /**
     * 根据运单号、流水号查询外发流水签收
     * @author foss-sunyanfei
     * @date 2015-10-24 下午4:36:53
     * @param serialSignResultEntity
     * @return
     */
    SerialSignResultEntity queryByConditions(SerialSignResultEntity serialSignResultEntity);
    
    /**
     * 单条添加外发流水签收信息
     * @author foss-sunyanfei
     * @date 2015-10-24 上午9:36:15
     * @param serialSignResultEntity
     * @return
     * @see
     */
    int addSerialSignResultInfo(SerialSignResultEntity serialSignResultEntity);
	
	/**
	 * 通过运单号作废当前运单的流水签收结果
	 * @author foss-sunyanfei
	 * @date 2015-10-31 下午15:44:12
	 * @param waybillNo
	 * @param modifyTime
	 * @return
	 */
	int invalidWaybillSignResult(String waybillNo,Date modifyTime);
}