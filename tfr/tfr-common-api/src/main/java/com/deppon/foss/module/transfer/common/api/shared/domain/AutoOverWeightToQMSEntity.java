package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;


public class AutoOverWeightToQMSEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long subHasWaybillId;
	//托运人姓名
    private String consignor;
    //收货人
    private String consignee;
     //收货部门名称
  	private String receiveDeptName;
     //到达部门名称
    private String arriveDeptName;
    //责任人工号
	private String respEmpCode;
	//责任人姓名
	private String respEmpName;
	//责任部门编号
	private String respDeptCode;
	//责任部门名称
	private String respDeptName;
	//责任事业部编号
	private String respDivisionCode;
	//责任事业部名称
	private String respDivisionName;
     //开单部门标杆编码
    private String billingDeptCode;
    //开单部门名称
    private String billingDeptName;
    //货物名称
    private String goodsName;
	//原重量L
	private BigDecimal weight;
	//原体积
	private BigDecimal volume;
	//实际重量
	private BigDecimal actualWeight;

	
	public Long getSubHasWaybillId() {
		return subHasWaybillId;
	}
	public void setSubHasWaybillId(Long subHasWaybillId) {
		this.subHasWaybillId = subHasWaybillId;
	}
	public String getConsignor() {
		return consignor;
	}
	public void setConsignor(String consignor) {
		this.consignor = consignor;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getReceiveDeptName() {
		return receiveDeptName;
	}
	public void setReceiveDeptName(String receiveDeptName) {
		this.receiveDeptName = receiveDeptName;
	}
	public String getArriveDeptName() {
		return arriveDeptName;
	}
	public void setArriveDeptName(String arriveDeptName) {
		this.arriveDeptName = arriveDeptName;
	}
	public String getRespEmpCode() {
		return respEmpCode;
	}
	public void setRespEmpCode(String respEmpCode) {
		this.respEmpCode = respEmpCode;
	}
	public String getRespEmpName() {
		return respEmpName;
	}
	public void setRespEmpName(String respEmpName) {
		this.respEmpName = respEmpName;
	}
	public String getRespDeptCode() {
		return respDeptCode;
	}
	public void setRespDeptCode(String respDeptCode) {
		this.respDeptCode = respDeptCode;
	}

	public String getRespDivisionCode() {
		return respDivisionCode;
	}
	public void setRespDivisionCode(String respDivisionCode) {
		this.respDivisionCode = respDivisionCode;
	}
	public String getRespDivisionName() {
		return respDivisionName;
	}
	public void setRespDivisionName(String respDivisionName) {
		this.respDivisionName = respDivisionName;
	}
	public String getBillingDeptCode() {
		return billingDeptCode;
	}
	public void setBillingDeptCode(String billingDeptCode) {
		this.billingDeptCode = billingDeptCode;
	}
	
	public String getBillingDeptName() {
		return billingDeptName;
	}
	public void setBillingDeptName(String billingDeptName) {
		this.billingDeptName = billingDeptName;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
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
	public BigDecimal getActualWeight() {
		return actualWeight;
	}
	public void setActualWeight(BigDecimal actualWeight) {
		this.actualWeight = actualWeight;
	}
	public String getRespDeptName() {
		return respDeptName;
	}
	public void setRespDeptName(String respDeptName) {
		this.respDeptName = respDeptName;
	}
	
}
