package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 临时租车快递机场扫描单据Entity
 * @author 313352
 *
 */
public class QueryExpressAirportEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 单据号
	 */
	private String airscanTaskNo;
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 重量
	 */
	private BigDecimal weight;
	/**
	 *体积
	 */
	private BigDecimal volume;
	/**
	 * 单据创建时间
	 */
	private Date createTime;
	
	public String getAirscanTaskNo() {
		return airscanTaskNo;
	}
	public void setAirscanTaskNo(String airscanTaskNo) {
		this.airscanTaskNo = airscanTaskNo;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public BigDecimal getVolume() {
		return volume;
	}
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
	
}
