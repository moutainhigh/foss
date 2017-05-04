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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/define/WayBillNoLocusConstant.java
 * 
 * FILE NAME        	: WayBillNoLocusConstant.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.define;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 常量定义
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-24 下午6:53:04
 */
public class WayBillNoLocusConstant {
	/*********** 操作类型 *************/
	/**
	 * PDA开单-CREATE(有PDA接货,未补录)
	 */
	public static final String OPERATE_TYPE_PDA_PENDING = "PDA_PENDING";
	/**
	 * PDA开单-CREATE(有PDA接货,未补录,图片上传)
	 */
	public static final String OPERATE_TYPE_PDA_PENDING_PIC = "PDA_PENDING_PIC";
	/**
	 * 开单-CREATE
	 */
	public static final String OPERATE_TYPE_CREATE = "CREATE";

	/**
	 * 开单-CREATE（PDA）
	 */
	public static final String OPERATE_TYPE_CREATE_PDA = "CREATE_PDA";
	/**
	 * 上分拣扫描-SORTING_SCAN 200968 zwd
	 */
	public static final String OPERATE_TYPE_SORTING_SCAN = "SORTING_SCAN";
	/**
	 * 包信息 -建包中 -Express_PackagIng 200968  zwd
	 */
	public static final String OPERATE_TYPE_Express_PackagIng = "Express_PackagIng";
	
	/**
	 * 包信息 -已建包 -Express_Packaged 200968  zwd
	 */
	public static final String OPERATE_TYPE_Express_Packaged = "Express_Packaged";
	
	/**
	 * 空运客户通知  200968  zwd 
	 */
	public static final String OPERATE_TYPE_AIR_NOTIFY_CUSTOMERS = "AIR_NOTIFY_CUSTOMERS";
	
	/**
	 * 空运到达类型:代理到机场提货 200968  zwd 2015-08-08
	 */
	public static final String AGENT_TO_AIRPORT_PICK_UP = "AGENT_TO_AIRPORT_PICK_UP";
	
	/**
	 * 空运到达类型 :货物到达代理处 200968  zwd  2015-08-08
	 */
	public static final String GOODS_ARRIVE_AGENCY = "GOODS_ARRIVE_AGENCY";
	
	/**
	 * 提交运单-CREATE_BSE 综合查询用
	 */
	public static final String OPERATE_TYPE_CREATE_BSE = "CREATE_BSE";

	/**
	 * 运单补录-CREATE_PDA_BSE 综合查询用
	 */
	public static final String OPERATE_TYPE_CREATE_PDA_BSE = "CREATE_PDA_BSE";
	/**
	 * 运单补录-CREATE_PDA_BSE 综合查询用-图片开单
	 */
	public static final String OPERATE_TYPE_CREATE_PDA_BSE_PIC = "CREATE_PDA_BSE_PIC";
	/**
	 * 补录重量体积-MAKEUP_PDA_BSE 综合查询用
	 */
	public static final String OPERATE_TYPE_MAKEUP_PDA_BSE = "MAKEUP_PDA_BSE";
	/**
	 * 生成运单-GENERATE_PDA_BSE 综合查询用
	 */
	public static final String OPERATE_TYPE_GENERATE_PDA_BSE = "GENERATE_PDA_BSE";
	/**
	 * 出发-DEPART
	 */
	public static final String OPERATE_TYPE_DEPART = "DEPART";
	/**
	 * 到达-ARRIVE
	 */
	public static final String OPERATE_TYPE_ARRIVE = "ARRIVE";
	/**
	 * 卸车-UNLOAD
	 */
	public static final String OPERATE_TYPE_UNLOAD = "UNLOAD";
	/**
	 * 接货卸车-UNLOAD_DELIVER
	 */
	public static final String OPERATE_TYPE_UNLOAD_DELIVER = "UNLOAD_DELIVER";
	/**
	 * 装车扫描-LOAD_SCAN
	 */
	public static final String OPERATE_TYPE_LOAD_SCAN = "LOAD_SCAN";
	/**
	 * 叉车司机扫描
	 * */
	public static final String OPERATE_TYPE_TRAY_SCAN="TRAY_SCAN";
	/**
	 * 建立卸车任务-UNLOAD_TASK zwd 20150105 200968 OPERATE_TYPE_UNLOAD_TASK
	 */
	public static final String OPERATE_TYPE_UNLOAD_TASK = "UNLOAD_TASK";
	
	/**
	 * 创建接货任务-PICK_TASK wsh 326027 
	 */
	public static final String OPERATE_TYPE_PICK_TASK = "PICK_TASK";
	
	/**
	 * 已扫描运单
	 */
	public static final String IS_PDASCAN_INACTIVE = "IS_PDASCAN_INACTIVE";
	
	/**
	 * 生成待补录运单-326027
	 */
	public static final String FINASH_ADDITIONAL_RECORDING = "ADDITIONAL_RECORDING";
	
	/**
	 * 提交接货任务-PICK_SUBMIT wsh 326027
	 */
	public static final String OPERATE_TYPE_PICK_SUBMIT = "PICK_SUBMIT";
	
/*	*//**
	 * 接货卸车-PICK_UNLOAD wsh 326027
	 *//*
	public static final String OPERATE_TYPE_PICK_UNLOAD = "PICK_UNLOAD";*/
	
	
	/**
	 * 提交装车任务-LOAD_SUBMIT
	 */
	public static final String OPERATE_TYPE_LOAD_SUBMIT = "LOAD_SUBMIT";
	/**
	 * 打包装扫描-PACKAGE
	 */
	public static final String OPERATE_TYPE_PACKAGE = "PACKAGE";
	/**
	 * 出发交接-HANDOVER
	 */
	public static final String OPERATE_TYPE_HANDOVER = "HANDOVER";
	/**
	 * 提货通知-NOTIFY
	 */
	public static final String OPERATE_TYPE_NOTIFY_PRE = "NOTIFY_PRE";
	public static final String OPERATE_TYPE_NOTIFY_DELIVER = "NOTIFY_DELIVER";
	public static final String OPERATE_TYPE_NOTIFY_PICKUP = "NOTIFY_PICKUP";
	/**
	 * 派送-DELIVERY
	 */
	public static final String OPERATE_TYPE_DELIVERY = "DELIVERY";
	/**
	 * 派送拉回-DELIVERY_RETURN
	 */
	public static final String OPERATE_TYPE_DELIVERY_RETURN = "DELIVERY_RETURN";
	/**
	 * 正常签收-NORMAL_SIGN
	 */
	public static final String OPERATE_TYPE_NORMAL_SIGN = "NORMAL_SIGN";
	/**
	 * 反签收-SIGN_RFC
	 */
	public static final String OPERATE_TYPE_SIGN_RFC = "SIGN_RFC";
	/**
	 * 终止-ABORTED
	 */
	public static final String OPERATE_TYPE_ABORTED = "ABORTED";
	/**
	 * 异常签收-EXCEPTION_SIGN
	 */
	public static final String OPERATE_TYPE_EXCEPTION_SIGN = "EXCEPTION_SIGN";
	/**
	 * 部分签收-PART_SIGN
	 */
	public static final String OPERATE_TYPE_PART_SIGN = "PART_SIGN";
	/**
	 * 制作航空正单-AIR_HANDOVER_BILL
	 */
	public static final String OPERATE_TYPE_AIR_HANDOVER_BILL = "AIR_HANDOVER_BILL";
	/**
	 * 航班起飞-AIR_TAKE_OFF
	 */
	public static final String OPERATE_TYPE_AIR_TAKE_OFF = "AIR_TAKE_OFF";
	/**
	 * 航空正单交接单(库存中)
	 */
	public static final String OPERATE_TYPE_AIR_HANDOVER_DETAIL_IN = "AIR_HANDOVER_DETAIL_IN";

	/**
	 * 航空正单交接单(出库)
	 */
	public static final String OPERATE_TYPE_AIR_HANDOVER_DETAIL_OUT = "AIR_HANDOVER_DETAIL_OUT";

	/**
	 * 航班到达-AIR_ARRIVE
	 */
	public static final String OPERATE_TYPE_AIR_ARRIVE = "AIR_ARRIVE";

	/**
	 * 制作合票清单-AIR_PICKUPBILL
	 */
	public static final String OPERATE_TYPE_AIR_PICKUPBILL = "AIR_PICKUPBILL";
	/**
	 * 制作中转提货清单-AIR_TRANS_PICKUPBILL
	 */
	public static final String OPERATE_TYPE_AIR_TRANS_PICKUPBILL = "AIR_TRANS_PICKUPBILL";
	/**
	 * 登入包装货区-PACKAGE_AREA_IN
	 */
	public static final String OPERATE_TYPE_PACKAGE_AREA_IN = "PACKAGE_AREA_IN";
	/**
	 * 登出包装货区-PACKAGE_AREA_OUT
	 */
	public static final String OPERATE_TYPE_PACKAGE_AREA_OUT = "PACKAGE_AREA_OUT";
//	/**
//	 * 登入普通货区-PACKAGE_AREA_IN
//	 */
//	public static final String OPERATE_TYPE_NORMAL_AREA_IN = "NORMAL_AREA_IN";
//	/**
//	 * 登出普通货区-PACKAGE_AREA_OUT
//	 */
//	public static final String OPERATE_TYPE_NORMAL_AREA_OUT = "NORMAL_AREA_OUT";
	/**
	 * 登入包装货区（贵重物品）-PACKAGE_AREA_IN_EXP
	 */
	public static final String OPERATE_TYPE_PACKAGE_AREA_IN_EXP = "PACKAGE_AREA_IN_EXP";
	/**
	 * 登出包装货区（贵重物品）-PACKAGE_AREA_OUT
	 */
	public static final String OPERATE_TYPE_PACKAGE_AREA_OUT_EXP = "PACKAGE_AREA_OUT_EXP";
	/**
	 * 清仓入库-STOCKCHECKING_MORE
	 */
	public static final String OPERATE_TYPE_STOCKCHECKING_MORE = "STOCKCHECKING_MORE";
	/**
	 * 单票入库-STOCKCHECKING_NORMAL
	 */
	public static final String OPERATE_TYPE_STOCKCHECKING_NORMAL = "STOCKCHECKING_NORMAL";
	/**
	 * 拉回入库-STOCKCHECKING_AIR
	 */
	public static final String OPERATE_TYPE_STOCKCHECKING_AIR = "STOCKCHECKING_AIR";
	/**
	 * 外发交接-PART_LINE
	 */
	public static final String OPERATE_TYPE_PART_LINE = "PART_LINE";
	/**
	 * 快递代理交接-LDP_HANDOVER
	 */
	public static final String OPERATE_TYPE_LDP_HANDOVER = "LDP_HANDOVER";
	/**
	 * 外发单录入-PARTIAL_LINE
	 */
	public static final String OPERATE_TYPE_PARTIAL_LINE = "PARTIAL_LINE";
	/**
	 * 快递代理外发单录入-PARTIAL_LINE
	 */
	public static final String OPERATE_TYPE_LDP_PARTIAL_LINE = "LDP_PARTIAL_LINE";
	/**
	 * 快递代理公司到达-LDP_TRACK_ARRIVE
	 */
	public static final String OPERATE_TYPE_LDP_TRACK_ARRIVE = "LDP_TRACK_ARRIVE";
	/**
	 * 快递代理公司离开-LDP_TRACK_LEAVE
	 */
	public static final String OPERATE_TYPE_LDP_TRACK_LEAVE = "LDP_TRACK_LEAVE";
	/**
	 * 快递代理公司派件-LDP_TRACK_DELIVER
	 */
	public static final String OPERATE_TYPE_LDP_TRACK_DELIVER = "LDP_TRACK_DELIVER";
	/**
	 * 快递代理公司派件失败-LDP_TRACK_DELIVER_FAIL
	 */
	public static final String OPERATE_TYPE_LDP_TRACK_DELIVER_FAIL = "LDP_TRACK_DELIVER_FAIL";
	/**
	 * 快递代理公司货物拉回德邦-OPERATE_TYPE_LDP_TRACK_RETURN
	 */
	public static final String OPERATE_TYPE_LDP_TRACK_RETURN = "OPERATE_TYPE_LDP_TRACK_RETURN";
	/**
	 * 快递代理公司拒签-OPERATE_TYPE_LDP_TRACK_DEFUSE_SIGN
	 */
	public static final String OPERATE_TYPE_LDP_TRACK_DEFUSE_SIGN = "OPERATE_TYPE_LDP_TRACK_DEFUSE_SIGN";
	/**
	 * 快递代理公司已配达-OPERATE_TYPE_LDP_TRACK_DELIVERY
	 */
	public static final String OPERATE_TYPE_LDP_TRACK_DELIVERY = "OPERATE_TYPE_LDP_TRACK_DELIVERY";	
	
	
	// 清关完成交接至金浦仓库 -------10--清关到达
    public static String OPERATE_TYPE_INTER_CUSTOMS_ARRIVAL = "INTERNATION_CUSTOMS_ARRIVAL";
    //货物运输至主要转运中心 -------11--转运场到达 
    public static String OPERATE_TYPE_INTER_TRANSFER_ARRIVAL = "INTERNATION_TRANSFER_ARRIVAL";
	//目的转运中心卸货中----------12--目的站到达 
	public static String OPERATE_TYPE_INTER_DEST_ARRIVAL  = "INTERNATION_DEST_ARRIVAL";
	//派送中/派送中(重货专用卡车)---13--派送
	public static String OPERATE_TYPE_INTER_DELIVER = "INTERNATION_DELIVER";
	//派送完成/二次派送成功  -------14--签收 
	public static String OPERATE_TYPE_INTER_SIGN = "INTERNATION_SIGN";
	//派送异常  ----------------20--派送 
	public static String OPERATE_TYPE_INTER_EXCEPTION_DELIVER = "INTERNATION_EXCEPTION_DELIVER";
	//已退回------------------21--已退回
	public static String OPERATE_TYPE_INTER_RETURN = "INTERNATION_RETURN";
	// 派送地址更改--------------22--派送拉回
	public static String OPERATE_TYPE_INTER_ADDRESS_CHANGE = "INTERNATION_ADDRESS_CHANGE";
	//派送未完成 ---------------23--派送拉回
	public static String OPERATE_TYPE_INTER_UNDELIVER = "INTERNATION_UNDELIVER";
	//地址错误  ----------------24--派送拉回  
	public static String OPERATE_TYPE_INTER_ADDRESS_ERROR = "INTERNATION_ADDRESS_ERROR";
	
	/**出口清关中*/
	public static String OPERATE_TYPE_EXPORT_CLEARANCE = "EXPORT_CLEARANCE";
	/**海关查验 */
	public static String OPERATE_TYPE_CUSTOMS_INSPECTION = "CUSTOMS_INSPECTION";
	/**出口清关完成*/
	public static String OPERATE_TYPE_COMPLETEEXPORTCLEAR = "COMPLETEEXPORTCLEAR";
	/**跨境运输中*/
	public static String OPERATE_TYPE_CROSSBORDERDELIVER = "CROSSBORDERDELIVER";
	/**进口清关中*/
	public static String OPERATE_TYPE_IMPORT_CLEARANCE = "IMPORT_CLEARANCE";
	
	/**
	 * 营业部外发-已转寄
	 */
	public static final String OPERATE_TYPE_SD_TRACK_TRANSFER = "OPERATE_TYPE_SD_TRACK_TRANSFER";
		
	/**
	 * 返货开单
	 */
	public static final String OPERATE_TYPE_RETURN_CARGO = "RETURN_CARGO";
	
	
	/**
	 * 空运拉货-AIR_UN_SHIPPED_GOODS
	 */
	public static final String OPERATE_TYPE_AIR_UN_SHIPPED_GOODS = "AIR_UN_SHIPPED_GOODS";
	/** 调整走货路径 入库新货区 */
	public static final String TRANSPORT_PATH_CHANGE_IN_STOCK_TYPE = "PATH_CHANGE_IN";
	/** 调整走货路径出库新货区 */
	public static final String TRANSPORT_PATH_CHANGE_OUT_STOCK_TYPE = "PATH_CHANGE_OUT";

	/********************** 派送单状态 *************************************/
	/** 综合查询派送单状态前缀 */
	public static final String OPERATE_TYPE_DELIVER_BILL_PREFIX = "DB_";
	/** 已保存 */
	public static final String OPERATE_TYPE_STATUS_SAVED = "DB_SAVED";
	/** 已提交 */
	public static final String OPERATE_TYPE_STATUS_SUBMITED = "DB_SUBMITED";
	/** 已分配 */
	public static final String OPERATE_TYPE_STATUS_ASSIGNED = "DB_ASSIGNED";
	/** 装车中 */
	public static final String OPERATE_TYPE_STATUS_LOADING = "DB_LOADING";
	/** 已装车 */
	public static final String OPERATE_TYPE_STATUS_LOADED = "DB_LOADED";
	/** 已确认 */
	public static final String OPERATE_TYPE_STATUS_CONFIRMED = "DB_CONFIRMED";
	/** PDA已下拉 */
	public static final String OPERATE_TYPE_STATUS_PDA_DOWNLOADED = "DB_PDA_DOWNLOADED";
	/** 已签收确认 */
	public static final String OPERATE_TYPE_STATUS_SIGNINFO_CONFIRMED = "DB_SIGNINFO_CONFIRMED";

	/** 操作类型 */
	public final static Map<String, String> map = new HashMap<String, String>();
	static {
		map.put(OPERATE_TYPE_PDA_PENDING, "PDA开单");
		map.put("CREATE", "开单");
		map.put("CREATE_PDA", "开单");
		map.put(OPERATE_TYPE_CREATE_BSE, "提交运单");
		map.put(OPERATE_TYPE_CREATE_PDA_BSE, "运单补录");
		map.put(OPERATE_TYPE_PDA_PENDING_PIC, "图片上传");
		map.put(OPERATE_TYPE_CREATE_PDA_BSE_PIC, "图片开单");
		map.put(OPERATE_TYPE_MAKEUP_PDA_BSE, "补录重量体积");
		map.put(OPERATE_TYPE_GENERATE_PDA_BSE, "生成运单");
		map.put("DEPART", "出发");
		map.put("ARRIVE", "到达");
		map.put("NOTIFY_PRE", "客户通知");
		map.put("NOTIFY_DELIVER", "客户通知");
		map.put("NOTIFY_PICKUP", "客户通知");
		map.put("DELIVERY", "派送");
		/** 派送单详细状态 */
		map.put(OPERATE_TYPE_STATUS_SAVED, "派送单-已保存");
		map.put(OPERATE_TYPE_STATUS_SUBMITED, "派送单-已提交");
		map.put(OPERATE_TYPE_STATUS_ASSIGNED, "派送单-已分配");
		map.put(OPERATE_TYPE_STATUS_LOADING, "派送单-装车中");
		map.put(OPERATE_TYPE_STATUS_LOADED, "派送单-已装车");
		map.put(OPERATE_TYPE_STATUS_CONFIRMED, "派送单-已确认");
		map.put(OPERATE_TYPE_STATUS_PDA_DOWNLOADED, "派送单-PDA已下拉");
		map.put(OPERATE_TYPE_STATUS_SIGNINFO_CONFIRMED, "派送单-已签收确认");
		map.put("DELIVERY_RETURN", "派送中");
		/** 派送单详细状态 */
		map.put("DELIVERY_RETURN", "派送拉回");
		map.put("NORMAL_SIGN", "正常签收");
		map.put("ABORTED", "终止");
		map.put("EXCEPTION_SIGN", "异常签收");
		map.put("PART_SIGN", "部分签收");
		map.put("AIR_HANDOVER_BILL", "制作航空正单");
		map.put("AIR_TAKE_OFF", "航班起飞");
		map.put("AIR_ARRIVE", "航班到达");
		map.put(OPERATE_TYPE_AIR_HANDOVER_DETAIL_IN, "航空正单交接单");
		map.put(OPERATE_TYPE_AIR_HANDOVER_DETAIL_OUT, "航空正单交接单");
		map.put("AIR_PICKUPBILL", "制作合票清单");
		map.put("AIR_TRANS_PICKUPBILL", "制作中转提货清单");
		map.put("SIGN_RFC", "反签收");
		map.put("UNLOAD", "卸车扫描");
		map.put("TRAY_SCAN", "叉车司机扫描");
		map.put("LOAD_SCAN", "装车扫描");
		map.put("LOAD_SUBMIT", "提交装车任务");
		map.put("HANDOVER", "出发交接");
		map.put("PACKAGE", "打包装扫描");
		map.put("PACKAGE_AREA_IN", "登入包装货区");
		map.put("PACKAGE_AREA_OUT", "登出包装货区");
		map.put("NORMAL_AREA_IN", "登入普通货区");
		map.put("NORMAL_AREA_OUT", "登出普通货区");
		map.put("PACKAGE_AREA_IN_EXP", "登入贵重货区");
		map.put("PACKAGE_AREA_OUT_EXP", "登出贵重货区");
		map.put("STOCKCHECKING_MORE", "清仓入库");
		map.put("STOCKCHECKING_NORMAL", "单票入库");
		map.put("STOCKCHECKING_AIR", "拉回入库");
		map.put("PART_LINE", "外发交接");
		map.put("PARTIAL_LINE", "外发单录入");
		map.put("LDP_HANDOVER", "快递代理交接");
		map.put("LDP_PARTIAL_LINE", "快递代理外发单录入");
		map.put("LDP_TRACK_ARRIVE", "快递代理公司到达");
		map.put("LDP_TRACK_LEAVE", "快递代理公司外发");
		map.put("LDP_TRACK_DELIVER", "快递代理公司派送");
		map.put("LDP_TRACK_DELIVER_FAIL", "快递代理公司派送失败");
		map.put("OPERATE_TYPE_LDP_TRACK_RETURN", "快递代理公司货物拉回德邦");
		map.put("OPERATE_TYPE_LDP_TRACK_DELIVERY", "快递代理公司已配达");
		map.put("OPERATE_TYPE_LDP_TRACK_DEFUSE_SIGN", "客户拒收");
		map.put("OPERATE_TYPE_SD_TRACK_TRANSFER", "转寄");
		map.put("AIR_UN_SHIPPED_GOODS", "空运拉货");
		map.put("UNLOAD_DELIVER", "接货卸车");
		map.put("MODIFY_HANDOVERBILL", "修改交接单重新入库");
		map.put("CANCEL_HANDOVERBILL", "作废交接单重新入库");
		map.put("EXCEPTION_AREA", "入库异常货区");
		map.put("RETURN_CARGO", "返货开单");
		map.put("SPECIAL_AREA", "登入特殊货区");
		map.put("LOAD_MORE", "装车多货");
		map.put("LOSS_GOODS_FIND", "少货找到");
		map.put("UNLOAD_LOSS_GOODS_FIND", "卸车少货找到");
		map.put("NO_LABEL", "无标签货物入库");
		map.put("NO_LABEL_ORIGINAL", "无标签找到入库");
		map.put("PATH_CHANGE_IN", "调整走货路径入库");
		map.put("CONTRABAND_STATION", "违禁品移交");
		map.put("UNLOAD_MORE_GOODS", "卸车多货入库");
		map.put("WHOLE_VEHICLE", "整车开单入库");
		map.put("AFTER_PACKAGE_NEW_GOODS", "包装后新流水入库");
		map.put("WHOLE_VEHICLE_ARRIVAL", "整车到达入库");
		map.put("WAYBILL_RFC", "更改单调整入库");
		map.put("WHOLE_MODIFY_HANDOVERBILL", "整车修改交接单重新入库");
		map.put("WHOLE_CANCEL_HANDOVERBILL", "整车作废交接单重新入库");
		map.put("BULK_GOODS", "散货入库");
		map.put("LOSE_GOODS", "少货入库到特殊组织");
		map.put("STOCKTAKING_LOSS_GOODS_FIND", "清仓少货找到");
		map.put("ADJUST_STOCK_TO_STATION", "调整外场库存到派送货区库存");
		map.put("CANCEL_DELIVERY_LOAD", "取消派送装车");
		map.put("SINGLE", "单票出库");
		map.put("FORMAL_HANDOVERBILL", "交接出库");
		map.put("AFTER_PACKAGE", "包装后原流水出库");
		map.put("PATH_CHANGE_OUT", "调整走货路径出库");
		map.put("WAYBILL_INVALID", "运单作废出库");
		map.put("ABANDON_GOODS", "弃货出库");
		map.put("WAYBILL_PDA_PENDING", "PDA待补录调整入库");
		map.put("UNLOAD_TASK", "建立卸车任务");
		map.put("PICK_TASK", "创建接货任务");
		map.put("PICK_SUBMIT", "提交接货任务");
		map.put("ADDITIONAL_RECORDING", "生成待补录");
		map.put("IS_PDASCAN_INACTIVE", "OCB已扫描");
		map.put("SORTING_SCAN", "上分拣扫描");
		map.put("Express_PackagIng", "建包扫描");
		map.put("AIR_NOTIFY_CUSTOMERS", "空运客户通知");
		map.put("Express_Packaged", "提交建包任务");
		map.put("AGENT_TO_AIRPORT_PICK_UP", "代理到机场提货");
		map.put("GOODS_ARRIVE_AGENCY", "货物到达代理处");
		map.put("IN", "点单");
		map.put("END", "点单");
		/**国际件*/
		map.put("INTERNATION_CUSTOMS_ARRIVAL", "清关到达");
		map.put("INTERNATION_TRANSFER_ARRIVAL", "转运场到达");
		map.put("INTERNATION_DEST_ARRIVAL", "目的站到达");
		map.put("INTERNATION_DELIVER", "派送");
		map.put("INTERNATION_SIGN", "签收");
		map.put("INTERNATION_EXCEPTION_DELIVER", "派送异常");
		map.put("INTERNATION_RETURN", "已退回");
		map.put("INTERNATION_ADDRESS_CHANGE", "派送拉回");
		map.put("INTERNATION_UNDELIVER", "派送拉回");
		map.put("INTERNATION_ADDRESS_ERROR", "派送拉回");
		map.put("EXPORT_CLEARANCE", "出口清关中");
		map.put("CUSTOMS_INSPECTION", "海关查验");
		map.put("COMPLETEEXPORTCLEAR", "出口清关完成");
		map.put("CROSSBORDERDELIVER", "跨境运输中");
		map.put("IMPORT_CLEARANCE", "进口清关中");
	}
	/** 货物状态 */
	public final static Map<String, String> currentStatusMap = new HashMap<String, String>();
	static {
		currentStatusMap.put(WayBillNoLocusConstant.OPERATE_TYPE_PDA_PENDING, "接货中");
		currentStatusMap.put(WayBillNoLocusConstant.OPERATE_TYPE_PDA_PENDING_PIC, "接货中");
		currentStatusMap.put("CREATE", "开单中");
		currentStatusMap.put("CREATE_PDA", "PDA开单中");
		currentStatusMap.put(WayBillNoLocusConstant.OPERATE_TYPE_CREATE_BSE, "库存中");
		currentStatusMap.put(WayBillNoLocusConstant.OPERATE_TYPE_CREATE_PDA_BSE, "库存中");
		currentStatusMap.put(WayBillNoLocusConstant.OPERATE_TYPE_CREATE_PDA_BSE_PIC, "库存中");
		currentStatusMap.put(WayBillNoLocusConstant.OPERATE_TYPE_MAKEUP_PDA_BSE, "库存中");
		currentStatusMap.put(WayBillNoLocusConstant.OPERATE_TYPE_GENERATE_PDA_BSE, "库存中");
		currentStatusMap.put("DEPART", "运输中");
		currentStatusMap.put("ARRIVE", "运输中");
		currentStatusMap.put("NOTIFY_PICKUP", "提前通知");
		currentStatusMap.put("NOTIFY_DELIVER", "提前通知");
		currentStatusMap.put("NOTIFY_PRE", "提前联系");
		currentStatusMap.put("DELIVERY", "派送中");
		currentStatusMap.put("OPP_STATUS", "运输中");
		/** 派送单详细状态 */
		currentStatusMap.put(OPERATE_TYPE_STATUS_SAVED, "库存中");
		currentStatusMap.put(OPERATE_TYPE_STATUS_SUBMITED, "排单待装车");
		currentStatusMap.put(OPERATE_TYPE_STATUS_ASSIGNED, "排单待装车");
		currentStatusMap.put(OPERATE_TYPE_STATUS_LOADING, "派送装车中");
		currentStatusMap.put(OPERATE_TYPE_STATUS_LOADED, "已装车待送货");
		currentStatusMap.put(OPERATE_TYPE_STATUS_CONFIRMED, "派送中");
		currentStatusMap.put(OPERATE_TYPE_STATUS_PDA_DOWNLOADED, "派送中");
		currentStatusMap.put(OPERATE_TYPE_STATUS_SIGNINFO_CONFIRMED, "派送中");
		currentStatusMap.put("DELIVERY_RETURN", "派送中");
		/** 派送单详细状态 */
		currentStatusMap.put("NORMAL_SIGN", "正常签收");
		currentStatusMap.put("ABORTED", "异常签收");
		currentStatusMap.put("EXCEPTION_SIGN", "异常签收");
		currentStatusMap.put("PART_SIGN", "异常（少货）");
		currentStatusMap.put("AIR_HANDOVER_BILL", "制作航空正单");
		currentStatusMap.put("AIR_TAKE_OFF", "航班起飞");
		currentStatusMap.put("AIR_ARRIVE", "航班到达");
		currentStatusMap.put("AIR_PICKUPBILL", "制作合票清单");
		currentStatusMap.put("AIR_TRANS_PICKUPBILL", "制作中转提货清单");
		currentStatusMap.put("SIGN_RFC", "库存中");
		currentStatusMap.put("UNLOAD", "已卸车");
		currentStatusMap.put("TRAY_SCAN", "进入货区");
		currentStatusMap.put("UNLOAD_DELIVER", "已卸车");
		currentStatusMap.put("LOAD_SCAN", "待出库");
		currentStatusMap.put("LOAD_SUBMIT", "已出库");
		currentStatusMap.put("HANDOVER", "已出库");
		currentStatusMap.put("PACKAGE", "包装货区库存");
		currentStatusMap.put("PACKAGE_AREA_IN", "包装货区库存");
		currentStatusMap.put("PACKAGE_AREA_OUT", "库存中");
		currentStatusMap.put("NORMAL_AREA_IN", "普通货区库存");
		currentStatusMap.put("NORMAL_AREA_OUT", "库存中");
		currentStatusMap.put("PACKAGE_AREA_IN_EXP", "贵重货区库存");
		currentStatusMap.put("PACKAGE_AREA_OUT_EXP", "库存中");
		currentStatusMap.put("STOCKCHECKING_MORE", "库存中");
		currentStatusMap.put("STOCKCHECKING_NORMAL", "库存中");
		currentStatusMap.put("STOCKCHECKING_AIR", "库存中");
		currentStatusMap.put("PART_LINE", "已出库");
		currentStatusMap.put("LDP_HANDOVER", "已出库");
		currentStatusMap.put("LDP_PARTIAL_LINE", "已出库");
		currentStatusMap.put(OPERATE_TYPE_AIR_HANDOVER_DETAIL_IN, "库存中");
		currentStatusMap.put(OPERATE_TYPE_AIR_HANDOVER_DETAIL_OUT, "已出库");
		currentStatusMap.put("PARTIAL_LINE", "已出库");
		currentStatusMap.put("AIR_UN_SHIPPED_GOODS", "库存中");
		currentStatusMap.put("MODIFY_HANDOVERBILL", "库存中");
		currentStatusMap.put("CANCEL_HANDOVERBILL", "库存中");
		currentStatusMap.put("EXCEPTION_AREA", "库存中");
		currentStatusMap.put("SPECIAL_AREA", "库存中");
		currentStatusMap.put("LOAD_MORE", "库存中");
		currentStatusMap.put("LOSS_GOODS_FIND", "库存中");
		currentStatusMap.put("UNLOAD_LOSS_GOODS_FIND", "库存中");
		currentStatusMap.put("NO_LABEL", "库存中");
		currentStatusMap.put("NO_LABEL_ORIGINAL", "库存中");
		currentStatusMap.put("PATH_CHANGE_IN", "库存中");
		currentStatusMap.put("CONTRABAND_STATION", "库存中");
		currentStatusMap.put("UNLOAD_MORE_GOODS", "库存中");
		currentStatusMap.put("WHOLE_VEHICLE", "库存中");
		currentStatusMap.put("AFTER_PACKAGE_NEW_GOODS", "库存中");
		currentStatusMap.put("WHOLE_VEHICLE_ARRIVAL", "库存中");
		currentStatusMap.put("WAYBILL_RFC", "库存中");
		currentStatusMap.put("WHOLE_MODIFY_HANDOVERBILL", "库存中");
		currentStatusMap.put("WHOLE_CANCEL_HANDOVERBILL", "库存中");
		currentStatusMap.put("BULK_GOODS", "库存中");
		currentStatusMap.put("LOSE_GOODS", "库存中");
		currentStatusMap.put("STOCKTAKING_LOSS_GOODS_FIND", "库存中");
		currentStatusMap.put("ADJUST_STOCK_TO_STATION", "库存中");
		currentStatusMap.put("CANCEL_DELIVERY_LOAD", "库存中");
		currentStatusMap.put("RETURN_CARGO", "库存中");
		currentStatusMap.put("SINGLE", "已出库");
		currentStatusMap.put("FORMAL_HANDOVERBILL", "已出库");
		currentStatusMap.put("AFTER_PACKAGE", "已出库");
		currentStatusMap.put("PATH_CHANGE_OUT", "已出库");
		currentStatusMap.put("WAYBILL_INVALID", "已出库");
		currentStatusMap.put("ABANDON_GOODS", "已出库");
		currentStatusMap.put("WAYBILL_PDA_PENDING", "库存中");
		currentStatusMap.put("SORTING_SCAN", "上分拣");
		currentStatusMap.put("Express_PackagIng", "建包中");
		currentStatusMap.put("AIR_NOTIFY_CUSTOMERS", "空运客户通知");
		currentStatusMap.put("Express_Packaged", "已建包");
		currentStatusMap.put("UNLOAD_TASK", "卸车中");
		currentStatusMap.put("PICK_TASK", "接货中");
		currentStatusMap.put("PICK_SUBMIT", "完成接货");
		currentStatusMap.put("ADDITIONAL_RECORDING", "待补录");
		currentStatusMap.put("IS_PDASCAN_INACTIVE", "已揽收");
		currentStatusMap.put("AGENT_TO_AIRPORT_PICK_UP", "代理到机场提货");
		currentStatusMap.put("GOODS_ARRIVE_AGENCY", "货物到达代理处");
		/***********OPP状态***********/
		currentStatusMap.put("OPP_Status", "运输中");
		currentStatusMap.put("IN", "点单中");
		currentStatusMap.put("END", "已点单");
		currentStatusMap.put("PICKUP", "已提货");
		currentStatusMap.put("DRAWOUT", "已出车");
		currentStatusMap.put("FINISHINSTALL", "安装完成");
		currentStatusMap.put("SIGN", "已完工");
		currentStatusMap.put("VERIFICATION", "已核销");
		currentStatusMap.put("APPOINTMENT", "预约");
		currentStatusMap.put("DISTRIBUTION", "分配");
		currentStatusMap.put("PCMODIFY", "PC修改");
		currentStatusMap.put("APPMODIFY", "APP修改");
		currentStatusMap.put("MEMO", "添加备注");
	}

	//	// 入库类型
	//	public final static Map<String, String> inStockStatusMap = new HashMap<String, String>();
	//	static {
	//		inStockStatusMap.put("MODIFY_HANDOVERBILL", "修改交接单重新入库");
	//		inStockStatusMap.put("CANCEL_HANDOVERBILL", "作废交接单重新入库");
	//		inStockStatusMap.put("EXCEPTION_AREA", "入库异常货区");
	//		inStockStatusMap.put("SPECIAL_AREA", "登入特殊货区");
	//		inStockStatusMap.put("LOAD_MORE", "等装车多货自提");
	//		inStockStatusMap.put("LOSS_GOODS_FIND", "少货找到");
	//		inStockStatusMap.put("UNLOAD_LOSS_GOODS_FIND", "卸车少货找到");
	//		inStockStatusMap.put("NO_LABEL", "无标签货物入库");
	//		inStockStatusMap.put("NO_LABEL_ORIGINAL", "无标签找到入库");
	//		inStockStatusMap.put("PATH_CHANGE", "调整走货路径入库");
	//		inStockStatusMap.put("CONTRABAND_STATION", "违禁品移交");
	//		inStockStatusMap.put("UNLOAD_MORE_GOODS", "卸车多货入库");
	//		inStockStatusMap.put("WHOLE_VEHICLE", "整车开单入库");
	//		inStockStatusMap.put("AFTER_PACKAGE_NEW_GOODS", "包装后新流水入库");
	//		inStockStatusMap.put("WHOLE_VEHICLE_ARRIVAL", "整车到达入库");
	//		inStockStatusMap.put("WAYBILL_RFC", "更改单调整入库");
	//		inStockStatusMap.put("WHOLE_MODIFY_HANDOVERBILL", "整车修改交接单重新入库");
	//		inStockStatusMap.put("WHOLE_CANCEL_HANDOVERBILL", "整车作废交接单重新入库");
	//		inStockStatusMap.put("BULK_GOODS", "散货入库");
	//		inStockStatusMap.put("LOSE_GOODS", "少货入库到特殊组织");
	//		inStockStatusMap.put("STOCKTAKING_LOSS_GOODS_FIND", "清仓少货找到");
	//		inStockStatusMap.put("ADJUST_STOCK_TO_STATION", "调整外场库存到派送货区库存");
	//		inStockStatusMap.put("CANCEL_DELIVERY_LOAD", "取消派送装车");
	//	}
	//
	//	// 出库类型
	//	public final static Map<String, String> outStockStatusMap = new HashMap<String, String>();
	//	static {
	//		outStockStatusMap.put("SINGLE", "单票出库");
	//		outStockStatusMap.put("FORMAL_HANDOVERBILL", "交接出库");
	//		outStockStatusMap.put("AFTER_PACKAGE", "包装后原流水出库");
	//		outStockStatusMap.put("PATH_CHANGE", "调整走货路径出库");
	//		outStockStatusMap.put("WAYBILL_INVALID", "运单作废出库");
	//		outStockStatusMap.put("ABANDON_GOODS", "弃货出库");
	//	}

	// 所有入库的类型
	public final static String[] inStockStatusStr = { "MODIFY_HANDOVERBILL", "CANCEL_HANDOVERBILL", "EXCEPTION_AREA",  "LOAD_MORE", "LOSS_GOODS_FIND", "UNLOAD_LOSS_GOODS_FIND", "NO_LABEL", "NO_LABEL_ORIGINAL", "PATH_CHANGE",
			"CONTRABAND_STATION", "UNLOAD_MORE_GOODS", "WHOLE_VEHICLE", "AFTER_PACKAGE_NEW_GOODS", "WHOLE_VEHICLE_ARRIVAL", "WAYBILL_RFC", "WHOLE_MODIFY_HANDOVERBILL", "WHOLE_CANCEL_HANDOVERBILL", "BULK_GOODS", "LOSE_GOODS",
			"STOCKTAKING_LOSS_GOODS_FIND", "ADJUST_STOCK_TO_STATION", "CANCEL_DELIVERY_LOAD", "WAYBILL_PDA_PENDING","RETURN_CARGO" };

	// 所有出库的类型
	public final static String[] outStockStatusStr = { "SINGLE", "FORMAL_HANDOVERBILL", "AFTER_PACKAGE", "PATH_CHANGE", "WAYBILL_INVALID", "ABANDON_GOODS" };

	/**
	 * 操作部门
	 */
	public static final int OPERATE_ORG = 1;
	/**
	 * 下一站
	 */
	public static final int NEXT_ORG = 2;
	/**
	 * 目的站
	 */
	public static final int DEST_ORG = 3;
	/**
	 * 10
	 */
	public static final int TEN = 10;
	/**
	 * 0
	 */
	public static final int ZERO = 0;
	/**
	 * pda调用
	 */
	public static final String INVOKING_SOURCE_PDA = "PDA";
	/**
	 * 官网调用
	 */
	public static final String INVOKING_SOURCE_WEBSITE = "WEBSITE";
	/**
	 * FOSS综合查询调用
	 */
	public static final String INVOKING_SOURCE_FOSS = "FOSS";
	// 一级产品-空运
	public static final String PRICING_PRODUCT_C1_C20002 = "TRANS_AIRCRAFT";// C10002
	/**
	 * 运单轨迹接口（供CC调用）货物状态--作废
	 */
	public static final String TRAJICTORY_CC_OBSOLETE  = "OBSOLETE";
	/**
	 * 运单轨迹接口（供CC调用）货物状态--终止
	 */
	public static final String TRAJICTORY_CC_ABORTED  = "ABORTED";
	/**
	 * 运单轨迹接口（供CC调用）货物状态--已到达
	 */
	public static final String TRAJICTORY_CC_STATION_IN  = "STATION_IN";
	/**
	 * 运单轨迹接口（供CC调用）货物状态--派送中
	 */
	public static final String TRAJICTORY_CC_DELIVERING  = "DELIVERING";
	/**
	 * 运单轨迹接口（供CC调用）货物状态--正常签收
	 */
	public static final String TRAJICTORY_CC_NORMAL_SIGN  = "NORMAL_SIGN";
	/**
	 * 运单轨迹接口（供CC调用）货物状态--异常签收
	 */
	public static final String TRAJICTORY_CC_UNNORMAL_SIGN  = "UNNORMAL_SIGN";
	/**
	 * 运单轨迹接口（供CC调用）货物状态--已外发
	 */
	public static final String TRAJICTORY_CC_IS_OUTER  = "IS_OUTER";
	/**
	 * 运单轨迹接口（供CC调用）货物状态--已开单
	 */
	public static final String TRAJICTORY_CC_EFFECTIVE  = "EFFECTIVE";
	/**
	 * 运单轨迹接口（供CC调用）货物状态--运输在库
	 */
	public static final String TRAJICTORY_CC_TFR_INSTOCK  = "TFR_INSTOCK";
	/**
	 * 运单轨迹接口（供CC调用）货物状态--运输不在库
	 */
	public static final String TRAJICTORY_CC_TFR_OUTSTOCK  = "TFR_OUTSTOCK";
	
	/**
	 * 装车类型--派件装车
	 */
	public static final String TRAJICTORY_EXPRESS_SENDPIECE_LOAD = "EXPRESS_SENDPIECE_LOAD";
	
	/**
	 * 装车类型--接驳装车
	 */
	public static final String TRAJICTORY_EXPRESS_CONNECTION_LOAD = "EXPRESS_CONNECTION_LOAD";
	
	/**
	 * 装车类型--司机装车
	 */
	public static final String TRAJICTORY_EXPRESS_DRIVER_LOAD = "EXPRESS_DRIVER_LOAD";
	
	/**
	 * 卸车类型--接驳卸车
	 */
	public static final String TRAJICTORY_SC_EXPRESS = "SC_EXPRESS";
	//点单任务状态 点单中 272681
	public static final String ORDER_TASK_STATE_IN = "IN";
		
    //点单完毕 272681
    public static final String ORDER_TASK_STATE_END= "END";
	
}