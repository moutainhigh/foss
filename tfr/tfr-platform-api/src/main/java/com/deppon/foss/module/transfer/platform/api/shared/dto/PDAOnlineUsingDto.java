/**
 * 
 */
package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;

/**
 * @author 105795
 *
 */
public class PDAOnlineUsingDto implements Serializable{
	private static final long serialVersionUID = -929077558461195173L;

	
	private String hqCode ;//--本部编码
	
	private String hqName ;//--本部名称
	
	private String transferCenterCode ;//--外场编码
	
	private String transferCenterName ;//--外场名称
	
	private int staMonth;//查询月份
	
	private String staDate;//统计日期
	
	// ----------------叉车-------------------
	
	private int forkUesdPDACountDay ;//--叉车当日使用PDA最高锋台数
	
	private String  forkUesdPDAOlineTimeDay ;//--叉车使用PDA日最高锋时刻
	
	private int forkPDAMaxQtyMonth;//叉车使用PDA当月最高锋使用台数
	
	private int forkPointRelatedMonth;//叉车当月截至到当天最大的pda使用量是在哪天
	
	private String forkDateRelatedMonth;//叉车当月截至到当天最大的pda使用量是在哪个时间点
	
	private String forkMonthDayPoingTime;//叉车某月某天某点
	
	// ----------------理货员-------------------
	
    private int clerkUesdPDACountDay ;//--理货员当日使用PDA最高锋台数
	
	private String  clerkUesdPDAOlineTimeDay ;//--理货员使用PDA日最高锋时刻
	
	private int clerkPDAMaxQtyMonth ;//理货员当月截至到当天最大的pda使用量
	
	private int clerkPointRelatedMonth;//理货员当月截至到当天最大的pda使用量是在哪天
	
	private String clerkDateRelatedMonth;//理货员当月截至到当天最大的pda使用量是在哪个时间点
	
	private String clerkMonthDayPoingTime;//理货员某月某天某点

   // ----------------所有人员-------------------
	
    private int allUesdPDACountDay ;//--所有人员当日使用PDA最高锋台数
	
	private String  allUesdPDAOlineTimeDay ;//--所有人员使用PDA日最高锋时刻
	
	private int allPDAMaxQtyMonth ;//所有人员当月截至到当天最大的pda使用量
	
	private int allPointRelatedMonth;//所有人员当月截至到当天最大的pda使用量是在哪天
	
	private String allDateRelatedMonth;//所有人员当月截至到当天最大的pda使用量是在哪个时间点
	
	private String allMonthDayPoingTime;//所有人员某月某天某点

	/**
	 * @return the hqCode
	 */
	public String getHqCode() {
		return hqCode;
	}

	/**
	 * @param hqCode the hqCode to set
	 */
	public void setHqCode(String hqCode) {
		this.hqCode = hqCode;
	}

	/**
	 * @return the hqName
	 */
	public String getHqName() {
		return hqName;
	}

	/**
	 * @param hqName the hqName to set
	 */
	public void setHqName(String hqName) {
		this.hqName = hqName;
	}

	/**
	 * @return the transferCenterCode
	 */
	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	/**
	 * @param transferCenterCode the transferCenterCode to set
	 */
	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	/**
	 * @return the transferCenterName
	 */
	public String getTransferCenterName() {
		return transferCenterName;
	}

	/**
	 * @param transferCenterName the transferCenterName to set
	 */
	public void setTransferCenterName(String transferCenterName) {
		this.transferCenterName = transferCenterName;
	}

	/**
	 * @return the staMonth
	 */
	public int getStaMonth() {
		return staMonth;
	}

	/**
	 * @param staMonth the staMonth to set
	 */
	public void setStaMonth(int staMonth) {
		this.staMonth = staMonth;
	}

   

	/**
	 * @return the staDate
	 */
	public String getStaDate() {
		return staDate;
	}

	/**
	 * @param staDate the staDate to set
	 */
	public void setStaDate(String staDate) {
		this.staDate = staDate;
	}

	/**
	 * @return the forkUesdPDACountDay
	 */
	public int getForkUesdPDACountDay() {
		return forkUesdPDACountDay;
	}

	/**
	 * @param forkUesdPDACountDay the forkUesdPDACountDay to set
	 */
	public void setForkUesdPDACountDay(int forkUesdPDACountDay) {
		this.forkUesdPDACountDay = forkUesdPDACountDay;
	}

	/**
	 * @return the forkUesdPDAOlineTimeDay
	 */
	public String getForkUesdPDAOlineTimeDay() {
		return forkUesdPDAOlineTimeDay;
	}

	/**
	 * @param forkUesdPDAOlineTimeDay the forkUesdPDAOlineTimeDay to set
	 */
	public void setForkUesdPDAOlineTimeDay(String forkUesdPDAOlineTimeDay) {
		this.forkUesdPDAOlineTimeDay = forkUesdPDAOlineTimeDay;
	}

	/**
	 * @return the forkPDAMaxQtyMonth
	 */
	public int getForkPDAMaxQtyMonth() {
		return forkPDAMaxQtyMonth;
	}

	/**
	 * @param forkPDAMaxQtyMonth the forkPDAMaxQtyMonth to set
	 */
	public void setForkPDAMaxQtyMonth(int forkPDAMaxQtyMonth) {
		this.forkPDAMaxQtyMonth = forkPDAMaxQtyMonth;
	}

	/**
	 * @return the forkPointRelatedMonth
	 */
	public int getForkPointRelatedMonth() {
		return forkPointRelatedMonth;
	}

	/**
	 * @param forkPointRelatedMonth the forkPointRelatedMonth to set
	 */
	public void setForkPointRelatedMonth(int forkPointRelatedMonth) {
		this.forkPointRelatedMonth = forkPointRelatedMonth;
	}


	/**
	 * @return the clerkUesdPDACountDay
	 */
	public int getClerkUesdPDACountDay() {
		return clerkUesdPDACountDay;
	}

	/**
	 * @param clerkUesdPDACountDay the clerkUesdPDACountDay to set
	 */
	public void setClerkUesdPDACountDay(int clerkUesdPDACountDay) {
		this.clerkUesdPDACountDay = clerkUesdPDACountDay;
	}

	/**
	 * @return the clerkUesdPDAOlineTimeDay
	 */
	public String getClerkUesdPDAOlineTimeDay() {
		return clerkUesdPDAOlineTimeDay;
	}

	/**
	 * @param clerkUesdPDAOlineTimeDay the clerkUesdPDAOlineTimeDay to set
	 */
	public void setClerkUesdPDAOlineTimeDay(String clerkUesdPDAOlineTimeDay) {
		this.clerkUesdPDAOlineTimeDay = clerkUesdPDAOlineTimeDay;
	}

	/**
	 * @return the clerkPDAMaxQtyMonth
	 */
	public int getClerkPDAMaxQtyMonth() {
		return clerkPDAMaxQtyMonth;
	}

	/**
	 * @param clerkPDAMaxQtyMonth the clerkPDAMaxQtyMonth to set
	 */
	public void setClerkPDAMaxQtyMonth(int clerkPDAMaxQtyMonth) {
		this.clerkPDAMaxQtyMonth = clerkPDAMaxQtyMonth;
	}

	/**
	 * @return the clerkPointRelatedMonth
	 */
	public int getClerkPointRelatedMonth() {
		return clerkPointRelatedMonth;
	}

	/**
	 * @param clerkPointRelatedMonth the clerkPointRelatedMonth to set
	 */
	public void setClerkPointRelatedMonth(int clerkPointRelatedMonth) {
		this.clerkPointRelatedMonth = clerkPointRelatedMonth;
	}

	
	/**
	 * @return the allUesdPDACountDay
	 */
	public int getAllUesdPDACountDay() {
		return allUesdPDACountDay;
	}

	/**
	 * @param allUesdPDACountDay the allUesdPDACountDay to set
	 */
	public void setAllUesdPDACountDay(int allUesdPDACountDay) {
		this.allUesdPDACountDay = allUesdPDACountDay;
	}

	/**
	 * @return the allUesdPDAOlineTimeDay
	 */
	public String getAllUesdPDAOlineTimeDay() {
		return allUesdPDAOlineTimeDay;
	}

	/**
	 * @param allUesdPDAOlineTimeDay the allUesdPDAOlineTimeDay to set
	 */
	public void setAllUesdPDAOlineTimeDay(String allUesdPDAOlineTimeDay) {
		this.allUesdPDAOlineTimeDay = allUesdPDAOlineTimeDay;
	}

	/**
	 * @return the allPDAMaxQtyMonth
	 */
	public int getAllPDAMaxQtyMonth() {
		return allPDAMaxQtyMonth;
	}

	/**
	 * @param allPDAMaxQtyMonth the allPDAMaxQtyMonth to set
	 */
	public void setAllPDAMaxQtyMonth(int allPDAMaxQtyMonth) {
		this.allPDAMaxQtyMonth = allPDAMaxQtyMonth;
	}

	/**
	 * @return the allPointRelatedMonth
	 */
	public int getAllPointRelatedMonth() {
		return allPointRelatedMonth;
	}

	/**
	 * @param allPointRelatedMonth the allPointRelatedMonth to set
	 */
	public void setAllPointRelatedMonth(int allPointRelatedMonth) {
		this.allPointRelatedMonth = allPointRelatedMonth;
	}

	/**
	 * @return the forkDateRelatedMonth
	 */
	public String getForkDateRelatedMonth() {
		return forkDateRelatedMonth;
	}

	/**
	 * @param forkDateRelatedMonth the forkDateRelatedMonth to set
	 */
	public void setForkDateRelatedMonth(String forkDateRelatedMonth) {
		this.forkDateRelatedMonth = forkDateRelatedMonth;
	}

	/**
	 * @return the clerkDateRelatedMonth
	 */
	public String getClerkDateRelatedMonth() {
		return clerkDateRelatedMonth;
	}

	/**
	 * @param clerkDateRelatedMonth the clerkDateRelatedMonth to set
	 */
	public void setClerkDateRelatedMonth(String clerkDateRelatedMonth) {
		this.clerkDateRelatedMonth = clerkDateRelatedMonth;
	}

	/**
	 * @return the allDateRelatedMonth
	 */
	public String getAllDateRelatedMonth() {
		return allDateRelatedMonth;
	}

	/**
	 * @param allDateRelatedMonth the allDateRelatedMonth to set
	 */
	public void setAllDateRelatedMonth(String allDateRelatedMonth) {
		this.allDateRelatedMonth = allDateRelatedMonth;
	}

	/**
	 * @return the forkMonthDayPoingTime
	 */
	public String getForkMonthDayPoingTime() {
		return forkMonthDayPoingTime;
	}

	/**
	 * @param forkMonthDayPoingTime the forkMonthDayPoingTime to set
	 */
	public void setForkMonthDayPoingTime(String forkMonthDayPoingTime) {
		this.forkMonthDayPoingTime = forkMonthDayPoingTime;
	}

	/**
	 * @return the clerkMonthDayPoingTime
	 */
	public String getClerkMonthDayPoingTime() {
		return clerkMonthDayPoingTime;
	}

	/**
	 * @param clerkMonthDayPoingTime the clerkMonthDayPoingTime to set
	 */
	public void setClerkMonthDayPoingTime(String clerkMonthDayPoingTime) {
		this.clerkMonthDayPoingTime = clerkMonthDayPoingTime;
	}

	/**
	 * @return the allMonthDayPoingTime
	 */
	public String getAllMonthDayPoingTime() {
		return allMonthDayPoingTime;
	}

	/**
	 * @param allMonthDayPoingTime the allMonthDayPoingTime to set
	 */
	public void setAllMonthDayPoingTime(String allMonthDayPoingTime) {
		this.allMonthDayPoingTime = allMonthDayPoingTime;
	}



	
}
