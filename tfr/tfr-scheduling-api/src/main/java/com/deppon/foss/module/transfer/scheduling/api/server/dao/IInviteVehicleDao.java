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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/dao/IInviteVehicleDao.java
 * 
 *  FILE NAME     :IInviteVehicleDao.java
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
package com.deppon.foss.module.transfer.scheduling.api.server.dao;

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleDto;

/**
 * 外请约车申请数据访问
 * @author 104306-foss-wangLong
 * @date 2012-12-15 上午11:08:37
 */
public interface IInviteVehicleDao {

	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 上午11:08:37
	 * @param inviteVehicleEntity
	 * @return 受影响的行数
	 */
	int addInviteVehicle(InviteVehicleEntity inviteVehicleEntity);

	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 上午11:08:37
	 * @param inviteVehicleEntity 
	 * @return 受影响的行数
	 */
	int updateInviteVehicle(InviteVehicleEntity inviteVehicleEntity);
	
	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 上午11:08:37
	 * @param inviteVehicleEntity
	 * @return java.util.List
	 */
	List<InviteVehicleDto> queryInviteVehicleList(InviteVehicleDto inviteVehicleDto);
	
	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 上午11:08:37
	 * @param inviteVehicleEntity
	 * @return java.util.List
	 */
	List<InviteVehicleDto> queryInviteVehicleListByNeedPassRecord(InviteVehicleDto inviteVehicleDto);
	
	/**
	 * 查询集合 分页
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 上午11:08:37
	 * @param inviteVehicleDto
	 * @param start
	 * @param pageSize
	 * @return java.util.List
	 */
	List<InviteVehicleDto> queryInviteVehicleForPage(InviteVehicleDto inviteVehicleDto, int start, int pageSize);

	/**
	 * 统计记录数
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 上午11:08:37
	 * @param inviteVehicleDto
	 * @return 行数
	 */
	Long queryCount(InviteVehicleDto inviteVehicleDto);

	/**
	 * 更新状态
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 上午11:09:01
	 * @param inviteNoList  外请车编号list
	 * @param status  状态
	 * @return {@link java.lang.Integer} 受影响的行数
	 */
	int updateInviteVehicleApplyStatus(List<String> inviteNoList, String status);

	/** 更新开单完成后状态
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 下午4:23:09
	 * @param inviteNo
	 * @param billStatus
	 */
	int updateInviteVehicleForFinishBill(String inviteNo, String billStatus);

	/**
	 * 更新外请约车使用状态
	 * @author 104306-foss-wangLong
	 * @date 2013-1-7 上午9:11:35
	 * @param inviteNo 外请编号
	 * @param vehicleNo 车牌号
	 * @param status 状态
	 */
	int updateUseStatus(String inviteNo,String vehicleNo, String status);
	

	/**
	 * 根据车牌号查询外请车使用状态
	 * @param vehicleNo
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-12 下午4:32:26
	 */
	List<InviteVehicleEntity> queryInviteUseStatus(String vehicleNo);

	/**
	 * 查询非本次约车编号对应的状态为"已报道"、"未使用"的外请车约车记录列表
	 * @param inviteNo 外请车约车编号
	 * @param vehicleNo 车牌号
	 * @param status 状态
	 * @param useStatus 使用状态
	 * 
	 * @return: 外请车约车列表
	 *
	 * @author foss-wuyingjie
	 * @date 2013-3-7 下午2:29:06
	 */
	List<InviteVehicleDto> queryCheckVehicleArriveRuleList(InviteVehicleDto inviteVehicleDto);

	/**
	 * 通过外请车约车编号，获取外请车约车信息
	 * @param inviteNo
	 * @return: 
	 *
	 * @author foss-wuyingjie
	 * @date 2013-3-7 下午2:51:15
	 */
	List<InviteVehicleDto> queryInviteVehicleByNo(String inviteNo);

	/**
	 * 外请车约车受理页面所显示的约车列表信息
	 * @param inviteVehicleDto
	 * @return: List<InviteVehicleDto>
	 *
	 * @author 134019-foss-yuyongxiang
	 * @date 2013年7月16日 22:04:07
	 * 
	 * 由于SERVICE 实现只是用了 约车编号一个条件,后期改造 出于优化SQl的目的缩减了SQL的查询条件
	 * PS : 如果需要使用本接口请通知
	 * 
	 */
	List<InviteVehicleDto> queryAuditInviteVehicleList(InviteVehicleDto inviteVehicleDto);
	/**
	 * 外请车详细页面 显示 配载单号
	 * @param inviteNo ->外请约车编号
	 * @return: 配载单号
	 *
	 * @author foss-yuyongxiang
	 * @date 2013年6月9日 16:12:23
	 */
	String queryVehicleassemblebillByInviteNo(String inviteNo);
	/**
	 * 修改 修改约车总价
	 * @author 134019-foss-yuyongxiang
	 * @date 2013年8月2日 17:41:03
	 * @param totalInviteCost 
	 * @return 受影响的行数
	 */
	int updateInviteVehicleForTotalInviteCost(String inviteNo,BigDecimal totalInviteCost);

	/**
	 * @author niuly
	 * @date 2014-1-20下午4:48:46
	 * @function 根据约车编号查询约车信息（配载单调用接口）
	 * @param inviteNo
	 * @return
	 */
	List<String> queryInviteVehicleInfo(String inviteNo);
}