package com.deppon.esb.pojo.domain.foss2sms;


import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The Class SmsInfo.
 */
public class SmsInfo implements Serializable {

	/** serialVersionUID:TODO（用一句话描述这个变量表示什么）. @since 1.0.0 */
	private static final long serialVersionUID = 1261948203532871619L;
	//电话号码
	/** The mobile. */
	private String mobile;
	//内容
	/** The msg content. */
	private String msgContent;
	//发送部门
	/** The send dept. */
	private String sendDept;
	//发送人
	/** The sender. */
	private String sender;
	//业务类型
	/** The msg type. */
	private String msgType;
	//系统来源
	/** The msg source. */
	private String msgSource;
	//唯一标识
	/** The union id. */
	private String unionId;
	//单号
	/** The waybill no. */
	private String waybillNo;
	//服务类型(短信、语音、短信语音)
	/** The service type. */
	private String serviceType;
	//最晚发送时间
	/** The latest send time. */
	private Timestamp latestSendTime;
	//发送时间
	/** The send time. */
	private Timestamp sendTime;
	
	

	/**
	 * Gets the mobile.
	 * 
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * Sets the mobile.
	 * 
	 * @param mobile
	 *            the new mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * Gets the msg content.
	 * 
	 * @return the msg content
	 */
	public String getMsgContent() {
		return msgContent;
	}

	/**
	 * Sets the msg content.
	 * 
	 * @param msgContent
	 *            the new msg content
	 */
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	/**
	 * Gets the send dept.
	 * 
	 * @return the send dept
	 */
	public String getSendDept() {
		return sendDept;
	}

	/**
	 * Sets the send dept.
	 * 
	 * @param sendDept
	 *            the new send dept
	 */
	public void setSendDept(String sendDept) {
		this.sendDept = sendDept;
	}

	/**
	 * Gets the sender.
	 * 
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * Sets the sender.
	 * 
	 * @param sender
	 *            the new sender
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * Gets the msg type.
	 * 
	 * @return the msg type
	 */
	public String getMsgType() {
		return msgType;
	}

	/**
	 * Sets the msg type.
	 * 
	 * @param msgType
	 *            the new msg type
	 */
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	/**
	 * Gets the msg source.
	 * 
	 * @return the msg source
	 */
	public String getMsgSource() {
		return msgSource;
	}

	/**
	 * Sets the msg source.
	 * 
	 * @param msgSource
	 *            the new msg source
	 */
	public void setMsgSource(String msgSource) {
		this.msgSource = msgSource;
	}

	/**
	 * Sets the union id.
	 * 
	 * @param unionId
	 *            the new union id
	 */
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	/**
	 * Gets the union id.
	 * 
	 * @return the union id
	 */
	public String getUnionId() {
		return unionId;
	}

	/**
	 * Sets the waybill no.
	 * 
	 * @param waybillNo
	 *            the new waybill no
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * Gets the waybill no.
	 * 
	 * @return the waybill no
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * Sets the service type.
	 * 
	 * @param serviceType
	 *            the new service type
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * Gets the service type.
	 * 
	 * @return the service type
	 */
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * Sets the latest send time.
	 * 
	 * @param latestSendTime
	 *            the new latest send time
	 */
	public void setLatestSendTime(Timestamp latestSendTime) {
		this.latestSendTime = latestSendTime;
	}

	/**
	 * Gets the latest send time.
	 * 
	 * @return the latest send time
	 */
	public Timestamp getLatestSendTime() {
		return latestSendTime;
	}

	/**
	 * Sets the send time.
	 * 
	 * @param sendTime
	 *            the new send time
	 */
	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * Gets the send time.
	 * 
	 * @return the send time
	 */
	public Timestamp getSendTime() {
		return sendTime;
	}
}
