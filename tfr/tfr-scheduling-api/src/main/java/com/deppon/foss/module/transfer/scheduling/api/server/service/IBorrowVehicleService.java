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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/service/IBorrowVehicleService.java
 * 
 *  FILE NAME     :IBorrowVehicleService.java
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
/*
 * PROJECT NAME: tfr-scheduling
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.server.service.impl
 * FILE    NAME: IBorrowVehicleService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.BorrowVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.BorrowVehicleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.ParameterException;

/**
 * 借车申请Service
 * @author 104306-foss-wangLong
 * @date 2012-12-03 下午1:06:57
 */
public interface IBorrowVehicleService extends IService {

	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:57
	 * @param borrowVehicleEntity
	 * @return 受影响的行数 
	 */
	int addBorrowVehicle(BorrowVehicleEntity borrowVehicleEntity);

	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:57
	 * @param borrowVehicleEntity
	 * @return 受影响的行数 
	 */
	int updateBorrowVehicle(BorrowVehicleEntity borrowVehicleEntity);

	/**
	 * 查询单个对象（按主键查询）
	 * <br>------------------------------<br>
	 * 存在 返回该对象 不存在返回null
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:57
	 * @param id  主键id
	 * @return BorrowVehicleEntity
	 */
	BorrowVehicleEntity queryBorrowVehicle(String id);
	
	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:57
	 * @param borrowVehicleDto
	 * @return java.util.List
	 */
	List<BorrowVehicleEntity> queryBorrowVehicleList(BorrowVehicleDto borrowVehicleDto);

	/**
	 * 根据借车单号List查询对象
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:57
	 * @param borrowNoList
	 * @return java.util.List
	 */
	List<BorrowVehicleEntity> queryBorrowVehicleListByBorrowNoList(List<String> borrowNoList);
	
	/**
	 * 根据借车单号List查询对象</br>
	 * 用于借车申请到借车审核页面
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:57
	 * @param borrowNoList
	 * @return java.util.List
	 */
	List<BorrowVehicleEntity> queryNeedAcceptBorrowVehicleApply(List<String> borrowNoList);
	
	/**
	 * 根据借车单号查询对象
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:57
	 * @param borrowVehicleDto
	 * @return BorrowVehicleEntity
	 */
	BorrowVehicleEntity queryBorrowVehicleByBorrowNo(String borrowNo);
	
	/**
	 * 查询集合 分页
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:57
	 * @param borrowVehicleDto
	 * @param start   
	 * @param pageSize
	 * @return java.util.List
	 */
	List<BorrowVehicleDto> queryBorrowVehicleForPage(BorrowVehicleDto borrowVehicleDto, int start, int pageSize);

	/**
	 * 统计记录数
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:57
	 * @param borrowVehicleDto
	 * @return 
	 */
	Long queryCount(BorrowVehicleDto borrowVehicleDto);

	/**
	 * 保存操作
	 * <p>根据主键id,来区分是新增操作 还是修改操作
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:57
	 * @param borrowVehicleEntity
	 * @throws ParameterException
	 */
	void saveBorrowVehicleApply(BorrowVehicleEntity borrowVehicleEntity);

	/**
	 * 撤销借车申请
	 * @author 104306-foss-wangLong
	 * @date 2012-12-5 下午2:04:06
	 * @param borrowNoList 借车编号 list
	 */
	void doUndoBorrowVehicleApply(List<String> borrowNoList);
	
	/**
	 * 确认到达
	 * @author 104306-foss-wangLong
	 * @date 2012-12-5 下午2:04:06
	 * @param borrowNoList 借车编号 list
	 */
	void doBorrowVehicleConfirmTo(List<String> borrowNoList);
	
	/**
	 * 车辆归还
	 * @author 104306-foss-wangLong
	 * @date 2012-12-5 下午2:06:18
	 * @param borrowNoList 借车编号 list
	 */
	void doBorrowVehicleGiveBack(List<String> borrowNoList);

	/**
	 * 查询借车申请明细
	 * @author 104306-foss-wangLong
	 * @date 2012-12-5 下午3:46:48
	 * @param borrowNo 借车单号
	 */
	BorrowVehicleDto queryBorrowVehicleApplyDetail(String borrowNo);

	/**
	 * 更新到退回状态
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 下午5:48:54
	 * @param borrowNo
	 */
	void updateBorrowVehicleApplyForReturnStatus(String borrowNo);
	
	/**
	 * 更新到拒绝状态
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 下午5:48:54
	 * @param borrowNo 借车编号
	 */
	void updateBorrowVehicleApplyForDismissStatus(String borrowNo);

	/**
	 * 更新到已受理(通过)状态
	 * @author 104306-foss-wangLong
	 * @date 2012-12-10 下午6:55:35
	 * @param borrowNo 借车编号
	 */
	void updateBorrowVehicleApplyForAcceptedStatus(String borrowNo);
}