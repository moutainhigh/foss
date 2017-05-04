package com.deppon.foss.module.transfer.unload.api.shared.domain;

import java.util.Date;

/**
 *电叉司机信息数据，提供前台页面显示 
 ***/
public class ForkliftDriverEntity {
	
	/**司机姓名**/
	private String forkliftDriverName;
	/**司机工号**/
	private String forkliftDriverCode;
	/**查询日期**/
	private Date queryDate;
	/**当日板均效率**/
	private String dayBoardsEfficiency;
	/**日票效率**/
	private String dayTicketsEfficiency;
	/**月板均效率**/
	private String monthBoardsEfficiency;
	/**月票效率**/
	private String monthTicketsEfficiency;
	
	public String getForkliftDriverName() {
		return forkliftDriverName;
	}
	public void setForkliftDriverName(String forkliftDriverName) {
		this.forkliftDriverName = forkliftDriverName;
	}
	public String getForkliftDriverCode() {
		return forkliftDriverCode;
	}
	public void setForkliftDriverCode(String forkliftDriverCode) {
		this.forkliftDriverCode = forkliftDriverCode;
	}
	public Date getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}
	public String getDayBoardsEfficiency() {
		return dayBoardsEfficiency;
	}
	public void setDayBoardsEfficiency(String dayBoardsEfficiency) {
		this.dayBoardsEfficiency = dayBoardsEfficiency;
	}
	public String getDayTicketsEfficiency() {
		return dayTicketsEfficiency;
	}
	public void setDayTicketsEfficiency(String dayTicketsEfficiency) {
		this.dayTicketsEfficiency = dayTicketsEfficiency;
	}
	public String getMonthBoardsEfficiency() {
		return monthBoardsEfficiency;
	}
	public void setMonthBoardsEfficiency(String monthBoardsEfficiency) {
		this.monthBoardsEfficiency = monthBoardsEfficiency;
	}
	public String getMonthTicketsEfficiency() {
		return monthTicketsEfficiency;
	}
	public void setMonthTicketsEfficiency(String monthTicketsEfficiency) {
		this.monthTicketsEfficiency = monthTicketsEfficiency;
	}
	
	
	
}