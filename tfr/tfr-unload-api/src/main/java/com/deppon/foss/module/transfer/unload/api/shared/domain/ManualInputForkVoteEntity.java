package com.deppon.foss.module.transfer.unload.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * @author 042770
 *
 */
public class ManualInputForkVoteEntity extends BaseEntity {

	private static final long serialVersionUID = 2143766061899308755L;

	private String transferCenterCode;

	private String transferCenterName;
	
	private Integer vote;

	private String note;

	private Date inputDate;

	private String inputMonth;

	private Date createTime;

	private String createUserCode;

	private String createUserName;

	private Date modifyTime;

	private String modifyUserCode;

	private String modifyUserName;

	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	public String getTransferCenterName() {
		return transferCenterName;
	}

	public void setTransferCenterName(String transferCenterName) {
		this.transferCenterName = transferCenterName;
	}

	public Integer getVote() {
		return vote;
	}

	public void setVote(Integer vote) {
		this.vote = vote;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public String getInputMonth() {
		return inputMonth;
	}

	public void setInputMonth(String inputMonth) {
		this.inputMonth = inputMonth;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
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

	@Override
	public String toString() {
		return "ManualInputForkVoteEntity [id=" + super.getId() + ", transferCenterCode="
				+ transferCenterCode + ", transferCenterName="
				+ transferCenterName + ", vote=" + vote + ", note=" + note
				+ ", inputDate=" + inputDate + ", inputMonth=" + inputMonth
				+ ", createTime=" + createTime + ", createUserCode="
				+ createUserCode + ", createUserName=" + createUserName
				+ ", modifyTime=" + modifyTime + ", modifyUserCode="
				+ modifyUserCode + ", modifyUserName=" + modifyUserName + "]";
	}
	
}
