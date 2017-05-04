package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ForkliftGoodsStayDurationDto implements Serializable{

	/**转运场code*/
    private String transferCenterCode;
	/**转运场name*/
	private String transferCenterName;
	/**统计时间*/
	private Date staDate;
	/**板均停留时长  -日*/
	private BigDecimal stayMinPerTrayDay;
	/**停留时长在（0,30] 区间的比率*/
	private BigDecimal stayMinDayRatio30;
	/**停留时长在（30,60] 区间的比率*/
	private BigDecimal stayMinDayRatio60;
	/**停留时长在（60,120] 区间的比率*/
	private BigDecimal stayMinDayRatio120;
	/**停留时长在（120,360] 区间的比率*/
	private BigDecimal stayMinDayRatio360;
	/**停留时长在（360,+∞] 区间的比率*/
	private BigDecimal stayMinDayRatio0;
	/**板均停留时长  -月*/
	private BigDecimal stayMinPerTrayMonth;
	/**停留时长在（0,30] 区间的比率*/
	private BigDecimal stayMinMonthRatio30;
	/**停留时长在（30,60] 区间的比率*/
	private BigDecimal stayMinMonthRatio60;
	/**停留时长在（60,120] 区间的比率*/
	private BigDecimal stayMinMonthRatio120;
	/**停留时长在（120,360] 区间的比率*/
	private BigDecimal stayMinMonthRatio360;
	/**停留时长在（360,+∞] 区间的比率*/
	private BigDecimal stayMinMonthRatio0;
	
	
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
	public Date getStaDate() {
		return staDate;
	}
	public void setStaDate(Date staDate) {
		this.staDate = staDate;
	}
	public BigDecimal getStayMinPerTrayDay() {
		return stayMinPerTrayDay;
	}
	public void setStayMinPerTrayDay(BigDecimal stayMinPerTrayDay) {
		this.stayMinPerTrayDay = stayMinPerTrayDay;
	}
	public BigDecimal getStayMinDayRatio30() {
		return stayMinDayRatio30;
	}
	public void setStayMinDayRatio30(BigDecimal stayMinDayRatio30) {
		this.stayMinDayRatio30 = stayMinDayRatio30;
	}
	public BigDecimal getStayMinDayRatio60() {
		return stayMinDayRatio60;
	}
	public void setStayMinDayRatio60(BigDecimal stayMinDayRatio60) {
		this.stayMinDayRatio60 = stayMinDayRatio60;
	}
	public BigDecimal getStayMinDayRatio120() {
		return stayMinDayRatio120;
	}
	public void setStayMinDayRatio120(BigDecimal stayMinDayRatio120) {
		this.stayMinDayRatio120 = stayMinDayRatio120;
	}
	public BigDecimal getStayMinDayRatio360() {
		return stayMinDayRatio360;
	}
	public void setStayMinDayRatio360(BigDecimal stayMinDayRatio360) {
		this.stayMinDayRatio360 = stayMinDayRatio360;
	}
	public BigDecimal getStayMinDayRatio0() {
		return stayMinDayRatio0;
	}
	public void setStayMinDayRatio0(BigDecimal stayMinDayRatio0) {
		this.stayMinDayRatio0 = stayMinDayRatio0;
	}
	public BigDecimal getStayMinPerTrayMonth() {
		return stayMinPerTrayMonth;
	}
	public void setStayMinPerTrayMonth(BigDecimal stayMinPerTrayMonth) {
		this.stayMinPerTrayMonth = stayMinPerTrayMonth;
	}
	public BigDecimal getStayMinMonthRatio30() {
		return stayMinMonthRatio30;
	}
	public void setStayMinMonthRatio30(BigDecimal stayMinMonthRatio30) {
		this.stayMinMonthRatio30 = stayMinMonthRatio30;
	}
	public BigDecimal getStayMinMonthRatio60() {
		return stayMinMonthRatio60;
	}
	public void setStayMinMonthRatio60(BigDecimal stayMinMonthRatio60) {
		this.stayMinMonthRatio60 = stayMinMonthRatio60;
	}
	public BigDecimal getStayMinMonthRatio120() {
		return stayMinMonthRatio120;
	}
	public void setStayMinMonthRatio120(BigDecimal stayMinMonthRatio120) {
		this.stayMinMonthRatio120 = stayMinMonthRatio120;
	}
	public BigDecimal getStayMinMonthRatio360() {
		return stayMinMonthRatio360;
	}
	public void setStayMinMonthRatio360(BigDecimal stayMinMonthRatio360) {
		this.stayMinMonthRatio360 = stayMinMonthRatio360;
	}
	public BigDecimal getStayMinMonthRatio0() {
		return stayMinMonthRatio0;
	}
	public void setStayMinMonthRatio0(BigDecimal stayMinMonthRatio0) {
		this.stayMinMonthRatio0 = stayMinMonthRatio0;
	}
	
	
	
}
