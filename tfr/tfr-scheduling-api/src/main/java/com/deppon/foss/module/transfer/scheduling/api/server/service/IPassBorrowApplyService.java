/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/service/IPassBorrowApplyService.java
 * 
 *  FILE NAME     :IPassBorrowApplyService.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PassBorrowApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.VehicleDriverWithDto;

/**
 * 审核借车Service
 * @author 104306-foss-wangLong
 * @date 2012-12-06 上午8:44:15
 */
public interface IPassBorrowApplyService  extends IService {

	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:15
	 * @param passBorrowApplyEntity
	 * @return 受影响的行数 
	 */
	int addPassBorrowApply(PassBorrowApplyEntity passBorrowApplyEntity);

	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:15
	 * @param passBorrowApplyEntity
	 * @return 受影响的行数 
	 */
	int updatePassBorrowApply(PassBorrowApplyEntity passBorrowApplyEntity);
	
	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:15
	 * @param passBorrowApplyEntity
	 * @return java.util.List
	 */
	List<PassBorrowApplyEntity> queryPassBorrowApplyList(PassBorrowApplyEntity passBorrowApplyEntity);
	
	/**
	 * 根据借车编号查询
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:15
	 * @param borrowNo
	 * @return java.util.List
	 */
	PassBorrowApplyEntity queryPassBorrowApplyByBorrowNo(String borrowNo);
	
	/**
	 * 查询集合 分页
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:15
	 * @param passBorrowApplyEntity
	 * @param start   
	 * @param pageSize
	 * @return java.util.List
	 */
	List<PassBorrowApplyEntity> queryPassBorrowApplyForPage(PassBorrowApplyEntity passBorrowApplyEntity, int start, int pageSize);

	/**
	 * 统计记录数
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:15
	 * @param passBorrowApplyEntity
	 * @return 
	 */
	Long queryCount(PassBorrowApplyEntity passBorrowApplyEntity);

	/**
	 * 借车审核通过
	 * @author 104306-foss-wangLong
	 * @date 2012-12-10 下午6:47:43
	 * @param passBorrowApplyEntity
	 * @param notes
	 */
	void doAcceptedBorrowVehicleApply(PassBorrowApplyEntity passBorrowApplyEntity, String notes);
	
	/**
	 *  借来的车辆, 约车使用
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 上午12:38:30
	 * @param vehicleDriverWithDto
	 */
	Map<String, VehicleDriverWithDto> queryBorrowVehicle(VehicleDriverWithDto vehicleDriverWithDto);
}