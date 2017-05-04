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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/service/IAbandonGoodsApplicationService.java
 * 
 * FILE NAME        	: IAbandonGoodsApplicationService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.service;

import java.io.InputStream;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveDeliverQueryDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.ArriveDeliverVo;


/**
 * @Description: 到达派送管理查询系统 
 * @author ibm-meiying
 * @date 2013-6-24 上午10:46:37
 * 
 */
public interface IArriveDeliverManagerService extends IService {
	/**
	 * 根据条件查询到达派送信息,合计信息
	 * @param dto
	 * @return
	 */
	 ArriveDeliverVo queryArriveDeliverByParams(ArriveDeliverQueryDto dto,int start,int limit);
	/**
	 * 导出到达派送信息
	 * @param dto
	 * @return
	 */
	 InputStream exportArriveDeliverInfo(ArriveDeliverQueryDto dto,int start, int limit);
	 /**
	 * 根据条件查询到达派送信息合计
	 * 
	 * @author foss-meiying
	 * @date 2013-6-25 下午4:59:27
	 * @param dto
	 * @return
	 */
	 Long queryArriveDeliverCountByParams(ArriveDeliverQueryDto dto);
	 
	 /**
	  * 加工查询条件，包括加入到达库存部门编码、名称、库区号
	  * @param dto 原始查询条件
	  * @return 加工后的查询条件
	  */
	 ArriveDeliverQueryDto refreshArriveDeliverQueryDto(ArriveDeliverQueryDto dto);
}