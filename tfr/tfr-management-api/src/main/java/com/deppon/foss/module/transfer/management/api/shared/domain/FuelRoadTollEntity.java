/**
 * 
 */
package com.deppon.foss.module.transfer.management.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * @author Administrator
 *
 */
public class FuelRoadTollEntity extends BaseEntity {

	private static final long serialVersionUID = -6480710354748956298L;

	private String departureId;
	//金额
	private BigDecimal roadToll;
	
	//地点
	private String roadAddress;
	
	//s时间
	private Date roadTime;
	
	//付款方式
	private String payment;

	/**
	 * @return the departureId
	 */
	public String getDepartureId() {
		return departureId;
	}

	/**
	 * @param departureId the departureId to set
	 */
	public void setDepartureId(String departureId) {
		this.departureId = departureId;
	}

	/**
	 * @return the roadToll
	 */
	public BigDecimal getRoadToll() {
		return roadToll;
	}

	/**
	 * @param roadToll the roadToll to set
	 */
	public void setRoadToll(BigDecimal roadToll) {
		this.roadToll = roadToll;
	}


	/**
	 * @return the roadAddress
	 */
	public String getRoadAddress() {
		return roadAddress;
	}

	/**
	 * @param roadAddress the roadAddress to set
	 */
	public void setRoadAddress(String roadAddress) {
		this.roadAddress = roadAddress;
	}

	/**
	 * @return the roadTime
	 */
	public Date getRoadTime() {
		return roadTime;
	}

	/**
	 * @param roadTime the roadTime to set
	 */
	public void setRoadTime(Date roadTime) {
		this.roadTime = roadTime;
	}

	/**
	 * @return the payment
	 */
	public String getPayment() {
		return payment;
	}

	/**
	 * @param payment the payment to set
	 */
	public void setPayment(String payment) {
		this.payment = payment;
	}
	
}
