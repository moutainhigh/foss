package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/***
 * 装卸车效率  统计实体
 * @author 200978  xiaobingcheng
 * 2015-1-19
 */
public class TruckEfficiencyEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**统计日期 2015-01-19*/
	private Date statisticDate;
	/**统计月份   201501*/
	private int statisticMonth;
	/**外场编码*/
	private String transferCenterCode;
	/**外场名称*/
	private String transferCenterName;
	/**装车操作员货量*/
	private BigDecimal loadTotalWeight;
	/**卸车操作员货量*/
	private BigDecimal unloadTotalWeight;
	/**装车操作时长*/
	private BigDecimal loadTotalDuration;
	/**卸车操作时长*/
	private BigDecimal unloadTotalDuration;
	/**装车效率       货量/时长*/
	private BigDecimal loadRatio;
	/**卸车效率    货量/时长*/
	private BigDecimal unloadRatio;
	/**总效率     装卸车货量／装卸车时长*/
	private BigDecimal totalRatio;
	/**创建时间*/
	private Date createTime;
	/**经营本部 编码*/
	private String operationDeptCode;
	/**经营本部 名称*/
	private String operationDeptName;
	
	
	public Date getStatisticDate() {
		return statisticDate;
	}
	public void setStatisticDate(Date statisticDate) {
		this.statisticDate = statisticDate;
	}
	public int getStatisticMonth() {
		return statisticMonth;
	}
	public void setStatisticMonth(int statisticMonth) {
		this.statisticMonth = statisticMonth;
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
	public BigDecimal getLoadTotalWeight() {
		return loadTotalWeight;
	}
	public void setLoadTotalWeight(BigDecimal loadTotalWeight) {
		this.loadTotalWeight = loadTotalWeight;
	}
	public BigDecimal getUnloadTotalWeight() {
		return unloadTotalWeight;
	}
	public void setUnloadTotalWeight(BigDecimal unloadTotalWeight) {
		this.unloadTotalWeight = unloadTotalWeight;
	}
	public BigDecimal getLoadTotalDuration() {
		return loadTotalDuration;
	}
	public void setLoadTotalDuration(BigDecimal loadTotalDuration) {
		this.loadTotalDuration = loadTotalDuration;
	}
	
	public BigDecimal getLoadRatio() {
		return loadRatio;
	}
	public void setLoadRatio(BigDecimal loadRatio) {
		this.loadRatio = loadRatio;
	}
	public BigDecimal getUnloadTotalDuration() {
		return unloadTotalDuration;
	}
	public void setUnloadTotalDuration(BigDecimal unloadTotalDuration) {
		this.unloadTotalDuration = unloadTotalDuration;
	}
	public BigDecimal getUnloadRatio() {
		return unloadRatio;
	}
	public void setUnloadRatio(BigDecimal unloadRatio) {
		this.unloadRatio = unloadRatio;
	}
	public BigDecimal getTotalRatio() {
		return totalRatio;
	}
	public void setTotalRatio(BigDecimal totalRatio) {
		this.totalRatio = totalRatio;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
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
	
	
	
}
