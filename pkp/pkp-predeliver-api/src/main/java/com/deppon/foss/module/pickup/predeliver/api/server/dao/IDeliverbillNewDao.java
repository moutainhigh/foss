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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/dao/IDeliverbillDao.java
 * 
 * FILE NAME        	: IDeliverbillDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillNewDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DriverDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoadTaskDto;

/**
 * 
 * 派送单DAO接口
 * 
 */
public interface IDeliverbillNewDao {
	

	/**
	 * 
	 * 根据输入条件，查询符合条件的派送单数量
	 * 
	 * @param deliverbillNewDto
	 *            查询条件
	 * 
	 * @return 符合条件的派送单数量
	 * @author 
	 * @date 
	 */
	Long queryCountByCondition(DeliverbillNewDto deliverbillNewDto);
	
	/**
	 * 
	 * 根据输入条件，查询派送单
	 * 
	 * @param deliverbillDto
	 *            查询条件
	 * @return 派送单列表
	 * @author 
	 * @date 
	 */
	List<DeliverbillNewDto> queryByCondition(DeliverbillNewDto deliverbillNewDto);
	
	
	/**
	 * 
	 * 根据输入条件，查询派送单
	 * 
	 * @param deliverbillNewDto
	 *            查询条件
	 * @return 派送单列表
	 * @author 
	 * @date 
	 */
	List<DeliverbillNewDto> queryByCondition(DeliverbillNewDto deliverbillNewDto,
			int start, int limit);
	
	
	/**
	 * 查询派送单序列
	 * @author 
	 * @date
	 */
	String querySequence();
	
	
	/**
	 * 根据ID查找派送单
	 * 
	 * @param id
	 *            派送单ID
	 * @return 派送单
	 * @author 
	 * @date 
	 */
	DeliverbillEntity queryById(String id);
	
	/**
	 * 
	 * 更新派送单
	 * 
	 * @param deliverbill
	 *            派送单
	 * @return 若成功，返回更新后的派送单；否则返回null
	 * @author 
	 * @date 
	 */
	DeliverbillEntity update(DeliverbillEntity deliverbill);
	
	/**
	 * 
	 * 更新派送单
	 * @author 
	 * @date 
	 */
	int updateDeliverBill(DeliverbillEntity deliverbill);
	
	
	/**
	 * 
	 * 根据查询条件(工号/姓名/电话号码)查询公司司机
	 * 
	 * @param driverDto
	 *            查询条件
	 * @return 符合条件的公司司机列表
	 * @author 
	 * @date 
	 */
	List<DriverDto> queryDriverListByDriverDto(DriverDto driverDto);
	
	/**
	 * 根据车牌号查询车辆信息
	 * queryByOwnTruck 公司车
	 * queryByLeasedTruck 外请车
	 * @param vehicleNo
	 * @return
	 */
	DeliverbillNewDto queryByOwnTruck(String vehicleNo);
	DeliverbillNewDto queryByLeasedTruck(String vehicleNo);
	
}