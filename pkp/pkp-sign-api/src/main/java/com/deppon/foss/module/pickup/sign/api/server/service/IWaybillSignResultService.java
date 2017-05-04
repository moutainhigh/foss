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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/service/IWaybillSignResultService.java
 * 
 * FILE NAME        	: IWaybillSignResultService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.esb.inteface.domain.foss2ptp.PartnerUpdateTakeEffectTimeRequest;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.PdaReturnDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaPullbackgoodsDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirWaybillCondition;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.ContrabandInfoDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.LostCargoInfoDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.OrderStatusToCrmDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.PullbackRenewalDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillSignResultDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;

/**
 * 运单签收结果
 * @author foss-meiying
 * @date 2012-10-23 上午11:47:29
 * @since
 * @version
 */
public interface IWaybillSignResultService extends IService {
	/**
	 * 
	 *保存运单签收结果信息
	 * @author foss-meiying
	 * @date 2012-10-16 下午4:45:47
	 * @param waybillSignResultEntity
	 * @return
	 * @see
	 */
	 Integer addWaybillSignResult(WaybillSignResultEntity waybillSignResultEntity);
	/**
	 * 修改运单签收结果部分信息
	 * @author foss-meiying
	 * @date 2012-10-23 上午11:41:30
	 * @return
	 * @see
	 */
	int updateWaybillSignResult(WaybillSignResultEntity entity);
	
	/**
	 * 查询运单表里的货物总件数 
	 * @author foss-meiying
	 * @date 2012-10-25 上午10:09:34
	 * @return
	 * @see
	 */
	Integer queryWaybillQty(WaybillSignResultEntity entity);
	/**
	 * 根据运单号查询运单签结果里的运单信息
	 * @author foss-meiying
	 * @date 2012-12-10 下午5:03:21
	 * @param entity.waybillNo 运单号
	 * @param entity.active 是否有效 
	 * @return
	 * @see
	 */
	WaybillSignResultEntity queryWaybillSignResultByWaybillNo(WaybillSignResultEntity entity);
	
	/**
	 * 根据运单号，active = 'Y'，和签收状态，查询第一次全部签收的签收结果信息
	 * @author foss-bieyexiong
	 * @date 2015-04-21 下午16:28:01
	 * @param entity
	 * @return 
	 */
	WaybillSignResultEntity queryFirstSignAllByEntity(WaybillSignResultEntity entity);
	
	/**
	 * 判断该运单是否已经录入运单签收结果信息，如果录入，作废当前运单号.
	 * 完善最新运单签收结果数据返回。 
	 * @author foss-meiying
	 * @date 2013-1-18 下午2:08:21
	 * @param entity
	 * @param waybill
	 * @param modifyTime
	 * @return
	 * @see
	 */
	WaybillSignResultEntity checkWaybillSignResultSign(ArriveSheetEntity entity,WaybillEntity waybill,Date modifyTime,CurrentInfo currentInfo);
	/**
	 * 根据运单号查询运单部分信息
	 * @author foss-meiying
	 * @date 2012-11-30 下午4:43:03
	 * @param waybillNo
	 * @return
	 * @see
	 */
	WaybillDto queryWaybillActualFreightPartByWaybillNo(String  waybillNo);
	/**
	 * 根据主键查询运单签收结果
	 * @author foss-meiying
	 * @date 2012-12-7 上午11:43:53
	 * @param id
	 * @return
	 * @see
	 */
	WaybillSignResultEntity queryWaybillSignResultById(String id);
	/**
	 * 根据签收时间查询签收结果
	 * @author foss-caodajun
	 * @date 2017-1-4 上午11:43:53
	 * @param signStartTime 签收开始时间
	 * @param signEndTime  签收结束时间
	 * @return
	 * @see
	 */
	List<PartnerUpdateTakeEffectTimeRequest> queryWaybillSignResultBySignTime(Date signTimeStart,Date signTimeEnd);
	
	/**
	 * 根据运单号查询运单签收结果里第一次添加的签收时间
	 * @author foss-meiying
	 * @date 2012-12-11 上午9:16:13
	 * @param waybillNo 运单号
	 * @return
	 * @see
	 */
	Date queryFirstSignTimeByWaybillNo(String waybillNo);
	/**
	 * 
	 * <p>更新运单签收结果<br />
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-17
	 * @param waybillSignResultEntity
	 * @return
	 * int
	 */
	void updateWaybillSignResultById(
			WaybillSignResultEntity waybillSignResultEntity);
	/**
	 * 根据传入的待撤销组织CODE,返回当前组织的未签收票数
	 * @author foss-meiying
	 * @date 2012-12-17 下午3:00:08
	 * @param lastLoadOrgCode 最终配载部门
	 * @return 
	 * @see
	 */
	int queryNotSignGoodsQtyByOrgCode(String lastLoadOrgCode);
	/**
	 * 为签收变更提供接口   偏线/空运
	 * 更新运单签收结果(t_srv_waybill_sign_result)
	 * 更新AcutalFreight中的“到达未出库件数”=“到达未出库件数”-（newWaybillSignResult的件数-oldNewWaybillSignResult的件数）
	 * @author foss-meiying
	 * @date 2012-12-19 下午9:06:06
	 * @param oldNewWaybillSignResult
	 * @param newWaybillSignResult
	 * @see
	 */
	void changeWaybillSignResult(WaybillSignResultEntity oldNewWaybillSignResult,WaybillSignResultEntity newWaybillSignResult);
	
	/**
	 * 
	 * 系统发送在线通知给发货部门，通知内容包括：货物单号、此货签收时间、签收件数、签收人、是否有异常
	 * @author foss-meiying
	 * @date 2012-12-21 上午10:00:25
	 * @param currentinfo  当前登录人信息
	 * @param receiveOrgCode  接收方组织编码
	 * @param noticeContext 通知内容
	 * @return
	 * @see
	 */
	boolean sendNotice(CurrentInfo currentinfo,String receiveOrgCode,String noticeContext);
	/**
	 * 调用发送短信,在线通知
	 * @author foss-meiying
	 * @date 2013-3-1 上午9:50:53
	 * @param waybill 运单
	 * @param currentinfo 当前登录人信息
	 * @param dto
	 * @return
	 * @see
	 */
	String sendMessNotice(WaybillEntity waybill,CurrentInfo currentinfo,WaybillSignResultDto dto);
    /**
     * 系统发送短信至发货人、收货人，通知内容包括：货物单号、此货签收时间、签收件数、签收人、是否有异常
     * @author foss-meiying
     * @date 2012-12-22 下午3:06:10
     * @param currentInfo
     * @param customerSms 内容
     * @param isToReceiveCustomer  是否给收货人发送短信
     * @param waybill
     * @return
     * @see
     */
    boolean sendMess(CurrentInfo currentInfo,String customerSms,boolean isToReceiveCustomer,String moduleCode,WaybillEntity waybill);
    /**
	 * 根据 派送出库时间开始日期 派送出库时间结束日期查询运单签收结果里的运单号
	 * @author foss-meiying
	 * @date 2012-12-24 下午2:12:52
	 * @param signTimeStart 起始时间
	 * @param signTimeEnd 结束时间
	 * @return
	 * @see
	 */
    List<String> queryWaybillNoByCondition(Date signTimeStart,Date signTimeEnd);
	/**
	 * 
	 * 根据运单号查询运单信息
	 * @param waybillNo 运单号
	 * @author 038590-foss-wanghui
	 * @date 2012-12-25 下午9:05:50
	 */
	AirWaybillDto queryWaybillInfoForEdi(AirWaybillCondition condition);
	/**
	 * 通过id 作废运单签收结果
	 * @author foss-meiying
	 * @date 2013-1-23 下午4:38:41
	 * @param id
	 * @param modifyTime
	 * @return
	 * @see
	 */
	int invalidWaybillSignResult(String id,Date modifyTime);
	
	/**
	 * 
	 * 查询中转库存流水号  通过运单编号,部门编码
	 * @author foss-meiying
	 * @date 2012-10-19 下午6:45:19
	 * @param dto (运单号,部门编码)
	 * @return
	 */
	 List<SignDetailEntity> queryOptStockSerinalNo(ContrabandInfoDto dto);
    /**
	 * 据运单号集合 查询运单签结果里的运单编号
	 * @author foss-meiying
	 * @date 2012-10-25 上午10:09:34
	 * @return
	 * @see
	 */
	List<String> queryWaybillSignResultWaybillNos(List<String> waybillNos);
	
	/**
	  * 根据传入的运单号,入库时间起止查询满足条件的运单信息
	  * @author foss-meiying
	  * @date 2013-2-3 下午3:35:04
	  * @param dto
	  * @param start
	  * @param limit
	  * @return
	  * @see
	  */
	 List<LostCargoInfoDto> queryLostCargoInfoByCondition(LostCargoInfoDto dto,int start, int limit);
	 /**
	  * 根据传入的运单号,入库时间起止查询满足条件的运单信息总数
	  * @author foss-meiying
	  * @date 2013-2-3 下午3:35:19
	  * @param dto
	  * @return
	  * @see
	  */
	 Long queryLostCargoCountByCondition(LostCargoInfoDto dto);
	 /**
	  * 签收时执行应用监控（先根据运单号查询相应信息）
	  * @author foss-meiying
	  * @date 2013-2-22 下午3:20:58
	  * @param currentInfo
	  * @param waybill
	  * @see
	  */
	 void signCounterByWaybillNo(CurrentInfo currentInfo, WaybillEntity waybill);
	/**
	 * 调用CRM修改订单接口更新订单状态.
	 * @author foss-meiying
	 * @date 2013-3-13 下午4:08:26
	 * @param orderNo
	 * 		订单号
	 * @param currentinfo
	 * 		当前登录人的信息
	 * @param entity 运单签收结果
	 * 		entity.waybillNo
	 * 		 运单号
	 * 		entity.signSituation 
	 * 		签收情况
	 * 		entity.signNote 
	 * 		签收备注
	 * @see
	 */
	 void updateCrmOrder(String orderNo,CurrentInfo currentinfo,WaybillSignResultEntity entity);
	 /**
	  * 提供给接送货的接口，获取订单的拒收状态，推送给crm  DN201602220004
	  * @author 243921
	  * @date 2016-03-03上午9:11:35
	  * @param dto
	  */
	 void updateCrmOrderForRefuse(OrderStatusToCrmDto dto);
	 /**
	  * 弃货签收接口
	  * @author foss-meiying
	  * @date 2013-4-11 上午9:56:09
	  * @param waybillNo
	  * @see
	  */
	 void addAbandonGoodsSign(String waybillNo,CurrentInfo current);
	 /**
	  * 据运单号查询运单签结果里的运单信息
	  * @author foss-meiying
	  * @date 2013-7-2 下午3:35:19
	  * @param dto
	  * @return
	  * @see
	  */
	 List<WaybillSignResultEntity> queryWaybillSignResultLit(WaybillSignResultEntity entity);
	 /**
		 * 获取短信信息
		 * @author foss-meiying
		 * @date 2013-1-8 上午11:34:39
		 * @param entity
		 * 			 * 			waybillNo
		 * 			运单号
		 * 			signSituation
		 * 			签收情况
		 * 			deliverymanName
		 * 			提货人名称
		 * 			identifytype
		 * 			证件类型
		 * 			identifyCode
		 * 			证件号码
		 * 			signGoodsQty
		 * 			签收件数
		 * 			isException
		 * 			是否有异常
		 * 			situation
		 * 			签收情况
		 * @param currentInfo
		 * 			currentInfo.operate 
		 * 				操作人
		 *          currentInfo.operatorCode 
		 *          	操作人编码
		 *          currentInfo.operateOrgName 
		 *          	操作部门名称
		 *          currentInfo.operateOrgCode 
		 *          	操作部门编码
		 * @param smsCode
		 * 			短信编码
		 * @return
		 * @see
		 */
	 String getSmsContent(WaybillSignResultDto entity, CurrentInfo currentInfo,String smsCode);
	 /**
	  * 校验是否需要将发票信息传输至发票系统 需要满足的条件（是否第一次签收，预付金额是否大于0，运单类型是否为电子发票）
	  * @param entity
	  * @param actual
	  * @return
	  */
	 boolean isNeedSendInvoiceInfo(WaybillEntity entity, ActualFreightEntity actual);
	 /**
	  * FOSS将发票信息传输至发票系统
	  * 159231---meiying
	  * @param entity
	  * @param actual
	  */
	 void sendInvoiceInfo(WaybillEntity entity,ActualFreightEntity actual);
	 /**
	  * DMANA-9716
	  * FOSS自动将正常签收的 投诉运单变更为异常签收
	  * @author 231438--chenjunying
	  * @param entity
	  * @param actual
	  */
	 void changeExceptionSignResult(WaybillSignResultEntity oldwaybillSignResultEntity,WaybillSignResultEntity entity);
	 /**
	  * 调用发送短信接口,通知发货人
	  * @author foss-sunyanfei
	  * @date 2015-7-11 下午17:08:12
	  * @param dto
	  * @param pdaReturnDto
	  * @param waybill
	  * @param orderEntity
	  * @param currentinfo
	  * @param isDelivery
	  * @return
	  */
	 boolean sendMessCustomer(PdaPullbackgoodsDto dto,PdaReturnDto pdaReturnDto,WaybillEntity waybill,DispatchOrderEntity orderEntity,CurrentInfo currentinfo,boolean isDelivery);
	 /**
	 * 根据运单号，是否有效 拿到子母件信息
	 *   --重新给子件集合赋值为子母件集合
	 * foss-231438-chenjunying
	 * @param params
	 * @return
	 */
	 TwoInOneWaybillDto queryWaybillRelateByWaybillOrOrderNo(Map<String,Object> params);
	/**
	 * @author 231438
	 * 查询运单是否有派送中状态，给接送货GUI端使用
	 * @param ArriveSheetEntity entity
	 */
	List<ArriveSheetEntity> queryArriveSheetByWaybillNo(ArriveSheetEntity entity);
	/**
	 * 提供给中转：外发时校验是否已签收
	 * @author 231438
 	 */
	WaybillSignResultEntity checkWaybillSignResult(WaybillSignResultEntity entity);
	
	/**
	* add by 353654 DN201608260005 FOSS回传派送拉回原因到OMS的接口
	* 
	* @param PullbackRenewalDto dto
	*/
	void updatePullBackStatus(PullbackRenewalDto dto);
}