/**   
* @Title: PushService.java 
* @Package com.deppon.pda.bdm.module.push.server.service.impl 
* @Description: 
* @author 183272
* @date 2014年10月31日 上午11:10:00 
* @version V1.0   
*/
package com.deppon.pda.bdm.module.push.server.service.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.PushBroadcastMessageRequest;
import com.baidu.yun.channel.model.PushBroadcastMessageResponse;
import com.baidu.yun.channel.model.PushUnicastMessageRequest;
import com.baidu.yun.channel.model.PushUnicastMessageResponse;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.deppon.pda.bdm.module.push.server.dao.IPushDao;
import com.deppon.pda.bdm.module.push.server.service.IPushService;
import com.deppon.pda.bdm.module.push.shared.domain.Constant;
import com.deppon.pda.bdm.module.push.shared.domain.PushMessage;

/** 
 * @ClassName: PushService 
 * @Description: 
 * @author 183272
 * @date 2014年10月31日 上午11:10:00 
 *  
 */
public class PushService implements IPushService{
	
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
	* @param @param deviceId  channleid|userid
	* @param @param title
	* @param @param description
	* @param @param value
	* @param @return    设定文件 
	* @throws
	 */
	
	private Logger log = Logger.getLogger(getClass());
	private IPushDao pushDao;
	
	@Override
	public String pushMessage(int pushType,int messageType,int deviceType,String deviceId,String title,String description,String value) {
		//封装推送信息
		PushMessage pushMessage = new PushMessage();
		pushMessage.setId(UUID.randomUUID().toString());
		pushMessage.setCreatetime(new Date());
		pushMessage.setPushType(pushType);
		pushMessage.setMessageType(messageType);
		pushMessage.setDeviceType(deviceType);
		pushMessage.setDeviceId(deviceId);
		pushMessage.setTitle(title);
		pushMessage.setDescription(description);
		pushMessage.setValue(value);
		try {
			//推送到百度云
			this.pushMessageToBaiduYun(
					pushType, 
					messageType,
					deviceType,
					deviceId,
					title,
					description,
					value);
			//1、推送成功，状态为1
			pushMessage.setStatus("1");
			//2、保存推送信息
			pushDao.savePushMessage(pushMessage);
		} catch (Exception e) {
			log.info(e);
			//1、推送失败，状态为2
			pushMessage.setStatus("2");
			//2、设置失败原因
			pushMessage.setError(e.toString());
			//3、保存推送信息
			pushDao.savePushMessage(pushMessage);
		}
		return null;
	}

	private void pushMessageToBaiduYun(int pushType,int messageType,int deviceType,String deviceId,String title,String description,String value){
		value = value.replaceAll("\\\"", "\\\\\\\"");
		
		
		if(deviceId==null||"".equals(deviceId)){
			return;
		}
		String[] deviceIds = deviceId.split("\\|");
		Long channel_id = Long.parseLong(deviceIds[0]);
		String user_id = deviceIds[1];
		
		 // 1. 设置developer平台的ApiKey/SecretKey
		ChannelKeyPair pair = new ChannelKeyPair(Constant.PUSH_BAIDU_API_KEY, Constant.PUSH_BAIDU_SECRET_KEY);
		
		 // 2. 创建BaiduChannelClient对象实例
		BaiduChannelClient channelClient = new BaiduChannelClient(pair);
		
        // 3. 若要了解交互细节，请注册YunLogHandler类
		channelClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
//				System.out.println(event.getMessage());
			}
		});
		String message[] = new String[2];
		try {
			switch (pushType) {
			case 1:
				//向单人发送消息
				PushUnicastMessageRequest request = new PushUnicastMessageRequest();
				request.setChannelId(channel_id);
				request.setUserId(user_id);
				
					message[0] = value;
					message[1] = "{\"title\":\""+title+"\",\"open_type\":2,\"pkg_content\":\"#Intent;component=com.express.accept.printlist/.AcctFinishActivity;end\",\"description\":\""+description+"\",\"custom_content\":{\"json\":\""+value+"\"}}";
					request.setDeviceType(deviceType);
				for (int i = 0; i <messageType+1 ; i++) {
					request.setMessage(message[i]);
					request.setMessageType(i);
					PushUnicastMessageResponse response = channelClient.pushUnicastMessage(request);
//					System.out.println(response.getSuccessAmount());
				}
		
				break;
			case 2:
				//一群人，必须指定 tag
				break;
			case 3:
				  // 4. 创建请求类对象
	            PushBroadcastMessageRequest broadcastRequest = new PushBroadcastMessageRequest();
	            broadcastRequest.setDeviceType(deviceType); 
	            broadcastRequest.setMessageType(messageType);
	    		message[0] = value;
	    		message[1] = "{\"title\":\""+title+"\",\"open_type\":2,\"pkg_content\":\"#Intent;component=com.express/com.baidu.push.example.PrintLabelListActivity;end\",\"description\":\""+description+"\",\"custom_content\":{\"json\":\""+value+"\"}}";
				broadcastRequest.setDeviceType(deviceType);
				for (int i = 0; i <messageType+1 ; i++) {
					broadcastRequest.setMessage(message[i]);
					broadcastRequest.setMessageType(i);
					PushBroadcastMessageResponse response = channelClient.pushBroadcastMessage(broadcastRequest);
//					System.out.println(response.getSuccessAmount());
				}
			default:
				break;
			}
		} catch (ChannelClientException e) {
			e.printStackTrace();
		} catch (ChannelServerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @parameters：
	 * @return：
	 */
	public void setPushDao(IPushDao pushDao) {
		this.pushDao = pushDao;
	}
	
}
