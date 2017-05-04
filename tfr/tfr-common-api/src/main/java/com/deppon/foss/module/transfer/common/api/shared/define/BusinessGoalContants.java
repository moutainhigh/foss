/**  
 * Project Name:tfr-common-api  
 * File Name:BusinessGoalContants.java  
 * Package Name:com.deppon.foss.module.transfer.common.api.shared.define  
 * Date:2015年4月14日下午1:21:27  
 *  
 */  
  
package com.deppon.foss.module.transfer.common.api.shared.define;  

/**  
 * ClassName: BusinessGoalContants <br/>  
 * Function: 常量类，业务节点B<br/>  
 * Reason: 详细注释参见BusinessSceneConstants类. <br/>  
 * date: 2015年4月14日 下午1:21:27 <br/>  
 *  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public class BusinessGoalContants {
	
	/**
	 * 建卸车任务 时发短信
	 */
	public static final String BUSINESS_GOAL_UNLOADTASK_SMS="UNLOADTASK_SMS";
	/**
	 * 删除 卸车任务 时 取消发短信
	 */
	public static final String BUSINESS_GOAL_UNLOADTASK_SMS_CANCEL="UNLOADTASK_SMS_CANCEL";
	/**
	 * 建卸车任务 时发短信
	 */
	public static final String BUSINESS_GOAL_CANCEL_UNLOADTASK_SMS="CANCEL_UNLOADTASK_SMS";
	/**
	 * 车辆出发、到达的正反操作，均需将车载运单存入临时表，以便给快递100推送信息，该部目标仅将运单存入临时表
	 */
	public static final String BUSINESS_GOAL_EXPRESS100 = "SAVE_TRACKINGS_4EXPRESS100";
	
	/**
	 * 长途车辆出发、到达后，将车辆状态推送给TPS
	 */
	public static final String BUSINESS_GOAL_TPS = "SEND_VEHICLESTATUS_2TPS";
	
	/**
	 * 派送车辆放行后，给客户发短信
	 */
	public static final String BUSINESS_GOAL_SENDSMS_AFTER_DELIVERDEPART = "SENDSMS_AFTER_DELIVERDEPART";

	/**
	 * taobao
	* @fields BUSINESS_GOAL_TO_TAOBAO
	* @author 14022-foss-songjie
	* @update 2015年5月30日 下午2:20:26
	* @version V1.0
	*/
	public static final String BUSINESS_GOAL_TO_TAOBAO = "BUSINESS_GOAL_TO_TAOBAO";
	
	
	/**
	 * 天猫家装
	* @fields BUSINESS_GOAL_TO_TAOBAOJZ
	* @author 14022-foss-songjie
	* @update 2015年5月30日 下午2:20:23
	* @version V1.0
	*/
	public static final String BUSINESS_GOAL_TO_TAOBAOJZ = "BUSINESS_GOAL_TO_TAOBAOJZ";
	
	
	/**
	 * 家哇云
	* @fields BUSINESS_GOAL_TO_JIAWAYUN
	* @author 218381-foss-lijie
	* @update 2015年12月11日 下午2:12:12
	* @version V1.0
	*/
	public static final String BUSINESS_GOAL_TO_JIAWAYUN = "BUSINESS_GOAL_TO_JIAWAYUN";
	/**
	 * PTP合伙人
	* @fields BUSINESS_GOAL_TO_PTP
	* @author 189284-foss-张许
	* @update 2016年02月19日 下午2:12:12
	* @version V1.0
	*/
	public static final String BUSINESS_GOAL_TO_PTP = "BUSINESS_GOAL_TO_PTP";
	
	/**
	 * 写入todo表的开关
	* @fields BUSINESS_GOAL_TODO_COMM
	* @author 14022-foss-songjie
	* @update 2015年5月30日 下午1:56:53
	* @version V1.0
	*/
	public static final String	BUSINESS_GOAL_TODO_COMM = 
			"BUSINESS_GOAL_TO_TAOBAOJZ:BUSINESS_GOAL_TO_TAOBAO:BUSINESS_GOAL_TO_JIAWAYUN";
	
	
	/**
	 * 推送消息给dop的开关
	* @fields BUSINESS_GOAL_DOP
	* @author 14022-foss-songjie
	* @update 2015年5月30日 下午1:56:49
	* @version V1.0
	*/
	public static final String BUSINESS_GOAL_DOP = "TAOBAOJZ:JIAWAYUN";
	
}
