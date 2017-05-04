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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/define/DeliverbillConstants.java
 * 
 * FILE NAME        	: DeliverbillConstants.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.define;

/**
 * 
 * 派送单常量
 * 
 * @author ibm-wangxiexu
 * @date 2012-11-13 下午5:08:07
 */
public class DeliverbillConstants {
    // ---------------------派送单状态----------------------

    public static final String STATUS_ALL = "ALL";

    /**
     * 已取消
     */
    public static final String STATUS_CANCELED = "CANCELED";

    /**
     * 已保存
     */
    public static final String STATUS_SAVED = "SAVED";

    /**
     * 已提交
     */
    public static final String STATUS_SUBMITED = "SUBMITED";

    /**
     * 已分配
     */
    public static final String STATUS_ASSIGNED = "ASSIGNED";

    /**
     * 装车中
     */
    public static final String STATUS_LOADING = "LOADING";

    /**
     * 已装车
     */
    public static final String STATUS_LOADED = "LOADED";

    /**
     * 已确认
     */
    public static final String STATUS_CONFIRMED = "CONFIRMED";

    /**
     * PDA已下拉
     */
    public static final String STATUS_PDA_DOWNLOADED = "PDA_DOWNLOADED";

    /**
     * 已签收确认
     */
    public static final String STATUS_SIGNINFO_CONFIRMED = "SIGNINFO_CONFIRMED";

    // ---------------------派送单异常类型----------------------
    public static final String EXCEPTION_ARRANGE_WAYBILL_COPY_PROPERTIES = "foss.pickup.predeliver.exception.arrangewaybill.copyproperties";

    /**
     * 短信通知
     */
    public static final String DELIVERBILL_SMS_NOTICE = "DELIVERBILL_SMS_NOTICE";

    /**
     * 通知司机
     */
    public static final String SMS_CODE_PKP_NOTIFY_DRIVER = "SMS_CODE_PKP_NOTIFY_DRIVER";

    /**
     * 短信业务类型
     */
    public static final String PKP_NOTIFY_DRIVER = "PKP_NOTIFY_DRIVER";

    /**
     * APP短信业务类型-接货
     */
    public static final String PKP_NOTIFY_APP_PICKUP = "PKP_ORDER";

    /**
     * APP短信业务类型-送货
     */
    public static final String PKP_NOTIFY_APP_DELIVER = "PKP_NOTIFY";

    /**
     * APP司机接货客户无应答短信
     */
    public static final String SMS_CODE_PKP_PICK_NO_ANSWER = "SMS_CODE_PKP_PICK_NO_ANSWER";

    /**
     * APP司机送货客户无应答短信
     */
    public static final String SMS_CODE_PKP_DELIVER_NO_ANSWER = "SMS_CODE_PKP_DELIVER_NO_ANSWER";

    // ---------------------计算精度----------------------
    /**
     * 重量精度
     */
    public static final int WEIGHT_PRECISION = 2;

    /**
     * 体积精度
     */
    public static final int VOLUME_PRECISION = 2;

    public static final String SPLIT_CHAR = "/";

    // ----- 派送单到达联相关 -----
    /**
     * 派送明细初始到达联编号值(当派送单尚未确认，未生成到达联时)
     */
    public static final String NULL_ARRIVESHEET_NO = "N/A";

    /**
     * 确认派送单时，车辆重量/体积装载率阈值(下限)
     */
    public static final double DELIVER_WEIGHT_LOWER_THRESHOLD = 0.6;

    public static final double DELIVER_VOLUME_LOWER_THRESHOLD = 0.6;

    // ---------------------派车类型----------------------

    // --------------------- 区域性质---------------------
    /**
     * 大区
     */
    public static final String REGION_NATURE_BQ = "REGION_NATURE_BQ";

    /**
     * 小区
     */
    public static final String REGION_NATURE_SQ = "REGION_NATURE_SQ";

    /**
     * 打印到达联状态
     */
    public static final String IS_ARRIVE_SHEET_YES = "Y";

    /**
     * 正常
     */
    public static final String NOMAL = "NOMAL";

    /**
     * 专车
     */
    public static final String SPECIAL = "SPECIAL";

    /**
     * 带人
     */
    public static final String MANNED = "MANNED";

    /**
     * Foss同步送货任务给GPS的版本号
     */
    public static final String ESB_FOSS2ESB_SYNC_DELIVERYTASK_VERSION = "0.1";

    /**
     * FOSS系统同步PDA作业时间的标识符
     */
    public static final String ESB_FOSS2ESB_SYNC_PDATIME_CODE = "ESB_FOSS2ESB_SYNC_PDATIME";

    /**
     * FOSS系统同步PDA作业时间 接送货标识 1 送货 2 接货（有订单） 3 接货（无订单）
     */
    public static final int GPS_ORDERTYPE_DELIVER = 1;

    /**
     * FOSS系统同步PDA作业时间 接送货标识 1 送货 2 接货（有订单） 3 接货（无订单）
     */
    public static final int GPS_ORDERTYPE_RECEIVE = 2;

    /**
     * FOSS系统同步PDA作业时间 接送货标识 1 送货 2 接货（有订单） 3 接货（无订单）
     */
    public static final int GPS_UNORDERTYPE_RECEIVE = 3;

    /**
     * Foss同步送货任务给GPS的编码
     */
    public static final String ESB_FOSS2ESB_SYNC_DELIVERYTASK_CODE = "ESB_FOSS2ESB_SYNC_DELIVERYTASK";

    /**
     * Foss异步发送信息给发票系统的编码
     */
    public static final String ESB_FOSS2ESB_QUOTA_MARK_CODE = "ESB_FOSS2ESB_QUOTA_MARK";

    public static final String ESB_FOSS2ESB_QUOTA_MARK_VERSION = "0.1";

    /**
     * 开单付款方式 月结
     */
    public static final String PAYMETHOD_MONTHPAY = "CT";

    /**
     * 提货方式为自提共用字符 PICKUP
     */
    public static final String RECEIVE_METHOD = "PICKUP";

    // 出车任务-DISPATCH_VEHICLE_TASK-二次派送5
    public static final String CAR_TASK_SECOND = "5";

    // 出车任务-DISPATCH_VEHICLE_TASK- 带+二次派送6
    public static final String CAR_TASK_SECOND_TAKE = "6";

    // 出车任务-DISPATCH_VEHICLE_TASK-带+送+接3
    public static final String CAR_TASK_TAKE_DELIVE_PICK = "3";

    // 出车任务-DISPATCH_VEHICLE_TASK- 带+送+转4
    public static final String CAR_TASK_TAKE_DELIVE_TRAN = "4";

}