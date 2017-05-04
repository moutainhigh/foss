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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/service/IDeliverHandlerService.java
 * 
 * FILE NAME        	: IDeliverHandlerService.java
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
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.FinancialDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
/**
 * 快递派送处理接口
 * @author foss-meiying
 * @date 2014-10-09 上午11:05:51
 * @since
 * @version
 */
public interface IExpDeliverHandlerService extends IService {
	/**
	 * 派送处理      根据条件查询运单编号
	 * @author foss-meiying
	 * @date 2014-10-09 上午11:05:51
	 * @param dto
	 * @return
	 * @see
	 */
	 List<DeliverbillDetailDto> queryDeliverbillWaybillNo(DeliverbillDetailDto dto);
	/**
	 * 根据运单号查询财务信息
	 * @author foss-meiying
	 * @date 2014-10-09 上午11:05:51
	 * @param waybillNo
	 * @return
	 * @see
	 */
	 FinancialDto queryFinanceSign(String waybillNo);
	 /**
	  * 根据条件查询到达联信息
	  * @author foss-meiying
	  * @date 2014-10-09 上午11:05:51
	  * @return
	  * @see
	  */
	 ArriveSheetEntity queryArriveSheetByParams(String arrivesheetNo);
	 /**
	  * 派送处理   --->签收确认
	  * @author foss-meiying
	  * @date 2014-10-09 上午11:05:51
	  * @param arriveSheet 到达联信息
	  * @param financialDto 财务信息
	  * @param currentInfo 当前登录人的信息
	  * @param signDetailEntitys 流水号集合
	  * @return
	  * @see
	  */
	 String addNoPdaSign(ArriveSheetEntity arriveSheet,FinancialDto financialDto,CurrentInfo currentInfo,List<SignDetailEntity> signDetailEntitys);
	 /**
	  * 派送处理---送货确认  录入拉回货物信息量
	  * @author foss-meiying
	  * @date 2014-10-09 上午11:05:51
	  * @return
	  * @see
	  */
	  int addPullbackGoods(DeliverbillDetailDto dto,CurrentInfo currentInfo);
	 /**
	  * 查询当前派送单中所有的运单到达联是否都完成了签收确认
	  * @author foss-meiying
	  * @date 2014-10-09 上午11:05:51
	  * @param dto
	  * @return
	  * @see
	  */
	 List<DeliverbillDetailDto> queryArrivesheetIsSign(DeliverbillDetailDto dto);
	 /**
	  * 录入拉回货物信息
	  * @author foss-meiying
	  * @date 2014-10-09 上午11:05:51
	  * @param dto.deliverbillNo 派送单编号
	  * @param dto.active 是否有效
	  * @param dto.createUserName 创建人
	  * @param dto.createUserCode 创建人编码
	  * @param dto.createTime 创建时间
	  * @return 1          没有拉回货物
	  * @return 2         录入拉回货物成功
	  * @see
	  */
	 int addPullbackInfo(DeliverbillDetailDto dto, String transferCenter);
	 /**
	  * 根据派送单号,运单号得到流水号集合
	  * @author foss-meiying
	  * @date 2014-10-09 上午11:05:51
	  * @param waybillNo 运单号
	  * @param deliverBillNo 派送单号
	  * @return
	  * @see
	  */
	 List<SignDetailEntity> queryStock(String waybillNo,String deliverBillNo);
	 /**
	  * 有pda时，录入到达联签收人,修改运单签收结果（时间建模）
	  * @author foss-meiying
	  * @date 2014-10-09 上午11:05:51
	  * @param arriveSheet
	  * @return
	  * @see
	  */
	 boolean addPdaSignInfo(ArriveSheetEntity arriveSheet);
	 /**
	 * 
	 * 根据运单号查询部分派送单信息（综合查询提供到达联派送中接口）
	 * 
	 * @return 
	 * @author foss-meiying
	 * @date 2014-10-09 上午11:05:51
	 */
	 List<DeliverbillDetailDto> queryPartDeliverBillByWaybillNo(DeliverbillDetailDto dto);
	 
	 /**
	  * 根据派送单号,运单号得到流水号集合（子母件）
	  * @author 268377 yanling
	  * @date 2015-9-11 上午 10:04:22
	  * @param waybillNos
	  * @param deliverBillNo
	  * @return
	  */
	 List<SignDetailEntity> queryCZMStock(List<String> waybillNos,String deliverBillNo);
	 /**
	  * 派送处理 子母件 
	  * @author 268377 yanling
	  * @param arriveSheet
	  * @param financialDto
	  * @param currentInfo
	  * @param signDetailEntitys
	  * @return
	  */
	 String addNoPdaCZMSign(ArriveSheetEntity arriveSheet,FinancialDto financialDto, CurrentInfo currentInfo,List<SignDetailEntity> signDetailEntitys,TwoInOneWaybillDto twoInOneWaybillDto);
	/**
		 * 子母件有pda时，录入到达联签收人,修改运单签收结果（时间建模）
		 * @author foss-yanling
		 * @date 2015-9-16 上午9:18:45
		 * @param arriveSheet
		 * @return
		 * @see
		 */
	boolean addCZMPdaSignInfo(ArriveSheetEntity arriveSheet,TwoInOneWaybillDto twoInOneWaybillDto);
	
	/**
	 * 合伙人快递派送处理---待处理模块下的运单信息
	 * @author gpz
	 * @date 2016年1月28日
	 * @param DeliverbillDetailDto 派送单明细dto
	 * @return List<DeliverbillDetailDto>
	 */
	List<DeliverbillDetailDto> queryPtpExpDeliverbillWaybillNo(
			DeliverbillDetailDto deliverbillDetailDto);
}