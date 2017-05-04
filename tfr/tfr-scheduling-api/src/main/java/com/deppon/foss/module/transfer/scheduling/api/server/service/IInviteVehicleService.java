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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/service/IInviteVehicleService.java
 * 
 *  FILE NAME     :IInviteVehicleService.java
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
 * FILE    NAME: IInviteVehicleService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.server.service;

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.OrgEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.InviteVehicleException;

/**
 * 外请约车Service
 * @author 104306-foss-wangLong
 * @date 2012-12-04 下午2:23:39
 */
public interface IInviteVehicleService extends IService {
	
	/**
	 * 根据外请车编号查询 请车价格 </br>
	 * 约车申请不存在 抛出异常{@link InviteVehicleException}
	 * @author 104306-foss-wangLong
	 * @date 2012-12-4 下午2:32:17
	 * @param inviteNo 外请车编号
	 * @return 外请车价格
	 * @throws InviteVehicleException
	 * @throws ParameterException
	 */
	BigDecimal queryInviteCostByInviteNo(String inviteNo) 
			throws InviteVehicleException;
	
	/**
	 * 检查是否有此外请车编号</br>
	 * @author 104306-foss-wangLong
	 * @date 2012-12-4 下午4:48:45
	 * @param inviteNo	外请车编号
	 * @param deptCode	部门编码
	 * @return 外请车申请状态
	 * @throws InviteVehicleException
	 * @throws ParameterException
	 */
	String checkInviteNoIsExists(String inviteNo, String deptCode)
			throws InviteVehicleException;
	
	/**
	 * 运单开单完成之后，更新状态</br>
	 * 标识此约车已经完成开单，重复更新抛出异常
	 * @author 104306-foss-wangLong
	 * @date 2012-12-4 下午5:23:02
	 * @param inviteNo	      外请车编号
	 * @param billStatus  开单状态
	 * @throws InviteVehicleException
	 * @throws ParameterException
	 */
	void updateInviteVehicleForFinishBill(String inviteNo)
			throws InviteVehicleException;
	
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
	 * @param inviteVehicleDto
	 * @return java.util.List
	 */
	List<InviteVehicleDto> queryInviteVehicleList(InviteVehicleDto inviteVehicleDto);
	
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
	 * @return 
	 */
	Long queryCount(InviteVehicleDto inviteVehicleDto);

	/**
	 * 保存外请约车申请
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午2:24:20
	 * @param inviteVehicleDto 
	 * @return 
	 */
	InviteVehicleDto saveInviteVehicleApply(InviteVehicleDto inviteVehicleDto);

	/**
	 * 查询外请约车明细
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 上午11:43:29
	 * @param inviteNo 外请约车编号
	 * @return {@link InviteVehicleDto}
	 */
	InviteVehicleDto queryInviteVehicleApplyDetail(String inviteNo);
	
	/**
	 * 根据外请车编号查询对象
	 * @author 104306-foss-wangLong
	 * @date 2012-12-17 下午3:35:15
	 * @param inviteNo 外请车编号
	 * @return InviteVehicleDto
	 */
	InviteVehicleDto queryInviteVehicleByInviteNo(String inviteNo);
	
	/**
	 * 根据外请车编号List查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-17 下午3:35:15
	 * @param inviteNoList 外请车编号list
	 * @return {@link java.util.List<InviteVehicleDto>}
	 */
	List<InviteVehicleDto> queryInviteVehicleByInviteNoList(List<String> inviteNoList);
	
	/**
	 * 查询营业部或外场地址
	 * @author 104306-foss-wangLong
	 * @date 2012-12-18 下午1:14:34
	 * @param useType		用车类型
	 * @param applyOrgCode	部门编码
	 * @return 用车地址
	 */
	String querySalesDepartmentAddressOrTransitAddress(String useType, String applyOrgCode);

	/**
	 * 撤销外请约车
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 上午11:06:34
	 * @param inviteNoList 约车编号list
	 */
	void doUndoInviteVehicleApply(List<String> inviteNoList);
	
	/**
	 * 报到
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 上午11:06:34
	 * @param inviteVehicleDto
	 */
	void doVerifyArriveInviteVehicleApply(InviteVehicleDto inviteVehicleDto);
	/**
	 * 报到
	 * FOSS同城约车报到以及FOSS整车约车报到
	 * @author 269701-foss-lln
	 * @date 2016-03-15 上午17:50:34
	 * @param inviteVehicleDto
	 */
	void doVerifyArriveInviteVehicleApplyForWholecar(InviteVehicleDto inviteVehicleDto);
	
	/**
	 * 释放
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 上午11:06:34
	 * @param inviteNoList 约车编号list
	 */
	void doReleaseInviteVehicleApply(List<String> inviteNoList);
	
	/**
	 * 更新审核外请约车操作状态
	 * @param inviteNo 外请车约车单号
	 * @param status 操作状态
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-25 下午1:57:49
	 */
	void updatePassInviteVehicleStatus(String inviteNo, String status);

	/**
	 * 查询合同线路
	 * @author 104306-foss-wangLong
	 * @date 2012-12-28 上午11:31:39
	 * @param inviteNo 外请车编号
	 * @return LineEntity
	 */
	LineEntity queryBargainLine(String inviteNo);

	/**
	 * 更新外请约车使用状态   - 已使用
	 * @author 104306-foss-wangLong
	 * @date 2013-1-28 上午10:37:00
	 * @param inviteNo 外请车单号
	 * @throws InviteVehicleException
	 */
	void updateUseStatusForUsing(String inviteNo,String vehicleNo) 
			throws InviteVehicleException;
	
	/**
	 * 更新外请约车使用状态   - 未使用
	 * @author 104306-foss-wangLong
	 * @date 2013-1-28 上午10:37:00
	 * @param inviteNo 外请车单号
	 * @throws InviteVehicleException
	 */
	void updateUseStatusForUnused(String inviteNo,String vehicleNo)
			throws InviteVehicleException;

	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 上午11:08:37
	 * @param inviteVehicleEntity
	 * @return java.util.List
	 */
	List<InviteVehicleDto> queryInviteVehicleListByNeedPassRecord(boolean isLoadAll,List<String> inviteNoList,InviteVehicleDto inviteVehicleDto);

	/**
	 * <pre>
	 * 车辆报到前，判断是否存在此车辆、"已报到"、"未使用"状态的，若存在，抛出异常，并且异常信息提示用户
	 *  存在以下外请车约车信息，已报道但未使用，请先对这些业务数据进行"释放"处理。
	 *    编号:001，用车部门：XXX
	 *    编号:002，用车部门：XXX
	 * </pre>
	 * @param inviteNo: 外请车约车编号
	 *
	 * @author foss-wuyingjie
	 * @date 2013-3-7 下午2:07:27
	 */
	void checkVehicleArriveRule(String inviteNo);
	/**
	 * @author niuly
	 * @date 2014-1-20下午4:40:31
	 * @function 根据约车编号查询约车信息（配载单调用接口）
	 * @param entity
	 * @return
	 */
	List<String> queryInviteVehicleInfo(String inviteNo);
    /**
     * @author 310248 查询费用承担部门 
     * @param applyOrgCode
     * @return
     */
	List<OrgEntity> queryBearFeesDept(String applyOrgCode,String dispatchTransDept);
	
	/**
     * @author 332219 根据当前部门查询单票费用 
     * @param applyOrgCode
     * @return
     */
	double queryVehicleCost(String orgCode);
}