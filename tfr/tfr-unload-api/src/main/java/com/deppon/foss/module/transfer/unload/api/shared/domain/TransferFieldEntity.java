package com.deppon.foss.module.transfer.unload.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zenghaibin
 * @date 2014-07-16
 *转运场信息数据，提供前台页面显示 
 ***/
public class TransferFieldEntity {
	
/**转运场名称**/
private String transfFieldName;
/**转运场编码**/
private String transfFieldCode;
/**查询日期**/
private Date queryDate;
/**当日票效率**/
private String dayTicketsEfficiency;
/**当日板均效率**/
private String dayBoardsEfficiency;
/**当日车均票数**/
private String dayCarEfficiency;
/**当日车叉车票总数数**/
private BigDecimal dayCarCount;
/**当日叉车个数**/
private BigDecimal forkliftCount;
/**当日人均数**/
private String dayPeopleEfficiency;
/**当日等待时长**/
private String dayWaitTime;
/**当月票效率**/
private String monthTicketsEfficiency;
/**当月板均效率**/
private String monthBoardsEfficiency;
/**当月车均票数**/
private String monthCarEfficiency;
/**当月车总数**/
private BigDecimal monthCarCount;
/**当月叉车个数总数**/
private BigDecimal sumCount;
/**当月人均票数**/
private String monthPeopleEfficiency;
/**当月等待时长**/
private String monthWaitTime;
public String getTransfFieldName() {
	return transfFieldName;
}
public void setTransfFieldName(String transfFieldName) {
	this.transfFieldName = transfFieldName;
}
public String getTransfFieldCode() {
	return transfFieldCode;
}
public void setTransfFieldCode(String transfFieldCode) {
	this.transfFieldCode = transfFieldCode;
}
public Date getQueryDate() {
	return queryDate;
}
public void setQueryDate(Date queryDate) {
	this.queryDate = queryDate;
}

public String getDayTicketsEfficiency() {
	return dayTicketsEfficiency;
}
public void setDayTicketsEfficiency(String dayTicketsEfficiency) {
	this.dayTicketsEfficiency = dayTicketsEfficiency;
}
public String getDayBoardsEfficiency() {
	return dayBoardsEfficiency;
}
public void setDayBoardsEfficiency(String dayBoardsEfficiency) {
	this.dayBoardsEfficiency = dayBoardsEfficiency;
}
public String getDayCarEfficiency() {
	return dayCarEfficiency;
}
public void setDayCarEfficiency(String dayCarEfficiency) {
	this.dayCarEfficiency = dayCarEfficiency;
}
public String getDayPeopleEfficiency() {
	return dayPeopleEfficiency;
}
public void setDayPeopleEfficiency(String dayPeopleEfficiency) {
	this.dayPeopleEfficiency = dayPeopleEfficiency;
}
public String getDayWaitTime() {
	return dayWaitTime;
}
public void setDayWaitTime(String dayWaitTime) {
	this.dayWaitTime = dayWaitTime;
}
public String getMonthTicketsEfficiency() {
	return monthTicketsEfficiency;
}
public void setMonthTicketsEfficiency(String monthTicketsEfficiency) {
	this.monthTicketsEfficiency = monthTicketsEfficiency;
}
public String getMonthBoardsEfficiency() {
	return monthBoardsEfficiency;
}
public void setMonthBoardsEfficiency(String monthBoardsEfficiency) {
	this.monthBoardsEfficiency = monthBoardsEfficiency;
}
public String getMonthCarEfficiency() {
	return monthCarEfficiency;
}
public void setMonthCarEfficiency(String monthCarEfficiency) {
	this.monthCarEfficiency = monthCarEfficiency;
}
public String getMonthPeopleEfficiency() {
	return monthPeopleEfficiency;
}
public void setMonthPeopleEfficiency(String monthPeopleEfficiency) {
	this.monthPeopleEfficiency = monthPeopleEfficiency;
}
public String getMonthWaitTime() {
	return monthWaitTime;
}
public void setMonthWaitTime(String monthWaitTime) {
	this.monthWaitTime = monthWaitTime;
}
public BigDecimal getDayCarCount() {
	return dayCarCount;
}
public void setDayCarCount(BigDecimal dayCarCount) {
	this.dayCarCount = dayCarCount;
}
public BigDecimal getMonthCarCount() {
	return monthCarCount;
}
public void setMonthCarCount(BigDecimal monthCarCount) {
	this.monthCarCount = monthCarCount;
}
public BigDecimal getForkliftCount() {
	return forkliftCount;
}
public void setForkliftCount(BigDecimal forkliftCount) {
	this.forkliftCount = forkliftCount;
}
public BigDecimal getSumCount() {
	return sumCount;
}
public void setSumCount(BigDecimal sumCount) {
	this.sumCount = sumCount;
}


}