package com.deppon.foss.module.settlement.consumer.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 小票单据明细
 * @author 101911-foss-zhouChunlai
 * @date 2012-10-10 下午4:37:13
 */
public class NoteDetailsEntity extends BaseEntity {

	private static final long serialVersionUID = -1169640526908987732L;


	/**
	 * 小票单据明细单号
	 */
	private String noteDetailsNo;

	/**
	 * 小票单据申请记录ID
	 */
	private String noteAppId;

	/**
	 * 下发单号
	 */
	private String noteStockinId;

	/**
	 * 业务日期
	 */
	private Date businessDate;

	/**
	 * 使用时间
	 */
	private Date useTime;

	/**
	 * 使用人编码
	 */
	private String userCode;

	/**
	 * 使用人名称
	 */
	private String userName;

	/**
	 * 使用部门编码
	 */
	private String useOrgCode;

	/**
	 * 使用部门名称
	 */
	private String useOrgName;

	/**
	 * 使用的小票单据类型
	 */
	private String notereqType;

	/**
	 * 单据状态
	 */
	private String status;

	/**
	 * 核销状态
	 */
	private String writeoffStatus;

	/**
	 * 核销时间
	 */
	private Date writeoffTime;

	/**
	 * 是否有效
	 */
	private String active;


	/**
	 * @return noteDetailsNo
	 */
	public String getNoteDetailsNo() {
		return noteDetailsNo;
	}

	/**
	 * @param  noteDetailsNo  
	 */
	public void setNoteDetailsNo(String noteDetailsNo) {
		this.noteDetailsNo = noteDetailsNo;
	}

	/**
	 * @return noteAppId
	 */
	public String getNoteAppId() {
		return noteAppId;
	}

	/**
	 * @param  noteAppId  
	 */
	public void setNoteAppId(String noteAppId) {
		this.noteAppId = noteAppId;
	}

	/**
	 * @return noteStockinId
	 */
	public String getNoteStockinId() {
		return noteStockinId;
	}

	/**
	 * @param  noteStockinId  
	 */
	public void setNoteStockinId(String noteStockinId) {
		this.noteStockinId = noteStockinId;
	}

	/**
	 * @return businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param  businessDate  
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * @return useTime
	 */
	public Date getUseTime() {
		return useTime;
	}

	/**
	 * @param  useTime  
	 */
	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	/**
	 * @return userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param  userCode  
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param  userName  
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return useOrgCode
	 */
	public String getUseOrgCode() {
		return useOrgCode;
	}

	/**
	 * @param  useOrgCode  
	 */
	public void setUseOrgCode(String useOrgCode) {
		this.useOrgCode = useOrgCode;
	}

	/**
	 * @return useOrgName
	 */
	public String getUseOrgName() {
		return useOrgName;
	}

	/**
	 * @param  useOrgName  
	 */
	public void setUseOrgName(String useOrgName) {
		this.useOrgName = useOrgName;
	}

	/**
	 * @return notereqType
	 */
	public String getNotereqType() {
		return notereqType;
	}

	/**
	 * @param  notereqType  
	 */
	public void setNotereqType(String notereqType) {
		this.notereqType = notereqType;
	}

	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param  status  
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return writeoffStatus
	 */
	public String getWriteoffStatus() {
		return writeoffStatus;
	}

	/**
	 * @param  writeoffStatus  
	 */
	public void setWriteoffStatus(String writeoffStatus) {
		this.writeoffStatus = writeoffStatus;
	}

	/**
	 * @return writeoffTime
	 */
	public Date getWriteoffTime() {
		return writeoffTime;
	}

	/**
	 * @param  writeoffTime  
	 */
	public void setWriteoffTime(Date writeoffTime) {
		this.writeoffTime = writeoffTime;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param  active  
	 */
	public void setActive(String active) {
		this.active = active;
	}

	
}
