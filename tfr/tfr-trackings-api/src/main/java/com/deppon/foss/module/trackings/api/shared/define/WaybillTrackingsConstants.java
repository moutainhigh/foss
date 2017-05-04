package com.deppon.foss.module.trackings.api.shared.define;

public class WaybillTrackingsConstants {
	//轨迹操作类型
	//开单
	public static final String OPERATE_TYPE_CREATE = "CREATE";
	//PDA开单
	public static final String OPERATE_TYPE_CREATE_PDA = "CREATE_PDA";
	//出发
	public static final String OPERATE_TYPE_DEPART = "DEPART";
	//取消出发
	public static final String OPERATE_TYPE_CANCEL_DEPART = "CANCEL_DEPART";
	//到达
	public static final String OPERATE_TYPE_ARRIVE = "ARRIVE";
	//取消到达
	public static final String OPERATE_TYPE_CANCEL_ARRIVE = "CANCEL_ARRIVE";
	//生成快递代理外发单
	public static final String OPERATE_TYPE_LDP_PARTIAL_LINE = "LDP_PARTIAL_LINE";
	//作废快递代理外发单
	//public static final String OPERATE_TYPE_LDP_PARTIAL_LINE_INVALID = "LDP_PARTIAL_LINE_INVALID";
	//反审核快递代理外发单
	public static final String OPERATE_TYPE_LDP_PARTIAL_LINE_DEAUDIT = "LDP_PARTIAL_LINE_DEAUDIT";
	//中转外发单
	//public static final String OPERATE_TYPE_LDP_PARTIAL_LINE_TRANSFER = "LDP_PARTIAL_LINE_TRANSFER";
	
	//快递代理轨迹同步（人工录入、代理公司推送、快递100推送）
	public static final String OPERATE_TYPE_LDP_TRACK = "LDP_TRACK";
	//快递代理-出发
	public static final String OPERATE_TYPE_LDP_TRACK_LEAVE = "LDP_TRACK_LEAVE";
	//快递代理-到达
	public static final String OPERATE_TYPE_LDP_TRACK_ARRIVE = "LDP_TRACK_ARRIVE";
	//快递代理-派件
	public static final String OPERATE_TYPE_LDP_TRACK_DELIVER = "LDP_TRACK_DELIVER";
	//营业部外发-转寄
	public static final String OPERATE_TYPE_SD_TRACK_TRANSFER = "OPERATE_TYPE_SD_TRACK_TRANSFER";
	//取消转寄
	public static final String OPERATE_TYPE_SD_TRACK_TRANSFER_CANCEL = "OPERATE_TYPE_SD_TRACK_TRANSFER_CANCEL";
	//客户通知
	public static final String OPERATE_TYPE_NOTIFY = "NOTIFY";
	public static final String OPERATE_TYPE_NOTIFY_PRE = "NOTIFY_PRE";
	public static final String OPERATE_TYPE_NOTIFY_DELIVER = "NOTIFY_DELIVER";
	public static final String OPERATE_TYPE_NOTIFY_PICKUP = "NOTIFY_PICKUP";
	//派送
	public static final String OPERATE_TYPE_DELIVERY = "DELIVERY";
	
	//签收（正常签收，部分签收、异常签收、派送拉回）
	//正常签收
	public static final String OPERATE_TYPE_NORMAL_SIGN = "NORMAL_SIGN";
	//部分签收
	public static final String OPERATE_TYPE_PART_SIGN = "PART_SIGN";
	//异常签收
	public static final String OPERATE_TYPE_EXCEPTION_SIGN = "EXCEPTION_SIGN";
	//派送拉回
	public static final String OPERATE_TYPE_DELIVERY_RETURN = "DELIVERY_RETURN";
	//反签收
	public static final String OPERATE_TYPE_SIGN_RFC = "SIGN_RFC";
	//终止（作废或中止）
	public static final String OPERATE_TYPE_ABORTED = "ABORTED";
	
	//返货开单
	public static final String OPERATE_TYPE_RETURN_CARGO = "RETURN_CARGO";
	
	//返回信息
	public static final String FLG_SUCCESS = "true";
	public static final String FLG_FAILURE = "false";
	
	public static final String CODE_SUCCESS = "200";
	public static final String CODE_FAILURE = "201";
	public static final String CODE_WAYBILLNO_ERROR = "504";
	public static final String CODE_WAYBILLNO_REPEAT = "502";
	public static final String CODE_DATA_MISS = "400";
	public static final String CODE_MSGID_ERROR = "600";
	
	//订阅方式
	public static final String ORDER_TYPE_ORDER = "order";
	public static final String ORDER_TYPE_REPUSH = "repush";
	//推送类型
	//全量推送
	public static final String PUSH_OVERRID = "override";
	//增量推送
	public static final String PUSH_APPEND = "append";
	
	//watchStatus
	public static final String WATCH_STATUS_NORMAL = "normal";
	public static final String WATCH_STATUS_ABORT = "abort";
	public static final String WATCH_STATUS_STOP = "stop";
	
	public static final String MSG_SUCCESS = "成功";
	public static final String MSG_FAILURE = "失败";
	public static final String MSG_WAYBILLNO_ERROR = "单号错误";
	public static final String MSG_WAYBILLNO_REPEAT = "重复订阅";
	public static final String MSG_DATA_MISS = "数据不完整";
	public static final String MSG_MSGID_ERROR = "msgId为空或有误";
	
	//日志编码
	//100订阅单号成功
	public static final String LOG_DOP2FOSS_WAYBILLNO_SUCCESS = "LOG_DOP2FOSS_WAYBILLNO_SUCCESS";
	//100订阅单号失败
	public static final String LOG_DOP2FOSS_WAYBILLNO_FAILURE = "LOG_DOP2FOSS_WAYBILLNO_FAILURE";
	//foss推送轨迹给DOP，DOP接收数据成功
	public static final String LOG_FOSS2DOP_TRACK_SUCCESS = "LOG_FOSS2DOP_TRACK_SUCCESS";
	//foss推送轨迹给DOP，DOP接收数据失败
	public static final String LOG_FOSS2DOP_TRACK_FAILURE = "LOG_FOSS2DOP_TRACK_FAILURE";
	//DOP推送100返回结果给FOSS，FOSS接收数据成功
	public static final String LOG_DOP2FOSS_RESULT_SUCCESS = "LOG_DOP2FOSS_RESULT_SUCCESS";
	//DOP推送100返回结果给FOSS，FOSS接收数据失败
	public static final String LOG_DOP2FOSS_RESULT_FAILURE = "LOG_DOP2FOSS_RESULT_FAILURE";

	
}
