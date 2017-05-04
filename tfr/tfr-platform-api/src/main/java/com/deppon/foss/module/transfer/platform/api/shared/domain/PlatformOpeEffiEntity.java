package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
* @ClassName: PlatformOpeEffiEntity 
* @Description: 月台操作效率实体
* @author 105944
* @date 2015-3-21 上午9:38:27
 */
public class PlatformOpeEffiEntity extends BaseEntity {

	
	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 1L;
	//统计日期
	private Date statisticDate;
	//统计月份
	private int statisticMonth;
	//经营本部编码
	private String businessDeptCode;
	//经营本部名称
	private String businessDept;	
	//外场编码
	private String outfieldCode;
	//外场名称 
	private String outfield;
	//月台编码
	private String platformNumber;
	//当日装车吞吐量
	private BigDecimal loadAmountByDay;
	//当日卸车吞吐量
	private BigDecimal downloadAmountByDay;
	//当日装车有效操作时长
	private BigDecimal loadTimeByDay;
	//当日卸车有效操作时长
	private BigDecimal downloadTimeByDay;
	//当日月台操作效率
	private BigDecimal platformOpeEffiByDay;
	//当月装车吞吐量
	private BigDecimal loadAmountByMonth;
	//当月卸车吞吐量
	private BigDecimal downloadAmountByMonth;
	//当月装车有效操作时长
	private BigDecimal loadTimeByMonth;
	//当月卸车有效操作时长
	private BigDecimal downloadTimeByMonth;
	//当月月台操作效率
	private BigDecimal platformOpeEffiByMonth;
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
	public String getBusinessDeptCode() {
		return businessDeptCode;
	}
	public void setBusinessDeptCode(String businessDeptCode) {
		this.businessDeptCode = businessDeptCode;
	}
	public String getBusinessDept() {
		return businessDept;
	}
	public void setBusinessDept(String businessDept) {
		this.businessDept = businessDept;
	}
	public String getOutfieldCode() {
		return outfieldCode;
	}
	public void setOutfieldCode(String outfieldCode) {
		this.outfieldCode = outfieldCode;
	}
	public String getOutfield() {
		return outfield;
	}
	public void setOutfield(String outfield) {
		this.outfield = outfield;
	}
	public String getPlatformNumber() {
		return platformNumber;
	}
	public void setPlatformNumber(String platformNumber) {
		this.platformNumber = platformNumber;
	}
	public BigDecimal getLoadAmountByDay() {
		return loadAmountByDay;
	}
	public void setLoadAmountByDay(BigDecimal loadAmountByDay) {
		this.loadAmountByDay = loadAmountByDay;
	}
	public BigDecimal getDownloadAmountByDay() {
		return downloadAmountByDay;
	}
	public void setDownloadAmountByDay(BigDecimal downloadAmountByDay) {
		this.downloadAmountByDay = downloadAmountByDay;
	}
	public BigDecimal getLoadTimeByDay() {
		return loadTimeByDay;
	}
	public void setLoadTimeByDay(BigDecimal loadTimeByDay) {
		this.loadTimeByDay = loadTimeByDay;
	}
	public BigDecimal getDownloadTimeByDay() {
		return downloadTimeByDay;
	}
	public void setDownloadTimeByDay(BigDecimal downloadTimeByDay) {
		this.downloadTimeByDay = downloadTimeByDay;
	}
	public BigDecimal getPlatformOpeEffiByDay() {
		return platformOpeEffiByDay;
	}
	public void setPlatformOpeEffiByDay(BigDecimal platformOpeEffiByDay) {
		this.platformOpeEffiByDay = platformOpeEffiByDay;
	}
	public BigDecimal getLoadAmountByMonth() {
		return loadAmountByMonth;
	}
	public void setLoadAmountByMonth(BigDecimal loadAmountByMonth) {
		this.loadAmountByMonth = loadAmountByMonth;
	}
	public BigDecimal getDownloadAmountByMonth() {
		return downloadAmountByMonth;
	}
	public void setDownloadAmountByMonth(BigDecimal downloadAmountByMonth) {
		this.downloadAmountByMonth = downloadAmountByMonth;
	}
	public BigDecimal getLoadTimeByMonth() {
		return loadTimeByMonth;
	}
	public void setLoadTimeByMonth(BigDecimal loadTimeByMonth) {
		this.loadTimeByMonth = loadTimeByMonth;
	}
	public BigDecimal getDownloadTimeByMonth() {
		return downloadTimeByMonth;
	}
	public void setDownloadTimeByMonth(BigDecimal downloadTimeByMonth) {
		this.downloadTimeByMonth = downloadTimeByMonth;
	}
	public BigDecimal getPlatformOpeEffiByMonth() {
		return platformOpeEffiByMonth;
	}
	public void setPlatformOpeEffiByMonth(BigDecimal platformOpeEffiByMonth) {
		this.platformOpeEffiByMonth = platformOpeEffiByMonth;
	}
}
