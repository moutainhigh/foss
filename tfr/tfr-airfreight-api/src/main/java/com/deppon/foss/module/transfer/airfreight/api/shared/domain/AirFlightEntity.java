package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 空运航班实体
 * @author 105869
 * @date 2014年10月18日 09:20:38
 * */
public class AirFlightEntity extends BaseEntity{

	private static final long serialVersionUID = -64597114880259075L;
	
	//航班号
	private String flightNo;
	//出发部门名称
	private String departDepet;
	//出发部门编码
	private String departDepetCode;
	//到达部门
	private String arriveDept;
	//到达部门编码	
	private String arriveDeptCode;
	//起飞时间
	private Date departureTime;
	//到达时间
	private Date arrivedTime;
	//总重量
	private BigDecimal totalWeight;
	//总体积
	private BigDecimal totalVolum;
	//总件数
	private BigDecimal totalGoods;
	
	
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public String getDepartDepet() {
		return departDepet;
	}
	public void setDepartDepet(String departDepet) {
		this.departDepet = departDepet;
	}
	public String getDepartDepetCode() {
		return departDepetCode;
	}
	public void setDepartDepetCode(String departDepetCode) {
		this.departDepetCode = departDepetCode;
	}
	public String getArriveDept() {
		return arriveDept;
	}
	public void setArriveDept(String arriveDept) {
		this.arriveDept = arriveDept;
	}
	public String getArriveDeptCode() {
		return arriveDeptCode;
	}
	public void setArriveDeptCode(String arriveDeptCode) {
		this.arriveDeptCode = arriveDeptCode;
	}
	public Date getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}
	public Date getArrivedTime() {
		return arrivedTime;
	}
	public void setArrivedTime(Date arrivedTime) {
		this.arrivedTime = arrivedTime;
	}
	public BigDecimal getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}
	public BigDecimal getTotalVolum() {
		return totalVolum;
	}
	public void setTotalVolum(BigDecimal totalVolum) {
		this.totalVolum = totalVolum;
	}
	public BigDecimal getTotalGoods() {
		return totalGoods;
	}
	public void setTotalGoods(BigDecimal totalGoods) {
		this.totalGoods = totalGoods;
	}
	
	
	
}
