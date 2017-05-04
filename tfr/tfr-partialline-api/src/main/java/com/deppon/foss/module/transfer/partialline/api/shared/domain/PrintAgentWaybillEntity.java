package com.deppon.foss.module.transfer.partialline.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * @author niuly
 * @function 打印代理面单实体
 * @date 2014年9月5日上午10:52:57
 */
public class PrintAgentWaybillEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	//运单号(打印/显示)
	private String waybillNo;
	//代理单号(显示)
	private String agentWaybillNo;
	//发货人(打印)
	private String deliverName;
	//发货人手机(打印)
	private String deliverTel;
	//发货人电话(打印)
	private String deliverPhone;
	//发货人省(打印)
	//private String deliverProv;
	//发货人市(打印)
	//private String deliverCity;
	//发货人区(打印)
	//private String deliverDist;
	//发货人地址(打印)
	//private String deliverAddr;
	//收货人(打印/显示)
	private String receiverName;
	//收货人手机(打印/显示)
	private String receiverTel;
	//发货人电话(打印/显示)
	private String receiverPhone;
	//收货人省份
	private  String receiverProv;
	//收货人市
	private String receiverCity;
	//收货人区
	private String receiverDist;
	//收货人地址(打印/显示)
	private String receiverAddr;
	//货物名称(打印/显示)
	private String goodsName;
	//件数(打印/显示)
	private int goodsQty;
	//重量(打印)
	private BigDecimal goodsWeight;
	//体积(打印)
	private BigDecimal goodsVolume;
	//保价(打印)
	private BigDecimal insurance;
	//打印人(显示)
	private String printer;
	//打印时间(显示)
	private Date printTime;
	//打印次数(显示)
	private int printCount;
	//流水号
	private String serialNo;
	//装车扫描时间(显示)
	private Date loadScanTime;
	private String agentCompanyCode;
	//代理公司名称(显示)
	private String agentCmpanyName;
	//状态(显示)
	private String status;
	//是否推送(显示)
	private String ispush;
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getAgentWaybillNo() {
		return agentWaybillNo;
	}
	public void setAgentWaybillNo(String agentWaybillNo) {
		this.agentWaybillNo = agentWaybillNo;
	}
	public String getDeliverName() {
		return deliverName;
	}
	public void setDeliverName(String deliverName) {
		this.deliverName = deliverName;
	}
	public String getDeliverTel() {
		return deliverTel;
	}
	public void setDeliverTel(String deliverTel) {
		this.deliverTel = deliverTel;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverTel() {
		return receiverTel;
	}
	public void setReceiverTel(String receiverTel) {
		this.receiverTel = receiverTel;
	}
	public String getReceiverAddr() {
		return receiverAddr;
	}
	public void setReceiverAddr(String receiverAddr) {
		this.receiverAddr = receiverAddr;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public int getGoodsQty() {
		return goodsQty;
	}
	public void setGoodsQty(int goodsQty) {
		this.goodsQty = goodsQty;
	}
	public BigDecimal getInsurance() {
		return insurance;
	}
	public void setInsurance(BigDecimal insurance) {
		this.insurance = insurance;
	}
	public String getPrinter() {
		return printer;
	}
	public void setPrinter(String printer) {
		this.printer = printer;
	}
	public Date getPrintTime() {
		return printTime;
	}
	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}
	public int getPrintCount() {
		return printCount;
	}
	public void setPrintCount(int printCount) {
		this.printCount = printCount;
	}
	public String getReceiverProv() {
		return receiverProv;
	}
	public void setReceiverProv(String receiverProv) {
		this.receiverProv = receiverProv;
	}
	public String getReceiverCity() {
		return receiverCity;
	}
	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}
	public String getReceiverDist() {
		return receiverDist;
	}
	public void setReceiverDist(String receiverDist) {
		this.receiverDist = receiverDist;
	}
	public String getDeliverPhone() {
		return deliverPhone;
	}
	public void setDeliverPhone(String deliverPhone) {
		this.deliverPhone = deliverPhone;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public BigDecimal getGoodsWeight() {
		return goodsWeight;
	}
	public void setGoodsWeight(BigDecimal goodsWeight) {
		this.goodsWeight = goodsWeight;
	}
	public BigDecimal getGoodsVolume() {
		return goodsVolume;
	}
	public void setGoodsVolume(BigDecimal goodsVolume) {
		this.goodsVolume = goodsVolume;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public Date getLoadScanTime() {
		return loadScanTime;
	}
	public void setLoadScanTime(Date loadScanTime) {
		this.loadScanTime = loadScanTime;
	}
	public String getAgentCmpanyName() {
		return agentCmpanyName;
	}
	public void setAgentCmpanyName(String agentCmpanyName) {
		this.agentCmpanyName = agentCmpanyName;
	}
	public String getAgentCompanyCode() {
		return agentCompanyCode;
	}
	public void setAgentCompanyCode(String agentCompanyCode) {
		this.agentCompanyCode = agentCompanyCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIspush() {
		return ispush;
	}
	public void setIspush(String ispush) {
		this.ispush = ispush;
	}
	
}
