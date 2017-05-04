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
 *  PROJECT NAME  : tfr-stock-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/api/define/StockConstants.java
 *  
 *  FILE NAME          :StockConstants.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stock.api.define;
/**
 * 定义库存相关的常量
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 上午11:45:26
 */
public class StockConstants {
	
	/**
	 * 出入库设备类型
	 */
	/**PC*/
	public static final String PC_DEVICE_TYPE = "PC";
	/**PDA*/
	public static final String PDA_DEVICE_TYPE = "PDA";
	
	/**
	 * 入库类型
	 */
	/** 快递代理拉回 */
	public static final String PACKAGE_RETURN = "PACKAGE_RETURN";
	/**开单入库*/
	public static final String CREATE_WAYBILL_IN_STOCK_TYPE = "CREATE_WAYBILL";
	/**修改交接单重新入库*/
	public static final String MODIFY_HANDOVERBILL_DELETE_SERIALNO = "MODIFY_HANDOVERBILL";
	/**作废交接单重新入库*/
	public static final String CANCEL_HANDOVERBILL = "CANCEL_HANDOVERBILL";
	/**入库包装货区*/
	public static final String PACKAGE_AREA_IN_STOCK_TYPE = "PACKAGE_AREA";
	/**入库异常货区*/
	public static final String EXCEPTION_AREA_IN_STOCK_TYPE = "EXCEPTION_AREA";
	/**入库贵重物品货区*/
	public static final String VALUABLE_AREA_IN_STOCK_TYPE = "VALUABLE_AREA";
	
	/**
	 * 入库类型  开单 返货类型
	 */
	public final static String WAYBILL_EXPRESSTYPE_RETURN_CARGO ="RETURN_CARGO";
	
	/**登入特殊货区*/
	public static final String SPECIAL_AREA_IN_STOCK_TYPE = "SPECIAL_AREA";
	/**从特殊货区登入到正常货区*/
	public static final String NORMAL_AREA_FROM_SPECIAL_IN_STOCK_TYPE = "SPECIAL_NORMAL";
	
	/**入库包装货区到正常货区*/
	public static final String PACKAGE_AREA_IN_STOCK_TYPE_NORMAL = "PACKAGE_AREA_NORMAL";
	/**入库异常货区到正常货区*/
	public static final String EXCEPTION_AREA_IN_STOCK_TYPE_NORMAL = "EXCEPTION_AREA_NORMAL";
	/**入库贵重物品货区到正常货区*/
	public static final String VALUABLE_AREA_IN_STOCK_TYPE_NORMAL = "VALUABLE_AREA_NORMAL";
	
	/**装车多货*/
	public static final String LOAD_MORE_GOODS_IN_STOCK_TYPE = "LOAD_MORE";
	/**提交装车任务*/
	public static final String SUBMIT_LOAD_TASK_IN_STOCK_TYPE = "SUBMIT_LOAD";
	
	/**少货找到*/
	public static final String LOSE_GOODS_FOUND_IN_STOCK_TYPE = "LOSS_GOODS_FIND";
	/**少货找到PDA扫描*/
	public static final String LOSE_GOODS_FOUND_PDA_IN_STOCK_TYPE = "LOSS_GOODS_FIND_PDA";
	
	/** 卸车少货找到*/
	public static final String UNLOAD_LOSE_GOODS_FOUND_IN_STOCK_TYPE = "UNLOAD_LOSS_GOODS_FIND";
	
	/**异常签收*/
	public static final String EXCEPTION_SIGN_IN_STOCK_TYPE = "SIGN_EXCEPTION";
	
	/**偏线拉回  */
	public static final String PARTIALLINE_RETURN_IN_STOCK_TYPE = "PARTIALLINE_RETURN";
	
	/**空运拉回*/
	public static final String AIR_FREIGHT_RETURN_SIGN_IN_STOCK_TYPE = "AIR_RETURN";
	
	/**派送拉回*/
	public static final String SEND_RETURN_IN_STOCK_TYPE = "SEND_RETURN";
	
	/**无标签货物入库异常货区*/
	public static final String NO_LABEL_GOODS_IN_STOCK_TYPE = "NO_LABEL";
	/**无标签货物原货物入库正常货区*/
	public static final String NO_LABEL_ORIGINAL_GOODS_IN_STOCK_TYPE = "NO_LABEL_ORIGINAL";
	/**调整走货路径 入库新货区*/
	public static final String TRANSPORT_PATH_CHANGE_IN_STOCK_TYPE = "PATH_CHANGE";
	
	/**清仓多货*/
	public static final String STOCKCHECKING_MORE_GOODS_IN_STOCK_TYPE = "STOCKCHECKING_MORE";
	
	/**落货入库 AirUnshippedGoods*/
	public static final String AIR_UNSHIPPED_GOODS_IN_STOCK_TYPE = "AIR_UNSHIPPED_GOODS";
	
	/**违禁品入库驻地派送部*/
	public static final String CONTRABAND_STATION_IN_STOCK_TYPE = "CONTRABAND_STATION";
	
	/**卸车入库*/
	public static final String UNLOAD_GOODS_IN_STOCK_TYPE = "UNLOAD";
	/**卸车多货入库*/
	public static final String UNLOAD_GOODS_MORE_IN_STOCK_TYPE = "UNLOAD_MORE_GOODS";
	
	/**反签收入库*/
	public static final String REVERSE_SIGN_IN_STOCK_TYPE = "REVERSE_SIGN";
	/**整车开单入库*/
	public static final String WHOLE_VEHICLE_IN_STOCK_TYPE = "WHOLE_VEHICLE";
	
	/**货件完成包装后生成新的流水号入库包装货区*/
	public static final String AFTER_PACKAGE_NEW_GOODS_IN_STOCK_TYPE = "AFTER_PACKAGE_NEW_GOODS";
	
	/**整车到达入库*/
	public static final String WHOLE_VEHICLE_ARRIVAL_IN_STOCK_TYPE = "WHOLE_VEHICLE_ARRIVAL";
	
	/**更改单调整入库*/
	public static final String WAYBILL_RFC_IN_STOCK_TYPE = "WAYBILL_RFC";
	
	/**PDA待补录调整入库*/
	public static final String WAYBILL_PDA_PENDING_IN_STOCK_TYPE = "WAYBILL_PDA_PENDING";
	
	/**整车修改交接单重新入库*/
	public static final String WHOLE_VEHICLE_MODIFY_HANDOVERBILL = "WHOLE_MODIFY_HANDOVERBILL";
	/**整车作废交接单重新入库*/
	public static final String WHOLE_VEHICLE_CANCEL_HANDOVERBILL = "WHOLE_CANCEL_HANDOVERBILL";
	/**散货货区货物入库到正常货区*/
	public static final String BULK_GOODS_IN_STOCK_TYPE = "BULK_GOODS";
	/**少货入库到特殊组织*/
	public static final String LOSE_GOODS_IN_STOCK_TYPE = "LOSE_GOODS";
	// modify by liangfuxiang 2013-6-8下午5:00:50 begin
	/** 重新计算走货路径入库 */
	public static final String RECREATE_TRANSPATH_IN_STOCK_TYPE = "RECREATE_PATH";
	// modify by liangfuxiang 2013-6-8下午5:00:54 end;
	
	// modify by liangfuxiang 2013-6-26下午2:51:34 begin BUG-37421
	/** 修改走货路径入库 */
	public static final String MODIFY_TRANSPATH_IN_STOCK_TYPE = "MODIFY_PATH";
	
	/**
	 * 入库类型 返货 
	 */
	public final static String RETURN_GOODS_OPEN_SINGLE ="RETURN_GOODS";
	
	// modify by liangfuxiang 2013-6-26下午2:52:22 end;
	

	//快递空运航空正单交接单重新入库类型 
	public static final String EXPRESS_AIR_DEL_HANDOVER_IN_STOCK="EXPRESS_AIR_DEL_HANDOVER_IN_STOCK"; 
	
	/**
	 * 用于小件
	 * 修改目的站!----补码需要修改走货路径
	 */
	public static final String CHANGE_DESTORG_PATH_FOR_EXPRESS_IN_STOCK_TYPE = "CHANGE_DESTORG_PATH_FOR_EXPRESS";
	
	/**
	 * 用于快递 补录 
	 */
	public static final String CHANGE_DESTORG_PATH_FOR_RECORDAGAIN_IN_STOCK_TYPE = "CHANGE_DESTORG_PATH_FOR_RECORDAGAIN";
	
	/** 清仓少货找到*/
	public static final String STOCKTAKING_LOSE_GOODS_FOUND_IN_STOCK_TYPE = "STOCKTAKING_LOSS_GOODS_FIND";
	
	/**调整外场库存到派送货区库存*/
	public static final String ADJUST_STOCK_TO_STATION_IN_STOCK_TYPE = "ADJUST_STOCK_TO_STATION";
	/** 取消派送装车*/
	public static final String CANCEL_DELIVERY_LOAD_IN_STOCK_TYPE = "CANCEL_DELIVERY_LOAD";
	/** 更改运单号*/
	public static final String MODIFY_WAYBILL_NO_IN_STOCK_TYPE = "MODIFY_WAYBILL_NO";
	/**PDA撤销扫描重新入库*/
	public static final String PDA_CANCEL_IN_STOCK_TYPE="PDA_CANCEL_LOAD";
	
	/**
	 * 出库类型
	 */
	/**PC查询库存界面 单件出库*/
	public static final String SINGLE_OUT_STOCK_TYPE = "SINGLE";
	/**手工生成正式交接单后，交接单中的货件出库*/
	public static final String HANDMADE_HANDOVER_BILL_OUT_STOCK_TYPE = "FORMAL_HANDOVERBILL";
	/**包装完成后旧流水号货物出库*/
	public static final String AFTER_PACKAGE_OLD_GOODS_OUT_STOCK_TYPE = "AFTER_PACKAGE";
	/**从特殊货区（异常货区、贵重物品货区、代包装货区）登出到正常货区*/
	public static final String LONOUT_SPECIAL_GOODSAREA_OUT_STOCK_TYPE = "LOGOUT_SPECIAL_AREA";
	/**调整走货路径 从原货区出库*/
	public static final String TRANSPORT_PATH_CHANGE_OUT_STOCK_TYPE = "PATH_CHANGE";
	/**签收出库*/
	public static final String SIGN_OUT_STOCK_TYPE = "SIGN";
	/**运单作废出库*/
	public static final String WAYBILL_INVALID_OUT_STOCK_TYPE = "WAYBILL_INVALID";
	/**航空交接单查询界面 出库*/
	public static final String AIR_HANDOVER_BILL_OUT_STOCK_TYPE = "AIR_HANDOVERBILL";
	/**弃货出库 */
	public static final String ABANDON_GOODS_OUT_STOCK_TYPE = "ABANDON_GOODS";
	/**装车出库 */
	public static final String LOAD_GOODS_OUT_STOCK_TYPE = "LOAD_GOODS";
	/**违禁品移交驻地派送部 出库异常货区*/
	public static final String CONTRABAND_EXCEPTION_AREA_OUT_STOCK_TYPE = "CONTRABAND_EXCEPTION";
	/**整车出库*/
	public static final String WHOLE_VEHICLE_OUT_STOCK_TYPE = "WHOLE_VEHICLE";
	/**移除货件出库*/
	public static final String INVALID_GOODS_OUT_STOCK_TYPE = "INVALID_GOODS";
	/**原单恢复状态*/
	public static final String BACK_RETURNGOODS_STOCK_TYPE = "BACK_RETURNGOODS";
	/** 用户 操作确认*/
	/**已确认*/
	public static final String CONFIRM = "Y";
	/**未确认*/
	public static final String NOT_CONFIRM = "N";
	
	/** mapper 文件命名空间*/
	public static final String NAME_SPACE = "foss.stock.";
	
	/** 查询库存 导出Excel文件的sheet最大行数*/
	public static final int SHEET_SIZE = 5000;
	/** 查询库存 导出Excel文件的表头*/
	public static final String[] ROW_HEADS = {"运单号","流水号","货物名称","运输性质","重量","体积","包装","出发部门","到达部门","操作人","收货客户联系人","入库时间","开单时间","开单件数","行政区域","货区","到达时间","库存件数"};
	public static final String[] ROW_HEADS_PRIORITYGOODS = {"运单号","货区","开单件数","库存件数","货物名称","包装","运输性质","重量(公斤)","体积(方)","出发部门","下一部门","到达部门","入库时间","在库时长(小时)"};
	
	public static final String SHEET_NAME = "库存";
	
	/**库存状态：已预配*/
	public static final String PRE_HANDOVER_STATUS = "PRE_HANDOVER";
	
	/**
	 * 整车虚拟货区
	 */
	public static final String WHOLE_VEHICLE_GOODS_AREA_CODE = "WHOLE_GOODS_AREA";
	/**
	 * 整车虚拟下一部门
	 */
	public static final String WHOLE_VEHICLE_NEXT_ORG_CODE = "WHOLE_GOODS_NEXT_ORG";
	/**
	 * 空运虚拟下一部门
	 */
	public static final String AIR_FREIGHT_NEXT_ORG_CODE = "AIR_FREIGHT_GOODS_NEXT_ORG";
	/**
	 * 调用出库存储过程 返回错误编码 ：货物不在库存中
	 */
	public static final String STOCK_NOT_EXIST = "STOCK_NOT_EXIST";
	/**
	 * 调用出库存储过程 返回错误编码： 货物在特殊货区中
	 */
	public static final String SPECIAL_GOODSAREA = "SPECIAL_GOODSAREA";
	/**
	 * 散货虚拟货区
	 */
	public static final String BULK_GOODS_AREA_CODE = "BULK_GOODS_AREA";
	/**
	 * 返货开单，原单号入库虚拟货区
	 */
	public static final String OPERATED_RETURN_CODE = "OPERATED_RETURN_CODE";
	/**
	 * 营业部没有货区(虚拟货区)
	 */
	public static final String VIRTUAL_GOODS_AREA_CODE = "N/A";
	/**
	 * 出入库 修改运单库存 等待锁的最大时间
	 */
	public static final int IN_OUT_TIMEOUT = 20;
	
	//****************到付清查报表-库存状态******************
	/** 库存少货*/
	public static final String LOSE_STOCK_STATUS = "LOSE_STOCK";
	/** 正常签收*/
	public static final String NORMAL_SIGN_STOCK_STATUS = "NORMAL_SIGN_STOCK";
	/** 异常签收*/
	public static final String UNNORMAL_SIGN_STOCK_STATUS = "UNNORMAL_SIGN";
	/** 未入库*/
	public static final String NO_STOCK_STOCK_STATUS = "NO_STOCK";
	/** 库存中*/
	public static final String IN_STOCK_STOCK_STATUS = "IN_STOCK";
	/** 库存异常*/
	public static final String EXCEPTION_STOCK_STOCK_STATUS = "EXCEPTION_STOCK";
	
	//****************库位修改类型******************************
	/**库位修改时的入库类型(库位修改日志表里使用)*/
	public static final String POSITION_MOD = "POSITION_MOD";
	
	//***************货件在库状态或者是否已签收******************************
	/**货件已签收*/
	public static final String STOCK_SIGN = "SIGN";
	/**货件在库存*/
	public static final String STOCK_AT = "AT";
	
	//*****************crm的返货类型************************
	public static final String SEVEN_DAYS_RETURN="SEVEN_DAYS_RETURN";
	//三天外发返货类型 218427
	public static final String OUTBOUND_THREE_DAYS_RETURN ="OUTBOUND_THREE_DAYS_RETURN";
	
	/**
	 * 快递代理拉回
	* @fields PACKAGE_RETURN_IN_STOCK_TYPE
	* @author 218381-foss-lijie
	* @update 2015年4月7日 上午9:44:29
	* @version V1.0
	*/
	public static final String PACKAGE_RETURN_IN_STOCK_TYPE="PACKAGE_RETURN";
	/**
	 * 按照装车类型出货
	 */
	public static final String OUT_STOCK_TYPE_LOADING="OUT_STOCK_TYPE_LOADING";
	/**
	 * 按照交接单类型出货
	 */
	public static final String OUT_STOCK_TYPE_HANDOVER="OUT_STOCK_TYPE_HANDOVER";
	
	/**sonar_constants_number*/
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

}