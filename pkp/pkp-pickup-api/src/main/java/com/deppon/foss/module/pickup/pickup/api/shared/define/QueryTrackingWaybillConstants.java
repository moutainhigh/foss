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
 * PROJECT NAME	: pkp-pickup-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/api/shared/define/QueryTrackingWaybillConstants.java
 * 
 * FILE NAME        	: QueryTrackingWaybillConstants.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.api.shared.define;

/**
 * 
 * 追踪运单常量
 * @author ibm-wangfei
 * @date Jan 6, 2013 10:52:01 AM
 */
public class QueryTrackingWaybillConstants {

	/**
	 * 下次追踪
	 */
	public static final String TRACK_NEXT = "TRACK_NEXT";
	/**
	 * 新追踪
	 */
	public static final String TRACK_NEW = "TRACK_NEW";
	/**
	 * 追踪完成
	 */
	public static final String TRACK_COMPLETE = "TRACK_COMPLETE";
	
	/**
	 * 没有返单
	 */
	public static final Integer RETURN_BILL_FLG_NUM_NONE = Integer.valueOf("0");
	
	/**
	 * 有返单
	 */
	public static final Integer RETURN_BILL_FLG_NUM_EXIST = Integer.valueOf("1");
	
	/**
	 * 跟踪类别  --进港跟踪
	 */
	public static final String TRACKTYPE_INTO_PORT ="TRACKTYPE_INTO_PORT";
	
	/**
	 * 跟踪类别  --出港跟踪
	 */
	public static final String TRACKTYPE_OUT_PORT ="TRACKTYPE_OUT_PORT";
	
	/**
	 * 跟踪类别  --配载跟踪
	 */
	public static final String TRACKTYPE_STOWAGE ="TRACKTYPE_STOWAGE";
	
	/**
	 * 跟踪类别  -- 其他异常
	 */
	public static final String TRACKTYPE_OTHER_EXCEPTION ="TRACKTYPE_OTHER_EXCEPTION";
	
	/**
	 * 跟踪类别  --查询备注
	 */
	public static final String TRACKTYPE_SEARCH_REMARK ="TRACKTYPE_SEARCH_REMARK";
	
	/**
	 * 跟踪方式  -- 电话反馈
	 */
	public static final String TRACKMETHOD_PHONE ="TRACKMETHOD_PHONE";
	
	/**
	 * 跟踪方式  -- 短信反馈
	 */
	public static final String TRACKMETHOD_SMS ="TRACKMETHOD_SMS";

	/**
	 * 未签收
	 */
	public static final String SITUATION_NO_SIGN = "NO_SIGN";
	//RFOSS2015041738跟踪运单增加导出功能需求  065340 liutao
	/**
	 * 签收情况
	 */
	public static final String PKP_SIGN_SITUATION = "PKP_SIGN_SITUATION";
	/** 
	 * 正常签收
	 */
	public static final String OPERATE_TYPE_NORMAL_SIGN = "NORMAL_SIGN";
	/**
	 * 异常签收
	 */
	public static final String OPERATE_TYPE_EXCEPTION_SIGN = "UNNORMAL_SIGN";
	/**
	 *已出库
	 * */
	public static final String OUT_STOCK = "OUT_STOCK";
	/**
	 * 库存中
	 * */
	public static final String IN_STOCK = "IN_STOCK";
	/**
	 * 普通运单
	 * */
	public static final String NORMAL = "NORMAL";
	/**
	 * 电子运单
	 * */
	public static final String EWAYBILL = "EWAYBILL";
	/**
	 * 已返单
	 * */
	public static final String ALREADY_RETURN_BILL = "ALREADY_RETURN_BILL";
	/**
	 * 未返单
	 * */
	public static final String NONE_RETURN_BILL = "NONE_RETURN_BILL";
}