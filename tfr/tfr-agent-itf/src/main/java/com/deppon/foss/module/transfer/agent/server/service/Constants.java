package com.deppon.foss.module.transfer.agent.server.service;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	/** 消息头 响应结果状态 成功 */
	public static final String RESULT_SUCCESS = "1";
	/** 消息头 响应结果状态 失败*/
	public static final String RESULT_FAILURE = "0";
	/** 消息体 异常类型 系统异常*/
	public static final String BODY_EXCEPTION_TYPE_SYS = "SYS";
	/** 消息体 异常类型 业务异常*/
	public static final String BODY_EXCEPTION_TYPE_APP = "APP";
	/** 空字符串 */
	public static final String BLANK_STRING = "";
	/** 正常签收 */
	public static final String SIGN_STATUS_ALL = "1";
	/** 部分签收 */
	public static final String SIGN_STATUS_PARTIAL = "3";
	/** 拒签 */
	public static final String SIGN_STATUS_REFUSE = "4";
	
	/**货物轨迹操作类型编码*/
	public static final String LDP_TRACK_OPERATION_TYPE_DEFUSE_SIGN = "99";
	
	public static Map<String,String> signExceptionDescribeMap = new HashMap<String, String>();
	
	static {
		signExceptionDescribeMap.put("DRIVER_LATE", "司机晚送");
		signExceptionDescribeMap.put("PAY_QUESTION", "付款问题");
		signExceptionDescribeMap.put("UNLOAD", "无法卸货");
		signExceptionDescribeMap.put("CAR_FORBID", "区域车辆禁行");
		signExceptionDescribeMap.put("BILL_QUESTION", "发票问题拒收");
		signExceptionDescribeMap.put("CAR_BREAK", "车辆故障/事故");
		signExceptionDescribeMap.put("CUSTOM_CHANGE_ADDRESS", "客户要求更改地址");
		signExceptionDescribeMap.put("CUSTOM_NOTIN", "客户不在无人收货");
		signExceptionDescribeMap.put("LINE_WRONG", "排单有误，线路安排不对");
		signExceptionDescribeMap.put("PHONE_WRONG", "开单地址有误/电话录入错误");
		signExceptionDescribeMap.put("GOODS_BREAK", "因货物破损/件数不符等原因客户拒收");
		signExceptionDescribeMap.put("CONSULT_UNSUCCESS", "客户要求开箱验货/送货上楼协商未果");
		signExceptionDescribeMap.put("OTHER", "其他");
	}
	/**韩国件*/
	public static final String INTERNATIONAL_TRACK_KOREA = "1";
	/**其他国际件*/
	public static final String INTERNATIONAL_TRACK_OTHER = "2";
	/**签收*/
	public static final String INTERNATIONAL_TRACK_SIGNED = "14";
}
