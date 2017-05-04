package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 外场人员异常信息
 * @author Ouyang
 */
public class TfrCtrAbsenteeInfoEntity extends BaseEntity {

	private static final long serialVersionUID = 8454527099837720185L;

	/**
	 * 异常人员编码
	 */
	private String absenteeCode;

	/**
	 * 异常人员名称
	 */
	private String absenteeName;

	/**
	 * 异常人员职位编码
	 */
	private String absenteePostCode;
	/**
	 * 异常人员职位名称
	 */
	private String absenteePostName;

	/**
	 * 异常人员状态 RESIGNATION("自离"),ABSENTEEISM("旷工"),
	 * INDUSTRIAL_INJURY("工伤"),LONG_HOLIDAYS("长假");
	 */
	private String absenteeStatus;

	/**
	 * 异常人员入职日期
	 */
	private Date absenteeEntryDate;

	/**
	 * 异常开始时间
	 */
	private Date absentBeginDate;

	/**
	 * 异常结束时间
	 */
	private Date absentEndDate;

	/**
	 * 异常人员所在部门编码
	 */
	private String absenteeOrgCode;

	/**
	 * 异常人员所在部门名称
	 */
	private String absenteeOrgName;

	/**
	 * 异常人员所在外场编码
	 */
	private String transferCenterCode;

	/**
	 * 异常人员所在外场名称
	 */
	private String transferCenterName;

	/**
	 * 创建人名称
	 */
	private String createUserName;

	/**
	 * 创建人编码
	 */
	private String modifyUserName;
	
	

	public String getAbsenteeCode() {
		return absenteeCode;
	}

	public void setAbsenteeCode(String absenteeCode) {
		this.absenteeCode = absenteeCode;
	}

	public String getAbsenteeName() {
		return absenteeName;
	}

	public void setAbsenteeName(String absenteeName) {
		this.absenteeName = absenteeName;
	}

	public String getAbsenteePostName() {
		return absenteePostName;
	}

	public void setAbsenteePostName(String absenteePostName) {
		this.absenteePostName = absenteePostName;
	}

	public String getAbsenteeStatus() {
		return absenteeStatus;
	}

	public void setAbsenteeStatus(String absenteeStatus) {
		this.absenteeStatus = absenteeStatus;
	}

	public Date getAbsenteeEntryDate() {
		return absenteeEntryDate;
	}

	public void setAbsenteeEntryDate(Date absenteeEntryDate) {
		this.absenteeEntryDate = absenteeEntryDate;
	}

	public Date getAbsentBeginDate() {
		return absentBeginDate;
	}

	public void setAbsentBeginDate(Date absentBeginDate) {
		this.absentBeginDate = absentBeginDate;
	}

	public Date getAbsentEndDate() {
		return absentEndDate;
	}

	public void setAbsentEndDate(Date absentEndDate) {
		this.absentEndDate = absentEndDate;
	}

	public String getAbsenteeOrgCode() {
		return absenteeOrgCode;
	}

	public void setAbsenteeOrgCode(String absenteeOrgCode) {
		this.absenteeOrgCode = absenteeOrgCode;
	}

	public String getAbsenteeOrgName() {
		return absenteeOrgName;
	}

	public void setAbsenteeOrgName(String absenteeOrgName) {
		this.absenteeOrgName = absenteeOrgName;
	}

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

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getAbsenteePostCode() {
		return absenteePostCode;
	}

	public void setAbsenteePostCode(String absenteePostCode) {
		this.absenteePostCode = absenteePostCode;
	}

	
	@Override
	public String toString() {
		return "TfrCtrAbsenteeInfoEntity [absenteeCode=" + absenteeCode
				+ ", absenteeName=" + absenteeName + ", absenteePostCode="
				+ absenteePostCode + ", absenteePostName=" + absenteePostName
				+ ", absenteeStatus=" + absenteeStatus + ", absenteeEntryDate="
				+ absenteeEntryDate + ", absentBeginDate=" + absentBeginDate
				+ ", absentEndDate=" + absentEndDate + ", absenteeOrgCode="
				+ absenteeOrgCode + ", absenteeOrgName=" + absenteeOrgName
				+ ", transferCenterCode=" + transferCenterCode
				+ ", transferCenterName=" + transferCenterName
				+ ", createUserName=" + createUserName + ", modifyUserName="
				+ modifyUserName + "]";
	}

}
