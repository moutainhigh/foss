package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 月累计都是截至到查询时间的累计
 * @author Ouyang
 */
public class TfrCtrStaffDto implements Serializable{

	private static final long serialVersionUID = 3841031075313921056L;

	/**
	 * 统计时间
	 */
	private Date statisticDate;
	
	/**
	 * 外场编码
	 */
	private String transferCenterCode;
	
	/**
	 * 外场名称
	 */
	private String transferCenterName;
	
	/**
	 * 当日总预算
	 */
	private Integer totalQtyBudeget;
	
	/**
	 * 当日理货员预算
	 */
	private Integer loaderBudget;
	
	/**
	 * 当日叉车司机预算
	 */
	private Integer driverBudget;
	
	/**
	 * 当日总实际
	 */
	private Integer totalQtyActual;
	
	/**
	 * 当月1号总实际
	 */
	private String totalQtyActualFirstDay;
	
	/**
	 * 当日理货员实际
	 */
	private Integer loaderActual;
	
	/**
	 * 当日叉车司机实际
	 */
	private Integer driverActual;
	
	/**
	 * 当日总缺口
	 */
	private Integer totalRemainder;
	
	/**
	 * 当日理货员缺口
	 */
	private Integer loaderRemainder;
	
	/**
	 * 当日叉车司机缺口
	 */
	private Integer driverRemainder;
	
	/**
	 * 当日异常数
	 */
	private Integer absenteeDayCnt;
	
	/**
	 * 月累计异常数
	 */
	private Integer absenteeAccumulatedCnt;
	
	/**
	 * 当日异常率 = 月累计异常数/总实际
	 */
	private String absentRate;
	
	/**
	 * 当日离职数
	 */
	private Integer dimission;
	
	/**
	 * 月累计离职数
	 */
	private Integer dimissionAccumulated;
	
	/**
	 * 当日离职率=月累计离职数/[(当月1号总实际+当日总实际)/2]
	 */
	private String dimissionRate;
	
	/**
	 * 当日出勤
	 */
	private Integer onDutyCnt;
	
	/**
	 * 当日未出勤
	 */
	private Integer noDutyCnt;
	
	/**
	 * 当日月累计连续3天未出勤
	 */
	private Integer noDuty3DayCnt;

	public Date getStatisticDate() {
		return statisticDate;
	}

	public void setStatisticDate(Date statisticDate) {
		this.statisticDate = statisticDate;
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

	public Integer getTotalQtyBudeget() {
		return totalQtyBudeget;
	}

	public void setTotalQtyBudeget(Integer totalQtyBudeget) {
		this.totalQtyBudeget = totalQtyBudeget;
	}

	public Integer getLoaderBudget() {
		return loaderBudget;
	}

	public void setLoaderBudget(Integer loaderBudget) {
		this.loaderBudget = loaderBudget;
	}

	public Integer getDriverBudget() {
		return driverBudget;
	}

	public void setDriverBudget(Integer driverBudget) {
		this.driverBudget = driverBudget;
	}

	public Integer getTotalQtyActual() {
		return totalQtyActual;
	}

	public void setTotalQtyActual(Integer totalQtyActual) {
		this.totalQtyActual = totalQtyActual;
	}

	public String getTotalQtyActualFirstDay() {
		return totalQtyActualFirstDay;
	}

	public void setTotalQtyActualFirstDay(String totalQtyActualFirstDay) {
		this.totalQtyActualFirstDay = totalQtyActualFirstDay;
	}

	public Integer getLoaderActual() {
		return loaderActual;
	}

	public void setLoaderActual(Integer loaderActual) {
		this.loaderActual = loaderActual;
	}

	public Integer getDriverActual() {
		return driverActual;
	}

	public void setDriverActual(Integer driverActual) {
		this.driverActual = driverActual;
	}

	public Integer getTotalRemainder() {
		return totalRemainder;
	}

	public void setTotalRemainder(Integer totalRemainder) {
		this.totalRemainder = totalRemainder;
	}

	public Integer getLoaderRemainder() {
		return loaderRemainder;
	}

	public void setLoaderRemainder(Integer loaderRemainder) {
		this.loaderRemainder = loaderRemainder;
	}

	public Integer getDriverRemainder() {
		return driverRemainder;
	}

	public void setDriverRemainder(Integer driverRemainder) {
		this.driverRemainder = driverRemainder;
	}

	public Integer getAbsenteeDayCnt() {
		return absenteeDayCnt;
	}

	public void setAbsenteeDayCnt(Integer absenteeDayCnt) {
		this.absenteeDayCnt = absenteeDayCnt;
	}

	public Integer getAbsenteeAccumulatedCnt() {
		return absenteeAccumulatedCnt;
	}

	public void setAbsenteeAccumulatedCnt(Integer absenteeAccumulatedCnt) {
		this.absenteeAccumulatedCnt = absenteeAccumulatedCnt;
	}

	public String getAbsentRate() {
		return absentRate;
	}

	public void setAbsentRate(String absentRate) {
		this.absentRate = absentRate;
	}

	public Integer getDimission() {
		return dimission;
	}

	public void setDimission(Integer dimission) {
		this.dimission = dimission;
	}

	public Integer getDimissionAccumulated() {
		return dimissionAccumulated;
	}

	public void setDimissionAccumulated(Integer dimissionAccumulated) {
		this.dimissionAccumulated = dimissionAccumulated;
	}

	public String getDimissionRate() {
		return dimissionRate;
	}

	public void setDimissionRate(String dimissionRate) {
		this.dimissionRate = dimissionRate;
	}

	public Integer getOnDutyCnt() {
		return onDutyCnt;
	}

	public void setOnDutyCnt(Integer onDutyCnt) {
		this.onDutyCnt = onDutyCnt;
	}

	public Integer getNoDutyCnt() {
		return noDutyCnt;
	}

	public void setNoDutyCnt(Integer noDutyCnt) {
		this.noDutyCnt = noDutyCnt;
	}

	public Integer getNoDuty3DayCnt() {
		return noDuty3DayCnt;
	}

	public void setNoDuty3DayCnt(Integer noDuty3DayCnt) {
		this.noDuty3DayCnt = noDuty3DayCnt;
	}

	@Override
	public String toString() {
		return "TfrCtrStaffDto [statisticDate=" + statisticDate
				+ ", transferCenterCode=" + transferCenterCode
				+ ", transferCenterName=" + transferCenterName
				+ ", totalQtyBudeget=" + totalQtyBudeget + ", loaderBudget="
				+ loaderBudget + ", driverBudget=" + driverBudget
				+ ", totalQtyActual=" + totalQtyActual
				+ ", totalQtyActualFirstDay=" + totalQtyActualFirstDay
				+ ", loaderActual=" + loaderActual + ", driverActual="
				+ driverActual + ", totalRemainder=" + totalRemainder
				+ ", loaderRemainder=" + loaderRemainder + ", driverRemainder="
				+ driverRemainder + ", absenteeDayCnt=" + absenteeDayCnt
				+ ", absenteeAccumulatedCnt=" + absenteeAccumulatedCnt
				+ ", absentRate=" + absentRate + ", dimission=" + dimission
				+ ", dimissionAccumulated=" + dimissionAccumulated
				+ ", dimissionRate=" + dimissionRate + ", onDutyCnt="
				+ onDutyCnt + ", noDutyCnt=" + noDutyCnt + ", noDuty3DayCnt="
				+ noDuty3DayCnt + "]";
	}
	
}
