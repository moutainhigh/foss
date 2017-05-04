package com.deppon.foss.shared.request;

import java.io.Serializable;


/**
 * <p>
 * <h2>简述</h2>
 * 		<ol>对接FOSS系统的保理业务标记推送接口的请求参数实体类</ol>
 * <h2>功能描述</h2>
 * 		<ol>对接FOSS系统的保理业务标记推送接口的请求参数实体类</ol>
 * <h2>修改历史</h2>
 *    <ol>如有修改，添加修改历史</ol>
 * </p>
 * @author Administrator
 * @2016-9-27
 * @version 1.0
 */
@SuppressWarnings("serial")
public class FOSSFactorFlagRequest implements Serializable{

	private String cusCode;//贷款客户编码
	private String outfieldName;//外场名称
	private String outfieldCode;//外场编码
	private String supplierName;//供应商名称
	private String supplierCode;//供应商编码
	private String factorBeginTime;//保理开始日期，格式为"yyyy-MM-dd"
	private String factorEndTime;//保理结束日期，格式为"yyyy-MM-dd"
	private String account;//保理回款账号
	private String operateType;//操作类型，0.新增1.修改 
	
	public FOSSFactorFlagRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public FOSSFactorFlagRequest(String cusCode, String outfieldName,
			String outfieldCode, String supplierName, String supplierCode,
			String factorBeginTime, String factorEndTime, String account,
			String operateType) {
		super();
		this.cusCode = cusCode;
		this.outfieldName = outfieldName;
		this.outfieldCode = outfieldCode;
		this.supplierName = supplierName;
		this.supplierCode = supplierCode;
		this.factorBeginTime = factorBeginTime;
		this.factorEndTime = factorEndTime;
		this.account = account;
		this.operateType = operateType;
	}
	
	public String getCusCode() {
		return cusCode;
	}
	public String getOutfieldName() {
		return outfieldName;
	}
	public String getOutfieldCode() {
		return outfieldCode;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public String getFactorBeginTime() {
		return factorBeginTime;
	}
	public String getFactorEndTime() {
		return factorEndTime;
	}
	public String getAccount() {
		return account;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}
	public void setOutfieldName(String outfieldName) {
		this.outfieldName = outfieldName;
	}
	public void setOutfieldCode(String outfieldCode) {
		this.outfieldCode = outfieldCode;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public void setFactorBeginTime(String factorBeginTime) {
		this.factorBeginTime = factorBeginTime;
	}
	public void setFactorEndTime(String factorEndTime) {
		this.factorEndTime = factorEndTime;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	
}
