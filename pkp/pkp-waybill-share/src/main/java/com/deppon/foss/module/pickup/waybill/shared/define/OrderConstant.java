package com.deppon.foss.module.pickup.waybill.shared.define;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;


/****
 * 记录短信相关的一些常量
 * 
 * @author camelot-foss
 * @data 2014年7月9日 下午4:43:08
 */
public class OrderConstant {

	/**
	 * 发件人短信批量发送:SENDER_SEND_SMS_BAUTH 发件人短信停发:SENDER_MESSAGE_STOP
	 * 收件人短信停发:RECEIVER_MESSAGE_STOP
	 **/
	public static final String[] sendStates = { "SENDER_SEND_SMS_BAUTH",
			"SENDER_MESSAGE_STOP", "RECEIVER_MESSAGE_STOP" };

	/* 发件人短信批量发送 The sender sent SMS in bulk */
	public static final String SENDER_SEND_SMS_BAUTH = "SENDER_SEND_SMS_BAUTH";

	/* 发件人短信停发 The sender's message of hair */
	public static final String SENDER_MESSAGE_STOP = "SENDER_MESSAGE_STOP";

	/* 收件人短信停发 The recipient SMS stop hair */
	public static final String RECEIVER_MESSAGE_STOP = "RECEIVER_MESSAGE_STOP";

	/* 定义两种状态 选中状态0 */
	public static final String CHOOSE_YES = "1";

	/* 定义两种状态 没有选中状态1 */
	public static final String CHOOSE_NO = "0";

	/* 如果crm那边传递给我的值格式不对或者是错误的数据的话 我设置一个默认的值 */
	public static final String DEFAULT_STATE_VALUE = "000";

	/* 签收单提醒的默认值 To sign for the single back to the text */
	public static final String SIGN_BACK_FAIL_DEFAULE = "不需要发送签收单短信";

	/* 描述crm端传递过来的状态码所给格式是否是正确的  */
	public static final String CRM_STATE_ERROR="状态码格式有误";
	/**
	 * 把对应的状态值 拼装成map的形式
	 * 
	 * @author camelot-foss
	 * @data 2014年7月9日 下午4:30:16
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getStateValue(String stateDatas) {

		Map<String, String> rstMap = new HashMap<String, String>();
		if (StringUtils.isEmpty(stateDatas)) {
			return MapUtils.EMPTY_MAP;
		} else {
			char[] stateCharArray = stateDatas.toCharArray();
			for (int i = 0; i < stateCharArray.length; i++) {
				rstMap.put(OrderConstant.sendStates[i],
						Character.toString(stateCharArray[i]));
			}
		}
		return rstMap;
	}
	
	//需求更改
	/* 发件人短信停发  **/
	public static final String STOP_MESSAGE_FOR_DELIVER="STOP_MESSAGE_FOR_DELIVER"; 
	/* 发件人短信批量发送 **/
	public static final String BATCH_MESSAGE_FOR_DELIVER="BATCH_MESSAGE_FOR_DELIVER";
	
	// 客户作为收件人短信停发
	public static final String STOP_MESSAGE_FOR_RECEIPT = "STOP_MESSAGE_FOR_RECEIPT";
	// 客户的收件人短信停发
	public static final String STOP_MESSAGE_FOR_CUST_RECEIPT = "STOP_MESSAGE_FOR_CUST_RECEIPT";
	// 两者都停发
	public static final String STOP_MESSAGE_FOR_DOUBLE = "STOP_MESSAGE_FOR_DOUBLE";
	

}
