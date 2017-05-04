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
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/define/TransportPathConstants.java
 * 
 *  FILE NAME     :TransportPathConstants.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.define
 * FILE    NAME: OrderVehicleConstants.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.define;

import com.deppon.foss.module.transfer.scheduling.api.shared.util.PricingConstants;

import java.util.HashMap;
import java.util.Map;


/**
 * 走货路径常量容器
 * @author huyue
 * @date 2012-10-16 下午4:08:23
 */
public final class TransportPathConstants {
	
	/**
	 * 所有为空string字段都设置为本字段
	 */
	public static final String  SET_NULL_STRING = "N/A";
	
	/** 走货路径状态  开单 */
	public static final String  TRANSPORTPATH_STATUS_BILLING = "BILLING";
	
	/** 走货路径状态  已交接  */
	public static final String  TRANSPORTPATH_STATUS_HANDOVER = "HANDOVER";
	
	/** 走货路径状态   出发 */
	public static final String  TRANSPORTPATH_STATUS_DEPART = "DEPART";
	
	/** 走货路径状态  到达 */
	public static final String  TRANSPORTPATH_STATUS_ARRIVE = "ARRIVE";
	
	/** 走货路径状态  库存中 */
	public static final String  TRANSPORTPATH_STATUS_INSTORE = "INSTORE";

	/** 走货路径状态  未入库 */
	public static final String  TRANSPORTPATH_STATUS_NOTINSTORE = "NOTSTORE";
	
	/** 走货路径状态  签收 */
	public static final String  TRANSPORTPATH_STATUS_SIGNIN  = "SIGNIN";
	
	/** 路径明细状态  未离开 */
	public static final String  PATHDETAIL_STATUS_NOTLEAVE = "NOTLEAVE";

	/** 路径明细状态  已交接*/
	public static final String  PATHDETAIL_STATUS_HANDOVER = "HANDOVER";
	
	/** 路径明细状态  离开  */
	public static final String  PATHDETAIL_STATUS_LEAVE = "LEAVE";
	
	/** 路径明细状态   抵达 */
	public static final String  PATHDETAIL_STATUS_ARRIVE = "ARRIVE";

	/** 路径明细状态  入库  */
	public static final String  PATHDETAIL_STATUS_INSTORE = "INSTORE";
	
	/** 路径明细状态  再离开  */
	public static final String  PATHDETAIL_STATUS_RELEAVE = "RELEAVE";

	/** 路径调整状态  合车调整*/
	public static final String CHANGEPATH_STATUS_JOINCAR = "JOINCAR";
	
	/** 路径调整状态  非合车调整*/
	public static final String CHANGEPATH_STATUS_MODIFY = "MODIFY";
	
	/** 路径调整后的当前部门所在路段号*/
	public static final String CHANGEPATH_ROUTE_NO = "1";
	
	/** 路径调整状态  已经分批*/
	public static final String PARTIALSTOWAGE = "Y";
	
	/** 路径调整状态  没有分批*/
	public static final String NOTPARTIALSTOWAGE = "N";
	
	/** 路径调整状态  已经调整出发时间*/
	public static final String CHANGETIME = "Y";
	
	/** 路径调整状态  没有调整出发时间*/
	public static final String NOTCHANGETIME = "N";
	
	/** 综合查询状态 可提货 */
	public static final String AVAILEDPICKUP = "Y";
	
	/** 综合查询状态  不可提货*/
	public static final String NOTAVAILEDPICKUP = "N";
	
	/** 综合查询状态  营业部已出发*/
	public static final String GENERAL_QUERY_DEPARTBILLING = "营业部已出发";
	
	/** 综合查询状态  已到达中转场*/
	public static final String GENERAL_QUERY_ARRIVETRANSFERCENTER = "已到达中转场";
	
	/** 综合查询状态  中转库存*/
	public static final String GENERAL_QUERY_TRANSFERCENTERINSTORE = "中转库存";
	
	/** 综合查询状态  中转运输*/
	public static final String GENERAL_QUERY_INTRANSIT = "中转运输";
	
	/** 综合查询状态  已到达营业部*/
	public static final String GENERAL_QUERY_ARRIVEDEST = "已到达营业部";
	
	/** 综合查询状态  营业部库存*/
	public static final String GENERAL_QUERY_DESTINSTORE = "营业部库存";
	
	/** 综合查询状态  营业部虚拟库存*/
	public static final String GENERAL_QUERY_VIRTUALSTORE = "虚拟库存";
	
	// add by liangfuxiang 2013-4-24上午10:49:36 begin: 增加库存状态编码
	
	/** 综合查询状态  营业部已出发*/
	public static final String GENERAL_QUERY_DEPARTBILLING_CODE = "SDLEAVE";
	
	/** 综合查询状态  已到达中转场*/
	public static final String GENERAL_QUERY_ARRIVETRANSFERCENTER_CODE = "TCARRIVED";
	
	/** 综合查询状态  中转库存*/
	public static final String GENERAL_QUERY_TRANSFERCENTERINSTORE_CODE = "TCINSTORE";
	
	/** 综合查询状态  中转运输*/
	public static final String GENERAL_QUERY_INTRANSIT_CODE = "TCTRANSPORT";
	
	/** 综合查询状态  已到达营业部 */
	public static final String GENERAL_QUERY_ARRIVEDEST_CODE = "SDARRIVED";
	
	/** 综合查询状态  营业部库存*/
	public static final String GENERAL_QUERY_DESTINSTORE_CODE = "SDINSTORE";
	
	// add by liangfuxiang 2013-4-24上午10:49:58 end;
	
	/** 路径调整状态  已经合车*/
	public static final int JOINCAR = 1;
	
	/** 路径调整状态  没有合车*/
	public static final int NOTJOINCAR = 0;
	
	/** 接口返回状态   没有数据  */
	public static final int  STATUS_NODATA = 0;
	
	/** 接口返回状态   成功  */
	public static final int  STATUS_RIGHT = 1;
	
	/** 接口返回状态   数据异常 */
	public static final int  STATUS_WRONG = 2;
	
	/** 接口返回状态   报错 */
	public static final int  STATUS_ERROR = 3;
	
	/** 接口返回状态   不能操作 */
	public static final int  STATUS_FORBID = 4;
	
	/** 接口返回状态   走货路径主表没有记录 */
	public static final int  STATUS_TRANPATHNULL = 5;
	
	/** 接口返回状态   走货路径明细表没有记录 */
	public static final int STATUS_TRANPATHDETAILNULL = 6;
	
	/** 接口返回状态   当前部门不在走货路径上*/
	public static final int  STATUS_CURRENTORGNOPATHDETAIL = 7;
	
	/** 查询货量预测 导出Excel文件的sheet最大行数*/
	public static final int SHEET_SIZE = 20000;
	/** 查询货量预测 导出Excel文件的表头*/
	public static final String[] FORECAST_ROW_HEADS = {"所属部门","区域","关联部门","总重量","总体积","总票数","卡航重量","卡航体积","卡航票数","城运重量","城运体积","城运票数","快递重量","快递体积","快递票数","在库重量","在库体积","在库票数","开单重量","开单体积","开单票数","在途重量","在途体积","在途票数","合车体积","预测发起时间","预测类型","预测的日期"};
	/** 查询货量预测开单明细 导出Excel文件的表头*/
	public static final String[] BILLING_ROW_HEADS = {"所属部门","所属区域","关联部门","所属营业区","所属营业部","开单重量","开单体积","开单票数","卡航重量","卡航体积","卡航票数","城运重量","城运体积","城运票数","快递重量","快递体积","快递票数","预测发起时间","预测类型"};
	/** 查询货量预测在途明细 导出Excel文件的表头*/
	public static final String[] INTRANSIT_ROW_HEADS = {"所属部门","所属区域","关联部门","在途车牌号","在途重量","在途体积","在途票数","卡航重量","卡航体积","卡航票数","城运重量","城运体积","城运票数","快递重量","快递体积","快递票数","预测发起时间","预测类型"};
	/** 查询统计货量查询 导出Excel文件的表头*/
	public static final String[] STATISTICALINQUIRIES_ROW_HEADS = {"运单号","运输性质","货物名称","货物状态","货物体积(方)","货物重量(公斤)","货物件数","出发部门","到达部门","开单时间","到达时间"};
	/** 查询展会货货量查询 导出Excel文件的表头*/
	public static final String[] FORECASTEXHIBIT_ROW_HEADS = {"运单号","重量(公斤)","体积(方)","运输性质","货物名称","包装","库存件数","开单件数","开单时间","入库时间","预计到达时间","提货方式","货物状态","到达部门"};
	/** 查询全国展会货货量查询 导出Excel文件的表头*/
	public static final String[] FORECASTEXHIBITFORWORLD_ROW_HEADS = {"外场","总票数","总重量(公斤)","总体积(方)","开单未交接重量","开单未交接体积","开单未交接票数","库存余货重量","库存余货体积","库存余货票数","在途重量","在途体积","在途票数","库存占比"};
	
	public static final String EXHIBITFORWORLD_SHEET_NAME = "全国展会货货量统计";
	
	public static final String EXHIBIT_SHEET_NAME = "展会货货量统计";
	
	public static final String FORECAST_SHEET_NAME = "货量预测";
	
	public static final String BILLING_SHEET_NAME = "开单预测";

	public static final String INTRANSIT_SHEET_NAME = "在途预测";
	
	/** 国际化Exception*/
	public static final String TRANSPORTPATH_ADJUST_CANTFINDTRANSFERCENTER = "foss.scheduling.transportPath.cantFindTransferCenter";
	
	public static final String TRANSPORTPATH_ADJUST_CANTFINDDEPARTURESTANDARD = "foss.scheduling.transportPath.cantFindDepartureStandard";
	
	public static final String TRANSPORTPATH_ADJUST_NOTJOINCARINTERFACEERROR = "foss.scheduling.transportPath.notJoinCarInterfaceError";
	
	public static final String TRANSPORTPATH_ADJUST_WAYBILLNO = "foss.scheduling.transportPath.waybillNo";
	
	public static final String TRANSPORTPATH_ADJUST_SERIALNO = "foss.scheduling.transportPath.serialNo";
	
	public static final String TRANSPORTPATH_ADJUST_STOCKINTERFACEERROR = "foss.scheduling.transportPath.stockInterfaceError";
	
	public static final String TRANSPORTPATH_RECREATE_CANTFINDSERIALNOBYWAYBILLNO = "foss.scheduling.transportPath.cantFindSerialNoByWaybillNo";
	
	public static final String TRANSPORTPATH_CALCULATE_USEINTERFACE = "foss.scheduling.transportPath.useInterface";
	
	public static final String TRANSPORTPATH_CALCULATE_CANTFINDTRANSPORTPATH = "foss.scheduling.transportPath.cantFindTransportPath";
	
	public static final String TRANSPORTPATH_CALCULATE_CANTFINDSOURCEORG = "foss.scheduling.transportPath.cantFindSourceOrg";
	
	public static final String TRANSPORTPATH_CALCULATE_CANTFINDTARGETORG = "foss.scheduling.transportPath.cantFindTargetOrg";
	
	public static final String TRANSPORTPATH_CALCULATE_CANTFINDLEAVETIME = "foss.scheduling.transportPath.cantFindLeaveTime";
	
	public static final String TRANSPORTPATH_CALCULATE_CANTFINDARRIVETIME = "foss.scheduling.transportPath.cantFindArriveTime";
	
	public static final String TRANSPORTPATH_CALCULATE_CANTFINDDATA = "foss.scheduling.transportPath.cantFindData";
	
	public static final String TRANSPORTPATH_CALCULATE_CANTDELETE = "foss.scheduling.transportPath.cantDelete";
	
	public static final String TRANSPORTPATH_CALCULATE_SERIALNOERROR = "foss.scheduling.transportPath.serialNoError";
	
	public static final String TRANSPORTPATH_CALCULATE_DETAILMORETHANONE = "foss.scheduling.transportPath.detailMoreThanOne";
	
	public static final String TRANSPORTPATH_CALCULATE_DETAILMORETHANONE_CURR_GLOB = "foss.scheduling.transportPath.detailMoreThanOneCurrGlob";
	
	public static final String TRANSPORTPATH_CALCULATE_DETAILMORETHANONE_GLOB = "foss.scheduling.transportPath.detailMoreThanOneGlob";
	
	public static final String TRANSPORTPATH_CALCULATE_DETAILMORETHANONE_NEW = "foss.scheduling.transportPath.detailMoreThanOneNew";
	
	public static final String TRANSPORTPATH_CALCULATE_CANTFINDDETAILDATA = "foss.scheduling.transportPath.cantFindDetailData";
	
	public static final String TRANSPORTPATH_CALCULATE_CANTFINDDETAILDATA_ONLY_SER = "foss.scheduling.transportPath.cantFindDetailDataOnlySer";
	
	public static final String TRANSPORTPATH_CALCULATE_CANTFINDDETAILDATA_DES_GLOB = "foss.scheduling.transportPath.cantFindDetailDataDesGlob";
	
	public static final String TRANSPORTPATH_CALCULATE_CANTFINDDETAILDATA_GLOB = "foss.scheduling.transportPath.cantFindDetailDataGlob";
	
	public static final String TRANSPORTPATH_CALCULATE_CANTFINDDETAILDATA_SER_GLOB = "foss.scheduling.transportPath.cantFindDetailDataSerGlob";
	
	public static final String TRANSPORTPATH_CALCULATE_NEXTDETAILMORETHANONE = "foss.scheduling.transportPath.nextDetailMoreThanOne";
	
	public static final String TRANSPORTPATH_CALCULATE_NEXTDETAILMORETHANONE_NEW = "得到的下一段明细对应数据大于一条";
	
	public static final String TRANSPORTPATH_CALCULATE_NEXTDETAILMORETHANONE_NEW_GLOB = "foss.scheduling.transportPath.nextDetailMoreThanOneGlob";
	
	public static final String TRANSPORTPATH_CALCULATE_BEFOREDETAILMORETHANONE_NEW_GLOB = "foss.scheduling.transportPath.beforeDetailMoreThanOneGlob";
	
	public static final String TRANSPORTPATH_CALCULATE_DETAILDATAERROR = "foss.scheduling.transportPath.detailDataError";
	
	public static final String TRANSPORTPATH_CALCULATE_CANTFINDBEFOREDETAILDATA = "foss.scheduling.transportPath.cantFindBeforeDetailData";
	
	public static final String TRANSPORTPATH_CALCULATE_CANTFINDCHANGEDETAILDATA = "foss.scheduling.transportPath.cantFindChangeDetailData";
	
	public static final String TRANSPORTPATH_CALCULATE_CANTFINDCHANGEDETAILDATA_GLOB = "foss.scheduling.transportPath.modifyChangeDetailData";
	
	public static final String TRANSPORTPATH_WAYBILLNO_ISEMPTY = "foss.scheduling.transportPath.waybillNoEmpty";
	
	public static final String TRANSPORTPATH_GOODSNOS_ISEMPTY = "foss.scheduling.transportPath.goodsNoEmpty";
	
	public static final String TRANSPORTPATH_CURRENTORGCODE_ISEMPTY = "foss.scheduling.transportPath.currentOrgCodeEmpty";
	
	public static final String TRANSPORTPATH_DESTORGCODE_ISEMPTY = "foss.scheduling.transportPath.destOrgCodeEmpty";
	
	public static final String TRANSPORTPATH_TRANSPORTMODEL_ISEMPTY = "foss.scheduling.transportPath.transportModelEmpty";
	
	/** 路径明细状态 为空 */
	public static final String TRANSPORTPATH_STATUS_BILLING_NULL = "";

	/** 走货路径状态 开单 */
	public static final String TRANSPORTPATH_STATUS_BILLING_CHINESE = "开单";

	/** 走货路径状态 已交接 */
	public static final String TRANSPORTPATH_STATUS_HANDOVER_CHINESE = "已交接";

	/** 走货路径状态 出发 */
	public static final String TRANSPORTPATH_STATUS_DEPART_CHINESE = "出发";

	/** 走货路径状态 到达 */
	public static final String TRANSPORTPATH_STATUS_ARRIVE_CHINESE = "到达";

	/** 走货路径状态 库存中 */
	public static final String TRANSPORTPATH_STATUS_INSTORE_CHINESE = "库存中";

	/** 走货路径状态 未入库 */
	public static final String TRANSPORTPATH_STATUS_NOTINSTORE_CHINESE = "未入库";

	/** 走货路径状态 签收 */
	public static final String TRANSPORTPATH_STATUS_SIGNIN_CHINESE = "签收";

	/** 走货路径状态 签收 */
	public static final String TRANSPORTPATH_STATUS_UNEFFECTIVE = "走货路径状态无效!系统中未定义此状态:";
	/** 走货路径状态 签收 */
	public static final String TRANSPORTPATH_STATUS_UNEFFECTIVE_GLOB = "foss.scheduling.transportPath.transPathActionInvalid";

	/** 走货路径为空 无 */
	public static final String TRANSPORTPATH_NULL_CHINESE = "无";

	/** 空字符串 */
	public static final String BLANK_STRING_CONSTANT = "";

	/** 三级产品-精准卡航 */
	public static final String PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT_CHINESE = "精准卡航";// C30001

	/** 三级产品-精准城运 */
	public static final String PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT_CHINESE = "精准城运";// C30002

	/** 三级产品-精准汽运(长途) */
	public static final String PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT_CHINESE = "精准汽运(长途)";// C30003

	/** 三级产品-精准汽运(短途) */
	public static final String PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT_CHINESE = "精准汽运(短途)";// C30004

	/** 三级产品-汽运偏线 */
	public static final String PRICING_PRODUCT_PARTIAL_LINE_CHINESE = "汽运偏线";// C30007

	/** 三级产品-整车 */
	public static final String PRICING_PRODUCT_FULL_VEHICLE_CHINESE = "整车";// C30005

	/** 三级产品-精准空运 */
	public static final String PRICING_PRODUCT_AIR_FREIGHT_CHINESE = "精准空运";// C30006

	/** 三级产品-空 */
	public static final String PRICING_PRODUCT_AIR_FREIGHT_NULL = "";

	/** 三级产品-运输类型无效 */
	public static final String PRICING_PRODUCT_UNEFFECTIVE = "运输类型无效!运输类型为:";
	/** 三级产品-运输类型无效 */
	public static final String PRICING_PRODUCT_UNEFFECTIVE_GLOB = "foss.scheduling.transportPath.transModelInvalid";

	/** 是否营业部：是 **/
	public static final String IS_SALESDEPARTMENT_YES = "Y";

	/** 根据部门编码未查询到部门信息异常 **/
	public static final String QUERY_DEPARTMENT_BY_CODE_WRONG = "根据部门编码未查询到部门信息!部门编码:";
	/** 根据部门编码未查询到部门信息异常 **/
	public static final String QUERY_DEPARTMENT_BY_CODE_WRONG_GLOB = "foss.scheduling.transportPath.queryOrgEntityNull";

	/** 出发部门为空 **/
	public static final String ORGINAL_ORG_CODE_NULL = "出发部门为空!";
	/** 出发部门为空 **/
	public static final String ORGINAL_ORG_CODE_NULL_GLOB = "foss.scheduling.transportPath.startOrgCodeNull";
	/**无运单信息**/
	public static final String WAYBILL_LIST_IS_NULL="运单号信息为空!";
	
	/** 当前有其他用户操作此运单，请稍后再试 **/
	public static final String WAYBILL_IS_LOCKED = "当前有其他用户操作此运单，请稍后再试";
	
	/** 签收时间间隔 **/
	public static final String SIGNINTIMEINTERVAL = "signInTimeInterval";
	/** 未签收时间间隔 **/
	public static final String UNSIGNINTIMEINTERVAL = "unsignInTimeInterval";
	/** 无效数据的时间间隔 **/
	public static final String INVALIDTIMEINTERVAL = "invalidTimeInterval";
	
	/** 从走货路径迁移表中恢复走货路径数据 **/
	public static final String RECOVER_TRANSPORT_PATH_FROM_MIGRATION = "从走货路径迁移表中恢复走货路径数据,运单号为：";
	/** 从走货路径迁移表中恢复走货路径数据异常 **/
	public static final String RECOVER_TRANSPORT_PATH_FROM_MIGRATION_EXCEPTION = "从走货路径迁移表中恢复走货路径数据异常!运单号为：";
	/** 从走货路径迁移表查询多条走货路径异常**/
	public static final String MULTY_TRANSPORT_PATH_FROM_MIGRATION_EXCEPTION = "从走货路径迁移表中查询到多条走货路径异常!运单号为：";
	/** 从走货路径迁移表查询多条走货路径异常**/
	public static final String MULTY_TRANSPORT_PATH_FROM_MIGRATION_EXCEPTION_GLOB = "foss.scheduling.transportPath.moreThanOneMigration";
	/** 从走货路径迁移表查询多条走货路径异常**/
	public static final String QUERY_TRANSPORT_PATH_FROM_MIGRATION_NORESULT = "从走货路径迁移表中未查询到走货路径信息!运单号为：";
	/** 根据当前组织查询顶级组织失败 **/
	public static final String QUERY_TOP_ORG_BY_CURRENT_ORG_FAIL = "根据当前组织查询所属的上级部门失败(包括营业部、派送部、外场、总调)!当前部门编码为:";
	/** 根据当前组织查询顶级组织失败 **/
	public static final String QUERY_TOP_ORG_BY_CURRENT_ORG_FAIL_GLOB = "foss.scheduling.transportPath.currentOrgCode";
	/** 查询调整走货路径的条件为空 **/
	public static final String QUERY_LEVEL1_ADJUSTENTITY_NULL = "查询调整走货路径的条件为空!";
	/** 查询调整走货路径的条件为空 **/
	public static final String QUERY_LEVEL1_ADJUSTENTITY_GLOB = "foss.scheduling.transportPath.adjustEntityNull";
	/** 查询调整走货路径当前组织编码为空 **/
	public static final String QUERY_LEVEL1_ORGCODE_NULL = "查询调整走货路径当前组织编码为空!";
	/**查询走货路径的条件为空**/
	public static final String QUERY_TRANSPORTPATH_NULL="查询走货路径的条件为空!";
	/** 查询走货路径,当前部门编码为空 **/
	public static final String QUERY_TRANSPORTPATH_CURRENTORGCODE_NULL = "查询走货路径,当前部门编码为空!";
	/** 录入生成走货路径的对象为空 **/
	public static final String CREATE_TRANSPORTPATH_PARAMS_NULL = "录入生成走货路径的对象为空!";
	/** 输入的运单号为空 **/
	public static final String WAYBILL_NO_IS_NULL = "输入的运单号为空!";
	/** 输入的运单号为空 **/
	public static final String WAYBILL_NO_IS_NULL_GLOB = "foss.scheduling.transportPath.inputWaybillNoNull";
	/** 输入的当前部门为空 **/
	public static final String CURRENT_ORG_CODE_IS_NULL = "输入的当前部门为空!";
	/** 输入的当前部门为空 **/
	public static final String CURRENT_ORG_CODE_IS_NULL_GLOB = "foss.scheduling.transportPath.currentOrgCodeIsNull";
	/** 输入的流水号为空 **/
	public static final String SERIALNO_IS_NULL = "走货路径为分批配载，而输入的流水号为空!";
	/** 输入的流水号为空 **/
	public static final String SERIALNO_IS_NULL_GLOB = "foss.scheduling.transportPath.parSerIsNull";
	/** 根据运单号查询运单信息为空 **/
	public static final String WAYBILL_INFO_NULL = "根据运单号查询运单信息为空!运单号为:";
	/** 根据运单号查询运单信息为空 **/
	public static final String WAYBILL_INFO_NULL_GLOB = "foss.scheduling.transportPath.queryWayBillIsNull";
	/** 运单对应的提货网点信息为空 **/
	public static final String WAYBILL_PRKCUPORGCODE_NULL = "运单对应的提货网点部门编码为空!运单号为:";
	/** 运单对应的提货网点信息为空 **/
	public static final String WAYBILL_PRKCUPORGCODE_NULL_GLOB = "foss.scheduling.transportPath.pickUpOrgCodeIsNull";
	/** 运单对应的运输类型为空 **/
	public static final String WAYBILL_TRANPORTTYPE_NULL = "运单对应的运输类型为空!运单号为:";
	/** 运单对应的运输类型为空 **/
	public static final String WAYBILL_TRANPORTTYPE_NULL_GLOB = "foss.scheduling.transportPath.wayBillTranspModelIsNull2";
	/**运单对应的开单部门编码为空**/
	public static final String WAYBILL_BILLINGCODE_NULL = "运单对应的开单部门编码为空!运单号为:";
	/**运单对应的开单部门编码为空**/
	public static final String WAYBILL_BILLINGCODE_NULL_GLOB = "foss.scheduling.transportPath.wayBillCreateOrgCodeIsNull";
	/** 拼接路径后的上一部门所在路段号*/
	public static final String SPLICEPATH_LAST_ROUTE_NO = "1";
	/** 拼接路径后的当前部门所在路段号*/
	public static final String SPLICEPATH_CURRENT_ROUTE_NO = "2";
	/** 是否修改过计划出发时间:NO */
	public static final String SPLICEPATH_IFCHANGETIME_NO = "N";
	/** 同一张运单有多条走货路径!运单号为: */
	public static final String SINGLE_WAYBILL_MULTI_TRANPORTPATH = "同一张运单有多条走货路径!运单号为:";
	/**非合车调整走货路径是分批配载,但流水号为空**/
	public static final String NOTJOINCARMODIFY_SERIALNO_NULL="非合车调整走货路径是分批配载,但输入的流水号为空!运单号为:";
	/**非合车调整走货路径是分批配载,但流水号为空**/
	public static final String NOTJOINCARMODIFY_SERIALNO_NULL_GLOB="foss.scheduling.transportPath.noParSerIsNull";
	/**非合车调整走货路径,但运单号为空**/
	public static final String NOTJOINCARMODIFY_WAYBILLNO_NULL = "非合车调整走货路径输入的运单号为空!";
	/**非合车调整走货路径,但运单号为空**/
	public static final String NOTJOINCARMODIFY_WAYBILLNO_NULL_GLOB = "foss.scheduling.transportPath.notJoinCarWaybillNoNull";
	/** 非合车调整走货路径,库区为空 **/
	public static final String NOTJOINCARMODIFY_ORIGGOODSAREACODE_NULL = "非合车调整走货路径库区为空!运单号为:";
	/** 非合车调整走货路径,库区为空 **/
	public static final String NOTJOINCARMODIFY_ORIGGOODSAREACODE_NULL_GLOB = "foss.scheduling.transportPath.notJoinCarAreaNoNull";
	/** 非合车调整走货路径,修改时间为空 **/
	public static final String NOTJOINCARMODIFY_MODIFYTIME_NULL = "非合车调整走货路径,修改时间为空!";
	/** 非合车调整走货路径,修改时间为空 **/
	public static final String NOTJOINCARMODIFY_MODIFYTIME_NULL_GLOB = "foss.scheduling.transportPath.notJoinCarModifyTimeNull";
	/** 非合车调整走货路径,当前部门为空 **/
	public static final String NOTJOINCARMODIFY_NOWORGCODE_NULL = "非合车调整走货路径,当前部门为空!";
	/** 非合车调整走货路径,当前部门为空 **/
	public static final String NOTJOINCARMODIFY_NOWORGCODE_NULL_GLOB = "foss.scheduling.transportPath.notJoinCarCurrentOrgCodeNull";
	/** 走货路径已经存在 **/
	public static final String TRANSPORT_PATH_EXIST = "该运单对应的走货路径已经存在!运单号为:";
	/** 走货路径已经存在 **/
	public static final String TRANSPORT_PATH_EXIST_GLOB = "foss.scheduling.transportPath.transportPathExist";
	/**从综合查询走货路线异常**/
	public static final String TRANSPORTPATH_CALCULATE_CANTFINDTRANSPORTPATH_FREIGHTROUTELINEDTO = "从基础数据未查询到走货路线!";
	/** 字符常量1 **/
	public static final String CONSTONT_NUMBER_ONE = "1";
	/** 无走货路径第一段信息 **/
	public static final String TRANSPORTPATH_DETAIL_NO_FIRST_ROUTE = "无走货路径第一段信息！";
	/** 无走货路径第一段信息 **/
	public static final String TRANSPORTPATH_DETAIL_NO_FIRST_ROUTE_GLOB = "foss.scheduling.transportPath.noFirstRoute";
	/** 从基础数据未查询到走货路线,故自动生成走货路径主表信息! **/
	public static final String TRANSPORT_PATH_BRANDNEW = "从基础数据未查询到走货路线,故自动生成走货路径主表信息!";
	/** 从基础数据未查询到走货路线,故自动生成走货路径主表信息! **/
	public static final String TRANSPORT_PATH_BRANDNEW_GLOB = "foss.scheduling.transportPath.createPathFail";
	/**到达时运单号为空!**/
	public static final String ARRIVE_WAYBILLNO_NULL = "到达:修改走货路径状态,输入的参数<运单号>为空!";
	/**到达时运单号为空!**/
	public static final String ARRIVE_WAYBILLNO_NULL_GLOB = "foss.scheduling.transportPath.arriveWaybillNoNull";
	/**到达:修改走货路径,到达部门编码为空**/
	public static final String ARRIVE_ARRIVEORGCODE_NULL = "到达:修改走货路径,输入的参数<达部门编码>为空!";
	/**到达:修改走货路径,到达部门编码为空**/
	public static final String ARRIVE_ARRIVEORGCODE_NULL_GLOB = "foss.scheduling.transportPath.arriveOrgcodeNull";
	/**到达:修改走货路径,实际到达时间为空!**/
	public static final String ARRIVE_ARRIVETIME_NULL = "到达:修改走货路径,输入的参数<实际到达时间>为空!";
	/**到达:修改走货路径,实际到达时间为空!**/
	public static final String ARRIVE_ARRIVETIME_NULL_GLOB = "foss.scheduling.transportPath.arriveTimeNull";
	/**到达:修改走货路径,走货路径状态为空!**/
	public static final String ARRIVE_ACTION_NULL = "到达:修改走货路径,输入的参数<走货路径状态>为空!";
	/**到达:修改走货路径,走货路径状态为空!**/
	public static final String ARRIVE_ACTION_NULL_GLOB = "foss.scheduling.transportPath.arriveActionNull";
	/**到达:修改走货路径,车牌号为空!**/
	public static final String ARRIVE_VEHICLENO_NULL = "到达:修改走货路径,输入的参数<车牌号>为空!";
	/**到达:修改走货路径,车牌号为空!**/
	public static final String ARRIVE_VEHICLENO_NULL_GLOB = "foss.scheduling.transportPath.arriveVehiclenoNull";
	/** 输入的流水号为空 **/
	public static final String ARRIVE_SERIALNO_NULL = "到达:修改走货路径,走货路径为分批配载，输入的参数<流水号>为空!";
	/** 输入的流水号为空 **/
	public static final String ARRIVE_SERIALNO_NULL_GLOB = "foss.scheduling.transportPath.arriveSerialNoNull";
	/**到达部门为空!**/
	public static final String ARRIVEORGCODE_IS_NULL = "到达部门为空!";
	/**到达部门为空!**/
	public static final String ARRIVEORGCODE_IS_NULL_GLOB = "foss.scheduling.transportPath.arriveOrgCodeIsNull";
	/** 运单的开单组织为空!运单号为: **/
	public static final String WAYBILL_CREATEORGCODE_NULL = "运单的开单组织为空!运单号为:";
	/** 运单的开单组织为空!运单号为: **/
	public static final String WAYBILL_CREATEORGCODE_NULL_GLOB = "foss.scheduling.transportPath.waybillCreagteOrgCodeIsNull";
	/** 运单的提货网点为空!运单号为: **/
	public static final String WAYBILL_PICKUPORGCODE_NULL = "运单的提货网点为空!运单号为:";
	/** 运单的提货网点为空!运单号为: **/
	public static final String WAYBILL_PICKUPORGCODE_NULL_GLOB = "foss.scheduling.transportPath.waybillPickUpCodeIsNull";
	/** 运单的运输性质为空!运单号为: **/
	public static final String WAYBILL_PRODUCTCODE_NULL = "运单的运输性质为空!运单号为:";
	/** 运单的运输性质为空!运单号为: **/
	public static final String WAYBILL_PRODUCTCODE_NULL_GLOB = "foss.scheduling.transportPath.waybillTranspModelIsNull";
	/**出发部门(:)和到达部门(:)之间无走货路径**/
	public static final String FREIGHTROUTE_IS_NULL = "出发部门(:)和到达部门(:)之间无走货路径!";
	/**出发部门(:)和到达部门(:)之间无走货路径**/
	public static final String FREIGHTROUTE_IS_NULL_GLOB = "foss.scheduling.transportPath.betweenOrgCodesNoWay";
	/** 输入的目标部门编码为空! **/
	public static final String INSTORAGE_INPUT_ORGCODE_NULL = "输入的目标部门编码为空!";
	/** 输入的目标部门编码为空! **/
	public static final String INSTORAGE_INPUT_ORGCODE_NULL_GLOB = "foss.scheduling.transportPath.inputOrgCodeNull";
	/** 走货路径不唯一 **/
	public static final String PATHDETAIL_MORE_THAN_ONE = "走货路径不唯一!单号为(:),到达部门为(:)";
	/** 走货路径不唯一 **/
	public static final String PATHDETAIL_MORE_THAN_ONE_GLOB = "foss.scheduling.transportPath.pathdetailMoreThanOne";
	/** 没有查询到走货路径 **/
	public static final String PATHDETAIL_IS_NULL = "没有查询到走货路径!单号为(:),出发部门为(:)";
	/** 没有查询到走货路径 **/
	public static final String PATHDETAIL_IS_NULL_DES_GLOB = "foss.scheduling.transportPath.pathdetailIsNullDesGlob";
	/** 没有查询到走货路径 **/
	public static final String PATHDETAIL_IS_NULL_CURR_GLOB = "foss.scheduling.transportPath.pathdetailIsNullCurrGlob";
	/** 没有查询到走货路径 **/
	public static final String PATHDETAIL_IS_NULL_GLOB = "foss.scheduling.transportPath.pathdetailIsNull";
	/** 入库时,输入参数:走货路径状态为空 **/
	public static final String PATH_DETAIL_ACTION_NULL = "输入参数:走货路径状态为空!";
	/** 入库时,输入参数:走货路径状态为空 **/
	public static final String PATH_DETAIL_ACTION_NULL_GLOB = "foss.scheduling.transportPath.inputActionNull";
	/** 下一部门编码为空! **/
	public static final String DEST_ORG_CODE_IS_NULL = "下一部门编码为空!";
	/** 下一部门编码为空! **/
	public static final String DEST_ORG_CODE_IS_NULL_GLOB = "foss.scheduling.transportPath.destOrgCodeIsNull";
	/** 空字符串(一个空格) **/
	public static final String BLANK_SPACE_STRING = " ";
	/** 当前部门编码 **/
	public final static String CURRENT_ORGCODE = "当前部门编码:";
	/** 目的部门编码 **/
	public final static String DEST_ORGCODE = "目的部门编码:";
	/** 运单号 **/
	public static final String WAYBILL_NO = "运单号为:";
	/** 流水号 **/
	public static final String SERIAL_NO = "流水号为:";
	/** 流水号 **/
	public static final String TRANS_MODEL = "运输类型为:";
	/** 修改货物走货路径,参数实体为空! **/
	public static final String ADJUSTENTITY_IS_NULL = "修改货物走货路径,参数实体为空!";
	/** 修改货物走货路径,参数实体为空! **/
	public static final String ADJUSTENTITY_IS_NULL_GLOB = "foss.scheduling.transportPath.adjustentityIsNull";
	/** 合车调整走货路径,部门编码为空! **/
	public static final String ADJUSTENTITY_ORGCODE_IS_NULL = "合车调整走货路径,部门编码为空!";
	/** 合车调整走货路径,部门编码为空! **/
	public static final String ADJUSTENTITY_ORGCODE_IS_NULL_GLOB = "foss.scheduling.transportPath.adjustentityOrgCodeIsNull";
	/** 合车调整走货路径,库区编码为空! **/
	public static final String ADJUSTENTITY_GETGOODSAREACODE_IS_NULL = "合车调整走货路径,库区编码为空!";
	/** 合车调整走货路径,库区编码为空! **/
	public static final String ADJUSTENTITY_GETGOODSAREACODE_IS_NULL_GLOB = "foss.scheduling.transportPath.adjustentityAreaCodeIsNull";
	/**从综合未查询到库区信息!***/
	public static final String GOODSAREAENTITY_IS_NULL = "从综合未查询到库区信息!";
	/**从综合未查询到库区信息!***/
	public static final String GOODSAREAENTITY_IS_NULL_GLOB = "foss.scheduling.transportPath.goodsAreaEntityIsnull";
	/**库区部门编码:**/
	public static final String GOODS_AREA_CODE = "库区部门编码:";
	/** 删除走货路径时,输入的参数：走货路径状态为空! **/
	public static final String TRANSPORTPATH_DELETE_ACTION_NULL = "删除走货路径时,输入的参数：走货路径状态为空!";
	/** 删除走货路径时,输入的参数：走货路径状态为空! **/
	public static final String TRANSPORTPATH_DELETE_ACTION_NULL_GLOB = "foss.scheduling.transportPath.transportpathDeleteActionNull";
	/**走货路径状态为空!**/
	public static final String TRANSPORTPATH_ACTION_IS_NULL = "走货路径状态为空!";
	/**走货路径状态为空!**/
	public static final String TRANSPORTPATH_ACTION_IS_NULL_GLOB = "foss.scheduling.transportPath.transportpathActionIsNull";
	/**要删除的走货路径状态与输入走货路径状态不一致!**/
	public static final String TRANSPORTPATH_ACTION_NOT_EQUAL = "要删除的走货路径状态与输入的走货路径状态不一致!";
	/**要删除的走货路径状态与输入走货路径状态不一致!**/
	public static final String TRANSPORTPATH_ACTION_NOT_EQUAL_GLOB = "foss.scheduling.transportPath.transportpathActionNotEqual";
	/**走货路径状态为:**/
	public static final String TRANSPORTPATH_ACTION="走货路径状态为:";
	/**走货路径状态为:**/
	public static final String TRANSPORTPATH_ACTION_INPUT="输入的走货路径状态为:";
	/**合车时没有查询到相应走货路径数据:**/
	public static final String TRANSPORTPATH_CALCULATE_DETAILMORETHANONE_JOINCAR = "合车时分批配载没有查询到相应走货路径数据!";
	/**合车时没有查询到相应走货路径数据:**/
	public static final String TRANSPORTPATH_CALCULATE_DETAILMORETHANONE_JOINCAR_GLOB = "foss.scheduling.transportPath.transportpathCalculateDetailMoreThanOneJoincar";
	/**合车时查询到多条走货路径明细数据**/
	public static final String TRANSPORTPATH_MULT_DETAILMORETHANONE_JOINCAR = "合车时分批配载查询到多条走货路径明细数据!";
	/**合车时查询到多条走货路径明细数据**/
	public static final String TRANSPORTPATH_MULT_DETAILMORETHANONE_JOINCAR_GLOB = "foss.scheduling.transportPath.transportpathMultDetailmoreThanoneJoincar";
	/**合车时查询到多条走货路径明细数据**/
	public static final String TRANSPORTPATH_NO_MULT_DETAILMORETHANONE_JOINCAR = "合车时查询到多条走货路径明细数据!";
	/**合车时查询到多条走货路径明细数据**/
	public static final String TRANSPORTPATH_NO_MULT_DETAILMORETHANONE_JOINCAR_GLOB = "foss.scheduling.transportPath.transportpathMultDetailMoreThanOneJoincar";
	/**合车时没有查询到相应走货路径数据**/
	public static final String TRANSPORTPATH_NO_CALCULATE_DETAILMORETHANONE_JOINCAR = "合车时没有查询到相应走货路径数据!";
	/**合车时没有查询到相应走货路径数据**/
	public static final String TRANSPORTPATH_NO_CALCULATE_DETAILMORETHANONE_JOINCAR_GLOB = "foss.scheduling.transportPath.transportpathNoCalculateDetailMoreThanOneJoincar";
	/**得到的上一段走货路径明细大于1**/
	public static final String TRANSPORTPATH_CALCULATE_BEFOREDETAILMORETHANONE = "得到的上一段走货路径明细有多条!";
	/** 走货路径不唯一 **/
	public static final String PATHDETAIL_ORG_MORE_THAN_ONE = "走货路径不唯一!单号为(:),出发部门为(:)";
	/** 走货路径不唯一 **/
	public static final String PATHDETAIL_ORG_MORE_THAN_ONE_GLOB = "foss.scheduling.transportPath.pathdetailOrgMoreThanOneGlob";
	/** 没有查询到走货路径 **/
	public static final String PATHDETAIL_ORG_IS_NULL = "没有查询到走货路径!单号为(:),出发部门为(:)";
	/** 没有查询到走货路径 **/
	public static final String PATHDETAIL_ORG_IS_NULL_GLOB = "foss.scheduling.transportPath.pathdetailOrgIsNullGlob";
	/** 货区编码 **/
	public static final String ORIGGOODSAREACODE = "货区编码为:";
	/** 路径调整 **/
	public static final String ADJUST_STATUS = "路径调整:";
	/** goodsAreaService.queryGoodsAreaByCode 通过货区获取线路信息返回值为空! **/
	public static final String QUERYGOODSAREABYCODE_NULL = "通过货区获取线路信息返回值为空!部门:";
	/** goodsAreaService.queryGoodsAreaByCode 通过货区获取线路信息返回值为空! **/
	public static final String QUERYGOODSAREABYCODE_NULL_GLOB = "foss.scheduling.transportPath.goodsAreaCodenull";
	/**根据当前部门获取外场编码为空**/
	public static final String OrgAdministrativeInfoEntity_OUT_CODE_NULL = "根据部门编码获取外场信息,外场组织编码为空!当前部门组织编码为:";
	/**根据当前部门获取外场编码为空**/
	public static final String OrgAdministrativeInfoEntity_OUT_CODE_NULL_GLOB = "foss.scheduling.transportPath.orgadministrativeinfoentityOutCodeNull";
	/**到达部门**/
	public static final String DESTORGNAME = "到达部门:";
	/**出发部门**/
	public static final String ORIGORGNAME = "出发部门:";
	/**出发部门**/
	public static final String TRANSPORTMODELNAME = "运输性质:";
	/** 左括号 **/
	public static final String LEFTBRACKET = "(";
	/** 右括号 **/
	public static final String RIGHTBRACKET = ")";
	/**逗号**/
	public static final String COMMA=",";
	
	//定义运输性质编码-名称MAP
	public static Map<String,String> transportModelMap=null;
	
	public static Map<String,String> getTransportModelMap(){
		if(null==transportModelMap){
			transportModelMap=new HashMap<String,String>();
			transportModelMap.put(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT, "精准卡航");
			transportModelMap.put(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT, "精准城运");
			transportModelMap.put(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT, "精准汽运(长途)");
			transportModelMap.put(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT, "精准汽运(短途)");
			transportModelMap.put(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE, "汽运偏线");
			transportModelMap.put(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE, "整车");
			transportModelMap.put(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT, "精准空运");
		}
		return transportModelMap;
	}
	
	/** 常量0 **/
	public static final String CONSTANT_ZERO = "0";
	/** 查询卸车时间：小时数为空 **/
	public static final String LOADANDUNLOADEFFICIENCYVEHICLE_UNLOADHOUR_NULL = "查询卸车时间，小时为空!";
	/** 车牌号为 **/
	public static final String VECHLE_NO = "车牌号为:";
	/** 外场编码为 **/
	public static final String OUTORGNO = "外场编码为:";
	/** 查询卸车时间：分钟数为空 **/
	public static final String LOADANDUNLOADEFFICIENCYVEHICLE_UNLOADMINUTE_NULL = "查询卸车时间，分钟数为空!";
	/** 正则表达式，为整数! **/
	public static final String CONSTANT_REG_INTEGER = "[0-9]+";
	/** 查询卸车时间：小时数不为整数 **/
	public static final String LOADANDUNLOADEFFICIENCYVEHICLE_UNLOADHOUR_NOTINEGER = "查询卸车时间：小时数不为整数!";
	/** 查询卸车时间：分钟数不为整数 **/
	public static final String LOADANDUNLOADEFFICIENCYVEHICLE_UNLOADMINUTE_NOTINEGER = "查询卸车时间：分钟数不为整数!";
	/** 线路发车标准的到达时间为空! **/
	public static final String HHMM_ARRIVETIME_IS_NULL = "线路发车标准的到达时间为空!";
	/** 线路发车标准的默认到达时间 **/
	public static final String DEFAULT_ARRIVE_TIME = "0000";
	/** 组织配置参数**/
	public static final String CONFIGURATIONPARAMSENTITY_PARAM = "组织配置参数:";
	/**组织配置项的值为空!**/
	public static final String CONFIGURATIONPARAMSENTITY_CONFIG_VALUE_NOT_INTEGER = "组织配置项的值存在非数字字符!";
	/** 外场阀值时间 **/
	public static final String TFR_PARM_PICKUP_THRESHOLD = "外场阀值时间 :";
	/** 部门编码 **/
	public static final String DEPT_ORG_CODE = "部门编码:";
	/** 线路发车标准的到达时间存在非数字字符! **/
	public static final String HHMM_ARRIVETIME_NOT_INTEGER = "线路发车标准的到达时间存在非数字字符!";
	/**走货路径详细信息中，路段号为空!**/
	public static final String PATH_DETAIL_ROUTE_NO_IS_NULL = "走货路径路段号为空!运单号为:";
	/**走货路径详细信息中，路段号为空!**/
	public static final String PATH_DETAIL_ROUTE_NO_IS_NULL_GLOB = "foss.scheduling.transportPath.pathDetailRouteNoNull";
	/**走货路径详细信息中，路段号为空!**/
	public static final String PATH_DETAIL_GOODSNO_IS_NULL = "走货路径流水号为空!运单号为:";
	/**走货路径详细信息中，路段号为空!**/
	public static final String PATH_DETAIL_GOOODSNO_IS_NULL_GLOB = "foss.scheduling.transportPath.pathDetailGoodsNoNull";
	/** 开单组织编码为空,运单号为: **/
	public static final String CREATE_ORG_CODE_IS_NULL = "运单信息中,开单组织编码为空,运单号为:";
	/** 开单组织编码为空,运单号为: **/
	public static final String CREATE_ORG_CODE_IS_NULL_GLOB = "foss.scheduling.transportPath.createOrgCodeNull";
	/** 开单运输性质为空!运单号为: **/
	public static final String TRANSTYPE_IS_NULL_GLOB = "foss.scheduling.transportPath.transTypeIsNull";
	/** 开单运输性质为空!运单号为: **/
	public static final String TRANSTYPE_IS_NULL = "开单组织运输性质为空!运单号为:";
	/**根据集中接送货部门编码未查询到外场和集中开单组信息**/
	public static final String BILLINGGROUPTRANSFERENTITY_IS_NULL = "根据集中接送货部门编码未查询到外场和集中开单组信息!运单号为:";
	/**根据集中接送货部门编码未查询到外场和集中开单组信息**/
	public static final String BILLINGGROUPTRANSFERENTITY_IS_NULL_GLOB = "foss.scheduling.transportPath.billinggrouptransferentityIsNull";
	/**根据集中接送货部门编码查询到外场和集中开单组信息中,外场组织编码为空!**/
	public static final String BILLINGGROUPTRANSFERENTITY_OUTORGNO_IS_NULL = "根据集中接送货部门编码查询到外场和集中开单组信息中,外场组织编码为空!运单号为:";
	/**根据集中接送货部门编码查询到外场和集中开单组信息中,外场组织编码为空!**/
	public static final String BILLINGGROUPTRANSFERENTITY_OUTORGNO_IS_NULL_GLOB = "foss.scheduling.transportPath.billinggrouptransferentityTransFerCodeNull";
	/**营业部查询默认配置外场**/
	public static final String CURRENTORGCODE_BY_DEFAULTTRANSCODE_IS_NULL = "从营业部未查询到默认始发配置外场!运单号为:";
	/**营业部查询默认配置外场**/
	public static final String CURRENTORGCODE_BY_DEFAULTTRANSCODE_IS_NULL_GLOB = "foss.scheduling.transportPath.defaulttranscodeIsNull";
	/**查询走货路径,输入的运单号为空!**/
	public static final String WAYBILL_IS_NULL = "查询走货路径,输入的运单号为空!";
	/**查询走货路径,输入的运单号为空!**/
	public static final String WAYBILL_IS_NULL_GLOB = "foss.scheduling.transportPath.waybillNoIsNull";
	/** 根据运单号查询走货路径上的流水号信息时,输入的运单号为空! **/
	public static final String LISTQUERYGOODSNO_WAYBILL_IS_NULL = "根据运单号查询走货路径上的流水号信息时,输入的运单号为空!";
	/** 根据运单号查询走货路径上的流水号信息时,输入的运单号为空! **/
	public static final String LISTQUERYGOODSNO_WAYBILL_IS_NULL_GLOB = "foss.scheduling.transportPath.queryGoodsNosWayBillNoNull";
	/**运单号为空!**/
	public static final String WAYBILL_NO_NULL = "运单号为空!";
	/**运单号为空!**/
	public static final String WAYBILL_NO_NULL_GLOB = "foss.scheduling.transportPath.waybillNoNull";
	/** 没有查询到走货路径详细信息,运单号为: **/
	public static final String NO_PATH_DETAIL_FOUND = "没有查询到走货路径详细信息,运单号为:";
	/** 没有查询到走货路径详细信息,运单号为: **/
	public static final String NO_PATH_DETAIL_FOUND_GLOB = "foss.scheduling.transportPath.noPathDetailFound";
	/**此票货还没有从部门离开**/
	public static final String DEPT_RELEAVE_NOT_EXSIT = "未查询到走货路径信息!请确认运单经过此部门，且已经离开!";
	/** 此票货还没有从部门离开 **/
	public static final String DEPT_RELEAVE_NOT_EXSIT_GLOB = "foss.scheduling.transportPath.notReleaveDeptOrg";
	/** 查询走货路径明细信息异常!经过同一个部门的路段信息重复! **/
	public static final String DEPT_RELEAVE_MULTI = "查询走货路径明细信息异常!经过同一个部门的路段信息重复!";
	/** 查询走货路径明细信息异常!经过同一个部门的路段信息重复! **/
	public static final String DEPT_RELEAVE_MULTI_GLOB = "foss.scheduling.transportPath.releaveMulti";
	/** 走货路径路径路段号无效<非数字>!运单号为： **/
	public static final String INVALID_ROUTENO = "走货路径路径路段号无效<非数字>!运单号为:";
	/** 走货路径路径路段号无效<非数字>!运单号为： **/
	public static final String INVALID_ROUTENO_GLOB = "foss.scheduling.transportPath.invalidRouteNo";
	/**从综合查询路线信息,出发部门为空!**/
	public static final String FREIGHTROUTEBYSOURCETARGET_SOURCECODE_NULL = "从综合查询路线信息,出发部门为空!";
	/**从综合查询路线信息,出发部门为空!**/
	public static final String FREIGHTROUTEBYSOURCETARGET_SOURCECODE_NULL_GLOB = "foss.scheduling.transportPath.sourceCodeIsNull";
	/**从综合查询路线信息,到达部门为空!**/
	public static final String FREIGHTROUTEBYSOURCETARGET_TARGETCODE_NULL =  "从综合查询路线信息,到达部门为空!";
	/**从综合查询路线信息,到达部门为空!**/
	public static final String FREIGHTROUTEBYSOURCETARGET_TARGETCODE_NULL_GLOB = "foss.scheduling.transportPath.targetCodeIsNull";
	/**从综合查询路线信息,出发时间为空!**/
	public static final String FREIGHTROUTEBYSOURCETARGET_LEAVEDATE_NULL = "从综合查询路线信息,计划出发时间为空!";
	/**从综合查询路线信息,出发时间为空!**/
	public static final String FREIGHTROUTEBYSOURCETARGET_LEAVEDATE_NULL_GLOB = "foss.scheduling.transportPath.leaveDateIsNull";
	/** 从综合查询路线信息,计划到达时间为空! **/
	public static final String FREIGHTROUTEBYSOURCETARGET_ARRIVEDATE_NULL = "从综合查询路线信息,计划到达时间为空!";
	/** 从综合查询路线信息,计划到达时间为空! **/
	public static final String FREIGHTROUTEBYSOURCETARGET_ARRIVEDATE_NULL_GLOB = "foss.scheduling.transportPath.arriveDateIsNull";
	
	public static final String HORIZONTAL_LINE = "-";
	
	public static final String BACKSLASH="/";
	/**调整走货路径失败,当前部门不在走货路径上。**/
	public static final String CURRENT_ORG_CODE_NOT_ON_PATH = "调整走货路径失败,当前部门不在走货路径上.";
	/**调整走货路径失败,当前部门不在走货路径上。**/
	public static final String CURRENT_ORG_CODE_NOT_ON_PATH_GLOB = "foss.scheduling.transportPath.currentOrgCodeNotOnPath";
	
	/** 输入的运单号为空 **/
	public static final String WAYBILL_NO_DELETE_IS_NULL = "删除走货路径时,运单号为空!";
	/** 输入的运单号为空 **/
	public static final String WAYBILL_NO_DELETE_IS_NULL_GLOB = "foss.scheduling.transportPath.deleteWaybillNoNull";
	/** 输入的运单号为空 **/
	public static final String WAYBILL_NO_INSERT_IS_NULL = "保存走货路径时,运单号为空!";
	/** 输入的运单号为空 **/
	public static final String WAYBILL_NO_INSERT_IS_NULL_GLOB = "foss.scheduling.transportPath.insertWaybillNoNull";
	/**走货路径超长，截取**/
	public static final String TRUNCATE_TRANSPORTPATH = "走货路径超长,截取长度为333个字符!运单号为:";
	/**走货路径超长，截取**/
	public static final String BEFORE_TRUNCATE_TRANSPORTPATH = ",截取前走货路径为:";
	/**走货路径超长，截取**/
	public static final String AFTER_TRUNCATE_TRANSPORTPATH = ",截取后走货路径为:";
	/**入库记录，存在重复!运单号:流水号为:**/
	public static final String IN_STOCK_REPEAT_RECORD = "foss.scheduling.transportPath.instockRepeatRecord";
	/**走货路径重复!**/
	public static final String PATH_DETAIL_REPEAT = "走货路径重复!运单号为：";
	/**走货路径重复!**/
	public static final String PATH_DETAIL_REPEAT_GLOB = "foss.scheduling.transportPath.repeatPathDetail";
	/**组织编码为空**/
	public static final String ORGCODE_IS_NULL = "组织编码为空!";
	/**运输性质为空**/
	public static final String TRANS_MODEL_NULL = "运输性质为空!";
	/**运输性质为空**/
	public static final String TRANS_MODEL_NULL_GLOB = "foss.scheduling.transportPath.transModelNull";
	/** 综合基础线路路段信息配置异常,路段出发部门为空!走货路径出发部门:到达部门:运输性质: **/
	public static final String FREIGHTROUTELINEDTO_CANTFINDSOURCEORG = "综合基础线路路段信息配置异常,路段出发部门为空!走货路径出发部门:到达部门:运输性质:";
	/** 综合基础线路路段信息配置异常,路段出发部门为空!走货路径出发部门:到达部门:运输性质: **/
	public static final String FREIGHTROUTELINEDTO_CANTFINDSOURCEORG_GLOB = "foss.scheduling.transportPath.freightSourceCodeNull";
	/** 综合基础线路路段信息配置异常,路段到达部门为空!走货路径出发部门:到达部门:运输性质: **/
	public static final String FREIGHTROUTELINEDTO_CANTFINDTARGETORG = "综合基础线路路段信息配置异常,路段到达部门为空!走货路径出发部门:到达部门:运输性质:";
	/** 综合基础线路路段信息配置异常,路段到达部门为空!走货路径出发部门:到达部门:运输性质: **/
	public static final String FREIGHTROUTELINEDTO_CANTFINDTARGETORG_GLOB = "foss.scheduling.transportPath.freightDestCodeNull";
	/**打印标签输入参数为:**/
	public static final String PRINTLABEL_INPUT_PARAMETERS = null;
	/** 查询走货路径明细最后一段信息异常!运单号为: **/
	public static final String LAST_PATHDETAIL_EXCEPTION = "查询走货路径明细最后一段信息异常!运单号为:";
	/** 走货路径下以部门修改! **/
	public static final String MODIFY_NEXTDESTORGCODE = "走货路径下以部门修改!";
	/** 下一部门: **/
	public static final String NEXT_DEST_ORG_CODE = "下一部门:";
	/** 路段号： **/
	public static final String ROUTE_NO = "路段号:";
	/** 修改为新的下一部门: **/
	public static final String NEW_DEST_ORG_CODE = "修改为新的下一部门:";
	/** 拼接新旧走货路径，为了衔接新旧路径，生成一条衔接路段： **/
	public static final String SPLICE_CHICKENRIBSPATHDETAILENTITY = "拼接新旧走货路径，为了衔接新旧路径，生成一条衔接路段!";
	/**标识这段走货路径是为了衔接新，旧走货路径，拼接出来的**/
	public static final String CHICKENRIBS_PATH = "CHICKENRIBS";
	/** 走货路径明细信息为空!运单号为: **/
	public static final String PATH_DETAIL_NULL = "走货路径明细信息为空!运单号为:";
	/** 走货路径明细信息为空!运单号为: **/
	public static final String PATH_DETAIL_NULL_GLOB = "foss.scheduling.transportPath.pathDetailNull";
	/** 走货路径明细信息中，流水号为空!运单号为: **/
	public static final String GOODSNO_IS_NULL = "走货路径明细信息中，流水号为空!运单号为:";
	/** 走货路径明细信息中，流水号为空!运单号为: **/
	public static final String GOODSNO_IS_NULL_GLOB = "foss.scheduling.transportPath.pathDetailGoodsNoNull";
	/** 小件修改走货路径目的地 **/
	public static final String CHANGE_DEST_FOR_EXPRESS = "express_objorgcode";
	/** 小件修改走货路径目的地倒数第二段走货路径的nextorgcode需要做相应修改 **/
	public static final String CHANGE_NEXT_FOR_EXPRESS = "express_nextorgcode";
	/** 找不到走货路径重新生成的路段.且目的地与出发部门相同 **/
	public static final String HIDDEN_RECREATE_SAMEORGOBJ = "HIDDEN_RECREATE_SAME";
	/** 走货路径只有一段，此时，拼接一段 **/
	public static final String ONLY_ONE_SPLICE_ONE = "ONLY_ONE_SPLICE_ONE";
	/** 非合车调整，修改老路径最后一段信息：objectOrgCode,nextOrgCode,nextArriveTime **/
	public static final String NOTJOINCAR_OLD = "NOTJOINCAR_OLD";
	/** 非合车调整，增加新路径，将修改的路径的最后一段信息：nextOrgCode,nextArriveTime **/
	public static final String NOTJOINCAR_NEW = "NOTJOINCAR_NEW";
	/** 开单后走货路径信息为: **/
	public static final String ORGINAL_TRANSPORTPATH = "开单后走货路径信息为:";
}