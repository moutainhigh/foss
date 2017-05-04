/**
 * 
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 *  统计货量查询DTO
 * @author yuyongxiang
 * @date 2013年7月6日 14:15:24
 */
public class StatisticalInquiriesEntity extends BaseEntity {
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
	private BigDecimal volume;
	
	/**重量(千克)*/
	private BigDecimal weight;
	
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
	 * 运输性质
	 * @return the transportModelCode
	 */
	public String getTransportModelCode() {
		return transportModelCode;
	}

	/**
	 * 运输性质
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
	public BigDecimal getVolume() {
		return volume;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	/**
	 * @return the weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(BigDecimal weight) {
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
	 * @return the arriveOrgCode
	 */
	public String getArriveOrgCode() {
		return arriveOrgCode;
	}

	/**
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

}
