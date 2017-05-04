/**  
 * Project Name:tfr-common-api  
 * File Name:BusinessSceneConstants.java  
 * Package Name:com.deppon.foss.module.transfer.common.api.shared.define  
 * Date:2015年4月14日下午6:46:23  
 *  
 */  
  
package com.deppon.foss.module.transfer.common.api.shared.define;  
/**  
 * ClassName: BusinessSceneConstants <br/>  
 * Function: 定义所有产生需要异步处理数据的业务场景(业务节点A)<br/>  
 * Reason: 中转模块中有很多在A业务节点去触发B动作，例如派送装车(A)时需要给客户发短信(B)之类的需求
 * 	为了不影响主流程，一般都将B节点的业务处理做成异步，所以需要在A节点时使用临时表记录待处理的业务数据，
 * 然后再起定时任务来读取临时表数据，异步完成B节点的业务，故此计划建立通用的、易维护的、易扩展的临时表，字段如下：
 * ①ID 数据库主键
 * ②business_id 业务主键(可以是A节点产生的单据的ID或者No之类的唯一标示)
 * ③business_scene 业务场景(业务节点A)
 * ④business_goal 业务目标(节点B)
 * ⑥create_time 创建时间时间戳，insert时写入sysdate
 * ⑦modify_time 更新时间，business_goal达成后写入sysdate
 * ⑧business_time 业务时间，由接口调用者传入
 * ⑨operator_code 操作人工号<br/>  
 * date: 2015年4月14日 下午6:46:23 <br/>  
 *  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public class BusinessSceneConstants {
	/**
	 * 卸车任务，发短信
	 */
	public final static String BUSINESS_SCENE_UNLOADTASK_SMS="UNLOADTASK_SMS";
	/**
	 * 取消 卸车任务，发短信
	 */
	public final static String BUSINESS_SCENE_CANCEL_UNLOADTASK_SMS="CANCEL_UNLOADTASK_SMS";
	
	/**
	 * 车辆出发
	 */
	public final static String BUSINESS_SCENE_TRUCK_DEPARTURE = "TRUCK_DEPARTURE";
	
	/**
	 * 取消车辆出发
	 */
	public final static String BUSINESS_SCENE_CANCEL_TRUCK_DEPARTURE = "CANCEL_TRUCK_DEPARTURE";
	
	/**
	 * 车辆到达
	 */
	public final static String BUSINESS_SCENE_TRUCK_ARRIVAL = "TRUCK_ARRIVAL";
	/**
	 * 卸车
	 */
	public final static String BUSINESS_SCENE_TRUCK_UNLOAD = "TRUCK_UNLOAD";
	
	/**
	 * 取消车辆到达
	 */
	public final static String BUSINESS_SCENE_CANCEL_TRUCK_ARRIVAL = "CANCEL_TRUCK_ARRIVAL";
	
	/**
	 * 派送车辆放行
	 */
	public final static String BUSINESS_SCENE_DELIVER_DEPART = "DELIVER_DEPART";
	
	/**
	 *派送装车扫描
	 */
	public final static String BUSINESS_SCENE_SENT_SCAN = "SENT_SCAN";
	
	/**
	* @fields BUSINESS_SCENE_TRUCK_STRAIGHT_ARRIVAL 直发中转场的到达
	* @author 276198-foss-duhao
	* @update 2015-10-23 下午2:33:16
	* @version V1.0
	*/
	public final static String BUSINESS_SCENE_TRUCK_STRAIGHT_ARRIVAL = "TRUCK_STRAIGHT_ARRIVAL";
	
	/**
	* @fields BUSINESS_SCENE_TRUCK_STRAIGHT_DEPARTURE 直发中转场的出发
	* @author 276198-foss-duhao
	* @update 2015-10-23 下午2:33:22
	* @version V1.0
	*/
	public final static String BUSINESS_SCENE_TRUCK_STRAIGHT_DEPARTURE = "TRUCK_STRAIGHT_DEPARTURE";
	
	/**
	 * 交接单状态：已出发
	 */
	public final static String WK_HANDORVERBILL_HAVE_DEPART = "HAVE_DEPART";
	
	/**
	 * 交接单状态：取消出发
	 */
	public final static String WK_HANDORVERBILL_CANCEL_HAVE_DEPART = "CANCEL_HAVE_DEPART";
	
	/**
	 * 交接单状态：已到达
	 */
	public final static String WK_HANDORVERBILL_HAVE_ARRIVE = "HAVE_ARRIVE";
	
	/**
	 * 交接单状态：取消到达
	 */
	public final static String WK_HANDORVERBILL_CANCEL_HAVE_ARRIVE = "CANCEL_HAVE_ARRIVE";
	
	/**
	 * 交接单状态：卸车
	 */
	public final static String WK_HANDORVERBILL_UNLOAD_UPDATE = "UNLOAD_UPDATE";

}
  
