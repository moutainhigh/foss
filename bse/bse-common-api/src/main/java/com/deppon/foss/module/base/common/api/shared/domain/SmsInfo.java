/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.base.common.api.shared.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 短信信息实体类
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2013-6-8 下午5:58:32
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2013-6-8 下午5:58:32
 * @since
 * @version
 */
public class SmsInfo implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -6801867591199042722L;

    /**
     * 电话号码.
     */
    private String mobile;

    /**
     * 内容.
     */
    private String msgContent;

    /**
     * 发送部门.
     */
    private String sendDept;

    /**
     * 发送人.
     */
    private String sender;

    /**
     * 业务类型.
     */
    private String msgType;

    /**
     * 系统来源.
     */
    private String msgSource;

    /**
     * 唯一标识.
     */
    private String unionId;

    /**
     * 单号.
     */
    private String waybillNo;

    /**
     * 服务类型(短信、语音、短信语音).
     */
    private String serviceType;

    /**
     * 最晚发送时间.
     */
    private Timestamp latestSendTime;

    /**
     * 发送时间.
     */
    private Timestamp sendTime;

    /**
     * 获取 电话号码.
     * 
     * @return the mobile
     */
    public String getMobile() {
	return mobile;
    }

    /**
     * 设置 电话号码.
     * 
     * @param mobile
     *            the mobile to set
     */
    public void setMobile(String mobile) {
	this.mobile = mobile;
    }

    /**
     * 获取 内容.
     * 
     * @return the msgContent
     */
    public String getMsgContent() {
	return msgContent;
    }

    /**
     * 设置 内容.
     * 
     * @param msgContent
     *            the msgContent to set
     */
    public void setMsgContent(String msgContent) {
	this.msgContent = msgContent;
    }

    /**
     * 获取 发送部门.
     * 
     * @return the sendDept
     */
    public String getSendDept() {
	return sendDept;
    }

    /**
     * 设置 发送部门.
     * 
     * @param sendDept
     *            the sendDept to set
     */
    public void setSendDept(String sendDept) {
	this.sendDept = sendDept;
    }

    /**
     * 获取 发送人.
     * 
     * @return the sender
     */
    public String getSender() {
	return sender;
    }

    /**
     * 设置 发送人.
     * 
     * @param sender
     *            the sender to set
     */
    public void setSender(String sender) {
	this.sender = sender;
    }

    /**
     * 获取 业务类型.
     * 
     * @return the msgType
     */
    public String getMsgType() {
	return msgType;
    }

    /**
     * 设置 业务类型.
     * 
     * @param msgType
     *            the msgType to set
     */
    public void setMsgType(String msgType) {
	this.msgType = msgType;
    }

    /**
     * 获取 系统来源.
     * 
     * @return the msgSource
     */
    public String getMsgSource() {
	return msgSource;
    }

    /**
     * 设置 系统来源.
     * 
     * @param msgSource
     *            the msgSource to set
     */
    public void setMsgSource(String msgSource) {
	this.msgSource = msgSource;
    }

    /**
     * 获取 唯一标识.
     * 
     * @return the unionId
     */
    public String getUnionId() {
	return unionId;
    }

    /**
     * 设置 唯一标识.
     * 
     * @param unionId
     *            the unionId to set
     */
    public void setUnionId(String unionId) {
	this.unionId = unionId;
    }

    /**
     * 获取 单号.
     * 
     * @return the waybillNo
     */
    public String getWaybillNo() {
	return waybillNo;
    }

    /**
     * 设置 单号.
     * 
     * @param waybillNo
     *            the waybillNo to set
     */
    public void setWaybillNo(String waybillNo) {
	this.waybillNo = waybillNo;
    }

    /**
     * 获取 服务类型(短信、语音、短信语音).
     * 
     * @return the serviceType
     */
    public String getServiceType() {
	return serviceType;
    }

    /**
     * 设置 服务类型(短信、语音、短信语音).
     * 
     * @param serviceType
     *            the serviceType to set
     */
    public void setServiceType(String serviceType) {
	this.serviceType = serviceType;
    }

    /**
     * 获取 最晚发送时间.
     * 
     * @return the latestSendTime
     */
    public Timestamp getLatestSendTime() {
	return latestSendTime;
    }

    /**
     * 设置 最晚发送时间.
     * 
     * @param latestSendTime
     *            the latestSendTime to set
     */
    public void setLatestSendTime(Timestamp latestSendTime) {
	this.latestSendTime = latestSendTime;
    }

    /**
     * 获取 发送时间.
     * 
     * @return the sendTime
     */
    public Timestamp getSendTime() {
	return sendTime;
    }

    /**
     * 设置 发送时间.
     * 
     * @param sendTime
     *            the sendTime to set
     */
    public void setSendTime(Timestamp sendTime) {
	this.sendTime = sendTime;
    }

}
