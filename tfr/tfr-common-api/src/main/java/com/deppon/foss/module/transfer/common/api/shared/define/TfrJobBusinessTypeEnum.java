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
 *  PROJECT NAME  : tfr-common-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/shared/define/TfrJobBusinessTypeEnum.java
 *  
 *  FILE NAME          :TfrJobBusinessTypeEnum.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.common.api.shared.define;
/**
 * 
 * @author 038300-foss-pengzhen
 *
 */
public enum TfrJobBusinessTypeEnum {
	UPDATE_TRANSPORT_PATH("UPDATE_TRANSPORT_PATH", "更新走货路径", 			 				 -30, 0,  0),
	ST_REPORT("ST_REPORT", "清仓差异报告", 				 				 -30, 0 , 0),
	ST_REPORT_ONE("ST_REPORT_ONE", "一次清仓差异报告", 				 				 -30, 0 , 0),
	ST_OA_ERROR("ST_OA_ERROR", "清仓差异报告_OA差错单上报", 				 -60, 24, 1),
	AUTO_CHECK_DPJZMSG("AUTO_CHECK_DPJZMSG", "自动审核德邦家装送装签收信息", 		-60, 24, 1),
	PUSH_AIRPICK_OPP("PUSH_AIRPICK_OPP", "推送合大票信息至OPP系统", 				 -60, 24, 1),
	HANDOVER_BILL("HANDOVER_BILL", "生成交接单", 			 				 -30, 0,  0),
	VEHICLE_TASK("VEHICLE_TASK", "生成任务车辆", 			 				 -30, 0,  0),
	HANDOVERBILL_TODO("HANDOVERBILL_TODO", "交接单中待办事项漂移", 			 				 -30, 0,  0),
	REPORT_UNLOAD_LGF_TO_OA("REPORT_UNLOAD_LGF_TO_OA", "上报oa卸车少货找到差错", 			 				 -30, 0,  0),
	REPORT_ST_LGF_TO_OA("REPORT_ST_LGF_TO_OA", "上报oa清仓少货找到差错", 			 				 -30, 0,  0),
	VEHICLE_TASK_CALL_ESB("VEHICLE_TASK_CALL_ESB", "任务车辆调用ESB", 			 				 -30, 0,  0),
	
	LOADER_WORKLOAD("LOADER_WORKLOAD", "装卸车工作量统计", 			 	 -30, 0,  0),
	LOADER_FORKLIFT_WORK("LOADER_FORKLIFT_WORK","叉车票数统计",                                  -30,     0,  0),
	DELIVER_LOAD_GAP_REPORT("DELIVER_LOAD_GAP_REPORT", "派送装车差异报告", -30, 0,  0),
	UNLOAD_DIFF_REPORT_OA("UNLOAD_DIFF_REPORT_OA", "卸车差异报告OA上报",   -30, 0,  0),
	UNLOAD_DIFF_REPORT_QMS("UNLOAD_DIFF_REPORT_QMS", "卸车差异报告QMS上报",   -30, 0,  0),
	FORECAST_TRANSFERCENTER_DEPART("FORECAST_TRANSFERCENTER_DEPART", "外场出发的货量预测", -60, 0,  0),
	FORECAST_TRANSFERCENTER_ARRIVE("FORECAST_TRANSFERCENTER_ARRIVE", "外场到达的货量预测", -60, 0,  0),
	FORECAST_SALESDEPARTMENT_DEPART("FORECAST_SALESDEPARTMENT_DEPART", "营业部出发的货量预测", -60, 0,  0),
	FORECAST_SALESDEPARTMENT_ARRIVE("FORECAST_SALESDEPARTMENT_ARRIVE", "营业部到达的货量预测", -60, 0,  0),
	CALCULATE_AVERAGE_WEIGHTANDVOLUME("CALCULATE_AVERAGE_WEIGHTANDVOLUME", "计算平均重量体积", -60*24*30, 0,  0),
	CANCEL_DEPARTURE("CANCEL_DEPARTURE", "车辆放行失效", -30, 0, 0),
	UNLOAD_DIFFER_REPORT("UNLOAD_DIFFER_REPORT", "生成卸车差异报告", -30, 0, 0),
	UNLOAD_DIFFER_REPORT_ONE("UNLOAD_DIFFER_REPORT_ONE", "生成一次卸车差异报告", -30, 0, 0),
	TRUCK_ACTION_UPDATE("TRUCK_ACTION_UPDATE", "更新任务车辆绑定表", -30, 0, 0),
	TRUCK_TASK_BILL_NO_STATUS_JOB("TRUCK_TASK_BILL_NO_STATUS_JOB", "车辆到达，进行入库操作，并执行入库后更新走货路径的操作", -30, 0, 0),
	TRUCK_TASK_STOCK_JOB("TRUCK_TASK_STOCK_JOB", "车辆入库，更改入库的走货路径", -30, 0, 0),
	TRUCK_TASK_STOCK_JOB_TRANS("TRUCK_TASK_STOCK_JOB_TRANS", "入库后更改走货路径", -30, 0, 0),
	TRUCK_TASK_FLAG_PLATFORM("TRUCK_TASK_FLAG_PLATFORM", "车辆出发、取消出发时更新月台的信息", -30, 0, 0),
	TRUCK_TASK_TRANSFER_CENTER_JOB("TRUCK_TASK_TRANSFER_CENTER_JOB", "车辆出发、到达、取消出发、取消到达时需要更新走货路径的状态", -30, 0, 0),
	MIGRATE_TRANPORT_PATH_DATA_JOB("MIGRATE_TRANPORT_PATH_DATA_JOB", "签收的货物走货路径数据迁移", -60*24, 0, 0),
	LOAD_VEHICLE_SEAL_DATA_JOB("LOAD_VEHICLE_SEAL_DATA_JOB", "封签差错上报OA", -30, 0, 0),
	MAKEUP_WAYBILL_JOB("MAKEUP_WAYBILL_JOB","运单补录或PDA更新重量体积时更新交接单配载单装卸车工作量",-30,0,0),
	AUTOCHECK_DPJZ_JOB("AUTOCHECK_DPJZ_JOB","自动审核德邦家装送装签收信息",-30,0,0),
	AUTOPUSH_TOOPP_JOB("AUTOPUSH_TOOPP_JOB","推送合大票信息至OPP系统",-30,0,0),
	STOCK_PAIR_JOB("STOCK_PAIR_JOB","库存副表定时处理数据",-30,0,0),
	CANCEL_ST_TASK("CANCEL_ST_TASK","自动取消清仓任务",-30,0,0),
	STOCK_POSITION_NUMBER_JOB("STOCK_POSITION_NUMBER_JOB","添加定位编号任务",-30,0,0),
	AIR_NOTIFY_CUSTOMERS_JOB("AIR_NOTIFY_CUSTOMERS_JOB","更新空运通知客户表和实际承运信息表的通知状态",-30,0,0),
	STOCK_SATURATION_JOB("STOCK_SATURATION_JOB","仓库饱和度",-30,0,0),
	STOCK_SATURATION_SMS_JOB("STOCK_SATURATION_SMS_JOB","仓库饱和度消息推送",-30,0,0),
	SEND_RATE_JOB("SEND_RATE_JOB","派送率定时处理数据",-30,0,0),
	RETURN_RATE_JOB("RETURN_RATE_JOB","退单率定时处理数据",-30,0,0),
	PULLBACK_RATE_JOB("PULLBACK_RATE_JOB","拉回率定时处理数据",-30,0,0),	ST_REPORT_GAP_REPAIR("ST_REPORT_GAP_REPAIR", "自动处理清仓差异报告明细未完成状态的数据", 				 				 			  -30, 0 , 0),
	GOODS_AREA_DENSITY("GOODS_AREA_DENSITY", "整点生成货区密度",-60,0,0),
	TFR_CTR_STAFF("TFR_CTR_STAFF", "通过货量、叉车票数计算理货员、叉车司机是否出勤",-60,0,0),
	QUANTITY_STA_DEPART("QUANTITY_STA_DEPART", "货量预测-实际货量-出发货量",-60,0,0),
	QUANTITY_STA_DEPART_2ND_DAY("QUANTITY_STA_DEPART_2ND_DAY", "货量预测-第2天实际货量-出发货量",-60,0,0),
	QUANTITY_STA_DEPART_HIS("QUANTITY_STA_DEPART_HIS", "迁移实际货量-出发货量",-60,0,0),
	QUANTITY_STA_DEPART_FCST_HIS("QUANTITY_STA_DEPART_FCST_HIS", "迁移预测货量-出发货量",-60,0,0),
	QUANTITY_STA_ARRIVE("QUANTITY_STA_ARRIVE", "货量预测-实际货量-到达货量",-60,0,0),
	QUANTITY_STA_ARRIVE_2ND_DAY("QUANTITY_STA_ARRIVE_2ND_DAY", "货量预测-第2天实际货量-到达货量",-60,0,0),
	QUANTITY_STA_ARRIVE_HIS("QUANTITY_STA_ARRIVE_HIS", "迁移实际货量-到达货量",-60,0,0),
	FORKLIFT_EFFICIENCY_JOB("FORKLIFT_EFFICIENCY_JOB","叉车效率统计任务",-30,0,0),
	LOSE_STARTING_OA_ERROR("LOSE_STARTING_OA_ERROR", "出发丢货上报",-60,0,0),
	LOSE_STARTING_FIND_OA_ERROR("LOSE_STARTING_FIND_OA_ERROR", "出发丢货找到",-60,0,0),
	FORECAST_EXHIBIT_CARGO("FORECAST_EXHIBIT_CARGO","展会货货量统计",-30,0,0),
	QUANTITY_STA_ARRIVE_FCST_HIS("QUANTITY_STA_ARRIVE_FCST_HIS", "迁移预测货量-到达货量",-60,0,0),
	SALES_DEPT_EXP_LOST_STOCK("SALES_DEPT_EXP_LOST_STOCK", "快递派送丢货差异报告-0点库存快照",-60,0,0),
	SALES_DEPT_EXP_LOST_REPORT("SALES_DEPT_EXP_LOST_REPORT", "快递派送丢货差异报告-14点差异报告",-60,0,0),
	PUSH_AGENT_WAYBILLNO_JOB("PUSH_AGENT_WAYBILLNO_JOB","推送绑定快递代理单号给快递100",-60,0,0),
	PUSH_FOR_WAYBILL_TRACKINGS_JOB("PUSH_FOR_WAYBILL_TRACKINGS_JOB","将运单异步存入轨迹表，以便后续将轨迹推送给快递100",-60,0,0),
	PUSH_FOR_WAYBILL_PTP_JOB("PUSH_FOR_WAYBILL_PTP_JOB","将运单推送给PTP合伙人",-60,0,0),
	PUSH_WAYBILL_TRACKS_JOB("PUSH_WAYBILL_TRACKS_JOB","推送运单轨迹给快递100",-60,0,0),
    SEND_VEHICLESTATUS_2TPS_JOB("SEND_VEHICLESTATUS_2TPS_JOB","推送车辆状态给TPS系统",-60,0,0),
	LOSEGOODS_FORGUIJI28("LOSEGOODS_FORGUIJI28", "在丢货小组超过28天", -30, 0,  0),
	PUSH_SENTSCAN_CARGO_TRACK_JOB("PUSH_SENTSCAN_CARGO_TRACK_JOB","将派件扫描货物轨迹异步存储到轨迹表，以便后续推送给淘宝",-30,0,0),
	PUSH_SENTARRIVAL_CARGO_TRACK_JOB("PUSH_SENTARRIVAL_CARGO_TRACK_JOB","将车辆到达的货物轨迹异步存储到轨迹表，以便后续推送给淘宝",-30,0,0),
	PUSH_SEND_TRACK_COMM_JOB("PUSH_SEND_TRACK_COMM_JOB","将货物轨迹异步存储到轨迹表,推送给DOP",-30,0,0),
	PUSH_SENTDEPARTURE_CARGO_TRACK_JOB("PUSH_SENTDEPARTURE_CARGO_TRACK_JOB","将车辆出发的货物轨迹异步存储到轨迹表，以便后续推送给淘宝",-30,0,0),
	PUSH_SENTCITY_CARGO_TRACK_JOB("PUSH_SENTCITY_CARGO_TRACK_JOB","将到达城市货物轨迹异步存储到轨迹表，以便后续推送给淘宝",-30,0,0),
	PUSH_TRUCK_ARRIVAL_TRACK_JOB("PUSH_TRUCK_ARRIVAL_TRACK_JOB","到达货物轨迹异步存储到轨迹表，以便后续推送给Dop",-30,0,0),
	PUSH_TRUCK_DEPARTURE_TRACK_JOB("PUSH_TRUCK_DEPARTURE_TRACK_JOB","出发货物轨迹异步存储到轨迹表，以便后续推送给Dop",-30,0,0),
	PUSH_SENT_SCAN_TRACK_JOB("PUSH_SENT_SCAN_TRACK_JOB","派送扫描货物轨迹异步存储到轨迹表，以便后续推送给Dop",-30,0,0),
	PUSH_SENTARRIVAL_CARGO_TRACK_JOB_FORDOP("PUSH_SENTARRIVAL_CARGO_TRACK_JOB_FORDOP","将车辆到达的货物轨迹异步存储到轨迹表，以便后续推送给DOP",-30,0,0),    COUNT_PDA_ONLINE_JOB("COUNT_PDA_ONLINE_JOB","统计PDA在线使用情况",-30,0,0),
	TRUCK_EFFICIENCY("TRUCK_EFFICIENCY","装卸车效率统计",-30,0,0),
	PLATFORM_EFFICIENCY("PLATFORM_EFFICIENCY","月台操作效率",-30,0,0),
	TRAYSCAN_EFFICIENCY("TRAYSCAN_EFFICIENCY","托盘绑定效率统计",-30,0,0),
	FORKLIFT_GOODS_EFFICIENCY("FORKLIFT_GOODS_EFFICIENCY","叉车分货效率统计",-30,0,0),
	GOODS_DISTRIBUTION_JOB("GOODS_DISTRIBUTION_JOB","转运场货量流动分布统计",-30,0,0),
	CARGO_FCST("CARGO_FCST", "货量预测",-60,0,0),
	STOCK_DURATION_LTC("STOCK_DURATION_LTC", "库存时长-零担",-60,0,0),
	STOCK_DURATION_EXP("STOCK_DURATION_EXP", "库存时长-快递",-60,0,0),
	DENSITY_DAY("DENSITY_DAY", "库区密度-每天",-60,0,0),
	DENSITY_HOUR("DENSITY_HOUR", "库区密度-每小时",-60,0,0),
	SEND_SMS_AFTER_DELIVER_DEPART_JOB("SEND_SMS_AFTER_DELIVER_DEPART_JOB","派送放行后，给收货客户发送短信",-60,0,0),

	TEST_GIS_BE_NORMAL_JOB("TEST_GIS_BE_NORMAL_JOB","检测GIS地址匹配服务是否正常",-60,0,0),
	NO_LABEL_MOREGOODS_JOB("NO_LABEL_MOREGOODS_JOB","无标签多货qms上报",-60,0,0),
	CREATE_BATCHLOADING_REPORT_JOB("CREATE_BATCHLOADING_REPORT_JOB","分批配载差错生成",-60,0,0), 
	BATCHLOADING_REPORT_JOB("BATCHLOADING_REPORT_JOB","分批配载差错自动上报",-60,0,0),
	UNLOAD_TASK_SMS_JOB("UNLOAD_TASK_SMS_JOB","卸车任务创建，修改，删除时，未发送短信的运单，自动发送短信",-60,0,0),
	PUSH_FINDLOSTGOODS_QMS_JOB("PUSH_FINDLOSTGOODS_QMS_JOB","找到丢货数据推送到QMS",-30,0,0),
	PUSH_IN_STOCK_DATE_JOB("PUSH_IN_STOCK_DATE_JOB","找到入库表数据同步到临时表中",-30,0,0),
	PUSH_LOSTWARNING_QMS_JOB("PUSH_LOSTWARNING_QMS_JOB","丢货预警数据推送到QMS",-30,0,0),
			//duhao-276198
	PUSH_TRUCK_STRAIGHT_DEPARTURE_TRACK_JOB("PUSH_TRUCK_STRAIGHT_DEPARTURE_TRACK_JOB","直发中转场提交任务时生成出站轨迹",-30,0,0), 
	PUSH_TRUCK_STRAIGHT_ARRIVAL_TRACK_JOB("PUSH_TRUCK_STRAIGHT_ARRIVAL_TRACK_JOB","外场分配快递集中接货卸车任务时候生成",-30,0,0),
	OVERWEIGHT_VOLUMN_TOQMS("PUSH_OVERWEIGHT_VOLUMN_TOQMS","将超方超重的货物信息自动上报给QMS",-30,0,0),
	ORDER_REPORT("ORDER_REPORT", "点单差异报告", 				 				 -30, 0 , 0),
	PUSH_ORDERTASKFINISH_QMS_JOB("PUSH_ORDERTASKFINISH_QMS_JOB","自动完成点单任务",-30,0,0);
	//业务编号
	private String bizCode;	
	//业务名称
	private String bizName;
	//初始化时的业务起始时间，相对于当前时间倒退的分钟数
	private int initMins;		
	//默认处理的业务时间段，定义为小时
	private int defaultBizHourSlice;	
	//默认处理的业务时间段，定义为天
	private int defaultBizDaySlice;		
	
	private TfrJobBusinessTypeEnum(String bizCode, String bizName, int initMins, int defaultBizHourSlice, int defaultBizDaySlice){
		this.bizCode = bizCode;
		this.bizName = bizName;
		this.initMins = initMins;
		this.defaultBizHourSlice = defaultBizHourSlice;
		this.defaultBizDaySlice = defaultBizDaySlice;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getBizName() {
		return bizName;
	}

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}

	public int getInitMins() {
		return initMins;
	}

	public void setInitMins(int initMins) {
		this.initMins = initMins;
	}

	public int getDefaultBizHourSlice() {
		return defaultBizHourSlice;
	}

	public void setDefaultBizHourSlice(int defaultBizHourSlice) {
		this.defaultBizHourSlice = defaultBizHourSlice;
	}

	public int getDefaultBizDaySlice() {
		return defaultBizDaySlice;
	}

	public void setDefaultBizDaySlice(int defaultBizDaySlice) {
		this.defaultBizDaySlice = defaultBizDaySlice;
	}
}