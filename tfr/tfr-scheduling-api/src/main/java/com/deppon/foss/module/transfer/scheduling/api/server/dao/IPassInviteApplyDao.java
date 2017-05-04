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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/dao/IPassInviteApplyDao.java
 * 
 *  FILE NAME     :IPassInviteApplyDao.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.server.dao
 * FILE    NAME: IPassInviteApplyDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.server.dao;

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PassInviteApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.OrgEntity;

/**
 * IPassInviteApplyDao
 * @author 104306-foss-wangLong
 * @date 2012-12-15 下午12:54:39
 */
public interface IPassInviteApplyDao {

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
	 * @return 行数
	 */
	Long queryCount(PassInviteApplyEntity passInviteApplyEntity);

	/**
	 * 查询外请车价格
	 * @param inviteNo
	 * @param vehicleNo 车牌号
	 * @return inviteCost 价格
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-29 下午2:07:22
	 * 
	 * @author_update 134019-yuyongxiang
	 * @date_update 2013年7月16日 11:40:53
	 */
	BigDecimal queryInviteCost(String inviteNo);
	
	/**
	 * 根据外请车车牌号、用车部门code查询请车价格
	 * @return inviteCost
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-7 上午8:17:25
	 */
	List<PassInviteApplyEntity> queryPassInvitePrice(String vehicleNo ,String applyOrgCode,String status,String useStatus);

	List<InviteVehicleDto> queryInviteVehicleListByPage(InviteVehicleDto inviteVehicleDto, int start, int limit);

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
    /**
     * 根据约车编号查询信息部名称和编码
     * @param inviteNo
     * @author 310248
     * @return
     */
	OrgEntity queryOrgDtoByApplyByInviteNo(String inviteNo);
	
}