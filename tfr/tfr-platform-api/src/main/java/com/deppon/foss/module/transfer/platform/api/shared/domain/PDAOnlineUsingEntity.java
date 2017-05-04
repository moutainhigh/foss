/**
 * 
 */
package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * @desc pda在线使用情况
 * @author 105795
 * @date 2015/01/28
 */
public class PDAOnlineUsingEntity extends BaseEntity {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5272823711588574698L;
	private String hqCode ;//--本部编码
	private String hqName ;//--本部名称
	private String transferCenterCode ;//--外场编码
	private String transferCenterName ;//--外场名称
	private int postCode ;//--1叉车司机 2理货员 99全部
	private int pdaMaxQtyDay ;//--当天最大的pda使用量
	private int pointRelatedDay ;//--当天最大的pda使用量是在哪个时间点
	private int pdaMaxQtyMonth ;//--当月截至到当天最大的pda使用量
	private int pointRelatedMonth ;//--当月截至到当天最大的pda使用量是在哪天
	private int dateRelatedMonth ;//--当月截至到当天最大的pda使用量是在哪个时间点
	private Date staDate ;//--统计日期
	private int staMonth ;//--统计月份
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
	 * @return the postCode
	 */
	public int getPostCode() {
		return postCode;
	}
	/**
	 * @param postCode the postCode to set
	 */
	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}
	/**
	 * @return the pdaMaxQtyDay
	 */
	public int getPdaMaxQtyDay() {
		return pdaMaxQtyDay;
	}
	/**
	 * @param pdaMaxQtyDay the pdaMaxQtyDay to set
	 */
	public void setPdaMaxQtyDay(int pdaMaxQtyDay) {
		this.pdaMaxQtyDay = pdaMaxQtyDay;
	}
	/**
	 * @return the pointRelatedDay
	 */
	public int getPointRelatedDay() {
		return pointRelatedDay;
	}
	/**
	 * @param pointRelatedDay the pointRelatedDay to set
	 */
	public void setPointRelatedDay(int pointRelatedDay) {
		this.pointRelatedDay = pointRelatedDay;
	}
	/**
	 * @return the pdaMaxQtyMonth
	 */
	public int getPdaMaxQtyMonth() {
		return pdaMaxQtyMonth;
	}
	/**
	 * @param pdaMaxQtyMonth the pdaMaxQtyMonth to set
	 */
	public void setPdaMaxQtyMonth(int pdaMaxQtyMonth) {
		this.pdaMaxQtyMonth = pdaMaxQtyMonth;
	}
	/**
	 * @return the pointRelatedMonth
	 */
	public int getPointRelatedMonth() {
		return pointRelatedMonth;
	}
	/**
	 * @param pointRelatedMonth the pointRelatedMonth to set
	 */
	public void setPointRelatedMonth(int pointRelatedMonth) {
		this.pointRelatedMonth = pointRelatedMonth;
	}
	
	/**
	 * @return the staDate
	 */
	public Date getStaDate() {
		return staDate;
	}
	/**
	 * @return the dateRelatedMonth
	 */
	public int getDateRelatedMonth() {
		return dateRelatedMonth;
	}
	/**
	 * @param dateRelatedMonth the dateRelatedMonth to set
	 */
	public void setDateRelatedMonth(int dateRelatedMonth) {
		this.dateRelatedMonth = dateRelatedMonth;
	}
	/**
	 * @param staDate the staDate to set
	 */
	
	
	/**
	 * @return the staMonth
	 */
	public int getStaMonth() {
		return staMonth;
	}
	/**
	 * @param staDate the staDate to set
	 */
	public void setStaDate(Date staDate) {
		this.staDate = staDate;
	}
	/**
	 * @param staMonth the staMonth to set
	 */
	public void setStaMonth(int staMonth) {
		this.staMonth = staMonth;
	}
	
	

}
