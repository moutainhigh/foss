package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
/**
 * 大客户系统需要的签收明细数据实体
 * @author 269871
 *
 */
public class SignDetailResultDto  implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  运单号
	 */
	private String waybillNo;
	/**
	 * 签收人
	 */
	private String signName;
	/**
	 * 签收时间
	 */
	private Date signDate;
	/**
	 * 签收备注
	 */
	private String signNote;
	/**
	 * 签收状态
	 */
	private String signStatus;
	public String getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getSignName() {
		return signName;
	}
	public void setSignName(String signName) {
		this.signName = signName;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public String getSignNote() {
		return signNote;
	}
	public void setSignNote(String signNote) {
		this.signNote = signNote;
	}
}
