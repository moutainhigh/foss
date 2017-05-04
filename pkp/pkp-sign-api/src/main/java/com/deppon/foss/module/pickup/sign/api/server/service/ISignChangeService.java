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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/service/ISignChangeService.java
 * 
 * FILE NAME        	: ISignChangeService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WayBillNoLocusDTO;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.ChangeSignRfcDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RepaymentArrivesheetDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.SignResultDto;

/**
 * 查询签收变更结果
 * 
 * @author ibm-lizhiguo
 * @date 2012-10-18 上午9:44:11
 */
public interface ISignChangeService extends IService {

	/*
	 * 专线--setp1(反签收) 根据运单号,获得运到 相关联的，付款LIST和到达联LIST
	 * @author ibm-lizhiguo
	 * @date 2012-11-16 下午2:31:16
	 */
	RepaymentArrivesheetDto searchRepaymentArrivesheet(String waybillNo);

	/**
	 * 
	 * 专线--setp2 保存
	 * 
	 * @author ibm-lizhiguo
	 * @date 2012-11-16 下午4:21:40
	 */
	void saveChangeDedicated(SignResultDto dto, CurrentInfo currentInfo);

	/**
	 * 
	 * 根据运单号,获得空运和偏线的签收结果
	 * 
	 * @author ibm-lizhiguo
	 * @date 2012-11-19 上午10:40:30
	 */
	WaybillSignResultEntity searchWaybillSignResult(String waybillNo);

	/**
	 * 
	 * 保存空运偏线提出的变更申请
	 * 
	 * @author ibm-lizhiguo
	 * @date 2012-11-19 上午11:34:49
	 */
	void saveChangeAirliftPartialLine(SignResultDto dto, CurrentInfo currentInfo);

	/*** 反签收 ***/
	/**
	 * 
	 * 在反签收查询中，只要有到达联的状态是修改中，将不能做付款的反签收
	 * 
	 * @author ibm-lizhiguo
	 * @date 2012-11-19 下午2:53:51
	 */
	RepaymentArrivesheetDto searchReverseSignDedicatedInfo(String waybillNo);

	/**
	 * 
	 * 反签收保存
	 * 
	 * @author ibm-lizhiguo
	 * @date 2012-11-19 下午7:11:35
	 */
	void saveReverseSignDedicatedInfo(SignResultDto dto, CurrentInfo currentInfo);

	/**
	 * 
	 * <p>
	 * 查询申请变更数据集<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-6
	 * @param entity
	 * @param start
	 * @param limit
	 * @return List<SignRfcEntity>
	 */
	List<SignRfcEntity> searchSignRfcList(SignRfcEntity entity, int start, int limit, CurrentInfo currentInfo);
	
	/**
	 * 
	 * <p>
	 * 查询申请变更数据集<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-6
	 * @param entity
	 * @param start
	 * @param limit
	 * @return List<SignRfcEntity>
	 */
	List<SignRfcEntity> searchSignRfcListForView(SignRfcEntity entity, int start, int limit, CurrentInfo currentInfo);

	/**
	 * 
	 * <p>
	 * 获得签收变更记录数<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-6
	 * @param entity
	 * @return Long
	 */
	Long getSignRfcCount(SignRfcEntity entity, CurrentInfo currentInfo);
	
	/**
	 * 
	 * <p>
	 * 获得签收变更记录数<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-6
	 * @param entity
	 * @return Long
	 */
	Long getSignRfcCountView(SignRfcEntity entity, CurrentInfo currentInfo);

	/**
	 * 
	 * <p>
	 * 同意申请变更<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-6
	 * @param id void
	 */
	void agree(String id, String notes, CurrentInfo currentInfo);

	/**
	 * 
	 * <p>
	 * 拒绝申请变更<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-6
	 * @param id void
	 */
	void refuse(String id, String notes, CurrentInfo currentInfo);

	/**
	 * 
	 * <p>
	 * 获得变更明细<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-7
	 * @param dto
	 * @return SignResultDto
	 */
	SignResultDto getSignDetail(SignResultDto dto);

	/**
	 * 
	 * <p>
	 * 获得反签收空运和偏线的信息<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-22 void
	 */
	List<ChangeSignRfcDto> queryArriveChangeByWaybillNo(String waybillNo);

	/**
	 * 
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-1-21
	 * @param waybillNo
	 * @return RepaymentArrivesheetDto
	 */
	RepaymentArrivesheetDto searchReverseSignAirPartial(String waybillNo);

	/**
	 * 
	 * <p>
	 * 空运偏线反签收保存<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-1-21
	 * @param dto
	 * @param currentInfo void
	 */
	void saveReverseSignAirPartialInfo(SignResultDto dto, CurrentInfo currentInfo);

	/**
	 * 
	 * <p>
	 * 查询运单是否存在未受理的到付款相关到达更改单(String 运单号)，该接口查询运单是否存在未审核的反货款结清或货款变更<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-12
	 * @param waybillNo
	 * @return boolean(true:表示没有审批中的数据，false表示有审批中的数据)
	 */
	boolean checkWayBillSignStatus(String waybillNo);

	/**
	 * 
	 * <p>
	 * 凡签收货物轨迹查询<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-20
	 * @param waybillNo
	 * @return List<WayBillNoLocusDTO>
	 */
	List<WayBillNoLocusDTO> queryReverseArriveSheetByWaybillNo(String waybillNo);

	/**
	 * 
	 * 验证运单签收变更、反签收申请情况
	 * 
	 * @param waybillNo
	 * @author ibm-wangfei
	 * @date Apr 2, 2013 5:03:27 PM
	 */
	void checkWayBillRfcStatus(String waybillNo);/**
	 * 
	 * 查询是否有到达变更未审批的
	 * @author foss-meiying
	 * @date 2013-8-2 下午4:11:35
	 */
	boolean isRfc(SignRfcEntity newEntity);
	
	/**
	 * 获得申请编号<br />
	 * @author 231438-chenjunying 借用变更签收的生成申请编号
	 * (更改之前的方法为public 共用)
	 * @version DMANA-9716 
	 * @return 
	 */
	String getRecNoInfo(String waybillNo);
	/**
	 * 查询运单签收变更、反签收记录
	 * @param waybillNo,status
	 * @author Foss-231438 chenjunying
	 * @date 2015-05-07
	 */
	List<SignRfcEntity> queryWayBillRfcListBy(String waybillNo, String status);
	/**
	 * 查询运单签反签收记录 (反结清、反签收【空运/偏线/落地配-反签收结果、专线反到达联】)
	 * @param waybillNo,status
	 * @author Foss-231438 chenjunying
	 * @date 2015-12-03
	 */
	List<SignRfcEntity> queryWayBillRfcReverseList(String waybillNo,String status);
    /**
     * @author 378375
     * @param entity
     * @return 查询审批中的签收变更单的运单数量
     * @date 2017年3月7日 20:46:35
     */
	Integer getWaybillApprovalCountling(SignRfcEntity entity);
}