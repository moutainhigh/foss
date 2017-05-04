package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class ExpressWorkerCompleteDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * 员工名称
	 */
	private String emp_name;
	/*
	 * 员工编码
	 */
	private String emp_code;
	/*
	 * 收派大区
	 */
	private String bigRegion;
	/*
	 * 收派小区
	 */
	private String smallRegion;
	/*
	 * 属性
	 */
	private String emptype;
	/*
	 * 获取订单数
	 */
	private Integer getOrder;
	/*
	 * 完成订单数
	 */
	private Integer receiveOrder;
	/*
	 * 无订单开单数
	 */
	private Integer noOrderWaybill;
	/*
	 * 获取运单数
	 */
	private Integer getWaybill;
	/*
	 * 完成运单数
	 */
	private Integer deliverWaybill;
	/*
	 * 员工最后更新时间
	 */
	private Date modifyTime;
	/*
	 * 快递员状态
	 */
	private String empStatus;
	//是否登录14.7.17 AUTO-177
	private String active;
	
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getEmpStatus() {
		return empStatus;
	}
	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public String getEmp_code() {
		return emp_code;
	}
	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}
	public String getBigRegion() {
		return bigRegion;
	}
	public void setBigRegion(String bigRegion) {
		this.bigRegion = bigRegion;
	}
	public String getSmallRegion() {
		return smallRegion;
	}
	public void setSmallRegion(String smallRegion) {
		this.smallRegion = smallRegion;
	}
	public String getEmptype() {
		return emptype;
	}
	public void setEmptype(String emptype) {
		this.emptype = emptype;
	}
	public Integer getGetOrder() {
		return getOrder;
	}
	public void setGetOrder(Integer getOrder) {
		this.getOrder = getOrder;
	}
	public Integer getReceiveOrder() {
		return receiveOrder;
	}
	public void setReceiveOrder(Integer receiveOrder) {
		this.receiveOrder = receiveOrder;
	}
	public Integer getNoOrderWaybill() {
		return noOrderWaybill;
	}
	public void setNoOrderWaybill(Integer noOrderWaybill) {
		this.noOrderWaybill = noOrderWaybill;
	}
	public Integer getGetWaybill() {
		return getWaybill;
	}
	public void setGetWaybill(Integer getWaybill) {
		this.getWaybill = getWaybill;
	}
	public Integer getDeliverWaybill() {
		return deliverWaybill;
	}
	public void setDeliverWaybill(Integer deliverWaybill) {
		this.deliverWaybill = deliverWaybill;
	}
	
	

}
