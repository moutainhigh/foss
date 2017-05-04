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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/service/IRepaymentService.java
 * 
 * FILE NAME        	: IRepaymentService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AccountBookRequestDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirAgencyQueryDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.FinancialDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RepaymentArriveDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RepaymentDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.RepaymentException;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PaymentSettlementDto;
/**
 * 
 * 货款结清Service
 * @author 043258-foss-zhaobin
 * @date 2012-11-20 下午3:51:02
 */
public interface IRepaymentService extends IService 
{
	/**
	 * 
	 * 返回待处理列表
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-20 下午3:49:50
	 */
	List<AirAgencyQueryDto> queryAirAgencyQueryDtoList(AirAgencyQueryDto airAgencyQueryDto);

	/**
	 * 
	 * 根据运单号返回详细信息
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-21 下午2:03:40
	 */
	RepaymentDto queryPaymentByWaybillNo(String waybillNo);
	
	/**
	 * 
	 * 根据运单号返回详细信息--vts整车
	 * @author 306579 guoxinru
	 * @date 2016-05-10
	 */
	RepaymentDto vtsQueryPaymentByWaybillNo(String waybillNo);
	
	/**
	 * 选中运单是否有签收单原件返回
	 * @param waybillNo
	 */
	WaybillDto queryWaybillByBack(String waybillNo);
	
	/**
	 * 
	 * 新增付款信息
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-26 下午2:57:30
	 */
	void addPaymentInfo(RepaymentEntity repaymentEntity,CurrentInfo currentInfo,WaybillDto dto);
	/**
	 * 张新 2015-2-9
	 * 原单结清新增付款信息
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-26 下午2:57:30
	 */
	void addOriginalPaymentInfo(RepaymentEntity repaymentEntity,CurrentInfo currentInfo, String returnType, String waybillNo, TwoInOneWaybillDto twoInOneWaybillDto);
	
	/**
	 * 
	 * 退款操作
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-27 下午2:30:42
	 */
	boolean refundPaymentInfo(String waybillNo);
	
	/**
	 * 
	 * 是否结清货款
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-27 下午7:22:40
	 */
	boolean isPayment(String waybillNo);
	
	/**
	 * 
	 *  签收出库调用
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-27 下午7:22:40
	 */
	boolean paymentOperate(RepaymentEntity repaymentEntity);
	
	/**
	 * 
	 * 派送处理调用
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-27 下午7:22:40
	 */
	void deliverOperate(RepaymentEntity repaymentEntity , CurrentInfo currentInfo);
	
	/**
	 * 
	 * 校验操作人密码
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-26 下午2:57:30
	 */
	void validatePaymentInfo(RepaymentEntity repaymentEntity,CurrentInfo currentInfo,WaybillDto waybillDto);
	
	/**
	 * 
	 *  job批量操作调用结算接口
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-30 上午10:32:19
	 */
	void batchjobs();
	
	/**
	 * 
	 * 签收变更接口调用反结清
	 * @author 043258-foss-zhaobin
	 * @date 2012-12-19 下午2:56:44
	 */
	String changeRepayment(RepaymentEntity oldRepayment , RepaymentEntity newRepayment , CurrentInfo currentInfo);

	/**
	 * 
	 * 反签收调用反结清
	 * @author 043258-foss-zhaobin
	 * @date 2012-12-19 下午2:59:53
	 */
	void reverseRepayment(List<RepaymentEntity> list , CurrentInfo currentInfo);
	
	/**
	 * 
	 * 根据运单号查询付款信息
	 * @author 043258-foss-zhaobin
	 * @date 2012-12-24 下午2:45:59
	 */
	List<RepaymentArriveDto> queryArriveFeeByWaybillNo(String waybillNo);
	
	/**
	 * 
	 * 是否存在付款信息
	 * @author 043258-foss-zhaobin
	 * @date 2013-1-28 下午4:11:10
	 */
	boolean isExistRepayment(String waybillNo);
	/**
	 * 根据运单号获取最后插入的 一条付款记录（查询付款方式，实付运费）
	 * @author foss-meiying
	 * @date 2013-2-21 下午2:28:26
	 * @param repayment
	 * @return
	 * @see
	 */
	RepaymentEntity queryRepaymentTypebyNo(RepaymentEntity repayment);
	
	/**
	 * 
	 * 查询汇款信息 
	 * @author 043258-
	 *				foss-zhaobin
	 * @date 2013-4-11 
	 *				下午4:28:31
	 * @param remitTransNum
	 * @return
	 * @see
	 */
	BigDecimal queryTransfer(String remitTransNum,String paymentType,String waybillNo);
	
	/**
	 * 
	 * 更新job id.
	 * @author 043258-foss-zhaobin
	 * @date 2013-6-5 上午10:09:52
	 */
	int updateRepaymentForJob(String uuid, Date currentTime);
	
	/**
	 * job 调用结清货款接口.
	 *
	 * @param uuid 
	 * 		the uuid
	 * @throws RepaymentException 
	 * 		the repayment exception
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-12-20
	 * 		 下午6:50:50
	 */
	void operatepaymentSettlementForJob(String uuid, Date currentTime);
	
	/**
	 * 
	 * 添加成功日志
	 * @author 043258-foss-zhaobin
	 * @date 2013-6-7 下午5:47:12
	 */
	void addJobSuccess(String repaymentid);
	
	/**
	 * 
	 * 添加异常日志
	 * @author 043258-foss-zhaobin
	 * @date 2013-6-7 下午5:47:30
	 */
	void addJobException(String repaymentid, RepaymentException e);
	
	/**
	 * 
	 * 修改付款数据
	 * @author ibm-lizhiguo
	 * @date 2012-11-16 下午3:46:47
	 */
	int updateRepayment(RepaymentEntity record);
	
	/**
	 * 
	 * 调用结算接口.
	 * @author 043258-foss-zhaobin
	 * @date 2013-6-17 上午11:22:41
	 */
	String paymentSettlement(RepaymentEntity repaymentEntity, CurrentInfo currentInfo, TwoInOneWaybillDto twoInOneWaybillDto);
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService.addOtherRevenueInfo
	 * @Description:调用结算接口添加小票信息
	 *
	 * @param repaymentEntity
	 * @param currentInfo
	 *
	 * @version:v1.0
	 * @author:130376-YANGKANG
	 * @date:2014-5-20 下午1:48:17
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-5-20    130376-YANGKANG      v1.0.0         create
	 */
	void addOtherRevenueInfo(RepaymentEntity repaymentEntity,WaybillDto waybillDto, CurrentInfo currentInfo);
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.sign.server.service.impl.RepaymentService.storageChargeByOtherRevenue
	 * @Description:根据运单号和发票产生类别查询小票记录集合  计算出该运单已经产生小票记录的保管费总和
	 *
	 * @param wayBillNo
	 * @return
	 *
	 * @version:v1.0
	 * @author:130376-YANGKANG
	 * @date:2014-5-21 下午3:20:07
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-5-21    130376-YANGKANG      v1.0.0         create
	 */
	BigDecimal queryStorageChargeWithOtherRevenue(String wayBillNo);
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.sign.server.service.impl.RepaymentService.querystorageChargeUpdateInfo
	 * @Description:调用偏线空运服务中的方法查询仓储费
	 *
	 * @param wayBillNo
	 * @return
	 *
	 * @version:v1.0
	 * @author:130376-YANGKANG
	 * @date:2014-5-28 下午4:26:15
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-5-28    130376-YANGKANG      v1.0.0         create
	 */
	WaybillDto querystorageChargeUpdateInfo(String wayBillNo);
	/**
	 * 根据运单号更新运单的保管费
	 *
	 * @Function: com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService.updatestorageCharge
	 * @Description:
	 *
	 * @param wayBillNo
	 *
	 * @version:v1.0
	 * @author:130376-YANGKANG
	 * @date:2014-5-28 下午6:10:52
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-5-28    130376-YANGKANG      v1.0.0         create
	 */
	int updatestorageCharge(WaybillDto dto,CurrentInfo currentInfo);
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService.isLack
	 * @Description:调用中转接口查询运单是否有上报差错
	 *
	 * @param waybillNo
	 * @return
	 *
	 * @version:v1.0
	 * @author:130376-YANGKANG
	 * @date:2014-6-22 下午6:04:53
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-6-22    130376-YANGKANG      v1.0.0         create
	 */
	 boolean isLack(String waybillNo);

	 /**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService.isLack
	 * @Description:提前找货相关接口
	 *
	 * @param WaybillDto
	 * @return
	 *
	 * @version:v1.0
	 * @author:foss-yuting
	 * @date:2014-11-25 下午6:04:53
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-11-25     foss-yuting     v1.0.0         create
	 */
	String updateInadvanceGoodsByRepayment(WaybillDto dto);
	
	
	/**
	 * 根据运单号查询财务信息
	 * @date 2015-2-9 下午7:13:50 运单号
	 * @return
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService
	 * 	#queryFinanceSignRookie(java.lang.String)
	 */
	FinancialDto queryFinanceSign(String waybillNo);
	
	/**
	 * 
	 * 是否存在过付款信息
	 * @author 043258-foss-zhaobin
	 * @date 2015-3-20 下午4:11:10
	 */
	boolean isHaveRepayment(String waybillNo);
	/**
	 * 查询母单的一条有效的付款信息；
	 * 如果子单没做结清，快递自提自动结清就copy母单的结清信息
	 * @author 231438-chenjunying
	 * @date 2015-08-29 上午9:45:19
	 */
	public RepaymentEntity queryOneRepaymentInfo(RepaymentEntity entity);
	
	/**
	 *  合伙人丢货、弃货、违禁品-签收冲销代收货款-同步接口
	 * @param dto 付款信息
	 * @param currentInfo 当前登录信息
	 * @param codAmount 代收货款金额
	 */
	public void reversPtpCOD(PaymentSettlementDto dto, CurrentInfo currentInfo, BigDecimal codAmount);
	
	/**
	 * 当付款方式为余额时返回客户的余额  add by 353654
	 * @throws Exception 
	 */
	public BigDecimal queryBalanceAmountByConsigneeCode(AccountBookRequestDto dto);
	
	/**
	 * 根据运单号查询运单所有的付款信息
	 * @author 243921-foss-zhagntingting
	 * @date 2017-03-02 17:50:42
	 */
	List<RepaymentEntity> queryRepaymentListByWaybillNo(String waybillNo);
	
	/**
	 * 添加结清货款信息
	 * @author 243921
	 * @date 2017年3月5日 下午6:01:25
	 * @param repaymentEntity
	 * @see
	 */
	public void addPaymentInfo(RepaymentEntity repaymentEntity);

}