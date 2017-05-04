package com.deppon.pda.bdm.module.foss.unload.shared.domain.kuaidi;

import java.util.Date;


/**
 * 查询补码返回实体类
 * @author wenwuneng
 * @date 2013-07-22
 * @version 1.0
 * @since
 */
public class QryComplementResModel {
	/**
	 * 运单号
	 */
	private String wblCode;
	/**
	 * 到达部门编号
	 */
	private String targetOrgCode;
	/**
	 * 目的部门
	 */
	private String deryCrgAddress;
	/**
	 * 补码时间
	 */
	private Date complementDate;
	/**
	 * 提货网点编码
	 */
	private String reachOrgCode;
	/**
	 * 提货网点名称
	 */
	private String reachOrgName;
	/**
	 * 是否补码
	 */
	private String isBeAddCode;
	
	/**
	 * 最终到达外场
	 */
	private String FinalOutDept;
	
	//是否电子面单 author:245960 Date:2015-06-29
	private String beEWaybill;
	//城市简称
	private String cityShort;
	
	public String getCityShort() {
		return cityShort;
	}
	public void setCityShort(String cityShort) {
		this.cityShort = cityShort;
	}
	public String getBeEWaybill() {
		return beEWaybill;
	}
	public void setBeEWaybill(String beEWaybill) {
		this.beEWaybill = beEWaybill;
	}
	public String getFinalOutDept() {
		return FinalOutDept;
	}
	public void setFinalOutDept(String finalOutDept) {
		FinalOutDept = finalOutDept;
	}
	public String getWblCode() {
		return wblCode;
	}
	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}
	
	public String getTargetOrgCode() {
		return targetOrgCode;
	}
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}
	public String getDeryCrgAddress() {
		return deryCrgAddress;
	}
	public void setDeryCrgAddress(String deryCrgAddress) {
		this.deryCrgAddress = deryCrgAddress;
	}
	public Date getComplementDate() {
		return complementDate;
	}
	public void setComplementDate(Date complementDate) {
		this.complementDate = complementDate;
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
	public String getIsBeAddCode() {
		return isBeAddCode;
	}
	public void setIsBeAddCode(String isBeAddCode) {
		this.isBeAddCode = isBeAddCode;
	}
	
	
}
