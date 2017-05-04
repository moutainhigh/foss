package com.deppon.foss.module.transfer.common.api.shared.define;


/**
* @description job同步数据到快递快递系统的通知任务类型定义
* @version 1.0
* @author 283250-foss-liuyi
* @update 2016年5月9日 下午4:23:01
*/
public class NotifyWkConstants {
	
	/**
	 * 车辆出发
	 */
	public final static String NOTIFY_TYPE_TRUCK_DEPARTURE = "TRUCK_DEPARTURE";
	
	/**
	 * 取消车辆出发
	 */
	public final static String NOTIFY_TYPE_CANCEL_TRUCK_DEPARTURE = "CANCEL_TRUCK_DEPARTURE";
	
	/**
	 * 车辆到达
	 */
	public final static String NOTIFY_TYPE_TRUCK_ARRIVAL = "TRUCK_ARRIVAL";
	
	/**
	 * 取消车辆到达
	 */
	public final static String NOTIFY_TYPE_CANCEL_TRUCK_ARRIVAL = "CANCEL_TRUCK_ARRIVAL";
	
	/**
	 *取消卸车分配
	 */
	public final static String NOTIFY_TYPE_CANCEL_ASSIGNED_UNLOAD_TASK="CANCEL_ASSIGNED_UNLOAD_TASK";
	
	/**
	 * 同步预计到达时间到wk
	 */
	public final static String NOTIFY_TYPE_PUSH_PLAN_ARRIVALTIME_TO_WK="PUSH_PLAN_ARRIVALTIME_TO_WK";
	
	/**
	 *分配卸车任务
	 */
	public final static String NOTIFY_TYPE_ASSIGNED_UNLOAD_TASK="ASSIGNED_UNLOAD_TASK";
	
	
	
	
	/**
	 * 同步车辆任务
	 */
	public final static String NOTIFY_TYPE_PUSH_TRUCK_TASK="PUSH_TRUCK_TASK_TO_WK";
	
	
	/**
	 * 生成车辆任务
	 * */
	public final static String NOTIFY_TYPE_CREATE_TRUCK_TASK="CREATE_TRUCK_TASK";
	
	/**
	 * 新增卸车、确认卸车(提交卸车任务)
	 * */
	public final static String NOTIFY_TYPE_UNLOAD_UPDATE="UNLOAD_UPDATE";
	
	
	/**
	 * 删除车辆任务信息
	 * */
	public final static String NOTIFY_TYPE_DELETE_TRUCK_TASK="DELETE_TRUCK_TASK";
	
}
