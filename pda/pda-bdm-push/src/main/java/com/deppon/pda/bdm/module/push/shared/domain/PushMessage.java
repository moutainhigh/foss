package com.deppon.pda.bdm.module.push.shared.domain;

import java.io.Serializable;
import java.util.Date;

public class PushMessage implements Serializable{
	
	 /***/
	private static final long serialVersionUID = 1L;
	
	/**推送类型*/
	private int pushType;
	/**消息类型*/
	private int messageType;
	/**设备类型*/
	private int deviceType;
	/**设备ID*/
	private String deviceId;
	/**标题*/
	private String title;
	/**描述*/
	private String description;
	/**值*/
	private String value;
	/**id*/
	private String id;
	/**创建时间*/
	private Date createtime;
	/**状态 0表示初始状态，1表示读取状态，2表示成功，3表示失败*/
	private String  status;
	/**推送失败，保存失败原因*/
	private String error;
	/**
	 * @parameters：
	 * @return：
	 */
	public int getPushType() {
		return pushType;
	}
	/**
	 * @parameters：
	 * @return：
	 */
	public void setPushType(int pushType) {
		this.pushType = pushType;
	}
	/**
	 * @parameters：
	 * @return：
	 */
	public int getMessageType() {
		return messageType;
	}
	/**
	 * @parameters：
	 * @return：
	 */
	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}
	/**
	 * @parameters：
	 * @return：
	 */
	public int getDeviceType() {
		return deviceType;
	}
	/**
	 * @parameters：
	 * @return：
	 */
	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}
	/**
	 * @parameters：
	 * @return：
	 */
	public String getDeviceId() {
		return deviceId;
	}
	/**
	 * @parameters：
	 * @return：
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	/**
	 * @parameters：
	 * @return：
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @parameters：
	 * @return：
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @parameters：
	 * @return：
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @parameters：
	 * @return：
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @parameters：
	 * @return：
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @parameters：
	 * @return：
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @parameters：
	 * @return：
	 */
	public String getId() {
		return id;
	}
	/**
	 * @parameters：
	 * @return：
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @parameters：
	 * @return：
	 */
	public Date getCreatetime() {
		return createtime;
	}
	/**
	 * @parameters：
	 * @return：
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * @parameters：
	 * @return：
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @parameters：
	 * @return：
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @parameters：
	 * @return：
	 */
	public String getError() {
		return error;
	}
	/**
	 * @parameters：
	 * @return：
	 */
	public void setError(String error) {
		this.error = error;
	}
	
	 
	
}
