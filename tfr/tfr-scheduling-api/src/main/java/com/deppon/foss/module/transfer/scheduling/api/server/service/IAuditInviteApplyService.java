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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/service/IAuditInviteApplyService.java
 * 
 *  FILE NAME     :IAuditInviteApplyService.java
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
 * FILE    NAME: IAuditInviteApplyService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.domain.TpsAboutVehicleRequestEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.VehicleRequestEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AuditInviteApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.PassInviteApplyVo;

/**
 * 外请车审核log service
 * @author 104306-foss-wangLong
 * @date 2012-12-15 下午12:47:47
 */

/**
* @description 这里用一句话描述这个类的作用
* @version 1.0
* @author 283250-foss-liuyi
* @update 2016年1月21日 下午3:12:56
*/

/**
* @description 这里用一句话描述这个类的作用
* @version 1.0
* @author 283250-foss-liuyi
* @update 2016年1月21日 下午3:13:00
*/
public interface IAuditInviteApplyService extends IService {

	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:47:47
	 * @param auditInviteApplyEntity
	 * @return 受影响的行数 
	 */
	int addAuditInviteApply(AuditInviteApplyEntity auditInviteApplyEntity);

	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:47:47
	 * @param auditInviteApplyEntity
	 * @return 受影响的行数 
	 */
	int updateAuditInviteApply(AuditInviteApplyEntity auditInviteApplyEntity);
	
	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:47:47
	 * @param auditInviteApplyEntity
	 * @return java.util.List
	 */
	List<AuditInviteApplyEntity> queryAuditInviteApplyList(AuditInviteApplyEntity auditInviteApplyEntity);
	
	/**
	 * 根据外请车编号查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:47:47
	 * @param inviteNo 外请车编号
	 * @return java.util.List
	 */
	List<AuditInviteApplyEntity> queryAuditInviteApplyListByInviteNo(String inviteNo);
	
	/**
	 * 查询集合 分页
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:47:47
	 * @param auditInviteApplyEntity
	 * @param start   
	 * @param pageSize
	 * @return java.util.List
	 */
	List<AuditInviteApplyEntity> queryAuditInviteApplyForPage(AuditInviteApplyEntity auditInviteApplyEntity, int start, int pageSize);

	/**
	 * 统计记录数
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:47:47
	 * @param auditInviteApplyEntity
	 * @return 
	 */
	Long queryCount(AuditInviteApplyEntity auditInviteApplyEntity);
	
	/**
	 * 拒绝审核外请车约车
	 * @param notes 审核结果备注
	 * @param inviteNo 外请车单号
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-25 下午2:34:30
	 */
	void refuseInviteInviteApplyApply(String notes ,String inviteNo);
	
	/**
	 * 通过审核外请车约车
	 * @param notes 审核备注
	 * @param inviteNo 外请车单号
	 * @param vehicleNo 外请车车牌号
	 * @param perdictArriveTime 预计到达时间
	 * @param inviteCost 请车价格
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-26 下午3:50:36
	 * @yuyongxiang
	 * 逻辑修改 : 受理信息由原来的一条变成多条,所以这边会计算一个总得请车价格放到主表里面
	 * @update_author 134019-yuyongxiang
	 * @update_date 2013年7月15日 16:45:13
	 * 
	 */
	void passInviteInviteApplyApply(PassInviteApplyVo passInviteApplyVo);
	
	/**
	 * 退回审核外请车约车申请
	 * @param notes 备注
	 * @param inviteNo 外车单号
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-26 下午6:25:23
	 */
	void backInviteInviteApplyApply (String notes,String inviteNo,String applyOrgCode);
	
	void passInviteInviteTPSApply(List<VehicleRequestEntity> vehicleRequestEntitys);
	
	
	/**
	* @description tps同步已受理约车信息至foss
	* @author 283250-foss-liuyi
	* @update 2016年1月21日 下午3:13:05
	*/
	void  tpsAboutVehicleToFoss(TpsAboutVehicleRequestEntity tpsAboutVehicleRequestEntity);
	
	/**
	* @description 通过约车编号更新
	* @param auditInviteApplyEntity
	* @return	int
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年3月19日 上午10:52:33
	*/
	int updateAuditInviteApplyByInvite(AuditInviteApplyEntity auditInviteApplyEntity);
}