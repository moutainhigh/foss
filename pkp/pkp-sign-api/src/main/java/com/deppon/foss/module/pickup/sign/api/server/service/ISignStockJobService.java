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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/service/IAirAgencySignService.java
 * 
 * FILE NAME        	: IAirAgencySignService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignStockEntity;

/**
 * 签收反签收同步改异步接口
 * @author foss-meiying
 * @date 2012-10-16 下午2:59:47
 * @since
 * @version
 */
public interface ISignStockJobService extends IService {
	/**
	 * 调用中转入库接口，走货路径
	 * @author foss-meiying
	 * @date 2013-3-21 下午5:54:16
	 * @param signStock
	 * @see
	 */
	 void inStock(SignStockEntity signStock);
	 /**
	  * 调用中转出库接口
	  * @author foss-meiying
	  * @date 2013-3-21 下午5:54:16
	  * @param signStock
	  * @see
	  */
	 void outStock(SignStockEntity signStock);
	 
	 /**
     * 添加一条记录
     * @author foss-meiying
     * @date 2013-3-22 下午3:46:55
     * @param record
     * @return
     * @see
     */
    int add(SignStockEntity record);
    /**
     * 有选择性的添加数据
     * @author foss-meiying
     * @date 2013-3-22 下午3:53:11
     * @param record
     * @return
     * @see
     */
    int addSelective(SignStockEntity record);
}