/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 038590-foss-wanghui
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
 * PROJECT NAME	: pkp-order-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/define/DispatchOrderStatusConstants.java
 * 
 * FILE NAME        	: DispatchOrderStatusConstants.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.define;

import java.util.HashMap;
import java.util.Map;


/**
 * 调度订单状态常量
 * @author 038590-foss-wanghui
 * @date 2012-10-20 上午10:40:35
 */
public class DispatchOrderStatusConstants {

	/**
	 * 订单处理状态
	 */
	// 未处理
	public static final String STATUS_NONE_HANDLE = "NONE_HANDLE";
	
	// 已派车
	public static final String STATUS_DISPATCH_VEHICLE = "DISPATCH_VEHICLE";
	
	// PDA接收
	public static final String STATUS_PDA_ACCEPT = "PDA_ACCEPT";
	
	// 接货中
	public static final String STATUS_PICKUPING = "PICKUPING";
	
	// 待改接
	public static final String STATUS_AGAIN_PICKUP = "AGAIN_PICKUP";
	
	// 已退回
	public static final String STATUS_RETURN = "RETURN";

	// 揽货失败
	public static final String STATUS_PICKUP_FAILURE = "PICKUP_FAILURE";
	
	// 已开单
	public static final String STATUS_WAYBILL = "WAYBILL";
	
	// 已撤销
	public static final String STATUS_REVOCATION = "REVOCATION";
	// 已作废
	public static final String STATUS_CANCEL = "CANCEL";
	//已约车
	public static final String STATUS_SHOUTCAR = "SHOUTCAR";
	
	// 数据库默认值
	public static final String DB_DEFAULT_STRING = "N/A";
	
	//更新电子运单订单
	public static final String OPERATE_MODIFIY= "modify";
	
	//删除电子运单订单
	public static final String OPERATE_DELETE= "delete";

	// FOSS-CRM订单状态映射
	public static final Map<String, String> CRM_ORDER_STATUS_MAPPING = new HashMap<String, String>();
	
	public final static  String UNACTIVE_EWAYBILL_GENERATE_FAILURE = "failure";//待激活电子运单生成失败
	
	static {
		CRM_ORDER_STATUS_MAPPING.put(STATUS_DISPATCH_VEHICLE, "RECEIPTING");
		CRM_ORDER_STATUS_MAPPING.put(STATUS_PDA_ACCEPT, "RECEIPTING");
		CRM_ORDER_STATUS_MAPPING.put(STATUS_PICKUPING, "RECEIPTING");
		CRM_ORDER_STATUS_MAPPING.put(STATUS_RETURN, "GOBACK");
		CRM_ORDER_STATUS_MAPPING.put(STATUS_PICKUP_FAILURE, "GOBACK");
		CRM_ORDER_STATUS_MAPPING.put(UNACTIVE_EWAYBILL_GENERATE_FAILURE, "REJECT");
		CRM_ORDER_STATUS_MAPPING.put(STATUS_SHOUTCAR,"SHOUTCAR");//已约车
	}
	
	/**
	 * FOSS灰度规则RPS 2017年3月17日15:39:44 qzq
	 */
	//灰度规则-FOSS处理
	public static final String HD_RULE_FOSS = "0";
	//灰度规则-RPS处理
	public static final String HD_RULE_RPS = "1";
	//RPS待处理，带调度状态
	public static final String RPS_WAIT_DISPATCH="WAIT_DISPATCH";
	//RPS变更状态
	public static final String RPS_CHANGE_STATUS="0";
	/**
	 * PDA状态
	 */
	//派车记录  是否使用PDA   异常
	public static final String ISPDA_EXCEPTION ="EXCEPTION";
	
	//派车记录  是否使用PDA   无
	public static final String ISPDA_NO ="NO";
	
	//派车记录  是否使用PDA   正常
	public static final String ISPDA_NORMAL ="NORMAL";
	
	/**
	 * 订单类型，接货订单
	 */
	public static final String TYPE_PICKUP_ORDER = "PICKUP_ORDER";

	/**
	 * 会员类型-诚信通会员
	 * memberType : CXT表示诚信通会员, PT表示普通会员
	 */
	public static final String TYPE_MEMBER_CXT = "CXT";
	
	//零担电子运单转货运单
	public static final String TYPE_EWAYBILL_TRANSFER_ORDER = "EWAYBILL_ORDER";
	
	// 转货订单
	public static final String TYPE_TRANSFER_ORDER = "TRANSFER_ORDER";
	
	//订单任务发送状态   发送中
	public static final String ORDER_SENDING ="ORDER_SENDING";
	
	//订单任务发送状态   发送成功
	public static final String ORDER_SENDSUCCESS ="ORDER_SENDSUCCESS";
	
	//订单业务锁自动释放时间
	public static final String ORDER_LOCK_TTL = "ORDER_LOCK_TTL";
	//订单更新锁 超时时间
	public static final int ZERO = 0;
	
	/**
	 * 处理订单失败原因
	 */
	// 更新失败
	public static final String FAILURE_UPDATE = "0";
	// 短信发送失败
	public static final String FAILURE_SMS = "1";
	// 在线通知失败
	public static final String FAILURE_NOTICE = "2";
	// CRM中订单不存在
	public static final String FAILURE_NOT_CRM = "3";
	// 订单已锁定，无法操作
	public static final String FAILURE_LOCK = "4";
	
	// 状态
	/**
	 * 退回
	 */
	public static final String STATUS_PROCESS_RETURN = "RETURN";
	// 受理
	public static final String STATUS_PROCESS_ACCEPT = "ACCEPT";
	// 改接
	public static final String STATUS_PROCESS_AGAIN = "AGAIN";
	//客户要求非当日接货
	public static final String  REJECT_NOT_TODAY = "PICKUP_NOT_TODAY";
	
	/**
	 * 客户退回原因
	 */
	// 客户取消或者重复下单(零担、快递)
	public static final String REJECT_CANCEL_REPEAT_ORDERS = "CANCEL_REPEAT_ORDERS";
	// 客户要求延迟揽货(零担、快递)
	public static final String REJECT_REQUIRE_DELAY = "REQUIRE_DELAY";
	// 违禁品(零担)
	public static final String REJECT_CONTRABAND = "CONTRABAND";
	// 拒收品或违禁品(快递)
	public static final String REJECT_REFUSE_CONTRABAND = "REFUSE_CONTRABAND";
	
	/**
	 * 公司退回原因
	 */
	// 其他(零担、快递)
	public static final String REJECT_ELSE_REASON = "ELSE_REASON";
	// 来不及接货(零担、快递)
	public static final String REJECT_NO_TIME_PICKUP = "NO_TIME_PICKUP";
	// 联系不上客户(零担、快递)
	public static final String REJECT_CONTACT_NO_CUSTOMER = "CONTACT_NO_CUSTOMER";
	// 接货地址不详或不准(零担、快递)
	public static final String REJECT_PICKUP_ADDRESS_ERROR = "PICKUP_ADDRESS_ERROR";	
	// 超出服务范围(快递)
	public static final String REJECT_BEYOND_SERVICE_SCOPE = "BEYOND_SERVICE_SCOPE";
	// 接货地址匹配错误(快递)
	public static final String REJECT_PICKUP_ADDRESS_MATCH_ERROR = "PICKUP_ADDRESS_MATCH_ERROR";
	// 大票货或异形货(零担)
	public static final String REJECT_BIG_SHAPE_GOODS = "BIG_SHAPE_GOODS";
	// 道路限制(禁行或路窄)(零担)
	public static final String REJECT_LIMIT_ROAD = "LIMIT_ROAD";
	// 超出接货范围(零担)
	public static final String REJECT_BEYOND_PICKUP_SCOPE = "BEYOND_PICKUP_SCOPE";
	// 车辆空间不足(零担)
	public static final String REJECT_VEHICLE_NO_SPACE = "VEHICLE_NO_SPACE";
	
	// 超出我的接货范围
	public static final String REJECT_BEYOND_RECEIVING_SCOPE = "BEYOND_RECEIVING_SCOPE";
	// 车辆无足够空间
	public static final String REJECT_WITHOUT_SUFFICIENT_SPACE = "WITHOUT_SUFFICIENT_SPACE";
	// 车辆故障
	public static final String REJECT_VEHICLE_BREAKDOWN = "VEHICLE_BREAKDOWN";
	
	/**
	 * 电子订单项目-多新增一种退回原因
	 */
	//已转无订单开单
	public static final String REJECT_TFR_NONE_ORDER_CREATE_ORDER = "REJECT_TFR_NONE_ORDER_CREATE_ORDER";
	
	/**
	 * 短信模板code
	 */
	// 接货订单发送给司机
	public static final String TEMPLATE_PICKUP_ORDER_TO_DRIVER_SMS = "PICKUP_ORDER_TO_DRIVER";
	// 转货订单发送给司机
	public static final String TEMPLATE_TRANSFER_ORDER_TO_DRIVER_SMS = "TRANSFER_ORDER_TO_DRIVER";
	// 订单发送给客户
	public static final String TEMPLATE_ORDER_TO_CUSTOMER_SMS = "ORDER_TO_CUSTOMER";
	// 小件订单发送给快递员
	public static final String TEMPLATE_PICKUP_ORDER_TO_COURIER_SMS = "PICKUP_ORDER_TO_COURIER";
	// 小件订单发送给快递员(发货客户为违禁品黑名单用户)
	public static final String TEMPLATE_PICKUP_ORDER_TO_COURIER_SMS_CONTRABAND = "PICKUP_ORDER_TO_COURIER_CONTRABAND";
	//零担黑名单短信模板
	public static final String LESS_CARLOAD_FREIGHT_BLACKLIST_SMS="LTL_BLACKLIST_SMS_TEMPLATES";
	// 小件订单发送给客户
	public static final String TEMPLATE_EXPRESS_ORDER_TO_CUSTOMER_SMS = "EXPRESS_ORDER_TO_CUSTOMER";
	//快递订单调度受理短信发给客户
	public static final String EXPRESS_ORDER_ACCEPT_SMS = "EXPRESS_ORDER_ACCPT";
	 // 电子运单调度约车短信
	public static final String TEMPLATE_NETWORKWAYBILLDISPATCH_SMS = "PKP_NETWORKWAY_DISPATCH";
	
	//快递订单业务模块定义
	public static final String EXPRESS_ORDER_MODULE = "PKP_ORDER_EXP";
	//快递类型运输产品
	public static final String EXPRESS_PRODUCT_CODE = "PACKAGE";
	//约车订单确认ESB接口服务编码
	public static final String ESB_FOSS_SYNC_GPS_ORDER = "ESB_FOSS2ESB_SYNC_COLLECTTASK";
	//GIS订单接货编码
	public static final String REGION_TYPE_KD = "KD_PK";
	//定区
    public static final String COURIER_NATURE_FIXED ="FIXED";
    //机动
    public static final String COURIER_NATURE_MOTORIZED="MOTORIZED";
    //预处理订单  已查询
    public static final String CHANGE_ORDER_QUERY="A";
    //预处理订单  未查询
    public static final String CHANGE_ORDER_NO_QUERY="N/A";
    //PDA退回操作状态-退回
    public static final String PDA_RETURN_ORDER = "ORDER_RETURN";
    //PDA退回操作状态-转发
    public static final String PDA_FORWARD_ORDER = "ORDER_FORWARD";
    //订单受理方式  SYS自动受理
    public static final String ORDER_ACCEPT_STATUS_SYS = "SYS";
    //订单受理方式  HANDLE人工
    public static final String ORDER_ACCEPT_STATUS_HANDLE = "HANDLE";
    //3.60 特惠件
	public static  final String ROUND_COUPON_PACKAGE ="RCP";
	
	//是否子母件
	public static  final String ISPICPACKAGE ="Y";
	
	//合票成功
	public static  final String TICKETSUCCESS ="pkp.order.bigCustomersPlayTag.closeticket.success";
	
	//反合票成功
	public static  final String CLOSETICKETERROR ="pkp.order.bigCustomersPlayTag.closeticket.error";
	
}