package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 批量更新自动付款付款单DTO
 * @author FOSS-302346-Jiang Xun
 * @date 2016-06-03 上午08:20:00
 */
public class BillPmtAutoPayDto implements Serializable{
	
	/**
	 * 实体序列号
	 */
	private static final long serialVersionUID = 6561300574956846174L;
	/**
	 * 付款单号集合
	 */
	private List<String> paymentNoList;
	/**
	 * 汇款状态
	 */
	private String remitStatus;
	
	/**
	 * 修改人工号
	 */
	private String modifyUserCode;

	/**
	 * 修改人姓名
	 */
	private String modifyUserName;

	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	/**
	 * 是否有效
	 */
	private String active;
	
	/**
	 * 作废处理意见
	 */
	private String disableOpinion;

	/**
	 * 合伙人到付运费自动付款推送次数
	 */
	private BigDecimal autoSendCount;
	/**
	 * 单据类型
	 */
	private String paymentType;
	
	public List<String> getPaymentNoList() {
		return paymentNoList;
	}

	public void setPaymentNoList(List<String> paymentNoList) {
		this.paymentNoList = paymentNoList;
	}

	public String getRemitStatus() {
		return remitStatus;
	}

	public void setRemitStatus(String remitStatus) {
		this.remitStatus = remitStatus;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDisableOpinion() {
		return disableOpinion;
	}

	public void setDisableOpinion(String disableOpinion) {
		this.disableOpinion = disableOpinion;
	}

	public BigDecimal getAutoSendCount() {
		return autoSendCount;
	}

	public void setAutoSendCount(BigDecimal autoSendCount) {
		this.autoSendCount = autoSendCount;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

}
