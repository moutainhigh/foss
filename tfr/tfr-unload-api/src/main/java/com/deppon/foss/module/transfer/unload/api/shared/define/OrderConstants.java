package com.deppon.foss.module.transfer.unload.api.shared.define;

public class OrderConstants {
    /*点单任务状态*/
	//点单中
	public static final String ASSIGN_ORDER_TASK_STATE_IN = "IN";
	//点单完毕
	public static final String ASSIGN_ORDER_TASK_STATE_END = "END";
	
	/*点单差异类型*/
	public static final String ORDER_REPORT_TYPE_LOSE = "LOSE";
	
	public static final String ORDER_REPORT_TYPE_NORMAL = "NORMAL";
	
	public static final String ORDER_REPORT_TYPE_MORE = "MORE";
	//已点单
	public static final String ORDER_TASK_ARLEADLY_ALREADY = "already_order";
	//点单任务不存在
	public static final String ORDER_TASK_NO_EXIST = "no_exist";
}
