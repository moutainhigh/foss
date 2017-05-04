package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 财务变更消息实体
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-12-3 上午11:35:21
 * @since
 * @version
 */
public class WaybillChangeMsgEntity extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 4950842232238100964L;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 消息发生日期
	 */
	private Date msgDate;

	/**
	 * 消息动作
	 */
	private String msgAction;

	/**
	 * 来源单据类型
	 */
	private String sourceBillType;

	/**
	 * 来源单据号
	 */
	private String sourceBillNo;

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
	 * @return sourceBillType
	 */
	public String getSourceBillType() {
		return sourceBillType;
	}

	/**
	 * @param sourceBillType
	 */
	public void setSourceBillType(String sourceBillType) {
		this.sourceBillType = sourceBillType;
	}

	/**
	 * @return sourceBillNo
	 */
	public String getSourceBillNo() {
		return sourceBillNo;
	}

	/**
	 * @param sourceBillNo
	 */
	public void setSourceBillNo(String sourceBillNo) {
		this.sourceBillNo = sourceBillNo;
	}

}
