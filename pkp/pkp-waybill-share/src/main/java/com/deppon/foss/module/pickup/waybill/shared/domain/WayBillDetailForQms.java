
package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.util.List;


/**
 * 运单信息
 * 
 */
public class WayBillDetailForQms {
    private String waybillNum;
    private String takeOverDeptCode;
    private String takeOverDeptName;
    private String repScene;
    private String arriveDeptCode;
    private String arriveDeptName;
    private String transNature;
    private String sendClientName;
    private String sendClientCode;
    private String takeOverClientName;
    private String takeOverClientCode;
    private String pickUpType;
    private String payType;
    private String sendClientMobile;
    private String billingDeptCode;
    private String billingDeptName;
    private String goodsName;
    private String weight;
    private String volume;
    private String goodsPackage;
    private String goodsNum;
    private String billingTime;
    private String billingFee;
    private String safeMoney;
    private String agentFee;
    private String returnBillType;
    private String storageTransport;
    private String loseFlowcode;
    private String loseGoodsPackage;
    private List<flowcodeAreaForQms> flowcodeAreasForQms;
	
	

	public String getWaybillNum() {
		return waybillNum;
	}
	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}
	public String getTakeOverDeptCode() {
		return takeOverDeptCode;
	}
	public void setTakeOverDeptCode(String takeOverDeptCode) {
		this.takeOverDeptCode = takeOverDeptCode;
	}
	public String getRepScene() {
		return repScene;
	}
	public void setRepScene(String repScene) {
		this.repScene = repScene;
	}
	public String getArriveDeptCode() {
		return arriveDeptCode;
	}
	public void setArriveDeptCode(String arriveDeptCode) {
		this.arriveDeptCode = arriveDeptCode;
	}
	public String getTransNature() {
		return transNature;
	}
	public void setTransNature(String transNature) {
		this.transNature = transNature;
	}
	public String getSendClientName() {
		return sendClientName;
	}
	public void setSendClientName(String sendClientName) {
		this.sendClientName = sendClientName;
	}
	public String getSendClientCode() {
		return sendClientCode;
	}
	public void setSendClientCode(String sendClientCode) {
		this.sendClientCode = sendClientCode;
	}
	public String getTakeOverClientName() {
		return takeOverClientName;
	}
	public void setTakeOverClientName(String takeOverClientName) {
		this.takeOverClientName = takeOverClientName;
	}
	public String getTakeOverClientCode() {
		return takeOverClientCode;
	}
	public void setTakeOverClientCode(String takeOverClientCode) {
		this.takeOverClientCode = takeOverClientCode;
	}
	public String getPickUpType() {
		return pickUpType;
	}
	public void setPickUpType(String pickUpType) {
		this.pickUpType = pickUpType;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getSendClientMobile() {
		return sendClientMobile;
	}
	public void setSendClientMobile(String sendClientMobile) {
		this.sendClientMobile = sendClientMobile;
	}
	public String getBillingDeptCode() {
		return billingDeptCode;
	}
	public void setBillingDeptCode(String billingDeptCode) {
		this.billingDeptCode = billingDeptCode;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getGoodsPackage() {
		return goodsPackage;
	}
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}
	public String getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
	}
	public String getBillingTime() {
		return billingTime;
	}
	public void setBillingTime(String billingTime) {
		this.billingTime = billingTime;
	}
	public String getBillingFee() {
		return billingFee;
	}
	public void setBillingFee(String billingFee) {
		this.billingFee = billingFee;
	}
	public String getSafeMoney() {
		return safeMoney;
	}
	public void setSafeMoney(String safeMoney) {
		this.safeMoney = safeMoney;
	}
	public String getAgentFee() {
		return agentFee;
	}
	public void setAgentFee(String agentFee) {
		this.agentFee = agentFee;
	}
	public String getReturnBillType() {
		return returnBillType;
	}
	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}
	public String getStorageTransport() {
		return storageTransport;
	}
	public void setStorageTransport(String storageTransport) {
		this.storageTransport = storageTransport;
	}
	public String getLoseFlowcode() {
		return loseFlowcode;
	}
	public void setLoseFlowcode(String loseFlowcode) {
		this.loseFlowcode = loseFlowcode;
	}
	public String getLoseGoodsPackage() {
		return loseGoodsPackage;
	}
	public void setLoseGoodsPackage(String loseGoodsPackage) {
		this.loseGoodsPackage = loseGoodsPackage;
	}
	public List<flowcodeAreaForQms> getFlowcodeAreasForQms() {
		return flowcodeAreasForQms;
	}
	public void setFlowcodeAreasForQms(List<flowcodeAreaForQms> flowcodeAreasForQms) {
		this.flowcodeAreasForQms = flowcodeAreasForQms;
	}
	public String getTakeOverDeptName() {
		return takeOverDeptName;
	}
	public void setTakeOverDeptName(String takeOverDeptName) {
		this.takeOverDeptName = takeOverDeptName;
	}
	public String getArriveDeptName() {
		return arriveDeptName;
	}
	public void setArriveDeptName(String arriveDeptName) {
		this.arriveDeptName = arriveDeptName;
	}
	public String getBillingDeptName() {
		return billingDeptName;
	}
	public void setBillingDeptName(String billingDeptName) {
		this.billingDeptName = billingDeptName;
	}
}
