package com.deppon.foss.module.pickup.sign.api.shared.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DopVo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	//运单号
	private String waybillNo;
	//dop数据传来时间
	private Date signTime;
	//到达联号
	private String arrivesheetNo;
	//到达件数
	private int arrivesheetGoodsQty;
	//提货人姓名
	private String deliverManName;
	//证件号码
	private String identifyCode;
	//证件类型
	private String identifyType;
	//签收情况
	private String situation;
	//签收件数
	private int signGoodsQty;
	//库存件数
	private int stockGoodsQty;
	//运输性质
	private String productCode;
	//是否整车运单
	private String isWholeVehicle;
	//签收备注
	private String signNote;
	//当前部门是否收货部门
	private String isCurrentOrgEqReciveOrg;
	//订单号
	private String OrderNo;
	//签收状态
	private String signStatus;
	//后台服务器当前时间
	private Date serviceTime;
	//外包装是否完好
	private String packingResult;
	//短少量
	private int alittleShort;
	//供应商名称
	private String supplierName;
	//供应商编码
	private String supplierCode;
	//付款方式
	private String payType;
	//收货部门名称
	private String receiveOrgName;
	//收货部门编码
	private String receiveOrgCode;
	//收货人
	private String receiverName;
	//收货人联系方式
	private String receiverPhone;
	//序列号
	private String serialNo;
	//特殊增值服务
	private String specialAddValueService;
	
	
	public String getDeliverOrgName() {
		return deliverOrgName;
	}
	public void setDeliverOrgName(String deliverOrgName) {
		this.deliverOrgName = deliverOrgName;
	}
	public String getDeliverOrgCode() {
		return deliverOrgCode;
	}
	public void setDeliverOrgCode(String deliverOrgCode) {
		this.deliverOrgCode = deliverOrgCode;
	}
	//提货网店名称
	private String deliverOrgName;
	//提货网店编码
	private String deliverOrgCode;
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public String getArrivesheetNo() {
		return arrivesheetNo;
	}
	public void setArrivesheetNo(String arrivesheetNo) {
		this.arrivesheetNo = arrivesheetNo;
	}
	public int getArrivesheetGoodsQty() {
		return arrivesheetGoodsQty;
	}
	public void setArrivesheetGoodsQty(int arrivesheetGoodsQty) {
		this.arrivesheetGoodsQty = arrivesheetGoodsQty;
	}
	public String getDeliverManName() {
		return deliverManName;
	}
	public void setDeliverManName(String deliverManName) {
		this.deliverManName = deliverManName;
	}
	public String getIdentifyCode() {
		return identifyCode;
	}
	public void setIdentifyCode(String identifyCode) {
		this.identifyCode = identifyCode;
	}
	public String getIdentifyType() {
		return identifyType;
	}
	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}
	public String getSituation() {
		return situation;
	}
	public void setSituation(String situation) {
		this.situation = situation;
	}
	public int getSignGoodsQty() {
		return signGoodsQty;
	}
	public void setSignGoodsQty(int signGoodsQty) {
		this.signGoodsQty = signGoodsQty;
	}
	public int getStockGoodsQty() {
		return stockGoodsQty;
	}
	public void setStockGoodsQty(int stockGoodsQty) {
		this.stockGoodsQty = stockGoodsQty;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getIsWholeVehicle() {
		return isWholeVehicle;
	}
	public void setIsWholeVehicle(String isWholeVehicle) {
		this.isWholeVehicle = isWholeVehicle;
	}
	public String getSignNote() {
		return signNote;
	}
	public void setSignNote(String signNote) {
		this.signNote = signNote;
	}
	public String getIsCurrentOrgEqReciveOrg() {
		return isCurrentOrgEqReciveOrg;
	}
	public void setIsCurrentOrgEqReciveOrg(String isCurrentOrgEqReciveOrg) {
		this.isCurrentOrgEqReciveOrg = isCurrentOrgEqReciveOrg;
	}
	public String getOrderNo() {
		return OrderNo;
	}
	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}
	public String getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}
	public Date getServiceTime() {
		return serviceTime;
	}
	public void setServiceTime(Date serviceTime) {
		this.serviceTime = serviceTime;
	}
	public String getPackingResult() {
		return packingResult;
	}
	public void setPackingResult(String packingResult) {
		this.packingResult = packingResult;
	}
	public int getAlittleShort() {
		return alittleShort;
	}
	public void setAlittleShort(int alittleShort) {
		this.alittleShort = alittleShort;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getReceiveOrgName() {
		return receiveOrgName;
	}
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getSpecialAddValueService() {
		return specialAddValueService;
	}
	public void setSpecialAddValueService(String specialAddValueService) {
		this.specialAddValueService = specialAddValueService;
	}
	public void setId(String id){
		this.id=id;
	}
	public String getId(){
		return this.id;
	}
	
/*	public void setSignTime(Date signTime) {
		String pattern="yyyy-MM-dd HH:mm:ss";  
		
		SimpleDateFormat dateFormat=new SimpleDateFormat(pattern); 
		String date=dateFormat.format(signTime);
		Date d=new Date();
		try {
			d=dateFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.signTime = d;
	}*/
	
/*	public static void main(String args[]){
		DopVo dv=new DopVo();
		String pattern="yyyy-MM-dd HH:mm:ss";  
		SimpleDateFormat df=new SimpleDateFormat(pattern); 
		try {
			Date d=df.parse("2015-9-9 9:49:21");
			dv.setSignTime(new Timestamp(d.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long t=(dv.signTime.getTime()-new Date().getTime())/(3600*1000);
		System.out.println("--------"+dv.signTime+"**** "+new Date()+"-------"+t);
		System.out.println(" ***  "+(new Date()-dv.signTime));
	}*/
	
}
