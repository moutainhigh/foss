
/**   
 * @title SmsInfo.java
 * @package com.deppon.ump.module.Interface.shared.domain
 * @description 
 * @author cbb   
 * @update 2012-10-20 下午5:43:30
 * @version V1.0   
 */
 
package com.deppon.foss.module.base.baseinfo.api.shared.define;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;


/**
 * @description 
 * @version 1.0
 * @author cbb
 * @update 2012-10-20 下午5:43:30 
 */

public class SmsInfo implements Serializable{

	
	/** 
	 * @fields serialVersionUID 
	 */ 
	
	private static final long serialVersionUID = 502553723350839131L;
	//电话号码
	private String mobile;
	//内容
	private String msgContent;
	//发送部门
	private String sendDept;
	//发送人
	private String sender;
	//业务类型
	private String msgType;
	//系统来源
	private String msgSource;
	//唯一标识
	private String unionId;
	//单号
	private String waybillNo;
	//服务类型(1、短信、2、语音、3、短信语音)
	private String serviceType;
	//最晚发送时间
	private Timestamp latestSendTime;
	//发送时间
	private Timestamp sendTime;
	//标准是否重复发送的状态(0 未重复发送，1重复发送)
	private int repeatState;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getSendDept() {
		return sendDept;
	}

	public void setSendDept(String sendDept) {
		this.sendDept = sendDept;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMsgSource() {
		return msgSource;
	}

	public void setMsgSource(String msgSource) {
		this.msgSource = msgSource;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceType() {
		return serviceType;
	}
	@JsonDeserialize(using = TimestampDeserializer.class)
	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}
	@XmlJavaTypeAdapter(TimestampAdapter.class)
	@JsonSerialize(using = TimestampSerializer.class)	
	public Timestamp getSendTime() {
		return sendTime;
	}
	@JsonDeserialize(using = TimestampDeserializer.class)
	public void setLatestSendTime(Timestamp latestSendTime) {
		this.latestSendTime = latestSendTime;
	}
	@XmlJavaTypeAdapter(TimestampAdapter.class)
	@JsonSerialize(using = TimestampSerializer.class)	
	public Timestamp getLatestSendTime() {
		return latestSendTime;
	}

	public int getRepeatState() {
		return repeatState;
	}

	public void setRepeatState(int repeatState) {
		this.repeatState = repeatState;
	}
}
