package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

public class TfrCtrStaffNoDutyEntity implements Serializable {

	private static final long serialVersionUID = 7721107741907996224L;

	/**
	 * 查询开始时间； 需求定义用前一天的6:00到当天的6:00到，卸车员是否有货量、叉车司机是否有叉车票来判断前一天是否出勤;
	 * 而此处的beginQueryDate就为前一天的6:00
	 */
	private Date beginQueryDate;

	/**
	 * 查询结束时间 此处的endQueryDate就为当天的6:00
	 */
	private Date endQueryDate;

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 装卸车员(包括电叉司机)编码
	 */
	private String empCode;

	/**
	 * 装卸车员(包括电叉司机)名称
	 */
	private String empName;

	/**
	 * 岗位code
	 */
	private String postCode;

	/**
	 * 岗位名称
	 */
	private String postName;

	/**
	 * 员工所属部门编码
	 */
	private String orgCode;

	/**
	 * 员工所属部门名称
	 */
	private String orgName;

	/**
	 * 员工所属外场编码
	 */
	private String transferCenterCode;

	/**
	 * 员工所属外场名称
	 */
	private String transferCenterName;

	/**
	 * 统计日期 yyyy-MM-dd
	 */
	private Date statisticDate;

	/**
	 * 统计月份 yyyy-MM
	 */
	private String statisticMonth;

	public Date getBeginQueryDate() {
		return beginQueryDate;
	}

	public void setBeginQueryDate(Date beginQueryDate) {
		this.beginQueryDate = beginQueryDate;
	}

	public Date getEndQueryDate() {
		return endQueryDate;
	}

	public void setEndQueryDate(Date endQueryDate) {
		this.endQueryDate = endQueryDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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

	public Date getStatisticDate() {
		return statisticDate;
	}

	public void setStatisticDate(Date statisticDate) {
		this.statisticDate = statisticDate;
	}

	public String getStatisticMonth() {
		return statisticMonth;
	}

	public void setStatisticMonth(String statisticMonth) {
		this.statisticMonth = statisticMonth;
	}

	@Override
	public String toString() {
		return "TfrCtrStaffNoDutyEntity [beginQueryDate=" + beginQueryDate
				+ ", endQueryDate=" + endQueryDate + ", id=" + id
				+ ", empCode=" + empCode + ", empName=" + empName
				+ ", postCode=" + postCode + ", postName=" + postName
				+ ", orgCode=" + orgCode + ", orgName=" + orgName
				+ ", transferCenterCode=" + transferCenterCode
				+ ", transferCenterName=" + transferCenterName
				+ ", statisticDate="
				+ String.format("%1$tF %2$tT", statisticDate, statisticDate)
				+ ", statisticMonth=" + statisticMonth + "]";
	}

}
