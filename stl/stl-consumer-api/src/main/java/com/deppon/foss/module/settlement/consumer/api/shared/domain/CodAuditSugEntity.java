package com.deppon.foss.module.settlement.consumer.api.shared.domain;

import java.util.Date;
import java.util.List;

public class CodAuditSugEntity {
	/**
	 * ID
	 */
	private String id;      			
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 运单号集合
	 */
	private List<String> waybillNos;
	/**
	 * 资金部审核意见
	 */
	private String auditFundsug;
	/**
	 * 复合会计组审核意见
	 */
	private String auditReviewAuditsug;
	/**
	 * 是否有效状态
	 */
	private String active;
	/**
	 * 资金部审核意见时上传的附件地址
	 */
	private String funAttachment;
	/**
	 * 复合会计组审核意见上传的附件地址
	 */
	private String reviewauditAttachment;
	/**
	 * 创建人
	 */
	private String createUser;
	/**
	 * 修改人
	 */
	private String modifyUser;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date modify_time;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getAuditFundsug() {
		return auditFundsug;
	}
	public void setAuditFundsug(String auditFundsug) {
		this.auditFundsug = auditFundsug;
	}
	public String getAuditReviewAuditsug() {
		return auditReviewAuditsug;
	}
	public void setAuditReviewAuditsug(String auditReviewAuditsug) {
		this.auditReviewAuditsug = auditReviewAuditsug;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getFunAttachment() {
		return funAttachment;
	}
	public void setFunAttachment(String funAttachment) {
		this.funAttachment = funAttachment;
	}
	public String getReviewauditAttachment() {
		return reviewauditAttachment;
	}
	public void setReviewauditAttachment(String reviewauditAttachment) {
		this.reviewauditAttachment = reviewauditAttachment;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModify_time() {
		return modify_time;
	}
	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}
	public List<String> getWaybillNos() {
		return waybillNos;
	}
	public void setWaybillNos(List<String> waybillNos) {
		this.waybillNos = waybillNos;
	}
}
