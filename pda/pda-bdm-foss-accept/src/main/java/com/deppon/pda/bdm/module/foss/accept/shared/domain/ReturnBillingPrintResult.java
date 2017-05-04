package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;

/**
 * 获取转寄件信息打印--返回实体类
 * @author 245955
 *Date 2016-03-15
 */
public class ReturnBillingPrintResult implements Serializable {
	private static final long serialVersionUID = 1L;
	//目的站
	private String destination;
	//件数
	private String pieces;
	 //收货人地址
    private String reciveAddress;
    //报价金额
    private String insuranceAmount;
    //代收货款
    private String codAmount;
    //签收单
    private String returnBillType;
    //包装费
    private String packageFee;
    //运费
    private String transportFee;
    //到付款合计
    private String toPayAmount;
    //返货类型
    private String returnType;
    //到达目的站城市外场的简称
    private String transportCityName;
    //到达目的站虚拟快递营业部
    private String cutsetrPickuRgname;
    
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getPieces() {
		return pieces;
	}
	public void setPieces(String pieces) {
		this.pieces = pieces;
	}
	public String getReciveAddress() {
		return reciveAddress;
	}
	public void setReciveAddress(String reciveAddress) {
		this.reciveAddress = reciveAddress;
	}
	public String getInsuranceAmount() {
		return insuranceAmount;
	}
	public void setInsuranceAmount(String insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}
	public String getCodAmount() {
		return codAmount;
	}
	public void setCodAmount(String codAmount) {
		this.codAmount = codAmount;
	}
	public String getReturnBillType() {
		return returnBillType;
	}
	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}
	public String getPackageFee() {
		return packageFee;
	}
	public void setPackageFee(String packageFee) {
		this.packageFee = packageFee;
	}
	public String getTransportFee() {
		return transportFee;
	}
	public void setTransportFee(String transportFee) {
		this.transportFee = transportFee;
	}
	public String getToPayAmount() {
		return toPayAmount;
	}
	public void setToPayAmount(String toPayAmount) {
		this.toPayAmount = toPayAmount;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public String getTransportCityName() {
		return transportCityName;
	}
	public void setTransportCityName(String transportCityName) {
		this.transportCityName = transportCityName;
	}
	public String getCutsetrPickuRgname() {
		return cutsetrPickuRgname;
	}
	public void setCutsetrPickuRgname(String cutsetrPickuRgname) {
		this.cutsetrPickuRgname = cutsetrPickuRgname;
	}
    
}
