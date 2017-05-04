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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/dao/IDeliverbillDetailDao.java
 * 
 * FILE NAME        	: IDeliverbillDetailDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverTaskConditionDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverTaskDetailsDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverTaskDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillNewDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDeliveryDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillToArrangeDto;


/**
 * 
 * 派送单明细DAO接口
 * 
 * @author 
 * @date
 */
public interface IDeliverbillNewDetailDao {
	
	List<DeliverbillDetailEntity> queryByDeliverbillIdForPrint(Map<Object,Object> map,int start, int limit);
	
	List<DeliverbillDetailEntity> queryByDeliverbillId(Map<Object,Object> map,int start, int limit);
	
	
	/**
	 * 根据派送单集合查询派送单明细
	 * @author ]
	 * @date ]
	 * @param 
	 * @return
	 * @see
	 */
	List<DeliverbillDetailDto> queryByDeliverbillNos(DeliverbillNewDto deliverbillNewDto);
	
	
	/**
	 * 根据派送单id查询派送单明细
	 * @param id
	 * @return
	 */
	List<DeliverbillDetailEntity> queryDeliverbillDetailEntityById(String id);
	
	
	/**
	 * 
	 * 根据派送单ID查找已生成到达联的派送明细列表
	 * 
	 * @param deliverbillDetailDto
	 *            包含派送单ID的查询条件
	 * @return 已生成到达联的派送明细列表
	 * @author 
	 * @date 
	 */
	List<DeliverbillDetailEntity> queryArrivesheetListByDeliverbillId(
			DeliverbillDetailDto deliverbillDetailDto);
	

	/**
	 * 
	 * 根据派送单ID查找已生成到达联的派送明细列表
	 * 
	 * @param deliverbillDetailDto
	 *            包含派送单ID的查询条件
	 * @return 已生成到达联的派送明细列表
	 * @author 
	 * @date 
	 */
	List<DeliverbillDetailEntity> queryArrivesheetListByDeliverbillById(
			DeliverbillDetailDto deliverbillDetailDto);

	
	/**
	 * 
	 * 根据派送单ID查找派送单明细列表大小
	 * 
	 * @param deliverbillId
	 *            派送单ID
	 * @return 派送单明细列表大小
	 * 
	 * @return 派送单明细列表大小
	 * @author 
	 * @date 
	 */
	Long queryCountByDeliverbillId(String deliverbillId);
	
	
	
	
}