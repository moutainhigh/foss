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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/service/IPassInviteApplyService.java
 * 
 *  FILE NAME     :IPassInviteApplyService.java
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
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.server.service
 * FILE    NAME: IPassInviteApplyService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PassInviteApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.OrgEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.VehicleDriverWithDto;

/**
 * 外请车受理服务
 * @author 104306-foss-wangLong
 * @date 2012-12-15 下午12:54:39
 */
public interface IPassInviteApplyService extends IService {

	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:54:39
	 * @param passInviteApplyEntity
	 * @return 受影响的行数 
	 */
	int addPassInviteApply(PassInviteApplyEntity passInviteApplyEntity);

	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:54:39
	 * @param passInviteApplyEntity
	 * @return 受影响的行数 
	 */
	int updatePassInviteApply(PassInviteApplyEntity passInviteApplyEntity);
	
	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:54:39
	 * @param passInviteApplyEntity
	 * @return java.util.List
	 */
	List<PassInviteApplyEntity> queryPassInviteApplyList(PassInviteApplyEntity passInviteApplyEntity);
	
	/**
	 * 没有地方在用这个接口使用请通知
	 * @update_author 134019-yuyongxiang
	 * @update_date 2013年7月16日 14:27:15
	 * 
	 * 根据外请车编号查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 下午2:02:20
	 * @param inviteNo 外请车编号
	 * @return PassInviteApplyEntity
	 */
	PassInviteApplyEntity queryPassInviteApplyListByInviteNo(String inviteNo);
	
	/**
	 * 查询集合 分页
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:54:39
	 * @param passInviteApplyEntity
	 * @param start   
	 * @param pageSize
	 * @return java.util.List
	 */
	List<PassInviteApplyEntity> queryPassInviteApplyForPage(PassInviteApplyEntity passInviteApplyEntity, int start, int pageSize);

	/**
	 * 统计记录数
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:54:39
	 * @param passInviteApplyEntity
	 * @return 
	 */
	Long queryCount(PassInviteApplyEntity passInviteApplyEntity);
	
	
	/**
	 * 根据外请车单号查询(审核受理记录、车辆信息)
	 * @param inviteNo 外请车单号
	 * @return InviteVehicleDto
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-25 下午12:20:14
	 */
	InviteVehicleDto queryAuditInviteLogListAndVehicleList(String inviteNo);
	
	/**
	 * 根据外请车车牌号、用车部门code查询请车价格
	 * <br/>
	 * PS:这个地方使用车牌号查询价格所以查出来的是车辆的价格 外请车约车表里面存着一个总价钱
	 * <br/>
	 * 
	 * 提供给配载单的接口
	 * @update_date 2013年7月16日 14:36:33
	 * 
	 * @return inviteCost
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-7 上午8:17:25
	 */
	PassInviteApplyEntity queryPassInvitePrice(String vehicleNo ,String applyOrgCode);
	
	/**
	 * 查询外请车辆信息
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-11 下午4:57:33
	 */
	List<VehicleDriverWithDto> queryPreviewPassInviteVehicleDetail(String vehicleNo,String vehicleType,
			String driverName,String driverPhone,boolean isOpenVehicle);
	
	/**
	 * 根据车牌号查询外请车使用状态
	 * @param vehicleNo
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-12 下午4:32:26
	 */
	List<InviteVehicleEntity> queryInviteUseStatus(String vehicleNo);

	/**
	 * 按照查询条件，查询外请车的信息
	 * @param inviteVehicleDto
	 * @param start
	 * @param limit
	 * @return: 
	 *
	 * @author foss-wuyingjie
	 * @date 2013-3-29 下午5:34:23
	 */
	List<InviteVehicleDto> queryInviteVehicleListByPage(InviteVehicleDto inviteVehicleDto, int start, int limit);

	/**
	 * 按照查询条件，返回到的外请车总数
	 * @param inviteVehicleDto
	 * @return: 
	 *
	 * @author foss-wuyingjie
	 * @date 2013-3-29 下午5:34:48
	 */
	Long queryInviteVehicleCountByPage(InviteVehicleDto inviteVehicleDto);
	
	
	/**
	* @description 根据约车编号更新
	* @param passInviteApplyEntity
	* @return	int
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年3月19日 上午11:01:17
	*/
	int updatePassInviteApplyByInviteNo(PassInviteApplyEntity passInviteApplyEntity);

	 

	OrgEntity queryOrgDtoByApplyByInviteNo(String inviteNo);
	
}