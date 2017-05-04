package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 理赔支付状态消息
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-12-3 上午11:30:52
 * @since
 * @version
 */
public class ClaimStatusMsgEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8195201122293880313L;

	/**
	 * ID
	 */
	private String id;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 消息发生日期
	 */
	private Date msgDate;

	/**
	 * 消息动作P/F(Pass/Fail)
	 */
	private String msgAction;

	/**
	 * 消息正文
	 */
	private String msgContent;

	/**
	 * 消息状态
	 */
	private String msgStatus;

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return msgDate
	 */
	public Date getMsgDate() {
		return msgDate;
	}

	/**
	 * @param msgDate
	 */
	public void setMsgDate(Date msgDate) {
		this.msgDate = msgDate;
	}

	/**
	 * @return msgAction
	 */
	public String getMsgAction() {
		return msgAction;
	}

	/**
	 * @param msgAction
	 */
	public void setMsgAction(String msgAction) {
		this.msgAction = msgAction;
	}

	/**
	 * @return msgContent
	 */
	public String getMsgContent() {
		return msgContent;
	}

	/**
	 * @param msgContent
	 */
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	/**
	 * @return msgStatus
	 */
	public String getMsgStatus() {
		return msgStatus;
	}

	/**
	 * @param msgStatus
	 */
	public void setMsgStatus(String msgStatus) {
		this.msgStatus = msgStatus;
	}

}
