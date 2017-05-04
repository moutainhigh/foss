package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;


/**
 * @ClassName ReturnBillingEWaybillEntity.java 
 * @Description 返货开单下拉明细
 * @author 201638
 * @date 2015-3-9
 */
public class ReturnBillingEWaybillEntity  implements Serializable{
	/**  
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 1L;
	//目的站
	private String destination;
	//运输类型
    private String transportType;
    //件数
  	private String pieces;
  	//受理状态
	private String returnStatus;
	//返货信息（类似于备注，字段长一点）
    private String returnGoodsInfo;
    //子母件母单号
    private String parentWaybillNo;
    
    //返货方式
    private String returnMode;
    
    //收货人信息，保价金额，代收货款，签收单，包装费，运费，到付款合计
    //收货人编码
    private String reciveProvCode;
    //收货人名称
    private String reciveProvName;
    //收货人城市
    private String reciveCityCode;
    //收货人城市名称
    private String reciveCityName;
    //收货人区县
    private String reciveCountyCode;
    //收货人区县名称
    private String reciveCountyName;
    //收货人编码
    private String reciveCustomeCode;
    //收货人名称
    private String reciveCustomeName;
    //移动电话
    private String reciveMobilePhone;
    //座机
    private String reciveHomePhone;
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
    
    
	public String getCutsetrPickuRgname() {
		return cutsetrPickuRgname;
	}
	public void setCutsetrPickuRgname(String cutsetrPickuRgname) {
		this.cutsetrPickuRgname = cutsetrPickuRgname;
	}
	public String getTransportCityName() {
		return transportCityName;
	}
	public void setTransportCityName(String transportCityName) {
		this.transportCityName = transportCityName;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public String getReturnMode() {
		return returnMode;
	}
	public void setReturnMode(String returnMode) {
		this.returnMode = returnMode;
	}
	public String getParentWaybillNo() {
		return parentWaybillNo;
	}
	public void setParentWaybillNo(String parentWaybillNo) {
		this.parentWaybillNo = parentWaybillNo;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getTransportType() {
		return transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	public String getPieces() {
		return pieces;
	}
	public void setPieces(String pieces) {
		this.pieces = pieces;
	}
	public String getReturnGoodsInfo() {
		return returnGoodsInfo;
	}
	public void setReturnGoodsInfo(String returnGoodsInfo) {
		this.returnGoodsInfo = returnGoodsInfo;
	}
	public String getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}
	public String getReciveProvCode() {
		return reciveProvCode;
	}
	public void setReciveProvCode(String reciveProvCode) {
		this.reciveProvCode = reciveProvCode;
	}
	public String getReciveProvName() {
		return reciveProvName;
	}
	public void setReciveProvName(String reciveProvName) {
		this.reciveProvName = reciveProvName;
	}
	public String getReciveCityCode() {
		return reciveCityCode;
	}
	public void setReciveCityCode(String reciveCityCode) {
		this.reciveCityCode = reciveCityCode;
	}
	public String getReciveCityName() {
		return reciveCityName;
	}
	public void setReciveCityName(String reciveCityName) {
		this.reciveCityName = reciveCityName;
	}
	public String getReciveCountyCode() {
		return reciveCountyCode;
	}
	public void setReciveCountyCode(String reciveCountyCode) {
		this.reciveCountyCode = reciveCountyCode;
	}
	public String getReciveCountyName() {
		return reciveCountyName;
	}
	public void setReciveCountyName(String reciveCountyName) {
		this.reciveCountyName = reciveCountyName;
	}
	public String getReciveCustomeCode() {
		return reciveCustomeCode;
	}
	public void setReciveCustomeCode(String reciveCustomeCode) {
		this.reciveCustomeCode = reciveCustomeCode;
	}
	public String getReciveCustomeName() {
		return reciveCustomeName;
	}
	public void setReciveCustomeName(String reciveCustomeName) {
		this.reciveCustomeName = reciveCustomeName;
	}
	public String getReciveMobilePhone() {
		return reciveMobilePhone;
	}
	public void setReciveMobilePhone(String reciveMobilePhone) {
		this.reciveMobilePhone = reciveMobilePhone;
	}
	public String getReciveHomePhone() {
		return reciveHomePhone;
	}
	public void setReciveHomePhone(String reciveHomePhone) {
		this.reciveHomePhone = reciveHomePhone;
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
    
	
}
