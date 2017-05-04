/**   
* @Title: RecivePushService.java 
* @Package com.deppon.pda.bdm.module.push.server.service.impl 
* @Description: 
* @author 183272
* @date 2014年10月31日 上午9:39:28 
* @version V1.0   
*/
package com.deppon.pda.bdm.module.push.server.service;

/** 
 * @ClassName: RecivePushService 
 * @Description: 
 * @author 183272
 * @date 2014年10月31日 上午9:39:28 
 *  
 */
public interface IPushService {
	/**
	 * 
	* @Title: pushMessage 
	* @Description: 
	* @author 183272
	* @date 2014年10月31日 下午2:59:26 
	* @param @param pushType  推送类型，取值范围为：1～3
												1：单个人，必须指定user_id 和 channel_id （指定用户的指定设备）或者user_id（指定用户的所有设备）
												2：一群人，必须指定 tag
												3：所有人，无需指定tag、user_id、channel_id
	* @param @param messageType 消息类型
												0：消息（透传给应用的消息体）
												1：通知（对应设备上的消息通知）
												默认值为0。
	* @param @param deviceType设备类型，取值范围为：1～5
												云推送支持多种设备，各种设备的类型编号如下：
												1：浏览器设备；
												2：PC设备；
												3：Andriod设备；
												4：iOS设备；
												5：Windows Phone设备；
												如果存在此字段，则向指定的设备类型推送消息。 默认为android设备类型。
	* @param @param deviceId channleid|userid
	* @param @param title
	* @param @param description
	* @param @param value
	* @param @return    设定文件 
	* @throws
	 */
		public String pushMessage(int pushType,int messageType,int deviceType,String deviceId,String title,String description,String value);
}
