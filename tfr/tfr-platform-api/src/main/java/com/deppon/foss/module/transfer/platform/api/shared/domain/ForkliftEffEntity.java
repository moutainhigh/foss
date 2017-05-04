package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class ForkliftEffEntity extends BaseEntity {
	
	/**经营本部编码*/
	private String operationDeptCode;
	/**经营本部名称*/
	private String operationDeptName;
	/**外场编码*/
	private String transferCenterCode;
	/**外场名称*/
	private String transferCenterName;
	/**叉车司机当天票数*/
	private BigDecimal voteDay;
	/**叉车司机当天扫描的托盘数*/
	private BigDecimal trayQtyDay;
	/**叉车司机花费时长(分钟)*/
	private BigDecimal minCostDay;
	/**票均效率-天*/
	private BigDecimal effPerVoteDay;
	/**板均效率-天*/
	private BigDecimal effPerTrayDay;
	/**当月截至当天叉车司机票数*/
	private BigDecimal voteMonth;
	/**当月截至当天叉车司机扫描托盘数*/
	private BigDecimal trayQtyMonth;
	/**当月截至当天叉车司机花费时长(分钟)*/
	private BigDecimal minCostMonth;
	/**票均效率-当月截至当天*/
	private BigDecimal effPerVoteMonth;
	/**板均效率-当月截至当天*/
	private BigDecimal effPerTrayMonth;
	/**统计日期*/
	private Date staDate;
	/**统计月份*/
	private int staMonth;
	
	
	public String getOperationDeptCode() {
		return operationDeptCode;
	}
	public void setOperationDeptCode(String operationDeptCode) {
		this.operationDeptCode = operationDeptCode;
	}
	public String getOperationDeptName() {
		return operationDeptName;
	}
	public void setOperationDeptName(String operationDeptName) {
		this.operationDeptName = operationDeptName;
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
	public BigDecimal getVoteDay() {
		return voteDay;
	}
	public void setVoteDay(BigDecimal voteDay) {
		this.voteDay = voteDay;
	}
	public BigDecimal getTrayQtyDay() {
		return trayQtyDay;
	}
	public void setTrayQtyDay(BigDecimal trayQtyDay) {
		this.trayQtyDay = trayQtyDay;
	}
	public BigDecimal getMinCostDay() {
		return minCostDay;
	}
	public void setMinCostDay(BigDecimal minCostDay) {
		this.minCostDay = minCostDay;
	}
	public BigDecimal getEffPerVoteDay() {
		return effPerVoteDay;
	}
	public void setEffPerVoteDay(BigDecimal effPerVoteDay) {
		this.effPerVoteDay = effPerVoteDay;
	}
	public BigDecimal getEffPerTrayDay() {
		return effPerTrayDay;
	}
	public void setEffPerTrayDay(BigDecimal effPerTrayDay) {
		this.effPerTrayDay = effPerTrayDay;
	}
	public BigDecimal getVoteMonth() {
		return voteMonth;
	}
	public void setVoteMonth(BigDecimal voteMonth) {
		this.voteMonth = voteMonth;
	}
	public BigDecimal getTrayQtyMonth() {
		return trayQtyMonth;
	}
	public void setTrayQtyMonth(BigDecimal trayQtyMonth) {
		this.trayQtyMonth = trayQtyMonth;
	}
	public BigDecimal getMinCostMonth() {
		return minCostMonth;
	}
	public void setMinCostMonth(BigDecimal minCostMonth) {
		this.minCostMonth = minCostMonth;
	}
	public BigDecimal getEffPerVoteMonth() {
		return effPerVoteMonth;
	}
	public void setEffPerVoteMonth(BigDecimal effPerVoteMonth) {
		this.effPerVoteMonth = effPerVoteMonth;
	}
	public BigDecimal getEffPerTrayMonth() {
		return effPerTrayMonth;
	}
	public void setEffPerTrayMonth(BigDecimal effPerTrayMonth) {
		this.effPerTrayMonth = effPerTrayMonth;
	}
	public Date getStaDate() {
		return staDate;
	}
	public void setStaDate(Date staDate) {
		this.staDate = staDate;
	}
	public int getStaMonth() {
		return staMonth;
	}
	public void setStaMonth(int staMonth) {
		this.staMonth = staMonth;
	}
	
	

}
