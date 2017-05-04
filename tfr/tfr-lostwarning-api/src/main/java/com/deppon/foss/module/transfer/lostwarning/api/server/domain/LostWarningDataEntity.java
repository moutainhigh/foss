package com.deppon.foss.module.transfer.lostwarning.api.server.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 推送丢送数据实体类
 * 
 * 项目名称：tfr-lostwarning-api
 * 
 * 类名称：LostWarningDataEntity
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-6-12 下午4:14:21
 * 
 * 版权所有：上海德邦物流有限公司
 */
public class LostWarningDataEntity implements Serializable{

	private static final long serialVersionUID = -3630829926348715059L;

	// 运单号
	private String waybillNum;

	// 收货部门编码
	private String takeOverDeptCode;

	// 收货部门名称
	private String takeOverDeptName;

	// 到达部门编码
	private String arriveDeptCode;

	// 到达部门名称
	private String arriveDeptName;

	// 运输性质
	private String transNature;

	// 发货客户名
	private String sendClientName;

	// 发货客户编码
	private String sendClientCode;

	// 收货客户名称
	private String takeOverClientName;

	// 收货客户编码
	private String takeOverClientCode;

	// 提货方式
	private String pickUpType;
	
	// 付款方式
	private String payType;
	
	// 发货客户手机
	private String sendClientMobile;
	
	// 开单部门编码
	private String billingDeptName;
	
	// 开单部门名称
	private String billingDeptCode;
	
	// 货物名称
	private String goodsName;
	
	// 货物重量
	private double weight;
	
	// 货物体积
	private double volume;
	
	// 货物包装
	private String goodsPackage;
	
	// 货物件数
	private String goodsNum;
	
	// 开单时间
	private String billingTime;
	
	// 开单金
	private String billingFee;
	
	// 保价金
	private String safeMoney;
	
	// 代收货款
	private String agentFee;
	
	// 返单类
	private String returnBillType;
	
	// 储运事项
	private String storageTransport;
	
	// 是否整件丢失 1否 2是
	private String isWholeTicket;
	
	// 丢货件数
	private String loseNum;
	
	// 丢货货物包装
	private String loseGoodsPackage;
	
	// 上报场景 1：零担 2：快递
	private String repScene;
	
	// 上报方式
	private String repType;
	
	// 系统自动上报惟一ID
	private String sysAutoPkId;
	
	//处理时间
	private String repTime;
	
	//流水号实体信息
	private List<WayBillSerialInfoEntity> flowCodeList;
	
	//短信通知对象工号
    private String shortMessageCodes;

    //短信通知对象名称
    private String shortMessageNames;
	
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

	public String getTakeOverDeptName() {
		return takeOverDeptName;
	}

	public void setTakeOverDeptName(String takeOverDeptName) {
		this.takeOverDeptName = takeOverDeptName;
	}

	public String getArriveDeptCode() {
		return arriveDeptCode;
	}

	public void setArriveDeptCode(String arriveDeptCode) {
		this.arriveDeptCode = arriveDeptCode;
	}

	public String getArriveDeptName() {
		return arriveDeptName;
	}

	public void setArriveDeptName(String arriveDeptName) {
		this.arriveDeptName = arriveDeptName;
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

	public String getBillingDeptName() {
		return billingDeptName;
	}

	public void setBillingDeptName(String billingDeptName) {
		this.billingDeptName = billingDeptName;
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

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
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

	public String getIsWholeTicket() {
		return isWholeTicket;
	}

	public void setIsWholeTicket(String isWholeTicket) {
		this.isWholeTicket = isWholeTicket;
	}

	public String getLoseNum() {
		return loseNum;
	}

	public void setLoseNum(String loseNum) {
		this.loseNum = loseNum;
	}

	public String getLoseGoodsPackage() {
		return loseGoodsPackage;
	}

	public void setLoseGoodsPackage(String loseGoodsPackage) {
		this.loseGoodsPackage = loseGoodsPackage;
	}

	public String getRepScene() {
		return repScene;
	}

	public void setRepScene(String repScene) {
		this.repScene = repScene;
	}

	public String getRepType() {
		return repType;
	}

	public void setRepType(String repType) {
		this.repType = repType;
	}

	public String getSysAutoPkId() {
		return sysAutoPkId;
	}

	public void setSysAutoPkId(String sysAutoPkId) {
		this.sysAutoPkId = sysAutoPkId;
	}

	public String getRepTime() {
		return repTime;
	}

	public void setRepTime(String repTime) {
		this.repTime = repTime;
	}

	public List<WayBillSerialInfoEntity> getFlowCodeList() {
		return flowCodeList;
	}

	public void setFlowCodeList(List<WayBillSerialInfoEntity> flowCodeList) {
		this.flowCodeList = flowCodeList;
	}

	public String getShortMessageCodes() {
		return shortMessageCodes;
	}

	public void setShortMessageCodes(String shortMessageCodes) {
		this.shortMessageCodes = shortMessageCodes;
	}

	public String getShortMessageNames() {
		return shortMessageNames;
	}

	public void setShortMessageNames(String shortMessageNames) {
		this.shortMessageNames = shortMessageNames;
	}
	
}
