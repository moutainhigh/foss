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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/exception/SignException.java
 * 
 * FILE NAME        	: SignException.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;
/***
 * 
 * 异常处理
 * @author foss-meiying
 * @date 2012-10-17 上午11:20:28
 * @since
 * @version
 */
public class SignException extends BusinessException {

	//waybillSignResultService
	public static final String OBJECT_IS_NOT_NULL="pkp.sign.signException.objectIsNotNull";//传入的对象为空，不能进行签收变更操作
	public static final String SIGN_SITUATION_IS_NOT_NULL="pkp.sign.signException.signSituationIsNotNull";//变更后的签收情况为空！不能执行变更操作

	//ContrabandSignService
	public static final String WAYBILLNO_IS_NOT_NULL="pkp.sign.signException.waybillnoIsNotNull";//运单号不能为空
	public static final String SUSPICION_CONTRABAND_PROCESS_RESULT="pkp.sign.signException.suspicionContrabandProcessResult";//违禁品申报进行中
	public static final String NO_CONTRABAND_PROCESS_RESULT="pkp.sign.signException.noContrabandProcessResulT";//违禁品申报审核未通过
	public static final String NO_REPORT_CONTRABAND="pkp.sign.signException.noReportContraband";//运单未申报违禁品
	public static final String STOCK_GOODS_QTY_ZERO="pkp.sign.signException.stockGoodsQtyZero";//该运单当前库存的件数为0
	public static final String WAYBILLNO_NULL="pkp.sign.signException.waybillnoNull";//运单号无效，请在综合查询查询运单状态是否已作废或已终止
	public static final String ACTUAL_FREIGHT_NULL="pkp.sign.signException.actualFreightNull";//根据运单号查询actualFreight没有数据！
	
	public static final String SMS_SIGN_NOTICE_SUCCESS="pkp.sign.signException.smsOkSignOkNoticeOk";//签收出库成功！发货人短信，收货人短信，在线通知都发送成功！
	public static final String SIGN_SMS_OK_NOTICE_FAILED="pkp.sign.signException.signSmsOkNoticeFai";//签收出库成功!收货人短信，发货人短信发送成功;在线通知发送失败
	public static final String SIGN_RECEIVESMS_NOTICE_OK_DELIVERYSMS_FAILED="pkp.sign.signException.signReceiveSmsNoticeOkDeliverySmsFal";//签收出库成功！收货人短信，在线通知发送成功，发货人短信发送失败！
	public static final String SIGN_RECEIVESMS_OK_DELIVERYSMS_NOTICE_FAILED="pkp.sign.signException.signReceiveSmsOkNoticeDeliverySmsFal";//签收出库成功!收货人短信发送成功;在线通知，发货人短信发送失败！
	public static final String SIGN_DELIVERYSMS_OK_RECEIVESMS_NOTICE_FAILED="pkp.sign.signException.signDeliverySmsOkNoticeReceiveSmsFal";//签收出库成功!发货人短信发送成功;在线通知，收货人短信发送失败！
	public static final String SIGN_NOTICE_OK_RECEIVESMS_DELIVERYSMS_FAILED="pkp.sign.signException.signNoticeOkDeliverySmsReceiveSmsFal";//签收出库成功！在线通知发送成功;发货人短信，收货人短信发送失败！
	public static final String SIGN_DELIVERYSMS_NOTICE_OK_RECEIVESMS_FAILED="pkp.sign.signException.signDeliverySmsNoticeOkReceiveSmsFal";//签收出库成功!发货人短信,在线通知发送成功;收货人短信发送失败！
	public static final String SIGN_OK_DELIVERYSMS_NOTICE_RECEIVESMS_FAILED="pkp.sign.signException.signOkDeliverySmsNoticeReceiveSmsFal";//签收出库成功!发货人短信，收货人短信，在线通知发送都失败！
	
	public static final String SIGN_NOTICE_OK="pkp.sign.signException.signNoticeOk";//签收出库成功！在线通知发送成功！
	public static final String SIGN_OK_NOTICE_FAILED="pkp.sign.signException.signOkNoticeFal";//签收出库成功！在线通知发送失败！

	public static final String MESS_CONTENT_ISNULL="pkp.sign.signException.messContentIsnull";//短信内容为空
	public static final String NO_SMS_TEMPLATES="pkp.sign.signException.noSmsTemplates";//没有对应的短信模版   
	public static final String SIGN_SUCCESS="pkp.sign.signException.signSuccess";//签收出库成功!
	public static final String OBJECT_IS_NULL="pkp.sign.signException.objectIsNull";//传入的对象为空!
	public static final String QUERY_STAYHANDOVER_DETAIL_IS_NULL="pkp.sign.signException.stayhandoverdetailIsNull";//根据运单号查询交接明细为空!
	
	public static final String SIGNTIME_ISNOT_SYSTEMDATE="pkp.sign.signException.signTimeIsNotSystemDate";//当前电脑时间有误，请调整日期为{0}
	
	
	public static final String SIGN_NOTICE_SUCCESS="pkp.sign.signException.SignOkNoticeOk";//签收出库成功！发货人短信,在线通知都发送成功！
	public static final String SIGN_DELIVERYSMS_OK_NOTICE_FAILED="pkp.sign.signException.signDeliveryOkNoticeFai";//签收出库成功!发货人短信发送成功;在线通知发送失败
	public static final String SIGN_NOTICE_OK_DELIVERYSMS_FAILED="pkp.sign.signException.signNoticeOkDeliverySmsFal";//签收出库成功！在线通知发送成功，发货人短信发送失败！
	public static final String SIGN_DELIVERYSMS_NOTICE_FAILED="pkp.sign.signException.signOkNoticeDeliverySmsFal";//签收出库成功!在线通知，发货人短信发送失败！
	/**
	 * 签收失败，请重新查询一下!
	 */
	public static final String SIGN_FAILED="pkp.sign.signException.signFailed";//签收操作失败，请在当前页面重新查询!
	/**张新
	 * 签收限制!
	 */
	public static final String SIGN_LIMIT="pkp.sign.signException.signLimit";//该单已外发，如需人工签收，请于“营业部外发单号绑定”中解除绑定
	/**
	 * 当前运单操作中，请稍后再试
	 */
	public static final String WAYBILL_LOCKED="pkp.sign.signException.waybill.locked";//当前运单操作中，请稍后再试
	public static final String SIGN_CHECK_REVIEWING = "pkp.sign.rfc.check.reviewing";// 该运单正在审核中，请联系区域财务部审核后签收操作
	public static final String SIGN_RFC_SIGN_APPROVALIN = "pkp.sign.signException.reviewingNotSign";// 该运单有到达更改还没有审批，请审批后再签收！
	public static final String APPLICATION_SIGN_RFC_ACCEPTED="pkp.sign.applicationSignRfc.accepted";//当前运单已审核，请查询后重新处理
	public static final String CURRENT_ORG_CODE_IS_FALSE="pkp.sign.signException.currentCodeFalse";//当前登录部门与运单的目的站不一致，不能签收，请确认运单目的站是否需要转货
	public static final String VEHICLEASSEMBLEBILL_INVALID="pkp.sign.signException.vehicleassemblebill.invalid";//整车运单不存在有效的配载单，当前部门不可进行签收操作！
	public static final String WAYBILL_IS_SIGN="pkp.sign.signException.waybillIsSign";//收货部门只能整票签收，不能进行分批签收
	public static final String WVH_NOT_PARTIAL_SIGN="pkp.sign.signException.Wvh_not_partialSign";//收货部门整车签收，只能整票签收，不能部分签收,请确认当前到达联件数是否为运单开单件数！
	private static final long serialVersionUID = 1L;
	
	/**
	 * 签收变更
	 */
	//该运单有到达更改还没有审批，请审批后再次提交申请。
	public static final String IS_NOT_APPROVAL="pkp.sign.applicationSignRfcException.is.not.approval";
	//子件没有全部反签收，请先把子件全部反签收，再进行母件反签收。
	public static final String SON_NOT_REVERSE="pkp.sign.applicationSignRfcException.son.not.reverse";
	//子件没有全部反结清货款，请先把子件全部反结清，再进行母件反结清。
	public static final String SON_NOT_REPAYMENT="pkp.sign.applicationSignRfcException.son.not.repayment";
	
	/**
	 * 有一个参数的构造函数
	 * @author foss-meiying
	 * @date 2013-3-13 下午2:55:24
	 * @param code
	 */
	public SignException(String code) {
		super();
		this.errCode = code;
	}
	/**
	 * 有两个参数的构造函数
	 * @author foss-meiying
	 * @date 2013-3-13 下午2:55:18
	 * @param code
	 * @param cause
	 */
	public SignException(String code, Throwable cause) {
		super(code,cause);
		this.errCode = code;
	}
	/**
	 * 有参构造函数
	 * @author foss-meiying
	 * @date 2013-3-11 下午2:10:12
	 * @param code
	 * @param args
	 */
	public SignException(String code,Object... args) {
		super(code,args);
	}
}