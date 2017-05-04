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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/dao/IAbandonGoodsProofDao.java
 * 
 * FILE NAME        	: IAbandonGoodsProofDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveDeliverDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveDeliverQueryDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveDeliverTotalDto;


/**
 * 到达派送dao
 * @author ibm-meiying
 * @date 2013-06-25 上午11:02:07
 */
public interface IArriveDeliverDao {
	
	

    /**
   	 * 查询到达派送信息
   	 * * @author ibm-meiying
   	 * @date 2013-06-25 上午11:04:08
   	 */
	List<ArriveDeliverDto> queryArriveDeliverByParams(ArriveDeliverQueryDto dto ,int start, int limit);
	/**
   	 * 查询到达派送总计信息
   	 * * @author ibm-meiying
   	 * @date 2013-06-25 上午11:02:08
   	 */
	ArriveDeliverTotalDto queryArriveDeliverTotalByParams(ArriveDeliverQueryDto dto);
	 /**
	 * 根据条件查询到达派送信息合计
	 * 
	 * @author foss-meiying
	 * @date 2013-6-25 下午4:59:27
	 * @param dto
	 * @return
	 */
	 Long queryArriveDeliverCountByParams(ArriveDeliverQueryDto dto);
}