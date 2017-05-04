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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/define/LoadConstants.java
 *  
 *  FILE NAME          :LoadConstants.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.shared.define
 * FILE    NAME: LoadConstants.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.define;


import com.deppon.foss.util.define.FossConstants;

/**
 * 定义装车过程中用到的常量类
 * @author dp-duyi/dp-shiwei/dp-pengzhen
 * @date 2012-11-1 上午11:22:02
 */
public class LoadConstants {
	/**装车入库次数*/
	public static final int LOAD_INSTOCK_TIMES = 3;
	/**装车入库等待时间*/
	public static final int LOAD_INSTOCK_WAIT_TIME = 3000;
	/**扫描等待时间*/
	public static final int PDA_SCAN_OUTTIME = 15;
	public static final int Transaction_TIME_OUT_TIME = 3;
	/**最大流水号数*/
	public static final int MAX_SERIAL_NUM = 9999;
	/**查询数据最大条数*/
	public static final int MAX_TOTAL_NUM = 100000;
	
	/**体积进率*/
	public static final int VOLUME_SIZE = 1000;
	
	/**保险价值乘*/
	public static final int INSURANCE_VALUE_MULTIPLY = 100;
	
	/**重泡比参数*/
	public static final int WEIGHT_VOLUME_SCALE_THREE = 3;
	
	/**已分配派送装车任务状态*/
	//未开始
	public static final String ASSIGN_DELIVER_LOAD_TASK_STATE_UNSTART = "UNSTART";
	//进行中
	public static final String ASSIGN_DELIVER_LOAD_TASK_STATE_PROCESSING = "PROCESSING";
	//已完成
	public static final String ASSIGN_DELIVER_LOAD_TASK_STATE_FINISHED = "FINISHED";
	
	/**差异报告状态*/
	//已生成
	public static final String DELIVER_LOAD_GAP_REPORT_STATE_GENERATED = "GENERATED";
	//已打回
	public static final String DELIVER_LOAD_GAP_REPORT_STATE_BACK = "BACK";
	//已确认
	public static final String DELIVER_LOAD_GAP_REPORT_STATE_AFFIRM = "AFFIRM";
	
	/**差异报告是否有效*/
	//有效
	public static final String DELIVER_LOAD_GAP_REPORT_VALID = FossConstants.YES;
	//无效
	public static final String DELIVER_LOAD_GAP_REPORT_INVALID = FossConstants.NO;
	
	/**装车任务状态*/
	//进行中-LOADING
	public static final String LOAD_TASK_STATE_LOADING = "LOADING";
	//已完成-FINISHEN
	public static final String LOAD_TASK_STATE_FINISHEN = "FINISHED";
	//已提交-SUBMITED
	public static final String LOAD_TASK_STATE_SUBMITED = "SUBMITED";
	//已取消-CANCELED
	public static final String LOAD_TASK_STATE_CANCELED = "CANCELED";
	
	/**装车任务类型*/
	//派送装车-DELIVER_LOAD 
	public static final String LOAD_TASK_TYPE_DELIVER = "DELIVER_LOAD";
	//偏线装车-PARTIALLINE_LOAD
	public static final String LOAD_TASK_TYPE_PARTIALLINE = "PARTIALLINE_LOAD";
	//长途装车-LONG_DISTANCE_LOAD
	public static final String LOAD_TASK_TYPE_LONG_DISTANCE = "LONG_DISTANCE_LOAD";
	//短途装车-SHORT_DISTANCE_LOAD
	public static final String LOAD_TASK_TYPE_SHORT_DISTANCE = "SHORT_DISTANCE_LOAD";
	//落地配装车-SHORT_DISTANCE_LOAD
	public static final String LOAD_TASK_TYPE_LDP = "LDP_LOAD";
	//快递接货装车-EXPRESS_PICK_LOAD
	public static final String LOAD_TASK_TYPE_EXPRESS_PICK_LOAD = "EXPRESS_PICK_LOAD";
	//二次接驳-司机装车
	public static final String LOAD_TASK_TYPE_EXPRESS_DRIVER_LOAD = "EXPRESS_DRIVER_LOAD";
	//二次接驳-理货员最终外场装车
	public static final String LOAD_TASK_TYPE_EXPRESS_CONNECTION_LOAD = "EXPRESS_CONNECTION_LOAD";
	//二次接驳-派件装车
	public static final String LOAD_TASK_TYPE_EXPRESS_SENDPIECE_LOAD = "EXPRESS_SENDPIECE_LOAD";
	/**查询使用*/
	public static final String LOAD_TASK_TYPE_ALL = "ALL";
	
	/**理货员参与情况类型*/
	//中转装车-TRANSFER_LOAD
	public static final String LOADER_PARTICIPATION_TRANSFER_LOAD = "TRANSFER_LOAD";
	//派送装车-DELIVER_LOAD
	public static final String LOADER_PARTICIPATION_DELIVER_LOAD = "DELIVER_LOAD";
	//快递接货装车-EXPRESS_PICK_LOAD
	public static final String LOADER_PARTICIPATION_EXPRESS_PICK_LOAD = "EXPRESS_PICK_LOAD";
	//二程接驳-EXPRESS_DRIVER_LOAD
	public static final String LOADER_PARTICIPATION_EXPRESS_DRIVER_LOAD = "EXPRESS_DRIVER_LOAD";
	//二次接驳-理货员最终外场装车
	public static final String LOADER_PARTICIPATION__EXPRESS_CONNECTION_LOAD = "EXPRESS_CONNECTION_LOAD";
	//中转卸车-TRANSFER_UNLOAD 
	public static final String LOADER_PARTICIPATION_TRANSFER_UNLOAD = "TRANSFER_UNLOAD";
	//集中接送货卸车-DELIVER_UNLOAD
	public static final String LOADER_PARTICIPATION_DELIVER_UNLOAD = "DELIVER_UNLOAD";
	//快递包-EXPRESS_PACKAGE
	public static final String LOADER_PARTICIPATION_EXPRESS_PACKAGE = "EXPRESS_PACKAGE";
	//二程接驳卸车任务类型-SC_UNLOAD
	public static final String LOADER_PARTICIPATION_SC_UNLOAD="SC_UNLOAD";
	
	/**装卸车车工作量常量*/
	//装卸车车工作量操作类型：装车
	public static final String LOADER_WORKLOAD_HANDLE_TYPE_LOAD = "LOAD";
	//装卸车车工作量操作类型：卸车
	public static final String LOADER_WORKLOAD_HANDLE_TYPE_UNLOAD = "UNLOAD";
	//装卸车车工作量任务类型：中转
	public static final String LOADER_WORKLOAD_TASK_TYPE_TRANSFER = "TRANSFER";
	//装卸车车工作量任务类型：派送
	public static final String LOADER_WORKLOAD_TASK_TYPE_PICKUP = "PICKUP";
	
	/**装车货物类型*/
	//正常
	public static final String LOAD_GOODS_STATE_NORMAL = "NORMAL";
	//取消
	//public static final String LOAD_GOODS_STATE_CANCELED = "CANCELED";
	//未装车
	public static final String LOAD_GOODS_STATE_NOT_LOADING = "NOT_LOADING";
	//多货
	public static final String LOAD_GOODS_STATE_MORE = "MORE";
	//多货-夹带
	//public static final String LOAD_GOODS_STATE_MORE_ENTRAINED = "MORE_ENTRAINED";
	//多货-异地夹带
	//public static final String LOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED = "MORE_ALLOPATRY_ENTRAINED";
	//少货
	public static final String LOAD_GOODS_STATE_LACK = "LACK";
	//强装-未包装
	//public static final String LOAD_GOODS_STATE_EXTRA_UNPACKAGE = "EXTRA_UNPACKAGE";
	//强装-有更改
	//public static final String LOAD_GOODS_STATE_EXTRA_MODIFY = "EXTRA_MODIFY";
	//强装-未出包装货区
	//public static final String LOAD_GOODS_STATE_EXTRA_PACKAGE_UNOUT_STORAGE = "EXTRA_PACKAGE_UNOUT_STORAGE";
	//强装-未出贵重物品货区
	//public static final String LOAD_GOODS_STATE_EXTRA_VALUABLES_UNOUT_STORAGE = "EXTRA_VALUABLES_UNOUT_STORAGE";
	//强装-未预配
	//public static final String LOAD_GOODS_STATE_EXTRA_UNPREWIRED = "EXTRA_UNPREWIRED";
	//强装-强装
	//public static final String LOAD_GOODS_STATE_EXTRA = "EXTRA";
	//强装-夹带
	//public static final String LOAD_GOODS_STATE_EXTRA_ENTRAINED = "EXTRA_ENTRAINED";
	//强装-异地夹带
	//public static final String LOAD_GOODS_STATE_EXTRA_ALLOPATRY_ENTRAINED = "EXTRA_ALLOPATRY_ENTRAINED";
	//无效
	//public static final String LOAD_GOODS_STATE_INVALID = "INVALID";
	public static final String BE_LOAD_COURIER = "C";
	/**交接单与配载单用到的常量类*/
	//全部
	public static final String LOAD_GOODSTYPE_ALL = "ALL";
	//A类
	public static final String LOAD_GOODSTYPE_A_TYPE = "A_TYPE";
	//B类
	public static final String LOAD_GOODSTYPE_B_TYPE = "B_TYPE";
	
	/**交接单类型*/
	//集配交接单
	public static final String HANDOVER_TYPE_LONG_DISTANCE = "LONG_DISTANCE_HANDOVER";
	//短配交接单
	public static final String HANDOVER_TYPE_SHORT_DISTANCE = "SHORT_DISTANCE_HANDOVER";
	//外发交接单
	public static final String HANDOVER_TYPE_PARTIALLINE= "PARTIALLINE_HANDOVER";
	//落地配交接单
	public static final String HANDOVER_TYPE_LDP = "LDP_HANDOVER";
	//分部交接单
	public static final String HANDOVER_TYPE_DIVISION= "DIVISION_HANDOVER";
	//接驳交接单
	public static final String HANDOVER_TYPE_DIVISION_CONNECTION="EXPRESS_CONNECTION_HANDOVER";
	//营业部交接单
	public static final String HANDOVER_TYPE_SALES_DEPARTMENT="SALES_DEPARTMENT_HANDOVER";
	
	/**交接单状态常量*/
	//10：已预配
	public static final int HANDOVERBILL_STATE_PREPARE_HANDOVER = 10;
	//20：已交接
	public static final int HANDOVERBILL_STATE_FORMAL_HANDOVER = 20;
	//21：已配载 (集配交接单专属状态)
	public static final int HANDOVERBILL_STATE_ALREADY_ASSEMBLE = 21;
	//30：已出发
	public static final int HANDOVERBILL_STATE_ALREADY_DEPART = 30;
	//40：已到达
	public static final int HANDOVERBILL_STATE_ALREADY_ARRIVE = 40;
	//50：已入库
	public static final int HANDOVERBILL_STATE_ALREADY_INSTOCK = 50;
	//90：已作废
	public static final int HANDOVERBILL_STATE_ALREADY_CANCEL = 90;
	
	/**快递交接单状态常量*/
	//已交接：HAVE_PLACED，
	public static final String WKHANDOVERBILL_STATE_FORMAL_HANDOVER = "HAVE_PLACED";
	//已出发：HAVE_DEPART
	public static final String WKHANDOVERBILL_STATE_ALREADY_DEPART = "HAVE_DEPART";
	//已到达：HAVE_ARRIVE
	public static final String WKHANDOVERBILL_STATE_ALREADY_ARRIVE = "HAVE_ARRIVE";
	//已入库：HAVE_STOCK
	public static final String WKHANDOVERBILL_STATE_ALREADY_INSTOCK = "HAVE_STOCK";
	//已作废 HAVE_CANLEL
	public static final String WKHANDOVERBILL_STATE_ALREADY_CANCEL = "HAVE_CANCEL";
	
	/**快递交接单类型*/
	//长途交接单：LONG_DISTANCE_HANDOVER
	public static final String WKHANDOVER_TYPE_LONG_DISTANCE = "LONG_DISTANCE_HANDOVER";
	//短途交接单：SHORT_DISTANCE_HANDOVER
	public static final String WKHANDOVER_TYPE_SHORT_DISTANCE = "SHORT_DISTANCE_HANDOVER";
	//航空交接单：AIR_TRANS_HANDOVER
	public static final String WKHANDOVER_TYPE_AIR_TRANS_HANDOVER= "AIR_TRANS_HANDOVER";
	
	//10：未发车，20：已出发，30：已到达，90：已作废
	public static final int VEHICLEASSEMBLEBILL_STATE_NOT_DEPART = 10;
	
	public static final int VEHICLEASSEMBLEBILL_STATE_ALREADY_DEPART = 20;
	
	public static final int VEHICLEASSEMBLEBILL_STATE_ALREADY_ARRIVE = 30;
	
	public static final int VEHICLEASSEMBLEBILL_STATE_ALREADY_CANCEL = 90;
	
	/**配载单配载类型常量*/
	//专线
	public static final String VEHICLE_ASSEMBLE_TYPE_OWNER_LINE = "OWNER_LINE";
	//整车
	public static final String VEHICLE_ASSEMBLE_TYPE_CAR_LOAD = "CAR_LOAD";
	
	/**奖罚协议类型*/
	public static final String REWARDPROT_TYPE_REWARD ="REWARD";
	public static final String REWARDPROT_TYPE_FINE ="FINE";
	/**转货类型*/
	public static final String TRANSITGOODS_TYPE_TRAN = "TRANSITGOODS";
	public static final String TRANSITGOODS_TYPE_TAKE = "CARRYGOODS";
	/**配载单运输性质常量*/
	/**精准卡航**/
	public static final String VEHICLE_ASSEMBLE_TRANSPROPERTY_TRUCK_LIKE_PLAN = "TRUCK_LIKE_PLAN";
	/**精准汽运（长）**/
	public static final String VEHICLE_ASSEMBLE_TRANSPROPERTY_LONG_DISTANCE = "LONG_DISTANCE";
	/**精准包裹**/
	public static final String VEHICLE_ASSEMBLE_TRANSPROPERTY_TRUCK_LIKE_PCP = "TRUCK_LIKE_PCP";
	
	/**辆所有权类型常量*/
	//自有车
	public static final String VEHICLE_OWNERSHIP_OWNER_VEHICLE = "OWNER_VEHICLE";
	//普通外请车
	public static final String VEHICLE_OWNERSHIP_LEASED_VEHICLE = "LEASED_VEHICLE";
	//外请合同车
	public static final String VEHICLE_OWNERSHIP_BARGAIN_VEHICLE = "BARGAIN_VEHICLE";
	
	/**
	 * GPS非失败的状态
	 */
	public static final String VEHICLE_GPS_TASK_NOT_FAIL = "M";
	
	/**
	 * 产品类型，精准空运
	 */
	public static final String PRODUCT_CODE_AF = "AF";
	
	/**
	 * 产品类型，精准卡航
	 */
	public static final String PRODUCT_CODE_FLF = "FLF";
	
	/**
	 * 产品类型，精准城运
	 */
	public static final String PRODUCT_CODE_FSF = "FSF";
	
	/**
	 * 产品类型，精准大票卡航
	 */
	public static final String PRODUCT_CODE_BGFLF = "BGFLF";
	
	/**
	 * 产品类型，精准大票城运
	 */
	public static final String PRODUCT_CODE_BGFSF = "BGFSF";
	
	/**
	 * 产品类型，快递
	 */
	public static final String PRODUCT_CODE_PACKAGE = "PACKAGE";
	/**
	 * 产品类型，快递 3.60特惠件
	 */
	public static final String PRODUCT_CODE_RCP = "RCP";
	
	/**
	 * 产品类型，电商尊享
	 */
	public static final String PRODUCT_CODE_EPEP = "EPEP";
	
	/**
	 * 产品类型，商务专递   263072 2015-10-13 15:07:41
	 */
	public static final String PRODUCT_CODE_DEAP = "DEAP";
	
	/**
	 * 交接单明细类型，用于标示交接单明细是否全部快递货
	 */
	public static final String HANDOVERBILL_DETAILS_TYPE_LTL = "LTL";
	
	/**
	 * 交接单明细类型，用于标示交接单明细是否全部零担货
	 */
	public static final String HANDOVERBILL_DETAILS_TYPE_EXPRESS = "EXPRESS";
	
	/**
	 * 若交接单明细全为快递货，则在交接单号前面加上标识K
	 */
	public static final String HANDOVERBILL_EXPRESS_PREFIX = "k";
	/**
	 * 若交接单明细全为商务专递（快递空运），则在交接单号前面加上标识ky
	 */
	public static final String HANDOVERBILL_EXPRESS_AIR_FREIGHT = "ky";
	
	
	/**
	 * 交接单为包交接，则单号前面有B
	 * */
	public static final String HANDOVERBILL_EXPRESS_PAKAGE = "B";
	
	/**
	 * 短信模版编码
	 */
	public static final String EXPRESS_TOPAY_SMS = "EXPRESS_DELIVERY_TOPAY";
	public static final String EXPRESS_NO_TOPAY_SMS = "EXPRESS_DELIVERY_NO_TOPAY";
	
	/**
	 * 二次接驳交接单类型
	 */
	public static final String HANDOVERBILL_TYPE = "EXPRESS_DRIVER";

	/** 
	 * 快递或者零担 272681
	 */
	public static final String EXPRESS = "EXPRESS";
	public static final String LD= "LD";
	
	/** 营业部交接管理
	 * 专用虚拟车牌 360903
	 */
	public static final String VEHICLE_NO_SALE= "营HBZY";
	
	/**sonar_constants_number*/
	public final static int SONAR_NUMBER_0 = 0;
	public final static int SONAR_NUMBER_1 = 1;
	public final static int SONAR_NUMBER_2 = 2;
	public final static int SONAR_NUMBER_3 = 3;
	public final static int SONAR_NUMBER_4 = 4;
	public final static int SONAR_NUMBER_5 = 5;
	public final static int SONAR_NUMBER_6 = 6;
	public final static int SONAR_NUMBER_7 = 7;
	public final static int SONAR_NUMBER_8 = 8;
	public final static int SONAR_NUMBER_9 = 9;
	public final static int SONAR_NUMBER_10 = 10;
	public final static int SONAR_NUMBER_11 = 11;
	public final static int SONAR_NUMBER_12 = 12;
	public final static int SONAR_NUMBER_13 = 13;
	public final static int SONAR_NUMBER_14 = 14;
	public final static int SONAR_NUMBER_15 = 15;
	public final static int SONAR_NUMBER_16 = 16;
	public final static int SONAR_NUMBER_17 = 17;
	public final static int SONAR_NUMBER_18 = 18;
	public final static int SONAR_NUMBER_19 = 19;
	public final static int SONAR_NUMBER_20 = 20;
	public final static int SONAR_NUMBER_21 = 21;
	public final static int SONAR_NUMBER_22 = 22;
	public static final int SONAR_NUMBER_23 = 23;
	public static final int SONAR_NUMBER_24 = 24;
	public static final int SONAR_NUMBER_25 = 25;
	public static final int SONAR_NUMBER_26 = 26;
	public static final int SONAR_NUMBER_27 = 27;
	public static final int SONAR_NUMBER_28 = 28;
	public static final int SONAR_NUMBER_29 = 29;
	public final static int SONAR_NUMBER_30 = 30;
	public final static int SONAR_NUMBER_31 = 31;
	public final static int SONAR_NUMBER_32 = 32;
	public final static int SONAR_NUMBER_33 = 33;
	public final static int SONAR_NUMBER_34 = 34;
	public final static int SONAR_NUMBER_35 = 35;
	public final static int SONAR_NUMBER_36 = 36;
	public final static int SONAR_NUMBER_37 = 37;
	public final static int SONAR_NUMBER_38 = 38;
	public final static int SONAR_NUMBER_39 = 39;
	public static final int SONAR_NUMBER_40 = 40;
	public static final int SONAR_NUMBER_41 = 41;
	public static final int SONAR_NUMBER_42 = 42;
	public static final int SONAR_NUMBER_43 = 43;
	public static final int SONAR_NUMBER_44 = 44;
	public static final int SONAR_NUMBER_45 = 45;
	public static final int SONAR_NUMBER_46 = 46;
	public static final int SONAR_NUMBER_47 = 47;
	public static final int SONAR_NUMBER_48 = 48;
	public static final int SONAR_NUMBER_49 = 49;
	public static final int SONAR_NUMBER_50 = 50;
	public static final int SONAR_NUMBER_51 = 51;
	public static final int SONAR_NUMBER_52 = 52;
	public static final int SONAR_NUMBER_53 = 53;
	public static final int SONAR_NUMBER_54 = 54;
	public static final int SONAR_NUMBER_55 = 55;
	public static final int SONAR_NUMBER_56 = 56;
	public static final int SONAR_NUMBER_57 = 57;
	public static final int SONAR_NUMBER_58 = 58;
	public static final int SONAR_NUMBER_59 = 59;
	public static final int SONAR_NUMBER_60 = 60;
	public static final int SONAR_NUMBER_61 = 61;
	public static final int SONAR_NUMBER_62 = 62;
	public final static int SONAR_NUMBER_100 = 100;
	public static final int SONAR_NUMBER_300 = 300;
	public final static int SONAR_NUMBER_384 = 384;
	public static final int SONAR_NUMBER_499 = 499;
	public static final int SONAR_NUMBER_500 = 500;
	public static final int SONAR_NUMBER_650 = 650;
	public static final int SONAR_NUMBER_900 = 900;
	public final static int SONAR_NUMBER_1000 = 1000;
	public static final int SONAR_NUMBER_1001 = 1001;
	public static final int SONAR_NUMBER_2000 = 2000;
	public final static int SONAR_NUMBER_3499 = 3499;
	public final static int SONAR_NUMBER_3500 = 3500;
	public static final int SONAR_NUMBER_5000 = 5000;
	public static final int SONAR_NUMBER_10000 = 10000;
	public static final int SONAR_NUMBER_20000 = 20000;
}
