/*
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.shared.dto
 * FILE    NAME: PDAComplementDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 分拣扫描
 * @author dp-duyi
 * @date 2013-7-23 下午4:29:00
 */
public class PDAComplementDto implements Serializable{

	private static final long serialVersionUID = 7486008440385814331L;
	/**运单号*/
	private String wayBillNo;
	/**目的站名称*/
	private String targetOrgCode;
	/**收货地址*/
	private String receiveCustomerAddress;
	/**补码时间*/
	private Date complementTime;
	/**提货网点编码  补码简称*/
	private String reachOrgCode;
	/**提货网点名称*/
	private String reachOrgName;
	/**是否补码**/
	private String beAddCode;
	/**最终外场名称**/
	private String finalOutDept;
	/**是否电子面单**/
	private String beEWaybill;
	// 下一部门 （王）
	private String nextOrgCode;
	// 产品类型（运输性质，运单表）
	private String productCode;
	
	/**
	 * @return the nextOrgCode
	 */
	public String getNextOrgCode() {
		return nextOrgCode;
	}
	/**
	 * @param nextOrgCode the nextOrgCode to set
	 */
	public void setNextOrgCode(String nextOrgCode) {
		this.nextOrgCode = nextOrgCode;
	}
	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}
	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	/**外场简称 -- 最终外场
	* @fields simpleOrgName
	* @author 14022-foss-songjie
	* @update 2015年8月20日 上午10:44:06
	* @version V1.0
	*/
	private String simpleOrgName;
	
	
	
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	public String getTargetOrgCode() {
		return targetOrgCode;
	}
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}
	public Date getComplementTime() {
		return complementTime;
	}
	public void setComplementTime(Date complementTime) {
		this.complementTime = complementTime;
	}
	public String getReachOrgCode() {
		return reachOrgCode;
	}
	public void setReachOrgCode(String reachOrgCode) {
		this.reachOrgCode = reachOrgCode;
	}
	public String getReachOrgName() {
		return reachOrgName;
	}
	public void setReachOrgName(String reachOrgName) {
		this.reachOrgName = reachOrgName;
	}
	public String getBeAddCode() {
		return beAddCode;
	}
	public void setBeAddCode(String beAddCode) {
		this.beAddCode = beAddCode;
	}
	public String getFinalOutDept() {
		return finalOutDept;
	}
	public void setFinalOutDept(String finalOutDept) {
		this.finalOutDept = finalOutDept;
	}
	public String getBeEWaybill() {
		return beEWaybill;
	}
	public void setBeEWaybill(String beEWaybill) {
		this.beEWaybill = beEWaybill;
	}
	public String getSimpleOrgName() {
		return simpleOrgName;
	}
	public void setSimpleOrgName(String simpleOrgName) {
		this.simpleOrgName = simpleOrgName;
	}
	
	
	
}
