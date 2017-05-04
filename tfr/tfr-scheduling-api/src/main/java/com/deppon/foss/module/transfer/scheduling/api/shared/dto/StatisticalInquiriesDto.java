/**
 * 
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *  统计货量查询DTO
 * @author yuyongxiang
 * @date 2013年7月6日 14:15:24
 */
public class StatisticalInquiriesDto  implements Serializable  {
	private static final long serialVersionUID = -604014506255433300L;
	
	/**运单号*/
	private String wayBillNo;
	
	/**运输性质*/
	private String transportModelCode;
	
	/**运输性质*/
	private String transportModelName;
	
	/**货物名称*/
	private String goodName;
	
	/**货物状态*/
	private String goodStatus;
	
	/**体积(方)*/
	private String volume;
	
	/**重量(千克)*/
	private String weight;
	
	/**件数*/
	private String goodQty;
	
	/**出发部门 code*/
	private String destOrgCode;
	
	/**出发部门 Name*/
	private String destOrgName;
	
	/**到达部门 code*/
	private String arriveOrgCode;
	
	/**到达部门 Name*/
	private String arriveOrgName;
	
	/**开单时间*/
	private Date billingTime;
	
	/**到达时间*/
	private Date arriveTime;
	
	/**当前部门*/
	private String currentOrgCode;
	
	/**query 获取当前部门所对应的外场或者营业部或为当前登陆部门*/
	private String transforCenterCode;
	
	/**
	 * 配载方案里面的值
	 */
	private List<String> arriveDeptList;
	
	/**
	 * 时间限定范围 start
	 */
	private Date startTime;
	/**
	 * 时间限定范围 end
	 */
	private Date endTime;
	

	/**
	 * 运单号
	 * @return the wayBillNo
	 */
	public String getWayBillNo() {
		return wayBillNo;
	}

	/**
	 * 运单号
	 * @param wayBillNo the wayBillNo to set
	 */
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}

	/**
	 * @return the transportModelCode
	 */
	public String getTransportModelCode() {
		return transportModelCode;
	}

	/**
	 * @param transportModelCode the transportModelCode to set
	 */
	public void setTransportModelCode(String transportModelCode) {
		this.transportModelCode = transportModelCode;
	}

	/**
	 * @return the transportModelName
	 */
	public String getTransportModelName() {
		return transportModelName;
	}

	/**
	 * @param transportModelName the transportModelName to set
	 */
	public void setTransportModelName(String transportModelName) {
		this.transportModelName = transportModelName;
	}

	/**
	 * @return the goodName
	 */
	public String getGoodName() {
		return goodName;
	}

	/**
	 * @param goodName the goodName to set
	 */
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	/**
	 * @return the goodStatus
	 */
	public String getGoodStatus() {
		return goodStatus;
	}

	/**
	 * @param goodStatus the goodStatus to set
	 */
	public void setGoodStatus(String goodStatus) {
		this.goodStatus = goodStatus;
	}

	/**
	 * @return the volume
	 */
	public String getVolume() {
		return volume;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(String volume) {
		this.volume = volume;
	}

	/**
	 * @return the weight
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}

	/**
	 * @return the goodQty
	 */
	public String getGoodQty() {
		return goodQty;
	}

	/**
	 * @param goodQty the goodQty to set
	 */
	public void setGoodQty(String goodQty) {
		this.goodQty = goodQty;
	}

	/**
	 * @return the destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * @param destOrgCode the destOrgCode to set
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * @return the destOrgName
	 */
	public String getDestOrgName() {
		return destOrgName;
	}

	/**
	 * @param destOrgName the destOrgName to set
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	/**
	 * 到达部门 code
	 * @return the arriveOrgCode
	 */
	public String getArriveOrgCode() {
		return arriveOrgCode;
	}

	/**
	 * 到达部门 code
	 * @param arriveOrgCode the arriveOrgCode to set
	 */
	public void setArriveOrgCode(String arriveOrgCode) {
		this.arriveOrgCode = arriveOrgCode;
	}

	/**
	 * @return the arriveOrgName
	 */
	public String getArriveOrgName() {
		return arriveOrgName;
	}

	/**
	 * @param arriveOrgName the arriveOrgName to set
	 */
	public void setArriveOrgName(String arriveOrgName) {
		this.arriveOrgName = arriveOrgName;
	}

	/**
	 * @return the billingTime
	 */
	public Date getBillingTime() {
		return billingTime;
	}

	/**
	 * @param billingTime the billingTime to set
	 */
	public void setBillingTime(Date billingTime) {
		this.billingTime = billingTime;
	}

	/**
	 * @return the arriveTime
	 */
	public Date getArriveTime() {
		return arriveTime;
	}

	/**
	 * @param arriveTime the arriveTime to set
	 */
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	/**
	 * @return the currentOrgCode
	 */
	public String getCurrentOrgCode() {
		return currentOrgCode;
	}

	/**
	 * @param currentOrgCode the currentOrgCode to set
	 */
	public void setCurrentOrgCode(String currentOrgCode) {
		this.currentOrgCode = currentOrgCode;
	}

	/**
	 * query 获取当前部门所对应的外场或者营业部或为当前登陆部门
	 * @return the transforCenterCode
	 */
	public String getTransforCenterCode() {
		return transforCenterCode;
	}

	/**
	 * query 获取当前部门所对应的外场或者营业部或为当前登陆部门
	 * @param transforCenterCode the transforCenterCode to set
	 */
	public void setTransforCenterCode(String transforCenterCode) {
		this.transforCenterCode = transforCenterCode;
	}

	/**
	 * 配载方案里面的值
	 * @return the arriveDeptList
	 */
	public List<String> getArriveDeptList() {
		return arriveDeptList;
	}

	/**
	 * 配载方案里面的值
	 * @param arriveDeptList the arriveDeptList to set
	 */
	public void setArriveDeptList(List<String> arriveDeptList) {
		this.arriveDeptList = arriveDeptList;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
