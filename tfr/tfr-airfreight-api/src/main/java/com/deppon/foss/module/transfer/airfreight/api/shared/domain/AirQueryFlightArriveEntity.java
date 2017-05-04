package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 查询空运到达的实体类
 * @author zwd 200968 zwd 
 * @date 2015-06-16
 *
 */
public class AirQueryFlightArriveEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -25014589762318731L;
	  

	/**
	 * 正单号
	 */
	private String airWaybillNo;

	/**
	 * 空运到达类型
	 */
	private String flightArriveType;
	/**
	 * 航空公司code
	 */
	private String airLineCode;
	
	/**
	 * 航班号
	 */
	private String flightNo;
	/**
	 * 到达件数
	 */
	private int arriveGoodsQty;
	/**
	 * 到达重量
	 */
	private BigDecimal arriveGoodsWeight;
	/**
	 * 代理公司code
	 */
	private String agentCompanyCode;
	/**
	 * 代理公司name
	 */
	private String agentCompanyName;
	/**
	 * 配载部门code
	 */
	private String orgCode;
	/**
	 * 配载部门name
	 */
	private String orgName;
	/**
	 * 操作人
	 */
	private String operateUserName;
	/**
	 * 操作人编码
	 */
	private String operateUserCode;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 是否有效
	 */
	private String active;
	
	public String getAirWaybillNo() {
		return airWaybillNo;
	}
	public void setAirWaybillNo(String airWaybillNo) {
		this.airWaybillNo = airWaybillNo;
	}
	public String getFlightArriveType() {
		return flightArriveType;
	}
	public void setFlightArriveType(String flightArriveType) {
		this.flightArriveType = flightArriveType;
	}
	public String getAirLineCode() {
		return airLineCode;
	}
	public void setAirLineCode(String airLineCode) {
		this.airLineCode = airLineCode;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	
	public int getArriveGoodsQty() {
		return arriveGoodsQty;
	}
	public void setArriveGoodsQty(int arriveGoodsQty) {
		this.arriveGoodsQty = arriveGoodsQty;
	}
	public String getAgentCompanyCode() {
		return agentCompanyCode;
	}
	public void setAgentCompanyCode(String agentCompanyCode) {
		this.agentCompanyCode = agentCompanyCode;
	}
	public String getAgentCompanyName() {
		return agentCompanyName;
	}
	public void setAgentCompanyName(String agentCompanyName) {
		this.agentCompanyName = agentCompanyName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOperateUserName() {
		return operateUserName;
	}
	public void setOperateUserName(String operateUserName) {
		this.operateUserName = operateUserName;
	}
	public String getOperateUserCode() {
		return operateUserCode;
	}
	public void setOperateUserCode(String operateUserCode) {
		this.operateUserCode = operateUserCode;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public BigDecimal getArriveGoodsWeight() {
		return arriveGoodsWeight;
	}
	public void setArriveGoodsWeight(BigDecimal arriveGoodsWeight) {
		this.arriveGoodsWeight = arriveGoodsWeight;
	}
	
}
